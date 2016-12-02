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
			throw new MidplatException("未找到对账文件！" + filePath);
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

			// 空行，直接跳过
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				System.out.println("空行，直接跳过，继续下一条！");
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
				System.out.println("第" + i + "条非实时投保单号：----"
						+ remove.getProposalNo() + "当前投保单号：===" + mProposalNo);
				if (mProposalNo.equals(remove.getProposalNo())) {
					System.out.println("非实时明细中有重复投保单号！====="
							+ remove.getProposalNo());
					flag = false;
				}
			}
			if (flag)
				tLNContStatusUpdateSet.add(tLNContStatusUpdateSchema);

		}
		mBufReader.close(); // 关闭流
		if (bankPrem != mSumDtlPrem) {
			throw new MidplatException("汇总金额与明细总金额不符！" + bankPrem + "!="
					+ mSumDtlPrem);
		}
		if (bankCount != mm) {
			throw new MidplatException("汇总明细总数与实际总数不符！" + bankCount + "!=" + mm);
		}

		System.out.println("非实时明细总数(DtlSet)为：" + tLNContStatusUpdateSet.size());
		MMap mSubmitMMap = new MMap();
		mSubmitMMap.put(tLNContStatusUpdateSet, "INSERT");
		VData mSubmitVData = new VData();
		mSubmitVData.add(mSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, "")) {
			System.out.println("保存非实时明细失败！"
					+ mPubSubmit.mErrors.getFirstError());
			throw new MidplatException("保存非实时明细失败！");
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
		resultDataSB.append("1141|");// 保险公司代码
		resultDataSB.append("03|");// 银行代码
		resultDataSB.append(count + "|");
		double sumprem = 0.0;
		for (int i = 1; i <= count; i++) {
			double prem = Double.parseDouble(toAmt(mLNContStatusUpdateSet
					.get(i).getTransAmt()));
			sumprem += prem;
		}
		resultDataSB.append(toAmt(String.valueOf(sumprem)) + "|");
		resultDataSB.append(System.getProperty("line.separator"));
		System.out.println("组织的农行非实时承保明细文件头文件为：" + resultDataSB.toString());
		for (int i = 1; i <= count; i++) {
			System.out.println("----农行非实时组织第" + i + "行明细内容");
			LNContStatusUpdateSchema mLNContStatusUpdateSchema = mLNContStatusUpdateSet
					.get(i);
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getTransDate()) + "|");// 原申请日期
			/*-------银行传来的数据--------*/
			resultDataSB
					.append(dealNull(mLNContStatusUpdateSchema.getBackUp1())
							+ "|");// 银行方流水号
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getAppntName()) + "|");// 投保人姓名
			String mAppntIdType = dealNull(YBTFun.getbank_Code(
					mLNContStatusUpdateSchema.getAppntIDType(), "idtype", "02"));
			resultDataSB.append(mAppntIdType + "|");// 投保人证件类型
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getAppntIDNo()) + "|");// 投保人证件号码
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getRiskCode()) + "|");// 保险公司险种代码
			resultDataSB.append("|");// 产品编码
			/*------------end--------------*/
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema.getPolNo())
					+ "|");// 保单号
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getSignDate()) + "|");// 承保日期
			String mRelation = "";

			resultDataSB.append("|");// 投被保人关系
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getInsuName()) + "|");// 被保人姓名
			String mInsuIdType = dealNull(YBTFun.getbank_Code(
					mLNContStatusUpdateSchema.getInsuIDType(), "idtype", "02"));
			resultDataSB.append(mInsuIdType + "|");// 被保人证件类型
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getInsuIDNo()) + "|");// 被保人证件号码
			resultDataSB
					.append(dealNull(mLNContStatusUpdateSchema.getallPrem())
							+ "|");// 保费
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getTransAmt()) + "|");// 保额
			resultDataSB
					.append(dealNull(mLNContStatusUpdateSchema.getBackUp2())
							+ "|");// 缴费账户
			resultDataSB.append("|");// 缴费期限
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getInsuEndDate()) + "|");// 保单到期日
			resultDataSB.append("|");// 投保份数
			resultDataSB.append("|");// 个性化费率
			resultDataSB.append(dealNull(mLNContStatusUpdateSchema
					.getOperator()) + "|");// 保单印刷号
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
			throw new MidplatException("未找到对账文件！" + filePath);
		}
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				mIns, "GBK"));
		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		LNContStatusUpdateSet tLNContStatusUpdateSet = new LNContStatusUpdateSet();
		for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());) {
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				System.out.println("空行，直接跳过，继续下一条！");
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
			System.out.println("保单号为：" + mContNo + " 的保单执行结果代码：" + mResultCode
					+ " 结果是：" + mResultMsg);
			LNContStatusUpdateDB tLNContStatusUpdateDB = new LNContStatusUpdateDB();
			tLNContStatusUpdateDB.setPolNo(mContNo);
			if (!tLNContStatusUpdateDB.getInfo()) {
				System.out.println("未查到该保单号对应记录，保单号是：" + mContNo);
				continue;
			}
			LNContStatusUpdateSchema mLNContStatusUpdateSchema = tLNContStatusUpdateDB
					.getSchema();
			mLNContStatusUpdateSchema.setFlag(mResultCode);
			mLNContStatusUpdateSchema.setDealMsg(mResultMsg);
			tLNContStatusUpdateSet.add(mLNContStatusUpdateSchema);
		}
		System.out.println("银行回传非实时信息共有：" + tLNContStatusUpdateSet.size());
		MMap mSubmitMMap = new MMap();
		mSubmitMMap.put(tLNContStatusUpdateSet, "UPDATE");
		VData mSubmitVData = new VData();
		mSubmitVData.add(mSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, "")) {
			System.out.println("更改非实时交易信息失败！" + mPubSubmit.mErrors.getFirstError());
			throw new MidplatException("更改非实时交易信息失败！");
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
	 * 处理null和空格
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
			throw new MidplatException("未找到对账文件！" + filePath);
		}
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				mIns, "GBK"));
		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		LNContStatusUpdateSet tLNContStatusUpdateSet = new LNContStatusUpdateSet();
		for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());) {
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				System.out.println("空行，直接跳过，继续下一条！");
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
			System.out.println("保单号为：" + mContNo + " 的保单执行结果代码：" + mResultCode
					+ " 结果是：" + mResultMsg);
			LNContStatusUpdateDB tLNContStatusUpdateDB = new LNContStatusUpdateDB();
			tLNContStatusUpdateDB.setPolNo(mContNo);
			if (!tLNContStatusUpdateDB.getInfo()) {
				System.out.println("未查到该保单号对应记录，保单号是：" + mContNo);
				continue;
			}
			LNContStatusUpdateSchema mLNContStatusUpdateSchema = tLNContStatusUpdateDB
					.getSchema();
			mLNContStatusUpdateSchema.setFlag(mResultCode);
			mLNContStatusUpdateSchema.setDealMsg(mResultMsg);
			tLNContStatusUpdateSet.add(mLNContStatusUpdateSchema);
		}
		System.out.println("银行回传保全信息共有：" + tLNContStatusUpdateSet.size());
		MMap mSubmitMMap = new MMap();
		mSubmitMMap.put(tLNContStatusUpdateSet, "UPDATE");
		VData mSubmitVData = new VData();
		mSubmitVData.add(mSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, "")) {
			System.out.println("更改保全交易信息失败！"
					+ mPubSubmit.mErrors.getFirstError());
			throw new MidplatException("更改保全交易信息失败！");
		}

	}
	
	protected boolean saveAndUpdateNoRealData() throws Exception {
		String filePath = "F:/ETSS_FTP/downLoad/INVALID.BANK1141.20150507";
		InputStream mIns = null;
		try {
			mIns = new FileInputStream(filePath);
		} catch (IOException ex) {
			throw new MidplatException("未找到对账文件！" + filePath);
		}
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				mIns, "GBK"));
		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		LNContStatusUpdateSet tLNContStatusUpdateSet = new LNContStatusUpdateSet();
		for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());) {
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				System.out.println("空行，直接跳过，继续下一条！");
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
			System.out.println("保单号为：" + mContNo + " 的保单执行结果代码：" + mResultCode
					+ " 结果是：" + mResultMsg);
			LNContStatusUpdateDB tLNContStatusUpdateDB = new LNContStatusUpdateDB();
			tLNContStatusUpdateDB.setPolNo(mContNo);
			if (!tLNContStatusUpdateDB.getInfo()) {
				System.out.println("未查到该保单号对应记录，保单号是：" + mContNo);
				continue;
			}
			LNContStatusUpdateSchema mLNContStatusUpdateSchema = tLNContStatusUpdateDB
					.getSchema();
			mLNContStatusUpdateSchema.setFlag(mResultCode);
			mLNContStatusUpdateSchema.setDealMsg(mResultMsg);
			tLNContStatusUpdateSet.add(mLNContStatusUpdateSchema);
		}
		mBufReader.close();
		System.out.println("银行回传非实时信息共有：" + tLNContStatusUpdateSet.size());
		MMap mSubmitMMap = new MMap();
		mSubmitMMap.put(tLNContStatusUpdateSet, "UPDATE");
		VData mSubmitVData = new VData();
		mSubmitVData.add(mSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, "")) {
			System.out.println("更改非实时交易信息失败！" + mPubSubmit.mErrors.getFirstError());
			throw new MidplatException("更改非实时交易信息失败！");
		}
		return true;
	}
}
