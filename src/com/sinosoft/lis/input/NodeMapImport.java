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
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	public CErrors logErrors = new CErrors();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 全局数据*/
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

	// 数据Xml解析节点描述
	private String FilePath = "/";

	// 配置文件Xml节点描述
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
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "导入文件格式有误!"+ex.getMessage();
			this.logErrors.addOneError(tError);
		}

		if (this.logErrors.getErrorCount() > 0) {
			this.mErrors = this.logErrors;
			System.out.println("结束时间:" + PubFun.getCurrentTime());
			return false;
		}

		return true;
	}

	
	/**
	 * 得到传入数据
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
		
		System.out.println("用户:" + mOperator + "正在进行网点专员批量提交");
	}

	/**
	 * 校验传输数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		if (mGlobalInput == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "无操作员信息，请重新登录!";
			this.mErrors.addOneError(tError);
			return false;
		}
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
	private boolean getFilePath() {

		FilePath = "nodemap/temp/";
		return true;
	}

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
		this.getFilePath();

		String filePath = mFilePath + FilePath;
		
		File tFile = new File(filePath);
		if (!tFile.exists()) {
			tFile.mkdirs();
		}

		ConfigFileName = filePath + "NodeMapImport.xml";
		System.out.println("用户:" + mOperator + ":试用的模板文件为：" +ConfigFileName);
		File tFile2 = new File(ConfigFileName);
		if (!tFile2.exists()) {
			System.out.println("检查导入配置文件是否存在");
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "请上传配置文件到指定路径" + FilePath + "!";
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
		this.getFilePath();
		ImportFileName = mFilePath + FilePath + mFileName;
		System.out.println("用户:" + mOperator + "提交的上传文件的路径为:" + ImportFileName);
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
		System.out.println("用户:" + mOperator + "文件上传成功");
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

		NodeMapVTSParser gcvp = new NodeMapVTSParser();

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
		if(tAgentComName == null || "".equals(tAgentComName) || !"网点名称".equals(tAgentComName)){
			throw new MidplatException("提交文件格式不符合要求,第1行第1列应为【网点名称】!");
		}
		if(tBankNode == null || "".equals(tBankNode) || !"银行网点编码".equals(tBankNode)){
			throw new MidplatException("提交文件格式不符合要求,第1行第2列应为【银行网点编码】!");
		}
		if(tAgentCom == null || "".equals(tAgentCom) || !"网点编码".equals(tAgentCom)){
			throw new MidplatException("提交文件格式不符合要求,第1行第3列应为【网点编码】!");
		}
		if(tAgentCode == null || "".equals(tAgentCode) || !"网点专员编码".equals(tAgentCode)){
			throw new MidplatException("提交文件格式不符合要求,第1行第5列应为【网点专员编码】!");
		}
		if(tCityNo == null || "".equals(tCityNo) || !"城市编码".equals(tCityNo)){
			throw new MidplatException("提交文件格式不符合要求,第1行第7列应为【城市编码】!");
		}
		if(tConAgentNo == null || "".equals(tConAgentNo) || !"兼业代理资格证".equals(tConAgentNo)){
			throw new MidplatException("提交文件格式不符合要求,第1行第8列应为【兼业代理资格证】!");
		}
		if(tConStartDate == null || "".equals(tConStartDate) || !"资格证起始日".equals(tConStartDate)){
			throw new MidplatException("提交文件格式不符合要求,第1行第9列应为【资格证起始日】!");
		}
		if(tConEndDate == null || "".equals(tConEndDate) || !"资格证到期日".equals(tConEndDate)){
			throw new MidplatException("提交文件格式不符合要求,第1行第10列应为【资格证到期日】!");
		}
		if(tState == null || "".equals(tState) || !"网点系统状态".equals(tState)){
			throw new MidplatException("提交文件格式不符合要求,第1行第11列应为【网点系统状态】!");
		}
		if(tSaleFlag == null || "".equals(tSaleFlag) || !"网点销售资格".equals(tSaleFlag)){
			throw new MidplatException("提交文件格式不符合要求,第1行第12列应为【网点销售资格】!");
		}
		
		List RowList = eContTable.getChildren("ROW");
		int maxCow = 0;
		if (RowList != null) {
			System.out.println("RowList.size():" + RowList.size());
			maxCow = RowList.size();
		}
		
		if(maxCow == 0){
			throw new MidplatException("上载文件为空文件,请确认!");
		} 
		for (int i = 0; i < maxCow; i++) {
			Element eRow = (Element) RowList.get(i);
			String sRowNo = eRow.getChildText("RowNo");
			
			//--------------AgentComName----------------------------------------------------------------
			String sAgentComName = eRow.getChildText("AgentComName");
			if(sAgentComName == null || "".equals(sAgentComName)){
				throw new MidplatException("第" + (i+2) + "行第1列【网点名称】不能为空!");
			}
			if(sAgentComName != null && sAgentComName.length()>50){
				throw new MidplatException("第" + (i+2) + "行第1列【网点名称】不符合要求，网点专员名称不能超过50个字符!");
			}
			
			//---------------BankNode----------------------------------------------------------------------------
			String sBankNode = eRow.getChildText("BankNode");
			if(sBankNode == null || "".equals(sBankNode)){
				throw new MidplatException("第" + (i+2) + "行第2列【银行网点编码】不能为空!");
			}
			if(!MidplatUtil.checkBankNode(sBankNode)){
				throw new MidplatException("第" + (i+2) + "行第2列【银行网点编码】不符合要求，请填写正确的银行网点编码!");
			}
			
			
			//---------------AgentCom----------------------------------------------------------------------------
			String sAgentCom = eRow.getChildText("AgentCom");
			if(sAgentCom == null || "".equals(sAgentCom)){
				throw new MidplatException("第" + (i+2) + "行第3列【网点编码】不能为空!");
			}
			if(!MidplatUtil.checkAxaAgentCom(sAgentCom)){
				throw new MidplatException("第" + (i+2) + "行第3列【网点编码】不符合要求，请填写正确的网点编码!");
			}
			
			//---------------AgentCode----------------------------------------------------------------------------
			String sAgentCode = eRow.getChildText("AgentCode");
			if(sAgentCode == null || "".equals(sAgentCode)){
				throw new MidplatException("第" + (i+2) + "行第5列【网点专员编码】不能为空!");
			}
			if(!MidplatUtil.checkAxaAgentCom(sAgentCode)){
				throw new MidplatException("第" + (i+2) + "行第5列【网点专员编码】不符合要求，请填写正确的网点专员编码!");
			}
//			if(!MidplatUtil.checkUnitCode(sAgentCom, sAgentCode)){
//				throw new MidplatException("第" + (i+2) + "行第3列和第5列的销售组号不相同,请确认!");
//			}
			//--------------------CityNo 华丽的分割线----------------------------
			String sCityNo = eRow.getChildText("CityNo");
			if(sCityNo == null || "".equals(sCityNo)){
				throw new Exception("第" + (i+2) + "行第7列【城市编码】不能为空!");
			}
			if(mManageCom != null && mManageCom.length() == 9){
				if(sCityNo != null && !sCityNo.equals(sCityNo)){
					throw new Exception("第" + (i+2) + "行第7列【城市编码】不为登陆用户所属城市!");
				}
			}else if(mManageCom != null && mManageCom.length() <= 4){
				if(!MidplatUtil.checkCityCode(sCityNo)){
					throw new Exception("第" + (i+2) + "行第7列【城市编码】不符合规则,应为三位有效数字!");
				}
				if(MidplatUtil.checkCityFManageDB(this.mManageCodeNo, sCityNo)){
					throw new Exception("第" + (i+2) + "行第7列【城市编码】无对应管理机构或不在此对应管理机构下,请确认!");
				}
			}
			String sManageCom = this.mManageCodeNo;
				//MidplatUtil.getManagecom4ac(sAreaNo, sCityNo);
			
//			if(MidplatUtil.checkAgentCode4DB(sManageCom,sAgentCode.substring(10, 16))){
//				throw new MidplatException("第" + (i+2) + "行第5列该【网点专员编码】不存在,请先添加该网点专员,再提交!");
//			}
			
			//---------------ConAgentNo----------------------------------------------------------------------------
			String sConAgentNo = eRow.getChildText("ConAgentNo");
			/*if(sConAgentNo == null || "".equals(sConAgentNo)){
				throw new MidplatException("第" + (i+2) + "行第6列【兼业代理资格证】不能为空!");
			}*/
			if(sConAgentNo != null && sConAgentNo.length()>30){
				throw new MidplatException("第" + (i+2) + "行第8列【兼业代理资格证】不符合要求，兼业代理资格证不能超过30个字符!");
			}
			
			//---------------ConStartDate----------------------------------------------------------------------------
			String sConStartDate = eRow.getChildText("ConStartDate");
			/*if(sConAgentNo == null || "".equals(sConAgentNo)){
				throw new MidplatException("第" + (i+2) + "行第7列【资格证起始日】不能为空!");
			}*/
			if(sConStartDate != null && !"".equals(sConStartDate)){
				if(!DateUtil.checkDate(sConStartDate)){
					System.out.println("sConStartDate:" + sConStartDate);
					throw new MidplatException("第" + (i+2) + "行第9列【资格证起始日】不符合要求，请填写正确的日期【yyyy-mm-dd】!");
				}
			} 
			
			//---------------ConEndDate----------------------------------------------------------------------------
			String sConEndDate = eRow.getChildText("ConEndDate");
			/*if(sConAgentNo == null || "".equals(sConAgentNo)){
				throw new MidplatException("第" + (i+2) + "行第8列【资格证到期日】不能为空!");
			}*/
			if(sConEndDate != null && !"".equals(sConEndDate)){
				if(!DateUtil.checkDate(sConEndDate)){
					
					throw new MidplatException("第" + (i+2) + "行第10列【资格证到期日】不符合要求，请填写正确的日期【yyyy-mm-dd】!");
				}
			}
			
			//---------------State----------------------------------------------------------------------------
			String sState = eRow.getChildText("State");
			/*if(sConAgentNo == null || "".equals(sConAgentNo)){
				throw new MidplatException("第" + (i+2) + "行第9列【网点系统状态】不能为空!");
			}*/
			
			if(sState != null){
				System.out.println(sState);
				if(!"".equals(sState) && !"T".equals(sState)){
					throw new MidplatException("第" + (i+2) + "行第11列【网点系统状态】不符合要求，只能为空或者'T'!");
				}
			}
			//---------------SaleFlag----------------------------------------------------------------------------
			String sSaleFlag = eRow.getChildText("SaleFlag");
			/*if(sConAgentNo == null || "".equals(sConAgentNo)){
				throw new MidplatException("第" + (i+2) + "行第10列【网点销售资格】不能为空!");
			}*/
			if(sSaleFlag != null){
				if(!"".equals(sSaleFlag) && !"L".equals(sSaleFlag)&&!"B".equals(sSaleFlag) ){
					throw new MidplatException("第" + (i+2) + "行第12列【网点销售资格】不符合要求，只能为空、'L'或者'B' !");
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
			if(!MapFlag){//银行网点不存在
				System.out.println(mNodeMapDB.getTranCom()+"-" + mNodeMapDB.getZoneNo()+"-" + mNodeMapDB.getNodeNo() +":该网点不存在!");
				if(MidplatUtil.checkAgentCom4DB(sManageCom, sAgentCom)){//保险公司网点不存在
					String sqlstr="select Max(mapno)+1 from nodemap";
					SSRS ssrs=new SSRS();
					ssrs=new ExeSQL().execSQL(sqlstr);
					String MaxMapno=ssrs.GetText(1, 1);
					int in=Integer.valueOf(MaxMapno);
					System.out.println("下一个MapNo是："+in);
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
						throw new MidplatException("在第" + aRowNo + "行插入网点失败!");
					}else{
						aNodeMapSet.add(mNodeMapDB);
						iInsertNo ++;
					}
					
				}else{//保险公司网点存在
					throw new MidplatException("在第" + aRowNo + "行的网点已存在于其他银行网点下,请确认!");
				}
				
			}else{//银行网点存在
				mNodeMapDB.getInfo();
				System.out.println(mNodeMapDB.getTranCom()+"-" + mNodeMapDB.getZoneNo()+"-" + mNodeMapDB.getNodeNo() +":该网点已存在!");
				if(MidplatUtil.checkAgentCom4DB(sManageCom, sAgentCom)){//保险公司网点不存在
					System.out.println(sAgentCom +":保险公司网点不存在!");
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
						throw new MidplatException("在第" + aRowNo + "行更新网点失败!");
					}
					else{
						aNodeMapSet.add(mNodeMapDB);
						iUpdateNo ++;
					}
				}
				else if(mNodeMapDB.getManageCom().equals(sManageCom)  && mNodeMapDB.getAgentCom().equals(sAgentCom)){//相同网点
					System.out.println(sAgentCom +":保险公司网点存在!");
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
						throw new MidplatException("在第" + aRowNo + "行更新网点失败!");
					}
					else{
						aNodeMapSet.add(mNodeMapDB);
						iUpdateNo ++;
					}
				}else if(!MidplatUtil.checkAgentCom4DB(sManageCom, sAgentCom)){
					throw new MidplatException("在第" + aRowNo + "行的网点已存在于其他银行网点下,请确认!");
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
	 * 得到日志显示结果
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
