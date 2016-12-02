package com.sinosoft.lis.ybt;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.AgentSchema;
import com.sinosoft.lis.schema.ContSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.NodeMapSchema;
import com.sinosoft.lis.vschema.AgentSet;
import com.sinosoft.lis.vschema.ContSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.NodeMapSet;
import com.sinosoft.midplat.common.*;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;

public class YbtContQueryBL implements XmlTag
{
	private final String cContNo;

	private final static Logger cLogger = Logger.getLogger(YbtContQueryBL.class);

	public YbtContQueryBL(String pContNo)
	{
		cContNo = pContNo;
	}

	public Document deal() throws MidplatException
	{
		cLogger.info("Into YbtContQueryBL.deal()...");

		Document mOutXmlDoc = getSimpOutXml("0", "交易成功！");

		long tStartMillism = System.currentTimeMillis();
		Element mBody = getBody(cContNo);
		cLogger.info("YbtContQueryBL.getLCCont()耗时：" + (System.currentTimeMillis() - tStartMillism) / 1000.0 + "s");
		

		mOutXmlDoc.getRootElement().addContent(mBody);

		cLogger.info("Out YbtContQueryBL.deal()!");
		//JdomUtil.print(mOutXmlDoc);
		return mOutXmlDoc;
	}

	
	
