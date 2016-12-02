package com.sinosoft.lis.input;

import java.util.List;

import com.sinosoft.lis.db.LKContNoDB;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.*;
import java.io.*;
import javax.xml.parsers.*;

import org.jdom.Document;
import org.jdom.Element;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author ZHJ
 * @version 6.0
 */
public class ITImport {
	// @Fields
	/** �������࣬ÿ����Ҫ����������ж����ø��� */
	public CErrors mErrors = new CErrors();

	public CErrors logErrors = new CErrors();

	/** ��ǰ�洫�����ݵ����� */
	private VData mResult = new VData();

	/** �����洫�����ݵ����� */
	private VData mInputData;

	/** ȫ������ */
	private GlobalInput mGlobalInput = new GlobalInput();

	public String sError = "";

	public int iError = 0;

	public int iSuccNo = 0;

	public int iRepeatNo = 0;

	public int iUpdateNo = 0;

	public int iEndNo = 0;

	private Document mDoc = null;

	private TransferData mTransferData = new TransferData();

	private String mFileName;

	public String XmlFileName;

	private String mFilePath;

	// ����Xml�����ڵ�����
	private String FilePath = "/";

	// �����ļ�Xml�ڵ�����
	private String ImportFileName;

	private String ConfigFileName;

	private String mOperator = "";

	private String mAreaNo = "";

	private String mCityNo = "";

	private String mManageCom = "";
	private String[] m_strDataFiles = null;



