package com.sinosoft.midplat.service;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;


public class GreenTest extends ServiceImpl {
	public GreenTest(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into GreenTest.service()...");
		cInXmlDoc = pInXmlDoc;

		Element mRootEle = cInXmlDoc.getRootElement(); 
		Element mHeadEle = (Element) mRootEle.getChild(Head).clone();
		
		long mUsedContConfirm =0;
		try {
			cTranLogDB = insertTranLog(pInXmlDoc);
			long mStartContConfirm = System.currentTimeMillis();
			cLogger.info("Into GreenTest.service()...-->authorityCheck.submitData(mHeadEle)交易权限");	
			mUsedContConfirm = (System.currentTimeMillis() - mStartContConfirm);
			cOutXmlDoc = cInXmlDoc;
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			Element tFlag = new Element(Flag);
			tFlag.setText("0");
			Element tDesc = new Element(Desc);
			tDesc.setText("绿灯测试交易成功！");
			tOutHeadEle.addContent(tFlag);
			tOutHeadEle.addContent(tDesc);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
		} 
		catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"绿灯测试交易失败！", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		} 
		catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"绿灯测试交易失败！", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		} 
		
		if (null != cTranLogDB) {	//插入日志失败时cTranLogDB=null
			Element iHeadEle = cInXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setAgentComName(iHeadEle.getChildText("AgentComName"));
			cTranLogDB.setAgentName(iHeadEle.getChildText("AgentName"));
			cTranLogDB.setAgentCodeGrade(iHeadEle.getChildText("AgentCodeGrade"));
			cTranLogDB.setUnitCode(iHeadEle.getChildText("UnitCode"));
			if(iHeadEle.getChildText("ManageCom") == null || "".equals(iHeadEle.getChildText("ManageCom"))){
				cTranLogDB.setManageCom("86");
			}else{
				cTranLogDB.setManageCom(iHeadEle.getChildText("ManageCom"));
			}
			cTranLogDB.setAgentCom(iHeadEle.getChildText("AgentCom"));
			cTranLogDB.setAgentCode(iHeadEle.getChildText("AgentCode"));
			cTranLogDB.setAgentGrade(iHeadEle.getChildText("AgentGrade"));
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			
			cTranLogDB.setOutDoc(tHeadEle.getChildText("OutDoc"));
			cTranLogDB.setInDoc(tHeadEle.getChildText("InDoc"));
			cTranLogDB.setInNoDoc(iHeadEle.getChildText("InNoDoc"));
			cTranLogDB.setOutNoDoc("");
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			cTranLogDB.setBak3(String.valueOf((tCurMillis-mStartMillis)/1000.0));
			cTranLogDB.setBak4(String.valueOf(mUsedContConfirm/1000.0));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
		cLogger.info("Out GreenTest.service()!");
		return cOutXmlDoc;
	}
	/**
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws MidplatException
	 * create by zhj 2010 11 05
	 * 网点 权限 添加校验方法
	 */
	private Document authority(Document mInXmlDoc) throws MidplatException{
		JdomUtil.print(mInXmlDoc);
  
		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head);
		Element mAgentCom = mHeadEle.getChild("AgentCom");
		Element mAgentCode = mHeadEle.getChild("AgentCode");
		String sNodeNo = (String)mHeadEle.getChildTextTrim("NodeNo");
		String sTranCom =  (String)mHeadEle.getChildTextTrim("TranCom");
		 
		cLogger.info("通过银行,地区,网点号查询代理机构号,并添加！");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='"+sTranCom).append('\'')
			.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);
		String tSqlStr3 = new StringBuilder("select AgentCode from NodeMap where TranCom='"+sTranCom).append('\'')
		.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCode = new ExeSQL().getOneValue(tSqlStr3); 
		cLogger.info("authority-->"+sAgentCom);
		cLogger.info("authority-->"+sAgentCode);   
		if (((""==sAgentCom)||(sAgentCom==null)) && ((""==sAgentCode)||(sAgentCode==null))){ 
			throw new MidplatException("此网点不存在，请确认！");
		}  
		mAgentCom.setText(sAgentCom);
		mAgentCode.setText(sAgentCode);
		return mInXmlDoc;
		
	}
}
