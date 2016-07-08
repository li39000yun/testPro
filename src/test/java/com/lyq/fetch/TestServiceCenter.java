package com.lyq.fetch;

import com.kingsoft.control.util.StringManage;
import com.lyq.common.GZipHelper;
import com.lyq.fetch.vo.FetchData;
import com.lyq.fetch.vo.FetchSearch;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import sun.misc.BASE64Decoder;

import javax.xml.namespace.QName;

/**
 * 服务中心测试类
 * Created by lyq on 2016/7/8.
 */
public class TestServiceCenter {

    private static String encryptKey = "4670af91302f9beadb49a802dbb0dcaa";

    public static void main(String[] args) throws Exception {
        String key = "一个测试字符串的旅行";
        byte[] bytes= GZipHelper.StringToGZip(key);
        System.out.println(bytes);
        System.out.println(GZipHelper.GZipToString(bytes));

        key = "H4sIAAAAAAAAAA==";
        BASE64Decoder base64 = new BASE64Decoder();
        bytes = base64.decodeBuffer(key);
        System.out.println(bytes);
        System.out.println(GZipHelper.GZipToString(bytes));

    }

    public static void testSyfy() throws Exception {
//        H4sIAAAAAAAAAA==
        String www = "http://szyt.net:8180/service_center/services/FetchService";
        FetchSearch serch = new FetchSearch();
//        serch.setBookingNo("1811X16FWD30099R1");
//        serch.setContainerNo("MEDU7332458");
//        serch.setBookingNo("576017283");
//        serch.setContainerNo("MRKU7187200");
//        serch.setDock("XMSYFY");
//        serch.setRegionId(4);

        serch.setBookingNo("XMPC514049");
        serch.setContainerNo("CMAU2180462");
        serch.setDock("XMGJHGFY");
        serch.setRegionId(4);



        Object[] entryArgs = new Object[] { encryptKey, JSONObject.fromObject(serch).toString()};
        String rvalue = execute(www,"conFetch",entryArgs);

        System.out.println(rvalue);
        // 解析返回结果
        if (!StringManage.isEmpty(rvalue)) {
            BASE64Decoder base64 = new BASE64Decoder();
            byte[] bytes = base64.decodeBuffer(rvalue);
            JSONArray jsonArray = JSONArray.fromObject(GZipHelper.GZipToString(bytes));
            FetchData[] f =  (FetchData[]) JSONArray.toArray(jsonArray, FetchData.class);
            for (FetchData fetchData : f) {
                System.out.println(fetchData.getName());
            }
        }
    }


    public static String execute(String www, String method, Object[] arguments) throws Exception {
        RPCServiceClient service = null;
        String rvalue = StringManage.FS_EMPTY;
        try {
            // 使用RPC方式调用WebService
            service = new RPCServiceClient();
            Options options = service.getOptions();

            // 指定调用WebService的URL
            EndpointReference targetEPR = new EndpointReference(www);
            options.setTo(targetEPR);

            // 指定方法返回值的数据类型的Class对象
            Class<?>[] classes = new Class<?>[] { String.class };

            // 指定要调用的方法及WSDL文件的命名空间
            QName opAddEntry = new QName("http://www.szyt.net", method);
            rvalue = (String) service.invokeBlocking(opAddEntry, arguments, classes)[0];
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
