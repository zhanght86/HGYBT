package com.sinosoft.midplat.newccb.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.service.ServiceImpl;

public class ContBatResponse extends ServiceImpl {

	public ContBatResponse(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc) throws Exception {

		cLogger.info("Into ContBatRequResult()...");
		long mStartMillis = System.currentTimeMillis();
		JdomUtil.print(pInXmlDoc);
		String tBatchFTPPaht4LIS = NewCcbConf.newInstance().getConf().getRootElement().getChildText("BatchFTPPaht4LIS");
		String tLocalFilePathRcv = NewCcbConf.newInstance().getConf().getRootElement().getChildText("LocalFilePathRcv");
		
		Element tBodyEle = pInXmlDoc.getRootElement().getChild(Body);
		String tFileName = tBodyEle.getChildText("FileName");
		String tBatFlag = tBodyEle.getChildText("BatFlag");
		String tNum = tBodyEle.getChildText("Num");
		String tSumAmt = tBodyEle.getChildText("SumAmt");
		tBodyEle.getChild("FileName").setText(tFileName+"_RESULT.xml");
		try { 
			// 1:记录日志
			cTranLogDB = insertTranLog(pInXmlDoc);
			cTranLogDB.setBak1(tFileName);
			cTranLogDB.setBak2(tBatFlag);
			cTranLogDB.setBak3(tNum);
			cTranLogDB.setBak4(tSumAmt);
			
			Element mFlagEle = new Element(Flag);
			Element mDescEle = new Element(Desc);
			if(!"00".equals(tBatFlag)){
				mFlagEle.setText("1");
				if("01".equals(tBatFlag)){
					mDescEle.setText("未收到此包");
				}else if("02".equals(tBatFlag)){
					mDescEle.setText("包的明细总笔数与实际明细笔数汇总不符");
				}else if("03".equals(tBatFlag)){
					mDescEle.setText("包的明细总金额与实际明细金额汇总不符");
				}else if("04".equals(tBatFlag)){
					mDescEle.setText("超出单包笔数限制");
				}else if("05".equals(tBatFlag)){
					mDescEle.setText("包明细存在负金额");
				}else if("06".equals(tBatFlag)){
					mDescEle.setText("保险公司返回批量包名称不符，或返回码有误");
				}else if("07".equals(tBatFlag)){
					mDescEle.setText("包明细数为0");
				}else if("08".equals(tBatFlag)){
					mDescEle.setText("批量包校验正确");
				}else if("09".equals(tBatFlag)){
					mDescEle.setText("批量包未生成");
				}else if("10".equals(tBatFlag)){
					mDescEle.setText("批量包已生成");
				}else if("11".equals(tBatFlag)){
					mDescEle.setText("批量包可能重复提交,重复包可能为x");
				}else if("12".equals(tBatFlag)){
					mDescEle.setText("下载批量包响应文件失败 ");
				}else if("13".equals(tBatFlag)){
					mDescEle.setText("保险公司账户余额不足，整批失败");
				}else if("14".equals(tBatFlag)){
					mDescEle.setText("批量明细序号重复");
				}else if("15".equals(tBatFlag)){
					mDescEle.setText("批量包文件格式错误");
				}/*else if("16".equals(tBatFlag)){
					mDescEle.setText("整批失败");
				}*/else if("99".equals(tBatFlag)){
					mDescEle.setText("建行内部处理错误，需要人工核实情况");
				}
				tBodyEle.getChild("FileName").setText(tFileName+"_SOURCE.xml");
			}else{
				mFlagEle.setText("0");
				mDescEle.setText("已到建行核心记账");
			}
			tBodyEle.addContent(mFlagEle);
			tBodyEle.addContent(mDescEle);
			
			cLogger.info("文件路径:"+tLocalFilePathRcv+"/"+tFileName+"_RESULT.XML");
			File tFile = new File(tLocalFilePathRcv+"/"+tFileName+"_RESULT.XML");
			File tMoveFile = new File(tBatchFTPPaht4LIS);
			if(!tMoveFile.exists()){
				tMoveFile.mkdirs();
			}
			if(!tFile.renameTo(new File(tBatchFTPPaht4LIS+tFileName+"_RESULT.xml"))){
				throw new MidplatException("移动文件失败"+tFileName+"_RESULT.xml");
			}
			//由于回盘的结果文件存储到snd文件夹中，所以进行第二次转存
			String sndLocal = NewCcbConf.newInstance().getConf().getRootElement().getChildText("LocalFilePathSnd");
			cLogger.info("第二次转存文件路径:"+sndLocal+tFileName+"_RESULT.XML");
		    tFile = new File(tLocalFilePathRcv+tFileName+"_RESULT.XML");
		    cLogger.info("start rename......");
			if(tFile.isFile()){
				tFile.renameTo(new File(tBatchFTPPaht4LIS+tFileName+"_RESULT.xml"));
			}
			cLogger.info("end rename......");
			
			cOutXmlDoc = new CallWebsvcAtomSvc("109").call(pInXmlDoc);
			
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//交易失败
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "交易成功");
			cTranLogDB.setRCode(0);
			cTranLogDB.setRText("已到建行核心记账");
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name) + "交易失败！", ex);

			if (null != cTranLogDB) { // 插入日志失败时cTranLogDB=null
				cTranLogDB.setRCode(1); // -1-未返回；0-交易成功，返回；1-交易失败，返回
				cTranLogDB.setRText(NumberUtil.cutStrByByte(ex.getMessage(),
						150, MidplatConf.newInstance().getDBCharset()));
			}
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "交易成功");
		}
		if (null != cTranLogDB) { // 插入日志失败时cTranLogDB=null
			long tCurMillis = System.currentTimeMillis();
			
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！"
						+ cTranLogDB.mErrors.getFirstError());
			}
		}
		JdomUtil.print(cOutXmlDoc);
		return cOutXmlDoc;
	}

	public static void main(String[] args) throws Exception {

//		Element cThisBusiConf = (Element)XPath.selectSingleNode(CcbConf.newInstance().getConf().getRootElement(), (new StringBuilder("business[funcFlag='")).append("803").append("']").toString());
//		ContBatResponse batch = new ContBatResponse(cThisBusiConf);
//		String mFilePath = "D:\\MyEclipseWorkSpace\\LIAN\\src\\test\\doc\\ccb\\803_inSvc.xml";
//		InputStream mIs = new FileInputStream(mFilePath);
//		Document pInXmlDoc = JdomUtil.build(mIs);
//		JdomUtil.print(batch.service(pInXmlDoc));
		
	
	}
}
