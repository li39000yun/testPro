package com.lyq.test.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lyq on 2016/11/2.
 */
public class TestFreemarker3 {
    public static void main(String[] args) throws IOException {
        TestFreemarker3 freemark = new TestFreemarker3("template");
        freemark.setTemplateName("freemarker.ftl");
        freemark.setFileName("doc_" + new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date()) + ".doc");
//        freemark.setFilePath("doc\\");
        //生成word
        freemark.createWord();
        System.out.println(TestFreemarker3.class.getClassLoader().getResource(""));
    }

    private void createWord() {

        Template t = null;
        try {
            //获取模板信息
            t = configuration.getTemplate(templateName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File outFile = new File(filePath + fileName);
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Map map = new HashMap<String, Object>();
        map.put("author", "蒙奇·D·路飞");
        map.put("country", "日本");
        map.put("city", "大阪");
        map.put("shengfen", "东京");
        map.put("time", new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date()));
        map.put("name1", "小哈");
        map.put("code1", "编号1");
        map.put("name2", "内档");
        map.put("code2", "繁多");
        try {
            //输出数据到模板中，生成文件。
            t.process(map, out);
            out.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * freemark初始化
     *
     * @param templatePath 模板文件位置
     */
    public TestFreemarker3(String templatePath) throws IOException {
        configuration = new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(this.getClass(),templatePath);
        //设置FreeMarker的模版文件夹位置
//        configuration.setDirectoryForTemplateLoading(new File("D:\\java\\ideaProjects\\testPro\\src\\test\\java\\com\\lyq\\test\\freemarker" + templatePath));

    }

    /**
     * freemark模板配置
     */
    private Configuration configuration;
    /**
     * freemark模板的名字
     */
    private String templateName;
    /**
     * 生成文件名
     */
    private String fileName;
    /**
     * 生成文件路径
     */
    private String filePath;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
