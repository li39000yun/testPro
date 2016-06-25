/**
 * 
 */
package com.lyq.common;

import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import com.kingsoft.control.util.StringManage;

import net.sf.json.JSONObject;

/**
 * 调用接口类
 * 
 * @author Administrator
 *
 */
public abstract class ExecuteService {
	private static String S_namespace = "http://www.szyt.net";

	public static String execute(String www, String jsonString) throws Exception {
		RPCServiceClient service = null;
		String rvalue = StringManage.FS_EMPTY;
		try {
			// 使用RPC方式调用WebService
			service = new RPCServiceClient();
			Options options = service.getOptions();

			// 设置超时时间
			options.setTimeOutInMilliSeconds(50000);

			// 指定调用WebService的URL
			EndpointReference targetEPR = new EndpointReference(www);
			options.setTo(targetEPR);

			// 指定方法返回值的数据类型的Class对象
			Class<?>[] classes = new Class<?>[] { String.class };

			// 指定要调用的方法及WSDL文件的命名空间
			QName opAddEntry = new QName(S_namespace, "preExecute");
			rvalue = (String) service.invokeBlocking(opAddEntry, new Object[] { jsonString }, classes)[0];
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (service != null) {
				service.cleanupTransport();// 关闭调用连接
			}
		}
		return rvalue;
	}

	/**
	 * 对象转成json格式输出
	 * 
	 * @param obj
	 *            对象
	 * @return
	 */
	public static String objectToJsonString(Object obj) {
		return JSONObject.fromObject(obj).toString();
	}

}
