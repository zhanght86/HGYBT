package com.sinosoft.midplat.newabc.bat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;

/**
 * @ClassName: DownloadLargeFile
 * @Description: 测试下载大于10M的文件
 * @author sinosoft
 * @date 2017-4-7 下午12:07:19
 */
public class DownloadLargeFile extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger.getLogger(getClass());

	protected String cResultMsg;
	protected static Element cConfigEle;
	private static String cCurDate = "";
	@SuppressWarnings("unused")
	private static String cCurTime="";

	public void run() {
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into DownloadLargeFile.run()...");
		try {
			cResultMsg = null;
			cConfigEle = BatUtils.getConfigEle("2011"); // 得到bat.xml文件中的对应节点.
			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			}

			cLogger.info("下载大文件开始...");
			String mFIleName = cConfigEle.getChildText("filename").trim();
			if(!new BatUtils().downLoadFile(mFIleName, "02", "2011",cCurDate )){
				throw new MidplatException(cConfigEle.getChildText(name)+"失败！");
			}
			cResultMsg = "下载大文件成功";
		} catch (Exception e) {
			cResultMsg = e.toString();
			cLogger.error(cConfigEle.getChildTextTrim("name") + "  处理异常..."+ e.getMessage());
			e.printStackTrace();
		} finally {
			cCurDate = "";
		}
		
		cLogger.info("下载大文件结束!");
		cLogger.info("Out DownloadLargeFile.run()!");
	}

	
	public final void setDate(String p8DateStr){
		cCurDate = p8DateStr; 
	}
	
	public String getResultMsg() {
		return this.cResultMsg;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.DownloadLargeFile.main");
		mLogger.info("新农行下载大文件程序启动...");

		DownloadLargeFile abcAES = new DownloadLargeFile();

		// 用于补对账，设置补对账日期
		if (0 != args.length) {
			mLogger.info("args[0] = " + args[0]);

			/**
			 * 严格日期校验的正则表达式：\\d{4}-((0\\d)|(1[012]))-(([012]\\d)|(3[01]))。
			 * 4位年-2位月-2位日。 4位年：4位[0-9]的数字。
			 * 1或2位月：单数月为0加[0-9]的数字；双数月必须以1开头，尾数为0、1或2三个数之一。
			 * 1或2位日：以0、1或2开头加[0-9]的数字，或者以3开头加0或1。
			 * 
			 * 简单日期校验的正则表达式：\\d{4}-\\d{2}-\\d{2}。
			 */
			if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))")) {
				cCurDate = args[0];
			} else {
				throw new MidplatException("日期格式有误，应为yyyyMMdd！" + args[0]);
			}
		}
		abcAES.run();
		System.out.println("新农行下载大文件程序完成!");
	}

}
