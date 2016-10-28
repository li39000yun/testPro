/**
 * @(#)IgzServiceHelper.java    2016年10月21日
 *
 * Copyright 2016 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved. 
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.lyq.jkxtest.serviceCenter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.kingsoft.control.util.StringManage;

/**
 * IGZ服务帮助类
 * 
 * @author wangchao
 * 
 * @version 2016年10月21日
 * 
 * @since JDK 1.6
 * 
 */
public class IgzServiceHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	public String ProcessRequest(String portcode, String dockcode,
			String traceno) {
		// portcode 指港口 营口传"CNYIK" dockcode 指港口下码头,可以传空字符

		// 请求地址
		String www = "http://api3.igenzong.com/d2d/trace";
		// 请求参数 其中strAppKey、strAppSecret需双方约定
		String strAppKey = "JinKeuserCN";
		String strAppSecret = "cn0506!";
		String strCharset = "UTF-8";
		// String strFormat = "json";//返回数据格式
		String info = StringManage.FS_EMPTY;
		// 签名参数数组
		String[] requestArr = { "appkey=" + strAppKey,
				"appsecret=" + strAppSecret, "portcode=" + portcode,
				"dockcode=" + dockcode, "traceno=" + traceno, "info=" + info };
		// 对签名数组进行排序
		String[] sortedArr = BubbleSort(requestArr);
		// 拼接签名数组
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < sortedArr.length; i++) {
			if (i < sortedArr.length - 1) {
				sb.append(sortedArr[i]).append("&");
			} else if (i == sortedArr.length - 1) {
				sb.append(sortedArr[i]);
			}

		}
		// 生成签名字符串
		String strSign = GetSign(sb.toString(), strCharset);

		URL url = null;
		HttpURLConnection conn = null;
		StringBuffer param = new StringBuffer();
		param.append("appkey=").append(strAppKey);
		param.append("&portcode=").append(portcode);
		param.append("&dockcode=").append(dockcode);
		param.append("&traceno=").append(traceno);
		param.append("&info=").append(info);
		param.append("&sign=").append(strSign);
		StringBuffer rvalue = new StringBuffer();
		try {
			// 打开与网站服务器的连接
			url = new URL(www);
			conn = (HttpURLConnection) url.openConnection();
			conn.setInstanceFollowRedirects(true);
			conn.setDoOutput(true); // 需要向服务器写数据
			conn.setDoInput(true); //
			conn.setUseCaches(false); // 获得服务器最新的信息
			conn.setAllowUserInteraction(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.getOutputStream().write(param.toString().getBytes(strCharset));
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			conn.connect();
			// 解析数据集
			InputStream is = conn.getInputStream();
			BufferedInputStream buff = new BufferedInputStream(is);
			Reader r = new InputStreamReader(buff, strCharset);
			BufferedReader br = new BufferedReader(r);
			String strLine = null;
			while ((strLine = br.readLine()) != null) {
				rvalue.append(strLine + "\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rvalue.toString();

	}

	// 获取签名的方法
	private static String GetSign(String _input, String _charset) {
		MessageDigest md;
		byte[] btInput = null;
		try {
			md = MessageDigest.getInstance("MD5");
			try {
				btInput = md.digest(_input.getBytes(_charset));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		StringBuilder sb = new StringBuilder(32);
		for (byte x : btInput) {
			if (Integer.toHexString(x & 0xff).length() < 2) {
				sb.append("0").append(Integer.toHexString(x & 0xff));
			} else {
				sb.append(Integer.toHexString(x & 0xff));
			}
		}
		return sb.toString();
	}

	// 数据排序 按参数名称
	private static String[] BubbleSort(String[] r) {
		int i, j; // 交换标志
		String temp;
		boolean exchange;
		for (i = 0; i < r.length; i++) { // 最多做R.Length-1趟排序
			exchange = false; // 本趟排序开始前，交换标志应为假
			for (j = r.length - 2; j >= i; j--) {// 交换条件
				if (r[j + 1].compareTo(r[j]) < 0) {
					temp = r[j + 1];
					r[j + 1] = r[j];
					r[j] = temp;
					exchange = true; // 发生了交换，故将交换标志置为真
				}
			}
			if (!exchange) { // 本趟排序未发生交换，提前终止算法
				break;
			}
		}
		return r;
	}
}
