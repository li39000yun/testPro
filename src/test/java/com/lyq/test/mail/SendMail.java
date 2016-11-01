


/**
 * @(#)SendMail.java 2011-1-18
 * <p>
 * Copyright 2011 Kingsoft Software Development Co.,Ltd, Inc. All rights reserved.
 * Kingsoft proprietary/confidential. Use is subject to license terms.
 */
package com.lyq.test.mail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.jsoup.Jsoup;
import sun.misc.BASE64Encoder;

import com.kingsoft.control.Console;
import com.kingsoft.control.util.StringManage;

/**
 * 发送邮件
 *
 * @author wangchao
 * @version 2011-7-14
 * @since JDK 1.6
 */
public class SendMail {


    public static void main(String[] args) throws Exception {
        Mail mail = new Mail();
        mail.setBody("测试邮件3");
        mail.setHost("smtp.exmail.qq.com");
        mail.setSubject("测试主题3");
        mail.setMailTo("li39000yun@qq.com");
        mail.setUser("luke@jkxsoft.com");
        mail.setPass("Luke123");
        mail.setSignature("Luke");
//        mail.setFilePath("C:\\Users\\jkx\\Desktop\\111.txt");

        SendMail sendMail = new SendMail();
        System.out.println("begin");
        sendMail.send(mail);
        System.out.println("end");
//		protected String subject = StringManage.FS_EMPTY;// 设置邮件主题
//		protected String body = StringManage.FS_EMPTY;// 设置邮件正文
//		protected String mailTo = StringManage.FS_EMPTY;// 设置邮件接收方的地址
//		protected String filePath = StringManage.FS_EMPTY;// 文件地址
//		protected String host = "smtp.163.com";// 邮箱服务器
//		protected String user = StringManage.FS_EMPTY;// 邮箱用户
//		protected String pass = StringManage.FS_EMPTY;// 邮箱密码
//		protected String mailFrom = StringManage.FS_EMPTY;// 邮件发送方的地址
//		protected String signature = StringManage.FS_EMPTY;// 签名信息
    }

    /**
     * 发送邮件
     *
     * @param mail 邮件信息
     * @throws Exception
     */
    public static void send(Mail mail) throws Exception {
        Properties props = new Properties();
        // 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
        props.put("mail.smtp.host", mail.getHost());

        // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        props.put("mail.smtp.auth", "true");

        // 用刚刚设置好的props对象构建一个session
        Session session = Session.getDefaultInstance(props);

        // 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
        // 用（你可以在控制台（console)上看到发送邮件的过程）
        session.setDebug(false);

        // 用session为参数定义消息对象
        MimeMessage message = new MimeMessage(session);
        Transport transport = null;
        try {
            // 加载发件人地址
            Address fAddr = new InternetAddress(mail.getMailFrom());
            message.setFrom(fAddr);

            // 加载收件人地址
            Address tAddr = new InternetAddress(mail.getMailTo());
            message.addRecipient(Message.RecipientType.TO, tAddr);

            // 加载标题
            if (StringManage.isEmpty(mail.getSubject()))
                mail.setSubject(mail.getSubject());
            BASE64Encoder enc = new BASE64Encoder();
            StringBuffer subject = new StringBuffer();
            subject.append("=?");
            subject.append(Console.getDefaultCharset());
            subject.append("?B?");
            subject.append(enc.encode(mail.getSubject().getBytes(
                    Console.getDefaultCharset())));
            subject.append("?=");
            message.setSubject(subject.toString());

            // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            Multipart multipart = new MimeMultipart();

            // 设置邮件的文本内容
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(mail.getBody() + mail.getSignature(),
                    "text/html;charset=UTF-8");
            multipart.addBodyPart(contentPart);

            // 添加附件
            if (!StringManage.isEmpty(mail.getFilePath())) {
                BodyPart fileBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(mail.getFilePath());

                // 添加附件的内容
                fileBodyPart.setDataHandler(new DataHandler(source));

                fileBodyPart.setFileName(new String(source.getName().getBytes(
                        "gb2312"), "ISO8859-1"));
                // 将附件的BodyPart对象加入到multipart中
                multipart.addBodyPart(fileBodyPart);
            }


            // 将multipart对象放到message中
            message.setContent(multipart);

            // 保存邮件
            message.saveChanges();

            // 发送邮件
            transport = session.getTransport("smtp");

            // 连接服务器的邮箱
            transport.connect(mail.getHost(), mail.getUser(), mail.getPass());

            // 把邮件发送出去
            transport.sendMessage(message, message.getAllRecipients());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                transport.close();
            }
        }
    }
}