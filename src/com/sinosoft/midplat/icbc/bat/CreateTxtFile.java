package com.sinosoft.midplat.icbc.bat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.icbc.IcbcConf;

public class CreateTxtFile {
	
	protected Logger cLogger = Logger.getLogger(getClass());

	public boolean writeFile(String[][] tDTLMSG,String tFuncflag,String sTranDate,String fileName) throws Exception {
		cLogger.info("into CreateTxtFile.writeFile().............");
		cLogger.info("tFuncflag:"+tFuncflag+";sTranDate:"+sTranDate);
		
		IcbcConf icbcConf = IcbcConf.newInstance();
		XPath tXPath = XPath.newInstance(
				"business[funcFlag='" + tFuncflag+ "']");
		Element tBusinessEle = (Element) tXPath.selectSingleNode(icbcConf.getConf().getRootElement());
		
		String filePath = tBusinessEle.getChildText("localDir");	
		
		System.out.println("filePath:---"+filePath+";fileName:---"+fileName);
		File newDir = new File(filePath);
		if(!newDir.exists()){
			newDir.mkdirs();
		}
			
		File cFile = new File(filePath+fileName);
			
			if(cFile.exists()){
				deleteFile(cFile);
			}				
				for (int i = 0; i <tDTLMSG.length; i++) {					
					String [] tRowData = tDTLMSG[i];					
					WriteFile(tRowData,cFile); // ��װtxt����
				}	
				
//			if(tDTLMSG.length==0){//û����ϸ��Ϣ�����������ļ�
//				WriteFile(new String[]{""},cFile);
//			}
		return true;
	}
	
	public void deleteFile(File cFile){
	 	   if(cFile.exists()){ //�ж��ļ��Ƿ����
	 	    if(cFile.isFile()){            //�ж��Ƿ����ļ�
	 	    	System.out.println("ɾ���ļ�!");
	 	     if(!cFile.delete()){
	 	    	 
	 	    	System.out.println(cFile.getPath()+"ɾ���ļ�ʧ��");
	 	     }                      //delete()���� ��Ӧ��֪�� ��ɾ������˼;  ==!ѽ���Ҳ¶����أ�
	 	    }
	 	   cFile.delete(); //�ж��Ƿ����ļ���ɶ���壿
	 	   }else{
	 		   System.out.println("��ɾ�����ļ������ڣ�"+'\n'); 
	 	   } 
	 	} 
	
 public void WriteFile(String [] pRowData,File cFile) throws Exception{
		
 	cLogger.info("��ʼ��װ�ļ�......");
		FileOutputStream fos = null;
		BufferedWriter bw = null;
			fos = new FileOutputStream(cFile,true);
			byte[] buff=new byte[]{};
			buff=getContentCont(pRowData).toString().getBytes("GBK");
			fos.write(buff,0,buff.length);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			cLogger.info("�ļ���д��ϣ��ѱ��浽����:"+cFile.getPath());	
			bw.newLine();
			bw.flush();
			bw.close();
			cLogger.info("�ļ���װ������");
		
	}
 
 public StringBuffer getContentCont(String [] pRowData) throws Exception{
		
		StringBuffer tStringBuffer = new StringBuffer();
		
		for(int i=0; i<pRowData.length; i++){
				tStringBuffer.append(pRowData[i]);
		}
		return tStringBuffer;
	}
 
	
	
}
