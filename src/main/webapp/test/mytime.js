lyq.timeUtils = {
	// 比较两个日期的查，返回相差天数
	compareDay : function(eDate, sDate) {
		var sArr = sDate.split("-");
		var eArr = eDate.split("-");
		var sRDate = new Date(sArr[0], sArr[1], sArr[2]);
		var eRDate = new Date(eArr[0], eArr[1], eArr[2]);
		return (eRDate - sRDate) / (24 * 60 * 60 * 1000);
	},
	// 获取当前日期,格式：YYYY-MM-DD
	getNowFormatDate : function() {
		var day = new Date();
		var Year = 0;
		var Month = 0;
		var Day = 0;
		var CurrentDate = "";
		// 初始化时间
		// Year= day.getYear();//有火狐下2008年显示108的bug
		Year = day.getFullYear();// ie火狐下都可以
		Month = day.getMonth() + 1;
		Day = day.getDate();
		// Hour = day.getHours();
		// Minute = day.getMinutes();
		// Second = day.getSeconds();
		CurrentDate += Year + "-";
		if (Month >= 10) {
			CurrentDate += Month + "-";
		} else {
			CurrentDate += "0" + Month + "-";
		}
		if (Day >= 10) {
			CurrentDate += Day;
		} else {
			CurrentDate += "0" + Day;
		}
		return CurrentDate;
	}
};