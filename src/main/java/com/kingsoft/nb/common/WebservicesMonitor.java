/**
 * @(#)WebservicesMonitor.java     2011-3-22
 *
 * Copyright 2011 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.kingsoft.nb.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kingsoft.control.Console;
import com.kingsoft.control.database.Connection;
import com.kingsoft.control.database.ConnectionProvider;
import com.kingsoft.control.database.ConnectionProviderFactory;
import com.kingsoft.control.tasks.AbstractLifeCycle;
import com.kingsoft.control.tasks.LifeCycle;
import com.kingsoft.control.util.StringManage;
import com.kingsoft.nb.dao.entity.manager.ServiceCenter;
import com.kingsoft.nb.dao.entity.system.ColumnSetup;
import com.kingsoft.nb.dao.entity.system.GlobalVariables;
import com.kingsoft.nb.dao.implement.mysql.manager.ServiceCenterDAOImpl;
import com.kingsoft.nb.dao.interfaces.manager.ServiceCenterDAO;
import com.kingsoft.nb.dao.interfaces.system.ColumnSetupDAO;
import com.kingsoft.nb.dao.interfaces.system.GlobalVariablesDAO;
import com.kingsoft.nb.outer.npedi.LoginEdi;
import com.kingsoft.nb.outer.npedi.LoginNewEdi;

/**
 * webservices服务控制中心
 * 
 * @author Administrator
 * 
 * @version 2011-3-22
 * 
 * @since JDK 1.6
 * 
 */
public class WebservicesMonitor extends AbstractLifeCycle {
	public static int FS_CRM = 2;// crm 的webservice id
	public static HashMap<Integer,ServiceCenter> FS_WebService = new HashMap<Integer,ServiceCenter>();// 服务中心webservices接口连接参数对象
	public static HashMap<String, List<ColumnSetup>> FS_Column_Setups = new HashMap<String, List<ColumnSetup>>();// 当前所有公司的自定义录入界面设置
	
	public static LoginEdi FS_FETCH_LOGIN = null;// 服务中心webservices接口连接参数对象
	public static LoginNewEdi FS_FETCH_NEW_LOGIN = null;// 宁波新版EDI连接参数对象
	public static GlobalVariables FS_FETCH_OUT = null;// 查询外抓取设置
	public static List<LoginNewEdi> FS_EDI_LIST = new ArrayList<LoginNewEdi>();// EDI链接池

	@Override
	public void afterStarted(LifeCycle arg0, Object... arg1) throws Exception {
		ConnectionProvider provider = null;// 娄据库连接提供者
		Connection conn = null;// 数据库连接
		try {
			provider = ConnectionProviderFactory.getConnectionProvider();
			conn = provider.getConnection();

			// 操作数据库，查询出设置好的服务中心webservices连接参数
			ServiceCenterDAO serviceCenterDAO = new ServiceCenterDAOImpl();
			serviceCenterDAO.setConnection(conn);
			ServiceCenter[] serviceCenter = serviceCenterDAO
					.select(new ServiceCenter());
			for(ServiceCenter s : serviceCenter){
				FS_WebService.put(s.getId(), s);
			}
			//设置连服务中心的
			WebservicesMonitor.setServiceCenter(FS_WebService.get(1));
			
			// 初始化自定义界面信息
			ColumnSetupDAO columnSetupDAO = (ColumnSetupDAO) serviceCenterDAO
					.getDAO(ColumnSetupDAO.class);
			columnSetupDAO.initColumnSetup();
			
			//取外抓取设置
			GlobalVariablesDAO globalVariablesDAO = (GlobalVariablesDAO) serviceCenterDAO.getDAO(GlobalVariablesDAO.class);
			FS_FETCH_OUT = globalVariablesDAO.selectByPrimaryKeys("wwwFetch");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				if (provider != null)
					provider.releaseConnection(conn);
		}
	}

	@Override
	public void afterStoped(LifeCycle arg0, Object... arg1) throws Exception {
	}

	@Override
	public void beforeStarted(LifeCycle arg0, Object... arg1) {
	}

	@Override
	public void beforeStoped(LifeCycle arg0, Object... arg1) throws Exception {
		if(FS_WebService != null){
			FS_WebService.clear();
			FS_WebService = null;
		}
		if(FS_Column_Setups != null){
			for(List<ColumnSetup> l : FS_Column_Setups.values()){
				l.clear();
				l = null;
			}
			FS_Column_Setups.clear();
			FS_Column_Setups = null;
		}
		if(FS_FETCH_LOGIN != null){
			FS_FETCH_LOGIN = null;
		}
		if (FS_FETCH_NEW_LOGIN != null){
			FS_FETCH_NEW_LOGIN = null;
		}
		if(FS_FETCH_OUT != null){
			FS_FETCH_OUT = null;
		}
	}

	/**
	 * 设置当前服务中心webservice接口连接参数对象
	 * 
	 * 在修改参数时同步
	 * 
	 * @param serviceCenter
	 *            webservice接口连接参数对象
	 */
	public synchronized static void setServiceCenter(ServiceCenter serviceCenter) {
		if (serviceCenter != null){
			FS_WebService.put(serviceCenter.getId(), serviceCenter);
		}
	}
	
	/**
	 * 设置当前授权公司界面自定义信息
	 * 
	 * 在修改参数时同步
	 * 
	 * @param accreditId
	 *            授权编号
	 * @param columnSetups
	 *            界面自定义信息
	 */
	public synchronized static void refurbish(String accreditId,
			List<ColumnSetup> columnSetups) {
		if (FS_Column_Setups != null) {
			FS_Column_Setups.put(accreditId, columnSetups);
		}
	}

	/**
	 * 初始化EDI链接数
	 *
	 * @param num
	 * @return
	 */
	public static List<LoginNewEdi> initEdiList(int num) {
		List<LoginNewEdi> ediList = new ArrayList<LoginNewEdi>();
		LoginNewEdi edi;
		for (int i = 0; i < num; i++) {
			edi = new LoginNewEdi();
			try {
				edi.login();
			} catch (Exception e) {
				e.printStackTrace();
			}
			edi.num = ediList.size();
			ediList.add(edi);
		}
		return ediList;
	}

	/**
	 * 获取EDI链接
	 * @return
     */
	public synchronized static LoginNewEdi getEDI() {
		LoginNewEdi edi = null;
		String now = Console.FS_TIME.getNow();
		if (WebservicesMonitor.FS_EDI_LIST.size() > 0) {
			for (int i = 0; i < WebservicesMonitor.FS_EDI_LIST.size(); i++) {
				LoginNewEdi loginNewEdi = WebservicesMonitor.FS_EDI_LIST.get(i);
				try {
					if (!StringManage.isEmpty(loginNewEdi.cookieTime)) {
						if (Console.FS_TIME.compareSecond(now, loginNewEdi.cookieTime) > 5) {
							loginNewEdi.isKeepConnection();
							if (!loginNewEdi.isLogin()) {
								loginNewEdi.login();
							}
							if (loginNewEdi.isLogin()) {
								edi = loginNewEdi;
								// 把当前对象放到最后面
								WebservicesMonitor.FS_EDI_LIST.remove(loginNewEdi);
								WebservicesMonitor.FS_EDI_LIST.add(loginNewEdi);
								break;
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		if (edi == null) {
			edi = new LoginNewEdi();
			try {
				edi.login();
				edi.num = WebservicesMonitor.FS_EDI_LIST.size();
				WebservicesMonitor.FS_EDI_LIST.add(edi);// 添加到列表上
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return edi;
	}

}
