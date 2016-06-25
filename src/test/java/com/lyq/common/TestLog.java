package com.lyq.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * 生成日志
 *
 * @author liyunqiang
 *
 * @version 2016年3月7日
 *
 */
public class TestLog {
	public static final String url = "jdbc:mysql://192.168.1.178/product_center";
	public static final String user = "";
	public static final String password = "";
	public static Connection conn = null;
	public static Statement statement = null;
	public static ResultSet rs = null;

	public static void main(String[] args) throws SQLException {
		conn = getConn();
		String sql = "SELECT * FROM information_schema.COLUMNS WHERE TABLE_NAME ='finance_apply_fee' AND TABLE_SCHEMA='product_center';";
		rs = executeQuery(sql);
		HashMap<String, String> rvalue = putValue(rs);
		createCode(rvalue);// 生成代码
		closeConn(rs);// 关闭连接
	}

	/**
	 * 生成代码
	 * 
	 * @param rvalue
	 */
	private static void createCode(HashMap<String, String> rvalue) {
		StringBuffer sb = new StringBuffer();
		for (String key : rvalue.keySet()) {
			System.out.println(key + "-" + rvalue.get(key));
			sb.append("if (maps.containsKey(\"").append(key).append("\")) {\n");
			sb.append("\tsb.append(\" ").append(rvalue.get(key)).append("=>>\").append(maps.get(\"").append(key).append("\"));\n");
			sb.append("}\n");
		}
		System.out.println("--------------");
		System.out.println(sb.toString());
	}

	private static final HashMap<String, String> putValue(ResultSet rs) throws SQLException {
		HashMap<String, String> rvalue = new HashMap<String, String>();
		while (rs.next()) {
			rvalue.put(rs.getString("COLUMN_NAME"), rs.getString("COLUMN_COMMENT"));
		}
		return rvalue;
	}

	/**
	 * 关闭连接
	 * 
	 * @param rs
	 */
	private static void closeConn(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行查询语句
	 * 
	 * @param sql
	 * @return
	 */
	private static ResultSet executeQuery(String sql) {
		ResultSet rs = null;
		try {
			statement = conn.createStatement();
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	private static Connection getConn() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);// 获取连接
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

}
