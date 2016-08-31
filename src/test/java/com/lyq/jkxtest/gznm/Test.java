package com.lyq.jkxtest.gznm;

import com.lyq.jkxtest.common.DB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Test extends DB {
	public static void main(String[] args) throws SQLException {
		user = "kingsoft";
		password = "";
		conn = getConn();
		String sql = "SELECT accredit_id, busi_id, sequence, other_report_id, other_payoutUnit_id FROM busi_container WHERE other_report_id != '' AND other_payoutUnit_id != '0,' ";
		rs = executeQuery(sql);
		List<Container> containers = new ArrayList<Container>();
		Container container;
		while(rs.next()){
			container = new Container(
					rs.getString("accredit_id")
					,rs.getString("busi_id")
					,rs.getString("sequence")
					,rs.getString("other_report_id")
					,rs.getString("other_payoutUnit_id"));
			containers.add(container);
		}
		
		String[] otherReportId;
		String[] otherPayoutUnitId;
		String sqlStr;
		for (Container container2 : containers) {
			otherReportId = container2.getOtherReportId().split(",");
			otherPayoutUnitId = container2.getOtherPayoutUnitId().split(",");
			for (int i = 0; i < otherReportId.length; i++) {
				if("0".equals(otherPayoutUnitId[i]))continue;
				
				sqlStr = "UPDATE busi_fee SET balance_report_id = '"+ otherReportId[i] +
						"' WHERE accredit_id='"+container2.getAccreditId()+
						"' and busi_id='"+container2.getBusiId()+
						"' and sequence="+container2.getSequence()+
						" and payout_to_unit_id="+otherPayoutUnitId[i]+" and state=6 and money<>0  and balance_report_id=''";
				execute(sqlStr);
				System.out.println(sqlStr);
			}
		}
		closeConn(rs);// 关闭连接
	}
}