	/**
	 * @param pContNo
	 * @return
	 * @throws MidplatException
	 */
	public Element getBody(String pContNo) throws MidplatException
	{
		cLogger.debug("Into YbtContQueryBL.getBody()...");

		LCContDB mLCContDB = new LCContDB();
		mLCContDB.setContNo(pContNo);
		if (!mLCContDB.getInfo())
		{
			throw new MidplatException("查询保单合同信息失败！");
		}

		LCPolDB mLCPolDB = new LCPolDB();
		String sql = "select * from lcpol p where p.contno='"+pContNo+"' order by polno";
		LCPolSet mLCPolSet = mLCPolDB.executeQuery(sql);
		if ((null == mLCPolSet) || (mLCPolSet.size() < 1))
		{
			cLogger.error(mLCPolDB.mErrors.getFirstError());
			throw new MidplatException("查询险种信息失败！");
		}
		
		LDComDB mLDComDB = new LDComDB();
		mLDComDB.setComCode(mLCContDB.getManageCom());
		if (!mLDComDB.getInfo())
		{
			throw new MidplatException("查询管理机构信息失败！");
		}
		
//		ContSchema mContSchema = new ContSchema();
//		ContDB mContDB = new ContDB();
//		mContDB.setContNo(mLCContDB.getContNo());
//		ContSet mContSet = mContDB.query();
//		if ((null == mContSet) || (mContSet.size() < 1))
//		{
//			cLogger.error(mLCPolDB.mErrors.getFirstError());
//			throw new MidplatException("查询险种信息失败！");
//		}
//		mContSchema = mContSet.get(1);
		


		
		
		/** <Body> */
		Element mBody = new Element(Body);
		
		String sBalanceState = "";
		if(mLCContDB.getBalanceState() != null && "Y".equals(mLCContDB.getBalanceState())){
			sBalanceState = "已对账";
		}else if(mLCContDB.getBalanceState() != null && "N".equals(mLCContDB.getBalanceState())){
			sBalanceState = "未对账";
		}
		/** <BalanceState> baodan状态 */
		Element mBalanceState = new Element("BalanceState");
		mBalanceState.setText(sBalanceState);
		mBody.addContent(mBalanceState);

		String sContState ="";
		if("0".equals(mLCContDB.getAppFlag()) && "1".equals(mLCContDB.getUWFlag())){
			sContState = "未签单";
		}else if(("B".equals(mLCContDB.getAppFlag())||"1".equals(mLCContDB.getAppFlag())) 
				&& ("9".equals(mLCContDB.getUWFlag())||"6".equals(mLCContDB.getUWFlag()))){
			sContState = "保单生效";
		}else if("0".equals(mLCContDB.getAppFlag()) && "3".equals(mLCContDB.getUWFlag())){
			sContState = "保单失效";
		}else if("1".equals(mLCContDB.getAppFlag()) && "8".equals(mLCContDB.getUWFlag())){
			sContState = "受理状态";
		}else if("0".equals(mLCContDB.getAppFlag()) && "7".equals(mLCContDB.getUWFlag())){
			sContState = "客户拒绝";
		}else if("0".equals(mLCContDB.getAppFlag()) && "11".equals(mLCContDB.getUWFlag())){
			sContState = "拒保";
		}else if(("B".equals(mLCContDB.getAppFlag())||"1".equals(mLCContDB.getAppFlag())) && "6".equals(mLCContDB.getUWFlag())){
			sContState = "转非实时";
		}else if("0".equals(mLCContDB.getAppFlag()) && "a".equals(mLCContDB.getUWFlag())){
			sContState = "协议终止";
		}else if("1".equals(mLCContDB.getAppFlag()) && "12".equals(mLCContDB.getUWFlag())){
			sContState = "延期";
		}
		
		/** <BalanceState> 保单状态 */
		Element mContState = new Element("ContState");
		mContState.setText(sContState);
		mBody.addContent(mContState);
		
		//System.out.println("银行签单流水"+mContSchema.getBak3());
		/** <TranNo> 银行签单流水 */
		Element mTranNo = new Element(TranNo);
		//mTranNo.setText(mContSchema.getBak3());
		mBody.addContent(mTranNo);
		
		/** <ContNo> 保险合同号 */
		Element mContNo = new Element(ContNo);
		mContNo.setText(mLCContDB.getContNo());
		mBody.addContent(mContNo);
  
		/** <ProposalPrtNo> 投保单号 */
		Element mProposalPrtNo = new Element(ProposalPrtNo);
		mProposalPrtNo.setText(mLCContDB.getProposalContNo());
		mBody.addContent(mProposalPrtNo);
		
		/** <PrtNo> 单证号 */
		Element mPrtNo = new Element("PrtNo");
		mPrtNo.setText(mLCContDB.getPrtNo());
		mBody.addContent(mPrtNo);
		
		/** <TranCom> 交易机构 */
		Element mTranCom = new Element("TranCom");
		mTranCom.setText(mLCContDB.getBankCode());
		mBody.addContent(mTranCom);
		
		/** <TranCom> 交易机构 */
		Element mAxaNodeMap = new Element("AxaNodeMap");
		mAxaNodeMap.setText(mLCContDB.getAXANodeMap());
		mBody.addContent(mAxaNodeMap);
		
		/** <TranComName> 交易机构名称 */
		Element mTranComName = new Element("TranComName");
		String mSqlStr = "select CODENAME from ldcode where codetype='trancom_bank' and code= '" + mLCContDB.getBankCode() + "'";
		mTranComName.setText(new ExeSQL().getOneValue(mSqlStr));
		mBody.addContent(mTranComName);
		
		/** <ZoneNo> 地区码 */
		Element mZoneNo = new Element("ZoneNo");
		//mZoneNo.setText(mContSchema.getZoneNo());
		mBody.addContent(mZoneNo);
		
		/** <NodeNo> 网点码 */
		Element mNodeNo = new Element("NodeNo");
		//mNodeNo.setText(mContSchema.getNodeNo());
		mBody.addContent(mNodeNo);
		
		/** <Prem> 总保费(分) */
		Element mSumPrem = new Element("Prem");
		mSumPrem.setText(String.valueOf((mLCContDB.getPrem())));
		mBody.addContent(mSumPrem);
		
		/** <Prem> 主险基本保费(分) */
		Element mMainPrem = new Element("MainPrem");
		mMainPrem.setText(String.valueOf(mLCContDB.getMainPolPrem()));
		mBody.addContent(mMainPrem);
		
		/** <Prem> 期交追加保费(分) */
		Element mAddPrem = new Element("AddPrem");
		mAddPrem.setText(String.valueOf(mLCContDB.getAddPrem()));
		mBody.addContent(mAddPrem);
		
		/** <Prem> 首期追加保费(分) */
		Element mFirstAddPrem = new Element("FirstAddPrem");
		mFirstAddPrem.setText(String.valueOf((mLCContDB.getFirstAddPrem())));
		mBody.addContent(mFirstAddPrem);

		/** <PremText> 总保费大写 */
		Element mSumAmnt = new Element("PremText");
		mSumAmnt.setText(PubFun.getChnMoney(mLCContDB.getPrem()));
		mBody.addContent(mSumAmnt);

		/** <Amnt> 总保额 */
		Element mSumAmntEle = new Element("Amnt");
		mSumAmntEle.setText(String.valueOf((mLCContDB.getAmnt())));
		mBody.addContent(mSumAmntEle);

		/** <AmntText> 总保额大写 */
		Element mSumAmntTextEle = new Element("AmntText");
		mSumAmntTextEle.setText(PubFun.getChnMoney(mLCContDB.getAmnt()));
		mBody.addContent(mSumAmntTextEle);

		/** <AmntText> 代理人编码 */
		Element mAgentCode = new Element(AgentCode);
		mAgentCode.setText(mLCContDB.getAgentCode());
		mBody.addContent(mAgentCode);

		/** <AgentName> 代理人名称 */
		Element mAgentName = new Element(AgentName);
		mAgentName.setText(mLCContDB.getAgentName());
		mBody.addContent(mAgentName);

		/** <AgentGrpCode> 代理人组别编码 */
		Element mUnitCode = new Element("AgentUnitCode");
		//mUnitCode.setText(mContSchema.getUnitCode());
		mBody.addContent(mUnitCode);

		/** <银行柜员> 代理人组别编码 */
		Element mOperator = new Element("Operator");
		mOperator.setText(mLCContDB.getInputOperator());
		mBody.addContent(mOperator);


		/** <AgentCom> 代理机构 */
		Element mAgentComEle = new Element("AgentCom");
		mAgentComEle.setText(mLCContDB.getAgentCom());
		mBody.addContent(mAgentComEle);

		/** <AgentComName> 代理机构名称 */
		Element mAgentComNameEle = new Element("AgentComName");
		mAgentComNameEle.setText(mLCContDB.getAgentComName());
		mBody.addContent(mAgentComNameEle);


		/** <ComCode> 承保公司编码 */
		Element mComCode = new Element("ComCode");
		mComCode.setText(mLDComDB.getComCode());
		mBody.addContent(mComCode);
		
		/** <AreaNo> 分公司地区码*/
		Element mAreaNo = new Element("AreaNo");
	//	mAreaNo.setText(mLDComDB.getAreaID());
		mBody.addContent(mAreaNo);
		
		/** <AreaNo> 分公司地区名称*/
		Element mAreaName = new Element("AreaName");
		//mAreaName.setText(mLDComDB.getAreaName());
		mBody.addContent(mAreaName);
		
		/** <AreaNo> 分公司城市码*/
		Element mCityNo = new Element("CityNo");
		//mCityNo.setText(mLDComDB.getCityCode());
		mBody.addContent(mCityNo);
		
		/** <AreaNo> 分公司城市名称*/
		Element mCityName = new Element("CityName");
		//mCityName.setText(mLDComDB.getCityName());
		mBody.addContent(mCityName);

		/** <ComLocation>  承保公司地址 */
		Element mComLocation = new Element("ComLocation");
		mComLocation.setText(mLDComDB.getAddress());
		mBody.addContent(mComLocation);

		/** <ComName>   承保公司名称 */
		Element mComName = new Element(ComName);
		mComName.setText(mLDComDB.getName());
		mBody.addContent(mComName);
		
		/** <ComName>   承保公司名称 */
		Element mComShortName = new Element("ComShortName");
		mComShortName.setText(mLDComDB.getShortName());
		mBody.addContent(mComShortName);

		/** <ComZipCode>  承保公司邮编*/
		Element mComZipCode = new Element(ComZipCode);
		mComZipCode.setText(mLDComDB.getZipCode());
		mBody.addContent(mComZipCode);

		/** <ComZipCode>  承保公司电话*/
		Element mComPhone = new Element(ComPhone);
		mComPhone.setText(mLDComDB.getPhone());
		mBody.addContent(mComPhone);

		/** <AccName>  账户所有人  */
		Element mAccName = new Element(AccName);
		mAccName.setText(mLCContDB.getAccName());
		mBody.addContent(mAccName);
		
		/** <AccName>  账户  */
		Element mAccNo = new Element(AccNo);
		mAccNo.setText(mLCContDB.getBankAccNo());
		mBody.addContent(mAccNo);
		
		/** <Appnt> 投保人节点 */
		mBody.addContent(getAppnt(pContNo));

		/** <Insured> 被保人节点 */
		mBody.addContent(getInsured(pContNo));

		/** <Bnf> 收益人节点 */
		LCBnfDB mLCBnfDB = new LCBnfDB();
		mLCBnfDB.setContNo(pContNo);

		LCBnfSet mLCBnfSet = mLCBnfDB.query();
		if (mLCBnfDB.mErrors.needDealError())
		{
			cLogger.error(mLCBnfDB.mErrors.getFirstError());
			throw new MidplatException("查询受益人信息失败！");
		}
		int mSize = mLCBnfSet.size();
		for (int i = 1; i <= mSize; i++)
		{
			mBody.addContent(getBnf(mLCBnfSet.get(i)));
			
		}

		
		/** <Cont> 险种节点 */
		mSize = mLCPolSet.size();
		
		for (int i = 1; i <= mSize; i++)
		{
			Element tRisk = getRisk(mLCPolSet.get(i));
			mBody.addContent(tRisk);
			
			
		}

		cLogger.debug("Out YbtContQueryBL.getBody()!");
		return mBody;
	}

	
	
