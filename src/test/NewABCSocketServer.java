package test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.jdom.Document;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.newabc.util.AES;

public class NewABCSocketServer {
   public static void main(String[] args) {
	   ServerSocket cServerSocket=null;
	   try {
		     cServerSocket = new ServerSocket(30089);
	      } catch (IOException ex) {
	        return;
	      }
	      while (true)
	      {
	        Socket cSocket = null;
	        try {
	          cSocket = cServerSocket.accept();
	        //包头73位
	  		byte[] mHeadBytes = new byte[73];
	  		IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
	  		String package_head = new String(mHeadBytes);
	  		//4-12 位是报文体长度
	  		
	  		int mBodyLen = Integer.parseInt(package_head.substring(3, 12).trim()); //包体长度
	  		
	  		//保险公司代码
	  		String cInsuID=String.valueOf(Integer.parseInt(package_head.substring(12, 19).trim()));
	  		
	  	
	  		byte[] mBodyBytes = new byte[mBodyLen]; //所有的body字节不带xml声明
	  		IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
	  		cSocket.shutdownInput();
	  		/**********解密请求****************/
	  		
	  		String axx = AES.Decrypt(new String(mBodyBytes,"UTF-8"));
	  		
	  		/**********解密完成****************/
	  		
	  		System.out.println("解密后的报文:============"+axx);
	  		StringBuffer abc_xml = new StringBuffer();
	  		abc_xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	  		abc_xml.append("\n");
	  		abc_xml.append(axx);
	  		byte[] all_xml = abc_xml.toString().getBytes("UTF-8");//#
	  		Document mXmlDoc_bank = JdomUtil.build(all_xml,"UTF-8"); //#
	  		
	  		JdomUtil.print(mXmlDoc_bank);
	        } catch (IOException ex) {
	        
	          if (cSocket != null) try { cSocket.close(); } catch (IOException localIOException1) {
	            } return;
	        } catch (Throwable ex) {
	        }if (cSocket == null) continue; try { cSocket.close(); } catch (IOException localIOException2) {
	        }
	      }
}
}
