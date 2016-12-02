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

	/** �������࣬ÿ����Ҫ����������ж����ø��� */
	public CErrors mErrors = new CErrors();

	/** ���ݲ����ַ��� */
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
		//getCell(i,j)  => ��j�еĵ�i�� ��Ҫ�㷴�� 
		sheet = Workbook.getWorkbook(new FileInputStream(sourcefile)).getSheet(0);
	} catch (Exception e) {
		mErrors.addOneError("�ϴ��ļ���ʽ����ӦΪExcel�ļ���������ѡ���ϴ�");
//		throw new MidplatException("�ϴ��ļ���ʽ����ӦΪExcel�ļ���������ѡ���ϴ�");
	}
//		String u1 = sheet.getCell(5,3).getContents();
		Integer makeDate = DateUtil.getCur8Date();
		cSubmitMMap.put("delete from FundPriceUpTemp","UPDATE");
//		System.out.println("sheet.getRows()--------------------"+sheet.getRows());
		for (int j = 3; j <= sheet.getRows(); j++) {//��
			
			String temp = sheet.getCell(0,j).getContents();
			System.out.println("Ͷ���˻��۸�ĵ�һ�е�����ֵ: "+temp);
			if(temp==null||"".equals(temp)){
				iEndNo = j+1;
				break;
			}
//			System.out.println(DateUtilEn.dateChange(temp)+"-------------------------------");
			Integer date = null;
			try {
				date = DateUtilEn.enDateTo8Date(DateUtilEn.dateChange(temp));
			} catch (Exception e) {
				mErrors.addOneError("�ϴ��ļ���ʽ���󣬵�"+(j+1)+"�еĵ�A�е����ڸ�ʽӦΪdd-mmm-yy");
				throw new MidplatException("�ϴ��ļ���ʽ���󣬵�һ�еĵ�"+(j+1)+"�е����ڸ�ʽӦΪdd-mmm-yy");
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
				mErrors.addOneError("�ϴ��ļ���ʽ���󣬵�"+(j+1)+"�еĵ�G�е�ֵӦΪ����");
				throw new MidplatException("�ϴ��ļ���ʽ���󣬵�"+(j+1)+"�еĵ�G�е�ֵӦΪ����");
			}
			try {
				String offer1 = sheet.getCell(7,j).getContents();
				o1 = Double.parseDouble(offer1);
			} catch (Exception e) {
				mErrors.addOneError("�ϴ��ļ���ʽ���󣬵�"+(j+1)+"�еĵ�H�е�ֵӦΪ����");
				throw new MidplatException("�ϴ��ļ���ʽ���󣬵�"+(j+1)+"�еĵ�H�е�ֵӦΪ����");
			}
			
			
			
			Double b2 = 0.0;
			Double o2 = 0.0;
			try {
				String bid2 = sheet.getCell(8,j).getContents();
				b2 = Double.parseDouble(bid2);	
			} catch (Exception e) {
				mErrors.addOneError("�ϴ��ļ���ʽ���󣬵�"+(j+1)+"�еĵ�I�е�ֵӦΪ����");
				throw new MidplatException("�ϴ��ļ���ʽ���󣬵�"+(j+1)+"�еĵ�I�е�ֵӦΪ����");
			}
			try {
				String offer2 = sheet.getCell(9,j).getContents();
				o2 = Double.parseDouble(offer2);
			} catch (Exception e) {
				mErrors.addOneError("�ϴ��ļ���ʽ���󣬵�"+(j+1)+"�еĵ�J�е�ֵӦΪ����");
				throw new MidplatException("�ϴ��ļ���ʽ���󣬵�"+(j+1)+"�еĵ�J�е�ֵӦΪ����");
			}
		
			
			
			Double b3 =0.0;
			Double o3 =0.0;
			try {
				String bid3 = sheet.getCell(10,j).getContents();
				b3 = Double.parseDouble(bid3);	
			} catch (Exception e) {
				mErrors.addOneError("�ϴ��ļ���ʽ���󣬵�"+(j+1)+"�еĵ�K�е�ֵӦΪ����");
				throw new MidplatException("�ϴ��ļ���ʽ���󣬵�"+(j+1)+"�еĵ�K�е�ֵӦΪ����");
			}
			try {
				String offer3 = sheet.getCell(11,j).getContents();
				o3 = Double.parseDouble(offer3);
			} catch (Exception e) {
				mErrors.addOneError("�ϴ��ļ���ʽ���󣬵�"+(j+1)+"�еĵ�L�е�ֵӦΪ����");
				throw new MidplatException("�ϴ��ļ���ʽ���󣬵�"+(j+1)+"�еĵ�L�е�ֵӦΪ����");
			}
			
			
			
			Double b4 = 0.0;
			Double o4 = 0.0;
			try {
				String bid4 = sheet.getCell(14,j).getContents();
				b4 = Double.parseDouble(bid4);	
			} catch (Exception e) {
				mErrors.addOneError("�ϴ��ļ���ʽ���󣬵�"+(j+1)+"�еĵ�14�е�ֵӦΪ����");
				throw new MidplatException("�ϴ��ļ���ʽ���󣬵�"+(j+1)+"�еĵ�14�е�ֵӦΪ����");
			}
			try {
				String offer4 = sheet.getCell(15,j).getContents();
				o4 = Double.parseDouble(offer4);
			} catch (Exception e) {
				mErrors.addOneError("�ϴ��ļ���ʽ���󣬵�"+(j+1)+"�еĵ�P�е�ֵӦΪ����");
				throw new MidplatException("�ϴ��ļ���ʽ���󣬵�"+(j+1)+"�еĵ�P�е�ֵӦΪ����");
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
	 * �������ݵĹ�������
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