	/**
	 * ************************************************************
	 * 		投保人节点
	 * @param pContNo
	 * @return Element
	 * @throws MidplatException
	 */
	private Element getAppnt(String pContNo) throws MidplatException
	{
		cLogger.debug("Into YbtContQueryBL.getAppnt()...");

		LCAppntDB mLCAppntDB = new LCAppntDB();
		mLCAppntDB.setContNo(pContNo);
		if (!mLCAppntDB.getInfo())
		{
			throw new MidplatException("查询投保人信息失败！");
		}
		LCAddressDB mAddressDB = new LCAddressDB();
		mAddressDB.setCustomerNo(mLCAppntDB.getAppntNo());
		mAddressDB.setAddressNo(mLCAppntDB.getAddressNo());
		if (!mAddressDB.getInfo())
		{
			throw new MidplatException("查询投保人地址信息失败！");
		}

		/** <CustomerNo> 投保人客户号*/
		Element mCustomerNo = new Element(CustomerNo);
		mCustomerNo.setText(mLCAppntDB.getAppntNo());

		/** <Name> 投保人姓名*/
		Element mName = new Element(Name);
		mName.setText(mLCAppntDB.getAppntName());

		/** <Sex> 投保人性别 */
		Element mSex = new Element(Sex);
		mSex.setText(mLCAppntDB.getAppntSex());
		
		
		/** <Sex> 投保人性别名称 */
		Element mSexName = new Element("SexName");
		mSexName.setText(getSexName(mLCAppntDB.getAppntSex()));

		/** <Birthday> 投保人生日 */
		Element mBirthday = new Element(Birthday);
		mBirthday.setText(mLCAppntDB.getAppntBirthday());
		
		/** <Age> 投保人生日 */
		Element mAge = new Element("Age");
		mAge.setText(MidplatUtil.calInterval(mLCAppntDB.getAppntBirthday(),mLCAppntDB.getMakeDate() , "Y"));
		
		/** <IDType> 投保人证件类型 */
		Element mIDType = new Element(IDType);
		mIDType.setText(mLCAppntDB.getIDType());
		
		/** <IDType> 投保人证件名称 */
		Element mIDTypeName = new Element("IDTypeName");
		mIDTypeName.setText(MidplatUtil.getLDCodeName(mLCAppntDB.getIDType(), "id_type"));

		/** <IDNo> 投保人证件号 */
		Element mIDNo = new Element(IDNo);
		mIDNo.setText(mLCAppntDB.getIDNo());

		/** <JobType> 职业类别 */
		Element mJobType = new Element("JobType");
		mJobType.setText(mLCAppntDB.getOccupationType());

		/** <JobCode> 职业代码 */
		Element mJobCode = new Element("JobCode");
		mJobCode.setText(mLCAppntDB.getOccupationCode());


		/** <Nationality> 国籍 */
		Element mNationality = new Element("Nationality");
		mNationality.setText(mLCAppntDB.getNativePlace());

		/** <Stature> 身高 */
		Element mStature = new Element("Stature");
		String stature = "";
		if (mLCAppntDB.getStature() > 0)
		{
			stature = Double.toString(mLCAppntDB.getStature());
		}
		mStature.setText(stature);

		/** <Weight> 体重 */
		String Avoirdupois = "";
		if (mLCAppntDB.getAvoirdupois() > 0)
		{
			Avoirdupois = Double.toString(mLCAppntDB.getAvoirdupois());
		}
		Element mWeight = new Element("Weight");
		mWeight.setText(Avoirdupois);

		/** <MaritalStatus>  婚否 */
		Element mMaritalStatus = new Element("MaritalStatus");
		mMaritalStatus.setText(mLCAppntDB.getMarriage());

		/** <Address>  地址 */
		Element mAddress = new Element(Address);
		mAddress.setText(mAddressDB.getPostalAddress());

		/** <ZipCode>  邮政编码 */
		Element mZipCode = new Element(ZipCode);
		mZipCode.setText(mAddressDB.getZipCode());

		/** <Mobile>  移动电话 */
		Element mMobile = new Element(Mobile);
		mMobile.setText(mAddressDB.getMobile());

		/** <Phone>  固定电话 */
		Element mPhone = new Element(Phone);
		mPhone.setText(mAddressDB.getPhone());

		/** <Email>  电子邮件 */
		Element mEmail = new Element(Email);
		mEmail.setText(mAddressDB.getEMail());

		/** <RelaToInsured>  与被保人关系 */
		Element mRelaToInsured = new Element(RelaToInsured);
		String mSqlStr = "select RelationToAppnt from LCInsured where ContNo='" + pContNo + "'";
		String sRelaToInsured = new ExeSQL().getOneValue(mSqlStr);
		mRelaToInsured.setText(sRelaToInsured);
		
		/** <IDType> 与被保人关系名称 */
		Element mRelaToInsuredName = new Element("RelaToInsuredName");
		mRelaToInsuredName.setText(MidplatUtil.getLDCodeName(sRelaToInsured, "relation"));
		
		Element mAppnt = new Element(Appnt);
		mAppnt.addContent(mCustomerNo);
		mAppnt.addContent(mName);
		mAppnt.addContent(mSex);
		mAppnt.addContent(mSexName);
		mAppnt.addContent(mBirthday);
		mAppnt.addContent(mAge);
		mAppnt.addContent(mIDType);
		mAppnt.addContent(mIDTypeName);
		mAppnt.addContent(mIDNo);
		mAppnt.addContent(mJobType);
		mAppnt.addContent(mJobCode);
		mAppnt.addContent(mNationality);
		mAppnt.addContent(mStature);
		mAppnt.addContent(mWeight);
		mAppnt.addContent(mMaritalStatus);
		mAppnt.addContent(mAddress);
		mAppnt.addContent(mZipCode);
		mAppnt.addContent(mMobile);
		mAppnt.addContent(mPhone);
		mAppnt.addContent(mEmail);
		mAppnt.addContent(mRelaToInsured);
		mAppnt.addContent(mRelaToInsuredName);

		cLogger.debug("Out YbtContQueryBL.getAppnt()!");
		return mAppnt;
	}

	
	
