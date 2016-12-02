package com.sinosoft.midplat.icbc.bat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.crypto.Cipher;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.*;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.icbc.net.IcbcKeyCache;
import com.sinosoft.midplat.service.Service;

public abstract class Balance extends TimerTask implements XmlTag {
	protected final Logger cLogger = Logger.getLogger(getClass());
	
	private final XmlConf cThisConf;
	private final int cFuncFlag;	//交易代码
	
	/**
	 * 提供一个全局访问点，只在每次对账开始时初始化，
	 * 确保在该次对账处理的整个过程中日期一致性，
	 * 不受跨天(前面的处理在0点前，后面的在0点后)的影响。
	 */
	protected Date cTranDate;
	
	protected String cResultMsg;
	
	/**
	 * 提供一个全局访问点，只在每次对账开始时重新初始化，
	 * 确保在该次对账处理的整个过程中配置一致性，
	 * 不受配置文件自动加载的影响。也就是说，本次定时任务一旦启动，
	 * 其后配置文件的修改将会在下一次批跑时生效，不影响本次。
	 */
	protected Element cMidplatRoot = null;
	protected Element cThisConfRoot = null;
	protected Element cThisBusiConf = null;
	protected String tZoneNo = null;
	protected String tBrNo = null;
	
	public Balance(XmlConf pThisConf, String pFuncFlag) {
		this(pThisConf, Integer.parseInt(pFuncFlag));
	}
	
	public Balance(XmlConf pThisConf, int pFuncFlag) {
		cThisConf = pThisConf;
		cFuncFlag = pFuncFlag;
	}
	
	/**
	 * 为保证对账Timer不会因为某天的一次异常而终止，这里必须捕获run()中的所有异常。
	 */
	
	public void run() {
		cLogger.info("Into Balance.run()...");
		
		//清空上一次结果信息
		cResultMsg = null;
		
		try {
			if (null == cTranDate) {
				cTranDate = new Date();
			}

			cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
			cThisConfRoot = cThisConf.getConf().getRootElement();
			cThisBusiConf = (Element) XPath.selectSingleNode(cThisConfRoot,
					"business[funcFlag='" + cFuncFlag + "']");

			Element tTranData = new Element(TranData);
			Document tInStdXml = new Document(tTranData);

			String ttFileName = getFileName();
			cLogger.info("FileName = " + ttFileName);
			String ttLocalDir = cThisBusiConf.getChildTextTrim("localDir");
			InputStream ttBatIns = null;
			if (null != ttLocalDir && !ttLocalDir.equals("")) { // 如果localDir有配置，优先取本地文件
				ttBatIns = getLocalFile(ttLocalDir, ttFileName);
			} else { // localDir和ftp都未配置，报错
				throw new MidplatException("非实时对账配置有误！");
			}
			//解密文件
			if(ttFileName.endsWith(".des"))
			{
				int x = 0;
				ByteArrayOutputStream ins = new ByteArrayOutputStream();
				while ((x = ttBatIns.read()) != -1) {
					ins.write(x);
				}
				byte[] bPack = ins.toByteArray();
				byte[] byte1 = decode(bPack);
			    cLogger.info("解密前字节流长度:"+bPack.length +" 解密后字节流长度: "+byte1.length);
			    if(byte1.length==0)
			    {
			    	throw new MidplatException("当日银行没有非实时文件明细。");
			    }
				ttBatIns = new ByteArrayInputStream(byte1, 0, byte1.length);
				//定义一个临时的文件输出流。
				FileOutputStream output  = new FileOutputStream(ttLocalDir+ttFileName.substring(0, 22));
				byte[] b = new byte[5];
				int con = 0;
				while((con = ttBatIns.read(b))!=-1)
					{
					output.write(b, 0, con);
					}
				 ttBatIns.close();
				 output.close();
				 ttBatIns=getLocalFile(ttLocalDir, ttFileName.substring(0, 22));
				}
			
			Element tgetHead = getHead(ttBatIns);
			tTranData.addContent(tgetHead);
			if(ttFileName.endsWith(".des"))
			{
			ttBatIns=getLocalFile(ttLocalDir, ttFileName.substring(0, 22));
			}
			else{
				ttBatIns = getLocalFile(ttLocalDir, ttFileName);
			}
			// 获取标准对账报文
			Element ttBodyEle = (Element) parse(ttBatIns);
            tTranData.addContent((Element)ttBodyEle.clone());
//            cLogger.info(JdomUtil.toString(tInStdXml));
			// 调用业务处理，获取标准返回报文
			String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";
			// 若midplat.xml中有非空默认配置，采用该配置
			String tServiceValue = cMidplatRoot.getChildText("defaService");
			if (null != tServiceValue && !"".equals(tServiceValue)) {
				tServiceClassName = tServiceValue;
			}
			// 若子系统的个性化配置文件中有非空默认配置，采用该配置
			tServiceValue = cThisConfRoot.getChildText("service");
			if (null != tServiceValue && !"".equals(tServiceValue)) {
				tServiceClassName = tServiceValue;
			}
			tServiceValue = cThisBusiConf.getChildText("service");
			if (null != tServiceValue && !"".equals(tServiceValue)) {
				tServiceClassName = tServiceValue;
			}
			cLogger.info("业务处理模块：" + tServiceClassName);
			Constructor tServiceConstructor =  Class
					.forName(tServiceClassName).getConstructor(
							new Class[] { Element.class });
			Service tService = (Service)tServiceConstructor
					.newInstance(new Object[] { cThisBusiConf });
			Document tOutStdXml = tService.service(tInStdXml);
		}catch (MidplatException ex) {
				cLogger.info(cThisBusiConf.getChildText(name)+"交易失败！", ex);			
		} catch (Exception ex) {
				cLogger.error(cThisBusiConf.getChildText(name)+"交易失败！", ex);
		}
		
		cTranDate = null;	//每次跑完，清空日期
		cLogger.info("Out Balance.run()!");
	}

