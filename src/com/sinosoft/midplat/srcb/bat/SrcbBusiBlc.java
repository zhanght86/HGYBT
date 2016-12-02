package com.sinosoft.midplat.srcb.bat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.bat.GetSFTPConnection;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.service.Service;
import com.sinosoft.midplat.srcb.SrcbConf;

import com.sinosoft.utility.ExeSQL;

public class SrcbBusiBlc extends Balance{

	public SrcbBusiBlc() {
		super(SrcbConf.newInstance(), "6005");
	}

	
	@Override
	protected String getFileName() {
		// TODO Auto-generated method stub
		Element mBankEle = cThisConfRoot.getChild("bank");
		return  "SRCB"+"_" +mBankEle.getAttributeValue("insu")+"_"+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+".txt";
	}

	   public void run()
	   {
	        Thread.currentThread().setName(
	          String.valueOf(NoFactory.nextTranLogNo()));
	        this.cLogger.info("Into SrcbBusiBlc.run()...");
	 
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
//			   Element mBankEle = cThisConfRoot.getChild("bank");
//	           String ttFileName = "SRCB"+mBankEle.getAttributeValue("insu")+"."+DateUtil.getDateStr(cTranDate, "yyyyMMdd");
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
	 
	       this.cLogger.info("Out SrcbBusiBlc.run()!");
	   }
	
	protected Element parse(InputStream pBatIs) throws Exception {
		
		cLogger.info("Into SrcbBusiBlc.parse()...");
		
		int sumCount=0;
		long sumPrem=0;
		
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		
		// �ļ�ÿ���ֶ�ֵ�԰�����ߡ�|���ָ���ÿ��ĩβҲ�ԡ�|��Ϊ������ʶ
		// �׼�¼�����ڣ�yyyymmdd��|�ܱ���|�ܽ���ԪΪ��λ��2λС����|
		// ��ϸ��¼�����ڣ�yyyymmdd��|ǩ��������ˮ��|������|����ԪΪ��λ��2λС����|
		
		System.out.println(pBatIs); 
		BufferedReader mBufReader = new BufferedReader(
				new InputStreamReader(pBatIs, mCharset));
		
		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		//�ѳɹ��ļ�¼��ȡ������������
		Element mCountEle = new Element(Count);
		mCountEle.setText(mSubMsgs[1].trim());
		Element mSumPremEle = new Element(Prem);
		mSumPremEle.setText(String.valueOf(NumberUtil.yuanToFen(mSubMsgs[2].trim())));
		
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mSumPremEle);
		
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
//			cLogger.info("���Ǹ�ô������"+tLineMsg);
			
			//���У�ֱ������
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("���У�ֱ��������������һ����");
				continue;
			}
			
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			
			//�Ϻ�ũ�����ж����ļ�����û���������ģ����ĵ�Ҫ����ÿ�������г�������
			String nodeNo=null;
			if(tSubMsgs[2]!=null&&tSubMsgs[3]!=null){
				nodeNo=tSubMsgs[2].trim()+tSubMsgs[3].trim();
			}
			String getNodeNo = new StringBuilder("select nodeno from cont where contno = '").append(tSubMsgs[2]).append("'")
			.append(" and state='2'").toString();
			nodeNo = new ExeSQL().getOneValue(getNodeNo);
			
			Element tTranDateEle = new Element(TranDate);
			tTranDateEle.setText(tSubMsgs[0]);
			
			Element tTranNoEle = new Element(TranNo);
			tTranNoEle.setText(tSubMsgs[1]);
			
			Element tContNoEle = new Element(ContNo);
			tContNoEle.setText(tSubMsgs[2]);
			
			Element tAgentCom=new Element(AgentCom);
			tAgentCom.setText(nodeNo);
			
			Element tPremEle = new Element(Prem);
			long tPremFen = NumberUtil.yuanToFen(tSubMsgs[3]);
			tPremEle.setText(String.valueOf(tPremFen));
			
			Element tDetailEle = new Element(Detail);
			tDetailEle.addContent(tTranDateEle);
			tDetailEle.addContent(tAgentCom);
			tDetailEle.addContent(tTranNoEle);
			tDetailEle.addContent(tContNoEle);
			tDetailEle.addContent(tPremEle);
			
			mBodyEle.addContent(tDetailEle);
		}
		mBufReader.close();	//�ر���n(mSumPrem)+"������������" + String.valueOf(mCount));
	
	cLogger.info("Out SrcbBusiBlc.parse()!");
	return mBodyEle;
}


protected Element getHead() {
	cLogger.info("Into SrcbBusiBlc.getHead()...");
	
	Element mTranDate = new Element(TranDate);
	mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
	
	Element mTranTime = new Element(TranTime);
	mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
	
	Element mTranCom = (Element) cThisConfRoot.getChild(TranCom).clone();
	
	Element mZoneNo = (Element) cThisBusiConf.getChild(ZoneNo).clone();
	
	Element mNodeNo = (Element) cThisBusiConf.getChild(NodeNo).clone();
	
	Element mTellerNo = new Element(TellerNo);
	mTellerNo.setText(CodeDef.SYS);
	
	Element mTranNo = new Element(TranNo);
	mTranNo.setText(getFileName());
	
	Element mFuncFlag = new Element(FuncFlag);
	mFuncFlag.setText(cThisBusiConf.getChildText(funcFlag));
	
	Element mTranLogNo = new Element("TranLogNo");
	mTranLogNo.setText(Thread.currentThread().getName());
	
	//����ͷ������Ӻ��ĵ����б���
	Element mBankCode = new Element("BankCode");
	mBankCode.setText("Srcb");
	
	Element mHead = new Element(Head);
	mHead.addContent(mTranDate);
	mHead.addContent(mTranTime);
	mHead.addContent(mTranCom);
	mHead.addContent(mZoneNo);
	mHead.addContent(mNodeNo);
	mHead.addContent(mTellerNo);
	mHead.addContent(mTranNo);
	mHead.addContent(mFuncFlag);
	mHead.addContent(mTranLogNo);
	//����ͷ������Ӻ��ĵ����б���
	mHead.addContent(mBankCode);
	
	cLogger.info("Out SrcbBusiBlc.getHead()!");
	return mHead;
}
public static void main(String[] args) throws Exception {
	Logger mLogger = Logger.getLogger("com.sinosoft.midplat.Srcb.bat.SrcbBusiBlc.main");
	mLogger.info("����ʼ...");
	
	SrcbBusiBlc mBatch = new SrcbBusiBlc();
	mBatch.run();
//	File file = new File("D://cest//down//DAYCHECKNBYH100020140812.xml");
//	InputStream in  = new FileInputStream(file);
//	Element mBodyEle = mBatch.parse(in);
//	JdomUtil.print(mBodyEle);
	mLogger.info("�ɹ�������");
}

}
