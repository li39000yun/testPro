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
        testSyfy();
    }

    /**
     * 嵩屿码头费用抓取测试
     * @throws Exception
     */
    public static void testSyfy() throws Exception {
        String www = "http://service.cttms.com:8180/service_center/services/FetchService";
        FetchSearch serch = new FetchSearch();
        serch.setRegionId(4);
        serch.setDock("XMSYFY");

//        serch.setBookingNo("1811X16FWD30099R1");
//        serch.setContainerNo("MEDU7332458");
//        serch.setBookingNo("576017283");
//        serch.setContainerNo("MRKU7187200");

        serch.setBookingNo("1811X0160565030J1");
        serch.setContainerNo("MEDU9531572");


//        serch.setBookingNo("XMPC514049");
//        serch.setContainerNo("CMAU2180462");
//        serch.setDock("XMGJHGFY");



//        [2016-07-12 10:12:56,776 DEBUG] - fetch-center FetchSearch [ regionId=xm , bookingNo=1811X16NPV00223D1 , containerNo=TGHU9638591 , dock=XMGJHGFY]
//        [2016-07-12 10:12:56,777 DEBUG] - fetch-center - [AbstractFetch] ContainerNo : TGHU9638591-1811X16NPV00223D1
//                [2016-07-12 10:12:56,944 DEBUG] - fetch-center FetchSearch [ regionId=xm , bookingNo=1811X16NPV00223D1 , containerNo=TCLU5305910 , dock=XMGJHGFY]
//        [2016-07-12 10:12:56,944 DEBUG] - fetch-center - [AbstractFetch] ContainerNo : TCLU5305910-1811X16NPV00223D1
//                [2016-07-12 10:12:57,140 DEBUG] - fetch-center FetchSearch [ regionId=xm , bookingNo=APLU077627465 , containerNo=APZU4902260 , dock=XMGJHGFY]
//        [2016-07-12 10:12:57,140 DEBUG] - fetch-center - [AbstractFetch] ContainerNo : APZU4902260-APLU077627465
//                [2016-07-12 10:12:57,395 DEBUG] - fetch-center FetchSearch [ regionId=xm , bookingNo=1811X0160565030J1 , containerNo=MEDU9531572 , dock=XMSYFY]
//        [2016-07-12 10:12:57,395 DEBUG] - fetch-center - [AbstractFetch] ContainerNo : MEDU9531572-1811X0160565030J1

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
