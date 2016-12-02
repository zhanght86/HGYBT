package com.sinosoft.lis.ybt;

import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.ybt.YbtContQueryBL;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.exception.MidplatException;

//update by fzg 2012-2-21
public class queryCont {
	public String bnfFlag1 = "N";
	public String bnfFlag2 = "N";
	public String bnfFlag3 = "N";
	
	//新增附加险节点显示的判断
	public String riskFlag1 = "N";
	public String riskFlag2 = "N";
	
	public String sProposalContNo = "";
	public	String sContNo = ""; 
	public	String sPrtNo = ""; 
	public	String sContState = "";
	public	String sBalanceState = "";
	public	String sTranNo = "";
	public	String sTranCom = ""; 
	public	String sTranComName = "";
	public	String sBankNode = "";
	public	String sOperator = "";
	//代理人信息
	public	String sAreaNo = ""; 
	public	String sAreaName  ="";
	public	String sCityNo ="";
	public	String sCityName ="";
	public	String sManageCom ="";
	public	String sManageComName ="";
	public	String sManageComShortName ="";
	public	String sAgentCom ="";
	public	String sAgentComName ="";
	public	String sAgentCode ="";
	public	String sAgentCodeName ="";
	public	String sUnitCode ="";
	//被保人信息
	public	String sInsuredName ="";
	public	String sInsuredSex ="";
	public	String sInsuredBirthDay ="";
	public	String sInsuredAge ="";
	public	String sInsuredIDType ="";
	public	String sInsuredIDTypeName ="";
	public	String sInsuredIDNo ="";
	public	String sInsuredPhone ="";
	public	String sInsuredMobile ="";
	public	String sInsuredEMail ="";
	public	String sInsuredAddress ="";
	public	String sInsuredZipCode ="";
	public	String sInsuredHomeAddress ="";
	public	String sInsuredHomeZipCode ="";
	public	String sInsuredJobeCode ="";

//投保人信息
	public	String sAppntName ="";
	public	String sAppntSex ="";
	public	String sAppntBirthDay ="";
	public	String sAppntAge ="";
	public	String sAppntIDType ="";
	public	String sAppntIDTypeName ="";
	public	String sAppntIDNo ="";
	public	String sAppntPhone ="";
	public	String sAppntMobile ="";
	public	String sAppntEMail ="";
	public	String sAppntAddress ="";
	public	String sAppntZipCode ="";
	public	String sAppntHomeAddress ="";
	public	String sAppntHomeZipCode ="";
	public	String sAppntJobeCode ="";
	public	String sAppntRealToInsured ="";


		//主险险种信息
	public	String sRiskCode ="";
	public	String sRiskCodeName ="";
	public	String sPayIntv ="";
	public	String sPayIntvName ="";
	public	String sInsuYearFlag ="";
	public	String sInsuYearFlagName ="";
	public	String sInsuYear ="";
	public	String sPayEndYearFlag ="";
	public	String sPayEndYearFlagName ="";
	public	String sPayEndYear ="";

	public	String sMult ="";
	public	String sAmnt ="";
	public	String sPrem ="";
	public	String sMainPrem ="";
	public	String sAddPrem ="";
	public	String sFirstAddPrem ="";
	
	//add by fzg 2012-2-20
	//附加险险种信息1
	public	String sRiskCode1 ="";
	public	String sRiskCodeName1 ="";
	public	String sInsuYearFlag1 ="";
	public	String sInsuYearFlagName1 ="";
	public	String sInsuYear1 ="";
	public	String sPayEndYearFlag1 ="";
	public	String sPayEndYearFlagName1 ="";
	public	String sPayEndYear1 ="";
	public	String sAmnt1 ="";
	public	String sPrem1 ="";
	//附加险险种信息2
	public	String sRiskCode2 ="";
	public	String sRiskCodeName2 ="";
	public	String sInsuYearFlag2 ="";
	public	String sInsuYearFlagName2 ="";
	public	String sInsuYear2 ="";
	public	String sPayEndYearFlag2 ="";
	public	String sPayEndYearFlagName2 ="";
	public	String sPayEndYear2 ="";
	public	String sAmnt2 ="";
	public	String sPrem2 ="";
	