	/**
	 * ************************************************************
	 * 		被保人节点
	 * @param pContNo
	 * @return Element
	 * @throws MidplatException
	 */	
	private Element getInsured(String pContNo) throws MidplatException
	{
		cLogger.debug("Into YbtContQueryBL.getInsured()...");

		LCInsuredDB mLCInsuredDB = new LCInsuredDB();
		mLCInsuredDB.setContNo(pContNo);
		LCInsuredSet mLCInsuredSet = mLCInsuredDB.query();
		if ((null == mLCInsuredSet) || (mLCInsuredSet.size() < 1))
		{
			throw new MidplatException("查询被保人信息失败！");
		}

		LCInsuredSchema mLCInsuredSchema = mLCInsuredSet.get(1);
		LCAddressDB mAddressDB = new LCAddressDB();
		mAddressDB.setCustomerNo(mLCInsuredSchema.getInsuredNo());
		mAddressDB.setAddressNo(mLCInsuredSchema.getAddressNo());
		if (!mAddressDB.getInfo())
		{
			throw new MidplatException("查询被保人地址信息失败！");
		}

		Element tCustomerNo = new Element(CustomerNo);
		tCustomerNo.setText(mLCInsuredSchema.getInsuredNo());

		Element tName = new Element(Name);
		tName.setText(mLCInsuredSchema.getName());

		Element tSex = new Element(Sex);
		tSex.setText(mLCInsuredSchema.getSex());
		
		/** <Sex> 被保人性别名称 */
		Element mSexName = new Element("SexName");
		mSexName.setText(getSexName(mLCInsuredSchema.getSex()));
		
		Element tBirthday = new Element(Birthday);
		tBirthday.setText(mLCInsuredSchema.getBirthday());
		
		/** <Age> 被保人生日 */
		Element mAge = new Element("Age");
		mAge.setText(String.valueOf(MidplatUtil.calInterval(mLCInsuredSchema.getBirthday(),mLCInsuredSchema.getMakeDate() , "Y", "AXA")));

		Element tIDType = new Element(IDType);
		tIDType.setText(mLCInsuredSchema.getIDType());

		/** <IDType> 投保人证件名称 */
		Element mIDTypeName = new Element("IDTypeName");
		mIDTypeName.setText(MidplatUtil.getLDCodeName(mLCInsuredSchema.getIDType(), "id_type"));
		
		Element tIDNo = new Element(IDNo);
		tIDNo.setText(mLCInsuredSchema.getIDNo());

		Element mJobType = new Element("JobType");
		mJobType.setText(mLCInsuredSchema.getOccupationType());

		Element mJobCode = new Element("JobCode");
		mJobCode.setText(mLCInsuredSchema.getOccupationCode());

		Element mStature = new Element("Stature");
		String stature = "";
		if (mLCInsuredSchema.getStature() > 0)
		{
			stature = Double.toString(mLCInsuredSchema.getStature());
		}
		mStature.setText(stature);

		Element mNationality = new Element("Nationality");
		mNationality.setText(mLCInsuredSchema.getNationality());

		String Avoirdupois = "";
		if (mLCInsuredSchema.getAvoirdupois() > 0)
		{
			Avoirdupois = Double.toString(mLCInsuredSchema.getAvoirdupois());
		}
		Element mWeight = new Element("Weight");
		mWeight.setText(Avoirdupois);

		Element mMaritalStatus = new Element("MaritalStatus");
		mMaritalStatus.setText(mLCInsuredSchema.getMarriage());

		Element tAddress = new Element(Address);
		tAddress.setText(mAddressDB.getPostalAddress());

		Element tZipCode = new Element(ZipCode);
		tZipCode.setText(mAddressDB.getZipCode());

		Element tMobile = new Element(Mobile);
		tMobile.setText(mAddressDB.getMobile());

		Element tPhone = new Element(Phone);
		tPhone.setText(mAddressDB.getPhone());

		Element tEmail = new Element(Email);
		tEmail.setText(mAddressDB.getEMail());


		Element mInsured = new Element(Insured);
		mInsured.addContent(tCustomerNo);
		mInsured.addContent(tName);
		mInsured.addContent(tSex);
		mInsured.addContent(mSexName);
		mInsured.addContent(tBirthday);
		mInsured.addContent(mAge);
		mInsured.addContent(tIDType);
		mInsured.addContent(mIDTypeName);
		mInsured.addContent(tIDNo);
		mInsured.addContent(mJobType);
		mInsured.addContent(mJobCode);
		//mInsured.addContent(mJobName);
		mInsured.addContent(mStature);
		mInsured.addContent(mNationality);
		mInsured.addContent(mWeight);
		mInsured.addContent(mMaritalStatus);
		mInsured.addContent(tAddress);
		mInsured.addContent(tZipCode);
		mInsured.addContent(tMobile);
		mInsured.addContent(tPhone);
		mInsured.addContent(tEmail);

		cLogger.debug("Out YbtContQueryBL.getInsured()!");
		return mInsured;
	}
	
	
	

