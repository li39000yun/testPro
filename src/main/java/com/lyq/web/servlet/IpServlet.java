package com.lyq.web.servlet;

import com.kingsoft.control.util.StringManage;
import com.lyq.common.IpUtils;
import com.lyq.common.vo.IpVO;
import net.sf.json.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * IP查询
 * Created by lyq on 2016/11/5.
 */
public class IpServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取查询请求的ip
        String searchIp = request.getParameter("searchIp");
        if (!StringManage.isEmpty(searchIp)) {
            IpVO searchIpVO = new IpVO();
            searchIpVO = IpUtils.getIp(searchIp);
            if (StringManage.isEmpty(searchIpVO.getIp())) {
                response.getWriter().println("ip错误！");
            } else {
                response.getWriter().println(JSONObject.fromObject(searchIpVO).toString());
            }
        } else {
            // 获取请求ip
            String ip = IpUtils.getIpAddr(request);
            IpVO ipVO = IpUtils.getIp(ip);
            RequestDispatcher rd = request.getRequestDispatcher("/test/tool/iptool.jsp");
            request.setAttribute("ipVO", ipVO);
            rd.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
