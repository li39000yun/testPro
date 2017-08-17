package com.lyq.common.tag.demo;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * 自定义标签测试类
 * Created by 云强 on 2017/7/17.
 */
public class HelloTag extends TagSupport {

    private long startTime;
    private long endTime;
    @Override
    public int doStartTag() throws JspException {
        startTime = System.currentTimeMillis();
        // 表示定制标记里面有包括的JSP页面
        return TagSupport.EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        endTime = System.currentTimeMillis();
        long elapsed = endTime - startTime;
        JspWriter out = pageContext.getOut();
        try {
            out.println("runtime is "+ elapsed+"<br/><a onclick=\"alert(1)\" >rentme is "+elapsed+"</a>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 表示JSP页面继续运行
        return TagSupport.EVAL_PAGE;
    }
}