	private Element getRisk(LCPolSchema pLCPolSchema) throws MidplatException
	{
		String mSqlStr1 = "select RiskCode,InsuredAppAge from LCPol where MainPolNo=PolNo and ContNo='"
				+ pLCPolSchema.getContNo() + "'";
		SSRS tSSRS2 = new SSRS();
		tSSRS2 = new ExeSQL().execSQL(mSqlStr1);
		String mMainRiskCodeStr = null;

		mMainRiskCodeStr = tSSRS2.GetText(1, 1);

		cLogger.debug("Into YbtContQueryBL.getRisk()...");
		
		Element mRiskCode = new Element(RiskCode);
		mRiskCode.setText(pLCPolSchema.getRiskCode());

		Element mRiskName = new Element(RiskName);
		String mSqlStr = "select RiskName from LMRiskApp where RiskCode='" + pLCPolSchema.getRiskCode() + "'";
		mRiskName.setText(new ExeSQL().getOneValue(mSqlStr));
		
		Element mMainRiskCode = new Element("MainRiskCode");
		mMainRiskCode.setText(mMainRiskCodeStr);

		Element mPolApplyDate = new Element(PolApplyDate);
		mPolApplyDate.setText((pLCPolSchema.getPolApplyDate()));

		Element mSignDate = new Element(SignDate);
		mSignDate.setText((pLCPolSchema.getSignDate()));
		
		Element mCValiDate = new Element(CValiDate);
		mCValiDate.setText((pLCPolSchema.getCValiDate()));

		Element mInsuEndDate = new Element("InsuEndDate");
		mInsuEndDate.setText((pLCPolSchema.getEndDate()));

		Element mAmnt = new Element(Amnt);
		mAmnt.setText(String.valueOf((pLCPolSchema.getAmnt())));

		Element mPrem = new Element(Prem);
		mPrem.setText(String.valueOf((pLCPolSchema.getPrem())));
			

		Element mMult = new Element(Mult);
		mMult.setText(String.valueOf((int) pLCPolSchema.getMult()));

		Element mPayIntv = new Element(PayIntv);
		mPayIntv.setText(String.valueOf(pLCPolSchema.getPayIntv()));
		
		Element mPayIntvName = new Element("PayIntvName");
		mPayIntvName.setText(MidplatUtil.getLDCodeName(String.valueOf(pLCPolSchema.getPayIntv()), "payintv"));

		Element mPayMode = new Element("PayMode");
		mPayMode.setText(pLCPolSchema.getPayMode());

		Element mInsuYearFlag = new Element(InsuYearFlag);
		mInsuYearFlag.setText(pLCPolSchema.getInsuYearFlag());

		Element mInsuYearFlagName = new Element("InsuYearFlagName");
		mInsuYearFlagName.setText(MidplatUtil.getLDCodeName(pLCPolSchema.getInsuYearFlag(), "insuyearflag"));
		
		Element mInsuYear = new Element(InsuYear);
		mInsuYear.setText(String.valueOf(pLCPolSchema.getInsuYear()));

		//Element mYears = new Element(Years);
		//mYears.setText(Double.toString(pLCPolSchema.getYears()));

		Element mPayEndYearFlag = new Element(PayEndYearFlag);
		mPayEndYearFlag.setText(pLCPolSchema.getPayEndYearFlag());
		
		Element mPayEndYearFlagName = new Element("PayEndYearFlagName");
		mPayEndYearFlagName.setText(MidplatUtil.getLDCodeName(pLCPolSchema.getPayEndYearFlag(), "payendyearflag"));

		Element mPayEndYear = new Element(PayEndYear);
		mPayEndYear.setText(String.valueOf(pLCPolSchema.getPayEndYear()));

		// Element mPayEndDate = new Element(PayEndDate); //交费期满日
		// if ( pLCPolSchema.getPayIntv()==0 ) {
		// mPayEndDate.setText("-");
		// } else {
		// mPayEndDate.setText(DateUtil.date10to8(pLCPolSchema.getPayEndDate()));
		// }

		Element mCostIntv = new Element("CostIntv");
		mCostIntv.setText("");

		Element mCostDate = new Element("CostDate");
		mCostDate.setText("");

		Element mPayToDate = new Element("PayToDate");
		mPayToDate.setText((pLCPolSchema.getPaytoDate()));

		Element mPayEndDate = new Element("PayEndDate");
		mPayEndDate.setText((pLCPolSchema.getPayEndDate()));

		Element mGetYearFlag = new Element("GetYearFlag");
		mGetYearFlag.setText(pLCPolSchema.getGetYearFlag());

		Element mGetStartDate = new Element("GetStartDate");
		mGetStartDate.setText((pLCPolSchema.getGetStartDate()));

		String age = "";
		if (pLCPolSchema.getGetYear() > 0)
		{
			age = Integer.toString(pLCPolSchema.getGetYear());
		}
		Element mGetYear = new Element("GetYear");
		mGetYear.setText(age);

		Element mGetIntv = new Element("GetIntv");
		mGetIntv.setText("");

		Element mGetBankCode = new Element("GetBankCode");
		// mGetBankCode .setText(pLCPolSchema.getGetBankCode());

		Element mGetBankAccNo = new Element("GetBankAccNo");
		// mGetBankAccNo.setText(pLCPolSchema.getGetBankAccNo());

		Element mGetAccName = new Element("GetAccName");
		// mGetAccName.setText(pLCPolSchema.getGetAccName());

		Element mAutoPayFlag = new Element("AutoPayFlag");
		mAutoPayFlag.setText(pLCPolSchema.getAutoPayFlag());

		Element mBonusGetModeEle = new Element(BonusGetMode);
		mBonusGetModeEle.setText(pLCPolSchema.getBonusGetMode());

		Element mSubFlag = new Element("SubFlag");
		mSubFlag.setText(pLCPolSchema.getSubFlag());

		Element mFullBonusGetModeEle = new Element(FullBonusGetMode);
		mFullBonusGetModeEle.setText("");

		
		//Element mCashValues = getCashValues(pLCPolSchema);

		Element mAccount = getAccount(pLCPolSchema);

		String mSpecContent = "";

		Element mSpecContentEle = new Element("SpecContent");
		mSpecContentEle.setText(mSpecContent);

		Element mRisk = new Element(Risk);
		mRisk.addContent(mRiskCode);
		mRisk.addContent(mRiskName);
		mRisk.addContent(mMainRiskCode);
		mRisk.addContent(mPolApplyDate);
		mRisk.addContent(mSignDate);
		mRisk.addContent(mCValiDate);
		mRisk.addContent(mInsuEndDate);
		mRisk.addContent(mAmnt);
		mRisk.addContent(mPrem);
		mRisk.addContent(mMult);
		mRisk.addContent(mPayIntv);
		mRisk.addContent(mPayIntvName);
		mRisk.addContent(mPayMode);
		mRisk.addContent(mInsuYearFlag);
		mRisk.addContent(mInsuYearFlagName);
		mRisk.addContent(mInsuYear);
		//mRisk.addContent(mYears);
		mRisk.addContent(mPayEndYearFlag);
		mRisk.addContent(mPayEndYearFlagName);
		mRisk.addContent(mPayEndYear);
		mRisk.addContent(mPayEndDate);
		mRisk.addContent(mCostIntv);
		mRisk.addContent(mCostDate);
		mRisk.addContent(mPayToDate);
		mRisk.addContent(mGetYearFlag);
		mRisk.addContent(mGetStartDate);
		mRisk.addContent(mGetYear);
		mRisk.addContent(mGetIntv);
		mRisk.addContent(mGetBankCode);
		mRisk.addContent(mGetBankAccNo);
		mRisk.addContent(mGetAccName);
		mRisk.addContent(mAutoPayFlag);
		mRisk.addContent(mBonusGetModeEle);
		mRisk.addContent(mSubFlag);
		mRisk.addContent(mFullBonusGetModeEle);
		mRisk.addContent(mAccount);
		mRisk.addContent(mSpecContentEle);
		
		cLogger.debug("Out YbtContQueryBL.getRisk()!");
		return mRisk;
	}

	
	
