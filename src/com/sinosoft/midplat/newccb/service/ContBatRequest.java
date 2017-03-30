package com.sinosoft.midplat.newccb.service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.net.ftp.FTPFile;
import org.jdom.Document;
import org.jdom.Element;


import cn.ccb.secapi.SecAPI;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.newccb.util.FTPFILEMAPDao;
import com.sinosoft.midplat.newccb.util.RegisterFtp;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.DBOper;
import com.sinosoft.utility.ExeSQL;
/**
 * 批量代收付取盘交易
 * @author anico
 *
 */
public class ContBatRequest extends ServiceImpl {

	
	public ContBatRequest(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc) throws Exception {
		cLogger.info("Into ContBatRequest()...");
		Element FTPEle = NewCcbConf.newInstance().getConf().getRootElement().getChild("FTP");
		String ip=FTPEle.getAttributeValue("ip");
		String user=FTPEle.getAttributeValue("user");
		String password=FTPEle.getAttributeValue("password");
		String remoteStr=FTPEle.getAttributeValue("remoteStr");
		//下载FTP批量包本地存储路径
		Element LocalDownloadZipFileEle = NewCcbConf.newInstance().getConf().getRootElement().getChild("LocalDownloadZipFile");
		String localDownloadZipPath=LocalDownloadZipFileEle.getText();
		Element tHeadEle = pInXmlDoc.getRootElement().getChild(Head);
		String tTranCom=tHeadEle.getChildText("TranCom");
		long mStartMillis = System.currentTimeMillis();
		cLogger.info(JdomUtil.toStringFmt(pInXmlDoc));
		String localID=pInXmlDoc.getRootElement().getChild("Head").getChildText("LocalID");
		String remoteID=pInXmlDoc.getRootElement().getChild("Head").getChildText("RemoteID");
		cLogger.info("银行端安全节点号:"+remoteID);
		cLogger.info("保险公司端安全节点号:"+localID);
		Element tBodyEle = pInXmlDoc.getRootElement().getChild(Body);
		String tFileName = tBodyEle.getChildText("FileName");
		String tType = tBodyEle.getChildText("Type");
		String coreType=tType.equals("0")?"S":"F";
		String tOrderNo=tBodyEle.getChildText("OrderNo");
		final String dateStr=tFileName.substring(9,17);
		cLogger.info("文件名为:"+tFileName);
		cLogger.info("文件序号:"+tOrderNo);
		cLogger.info("文件日期:"+dateStr);
		cLogger.info("文件类型:"+tType+"|"+coreType);
		String dateFmt=new SimpleDateFormat("/yyyy-MM-dd").format(new Date());
		String checkRemoteFilePath=remoteStr+dateFmt;
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
			//FTP登录
			RegisterFtp ftp=new RegisterFtp(ip, user, password);
			boolean loginFlag=ftp.loginFTP();
			FTPFile[] fileList=null;
			if(!loginFlag){
				throw new MidplatException("FTP登录失败!");
			}
			ExeSQL exeSql=new ExeSQL();
			//查询是否已经成功生成对应银行文件名的取盘文件，有可能由于网络延时导致取盘交易失败，但取盘文件已经生成
			String sql="select count(1) from FTPFILEMAP where bankname='"+tFileName+"' and disposeflag='0' and bankflag='"+tTranCom+"'";
			String val=exeSql.getOneValue(sql);
			if(Integer.parseInt(val)!=1){
				//获取核心上传的批量包
				fileList=ftp.checkRemoteFile(checkRemoteFilePath);
				String dateStr1=new SimpleDateFormat("yyyy/MM/dd").format(new Date());
				//对银行文件和核心文件做映射
				FTPFile file1=null;
				String fileName=null;
				//银行文件和核心文件已经做过映射，但处理未成功就不用再做映射
				sql="select localname from FTPFILEMAP where bankname='"+tFileName+"' and disposeflag='1' and bankflag='"+tTranCom+"'";
				val=exeSql.getOneValue(sql);
				if(!val.contains("zip")){
					for (int i = 0; i < fileList.length; i++) {
					    file1=fileList[i];
					    fileName=file1.getName();
					    //查看核心文件是否已成功做过取盘处理，对于已成功的不再做处理
					    sql="select count(1) from FTPFILEMAP where localname='"+fileName+"' and disposeflag='0' and bankflag='"+tTranCom+"'";
						String val1=exeSql.getOneValue(sql);
						if(Integer.parseInt(val1)==1||!fileName.contains(coreType)){
							//如果核心文件以做映射且处理成功跳过本次循环
							continue;
						}
						sql="insert into FTPFILEMAP values('"+tFileName+"','"+fileName+"','1','"+dateStr1+"','"+tTranCom+"')";
					    FTPFILEMAPDao.insert(sql);
						break;
					}
				}else{
					fileName=val;
				}
			    cLogger.info("获取到的代收付包文件名为:"+fileName);
			    //判断文件是否已经下载到本地
			    if(!(new File(localDownloadZipPath+fileName).exists())){
			    	//下载文件到本地
			    	boolean downloadFlag=ftp.downloadFile(checkRemoteFilePath,fileName,localDownloadZipPath);
			    	//关闭FTP连接
				    ftp.closeConnect();
				    if(!downloadFlag){
			    		throw new MidplatException("FTP下载文件失败!");
			    	}
			    }
			    File file0=new File(localDownloadZipPath+fileName.substring(0,fileName.lastIndexOf("."))+".txt");
			    if(!file0.exists()){
				    //如果文件未解压，先解压文件
				    uncompressZipFile (localDownloadZipPath,fileName);
				}
			    cLogger.info(fileName+"文件已解压！"+"解压路径为:"+file0.getPath());
			    //在本地生成响应银行未加密文件
				generateBankFile(LocalDir,tFileName,file0.getPath());
				//加密文件
			    cLogger.info("加密文件入参localID:"+localID+",remoteID:"+remoteID
							 +",加密前文件:"+LocalDir+tFileName+"_SOURCE.xml"
						     +",加密后文件:"+ccbLocalDir+tFileName+"_SOURCE.xml");
			   SecAPI.fileEnvelop(localID, remoteID,LocalDir+tFileName+"_SOURCE.xml",ccbLocalDir+tFileName+"_SOURCE.xml");
			}
		   
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
			eFilePath.setText(ccbLocalDir.substring(0,ccbLocalDir.length()-1));
			eBody.addContent(eFilePath);
		
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "交易成功");
			cOutXmlDoc.getRootElement().addContent(eBody);
			
