package com.sinosoft.lis.citi;

import jxl.format.BorderLineStyle;

import com.sinosoft.lis.db.PolicyMasterDB;
import com.sinosoft.lis.db.PolicyTransactionAdjustmentDB;
import com.sinosoft.lis.db.PolicyTransactionDB;
import com.sinosoft.lis.excel.CreatExcel;
import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelBorder;
import com.sinosoft.lis.excel.ExcelFont;
import com.sinosoft.lis.f1print.LO_NoReal_PrintBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.PolicyMasterSchema;
import com.sinosoft.lis.schema.PolicyTransactionSchema;
import com.sinosoft.lis.vschema.PolicyMasterSet;
import com.sinosoft.lis.vschema.PolicyTransactionSet;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.DateUtilEn;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PolicyPremU{
	public CErrors mErrors = new CErrors();// 错误处理类，每个需要错误处理的类中都放置该类

	private MMap cSubmitMMap = new MMap();	
	VData inputData = new VData();
	TransferData tTransferData = new TransferData();
	
	private String policyNo = "";
	private String costAmount = "";
	private String premType = "";//此字段现在没用，因为是自己定义的，所以直接想要用也可以，
	private String premName = "";
	private String policyYear = "";
	private String productType = "";
	private String productName = "";
	private String tranDay = "";

	public PolicyPremU(VData tVData) {
		inputData = tVData;
		tTransferData = (TransferData) inputData.get(0);

		this.policyNo = (String) tTransferData.getValueByName("PolicyNo");
		this.costAmount = (String) tTransferData.getValueByName("CostAmount");
		this.premType = (String) tTransferData.getValueByName("PremType");
		this.premName = (String) tTransferData.getValueByName("PremName");
		this.policyYear = (String) tTransferData.getValueByName("PolicyYear");
		this.productType = (String) tTransferData.getValueByName("ProductType");
		this.productName = (String) tTransferData.getValueByName("ProductName");
		this.tranDay = (String) tTransferData.getValueByName("TranDay");
	}

	/**
	 * 返回错误
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 * @throws MidplatException 
	 */
	public void submitData() throws MidplatException {
		//Schema模式的使用现在没用。
		//PolicyTransactionAdjustmentDB pt = new PolicyTransactionAdjustmentDB();
		//pt.setSerialNo(aSerialNo)
		
		VData mSubmitVData = new VData();
		mSubmitVData.add(getInputData());
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, ""))
		{	
//			CError tError = new CError();
//			tError.moduleName = "PrintRateBL";
//			tError.functionName = "getPrintBankData";
//			tError.errorMessage = "@GMRSelGatherBL020@";
//			this.mErrors.addOneError(tError);
			this.mErrors.addOneError("执行插入失败");
			throw new MidplatException("调整失败");
		}

		cSubmitMMap = null;
		
	}
	

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 * @throws MidplatException 
	 */
	private MMap getInputData() throws MidplatException {
		String oid = PubFun1.CreateMaxNo("policyTranAdjust",1);
		Integer makeDate = DateUtil.getCur8Date();
		String makeTime = DateUtil.getCur8Time();
		String sql = "insert into PolicyTransactionAdjustment(SerialNo,TranDate,PolicyNo,PlanCode,PlanName,PremType,CostAmount,PolicyYear,MakeDate,MakeTime) " +
				"values('"+oid+"','"+tranDay+"','"+policyNo+"','"+productType+"','"+productName+"','"+premName+"',"+costAmount+",'"+policyYear+"',"+makeDate+",'"+makeTime+"')";
		cSubmitMMap.put(sql, "INSERT");
		
		String getPolicyMaster = "select * from PolicyMaster pm where pm.policyNo = '"+this.policyNo+"' order by pm.ExtractedDate desc ";
		PolicyMasterDB policyMasterDB = new PolicyMasterDB();
		PolicyMasterSet policyMasterSet = new PolicyMasterSet();
		policyMasterSet = policyMasterDB.executeQuery(getPolicyMaster);
		if(policyMasterSet==null){
			mErrors.addOneError("更新的保单号有误，请确认保单号");
			throw new MidplatException("更新的保单号有误，请确认保单号");
		}
		PolicyMasterSchema pm =  policyMasterSet.get(1);
		if(pm==null){
			mErrors.addOneError("更新的保单号有误，请确认保单号");
			throw new MidplatException("更新的保单号有误，请确认保单号");
		}
		policyMasterDB = pm.getDB();	
		policyMasterDB.setSerialNo("8"+PubFun1.CreateMaxNo("PolicyMaster", 19));
		
		PolicyTransactionDB policyTransactionDB = new PolicyTransactionDB();
		PolicyTransactionSet policyTransactionSet = new PolicyTransactionSet();
		String getPolicyTransaction ="select * from PolicyTransaction pt where pt.policyNo='"+this.policyNo+"' and pt.PlanType='B' order by pt.ExtractedDate desc ";
		policyTransactionSet = policyTransactionDB.executeQuery(getPolicyTransaction);
		if(policyTransactionSet ==null){
			mErrors.addOneError("更新的保单号有误，请确认保单号");
			throw new MidplatException("更新的保单号有误，请确认保单号");
		}
		PolicyTransactionSchema pt  =  policyTransactionSet.get(1);
		if(pt==null){
			mErrors.addOneError("更新的保单号有误，请确认保单号");
			throw new MidplatException("更新的保单号有误，请确认保单号");
		}
		policyTransactionDB = pt.getDB();
		
		
		policyTransactionDB.setSerialNo("8"+PubFun1.CreateMaxNo("PolicyTransaction", 19));
		policyTransactionDB.setPolicyYear(this.policyYear);
		
		policyMasterDB.setDealDate(0);
		policyMasterDB.setDealTime("");
		policyMasterDB.setModalPremium(0.0);
		policyMasterDB.setLumpSumPremium(0.0);
		policyMasterDB.setInitLumpSumPremium(0.0);
		policyMasterDB.setRegularTopUpPremium(0.0);
		policyMasterDB.setMakeDate(DateUtilEn.dateToTomorrow(DateUtil.getCur8Date()));
		policyMasterDB.setModifyDate(DateUtil.getCur8Date());
		policyMasterDB.setExtractedDate(DateUtil.getCur8Date());
//		policyMasterDB.setModifyTime("");
//		policyMasterDB.setMakeDate(DateUtilEn.dateToTomorrow(Integer.parseInt(DateUtil.date10to8(this.tranDay))));
		
		policyTransactionDB.setDealDate(0);
		policyTransactionDB.setDealTime("");
		policyTransactionDB.setInitLumpSumPremium(0.0);
		policyTransactionDB.setModalRegularPremium(0.0);
		policyTransactionDB.setRegularTopUpPremium(0.0);
		policyTransactionDB.setLumpSumPremium(0.0);
		policyTransactionDB.setTransactionType("ADJ");
		policyTransactionDB.setSubTransactionType("E");
		policyTransactionDB.setMakeDate(DateUtilEn.dateToTomorrow(DateUtil.getCur8Date()));
		policyTransactionDB.setModifyDate(DateUtil.getCur8Date());
		policyTransactionDB.setExtractedDate(DateUtil.getCur8Date());
//		policyTransactionDB.setModifyTime("");
//		policyTransactionDB.setMakeDate(DateUtilEn.dateToTomorrow(Integer.parseInt(DateUtil.date10to8(this.tranDay))));
		
		
		if("AF".equals(premType)){//追加投资 CHG T RegularTopUpPremium； TransactionAmount
//			policyTransactionDB.setTransactionType("CHG");
//			policyTransactionDB.setSubTransactionType("T");
			policyTransactionDB.setRegularTopUpPremium(this.costAmount);
			policyTransactionDB.setTransactionAmount(this.costAmount);
			
			policyMasterDB.setRegularTopUpPremium(this.costAmount);
		}else if("FA".equals(premType)){//首期追加 CHG T initLumpSumPremium； TransactionAmount
//			policyTransactionDB.setTransactionType("CHG");
//			policyTransactionDB.setSubTransactionType("T");
			policyTransactionDB.setInitLumpSumPremium(this.costAmount);
			policyTransactionDB.setTransactionAmount(this.costAmount);
			
			policyMasterDB.setInitLumpSumPremium(this.costAmount);
				
		}else if("FP".equals(premType)){//首期保费 PAY 1 ModalRegularPremium； TransactionAmount
//			policyTransactionDB.setTransactionType("PAY");
//			policyTransactionDB.setSubTransactionType("1");
			policyTransactionDB.setModalRegularPremium(this.costAmount);
			policyTransactionDB.setTransactionAmount(this.costAmount);
			
			policyMasterDB.setModalPremium(this.costAmount);
		}else if("PU".equals(premType)){//保单变更 CHG 2 TransactionAmount；保全 
//			policyTransactionDB.setTransactionType("CHG");
//			policyTransactionDB.setSubTransactionType("2");
			policyTransactionDB.setTransactionAmount(this.costAmount);
			
			policyMasterDB.setModalPremium(this.costAmount);
			
		}else if("XP".equals(premType)){//续期保费PAY 3 ModalRegularPremium； TransactionAmount
//			policyTransactionDB.setTransactionType("PAY");
//			policyTransactionDB.setSubTransactionType("3");
			policyTransactionDB.setModalRegularPremium(this.costAmount);
			policyTransactionDB.setTransactionAmount(this.costAmount);
			
			policyMasterDB.setModalPremium(this.costAmount);
					
		}else if("TZ".equals(premType)){//投资CHG T LumpSumPremium； TransactionAmount
			
//			policyTransactionDB.setTransactionType("CHG");
//			policyTransactionDB.setSubTransactionType("T");
			policyTransactionDB.setLumpSumPremium(this.costAmount);
			policyTransactionDB.setTransactionAmount(this.costAmount);
			
			policyMasterDB.setLumpSumPremium(this.costAmount);		
		}
		if(!policyMasterDB.insert()){
			//插入失败，保单号对应的记录没查到，
			mErrors.addOneError("执行插入失败");
			throw new MidplatException("执行插入失败");
		}
		if(!policyTransactionDB.insert()){
			//插入失败
			mErrors.addOneError("执行插入失败");
			throw new MidplatException("执行插入失败");
		}	 
			
		return cSubmitMMap;
	}
	
	public static void main(String[] args) {	
//		String s = "8"+PubFun1.CreateMaxNo("aaaaaa", 19);
//		System.out.print(s);
	}
}
