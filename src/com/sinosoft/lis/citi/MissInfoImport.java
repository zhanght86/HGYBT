package com.sinosoft.lis.citi;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.AxaMissingInfoForPolicyTempDB;
import com.sinosoft.lis.db.LKContNoDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.vdb.AxaMissingInfoForPolicyTempDBSet;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IdCardUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;



public class MissInfoImport {
	// @Fields
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	public CErrors logErrors = new CErrors();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 全局数据 */
//	private GlobalInput mGlobalInput = new GlobalInput();

	public String sError = "";

	public int iError = 0;

	public int iSuccNo = 0;

	public int iRepeatNo = 0;

	public int iUpdateNo = 0;

	public int iEndNo = 0;

	private Document mDoc = null;

	private TransferData mTransferData = new TransferData();

	private String mFileName;
	
	private String infoType;

	public String XmlFileName;

	private String mFilePath;

	// 数据Xml解析节点描述
//	private String FilePath = "/";

	// 配置文件Xml节点描述
	private String ImportFileName;

	private String ConfigFileName;
	
	private MMap mDelMap = new MMap();
//	private String mOperator = "";

//	private String mAreaNo = "";

//	private String mCityNo = "";


	private String[] m_strDataFiles = null;

	private final static Logger cLogger = Logger.getLogger(MissInfoImport.class);

	/**
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData) {
		mInputData = (VData) cInputData.clone();
//		cLogger.info("111111111");
		this.getInputData();

		if (!this.checkData()) {
			return false;
		}

		try {
			if (!this.parseVts()) {
				return false;
			}

			for (int nIndex = 0; nIndex < m_strDataFiles.length; nIndex++) {
				XmlFileName = m_strDataFiles[nIndex];
//				System.out.println("!!!!!!!!!!!");
				this.ParseXml();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			cLogger.info(ex.getMessage());
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "导入文件格式有误!" + ex.getMessage();
			this.logErrors.addOneError(tError);
		}

		if (this.logErrors.getErrorCount() > 0) {
			this.mErrors = this.logErrors;
			cLogger.info("结束时间:" + PubFun.getCurrentTime());
			return false;
		}

		return true;
	}

	/**
	 * 得到传入数据
	 */
	private void getInputData() {		
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		infoType = (String) mTransferData.getValueByName("InfoType");
		mFilePath = (String) mTransferData.getValueByName("FilePath");
		mFileName = (String) mTransferData.getValueByName("FileName");
		ConfigFileName = (String) mTransferData.getValueByName("ConfigFileName");
		cLogger.info("正在上传文件");
	}

	/**
	 * 校验传输数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		
		/*
		 * if (mManageCom != null && "86".equals(mManageCom)) { // @@错误处理 CError
		 * tError = new CError(); tError.moduleName = "ParseGuideIn";
		 * tError.functionName = "checkData"; tError.errorMessage =
		 * "该操作员所属机构无导入网点专员的权限!"; this.mErrors.addOneError(tError); return
		 * false; }
		 */
		if (mTransferData == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "无导入文件信息，请重新导入!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			if (mFileName == null) {
				mFileName = "";
			}
		}
		return true;
	}

	/**
	 * 得到生成文件路径
	 * 
	 * @return
	 */
