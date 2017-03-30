package com.sinosoft.midplat.newccb.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.newccb.util.RegisterFtp;
import com.sinosoft.utility.ExeSQL;

public class BackFileThread extends Thread{
	protected final Logger cLogger = Logger.getLogger(getClass());
	private String filePath;
	private String tranCom;
	private String tranNo;
	public BackFileThread(String path,String tranCom,String tranNo){
		this.filePath=path;
		this.tranCom=tranCom;
		this.tranNo=tranNo;
	}
	@Override
	public void run() {
		ExeSQL exeSql=new ExeSQL();
		String updateSql;
		try {
			uploadBackZipFile(filePath);
			updateSql="update tranlog set bak2='上传核心回盘文件成功!' where tranno='"+tranNo+"'";
		    if(!exeSql.execUpdateSQL(updateSql)){
		    	cLogger.info("执行更新语句失败"+updateSql);
		    }
		} catch (Exception e) {
			updateSql="update tranlog set bak2='上传核心回盘文件失败!' where tranno='"+tranNo+"'";
			 if(!exeSql.execUpdateSQL(updateSql)){
			    	cLogger.info("执行更新语句失败"+updateSql);
			 }
			e.printStackTrace();
		}
	}
	public void uploadBackZipFile(String localFile) throws Exception{
		FileOutputStream fileOutputStream=null;
		ZipOutputStream zipOutputStream=null;
		FileInputStream fileInputStream=null;
		InputStreamReader inputStreamReader=null;
		BufferedReader BufferedReader=null;
		FileInputStream fileInputStream1=null;
		File file=new File(localFile);
		//FTP配置信息
		Element FTPEle = NewCcbConf.newInstance().getConf().getRootElement().getChild("FTP");
		String ip=FTPEle.getAttributeValue("ip");
		String user=FTPEle.getAttributeValue("user");
		String password=FTPEle.getAttributeValue("password");
		String uploadStr=FTPEle.getAttributeValue("localStr");
		String remoteStr=FTPEle.getAttributeValue("remoteStr");
		
		Element LocalDownloadZipFileEle = NewCcbConf.newInstance().getConf().getRootElement().getChild("LocalDownloadZipFile");
		String localDownloadZipPath=LocalDownloadZipFileEle.getText();
		Element backBatchZipEle = NewCcbConf.newInstance().getConf().getRootElement().getChild("backBatchZip");
		String backBatchZipPath=backBatchZipEle.getText();
		
		//查询银行取盘文件处理日期
		String dateStrFmtSql="select disposeDate from FTPFILEMAP where bankname='"+file.getName().substring(0,19)+"' and disposeflag='0' and bankflag='"+tranCom+"'";
		String dateStrFmt=new ExeSQL().getOneValue(dateStrFmtSql).replace("/","-");
		try {
			String fileName=file.getName();
			String sql="select localname from FTPFILEMAP where bankname='"+fileName.substring(0,19)+"' and disposeflag='0' and bankflag='"+tranCom+"'";
			String coreUpdateFileStr=new ExeSQL().getOneValue(sql);
			cLogger.info("回盘文件对应的核心文件为:"+coreUpdateFileStr);
	        String downloadFileName=localDownloadZipPath+coreUpdateFileStr;
	        RegisterFtp ftp=new RegisterFtp(ip, user, password);
			boolean loginFlag=ftp.loginFTP();
			if(!loginFlag){
				throw new MidplatException("FTP登录失败!");
			}
			String checkRemoteFilePath=remoteStr+"/"+dateStrFmt;
	        if(!new File(downloadFileName).exists()){
	        	boolean downloadFlag=ftp.downloadFile(checkRemoteFilePath,coreUpdateFileStr,localDownloadZipPath);
	        	if(!downloadFlag){
	        		//关闭FTP连接
				    ftp.closeConnect();
				    throw new MidplatException("下载"+coreUpdateFileStr+"取盘文件失败!");
	        	}
	        }
	        File file0=new File(localDownloadZipPath+coreUpdateFileStr.substring(0,coreUpdateFileStr.lastIndexOf("."))+".txt");
	        if(!file0.exists()){
			    //如果文件未解压，先解压文件
			    uncompressZipFile (localDownloadZipPath,coreUpdateFileStr);
			}
	        fileOutputStream=new FileOutputStream(backBatchZipPath+coreUpdateFileStr);
	        String TxtFileName=coreUpdateFileStr.substring(0,coreUpdateFileStr.lastIndexOf("."))+".txt";
	        ZipEntry zipEntry=new ZipEntry(TxtFileName);
	        zipOutputStream=new ZipOutputStream(fileOutputStream);
	        zipOutputStream.putNextEntry(zipEntry);
	        
	        fileInputStream=new FileInputStream(localDownloadZipPath+TxtFileName);
	        inputStreamReader=new InputStreamReader(fileInputStream,"GBK");
	        BufferedReader=new BufferedReader(inputStreamReader);
	        
	        fileInputStream1=new FileInputStream(file);
	        Element docRoot=JdomUtil.build(fileInputStream1,"UTF-8").getRootElement();//将银行送盘文件解析成document并获取根节点
	        String dataStr=null;
	        while((dataStr=BufferedReader.readLine())!=null){
	        	if("".equals(dataStr)){
	        	     continue;
	        	}
	        	String[] strs=dataStr.split(",");
	        	if(strs.length==6){
	        		if(!docRoot.getChild("Head").getChildText("Cur_Btch_Dtl_TDnum").equals(strs[3])){
	        		   throw new MidplatException("送盘文件和取盘文件总数据不一致！");
	        		}
	        		zipOutputStream.write(dataStr.getBytes("GBK"));
	        		continue;
	        	}
	        	zipOutputStream.write("\r\n".getBytes());//换行符
	        	//获取银行账号根据银行账号获取银行处理报文相对应的节点
	        	String account=strs[4];
	        	XPath tXPath = XPath.newInstance("Detail_List/Detail[Cst_AccNo='"+account+"']");
	        	Element Detail=(Element) tXPath.selectSingleNode(docRoot);
	        	if(Detail==null){
	        		throw new MidplatException("送盘数据与取盘数据不一致,请核对！");
	        	}
	        	dataStr=dataStr.substring(0,dataStr.lastIndexOf(","));
	        	String responseCode=Detail.getChildText("Hst_Rsp_Cd");//主机响应码
	        	String responseStr=Detail.getChildText("Hst_Rsp_Inf");//主机响应描述
	        	cLogger.info("银行账号为:"+account+"交易数据的响应码为:"+responseCode+"响应描述为:"+responseStr);
	        	zipOutputStream.write((dataStr+responseCodeTransfer(responseCode)+","+responseStr).getBytes("GBK"));
	        }
	        zipOutputStream.flush();
	        zipOutputStream.closeEntry();
	        cLogger.info("本地生成核心回盘文件路径为："+downloadFileName);
	        
	        //将回盘文件上传到FTP服务器
	        boolean uploadFlag=ftp.uploadFile(uploadStr,backBatchZipPath+coreUpdateFileStr,coreUpdateFileStr);
	        if(!uploadFlag){
	        	throw new MidplatException("上传回盘文件失败:"+coreUpdateFileStr);
	        }
		} catch (Exception e) {
			e.printStackTrace();
			throw new MidplatException(e.getMessage());
		}finally{
			if(zipOutputStream!=null){
				zipOutputStream.close();
			}
			if(fileOutputStream!=null){
				fileOutputStream.close();
			}
			if(BufferedReader!=null){
				BufferedReader.close();
			}
			if(inputStreamReader!=null){
				inputStreamReader.close();
			}
			if(fileInputStream!=null){
				fileInputStream.close();
			}
			if(fileInputStream1!=null){
				fileInputStream1.close();
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
	//银行响应码转换成核心响应码
	public String responseCodeTransfer(String code){
		if("00000".equals(code)){return "0000";}//收付费成功
	    else if("E8208".equals(code)){return "3051";}//钞汇已结清
		else if("E8201".equals(code)){return "3039";}//帐户已销户
		else if("E3150".equals(code)){return "3009";}//帐户不存在
		else if("E5502".equals(code)){return "3059";}//密码已挂失 <批量交易不会遇到此错误> 
		else if("E4501".equals(code)){return "2005";}//帐户控制状态不允许存入
		else if("E3001".equals(code)){return "3032";}//币别或钞汇鉴别不存在
		else if("E4500".equals(code)){return "2006";}//帐户控制状态不允许支取
		else if("E5000".equals(code)){return "3016";}//存折已挂失
		else if("E5002".equals(code)){return "3006";}//卡已挂失
		else if("E7102".equals(code)){return "3008";}//余额不足
		else if("E3266".equals(code)){return "3065";}//客户名称检查失败
		else if("E1408".equals(code)){return "3057";}//帐号类型不符
		else if("E8301".equals(code)){return "3039";}//存折已挂失
		else if("E2351".equals(code)){return "3004";}//无效卡号
		else if("E1085".equals(code)){return "3999";}//挂失标志不正确
		else if("E3540".equals(code)){return "3999";}//注销状态不正确
		else if("E3556".equals(code)){return "3999";}//卡状态不正常
		else if("SHK02".equals(code)){return "3999";}//使用非法账号导致
		else if("SDB01".equals(code)){return "3999";}//使用非法账号导致
		else if("E9999".equals(code)){return "3999";}//建行内部处理错误
		else{return "3999";}
	}
}
