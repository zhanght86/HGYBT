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
					WriteFile(tRowData,cFile); // 组装txt内容
				}	
				
//			if(tDTLMSG.length==0){//没有明细信息，则不生产空文件
//				WriteFile(new String[]{""},cFile);
//			}
		return true;
	}
	
	public void deleteFile(File cFile){
	 	   if(cFile.exists()){ //判断文件是否存在
	 	    if(cFile.isFile()){            //判断是否是文件
	 	    	System.out.println("删除文件!");
	 	     if(!cFile.delete()){
	 	    	 
	 	    	System.out.println(cFile.getPath()+"删除文件失败");
	 	     }                      //delete()方法 你应该知道 是删除的意思;  ==!呀！我猜对了呢！
	 	    }
	 	   cFile.delete(); //判断是否是文件有啥意义？
	 	   }else{
	 		   System.out.println("所删除的文件不存在！"+'\n'); 
	 	   } 
	 	} 
	
 public void WriteFile(String [] pRowData,File cFile) throws Exception{
		
 	cLogger.info("开始组装文件......");
		FileOutputStream fos = null;
		BufferedWriter bw = null;
			fos = new FileOutputStream(cFile,true);
			byte[] buff=new byte[]{};
			buff=getContentCont(pRowData).toString().getBytes("GBK");
			fos.write(buff,0,buff.length);
			bw = new BufferedWriter(new OutputStreamWriter(fos));
			cLogger.info("文件书写完毕，已保存到本地:"+cFile.getPath());	
			bw.newLine();
			bw.flush();
			bw.close();
			cLogger.info("文件组装结束！");
		
	}
 
 public StringBuffer getContentCont(String [] pRowData) throws Exception{
		
		StringBuffer tStringBuffer = new StringBuffer();
		
		for(int i=0; i<pRowData.length; i++){
				tStringBuffer.append(pRowData[i]);
		}
		return tStringBuffer;
	}
 
	
	
}
