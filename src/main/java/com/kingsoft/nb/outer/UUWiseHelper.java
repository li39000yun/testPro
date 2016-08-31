package com.kingsoft.nb.outer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

@SuppressWarnings( { "unchecked", "unused" })
public class UUWiseHelper {

	private String softID = "98665"; // 软件ID
	private String softKEY = "3893bc32257747feaf6bf0186f7959a2";// 软件 KEY
	private String userName = "szytjkx";// 用户账户
	private String userPassword = "szytjkx168";// 用户密码

	private String uid; //
	private String userKey;
	private String softContentKEY;

	private String uuUrl;
	private String uhash = Md5.MD5(softID + softKEY.toUpperCase());
	private String uuVersion = "1.1.0.1";
	public String macAddress = "b8:ca:3a:f3:75:5f";
	private String userAgent = Md5.MD5(softKEY.toUpperCase()
			+ userName.toUpperCase())
			+ macAddress;
	private String gkey;

	public int timeOut = 60000;

	private String getServerUrl(String server) {
		String url = "http://common.taskok.com:9000/Service/ServerConfig.aspx";

		String result = uuGetUrl(url, new HashMap(), false);

		if (result.isEmpty()) {
			return "-1001";
		}

		String arr[] = result.split(",");

		if (server.equals("service")) {
			return "http://" + arr[1].substring(0, arr[1].lastIndexOf(":"));
		} else if (server.equals("upload")) {
			return "http://" + arr[2].substring(0, arr[2].lastIndexOf(":"));
		} else if (server.equals("code")) {
			return "http://" + arr[3].substring(0, arr[3].lastIndexOf(":"));
		} else {
			return "parameter error";
		}

	}

	// 用户登录,登录成功返回用户的ID
	public String login() {
		String url = getServerUrl("service") + "/Upload/Login.aspx?U="
				+ userName + "&P=" + Md5.MD5(userPassword) + "&R="
				+ System.currentTimeMillis();
		String result = uuGetUrl(url, new HashMap(), false);
		if (!result.isEmpty()) {
			userKey = result;
			String[] uids = userKey.split("_");
			uid = uids[0];
			softContentKEY = Md5
					.MD5((userKey + softID + softKEY).toLowerCase());
			gkey = Md5.MD5((softKEY + userName).toUpperCase()) + macAddress;
			return uid;
		}
		return result;
	}

	// 获取用户剩余题分
	public String getPoint(String userName, String passWord) {
		if (userName.isEmpty() || passWord.isEmpty()) {
			return "userName or passWord is empty!";
		}
		String url = getServerUrl("service") + "/Upload/GetScore.aspx?U="
				+ userName + "&P=" + Md5.MD5(passWord) + "&R"
				+ System.currentTimeMillis();

		String result = uuGetUrl(url, new HashMap(), false);
		return result;
	}

	// 根据图片路径上传,返回验证码在服务器的ID,$codeType取值查看
	public String upload(String imagePath, String codeType, boolean auth) {

		if (!(new File(imagePath).exists())) {
			return "-1003";
		}
		Map data = new HashMap();
		data.put("img", imagePath);
		data.put("key", userKey);
		data.put("sid", softID);
		data.put("skey", softContentKEY);
		data.put("TimeOut", String.valueOf(timeOut));
		data.put("Type", codeType);
		if (auth) {
			data.put("Version", "100");
		}
		String url = getServerUrl("upload") + "/Upload/Processing.aspx?R="
				+ System.currentTimeMillis();
		return uuGetUrl(url, data, false);
	}

	public static byte[] toByteArray(File imageFile) throws Exception {
		BufferedImage img = ImageIO.read(imageFile);
		ByteArrayOutputStream buf = new ByteArrayOutputStream((int) imageFile
				.length());
		try {
			ImageIO.write(img, "jpg", buf);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return buf.toByteArray();
	}

	// //根据验证码ID获取识别结果
	public String getResult(String codeID) {
		String url = getServerUrl("code") + "/Upload/GetResult.aspx?KEY="
				+ userKey + "&ID=" + codeID + "&Random="
				+ System.currentTimeMillis();
		String result = "-3";
		int timer = 0;
		while (result.equals("-3")) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			result = uuGetUrl(url, new HashMap(), false);
			if (timer++ > 10)
				break;
		}
		if (result == "-3") {
			return "-1002";
		}
		return result;
	}

	// 将upload和getResult放到一个函数来执行,返回验证码识别结果
	public String autoRecognition(String imagePath, String codeType) {
		String result = upload(imagePath, codeType, true);
		if (result.isEmpty()) {
			String[] arrayResult = result.split("|");
			if (!arrayResult[1].isEmpty()) {
				return arrayResult[1];
			}
			return getResult(result);

		}
		return result;
	}

