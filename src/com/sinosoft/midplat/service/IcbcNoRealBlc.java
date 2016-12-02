package com.sinosoft.midplat.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.NoRealBlcDtlSchema;
import com.sinosoft.lis.vschema.NoRealBlcDtlSet;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.format.Format;
import com.sinosoft.midplat.icbc.bat.CreateTxtFile;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

public class IcbcNoRealBlc extends ServiceImpl {
	
	public IcbcNoRealBlc(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into IcbcNoRealBlc.service()...");
		cInXmlDoc = pInXmlDoc; 		
		
		try {
			
			cTranLogDB = insertTranLog(cInXmlDoc);
			
			String tSqlStr = new StringBuilder("select 1 from TranLog where RCode=").append(CodeDef.RCode_OK)
				.append(" and TranDate=").append(cTranLogDB.getTranDate())
				.append(" and FuncFlag=").append(cTranLogDB.getFuncFlag())
				.append(" and TranCom=").append(cTranLogDB.getTranCom())
				.append(" and NodeNo='").append(cTranLogDB.getNodeNo()).append('\'')
				.toString();
			ExeSQL tExeSQL = new ExeSQL();
			if ("1".equals(tExeSQL.getOneValue(tSqlStr))) {
				throw new MidplatException("�ѳɹ�������ʵʱ�˱����˽��ף������ظ�������");
			} else if (tExeSQL.mErrors.needDealError()) {
				throw new MidplatException("��ѯ��ʷ������Ϣ�쳣��");
			} 
			
			//����ǰ�û��������ı�����Ϣ(ɨ�賬ʱ��)
			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
			if (null != tErrorStr) {
				throw new MidplatException(tErrorStr);
			}
			
			//���������ϸ
			saveDetails(cInXmlDoc);
			
			//�˴�����һ����ʽ����ش����ͺ�����Mapping ----start
			String tFormatClassName = cThisBusiConf.getChildText("format");
			this.cLogger.info("����ת��ģ�飺" + tFormatClassName);
			Constructor tFormatConstructor = Class.forName(
			        tFormatClassName).getConstructor(new Class[] { Element.class });
			Format tFormat = (Format)tFormatConstructor.newInstance(new Object[] { cThisBusiConf });
			cInXmlDoc = tFormat.noStd2Std(cInXmlDoc);
			
			
			/**
			 * ת���������  ----end
			 */
			
			cOutXmlDoc = new CallWebsvcAtomSvc("20").call(cInXmlDoc);
			
//			String mInFilePathName = "D:/YBT_SAVE_XML/ZHH/��ʵʱ/�к���ʵʱ���󷵻�ģ�ⱨ��.xml";
//			InputStream mIs = new FileInputStream(mInFilePathName);
//			byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
//			Document XmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
//			cOutXmlDoc = XmlDoc;	
			
			
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//����ʧ��
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
		} 
		catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		} 
		catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		
		if (null != cTranLogDB) {	//������־ʧ��ʱcTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));	//-1-δ���أ�0-���׳ɹ������أ�1-����ʧ�ܣ�����
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
		cLogger.info("Out IcbcNoRealBlc.service()!");
		return cOutXmlDoc;
	}
	
	/** 
	 * ���������ϸ��(ICBCNOREALBLCDTL)
	 */
	@SuppressWarnings("unchecked")
	protected void saveDetails(Document pXmlDoc) throws Exception {
		cLogger.debug("Into IcbcNoRealBlc.saveDetails()...");
		
		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mBodyEle = mTranDataEle.getChild(Body);
		
		List<Element> mDetailList = mBodyEle.getChildren(Detail);
		NoRealBlcDtlSet mICBCNoRealBlcDtlSetSet = new NoRealBlcDtlSet();
		for (Element tDetailEle : mDetailList) {
			
			NoRealBlcDtlSchema tContBlcDtlSchema = new NoRealBlcDtlSchema();
			tContBlcDtlSchema.setBlcNo(PubFun1.CreateMaxNo("NOREALBLC", 20));
			tContBlcDtlSchema.setTranCom("01");
			tContBlcDtlSchema.setTranDate(tDetailEle.getChildText(TranDate));
			tContBlcDtlSchema.setZoneNo(tDetailEle.getChildText(ZoneNo));
			tContBlcDtlSchema.setNodeNo(tDetailEle.getChildText(NodeNo));
			tContBlcDtlSchema.setTellerNo(tDetailEle.getChildText(TellerNo));
			tContBlcDtlSchema.setBankTranNo(tDetailEle.getChildText(TranNo));
			tContBlcDtlSchema.setProposalPrtNo(tDetailEle.getChildText(ProposalPrtNo));
			tContBlcDtlSchema.setSaleChannel(tDetailEle.getChildText("SaleChannel"));
			tContBlcDtlSchema.setAppFlag(tDetailEle.getChildText("AppFlag"));
			tContBlcDtlSchema.setAplName(tDetailEle.getChildText("AppntName"));
			tContBlcDtlSchema.setAplIDType(tDetailEle.getChildText("AppntIDType"));
			tContBlcDtlSchema.setAplIDNo(tDetailEle.getChildText("AppntIDNo"));
			tContBlcDtlSchema.setAplAccNo(tDetailEle.getChildText("AccNo"));
			
			tContBlcDtlSchema.setMakeDate(DateUtil.getCur8Date());
			tContBlcDtlSchema.setMakeTime(DateUtil.getCur6Time());
			tContBlcDtlSchema.setModifyDate(DateUtil.getCur8Date());
			tContBlcDtlSchema.setModifyTime(DateUtil.getCur6Time());

			mICBCNoRealBlcDtlSetSet.add(tContBlcDtlSchema);
		}
		
		/** 
		 * �����з������ķ�ʵʱ������ϸ�洢����ʵʱ������ϸ��(ICBCNOREALBLCDTL)��
		 */
		cLogger.info("������ϸ����(DtlSet)Ϊ��" + mICBCNoRealBlcDtlSetSet.size());
		MMap mSubmitMMap = new MMap();
		mSubmitMMap.put(mICBCNoRealBlcDtlSetSet, "INSERT");
		VData mSubmitVData = new VData();
		mSubmitVData.add(mSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, "")) {
			cLogger.error("���������ϸʧ�ܣ�" + mPubSubmit.mErrors.getFirstError());
			throw new MidplatException("���������ϸʧ�ܣ�");
		}
		
		cLogger.debug("Out IcbcNoRealBlc.saveDetails()!");
	}
}
