package com.sinosoft.midplat.icbc.bat;


import java.io.ByteArrayOutputStream;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import test.security.DESTest;

import com.sinosoft.midplat.bat.ToBankBalance;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.icbc.IcbcConf;
import com.sinosoft.midplat.icbc.net.IcbcKeyCache;
import com.sinosoft.utility.ExeSQL;

public class ToIcbcResultFileBlc extends ToBankBalance {
	public ToIcbcResultFileBlc() {
		super(IcbcConf.newInstance(), "1203");
	}
	
	protected String getFileName() {
		Element mBankEle = cThisConfRoot.getChild("bank");
		return "ENYIAAS"+mBankEle.getAttributeValue("insu")+mBankEle.getAttributeValue(id)+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+"03.txt";
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
	 * 生产结果文件
	 * @param cOutXmlDoc
	 * @return
	 */
	public boolean createFile(Document cOutXmlDoc,int date){
		cLogger.info("Into IcbcNoRealResultBack.createResultFile...");
		try {
		List<Element> mResultDetailEles = cOutXmlDoc.getRootElement().getChild(Body).getChildren("ResultDetail");
		
		String[][] tResultDTLMSG = new String[mResultDetailEles.size()][56];
		String sql=null;
		for(int i = 0;i<mResultDetailEles.size();i++){	
			//对账文件中每一行的信息
			StringBuffer sLineMsg = new StringBuffer();
			
			Element dtl = (Element) mResultDetailEles.get(i);
			
			String sZoneNo = dtl.getChildText(NodeNo).substring(0,5);//核心的NodeNo是地区码（5位）+网点码（位）的，所以返回给银行地区码的话需截取前五位地区码即可
			String sInsuId  =dtl.getChildText("InsuId");
			String sProposalPrtNo = dtl.getChildText(ProposalPrtNo);
			String sTranNo=null;
			sql="select BankTranno from NoRealBlcDtl where proposalPrtNo='"+sProposalPrtNo+"'";
			sTranNo=new ExeSQL().getOneValue(sql);
			String sContNo  =dtl.getChildText(ContNo);
			String sResult = dtl.getChildText(Result);
			String sReMark  = dtl.getChildText("ReMark");
			String sTotalPrem = dtl.getChildText("TotalPrem");
			String sInsuName = dtl.getChildText("InsuName");
			String sInsuIDType = dtl.getChildText("InsuIDType");
			String sInsuIDNo = dtl.getChildText("InsuIDNo");
			String sMainRiskCode = dtl.getChildText("MainRiskCode");
			String sRiskState = dtl.getChildText("RiskState");
			String sMult = dtl.getChildText(Mult);
			String sPrem = dtl.getChildText(Prem);
			String sAmnt = dtl.getChildText(Amnt);
			String sInsuYearFlag = dtl.getChildText(InsuYearFlag);
			String sInsuYear = dtl.getChildText(InsuYear);
			String sPayEndYearFlag = dtl.getChildText(PayEndYearFlag);
			String sPayEndYear = dtl.getChildText(PayEndYear);
			String sPayIntv  = dtl.getChildText(PayIntv);
			
			sLineMsg.append(sZoneNo+"|").append(sInsuId+"|").append(sTranNo+"|").append(sProposalPrtNo+"|").append(sContNo+"|")
					.append(sResult+"|").append(sReMark+"|").append(sTotalPrem+"|").append(sInsuName+"|").append(sInsuIDType+"|")
					.append(sInsuIDNo+"|").append(sPayIntv+"|").append(sMainRiskCode+"|").append(sRiskState+"|").append(sMult+"|").append(sPrem+"|")
					.append(sAmnt+"|").append(sInsuYearFlag+"|").append(sInsuYear+"|").append(sPayEndYearFlag+"|").append(sPayEndYear+"|");
			
			//处理附加险的信息
			List<Element> subRiskELes = dtl.getChild("List").getChildren(Risk);
			for(int j = 0;j<subRiskELes.size();j++){	
				Element subDtl = (Element) subRiskELes.get(j);
				
				String sRiskCode = subDtl.getChildText("RiskCode");
				String sRiskState2 = subDtl.getChildText("RiskState");
				String sMult2 = subDtl.getChildText(Mult);
				String sPrem2 = subDtl.getChildText(Prem);
				String sAmnt2 = subDtl.getChildText(Amnt);
				String sInsuYearFlag2 = subDtl.getChildText(InsuYearFlag);
				String sInsuYear2 = subDtl.getChildText(InsuYear);
				String sPayEndYearFlag2 = subDtl.getChildText(PayEndYearFlag);
				String sPayEndYear2 = subDtl.getChildText(PayEndYear);
				
				sLineMsg.append(sRiskCode+"|").append(sRiskState2+"|").append(sMult2+"|").append(sPrem2+"|")
				.append(sAmnt2+"|").append(sInsuYearFlag2+"|").append(sInsuYear2+"|").append(sPayEndYearFlag2+"|").append(sPayEndYear2+"|");
			}
			
			if(subRiskELes.size()==0){
				sLineMsg.append("||||||||||||||||||||||||||||||||||||");
			}else if(subRiskELes.size()==1){
				sLineMsg.append("|||||||||||||||||||||||||||");
			}else if(subRiskELes.size()==2){
				sLineMsg.append("||||||||||||||||||");
			}else if(subRiskELes.size()==3){
				sLineMsg.append("|||||||||");
			}
			
			
			cLogger.info("第"+(i+1)+"行数据（主险部分）:NodeNo:"+sZoneNo+",InsuId:"+sInsuId+",TranNo:"+
					sTranNo+",ProposalPrtNo:"+sProposalPrtNo+",ContNo:"+sContNo+",Result:"+
					sResult+",ReMark:"+sReMark+",TotalPrem:"+sTotalPrem+",InsuName:"+sInsuName+",InsuIDType:"+
					sInsuIDType+",InsuIDNo:"+sInsuIDNo+",MainRiskCode:"+sMainRiskCode+",RiskState:"+sRiskState+",Mult:" +
					sMult+",Prem:"+sPrem+",Amnt:"+sAmnt+",InsuYearFlag:"+sInsuYearFlag+",InsuYear:"+sInsuYear+",PayEndYearFlag:"+
					sPayEndYearFlag+",PayEndYear:"+sPayEndYear);
			tResultDTLMSG[i]=new String[]{sLineMsg.toString()};
		}
		
		
		String tranDate = String.valueOf(date);
		String fileName = "ENYIAAS050"+"01"+tranDate+"03.txt";
		
		CreateTxtFile createFile = new CreateTxtFile();
		
			createFile.writeFile(tResultDTLMSG,"1203",tranDate,fileName);
		} catch (Exception e) {
			cLogger.info("生产投保单结果文件失败！"+e.getMessage());
			e.printStackTrace();
			return false;
		}
		cLogger.info("Out IcbcNoRealResultBack.createResultFile()!");
		return true;
	}
	
	public static void main(String[] args) throws Exception {}
}
