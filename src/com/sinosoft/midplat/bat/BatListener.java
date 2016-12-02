package com.sinosoft.midplat.bat;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.Log4jPrint;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.common.cache.FileCacheManage;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

public class BatListener implements ServletContextListener, XmlTag
{
	private static final Logger cLogger = Logger.getLogger(BatListener.class);

	private Timer[] cTimers = null;

	public BatListener()
	{
		BatConf.newInstance().setListener(this);
	}

	public static void main(String[] args)
	{
		if (MidplatConf.newInstance().resetLog())
		{
			System.out.println("Start reset System.out...");
			System.setOut(new Log4jPrint(System.out));
			System.setErr(new Log4jPrint(System.err));
			System.out.println("End reset System.out!");
		}

		FileCacheManage.newInstance().start();

		new BatListener().contextInitialized(null);
	}

	public void contextInitialized(ServletContextEvent event)
	{
		cLogger.info("Into BatListener.contextInitialized()...");

		Document mBatConfDoc = BatConf.newInstance().getConf();
		List mBatList = mBatConfDoc.getRootElement().getChildren();
		int mSize = mBatList.size();
		this.cTimers = new Timer[mSize];
		for (int i = 0; i < mSize; i++)
		{
			try
			{
				Element ttBalanceConfig = (Element) mBatList.get(i);
				cLogger.info(i + "_batch(" + ttBalanceConfig.getChildText("name") + "): startTime=" + ttBalanceConfig.getChildText("startTime") + "; class=" + ttBalanceConfig.getChildText("class"));
				String ttClassName = ttBalanceConfig.getChildText("class");
				TimerTask ttTimerTask = (TimerTask) Class.forName(ttClassName).newInstance();

				int ttStartHour = 23;
				int ttStartMinute = 0;
				int ttStartSecond = 0;
				String ttStartTime = ttBalanceConfig.getChildText("startTime");
				String[] ttStartTimes = ttStartTime.split(":");
				try
				{
					ttStartHour = Integer.parseInt(ttStartTimes[0]);
					ttStartMinute = Integer.parseInt(ttStartTimes[1]);
					ttStartSecond = Integer.parseInt(ttStartTimes[2]);
				}
				catch (Exception ex)
				{
					cLogger.warn(i + "_����ʱ����������ϵͳ�Զ�����Ĭ���ã�");
					cLogger.debug(i + "_����ʱ����������", ex);
				}
				Calendar ttCalendar = Calendar.getInstance();
				ttCalendar.set(11, ttStartHour);
				ttCalendar.set(12, ttStartMinute);
				ttCalendar.set(13, ttStartSecond);
				cLogger.info("ÿ�����ܿ�ʼʱ�䣺" + ttStartHour + ":" + ttStartMinute + ":" + ttStartSecond);

				Timer ttTimer = new Timer();
				ttTimer.scheduleAtFixedRate(ttTimerTask, ttCalendar.getTime(), 86400000L);
				this.cTimers[i] = ttTimer;
				cLogger.info(i + "_batch(" + ttBalanceConfig.getChildText("name") + ")���سɹ�!");
			}
			catch (Exception ex)
			{
				cLogger.error(i + "_batch����ʧ��!", ex);
			}
		}

		cLogger.info("Out BatListener.contextInitialized()!");
	}

	public void contextDestroyed(ServletContextEvent event)
	{
		cLogger.info("Into BatListener.contextDestroyed()...");

		cLogger.info("�ر�����Timer...");

		for (int i = 0; i < this.cTimers.length; i++)
		{
			if (this.cTimers[i] != null)
			{
				this.cTimers[i].cancel();
			}
		}

		cLogger.info("Out BatListener.contextDestroyed()!");
	}
}