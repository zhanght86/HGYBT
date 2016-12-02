package com.sinosoft.midplat.newccb.service;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import cn.ccb.secapi.SecAPI;

import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;
	/**
	 * 代理保险售后提醒查询
	 * @author lizk
	 *
	 */

public class ActInsuSaleRemindAccess extends ServiceImpl {
	
	private String checkDate = "";
	
	public ActInsuSaleRemindAccess(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into ActInsuSaleRemindAccess.service()...");
		cInXmlDoc = pInXmlDoc;
		
		try {
			
			
			
			cTranLogDB = insertTranLog(cInXmlDoc);
			
			String mBagName = cInXmlDoc.getRootElement().getChild("Body").getChildText("BagName");
			
			checkDate = mBagName.substring(10, 18);
			
			
			//本地加密前路徑 
			String localPath = cThisBusiConf.getChildText("LocalDir").replace('\\', '/');
			if (!localPath.endsWith("/")) {
				localPath += '/';}
			
			//建行取文件路徑
			String ccblocalPath = cThisBusiConf.getChildText("ccbLocalDir").replace('\\', '/');
			if (!ccblocalPath.endsWith("/")) {
				ccblocalPath += '/';}
			
			String tTranCode = cThisBusiConf.getChild("funcFlag").getAttributeValue("outcode");
			System.out.println("加密前文件路径：" + localPath);
			System.out.println("交易码：" + tTranCode);
		
			//保险公司节点
			String secNodeId = cInXmlDoc.getRootElement().getChild("Body").getChildTextTrim("secNodeId");
			//银行节点
			String rmtSecNodeId = cInXmlDoc.getRootElement().getChild("Body").getChildTextTrim("rmtSecNodeId");
			
			Element mFileName = new Element("fileName");
			Element mCcblocalPath = new Element("ccblocalPath");
			Element mCount = new Element("Count");
			
			mCcblocalPath.setText(ccblocalPath);
			
			if(mBagName.substring(mBagName.length()-5, mBagName.length()).equals("00001")){
				cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_ActInsuSaleRemindAccess).call(cInXmlDoc);

				cLogger.info("发送核心获取代理保险售后提醒取数报文完成 ");
				JdomUtil.print(cOutXmlDoc);
				Element tOutRootEle = cOutXmlDoc.getRootElement();
				Element tOutHeadEle = tOutRootEle.getChild(Head);
				if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
					throw new MidplatException(tOutHeadEle.getChildText(Desc));
				}else{
					String fileName = mBagName + ".xml";
					String filePath = localPath + fileName;
					System.out.println("文件绝对路径：" + filePath);
					mFileName.setText(fileName);
					
					List<Element> tDetail = cOutXmlDoc.getRootElement().getChild("Body").getChildren("Detail");
					try{
						//组织代理售后提醒取数文件
						isSuccessed(tDetail,localPath);
					}catch(Exception ex){
						throw new MidplatException("组织保单详情文件："+fileName+"出错！"+ex.getMessage());
					}
					
					
					
					Document mInXmlDoc = JdomUtil.build(new FileInputStream(filePath),"UTF-8");
					mCount.setText(mInXmlDoc.getRootElement().getChild("Head").getChildText("Rvl_Rcrd_Num")); 
					
					try{
						//加密：本地节点，对端节点，加密之前文件存放路径， 加密之后文件绝对路径
//						System.out.println("secNodeId ====== "+secNodeId);
//						System.out.println("rmtSecNodeId ====== "+rmtSecNodeId);
//						System.out.println("localPath+fileName ====== "+localPath+fileName);
//						System.out.println("ccblocalPath+fileName ====== "+ccblocalPath+fileName);
//						
						SecAPI.fileEnvelop(secNodeId, rmtSecNodeId, localPath+fileName, ccblocalPath+fileName);
//						
					}catch(Exception ex){
						throw new MidplatException("加密代理保险售后提醒取数文件："+fileName+"出错！");
					}
				}
				
				
			}else{
				String fileName = mBagName + ".xml";
				String filePath = localPath + fileName;
				System.out.println("文件绝对路径：" + filePath);
				mFileName.setText(fileName);
				File mFile = new File(filePath);
				if(!mFile.exists()){
					throw new MidplatException("未找到代理保险售后提醒取数文件："+fileName);
				}
				
				Document mInXmlDoc = JdomUtil.build(new FileInputStream(filePath),"UTF-8");
				mCount.setText(mInXmlDoc.getRootElement().getChild("Head").getChildText("Rvl_Rcrd_Num")); 
				
				try{
					//加密：本地节点，对端节点，加密之前文件存放路径， 加密之后文件绝对路径
//					System.out.println("secNodeId ====== "+secNodeId);
//					System.out.println("rmtSecNodeId ====== "+rmtSecNodeId);
//					System.out.println("localPath+fileName ====== "+localPath+fileName);
//					System.out.println("ccblocalPath+fileName ====== "+ccblocalPath+fileName);
//					
//					SecAPI.fileEnvelop(secNodeId, rmtSecNodeId, localPath+fileName, ccblocalPath+fileName);
				
				}catch(Exception ex){
					throw new MidplatException("加密代理保险售后提醒取数文件："+fileName+"出错！");
				}
				cOutXmlDoc = cInXmlDoc;
				Element mFlag = new Element(Flag);
				Element mDesc = new Element(Desc);
				mFlag.setText("0");
				mDesc.setText("交易成功！");
				cOutXmlDoc.getRootElement().getChild(Head).addContent(mFlag);
				cOutXmlDoc.getRootElement().getChild(Head).addContent(mDesc);
			}
			