 //投资账户信息 
	public	String sU1ZY = "";
	public	String sU2WJ ="";
	public	String sU3AX ="";
	//add by fzg 2012-2-21
	public  String sU6JQ ="";
	public  String sU8HX ="";
	//投资日期标志
	public  String sAccTimeFlag = "";
	
	//账户信息
	public	String sAccName ="";
	public	String sAccNo ="";
  	//受益人信息1 
	public	String sBnfName ="";
	public	String sBnfSex ="";
	public	String sBnfBirthDay ="";
	public	String sBnfAge ="";
	public	String sBnfIDType ="";
	public	String sBnfIDTypeName ="";
	public	String sBnfIDNo ="";
	public	String sBnfGrade ="";
	public	String sBnfLot ="";
	public	String sBnfRealToInsured ="";
	public	String sBnfRealToInsuredName ="";
	//受益人信息2
	public	String sBnfName2 ="";
	public	String sBnfSex2 ="";
	public	String sBnfBirthDay2 ="";
	public	String sBnfAge2 ="";
	public	String sBnfIDType2 ="";
	public	String sBnfIDTypeName2 ="";
	public	String sBnfIDNo2 ="";
	public	String sBnfGrade2 ="";
	public	String sBnfLot2 ="";
	public	String sBnfRealToInsured2 ="";
	public	String sBnfRealToInsured2Name ="";
  
		//受益人信息3
	public	String sBnfName3 ="";
	public	String sBnfSex3 ="";
	public	String sBnfBirthDay3 ="";
	public	String sBnfAge3 ="";
	public	String sBnfIDType3 ="";
	public	String sBnfIDTypeName3 ="";
	public	String sBnfIDNo3 ="";
	public	String sBnfGrade3 ="";
	public	String sBnfLot3 ="";
	public	String sBnfRealToInsured3 ="";
	public	String sBnfRealToInsured3Name ="";

	public queryCont (String contno){
		this.getCont(contno);
	}
	
