package com.lyq.tcb;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class TestShipxyPost2 {
	public static void main(String[] args) throws Exception {
        HttpClient client = HttpClients. createDefault();
        
        HttpPost post = new HttpPost("http://124.127.127.166:8002/api/Desktop/KingTech" );
        
         //组装一个 json串，用于发送
//        JSONObject jsonObj = new JSONObject();
//        jsonObj.put( "website" , "http://www.dutycode.com" );
//        jsonObj.put( "email" , "dutycode@gmail.com" );
        
        String j = "{\"accreditid\":\"zcdcs\",\"appkey\":\"D2EE3201ACAD261606B8DC9DD1761209FE15D467B49E39AFF0FC430BA2AFA632\",\"cmd\":\"YUQIANG\",\"data\":[{\"accreditid\":\"zcdcs\",\"address\":\"\",\"appointTime\":\"2015-10-16 16:57:46\",\"arriveTime\":\"\",\"bookingNo\":\"DDFFFF\",\"businessid\":\"Y15100002-1-1\",\"businesstype\":0,\"contact\":\"\",\"containerNo\":\"\",\"containerType\":\"20GP\",\"containerWeight\":0,\"cubage\":0,\"customerShortName\":\"测试客户\",\"customs\":\"冠通\",\"customsMode\":\"\",\"cutOffTime\":\"\",\"cutOffWarehouseTime\":\"\",\"dispatchtarget\":0,\"dispatchtype\":0,\"driver\":\"王志平\",\"driverMobilePhone\":\"13126733579\",\"endPort\":\"\",\"factoryShortName\":\"\",\"getConPile\":\"\",\"getConPileContact\":\"\",\"getConPileTelephone\":\"\",\"goodName\":\"\",\"ismatch\":0,\"leaveTime\":\"\",\"line\":\"\",\"loadPlace\":\"\",\"matchbusinessid\":\"\",\"onDutyTelephone\":\"\",\"piece\":0,\"returnConPile\":\"\",\"returnConPileContact\":\"\",\"returnConPileTelephone\":\"\",\"returnTime\":\"\",\"sealNo\":\"\",\"ship\":\"\",\"shipperRemark\":\"\",\"startCustoms\":\"\",\"startPort\":\"深圳\",\"telephone\":\"\",\"transportFee\":0,\"transportTeam\":\"测试车队\",\"transportTeamTelephone\":\"13510551303\",\"truck\":\"\",\"voyage\":\"\",\"warehouse\":\"\",\"warehouseContact\":\"\",\"warehouseTelephone\":\"\",\"weight\":0}],\"userid\":918}";

        JSONObject js = new JSONObject();
        js.put("accreditId", "zcdcs");
        js.put("cmd", "YUPAI");
        js.toString();
        System.out.println(js.toString());
        
        StringEntity entity = new StringEntity(j);
        entity.setContentEncoding( "UTF-8" );
        entity.setContentType( "application/json" );//设置为 json数据
        
        post.setEntity(entity);
        
        HttpResponse response = client.execute(post);
        
        HttpEntity resEntity = response.getEntity();
        String res = EntityUtils. toString(resEntity);
        
        System. out .println(res);
  }
}
