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
		//批量文件传输路径
		String tBatchFTPPaht4LIS = NewCcbConf.newInstance().getConf().getRootElement().getChildText("BatchFTPPaht4LIS");
		//本地接收文件路径
		String tLocalFilePathRcv = NewCcbConf.newInstance().getConf().getRootElement().getChildText("LocalFilePathRcv");
		
		//标准输入报文体
		Element tBodyEle = pInXmlDoc.getRootElement().getChild(Body);
		//批量包名称
		String tFileName = tBodyEle.getChildText("FileName");
		//处理状态
		String tBatFlag = tBodyEle.getChildText("BatFlag");
		//明细总笔数
		String tNum = tBodyEle.getChildText("Num");
		//明细总金额
		String tSumAmt = tBodyEle.getChildText("SumAmt");
		//批量送盘文件名称
		tBodyEle.getChild("FileName").setText(tFileName+"_RESULT.xml");
		try { 
			// 1:记录日志
			//标准输入报文插入交易日志
			cTranLogDB = insertTranLog(pInXmlDoc);
			//设置备用1[批量包名称]
			cTranLogDB.setBak1(tFileName);
			//设置备用2[处理状态]
			cTranLogDB.setBak2(tBatFlag);
			//设置备用3[明细总笔数]
			cTranLogDB.setBak3(tNum);
			//设置备用4[明细总金额]
			cTranLogDB.setBak4(tSumAmt);
			//交易结果
			Element mFlagEle = new Element(Flag);
			//交易结果描述
			Element mDescEle = new Element(Desc);
			//未到建行核心记账
			if(!"00".equals(tBatFlag)){
				//交易结果[失败]
				mFlagEle.setText("1");
				//01:未收到此包
				if("01".equals(tBatFlag)){
					mDescEle.setText("未收到此包");
				//02:包的明细总笔数与实际明细笔数汇总不符
				}else if("02".equals(tBatFlag)){
					mDescEle.setText("包的明细总笔数与实际明细笔数汇总不符");
				//03:包的明细总金额与实际明细金额汇总不符
				}else if("03".equals(tBatFlag)){
					mDescEle.setText("包的明细总金额与实际明细金额汇总不符");
				//04:超出单包笔数限制
				}else if("04".equals(tBatFlag)){
					mDescEle.setText("超出单包笔数限制");
				//05:包明细存在负金额
				}else if("05".equals(tBatFlag)){
					mDescEle.setText("包明细存在负金额");
				//06:保险公司返回批量包名称不符，或返回码有误
				}else if("06".equals(tBatFlag)){
					mDescEle.setText("保险公司返回批量包名称不符，或返回码有误");
				//07:包明细数为0
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
				//99:建行内部处理错误，需要人工核实情况
				}*/else if("99".equals(tBatFlag)){
					mDescEle.setText("建行内部处理错误，需要人工核实情况");
				}
				//标准输入报文体批量包名称设置为批量送盘文件名称
				tBodyEle.getChild("FileName").setText(tFileName+"_SOURCE.xml");
			//00:已到建行核心记账
			}else{
				//交易结果[成功]
				mFlagEle.setText("0");
				//设置交易结果描述[已到建行核心记账]
				mDescEle.setText("已到建行核心记账");
			}
			//标准输入报文体加入交易结果
			tBodyEle.addContent(mFlagEle);
			//标准输入报文体加入交易结果描述
			tBodyEle.addContent(mDescEle);
			
			//文件路径:本地接收文件路径/批量送盘文件名称_RESULT.XML
			cLogger.info("文件路径:"+tLocalFilePathRcv+"/"+tFileName+"_RESULT.XML");
			//本地接收文件路径文件对象
			File tFile = new File(tLocalFilePathRcv+"/"+tFileName+"_RESULT.XML");
			//批量文件传输路径文件对象
			File tMoveFile = new File(tBatchFTPPaht4LIS);
			//移动文件不存在
			if(!tMoveFile.exists()){
				//创建移动文件
				tMoveFile.mkdirs();
			}
			//本地接收文件重命名[批量文件传输路径批量送盘文件名称]
			if(!tFile.renameTo(new File(tBatchFTPPaht4LIS+tFileName+"_RESULT.xml"))){
				//移动文件失败，抛出异常
				throw new MidplatException("移动文件失败"+tFileName+"_RESULT.xml");
			}
			//由于回盘的结果文件存储到snd文件夹中，所以进行第二次转存
			//本地发送文件路径
			String sndLocal = NewCcbConf.newInstance().getConf().getRootElement().getChildText("LocalFilePathSnd");
			//第二次转存文件路径:本地发送文件路径批量包名称_RESULT.XML
			cLogger.info("第二次转存文件路径:"+sndLocal+tFileName+"_RESULT.XML");
			//本地接收文件路径批量包名称_RESULT.XML
		    tFile = new File(tLocalFilePathRcv+tFileName+"_RESULT.XML");
		    //start rename......[开始重命名]
		    cLogger.info("start rename......");
		    //本地接收文件路径是文件
			if(tFile.isFile()){
				//重命名[批量文件传输路径批量包名称_RESULT.xml]
				tFile.renameTo(new File(tBatchFTPPaht4LIS+tFileName+"_RESULT.xml"));
			}
			//end rename......[结束重命名]
			cLogger.info("end rename......");
			
			//标准输入报文调用WebService原子服务返回标准输出报文
			cOutXmlDoc = new CallWebsvcAtomSvc("109").call(pInXmlDoc);
			
			//标准输出报文头
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			//交易结果[失败]
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//交易失败
				//抛出异常，标准输出报文头交易结果描述
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			//根据交易结果和交易结果描述，生成简单的标准输出报文
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "交易成功");
			//设置交易日志[交易结果:失败]
			cTranLogDB.setRCode(0);
			//设置交易日志[结果描述]
			cTranLogDB.setRText("已到建行核心记账");
		} catch (Exception ex) {
			//批量代收代付送盘交易失败！
			cLogger.error(cThisBusiConf.getChildText(name) + "交易失败！", ex);
			//避免开空指针
			if (null != cTranLogDB) { // 插入日志失败时cTranLogDB=null
				//设置交易结果[成功]
				cTranLogDB.setRCode(1); // -1-未返回；0-交易成功，返回；1-交易失败，返回
				//设置结果描述[截取前150位]
				cTranLogDB.setRText(NumberUtil.cutStrByByte(ex.getMessage(),
						150, MidplatConf.newInstance().getDBCharset()));
			}
			//根据交易结果和交易结果描述，生成简单的标准输出报文
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "交易成功");
		}
		//避免空指针
		if (null != cTranLogDB) { // 插入日志失败时cTranLogDB=null
			long tCurMillis = System.currentTimeMillis();
			//设置服务耗时[(s)当前时间毫秒数-开始时间毫秒数]
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			//最后修改日期[当前时间毫秒数8位日期]
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			//最后修改时间[当前时间毫秒数6位时间]
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			//交易日志更新失败
			if (!cTranLogDB.update()) {
				//更新日志信息失败！
				cLogger.error("更新日志信息失败！"
						+ cTranLogDB.mErrors.getFirstError());
			}
		}
		JdomUtil.print(cOutXmlDoc);
		//返回标准输出报文
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
