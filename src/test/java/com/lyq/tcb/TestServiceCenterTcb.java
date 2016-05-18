package com.lyq.tcb;

import com.kingsoft.control.util.StringManage;
import com.lyq.tcb.vo.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.xml.namespace.QName;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lyq
 * @version V1.0
 * @Description: 服务中心拖车宝调用测试
 * @Date 2015-11-26 上午11:02:56
 */
public class TestServiceCenterTcb {
    private static String S_namespace = "http://www.szyt.net";
    private static String www_service = "http://service.cttms.com:8180";
//	private static String www_service = "http://szyt.net:8180";

    //	private static String www = "http://service.cttms.com:8180/service_center/services/TuoCheBaoService";
    private static String www = www_service + "/service_center/services/TuoCheBaoService";
    //	private static String www_to_tcb = "http://szyt.net:8180/service_center/services/TuoCheBaoService/preExecute";
    private static String www_to_tcb = www_service + "/service_center/services/TuoCheBaoService/preExecute";
    private static String appkey = "4670af91302f9beadb49a802dbb0dcaa";
    private static String tcb_appkey = "D2EE3201ACAD261606B8DC9DD1761209FE15D467B49E39AFF0FC430BA2AFA632";
    protected static List<NameValuePair> nvps = null;// 查询参数
    protected static HttpResponse response = null;
    //	private static String accreditId = "yhlsztms01";
    private static String accreditId = "tcbsztms";
    //	private static String accreditId = "sztms";
//	private static String accreditId = "zcdcs";
//	private static String accreditId = "tcbzcdtest";
//	private static String accreditId = "yhl";
    private static String busiId = "Y16010084-1-1";
    private static int feeid = 4316;
    private static int feeid2 = 1506;
    private static String FS_DriverAcceptOrder = "driverAcceptOrder";// 司机接单接口
    private static String FS_DriverRefuseOrder = "driverRefuseOrder";// 司机拒单接口
    private static String FS_CancelOrder = "cancelOrder";// 调度撤单接口
    private static String FS_EditOrderContainerNo = "editOrderContainerNo";// 修改箱封号接口
    private static String FS_ConfirmAssignment = "confirmAssignment";// 调度确认出车结束接口
    private static String FS_ConfirmFee = "confirmFee";// 司机确认费用接口
    private static String FS_SynchronizationFees = "synchronizationFees";// 同步录入的司机费用接口
    private static String FS_Payment = "payment";// 财务付款接口
    private static String FS_CancelViedOrder = "cancelViedOrder";// 撤销抢单接口
    private static String FS_ManageFee = "manageFee";// 司机挂靠费接口
    private static String FS_AddDriver = "addDriver";// 添加司机接口

    public static void main(String[] args) throws Exception {
//		testFS_FEE();// 查费用
//		testFS_EditOrderContainerNo();// 修改箱封号
        testFS_USER();// 查用户
//		 testFS_WRITEOFFFEE();// 核销费用
//		testFS_SyncFee();// 保存费用
//		testYUPAI();// 预派
//		testFS_GetPicture();//　获取图片
//		testFS_CommitFee();// 调度确认费用
//		testFS_SynchronizationFees();// 同步费用
//		testFS_ConfirmAssignment();// 出车结束
//		testFS_ComfirmFee();// 司机确认费用
//		testFS_ManageFee();// 管理费
//		testFS_DriverAcceptOrder();// 接单
//		testFS_AddDriver();// 新增司机
    }

    private static void testFS_AddDriver() throws Exception {
        JSONObject json = new JSONObject();
        json.put("methodname", FS_AddDriver);
        json.put("accreditid", accreditId);
        json.put("appkey", appkey);
        TcbDriver driver = new TcbDriver();
        driver.setAccreditid(accreditId);
        driver.setCode("zs");
        driver.setDriver("张三");
        driver.setMobilephone("13588888888");
        json.put("data", driver);
        System.out.println(json.toString());
        Object[] arguments = new Object[]{json.toString()};
        String rvalue = executeTcb(www, arguments);
        System.out.println(rvalue);
    }