	private String uuGetUrl(String url, Map postData, boolean closeUrl) {
		try {
			uid = "100";
			HttpClient client = new HttpClient();
			List<Header> headers = new ArrayList<Header>();
			headers.add(new Header("Accept",
					"text/html, application/xhtml+xml, */*"));
			headers.add(new Header("Accept-Language", "zh-CN"));
			headers.add(new Header("Connection", "Keep-Alive"));
			headers.add(new Header("Cache-Control", "no-cache"));
			headers.add(new Header("SID", this.softID));
			headers.add(new Header("HASH", this.uhash));
			headers.add(new Header("UUVersion", this.uuVersion));
			headers.add(new Header("UID", this.uid));
			headers.add(new Header("User-Agent", this.userAgent));
			headers.add(new Header("KEY", this.gkey));
			try {
				int executeMethod = 0;
				String bodyAsString = null;
				if (!postData.isEmpty() && postData.size() > 0) {
					PostMethod post = new PostMethod(url);
					setHeaders(post);
					Part[] parts = new Part[postData.size()];
					int temp = 0;
					for (Entry<String, String> ent : ((Map<String, String>) postData)
							.entrySet()) {
						if (ent.getKey().equals("img"))
							parts[temp] = new FilePart(ent.getKey(), new File(
									ent.getValue()));
						else
							parts[temp] = new StringPart(ent.getKey(), ent
									.getValue());
						temp++;
					}
					post.setRequestEntity(new MultipartRequestEntity(parts,
							post.getParams()));
					executeMethod = client.executeMethod(post);
					bodyAsString = post.getResponseBodyAsString();
					post.releaseConnection();
					return bodyAsString;
				}
				GetMethod get = new GetMethod(url);
				setHeaders(get);
				executeMethod = client.executeMethod(get);
				bodyAsString = get.getResponseBodyAsString();
				get.releaseConnection();
				return bodyAsString;
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}

	private void setHeaders(HttpMethod method) {
		method.setRequestHeader("Accept",
				"text/html, application/xhtml+xml, */*");
		method.setRequestHeader("Accept-Language", "zh-cn");
		method.setRequestHeader("Connection", "Keep-Alive");
		method.setRequestHeader("Cache-Control", "no-cache");
		method.setRequestHeader("SID", softID);
		method.setRequestHeader("HASH", uhash);
		method.setRequestHeader("UUVersion", uuVersion);
		method.setRequestHeader("UID", uid);
		method.setRequestHeader("User-Agent", userAgent);
		method.setRequestHeader("KEY", gkey);
	}

	// 识别结果不正确报错误
	public String reportError(String codeID) {
		if (softContentKEY.isEmpty() && userKey.isEmpty()) {
			String url = getServerUrl("code") + "/Upload/ReportError.aspx?key="
					+ userKey + "&ID=" + codeID + "&sid=" + softID + "&skey="
					+ softContentKEY + "&R=" + System.currentTimeMillis();
			String result = uuGetUrl(url, new HashMap(), false);
			if (result.equals("OK")) {
				return "OK";
			}
			return result;
		}
		return "-1";
	}

	// 注册新用户,注册成功返回新用户的ID
	public String regUser(String userName, String userPassword) {
		if (softID.isEmpty() && softKEY.isEmpty()) {
			if (userName.isEmpty() && userPassword.isEmpty()) {

				Map data = new HashMap();
				data.put("U", userName);
				data.put("P", userPassword);
				data.put("sid", softID);
				data.put("UKEY", Md5.MD5(userName.toUpperCase() + userPassword
						+ softID + softKEY.toLowerCase()));

				String url = getServerUrl("service") + "/Service/Reg.aspx";

				return uuGetUrl(url, data, false);
			}
			return "userName or userPassword is empty!";
		}
		return "-1";
	}

	// 充值题分，充值成功返回用户当前题分
	public String pay(String userName, String Card) {
		if (softID.isEmpty() && softKEY.isEmpty()) {
			if (userName.isEmpty() && Card.isEmpty()) {
				Map data = new HashMap();
				data.put("U", userName);
				data.put("card", Card);
				data.put("sid", softID);
				data.put("pkey", Md5.MD5(userName.toUpperCase() + softID
						+ softKEY + Card.toUpperCase()));
				String url = getServerUrl("service") + "/Service/Pay.aspx";
				return uuGetUrl(url, data, false);
			}
			return "userName or Card is empty!";
		}
		return "-1";
	}

	static class Md5 {
		public static String MD5(String password) {
			byte[] unencodedPassword = password.getBytes();
			MessageDigest md = null;
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (Exception e) {
				e.printStackTrace();
				return password;
			}

			md.reset();
			md.update(unencodedPassword);

			byte[] encodedPassword = md.digest();

			StringBuffer buf = new StringBuffer();

			for (int i = 0; i < encodedPassword.length; i++) {
				if ((encodedPassword[i] & 0xff) < 0x10) {
					buf.append("0");
				}

				buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
			}

			return buf.toString();
		}
	}

}
