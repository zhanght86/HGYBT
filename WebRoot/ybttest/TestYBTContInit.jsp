<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<% 
	//程序名称：TestYBTContInit.jsp
	//程序功能：工行新单测试 
	//创建日期：2014-11-18 11:22:33
	//创建人  ：李路
	//更新记录：  更新人    更新日期     更新原因/内容
%>
<%  
	PubFun1 pubfun = new PubFun1();
	String strTransExeDate = DateUtil.getCur10Date();
	String strTransRefGUID = pubfun.CreateMaxNo("TransNo",12);
	String strHOAppFormNumber = "2104141"+pubfun.CreateMaxNo("PrtNo",8);
	String strProviderFormNumber = "0101141"+pubfun.CreateMaxNo("ContNo",8);
%>
<script language="JavaScript">
function initForm()
{
	try 
	{
		initInputBox();
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->InitForm 函数中发生异常:初始化界面错误!");
	}
}

function initBox()
{
try { 
//交易信息
	//银行端交易日期
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//交易流水号
	fm.TransRefGUID.value = '<%=strTransRefGUID%>';
	//地区代码
	fm.RegionCode.value = '';
	//网点代码
	fm.Branch.value = '';
	//柜员代码
	fm.Teller.value = '';
	//投保日期 
	fm.SubmissionDate.value = '<%=strTransExeDate%>';
	//投保书号
	fm.HOAppFormNumber.value = '<%=strHOAppFormNumber%>';
	//保单印刷号
	fm.ProviderFormNumber.value = '<%=strProviderFormNumber%>';
   
//投保人信息
	//投保人姓名
	fm.AppFullName.value = '';
	//投保人性别
	fm.AppGender.value = '';
	//投保人证件类型
	fm.AppGovtIDTC.value = '';
	//投保人证件号码
	fm.AppGovtID.value = '';
	//投保人生日
	fm.AppBirthDate.value = '';
	//投保人电子邮箱
	fm.AppAddrLine.value = '';
	//投保人通讯地址	
	fm.AppLine1.value = '';
	//投保人邮政编码
	fm.AppZip.value = '';
	//投保人家庭电话
	fm.AppDialNumber1.value = '';
	//投保人移动电话
	fm.AppDialNumber3.value = '';
	//投保人与被报人的关系
	fm.AppToInsRelation.value = '';
	
//被保人信息
	//被保人姓名
	fm.InsFullName.value = '';
	//被保人性别
	fm.InsGender.value = ''; 
	fm.InsGenderh.value = '';
	//被保人证件类型
	fm.InsGovtIDTC.value = '';
	//被保人证件号码
	fm.InsGovtID.value = '';
	//被保人生日
	fm.InsBirthDate.value = '';
	//被保人电子邮箱
	fm.InsAddrLine.value = '';
	//被保人通讯地址	
	fm.InsLine1.value = ''; 
	//被保人邮政编码
	fm.InsZip.value = '';
	//被保人家庭电话 
	fm.InsDialNumber1.value = '';
	//被保人移动电话
	fm.InsDialNumber3.value = '';
	//健康告知
	fm.HealthIndicator.value = '';
	
//受益人信息1
	//受益人姓名
	fm.BnfFullName1.value = '';
	//受益人性别
	fm.BnfGender1.value = '';
	//受益人证件类型
	fm.BnfGovtIDTC1.value = '';
	//受益人证件号码
	fm.BnfGovtID1.value = '';
	//受益人出生日期
	fm.BnfBirthDate1.value = '';
	//受益百分数
	fm.InterestPercent1.value = '';
	//受益顺序  
	fm.Sequence1.value = ''; 
	//受益人与被报人的关系 
	fm.BnfToInsRelation1.value = '';
	//受益人是否为法定标志 
	fm.BeneficiaryIndicator.value='';  
 
//受益人信息2
	//受益人姓名
	fm.BnfFullName2.value = '';
	//受益人性别
	fm.BnfGender2.value = '';
	//受益人证件类型
	fm.BnfGovtIDTC2.value = '';
	//受益人证件号码
	fm.BnfGovtID2.value = '';
	//受益人出生日期
	fm.BnfBirthDate2.value = '';
	//受益百分数
	fm.InterestPercent2.value = '';
	//受益顺序
	fm.Sequence2.value = '';
	//受益人与被报人的关系
	fm.BnfToInsRelation2.value = '';
	 
//受益人信息3
	//受益人姓名
	fm.BnfFullName3.value = '';
	//受益人性别
	fm.BnfGender3.value = '';
	//受益人证件类型
	fm.BnfGovtIDTC3.value = '';
	//受益人证件号码 
	fm.BnfGovtID3.value = '';
	//受益人出生日期
	fm.BnfBirthDate3.value = '';
	//受益百分数
	fm.InterestPercent3.value = '';
	//受益顺序 
	fm.Sequence3.value = '';
	//受益人与被报人的关系
	fm.BnfToInsRelation3.value = '';
	
//主险信息
	//主险代码 
	fm.ProductCode.value = '';
	//份数 
	fm.IntialNumberOfUnits.value = '';
	//保费 
	fm.InitCovAmt.value = '';
	//保额 
	fm.ModalPremAmt.value = '';
	//保险年期类型  
	fm.DurationMode.value = '';
	//保险年期 
	fm.Duration.value = '';
	//缴费年期类型 
	fm.PaymentDurationMode.value = '';
	//缴费年期 
	fm.PaymentDuration.value = '';

//附加险信息1
	//主险代码 
	fm.ProductCode1.value = '';
	//份数 
	fm.IntialNumberOfUnits1.value = '';
	//保费 
	fm.InitCovAmt1.value = '';
	//保额 
	fm.ModalPremAmt1.value = '';
	//保险年期类型 
	fm.DurationMode1.value = '';
	//保险年期 
	fm.Duration1.value = '';
	//缴费年期类型 
	fm.PaymentDurationMode1.value = '';
	//缴费年期 
	fm.PaymentDuration1.value = '';
		
//附加险信息2
	//主险代码 
	fm.ProductCode2.value = '';
	//份数 
	fm.IntialNumberOfUnits2.value = '';
	//保费 
	fm.InitCovAmt2.value = '';
	//保额  
	fm.ModalPremAmt2.value = '';
	//保险年期类型 
	fm.DurationMode2.value = '';
	//保险年期 
	fm.Duration2.value = '';
	//缴费年期类型 
	fm.PaymentDurationMode2.value = '';
	//缴费年期 
	fm.PaymentDuration2.value = '1';
	
//投保信息

	//缴费方式 
	fm.PaymentMode.value = '';
	//缴费形式(频次)
	fm.PaymentMethod.value = '';     
	//领取方式  
	fm.BenefitMode.value = ''; 
	//红利领取方式  
	fm.DivType.value = '';
	//保单传送方式
	fm.PolicyDeliveryMethod.value = ''; 
	//银行账号  
	fm.AccountNumber.value = '';
	//帐户姓名
	fm.AcctHolderName.value = '';
	//特别约定 
	fm.SpecialClause.value = '';
	fm.OccupationIndicator.value = '';	
	
//
	fm.ip.value = '127.0.0.1';
	fm.port.value = '52002'; 
	fm.tranFlagCode.value = '1013'; 
	//fm.tranFlagCodeName.value = '新契约交易';
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initBox 函数中发生异常:初始化界面错误!");
	}
}


