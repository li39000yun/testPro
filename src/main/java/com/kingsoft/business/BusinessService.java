/**
 * @(#)BusinessService.java     2010-2-26
 *
 * Copyright 2010 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.kingsoft.business;

import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import com.kingsoft.common.UserInfo;
import com.kingsoft.control.exception.BaseException;
import com.kingsoft.control.service.AbstractService;
import com.kingsoft.control.service.Service;
import com.kingsoft.control.util.StringManage;

/**
 * 抽象业务服务基础类，扩展服务抽象类.
 * 
 * @author zhangxulong
 * 
 * @version 2010-2-26
 * 
 * @since JDK 1.6.0_10-rc2
 * 
 */
public abstract class BusinessService extends AbstractService {
	private static String S_namespace = "http://www.szyt.net";
	/**
	 * 根据给定的业务服务接口类，获取相应的实现类实例.
	 * 
	 * @param service
	 *            业务服务接口类
	 * @return Service 服务实例
	 * @throws BaseException
	 */
	public Service getService(Class<?> service) throws BaseException {
		return getService(service.getName());
	}

	/**
	 * 获取当前服务所属的用户信息
	 * 
	 * @return UserInfo 用户对象
	 */
	public final UserInfo getUserInfo() {
		return (UserInfo) getServiceProvider().getUser();
	}

	/**
	 * 获取当前用户的授权编号
	 * 
	 * @return String 授权编号
	 */
	public final String accreditId() {
		UserInfo userInfo = (UserInfo) getServiceProvider().getUser();
		return userInfo.getAccreditId();
	}
	
	protected String execute(String www, String method, Object[] arguments)
			throws Exception {
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
			rvalue = (String) service.invokeBlocking(opAddEntry, arguments,
					classes)[0];
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