	/**
	 * *****************************************
	 * 		受益人节点
	 * @param pLCBnfSchema
	 * @return Element
	 * @throws MidplatException
	 */
	private Element getBnf(LCBnfSchema pLCBnfSchema) throws MidplatException
	{
		cLogger.debug("Into YbtContQueryBL.getBnf()...");

		Element mType = new Element(Type);
		mType.setText(pLCBnfSchema.getBnfType());

		Element mGrade = new Element(Grade);
		mGrade.setText(pLCBnfSchema.getBnfGrade());

		Element mName = new Element(Name);
		mName.setText(pLCBnfSchema.getName());

		Element mSex = new Element(Sex);
		mSex.setText(pLCBnfSchema.getSex());
		
		/** <Sex> 受益人性别名称 */
		Element mSexName = new Element("SexName");
		mSexName.setText(getSexName(pLCBnfSchema.getSex()));

		Element mBirthday = new Element(Birthday);
		mBirthday.setText((pLCBnfSchema.getBirthday()));

		
		/** <Age> 受益人生日 */
		Element mAge = new Element("Age");
		mAge.setText(MidplatUtil.calInterval(pLCBnfSchema.getBirthday(),pLCBnfSchema.getMakeDate() , "Y"));
		
		Element mIDType = new Element(IDType);
		mIDType.setText(pLCBnfSchema.getIDType());
		
		/** <IDType> 受益人证件名称 */
		Element mIDTypeName = new Element("IDTypeName");
		mIDTypeName.setText(MidplatUtil.getLDCodeName(pLCBnfSchema.getIDType(), "id_type"));

		Element mIDNo = new Element(IDNo);
		mIDNo.setText(pLCBnfSchema.getIDNo());

		Element mRelaToInsured = new Element(RelaToInsured);
		String sRelaToInsured = pLCBnfSchema.getRelationToInsured();
		mRelaToInsured.setText(pLCBnfSchema.getRelationToInsured());

		/** <IDType> 与被保人关系名称 */
		Element mRelaToInsuredName = new Element("RelaToInsuredName");
		mRelaToInsuredName.setText(MidplatUtil.getLDCodeName(sRelaToInsured, "relation"));
		
		Element mLot = new Element(Lot);
		mLot.setText(String.valueOf((int) (pLCBnfSchema.getBnfLot() * 100))); // 核心的受益比例为0.0-1.0，而标准报文是1-100，在此做转换

		Element mBnf = new Element(Bnf);
		mBnf.addContent(mType);
		mBnf.addContent(mGrade);
		mBnf.addContent(mName);
		mBnf.addContent(mBirthday);
		mBnf.addContent(mAge);
		mBnf.addContent(mSex);
		mBnf.addContent(mSexName);
		mBnf.addContent(mIDType);
		mBnf.addContent(mIDTypeName);
		mBnf.addContent(mIDNo);
		mBnf.addContent(mRelaToInsured);
		mBnf.addContent(mRelaToInsuredName);
		mBnf.addContent(mLot);

		cLogger.debug("Out YbtContQueryBL.getBnf()!");
		return mBnf;
	}
	
	
	


	
	