function initInputBox() 
{
	try {
//交易信息
	//银行端交易日期
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//交易流水号
	fm.TransRefGUID.value = '<%=strTransRefGUID%>';
	//地区代码 
	fm.RegionCode.value = '10011';
	//网点代码  
	fm.Branch.value = '14800';
	//柜员代码
	fm.Teller.value = '00001';
	//投保日期 
	fm.SubmissionDate.value = '<%=strTransExeDate%>';
	//投保书号
	fm.HOAppFormNumber.value = '<%=strHOAppFormNumber%>';
	//保单印刷号
	fm.ProviderFormNumber.value = '<%=strProviderFormNumber%>';
	//销售人员工号
	fm.BankManagerAgentId.value='1009611';
	//销售人员姓名
	fm.BankManagerName.value='米拉娜';
   
//投保人信息
	//投保人姓名
	fm.AppFullName.value = '花无缺';
	//投保人性别
	fm.AppGender.value = '1';
	//投保人证件类型
	fm.AppGovtIDTC.value = '6';
	//投保人证件号码
	fm.AppGovtID.value = '98345787435354';
	//投保人生日
	fm.AppBirthDate.value = '1983-01-13';
	//投保人电子邮箱
	fm.AppAddrLine.value = 'huawuque@sina.com';
	//投保人通讯地址	 
	fm.AppLine1.value = '北京市朝阳区建国门外天朝路168号'; 
	//投保人邮政编码
	fm.AppZip.value = '100036';
	//投保人家庭电话
	fm.AppDialNumber1.value = '01058005630';
	//投保人移动电话
	fm.AppDialNumber3.value = '15821576966';
	//投保人国籍
	fm.AppNationality.value='中国';
	fm.AppNationalityNo.value= '156';
	//投保人与被报人的关系
	fm.AppToInsRelation.value = '2';
	//身高
	fm.AppStature.value='180';
	//体重
	fm.AppWeight.value='86';
	//年收入
	fm.AppEstSalary.value='24000000';
	fm.FamilyEstSalary.value='42000000';
	fm.PbDenType.value='1';
	fm.AppCompany.value='北京百度网讯科技有限公司'
	//投保人的职业代码
	fm.AppJobCode.value='3010101';
	//身份证有效期
	fm.AppGovtTermDate.value='2020-08-29';
//被保人信息
	//被保人姓名
	fm.InsFullName.value = '上官飞燕';
	//被保人性别
	fm.InsGender.value = '2';
	//被保人证件类型
	fm.InsGovtIDTC.value = '6';
	//被保人证件号码
	fm.InsGovtID.value = '78654354678765';
	//被保人生日
	fm.InsBirthDate.value = '1984-05-18';
	//被保人电子邮箱
	fm.InsAddrLine.value = 'sgflybird@sohu.com';
	//被保人通讯地址	
	fm.InsLine1.value = '广州市天河区阳光大道110号'; 
	//被保人邮政编码
	fm.InsZip.value = '376112';
	//被保人家庭电话 
	fm.InsDialNumber1.value = '02053205611';
	//被保人移动电话
	fm.InsDialNumber3.value = '13821576911';
	//被保人国籍
	fm.InsNationality.value='中国';
	fm.InsNationalityNo.value = '156';
	//健康告知
	fm.HealthIndicator.value = 'N';
	//身高
	fm.InsStature.value='162';
	//体重
	fm.InsWeight.value='48';
	//年收入
	fm.InsEstSalary.value='18000000';
	//被保人的职业代码
	fm.InsJobCode.value='3010101';
	//身份证有效期
	fm.InsGovtTermDate.value='2020-08-29';
	fm.InsCompany.value='浙江淘宝网络有限公司';
//受益人信息1 
	//受益人姓名
	fm.BnfFullName1.value = '风行者';
	//受益人性别 
	fm.BnfGender1.value = '1';
	//受益人证件类型
	fm.BnfGovtIDTC1.value = '6';
	//受益人证件号码
	fm.BnfGovtID1.value = '23464534234';
	//受益人出生日期
	fm.BnfBirthDate1.value = '1990-04-12';
	//受益百分数 
	fm.InterestPercent1.value = '100';
	//受益顺序  
	fm.Sequence1.value = '1'; 
	//受益人与被报人的关系  
	fm.BnfToInsRelation1.value = '2';
	//受益人是否为法定标志 
	fm.BeneficiaryIndicator.value='N';  
 
//受益人信息2
	//受益人姓名
	fm.BnfFullName2.value = '';
	//受益人性别
	fm.BnfGender2.value = '';
	//受益人证件类型
	fm.BnfGovtIDTC2.value = '';
	//受益人证件号码
	fm.BnfGovtID2.value = '';
	//受益人出生日期
	fm.BnfBirthDate2.value = '';
	//受益百分数
	fm.InterestPercent2.value = '';
	//受益顺序
	fm.Sequence2.value = '';
	//受益人与被报人的关系
	fm.BnfToInsRelation2.value = '';
	 
//受益人信息3
	//受益人姓名
	fm.BnfFullName3.value = '';
	//受益人性别
	fm.BnfGender3.value = '';
	//受益人证件类型
	fm.BnfGovtIDTC3.value = '';
	//受益人证件号码 
	fm.BnfGovtID3.value = '';
	//受益人出生日期
	fm.BnfBirthDate3.value = '';
	//受益百分数
	fm.InterestPercent3.value = '';
	//受益顺序 
	fm.Sequence3.value = '';
	//受益人与被报人的关系
	fm.BnfToInsRelation3.value = '';
	
//主险信息
	//主险代码 
	fm.ProductCode.value = '012';
	//份数 
	fm.IntialNumberOfUnits.value = '1';
	//保费 
	fm.InitCovAmt.value = '20000000';
	//保额 
	fm.ModalPremAmt.value = '10000000';
	//保险年期类型  
	fm.DurationMode.value = '2';
	//保险年期 
	fm.Duration.value = '10';
	//缴费年期类型 
	fm.PaymentDurationMode.value = '5';
	//缴费年期 
	fm.PaymentDuration.value = '1';

//附加险信息1
	//主险代码 
	fm.ProductCode1.value = '';
	//份数 
	fm.IntialNumberOfUnits1.value = '';
	//保费 
	fm.InitCovAmt1.value = '';
	//保额 
	fm.ModalPremAmt1.value = '';
	//保险年期类型 
	fm.DurationMode1.value = '';
	//保险年期 
	fm.Duration1.value = '';
	//缴费年期类型 
	fm.PaymentDurationMode1.value = '';
	//缴费年期 
	fm.PaymentDuration1.value = '';
		
//附加险信息2
	//主险代码 
	fm.ProductCode2.value = '';
	//份数 
	fm.IntialNumberOfUnits2.value = '';
	//保费 
	fm.InitCovAmt2.value = '';
	//保额  
	fm.ModalPremAmt2.value = '';
	//保险年期类型 
	fm.DurationMode2.value = '';
	//保险年期 
	fm.Duration2.value = '';
	//缴费年期类型 
	fm.PaymentDurationMode2.value = '';
	//缴费年期 
	fm.PaymentDuration2.value = '';
	
//投保信息

	//缴费方式 
	fm.PaymentMode.value = '5';
	//缴费形式(频次)
	fm.PaymentMethod.value = '5';     
	//领取方式  
	fm.BenefitMode.value = '1'; 
	//红利领取方式  
	fm.DivType.value = '1';
	//保单传送方式
	fm.PolicyDeliveryMethod.value = '1'; 
	//银行账号  
	fm.AccountNumber.value = '6225132348577857';
	fm.AcctHolderName.value = '花无缺';
	//特别约定 
	fm.SpecialClause.value = 'SpecialClause';	
	fm.OccupationIndicator.value='N';
// 
	fm.ip.value = '127.0.0.1';
	fm.port.value = '35000';
	fm.tranFlagCode.value = '1013'; 
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initInputBox 函数中发生异常:初始化界面错误!");
	}
}

