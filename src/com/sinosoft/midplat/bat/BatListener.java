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
		BatConf.newInstance().setListener(this);	//ע�ᵽ���������û����У��������ú��Զ�������ط��������˿ڼ���
	}
	
	public static void main(String[] args) {
		if (MidplatConf.newInstance().resetLog()) {
			System.out.println("Start reset System.out...");
			System.setOut(new Log4jPrint(System.out));
			System.setErr(new Log4jPrint(System.err));
			System.out.println("����");
			System.out.println("End reset System.out!");
		}
		
		FileCacheManage.newInstance().start();
		
		System.out.println("Into contextInitialized...");//����
		new BatListener().contextInitialized(null);
		System.out.println("Out contextInitialized!");//����
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
				 * �����߳���̳���TimerTask
				 */
				TimerTask ttTimerTask = (TimerTask) Class.forName(ttClassName).newInstance();
				
				//------------�����Զ�����ʱ��begin------------
				Timer ttTimer = new Timer();
				String runType = ttBalanceConfig.getChildText("runType");
				if("01".equals(runType)){
					int ttStartHour = 23;
					int ttStartMinute = 00;
					int ttStartSecond = 00;
					String ttStartTime = ttBalanceConfig.getChildText(startTime);
					System.out.println("������ÿ��"+ttStartTime+"��һ�Σ�");
					String[] ttStartTimes = ttStartTime.split(":");
					try {
						ttStartHour = Integer.parseInt(ttStartTimes[0]);
						ttStartMinute = Integer.parseInt(ttStartTimes[1]);
						ttStartSecond = Integer.parseInt(ttStartTimes[2]);
					} catch (Exception ex) {
						cLogger.warn(i + "_����ʱ����������ϵͳ�Զ�����Ĭ���ã�");
						cLogger.debug(i + "_����ʱ����������", ex);		//debugģʽ�����ȫ�������ջ�������ڲ��Ի�������
					}
					Calendar ttCalendar = Calendar.getInstance();
					ttCalendar.set(Calendar.HOUR_OF_DAY, ttStartHour);
					ttCalendar.set(Calendar.MINUTE, ttStartMinute);
					ttCalendar.set(Calendar.SECOND, ttStartSecond);
					cLogger.info("ÿ�����ܿ�ʼʱ�䣺" + ttStartHour + ":" + ttStartMinute + ":" + ttStartSecond);
					//-----------�����Զ�����ʱ��end-----------
					ttTimer.scheduleAtFixedRate(ttTimerTask, ttCalendar.getTime(), 24*60*60*1000);
				}else if("02".equals(runType)){
					String timePiece = ttBalanceConfig.getChildText("timePiece");
					System.out.println("������ÿ��"+timePiece+"������һ�Σ�");
					int ttimePiece = Integer.parseInt(timePiece);
					Calendar ttCalendar = Calendar.getInstance();
					ttTimer.scheduleAtFixedRate(ttTimerTask, ttCalendar.getTime(), ttimePiece*60*1000);
				}
				
				cTimers[i] = ttTimer;
				cLogger.info(i + "_batch(" + ttBalanceConfig.getChildText(name) + ")���سɹ�!");
			} catch (Exception ex) {
				cLogger.error(i + "_batch����ʧ��!", ex);
			}
		}
		
		cLogger.info("Out BatListener.contextInitialized()!");
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		cLogger.info("Into BatListener.contextDestroyed()...");
		
		cLogger.info("�ر�����Timer...");
		/**
		 * һ��Ҫ�رգ���������ʱ��һ�����������̻߳�����ܣ���ɶ��ִ����ͬ��������Ķ����߳�ͬʱ�ܵ������
		 * �������ͬһ��ͬһ���ж�ζ��ˡ�
		 */
		for (int i = 0; i < cTimers.length; i++) {
			/**
			 * �ڼ���balanceConfig.xml��ĳ��ʧ��ʱ��cTimers�ж�Ӧ��ֵ��Ϊnull���ڴ�����˵���
			 */
			if (null != cTimers[i]) {
				cTimers[i].cancel();
			}
		}
		
		cLogger.info("Out BatListener.contextDestroyed()!");
	}
}