	private Element getAccount(LCPolSchema mLCPolSchema) throws MidplatException
	{
		cLogger.debug("Into YbtContQueryBL.getAccount()...");
		String mSqlStr = "select AccountFlag from LMRiskApp where RiskCode='" + mLCPolSchema.getRiskCode() + "'";
		String sAccountFlag = new ExeSQL().getOneValue(mSqlStr);
		Element mAccountList = new Element("AccountList");
		
		if (null == sAccountFlag || "N".equals(sAccountFlag))
		{
			return mAccountList;
		}else if(null == sAccountFlag || "Y".equals(sAccountFlag)){
		String mCalSql = "select AccCode,AccRate from LCInsureAcc where ContNo='" + mLCPolSchema.getContNo() + "'" ;
		cLogger.debug("CashValue.CalSql = " + mCalSql);
		
		//增加投资日期标志add by fzg 2012-3-8
		String mAccTimeFlagSql = "select distinct(AccTimeFlag) from LCInsureAcc where ContNo='" + mLCPolSchema.getContNo() + "'" ;
		String accTimeFlag = new ExeSQL().getOneValue(mAccTimeFlagSql);
		if("1".equals(accTimeFlag.trim())){
			accTimeFlag = "投保次日";
		}
		if("2".equals(accTimeFlag.trim())){
			accTimeFlag = "犹豫期后";
		}
		Element mAccTimeFlag = new Element("AccTimeFlag");
		mAccTimeFlag.setText(accTimeFlag);
		mAccountList.addContent(mAccTimeFlag);
		
		
		
		SSRS mSSRS = new ExeSQL().execSQL(mCalSql);
		if(mSSRS.MaxRow == 0){
			System.out.println(mLCPolSchema.getContNo()+"：没有投资账户呢");
		}
		for (int i = 1; i <= mSSRS.MaxRow; i++)
		{
			Element tAccName = new Element(AccName); // 年期(第N年度末)
			tAccName.setText(mSSRS.GetText(i, 1));
			
			Element tAccRate = new Element(AccRate); // 现金价值
			tAccRate.setText(mSSRS.GetText(i, 2));
			
			Element tAccount = new Element(Account);
			tAccount.addContent(tAccName);
			tAccount.addContent(tAccRate);
			
			mAccountList.addContent(tAccount);
		}
		}
		cLogger.debug("Out YbtContQueryBL.getCashValues()!");
		
		return mAccountList;
	}

	


	
	
	
	/**
	 * 根据pFlag和pMessage，生成简单的标准返回报文。
	 */
	private Document getSimpOutXml(String pFlag, String pMessage)
	{
		Element mFlag = new Element(Flag);
		mFlag.addContent(pFlag);

		Element mDesc = new Element(Desc);
		mDesc.addContent(pMessage);

		Element mHeadEle = new Element(Head);
		mHeadEle.addContent(mFlag);
		mHeadEle.addContent(mDesc);

		Element mTranData = new Element(TranData);
		mTranData.addContent(mHeadEle);

		return new Document(mTranData);
	}

	

