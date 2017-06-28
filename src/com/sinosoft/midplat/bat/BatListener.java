package com.sinosoft.midplat.bat;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.Log4jPrint;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.common.cache.FileCacheManage;

public class BatListener implements ServletContextListener, XmlTag {
	private final static Logger cLogger = Logger.getLogger(BatListener.class);

	private Timer[] cTimers = null;
	
	public BatListener() {
		BatConf.newInstance().setListener(this);	//注册到批处理配置缓存中，调整配置后自动调用相关方法重启端口监听
	}
	
	public static void main(String[] args) {
		if (MidplatConf.newInstance().resetLog()) {
			System.out.println("Start reset System.out...");
			System.setOut(new Log4jPrint(System.out));
			System.setErr(new Log4jPrint(System.err));
			System.out.println("测试");
			System.out.println("End reset System.out!");
		}
		
		FileCacheManage.newInstance().start();
		
		System.out.println("Into contextInitialized...");//测试
		new BatListener().contextInitialized(null);
		System.out.println("Out contextInitialized!");//测试
	} 
	
	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent event) {
		cLogger.info("Into BatListener.contextInitialized()...");

		Document mBatConfDoc = BatConf.newInstance().getConf();
		List<Element> mBatList = mBatConfDoc.getRootElement().getChildren();
		int mSize = mBatList.size();
		cTimers = new Timer[mSize];
		for (int i = 0; i < mSize; i++) {
			try {
				Element ttBalanceConfig = mBatList.get(i);
				cLogger.info(i + "_batch(" + ttBalanceConfig.getChildText("remark")
						+ "): startTime=" + ttBalanceConfig.getChildText(startTime)
						+ "; class=" + ttBalanceConfig.getChildText("class"));
				String ttClassName = ttBalanceConfig.getChildText("class");
				/**
				 * 批跑线程类继承自TimerTask
				 */
				TimerTask ttTimerTask = (TimerTask) Class.forName(ttClassName).newInstance();
				
				//------------设置自动批跑时间begin------------
				Timer ttTimer = new Timer();
				String runType = ttBalanceConfig.getChildText("runType");
				if("01".equals(runType)){
					int ttStartHour = 23;
					int ttStartMinute = 00;
					int ttStartSecond = 00;
					String ttStartTime = ttBalanceConfig.getChildText(startTime);
					System.out.println("该任务每天"+ttStartTime+"跑一次！");
					String[] ttStartTimes = ttStartTime.split(":");
					try {
						ttStartHour = Integer.parseInt(ttStartTimes[0]);
						ttStartMinute = Integer.parseInt(ttStartTimes[1]);
						ttStartSecond = Integer.parseInt(ttStartTimes[2]);
					} catch (Exception ex) {
						cLogger.warn(i + "_批跑时间配置有误，系统自动采用默认置！");
						cLogger.debug(i + "_批跑时间配置有误！", ex);		//debug模式下输出全部错误堆栈，便于在测试环境调试
					}
					Calendar ttCalendar = Calendar.getInstance();
					ttCalendar.set(Calendar.HOUR_OF_DAY, ttStartHour);
					ttCalendar.set(Calendar.MINUTE, ttStartMinute);
					ttCalendar.set(Calendar.SECOND, ttStartSecond);
					cLogger.info("每日批跑开始时间：" + ttStartHour + ":" + ttStartMinute + ":" + ttStartSecond);
					//-----------设置自动批跑时间end-----------
					ttTimer.scheduleAtFixedRate(ttTimerTask, ttCalendar.getTime(), 24*60*60*1000);
				}else if("02".equals(runType)){
					String timePiece = ttBalanceConfig.getChildText("timePiece");
					System.out.println("该任务每隔"+timePiece+"分钟跑一次！");
					int ttimePiece = Integer.parseInt(timePiece);
					Calendar ttCalendar = Calendar.getInstance();
					ttTimer.scheduleAtFixedRate(ttTimerTask, ttCalendar.getTime(), ttimePiece*60*1000);
				}
				
				cTimers[i] = ttTimer;
				cLogger.info(i + "_batch(" + ttBalanceConfig.getChildText(name) + ")加载成功!");
			} catch (Exception ex) {
				cLogger.error(i + "_batch加载失败!", ex);
			}
		}
		
		cLogger.info("Out BatListener.contextInitialized()!");
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		cLogger.info("Into BatListener.contextDestroyed()...");
		
		cLogger.info("关闭批跑Timer...");
		/**
		 * 一定要关闭，否则重启时上一个对账任务线程会继续跑，造成多个执行相同对账任务的对账线程同时跑的情况，
		 * 进而造成同一天同一银行多次对账。
		 */
		for (int i = 0; i < cTimers.length; i++) {
			/**
			 * 在加载balanceConfig.xml中某项失败时，cTimers中对应的值会为null，在此需过滤掉。
			 */
			if (null != cTimers[i]) {
				cTimers[i].cancel();
			}
		}
		
		cLogger.info("Out BatListener.contextDestroyed()!");
	}
}
