
package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.midplat.service.Service;


/**
 * ��ʵʱ��������ļ�-���д���������
 * @author liuzk
 *
 */
public class NonReaTimeIssResDocBankDeal0323 extends Balance{
	public NonReaTimeIssResDocBankDeal0323() {
		super(NewAbcConf.newInstance(), "2007");
	}
	protected String getFileName()throws Exception {
		Element mBankEle = cThisConfRoot.getChild("bank");
		File_download f = new File_download(cThisBusiConf,"FSSCDHP",DateUtil.getDateStr(cTranDate,"yyyyMMdd"),mBankEle.getAttributeValue("insu"));
		String fileName="FRESULT.BANK"+mBankEle.getAttributeValue("insu")+"."+DateUtil.getDateStr(cTranDate, "yyyyMMdd");
		try {
			f.bank_dz_file();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new MidplatException(ex.getMessage());
		} 
		return fileName;
	}
	
	 public void run()
	   {
	        Thread.currentThread().setName(
	          String.valueOf(NoFactory.nextTranLogNo()));
	        this.cLogger.info("Into NonReaTimeIssResDocBankDeal.run()...");
	 
	        this.cResultMsg = null;
	     try
	     {
	         this.cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
	         this.cThisConfRoot = this.cThisConf.getConf().getRootElement();
	         this.cThisBusiConf = ((Element)XPath.selectSingleNode(
	           this.cThisConfRoot, "business[funcFlag='" + this.cFuncFlag + "']"));
	 
	         String nextDate = this.cThisBusiConf.getChildText("nextDate");
	 
	         if (this.cTranDate == null)
	           if ((nextDate != null) && ("Y".equals(nextDate))) {
	             this.cTranDate = new Date();
	             this.cTranDate = new Date(this.cTranDate.getTime() - 86400000L);
	      } else {
	             this.cTranDate = new Date();
	      }
	         Element tTranData = new Element("TranData");
	         Document tInStdXml = new Document(tTranData);
	 
	         Element tHeadEle = getHead();
	         tTranData.addContent(tHeadEle);
	    try
	    {
	           String ttFileName = getFileName();
	           this.cLogger.info("FileName = " + ttFileName);
	           String ttLocalDir = this.cThisBusiConf.getChildTextTrim("localDir");
	           this.cLogger.info("localDir = "+ttLocalDir);
	           InputStream ttBatIns = null;
	           
	           ttBatIns = new FileInputStream(ttLocalDir+ttFileName);
	           Element ttBodyEle = parse(ttBatIns);
	           tTranData.addContent(ttBodyEle);
	    } catch (Exception ex) {
	           this.cLogger.error("���ɱ�׼���˱��ĳ���!", ex);
	 
	           Element ttError = new Element("Error");
	           String ttErrorStr = ex.getMessage();
	           if ("".equals(ttErrorStr)) {
	             ttErrorStr = ex.toString();
	      }
	           ttError.setText(ttErrorStr);
	           tTranData.addContent(ttError);
	    }
	 
	         String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";
	 
	         String tServiceValue = this.cMidplatRoot.getChildText("service");
	         if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
	           tServiceClassName = tServiceValue;
	    }
	 
	         tServiceValue = this.cThisConfRoot.getChildText("service");
	         if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
	           tServiceClassName = tServiceValue;
	    }
	         tServiceValue = this.cThisBusiConf.getChildText("service");
	         if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
	           tServiceClassName = tServiceValue;
	    }
	         this.cLogger.info("ҵ����ģ��" + tServiceClassName);
	         Constructor tServiceConstructor = Class.forName(
	           tServiceClassName).getConstructor(new Class[] { Element.class });
	         Service tService = (Service)tServiceConstructor.newInstance(new Object[] { this.cThisBusiConf });
	         Document tOutStdXml = tService.service(tInStdXml);
	 
	         this.cResultMsg = tOutStdXml.getRootElement().getChild(
	           "Head").getChildText("Desc");
	 
	  }
	  catch (Throwable ex) {
	         this.cLogger.error("���׳���", ex);
	         this.cResultMsg = ex.toString();
	  }
	 
	       this.cTranDate = null;
	 
	       this.cLogger.info("Out NonReaTimeIssResDocBankDeal.run()!");
	   }
	
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into NonReaTimeIssResDocBankDeal.parse()...");
		
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		// ��ʽ�����չ�˾����|���д���|�ܼ�¼��|�ܽ��|
		//�ļ��������ݣ�����ϸ��¼��
		// ���չ�˾����|��������|��������˳���|Ͷ��������|Ͷ����֤������|Ͷ����֤������|���ֱ���|��Ʒ����|������|�б�����|
		// Ͷ�����˹�ϵ|����������|������֤������|������֤������|����|����|�ɷ��˻�|�ɷѷ�ʽ|�ɷ�����|����������|Ͷ������|
		// ���Ի�����|����ӡˢ��|������|������Ϣ|��ע1|��ע2|
		BufferedReader mBufReader = new BufferedReader(
				new InputStreamReader(pBatIs, mCharset));
		
		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		Element mCountEle = new Element(Count);
		mCountEle.setText(mSubMsgs[2].trim());
		Element mSumPremEle = new Element(Prem);
		mSumPremEle.setText(String.valueOf(NumberUtil.yuanToFen(mSubMsgs[3].trim())));
		
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mSumPremEle);
		
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			
			//���У�ֱ������
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("���У�ֱ��������������һ����");
				continue;
			}
			
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
//			
//			if (!"01".equals(tSubMsgs[10])) {
//				cLogger.warn("�ǳб�������ֱ��������������һ����");
//				continue;
//			}
//			if (!("01".equals(tSubMsgs[11])&&("0000".equals(tSubMsgs[12])))) {
//				cLogger.warn("�б������������򳷵��ĵ��ӣ���ֱ��������������һ����");
//				continue;
//			}
			
			/*
			 * ũ�е�ʵʱ�ĵ�������4λ�ģ�����ʡ�д���+2λ�����룩�����˵ĵ�������2λ�ģ����Զ��˵ĵ����뻹Ҫƴ��ʡ�����д��벿��
			 *    
			 * ������ʱ���ũ�е���Աȷ�ϵģ�20130403
			 * 
			 * ��������|���н�����ˮ��|����ʡ�д���|�������|������|���׽��|��������|����״̬
			 */