	/**
	 * *****************************************
	 * 		现金价值节点
	 * @param pLCBnfSchema
	 * @return Element
	 * @throws MidplatException
	 */
//	private Element getCashValues(LCPolSchema pLCPolSchema) throws MidplatException
//	{
//		cLogger.debug("Into YbtContQueryBL.getCashValues()...");
//		Element mCashValues = new Element(CashValues);
//		String mSqlStr = "select CalSql from LMCalMode where RiskCode='" + pLCPolSchema.getRiskCode()
//				+ "' and Type='X'";
//		String mCalSql = new ExeSQL().getOneValue(mSqlStr);
//		cLogger.debug("CashValue.?CalSql? = " + mCalSql);
//
//		if (null == mCalSql || "".equals(mCalSql))
//		{
//			return mCashValues;
//		}
//		mCalSql = mCalSql.replaceAll("\\?RiskCode\\?", String.valueOf(pLCPolSchema.getRiskCode()))
//						.replaceAll("\\?AXARiskCode\\?", String.valueOf(pLCPolSchema.getRiskAlias()))
//						.replaceAll("\\?InsuredSex\\?", pLCPolSchema.getInsuredSex())
//						.replaceAll("\\?InsuredAge\\?", String.valueOf(pLCPolSchema.getInsuredAppAge()))
//						.replaceAll("\\?PayIntv\\?", String.valueOf(pLCPolSchema.getPayIntv()))
//						.replaceAll("\\?PayEndYear\\?", String.valueOf(pLCPolSchema.getPayEndYear()))
//						.replaceAll("\\?PayEndYearFlag\\?", pLCPolSchema.getPayEndYearFlag())
//						.replaceAll("\\?Amnt\\?", String.valueOf(pLCPolSchema.getAmnt()));
//		cLogger.debug("CashValue.CalSql = " + mCalSql);
//		
//		SSRS mSSRS = new ExeSQL().execSQL(mCalSql);
//		if(mSSRS.MaxRow == 0){
//			System.out.println("没有对应的现价呢");
//		}
//		for (int i = 1; i <= mSSRS.MaxRow; i++)
//		{
//			Element tYear = new Element("EndYear"); // 年期(第N年度末)
//			tYear.setText(mSSRS.GetText(i, 1));
//
//			Element tCash = new Element(Cash); // 现金价值
//			tCash.setText(String.valueOf(NumberUtil.yuanToFen(mSSRS.GetText(i, 2))));
//		
//			Element tCashValue = new Element(CashValue);
//			tCashValue.addContent(tYear);
//			tCashValue.addContent(tCash);
//
//			mCashValues.addContent(tCashValue);
//		}
//
//		cLogger.debug("Out YbtContQueryBL.getCashValues()!");
//		
//		return mCashValues;
//	}	
	
	
	
	
	
	
	/**
	 * **********************************************************
	 * 		主函数	用于测试
	 * @param args
	 */
	public static void main(String args [] ){
			
			/*try {
				GlobalInput cGlobalInput = new GlobalInput();
				cGlobalInput.Operator="YBT";
				cGlobalInput.AgentCom="00000000";
				cGlobalInput.ComCode = "86";
				cGlobalInput.ManageCom = "86";
				
				String inStdPath = "D:/YBT_WORKSPACE/金盛/报文/inStdDoc.xml";
				InputStream inStdIO;
				inStdIO = new FileInputStream(inStdPath);
				Document inStdDoc = JdomUtil.build(inStdIO);
				Element mEle = new Element("Test");
				NewContInput contInput = new NewContInput(mEle);
				Document retDoc = contInput.service(inStdDoc);
				JdomUtil.print(retDoc);
				System.out.println("Schema");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}*/
		

		YbtContQueryBL bl = new YbtContQueryBL("301-1291121");
		try {
			JdomUtil.print(bl.deal());
		} catch (MidplatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	public static String getSexName (String sex){
		String SexName = "--";
		
		if(sex == null){
			return SexName;
		}else if(sex.equals("M")){
			SexName = "男";
		}else if(sex.equals("F")){
			SexName = "女";
		}
		
		return SexName;
		
	}
	
}
