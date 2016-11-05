package com.lyq.common;

import com.lyq.common.vo.IpVO;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Ip工具
 * Created by lyq on 2016/11/5.
 */
public class IpUtils {

    public static IpVO getIp(String ip) {
        IpVO ipVO = new IpVO();
        // 使用淘宝的ip地址库
        String apiUrl = "http://ip.taobao.com/service/getIpInfo.php?ip=";
        try {
            String rvalue = Jsoup.connect(apiUrl + ip).timeout(5000).get().body().text();
            JSONObject ipObject = JSONObject.fromObject(rvalue);
            if (ipObject.getInt("code") == 0) {
                ipVO = (IpVO) JSONObject.toBean((JSONObject) ipObject.get("data"), IpVO.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ipVO;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static void main(String[] args) {
        IpVO ipVO = new IpVO();
        String ip = "116.30.215.220";
        String apiUrl = "http://ip.taobao.com/service/getIpInfo.php?ip=";
        try {
            String rvalue = Jsoup.connect(apiUrl + ip).timeout(5000).get().body().text();
            JSONObject ipObject = JSONObject.fromObject(rvalue);
            if (ipObject.getInt("code") == 0) {
                System.out.println("查询成功");
                ipVO = (IpVO) JSONObject.toBean((JSONObject) ipObject.get("data"), IpVO.class);
                System.out.println(ipVO.getArea());
            } else {
                System.out.println("查询失败");
            }
            System.out.println(ipObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
