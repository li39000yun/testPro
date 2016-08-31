package com.kingsoft.nb.outer.npedi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

import org.apache.log4j.Logger;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import com.kingsoft.control.Console;
import com.kingsoft.control.util.StringManage;
import com.kingsoft.nb.business.vo.outer.npedi.Voyage;
import com.kingsoft.nb.business.vo.outer.npedi.VoyageInfoVO;
import com.kingsoft.nb.outer.VoyageInfo;

import net.sf.json.JSONArray;

/**
 *  URL：http://www.npedi.com/ediportal-web/ediweb/VoyageInfo.jsp
 *  EDI抓取 船名航次
 * @author lxm
 */
/**
 * @author Administrator
 *
 */
public class InGateInfoImpl{
  private static Logger S_Logger = Logger.getLogger(InGateInfoImpl.class);
  
  private String url = "http://www.npedi.com/ediportal-web/getVoyagePorts.action";
  private String voyage_url = "http://www.npedi.com/ediportal-web/getOnesiteCustLcmdUV.action";
  
  private static final Pattern FS_PATTERN = Pattern.compile("(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})");
  private static Matcher matcher;
  private Map<String,String> matous = new HashMap<String, String>();
  private List<String> voyages = new ArrayList<String>();
  private List<VoyageInfo> voyageInfos = new ArrayList<VoyageInfo>();
  
  public InGateInfoImpl(){}
  
  public VoyageInfo[] fetch() throws InterruptedException {
    matous.put("BLCT","NBCT(二期)");
    matous.put("BLCT2","北二集司(三期)");
    matous.put("BLCT3","港吉(四期)");
    matous.put("BLCTYD","远东(五期)");
    matous.put("BLCTZS","大榭招商(CMICT)");
    matous.put("BLCTMS","梅山码头");
    matous.put("ZHCT","镇司(ZHCT)");
    matous.put("ZIT","世航五洲(乍浦码头)");
    matous.put("YZCT","甬舟码头");
    matous.put("DXCTE","大榭E港区");
    matous.put("B2SCT","北仑山码头");
    
    getAllVoyage();
    System.out.println("all voyage："+voyages.size());
    if (S_Logger.isDebugEnabled())
		S_Logger.debug("transit_nb - InGateInfoImpl fetch all voyage length:"+ voyageInfos.size());
    
    int count=1;
    for (String vesselcode : voyages) {
      getInfo(vesselcode);
      if(count%50==0){
        Thread.sleep(getRand()*1000);
      }
      count++;
    }
    
    if (S_Logger.isDebugEnabled())
		S_Logger.debug("transit_nb - InGateInfoImpl fetch OK length:"+ voyageInfos.size() +" | | time:"+Console.FS_DATE.getNow());
    return voyageInfos.toArray(new VoyageInfo[0]);
  }
  
  /**
   * 获取1-5之间随机数
   * @return
   */
  public int getRand() {
    return (int)(Math.random()*10)%6+1;
  }
  /**
   * 获取列表所有船名航次  un号/航次 数组
   */
  private void getAllVoyage() {
    try {
      Response respose = Jsoup.connect(voyage_url)
          .header("Cookie", "JSESSIONID=MqaqMYAfqpImBlckpMUNtDGK.edi-webb")
          .ignoreContentType(true)
          .timeout(5*1000)
          .execute();
      String JsonStr = respose.body().toString();
      if (JsonStr.isEmpty())
        return;
      JSONArray fromObject = JSONArray.fromObject(JsonStr);
      VoyageInfoVO[] beans =  (VoyageInfoVO[]) JSONArray.toArray(fromObject,VoyageInfoVO.class);
      for (VoyageInfoVO data : beans) {
        voyages.add(data.getVesselcode()+"/"+data.getVoyage());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
  }
  
  /**
   * 获取船详细信息
   * @param vesselcode
   */
  public void getInfo(String vesselcode) {
    try {
      Response respose = Jsoup.connect(url)
          .header("Cookie", "JSESSIONID=WWl7I9wnOfdPD1c0zIIPU-NC.edi-webc")
          .data("cpcode", "")
          .data("vesselcode", vesselcode)
          .ignoreContentType(true)
          .method(Method.GET)
          .timeout(10*1000)
          .execute();
      
      String JsonStr = respose.body().toString();
      if (JsonStr.isEmpty())
        return;
      
      JSONArray fromObject = JSONArray.fromObject(JsonStr);
      Voyage[] datas =  (Voyage[]) JSONArray.toArray(fromObject,Voyage.class);
      VoyageInfo info;
      for (Voyage data : datas) {
    	  
    	//满足条件数据
			info = new VoyageInfo();
			info.setEn_ship(data.getVesselename());// 英文船名
			info.setUnCode(data.getVesselcode());// 船舶UN代码
			info.setVoyage(data.getVoyage());// 航次
			info.setDock(matous.get(data.getCpcode()));// 码头
			info.setType("E");
			if(!StringManage.isEmpty(data.getCtnstart()))
				info.setInStartTime(fmtDate(data.getCtnstart()));
			if(!StringManage.isEmpty(data.getCtnend()))
				info.setInCutOffTime(fmtDate(data.getCtnend()));
			voyageInfos.add(info);
      }
    } catch (Exception e) {
    	if (S_Logger.isDebugEnabled())
			S_Logger.debug("transit_nb - error - InGateInfoImpl voyage:"+ vesselcode+" | | time:"+Console.FS_DATE.getNow());
      e.printStackTrace();
    }
  }
  
  /**
   * 格式化日期
   * @param dateStr
   * @return
   * @throws DataFormatException
   */
  private static String fmtDate(String dateStr) throws DataFormatException{
    if(dateStr.isEmpty()) return "";
    matcher = FS_PATTERN.matcher(dateStr);
    if(matcher.find())
      return matcher.group(1)+"-"+matcher.group(2)+"-"+matcher.group(3)+" "+matcher.group(4)+":"+matcher.group(5)+":"+matcher.group(6);
    else
      throw new DataFormatException(dateStr + " 日期格式不对");
  } 
  
}