// 出纳业务信息列表的初始化
function initPolGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="出纳编码";
		iArray[1][1]="0px";
		iArray[1][2]=100;
		iArray[1][3]=3;

		iArray[2]=new Array();
		iArray[2][0]="出纳姓名";
		iArray[2][1]="0px";
		iArray[2][2]=100;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="付费类型";
		iArray[3][1]="80px";
		iArray[3][2]=200;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="实付号码";
		iArray[4][1]="100px";
		iArray[4][2]=200;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="付费方式";
		iArray[5][1]="80px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="付费方式编码";
		iArray[6][1]="0px";
		iArray[6][2]=100;
		iArray[6][3]=3;

		iArray[7]=new Array();
		iArray[7][0]="付费金额";
		iArray[7][1]="80px";
		iArray[7][2]=100;
		iArray[7][3]=0;
		
		iArray[8]=new Array();
		iArray[8][0]="开户银行";
		iArray[8][1]="80px";
		iArray[8][2]=100;
		iArray[8][3]=0;		
		
		iArray[9]=new Array();
		iArray[9][0]="银行账户";
		iArray[9][1]="80px";
		iArray[9][2]=100;
		iArray[9][3]=0;				

		PolGrid = new MulLineEnter( "fm" , "PolGrid" );
		//这些属性必须在loadMulLine前
		PolGrid.mulLineCount = 0;
		PolGrid.displayTitle = 1;
		PolGrid.locked = 1;
		PolGrid.canSel = 0;
		PolGrid.canChk = 1;
		PolGrid.hiddenPlus = 1;
		PolGrid.hiddenSubtraction = 1;
		PolGrid.chkBoxEventFuncName = "AddMoney";
		PolGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

