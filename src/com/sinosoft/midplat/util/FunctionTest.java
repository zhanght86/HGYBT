package com.sinosoft.midplat.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Date;

import com.sinosoft.lis.db.LNContStatusUpdateDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LNContStatusUpdateSchema;
import com.sinosoft.lis.vschema.LNContStatusUpdateSet;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.VData;

public class FunctionTest {

	public static boolean saveNoRealRecord(String mDownLoadPath,
			String cDownloadFileName) throws Exception {
		String filePath = mDownLoadPath + cDownloadFileName;
		InputStream mIns = null;
		try {
			mIns = new FileInputStream(filePath);
		} catch (IOException ex) {
			throw new MidplatException("δ�ҵ������ļ���" + filePath);
		}
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				mIns, "GBK"));
		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		int mm = 0;
		double mSumDtlPrem = 0;

		int bankCount = Integer.parseInt(mSubMsgs[2]);
		double bankPrem = Double.parseDouble(mSubMsgs[3]);
		LNContStatusUpdateSet tLNContStatusUpdateSet = new LNContStatusUpdateSet();

		for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());) {
			System.out.println(tLineMsg);

			// ���У�ֱ������
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				System.out.println("���У�ֱ��������������һ����");
				continue;
			}
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			mm = mm + 1;
			mSumDtlPrem += Double.parseDouble(tSubMsgs[8]);
			LNContStatusUpdateSchema tLNContStatusUpdateSchema = new LNContStatusUpdateSchema();
			String mTranDate = tSubMsgs[0];
			String mTranNo = tSubMsgs[1];
			String mAppntName = tSubMsgs[2];
			String mAppntIDType = YBTFun.getinsurer_Code(tSubMsgs[3], "idtype",
					"02");
			String mAppntIDNo = tSubMsgs[4];
			String mRiskCode = tSubMsgs[5];
			String mProductID = tSubMsgs[6];
			String mProposalNo = tSubMsgs[7];
			String mPrem = tSubMsgs[8];
			String mBankAccNo = tSubMsgs[10];
			String mPhone = tSubMsgs[11];
			String mMobile = tSubMsgs[12];
			String mAddress = tSubMsgs[13];
			String mZipCode = tSubMsgs[14];
			String mZone = tSubMsgs[16];
			String mBranch = tSubMsgs[17];
			tLNContStatusUpdateSchema.setBankCode("03");
			tLNContStatusUpdateSchema.setZoneNo(mZone + mBranch);
			tLNContStatusUpdateSchema.setRiskCode(mRiskCode);
			tLNContStatusUpdateSchema.setProposalNo(mProposalNo);
			tLNContStatusUpdateSchema.setPolNo(mProposalNo);
			tLNContStatusUpdateSchema.setAppntName(mAppntName);
			tLNContStatusUpdateSchema.setAppntIDType(mAppntIDType);
			tLNContStatusUpdateSchema.setAppntIDNo(mAppntIDNo);
			tLNContStatusUpdateSchema.setallPrem(mPrem);
			tLNContStatusUpdateSchema.setTransDate(mTranDate);
			tLNContStatusUpdateSchema.setBackUp1(mTranNo);
			tLNContStatusUpdateSchema.setMakeDate(DateUtil.get8Date(new Date())
					+ "");
			tLNContStatusUpdateSchema.setModifyDate(DateUtil
					.get8Date(new Date()) + "");
			tLNContStatusUpdateSchema.setMakeTime(DateUtil.getCur6Time() + "");
			tLNContStatusUpdateSchema
					.setModifyTime(DateUtil.getCur6Time() + "");
			boolean flag = true;
			for (int i = 1; i <= tLNContStatusUpdateSet.size(); i++) {
				LNContStatusUpdateSchema remove = tLNContStatusUpdateSet.get(i);
				System.out.println("��" + i + "����ʵʱͶ�����ţ�----"
						+ remove.getProposalNo() + "��ǰͶ�����ţ�===" + mProposalNo);
				if (mProposalNo.equals(remove.getProposalNo())) {
					System.out.println("��ʵʱ��ϸ�����ظ�Ͷ�����ţ�====="
							+ remove.getProposalNo());
					flag = false;
				}
			}
			if (flag)
				tLNContStatusUpdateSet.add(tLNContStatusUpdateSchema);

		}
		mBufReader.close(); // �ر���
		if (bankPrem != mSumDtlPrem) {
			throw new MidplatException("���ܽ������ϸ�ܽ�����" + bankPrem + "!="
					+ mSumDtlPrem);
		}
		if (bankCount != mm) {
			throw new MidplatException("������ϸ������ʵ������������" + bankCount + "!=" + mm);
		}

		System.out.println("��ʵʱ��ϸ����(DtlSet)Ϊ��" + tLNContStatusUpdateSet.size());
		MMap mSubmitMMap = new MMap();
		mSubmitMMap.put(tLNContStatusUpdateSet, "INSERT");
		VData mSubmitVData = new VData();
		mSubmitVData.add(mSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, "")) {
			System.out.println("�����ʵʱ��ϸʧ�ܣ�"
					+ mPubSubmit.mErrors.getFirstError());
			throw new MidplatException("�����ʵʱ��ϸʧ�ܣ�");
		}

		return true;
	}

	protected boolean makeNoRealFile(String mUpLoadPath,
			String cDownloadFileName) throws Exception {
		String filePath = mUpLoadPath + cDownloadFileName;

		LNContStatusUpdateDB mLNContStatusUpdateDB = new LNContStatusUpdateDB();
		mLNContStatusUpdateDB.setType("08");
		mLNContStatusUpdateDB.setFlag("1");
		mLNContStatusUpdateDB.setBankCode("0103");
		LNContStatusUpdateSet mLNContStatusUpdateSet = mLNContStatusUpdateDB
				.query();
		int count = mLNContStatusUpdateSet.size();
		StringBuffer resultDataSB = new StringBuffer();
		resultDataSB.append("1141|");// ���չ�˾����
		resultDataSB.append("03|");// ���д���
		resultDataSB.append(count + "|");
		double sumprem = 0.0;
		for (int i = 1; i <= count; i++) {
			double prem = Double.parseDouble(toAmt(mLNContStatusUpdateSet
					.get(i).getTransAmt()));
			sumprem += prem;
		}
		resultDataSB.append(toAmt(String.valueOf(sumprem)) + "|");
		resultDataSB.append(System.getProperty("line.separator"));
		System.out.println("��֯��ũ�з�ʵʱ�б���ϸ�ļ�ͷ�ļ�Ϊ��" + resultDataSB.toString());
		for (int i = 1; i <= count; i++) {
			System.out.println("----ũ�з�ʵʱ��֯��" + i + "����ϸ����");
			LNContStatusUpdateSchema mLNContStatusUpdateSchema = mLNContStatusUpdateSet
					.get(i);
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getTransDate()) + "|");// ԭ��������
			/*-------���д���������--------*/
			resultDataSB
					.append(dealNull(mLNContStatusUpdateSchema.getBackUp1())
							+ "|");// ���з���ˮ��
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getAppntName()) + "|");// Ͷ��������
			String mAppntIdType = dealNull(YBTFun.getbank_Code(
					mLNContStatusUpdateSchema.getAppntIDType(), "idtype", "02"));
			resultDataSB.append(mAppntIdType + "|");// Ͷ����֤������
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getAppntIDNo()) + "|");// Ͷ����֤������
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getRiskCode()) + "|");// ���չ�˾���ִ���
			resultDataSB.append("|");// ��Ʒ����
			/*------------end--------------*/
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema.getPolNo())
					+ "|");// ������
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getSignDate()) + "|");// �б�����
			String mRelation = "";

			resultDataSB.append("|");// Ͷ�����˹�ϵ
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getInsuName()) + "|");// ����������
			String mInsuIdType = dealNull(YBTFun.getbank_Code(
					mLNContStatusUpdateSchema.getInsuIDType(), "idtype", "02"));
			resultDataSB.append(mInsuIdType + "|");// ������֤������
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getInsuIDNo()) + "|");// ������֤������
			resultDataSB
					.append(dealNull(mLNContStatusUpdateSchema.getallPrem())
							+ "|");// ����
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getTransAmt()) + "|");// ����
			resultDataSB
					.append(dealNull(mLNContStatusUpdateSchema.getBackUp2())
							+ "|");// �ɷ��˻�
			resultDataSB.append("|");// �ɷ�����
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getInsuEndDate()) + "|");// ����������
			resultDataSB.append("|");// Ͷ������
			resultDataSB.append("|");// ���Ի�����
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getOperator()) + "|");// ����ӡˢ��
			resultDataSB.append("|");
			resultDataSB.append(System.getProperty("line.separator"));
		}
		File tFile = new File(filePath);
		try {
			tFile.createNewFile();
			FileWriter fw = new FileWriter(tFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(resultDataSB.toString());
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	protected boolean saveAndUpdateNoRealData(String mDownLoadPath,
			String cDownloadFileName) throws Exception {
		String filePath = mDownLoadPath + cDownloadFileName;
		InputStream mIns = null;
		try {
			mIns = new FileInputStream(filePath);
		} catch (IOException ex) {
			throw new MidplatException("δ�ҵ������ļ���" + filePath);
		}
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				mIns, "GBK"));
		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		LNContStatusUpdateSet tLNContStatusUpdateSet = new LNContStatusUpdateSet();
		for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());) {
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				System.out.println("���У�ֱ��������������һ����");
				continue;
			}
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			String mContNo = tSubMsgs[8];
			String mResultCode = tSubMsgs[23];
			String mResultMsg = tSubMsgs[24];
			if (mResultCode.equals("240000"))
				mResultCode = "10";
			else
				mResultCode = "99";
			System.out.println("������Ϊ��" + mContNo + " �ı���ִ�н�����룺" + mResultCode
					+ " ����ǣ�" + mResultMsg);
			LNContStatusUpdateDB tLNContStatusUpdateDB = new LNContStatusUpdateDB();
			tLNContStatusUpdateDB.setPolNo(mContNo);
			if (!tLNContStatusUpdateDB.getInfo()) {
				System.out.println("δ�鵽�ñ����Ŷ�Ӧ��¼���������ǣ�" + mContNo);
				continue;
			}
			LNContStatusUpdateSchema mLNContStatusUpdateSchema = tLNContStatusUpdateDB
					.getSchema();
			mLNContStatusUpdateSchema.setFlag(mResultCode);
			mLNContStatusUpdateSchema.setDealMsg(mResultMsg);
			tLNContStatusUpdateSet.add(mLNContStatusUpdateSchema);
		}
		System.out.println("���лش���ʵʱ��Ϣ���У�" + tLNContStatusUpdateSet.size());
		MMap mSubmitMMap = new MMap();
		mSubmitMMap.put(tLNContStatusUpdateSet, "UPDATE");
		VData mSubmitVData = new VData();
		mSubmitVData.add(mSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, "")) {
			System.out.println("���ķ�ʵʱ������Ϣʧ�ܣ�" + mPubSubmit.mErrors.getFirstError());
			throw new MidplatException("���ķ�ʵʱ������Ϣʧ�ܣ�");
		}
		return true;
	}

	private static String toAmt(String str) {
		if (str == null || "".equals(str)) {
			return "0.00";
		} else {
			DecimalFormat df = new DecimalFormat("0.00");
			String prem = df.format(Double.parseDouble(str.trim()));
			return prem;
		}
	}

	/**
	 * ����null�Ϳո�
	 * 
	 * @param str
	 * @return
	 */
	private static String dealNull(String str) {
		if (str == null || "".equals(str)) {
			return "";
		} else {
			return str.trim();
		}
	}

	public static void main(String[] args) throws Exception {
		String filePath = "F:/ETSS_FTP/downLoad/INVALID.BANK1141.20150507";
		InputStream mIns = null;
		try {
			mIns = new FileInputStream(filePath);
		} catch (IOException ex) {
			throw new MidplatException("δ�ҵ������ļ���" + filePath);
		}
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				mIns, "GBK"));
		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		LNContStatusUpdateSet tLNContStatusUpdateSet = new LNContStatusUpdateSet();
		for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());) {
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				System.out.println("���У�ֱ��������������һ����");
				continue;
			}
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			String mContNo = tSubMsgs[2];
			String mResultCode = tSubMsgs[13];
			String mResultMsg = tSubMsgs[14];
			if (mResultCode.equals("240000"))
				mResultCode = "10";
			else
				mResultCode = "99";
			System.out.println("������Ϊ��" + mContNo + " �ı���ִ�н�����룺" + mResultCode
					+ " ����ǣ�" + mResultMsg);
			LNContStatusUpdateDB tLNContStatusUpdateDB = new LNContStatusUpdateDB();
			tLNContStatusUpdateDB.setPolNo(mContNo);
			if (!tLNContStatusUpdateDB.getInfo()) {
				System.out.println("δ�鵽�ñ����Ŷ�Ӧ��¼���������ǣ�" + mContNo);
				continue;
			}
			LNContStatusUpdateSchema mLNContStatusUpdateSchema = tLNContStatusUpdateDB
					.getSchema();
			mLNContStatusUpdateSchema.setFlag(mResultCode);
			mLNContStatusUpdateSchema.setDealMsg(mResultMsg);
			tLNContStatusUpdateSet.add(mLNContStatusUpdateSchema);
		}
		System.out.println("���лش���ȫ��Ϣ���У�" + tLNContStatusUpdateSet.size());
		MMap mSubmitMMap = new MMap();
		mSubmitMMap.put(tLNContStatusUpdateSet, "UPDATE");
		VData mSubmitVData = new VData();
		mSubmitVData.add(mSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, "")) {
			System.out.println("���ı�ȫ������Ϣʧ�ܣ�"
					+ mPubSubmit.mErrors.getFirstError());
			throw new MidplatException("���ı�ȫ������Ϣʧ�ܣ�");
		}

	}
	
	protected boolean saveAndUpdateNoRealData() throws Exception {
		String filePath = "F:/ETSS_FTP/downLoad/INVALID.BANK1141.20150507";
		InputStream mIns = null;
		try {
			mIns = new FileInputStream(filePath);
		} catch (IOException ex) {
			throw new MidplatException("δ�ҵ������ļ���" + filePath);
		}
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				mIns, "GBK"));
		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		LNContStatusUpdateSet tLNContStatusUpdateSet = new LNContStatusUpdateSet();
		for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());) {
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				System.out.println("���У�ֱ��������������һ����");
				continue;
			}
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			String mContNo = tSubMsgs[8];
			String mResultCode = tSubMsgs[23];
			String mResultMsg = tSubMsgs[24];
			if (mResultCode.equals("240000"))
				mResultCode = "10";
			else
				mResultCode = "99";
			System.out.println("������Ϊ��" + mContNo + " �ı���ִ�н�����룺" + mResultCode
					+ " ����ǣ�" + mResultMsg);
			LNContStatusUpdateDB tLNContStatusUpdateDB = new LNContStatusUpdateDB();
			tLNContStatusUpdateDB.setPolNo(mContNo);
			if (!tLNContStatusUpdateDB.getInfo()) {
				System.out.println("δ�鵽�ñ����Ŷ�Ӧ��¼���������ǣ�" + mContNo);
				continue;
			}
			LNContStatusUpdateSchema mLNContStatusUpdateSchema = tLNContStatusUpdateDB
					.getSchema();
			mLNContStatusUpdateSchema.setFlag(mResultCode);
			mLNContStatusUpdateSchema.setDealMsg(mResultMsg);
			tLNContStatusUpdateSet.add(mLNContStatusUpdateSchema);
		}
		mBufReader.close();
		System.out.println("���лش���ʵʱ��Ϣ���У�" + tLNContStatusUpdateSet.size());
		MMap mSubmitMMap = new MMap();
		mSubmitMMap.put(tLNContStatusUpdateSet, "UPDATE");
		VData mSubmitVData = new VData();
		mSubmitVData.add(mSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, "")) {
			System.out.println("���ķ�ʵʱ������Ϣʧ�ܣ�" + mPubSubmit.mErrors.getFirstError());
			throw new MidplatException("���ķ�ʵʱ������Ϣʧ�ܣ�");
		}
		return true;
	}
}
