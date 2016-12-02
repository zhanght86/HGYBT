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
	/** �������࣬ÿ����Ҫ����������ж����ø��� */
	public CErrors mErrors = new CErrors();

	public CErrors logErrors = new CErrors();

	/** ��ǰ�洫�����ݵ����� */
	private VData mResult = new VData();

	/** �����洫�����ݵ����� */
	private VData mInputData;

	/** ȫ������ */
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

	// ����Xml�����ڵ�����
//	private String FilePath = "/";

	// �����ļ�Xml�ڵ�����
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
			// @@������
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "�����ļ���ʽ����!" + ex.getMessage();
			this.logErrors.addOneError(tError);
		}

		if (this.logErrors.getErrorCount() > 0) {
			this.mErrors = this.logErrors;
			cLogger.info("����ʱ��:" + PubFun.getCurrentTime());
			return false;
		}

		return true;
	}

	/**
	 * �õ���������
	 */
	private void getInputData() {		
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		infoType = (String) mTransferData.getValueByName("InfoType");
		mFilePath = (String) mTransferData.getValueByName("FilePath");
		mFileName = (String) mTransferData.getValueByName("FileName");
		ConfigFileName = (String) mTransferData.getValueByName("ConfigFileName");
		cLogger.info("�����ϴ��ļ�");
	}

	/**
	 * У�鴫������
	 * 
	 * @return
	 */
	private boolean checkData() {
		
		/*
		 * if (mManageCom != null && "86".equals(mManageCom)) { // @@������ CError
		 * tError = new CError(); tError.moduleName = "ParseGuideIn";
		 * tError.functionName = "checkData"; tError.errorMessage =
		 * "�ò���Ա���������޵�������רԱ��Ȩ��!"; this.mErrors.addOneError(tError); return
		 * false; }
		 */
		if (mTransferData == null) {
			// @@������
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "�޵����ļ���Ϣ�������µ���!";
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
	 * �õ������ļ�·��
	 * 
	 * @return
	 */
//	private boolean getFilePath() {
//
//		FilePath = "reportit/temp/";
//		return true;
//	}

	/**
	 * �����ļ��Ƿ����
	 * 
	 * @return
	 */
	private boolean checkXmlFileName() {
		File tFile = new File(XmlFileName);
		if (!tFile.exists()) {

			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "ȱ���ļ�����·��!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * ��鵼�������ļ��Ƿ����
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
		cLogger.info("���õ�ģ���ļ�Ϊ��" + ConfigFileName);
		File tFile2 = new File(ConfigFileName);
		if (!tFile2.exists()) {
			cLogger.info("��鵼�������ļ��Ƿ����");
			// @@������
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "���ϴ������ļ���ָ��·��" + mFilePath + "!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * ��ʼ���ϴ��ļ�
	 * 
	 * @return
	 */
	private boolean initImportFile() {
//		this.getFilePath();
		ImportFileName = mFilePath + mFileName;
		cLogger.info("�ύ���ϴ��ļ���·��Ϊ:" + ImportFileName);
		File tFile = new File(ImportFileName);
		if (!tFile.exists()) {
			// @@������
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "δ�ϴ��ļ���ָ��·��" + mFileName + "!";
			this.mErrors.addOneError(tError);
			return false;
		}
		cLogger.info("�ļ��ϴ��ɹ�");
		return true;
	}

	/**
	 * ����excel��ת����xml�ļ�
	 * 
	 * @return
	 */
	private boolean parseVts() throws Exception {
		// ��ʼ�������ļ�
		if (!this.initImportFile()) {
			return false;
		}
		// ����xmlУ���ļ�
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
		// ת��excel��xml
		if (!gcvp.transform()) {
			mErrors.copyAllErrors(gcvp.mErrors);
			return false;
		}

		// �õ����ɵ�XML�����ļ���
		m_strDataFiles = gcvp.getDataFiles();
		mDoc = gcvp.getXmlDoc();
		return true;
	}

	/**
	 * ����xml,
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

		if (tTop1 == null || "".equals(tTop1) || !"������".equals(tTop1)) {
			throw new Exception("�ύ�ļ���ʽ������Ҫ��,��1�е�1��ӦΪ�������š�!");
		}
		if (tTop2 == null || "".equals(tTop2) || !"������֤������".equals(tTop2)) {
			throw new Exception("�ύ�ļ���ʽ������Ҫ��,��1�е�2��ӦΪ��������֤�����͡�!");
		}
		if (tTop3 == null || "".equals(tTop3) || !"Ͷ����֤������".equals(tTop3)) {
			throw new Exception("�ύ�ļ���ʽ������Ҫ��,��1�е�3��ӦΪ��Ͷ����֤�����͡�!");
		}
		if (tTop4 == null || "".equals(tTop4) || !"Ͷ����֤����".equals(tTop4)) {
			throw new Exception("�ύ�ļ���ʽ������Ҫ��,��1�е�4��ӦΪ��Ͷ����֤���š�!");
		}
		if (tTop5 == null || "".equals(tTop5) || !"Ͷ���������ʺ�".equals(tTop5)) {
			throw new Exception("�ύ�ļ���ʽ������Ҫ��,��1�е�5��ӦΪ��Ͷ���������ʺš�!");
		}
		if (tTop6 == null || "".equals(tTop6) || !"��ˮ��".equals(tTop6)) {
			throw new Exception("�ύ�ļ���ʽ������Ҫ��,��1�е�6��ӦΪ����ˮ�š�!");
		}
		if (tTop7 == null || "".equals(tTop7) || !"�˱�ԭ��".equals(tTop7)) {
			throw new Exception("�ύ�ļ���ʽ������Ҫ��,��1�е�7��ӦΪ���˱�ԭ��!");
		}
		if (tTop8 == null || "".equals(tTop8) || !"��������".equals(tTop8)) {
			throw new Exception("�ύ�ļ���ʽ������Ҫ��,��1�е�8��ӦΪ���������ڡ�!");
		}
		

		List RowList = eContTable.getChildren("ROW");
		int maxCow = 0;
		if (RowList != null) {
			maxCow = RowList.size();
		}

		for (int i = 0; i < maxCow; i++) {
			Element eRow = (Element) RowList.get(i);

			// --------------------PolicyNo �����ķָ���----------------------------
			String sPolicyNo = eRow.getChildTextTrim("PolicyNo");
			if (sPolicyNo == null || "".equals(sPolicyNo)) {
				throw new Exception("��" + (i + 2) + "�е�"+sPolicyNo+"�����ĵ�1�С������š�����Ϊ��!");
			}
			if (sPolicyNo.length() != 11 || !MidplatUtil.checkContNo(sPolicyNo)) {
				throw new Exception("��" + (i + 2) + "�е�"+sPolicyNo+"�����ĵ�1�С������š������ϱ������,��ȷ��!");
			}

			// --------------------InsuredIdType �����ķָ���----------------------------
			String sInsuredIdType = eRow.getChildText("InsuredIdType");
			if (sInsuredIdType == null || "".equals(sInsuredIdType)) {
				throw new Exception("��" + (i + 2) + "�е�"+sPolicyNo+"�����ĵ�2�С�������֤�����͡�����Ϊ��!");
			}
			if (!MidplatUtil.checkIdType(sInsuredIdType)) {
				throw new Exception("��" + (i + 2)
						+ "�е�"+sPolicyNo+"�����ĵ�2�С�������֤�����͡�ֻ��ΪA����֤��I���֤��P���գ�S����֤��X����,��ȷ��!");
			}

			// --------------------ApplicantIdType�����ķָ���----------------------------
			String sApplicantIdType = eRow.getChildText("ApplicantIdType");
			if (sApplicantIdType == null || "".equals(sApplicantIdType)) {
				throw new Exception("��" + (i + 2) + "�е�"+sPolicyNo+"�����ĵ�3�С�Ͷ����֤�����͡�����Ϊ��!");
			}
			if (!MidplatUtil.checkIdType(sApplicantIdType)) {
				throw new Exception("��" + (i + 2) + "�е�"+sPolicyNo+"�����ĵ�3�С�Ͷ����֤�����͡�ֻ��ΪA����֤��I���֤��P���գ�S����֤��X����,��ȷ��!");
			}
			
			// --------------------ApplicantIdNo �����ķָ���----------------------------
			String sApplicantIdNo = eRow.getChildTextTrim("ApplicantIdNo");
//System.out.println("sApplicantIdNo:------"+sApplicantIdNo);
			if (sApplicantIdNo == null || "".equals(sApplicantIdNo)) {
				throw new Exception("��" + (i + 2) + "�е�"+sPolicyNo+"�����ĵ�4�С�Ͷ����֤���š�����Ϊ��!");
			}
			if("I".equals(sApplicantIdType)){
			String temp = IdCardUtil.converAllTo18(sApplicantIdNo);
			if(temp==null){
				throw new Exception("��" + (i + 2) + "�е�"+sPolicyNo+"�����ĵ�4�С�Ͷ����֤���š�Ϊ��Ч���֤��,��ȷ��!");
			}
			if ("I".equals(sApplicantIdType)&& !IdCardUtil.v18IDCard(temp)) {
				throw new Exception("��" + (i + 2) + "�е�"+sPolicyNo+"�����ĵ�4�С�Ͷ����֤���š�Ϊ��Ч���֤��,��ȷ��!");
			}
			}
			// --------------------ApplicantAcctNo �����ķָ���----------------------------
			String sApplicantAcctNo = eRow.getChildText("ApplicantAcctNo");
			if (sApplicantAcctNo == null || "".equals(sApplicantAcctNo)) {
				throw new Exception("��" + (i + 2) + "�е�"+sPolicyNo+"�����ĵ�5�С�Ͷ���������ʺš�����Ϊ��!");
			}
			
			// --------------------TransNo �����ķָ���----------------------------
			String sTransNo = eRow.getChildText("Oid");
			if (sTransNo == null || "".equals(sTransNo)) {
				throw new Exception("��" + (i + 2) + "�е�"+sPolicyNo+"�����ĵ�6�С���ˮ�š�����Ϊ��!");
			}
			
			// --------------------Extracted �����ķָ���----------------------------
			String sExtracted = eRow.getChildText("ExtractedDate");
			if (sExtracted == null || "".equals(sExtracted)) {
				throw new Exception("��" + (i + 2) + "�е�"+sPolicyNo+"�����ĵ�8�С��������ڡ�����Ϊ��!");
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
			throw new MidplatException("�ڵ�" + iError 
					+ "���ύʧ��!");
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
	 * �õ���־��ʾ���
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