	private byte[] decode(byte[] pBytes) throws Exception {
		Cipher mCipher = Cipher.getInstance("DES");
		mCipher.init(Cipher.DECRYPT_MODE, IcbcKeyCache.newInstance().getKey());
		
		return mCipher.doFinal(pBytes);
	}
	protected Element getHead(InputStream tIn) throws Exception {
		cLogger.info("Into Balance.getHead()...");
		
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
		
		Element mTranCom = new Element(TranCom);
		mTranCom.setText("1");
		
		Element mNodeNo = (Element) cThisBusiConf.getChild(NodeNo).clone();
		
		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText(CodeDef.SYS);
		
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(getFileName());
		
		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cThisBusiConf.getChildText(funcFlag));
		
		Element mHead = new Element(Head);
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);
		mHead.addContent(mTranCom);
		mHead.addContent(mNodeNo);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		
		cLogger.info("Out Balance.getHead()!");
		return mHead;
	}
	
	
	private InputStream getLocalFile(String pDir, String pName) throws MidplatException {
		cLogger.info("Into Balance.getLocalFile()...");
		
		pDir = pDir.replace('\\', '/');
		if (!pDir.endsWith("/")) {
			pDir += '/';
		}
		String mPathName = pDir + pName;
		cLogger.info("LocalPath = " + mPathName);
		InputStream mIns = null;
		
		try {
			mIns = new FileInputStream(mPathName);
		} catch (IOException ex) {
			//未找到对账文件
			throw new MidplatException("未找到对账文件！" + mPathName);
		}
		
		cLogger.info("Out Balance.getLocalFile()!");
		return mIns;
	}
	
	@SuppressWarnings("unchecked")
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into IcbcNoRealBlc.parse()...");
		
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		
		BufferedReader mBufReader = new BufferedReader(
				new InputStreamReader(pBatIs, mCharset));
		
		Element mBodyEle = new Element(Body);
		Element mCountEle = new Element(Count);
		mBodyEle.addContent(mCountEle);

		long mSumPrem = 0;
		int mCount = 0;
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			
			//空行，直接跳过
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}
			
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			
			Element tTranComEle = new Element(TranCom);
			tTranComEle.setText(tSubMsgs[0]);
			
			Element tTranDateEle = new Element(TranDate);
			tTranDateEle.setText(tSubMsgs[1]);
			
			Element tZoneNoEle = new Element("ZoneNo");
			tZoneNoEle.setText(tSubMsgs[2]);
			
			Element tNodeNoEle = new Element(NodeNo);
			tNodeNoEle.setText(tSubMsgs[3]);
			
			Element tTellerNoEle = new Element(TellerNo);
			tTellerNoEle.setText(tSubMsgs[4]);
			
			Element tTranNoEle = new Element(TranNo);
			tTranNoEle.setText(tSubMsgs[5]);
			
			Element tProposalPrtNoEle = new Element(ProposalPrtNo);
			tProposalPrtNoEle.setText(tSubMsgs[6]);
			
			Element tSaleChannelEle = new Element("SaleChannel");
			tSaleChannelEle.setText(tSubMsgs[7]);
			
			Element tAppFlagEle = new Element("AppFlag");
			tAppFlagEle.setText(tSubMsgs[8]);
			
			Element tAppntNameEle = new Element("AppntName");
			tAppntNameEle.setText(tSubMsgs[9]);
			
			Element tAppntIDTypeEle = new Element("AppntIDType");
			tAppntIDTypeEle.setText(tSubMsgs[10]);
			
			Element tAppntIDNoEle = new Element("AppntIDNo");
			tAppntIDNoEle.setText(tSubMsgs[11]);
			
			Element tAccNoEle = new Element("AccNo");
			tAccNoEle.setText(tSubMsgs[12]);
			
			
			
			Element tDetailEle = new Element(Detail);
			tDetailEle.addContent(tTranComEle);
			tDetailEle.addContent(tTranDateEle);
			tDetailEle.addContent(tZoneNoEle);
			tDetailEle.addContent(tNodeNoEle);
			tDetailEle.addContent(tTellerNoEle);
			
			tDetailEle.addContent(tTranNoEle);
			tDetailEle.addContent(tProposalPrtNoEle);
			tDetailEle.addContent(tSaleChannelEle);
			tDetailEle.addContent(tAppFlagEle);
			tDetailEle.addContent(tAppntNameEle);
			
			tDetailEle.addContent(tAppntIDTypeEle);
			tDetailEle.addContent(tAppntIDNoEle);
			tDetailEle.addContent(tAccNoEle);
			
			mBodyEle.addContent(tDetailEle);
			 
			mCount++; 
		} 
		mCountEle.setText(String.valueOf(mCount));

		mBufReader.close();	//关闭流 
		
		cLogger.info("Out IcbcNoRealBlc.parse()!");
		return mBodyEle;
	}
	public final void setDate(Date pDate) {
		cTranDate = pDate;
	}
	
	public final void setDate(String p8DateStr) {
		cTranDate = DateUtil.parseDate(p8DateStr, "yyyyMMdd");
	}
	
	/**
	 * 根据对账文件命名规则，生成当天对账文件名
	 */
	protected abstract String getFileName();
	
	/**
	 * 该方法会在调用完后台服务之后被调用。
	 * 默认是空实现，在一些特殊交易中可以用来做一些个性化的额外后续处理，
	 * 常见的情况：工行非实时核保对账，在调用完后台后，进行文件上传动作，即可在此方法中实现。
	 * @param Document pOutStdXml: 后台返回的标准报文。
	 */
	protected void ending(Document pOutStdXml) throws Exception {
		cLogger.info("Into Balance.ending()...");
		
		cLogger.info("do nothing, just out!");
		
		cLogger.info("Out Balance.ending()!");
	}
	
	public String getResultMsg() {
		return cResultMsg;
	}
}
