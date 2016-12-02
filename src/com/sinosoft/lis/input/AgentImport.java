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
	
	public int iRepeatNo = 0;
	
	public int iUpdateNo = 0;
	
	public int iEndNo = 0;

	private Document mDoc = null;

	private TransferData mTransferData = new TransferData();

	private String mFileName;

	public String XmlFileName;

	private String mFilePath;

	private String mImportFlag;

	// 数据Xml解析节点描述
	private String FilePath = "/";

	// 配置文件Xml节点描述
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
		mAreaNo = (String) mTransferData.getValueByName("AreaNo");
		mCityNo = (String) mTransferData.getValueByName("CityNo");
		//mManageCom = (String) mTransferData.getValueByName("ManageCom");
		System.out.println("地区:" + mAreaNo + "城市:" + mCityNo+"管理机构："+mManageCom);
		mOperator = mGlobalInput.Operator;
		mManageCom = mGlobalInput.ManageCom;
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
		/*if (mManageCom != null && "86".equals(mManageCom)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "该操作员所属机构无导入网点专员的权限!";
			this.mErrors.addOneError(tError);
			return false;
		}*/
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

		FilePath = "agent/temp/";
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

		ConfigFileName = filePath + "AgentImport.xml";
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
		System.out.println("解析excel并转换成xml文件开始。。。。。。。。。。。");
		// 初始化导入文件
		if (!this.initImportFile()) {
			return false;
		}
		// 配置xml校验文件
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
		// 转换excel到xml
		if (!gcvp.transform()) {
			mErrors.copyAllErrors(gcvp.mErrors);
			return false;
		}

		// 得到生成的XML数据文件名
		m_strDataFiles = gcvp.getDataFiles();
		mDoc = gcvp.getXmlDoc();
		System.out.println("解析excel并转换成xml文件正确结束。。。。。。。。。。。");
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
		//String tAreaNo = eTop.getChildText("AreaNo");
		String tCityNo = eTop.getChildText("CityNo");
		String tAgentCode = eTop.getChildText("AgentCode");
		String tAgentName = eTop.getChildText("AgentName");
//		if(tAreaNo == null || "".equals(tAreaNo) || !"地区编码".equals(tAreaNo)){
//			throw new Exception("提交文件格式不符合要求,第1行第1列应为【地区编码】!");
//		}
		if(tCityNo == null || "".equals(tCityNo) || !"城市编码".equals(tCityNo)){
			throw new Exception("提交文件格式不符合要求,第1行第1列应为【城市编码】!");
		}
		if(tAgentCode == null || "".equals(tAgentCode) || !"网点专员编码".equals(tAgentCode)){
			throw new Exception("提交文件格式不符合要求,第1行第2列应为【网点专员编码】!");
		}
		if(tAgentName == null || "".equals(tAgentName) || !"网点专员名称".equals(tAgentName)){
			throw new Exception("提交文件格式不符合要求,第1行第3列应为【网点专员名称】!");
		}
		
		List RowList = eContTable.getChildren("ROW");
		int maxCow = 0;
		if (RowList != null) {
			maxCow = RowList.size();
		}
		
		for (int i = 0; i < maxCow; i++) {
			Element eRow = (Element) RowList.get(i);
			String sRowNo = eRow.getChildText("RowNo");
			
			//--------------------AreaNo 华丽的分割线----------------------------
			String sAreaNo = eRow.getChildText("AreaNo");
//			if(sAreaNo == null || "".equals(sAreaNo)){
//				throw new Exception("第" + (i+2) + "行第1列【地区编码】不能为空!");
//			}
//			if(mManageCom != null && !"86".equals(mManageCom)){
//				if(sAreaNo != null && !sAreaNo.equals(mAreaNo)){
//					throw new Exception("第" + (i+2) + "行第1列【地区编码】不为登陆用户所属地区编码!");
//				}
//			}else if(mManageCom != null && "86".equals(mManageCom)){
//				if(!(sAreaNo.equals("01") || sAreaNo.equals("02") || sAreaNo.equals("03") || sAreaNo.equals("04"))){
//					throw new Exception("第" + (i+2) + "行第1列【地区编码】不符合规则!,请填写正确的地区编码");
//				}
//			}
			
			//--------------------CityNo 华丽的分割线----------------------------
			String sCityNo = eRow.getChildText("CityNo");
			if(sCityNo == null || "".equals(sCityNo)){
				throw new Exception("第" + (i+2) + "行第1列【城市编码】不能为空!");
			}
			if(mManageCom != null && mManageCom.length() == 9){
				if(sCityNo != null && !sCityNo.equals(mCityNo)){
					throw new Exception("第" + (i+2) + "行第1列【城市编码】不为登陆用户所属城市!");
				}
			}else if(mManageCom != null && mManageCom.length() <= 4){
				if(!MidplatUtil.checkCityCode(sCityNo)){
					throw new Exception("第" + (i+2) + "行第1列【城市编码】不符合规则,应为三位有效数字!");
				}
				if(MidplatUtil.checkCityCode4DB(sCityNo)){
					throw new Exception("第" + (i+2) + "行第1列【城市编码】无对应管理机构,请确认!");
				}
			}
			
			//--------------------AgentCode 华丽的分割线----------------------------
			String sAgentCode = eRow.getChildText("AgentCode");
			if(sAgentCode == null || "".equals(sAgentCode)){
				throw new Exception("第" + (i+2) + "行第2列【网点专员编码】不能为空!");
			}
			if(!MidplatUtil.checkAgentCode(sAgentCode)){
				throw new Exception("第" + (i+2) + "行第2列【网点专员编码】不符合要求，请填写6位有效数字!");
			}
			String sAgentName = eRow.getChildText("AgentName");
			if(sAgentName == null || "".equals(sAgentName)){
				throw new Exception("第" + (i+2) + "行第2列【网点专员名称】不能为空!");
			}
			if(sAgentName.length() > 50){
				throw new Exception("第" + (i+2) + "行第2列【网点专员名称】不符合要求，网点专员名称不能超过50个字符!");
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
					
					throw new Exception("网点专员批量维护,插入语句失败!");
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
							throw new MidplatException("更新网点专员信息失败！");
						}
					}else{
						throw new Exception("网点专员批量维护,更新语句失败!");
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
	 * 得到日志显示结果
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}
	
	public void saveAaxaAgent(Document stdDoc){
		
	}

}
