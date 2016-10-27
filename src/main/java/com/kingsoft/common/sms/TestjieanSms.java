package com.kingsoft.common.sms;

import java.util.HashMap;
import java.util.Map;

import com.huawei.eie.api.sm.DBSMProxy;
import com.kingsoft.dao.entity.sms.SmsSetup;

public class TestjieanSms {

	public static void main(String[] args) {
		DBSMProxy smproxy = new DBSMProxy();
		SmsSetup smsSetup = new SmsSetup();
		smsSetup.setId(1);
		smsSetup.setTelephone("106573017713");
		smsSetup.setDbName("jdbc:microsoft:sqlserver://183.238.157.26:50110;DatabaseName=db_customsms");
		smsSetup.setDbUser("CustomSMS");
		smsSetup.setDbPass("MasInfoEIE@20110406!@#");
		smsSetup.setUserId("YFL");
		smsSetup.setUserPass("3611101");
//		smsSetup.setUserPass("325510");
		smsSetup.setServiceId("MGD0019901");
		smsSetup.setActflag(1);
		try {
			/** 初始化函数 */
			Map<String, String> paras = new HashMap<String, String>();

			// 企业信息机短信接口db的url。
			paras.put("url", smsSetup.getDbName());

			// 企业信息机短信接口db登陆用户名。
			paras.put("user", smsSetup.getDbUser());

			// 企业信息机短信接口db登陆的密码。
			paras.put("password", smsSetup.getDbPass());
			System.out.println("||||||||||||||");
			smproxy.initConn(paras);

			System.out.println("-------------");
			/** 用户登陆 */
			smproxy.login(smsSetup.getUserId(), smsSetup.getUserPass());
			System.out.println("===========");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
