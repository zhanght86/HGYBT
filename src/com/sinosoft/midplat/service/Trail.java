/**
 * 录单核保+收费签单，针对工行新契约
 */

package com.sinosoft.midplat.service;


import java.io.FileInputStream;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.RuleParser;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.common.YBTDataVerification;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.ExeSQL;

public class Trail extends ServiceImpl {
	public Trail(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) {

		//mStartMillis 计算Trail起始时间
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into Trail.service()...");
		cInXmlDoc = pInXmlDoc;  
		Element mHeadEle = (Element) pInXmlDoc.getRootElement().getChild(Head);
		String mTranCom = mHeadEle.getChildText(TranCom);
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
		.append('_').append(NoFactory.nextAppNo())
		.append('_').append(0)
		.append("_inSvc.xml");			
		SaveMessage.save(pInXmlDoc, mTranCom, mSaveName.toString());
		cLogger.info("保存报文完毕！"+mSaveName);		   
		try {					
		cLogger.info("Into Trail.service()...-->authority(cInXmlDoc)网点与权限 添加代理");	
		//add by zhj 网点与权限 添加代理
		cInXmlDoc = authority(cInXmlDoc); 	
		//add by zhj 网点与权限 添加代理end 			
		 	
		cLogger.info("Into Trail.service()...-->authorityCheck.submitData(mHeadEle)交易权限");	
		AuthorityCheck authorityCheck = new AuthorityCheck();
		if(!authorityCheck.submitData(mHeadEle)){
			throw new MidplatException("该网点无权限！");
		} 
			//校验系统中是否有相同保单正在处理，尚未返回
			//默认超时设置为5分钟(300s)；如果未配置锁定时间，则使用该值。
			int tLockTime = 300;	
			try { 
				cLogger.info("-----配置的锁定时间为:"+Integer.parseInt(cThisBusiConf.getChildText(locktime)));
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			} catch (Exception ex) {	//使用默认值
				cLogger.debug("未配置锁定时间，或配置有误，使用默认值(s)："+tLockTime, ex);
			} 			

			cLogger.info("Into Trail.service()...-->RuleParser().check(cInXmlDoc)校验");
			long mStartRuleParser = System.currentTimeMillis();
			new RuleParser().check(cInXmlDoc);
			long mUsedRuleParser = (System.currentTimeMillis() - mStartRuleParser);
			cLogger.info("----------Timekeeping---------->RuleParser().check(cInXmlDoc)校验时间为:"+String.valueOf(mUsedRuleParser));			
			//add by zhanghj   
			long mStartYBTDataVerification = System.currentTimeMillis();
			YBTDataVerification verification = new YBTDataVerification();
			boolean GradeFlag = verification.SameGradeBnfVerification(cInXmlDoc);
			if(GradeFlag==false){
				throw new MidplatException("同一受益顺序受益份额之和不等于1！请确认");
			}
			boolean digitFlag = verification.digitBnfVerification(cInXmlDoc);
			if(digitFlag==false){ 
				throw new MidplatException("受益顺序不能跳号！请确认");
			}  
			long mUsedYBTDataVerification = (System.currentTimeMillis() - mStartYBTDataVerification);
			cLogger.info("----------Timekeeping---------->YBTDataVerification校验时间为:"+String.valueOf(mUsedYBTDataVerification));			
			//add end 			   						 
			String tRiskCode = cInXmlDoc.getRootElement().getChild("Body").getChild("Risk").getChildText("MainRiskCode");
			if("313040".equals(tRiskCode)){
			calculatePrem(cInXmlDoc); 
			}else{
				throw new MidplatException("此产品无需试算");
			}
			Element mFlag = new Element(Flag);
			mFlag.addContent("0");
			
			Element mDesc = new Element(Desc);
			mDesc.addContent("交易成功");
			
			Element mHead = new Element(Head);
			mHead.addContent(mFlag);
			mHead.addContent(mDesc);
			cOutXmlDoc = new Document();
			Element tTranData = new Element("TranData");
			tTranData.addContent(mHead);
			tTranData.addContent((Element)cInXmlDoc.getRootElement().getChild(Body).clone());
			cOutXmlDoc.addContent(tTranData);
			mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(0)
			.append("_outSvc.xml");			
			SaveMessage.save(cOutXmlDoc, mTranCom, mSaveName.toString());
		} catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"交易失败！", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
			mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(0)
			.append("_outSvc.xml");			
			SaveMessage.save(cOutXmlDoc, mTranCom, mSaveName.toString());
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"交易失败！", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
			mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(0)
			.append("_outSvc.xml");			
			SaveMessage.save(cOutXmlDoc, mTranCom, mSaveName.toString());
		}				
		
		cLogger.info("Out Trail.service()!");
		return cOutXmlDoc;
	}
	
	private boolean calculatePrem(Document cInXmlDoc) throws MidplatException {
		cLogger.debug("Into Trail.calculatePrem()...");
		double tAmnt = Double.parseDouble(NumberUtil.fenToYuan(cInXmlDoc.getRootElement().getChild("Body").getChild("Risk").getChildText("Amnt")));
		
	    String tBirthDay = cInXmlDoc.getRootElement().getChild("Body").getChild("Insured").getChildText("Birthday");
	    int tAppAge = DateUtil.getAge(tBirthDay);
		String tsql = "select "+tAmnt+"/1000*prem From rt_313040 where "+tAppAge+" Between age1 and age2";
//		String tPrem = String.valueOf((Double.parseDouble(new ExeSQL().getOneValue(tsql)))*100.00);
		String tPrem = new ExeSQL().getOneValue(tsql);
		cLogger.debug("保额："+tAmnt);
		cLogger.debug("被保人生日："+tBirthDay);
		cLogger.debug("被保人年龄："+tAppAge);
		cLogger.debug("计算出的保费："+tPrem);
		if("".equals(tPrem)||tPrem == null){
			throw new MidplatException("计算保费失败，可能被保人投保年龄受限");
		}
		cInXmlDoc.getRootElement().getChild("Body").getChild("Risk").getChild("Prem").setText(tPrem);
		cLogger.debug("Out Trail.getContDB()!");
		return true;
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
	public static void main(String[] args) throws Exception {
		String mInFilePath = "D:/instd.xml";
		mInFilePath="F:/MyEclipse/workspace/HGLIFE/WebRoot/WEB-INF/conf/rule.xml";
		String mOutFilePath = "D:/trailOut.xml";
		mOutFilePath="D:/task/20161121/P53819113Out.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		new RuleParser().check(mInXmlDoc);
		System.out.println("out");
	}
	
}
