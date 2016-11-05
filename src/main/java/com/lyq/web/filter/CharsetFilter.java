package com.lyq.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * 编码过滤器
 * Created by lyq on 2016/11/5.
 */
public class CharsetFilter implements Filter {
    private FilterConfig configer;
    private String charset = "UTF-8";

    public void destroy() {
        configer = null;
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding(charset);
        resp.setCharacterEncoding(charset);
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        this.configer = config;
        String s = configer.getInitParameter("encoding");
        if (s != null) {
            charset = s;
        }
    }

}
