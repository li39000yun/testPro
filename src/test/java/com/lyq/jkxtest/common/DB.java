package com.lyq.jkxtest.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 *
 *@author liyunqiang
 *
 *@version 2016年6月14日
 *
 */
public class DB {

	public static String url = "jdbc:mysql://ys.cttms.com:3306/service_center";
	public static String user = "";
	public static String password = "";
	public static Connection conn = null;
	public static Statement statement = null;
	public static ResultSet rs = null;

	public static void main(String[] args) throws SQLException, InterruptedException {
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

	public static final LinkedHashMap<String, String> putValue(ResultSet rs) throws SQLException {
		LinkedHashMap<String, String> rvalue = new LinkedHashMap<String, String>();
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
	public static void closeConn(ResultSet rs) {
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
	public static ResultSet executeQuery(String sql) {
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
	 * 执行语句
	 * @param sql
	 * @return
	 */
	public static void execute(String sql) {
		try {
			statement = conn.createStatement();
			statement.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	public static Connection getConn() {
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

