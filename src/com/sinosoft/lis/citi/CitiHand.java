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

	/** �������࣬ÿ����Ҫ����������ж����ø��� */
	public CErrors mErrors = new CErrors();

	/** ���ݲ����ַ��� */	
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
		//getCell(i,j)  => ��j�еĵ�i�� ��Ҫ�㷴�� 
		Sheet sheet = Workbook.getWorkbook(new FileInputStream(sourcefile)).getSheet(0);
		String policy = sheet.getCell(0,0).getContents();
//System.out.println("policy:"+policy);
		if(!"policy".equals(policy)){
			throw new MidplatException("�ϴ��ļ���ʽ����������ѡ���ϴ�");
		}
		cSubmitMMap.put("delete from PolicyNoTemp","UPDATE");
		for (int j = 1; j < sheet.getRows(); j++) {//��
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
	 * �������ݵĹ�������
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
				if("Policy".equals(this.fileTypeCode)){//������Ϣ�ļ�
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
				}else if("FundTxn".equals(this.fileTypeCode)){//Ͷ���˻���Ϣ�ļ�
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
				}else if("FundSum".equals(this.fileTypeCode)){//Ͷ���˻�������Ϣ�ļ�
					
					String getSerialsNoSQL = "select serialno from FundSummary where policyno='"+policyNo+
					              "' and ExtractedDate =(select max(ExtractedDate) from FundSummary where policyno='"+policyNo+"'" +
					              		" and ExtractedDate between "+this.startDate+" and "+this.endDate+" )" ;
					SSRS serialsNoS = exe.execSQL(getSerialsNoSQL);
					
					MMap map = new MMap();//��Ÿ��±��SQL
					
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
				}else if("FundPrice".equals(this.fileTypeCode)){//����´�������Ͷ���˻��۸���Ϣ�ļ�
				}else if("Commission".equals(this.fileTypeCode)){//Ӷ����Ϣ�ļ�
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
					throw new MidplatException("��������");
				}			
			}
		} catch (Exception e) {
//			throw new MidplatException("");
			return false;
		}	
		return true;
	}
	
//	public void checkFile()throws MidplatException{//������,����֮ǰ��û�����ɶ�Ҫ���� 
//		String date = DateUtil.getCur10Date();
//		String citiLogSql = "select * from CitiIFLog cf where cf.IFtype = '"+fileTypeCode+"' and cf.MakeDate=date'"+date+"' and cf.RCode='Y' and cf.DelFlag is null ";
//		SSRS ssRS = new SSRS();
//		ssRS = exe.execSQL(citiLogSql);
//		if(ssRS.getMaxRow()==1){//�ļ��Ѿ�������,����¼�¼���ļ�
//			String sqlPath = "select cf.FilePath,cf.FileName from CitiIFLog cf where cf.IFtype = '"+fileTypeCode+"' and cf.MakeDate=date'"+date+"' and cf.RCode='Y' and cf.DelFlag is null ";
//			String sql = "update CitiIFLog cf set cf.DelFlag = 'Y' where cf.IFtype = '"+fileTypeCode+"' and cf.MakeDate=date'"+date+"' and cf.RCode='Y' and cf.DelFlag is null ";
//			
//			
//			SSRS filePaths = new SSRS();
//			filePaths = exe.execSQL(sqlPath);
//			exe.execUpdateSQL(sql);//���¼�¼
//			String filePath="";
//			for(int i=1;i<=filePaths.getMaxRow();i++){
//				filePath =filePaths.GetText(i, 1)+filePaths.GetText(i, 2);
//			}
//System.out.println("FilePath:"+filePath);
//			File file = new File(filePath);
//			if(file.exists()){
//				file.delete();// ɾ���ļ�
//			}
//			
//		}
//	}
	
	public static void main(String[] args) {
	}
}