// 险种列表信息的初始化
function initRiskList()
{                               
    var iArray = new Array();
      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         		  //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";        			//列宽
      iArray[0][2]=30;          			  //列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种编码";    	        //列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=50;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许,2表示代码选择
      iArray[1][4]="ProductCode";           //引用代码

      iArray[2]=new Array();
      iArray[2][0]="主险编码";    	        //列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=50;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许,2表示代码选择
      iArray[2][4]="ProductCode";           //引用代码

      iArray[3]=new Array();
      iArray[3][0]="续期缴费账号";    	            //列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=50;            			//列最大值
      iArray[3][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="生效日期";    	            //列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=50;            			//列最大值
      iArray[4][3]=3;              			//是否允许输入,1表示允许，0表示不允许，3表示隐藏

      iArray[5]=new Array();
      iArray[5][0]="保费";    	            //列名
      iArray[5][1]="70px";            		//列宽
      iArray[5][2]=50;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="保额";    	            //列名
      iArray[6][1]="70px";            		//列宽
      iArray[6][2]=50;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="份数";    	            //列名
      iArray[7][1]="50px";            		//列宽
      iArray[7][2]=50;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="缴费方式";    	            //列名
      iArray[8][1]="60px";            		//列宽
      iArray[8][2]=50;            			//列最大值
      iArray[8][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[8][10]="payintv";
      iArray[8][11]="0|^0|无关|^1|趸缴|^2|月缴|^3|季缴|^4|半年缴|^5|年缴|^7|不定期交|^9|其他"; 

      iArray[9]=new Array();
      iArray[9][0]="缴费年期类型";    	            //列名
      iArray[9][1]="80px";            		//列宽
      iArray[9][2]=50;            			//列最大值
      iArray[9][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[9][10]="payflag";
      iArray[9][11]="0|^0|无关|^1|趸缴|^2|按年限缴|^3|缴至某确定年龄|^4|终生缴费|^5|不定期缴";

      iArray[10]=new Array();
      iArray[10][0]="缴费年期";    	            //列名
      iArray[10][1]="60px";            		//列宽
      iArray[10][2]=50;            			//列最大值
      iArray[10][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="领取年期类型";    	            //列名
      iArray[11][1]="80px";            		//列宽
      iArray[11][2]=50;            			//列最大值
      iArray[11][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[11][10]="getflag";
      iArray[11][11]="0|^0|无关|^1|一次性领取|^2|月领|^3|季领|^4|半年领|^5|年领";

      iArray[12]=new Array();
      iArray[12][0]="领取年期";    	            //列名
      iArray[12][1]="60px";            		//列宽
      iArray[12][2]=50;            			//列最大值
      iArray[12][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[13]=new Array();
      iArray[13][0]="保险年期类型";    	            //列名
      iArray[13][1]="80px";            		//列宽
      iArray[13][2]=50;            			//列最大值
      iArray[13][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[13][10]="insuflag";
      iArray[13][11]="0|^0|无关|^1|保终身|^2|按年限保|^3|保至某确定年龄|^4|按月保|^5|按天保";
      
      iArray[14]=new Array();
      iArray[14][0]="保险年期";    	            //列名
      iArray[14][1]="60px";            		//列宽
      iArray[14][2]=50;            			//列最大值
      iArray[14][3]=1;              			//是否允许输入,1表示允许，0表示不允许      
      
      iArray[15]=new Array();
      iArray[15][0]="自动垫交标记";    	            //列名
      iArray[15][1]="80px";            		//列宽
      iArray[15][2]=50;            			//列最大值
      iArray[15][3]=2;              			//是否允许输入,1表示允许，0表示不允许 
      iArray[15][10]="AutoPayFlag";
      iArray[15][11]="0|^0|正常|^1|垫交";
      iArray[15][14]="0";
      
      iArray[16]=new Array();
      iArray[16][0]="红利分配标记";    	            //列名
      iArray[16][1]="80px";            		//列宽
      iArray[16][2]=50;            			//列最大值
      iArray[16][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[16][10]="BonusPayMode";
      iArray[16][11]="0|^0|红利分配|^1|无红利分配";       
      iArray[16][14]="0";
      
      iArray[17]=new Array();
      iArray[17][0]="红利领取方式";    	            //列名
      iArray[17][1]="80px";            		//列宽
      iArray[17][2]=50;            			//列最大值
      iArray[17][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[17][10]="BonusGetMode";
      iArray[17][11]="0|^1|抵交保费|^2|累积生息|^3|增额缴清|^4|现金";       
      iArray[17][14]="1";
      
      iArray[18]=new Array();
      iArray[18][0]="减额交清标记";    	            //列名
      iArray[18][1]="80px";            		//列宽
      iArray[18][2]=50;            			//列最大值
      iArray[18][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[18][10]="SubFlag";
      iArray[18][11]="0|^0|正常|^1|减额交清";       
      iArray[18][14]="0";
      
      iArray[19]=new Array();
      iArray[19][0]="合同争议处理方式";    	            //列名
      iArray[19][1]="100px";            		//列宽
      iArray[19][2]=50;            			//列最大值
      iArray[19][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[19][10]="DEALTYPE";
      iArray[19][11]="0|^1|诉讼|^2|仲裁";       
      iArray[19][14]="1";
      
      iArray[20]=new Array();
      iArray[20][0]="健康告知标志";    	            //列名
      iArray[20][1]="80px";            		//列宽
      iArray[20][2]=50;            			//列最大值
      iArray[20][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[20][10]="HealthFlag";
      iArray[20][11]="0|^N|否|^Y|是";       
      iArray[20][14]="N";
      
      iArray[21]=new Array();
      iArray[21][0]="特别约定";    	            //列名
      iArray[21][1]="200px";            		//列宽
      iArray[21][2]=50;            			//列最大值
      iArray[21][3]=1;              			//是否允许输入,1表示允许，0表示不允许      

      RiskList = new MulLineEnter( "fm" , "RiskList" ); 
      //这些属性必须在loadMulLine前
      RiskList.mulLineCount = 0;   
      RiskList.displayTitle = 1;
      RiskList.loadMulLine(iArray);  
      
    } catch(ex) {
      alert(ex);
    }
   }
    function initBnfList(){
     var iArray = new Array();
      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         		  //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";        			//列宽
      iArray[0][2]=30;          			  //列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="受益人类型";    	        //列名
      iArray[1][1]="70px";            		//列宽
      iArray[1][2]=50;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许,2表示代码选择
      iArray[1][10]="BnfType";
      iArray[1][11]="0|^0|生存受益人|^1|死亡受益人|^2|红利受益人"; 
      iArray[1][14]="1";          

      iArray[2]=new Array();
      iArray[2][0]="受益顺序";    	        //列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=50;            			//列最大值
      iArray[2][3]=2;   
      iArray[2][14]="1";                    //引用代码

      iArray[3]=new Array();
      iArray[3][0]="受益人姓名";    	            //列名
      iArray[3][1]="70px";            		//列宽
      iArray[3][2]=50;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="受益人性别";    	            //列名
      iArray[4][1]="70px";            		//列宽
      iArray[4][2]=40;            			//列最大值
      iArray[4][3]=3;              			//是否允许输入,1表示允许，0表示不允许，3表示隐藏
      iArray[4][10]="BnfSex";
      iArray[4][11]="0|^0|男|^1|女|^2|不详"; 
      iArray[4][14]="1";   
      
      iArray[5]=new Array();
      iArray[5][0]="受益人生日(YYYY-MM-DD)";  //列名
      iArray[5][1]="150px";            		//列宽
      iArray[5][2]=50;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][9]="受益人生日|NOTNULL";

      iArray[6]=new Array();
      iArray[6][0]="受益人证件类型";    	            //列名
      iArray[6][1]="120px";            		//列宽
      iArray[6][2]=30;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[6][10]="BnfIDType";
      iArray[6][11]="0|^15|居民身份证^16|临时身份证^17|军人身份证件^18|武警身份证件^19|通行证^20|护照^21|其他^22|临时户口^23|户口簿^24|边境证^25|外国人居留证^26|身份证明"; 
      iArray[6][14]="15";   

      iArray[7]=new Array();
      iArray[7][0]="受益人证件号码";    	            //列名
      iArray[7][1]="120px";            		//列宽
      iArray[7][2]=50;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="受益百分数";    	            //列名
      iArray[8][1]="70px";            		//列宽
      iArray[8][2]=30;            			//列最大值
      iArray[8][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[8][14]="100";
      
      iArray[9]=new Array();
      iArray[9][0]="与被保人关系(代号)";    	            //列名
      iArray[9][1]="120px";            		//列宽
      iArray[9][2]=30;            			//列最大值
      iArray[9][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[9][14]="1";

     
      
      
      BnfList = new MulLineEnter( "fm" , "BnfList" ); 
      //这些属性必须在loadMulLine前
      BnfList.mulLineCount = 0;   
      BnfList.displayTitle = 1;
      BnfList.loadMulLine(iArray);  
       }catch(ex) {
      alert(ex);
    }
  }
    

</script>