//	private boolean getFilePath() {
//
//		FilePath = "reportit/temp/";
//		return true;
//	}

	/**
	 * 检验文件是否存在
	 * 
	 * @return
	 */
	private boolean checkXmlFileName() {
		File tFile = new File(XmlFileName);
		if (!tFile.exists()) {

			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "缺少文件导入路径!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 检查导入配置文件是否存在
	 * 
	 * @return
	 */
	private boolean checkImportConfig() {
//		this.getFilePath();

//		String filePath = mFilePath + FilePath;

		File tFile = new File(mFilePath);
		if (!tFile.exists()) {
			tFile.mkdirs();
		}

		ConfigFileName = mFilePath + "MissInfo.xml";
		cLogger.info("试用的模板文件为：" + ConfigFileName);
		File tFile2 = new File(ConfigFileName);
		if (!tFile2.exists()) {
			cLogger.info("检查导入配置文件是否存在");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "请上传配置文件到指定路径" + mFilePath + "!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 初始化上传文件
	 * 
	 * @return
	 */
	private boolean initImportFile() {
//		this.getFilePath();
		ImportFileName = mFilePath + mFileName;
		cLogger.info("提交的上传文件的路径为:" + ImportFileName);
		File tFile = new File(ImportFileName);
		if (!tFile.exists()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "未上传文件到指定路径" + mFileName + "!";
			this.mErrors.addOneError(tError);
			return false;
		}
		cLogger.info("文件上传成功");
		return true;
	}

	/**
	 * 解析excel并转换成xml文件
	 * 
	 * @return
	 */
	private boolean parseVts() throws Exception {
		// 初始化导入文件
		if (!this.initImportFile()) {
			return false;
		}
		// 配置xml校验文件
		if (!this.checkImportConfig()) {
			return false;
		}

		MissInfoVTSParser gcvp = new MissInfoVTSParser();
//		infoType
		gcvp.setInfoType(infoType);
		if (!gcvp.setFileName(ImportFileName)) {
			mErrors.copyAllErrors(gcvp.mErrors);
			return false;
		}
		if (!gcvp.setConfigFileName(ConfigFileName)) {
			mErrors.copyAllErrors(gcvp.mErrors);
			return false;
		}
		// 转换excel到xml
		if (!gcvp.transform()) {
			mErrors.copyAllErrors(gcvp.mErrors);
			return false;
		}

		// 得到生成的XML数据文件名
		m_strDataFiles = gcvp.getDataFiles();
		mDoc = gcvp.getXmlDoc();
		return true;
	}

	/**
	 * 解析xml,
	 * 
	 * @return
	 */
	private boolean ParseXml() throws Exception {

		if (!checkXmlFileName()) {
			return false;
		}

		Document newDoc = mDoc;

		// ----------------------------------------------------------------
		Element eDate = newDoc.getRootElement();
		Element eContTable = eDate.getChild("CONTTABLE");
		Element eTop = eContTable.getChild("Top");
		String tTop1 = eTop.getChildText("Top1");
		String tTop2 = eTop.getChildText("Top2");
		String tTop3 = eTop.getChildText("Top3");
		String tTop4 = eTop.getChildText("Top4");
		String tTop5 = eTop.getChildText("Top5");
		String tTop6 = eTop.getChildText("Top6");
		String tTop7 = eTop.getChildText("Top7");
		String tTop8 = eTop.getChildText("Top8");
//		String tShortName = MidplatUtil.getShortName4LDCom(mCityNo);

		if (tTop1 == null || "".equals(tTop1) || !"保单号".equals(tTop1)) {
			throw new Exception("提交文件格式不符合要求,第1行第1列应为【保单号】!");
		}
		if (tTop2 == null || "".equals(tTop2) || !"被保人证件类型".equals(tTop2)) {
			throw new Exception("提交文件格式不符合要求,第1行第2列应为【被保人证件类型】!");
		}
		if (tTop3 == null || "".equals(tTop3) || !"投保人证件类型".equals(tTop3)) {
			throw new Exception("提交文件格式不符合要求,第1行第3列应为【投保人证件类型】!");
		}
		if (tTop4 == null || "".equals(tTop4) || !"投保人证件号".equals(tTop4)) {
			throw new Exception("提交文件格式不符合要求,第1行第4列应为【投保人证件号】!");
		}
		if (tTop5 == null || "".equals(tTop5) || !"投保人银行帐号".equals(tTop5)) {
			throw new Exception("提交文件格式不符合要求,第1行第5列应为【投保人银行帐号】!");
		}
		if (tTop6 == null || "".equals(tTop6) || !"流水号".equals(tTop6)) {
			throw new Exception("提交文件格式不符合要求,第1行第6列应为【流水号】!");
		}
		if (tTop7 == null || "".equals(tTop7) || !"退保原因".equals(tTop7)) {
			throw new Exception("提交文件格式不符合要求,第1行第7列应为【退保原因】!");
		}
		if (tTop8 == null || "".equals(tTop8) || !"操作日期".equals(tTop8)) {
			throw new Exception("提交文件格式不符合要求,第1行第8列应为【操作日期】!");
		}
		

		List RowList = eContTable.getChildren("ROW");
		int maxCow = 0;
		if (RowList != null) {
			maxCow = RowList.size();
		}

		for (int i = 0; i < maxCow; i++) {
			Element eRow = (Element) RowList.get(i);

			// --------------------PolicyNo 华丽的分割线----------------------------
			String sPolicyNo = eRow.getChildTextTrim("PolicyNo");
			if (sPolicyNo == null || "".equals(sPolicyNo)) {
				throw new Exception("第" + (i + 2) + "行的"+sPolicyNo+"保单的第1列【保单号】不能为空!");
			}
			if (sPolicyNo.length() != 11 || !MidplatUtil.checkContNo(sPolicyNo)) {
				throw new Exception("第" + (i + 2) + "行的"+sPolicyNo+"保单的第1列【保单号】不符合编码规则,请确认!");
			}

			// --------------------InsuredIdType 华丽的分割线----------------------------
			String sInsuredIdType = eRow.getChildText("InsuredIdType");
			if (sInsuredIdType == null || "".equals(sInsuredIdType)) {
				throw new Exception("第" + (i + 2) + "行的"+sPolicyNo+"保单的第2列【被保人证件类型】不能为空!");
			}
			if (!MidplatUtil.checkIdType(sInsuredIdType)) {
				throw new Exception("第" + (i + 2)
						+ "行的"+sPolicyNo+"保单的第2列【被保人证件类型】只能为A出生证，I身份证，P护照，S军人证，X其他,请确认!");
			}

			// --------------------ApplicantIdType华丽的分割线----------------------------
			String sApplicantIdType = eRow.getChildText("ApplicantIdType");
			if (sApplicantIdType == null || "".equals(sApplicantIdType)) {
				throw new Exception("第" + (i + 2) + "行的"+sPolicyNo+"保单的第3列【投保人证件类型】不能为空!");
			}
			if (!MidplatUtil.checkIdType(sApplicantIdType)) {
				throw new Exception("第" + (i + 2) + "行的"+sPolicyNo+"保单的第3列【投保人证件类型】只能为A出生证，I身份证，P护照，S军人证，X其他,请确认!");
			}
			
			// --------------------ApplicantIdNo 华丽的分割线----------------------------
			String sApplicantIdNo = eRow.getChildTextTrim("ApplicantIdNo");
//System.out.println("sApplicantIdNo:------"+sApplicantIdNo);
			if (sApplicantIdNo == null || "".equals(sApplicantIdNo)) {
				throw new Exception("第" + (i + 2) + "行的"+sPolicyNo+"保单的第4列【投保人证件号】不能为空!");
			}
			if("I".equals(sApplicantIdType)){
			String temp = IdCardUtil.converAllTo18(sApplicantIdNo);
			if(temp==null){
				throw new Exception("第" + (i + 2) + "行的"+sPolicyNo+"保单的第4列【投保人证件号】为无效身份证号,请确认!");
			}
			if ("I".equals(sApplicantIdType)&& !IdCardUtil.v18IDCard(temp)) {
				throw new Exception("第" + (i + 2) + "行的"+sPolicyNo+"保单的第4列【投保人证件号】为无效身份证号,请确认!");
			}
			}
			// --------------------ApplicantAcctNo 华丽的分割线----------------------------
			String sApplicantAcctNo = eRow.getChildText("ApplicantAcctNo");
			if (sApplicantAcctNo == null || "".equals(sApplicantAcctNo)) {
				throw new Exception("第" + (i + 2) + "行的"+sPolicyNo+"保单的第5列【投保人银行帐号】不能为空!");
			}
			
			// --------------------TransNo 华丽的分割线----------------------------
			String sTransNo = eRow.getChildText("Oid");
			if (sTransNo == null || "".equals(sTransNo)) {
				throw new Exception("第" + (i + 2) + "行的"+sPolicyNo+"保单的第6列【流水号】不能为空!");
			}
			
			// --------------------Extracted 华丽的分割线----------------------------
			String sExtracted = eRow.getChildText("ExtractedDate");
			if (sExtracted == null || "".equals(sExtracted)) {
				throw new Exception("第" + (i + 2) + "行的"+sPolicyNo+"保单的第8列【操作日期】不能为空!");
			}

		}

		AxaMissingInfoForPolicyTempDBSet aAxaMissingInfoDBSet = new AxaMissingInfoForPolicyTempDBSet();
		for (int i = 0; i < maxCow; i++) {
			int flag = 0;
			Element eRow = (Element) RowList.get(i);
			String aOid = PubFun1.CreateMaxNo("MissInfo", 8);
			String aPolicyNo = eRow.getChildText("PolicyNo");
			String aInsuredIdType = eRow.getChildText("InsuredIdType");
			String aApplicantIdType = eRow.getChildText("ApplicantIdType");
			String aApplicantIdNo = eRow.getChildText("ApplicantIdNo");
			String aApplicantAcctNo = eRow.getChildText("ApplicantAcctNo");
			String aTransNo = eRow.getChildText("Oid");
			String aCancelReason = eRow.getChildText("CancelReason");
			String aExtracted = eRow.getChildText("ExtractedDate");
			int aUpdated = DateUtil.getCur8Date();
			
			AxaMissingInfoForPolicyTempDB aAxaMissingInfoDB = new AxaMissingInfoForPolicyTempDB();
			aAxaMissingInfoDB.setOid(aOid);
			aAxaMissingInfoDB.setPolicyNo(aPolicyNo);
			aAxaMissingInfoDB.setExtractedDate(Integer.parseInt(aExtracted));
			aAxaMissingInfoDB.setInsuredIdType(aInsuredIdType);
			aAxaMissingInfoDB.setApplicantIdType(aApplicantIdType);
			aAxaMissingInfoDB.setApplicantIdNo(aApplicantIdNo);
			aAxaMissingInfoDB.setApplicantAcctNo(aApplicantAcctNo);
			aAxaMissingInfoDB.setCancelReason(aCancelReason);
			aAxaMissingInfoDB.setHadProcessed(0);
			aAxaMissingInfoDB.setBeCovered(0);
			aAxaMissingInfoDB.setMakeDate(aUpdated);
			aAxaMissingInfoDB.setApplicationNo(aTransNo);
//			if (aAxaMissingInfoDB.getInfo()) {
//				iRepeatNo++;
//			} else {
//					cLogger.info("------------------------");
//					cLogger.info(aAxaMissingInfoDBSet.size());
//					for (int k = 1; k <= aAxaMissingInfoDBSet.size(); k++) {
//						
//						if (aPolicyNo.equals(aAxaMissingInfoDBSet.get(k).getContNo())
//								&& aCityNo.equals(aAxaMissingInfoDBSet.get(k)
//										.getCityCode())) {
//							iRepeatNo++;
//							flag = 1;
//						}
//					}
//					if(flag != 1){
//						aAxaMissingInfoDBSet.add(aAxaMissingInfoDB);
//						iSuccNo++;
//					}
//				}
			aAxaMissingInfoDBSet.add(aAxaMissingInfoDB);
			iSuccNo++;
			}
		
		VData mSubmitVData = new VData();
		mSubmitVData.add(getDeleteMMap());
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, ""))
		{
			return false;
		}
		
		if (!aAxaMissingInfoDBSet.insert()) {
			iError = aAxaMissingInfoDBSet.iError;
			throw new MidplatException("在第" + iError 
					+ "行提交失败!");
		}

		iEndNo = maxCow + 2;

		// ----------------------------------------------------------------

		if (this.mErrors.needDealError()) {
			cLogger.info(this.mErrors.getErrorCount() + "error:"
					+ this.mErrors.getFirstError());
		}

		return true;
	}
	
	private MMap getDeleteMMap(){
		mDelMap.put("DELETE FROM AxaMissingInfoForPolicyTemp", "UPDATE");
		
		return mDelMap;
	}

	public String getExtendFileName(String aFileName) {
		File tFile = new File(aFileName);
		String aExtendFileName = "";
		String name = tFile.getName();
		for (int i = name.length() - 1; i >= 0; i--) {
			if (i < 1) {
				i = 1;
			}
			if (name.substring(i - 1, i).equals(".")) {
				aExtendFileName = name.substring(i, name.length());
				cLogger.info("ExtendFileName;" + aExtendFileName);
				return aExtendFileName;
			}
		}
		return aExtendFileName;
	}



	/**
	 * 得到日志显示结果
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {

		cLogger.info(PubFun1.CreateMaxNo("MissInfo", 15));
	}

}
