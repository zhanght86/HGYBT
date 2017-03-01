package com.sinosoft.midplat.newabc.service;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;

public class NewRenewalPaymentQuery extends ServiceImpl {

	public NewRenewalPaymentQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc) throws Exception{
		cLogger.info("Into NewRenewalPaymentQuery.service()..."); 
		//开始毫秒数
		long mStartMillis=System.currentTimeMillis();
		//成员标准输入报文
		cInXmlDoc=pInXmlDoc;
		//标准输入报文根节点
		Element mRootEle=cInXmlDoc.getRootElement();
		//报文体节点
		Element mBodyEle=mRootEle.getChild(Body);
		//投保单(印刷)号
		String mProposalPrtNo=mBodyEle.getChildText(ProposalPrtNo);
		//监控异常
		try {
			//插入交易日志
			insertTranLog(cInXmlDoc);
			//调用WebService原子服务[查询(续期)保费]返回标准输出报文
			cOutXmlDoc=new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_QueryPrem).call(cInXmlDoc);
			//将标准输出报文打印到控制台， GBK编码，缩进3空格
			JdomUtil.print(cOutXmlDoc);
			//标准输出报文根节点
			Element mOutRootEle=cOutXmlDoc.getRootElement();
			//报文头节点
			Element mOutHeadEle=mOutRootEle.getChild(Head);
			//报文体节点
			@SuppressWarnings("unused")
			Element mOutBodyEle=mOutRootEle.getChild(Body);
			//交易结果为交易失败返回码 抛出异常[交易结果描述]
			if(AblifeCodeDef.RCode_ERROR==Integer.parseInt(mOutHeadEle.getChildText(Flag)))
				throw new MidplatException(mOutHeadEle.getChildText(Desc));
			//使用时间毫秒数
			long tUseTime=(System.currentTimeMillis()-mStartMillis)/1000;
			//默认超时1分钟
			int timeOut=60;
			//交易配置超时时间
			timeOut=Integer.parseInt(cThisBusiConf.getChildText(timeout));
			//使用时间毫秒数>超时时间毫秒数 处理超时 回滚系统数据 抛出异常[系统繁忙]
			if(tUseTime>timeOut*1000){
				cLogger.info("处理超时! tUseTime:"+tUseTime/1000+"s; timeOut:"+timeOut+"s; 投保单(印刷)号:"+mProposalPrtNo);
				rollback();
				throw new MidplatException("系统繁忙，请稍后再试!");
			}
		//捕获异常
		} catch (Exception e) {
			//交易配置交易失败 根据交易结果和交易结果描述，生成简单的标准输出报文。
			cLogger.info(cThisBusiConf.getChild(name)+"交易失败！",e);
			MidplatUtil.getSimpOutXml(AblifeCodeDef.RCode_ERROR, e.getMessage());
		}
		
		//交易日志非空
		if(cTranLogDB!=null){
			//标准输出报文头
			Element mHeadEle=cOutXmlDoc.getRootElement().getChild(Head);
			//设置交易日志:交易结果、结果描述、服务耗时[秒:(当前毫秒数-开始毫秒数)]、最后修改日期、最后修改时间
			cTranLogDB.setRCode(mHeadEle.getChildText(Flag));
			cTranLogDB.setRText(mHeadEle.getChildText(Desc));
			long mCurrMillis=System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(mCurrMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(mCurrMillis));
			cTranLogDB.setMakeTime(DateUtil.get6Time(mCurrMillis));
			//更新交易日志失败 第一个错误信息
			if(!cTranLogDB.update()) {
				cLogger.error("更新交易日志信息失败!"+cTranLogDB.mErrors.getFirstError());
			}
		}
		cLogger.info("Out NewRenewalPaymentQuery.service()!"); 
		return cOutXmlDoc;
	}
	
	private void rollback(){
		cLogger.debug("Into NewRenewalPaymentQuery.rollback()... ");
		//标准输入报文根节点
		Element tRootEle=cInXmlDoc.getRootElement();
		//报文体节点
		Element tBody=tRootEle.getChild(Body);
		//报文头节点[克隆]
		Element tHead=(Element) tRootEle.getChild(Head).clone();
		//报文头节点设置服务id为银保新单回滚码
		tHead.getChild(ServiceId).setText(AblifeCodeDef.SID_Bank_ContRollback);
		//新建报文体节点
		Element mBody=new Element(Body);
		//加入标准输入报文:投保单(印刷)号[克隆]、保单合同印刷号[克隆]、标准输出报文体:保险单号[克隆] 子节点
		mBody.addContent(tBody.getChild(ProposalPrtNo));
		mBody.addContent(tBody.getChildText(ContPrtNo));
		mBody.addContent(cOutXmlDoc.getRootElement().getChild(Body).getChildText(ContNo));
		//新建标准输入报文根节点
		Element mRootEle=new Element(TranData);
		//加入标准输入报文头节点、新建报文体节点
		mRootEle.addContent(tHead);
		mRootEle.addContent(mBody);
		//新建标准输入报文
		Document mInXmlDoc=new Document(mRootEle);
		//监控异常
		//调用WebService原子服务[银保新单回滚]
		try {
			new CallWebsvcAtomSvc(tHead.getChildText(ServiceId)).call(mInXmlDoc);
		//捕获异常
		} catch (Exception e) {
			//回滚失败 异常
			cLogger.error("回滚数据失败!",e);
		}
		cLogger.debug("Out NewRenewalPaymentQuery.rollback()!");
	}
	
}
;