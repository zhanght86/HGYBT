package com.sinosoft.midplat.newabc.bat;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
/*
 * 证书下载
 * 
 */

public class CertificateDownload extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger.getLogger(getClass());
	
	protected String cResultMsg;
	protected Element cConfigEle;
	private static String cCurDate = "";
	@SuppressWarnings("unused")
	private TranLogDB cTranLogDB;
	
	public void run() {
		cLogger.info("Into CertificateDownload.run()...");
		
		try {
			cResultMsg = null;
			cTranLogDB = insertTranLog();
			cConfigEle = BatUtils.getConfigEle("1031");	
			if("".equals(cCurDate)){
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date()).replace("-","");
			}
			
			String mFIleName = "cacert.crt" ;
			
			//得到下载文件内容
			if(!new BatUtils().downLoadFile(mFIleName, "01","1031",cCurDate)){
				throw new MidplatException("证书下载异常");
			}
			 
			cResultMsg = "证书下载成功!";
		} catch (Exception e) {
			cResultMsg = e.toString();
			cLogger.info(cConfigEle.getChildTextTrim("name")+"  对账处理异常..."+e.getMessage());
			e.printStackTrace();
		}
		
		cLogger.info("Out CertificateDownload.run()!");
	}
	
	protected TranLogDB insertTranLog() throws MidplatException {
	    this.cLogger.debug("Into CertificateDownload.insertTranLog()...");

	    TranLogDB mTranLogDB = new TranLogDB();
	    mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
	    mTranLogDB.setTranCom("05");
	    mTranLogDB.setNodeNo("0000000");
	    mTranLogDB.setFuncFlag("1031");
	    mTranLogDB.setOperator("YBT");
	    mTranLogDB.setTranNo(NoFactory.nextTranLogNo()+"");
	    mTranLogDB.setTranDate(DateUtil.getCur8Date());
	    mTranLogDB.setTranTime(DateUtil.getCur6Time());
	    Date mCurDate = new Date();
	    mTranLogDB.setTranTime(DateUtil.get6Time(mCurDate));
	    mTranLogDB.setRCode(0);
	    mTranLogDB.setUsedTime(0);
	    mTranLogDB.setBak1("");
	    mTranLogDB.setRCode("-1");
	    mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
	    mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
	    mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
	    mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
	    if (!(mTranLogDB.insert())) {
	    	this.cLogger.error(mTranLogDB.mErrors.getFirstError());
	    	throw new MidplatException("插入日志失败！");
	    }
	    
	    this.cLogger.debug("Out CertificateDownload.insertTranLog()!");
	    return mTranLogDB;
	 }
	
	public final void setDate(String p8DateStr){
		cCurDate = p8DateStr; 
	}
	
	public String getResultMsg() {
		return this.cResultMsg;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.CertificateDownload.main");
		mLogger.info("新农行证书下载程序启动...");
		
		CertificateDownload abcAES = new CertificateDownload();
		
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
		mLogger.info("新农行证书下载程序完成!");
	}
	
}
