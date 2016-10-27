/**
 * @(#)MASMonitor.java     2011-4-11
 *
 * Copyright 2011 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.kingsoft.common.sms;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.huawei.eie.api.sm.DBSMProxy;
import com.huawei.eie.api.sm.SmReceiveBean;
import com.huawei.eie.api.sm.SmSendBean;
import com.kingsoft.control.database.Connection;
import com.kingsoft.control.database.ConnectionProvider;
import com.kingsoft.control.database.ConnectionProviderFactory;
import com.kingsoft.control.tasks.AbstractLifeCycle;
import com.kingsoft.control.tasks.LifeCycle;
import com.kingsoft.dao.entity.sms.SmsSetup;
import com.kingsoft.dao.implement.mysql.sms.SmsSetupDAOImpl;
import com.kingsoft.dao.interfaces.sms.SmsSetupDAO;

/**
 * 移动代理服务器(MAS)监听器
 * 
 * @author zhangxulong
 * 
 * @version 2011-4-11
 * 
 * @since JDK 1.6.0_10-rc2
 * 
 */
public class MASMonitor extends AbstractLifeCycle {
	private static Logger S_Logger = Logger.getLogger(MASMonitor.class);
	private static boolean S_execute = true;// 是否开启短信服务
	// private static String S_serviceID = null;// 业务服务类型
	private static int S_needStatusReport = 1;// 是否需要短信到达提醒,默认提醒
	// private static String S_orgAddr = null;;// 本地号码
	private static SmsSetup smsSetup = null;

	private static DBSMProxy smproxy = new DBSMProxy();
	private static int expTimes = 0;// 接口出现异常次数

	public void afterStarted(LifeCycle arg0, Object... arg1) throws Exception {
		S_Logger.info("Initing MAS Service Monitor...");

		ConnectionProvider provider = null;// 默认数据库连接提供者
		Connection conn = null;// 数据库连接
		SmsSetupDAO smsSetupDAO = null;// 短信服务设置持久层对象
		try {
			// 获取默认数据库连接
			provider = ConnectionProviderFactory.getConnectionProvider();
			conn = provider.getConnection();

			// 设置短信服务的配置参数
			smsSetupDAO = new SmsSetupDAOImpl();
			smsSetupDAO.setConnection(conn);

			smsSetup = smsSetupDAO.selectByPrimaryKeys(1);
			// 启动MAS短信服务
			startUp();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (provider != null && conn != null) {
				provider.releaseConnection(conn);
			}
			smsSetupDAO = null;
			provider = null;
		}
	}

	public void afterStoped(LifeCycle arg0, Object... arg1) throws Exception {
	}

	public void beforeStarted(LifeCycle arg0, Object... arg1) {
	}

	public void beforeStoped(LifeCycle arg0, Object... arg1) throws Exception {
		S_Logger.info("Shutdown MAS Service Monitor...");

		// 释放MAS短信服务占用资源
		cleanUp();
		S_Logger = null;
		smsSetup = null;
	}

	/**
	 * 启动MAS短信服务
	 * 
	 * @param smsSetup
	 *            短信服务的配置参数
	 */
	public static final boolean startUp() {
		S_Logger.info("Start MAS Service Monitor...actflag:" + smsSetup.getActflag() + "  execute:" + S_execute);
		if (smsSetup.getActflag() == 1) {
			try {
				/** 初始化函数 */
				Map<String, String> paras = new HashMap<String, String>();

				// 企业信息机短信接口db的url。
				paras.put("url", smsSetup.getDbName());

				// 企业信息机短信接口db登陆用户名。
				paras.put("user", smsSetup.getDbUser());

				// 企业信息机短信接口db登陆的密码。
				paras.put("password", smsSetup.getDbPass());
				smproxy.initConn(paras);

				/** 用户登陆 */
				smproxy.login(smsSetup.getUserId(), smsSetup.getUserPass());
				S_execute = true;
			} catch (Exception e) {
				S_execute = false;
				e.printStackTrace();
			}
		}
		return S_execute;
	}

	/**
	 * 释放函数
	 */
	public static final boolean cleanUp() {
		S_Logger.info("Clean MAS Service Monitor...");
		try {
			smproxy.logout();
			smproxy.destroy();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			S_execute = false;
			expTimes = 0;
		}
		return true;
	}

	/**
	 * 发送短信
	 * 
	 * @param atTime
	 *            发送短信的时间
	 * @param sourceAddr
	 *            待发送短信的源地址
	 * @param destAddr
	 *            待发送短信的目的地址
	 * @param content
	 *            短信内容
	 * @return boolean 发送状态,(true:发送成功;false:发送失败)
	 */
	public static final boolean send(SmSendBean bean) {
		try {
			if (S_execute) {
				// 提交短信
				bean.setSmServiceId(smsSetup.getServiceId());// 服务类型
				bean.setSmNeedStateReport(S_needStatusReport);// 短信到达提醒
				bean.setSmOrgAddr(smsSetup.getTelephone());// 本地号码
				int[] ret = null;
				ret = smproxy.sendSm(bean);

				if (ret[0] < 1) {
					exp();
				}

				return ret[0] > 0;
			}
		} catch (Exception e) {
			S_Logger.error("发送短信异常");

			e.printStackTrace();

		}
		return false;
	}

	/**
	 * 接收短信
	 * 
	 * @return SmReceiveBean[] 接收短信对象
	 */
	public static final SmReceiveBean[] receive() {
		try {
			if (S_execute) {
				// 提交短信
				return smproxy.getReceivedSms(100, null, null, null, null);
			}
		} catch (Exception e) {
			S_Logger.error("接收短信异常");
			exp();
			e.printStackTrace();

		}

		return new SmReceiveBean[0];
	}

	/**
	 * 异常处理类,当出现异常次数达到一定值时关闭此项目服务
	 */
	public static void exp() {
		expTimes++;
		S_Logger.error("MAS Service Monitor Exception Times " + expTimes);
		if (expTimes > 5) {
			// 关闭接口
			cleanUp();
			new Thread(new RestartProcess()).start();
		}
	}

	public static boolean isS_execute() {
		return S_execute;
	}

	public static void setS_execute(boolean sExecute) {
		S_execute = sExecute;
	}

	public static SmsSetup getSmsSetup() {
		return smsSetup;
	}

	public static void setSmsSetup(SmsSetup smsSetup) {
		MASMonitor.smsSetup = smsSetup;
	}
}
