package com.lyq.chedui;

import com.kingsoft.control.util.StringManage;
import com.lyq.chedui.base.AppFeedbackJson;
import com.lyq.chedui.base.CdbBase;
import com.lyq.common.Base64Helper;
import com.lyq.common.ExecuteService;
import net.sf.json.JSONObject;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * 车队版APP测试类
 *
 * @author Administrator
 */
public class TestCdb extends ExecuteService {
    // private static String www =
    // "http://frm.cttms.com:8080/framework/services/AppTransportService";
    // private static String www =
    // "http://192.168.1.69/service_center/services/AppTransportService";
//    private static String www = "http://szyt.net:8180/service_center/services/AppTransportService";
    private static String www = "http://service.cttms.com:8180/service_center/services/AppTransportService";
    // private static String www =
    // "http://ys.cttms.com/services/AppTransportService";
    private static String accreditId = "nbdemo";
    private static String userId = "jkx";
    private static String password = "jkx168";// 加密2762c44b29a06d4a

    public static void main(String[] args) throws Exception {
        // register();// 注册
//         login();// 登录
        // busiIncomePayCount();// 主要业务收支统计
        // truckMakeAmount();
        // customerMakeAmount();
        // dayRevenueAnalytic();
        // incomePayDetail();
        saveFeedback();// 添加意见反馈
    }


    public void testWebp() {
        File file1 = new File("C:\\Users\\lyq\\Desktop\\1.webp");
        File file2 = new File("C:\\Users\\lyq\\Desktop\\1.png");
//        System.out.println(System.getProperty("java.library.path"));
        try {
            BufferedImage im = ImageIO.read(file1);
            ImageIO.write(im, "png", file2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加意见反馈
     *
     * @throws Exception
     */
    private static void saveFeedback() throws Exception {
        AppFeedbackJson appFeedbackJson = new AppFeedbackJson();
        appFeedbackJson.setAccreditId("nbdemo");
        appFeedbackJson.setUserId("jkx");
        appFeedbackJson.setPassword("jkx168");
        appFeedbackJson.setContents("意见反馈测试19");
        appFeedbackJson.setMethodname("saveFeedback");
        appFeedbackJson.setUniqueKey("jkx20160513175040");
        appFeedbackJson.setMobilePhoneInfo("13510551303魅族Note1");
        appFeedbackJson.setVersion("v0.1.1");
        appFeedbackJson.setImageFormat("webp");
        String filePath = "C:\\Users\\lyq\\Desktop\\1.webp";
//        appFeedbackJson.setImageFormat("png");
//        String filePath = "C:\\Users\\lyq\\Desktop\\jyb.png";
        appFeedbackJson.setImageStreams(new String[]{getImageStream(filePath)});
//        appFeedbackJson.setImageStreams(new String[]{getFile("C:\\Users\\lyq\\Desktop\\w.txt")});
        System.out.println(objectToJsonString(appFeedbackJson));
        System.out.println(execute(objectToJsonString(appFeedbackJson)));
//        System.out.println(getFile("D:\\用户资料\\Desktop\\req.txt"));
//        System.out.println(execute(getFile("D:\\用户资料\\Desktop\\req.txt")));
    }

    public static String getFile(String filePath) {
        File file = null;
        if (StringManage.isEmpty(filePath)) {
            file = new File("D:\\用户资料\\Desktop\\new.txt");
        } else {
            file = new File(filePath);
        }
        Scanner scanner = null;
        String str = "";
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                str += scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        // System.out.println(str);
        return str;
    }

    /**
     * 获取图片流
     *
     * @param filePath 图片路径
     * @return
     */
    private static String getImageStream(String filePath) {
        return Base64Helper.GetImageStr(filePath);
    }

    /**
     * 登录
     *
     * @throws Exception
     */
    public static void login() throws Exception {
        CdbBase base = new CdbBase();
        setBase(base, "login");
        System.out.println(objectToJsonString(base));
        System.out.println(execute(objectToJsonString(base)));
        // http://ys.cttms.com:8180/services/AppDriverService/preExecute?jsonString={%22accreditId%22:%22demo%22,%22methodname%22:%22login%22,%22password%22:%22123456%22,%22truckNo%22:%22%E7%B2%A4BL88888%22}
        // http://192.168.1.69:8180/crm/services/AppDriverService/preExecute?jsonString={"accreditId":"demo","methodname":"login","password":"123456","truckNo":"粤BL88888"}
    }

    private static String execute(String jsonString) throws Exception {
        return execute(www, jsonString);
    }

    /**
     * 对象转成json格式输出
     *
     * @param obj 对象
     * @return
     */
    public static String objectToJsonString(Object obj) {
        return JSONObject.fromObject(obj).toString();
    }

    /**
     * 设置基础信息,车牌、密码、授权编号、方法名
     */
    private static void setBase(CdbBase base, String methodName) {
        base.setAccreditId(accreditId);
        base.setUserId(userId);
        base.setPassword(password);
        base.setMethodname(methodName);
    }

    private static void busiIncomePayCount() throws Exception {
        JSONObject json = new JSONObject();
        json.put("methodname", "busiIncomePayCount");
        json.put("accreditId", accreditId);
        json.put("userId", userId);
        json.put("password", password);
        json.put("month", "2016-04");

        System.out.println(execute(json.toString()));

    }

    private static void truckMakeAmount() throws Exception {
        JSONObject json = new JSONObject();
        json.put("methodname", "truckMakeAmount");
        json.put("accreditId", accreditId);
        json.put("userId", userId);
        json.put("password", password);
        json.put("month", "2016-04");

        System.out.println(execute(json.toString()));

    }

    private static void customerMakeAmount() throws Exception {
        JSONObject json = new JSONObject();
        json.put("methodname", "customerMakeAmount");
        json.put("accreditId", accreditId);
        json.put("userId", userId);
        json.put("password", password);
        json.put("month", "2016-04");

        System.out.println(execute(json.toString()));

    }

    private static void dayRevenueAnalytic() throws Exception {
        JSONObject json = new JSONObject();
        json.put("methodname", "dayRevenueAnalytic");
        json.put("accreditId", accreditId);
        json.put("userId", userId);
        json.put("password", password);
        json.put("month", "2016-04");

        System.out.println(execute(json.toString()));

    }

    private static void incomePayDetail() throws Exception {
        JSONObject json = new JSONObject();
        json.put("methodname", "incomePayDetail");
        json.put("accreditId", accreditId);
        json.put("userId", userId);
        json.put("password", password);
        json.put("month", "2016-03");

        System.out.println(execute(json.toString()));

    }

}
