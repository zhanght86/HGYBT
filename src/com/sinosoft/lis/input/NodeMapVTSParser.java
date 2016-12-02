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
	/** ��������  */
	public CErrors mErrors = new CErrors();
	/** �������Ͷ���ļ����ļ���  */
	private String m_strFileName = "";
	/** �������Ͷ���ļ���·��  */
	private String m_strPathName = "";
	/** ����Sheet��ص���Ϣ��������У�����У���ǰ�������е�  */
	private Hashtable[] m_hashInfo = null;
	/** ��ʽ�ļ� */
	private String m_strConfigFileName = "";
	private int tLimitNum = 100;


	// ��������
	private static String PROP_MAX_ROW = "max_row";
	private static String PROP_MAX_COL = "max_col";
	private static String PROP_CUR_ROW = "cur_row";
	private static String PROP_COL_NAME = "col_name";
	private static final int SHEET_COUNT = 1;
	public int iCur_Row = 0;
	/** sheet��������ݼ�,�Լ��������ڵ��� */
	private final int SHEET_CAR = 0;
	private final int STRID_COL_CAR = 0;
	private final int SHEET_LCPOL = 1;
	private final int STRID_COL_LCPOL = 0;
	

	// ͳһ�������ڣ�ʱ��
	private String theCurrentDate = DateUtil.getCur10Date();
	private String theCurrentTime = DateUtil.getCur8Time();

	private static final int ROW_LIMIT = 5000; // һ�δ��������

	private BookModelImpl m_book = new BookModelImpl();

	private Document xmlDoc = null;

	// ��������ÿһ��С�������ɵ�XML�ļ���
	private List m_listFiles = new ArrayList();

	public Document getXmlDoc() {
		return this.xmlDoc;
	}

	public NodeMapVTSParser() {

		m_hashInfo = new Hashtable[SHEET_COUNT];

		for (int nIndex = 0; nIndex < SHEET_COUNT; nIndex++) {
			m_hashInfo[nIndex] = new Hashtable();
			m_hashInfo[nIndex].put(PROP_CUR_ROW, "1"); // �ӵ�һ�п�ʼ����
		}

	}

	/**
	 * DESC ����Ҫ�����ļ����ļ���
	 * @param strFileName
	 * @return boolean
	 */
	public boolean setFileName(String strFileName) {
		File file = new File(strFileName);
		if (!file.exists()) {
			buildError("setFileName", "ָ�����ļ������ڣ�");
			return false;
		}

		m_strFileName = strFileName;
		m_strPathName = file.getParent();

		return true;
	}	

	/**
	 * DESC ��ȡ������ļ� ��
	 * @return
	 */
	public String getFileName() {
		return m_strFileName;
	}

	/**
	 * DESC ����ӳ���ļ���
	 * @param strConfigFileName
	 * @return
	 */
	public boolean setConfigFileName(String strConfigFileName) {
		File file = new File(strConfigFileName);
		if (!file.exists()) {
			buildError("setFileName", "ָ�����ļ������ڣ�");
			return false;
		}

		m_strConfigFileName = strConfigFileName;
		return true;
	}
	
	/**
	 * ת����������excel�ļ�ת����ָ����ʽ��XML�ļ���
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
			System.out.println("��һ��Ϊ��" + nCurRow2);
			System.out.println("���һ��Ϊ��" + nMaxRow2);

			Element eLimitNum = 
		  		 MidplatConf.newInstance().getConf().getRootElement().getChild("NodeMapImport");
		  	String sLimitNum = eLimitNum.getAttributeValue("number");
			
		  	if(sLimitNum != null && !"".equals(sLimitNum)){
		  		tLimitNum = Integer.valueOf(sLimitNum);
		  	}
		  	
		  	if(nMaxRow2 == 1){
		  		throw new MidplatException("�����ļ�Ϊ�ļ�����λ��,��ȷ��!");
		  	}
		  	
			System.out.println("��������Ϊ:" + tLimitNum);
			if ((nMaxRow2 + nMaxRow) > tLimitNum) {
				buildError("transform", "һ�β����ύ" + tLimitNum + "����������!");
				return false;
			}

			int i = 0;
			
			/** ��ʼѭ��ÿһ�� */
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
	 * DESC ��ȡӳ���ļ���
	 * @return ӳ���ļ���
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
	 * ��ȡ������Ϣ����������Ϣ��ÿһsheet�е���������
	 * @throws Exception
	 */
	private void verify() throws Exception {
		if (m_strFileName.equals("")) {
			throw new Exception("���ȵ���setFileName��������Ҫ������ļ�����");
		}
		
		m_book.read(m_strFileName, new com.f1j.ss.ReadParams());

		if (m_book.getNumSheets() < SHEET_COUNT) {
			throw new Exception("����Ͷ���ļ���������ȱ��Sheet��");
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
				throw new Exception("�Ҳ�����Ӧ��������Ϣ��Sheet"
						+ String.valueOf(nIndex + 1));
			}
		}
	}

	/**
	 * ִ��һ�����ɣ���ROW_LIMIT������VTS��������һ��Document
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
	 * �ж��Ƿ�������û�д��� ����ڴ�ű�����Ϣ��Sheet�У��Ѿ�����������У�����false�� ���򣬷���true;
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
	 * ������λ�ȡ�����ֵ �����һ�е�ǰ����λ��
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
			throw new Exception("�ύ�ļ���ʽ������Ҫ��,��ȷ���ύ�ļ���ʽ!");
		}
		System.out.println("�ύ�ļ��������Ϊ:" + nMaxRow);
		return nMaxRow;
	}

	/**
	 * ȡ�����ֵ
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
	 * ��ȡ��ǰ��ֵ�������ֵ����ֵ��
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
	 * ���浱ǰ��ֵ�������ֵ����ֵ��
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
	 * �ӻ����ж�ȡ��ǰsheetҳ������
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
	 * ���ײ������ļ�CarImport.xml�����������Ա���
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
	 * ����xml
	 * 
	 * @param eleParent
	 *            Element
	 * @param sheetNo
	 *            int
	 * @throws Exception
	 */
	private void genXMLPart(Element eleParent, int sheetNo) throws Exception {

		// ������Ϣ����ڵ�һ��Sheet�С�
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
		
		// nRow����sheet�е���
		for (nRow = nCurRow, nCount = 0; nRow < nMaxRow; nRow++) {
			
			// ȡ��Ψһ����Ϣ -- ��ͬID
			strID = m_book.getText(sheetNo, nRow, this.STRID_COL_CAR);

			// nCount����xml�к�ͬ�е���
			// ���������С�����Ʋ��Һ�ͬID�ѱ� �������������ļ�
			// Ŀ���Ǳ�֤ͬһ��ͬ����Ϣ������ͬһ�ļ���
			if (++nCount > NodeMapVTSParser.ROW_LIMIT) {
				break;
			}
			genPartSubTable(eleParent, this.SHEET_CAR, strID,
					this.STRID_COL_CAR);
		}
		setPropNumber(sheetNo, PROP_CUR_ROW, nRow);

	}

	/**
	 * �����ӱ� ��Ϊ��Sheet�������У��ǰ��ա�Ψһ�š�ID����ģ����Զ��ӱ�����ݣ� ����Ҳֻ��Ҫɨ��һ�ξͿ����ˡ�
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
			// ����ͬһ��ͬID����
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