			cTranLogDB.setBak1(tFileName);
			cTranLogDB.setRText("银行取盘成功");
			cTranLogDB.setRCode(0);
			//取盘成功，跟新数据库处理状态
			sql="update  FTPFILEMAP set disposeflag='0' where bankname='"+tFileName+"'";
			exeSql.execUpdateSQL(sql);
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
	 public void generateBankFile(String LocalDir,String localFileName,String unzipPath) throws Exception{
		 FileInputStream fileInputStream=null;
         InputStreamReader inputStreamReader=null;
         BufferedReader bufferedReader=null;
         FileOutputStream fileOutputStream=null;
         BufferedOutputStream bufferedOutputStream=null;
		 try {
			 fileInputStream=new FileInputStream(unzipPath);
			 inputStreamReader=new InputStreamReader(fileInputStream,"GBK");
			 bufferedReader=new BufferedReader(inputStreamReader);
			 String oneLineData=null;
			 Element rootEle=new Element("Root");
			 Element HeadEle=new Element("Head");
			 rootEle.addContent(HeadEle);
			 Element AgIns_BtchBag_NmEle=new Element("AgIns_BtchBag_Nm");//代理保险批量包名称
			 AgIns_BtchBag_NmEle.setText(localFileName+"_SOURCE.xml");
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
//			 while ((oneLineData=bufferedReader.readLine())!=null&&!"".equals(oneLineData)) {
			 while ((oneLineData=bufferedReader.readLine())!=null) {
				 if("".equals(oneLineData)){
					continue; 
				 }
				 String[] strs=oneLineData.split(",");
				 if(strs.length==6){
					 Cur_Btch_Dtl_TDnumEle.setText(strs[3]);
					 CCur_Btch_Dtl_TAmtEle.setText(NumberUtil.fenToYuan(strs[4]));
					 codeSF=strs[5];//代收付业务代码
				 }
				 if(strs.length>6){
					Element DetailEle=new Element("Detail");
					Detail_ListEle.addContent(DetailEle);
					Element Cst_NmEle=new Element("Cst_Nm");//客户名称
					Cst_NmEle.setText(strs[5]);
					Element Cst_AccNoEle=new Element("Cst_AccNo");//客户账号
					Cst_AccNoEle.setText(strs[4]);
					Element CcyCdEle=new Element("CcyCd");//币种代码
					CcyCdEle.setText("156");//建行只有一种币种156:人民币
					Element CshEx_CdEle=new Element("CshEx_Cd");//钞汇代码
					CshEx_CdEle.setText("1");
					Element Btch_Dtl_SNEle=new Element("Btch_Dtl_SN");//批量明细序号
					Btch_Dtl_SNEle.setText(Integer.valueOf(strs[0])+"");
					Element Ins_Co_SRP_Bsn_CtCdEle=new Element("Ins_Co_SRP_Bsn_CtCd");//保险公司代收付业务种类代码
					//银行代收付代码类型(01:首期缴费  02:续期缴费  03:其他代收 12:理赔 13:红利代发  15:其他代付)
					Ins_Co_SRP_Bsn_CtCdEle.setText(codeSF.equals("10600")?"01":codeSF.equals("10601")?"02":codeSF.equals("00600")?"12":codeSF.equals("00601")?"13":codeSF.startsWith("1")?"03":"15");
					Element InsPolcy_NoEle=new Element("InsPolcy_No");//保单号码
					if(codeSF.equals("10601")){//续期缴费
					   //保险公司代收付业务种类代码为“续期缴费”、“追加保费”、“退保”、“部分支取”时，公司必须上送该收付业务的保单号码
					   //核心的批量包暂时未提供保单号,核心确认不会生成“续期缴费”、“追加保费”、“退保”、“部分支取”数据
					   InsPolcy_NoEle.setText("");
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
			throw  new MidplatException("取盘交易失败!"+e.getMessage());
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
					cLogger.info(zipFilePath+zipEntry.getName());
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
//		String[] strs=new String[1];
//		strs[0]="CNY";
//		System.out.println(strs[0].equals("")?"156":strs[0].equals("CNY")?"156":strs[0]);
		String codeSF="10600";
		System.out.println(codeSF.startsWith("1") ? "03" : codeSF.equals("00601") ? "13" : codeSF.equals("00600") ? "12" : codeSF.equals("10601") ? "02" : codeSF.equals("10600") ? "01" : "15");
	}
}
