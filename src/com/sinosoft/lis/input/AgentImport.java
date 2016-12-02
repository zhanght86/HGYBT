package com.sinosoft.lis.input;

import java.util.List;

import com.sinosoft.lis.db.AxaAgentDB;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.vschema.AxaAgentSet;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
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
public class AgentImport {
	// @Fields
	/** �������࣬ÿ����Ҫ����������ж����ø��� */
	public CErrors mErrors = new CErrors();

	public CErrors logErrors = new CErrors();

	/** ��ǰ�洫�����ݵ����� */
	private VData mResult = new VData();

	/** �����洫�����ݵ����� */
	private VData mInputData;

	/** ȫ������*/
	private GlobalInput mGlobalInput = new GlobalInput();
	
	public String sError = "";
	
	public int iSuccNo = 0;
	
	public int iRepeatNo = 0;
	
	public int iUpdateNo = 0;
	
	public int iEndNo = 0;

	private Document mDoc = null;

	private TransferData mTransferData = new TransferData();

	private String mFileName;

	public String XmlFileName;

	private String mFilePath;

	private String mImportFlag;

	// ����Xml�����ڵ�����
	private String FilePath = "/";

	// �����ļ�Xml�ڵ�����
	private String ImportFileName;

	private String ConfigFileName;
	
	private String mOperator = "";
	
	private String mAreaNo = "";
	
	private String mCityNo = "";
	
	private String mManageCom = "";

	private org.w3c.dom.Document m_doc = null; //

	private String[] m_strDataFiles = null;


	public AgentImport() {
		bulidDocument();
	}

	public AgentImport(String fileName) {
		bulidDocument();
		mFileName = fileName;
	}

	
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
			tError.errorMessage = "�����ļ���ʽ����!"+ex.getMessage();
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
		//mManageCom = (String) mTransferData.getValueByName("ManageCom");
		System.out.println("����:" + mAreaNo + "����:" + mCityNo+"���������"+mManageCom);
		mOperator = mGlobalInput.Operator;
		mManageCom = mGlobalInput.ManageCom;
		System.out.println("�û�:" + mOperator + "���ڽ�������רԱ�����ύ");
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
		/*if (mManageCom != null && "86".equals(mManageCom)) {
			// @@������
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "�ò���Ա���������޵�������רԱ��Ȩ��!";
			this.mErrors.addOneError(tError);
			return false;
		}*/
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

		FilePath = "agent/temp/";
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

		ConfigFileName = filePath + "AgentImport.xml";
		System.out.println("�û�:" + mOperator + ":���õ�ģ���ļ�Ϊ��" +ConfigFileName);
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
		System.out.println("����excel��ת����xml�ļ���ʼ����������������������");
		// ��ʼ�������ļ�
		if (!this.initImportFile()) {
			return false;
		}
		// ����xmlУ���ļ�
		if (!this.checkImportConfig()) {
			return false;
		}

