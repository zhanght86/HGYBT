package com.sinosoft.lis.citi;

import java.io.File;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class MissInfoImportUI {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
//	private String mOperate;
	
	public int iSuccNo = 0;
	
	public int iUpdateNo = 0;
	
//	public int iRepeatNo = 0;
	
	public int iEndNo = 0;
	
	private MMap cSubmitMMap = new MMap();	
	
	private final static Logger cLogger = Logger.getLogger(MissInfoImportUI.class);
	
	VData inputData = new VData();
	TransferData tTransferData = new TransferData();

	private Integer ExtractedDate;
	private String PolicyNo = "";
	private String InsuredIdType = "";
	private String ApplicantIdType = "";
	private String ApplicantIdNo = "";
	private String ApplicantAcctNo = "";
	private String CustomNO = "";
	private String CancelReason = "";
	private String ApplicateNo = "";
	private String CancelCode = "";
	
	public MissInfoImportUI() {

	}
	public MissInfoImportUI(VData tVData) {
		inputData = tVData;
		tTransferData = (TransferData) inputData.get(0);

		this.ExtractedDate = Integer.parseInt(DateUtil.date10to8((String)(tTransferData.getValueByName("ExtractedDate"))));
		this.PolicyNo = (String) tTransferData.getValueByName("PolicyNo");
		this.InsuredIdType = (String) tTransferData.getValueByName("InsuredIdType");
		this.ApplicantIdType = (String) tTransferData.getValueByName("ApplicantIdType");
		this.ApplicantIdNo = (String) tTransferData.getValueByName("ApplicantIdNo");
		this.ApplicantAcctNo = (String) tTransferData.getValueByName("ApplicantAcctNo");
		this.CustomNO = (String) tTransferData.getValueByName("CustomNO");
		this.CancelReason = (String) tTransferData.getValueByName("CancelReason");
		this.ApplicateNo = (String) tTransferData.getValueByName("ApplicateNo");
		this.CancelCode = (String) tTransferData.getValueByName("CancelCode");

	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData) {

//		this.mOperate = cOperate;
		MissInfoImport tMissInfoImport = new MissInfoImport();

		if (tMissInfoImport.submitData(cInputData) == false) {
			this.mErrors.copyAllErrors(tMissInfoImport.mErrors);
			cLogger.info(tMissInfoImport.mErrors.getError(0).errorMessage);
			return false;
		} else {
			if (tMissInfoImport.mErrors.needDealError()) {
//				System.out.println();
				this.mErrors.copyAllErrors(tMissInfoImport.mErrors);
				System.out.println("Error");
			}
			mResult = tMissInfoImport.getResult();
			iSuccNo = tMissInfoImport.iSuccNo;
//			iRepeatNo = tMissInfoImport.iRepeatNo;
			iUpdateNo = tMissInfoImport.iUpdateNo;
			iEndNo = tMissInfoImport.iEndNo;
			cLogger.info(tMissInfoImport.XmlFileName);
			cLogger.info("成功提交" + tMissInfoImport.iSuccNo + "条数据!");
			if(tMissInfoImport.iRepeatNo != 0){
				cLogger.info(tMissInfoImport.iRepeatNo +"条数据已存在!");
			}
			if(tMissInfoImport.iUpdateNo != 0){
				cLogger.info(tMissInfoImport.iUpdateNo +"条数据已更新!");
			}
			
			File tFile = new File(tMissInfoImport.XmlFileName);
			if (!tFile.delete()) {
				CError tError = new CError();
				tError.moduleName = "ParseGuideIn";
				tError.functionName = "checkData";
				tError.errorMessage = "Xml文件删除失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return true;
	}
	public void update() throws MidplatException {
		StringBuffer sql = new StringBuffer();
		sql.append("update AxaMissingInfoForPolicyTemp set " 
				+ "ExtractedDate = "+this.ExtractedDate
				+",InsuredIdType = '"+this.InsuredIdType+"'" 
				+",ApplicantIdType = '"+this.ApplicantIdType+"'" 
				+",ApplicantIdNo='"+this.ApplicantIdNo+"'"
				+",ApplicantAcctNo ='"+this.ApplicantAcctNo+"'"
				+",CustomerNO = '"+this.CustomNO+"'" 
				+",CancelReason =' "+this.CancelReason+"'" 
				+",ApplicationNo = '"+this.ApplicateNo+"'"
				+",POLICYCANCELCODE = '"+this.CancelCode+"'"
				+",HADPROCESSED =1 "
				+" Where PolicyNo ='"+this.PolicyNo+"'"
				);
		try{
			new ExeSQL().execUpdateSQL(sql.toString());
		}catch(Exception e){
			throw new MidplatException("更新信息失败，请重试!");
		}
		
	}
	public void delete() throws MidplatException {
		String sql = "DELETE FROM AxaMissingInfoForPolicyTemp where policyno = '"+this.PolicyNo+"'";
		try {
			new ExeSQL().execUpdateSQL(sql);
		} catch (Exception e) {
			throw new MidplatException("删除记录失败，请重试!");
		}
	}
	public VData getResult() {
		return mResult;
	}

	public String getPolicyNo() {
		return PolicyNo;
	}
	public void setPolicyNo(String policyNo) {
		PolicyNo = policyNo;
	}
	public static void main(String[] args) {
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("FileName", "缺失信息上传格式.xls");
		tTransferData.setNameAndValue("FilePath", "D:/金盛/文档/reportit/temp/");
		tTransferData.setNameAndValue("ConfigFileName", "D:/金盛/文档/reportit/temp/C01模板.xml");
		tTransferData.setNameAndValue("InfoType", "C01");
		tVData.add(tTransferData);
		MissInfoImportUI tMissInfoImportUI = new MissInfoImportUI();
		tMissInfoImportUI.submitData(tVData);
	}
}