//			String nodeNo=null;
//			if(tSubMsgs[9]!=null&&tSubMsgs[10]!=null){
//				nodeNo=tSubMsgs[9].trim()+tSubMsgs[10].trim();
//			}
			
			Element tTranDateEle = new Element(TranDate);
			tTranDateEle.setText(tSubMsgs[1]);
//			
//			Element tTranNoEle = new Element(TranNo);
//			tTranNoEle.setText(tSubMsgs[1]);
//			
//			Element tProposalPrtNoEle = new Element(ProposalPrtNo);
//			tProposalPrtNoEle.setText(tSubMsgs[11]);
			
			//��������˳��Ÿ���contblcdtl����cont�ֶ�
			Element tContNoEle = new Element(ContNo);
			tContNoEle.setText(tSubMsgs[8]);
			
			Element tApplyNo = new Element("ApplyNo");
			tApplyNo.setText(tSubMsgs[2]);
			
//			Element tAgentCom=new Element(AgentCom);
//			tAgentCom.setText(nodeNo);
			
			Element tPremEle = new Element(Prem);
			long tPremFen = NumberUtil.yuanToFen(tSubMsgs[14]);
			tPremEle.setText(String.valueOf(tPremFen));
			
			/*Element tContTypeEle = new Element("ContType");
			if (!(tSubMsgs[8].trim()).endsWith("88")) {
				tContTypeEle.setText(String.valueOf(HxlifeCodeDef.ContType_Group));
			} else {
				tContTypeEle.setText(String.valueOf(HxlifeCodeDef.ContType_Bank));
			}*/
			
			Element tDetailEle = new Element(Detail);
