package com.lyq.common;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.kingsoft.control.Console;

/**
 * 数据压缩服务类
 */
public class GZipHelper {
	/**
	 * 将String压缩成GZip
	 * 
	 * @param data
	 *            需压缩的字节
	 * @return GZip
	 */
	public static byte[] StringToGZip(String data) {
		byte[] rvalue = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(bos);
			gzip.write(data.getBytes(Console.getDefaultCharset()));
			gzip.finish();
			gzip.close();
			rvalue = bos.toByteArray();
			bos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rvalue;
	}

	/**
	 * 将文件压缩成GZip
	 * 
	 * @param filePath
	 *            文件路径
	 * @return GZip
	 */
	public static byte[] FileToGZip(String filePath) {
		byte[] data = null;

		// 读取图片字节数组
		try {
			InputStream in = new FileInputStream(filePath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] rvalue = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(bos);
			gzip.write(data);
			gzip.finish();
			gzip.close();
			rvalue = bos.toByteArray();
			bos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rvalue;
	}

	/**
	 * 将GZip解压为String
	 * 
	 * @param data
	 *            GZip数据集
	 * @return String
	 */
	public static String GZipToString(byte[] data) {
		String rvalue = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			GZIPInputStream gzip = new GZIPInputStream(bis);
			byte[] buf = new byte[1024];
			int num = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((num = gzip.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
			}
			rvalue = new String(baos.toByteArray(), Console.getDefaultCharset());
			baos.flush();
			baos.close();
			gzip.close();
			bis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rvalue;
	}

	/**
	 * 将GZip解压为String
	 * 
	 * @param data
	 *            GZip数据集
	 * @return String
	 */
	public static byte[] GZipToBytes(byte[] data) {
		byte[] rvalue = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			GZIPInputStream gzip = new GZIPInputStream(bis);
			byte[] buf = new byte[1024];
			int num = -1;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while ((num = gzip.read(buf, 0, buf.length)) != -1) {
				baos.write(buf, 0, num);
			}
			rvalue = baos.toByteArray();
			baos.flush();
			baos.close();
			gzip.close();
			bis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rvalue;
	}
}
