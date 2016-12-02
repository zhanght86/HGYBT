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
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class CitiHand {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 数据操作字符串 */	
	public int iSuccNo = 0;
		
	public int iEndNo = 0;
	
	SSRS tSSRS = new SSRS(); 
	VData inputParameters = new VData();
	private MMap cSubmitMMap = new MMap();
	private String fileTypeCode="";
	private String startDate = "";
	private String endDate = "";
	private String modifyDate = "";
	private ExeSQL exe = new ExeSQL();
	private TransferData mTransferData = new TransferData();
	
	public CitiHand(String sourcefile) {		
	try {
		//getCell(i,j)  => 第j行的第i列 不要搞反了 
		Sheet sheet = Workbook.getWorkbook(new FileInputStream(sourcefile)).getSheet(0);
		String policy = sheet.getCell(0,0).getContents();
//System.out.println("policy:"+policy);
		if(!"policy".equals(policy)){
			throw new MidplatException("上传文件格式有误，请重新选择上传");
		}
		cSubmitMMap.put("delete from PolicyNoTemp","UPDATE");
		for (int j = 1; j < sheet.getRows(); j++) {//行
//System.out.println("sheet.getRows():"+sheet.getRows());			
			String policyNo = sheet.getCell(0,j).getContents();
			if(policyNo==null||"".equals(policyNo)){
				break;
			}
			iEndNo = j+1;
			iSuccNo++;
			String sql ="insert into PolicyNoTemp(PolicyNo) values('"+policyNo+"')";
			cSubmitMMap.put(sql, "INSERT");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
}
	/**
	 * 传输数据的公共方法
	 * @throws MidplatException 
	 */
	public boolean submitData(VData tVData) throws MidplatException{
		VData mSubmitVData = new VData();
		mSubmitVData.add(getSubmitMMap());
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, ""))
		{
//			return false;
			throw new MidplatException(mPubSubmit.mErrors.getFirstError());
		}
		cSubmitMMap = null;
		this.inputParameters = tVData;
		return true;
	}
	
	public MMap getSubmitMMap() {
		return cSubmitMMap;
	}
	
	public boolean dealUpdate()throws MidplatException{
		this.mTransferData = (TransferData)this.inputParameters.getObjectByObjectName("TransferData",0);
		this.fileTypeCode = (String)mTransferData.getValueByName("FileTypeCode");
		this.startDate = DateUtil.date10to8((String)mTransferData.getValueByName("StartDay1"));
		this.endDate = DateUtil.date10to8((String)mTransferData.getValueByName("EndDay1"));
//		this.modifyDate = DateUtil.date10to8((String)mTransferData.getValueByName("EndDay2"));
		String getPolicyNoSql = "select PolicyNo from PolicyNoTemp";
		this.tSSRS = exe.execSQL(getPolicyNoSql);
		try {
			for(int i=1;i<=this.tSSRS.getMaxRow();i++){
				String policyNo = tSSRS.GetText(i, 1);
				if("".equals(policyNo)||policyNo==null){
					return true;
				}
				if("Policy".equals(this.fileTypeCode)){//保单信息文件
					String sql1 = "update PolicyMaster p set p.DealDate='' where p.serialno= (select * from (select serialno from policymaster where policyno='"+policyNo+
					              "' and ExtractedDate between "+this.startDate+" and "+this.endDate+
					              " order by extracteddate desc) where ROWNUM <= 1)";
				
					String sql2 = "update PolicyTransaction p set p.DealDate='' where p.serialno= (select * from (select serialno from PolicyTransaction where policyno='"+policyNo+
					              "' and ExtractedDate between "+this.startDate+" and "+this.endDate+
					              " order by extracteddate desc) where ROWNUM <= 1)";
				
//					String sql3 = "update PolicyRider p set p.MODIFYDATE="+this.modifyDate+" where p.PolicyNo ='"+policyNo+"'" +
//						" and p.MakeDate between "+this.startDate+" and "+this.endDate;		

					String sql4 = "update ApplicationMaster a set a.DealDate='' where a.serialno= (select * from (select serialno from ApplicationMaster where policyno='"+policyNo+
					              "' and ExtractedDate between "+this.startDate+" and "+this.endDate+
					              " order by extracteddate desc) where ROWNUM <= 1)";
			
//					String sql5 = "update ApplicationRider a set a.MODIFYDATE="+this.modifyDate+" where a.PolicyNo ='"+policyNo+"'" +
//						" and a.MakeDate between "+this.startDate+" and "+this.endDate;		
					MMap map = new MMap();
					map.put(sql1, "UPDATE");
					map.put(sql2, "UPDATE");
//					map.put(sql3, "UPDATE");
					map.put(sql4, "UPDATE");
//					map.put(sql5, "UPDATE");
					VData temp = new VData();
					temp.add(map);
					PubSubmit mPubSubmit = new PubSubmit();
					if (!mPubSubmit.submitData(temp, "")){
						throw new MidplatException(mPubSubmit.mErrors.getFirstError());
					}
				}else if("FundTxn".equals(this.fileTypeCode)){//投资账户信息文件
					String sql = "update AXAFundTransaction ft set ft.DealDate='' where ft.PolicyNo ='"+policyNo+"'" +
					" and ft.ExtractedDate between "+this.startDate+" and "+this.endDate;	
					MMap map = new MMap();
					map.put(sql, "UPDATE");
					VData temp = new VData();
					temp.add(map);
					PubSubmit mPubSubmit = new PubSubmit();
					if (!mPubSubmit.submitData(temp, "")){
						throw new MidplatException(mPubSubmit.mErrors.getFirstError());
					}
				}else if("FundSum".equals(this.fileTypeCode)){//投资账户汇总信息文件
					
					String getSerialsNoSQL = "select serialno from FundSummary where policyno='"+policyNo+
					              "' and ExtractedDate =(select max(ExtractedDate) from FundSummary where policyno='"+policyNo+"'" +
					              		" and ExtractedDate between "+this.startDate+" and "+this.endDate+" )" ;
					SSRS serialsNoS = exe.execSQL(getSerialsNoSQL);
					
					MMap map = new MMap();//存放更新表的SQL
					
					for(int j=1;j<=serialsNoS.getMaxRow();j++){											
						String sql = "update FundSummary fs set fs.DealDate='' where fs.serialno= '"+serialsNoS.GetText(j, 1)+"'";						
						map.put(sql, "UPDATE");
					}
					
					VData temp = new VData();
					temp.add(map);
					PubSubmit mPubSubmit = new PubSubmit();
					if (!mPubSubmit.submitData(temp, "")){
						throw new MidplatException(mPubSubmit.mErrors.getFirstError());
					}
				}else if("FundPrice".equals(this.fileTypeCode)){//表更新待定。。投资账户价格信息文件
				}else if("Commission".equals(this.fileTypeCode)){//佣金信息文件
					String sql = "update AxaPolicyTransaction p set p.DealDate='' where p.PolicyNo ='"+policyNo+"'" +
					" and p.extracted between "+this.startDate+" and "+this.endDate;
					MMap map = new MMap();
					map.put(sql, "UPDATE");
					VData temp = new VData();
					temp.add(map);
					PubSubmit mPubSubmit = new PubSubmit();
					if (!mPubSubmit.submitData(temp, "")){
						throw new MidplatException(mPubSubmit.mErrors.getFirstError());
					}
				}else{				
					throw new MidplatException("操作有误");
				}			
			}
		} catch (Exception e) {
//			throw new MidplatException("");
			return false;
		}	
		return true;
	}
	
//	public void checkFile()throws MidplatException{//补生成,不论之前有没有生成都要生成 
//		String date = DateUtil.getCur10Date();
//		String citiLogSql = "select * from CitiIFLog cf where cf.IFtype = '"+fileTypeCode+"' and cf.MakeDate=date'"+date+"' and cf.RCode='Y' and cf.DelFlag is null ";
//		SSRS ssRS = new SSRS();
//		ssRS = exe.execSQL(citiLogSql);
//		if(ssRS.getMaxRow()==1){//文件已经生成了,则更新记录和文件
//			String sqlPath = "select cf.FilePath,cf.FileName from CitiIFLog cf where cf.IFtype = '"+fileTypeCode+"' and cf.MakeDate=date'"+date+"' and cf.RCode='Y' and cf.DelFlag is null ";
//			String sql = "update CitiIFLog cf set cf.DelFlag = 'Y' where cf.IFtype = '"+fileTypeCode+"' and cf.MakeDate=date'"+date+"' and cf.RCode='Y' and cf.DelFlag is null ";
//			
//			
//			SSRS filePaths = new SSRS();
//			filePaths = exe.execSQL(sqlPath);
//			exe.execUpdateSQL(sql);//更新记录
//			String filePath="";
//			for(int i=1;i<=filePaths.getMaxRow();i++){
//				filePath =filePaths.GetText(i, 1)+filePaths.GetText(i, 2);
//			}
//System.out.println("FilePath:"+filePath);
//			File file = new File(filePath);
//			if(file.exists()){
//				file.delete();// 删除文件
//			}
//			
//		}
//	}
	
	public static void main(String[] args) {
	}
}
