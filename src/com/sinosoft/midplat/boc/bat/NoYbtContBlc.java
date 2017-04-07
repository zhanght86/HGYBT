package com.sinosoft.midplat.boc.bat;

/**
 * 非银保通出单
 * @author PengYF
 */
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import org.jdom.Element;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.FTPDealBL;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;

public class NoYbtContBlc extends TimerTask implements XmlTag 
{
	
	protected final Logger cLogger = Logger.getLogger(getClass());

	protected static Element cConfigEle;
	private static String cCurDate = "";
	@Override
	public void run() 
	{
		cLogger.info("Into SurrenderDataSyc.run()...");
		cLogger.info("中国银行非银保通出单预登记处理开始...");
		try 
		{
			//1、下载前置机FTP文件到本地
			cConfigEle = BatUtils.getConfigEle("1108");
			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date().getTime());
			}
			//保险公司代码
			String mCorNo = cConfigEle.getChildTextTrim("ComCode");
			//文件名
			String mFIleName = "ALLYC"+mCorNo+"BOC"+cCurDate+".txt";
			cLogger.info("下载文件名为："+mFIleName);
			//==========================下载对账文件开始===========================
			Element ttFtpEle = cConfigEle.getChild("ftp");
			new BocFileDownload().getFtpFile(ttFtpEle,mFIleName,cConfigEle.getChildTextTrim("FilePath"));
			//==========================下载对账文件结束===========================
			String mFilePath = cConfigEle.getChildTextTrim("FilePath")+ mFIleName;
			cLogger.info("本地文件路径："+mFilePath);
			File file =new File(mFilePath);
			if(!file.exists()){
				throw new MidplatException("本地中行非银保通出单预登记处理文件不存在,请确认！");
			}
			//2、上传文件到银行前置机
			String sFtpIP = cConfigEle.getChildText("FtpIP");
			String sFtpPort = cConfigEle.getChildText("FtpPort");
			String sFtpUser = cConfigEle.getChildText("FtpUser");
			String sFtpPass = cConfigEle.getChildText("FtpPass");
			String sFtpFilePath = cConfigEle.getChildText("FtpFilePath");//上传路径
			cLogger.info("提取配置文件ftp数据完成...");
			FTPDealBL tFTPDealBL = new FTPDealBL(sFtpIP, sFtpUser,
					sFtpPass, Integer.valueOf(sFtpPort));
			File mfile = new File(mFilePath);
			cLogger.info("开始上传...");
			if (!tFTPDealBL.ApacheFTPUploadFile(mfile, sFtpFilePath)) {
				cLogger.info("上传有误...");
				throw new MidplatException("FTP上传出错 FileName = " + mFIleName);
			} 
			cLogger.info("上传结束...");
			//3、备份本地对账文件
			Date date = new Date();  
			if ("01".equals(DateUtil.getDateStr(date, "dd"))){
				bakFiles(cConfigEle.getChildTextTrim("FilePath"),date);
			}
		}catch (Exception e) {
			cLogger.error(cConfigEle.getChildText(name) + "交易失败！", e);
			e.printStackTrace();
		}finally {
			cCurDate = "";
		}
		cLogger.info("中国银行非银保通出单预登记处理结束...");
		cLogger.info("Out SurrenderDataSyc.run()...");
	}
	
	
	/*****************备份文件********************/
	public void bakFiles(String pFileDir, final Date date) {
	    System.out.println("Into Balance.bakFiles()...");
	    
	    if ((pFileDir == null) || ("".equals(pFileDir))) {
	      System.out.println("本地文件目录为空，不进行备份操作！");
	      return;
	    }
	    File mDirFile = new File(pFileDir);
	    if ((!mDirFile.exists()) || (!mDirFile.isDirectory())) {
	      System.out.println("本地文件目录不存在，不进行备份操作！" + mDirFile);
	      return;
	    }

	    File[] mOldFiles = mDirFile.listFiles(
	      new FileFilter()
	    {
	      public boolean accept(File pFile)
	      {
	        if (!pFile.isFile()) {
	          return false;
	        }

	        Calendar tCurCalendar = Calendar.getInstance();
	        tCurCalendar.setTime(date);
	        tCurCalendar.set(11, 8);

	        Calendar tFileCalendar = Calendar.getInstance();
	        tFileCalendar.setTimeInMillis(pFile.lastModified());

	        return tFileCalendar.before(tCurCalendar);
	      }
	    });
	    Calendar mCalendar = Calendar.getInstance();
	    mCalendar.add(2, -1);
	    File mNewDir = 
	      new File(mDirFile, DateUtil.getDateStr(mCalendar, "yyyy/yyyyMM"));
	    for (File tFile : mOldFiles) {
	      System.out.println(tFile.getAbsoluteFile() + " start move...");
	      try {
	        IOTrans.fileMove(tFile, mNewDir);
	        System.out.println(tFile.getAbsoluteFile() + " end move!");
	      } catch (IOException ex) {
	        System.out.printf(tFile.getAbsoluteFile() + "备份失败！", ex);
	      }
	    }

	    System.out.println("Out Balance.bakFiles()!");
	  }
	public static void main(String[] args) throws Exception {
			Logger mLogger = Logger
					.getLogger("com.sinosoft.midplat.boc.bat.SurrenderDataSyc.main");
			mLogger.info("中国银行非银保通出单预登记处理程序启动。。。");

			NoYbtContBlc mBatch = new NoYbtContBlc();

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
			mBatch.run();
			System.out.println("中国银行非银保通出单预登记处理程序完成。。。");
		}
}
