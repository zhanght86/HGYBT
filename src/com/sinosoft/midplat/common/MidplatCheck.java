package com.sinosoft.midplat.common;

/**
 * <p>Title:        ������ϢУ��</p>
 * <p>Description:  ����������״̬�Ƿ���Ч </p>
 * <p>Copyright:    Copyright (c) 2006.08.26</p>
 * <p>Company:      ��˾����picc</p>
 * @author
 * @version         1.0
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import com.sinosoft.lis.db.AxaAgentDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LKTransAuthorizationDB;
import com.sinosoft.lis.db.LMRiskMapDB;
import com.sinosoft.lis.db.NodeMapDB;
import com.sinosoft.lis.schema.LKTransAuthorizationSchema;
import com.sinosoft.lis.schema.NodeMapSchema;
import com.sinosoft.lis.vschema.LKTransAuthorizationSet;
//import com.sinosoft.lis.ybt.common.ControlUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

/**
 * @author Administrator
 */
public class MidplatCheck implements XmlTag {
	private final static Logger cLogger = Logger.getLogger(MidplatCheck.class);

	public MidplatCheck() {

	}

	/**
	 * @DESC ����LMRiskMap���������Ĳ�Ʒ���ۺ�ͣ��ʱ�����������Ȩ��У��
	 * @return boolean create by fzg 2012-2-14
	 */
	public static void NodeTimeCheck(Element mRootEle) throws MidplatException {
		Element mHeadEle = (Element) mRootEle.getChild(Head);
		Element mBodyEle = (Element) mRootEle.getChild(Body);
		// Element mRiskEle = (Element) mBodyEle.getChild(Risk);//���ֲ�Ψһ�������պ͸�����

		String mTranCom = mHeadEle.getChildText(TranCom);
		String mZoneNo = mHeadEle.getChildText("ZoneNo");
		String mNodeNo = mHeadEle.getChildText(NodeNo);
		String mTranDate = mHeadEle.getChildText(TranDate);
		/**
		 * �����������͵�ԭ�򣬵������д���(����011,012)֮ǰ��0��ʧ��
		 * �����ڲ�ѯ��LMRiskMapʱ������'12'ȥƥ�䣬��������ȷ��'012'�� ������Ҫ�ڴ˴���mTranCom���µ�ֵ��
		 */

		String mSqlStr = new StringBuilder(
				"select ManageCom from NodeMap where Type=").append("0")
				.append(" and TranCom='").append(mTranCom).append('\'')
				.append(" and ZoneNo='").append(mZoneNo).append('\'')
				.append(" and NodeNo='").append(mNodeNo).append('\'')
				.toString();
		String mManageCom = new ExeSQL().getOneValue(mSqlStr);
		if (mManageCom == "") {
			throw new MidplatException("������Ŀǰû�в�Ʒ������Ȩ�ޣ�");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date tranDate = null;
		try {
			tranDate = dateFormat.parse(mTranDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// �������ж��Ƿ�������Ȩ��
		List<Element> mRisks = new ArrayList<Element>();
		mRisks = mBodyEle.getChildren(Risk);

		for (int i = 0; i < mRisks.size(); i++) {
			Element Risk = (Element) mRisks.get(i);
			String mRiskCode = Risk.getChildText(RiskCode);
			String mMainRiskCode = Risk.getChildText(MainRiskCode);
			String mTranRiskCode = Risk.getChildText("TranRiskCode");
			if(!mRiskCode.equals(mMainRiskCode)){
				return;
			}
			LMRiskMapDB lmRiskMapDB = new LMRiskMapDB();
			lmRiskMapDB.setCodeType("risk_com");
			lmRiskMapDB.setComCode(mManageCom);
			lmRiskMapDB.setBankCode(mTranCom);
			lmRiskMapDB.setInsuCode(mTranRiskCode);

			// ��Ʒ����Ȩ��û���ø�ͨ��
			if (lmRiskMapDB.getInfo()) {
				String startD = lmRiskMapDB.getStartDate();
				String endD = lmRiskMapDB.getEndDate();
				Date startDate = null;
				Date endDate = null;
				try {
					startDate = dateFormat.parse(startD);// String
															// --->java.util.Date
					endDate = dateFormat.parse(endD);
				} catch (Exception e) {
					e.printStackTrace();
					throw new MidplatException(e.getMessage());
				}
				if (tranDate.before(startDate)) {
					throw new MidplatException("������Ĳ�Ʒ" + mRiskCode
							+ "��û���������ڣ�");
				}
				if (tranDate.after(endDate)) {
					throw new MidplatException("������Ĳ�Ʒ" + mRiskCode
							+ "���������Ѿ�������");
				}
			}

		}
	}

	/**
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws MidplatException
	 *             create by zhj 2010 11 05 ���� Ȩ�� ���У�鷽��
	 */
	public static Document NodeMapCheck(Document cInXmlDoc)
			throws MidplatException {
		Document mInXmlDoc = cInXmlDoc;
		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head);
		Element mAgentCom = mHeadEle.getChild("AgentCom");
		Element mAgentCode = mHeadEle.getChild("AgentCode");
		Element mAgentComName = mHeadEle.getChild("AgentComName");
		Element mAgentName = mHeadEle.getChild("AgentName");
		Element mManageCom = mHeadEle.getChild("ManageCom");
		Element mUnitCode = mHeadEle.getChild("UnitCode");
		Element mAgentGrade = mHeadEle.getChild("AgentGrade");
		Element mAgentCodeGrade = mHeadEle.getChild("AgentCodeGrade");
		Element mBodyEle = (Element) mRootEle.getChild(Body);
		String sNodeNo = (String) mHeadEle.getChildTextTrim("NodeNo");
		String sZoneNo = (String) mHeadEle.getChildTextTrim("ZoneNo");
		String sTranCom = (String) mHeadEle.getChildTextTrim("TranCom");
		String sFuncFlag = (String) mHeadEle.getChildTextTrim("FuncFlag");
		String mRiskEle = "";
		List mRiskList = mBodyEle.getChildren(Risk);

		String mSqlStr = new StringBuilder(
				"select MapNo from NodeMap where Type=").append("0")
				.append(" and TranCom=").append(sTranCom)
				.append(" and ZoneNo='").append(sZoneNo).append('\'')
				.append(" and NodeNo='").append(sNodeNo).append('\'')
				.toString();
		String sMapNo = new ExeSQL().getOneValue(mSqlStr);
		if(sMapNo==null || "".equals(sMapNo) || "0".equals(sMapNo)){
			throw new MidplatException("�����㲻���ڣ���ȷ�ϣ�");
		}
		NodeMapDB aNodeMapDB = new NodeMapDB();
		aNodeMapDB.setMapNo(sMapNo);
		aNodeMapDB.setTranCom(sTranCom);
		aNodeMapDB.setZoneNo(sZoneNo);
		aNodeMapDB.setNodeNo(sNodeNo);
		long mStartMillis = System.currentTimeMillis();
		if (!aNodeMapDB.getInfo()) {
			throw new MidplatException("�����㲻���ڣ���ȷ�ϣ�");
		}

		mAgentCom.setText(aNodeMapDB.getAgentCom());
		mAgentCode.setText(aNodeMapDB.getAgentCode());
		mUnitCode.setText(aNodeMapDB.getUnitCode());
		mAgentGrade.setText(aNodeMapDB.getAgentGrade());
		mManageCom.setText(aNodeMapDB.getManageCom());
		mAgentComName.setText(aNodeMapDB.getAgentComName());		
		
		AxaAgentDB aAxaAgentDB = new AxaAgentDB();
		aAxaAgentDB.setAgentCode(aNodeMapDB.getAgentCode());
		aAxaAgentDB.setManageCom(aNodeMapDB.getManageCom());
		mStartMillis = System.currentTimeMillis();
		if (!aAxaAgentDB.getInfo()) {
			throw new MidplatException("��������������רԱ�����ڣ���ȷ�ϣ�");
		}
		
		String sAgentCodeGrade = aNodeMapDB.getAgentCodeGrade();
		if (sAgentCodeGrade.length() == 1) {
			sAgentCodeGrade = "0" + sAgentCodeGrade;
		}
		mAgentName.setText(aAxaAgentDB.getAgentName());
		mAgentCodeGrade.setText(sAgentCodeGrade);
		return mInXmlDoc;
	}

	public static void NodeMapSpecCheck(Document cInXmlDoc)
			throws MidplatException {
		Document mInXmlDoc = cInXmlDoc;
		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head);
		Element mBodyEle = (Element) mRootEle.getChild(Body);
		String sNodeNo = (String) mHeadEle.getChildTextTrim("NodeNo");
		String sZoneNo = (String) mHeadEle.getChildTextTrim("ZoneNo");
		String sTranCom = (String) mHeadEle.getChildTextTrim("TranCom");
		String sManageCom= (String) mHeadEle.getChildTextTrim("ManageCom");

		List mRiskList = mBodyEle.getChildren(Risk);

		String mSqlStr = new StringBuilder(
				"select MapNo from NodeMap where Type=").append("0")
				.append(" and TranCom=").append(sTranCom)
				.append(" and ZoneNo='").append(sZoneNo).append('\'')
				.append(" and NodeNo='").append(sNodeNo).append('\'')
				.toString();
		String sMapNo = new ExeSQL().getOneValue(mSqlStr);
		if(sMapNo==null || "".equals(sMapNo) || "0".equals(sMapNo)){
			throw new MidplatException("�����㲻���ڣ���ȷ�ϣ�");
		}
		NodeMapDB aNodeMapDB = new NodeMapDB();
		aNodeMapDB.setMapNo(sMapNo);
		aNodeMapDB.setTranCom(sTranCom);
		aNodeMapDB.setZoneNo(sZoneNo);
		aNodeMapDB.setNodeNo(sNodeNo);
		long mStartMillis = System.currentTimeMillis();
		if (!aNodeMapDB.getInfo()) {
			throw new MidplatException("�����㲻���ڣ���ȷ�ϣ�");
		}
		
		ControlUtil.sqlExecTime("NodeMapDB.getInfo()", mStartMillis);
		String state = aNodeMapDB.getState();
		if (state == null) {
			state = "";
		}
		String saleFlag = aNodeMapDB.getSaleFlag();
		if (saleFlag == null) {
			saleFlag = "";
		}
		boolean ifAccFlag = false;
		if (MidplatUtil.ifAccRiskCheck(mRiskList, sTranCom)) {
			ifAccFlag = MidplatUtil.ifAccRisk(mRiskList);
		}
		System.out.println("Ͷ���ձ�־:" + ifAccFlag);
		
		LDComDB cLDComDB = new LDComDB();
		cLDComDB.setComCode(sManageCom);
		if(!cLDComDB.getInfo()){
			throw new MidplatException("δ�ҵ���ع������,��ȷ��");
		} 
		if(cLDComDB.getTellerFlag()!= null && !"".equals(cLDComDB.getTellerFlag())){
			cLogger.debug("����У�鿪ʼ");
			
			if ("T".equals(state)) {// ϵͳ״̬ΪT
				throw new MidplatException("�����������ʸ��Ѿ���ֹ���������������ʸ�У��ʧ�ܣ�������ʵʱ����!");
			}
			
			CheckNodeQu(aNodeMapDB);
			
			if ("".equals(saleFlag)) {
					throw new MidplatException("�����������۴˲�Ʒ�����ʣ��������������ʸ�У��ʧ�ܣ�������ʵʱ����!");
			} else if ("L".equals(saleFlag) && ifAccFlag) {
					throw new MidplatException("�����������۴˲�Ʒ�����ʣ��������������ʸ�У��ʧ�ܣ�������ʵʱ����!");
				}
			
			CheckTeller(mHeadEle);
			
			cLogger.debug("����У�����");
		}else if ("T".equals(state)) {// ϵͳ״̬ΪT
			throw new MidplatException("�����㴦�ڹر�״̬��ϵͳ����Ͷ��!");
		} else if ("".equals(saleFlag)) {
				throw new MidplatException("������δ���������ɣ�����Ͷ��!");
		} else if ("L".equals(saleFlag) && ifAccFlag) {
				throw new MidplatException("������δ���Ͷ����Ʒ������ɣ�����Ͷ��");
			}
		
		
		String sAgentCodeGrade = aNodeMapDB.getAgentCodeGrade();
		if (sAgentCodeGrade.length() == 1) {
			sAgentCodeGrade = "0" + sAgentCodeGrade;
		}
		// add by fzg 2012-2-14 ʱ��У��
		NodeTimeCheck(mRootEle);
		
	}
	/**
	 * ͬһ����˳��У��
	 * 
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws Exception
	 */
	public static boolean SameGradeBnfCheck(Document InXmlDoc) throws Exception {
		Element mRootEle = InXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild("Body");

		String tBnfGrade = "";
		List bnfs = new ArrayList(); // ����װ������
		List bnfLots = new ArrayList(); // ����װ������
		// List bnfID = new ArrayList(); // ����װ������֤����
		bnfs = mBodyEle.getChildren("Bnf");
		for (int i = 0; i < bnfs.size(); i++) {
			Element bnf = (Element) bnfs.get(i);
			String beneficType = bnf.getChildTextTrim("BeneficType");
			if (beneficType.equals("N")) {
				String[] tBnfLot1 = new String[2];
				tBnfLot1[0] = bnf.getChildText("Grade");
				tBnfLot1[1] = bnf.getChildText("Lot");
				bnfLots.add(tBnfLot1);
			}
		}

		// �ж����������Ƿ��� �����ڵ�¼��ͬһ��������
		for (int i = 0; i < bnfs.size(); i++) {
			Element bnf = (Element) bnfs.get(i);
			String beneficType = bnf.getChildTextTrim("BeneficType");
			if (beneficType.equals("N")) {
				String[] tBnfID = new String[2];
				tBnfID[0] = bnf.getChildText("IDType");
				tBnfID[1] = bnf.getChildText("IDNo");
				// ѭ���Ƚϵ�ǰ�ڵ���˽ڵ�֮���ÿ���ڵ��֤�����ͼ������Ƿ���ͬ
				for (int j = i + 1; j < bnfs.size(); j++) {
					Element bnf2 = (Element) bnfs.get(j);
					String[] tBnfID2 = new String[2];
					tBnfID2[0] = bnf2.getChildText("IDType");
					tBnfID2[1] = bnf2.getChildText("IDNo");
					if (tBnfID[0].equals(tBnfID2[0])
							&& tBnfID2[1].equals(tBnfID[1])) {
						throw new Exception("������֤����Ϣ����");
					}
				}
			}

		}

		// �ж�ͬһ˳��������˷ݶ��Ƿ�Ϊ100%
		for (int i = 0; i < bnfLots.size(); i++) {
			String[] tArrBnfLoti = (String[]) bnfLots.get(i);
			String tBnfGradei = tArrBnfLoti[0];
			double tBnfLoti = Double.parseDouble(tArrBnfLoti[1]);

			// �������һ�������ˣ���ֱ�ӽ����ж�
			if (bnfLots.size() == 1) {
				if (tBnfLoti < 100) {
					return false;
				}
			}

			if ("".equals(tBnfGrade) || tBnfGrade.indexOf(tBnfGradei) < 0) {
				for (int j = i + 1; j < bnfLots.size(); j++) {
					String[] tArrBnfLotj = (String[]) bnfLots.get(j);
					String tBnfGradej = tArrBnfLotj[0];
					double tBnfLotj = Double.parseDouble(tArrBnfLotj[1]);
					if (tBnfGradej.equals(tBnfGradei)) {
						// �����Ѿ��жϹ���������˳��
						tBnfGrade += tBnfGradej + ",";
						// ����ͬһ����˳����������ܷݶ�
						tBnfLoti += tBnfLotj;
						System.out.println("tBnfLoti" + tBnfLoti);
					}
					if (j == bnfLots.size() - 1) {
						if (tBnfLoti > 100) {
							return false;
						}
						if (tBnfLoti < 100) {
							return false;
						}
					}
				}
				if (i == bnfLots.size() - 1) {
					if (tBnfLoti < 100) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * ����У��
	 * 
	 * @param InXmlDoc
	 * @return
	 */
	public static boolean DigitBnfCheck(Document InXmlDoc) {
		boolean digitFlag = true;
		// �������б�
		Element mRootEle = InXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild("Body");
		List bnfs = new ArrayList(); // ����װ������
		List<Integer> bnfGrades = new ArrayList<Integer>(); // ����װ������
		bnfs = mBodyEle.getChildren("Bnf");
		for (int i = 0; i < bnfs.size(); i++) {
			Element bnf = (Element) bnfs.get(i);
			String beneficType = bnf.getChildTextTrim("BeneficType");
			String Grade = bnf.getChildTextTrim("Grade");
			int iGrade = Integer.valueOf(Grade);
			if (beneficType.equals("N")) {
				bnfGrades.add(iGrade);
			}
		}

		// ����������
		Collections.sort(bnfGrades);

		// �������
		digitFlag = check(bnfGrades);

		return digitFlag;
	}

	public static boolean check(List<Integer> bnfGrades) {
		for (int i = 0; i < bnfGrades.size() - 1; i++) {
			if ((bnfGrades.get(i + 1) - bnfGrades.get(i)) > 1) {
				return false;
			}
		}
		return true;
	}

	public static void CheckPrt20No(String sPrtNo) throws MidplatException {
		// if (sPrtNo == null || "".equals(sPrtNo)) {
		// throw new MidplatException("������ҳ�Ų���Ϊ�գ�");
		// } else if (sPrtNo.length() != 20) {
		// throw new MidplatException("������ҳ�ų��ȱ���Ϊ20λ��");
		// }
	}
	
	public static void CheckTeller (Element mHeadEle) throws MidplatException{
		String sZoneNo = (String) mHeadEle.getChildTextTrim("ZoneNo");
		String sTranCom = (String) mHeadEle.getChildTextTrim("TranCom");
		String sTellerNo= (String) mHeadEle.getChildTextTrim("TellerNo");
		String sTranDate= (String) mHeadEle.getChildTextTrim("TranDate");
		String sql="Select TellerQuNo,to_char(EndDate,'yyyyMMdd') from LdTeller where TranCom='"+sTranCom+"' and ZoneNo='"+sZoneNo+"' and TellerNo='"+sTellerNo+"'";
		SSRS tSSRS = new SSRS();
		ExeSQL texeSql = new ExeSQL();
		tSSRS = texeSql.execSQL(sql);
		if(tSSRS.MaxRow==0){
			throw new MidplatException("δ�ҵ�������й�Ա,��ȷ��");
		}else if(tSSRS.GetText(1, 1)==null || "".equals(tSSRS.GetText(1, 1))){
			throw new MidplatException("δ�ҵ��ù�Ա�ļ�ҵ�����ʸ�֤��Ϣ����������У��ʧ�ܣ�������ʵʱ����");
		}else {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date tranDate=null;
			Date endDate= null;
			
			try {
				tranDate = dateFormat.parse(sTranDate);
				if(tSSRS.GetText(1, 2)==null || "".equals(tSSRS.GetText(1, 2))){
					throw new MidplatException("δ�ҵ��ù�Ա�ļ�ҵ�����ʸ�֤��������Ϣ����������У��ʧ�ܣ�������ʵʱ����");
				}
				endDate = dateFormat.parse(tSSRS.GetText(1, 2));
//				if(tSSRS.GetText(1, 2)!=null && !"".equals(tSSRS.GetText(1, 2))){
//					StartDate = dateFormat.parse(tSSRS.GetText(1, 2));	
//				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			if(tranDate.before(StartDate)){
//				throw new MidplatException("�����й�Ա�����ʸ�֤δ��Ч,����Ͷ��");
//			}
			if(endDate.before(tranDate)){
				throw new MidplatException("�ù�Ա�ļ�ҵ�����ʸ�֤�ѵ��ڣ���������У��ʧ�ܣ�������ʵʱ����");
			}
			
		} 
	}
	
	
	public static void CheckNodeQu (NodeMapDB aNodeMapDB) throws MidplatException{
		String Conagentno = aNodeMapDB.getConAgentNo();
		String Conenddate = aNodeMapDB.getConEndDate();
		
		if(Conagentno==null || "".equals(Conagentno)){
			throw new MidplatException("δ�ҵ������������ʸ�֤�ţ��������������ʸ�У��ʧ�ܣ�������ʵʱ����!");
		}else {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date tranDate=null;
			Date endDate= null;
			
			try {
				tranDate = dateFormat.parse(DateUtil.getCur10Date());
				if(Conenddate==null || "".equals(Conenddate)){
					throw new MidplatException("δ�ҵ������������ʸ�֤��������Ϣ���������������ʸ�У��ʧ�ܣ�������ʵʱ����!");
				}
				endDate = dateFormat.parse(Conenddate);
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(endDate.before(tranDate)){
				throw new MidplatException("�����������ʸ�֤�ѵ��ڣ��������������ʸ�У��ʧ�ܣ�������ʵʱ����");
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		try {
////			String s = "0123456789012345678";
//			MidplatCheck.CheckTeller(s);
//		} catch (Exception e) {
//			System.out.print(e.getMessage());
//		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date endDate= null;
		try {
			endDate = dateFormat.parse("20120405");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date StartDate=new Date(0);
		try {
			StartDate=dateFormat.parse("20120405");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(StartDate.equals(endDate));
	}
}
