package com.kingsoft.nb.common;

import java.sql.SQLException;

import com.kingsoft.control.database.Connection;
import com.kingsoft.nb.dao.entity.system.ConnectionLog;
import com.kingsoft.nb.dao.implement.mysql.system.ConnectionLogDAOImpl;
import com.kingsoft.nb.dao.interfaces.system.ConnectionLogDAO;

/**
 * 宁波标准版工具类
 *
 * @author liyunqiang
 *
 * @version 2016年8月25日
 *
 */
public class NbUtils {

	/**
	 * 添加接口日志
	 * 
	 * @param connection
	 *            数据库链接
	 * @param accreditId
	 *            授权编号
	 * @param operationMan
	 *            操作人
	 * @param service
	 *            接口地址及名称
	 * @param parameter
	 *            参数
	 * @param result
	 *            返回结果
	 * @param remark
	 *            备注
	 * @return
	 * @throws SQLException
	 */
	public static void addConnectionLog(Connection connection, String accreditId, String operationMan, String service, String parameter, String result, String remark) throws SQLException {
		ConnectionLog connectionLog = new ConnectionLog();
		connectionLog.setAccreditId(accreditId);
		connectionLog.setOperationMan(operationMan);
		connectionLog.setService(service);
		connectionLog.setParameter(parameter);
		connectionLog.setResult(result);
		connectionLog.setRemark(remark);
		addConnectionLog(connection, connectionLog);
	}

	/**
	 * 添加接口日志
	 * 
	 * @param connection
	 *            数据库链接
	 * @param service
	 *            接口地址及名称
	 * @param parameter
	 *            参数
	 * @param result
	 *            返回结果
	 * @return
	 * @throws SQLException
	 */
	public static void addConnectionLog(Connection connection, String service, String parameter, String result) throws SQLException {
		ConnectionLog connectionLog = new ConnectionLog();
		connectionLog.setService(service);
		connectionLog.setParameter(parameter);
		connectionLog.setResult(result);
		addConnectionLog(connection, connectionLog);
	}

	/**
	 * 添加接口日志
	 *
	 * @param connection
	 *            数据库链接
	 * @param service
	 *            接口地址及名称
	 * @param parameter
	 *            参数
	 * @param result
	 *            返回结果
	 * @return
	 * @throws SQLException
	 */
	public static void addConnectionLog(String service, String parameter, String result) throws SQLException {
		ConnectionLog connectionLog = new ConnectionLog();
		connectionLog.setService(service);
		connectionLog.setParameter(parameter);
		connectionLog.setResult(result);
		System.out.println(connectionLog.toString());
	}

	/**
	 * 添加接口日志
	 * 
	 * @param connection
	 *            数据库链接
	 * @param connectionLog
	 *            日志对象
	 * @throws SQLException
	 */
	public static void addConnectionLog(Connection connection, ConnectionLog connectionLog) throws SQLException {
		ConnectionLogDAO connectionLogDAO = new ConnectionLogDAOImpl();
		connectionLogDAO.setConnection(connection);
		connectionLogDAO.insert(connectionLog);
	}

	/**
	 * 字符串数组转为字符串，用逗号隔开
	 * 
	 * @param strings
	 * @return
	 */
	public static String arrayToString(String[] strings) {
		StringBuffer rvalue = new StringBuffer();
		if (strings != null) {
			int length = strings.length;
			for (int i = 0; i < length; i++) {
				rvalue.append(strings[i].toString());
				if (i < length - 1) {
					rvalue.append(",");
				}
			}
		}
		return rvalue.toString();
	}

	/**
	 * 数值数组转为字符串，用逗号隔开
	 * 
	 * @param strings
	 * @return
	 */
	public static String arrayToString(int[] strings) {
		StringBuffer rvalue = new StringBuffer();
		if (strings != null) {
			int length = strings.length;
			for (int i = 0; i < length; i++) {
				rvalue.append(strings[i]);
				if (i < length - 1) {
					rvalue.append(",");
				}
			}
		}
		return rvalue.toString();
	}

}
