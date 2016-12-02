///手工单出单结果明细文件-银行处理结果回盘
package com.sinosoft.midplat.newabc.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newabc.format.HandContRiskDtlOutXsl;
import com.sinosoft.midplat.newabc.util.AbcFileTransUtil;
import com.sinosoft.midplat.service.ServiceImpl;

public class HandContRiskDtlBlc extends ServiceImpl {
	public HandContRiskDtlBlc(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into HandContBlc.service()...");
		cInXmlDoc = pInXmlDoc;
		JdomUtil.print(cInXmlDoc);
		try {
			cTranLogDB = insertTranLog(pInXmlDoc);
			String cTrandate = cInXmlDoc.getRootElement().getChild(Body).getChildText(TranDate);
			
			cLogger.info("start call lis service()...");
		    cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_NonRealTimeContRiskDtl).call(cInXmlDoc);
		    cLogger.info("end call lis service()...");
		    //处理核心报文，并生成手工单结果明细文件上传至银行
		    dealFile(cOutXmlDoc,cTrandate);
		    
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
	} 
		catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"交易失败！", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
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
		cLogger.info("Out NewContQuery.service()!");
		JdomUtil.print(cOutXmlDoc);
		return cOutXmlDoc;
	}

	private void dealFile(Document cOutXmlDoc, String cTrandate) throws Exception {
		cLogger.info("Into HandContRiskDtl.receive()..."+cTrandate);
		JdomUtil.print(cOutXmlDoc);
		JdomUtil.print(cOutXmlDoc);
		cOutXmlDoc= HandContRiskDtlOutXsl.newInstance().getCache().transform(cOutXmlDoc);
		String outHead = null;//头记录
		String serialNo="";//序号
		String fileName = "";//文件名称
		String out ="";
		String outBody="";
	    int  maxNo = 100;//一个txt中允许最多的明细记录数  测试的话，只需要把这个值换掉即可
		//头记录处理
		Element tRoot = cOutXmlDoc.getRootElement();
		Element tHead = tRoot.getChild("Head");
		System.out.println(tRoot+"           ===========      "+tHead);
		String tFlag = tHead.getChildText("Flag");//交易成功标志
		String tComCode = "3103";//保险公司代码
		String tFuncFlag = cThisBusiConf.getChildText("funcFlag");//交易代码
		String tCount = null;//明细记录总笔数
		String tSumMoney = null;//总金额
	    Element tBody = tRoot.getChild("Body");
	    List<Element> list  = tBody.getChildren("Detail");
    	tCount = Integer.toString(list.size());
    	tSumMoney="0000";
    	long sum=0;
    	String ttLocalDir = cThisBusiConf.getChildText("localDir");  	
	    cLogger.info("生成返回文件头记录"+outHead);
	    cLogger.info("生成返回文件总记录"+list.size());
	    if(list.size()!=0){
	    /*
		 * 通过总数和最大数取得所能生成文件的数量
		 */
	    for (int i = 0; i <=list.size()/maxNo; i++) {
			if(i<10){
				serialNo ="0" + Integer.toString(i);
				fileName = "SRESULTKZ"+tComCode+"."+cTrandate;
			}else{
				serialNo = Integer.toString(i);
				fileName = "SRESULTKZ"+tComCode+"."+cTrandate;
			}
			File file = new File(ttLocalDir+"/"+fileName);
			//如果文件不存在穿件文件
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/*
			 * 此循环是向第i个文件中流出(i+1)*maxNo - i*maxNo个明细记录
			 * 第i个文件里取的明细记录是从第i*maxNo个至第(i+1)*maxNo个,
			 * 当取的值和总数相同时，则跳出此循环 
			 */
			for (int j = i*maxNo; j <(i+1)*maxNo; j++) {
				if(j==list.size()){
					break;
				}
				Element tDetail = list.get(j);
				
				String tRiskCode = tDetail.getChildText("RiskCode");
				String tContNo = tDetail.getChildText("ContNo");
				String tPayIntv = tDetail.getChildText("PayIntv");
				String tPrem = tDetail.getChildText("Prem");
				//处理金额
				long prem=Long.parseLong(tPrem);
				sum=sum+prem;
				tPrem = NumberUtil.fenToYuan(tPrem);
				String tAmnt = tDetail.getChildText("Amnt");
				tAmnt=NumberUtil.fenToYuan(tAmnt);
				
				String tPayEndYearFlag = tDetail.getChildText("PayEndYearFlag");
				String tPayEndYear = tDetail.getChildText("PayEndYear");
				String tInsuYearFlag = tDetail.getChildText("InsuYearFlag");
				String tInsuYear = tDetail.getChildText("InsuYear");

				outBody += tRiskCode+"|"+tContNo+"|"+tPayIntv+"|"+tPayEndYearFlag+"|"+tPayEndYear+"|"+tInsuYearFlag+"|"+tInsuYear+"|"+tPrem+"|"+tAmnt+"|"+"\n";
			}
			//输出记录至txt文件
			tSumMoney=String.valueOf(sum);
			tSumMoney=NumberUtil.fenToYuan(tSumMoney);
			System.out.println("总金额："+tSumMoney);
			if(tSumMoney.equals("")||tSumMoney==null){
				tSumMoney="0.00";
			}
			outHead = tComCode+"|"+"03"+"|"+tCount+"|"+tSumMoney+"|"+"\n";
			out = outHead + outBody;
			cLogger.info("第"+i+"个文件生成返回文件总记录"+out);
			byte[] m = out.getBytes("GBK");
			try {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(m);
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				cLogger.error("找不到文件");
			} catch (IOException e) {
				e.printStackTrace();
				cLogger.error("I/O异常");
			}
			//删除文件，避免生成多余文件
			if(i*maxNo == list.size()){
				file.delete();
			}
			//清空明细记录和总记录
			outBody="";
			out="";
		}
	  }else{
		  	fileName = "SRESULTKZ"+tComCode+"."+cTrandate;
	    	File file = new File(ttLocalDir+"/"+fileName);
			//如果文件不存在创建文件
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//输出记录至txt文件
			tCount="0";
			tSumMoney="0.00";
			outHead = tComCode+"|"+"03"+"|"+tCount+"|"+tSumMoney+"|"+"\n";
			out = outHead;
			cLogger.info("只返回头文件，核心未返回退保值"+out);
			byte[] m = out.getBytes("GBK");
			try {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(m);
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				cLogger.error("找不到文件");
			} catch (IOException e) {
				e.printStackTrace();
				cLogger.error("I/O异常");
			}
	  }
        System.out.println("文件名："+ttLocalDir+"/"+fileName);
		AbcFileTransUtil util = new AbcFileTransUtil(ttLocalDir,ttLocalDir);
		util.upLoadFile(fileName,"02");
		cLogger.info("Out HandContRiskDtl.receive()...");
	}
}
