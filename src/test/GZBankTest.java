package test;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;

public class GZBankTest {
	private static  Logger cLogger = Logger.getLogger(GZBankTest.class);
    public static void main(String[] args) {
		String ip="127.0.0.1";//本机ip地址
//    	String ip="10.2.0.31";//测试环境ip
		int port=35017;
		String path="D:/task/20170117/gz/core_test/32742_297_1012_in.xml";
		       //path="C:\\Users\\anico\\Desktop\\贵州银行报文\\9000103_缴费出单.xml";
		       //path="C:\\Users\\anico\\Desktop\\贵州银行报文\\9000801_保单重打.xml";
		       //path="C:\\Users\\anico\\Desktop\\贵州银行报文\\9000901_当日撤单.xml";
		String FuncFlag="9000102";//保费试算交易
//		       FuncFlag="9000103";//缴费出单
		       //FuncFlag="9000801";//保单重打
		       //FuncFlag="9000901";
//		String returnPath="D:/bank/gz/"+FuncFlag+"_out.xml";
		String returnPath="D:/task/20170117/gz/core_test/32742_297_1012_out.xml";
		
		String insureCode="006"+"   ";//目标保险公司代码
		try {
			FileInputStream input=new FileInputStream(path);
			byte[] bytes=IOTrans.toBytes(input);
			Socket socket=new Socket(ip,port);
			//socket.setSoTimeout(0);
			cLogger.info("贵州银行请求报文："+JdomUtil.toStringFmt(JdomUtil.build(bytes,"GBK")));
			cLogger.info("请求报文长度为："+bytes.length+"个字节");
			String head="";
			
			String byteLength=bytes.length+"";
			int count=6-byteLength.length();
			if(byteLength.length()<6){
			    for (int i = 0; i <count; i++) {
			    	byteLength+=" ";
				}
			}
			head=byteLength+FuncFlag+insureCode;
			cLogger.info("消息头长度为:"+head.getBytes().length);
			long oldTimeMillis=System.currentTimeMillis();
			OutputStream out=socket.getOutputStream();
			out.write(head.getBytes());
			out.write(bytes);
			out.flush();
			socket.shutdownOutput();
			InputStream in=socket.getInputStream();
			ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
			byte[] byteArray=new byte[1024*3];
			int size=0;
			while ((size=in.read(byteArray))!=-1) {
				byteArrayOutputStream.write(byteArray,0,size);
			}
			socket.shutdownInput();
			
			byte[] byteArrays=byteArrayOutputStream.toByteArray();
			String length=new String(Arrays.copyOfRange(byteArrays,0,6)).trim();
			String tranNo=new String(Arrays.copyOfRange(byteArrays,6,13)).trim();
		    String insureNo=new String(Arrays.copyOfRange(byteArrays,13,19)).trim();
		    cLogger.info("---------------------------------------------------");
			cLogger.info("报文字节 长度为:"+length);
			cLogger.info("交易码为:"+tranNo);
			cLogger.info("目标保险公司代码为:"+insureNo);
			long newTimeMillis=System.currentTimeMillis();
			cLogger.info("交易结束，共耗费："+(newTimeMillis-oldTimeMillis)/1000.0+"秒,"+(newTimeMillis-oldTimeMillis)+"毫秒");
			cLogger.info("返回给贵州银行报文："+JdomUtil.toStringFmt(JdomUtil.build(Arrays.copyOfRange(byteArrays,19,byteArrays.length))));
			socket.close();
			
			OutputStream pOs = new FileOutputStream(returnPath);
			JdomUtil.output(JdomUtil.build(Arrays.copyOfRange(byteArrays,19,byteArrays.length)), pOs);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
