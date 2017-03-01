package com.sinosoft.midplat.newccb.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipOutputStream;

import org.jdom.Document;
import org.jdom.Element;

import cn.ccb.secapi.SecAPI;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.service.ServiceImpl;

public class ContBatchSendDisk extends ServiceImpl {

	public ContBatchSendDisk(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc) throws Exception{
		cLogger.info("Into ContBatchSendDisk.service()...");
		long mStartMillis=System.currentTimeMillis();
		//标准输入报文
		cLogger.info(JdomUtil.toStringFmt(pInXmlDoc));
		//银行送盘文件解密存储路径
		String localDir=this.cThisBusiConf.getChildText("LocalDir");
		//银行端安全节点号
		String localID=pInXmlDoc.getRootElement().getChild(Head).getChildText("LocalID");
		//保险公司端安全节点号
		String remoteID=pInXmlDoc.getRootElement().getChild(Head).getChildText("remoteID");
		//建行方PUT文件的文件路径（保险公司接收）
		String filePath="/home/ap/fserver2/rcv/";
		Element tBody=pInXmlDoc.getRootElement().getChild(Body);
		//代理保险批量包名称
		String tFileName=tBody.getChildText("FileName");
		//银行回盘文件路径
		cLogger.info("银行回盘文件路径:"+tFileName+tFileName);
		//代理保险批量包处理状态代码
		int tBatFlag=Integer.parseInt(tBody.getChildText("BatFlag"));
		//当前批明细总笔数
		String tNum=tBody.getChildText("Num");
		//当前批明细总金额
		String tSumAmt=tBody.getChildText("SumAmt");
		/**插入交易日志,设置[备用1:代理保险批量包名称,备用2:代理保险批量包处理状态代码,备用3:当前批明细总笔数,备用4:当前批明细总金额]*/
		try {
			cTranLogDB.setBak1(tFileName);
			cTranLogDB.setBak2(tBody.getChildText("BatFlag"));
			cTranLogDB.setBak3(tNum);
			cTranLogDB.setBak4(tSumAmt);
			String descStr=null;
			if(!"00".equals(tBatFlag)||!"16".equals(tBatFlag)){
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
			cLogger.info("银行端安全节点号:"+localID+"保险公司端安全节点号:"+remoteID+"解密前路径:"+ filePath+tFileName+"解密后路径:"+localDir+tFileName);
			//解密
			SecAPI.fileUnEnvelop(localID, remoteID, filePath+tFileName, localDir+tFileName);
			//简单报文
			MidplatUtil.getSimpOutXml(0, "交易成功");
			if(cTranLogDB!=null){
				cTranLogDB.setRCode(0);
				cTranLogDB.setRText("交易成功");
			}
		} catch (Exception e) {
			cLogger.info(cThisBusiConf.getChildText("name")+"交易失败!");
			//简单报文
			MidplatUtil.getSimpOutXml(1, e.getMessage());
			cTranLogDB.setRCode(1);
			cTranLogDB.setRText(e.getMessage());
		}
		if(cTranLogDB!=null){
			long mCurrMillis=System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(mStartMillis-mCurrMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(mCurrMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(mCurrMillis));
		}
		if(!cTranLogDB.update()){
			throw new MidplatException("更新TranLogDB表失败!");
		}
		cLogger.info("Out ContBatchSendDisk.service()!");
		return cOutXmlDoc;
	}
	
	public String generateCoreZipFile(String localFile) throws Exception{
		FileOutputStream fileOutputStream=null;
		ZipOutputStream zipOutputStream=null;
		FileInputStream fileInputStream=null;
		InputStreamReader inputStreamReader=null;
		BufferedReader bufferedReader=null;
		FileInputStream fileInputStream1=null;
		String coreUploadFilePath=NewCcbConf.newInstance().getConf().getRootElement().getChildText("coreUploadFilePath");
		String coreDownloadFilePath=NewCcbConf.newInstance().getConf().getRootElement().getChildText("coreDownloadFilePath");
		if(!coreUploadFilePath.endsWith("/")) coreUploadFilePath+="/";
		File coreUploadFile=new File(coreUploadFilePath);
		File file=new File(localFile);
		String filename=file.getName();
		//代收付标志[AL03100192017011101_RESULT.XML]
		final String tfFlag=filename.substring(2,3).equals("0")?"S":"F";
		final String tSubmitDate=filename.substring(9,17);
		String batchNo=filename.substring(17,19);
		if(batchNo.length()<5){
			int num=5-batchNo.length();
			for (int i = 0; i <num; i++) {
				batchNo="0"+batchNo;
			}
		}
		final String tBatchNo=batchNo;
		cLogger.info("代收代付标志为:"+tBatchNo);
		cLogger.info("提交日期为:"+tSubmitDate);
		cLogger.info("当日批次号为:"+tBatchNo);
		cLogger.info("代收付文件匹配正则表达式:"+"^\\w{10,15}_"+tfFlag+"02"+tSubmitDate+"_"+tBatchNo+".txt$");
		File[]  files=coreUploadFile.listFiles(new FileFilter() {
			Pattern pattern=Pattern.compile("^\\w{10,15}_"+tfFlag+"02"+tSubmitDate+"_"+tBatchNo+".txt$");
			@Override
			public boolean accept(File filePath) {
				String fileName=filePath.getName();
				Matcher matcher=pattern.matcher(fileName);
				return matcher.matches();
			}
		});
		if(files.length==0){
			throw new MidplatException("匹配取盘txt文件失败，请核对！");
		}else if(files.length>1){
			throw new MidplatException("匹配取盘txt文件个数大于1，请核对！");
		}
		cLogger.info("匹配取盘的文件名为:"+files[0].getPath());
		String filenamePath=files[0].getPath();
		
		return null;
	}

}
