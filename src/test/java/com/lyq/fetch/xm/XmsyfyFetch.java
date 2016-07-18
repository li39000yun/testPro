package com.lyq.fetch.xm;

import com.kingsoft.control.util.StringManage;
import com.lyq.fetch.common.AbstractFetch;
import com.lyq.fetch.vo.FetchData;
import com.lyq.fetch.vo.FetchSearch;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 厦门嵩屿码头费用数据抓取服务类
 * 
 * @author liyunqiang
 * 
 * @version 2015-5-6
 * 
 * @since JDK 1.6
 * 
 */
public class XmsyfyFetch extends AbstractFetch {
	private static Logger S_Logger = Logger.getLogger(XmsyfyFetch.class);
	private static final long serialVersionUID = 1L;
	/** 查询网址维护后费用 */
	private static final String FS_URL = "http://www.xsct.com.cn/new/Com_Tarrif_report.aspx";
	/** 查询网址维护前费用 */
	private static final String FS_URL_BEFORE = "http://www.xsct.com.cn/new/Com_Tarrif_report_latest.aspx";
	/** 返回结果 */
	private List<FetchData> fetchDatas = new ArrayList<FetchData>();

	private String bn = StringManage.FS_EMPTY;// 提单
	private String cn = StringManage.FS_EMPTY;// 箱号
	private int type=2;//用于查询页面表格id 2维护后，1维护前 (2个网址对应的数据表格Id不一样  id=GridView+type)
	private int hasPages=0;//存在分页

	public static void main(String[] args) {
		FetchSearch serch = new FetchSearch();
		serch.setRegionId(4);
		serch.setDock("XMSYFY");
//		serch.setBookingNo("576017283");
//		serch.setContainerNo("MRKU7187200");
        serch.setBookingNo("1811X0160565030J1");
        serch.setContainerNo("MEDU9531572");
		XmsyfyFetch main = new XmsyfyFetch();
		try {
			String rvalue = main.preExecute(serch);
			System.out.println(rvalue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String preExecute(FetchSearch search) throws Exception {

		bn = search.getBookingNo();
		cn = search.getContainerNo();
		String rvalue="";
		try {
			rvalue = fetch(FS_URL + "?bn=" + search.getBookingNo() + "&cn=");
			System.out.println("after:"+rvalue);
			if (rvalue.equals("[]")) {
				type=1;
				rvalue = fetch(FS_URL_BEFORE + "?bn=" + search.getBookingNo() + "&cn=");
				System.out.println("befor:"+rvalue);
			}
		}catch (Exception e) {
			if (S_Logger.isDebugEnabled())
				S_Logger.debug("service_center - XmsyfyFetch error type="+type +"  "+ search.toString2() +" " + e.getMessage());
			e.printStackTrace();
		}
		return rvalue;
	}

	public String fetch(String url) throws Exception {
		Document doc = Jsoup.connect(url).post();
		// 标题
		Elements headName = doc.select("table#GridView"+type+" tr th");
		// 字段表示在表格中td下标，key=标题中文名 es:提单号
		Map<String, Integer> headNameMap = new HashMap<String, Integer>();
		Map<Integer, String> headIndexMap = new HashMap<Integer, String>();
		int rowIndex = 0;
		for (Element th : headName) {
			headNameMap.put(th.text(), rowIndex);
			headIndexMap.put(rowIndex, th.text());
			rowIndex++;
		}
		if (headName.isEmpty())
			return "[]";

		// 获取页面表格，最后一行，判断是否有分页(id=GridView1的表格 tr class =gv-foot)
		Elements lastRow = doc.select("table#GridView"+type+" tr.gv-foot");
		int pageLength = 1;
		if (lastRow != null && lastRow.size()>0) {// lastRow.text() 结果 1 2 3
			pageLength = lastRow.text().split(" ").length;
			hasPages=1;
		}

		// 处理分页
		String result = StringManage.FS_EMPTY;
		for (int page = 1; page <= pageLength; page++) {
			result = nextPage(doc, headNameMap, headIndexMap, page);
		}
		return result;
	}

	/**
	 * 处理分页
	 * 
	 * @param doc
	 *            文档对象
	 * @param headNameMap
	 *            标题下标Map
	 * @param headIndexMap
	 *            标题下标Map
	 * @param page
	 *            第几页
	 * @return
	 * @throws Exception
	 */
	public String nextPage(Document doc , Map<String, Integer> headNameMap , Map<Integer, String> headIndexMap , int page) throws Exception {
		if (page != 1)
			doc = Jsoup.connect("http://www.xsct.com.cn/new/Com_Tarrif_report_latest.aspx")
					.header("Cookie", "ASP.NET_SessionId=duwceg451wlc5a45gyeur345")
					.data("bn", bn)
					.data("cn", "")
					.data("__EVENTARGUMENT", "Page$" + page)
					.data("__EVENTTARGET", "GridView"+type)
					.data("__EVENTVALIDATION", "/wEWAwLRjJuNDgKM54rGBgKtsp64BPtsRf+b6+PZulPX58Z1b6GV+ceB")
					.data("__VIEWSTATE","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")
					.timeout(8000)
					.post();

		// 获取 tr带align属性的行(table id=GridView1表格)
		Elements trs = doc.select("table#GridView"+type+" tr[align=center]");
		if (trs == null)
			return "";

		// feeBeginIndex:费用开始下标 , feeEndIndex:结束下标
		int feeBeginIndex = headNameMap.get("堆存天数") + 1;
		int feeEndIndex = headNameMap.get("费用合计");

		// 循环去除最后一行分页(有分页才去 -hasPages )
		for (int i = 0; i < trs.size() - hasPages; i++) {
			// 箱号封号与查询的不一致，跳过
			if (!bn.equals(trs.get(i).child(headNameMap.get("提单号")).text()) 
					|| !cn.equals(trs.get(i).child(headNameMap.get("箱号")).text()))
				continue;

			// 循环费用
			for (int feeIndex = feeBeginIndex; feeIndex < feeEndIndex; feeIndex++) {
				if ("0".equals(trs.get(i).child(feeIndex).text()))
					continue;
//				 System.out.print("||"+headIndexMap.get(feeIndex) +" " +
//				 trs.get(i).child(feeIndex).text());

				// trs.get(i).child(j).text() = 费用值
				if (isNumeric(trs.get(i).child(feeIndex).text())) {
					if (Double.parseDouble(trs.get(i).child(feeIndex).text()) != 0) {
						FetchData fetchData = new FetchData();
						fetchData.setName(headIndexMap.get(feeIndex));
						fetchData.setValue(trs.get(i).child(feeIndex).text());
						fetchDatas.add(fetchData);
					}
				}
			}
			// System.out.println();
		}
		return arrayToJson(fetchDatas.toArray(new FetchData[0]));
	}
}