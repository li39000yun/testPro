package com.lyq.common;

import com.kingsoft.control.util.StringManage;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import javax.xml.namespace.QName;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by lyq on 2016/5/24.
 */
public class LyqUtil {

    private static String S_namespace = "http://www.szyt.net";

    /**
     * 传入文件名以及字符串, 将字符串信息保存到文件中
     *
     * @param strFilename
     * @param strBuffer
     */
    public static void TextToFile(final String strFilename, final String strBuffer) {
        try {
            // 创建文件对象
            File fileText = new File(strFilename);
            // 向文件写入对象写入信息
            FileWriter fileWriter = new FileWriter(fileText);

            // 写文件
            fileWriter.write(strBuffer);
            // 关闭
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String execute(String www, Object[] params, String methodName) throws Exception {
        RPCServiceClient service = null;
        String rvalue = StringManage.FS_EMPTY;
        try {
            // 使用RPC方式调用WebService
            service = new RPCServiceClient();
            Options options = service.getOptions();

            // 设置超时时间
            options.setTimeOutInMilliSeconds(50000);

            // 指定调用WebService的URL
            EndpointReference targetEPR = new EndpointReference(www);
            options.setTo(targetEPR);

            // 指定方法返回值的数据类型的Class对象
            Class<?>[] classes = new Class<?>[] { String.class };

            // 指定要调用的方法及WSDL文件的命名空间
            QName opAddEntry = new QName(S_namespace, methodName);
            rvalue = (String) service.invokeBlocking(opAddEntry, params, classes)[0];
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (service != null) {
                service.cleanupTransport();// 关闭调用连接
            }
        }
        return rvalue;
    }

}