		AgentVTSParser gcvp = new AgentVTSParser();

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
		System.out.println("����excel��ת����xml�ļ���ȷ��������������������������");
		return true;
	}

	
	/**
	 * ����xml,
	 * @return
	 */
	private boolean ParseXml() throws Exception {

		if (!checkXmlFileName()) {
			return false;
		}

		Document newDoc = mDoc;


		//----------------------------------------------------------------
		Element eDate = newDoc.getRootElement();
		Element eContTable = eDate.getChild("CONTTABLE");
		Element eTop = eContTable.getChild("Top");
		//String tAreaNo = eTop.getChildText("AreaNo");
		String tCityNo = eTop.getChildText("CityNo");
		String tAgentCode = eTop.getChildText("AgentCode");
		String tAgentName = eTop.getChildText("AgentName");
//		if(tAreaNo == null || "".equals(tAreaNo) || !"��������".equals(tAreaNo)){
//			throw new Exception("�ύ�ļ���ʽ������Ҫ��,��1�е�1��ӦΪ���������롿!");
//		}
		if(tCityNo == null || "".equals(tCityNo) || !"���б���".equals(tCityNo)){
			throw new Exception("�ύ�ļ���ʽ������Ҫ��,��1�е�1��ӦΪ�����б��롿!");
		}
		if(tAgentCode == null || "".equals(tAgentCode) || !"����רԱ����".equals(tAgentCode)){
			throw new Exception("�ύ�ļ���ʽ������Ҫ��,��1�е�2��ӦΪ������רԱ���롿!");
		}
		if(tAgentName == null || "".equals(tAgentName) || !"����רԱ����".equals(tAgentName)){
			throw new Exception("�ύ�ļ���ʽ������Ҫ��,��1�е�3��ӦΪ������רԱ���ơ�!");
		}
		
		List RowList = eContTable.getChildren("ROW");
		int maxCow = 0;
		if (RowList != null) {
			maxCow = RowList.size();
		}
		
		for (int i = 0; i < maxCow; i++) {
			Element eRow = (Element) RowList.get(i);
			String sRowNo = eRow.getChildText("RowNo");
			
			//--------------------AreaNo �����ķָ���----------------------------
			String sAreaNo = eRow.getChildText("AreaNo");
//			if(sAreaNo == null || "".equals(sAreaNo)){
//				throw new Exception("��" + (i+2) + "�е�1�С��������롿����Ϊ��!");
//			}
//			if(mManageCom != null && !"86".equals(mManageCom)){
//				if(sAreaNo != null && !sAreaNo.equals(mAreaNo)){
//					throw new Exception("��" + (i+2) + "�е�1�С��������롿��Ϊ��½�û�������������!");
//				}
//			}else if(mManageCom != null && "86".equals(mManageCom)){
//				if(!(sAreaNo.equals("01") || sAreaNo.equals("02") || sAreaNo.equals("03") || sAreaNo.equals("04"))){
//					throw new Exception("��" + (i+2) + "�е�1�С��������롿�����Ϲ���!,����д��ȷ�ĵ�������");
//				}
//			}
			
			//--------------------CityNo �����ķָ���----------------------------
			String sCityNo = eRow.getChildText("CityNo");
			if(sCityNo == null || "".equals(sCityNo)){
				throw new Exception("��" + (i+2) + "�е�1�С����б��롿����Ϊ��!");
			}
			if(mManageCom != null && mManageCom.length() == 9){
				if(sCityNo != null && !sCityNo.equals(mCityNo)){
					throw new Exception("��" + (i+2) + "�е�1�С����б��롿��Ϊ��½�û���������!");
				}
			}else if(mManageCom != null && mManageCom.length() <= 4){
				if(!MidplatUtil.checkCityCode(sCityNo)){
					throw new Exception("��" + (i+2) + "�е�1�С����б��롿�����Ϲ���,ӦΪ��λ��Ч����!");
				}
				if(MidplatUtil.checkCityCode4DB(sCityNo)){
					throw new Exception("��" + (i+2) + "�е�1�С����б��롿�޶�Ӧ�������,��ȷ��!");
				}
			}
			
			//--------------------AgentCode �����ķָ���----------------------------
			String sAgentCode = eRow.getChildText("AgentCode");
			if(sAgentCode == null || "".equals(sAgentCode)){
				throw new Exception("��" + (i+2) + "�е�2�С�����רԱ���롿����Ϊ��!");
			}
			if(!MidplatUtil.checkAgentCode(sAgentCode)){
				throw new Exception("��" + (i+2) + "�е�2�С�����רԱ���롿������Ҫ������д6λ��Ч����!");
			}
			String sAgentName = eRow.getChildText("AgentName");
			if(sAgentName == null || "".equals(sAgentName)){
				throw new Exception("��" + (i+2) + "�е�2�С�����רԱ���ơ�����Ϊ��!");
			}
			if(sAgentName.length() > 50){
				throw new Exception("��" + (i+2) + "�е�2�С�����רԱ���ơ�������Ҫ������רԱ���Ʋ��ܳ���50���ַ�!");
			}
			
		}
		
		AxaAgentSet aAxaAgentSet = new AxaAgentSet();
		for (int i = 0; i < maxCow; i++) {
			Element eRow = (Element) RowList.get(i);
			String aRowNo = eRow.getChildText("RowNo");
			String aAreaNo = eRow.getChildText("AreaNo");
			String aCityNo = eRow.getChildText("CityNo");
			String aAgentCode = eRow.getChildText("AgentCode");
			String aAgentName = eRow.getChildText("AgentName");
			String aManageCom = MidplatUtil.getManagecom4ac(aCityNo);
			AxaAgentDB aAxaAgentDB = new AxaAgentDB();
			aAxaAgentDB.setAgentCode(aAgentCode);
			aAxaAgentDB.setManageCom(aManageCom);
			if(!aAxaAgentDB.getInfo()){
				aAxaAgentDB.setAgentName(aAgentName);
				aAxaAgentDB.setMakeDate(DateUtil.getCur8Date());
				aAxaAgentDB.setMakeTime(DateUtil.getCur6Time());
				aAxaAgentDB.setOperator(mOperator);
				if(aAxaAgentDB.insert()){
					aAxaAgentSet.add(aAxaAgentDB);
					iSuccNo ++;
				}else{
					
					throw new Exception("����רԱ����ά��,�������ʧ��!");
				}
			}else{
				System.out.println("aRowNo:" + (i+2)+"_aManageCom:"+aManageCom + "_aAgentCode:"+aAgentCode+"_aAgentName:"+aAgentName
						+"_aAxaAgentDB.getAgentName():"+aAxaAgentDB.getAgentName()
				);
				if(!aAxaAgentDB.getAgentName().equals(aAgentName)){
					aAxaAgentDB.setAgentName(aAgentName);
					if(aAxaAgentDB.update()){
						iUpdateNo ++;
						String sSql = "UPDATE NODEMAP SET AGENTNAME = '" +aAxaAgentDB.getAgentName()+ "' WHERE MANAGECOM='"+aAxaAgentDB.getManageCom()+"' AND AGENTCODE = '"+aAxaAgentDB.getAgentCode()+"'";
						ExeSQL exeSQL = new ExeSQL();
						
						if (!exeSQL.execUpdateSQL(sSql)) {
							throw new MidplatException("��������רԱ��Ϣʧ�ܣ�");
						}
					}else{
						throw new Exception("����רԱ����ά��,�������ʧ��!");
					}
				}
				iRepeatNo ++ ;
			}
		}
		TransferData tTransferData = new TransferData();
		iEndNo = maxCow + 2;
		mInputData.add(aAxaAgentSet);

		
		//----------------------------------------------------------------

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
	 * Build a instance document object for function transfromNode()
	 */
	private void bulidDocument() {
		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		dfactory.setNamespaceAware(true);

		try {
			m_doc = dfactory.newDocumentBuilder().newDocument();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * �õ���־��ʾ���
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}
	
	public void saveAaxaAgent(Document stdDoc){
		
	}

}
