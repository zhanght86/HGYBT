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
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	public CErrors logErrors = new CErrors();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 全局数据 */
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

	// 数据Xml解析节点描述
	private String FilePath = "/";

	// 配置文件Xml节点描述
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
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ParseGuideIn";
			tError.functionName = "checkData";
			tError.errorMessage = "导入文件格式有误!" + ex.getMessage();
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
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		System.out.println("地区:" + mAreaNo + "城市:" + mCityNo);
		mOperator = mGlobalInput.Operator;
		System.out.println("用户:" + mOperator + "正在进行保单号批量提交");
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
	private boolean getFilePath() {

		FilePath = "reportit/temp/";
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

		ConfigFileName = filePath + "ITImport.xml";
		System.out.println("用户:" + mOperator + ":试用的模板文件为：" + ConfigFileName);
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

		ITVTSParser gcvp = new ITVTSParser();

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
		String tContNo = eTop.getChildText("ContNo");
		String tCityNo = eTop.getChildText("CityNo");
		String tSysFlag = eTop.getChildText("SysFlag");
		String tShortName = MidplatUtil.getShortName4LDCom(mCityNo);
       // String tShortName = MidplatUtil.getShortName4LDCom(mCityNo);
		//String tShortName =mCityNo;
		if (tContNo == null || "".equals(tContNo) || !"保单号".equals(tContNo)) {
			throw new Exception("提交文件格式不符合要求,第1行第1列应为【保单号】!");
		}
		if (tCityNo == null || "".equals(tCityNo) || !"城市代码".equals(tCityNo)) {
			throw new Exception("提交文件格式不符合要求,第1行第2列应为【城市代码】!");
		}
		if (tSysFlag == null || "".equals(tSysFlag) || !"系统标志".equals(tSysFlag)) {
			throw new Exception("提交文件格式不符合要求,第1行第3列应为【系统标志】!");
		}
		

		List RowList = eContTable.getChildren("ROW");
		int maxCow = 0;
		if (RowList != null) {
			maxCow = RowList.size();
		}

		for (int i = 0; i < maxCow; i++) {
			Element eRow = (Element) RowList.get(i);

			// --------------------ContNo 华丽的分割线----------------------------
			String sContNo = eRow.getChildText("ContNo");
			if (sContNo == null || "".equals(sContNo)) {
				throw new Exception("第" + (i + 2) + "行第1列【保单号】不能为空!");
			}
			if (sContNo.length() != 11 || !MidplatUtil.checkContNo(sContNo)) {
				throw new Exception("第" + (i + 2) + "行第1列【保单号】不符合编码规则,请确认!");
			}

			// --------------------CityNo 华丽的分割线----------------------------
			String sCityNo = eRow.getChildText("CityNo");
			if (sCityNo == null || "".equals(sCityNo)) {
				throw new Exception("第" + (i + 2) + "行第2列【城市代码】不能为空!");
			}
			if (!sCityNo.equals(tShortName)) {
				throw new Exception("第" + (i + 2)
						+ "行第2列【城市代码】与所选城市编码不符,请确认!");
			}

			// --------------------SysFlag华丽的分割线----------------------------
			String sSysFlag = eRow.getChildText("SysFlag");
			if (sSysFlag == null || "".equals(sSysFlag)) {
				throw new Exception("第" + (i + 2) + "行第3列【系统标志】不能为空!");
			}
			if (!("RLS".equals(sSysFlag) || "LEG".equals(sSysFlag))) {
				throw new Exception("第" + (i + 2) + "行第3列【系统标志】不为RLS或者LEG,请确认!");
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
			throw new MidplatException("在第" + (iError + iRepeatNo + 1)
					+ "行提交失败!");
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
	 * 得到日志显示结果
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
