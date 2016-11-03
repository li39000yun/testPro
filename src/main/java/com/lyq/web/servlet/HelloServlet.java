package com.lyq.web.servlet;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * freemarker测试类
 * Created by lyq on 2016/11/3.
 */
public class HelloServlet extends HttpServlet {
    private Configuration cfg = null;

    @Override
    public void init() throws ServletException {
        // FreeMarker程序入口点
        cfg = new Configuration();
        // 初始化Servlet上下文(第二参数为ServletContext的相对路径,null代表当前网站根路径，相对路径)
        cfg.setServletContextForTemplateLoading(this.getServletContext(), null);
        cfg.setDefaultEncoding("UTF-8");
        /***********************************************************************
         * *cfg.setServletContextForTemplateLoading(this.getServletContext(),
         * "WEB-INF"); 如果设置成WEB-INF相应的获模板时需要改成Template t =
         * cfg.getTemplate("/templates/test.ftl");
         **********************************************************************/
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        // 模板文件会根据key，读取value
        Map root = new HashMap();
        root.put("message", "您好！");
        root.put("username", "编程爱好者");
        Template t = cfg.getTemplate("WEB-INF/templates/test.ftl");
        resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        try {
            t.process(root, out);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
