package com.sinosoft.midplat.newabc.service;

import java.util.Calendar;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;
/**
 * 保全申请
 * @author liuzk
 *
 */
public class ContSecureApply extends ServiceImpl{

	public ContSecureApply(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc){
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into ContSecureApply.service()...");
		cInXmlDoc = pInXmlDoc;
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild(Body);
		String mContNo = mBodyEle.getChildText(ContNo);
		String busiType = mBodyEle.getChildText("BusiType");
		try {
			cTranLogDB = insertTranLog(pInXmlDoc);
			
			//校验系统中是否有相同保单正在处理，尚未返回
			int tLockTime = 300;	//默认超时设置为5分钟(300s)；如果未配置锁定时间，则使用该值。
			try {
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			} catch (Exception ex) {	//使用默认值
				cLogger.debug("未配置锁定时间，或配置有误，使用默认值(s)："+tLockTime, ex);
			}
			Calendar tCurCalendar = Calendar.getInstance();
			tCurCalendar.add(Calendar.SECOND, -tLockTime);
			String tSqlStr = new StringBuilder("select count(1) from TranLog where RCode=").append(CodeDef.RCode_NULL)
				.append(" and ContNo='").append(mContNo).append('\'')
				.append(" and MakeDate>=").append(DateUtil.get8Date(tCurCalendar))
				.append(" and MakeTime>=").append(DateUtil.get6Time(tCurCalendar))
				.toString();
			if (!"1".equals(new ExeSQL().getOneValue(tSqlStr))) {
				throw new MidplatException("此保单数据正在处理中，请稍候！");
			}
			cLogger.info("判断是否重发，1为重发，重发Body增加ResendFlag节点");
			String tResendSql = new StringBuilder("select count(1) from TranLog where RCode=").append(CodeDef.RCode_OK)
			.append(" and contno='").append(mContNo).append('\'')
			.append(" and funcflag='").append("3013").append('\'')
			.append(" and MakeDate =").append(DateUtil.get8Date(tCurCalendar))
			.toString();
			if ("1".equals(new ExeSQL().getOneValue(tResendSql))) {
				Element tResendFlag=new ElementLis("ResendFlag", "1",mBodyEle);
				cLogger.info("重发标志："+tResendFlag.getText());
			}
			JdomUtil.print(cInXmlDoc);
			if(busiType.equals("07")){//犹撤
				cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_NoTaken).call(cInXmlDoc);
			}else 	if(busiType.equals("09")){//满期 核心暂时没有
				cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ManPayment).call(cInXmlDoc);
			}else		if(busiType.equals("10")){//退保
				cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_Taken).call(cInXmlDoc);
			}
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
		} 
		catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"交易失败！", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		if (null != cTranLogDB) {	//插入日志失败时cTranLogDB=null
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
		cLogger.info("Out ContSecureApply.service()!");
		JdomUtil.print(cOutXmlDoc);
		return cOutXmlDoc;
	}
	
}
