package com.sinosoft.midplat.icbc.net;

import java.net.Socket;

import javax.crypto.Cipher;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.SocketNetImpl;
import com.sinosoft.utility.ExeSQL;

public class IcbcNetImpl extends SocketNetImpl {
	private String cOutFuncFlag = null;
	private String cInsuID = null;
	
	public IcbcNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException {
		super(pSocket, pThisConfRoot);
	}
	
	public Document receive() throws Exception {
		cLogger.info("Into IcbcNetImp.receive()...");
		
		//处理报文头
		byte[] mHeadBytes = new byte[16];
		IOTrans.readFull(mHeadBytes, cSocket.getInputStream());
		int mBodyLength = Integer.parseInt(new String(mHeadBytes, 0, 6).trim());
		cLogger.debug("请求报文长度：" + mBodyLength);
		cOutFuncFlag = new String(mHeadBytes, 6, 4).trim();
		cLogger.info("交易代码：" + cOutFuncFlag);
		cInsuID = new String(mHeadBytes, 10, 6).trim();
		//处理报文体
		byte[] mBodyBytes = new byte[mBodyLength];
		IOTrans.readFull(mBodyBytes, cSocket.getInputStream());
		cSocket.shutdownInput();
		
		cLogger.info("开始解密...");
		mBodyBytes = decode(mBodyBytes);
		cLogger.info("解密成功！");
		Document mXmlDoc = JdomUtil.build(mBodyBytes);
		Element mRootEle = mXmlDoc.getRootElement();
		
		XPath mXPath = XPath.newInstance(
				"business/funcFlag[@outcode='" + cOutFuncFlag + "']");
		cFuncFlag = mXPath.valueOf(cThisConfRoot);
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(cFuncFlag)
			.append("_in.xml");
		SaveMessage.save(mXmlDoc, cTranComEle.getText(), mSaveName.toString());
		cLogger.info("保存报文完毕！"+mSaveName);
		  
		      
		//生成标准报文头



		Element mClientIpEle = new Element(ClientIp);
		mClientIpEle.setText(cClientIp);
		Element mFuncFlagEle = new Element(FuncFlag);
		mFuncFlagEle.setText(cFuncFlag);
		Element mInNoDoc = new Element("InNoDoc");
		mInNoDoc.setText(mSaveName.toString());
		Element mAgentCom = new Element(AgentCom);
		Element mAgentCode = new Element(AgentCode);
		Element mHeadEle = new Element(Head);
		mHeadEle.addContent(mClientIpEle);
		mHeadEle.addContent(cTranComEle);
		mHeadEle.addContent(mFuncFlagEle);  
		mHeadEle.addContent(mAgentCom);
		mHeadEle.addContent(mAgentCode);
		mHeadEle.addContent(mInNoDoc);
		mRootEle.addContent(mHeadEle);
		cLogger.info("Out IcbcNetImp.receive()!");
		return mXmlDoc;
	}
	
