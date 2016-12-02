package com.sinosoft.midplat.icbc.bat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.crypto.Cipher;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.bat.ToBankBalance;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.icbc.IcbcConf;
import com.sinosoft.midplat.icbc.net.IcbcKeyCache;
import com.sinosoft.utility.ExeSQL;

public class ToIcbcBaoQuanFileBlc extends ToBankBalance {
	public ToIcbcBaoQuanFileBlc() {
		super(IcbcConf.newInstance(), "1205");
	}

	protected String getFileName() {
		Element mBankEle = cThisConfRoot.getChild("bank");
		return "ENYIAAS" + mBankEle.getAttributeValue("insu")+"_"
				+ mBankEle.getAttributeValue(id)+"_"
				+ DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "_UPDATESTATUS.txt";
	}
	
	public String encode(String ttFileName) throws Exception{
		String ttFileNameDES = ttFileName+".des";
		try{
			byte[] mInBytes = IsToBytes(
					new FileInputStream(ttFileName));
	
			Cipher mCipher = Cipher.getInstance("DES");
			mCipher.init(Cipher.ENCRYPT_MODE, IcbcKeyCache.newInstance().getKey());
			byte[] mInBytesDES = mCipher.doFinal(mInBytes);
			
			OutputStream mFos = new FileOutputStream(ttFileNameDES);
			mFos.write(mInBytesDES);
			mFos.close();
		}catch (Exception e) {
			e.printStackTrace();
			throw new MidplatException("加密出错");
		}
		return ttFileNameDES;
	}
	
	/**
	 * 生成投保单状态变更文件
	 * @param cOutXmlDoc
	 * @return
	 */
	public boolean createFile(Document cOutXmlDoc,int date){
		cLogger.info("Into ToIcbcBaoQuanFileBlc.createFile()...");
		
		try {
		List<Element> mPolicyDetailEles = cOutXmlDoc.getRootElement().getChild(Body).getChildren("Detail");
		
		String[][] tStateDTLMSG = new String[mPolicyDetailEles.size()][15];
		String sql=null;
		for(int i = 0;i<mPolicyDetailEles.size();i++){	
			Element dtl = (Element) mPolicyDetailEles.get(i);
			
			String sTranDate = dtl.getChildText(TranDate);
			String sBusiType = dtl.getChildText("BusiType");
			String sModifyDate = dtl.getChildText("ModifyDate");
			String sInsuId = dtl.getChildText("InsuId");
			//核心的NodeNo是地区码（5位）+网点码（位）的，所以返回给银行地区码的话需截取前五位地区码即可
			String sZoneNo = dtl.getChildText(NodeNo).substring(0,5);
			String sProposalPrtNo = dtl.getChildText(ProposalPrtNo);
			String sContNo = dtl.getChildText("ContNo");
			String sAppntName  =dtl.getChildText("AppntName");
			String sAppntIDType  =dtl.getChildText("AppntIDType");
			String sAppntIDNo  =dtl.getChildText("AppntIDNo");
			String sState = dtl.getChildText(State);
			String sInsuEndDate  = dtl.getChildText("InsuEndDate");
			String sBak1 = dtl.getChildText("Bak1");
			String sBak2 = dtl.getChildText("Bak2");
			String sBak3 = dtl.getChildText("Bak3");
			String sBak4 = dtl.getChildText("Bak4");
			
			cLogger.info("第"+(i+1)+"行数据:BusiType:"+sBusiType+",ModifyDate:"+sModifyDate+",InsuId:"+
					sInsuId+",ZoneNo:"+sZoneNo+",ProposalPrtNo:"+sProposalPrtNo+",ContNo:"+
					sContNo+",AppntName:"+sAppntName+",AppntIDType:"+sAppntIDType+",AppntIDNo:"+
					sAppntIDNo+",State:"+sState+",InsuEndDate:"+sInsuEndDate+",Bak1:"+sBak1);
			
			tStateDTLMSG[i]=new String[]{sTranDate+"|",sBusiType+"|",sModifyDate+"|",sInsuId+"|",sZoneNo+"|",sProposalPrtNo+"|",sContNo+"|",
										sAppntName+"|",sAppntIDType+"|",sAppntIDNo+"|",sState+"|",sInsuEndDate+"|",sBak1+"|",
										sBak2+"|",sBak3+"|",sBak4+"|"};
		}
		
		
		String tranDate = String.valueOf(date);
		String fileName = getFileName();
		
		CreateTxtFile createFile = new CreateTxtFile();
		
			createFile.writeFile(tStateDTLMSG,"1205",tranDate,fileName);
		} catch (Exception e) {
			cLogger.info("生成保全数据回传文件失败！"+e.getMessage());
			e.printStackTrace();
			return false;
		}
		cLogger.info("Out ToIcbcBaoQuanFileBlc.createFile()!");
		return true;
	}

	

	public static void main(String[] args) throws Exception {}
}