			cOutXmlDoc.getRootElement().getChild(Body).addContent(mFileName);
			cOutXmlDoc.getRootElement().getChild(Body).addContent(mCcblocalPath);
			cOutXmlDoc.getRootElement().getChild(Body).addContent(mCount);
			
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"交易失败！", ex);
			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		
		cLogger.info("插入日志："+cTranLogDB);
		if (null != cTranLogDB) {	//插入日志失败时cTranLogDB=null
			cLogger.info("插入日志成功："+cTranLogDB);
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
		cLogger.info("Out ActInsuSaleRemindAccess.service()!");
		return cOutXmlDoc;
	}
	
	
	private void rollback() {
		cLogger.debug("Into ActInsuSaleRemindAccess.rollback()...");
		
		Element mInRootEle = cInXmlDoc.getRootElement();
		Element mInBodyEle = mInRootEle.getChild(Body);
		Element mHeadEle = (Element) mInRootEle.getChild(Head).clone();
		mHeadEle.getChild(ServiceId).setText(AblifeCodeDef.SID_Bank_ContRollback);
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(
				(Element) mInBodyEle.getChild(ProposalPrtNo).clone());
		mBodyEle.addContent(
				(Element) mInBodyEle.getChild(ContPrtNo).clone());
		mBodyEle.addContent(
				(Element) cOutXmlDoc.getRootElement().getChild(Body).getChild(ContNo).clone());
		Element mTranDataEle = new Element(TranData);
		mTranDataEle.addContent(mHeadEle);
		mTranDataEle.addContent(mBodyEle);
		Document mInXmlDoc = new Document(mTranDataEle);
		
		try {
			new CallWebsvcAtomSvc(mHeadEle.getChildText(ServiceId)).call(mInXmlDoc);
		} catch (Exception ex) {
			cLogger.error("回滚数据失败！", ex);
		}
		
		cLogger.debug("Out ActInsuSaleRemindAccess.rollback()!");
	}
	/**
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws MidplatException
	 * create by zhj 2010 11 05
	 * 网点 权限 添加校验方法
	 */
	private Document authority(Document mInXmlDoc) throws MidplatException{
		
  
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
	
private void isSuccessed(List<Element> list,String mLocalPath)  throws MidplatException, IOException
	
	{
		
		String cardType = "";
		String mSsnType = "";
		String mTranType = "";
		String mRiskCode = "";
		String mBonusType = "";
		Element mDetail;
		
		int rowNum = list.size();  //文件明细数
		
		int lastNum = rowNum%5000;
		int fileNum = rowNum/5000; //文件数
		
		if(lastNum != 0 ){
			fileNum = fileNum + 1;
		}
		//创建文件，并且按照顺序，在每个文件中写入5000个保单明细
		for(int i =0;i< fileNum;i++ ){
			int endNum = 0;
			if((i+1)*5000<rowNum){
				endNum = (i+1)*5000;
			}else{
				endNum = rowNum;
			}
			System.out.println("endNum ========="+endNum);
			ElementLis Root = new ElementLis("Root");
			ElementLis Head = new ElementLis("Head",Root);
			new ElementLis("Rvl_Rcrd_Num",String.valueOf(endNum-i*5000),Head);//循环记录条数
			ElementLis Insu_List = new ElementLis("Insu_List",Root);
			
			for(int j=i*5000+1;j<= endNum;j++){
				
				System.out.println("第"+j+"次查询！");
				
				ElementLis Insu_Detail = new ElementLis("Insu_Detail",Insu_List);
				mDetail = list.get(j-1);
				
				new ElementLis("Ins_Co_ID","010073",Insu_Detail);//保险公司编号
				mRiskCode = mDetail.getChildText("RiskCode");
				

				new ElementLis("Cvr_ID",mRiskCode,Insu_Detail);//险种编号
				new ElementLis("InsPolcy_No",mDetail.getChildText("ContNo"),Insu_Detail);//保单号码
			
				
				new ElementLis("AgIns_Rmndr_TpCd",mDetail.getChildText("AlertType"),Insu_Detail);//代理保险提醒类型代码
				
				
			    new ElementLis("Plchd_Nm",mDetail.getChildText("AppntName"),Insu_Detail);//投保人名称
			    
			    mSsnType = mDetail.getChildText("IDType");
			    	if(mSsnType.equals("0")){
						cardType = "1010";//身份证
					}
					if(mSsnType.equals("2")){
						cardType = "1022";//军官证
					}
					if(mSsnType.equals("D")){
						cardType = "1032";//警官证 
					}
					if(mSsnType.equals("A")){
						cardType = "1021";//解放军士兵证
					}
					if(mSsnType.equals("4")){
						cardType = "1040";//户口簿
					}
					if(mSsnType.equals("B")){
						cardType = "1080";//(港澳)回乡证及通行证
					}
					if(mSsnType.equals("1")){
						cardType = "1050";//护照
					}
					if(mSsnType.equals("5")){
						cardType = "1060";//学生证
					}
					if(mSsnType.equals("3")){
						cardType = "1100";//驾照
					}
					if(mSsnType.equals("6")){
						cardType = "1999";//个人其他证件
					}
					if(mSsnType.equals("C")){
						cardType = "1011";//临时居民身份证
					}
					if(mSsnType.equals("E")){
						cardType = "1160";//台湾居民身份证 台胞证
					}
					
				new ElementLis("Plchd_Crdt_TpCd",cardType,Insu_Detail);//投保人证件类型代码
				new ElementLis("Plchd_Crdt_No",mDetail.getChildText("IDNo"),Insu_Detail);//投保人证件号码
			    
			
			 
				new ElementLis("TXN_DT",DateUtil.date10to8(mDetail.getChildText("TranDate")),Insu_Detail);//交易日期
				
				
				new ElementLis("InsPrem_Amt",NumberUtil.yuanToDouble(mDetail.getChildText("Prem")),Insu_Detail);//保费金额
				new ElementLis("CnclIns_Amt",NumberUtil.yuanToDouble(mDetail.getChildText("EdorCTPrem ")),Insu_Detail);//退保金额 

				new ElementLis("Rnew_PyF_Amt",NumberUtil.yuanToDouble(mDetail.getChildText("RecvAmount")),Insu_Detail);//续期缴费金额
				new ElementLis("Rnew_Pbl_Dt",DateUtil.date10to8(mDetail.getChildText("RecvDate")),Insu_Detail);//续期应缴日期

				new ElementLis("InsPolcy_ExDat",DateUtil.date10to8(mDetail.getChildText("InsuEndDate")),Insu_Detail);//保单到期日期
		
				new ElementLis("CrnPrd_XtDvdAmt",NumberUtil.yuanToDouble(mDetail.getChildText("CurrentAmt")),Insu_Detail);//本期红利金额
				new ElementLis("Acm_XtDvdAmt",NumberUtil.yuanToDouble(mDetail.getChildText("BonusAmt")),Insu_Detail);//累积红利金额
				
				/**
				 * 0	直接给付
				 * 1	抵交保费
				 * 2	累计生息
				 * 3	增额交清
				 * 4	转入万能账户
				 */
				
				if("1".equals(mDetail.getChildText("BonusGetMode"))){
					mBonusType = "2";//累计生息
				}
				if("2".equals(mDetail.getChildText("BonusGetMode"))){
					mBonusType = "0";//直接给付
				}
				if("3".equals(mDetail.getChildText("BonusGetMode"))){
					mBonusType = "1";//抵交保费
				}
			
				new ElementLis("XtraDvdn_Pcsg_MtdCd",mBonusType,Insu_Detail);//红利处理方式代码
				
				new ElementLis("Plchd_AccNo",mDetail.getChildText("AccNo "),Insu_Detail);//投保人账号
				new ElementLis("Ins_PD_TpCd",mTranType,Insu_Detail);//保险产品类型代码
				
			}
			
			Document returnStd = new Document(Root);
			cLogger.info("组织的报文=============");
			JdomUtil.print(returnStd);
			//在将数据写入到文件中时，要覆盖原有数据，并且使用UFT-8编码
			FileOutputStream fOut = new FileOutputStream(mLocalPath+ getFileName(i),false);
			JdomUtil.output(returnStd,fOut,"UTF-8");
			fOut.flush();
			fOut.close();
		
		}		
	}

	private String getFileName(int index){
		index = index + 1;
		String indexStr = NumberUtil.fillWith0(index, 5);
	//	String checkdate = DateUtil.date10to8(checkDate);
		String fileName = "P5381B223_"+checkDate+"_010072_"+indexStr+".xml";
		return fileName;
	}
}