	/**
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();

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
				this.ParseXml();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			// @@������
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "�����ļ���ʽ����!" + ex.getMessage();
			this.logErrors.addOneError(tError);
		}

		if (this.logErrors.getErrorCount() > 0) {
			this.mErrors = this.logErrors;
			System.out.println("����ʱ��:" + PubFun.getCurrentTime());
			return false;
		}

		return true;
	}

	/**
	 * �õ���������
	 */
	private void getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mFilePath = (String) mTransferData.getValueByName("FilePath");
		mFileName = (String) mTransferData.getValueByName("FileName");
		mAreaNo = (String) mTransferData.getValueByName("AreaNo");
		mCityNo = (String) mTransferData.getValueByName("CityNo");
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		System.out.println("����:" + mAreaNo + "����:" + mCityNo);
		mOperator = mGlobalInput.Operator;
		System.out.println("�û�:" + mOperator + "���ڽ��б����������ύ");
	}

	/**
	 * У�鴫������
	 * 
	 * @return
	 */
	private boolean checkData() {
		if (mGlobalInput == null) {
			// @@������
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "�޲���Ա��Ϣ�������µ�¼!";
			this.mErrors.addOneError(tError);
			return false;
		}
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
	private boolean getFilePath() {

		FilePath = "reportit/temp/";
		return true;
	}

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
		this.getFilePath();

		String filePath = mFilePath + FilePath;

		File tFile = new File(filePath);
		if (!tFile.exists()) {
			tFile.mkdirs();
		}

		ConfigFileName = filePath + "ITImport.xml";
		System.out.println("�û�:" + mOperator + ":���õ�ģ���ļ�Ϊ��" + ConfigFileName);
		File tFile2 = new File(ConfigFileName);
		if (!tFile2.exists()) {
			System.out.println("��鵼�������ļ��Ƿ����");
			// @@������
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "���ϴ������ļ���ָ��·��" + FilePath + "!";
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
		this.getFilePath();
		ImportFileName = mFilePath + FilePath + mFileName;
		System.out.println("�û�:" + mOperator + "�ύ���ϴ��ļ���·��Ϊ:" + ImportFileName);
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
		System.out.println("�û�:" + mOperator + "�ļ��ϴ��ɹ�");
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

		ITVTSParser gcvp = new ITVTSParser();

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
		String tContNo = eTop.getChildText("ContNo");
		String tCityNo = eTop.getChildText("CityNo");
		String tSysFlag = eTop.getChildText("SysFlag");
		String tShortName = MidplatUtil.getShortName4LDCom(mCityNo);
       // String tShortName = MidplatUtil.getShortName4LDCom(mCityNo);
		//String tShortName =mCityNo;
		if (tContNo == null || "".equals(tContNo) || !"������".equals(tContNo)) {
			throw new Exception("�ύ�ļ���ʽ������Ҫ��,��1�е�1��ӦΪ�������š�!");
		}
		if (tCityNo == null || "".equals(tCityNo) || !"���д���".equals(tCityNo)) {
			throw new Exception("�ύ�ļ���ʽ������Ҫ��,��1�е�2��ӦΪ�����д��롿!");
		}
		if (tSysFlag == null || "".equals(tSysFlag) || !"ϵͳ��־".equals(tSysFlag)) {
			throw new Exception("�ύ�ļ���ʽ������Ҫ��,��1�е�3��ӦΪ��ϵͳ��־��!");
		}
		

		List RowList = eContTable.getChildren("ROW");
		int maxCow = 0;
		if (RowList != null) {
			maxCow = RowList.size();
		}

		for (int i = 0; i < maxCow; i++) {
			Element eRow = (Element) RowList.get(i);

			// --------------------ContNo �����ķָ���----------------------------
			String sContNo = eRow.getChildText("ContNo");
			if (sContNo == null || "".equals(sContNo)) {
				throw new Exception("��" + (i + 2) + "�е�1�С������š�����Ϊ��!");
			}
			if (sContNo.length() != 11 || !MidplatUtil.checkContNo(sContNo)) {
				throw new Exception("��" + (i + 2) + "�е�1�С������š������ϱ������,��ȷ��!");
			}

			// --------------------CityNo �����ķָ���----------------------------
			String sCityNo = eRow.getChildText("CityNo");
			if (sCityNo == null || "".equals(sCityNo)) {
				throw new Exception("��" + (i + 2) + "�е�2�С����д��롿����Ϊ��!");
			}
			if (!sCityNo.equals(tShortName)) {
				throw new Exception("��" + (i + 2)
						+ "�е�2�С����д��롿����ѡ���б��벻��,��ȷ��!");
			}

			// --------------------SysFlag�����ķָ���----------------------------
			String sSysFlag = eRow.getChildText("SysFlag");
			if (sSysFlag == null || "".equals(sSysFlag)) {
				throw new Exception("��" + (i + 2) + "�е�3�С�ϵͳ��־������Ϊ��!");
			}
			if (!("RLS".equals(sSysFlag) || "LEG".equals(sSysFlag))) {
				throw new Exception("��" + (i + 2) + "�е�3�С�ϵͳ��־����ΪRLS����LEG,��ȷ��!");
			}

		}

		LKContNoDBSet aLKContNoDBSet = new LKContNoDBSet();
		for (int i = 0; i < maxCow; i++) {
			int flag = 0;
			Element eRow = (Element) RowList.get(i);
			String aRowNo = eRow.getChildText("RowNo");
			String aContNo = eRow.getChildText("ContNo");
			String aCityNo = eRow.getChildText("CityNo");
			String aSysFlag = eRow.getChildText("SysFlag");

			LKContNoDB aLKContNoDB = new LKContNoDB();
			aLKContNoDB.setContNo(aContNo);
			aLKContNoDB.setCityCode(aCityNo);
			aLKContNoDB.setSysFlag(aSysFlag);
			aLKContNoDB.setStatus("0");
			aLKContNoDB.setbak1(this.mOperator);
			aLKContNoDB.setMakeDate(DateUtil.getCur10Date());
			aLKContNoDB.setMakeTime(DateUtil.getCur8Time());
			if (aLKContNoDB.getInfo()) {
				iRepeatNo++;
			} else {
					System.out.println("------------------------");
					System.out.println(aLKContNoDBSet.size());
					System.out.println("aContNo:"+aContNo+" aCityNo:"+aCityNo);
					for (int k = 1; k <= aLKContNoDBSet.size(); k++) {
						
						if (aContNo.equals(aLKContNoDBSet.get(k).getContNo())
								&& aCityNo.equals(aLKContNoDBSet.get(k)
										.getCityCode())) {
							iRepeatNo++;
							flag = 1;
						}
					}
					if(flag != 1){
						aLKContNoDBSet.add(aLKContNoDB);
						iSuccNo++;
					}
				}
			}

		if (!aLKContNoDBSet.insert()) {
			iError = aLKContNoDBSet.iError;
			throw new MidplatException("�ڵ�" + (iError + iRepeatNo + 1)
					+ "���ύʧ��!");
		}

		iEndNo = maxCow + 2;

		// ----------------------------------------------------------------

		if (this.mErrors.needDealError()) {
			System.out.println(this.mErrors.getErrorCount() + "error:"
					+ this.mErrors.getFirstError());
		}

		return true;
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
				System.out.println("ExtendFileName;" + aExtendFileName);
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
		long sum = 0;

		for (int i = 0; i <= 5000; i++) {
			for (int j = 0; j <= 5000; j++) {
				sum += i + j;
			}
		}
		System.out.println(sum);
	}

}
