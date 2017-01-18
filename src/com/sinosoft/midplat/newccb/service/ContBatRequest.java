package com.sinosoft.midplat.newccb.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import cn.ccb.secapi.SecAPI;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sun.xml.internal.bind.util.Which;

/**
 * @ClassName: ContBatRequest
 * @Description: 批量代收代付取盘业务处理类
 * @author yuantongxin
 * @date 2017-1-18 下午6:18:23
 */
public class ContBatRequest extends ServiceImpl {

	/**
	 * <p>Title: ContBatRequest</p>
	 * <p>Description: 批量代收代付取盘业务处理类构造函数</p>
	 * @param pThisBusiConf 当前业务配置文件
	 */
	public ContBatRequest(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	/**
	 * 标准输入报文处理为标准输出报文
	 * @param pInXmlDoc 标准输入报文
	 * @return 标准输出报文
	 */
	public Document service(Document pInXmlDoc) throws Exception {
		//Into ContBatRequest()...
		cLogger.info("Into ContBatRequest()...");
		//开始时间毫秒数
		long mStartMillis = System.currentTimeMillis();
		//标准输入报文[缩进3空格，忽略声明中的编码]
		cLogger.info(JdomUtil.toStringFmt(pInXmlDoc));
		//本地安全节点号
		String localID=pInXmlDoc.getRootElement().getChild("Head").getChildText("LocalID");
		//建行安全节点号
		String remoteID=pInXmlDoc.getRootElement().getChild("Head").getChildText("RemoteID");
		//银行端安全节点号:建行安全节点号
		cLogger.info("银行端安全节点号:"+remoteID);
		//保险公司端安全节点号:本地安全节点号
		cLogger.info("保险公司端安全节点号:"+localID);
		//标准输入报文体
		Element tBodyEle = pInXmlDoc.getRootElement().getChild(Body);
		//代理保险批量包名称
		String tFileName = tBodyEle.getChildText("FileName");
		//代收代付标志
		String tType = tBodyEle.getChildText("Type");
		//当日批次号
		String tOrderNo=tBodyEle.getChildText("OrderNo");
		//提交日期
		final String dateStr=tFileName.substring(9,17);
		//文件名为:代理保险批量包名称
		cLogger.info("文件名为:"+tFileName);
		//文件序号:当日批次号
		cLogger.info("文件序号:"+tOrderNo);
		//文件日期:提交日期
		cLogger.info("文件日期:"+dateStr);
		//文件类型:代收代付标志
		cLogger.info("文件类型:"+tType+(tType.equals("0")?"代收":"代付"));
		String coreUploadFilePath=NewCcbConf.newInstance().getConf().getRootElement().getChildText("coreUploadFilePath");
		try {
			// 1:记录日志
			cTranLogDB = insertTranLog(pInXmlDoc);
			//响应银行文件加密前路径
			String LocalDir=this.cThisBusiConf.getChildText("LocalDir");
			File fileLocalDir=new File(LocalDir.endsWith("/")?LocalDir:LocalDir+"/");
			if(!fileLocalDir.exists()){
				if(!fileLocalDir.mkdirs())cLogger.info("创建文件路径失败："+LocalDir);
			}
			//响应银行文件加密后路径
			String ccbLocalDir=this.cThisBusiConf.getChildText("ccbLocalDir");
			File file=new File(coreUploadFilePath);
			if(!file.exists()){
				throw new MidplatException("核心FTP上传文件路径不存在:"+coreUploadFilePath);
			}
			//取文件包的序号位数不足5位前面补0
			if(tOrderNo.length()<5){
				int num=5-tOrderNo.length();
				for (int i = 0; i <num; i++) {
					tOrderNo="0"+tOrderNo;
				}
			}
			final String ttOrderNo=tOrderNo;
			//S:代收 F:代付
	        final String SFflag="0".equals(tType)?"S":"F";
	        cLogger.info("代收付交易文件匹配正则表达式:"+"^\\w{10,15}_"+SFflag+"02"+dateStr+"_"+ttOrderNo+".zip$");
	        File[] fileList=file.listFiles(new FileFilter() {
			   Pattern pattern=Pattern.compile("^\\w{10,15}_"+SFflag+"02"+dateStr+"_"+ttOrderNo+".zip$");
			   @Override
			   public boolean accept(File filePath) {
			      String fileName=filePath.getName();
				  Matcher matcher=pattern.matcher(fileName);
				  return matcher.matches();
			   }
			});
		    if(fileList.length>1){
		       cLogger.info("符合规则的文件个数为:"+fileList.length+"!请核对");
		       throw new MidplatException("交易失败");
		    }
		    if(fileList.length==0){
			       cLogger.info("符合规则的文件个数为:"+fileList.length+"!请核对");
			       throw new MidplatException("交易失败");
			    }
		    cLogger.info("获取到的代收包文件名为:"+fileList[0].getPath());
		    File file0=new File(fileList[0].getPath().substring(0,fileList[0].getPath().lastIndexOf("."))+".txt");
		    if(!file0.exists()){
		        //如果文件未解压，先解压文件
		        uncompressZipFile (coreUploadFilePath,fileList[0].getName());
		    }
		    cLogger.info(fileList[0].getName()+"文件已解压！"+"解压路径为:"+file0.getPath());
		    //在本地生成响应银行未加密文件
		    generateBankFile(LocalDir,tFileName,file0.getName());
		    //加密文件
			cLogger.info("加密文件入参localID:"+localID+",remoteID:"+remoteID
						     +",加密前文件:"+LocalDir+tFileName+"_SOURCE.xml"
						     +",加密后文件:"+ccbLocalDir+tFileName+"_SOURCE.xml");
			SecAPI.fileEnvelop(localID, remoteID,LocalDir+tFileName+"_SOURCE.xml",ccbLocalDir+tFileName+"_SOURCE.xml");
			Document tReturnDoc = JdomUtil.build(new FileInputStream(LocalDir+tFileName+"_SOURCE.xml"),"UTF-8");
			String tSumNum = tReturnDoc.getRootElement().getChild(Head).getChildText("Cur_Btch_Dtl_TDnum");
			String tSumAmt = tReturnDoc.getRootElement().getChild(Head).getChildText("Cur_Btch_Dtl_TAmt");
			Element eBody = new Element (Body);
			
			Element eFileName = new Element("FileName");
			eFileName.setText(tFileName);
			eBody.addContent(eFileName);
			
			Element eNum = new Element("Num");
			eNum.setText(tSumNum);
			eBody.addContent(eNum);
			
			Element eSumAmt = new Element("SumAmt");
			eSumAmt.setText(tSumAmt);
			eBody.addContent(eSumAmt);
			
			Element eFilePath = new Element("FilePath");
			eFilePath.setText(ccbLocalDir);
			eBody.addContent(eFilePath);
		
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "交易成功");
			cOutXmlDoc.getRootElement().addContent(eBody);
			
			cTranLogDB.setBak1(tFileName);
			cTranLogDB.setRText("银行取盘成功");
			cTranLogDB.setRCode(0);
		} catch (Exception ex) {
			ex.printStackTrace();
			cLogger.error(cThisBusiConf.getChildText(name) + "交易失败！", ex);
			if (null != cTranLogDB) { // 插入日志失败时cTranLogDB=null
				cTranLogDB.setRCode(1); // -1-未返回；0-交易成功，返回；1-交易失败，返回
				cTranLogDB.setRText(ex.getMessage());
			}
			cOutXmlDoc = MidplatUtil.getSimpOutXml(1, ex.getMessage());
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
	/**
	 * 生成响应银行未加密文件
	 * @param LocalDir 本地存储文件路径
	 * @param localFileName 本地存储文件名
	 * @param fileName 读取文件名
	 * @throws Exception
	 */
	 public void generateBankFile(String LocalDir,String localFileName,String fileName) throws Exception{
		 String coreUploadFilePath=NewCcbConf.newInstance().getConf().getRootElement().getChildText("coreUploadFilePath");
		 File file=new File(coreUploadFilePath+fileName);
		 if(!file.exists()){
			 throw new MidplatException(file.getPath()+"文件不存在！");
		 }
		 FileInputStream fileInputStream=null;
         InputStreamReader inputStreamReader=null;
         BufferedReader bufferedReader=null;
         FileOutputStream fileOutputStream=null;
         BufferedOutputStream bufferedOutputStream=null;
		 try {
			 fileInputStream=new FileInputStream(file);
			 inputStreamReader=new InputStreamReader(fileInputStream,"GBK");
			 bufferedReader=new BufferedReader(inputStreamReader);
			 String oneLineData=null;
			 Element rootEle=new Element("root");
			 Element HeadEle=new Element("Head");
			 rootEle.addContent(HeadEle);
			 Element AgIns_BtchBag_NmEle=new Element("AgIns_BtchBag_Nm");//代理保险批量包名称
			 AgIns_BtchBag_NmEle.setText(localFileName);
			 Element Cur_Btch_Dtl_TDnumEle=new Element("Cur_Btch_Dtl_TDnum");//当前批明细总数
			 Element CCur_Btch_Dtl_TAmtEle=new Element("Cur_Btch_Dtl_TAmt");//当前批明细总金额
			 Element AgIns_BtchBag_TpCdEle=new Element("AgIns_BtchBag_TpCd");//代理保险批量包类型代码
			 HeadEle.addContent(AgIns_BtchBag_NmEle);
			 HeadEle.addContent(Cur_Btch_Dtl_TDnumEle);
			 HeadEle.addContent(CCur_Btch_Dtl_TAmtEle);
			 HeadEle.addContent(AgIns_BtchBag_TpCdEle);
			 Element Detail_ListEle=new Element("Detail_List");
			 rootEle.addContent(Detail_ListEle);
			 String codeSF=null;
			 while ((oneLineData=bufferedReader.readLine())!=null&&!"".equals(oneLineData)) {
				 String[] strs=oneLineData.split(",");
				 if(strs.length==6){
					 Cur_Btch_Dtl_TDnumEle.setText(strs[3]);
					 CCur_Btch_Dtl_TAmtEle.setText(NumberUtil.fenToYuan(strs[4]));
					 codeSF=strs[5];//代收业务代码
				 }
				 if(strs.length>6){
					Element DetailEle=new Element("Detail");
					Detail_ListEle.addContent(DetailEle);
					Element Cst_NmEle=new Element("Cst_Nm");//客户名称
					Cst_NmEle.setText(strs[5]);
					Element Cst_AccNoEle=new Element("Cst_AccNo");//客户账号
					Cst_AccNoEle.setText(strs[4]);
					Element CcyCdEle=new Element("CcyCd");//币种代码
					CcyCdEle.setText(strs[11].equals("")?"CNY":strs[11]);//如果为空默认为CNY人民币
					Element CshEx_CdEle=new Element("CshEx_Cd");//钞汇代码
					CshEx_CdEle.setText("1");
					Element Btch_Dtl_SNEle=new Element("Btch_Dtl_SN");//批量明细序号
					Btch_Dtl_SNEle.setText(Integer.valueOf(strs[0])+"");
					Element Ins_Co_SRP_Bsn_CtCdEle=new Element("Ins_Co_SRP_Bsn_CtCd");//保险公司代收付业务种类代码
					Ins_Co_SRP_Bsn_CtCdEle.setText(codeSF.equals("10600")?"01":codeSF.equals("10601")?"02":codeSF.equals("00600")?"12":codeSF.equals("00601")?"13":"--");
					Element InsPolcy_NoEle=new Element("InsPolcy_No");//保单号码
					if(codeSF.equals("10600")){
					   //保险公司代收付业务种类代码为“续期缴费”、“追加保费”、“退保”、“部分支取”时，公司必须上送该收付业务的保单号码
					   InsPolcy_NoEle.setText("待定");
					}
					Element AmtEle=new Element("Amt");//金额
					AmtEle.setText(NumberUtil.fenToYuan(strs[10]));
					Element Rnew_Pbl_Prd_NumEle=new Element("Rnew_Pbl_Prd_Num");//续期应缴期数
					Element Rsrv_Fld1_InfEle=new Element("Rsrv_Fld1_Inf");//备用字段1信息
					Element Rsrv_Fld2_InfEle=new Element("Rsrv_Fld2_Inf");//备用字段2信息
					Element Rsrv_Fld3_InfEle=new Element("Rsrv_Fld3_Inf");//备用字段3信息
					DetailEle.addContent(Cst_NmEle).addContent(Cst_AccNoEle).addContent(CcyCdEle)
					.addContent(CshEx_CdEle).addContent(Btch_Dtl_SNEle).addContent(Ins_Co_SRP_Bsn_CtCdEle)
					.addContent(InsPolcy_NoEle).addContent(AmtEle).addContent(Rnew_Pbl_Prd_NumEle)
					.addContent(Rsrv_Fld1_InfEle).addContent(Rsrv_Fld2_InfEle).addContent(Rsrv_Fld3_InfEle);
				 }
			}
			Document rootDoc=new Document(rootEle);
			fileOutputStream=new FileOutputStream(LocalDir+localFileName+"_SOURCE.xml");
			bufferedOutputStream=new BufferedOutputStream(fileOutputStream);
			bufferedOutputStream.write(JdomUtil.toStringFmt(rootDoc,"UTF-8").getBytes("UTF-8"));
			cLogger.info("生成银行未加密文件成功："+LocalDir+localFileName+"_SOURCE.xml");
		} catch (Exception e) {
			e.printStackTrace();
			cLogger.info("生成银行未加密文件失败！");
			throw  new MidplatException(e.getMessage());
		}finally{
			if(bufferedReader!=null){
				bufferedReader.close();
			}
			if(inputStreamReader!=null){
				inputStreamReader.close();
			}
			if(fileInputStream!=null){
				fileInputStream.close();
			}
			if(bufferedOutputStream!=null){
				bufferedOutputStream.close();
			}
			if(fileOutputStream!=null){
				fileOutputStream.close();
			}
		}
		
	 }
	 public  void uncompressZipFile (String zipFilePath,String zipFileName) throws Exception{
	    	FileInputStream fileInputStream=null;
	    	ZipInputStream zipInputStream=null;
	    	FileOutputStream fileOutputStream=null;
	    	try {
				fileInputStream=new FileInputStream(zipFilePath+zipFileName);
				zipInputStream=new ZipInputStream(fileInputStream);
				ZipEntry zipEntry=null;
				byte[] bytes=new byte[1024];//1KB
				while ((zipEntry=zipInputStream.getNextEntry())!=null) {
					//cLogger.info(zipEntry.getName());
					System.out.println(zipEntry.getName());
					if(!zipEntry.isDirectory()){
						fileOutputStream=new FileOutputStream(zipFilePath+zipEntry.getName());
						int i=-1;
						while ((i=zipInputStream.read(bytes))!=-1) {
							fileOutputStream.write(bytes,0,i);
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				cLogger.info(zipFileName+"文件解压失败!");
				throw new MidplatException("文件解压失败!");
			}finally{
				if(zipInputStream!=null){
					zipInputStream.close();
				}
				if(fileInputStream!=null){
					   fileInputStream.close();
					}
				if(fileOutputStream!=null){
				   fileOutputStream.close();
				}
			}
	    }
	public static void main(String[] args) throws Exception {

//		Element cThisBusiConf = (Element)XPath.selectSingleNode(CcbConf.newInstance().getConf().getRootElement(), (new StringBuilder("business[funcFlag='")).append("803").append("']").toString());
//		ContBatRequest batch = new ContBatRequest(cThisBusiConf);
//		String mFilePath = "D:\\MyEclipseWorkSpace\\LIAN\\src\\test\\doc\\ccb\\803_inSvc.xml";
//		InputStream mIs = new FileInputStream(mFilePath);
//		Document pInXmlDoc = JdomUtil.build(mIs);
//		JdomUtil.print(batch.service(pInXmlDoc));
	}
}
