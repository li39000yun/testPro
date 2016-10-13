package com.lyq.fetch.sh;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


/**
 * 该类为读取亿通货箱信息
 * @author lxm
 * @date 2016年7月8日
 */
public class ETGoodsInfoFetch extends AbstractOuterFetch{
	private static Logger S_Logger = Logger.getLogger(ETGoodsInfoFetch.class);
	 //验证码url
    private String img_url = "http://edi.easipass.com/dataportal/query.do?qn=dp_captcha";
    //获取cookie url
    private String cookie_url = "http://edi.easipass.com/dataportal/q.do?qn=dp_cst_vsl";
    //查询url
    private String query_url = "http://edi.easipass.com/dataportal/query.do";
    private List<GoodsContainerInfo> result = new ArrayList<GoodsContainerInfo>();// 返回列表数据


    public static void main(String[] args) throws Exception {
        ETGoodsInfoFetch fetch = new ETGoodsInfoFetch();
        List<GoodsContainerInfo> infos = fetch.fetch("SITU2808417","","");
        for (int i = 0; i < infos.size(); i++) {
            GoodsContainerInfo goodsContainerInfo =  infos.get(i);
            System.out.println(i+":"+goodsContainerInfo.getEtBookingNo());
        }
    }

    public List<GoodsContainerInfo> fetch(String con, String ship, String vos) throws Exception {
    	String validateCode="";
        try {
        	setHost("edi.easipass.com");
 			getCookie(cookie_url);
			validateCode = getValidateCode(img_url);
            // 手动输入验证码
            Scanner scanner = new Scanner(System.in);
            System.out.println("输入验证码");
            validateCode = scanner.nextLine();
 			//提交查询
            Document doc = Jsoup.connect(query_url).header("Cookie", getCookie())
                    .data("ctn0", con)
                    .data("bln0", "")//提单号必填
                    .data("captcha", validateCode)
                    .data("pagesize", "30")
                    .data("submit", "提交")
                    .data("qid", "402803af0ecb1a4c010ecb1bb2b3003e")
//                    .data("qn", "dp_cst_vsl")
                    .get();



//            http://edi.easipass.com/dataportal/query.do?ctn0=SITU2808417&bln0=&captcha=5gp5&pagesize=30&submit=%E6%89%A7%E8%A1%8C&qid=402803af0ecb1a4c010ecb1bb2b3003e

//            http://edi.easipass.com/dataportal/query.do?qn=dp_cst_query_ctndetail

//            http://edi.easipass.com/dataportal/query.do?qn=dp_captcha&t=0.31700020174697296

//            http://edi.easipass.com/dataportal/query.do?qn=dp_cst_query_ctndetail&vs1name=SITC LAEM CHABANG&v0yage
//            =1618S&ctn0=SITU2808417&captcha=3386
//            http://edi.easipass.com/dataportal/query.do?ctn0=SITU2808417&bln0=&captcha=837b&pagesize=30&submit=%E6%9F%A5%E8%AF%A2&qn=dp_cst_vsl
//            http://edi.easipass.com/dataportal/query.do?qn=dp_cst_query_ctndetail&vs1name=SITC%20LAEM%20CHABANG&v0yage=1618S&ctn0=SITU2808417&captcha=837b
            System.out.println("code:"+validateCode);
            System.out.println(doc.html());

            //获取第2个表，第2行
            Elements info = doc.select("td[height=100]");//出错是有这个td
            if(doc.toString().contains("INFO：") && info!=null){
                if (S_Logger.isDebugEnabled())
					S_Logger.debug("service_center - ETGoodsInfoFetch contaienrNo: " + con + "  " + info.text());
                return result;
            }
            Elements trs = doc.select("table:eq(1) tr:eq(1)");
            if (trs == null || trs.size() == 0) return result;
            
            GoodsContainerInfo goods = new GoodsContainerInfo();
            goods.setEtShip(trs.last().child(0).text().replace("船名: ",""));
			goods.setEtVoyage(trs.last().child(1).text().replace("航次: ",""));
			
//            System.out.println(trs.last().child(0).text().replace("船名: ",""));
//            System.out.println(trs.last().child(1).text().replace("航次: ",""));
//            System.out.println(trs.last().child(2).text().replace("箱号: ",""));

            //获取提单，重量件数,根据箱号查询只会有一条记录
            trs = doc.select("table[width=900] tr:eq(1)");
            
//            System.out.println("提单号:" + trs.first().child(0).text());
//            System.out.println("重量:" + trs.first().child(1).text());
//            System.out.println("件数:" + trs.first().child(2).text());
//            System.out.println("卸港代码:" + trs.first().child(5).text());
           
			goods.setEtBookingNo(trs.first().child(0).text());//提单号
			goods.setEtGoodsWeight(Double.parseDouble(trs.first().child(1).text()));//货重
			goods.setEtPiece(Integer.parseInt(trs.first().child(2).text()));//件数
			goods.setEtDischarge(trs.first().child(5).text());//卸港代码
            result.add(goods);
            
        } catch (Exception e) {
        	if (S_Logger.isDebugEnabled())
				S_Logger.debug("service_center - ETGoodsInfoFetch error contaienrNo: " + con + "  验证码 : "+validateCode +" "+ e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

}