	public void send(Document pOutNoStd) throws Exception {
		cLogger.info("Into IcbcNetImp.send()...");
		
		/*Start-组织返回报文头*/
		Element mRootEle = pOutNoStd.getRootElement();
		
		Element mHeadEle = (Element) mRootEle.getChild(Head).detach();
		Element mResultCode = new Element("ResultCode");
		int mFlagInt = Integer.parseInt(mHeadEle.getChildText(Flag));
		if (CodeDef.RCode_OK == mFlagInt) {
			mResultCode.setText("0000");
		} else if (CodeDef.RCode_RenHe == mFlagInt) {
			mResultCode.setText("1222");
		} else {
			mResultCode.setText("1234");
		}
		
		Element mResultInfoDesc = new Element("ResultInfoDesc");
		mResultInfoDesc.setText(mHeadEle.getChildText(Desc));
		Element mResultInfo = new Element("ResultInfo");
		mResultInfo.addContent(mResultInfoDesc);
		
		Element mTransResult = new Element("TransResult");
		mTransResult.addContent(mResultCode);
		mTransResult.addContent(mResultInfo);
		
		if ("0001".equals(cOutFuncFlag)) {
			mRootEle.addContent(0, mTransResult);
		} else {
			mRootEle.getChild("TXLifeResponse").addContent(0, mTransResult);
		}
		/*End-组织返回报文头*/
		
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(cFuncFlag)
			.append("_out.xml");
		SaveMessage.save(pOutNoStd, cTranComEle.getText(), mSaveName.toString());
		cLogger.info("保存报文完毕！"+mSaveName);
		cOutNoStdDoc = mSaveName.toString();
		
//		byte[] mBodyBytes = JdomUtil.toBytes(pOutNoStd);
//		
//		cLogger.info("开始加密...");
//		mBodyBytes = encode(mBodyBytes);
//		cLogger.info("加密成功！");
//		
//		byte[] mHeadBytes = new byte[16];
//		//报文体长度
//		String mLengthStr = String.valueOf(mBodyBytes.length);
//		cLogger.info("返回报文长度：" + mLengthStr);
//		byte[] mLengthBytes = mLengthStr.getBytes();
//		System.arraycopy(mLengthBytes, 0, mHeadBytes, 0, mLengthBytes.length);
//		//交易代码
//		byte[] mFuncFlagBytes = cOutFuncFlag.getBytes();
//		System.arraycopy(mFuncFlagBytes, 0, mHeadBytes, 6, mFuncFlagBytes.length);
//		//公司代码
//		byte[] mInsuIDBytes = cInsuID.getBytes();
//		System.arraycopy(mInsuIDBytes, 0, mHeadBytes, 10, mInsuIDBytes.length);
//		
//		cSocket.getOutputStream().write(mHeadBytes);	//发送报文头
//		cSocket.getOutputStream().write(mBodyBytes);	//发送报文体
//		cSocket.shutdownOutput();
//		
//		cLogger.info("Out IcbcNetImp.send()!");
		
		String sOutNoStd = JdomUtil.toStringFmtNull(pOutNoStd,"GBK");
		System.out.println("缩进后："+sOutNoStd);
	    byte[] mBodyBytes = sOutNoStd.getBytes();
	
	    cLogger.info("开始加密...");
	    mBodyBytes = encode(mBodyBytes);
	    cLogger.info("加密成功！");
	
//	    byte[] mHeadBytes = new byte[16];
	    //报文体长度
	    String mLengthStr = String.valueOf(mBodyBytes.length);
//	    cLogger.info("返回报文长度：" + mLengthStr);
//	    byte[] mLengthBytes = mLengthStr.getBytes();
//	    System.arraycopy(mLengthBytes, 0, mHeadBytes, 0, mLengthBytes.length);
//	    //交易代码
//	    byte[] mFuncFlagBytes = cOutFuncFlag.getBytes();
//	    System.arraycopy(mFuncFlagBytes, 0, mHeadBytes, 6, mFuncFlagBytes.length);
//	    //公司代码
//	    byte[] mInsuIDBytes = cInsuID.getBytes();
//	    System.arraycopy(mInsuIDBytes, 0, mHeadBytes, 10, mInsuIDBytes.length);
	
	    
	    byte array[] = new byte[16]; 
	      int m = 0;
	      
	      //包体长度
        byte arrayPackHeadLength[] = mLengthStr.getBytes();
        cLogger.info("返回报文长度：" + mLengthStr);
	      for(int i = 0; i < 6; i++)
	      {
	    	 if(i < arrayPackHeadLength.length)
	    	 {
	           array[m] = arrayPackHeadLength[i];
	    	 }
	    	 else if(i >= arrayPackHeadLength.length){
	    		 byte[] spacebyte = " ".getBytes();
	    		 array[m] = spacebyte[0];
	    	 }
	    	 m++;
	      }     
	      
	      byte arrayPackFunctionFlag[] = cOutFuncFlag.getBytes();
	      for(int i = 0; i < 4; i++)
	      {	     
	         if(i < arrayPackFunctionFlag.length)
        	 {
               array[m] = arrayPackFunctionFlag[i];
        	 }
	         else if(i >= arrayPackFunctionFlag.length){
	    		 byte[] spacebyte = " ".getBytes();
	    		 array[m] = spacebyte[0];
	    	 }
	         m++;
	      }      
	      
	      //目标保险公司代码
	      System.out.println("保险公司编码"+cInsuID.getBytes());
	      byte arraytPackInsuID[] = cInsuID.getBytes();
	      for(int i = 0; i < 6; i++)
	      {
	    	  System.out.println(m);
	    	  System.out.println("arraytPackInsuID.length:"+arraytPackInsuID.length);
	         if(i < arraytPackInsuID.length)
        	 {
               array[m] = arraytPackInsuID[i];
               if(arraytPackInsuID[i] == 0){
              	 byte[] spacebyte = " ".getBytes();
  	    		 array[m] = spacebyte[0];
               }
        	 }
	         else if(i >= arraytPackInsuID.length){
	    		 byte[] spacebyte = " ".getBytes();
	    		 array[m] = spacebyte[0];
	    		 System.out.println(m +":"+array[m]);
	    	 }
	         m++;
	         
	      } 
	      
	      cLogger.info("返回报文长度：" + new String(array, 0, 6));
			cLogger.info("交易代码：" + new String(array, 6, 4));
			cLogger.info("保险公司代码：" + new String(array, 10, 6));
	    cSocket.getOutputStream().write(array);	//发送报文头
	    cSocket.getOutputStream().write(mBodyBytes);	//发送报文体
	    cSocket.shutdownOutput();
	
	    cLogger.info("Out IcbcNetImp.send()!");
	}
	
	/**
	 * 解密
	 */
	private byte[] decode(byte[] pBytes) throws Exception {
		Cipher mCipher = Cipher.getInstance("DES");
		mCipher.init(Cipher.DECRYPT_MODE, IcbcKeyCache.newInstance().getKey());
		
		return mCipher.doFinal(pBytes);
	}
	
	/**
	 * 加密
	 */
	private byte[] encode(byte[] pBytes) throws Exception {
		Cipher mCipher = Cipher.getInstance("DES");
		mCipher.init(Cipher.ENCRYPT_MODE, IcbcKeyCache.newInstance().getKey());
		
		return mCipher.doFinal(pBytes);
	}
}