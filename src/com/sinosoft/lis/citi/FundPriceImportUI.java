package com.sinosoft.lis.citi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.log4j.Logger;

import com.sinosoft.lis.encrypt.LisIDEA;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.DateUtilEn;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class FundPriceImportUI {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 数据操作字符串 */
//	private String mOperate;
	
	public int iSuccNo = 0;
	
	public int iUpdateNo = 0;
	
//	public int iRepeatNo = 0;
	
	public int iEndNo = 0;
	
	private MMap cSubmitMMap = new MMap();
	
	public int iHandNum = 0;
	public int iRollBackNum = 0;
	
	public FundPriceImportUI(){
		
	}
	
	public void upByFileName(String sourcefile)throws MidplatException{
		
		Sheet sheet = null;
	try {
		//getCell(i,j)  => 第j行的第i列 不要搞反了 
		sheet = Workbook.getWorkbook(new FileInputStream(sourcefile)).getSheet(0);
	} catch (Exception e) {
		mErrors.addOneError("上传文件格式有误，应为Excel文件，请重新选择上传");
//		throw new MidplatException("上传文件格式有误，应为Excel文件，请重新选择上传");
	}
//		String u1 = sheet.getCell(5,3).getContents();
		Integer makeDate = DateUtil.getCur8Date();
		cSubmitMMap.put("delete from FundPriceUpTemp","UPDATE");
//		System.out.println("sheet.getRows()--------------------"+sheet.getRows());
		for (int j = 3; j <= sheet.getRows(); j++) {//行
			
			String temp = sheet.getCell(0,j).getContents();
			System.out.println("投连账户价格的第一列的日期值: "+temp);
			if(temp==null||"".equals(temp)){
				iEndNo = j+1;
				break;
			}
//			System.out.println(DateUtilEn.dateChange(temp)+"-------------------------------");
			Integer date = null;
			try {
				date = DateUtilEn.enDateTo8Date(DateUtilEn.dateChange(temp));
			} catch (Exception e) {
				mErrors.addOneError("上传文件格式有误，第"+(j+1)+"行的第A列的日期格式应为dd-mmm-yy");
				throw new MidplatException("上传文件格式有误，第一列的第"+(j+1)+"行的日期格式应为dd-mmm-yy");
			}
			String oid1 = PubFun1.CreateMaxNo("Oid",1);
			String oid2 = PubFun1.CreateMaxNo("Oid",1);
			String oid3 = PubFun1.CreateMaxNo("Oid",1);
			String oid4 = PubFun1.CreateMaxNo("Oid",1);
			
			
			
			Double b1=0.0;
			Double o1=0.0;
			try {
				String bid1 = sheet.getCell(6,j).getContents();
				b1 = Double.parseDouble(bid1);	
			} catch (Exception e) {
				mErrors.addOneError("上传文件格式有误，第"+(j+1)+"行的第G列的值应为数字");
				throw new MidplatException("上传文件格式有误，第"+(j+1)+"行的第G列的值应为数字");
			}
			try {
				String offer1 = sheet.getCell(7,j).getContents();
				o1 = Double.parseDouble(offer1);
			} catch (Exception e) {
				mErrors.addOneError("上传文件格式有误，第"+(j+1)+"行的第H列的值应为数字");
				throw new MidplatException("上传文件格式有误，第"+(j+1)+"行的第H列的值应为数字");
			}
			
			
			
			Double b2 = 0.0;
			Double o2 = 0.0;
			try {
				String bid2 = sheet.getCell(8,j).getContents();
				b2 = Double.parseDouble(bid2);	
			} catch (Exception e) {
				mErrors.addOneError("上传文件格式有误，第"+(j+1)+"行的第I列的值应为数字");
				throw new MidplatException("上传文件格式有误，第"+(j+1)+"行的第I列的值应为数字");
			}
			try {
				String offer2 = sheet.getCell(9,j).getContents();
				o2 = Double.parseDouble(offer2);
			} catch (Exception e) {
				mErrors.addOneError("上传文件格式有误，第"+(j+1)+"行的第J列的值应为数字");
				throw new MidplatException("上传文件格式有误，第"+(j+1)+"行的第J列的值应为数字");
			}
		
			
			
			Double b3 =0.0;
			Double o3 =0.0;
			try {
				String bid3 = sheet.getCell(10,j).getContents();
				b3 = Double.parseDouble(bid3);	
			} catch (Exception e) {
				mErrors.addOneError("上传文件格式有误，第"+(j+1)+"行的第K列的值应为数字");
				throw new MidplatException("上传文件格式有误，第"+(j+1)+"行的第K列的值应为数字");
			}
			try {
				String offer3 = sheet.getCell(11,j).getContents();
				o3 = Double.parseDouble(offer3);
			} catch (Exception e) {
				mErrors.addOneError("上传文件格式有误，第"+(j+1)+"行的第L列的值应为数字");
				throw new MidplatException("上传文件格式有误，第"+(j+1)+"行的第L列的值应为数字");
			}
			
			
			
			Double b4 = 0.0;
			Double o4 = 0.0;
			try {
				String bid4 = sheet.getCell(14,j).getContents();
				b4 = Double.parseDouble(bid4);	
			} catch (Exception e) {
				mErrors.addOneError("上传文件格式有误，第"+(j+1)+"行的第14列的值应为数字");
				throw new MidplatException("上传文件格式有误，第"+(j+1)+"行的第14列的值应为数字");
			}
			try {
				String offer4 = sheet.getCell(15,j).getContents();
				o4 = Double.parseDouble(offer4);
			} catch (Exception e) {
				mErrors.addOneError("上传文件格式有误，第"+(j+1)+"行的第P列的值应为数字");
				throw new MidplatException("上传文件格式有误，第"+(j+1)+"行的第P列的值应为数字");
			}
			
			String sql = "insert into FundPriceUpTemp" + "(Oid,FundCode,PriceEffectiveDate,BidPrice,OfferPrice,makeDate) "
			+ "Values  " 
			+ "("+ oid1 +",'U1ZY'," + date + ","+ b1 + "," + o1 + ","+ makeDate +")";
			cSubmitMMap.put(sql, "INSERT");
			
			String sql2 = "insert into FundPriceUpTemp" + "(Oid,FundCode,PriceEffectiveDate,BidPrice,OfferPrice,makeDate) "
			+ "Values  " 
			+ "("+ oid2 +",'U2WJ'," + date + ","+ b2 + "," + o2 + ","+ makeDate +")";
			cSubmitMMap.put(sql2, "INSERT");
			
			String sql3 = "insert into FundPriceUpTemp" + "(Oid,FundCode,PriceEffectiveDate,BidPrice,OfferPrice,makeDate) "
			+ "Values  " 
			+ "("+ oid3 +",'U3AX'," + date + ","+ b3 + "," + o3 + ","+ makeDate +")";
			cSubmitMMap.put(sql3, "INSERT");
			
			String sql4 = "insert into FundPriceUpTemp" + "(Oid,FundCode,PriceEffectiveDate,BidPrice,OfferPrice,makeDate) "
			+ "Values  " 
			+ "("+ oid4 +",'U8HX'," + date + ","+ b4 + "," + o4 + ","+ makeDate +")";			
			cSubmitMMap.put(sql4, "INSERT");
			iSuccNo++;
		}
		iSuccNo = iSuccNo*4;
		System.out.println(iSuccNo);
	}

	/**
	 * 传输数据的公共方法
	 * @throws MidplatException 
	 */
	public boolean submitData() throws MidplatException{

		VData mSubmitVData = new VData();
		mSubmitVData.add(getSubmitMMap());
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, ""))
		{
//			return false;
			throw new MidplatException(mPubSubmit.mErrors.getFirstError());
		}

		cSubmitMMap = null;
		return true;
	}

	public MMap getSubmitMMap() {
		return cSubmitMMap;
	}
		
	public static void main(String[] args) {
	}
}