//			tDetailEle.addContent(tTranDateEle);
//			tDetailEle.addContent(tAgentCom);
//			tDetailEle.addContent(tTranNoEle);
//			tDetailEle.addContent(tProposalPrtNoEle);
			tDetailEle.addContent(tContNoEle);
			tDetailEle.addContent(tApplyNo);
			tDetailEle.addContent(tPremEle);
			
			mBodyEle.addContent(tDetailEle);
		}
		mBufReader.close();	//�ر���
		
		cLogger.info("Out NonReaTimeIssResDocBankDeal.parse()!");
		return mBodyEle;
	}
	
	protected Element getHead() {
		cLogger.info("Into NonReaTimeIssResDocBankDeal.getHead()...");
		String tBalanceFlag = "0";
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		String mCurrDate = DateUtil.getCurDate("yyyyMMdd");
		cLogger.info(" ��������Ϊ..."+DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		cLogger.info(" ��ǰ����Ϊ..."+mCurrDate);
		
		// ���ֹ����ˣ���tBalanceFlag��־��Ϊ1 �����ն�����Ϊ0 modify by liuq 2010-11-11
		if(!DateUtil.getDateStr(cTranDate, "yyyyMMdd").equals(mCurrDate)){
			tBalanceFlag = "1";
		}
		
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
		
		Element mTranCom = new Element(TranCom);
		mTranCom.setText(cThisConfRoot.getChildText("TranCom"));
		String tTempStr = cThisConfRoot.getChild("TranCom").getAttributeValue(outcode);
		if (null!=tTempStr && !"".equals(tTempStr)) {
			mTranCom.setAttribute(outcode, tTempStr);
		}
		
		Element mZoneNo = new Element("ZoneNo");
		mZoneNo.setText(cThisBusiConf.getChildText("zone"));
		
		Element mNodeNo = new Element(NodeNo);
		mNodeNo.setText(cThisBusiConf.getChildText("node"));
		
		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText("sys");
		
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(Thread.currentThread().getName());
		
		Element mFuncFlag = new Element(FuncFlag);

		tTempStr = cThisBusiConf.getChild(funcFlag).getAttributeValue(outcode);
		mFuncFlag.setText(tTempStr);
		
		Element mBalanceFlag = new Element("BalanceFlag");
		mBalanceFlag.setText(tBalanceFlag);
		
		//����ͷ������Ӻ��ĵ����б���
		Element mBankCode = new Element("BankCode");
		mBankCode.setText("0102");
		
		Element mHead = new Element(Head);
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);
		
		//����ͷ������Ӻ��ĵ����б���
		mHead.addContent(mBankCode);
		
		mHead.addContent(mTranCom);
		mHead.addContent(mZoneNo);
		mHead.addContent(mNodeNo);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		mHead.addContent(mBalanceFlag);

		cLogger.info("Out NonReaTimeIssResDocBankDeal.getHead()!");
		return mHead;
	}
	
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.Abc.bat.NonReaTimeIssResDocBankDeal.main");
		mLogger.info("����ʼ...");
		
		NonReaTimeIssResDocBankDeal0323 mBatch = new NonReaTimeIssResDocBankDeal0323();
		//���ڲ����ˣ����ò���������
		if (0 != args.length) {
			mLogger.info("args[0] = " + args[0]);
			
			/**
			 * �ϸ�����У���������ʽ��\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))��
			 * 4λ��-2λ��-2λ�ա�
			 * 4λ�꣺4λ[0-9]�����֡�
			 * 1��2λ�£�������Ϊ0��[0-9]�����֣�˫���±�����1��ͷ��β��Ϊ0��1��2������֮һ��
			 * 1��2λ�գ���0��1��2��ͷ��[0-9]�����֣�������3��ͷ��0��1��
			 * 
			 * ������У���������ʽ��\\d{4}\\d{2}\\d{2}��
			 */
			if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))")) {
				mBatch.setDate(args[0]);
			} else {
				throw new MidplatException("���ڸ�ʽ����ӦΪyyyyMMdd��" + args[0]);
			}
		}
		
		mBatch.run();
		
		mLogger.info("�ɹ�������");
	}
}