	public void getCont(String contno){

		YbtContQueryBL ContQuery = new YbtContQueryBL(contno);
		Document query = null;
		try {
			query = ContQuery.deal();
		} catch (MidplatException e) {
			
			e.printStackTrace();
		}
		
	JdomUtil.print(query);
		
		Element mBody = query.getRootElement().getChild("Body");
		Element mAppnt = mBody.getChild("Appnt");
		Element mInsured = mBody.getChild("Insured");
		
			//保单信息
			this.sProposalContNo = mBody.getChildText("ProposalPrtNo");
			this.sContNo = mBody.getChildText("ContNo");
			this.sPrtNo = mBody.getChildText("PrtNo");
			this.sContState = mBody.getChildText("ContState");
			this.sBalanceState = mBody.getChildText("BalanceState");
			this.sTranNo = mBody.getChildText("TranNo");
			this.sTranCom = mBody.getChildText("TranCom");
			this.sTranComName = mBody.getChildText("TranComName");
			this.sBankNode = mBody.getChildText("AxaNodeMap");
			this.sOperator = mBody.getChildText("Operator");

			//代理人信息
			this.sAreaNo = mBody.getChildText("AreaNo");
			this.sAreaName = mBody.getChildText("AreaName");
			this.sCityNo = mBody.getChildText("CityNo");
			this.sCityName = mBody.getChildText("CityName");
			this.sManageCom = mBody.getChildText("ComCode");
			this.sManageComName = mBody.getChildText("ComName");
			this.sManageComShortName = mBody.getChildText("ComShortName");
			this.sAgentCom = mBody.getChildText("AgentCom");
			this.sAgentComName = mBody.getChildText("AgentComName");
			this.sAgentCode = mBody.getChildText("AgentCode");
			this.sAgentCodeName = mBody.getChildText("AgentName");
			this.sUnitCode = mBody.getChildText("AgentUnitCode");

			//被保人信息
			this.sInsuredName = mInsured.getChildText("Name");
			this.sInsuredSex =  mInsured.getChildText("SexName");;
			this.sInsuredBirthDay = mInsured.getChildText("Birthday");
			this.sInsuredAge =  mInsured.getChildText("Age");
			this.sInsuredIDType =  mInsured.getChildText("IDType");
			this.sInsuredIDTypeName =  mInsured.getChildText("IDTypeName");
			this.sInsuredIDNo = mInsured.getChildText("IDNo");
			this.sInsuredPhone = mInsured.getChildText("Phone");
			this.sInsuredMobile = mInsured.getChildText("Mobile");
			this.sInsuredEMail = mInsured.getChildText("Email");
			if(this.sInsuredEMail == null){
				this.sInsuredEMail="";
			}
			this.sInsuredAddress = mInsured.getChildText("Address");
			this.sInsuredZipCode = mInsured.getChildText("ZipCode");
			this.sInsuredJobeCode = mInsured.getChildText("JobCode");
			if(this.sInsuredJobeCode == null){
				this.sInsuredJobeCode="";
			}
			//投保人信息
			this.sAppntName = mAppnt.getChildText("Name");
			this.sAppntSex = mAppnt.getChildText("SexName");;
			this.sAppntBirthDay = mAppnt.getChildText("Birthday");
		
			this.sAppntAge = mAppnt.getChildText("Age");
			this.sAppntIDType =  mAppnt.getChildText("IDType");
			this.sAppntIDTypeName =  mAppnt.getChildText("IDTypeName");
			this.sAppntIDNo =  mAppnt.getChildText("IDNo");
			this.sAppntPhone = mAppnt.getChildText("Phone");
			this.sAppntMobile = mAppnt.getChildText("Mobile"); 
			this.sAppntEMail =  mAppnt.getChildText("Email");
			if(this.sAppntEMail == null){
				this.sAppntEMail="";
			}
			this.sAppntAddress = mAppnt.getChildText("Address");
			this.sAppntZipCode = mAppnt.getChildText("ZipCode");
			this.sAppntJobeCode = mAppnt.getChildText("JobCode");
			if(this.sAppntJobeCode == null){
				this.sAppntJobeCode="";
			}
			this.sAppntRealToInsured = mAppnt.getChildText("RelaToInsuredName");
		
			//投资账户信息 
			this.sU1ZY = "";
			this.sU2WJ = "";
			this.sU3AX = "";
			this.sU6JQ = "";
			this.sU8HX = "";
			this.sAccTimeFlag = "";
			//主险险种信息
			this.sRiskCode ="";
			this.sRiskCodeName ="";
			this.sPayIntv ="";
			this.sPayIntvName ="";
			this.sInsuYearFlag ="";
			this.sInsuYearFlagName ="";
			this.sInsuYear ="";
			this.sPayEndYearFlag ="";
			this.sPayEndYearFlagName ="";
			this.sPayEndYear ="";

			this.sMult ="";
			this.sAmnt ="";
			this.sPrem ="";
			this.sMainPrem ="";
			this.sAddPrem ="";
			this.sFirstAddPrem ="";
			//附加险信息1
			this.sRiskCode1 = "";
			this.sRiskCodeName1 = "";
			this.sInsuYearFlag1 = "";
			this.sInsuYear1 = "";
			this.sInsuYearFlagName1 = "";
			this.sPayEndYear1 = "";
			this.sPayEndYearFlag1 = "";
			this.sPayEndYearFlagName1 = "";
			this.sAmnt1 = "";
			this.sPrem1 = "";
			
			//附加险信息2
			this.sRiskCode2 = "";
			this.sRiskCodeName2 = "";
			this.sInsuYearFlag2 = "";
			this.sInsuYear2 = "";
			this.sInsuYearFlagName2 = "";
			this.sPayEndYear2 = "";
			this.sPayEndYearFlag2 = "";
			this.sPayEndYearFlagName2 = "";
			this.sAmnt2 = "";
			this.sPrem2 = "";
//			JdomUtil.print(mBody);
			
			/** 循环获得险种信息 */
			List mRiskList = mBody.getChildren("Risk");
			int mSize = mRiskList.size();
			for (int i = 0; i < mSize; i++) {
				Element tRiskEle = (Element) mRiskList.get(i);
				Element mRisk = tRiskEle;
				LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
				tLMRiskAppDB.setRiskCode(tRiskEle.getChildText("RiskCode"));
				if (!tLMRiskAppDB.getInfo()) {
					System.out.println();
				}

				if(tRiskEle.getChildText("RiskCode").equals(tRiskEle.getChildText("MainRiskCode"))){					
					String tAccountFlag = tLMRiskAppDB.getAccountFlag();
				
					if (tAccountFlag != null && "Y".equals(tAccountFlag)) {
						//取投资日期标志
						this.sAccTimeFlag = tRiskEle.getChild("AccountList").getChildText("AccTimeFlag");
						List mAccountList = tRiskEle.getChild("AccountList").getChildren("Account");
						int aSize = mAccountList.size();
						for (int j = 0; j < aSize; j++) {
							Element account = (Element) mAccountList.get(j);
							String sAccName = account.getChildText("AccName");
							String sAccRate = account.getChildText("AccRate");
							if(sAccName != null && "U1ZY".equals(sAccName)){
								this.sU1ZY = sAccRate;
							}
							if(sAccName != null && "U2WJ".equals(sAccName)){
								this.sU2WJ = sAccRate;
							}
							if(sAccName != null && "U3AX".equals(sAccName)){
								this.sU3AX = sAccRate;
							}
							if(sAccName != null && "U6JQ".equals(sAccName)){
								this.sU6JQ = sAccRate;
							}
							if(sAccName != null && "U8HX".equals(sAccName)){
								this.sU8HX = sAccRate;
							}
						}
					}
					//主险险种信息
					this.sRiskCode = mRisk.getChildText("RiskCode");
					this.sRiskCodeName = mRisk.getChildText("RiskName");
					this.sPayIntv = mRisk.getChildText("PayIntv");
					this.sPayIntvName = mRisk.getChildText("PayIntvName");
					this.sInsuYearFlag = mRisk.getChildText("InsuYearFlag");
					this.sInsuYearFlagName = mRisk.getChildText("InsuYearFlagName");
					this.sInsuYear = mRisk.getChildText("InsuYear");
					 if ("5".equals(this.sInsuYearFlag)) {
				          this.sInsuYear = "终身";
				        }
				        if ("1".equals(this.sInsuYearFlag)) {
				          this.sInsuYear = "至" + this.sInsuYear + "周岁";
				        }
				        if ("2".equals(this.sInsuYearFlag)) {
				          this.sInsuYear += "年";
				        }
				        this.sPayEndYearFlag = mRisk.getChildText("PayEndYearFlag");
				        this.sPayEndYearFlagName = mRisk.getChildText("PayEndYearFlagName");
				        this.sPayEndYear = mRisk.getChildText("PayEndYear");

				        if ("6".equals(this.sPayEndYearFlag)) {
				          this.sPayEndYear = "终身";
				        }
				        if ("5".equals(this.sPayEndYearFlag)) {
				          this.sPayEndYear = "--";
				        }
				        if ("1".equals(this.sPayEndYearFlag)) {
				          this.sPayEndYear = "至" + this.sPayEndYear + "周岁";
				        }
				        if ("2".equals(this.sPayEndYearFlag)) {
				          this.sPayEndYear += "年";
				        }

				        this.sMult = "";
					this.sAmnt = mRisk.getChildText("Amnt");
					if(this.sAmnt.equals("0.0")||this.sAmnt.equals("0")||this.sAmnt.equals("0.00")){
						this.sAmnt = "";
					}				  
					this.sPrem = mBody.getChildText("Prem");
					this.sMainPrem = mRisk.getChildText("Prem");
					this.sAddPrem = mBody.getChildText("AddPrem");
					this.sFirstAddPrem = mBody.getChildText("FirstAddPrem");
				}else if (i == 1) {
				        this.riskFlag1 = "Y";
				        this.sRiskCode1 = mRisk.getChildText("RiskCode");
				        this.sRiskCodeName1 = mRisk.getChildText("RiskName");
				        this.sInsuYearFlag1 = mRisk.getChildText("InsuYearFlag");
				        this.sInsuYearFlagName1 = mRisk.getChildText("InsuYearFlagName");
				        this.sInsuYear1 = mRisk.getChildText("InsuYear");

				        if ("5".equals(this.sInsuYearFlag1)) {
				          this.sInsuYear1 = "终身";
				        }
				        if ("1".equals(this.sInsuYearFlag1)) {
				          this.sInsuYear1 = "至" + this.sInsuYear1 + "周岁";
				        }
				        if ("2".equals(this.sInsuYearFlag1)) {
				          this.sInsuYear1 += "年";
				        }
				        this.sPayEndYearFlag1 = mRisk.getChildText("PayEndYearFlag");
				        this.sPayEndYearFlagName1 = mRisk.getChildText("PayEndYearFlagName");
				        this.sPayEndYear1 = mRisk.getChildText("PayEndYear");

				        if ("6".equals(this.sPayEndYearFlag1)) {
				          this.sPayEndYear1 = "终身";
				        }
				        if ("5".equals(this.sPayEndYearFlag1)) {
				          this.sPayEndYear1 = "--";
				        }
				        if ("1".equals(this.sPayEndYearFlag1)) {
				          this.sPayEndYear1 = "至" + this.sPayEndYear1 + "周岁";
				        }
				        if ("2".equals(this.sPayEndYearFlag1)) {
				          this.sPayEndYear1 += "年";
				        }
				        this.sAmnt1 = mRisk.getChildText("Amnt");
				        if ((this.sAmnt1.equals("0.0")) || (this.sAmnt1.equals("0")) || (this.sAmnt1.equals("0.00"))) {
				          this.sAmnt1 = "";
				        }
				        this.sPrem1 = mRisk.getChildText("Prem");
				      }
				      else if (i == 2) {
				        this.riskFlag2 = "Y";
				        this.sRiskCode2 = mRisk.getChildText("RiskCode");
				        this.sRiskCodeName2 = mRisk.getChildText("RiskName");
				        this.sInsuYearFlag2 = mRisk.getChildText("InsuYearFlag");
				        this.sInsuYearFlagName2 = mRisk.getChildText("InsuYearFlagName");
				        this.sInsuYear2 = mRisk.getChildText("InsuYear");

				        if ("5".equals(this.sInsuYearFlag2)) {
				          this.sInsuYear2 = "终身";
				        }
				        if ("1".equals(this.sInsuYearFlag2)) {
				          this.sInsuYear2 = "至" + this.sInsuYear2 + "周岁";
				        }
				        if ("2".equals(this.sInsuYearFlag2)) {
				          this.sInsuYear2 += "年";
				        }
				        this.sPayEndYearFlag2 = mRisk.getChildText("PayEndYearFlag");
				        this.sPayEndYearFlagName2 = mRisk.getChildText("PayEndYearFlagName");
				        this.sPayEndYear2 = mRisk.getChildText("PayEndYear");

				        if ("6".equals(this.sPayEndYearFlag2)) {
				          this.sPayEndYear2 = "终身";
				        }
				        if ("5".equals(this.sPayEndYearFlag2)) {
				          this.sPayEndYear2 = "--";
				        }
				        if ("1".equals(this.sPayEndYearFlag2)) {
				          this.sPayEndYear2 = "至" + this.sPayEndYear2 + "周岁";
				        }
				        if ("2".equals(this.sPayEndYearFlag2)) {
				          this.sPayEndYear2 += "年";
				        }
				        this.sAmnt2 = mRisk.getChildText("Amnt");
				        if ((this.sAmnt2.equals("0.0")) || (this.sAmnt2.equals("0")) || (this.sAmnt2.equals("0.00"))) {
				          this.sAmnt2 = "";
				        }
				        this.sPrem2 = mRisk.getChildText("Prem");
				      }
								
				
			}
			

			//账户信息
			this.sAccName = mBody.getChildText("AccName");
			this.sAccNo = mBody.getChildText("AccNo");

			//受益人信息1 
			this.sBnfName = "";
			this.sBnfSex = "";
			this.sBnfBirthDay = "";
			this.sBnfAge = "";
			this.sBnfIDType = "";
			this.sBnfIDTypeName = "";
			this.sBnfIDNo = "";
			this.sBnfGrade = "";
			this.sBnfLot = "";
			this.sBnfRealToInsured = "";
			this.sBnfRealToInsuredName = "";

			//受益人信息2
			this.sBnfName2 = "";
			this.sBnfSex2 = "";
			this.sBnfBirthDay2 = "";
			this.sBnfAge2 = "";
			this.sBnfIDType2 = "";
			this.sBnfIDTypeName2 = "";
			this.sBnfIDNo2 = "";
			this.sBnfGrade2 = "";
			this.sBnfLot2 = "";
			this.sBnfRealToInsured2 = "";
			this.sBnfRealToInsured2Name = "";

			//受益人信息3
			this.sBnfName3 = "";
			this.sBnfSex3 = "";
			this.sBnfBirthDay3 = "";
			this.sBnfAge3 = "";
			this.sBnfIDType3 = "";
			this.sBnfIDTypeName3 = "";
			this.sBnfIDNo3 = "";
			this.sBnfGrade3 = "";
			this.sBnfLot3 = "";
			this.sBnfRealToInsured3 = "";
			this.sBnfRealToInsured3Name = "";
			
			/** 受益人信息 */
			List mBnfList = mBody.getChildren("Bnf");
			int bSize = mBnfList.size();
			LCBnfSet mLCBnfSet = new LCBnfSet();
			for (int i = 0; i < bSize; i++) {
				Element bnf = (Element) mBnfList.get(i);
				if(i==0){
					bnfFlag1 = "Y"; 
					
					sBnfName = bnf.getChildText("Name");
					if(sBnfName == null){
						this.sBnfName = "";
					}

					sBnfSex = bnf.getChildText("SexName");
					if(sBnfSex == null){
						this.sBnfSex = "";
					}
					sBnfBirthDay = bnf.getChildText("Birthday");		
					if(sBnfBirthDay == null){
						this.sBnfBirthDay = "";
					}
					sBnfAge = bnf.getChildText("Age");
					if(sBnfAge == null){
						this.sBnfAge = "";
					}
					sBnfIDType = bnf.getChildText("IDType");
					if(sBnfIDType == null){
						this.sBnfIDType = "";
					}
					sBnfIDTypeName = bnf.getChildText("IDTypeName");
					if(sBnfIDTypeName == null){
						this.sBnfIDTypeName = "";
					}
					sBnfIDNo = bnf.getChildText("IDNo");
					if(sBnfIDNo == null){
						this.sBnfIDNo = "";
					}
					sBnfGrade = bnf.getChildText("Grade");
					if(sBnfGrade == null){
						this.sBnfGrade = "";
					}
					sBnfLot = bnf.getChildText("Lot");
					if(sBnfLot == null){
						this.sBnfLot = "";
					}
					sBnfRealToInsured = bnf.getChildText("RelaToInsuredName");
					System.out.println("sBnfRealToInsured"+sBnfRealToInsured);
					if(sBnfRealToInsured == null){
						this.sBnfRealToInsured = "";
					}
					
				}
				else if(i==1){
					bnfFlag2 = "Y";
					sBnfName2 = bnf.getChildText("Name");
					sBnfSex2 = bnf.getChildText("SexName");
					sBnfBirthDay2 = bnf.getChildText("Birthday");
					System.out.println("sBnfBirthDay2" + sBnfBirthDay2);
					sBnfAge2 = bnf.getChildText("Age");
					sBnfIDType2 = bnf.getChildText("IDType");
					sBnfIDTypeName2 = bnf.getChildText("IDTypeName");
					sBnfIDNo2 = bnf.getChildText("IDNo");
					sBnfGrade2 = bnf.getChildText("Grade");
					sBnfLot2 = bnf.getChildText("Lot");
					sBnfRealToInsured2= bnf.getChildText("RelaToInsuredName");
					
				}
				else if(i==2){
					bnfFlag3 = "Y";
					sBnfName3 = bnf.getChildText("Name");
					sBnfSex3 = bnf.getChildText("SexName");
					sBnfBirthDay3 = bnf.getChildText("Birthday");
					System.out.println("sBnfBirthDay3" + sBnfBirthDay3);
					sBnfAge3 = bnf.getChildText("Age");
					sBnfIDType3 = bnf.getChildText("IDType");
					sBnfIDTypeName3 = bnf.getChildText("IDTypeName");
					sBnfIDNo3 = bnf.getChildText("IDNo");
					sBnfGrade3 = bnf.getChildText("Grade");
					sBnfLot3 = bnf.getChildText("Lot");
					sBnfRealToInsured3= bnf.getChildText("RelaToInsuredName");
					System.out.println("sBnfRealToInsured3" + sBnfRealToInsured3);
				}
			}
	}
	
}