    private static void testFS_FEE() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appkey", appkey);
        jsonObject.put("accreditid", accreditId);
        jsonObject.put("accessindex", "1");
        jsonObject.put("datasize", "10");
        jsonObject.put("userid", "0");
        jsonObject.put("methodname", "fee");
//		jsonObject.put("methodname", "endcustoms");
        System.out.println(jsonObject.toString());
        Object[] arguments = new Object[]{jsonObject.toString()};
        String rvalue = executeTcb(www, arguments);
        System.out.println(rvalue);
    }

    /**
     * 测试接单接口
     *
     * @throws Exception
     */
    private static void testFS_DriverAcceptOrder() throws Exception {
        DriverAccept driverAccept = new DriverAccept();
        driverAccept.setAccreditid(accreditId);
        driverAccept.setAppkey(appkey);
        driverAccept.setUserid(809);
        driverAccept.setTruckno("粤BBB005");
        driverAccept.setDriver("李书良");
        driverAccept.setPhone("13823698495");
        ArrayList<String> list = new ArrayList<String>();
        list.add(busiId);
        driverAccept.setBusinessids(list.toArray(new String[0]));
        System.out.println(JSONObject.fromObject(driverAccept).toString());
        JSONObject json = JSONObject.fromObject(driverAccept);
        json.put("methodname", FS_DriverAcceptOrder);
        Object[] arguments = new Object[]{json.toString()};
        String rvalue = executeTcb(www_to_tcb, arguments);
        System.out.println(rvalue);
    }

    private static void testFS_ManageFee() throws Exception {
        JSONObject json = new JSONObject();
        json.put("appkey", appkey);
        json.put("accreditid", accreditId);
        json.put("userid", 0);
        json.put("beginDate", "2015-12-01");
        json.put("endDate", "2015-12-31");
        String[] trucks = new String[1];
        trucks[0] = "粤B23456";
        json.put("trucks", JSONArray.fromObject(trucks));
        json.put("methodname", FS_ManageFee);
        System.out.println(json.toString());
        Object[] arguments = new Object[]{json.toString()};
        String rvalue = executeTcb(www, arguments);
        System.out.println(rvalue);
    }

    private static void testFS_ComfirmFee() throws Exception {
        DriverAccept driverAccept = new DriverAccept();
        driverAccept.setAccreditid(accreditId);
        driverAccept.setAppkey(appkey);
        driverAccept.setUserid(1);
        driverAccept.setBusinessid(busiId);
        driverAccept.setType(0);
        JSONObject json = JSONObject.fromObject(driverAccept);
        json.put("methodname", FS_ConfirmFee);
        System.out.println(json.toString());
        Object[] arguments = new Object[]{json.toString()};
        String rvalue = executeTcb(www, arguments);
        System.out.println(rvalue);
    }

    private static void testFS_ConfirmAssignment() throws Exception {
        DriverAccept driverAccept = new DriverAccept();
        driverAccept.setAccreditid(accreditId);
        driverAccept.setAppkey(appkey);
        driverAccept.setUserid(1);
        ArrayList<String> list = new ArrayList<String>();
        list.add(busiId);
        driverAccept.setBusinessids(list.toArray(new String[0]));
        JSONObject json = JSONObject.fromObject(driverAccept);
        json.put("methodname", FS_ConfirmAssignment);
        System.out.println(json.toString());
        Object[] arguments = new Object[]{json.toString()};
        String rvalue = executeTcb(www, arguments);
        System.out.println(rvalue);
    }

    /**
     * 测试同步费用接口
     *
     * @throws Exception
     */
    private static void testFS_SynchronizationFees() throws Exception {
        DriverFeeVO driverFeesVO = new DriverFeeVO();
        driverFeesVO.setAccreditid(accreditId);
        driverFeesVO.setAppkey(appkey);
        driverFeesVO.setUserid(0);
        driverFeesVO.setUsername("测试接口用户");
        driverFeesVO.setBusinessid(busiId);
        ArrayList<DriverFee> list = new ArrayList<DriverFee>();
        DriverFee fee = new DriverFee();
        fee.setFeeid(feeid);
        fee.setAmount(45000);
        fee.setIspicture(0);
        fee.setSynctime("eeeee");
        list.add(fee);
//		DriverFee fee2 = new DriverFee();
//		fee2.setFeeid(feeid2);
//		fee2.setAmount(0);
//		fee2.setSynctime(Console.FS_TIME.getNow());
//		list.add(fee2);
        driverFeesVO.setFee(list.toArray(new DriverFee[0]));
        JSONObject json = JSONObject.fromObject(driverFeesVO);
        json.put("methodname", FS_SynchronizationFees);
        json.toString().replace("\"eeeee\"", "null");
        Object[] arguments = new Object[]{json.toString().replace("\"eeeee\"", "null")};
        System.out.println(json.toString().replace("\"eeeee\"", "null"));
        String rvalue = executeTcb(www_to_tcb, arguments);
        System.out.println(rvalue);
    }

    private static void testFS_CommitFee() throws Exception {
        String jsonString = "{\"accreditid\":\"tcbsztms\",\"appkey\":\"D2EE3201ACAD261606B8DC9DD1761209FE15D467B49E39AFF0FC430BA2AFA632\",\"cmd\":\"CommitFee\",\"data\":[{\"businessid\":\"Y15110005-1-1\"}],\"transmitaccreditid\":\"tcbtest\"}";
        TcbComfirmDriverFeeVO vo = new TcbComfirmDriverFeeVO();
        vo.setAccreditid(accreditId);
        vo.setAppkey(tcb_appkey);
        vo.setCmd("CommitFee");
        TcbComfirmDriverFee tcbComfirmDriverFee = new TcbComfirmDriverFee();
        tcbComfirmDriverFee.setBusinessid(busiId);
        vo.setData(tcbComfirmDriverFee);
        jsonString = JSONObject.fromObject(vo).toString();
        System.out.println(jsonString);
        String rvalue = sendShipxy(www_to_tcb, jsonString);
        System.out.println(rvalue);
    }

    private static void testFS_GetPicture() throws Exception {
        String jsonString = "{\"accreditid\":\"tcbsztms\",\"appkey\":\"D2EE3201ACAD261606B8DC9DD1761209FE15D467B49E39AFF0FC430BA2AFA632\",\"businessid\":\"Y15110005-1-1\",\"cmd\":\"GetFeeImageUrl\",\"feeid\":247,\"userid\":113,\"data\":{\"accreditid\":\"\",\"appkey\":\"\",\"businessid\":\"Y15110005-1-1\",\"cmd\":\"\",\"feeid\":247,\"userid\":0},\"transmitaccreditid\":\"tcbtest\"}";
        JSONObject obj = new JSONObject();
        obj.put("accreditid", accreditId);
        obj.put("appkey", tcb_appkey);
        obj.put("cmd", "GetFeeImageUrl");
        JSONObject j = new JSONObject();
        j.put("businessid", busiId);
        j.put("feeid", feeid);
        obj.put("data", j);
        System.out.println(obj.toString());
        jsonString = obj.toString();
        String rvalue = sendShipxy(www_to_tcb, jsonString);
        System.out.println(rvalue);
    }

    private static void testFS_SyncFee() throws Exception {
        TcbPayoutFee payoutFee = new TcbPayoutFee();
        payoutFee.setAmount(300);
        payoutFee.setFeeid(1606);
        payoutFee.setIspicture(0);
//		payoutFee.setSynctime("2015-12-24 14:00:00");
        TcbSyncFee fee = new TcbSyncFee();
        fee.setAccreditid(accreditId);
        fee.setAppkey(tcb_appkey);
        fee.setBusinessid(busiId);
//		{"accreditid":"tcbzcdtest","appkey":"D2EE3201ACAD261606B8DC9DD1761209FE15D467B49E39AFF0FC430BA2AFA632","businessid":"Y15120022-1-1","cmd":"SaveFee","data":[{"amount":30,"feeid":1407,"ispicture":1,"synctime":"2015-12-04 18:42:33"}],"transmitaccreditid":"tcbtest"}
        fee.setCmd("SaveFee");
        TcbPayoutFee[] data = new TcbPayoutFee[1];
        data[0] = payoutFee;
        fee.setData(data);
        JSONObject jsonobject = JSONObject.fromObject(fee);
        jsonobject.put("transmitaccreditid", "tcb");
        String jsonString = jsonobject.toString();
        System.out.println(jsonString);
        String rvalue = sendShipxy(www_to_tcb, jsonString);
        System.out.println(rvalue);

        System.out.println("end");
    }

    private static void testYUPAI() throws Exception {
        String jsonString = "{\"accreditid\":\"zcdcs\",\"appkey\":\"D2EE3201ACAD261606B8DC9DD1761209FE15D467B49E39AFF0FC430BA2AFA632\",\"cmd\":\"YUPAI\",\"data\":[{\"accreditid\":\"zcdcs\",\"address\":\"????????????229-3\",\"appointTime\":\"2016-01-19 11:44:22\",\"arriveTime\":\"\",\"bookingNo\":\"DCH001\",\"businessid\":\"Y16010005-1-1\",\"businesstype\":0,\"contact\":\"??\",\"containerNo\":\"\",\"containerType\":\"20GP\",\"containerWeight\":0,\"cubage\":0,\"customerShortName\":\"????\",\"customs\":\"??\",\"customsMode\":\"??\",\"cutOffTime\":\"\",\"cutOffWarehouseTime\":\"\",\"dispatchtarget\":0,\"dispatchtype\":1,\"driver\":\"michael\",\"driverMobilePhone\":\"13810510619\",\"endCustoms\":\"\",\"endPort\":\"\",\"factoryShortName\":\"????\",\"getConPile\":\"??????\",\"getConPileContact\":\"\",\"getConPileTelephone\":\"25267517\",\"goodName\":\"\",\"ismatch\":1,\"lat\":22.595071,\"leaveTime\":\"\",\"line\":\"\",\"lng\":114.140239,\"loadPlace\":\"??\",\"matchbusinessid\":\"Y16010005-1-2\",\"onDutyTelephone\":\"\",\"piece\":0,\"returnConPile\":\"??????\",\"returnConPileContact\":\"\",\"returnConPileTelephone\":\"29838961/2/4\",\"returnTime\":\"\",\"sealNo\":\"\",\"ship\":\"\",\"shipLeaveTime\":\"\",\"shipperRemark\":\"\",\"startCustoms\":\"\",\"startPort\":\"\",\"telephone\":\"\",\"transportFee\":0,\"transportTeam\":\"????\",\"transportTeamTelephone\":\"13510551303\",\"truck\":\"?C0806\",\"voyage\":\"\",\"warehouse\":\"\",\"warehouseContact\":\"\",\"warehouseTelephone\":\"\",\"weight\":0},{\"accreditid\":\"zcdcs\",\"address\":\"????????????229-3\",\"appointTime\":\"2016-01-19 11:44:22\",\"arriveTime\":\"\",\"bookingNo\":\"DCH0010\",\"businessid\":\"Y16010005-1-2\",\"businesstype\":0,\"contact\":\"??\",\"containerNo\":\"\",\"containerType\":\"20GP\",\"containerWeight\":0,\"cubage\":0,\"customerShortName\":\"????\",\"customs\":\"??\",\"customsMode\":\"??\",\"cutOffTime\":\"\",\"cutOffWarehouseTime\":\"\",\"dispatchtarget\":0,\"dispatchtype\":1,\"driver\":\"michael\",\"driverMobilePhone\":\"13810510619\",\"endCustoms\":\"\",\"endPort\":\"\",\"factoryShortName\":\"????\",\"getConPile\":\"??????\",\"getConPileContact\":\"\",\"getConPileTelephone\":\"25267517\",\"goodName\":\"\",\"ismatch\":1,\"lat\":22.595071,\"leaveTime\":\"\",\"line\":\"\",\"lng\":114.140239,\"loadPlace\":\"??\",\"matchbusinessid\":\"Y16010005-1-1\",\"onDutyTelephone\":\"\",\"piece\":0,\"returnConPile\":\"??????\",\"returnConPileContact\":\"\",\"returnConPileTelephone\":\"29838961/2/4\",\"returnTime\":\"\",\"sealNo\":\"\",\"ship\":\"\",\"shipLeaveTime\":\"\",\"shipperRemark\":\"\",\"startCustoms\":\"\",\"startPort\":\"\",\"telephone\":\"\",\"transportFee\":0,\"transportTeam\":\"????\",\"transportTeamTelephone\":\"13510551303\",\"truck\":\"?C0806\",\"voyage\":\"\",\"warehouse\":\"\",\"warehouseContact\":\"\",\"warehouseTelephone\":\"\",\"weight\":0}],\"userid\":918,\"transmitaccreditid\":\"tcb\"}";
        String rvalue = sendShipxy(www_to_tcb, jsonString);
        System.out.println(rvalue);
        System.out.println("end");
    }

    private static void testFS_WRITEOFFFEE() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appkey", tcb_appkey);
        jsonObject.put("accreditid", accreditId);
        jsonObject.put("cmd", "SaveWriteOffFee");
        jsonObject.put("transmitaccreditid", "tcbtest");
        jsonObject.put("data", "[{\"businessid\":\"Y15110004-1-1\",\"feeid\":1163,\"wfamount\":11}]");
        System.out.println(jsonObject.toString());
        Object[] arguments = new Object[]{jsonObject.toString()};
        String rvalue = executeTcb(www, arguments);
        System.out.println(rvalue);
    }

    /**
     * 测试用户接口
     *
     * @throws Exception
     */
    private static void testFS_USER() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appkey", appkey);
        jsonObject.put("accreditid", accreditId);
        jsonObject.put("accessindex", "1");
        jsonObject.put("datasize", "10");
        jsonObject.put("userid", "0");
        jsonObject.put("methodname", "user");
        System.out.println(jsonObject.toString());
        Object[] arguments = new Object[]{jsonObject.toString()};
        String rvalue = executeTcb(www, arguments);
        System.out.println(rvalue);
    }

    public static String executeTcb(String www, Object[] arguments) throws Exception {
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
            Class<?>[] classes = new Class<?>[]{String.class};

            // 指定要调用的方法及WSDL文件的命名空间
            QName opAddEntry = new QName(S_namespace, "preExecute");
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

    private static String sendShipxy(String www, String jsonString) throws Exception {
        String rvalue = "";
        nvps = new ArrayList<NameValuePair>();// 参数
        // 添加参数
        // json数据
        JSONObject object = JSONObject.fromObject(jsonString);
        object.put("transmitaccreditid", "tcbtest");
        jsonString = object.toString();
        nvps.add(new BasicNameValuePair("jsonString", URLEncoder.encode(jsonString, "UTF-8")));
        rvalue = execute(www, "UTF-8");
        if (rvalue.indexOf("{") != 0) {
            int begin = rvalue.indexOf("{"), end = rvalue.lastIndexOf("}") + 1;
            rvalue = rvalue.substring(begin, end);
        }
        return rvalue;
    }

    /**
     * 测试箱封号接口
     *
     * @throws Exception
     */
    private static void testFS_EditOrderContainerNo() throws Exception {
        DriverAccept driverAccept = new DriverAccept();
        driverAccept.setAccreditid(accreditId);
        driverAccept.setAppkey(appkey);
        driverAccept.setUserid(0);
        driverAccept.setContainerno("APLU1111114");
        driverAccept.setSealno("ff001");
        driverAccept.setBusinessid(busiId);
        driverAccept.setConsealnoremark("箱封号备注信息");
        System.out.println(JSONObject.fromObject(driverAccept).toString());
        JSONObject json = JSONObject.fromObject(driverAccept);
        json.put("methodname", FS_EditOrderContainerNo);
        Object[] arguments = new Object[]{json.toString()};
        String rvalue = executeTcb(www, arguments);
        System.out.println(rvalue);
    }

    /**
     * post访问,访问一次后关闭链接
     *
     * @param wwwUrl   网址
     * @param encoding 编码
     * @return
     * @throws Exception
     */
    protected static String execute(String wwwUrl, String encoding) throws Exception {
        // 获取最新记录
        DefaultHttpClient httpclient = null;
        String html = StringManage.FS_EMPTY;
        try {
            // 设置请求参数
            HttpPost httpPost = new HttpPost(wwwUrl);
            if (StringManage.isEmpty(encoding)) {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            } else {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
            }

            httpclient = new DefaultHttpClient();
            response = httpclient.execute(httpPost);
            html = getEntityString(response, encoding);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpclient != null) {
                httpclient.getConnectionManager().shutdown();
            }
        }
        return html;
    }

    private static String getEntityString(HttpResponse response, String encoding) throws Exception {
        String html = StringManage.FS_EMPTY;
        HttpEntity entity = response.getEntity();
        if (StringManage.isEmpty(encoding)) {
            html = EntityUtils.toString(entity);
        } else {
            html = EntityUtils.toString(entity, encoding);
        }
        // 释放资源
        EntityUtils.consume(entity);
        return html;
    }

}
