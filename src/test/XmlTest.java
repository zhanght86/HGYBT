package test;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;

public class XmlTest {
	public static Document build(){
		Element mPolicyInfo=new Element("PolicyInfo");
		
		Element mProposalPrtNo=new Element("ProposalPrtNo");
		Element mContNo=new Element("ContNo");
		Element mSumPrem=new Element("SumPrem");
		Element mAddPrem=new Element("AddPrem");
		Element mCustomerNo=new Element("CustomerNo");
		Element mPolapplyDate=new Element("PolapplyDate");
		Element mPayIntv=new Element("PayIntv");
		Element mBankCode=new Element("BankCode");
		Element mAccName=new Element("AccName");
		Element mAccNo=new Element("AccNo");
		Element mBankName=new Element("BankName");
		Element mGetIntv=new Element("GetIntv");
		Element mGetBankCode=new Element("GetBankCode");
		Element mGetAccName=new Element("GetAccName");
		Element mGetAccNo=new Element("GetAccNo");
		Element mGetBankName=new Element("GetBankName");
		Element mRelationToAppnt=new Element("RelationToAppnt");
		mPolicyInfo.addContent(mProposalPrtNo).addContent(mContNo).addContent(mSumPrem).addContent(mAddPrem).addContent(mCustomerNo).addContent(mPolapplyDate)
		.addContent(mPayIntv).addContent(mBankCode).addContent(mAccName).addContent(mAccNo).addContent(mBankName).addContent(mGetIntv).addContent(mGetBankCode)
		.addContent(mGetAccName).addContent(mGetAccNo).addContent(mGetBankName).addContent(mRelationToAppnt);
		
		Element mAppnt=new Element("Appnt"); 
		Element mName=new Element("Name");
		Element mSex=new Element("Sex");
		Element mBirthday=new Element("Birthday");
		Element mIDType=new Element("IDType");
		Element mIDNo=new Element("IDNo");
		Element mAddress=new Element("Address");
		Element mPostAddress=new Element("PostAddress");
		Element mZipCode=new Element("ZipCode");
		Element mMobile=new Element("Mobile");
		Element mPhone=new Element("Phone");
		Element mJobCode=new Element("JobCode");
		Element mJob=new Element("Job");
		mAppnt.addContent(mName).addContent(mSex).addContent(mBirthday).addContent(mIDType).addContent(mIDNo).addContent(mAddress).addContent(mPostAddress)
		.addContent(mZipCode).addContent(mMobile).addContent(mPhone).addContent(mJobCode).addContent(mJob);
		mPolicyInfo.addContent(mAppnt);
		
		Element mInsured=new Element("Insured");
		Element eName=new Element("Name");
		Element eSex=new Element("Sex");
		Element eBirthday=new Element("Birthday");
		Element eIDType=new Element("IDType");
		Element eIDNo=new Element("IDNo");
		Element eAddress=new Element("Address");
		Element ePostAddress=new Element("PostAddress");
		Element eZipCode=new Element("ZipCode");
		Element eMobile=new Element("Mobile");
		Element ePhone=new Element("Phone");
		Element eJobCode=new Element("JobCode");
		Element eJob=new Element("Job");
		mInsured.addContent(eName).addContent(eSex).addContent(eBirthday).addContent(eIDType).addContent(eIDNo).addContent(eAddress).addContent(ePostAddress)
		.addContent(eZipCode).addContent(eMobile).addContent(ePhone).addContent(eJobCode).addContent(eJob);
		mPolicyInfo.addContent(mInsured);
		
		Element mBnfs=new Element("Bnfs");
		Element mBnfCount=new Element("BnfCount");
		mBnfs.addContent(mBnfCount);
		Element mBnf=new Element("Bnf");
		Element mBnfNo=new Element("BnfNo");
		Element mType=new Element("Type");
		Element mGrade=new Element("Grade");
		Element bName=new Element("Name");
		Element bSex=new Element("Sex");
		Element bBirthday=new Element("Birthday");
		Element bIDType=new Element("IDType");
		Element bIDNo=new Element("IDNo");
		Element mRelaToInsured=new Element("RelaToInsured");
		Element mLot=new Element("Lot");
		mBnf.addContent(mBnfNo).addContent(mType).addContent(mGrade).addContent(bName).addContent(bSex).addContent(bBirthday).addContent(bIDType).addContent(bIDNo)
		.addContent(mRelaToInsured).addContent(mLot);
		mPolicyInfo.addContent(mBnfs);
		
		mBnfs.addContent(mBnf);
		Element mRisks=new Element("Risks");
		mPolicyInfo.addContent(mRisks);
		Element mRisk=new Element("Risk");
		mRisks.addContent(mRisk);
		Element mRiskCode=new Element("RiskCode");
		Element mMainRiskCode=new Element("MainRiskCode");
		Element mRiskName=new Element("RiskName");
		Element mMainRiskFlag=new Element("MainRiskFlag");
		Element mAmnt=new Element("Amnt");
		Element mPrem=new Element("Prem");
		Element mMult=new Element("Mult");
		Element mInsuYearFlag=new Element("InsuYearFlag");
		Element mInsuYear=new Element("InsuYear");
		Element mPayEndYearFlag=new Element("PayEndYearFlag");
		Element mPayEndYear=new Element("PayEndYear");
		Element mBonusGetMode=new Element("BonusGetMode");
		Element mAutoPayFlag=new Element("AutoPayFlag");
		Element mAccRate=new Element("AccRate");
		Element mAutoRenewFlag=new Element("AutoRenewFlag");
		mRisk.addContent(mRiskCode).addContent(mMainRiskCode).addContent(mRiskName).addContent(mMainRiskFlag).addContent(mAmnt).addContent(mPrem).addContent(mMult)
		.addContent(mInsuYearFlag).addContent(mInsuYear).addContent(mPayEndYearFlag).addContent(mPayEndYear).addContent(mBonusGetMode).addContent(mAutoPayFlag).addContent(mAccRate)
		.addContent(mAutoRenewFlag);
	
		return new Document(mPolicyInfo);
	}
	public static void main(String[] args) {
		System.out.println(JdomUtil.toStringFmt(build()));
	}
}
