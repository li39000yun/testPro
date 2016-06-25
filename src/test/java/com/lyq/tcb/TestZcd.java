package com.lyq.tcb;

import java.util.ArrayList;

import javax.xml.namespace.QName;

import net.sf.json.JSONObject;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import com.kingsoft.control.util.StringManage;
import com.lyq.tcb.vo.SearchManageFee;

/**
 * 
 * @author wmy
 * 
 * @version 2015-8-27
 * 
 * @since JDK 1.6
 * 
 */
public class TestZcd {
	private static String S_namespace = "http://www.szyt.net";
	private static String www = "http://szyt.net:8180/zcd/services/BusiService";

	private static String busiId = "Y15090004-1-1";
	private static String appkey = "52db4c7e86c81bad";
	
	public static void main(String[] args) throws Exception {
		 testManageFee();

	}
	//测试挂靠费
	private static void testManageFee() throws Exception {
		SearchManageFee searchManageFee = new SearchManageFee();
		searchManageFee.setAccreditid("zcd");
		searchManageFee.setAppkey("52db4c7e86c81bad");
		searchManageFee.setUserid(0);
		searchManageFee.setBeginDate("2015-09-01");
		searchManageFee.setEndDate("2015-11-01");

		ArrayList<String> list = new ArrayList<String>();
		list.add("粤BS6830");
//		list.add("123");
		
		searchManageFee.setTrucks(list.toArray(new String[0]));
		
		System.out.println(JSONObject.fromObject(searchManageFee).toString());
		Object[] arguments = new Object[] { JSONObject.fromObject(searchManageFee).toString() };
		String rvalue = execute(www, "manageFee", arguments);
		System.out.println(rvalue);
	}
	

	public static String execute(String www, String method, Object[] arguments) throws Exception {
		RPCServiceClient service = null;
		String rvalue = StringManage.FS_EMPTY;
		try {
			// 使用RPC方式调用WebService
			service = new RPCServiceClient();
			Options options = service.getOptions();

			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference(www);
			options.setTo(targetEPR);

			// 指定方法返回值的数据类型的Class对象
			Class<?>[] classes = new Class<?>[] { String.class };

			// 指定要调用的方法及WSDL文件的命名空间
			QName opAddEntry = new QName(S_namespace, method);
			rvalue = (String) service.invokeBlocking(opAddEntry, arguments, classes)[0];
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (service != null) {
				service.cleanupTransport();// 关闭调用连接
			}
		}
		return rvalue;
	}
	
}
