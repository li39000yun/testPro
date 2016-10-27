package com.kingsoft.common.sms;

import org.apache.log4j.Logger;

public class RestartProcess implements Runnable {
	private static Logger S_Logger = Logger.getLogger(MASMonitor.class);

	public void run() {
		while (true) {
			try {
				Thread.sleep(1000 * 60 * 10);// 暂停10分钟后执行此线程
				if (MASMonitor.isS_execute()) {
					MASMonitor.cleanUp();
				}
				if (MASMonitor.startUp()) {
					S_Logger.debug("Restart MAS Service Monitor...SUCCESS");
					break;
				} else {
					S_Logger.debug("Restart MAS Service Monitor...FAILD");
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
