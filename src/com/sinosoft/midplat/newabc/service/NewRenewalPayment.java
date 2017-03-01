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

public class NewRenewalPayment extends ServiceImpl {

	public NewRenewalPayment(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc) throws Exception{
		cLogger.info("Into NewRenewalPayment.service()...");
		//开始毫秒数
		long mStartMillis=System.currentTimeMillis();
		//成员标准输入报文
		cInXmlDoc=pInXmlDoc;
		//将标准输入报文打印到控制台， GBK编码，缩进3空格。
		JdomUtil.print(cInXmlDoc);
		//标准输入报文根节点
		Element mInRootEle=cInXmlDoc.getRootElement();
		//报文体节点
		@SuppressWarnings("unused")
		Element mInBodyEle=mInRootEle.getChild(Body);
		//标准输入报文头交易流水号节点
		Element tranNo=mInRootEle.getChild(Head).getChild(TranNo);
		//监控异常
		try {
			//插入交易日志
			cTranLogDB=insertTranLog(cInXmlDoc);
			// 将标准输入报文打印到控制台， GBK编码，缩进3空格
			JdomUtil.print(cInXmlDoc);
			//调用WebService原子服务[续期缴费]返回标准输出报文
			cOutXmlDoc=new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_RenewalPay).call(cInXmlDoc);
			//将标准输出报文打印到控制台， GBK编码，缩进3空格
			JdomUtil.print(cOutXmlDoc);
			//标准输出报文根节点
			Element mOutRootEle=cOutXmlDoc.getRootElement();
			//报文头节点
			Element mOutHeadEle=mOutRootEle.getChild(Head);
			//报文体节点
			Element mOutBodyEle=mOutRootEle.getChild(Body);
			//标准输出报文头交易结果为交易失败返回码
			if(AblifeCodeDef.RCode_ERROR==Integer.parseInt(mOutHeadEle.getChildText(Flag)))
				//抛出异常 交易结果描述
				throw new MidplatException(mOutHeadEle.getChildText(Desc));
			
			//使用时间毫秒数
			long mUsedMillis=System.currentTimeMillis()-mStartMillis;
			//默认超时时间1分钟
			int mTimeOut=60;
			//交易配置超时时间
			try {
				mTimeOut=Integer.parseInt(cThisBusiConf.getChildText(timeout));
			} catch (Exception e) {
				cLogger.error("未配置超时时间,使用默认超时时间:"+mTimeOut+"s",e);
			}
			//使用时间毫秒数>超时时间毫秒数
			if(mUsedMillis>mTimeOut*1000){
				//处理超时
				cLogger.info("处理超时!使用时间:"+mUsedMillis/1000.0+"s; 超时时间:"+mTimeOut+"s; 投保单(印刷)号:"+mOutBodyEle.getChildText(ProposalPrtNo)+"; 交易流水号:"+tranNo);
				//回滚系统数据
				rollback();
			//抛出异常 系统繁忙
				throw new MidplatException("系统繁忙,请稍后再试!");
			}
		//捕获异常
		} catch (Exception e) {
			//交易配置交易名交易失败
			cLogger.error(cThisBusiConf.getChild(name)+"交易失败!",e);
			//根据交易结果和交易结果描述，生成简单的标准输出报文。
			MidplatUtil.getSimpOutXml(AblifeCodeDef.RCode_ERROR, e.getMessage());
		}
		
		//交易日志非空
		if(cTranLogDB!=null){
			//标准输出报文头节点
			Element mRootEle=cOutXmlDoc.getRootElement().getChild(Head);
			//设置交易日志:交易结果、结果描述、服务耗时[秒:(当前毫秒数-开始毫秒数)]、最后修改日期、最后修改时间
			cTranLogDB.setRCode(mRootEle.getChildText(Flag));
			cTranLogDB.setRText(mRootEle.getChildText(Desc));
			long mCurMillis=System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(mCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(mCurMillis));
			cTranLogDB.setMakeTime(DateUtil.get6Time(mCurMillis));
			//更新交易日志失败 第一个错误信息
			if(!cTranLogDB.update())
				cLogger.error("更新交易日志信息失败!"+cTranLogDB.mErrors.getFirstError());
		}
		cLogger.info("Out NewRenewalPayment.service()!");
		return cOutXmlDoc;
	}
	
	private void rollback(){
		cLogger.info("Into NewRenewalPayment.rollback()...");
		//标准输入报文根节点
		Element mInRootEle=cInXmlDoc.getRootElement();
		//报文体节点
		Element mInBodyEle=mInRootEle.getChild(Body);
		//报文头节点[克隆]
		Element mInHeadEle=(Element) mInRootEle.getChild(Head).clone();
		//报文头节点设置服务id为银保新单回滚码
		mInHeadEle.getChild(ServiceId).setText(AblifeCodeDef.SID_Bank_ContRollback);
		//新建报文体节点
		Element mBodyEle=new Element(Body);
		//报文体节点加入标准输入报文体:投保单(印刷)号[克隆]、保单合同印刷号[克隆]、标准输出报文体保险单号[克隆]
		mBodyEle.addContent((Element)mInBodyEle.getChild(ProposalPrtNo).clone());
		mBodyEle.addContent((Element)mInBodyEle.getChild(ContPrtNo).clone());
		mBodyEle.addContent((Element)mInBodyEle.getChild(cOutXmlDoc.getRootElement().getChild(Body).getChildText(ContNo)).clone());
		//新建标准输入报文根节点
		Element mRootEle=new Element(TranData);
		//加入标准输入报文头节点、新建报文体节点
		mRootEle.addContent(mInHeadEle);
		mRootEle.addContent(mBodyEle);
		//新建标准输入报文
		Document mInXmlDoc=new Document(mRootEle);
		//监控异常
		try {
			//调用WebService原子服务[银保新单回滚]
			new CallWebsvcAtomSvc(mInHeadEle.getChildText(ServiceId)).call(mInXmlDoc);
			//捕获异常 
		} catch (Exception e) {
			//回滚失败 异常
			cLogger.error("回滚数据失败!",e);
		}
		cLogger.info("Out NewRenewalPayment.rollback()!");
	}
	
}
