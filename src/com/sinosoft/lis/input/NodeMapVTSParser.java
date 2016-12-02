package com.sinosoft.lis.input;

import java.util.List;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.CErrors;
import com.f1j.ss.BookModelImpl;
import java.util.ArrayList;
import java.util.Hashtable;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.sinosoft.utility.CError;
import java.io.File;
import org.jdom.Document;
import org.jdom.input.DOMBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.Element;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class NodeMapVTSParser {
	/** 错误处理类  */
	public CErrors mErrors = new CErrors();
	/** 保存磁盘投保文件的文件名  */
	private String m_strFileName = "";
	/** 保存磁盘投保文件的路径  */
	private String m_strPathName = "";
	/** 保存Sheet相关的信息，如最大行，最大列，当前处理到的行等  */
	private Hashtable[] m_hashInfo = null;
	/** 样式文件 */
	private String m_strConfigFileName = "";
	private int tLimitNum = 100;


	// 常量定义
	private static String PROP_MAX_ROW = "max_row";
	private static String PROP_MAX_COL = "max_col";
	private static String PROP_CUR_ROW = "cur_row";
	private static String PROP_COL_NAME = "col_name";
	private static final int SHEET_COUNT = 1;
	public int iCur_Row = 0;
	/** sheet代表的数据集,以及索引所在的列 */
	private final int SHEET_CAR = 0;
	private final int STRID_COL_CAR = 0;
	private final int SHEET_LCPOL = 1;
	private final int STRID_COL_LCPOL = 0;
	

	// 统一更新日期，时间
	private String theCurrentDate = DateUtil.getCur10Date();
	private String theCurrentTime = DateUtil.getCur8Time();

	private static final int ROW_LIMIT = 5000; // 一次处理的行数

	private BookModelImpl m_book = new BookModelImpl();

	private Document xmlDoc = null;

	// 用来保存每一个小部分生成的XML文件名
	private List m_listFiles = new ArrayList();

	public Document getXmlDoc() {
		return this.xmlDoc;
	}

	public NodeMapVTSParser() {

		m_hashInfo = new Hashtable[SHEET_COUNT];

		for (int nIndex = 0; nIndex < SHEET_COUNT; nIndex++) {
			m_hashInfo[nIndex] = new Hashtable();
			m_hashInfo[nIndex].put(PROP_CUR_ROW, "1"); // 从第一行开始解析
		}

	}

	/**
	 * DESC 设置要处理文件的文件名
	 * @param strFileName
	 * @return boolean
	 */
	public boolean setFileName(String strFileName) {
		File file = new File(strFileName);
		if (!file.exists()) {
			buildError("setFileName", "指定的文件不存在！");
			return false;
		}

		m_strFileName = strFileName;
		m_strPathName = file.getParent();

		return true;
	}	

	/**
	 * DESC 获取处理的文件 名
	 * @return
	 */
	public String getFileName() {
		return m_strFileName;
	}

	/**
	 * DESC 设置映射文件名
	 * @param strConfigFileName
	 * @return
	 */
	public boolean setConfigFileName(String strConfigFileName) {
		File file = new File(strConfigFileName);
		if (!file.exists()) {
			buildError("setFileName", "指定的文件不存在！");
			return false;
		}

		m_strConfigFileName = strConfigFileName;
		return true;
	}
	
	/**
	 * 转换操作，将excel文件转换成指定格式的XML文件。
	 * @return boolean
	 */
	public boolean transform() {

		String strFileName = "";

		try {
			int nCurRow = getPropNumber(0, PROP_CUR_ROW);
			int nMaxRow = getPropNumber(0, PROP_MAX_ROW);
 
			verify();

			int nCurRow2 = getPropNumber(0, PROP_CUR_ROW);
			int nMaxRow2 = getPropNumber(0, PROP_MAX_ROW);
			System.out.println("第一行为：" + nCurRow2);
			System.out.println("最后一行为：" + nMaxRow2);

			Element eLimitNum = 
		  		 MidplatConf.newInstance().getConf().getRootElement().getChild("NodeMapImport");
		  	String sLimitNum = eLimitNum.getAttributeValue("number");
			
		  	if(sLimitNum != null && !"".equals(sLimitNum)){
		  		tLimitNum = Integer.valueOf(sLimitNum);
		  	}
		  	
		  	if(nMaxRow2 == 1){
		  		throw new MidplatException("上载文件为文件数据位空,请确认!");
		  	}
		  	
			System.out.println("限制条数为:" + tLimitNum);
			if ((nMaxRow2 + nMaxRow) > tLimitNum) {
				buildError("transform", "一次不能提交" + tLimitNum + "条以上数据!");
				return false;
			}

			int i = 0;
			
			/** 开始循环每一行 */
			while (hasMoreData()) {
				i++;
				strFileName = m_strPathName + "\\NodeMapImportTemp" + ".xml";
				System.out.println("strFileName:" + strFileName);
				genPart(strFileName);

				m_listFiles.add(strFileName);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("transfrom", ex.getMessage());
			return false;
		}
		return true;
	}

	
	/**
	 * DESC 获取映射文件名
	 * @return 映射文件名
	 */
	public String getConfigFileName() {
		return m_strConfigFileName;
	}

	public String[] getDataFiles() {
		String[] files = new String[m_listFiles.size()];

		for (int nIndex = 0; nIndex < m_listFiles.size(); nIndex++) {
			files[nIndex] = (String) m_listFiles.get(nIndex);
		}
		return files;
	}



	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "GrpPolVTSParser";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 读取配置信息，将配置信息的每一sheet中的列名缓存
	 * @throws Exception
	 */
	private void verify() throws Exception {
		if (m_strFileName.equals("")) {
			throw new Exception("请先调用setFileName函数设置要处理的文件名。");
		}
		
		m_book.read(m_strFileName, new com.f1j.ss.ReadParams());

		if (m_book.getNumSheets() < SHEET_COUNT) {
			throw new Exception("磁盘投保文件不完整，缺少Sheet。");
		}

		Document doc = 	JdomUtil.build(new FileInputStream(m_strConfigFileName));

		Element eleRoot = doc.getRootElement();
		Element ele = null;
		String strColName = "";

		for (int nIndex = 0; nIndex < SHEET_COUNT; nIndex++) {
			int nMaxRow = getMaxRow(nIndex);
			String sheetId = "Sheet" + String.valueOf(nIndex + 1);
			try {
				ele = eleRoot.getChild(sheetId);
				int nMaxCol = getMaxCol(nIndex);
				String[] strArr = new String[nMaxCol];

				for (int nCol = 0; nCol < nMaxCol; nCol++) {
					strColName = ele.getChildText("COL" + String.valueOf(nCol));
					strArr[nCol] = strColName;
				}
				setPropArray(nIndex, PROP_COL_NAME, strArr);
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new Exception("找不到对应的配置信息，Sheet"
						+ String.valueOf(nIndex + 1));
			}
		}
	}

	/**
	 * 执行一次生成，将ROW_LIMIT行数的VTS数据生成一个Document
	 * 
	 * @param strFileName
	 *            String
	 * @throws Exception
	 */
	private void genPart(String strFileName) throws Exception {
		Element root = new Element("Date");
		Document doc = new Document(root);

		Element ele = new Element("CONTTABLE");
		root.addContent(ele);
		genXMLPart(ele, this.SHEET_CAR);
		this.xmlDoc = doc;
		JdomUtil.print(xmlDoc);
		FileOutputStream fs = new FileOutputStream(strFileName);
		JdomUtil.output(doc, fs);
		fs.flush();
		fs.close();

	}

	/**
	 * 判断是否还有数据没有处理 如果在存放保单信息的Sheet中，已经处理到了最大行，返回false； 否则，返回true;
	 * 
	 * @return boolean
	 */
	private boolean hasMoreData() {
		int nCurRow = getPropNumber(0, PROP_CUR_ROW);
		int nMaxRow = getPropNumber(0, PROP_MAX_ROW);
		if (nCurRow >= nMaxRow) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 定义如何获取最大行值 如果有一行的前四列位空
	 * @param nSheetIndex
	 * @return int
	 * @throws Exception
	 */
	private int getMaxRow(int nSheetIndex) throws Exception {
		String col1 = "";
		String col2 = "";
		String col3 = "";
		String col4 = "";
		int nMaxRow = getPropNumber(nSheetIndex, PROP_MAX_ROW);
		if (nMaxRow == -1) {
			for (nMaxRow = 0; nMaxRow < m_book.getMaxRow(); nMaxRow++) {
				col1 = m_book.getText(nSheetIndex, nMaxRow, 0);
				col2 = m_book.getText(nSheetIndex, nMaxRow, 1);
				col3 = m_book.getText(nSheetIndex, nMaxRow, 2);
				col4 = m_book.getText(nSheetIndex, nMaxRow, 3);
				if ((col1 == null || col1.trim().equals(""))
						&& (col2 == null || col2.trim().equals(""))
						&& (col3 == null || col3.trim().equals(""))
						&& (col4 == null || col4.trim().equals(""))) {
					break;
				}

			}
			setPropNumber(nSheetIndex, PROP_MAX_ROW, nMaxRow);
		}
		if(nMaxRow == 0){
			throw new Exception("提交文件格式不符合要求,请确认提交文件格式!");
		}
		System.out.println("提交文件的最大行为:" + nMaxRow);
		return nMaxRow;
	}

	/**
	 * 取最大列值
	 * @param nSheetIndex
	 * @return int
	 * @throws Exception
	 */
	private int getMaxCol(int nSheetIndex) throws Exception {
		String str = "";
		int nMaxCol = getPropNumber(nSheetIndex, PROP_MAX_COL);

		if (nMaxCol == -1) {
			for (nMaxCol = 0; nMaxCol < m_book.getMaxCol(); nMaxCol++) {
				str = m_book.getText(nSheetIndex, 0, nMaxCol);
				if (str == null || str.trim().equals("")) {
					break;
				}
			}
			setPropNumber(nSheetIndex, PROP_MAX_COL, nMaxCol);
		}
		return nMaxCol;
	}

	/**
	 * 读取当前行值或最大行值、列值等
	 * @param nSheetIndex
	 * @param strPropName
	 * @return int
	 */
	private int getPropNumber(int nSheetIndex, String strPropName) {
		Hashtable hash = m_hashInfo[nSheetIndex];
		String str = (String) hash.get(strPropName);
		if (str != null && !str.equals("")) {
			return Integer.parseInt(str);
		} else {
			return -1;
		}
	}

	/**
	 * 缓存当前行值或最大行值、列值等
	 * 
	 * @param nSheetIndex
	 *            int
	 * @param strPropName
	 *            String
	 * @param nValue
	 *            int
	 */
	private void setPropNumber(int nSheetIndex, String strPropName, int nValue) {
		Hashtable hash = m_hashInfo[nSheetIndex];
		hash.put(strPropName, String.valueOf(nValue));
	}

	/**
	 * 从缓存中读取当前sheet页的列名
	 * 
	 * @param nSheetIndex
	 *            int
	 * @param strPropName
	 *            String
	 * @return String[]
	 */
	private String[] getPropArray(int nSheetIndex, String strPropName) {
		Hashtable hash = m_hashInfo[nSheetIndex];
		String[] strArr = (String[]) hash.get(strPropName);
		return strArr;
	}

	/**
	 * 将底层配置文件CarImport.xml的列名缓存以备用
	 * 
	 * @param nSheetIndex
	 *            int
	 * @param strPropName
	 *            String
	 * @param strArr
	 *            String[]
	 */
	private void setPropArray(int nSheetIndex, String strPropName,
			String[] strArr) {
		Hashtable hash = m_hashInfo[nSheetIndex];
		hash.put(strPropName, strArr);
	}

	/**
	 * 生成xml
	 * 
	 * @param eleParent
	 *            Element
	 * @param sheetNo
	 *            int
	 * @throws Exception
	 */
	private void genXMLPart(Element eleParent, int sheetNo) throws Exception {

		// 保单信息存放在第一个Sheet中。
		int nCurRow = getPropNumber(sheetNo, PROP_CUR_ROW);
		int nMaxRow = getMaxRow(sheetNo);

		String strID = "";

		int nRow = 0;
		int nCount = 0;

		String strIDCached = "";
		
		Element eTop = new Element("Top");
		
		Element eAgentComName = new Element("AgentComName");
		eAgentComName.setText(m_book.getText(this.SHEET_CAR, nRow, 0));
		eTop.addContent(eAgentComName);
		Element eBankNode = new Element("BankNode");
		eBankNode.setText(m_book.getText(this.SHEET_CAR, nRow, 1));
		eTop.addContent(eBankNode);
		Element eAgentCom = new Element("AgentCom");
		eAgentCom.setText(m_book.getText(this.SHEET_CAR, nRow, 2));
		eTop.addContent(eAgentCom);
		Element eAgentCodeName = new Element("AgentCodeName");
		eAgentCodeName.setText(m_book.getText(this.SHEET_CAR, nRow, 3));
		eTop.addContent(eAgentCodeName);
		Element eAgentCode = new Element("AgentCode");
		eAgentCode.setText(m_book.getText(this.SHEET_CAR, nRow, 4));
		eTop.addContent(eAgentCode);
		Element eCityName = new Element("CityName");
		eCityName.setText(m_book.getText(this.SHEET_CAR, nRow, 5));
		eTop.addContent(eCityName);
		Element eCityNo = new Element("CityNo");
		eCityNo.setText(m_book.getText(this.SHEET_CAR, nRow, 6));
		eTop.addContent(eCityNo);
		Element eConAgentNo = new Element("ConAgentNo");
		eConAgentNo.setText(m_book.getText(this.SHEET_CAR, nRow, 7));
		eTop.addContent(eConAgentNo);
		Element eConStartDate = new Element("ConStartDate");
		eConStartDate.setText(m_book.getText(this.SHEET_CAR, nRow, 8));
		eTop.addContent(eConStartDate);
		Element eConEndDate = new Element("ConEndDate");
		eConEndDate.setText(m_book.getText(this.SHEET_CAR, nRow, 9));
		eTop.addContent(eConEndDate);
		Element eState = new Element("State");
		eState.setText(m_book.getText(this.SHEET_CAR, nRow,10));
		eTop.addContent(eState);
		Element eSaleFlag = new Element("SaleFlag");
		eSaleFlag.setText(m_book.getText(this.SHEET_CAR, nRow, 11));
		eTop.addContent(eSaleFlag);
		
		eleParent.addContent(eTop);
		
		// nRow控制sheet行递增
		for (nRow = nCurRow, nCount = 0; nRow < nMaxRow; nRow++) {
			
			// 取得唯一号信息 -- 合同ID
			strID = m_book.getText(sheetNo, nRow, this.STRID_COL_CAR);

			// nCount控制xml中合同行递增
			// 如果超出最小行限制并且合同ID已变 则跳出生成新文件
			// 目的是保证同一合同的信息保存在同一文件中
			if (++nCount > NodeMapVTSParser.ROW_LIMIT) {
				break;
			}
			genPartSubTable(eleParent, this.SHEET_CAR, strID,
					this.STRID_COL_CAR);
		}
		setPropNumber(sheetNo, PROP_CUR_ROW, nRow);

	}

	/**
	 * 处理子表 因为在Sheet的数据中，是按照“唯一号”ID排序的，所以对子表的数据， 我们也只需要扫描一次就可以了。
	 * 
	 * @param eleParent
	 *            Element
	 * @param nSheetIndex
	 *            int
	 * @param strID
	 *            String
	 * @param strIDColIndex
	 *            int
	 * @throws Exception
	 */
	private void genPartSubTable(Element eleParent, int nSheetIndex,
			String strID, int strIDColIndex) throws Exception {
		int nCurRow = getPropNumber(nSheetIndex, PROP_CUR_ROW);
		int nMaxRow = getMaxRow(nSheetIndex);
		int nMaxCol = getMaxCol(nSheetIndex);

		String[] strColName = getPropArray(nSheetIndex, PROP_COL_NAME);

		int nRow = 0;
		


		for (nRow = nCurRow; nRow < nMaxRow; nRow++) {
			// 不是同一合同ID跳出
			if (!strID.equals(m_book.getText(nSheetIndex, nRow, strIDColIndex))) {
				break;
			}

			Element eleRow = new Element("ROW");
			eleParent.addContent(eleRow);
			Element eRowNo = new Element("RowNo");
			String sCurRow = String.valueOf(nCurRow+1);
			eRowNo.setText(sCurRow);
			eleRow.addContent(eRowNo);
			for (int nCol = 0; nCol < nMaxCol; nCol++) {

				Element ele = new Element(strColName[nCol]);
				ele.setText(m_book.getText(nSheetIndex, nRow, nCol));
				eleRow.addContent(ele);
			}
			if (nSheetIndex == this.SHEET_LCPOL) {

			}

		}
		setPropNumber(nSheetIndex, PROP_CUR_ROW, nRow);
	}
}
