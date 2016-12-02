package com.sinosoft.lis.input;

import java.util.List;

import com.sinosoft.lis.db.AxaAgentDB;
import com.sinosoft.lis.db.NodeMapDB;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.vschema.AxaAgentSet;
import com.sinosoft.lis.vschema.NodeMapSet;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
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
public class NodeMapImport {
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
	
	public int iInsertNo = 0;
	
	public int iUpdateNo = 0;
	
	public int iRepeatNo = 0;
	
	public int iEndNo = 0;

	private Document mDoc = null;

	private TransferData mTransferData = new TransferData();

	private String mFileName;
	
	private String mManageCodeNo="";
	
	public String XmlFileName;

	private String mFilePath;

	private String mImportFlag;

	// ����Xml�����ڵ�����
	private String FilePath = "/";

	// �����ļ�Xml�ڵ�����
	private String ImportFileName;

	private String ConfigFileName;
	
	private String mOperator = "";
	
	private String mManageCom = "";
	
	private String mAreaNo = "";
	
	private String mCityNo = "";

	private org.w3c.dom.Document m_doc = null; //

	private String[] m_strDataFiles = null;


	public NodeMapImport() {
		bulidDocument();
	}

	public NodeMapImport(String fileName) {
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
		mManageCodeNo = (String) mTransferData.getValueByName("ManageCodeNo");
		mOperator = mGlobalInput.Operator;
		mManageCom = mGlobalInput.ManageCom;
		if(mManageCom != null && mManageCom.length() == 4){
			this.mAreaNo = mManageCom.substring(2, 4);
		}
		if(mManageCom != null && mManageCom.length() == 9){
			this.mAreaNo = mManageCom.substring(6, 3);
		}
		
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

		FilePath = "nodemap/temp/";
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

		ConfigFileName = filePath + "NodeMapImport.xml";
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
		// ��ʼ�������ļ�
		if (!this.initImportFile()) {
			return false;
		}
		// ����xmlУ���ļ�
		if (!this.checkImportConfig()) {
			return false;
		}

		NodeMapVTSParser gcvp = new NodeMapVTSParser();

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
		String tAgentComName= eTop.getChildText("AgentComName");
		String tBankNode = eTop.getChildText("BankNode");
		String tAgentCom = eTop.getChildText("AgentCom");
		String tAgentCode = eTop.getChildText("AgentCode");
		String tAreaNo = eTop.getChildText("AreaNo");
		String tCityNo= eTop.getChildText("CityNo");
		String tConAgentNo = eTop.getChildText("ConAgentNo");
		String tConStartDate = eTop.getChildText("ConStartDate");
		String tConEndDate = eTop.getChildText("ConEndDate");
		String tState = eTop.getChildText("State");
		String tSaleFlag = eTop.getChildText("SaleFlag");
		if(tAgentComName == null || "".equals(tAgentComName) || !"��������".equals(tAgentComName)){
			throw new MidplatException("�ύ�ļ���ʽ������Ҫ��,��1�е�1��ӦΪ���������ơ�!");
		}
		if(tBankNode == null || "".equals(tBankNode) || !"�����������".equals(tBankNode)){
			throw new MidplatException("�ύ�ļ���ʽ������Ҫ��,��1�е�2��ӦΪ������������롿!");
		}
		if(tAgentCom == null || "".equals(tAgentCom) || !"�������".equals(tAgentCom)){
			throw new MidplatException("�ύ�ļ���ʽ������Ҫ��,��1�е�3��ӦΪ��������롿!");
		}
		if(tAgentCode == null || "".equals(tAgentCode) || !"����רԱ����".equals(tAgentCode)){
			throw new MidplatException("�ύ�ļ���ʽ������Ҫ��,��1�е�5��ӦΪ������רԱ���롿!");
		}
		if(tCityNo == null || "".equals(tCityNo) || !"���б���".equals(tCityNo)){
			throw new MidplatException("�ύ�ļ���ʽ������Ҫ��,��1�е�7��ӦΪ�����б��롿!");
		}
		if(tConAgentNo == null || "".equals(tConAgentNo) || !"��ҵ�����ʸ�֤".equals(tConAgentNo)){
			throw new MidplatException("�ύ�ļ���ʽ������Ҫ��,��1�е�8��ӦΪ����ҵ�����ʸ�֤��!");
		}
		if(tConStartDate == null || "".equals(tConStartDate) || !"�ʸ�֤��ʼ��".equals(tConStartDate)){
			throw new MidplatException("�ύ�ļ���ʽ������Ҫ��,��1�е�9��ӦΪ���ʸ�֤��ʼ�ա�!");
		}
		if(tConEndDate == null || "".equals(tConEndDate) || !"�ʸ�֤������".equals(tConEndDate)){
			throw new MidplatException("�ύ�ļ���ʽ������Ҫ��,��1�е�10��ӦΪ���ʸ�֤�����ա�!");
		}
		if(tState == null || "".equals(tState) || !"����ϵͳ״̬".equals(tState)){
			throw new MidplatException("�ύ�ļ���ʽ������Ҫ��,��1�е�11��ӦΪ������ϵͳ״̬��!");
		}
		if(tSaleFlag == null || "".equals(tSaleFlag) || !"���������ʸ�".equals(tSaleFlag)){
			throw new MidplatException("�ύ�ļ���ʽ������Ҫ��,��1�е�12��ӦΪ�����������ʸ�!");
		}
		
		List RowList = eContTable.getChildren("ROW");
		int maxCow = 0;
		if (RowList != null) {
			System.out.println("RowList.size():" + RowList.size());
			maxCow = RowList.size();
		}
		
		if(maxCow == 0){
			throw new MidplatException("�����ļ�Ϊ���ļ�,��ȷ��!");
		} 
		for (int i = 0; i < maxCow; i++) {
			Element eRow = (Element) RowList.get(i);
			String sRowNo = eRow.getChildText("RowNo");
			
			//--------------AgentComName----------------------------------------------------------------
			String sAgentComName = eRow.getChildText("AgentComName");
			if(sAgentComName == null || "".equals(sAgentComName)){
				throw new MidplatException("��" + (i+2) + "�е�1�С��������ơ�����Ϊ��!");
			}
			if(sAgentComName != null && sAgentComName.length()>50){
				throw new MidplatException("��" + (i+2) + "�е�1�С��������ơ�������Ҫ������רԱ���Ʋ��ܳ���50���ַ�!");
			}
			
			//---------------BankNode----------------------------------------------------------------------------
			String sBankNode = eRow.getChildText("BankNode");
			if(sBankNode == null || "".equals(sBankNode)){
				throw new MidplatException("��" + (i+2) + "�е�2�С�����������롿����Ϊ��!");
			}
			if(!MidplatUtil.checkBankNode(sBankNode)){
				throw new MidplatException("��" + (i+2) + "�е�2�С�����������롿������Ҫ������д��ȷ�������������!");
			}
			
			
			//---------------AgentCom----------------------------------------------------------------------------
			String sAgentCom = eRow.getChildText("AgentCom");
			if(sAgentCom == null || "".equals(sAgentCom)){
				throw new MidplatException("��" + (i+2) + "�е�3�С�������롿����Ϊ��!");
			}
			if(!MidplatUtil.checkAxaAgentCom(sAgentCom)){
				throw new MidplatException("��" + (i+2) + "�е�3�С�������롿������Ҫ������д��ȷ���������!");
			}
			
			//---------------AgentCode----------------------------------------------------------------------------
			String sAgentCode = eRow.getChildText("AgentCode");
			if(sAgentCode == null || "".equals(sAgentCode)){
				throw new MidplatException("��" + (i+2) + "�е�5�С�����רԱ���롿����Ϊ��!");
			}
			if(!MidplatUtil.checkAxaAgentCom(sAgentCode)){
				throw new MidplatException("��" + (i+2) + "�е�5�С�����רԱ���롿������Ҫ������д��ȷ������רԱ����!");
			}
//			if(!MidplatUtil.checkUnitCode(sAgentCom, sAgentCode)){
//				throw new MidplatException("��" + (i+2) + "�е�3�к͵�5�е�������Ų���ͬ,��ȷ��!");
//			}
			//--------------------CityNo �����ķָ���----------------------------
			String sCityNo = eRow.getChildText("CityNo");
			if(sCityNo == null || "".equals(sCityNo)){
				throw new Exception("��" + (i+2) + "�е�7�С����б��롿����Ϊ��!");
			}
			if(mManageCom != null && mManageCom.length() == 9){
				if(sCityNo != null && !sCityNo.equals(sCityNo)){
					throw new Exception("��" + (i+2) + "�е�7�С����б��롿��Ϊ��½�û���������!");
				}
			}else if(mManageCom != null && mManageCom.length() <= 4){
				if(!MidplatUtil.checkCityCode(sCityNo)){
					throw new Exception("��" + (i+2) + "�е�7�С����б��롿�����Ϲ���,ӦΪ��λ��Ч����!");
				}
				if(MidplatUtil.checkCityFManageDB(this.mManageCodeNo, sCityNo)){
					throw new Exception("��" + (i+2) + "�е�7�С����б��롿�޶�Ӧ����������ڴ˶�Ӧ���������,��ȷ��!");
				}
			}
			String sManageCom = this.mManageCodeNo;
				//MidplatUtil.getManagecom4ac(sAreaNo, sCityNo);
			
//			if(MidplatUtil.checkAgentCode4DB(sManageCom,sAgentCode.substring(10, 16))){
//				throw new MidplatException("��" + (i+2) + "�е�5�иá�����רԱ���롿������,������Ӹ�����רԱ,���ύ!");
//			}
			
			//---------------ConAgentNo----------------------------------------------------------------------------
			String sConAgentNo = eRow.getChildText("ConAgentNo");
			/*if(sConAgentNo == null || "".equals(sConAgentNo)){
				throw new MidplatException("��" + (i+2) + "�е�6�С���ҵ�����ʸ�֤������Ϊ��!");
			}*/
			if(sConAgentNo != null && sConAgentNo.length()>30){
				throw new MidplatException("��" + (i+2) + "�е�8�С���ҵ�����ʸ�֤��������Ҫ�󣬼�ҵ�����ʸ�֤���ܳ���30���ַ�!");
			}
			
			//---------------ConStartDate----------------------------------------------------------------------------
			String sConStartDate = eRow.getChildText("ConStartDate");
			/*if(sConAgentNo == null || "".equals(sConAgentNo)){
				throw new MidplatException("��" + (i+2) + "�е�7�С��ʸ�֤��ʼ�ա�����Ϊ��!");
			}*/
			if(sConStartDate != null && !"".equals(sConStartDate)){
				if(!DateUtil.checkDate(sConStartDate)){
					System.out.println("sConStartDate:" + sConStartDate);
					throw new MidplatException("��" + (i+2) + "�е�9�С��ʸ�֤��ʼ�ա�������Ҫ������д��ȷ�����ڡ�yyyy-mm-dd��!");
				}
			} 
			
			//---------------ConEndDate----------------------------------------------------------------------------
			String sConEndDate = eRow.getChildText("ConEndDate");
			/*if(sConAgentNo == null || "".equals(sConAgentNo)){
				throw new MidplatException("��" + (i+2) + "�е�8�С��ʸ�֤�����ա�����Ϊ��!");
			}*/
			if(sConEndDate != null && !"".equals(sConEndDate)){
				if(!DateUtil.checkDate(sConEndDate)){
					
					throw new MidplatException("��" + (i+2) + "�е�10�С��ʸ�֤�����ա�������Ҫ������д��ȷ�����ڡ�yyyy-mm-dd��!");
				}
			}
			
			//---------------State----------------------------------------------------------------------------
			String sState = eRow.getChildText("State");
			/*if(sConAgentNo == null || "".equals(sConAgentNo)){
				throw new MidplatException("��" + (i+2) + "�е�9�С�����ϵͳ״̬������Ϊ��!");
			}*/
			
			if(sState != null){
				System.out.println(sState);
				if(!"".equals(sState) && !"T".equals(sState)){
					throw new MidplatException("��" + (i+2) + "�е�11�С�����ϵͳ״̬��������Ҫ��ֻ��Ϊ�ջ���'T'!");
				}
			}
			//---------------SaleFlag----------------------------------------------------------------------------
			String sSaleFlag = eRow.getChildText("SaleFlag");
			/*if(sConAgentNo == null || "".equals(sConAgentNo)){
				throw new MidplatException("��" + (i+2) + "�е�10�С����������ʸ񡿲���Ϊ��!");
			}*/
			if(sSaleFlag != null){
				if(!"".equals(sSaleFlag) && !"L".equals(sSaleFlag)&&!"B".equals(sSaleFlag) ){
					throw new MidplatException("��" + (i+2) + "�е�12�С����������ʸ񡿲�����Ҫ��ֻ��Ϊ�ա�'L'����'B' !");
				}
			}
			
			
		}
		
		NodeMapSet aNodeMapSet = new NodeMapSet();
		for (int i = 0; i < maxCow; i++) {
			Element eRow = (Element) RowList.get(i);
			String aRowNo = eRow.getChildText("RowNo");
			//String sAreaNo = eRow.getChildText("AreaNo");
			String sCityNo = eRow.getChildText("CityNo");
			String sManageCom = this.mManageCodeNo;
				//MidplatUtil.getManagecom4ac(sAreaNo, sCityNo);
			String sAgentComName = eRow.getChildText("AgentComName");
			
			String sBankNode = eRow.getChildText("BankNode");
			String sTranCom = sBankNode.substring(0, 3);
			int iTranCom = Integer.valueOf(sTranCom);
			String sZoneNo = "";
			String sNodeNo = "";
			if(iTranCom == 11){
				 sZoneNo = sBankNode.substring(4, 9);
				 sNodeNo = sBankNode.substring(10, 15);
			}
			if(iTranCom == 12){
				 sZoneNo = sBankNode.substring(4, 10);
				 sNodeNo = sBankNode.substring(11, 17);
			}

			
			String sAxaAgentCom = eRow.getChildText("AgentCom").trim();
			String sUnitCode = sAxaAgentCom.substring(0, 6);
			String sAgentGrade = sAxaAgentCom.substring(7,9);
			String sAgentCom = sAxaAgentCom.substring(10, 16);
			
			String sAxaAgentCode = eRow.getChildText("AgentCode");
			String sAgentCodeGrade = sAxaAgentCode.substring(7, 9);
			String sAgentCode = sAxaAgentCode.substring(10, 16);
			
			
			String ssManagecom = MidplatUtil.getManagecom4ac(sCityNo);
			String sAgentName = MidplatUtil.checkAgentCodeName4DB(ssManagecom,sAgentCode);
			
			String sConAgentNo = eRow.getChildText("ConAgentNo");
			String sConStartDate = eRow.getChildText("ConStartDate");
			String sConEndDate = eRow.getChildText("ConEndDate");
			String sState = eRow.getChildText("State");
			String sSaleFlag = eRow.getChildText("SaleFlag");
			String CityNoForMa=eRow.getChildText("CityNo");
			NodeMapDB mNodeMapDB = new NodeMapDB();
			mNodeMapDB.setTranCom(iTranCom);
			mNodeMapDB.setZoneNo(sZoneNo);
			mNodeMapDB.setNodeNo(sNodeNo);
			
			String mSqlStr = new StringBuilder(
			"select MapNo from NodeMap where Type=").append("0")
				.append(" and TranCom=").append(iTranCom)
				.append(" and ZoneNo='").append(sZoneNo).append('\'')
				.append(" and NodeNo='").append(sNodeNo).append('\'')
				.toString();
			String MapNo = new ExeSQL().getOneValue(mSqlStr);
			System.out.println("MapNO==========================="+MapNo);
			boolean MapFlag=false;
			if(MapNo!=""){
				MapFlag=true;
				mNodeMapDB.setMapNo(MapNo);
			}
			//boolean ifAgentCom = MidplatUtil.checkAgentCom4DB(sManageCom, sAgentCom);
			if(!MapFlag){//�������㲻����
				System.out.println(mNodeMapDB.getTranCom()+"-" + mNodeMapDB.getZoneNo()+"-" + mNodeMapDB.getNodeNo() +":�����㲻����!");
				if(MidplatUtil.checkAgentCom4DB(sManageCom, sAgentCom)){//���չ�˾���㲻����
					String sqlstr="select Max(mapno)+1 from nodemap";
					SSRS ssrs=new SSRS();
					ssrs=new ExeSQL().execSQL(sqlstr);
					String MaxMapno=ssrs.GetText(1, 1);
					int in=Integer.valueOf(MaxMapno);
					System.out.println("��һ��MapNo�ǣ�"+in);
					mNodeMapDB.setMapNo(in);
					
					mNodeMapDB.setType(0);
					mNodeMapDB.setAgentCom(sAgentCom);
					mNodeMapDB.setAgentComName(sAgentComName);
					mNodeMapDB.setAgentCode(sAgentCode);
					mNodeMapDB.setAgentName(sAgentName);
					sqlstr="select comcode from ldcom where substr(comcode,7,3)='"+CityNoForMa+"' and comgrade='04'";
					ssrs=new ExeSQL().execSQL(sqlstr);
					sManageCom=ssrs.GetText(1, 1);
					mNodeMapDB.setManageCom(sManageCom);
					mNodeMapDB.setUnitCode(sUnitCode); 
					mNodeMapDB.setAgentGrade(sAgentGrade);
					mNodeMapDB.setAgentCodeGrade(sAgentCodeGrade);
					mNodeMapDB.setConAgentNo(sConAgentNo);
					if(sConStartDate != null && !"".equals(sConEndDate)){
						mNodeMapDB.setConStartDate(sConStartDate);
					}
					if(sConEndDate != null && !"".equals(sConEndDate)){
						mNodeMapDB.setConEndDate(sConEndDate);
					}
					mNodeMapDB.setSaleFlag(sSaleFlag);
					mNodeMapDB.setState(sState);
					mNodeMapDB.setMakeDate(DateUtil.getCur8Date());
					mNodeMapDB.setMakeTime(DateUtil.getCur6Time());
					mNodeMapDB.setOperator(mOperator);
					
					if(!mNodeMapDB.insert()){
						throw new MidplatException("�ڵ�" + aRowNo + "�в�������ʧ��!");
					}else{
						aNodeMapSet.add(mNodeMapDB);
						iInsertNo ++;
					}
					
				}else{//���չ�˾�������
					throw new MidplatException("�ڵ�" + aRowNo + "�е������Ѵ�������������������,��ȷ��!");
				}
				
			}else{//�����������
				mNodeMapDB.getInfo();
				System.out.println(mNodeMapDB.getTranCom()+"-" + mNodeMapDB.getZoneNo()+"-" + mNodeMapDB.getNodeNo() +":�������Ѵ���!");
				if(MidplatUtil.checkAgentCom4DB(sManageCom, sAgentCom)){//���չ�˾���㲻����
					System.out.println(sAgentCom +":���չ�˾���㲻����!");
					mNodeMapDB.setType(0);
					mNodeMapDB.setAgentCom(sAgentCom);
					mNodeMapDB.setAgentComName(sAgentComName);
					mNodeMapDB.setAgentCode(sAgentCode);
					mNodeMapDB.setAgentName(sAgentName);
					SSRS ssrs=new SSRS();
					String sqlstr="select comcode from ldcom where substr(comcode,7,3)='"+CityNoForMa+"' and comgrade='04'";
					ssrs=new ExeSQL().execSQL(sqlstr);
					sManageCom=ssrs.GetText(1, 1);
					mNodeMapDB.setManageCom(sManageCom);
					mNodeMapDB.setUnitCode(sUnitCode); 
					mNodeMapDB.setAgentGrade(sAgentGrade);
					mNodeMapDB.setAgentCodeGrade(sAgentCodeGrade);
					mNodeMapDB.setConAgentNo(sConAgentNo);
				    mNodeMapDB.setConStartDate(sConStartDate);
				    mNodeMapDB.setConEndDate(sConEndDate);
					mNodeMapDB.setSaleFlag(sSaleFlag);
					mNodeMapDB.setState(sState);
					mNodeMapDB.setModifyDate(DateUtil.getCur8Date());
					mNodeMapDB.setModifyTime(DateUtil.getCur6Time());
					mNodeMapDB.setOperator(mOperator);
					if(!mNodeMapDB.update()){
						throw new MidplatException("�ڵ�" + aRowNo + "�и�������ʧ��!");
					}
					else{
						aNodeMapSet.add(mNodeMapDB);
						iUpdateNo ++;
					}
				}
				else if(mNodeMapDB.getManageCom().equals(sManageCom)  && mNodeMapDB.getAgentCom().equals(sAgentCom)){//��ͬ����
					System.out.println(sAgentCom +":���չ�˾�������!");
					mNodeMapDB.setType(0);
					mNodeMapDB.setAgentCom(sAgentCom);
					mNodeMapDB.setAgentComName(sAgentComName);
					mNodeMapDB.setAgentCode(sAgentCode);
					mNodeMapDB.setAgentName(sAgentName);
					SSRS ssrs=new SSRS();
					String sqlstr="select comcode from ldcom where substr(comcode,7,3)='"+CityNoForMa+"' and comgrade='04'";
					ssrs=new ExeSQL().execSQL(sqlstr);
					sManageCom=ssrs.GetText(1, 1);
					mNodeMapDB.setManageCom(sManageCom);
					mNodeMapDB.setUnitCode(sUnitCode); 
					mNodeMapDB.setAgentGrade(sAgentGrade);
					mNodeMapDB.setAgentCodeGrade(sAgentCodeGrade);
					mNodeMapDB.setConAgentNo(sConAgentNo);
					mNodeMapDB.setConStartDate(sConStartDate);
					mNodeMapDB.setConEndDate(sConEndDate);
					mNodeMapDB.setSaleFlag(sSaleFlag);
					mNodeMapDB.setState(sState);
					mNodeMapDB.setModifyDate(DateUtil.getCur8Date());
					mNodeMapDB.setModifyTime(DateUtil.getCur6Time());
					mNodeMapDB.setOperator(mOperator);
					if(!mNodeMapDB.update()){
						throw new MidplatException("�ڵ�" + aRowNo + "�и�������ʧ��!");
					}
					else{
						aNodeMapSet.add(mNodeMapDB);
						iUpdateNo ++;
					}
				}else if(!MidplatUtil.checkAgentCom4DB(sManageCom, sAgentCom)){
					throw new MidplatException("�ڵ�" + aRowNo + "�е������Ѵ�������������������,��ȷ��!");
				}
				
				iRepeatNo ++ ;
			}
		}
		TransferData tTransferData = new TransferData();
		iEndNo = maxCow + 2;
		mInputData.add(aNodeMapSet);

		
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
	
	public static void main(String [] args){
		String mManageCom = "202003-03-088884";
		if(mManageCom != null && !"86".equals(mManageCom)){
			System.out.println(mManageCom.substring(7, 9));
			System.out.println(mManageCom.substring(6, 9));
		}
	}

}
