package com.sinosoft.midplat.newccb.service;

import org.jdom.Document;
import org.jdom.Element;

import cn.ccb.secapi.SecAPI;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.service.ServiceImpl;

public class ContBatResponse extends ServiceImpl {
    private String tTranCom=null;
	public ContBatResponse(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc) throws Exception {
		cLogger.info("Into ContBatRequResult()...");
		Element tHeadEle = pInXmlDoc.getRootElement().getChild(Head);
		String tranNo=tHeadEle.getChildText(TranNo);
		tTranCom=tHeadEle.getChildText("TranCom");
		long mStartMillis = System.currentTimeMillis();
		cLogger.info(JdomUtil.toStringFmt(pInXmlDoc));
		//银行回盘文件解密文件路径
		String localDir=this.cThisBusiConf.getChildText("LocalDir");
		String localID=pInXmlDoc.getRootElement().getChild("Head").getChildText("LocalID");
		String remoteID=pInXmlDoc.getRootElement().getChild("Head").getChildText("RemoteID");
		cLogger.info("银行端安全节点号:"+remoteID);
		cLogger.info("保险公司端安全节点号:"+localID);
		String filePath=this.cThisBusiConf.getChildText("ccbLocalDir");;//建行送盘文件路径
		Element tBodyEle = pInXmlDoc.getRootElement().getChild(Body);
		String tFileName = tBodyEle.getChildText("FileName");
	    cLogger.info("银行回盘文件路径为:"+filePath+tFileName);
		int tBatFlag = Integer.parseInt(tBodyEle.getChildText("BatFlag"));
		String tNum = tBodyEle.getChildText("Num");
		String tSumAmt = tBodyEle.getChildText("SumAmt");
		try { 
			// 1:记录日志
			cTranLogDB = insertTranLog(pInXmlDoc);
			cTranLogDB.setBak1(tFileName);
			cTranLogDB.setBak2(tBodyEle.getChildText("BatFlag"));
			cTranLogDB.setBak3(tNum);
			cTranLogDB.setBak4(tSumAmt);
			
			String descStr=null;
			if(!(tBatFlag==0)&&!(tBatFlag==16)){
				//00:整批处理成功，16：整批处理失败
				switch (tBatFlag) {
				  case 1:descStr="未收到此包";break;
				  case 2:descStr="包的明细总笔数与实际明细笔数汇总不符";break;
				  case 3:descStr="包的明细总金额与实际明细金额汇总不符";break;
				  case 4:descStr="超出单包笔数限制";break;
				  case 5:descStr="包明细存在负金额";break;
				  case 6:descStr="保险公司返回批量包名称不符，或返回码有误";break;
				  case 7:descStr="包明细数为0";break;
				  case 8:descStr="批量包校验正确";break;
				  case 9:descStr="批量包未生成";break;
				  case 10:descStr="批量包已生成";break;
				  case 11:descStr="批量包可能重复提交,重复包可能为x";break;
				  case 12:descStr="下载批量包响应文件失败";break;
				  case 13:descStr="保险公司账户余额不足，整批失败";break;
				  case 14:descStr="批量明细序号重复";break;
				  case 15:descStr="批量包文件格式错误";break;
				  case 99:descStr="建行内部处理错误，需要人工核实情况";break;
			    }
				throw new MidplatException(descStr);
			}
			cLogger.info("解密文件入参localID:"+localID+",remoteID:"+remoteID +",解密前文件:"+filePath+tFileName
				         +",解密后文件:"+localDir+tFileName);
		    //解密银行送盘交易文件
			SecAPI.fileUnEnvelop(localID, remoteID,filePath+tFileName,localDir+tFileName);
			//另起线程 根据银行送盘文件生成核心zip文件上传FTP服务器
			BackFileThread backFileThread=new BackFileThread(localDir+tFileName,tTranCom,tranNo);
			backFileThread.start();
			
//			BackFileThread backFileThread=new BackFileThread("C:\\Users\\anico\\Desktop\\unencrypt\\AL01100792017022701_RESULT.XML",tTranCom,tranNo);
//			backFileThread.start();
		//	String fileName=generateCoreZipFile(localDir+tFileName);
        // 	String fileName=generateCoreZipFile("C:\\Users\\anico\\Desktop\\unencrypt\\AL01100792017022701_RESULT.XML");
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "交易成功");
			cTranLogDB.setRCode(0);
			cTranLogDB.setRText("回盘文件接收成功:"+filePath+tFileName);
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name) + "交易失败！", ex);
			if (null != cTranLogDB) { 
				cTranLogDB.setRCode(1); 
				cTranLogDB.setRText(ex.getMessage());
			}
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "交易成功");
		}
		if (null != cTranLogDB) { // 插入日志失败时cTranLogDB=null
			long tCurMillis = System.currentTimeMillis();
			
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！"+ cTranLogDB.mErrors.getFirstError());
			}
		}
		cLogger.info(JdomUtil.toStringFmt(cOutXmlDoc));
		return cOutXmlDoc;
	}
	public static void main(String[] args) throws Exception {

//     FileInputStream in=new FileInputStream(new File("C:\\Users\\anico\\Desktop\\建行测试报文\\CCB代收付文件20170111\\CCB代收付文件20170111\\AL03100192017011101_RESULT.XML"));
//     Element doc=JdomUtil.build(in,"UTF-8").getRootElement();
//     //System.out.println(JdomUtil.toStringFmt(doc));
//     XPath tXPath = XPath.newInstance("Detail_List/Detail[Cst_AccNo='1216129980110291838']");
//     Element e=(Element) tXPath.selectSingleNode(doc);
//	 System.out.println(JdomUtil.toStringFmt(e));
//		String str="232493u,";
//		str=str.substring(0,str.lastIndexOf(","));
//		System.out.println(str);
	}
}
