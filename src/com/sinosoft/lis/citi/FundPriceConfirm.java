package com.sinosoft.lis.citi;

import jxl.format.BorderLineStyle;

import com.sinosoft.lis.excel.CreatExcel;
import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelBorder;
import com.sinosoft.lis.excel.ExcelFont;
import com.sinosoft.lis.f1print.LO_NoReal_PrintBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class FundPriceConfirm{
	public CErrors mErrors = new CErrors();// 错误处理类，每个需要错误处理的类中都放置该类

	private MMap cSubmitMMap = new MMap();
	
	private MMap cCancelMMap = new MMap();
	
	public int iSuccNo = 0;
	

	SSRS tSSRS = new SSRS(); // 公共，所有查询都可使用

	public FundPriceConfirm() {

	}

	/**
	 * 返回错误
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 * @throws MidplatException 
	 */
	public boolean submitData() throws MidplatException {
		VData mSubmitVData = new VData();
		mSubmitVData.add(getInputData());
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, ""))
		{
			throw new MidplatException(mPubSubmit.mErrors.getFirstError());
		}

		cSubmitMMap = null;
		return true;
		
	}
	
	public boolean cancelDate()throws MidplatException{
		VData cCancelVDate = new VData();
		cCancelVDate.add(getCancelData());
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(cCancelVDate, ""))
		{
			throw new MidplatException(mPubSubmit.mErrors.getFirstError());
		}

		cCancelMMap = null;
		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private MMap getInputData() {

		ExeSQL exeSql = new ExeSQL();
		String sSql = "SELECT priceeffectivedate,FUNDCODE,OFFERPRICE,BIDPRICE,MAKEDATE FROM FundPriceUpTemp fp where fp.PRICEEFFECTIVEDATE = (select max(priceeffectivedate)from FundPriceUpTemp)";				
		tSSRS = exeSql.execSQL(sSql);
		iSuccNo = tSSRS.getMaxRow();
		
		String deleteSql = "delete from FundPriceUp f where f.PriceEffectiveDate = " +tSSRS.GetText(1, 1);
		cSubmitMMap.put(deleteSql, "UPDATE");
		
		for (int i = 1; i <=tSSRS.getMaxRow(); i++) {
			String oid = PubFun1.CreateMaxNo("fp",1);
			String date = tSSRS.GetText(i, 1);
			String fundCode = tSSRS.GetText(i, 2);
			String offerprice = tSSRS.GetText(i, 3);
			String bidprice = tSSRS.GetText(i, 4);
			String makeDate = tSSRS.GetText(i, 5);
			String sql = "insert into FundPriceUp" + "(Oid,FundCode,PriceEffectiveDate,Currency,OfferPrice,BidPrice,makeDate,modifydate,extracteddate) "
			+ "Values  " 
			+ "("+ oid +",'"+fundCode+"'," + date + ",'RMB',"+ offerprice + "," + bidprice + ","+ makeDate +"," + makeDate + "," + makeDate + ")";
			cSubmitMMap.put(sql, "INSERT");
		}
		cSubmitMMap.put("delete from FundPriceUpTemp", "UPDATE");
		return cSubmitMMap;
	}
	
	
	private MMap getCancelData(){
		cCancelMMap.put("delete from FundPriceUpTemp", "UPDATE");
		return cCancelMMap;
	}
	public static void main(String[] args) {
		
	}
}
