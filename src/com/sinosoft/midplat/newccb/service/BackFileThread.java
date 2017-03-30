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
			updateSql="update tranlog set bak2='�ϴ����Ļ����ļ��ɹ�!' where tranno='"+tranNo+"'";
		    if(!exeSql.execUpdateSQL(updateSql)){
		    	cLogger.info("ִ�и������ʧ��"+updateSql);
		    }
		} catch (Exception e) {
			updateSql="update tranlog set bak2='�ϴ����Ļ����ļ�ʧ��!' where tranno='"+tranNo+"'";
			 if(!exeSql.execUpdateSQL(updateSql)){
			    	cLogger.info("ִ�и������ʧ��"+updateSql);
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
		//FTP������Ϣ
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
		
		//��ѯ����ȡ���ļ���������
		String dateStrFmtSql="select disposeDate from FTPFILEMAP where bankname='"+file.getName().substring(0,19)+"' and disposeflag='0' and bankflag='"+tranCom+"'";
		String dateStrFmt=new ExeSQL().getOneValue(dateStrFmtSql).replace("/","-");
		try {
			String fileName=file.getName();
			String sql="select localname from FTPFILEMAP where bankname='"+fileName.substring(0,19)+"' and disposeflag='0' and bankflag='"+tranCom+"'";
			String coreUpdateFileStr=new ExeSQL().getOneValue(sql);
			cLogger.info("�����ļ���Ӧ�ĺ����ļ�Ϊ:"+coreUpdateFileStr);
	        String downloadFileName=localDownloadZipPath+coreUpdateFileStr;
	        RegisterFtp ftp=new RegisterFtp(ip, user, password);
			boolean loginFlag=ftp.loginFTP();
			if(!loginFlag){
				throw new MidplatException("FTP��¼ʧ��!");
			}
			String checkRemoteFilePath=remoteStr+"/"+dateStrFmt;
	        if(!new File(downloadFileName).exists()){
	        	boolean downloadFlag=ftp.downloadFile(checkRemoteFilePath,coreUpdateFileStr,localDownloadZipPath);
	        	if(!downloadFlag){
	        		//�ر�FTP����
				    ftp.closeConnect();
				    throw new MidplatException("����"+coreUpdateFileStr+"ȡ���ļ�ʧ��!");
	        	}
	        }
	        File file0=new File(localDownloadZipPath+coreUpdateFileStr.substring(0,coreUpdateFileStr.lastIndexOf("."))+".txt");
	        if(!file0.exists()){
			    //����ļ�δ��ѹ���Ƚ�ѹ�ļ�
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
	        Element docRoot=JdomUtil.build(fileInputStream1,"UTF-8").getRootElement();//�����������ļ�������document����ȡ���ڵ�
	        String dataStr=null;
	        while((dataStr=BufferedReader.readLine())!=null){
	        	if("".equals(dataStr)){
	        	     continue;
	        	}
	        	String[] strs=dataStr.split(",");
	        	if(strs.length==6){
	        		if(!docRoot.getChild("Head").getChildText("Cur_Btch_Dtl_TDnum").equals(strs[3])){
	        		   throw new MidplatException("�����ļ���ȡ���ļ������ݲ�һ�£�");
	        		}
	        		zipOutputStream.write(dataStr.getBytes("GBK"));
	        		continue;
	        	}
	        	zipOutputStream.write("\r\n".getBytes());//���з�
	        	//��ȡ�����˺Ÿ��������˺Ż�ȡ���д��������Ӧ�Ľڵ�
	        	String account=strs[4];
	        	XPath tXPath = XPath.newInstance("Detail_List/Detail[Cst_AccNo='"+account+"']");
	        	Element Detail=(Element) tXPath.selectSingleNode(docRoot);
	        	if(Detail==null){
	        		throw new MidplatException("����������ȡ�����ݲ�һ��,��˶ԣ�");
	        	}
	        	dataStr=dataStr.substring(0,dataStr.lastIndexOf(","));
	        	String responseCode=Detail.getChildText("Hst_Rsp_Cd");//������Ӧ��
	        	String responseStr=Detail.getChildText("Hst_Rsp_Inf");//������Ӧ����
	        	cLogger.info("�����˺�Ϊ:"+account+"�������ݵ���Ӧ��Ϊ:"+responseCode+"��Ӧ����Ϊ:"+responseStr);
	        	zipOutputStream.write((dataStr+responseCodeTransfer(responseCode)+","+responseStr).getBytes("GBK"));
	        }
	        zipOutputStream.flush();
	        zipOutputStream.closeEntry();
	        cLogger.info("�������ɺ��Ļ����ļ�·��Ϊ��"+downloadFileName);
	        
	        //�������ļ��ϴ���FTP������
	        boolean uploadFlag=ftp.uploadFile(uploadStr,backBatchZipPath+coreUpdateFileStr,coreUpdateFileStr);
	        if(!uploadFlag){
	        	throw new MidplatException("�ϴ������ļ�ʧ��:"+coreUpdateFileStr);
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
				cLogger.info(zipFileName+"�ļ���ѹʧ��!");
				throw new MidplatException("�ļ���ѹʧ��!");
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
	//������Ӧ��ת���ɺ�����Ӧ��
	public String responseCodeTransfer(String code){
		if("00000".equals(code)){return "0000";}//�ո��ѳɹ�
	    else if("E8208".equals(code)){return "3051";}//�����ѽ���
		else if("E8201".equals(code)){return "3039";}//�ʻ�������
		else if("E3150".equals(code)){return "3009";}//�ʻ�������
		else if("E5502".equals(code)){return "3059";}//�����ѹ�ʧ <�������ײ��������˴���> 
		else if("E4501".equals(code)){return "2005";}//�ʻ�����״̬���������
		else if("E3001".equals(code)){return "3032";}//�ұ�򳮻���𲻴���
		else if("E4500".equals(code)){return "2006";}//�ʻ�����״̬������֧ȡ
		else if("E5000".equals(code)){return "3016";}//�����ѹ�ʧ
		else if("E5002".equals(code)){return "3006";}//���ѹ�ʧ
		else if("E7102".equals(code)){return "3008";}//����
		else if("E3266".equals(code)){return "3065";}//�ͻ����Ƽ��ʧ��
		else if("E1408".equals(code)){return "3057";}//�ʺ����Ͳ���
		else if("E8301".equals(code)){return "3039";}//�����ѹ�ʧ
		else if("E2351".equals(code)){return "3004";}//��Ч����
		else if("E1085".equals(code)){return "3999";}//��ʧ��־����ȷ
		else if("E3540".equals(code)){return "3999";}//ע��״̬����ȷ
		else if("E3556".equals(code)){return "3999";}//��״̬������
		else if("SHK02".equals(code)){return "3999";}//ʹ�÷Ƿ��˺ŵ���
		else if("SDB01".equals(code)){return "3999";}//ʹ�÷Ƿ��˺ŵ���
		else if("E9999".equals(code)){return "3999";}//�����ڲ��������
		else{return "3999";}
	}
}
