<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<%@page import="org.jdom.*" %>
<%@page import="com.sinosoft.midplat.MidplatConf" %>
<% 
	//�������ƣ�XBTContInit.jsp
	
%>
<%  
	PubFun1 pubfun = new PubFun1();
	String strTransExeDate = DateUtil.getCur10Date();
	String strTransRefGUID = PubFun1.CreateMaxNo("TransNo",16);
  String strProposalContNo = "2104141"+PubFun1.CreateMaxNo("ProNo",8);
	Element mTestUI = 
 		 MidplatConf.newInstance().getConf().getRootElement().getChild("NewABCTestUI");
// 	 String tIp = mTestUI.getAttributeValue("ip");
// 	 String tPort = mTestUI.getAttributeValue("port");
//  	 String tZoneNo = mTestUI.getAttributeValue("ZoneNo");
//  	 String tNodeNo = mTestUI.getAttributeValue("NodeNo");
	String tIp="127.0.0.1";
	String tPort="35006";
	String tZoneNo="11";
	String tNodeNo="0102";
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
		alert("NewAbcContInit.jsp-->InitForm11 �����з����쳣:��ʼ���������!");
	}
}



function initInputBox() 
{
	try {
	fm.TranCom.value='05';
	fm.APPNO.value='<%=strTransRefGUID%>';
	fm.SERIALNO.value='<%=strTransRefGUID%>';
	//���ؽ�����ˮ��
	fm.InputTransrNo.value = '<%=strTransRefGUID%>';
	fm.POLICYAPPLYSERIAL.value='<%=strProposalContNo%>';
	fm.TRANSDATE.value='<%=strTransExeDate%>';
	fm.BRANCHNO.value='0102';
	fm.TLID.value='0005';
		
	fm.CONACCNAME.value='�����';
	fm.CONACCNO.value='8989898989898';
	fm.APPLYDATE.value='<%=strTransExeDate%>';
	fm.SALER.value='����';
	fm.SALERCERTNO.value='10209129301230';
	fm.BRANCHCERTNO.value='1203120999';
	fm.BRANCHNAME.value='ũҵ���в�������';
	fm.VCHNO.value='0000000093';
	fm.ProvCode.value='11';
	//Ͷ����
  document.getElementById("OldTranNo").disabled=true;
  document.getElementById("PayAccountID").disabled=true;
	fm.APPLIDKIND.value='110013';
	fm.APPLIDCODE.value='100101008611';
	fm.APPLBEGINDATE.value='1985-09-17';
	fm.APPLINVALIDDATE.value='2019-12-11';
	fm.APPLNAME.value='ë����';
	fm.APPLSEX.value='0';
	fm.APPLBIRTHDAY.value='1965-09-17';
	fm.APPLCOUNTRY.value='156';
	fm.APPLADDRESS.value='�㽭ʡ������UDCʱ������23¥';
	fm.APPLPROV.value='�㽭ʡ';
	fm.APPLZIPCODE.value='100001';
	fm.APPLEMAIL.value='maozexi@sina.com';
	fm.APPLPHONE.value='10112481294';
	fm.APPLMOBILE.value='18919082301';
	fm.APPLANNUALINCOME.value='1000000';
	fm.APPLRELATOINSURED.value='04';
	fm.ApplJobCode.value='09';
	fm.PbDenType.value='1';
	
	//������
	fm.INSUIDKIND.value='110013';
	fm.INSUIDCODE.value='100861001022';
	fm.INSUBEGINDATE.value='1991-10-10';
	fm.INSUINVALIDDATE.value='2019-10-10';
	fm.INSUNAME.value='ϰԶƽ';
	fm.INSUSEX.value='0';
	fm.INSUBIRTHDAY.value='1991-10-10';
	fm.INSUCOUNTRY.value='156';
	fm.INSUADDRESS.value='�㽭ʡ������UDCʱ������23¥';
	fm.INSUPROV.value='�㽭ʡ';
	fm.INSUZIPCODE.value='100001';
	fm.INSUEMAIL.value='xiyuanping@sina.com';
	fm.INSUPHONE.value='10112512000';
	fm.INSUMOBILE.value='18902392000';
	fm.INSUJOBCODE.value='08'
	fm.INSUNOTICE.value='0';
	fm.INSUANNUALINCOME.value='1000000';	
	fm.INSUISRISKJOB.value='0';
	fm.INSUHEALTHNOTICE.value='0';
	
	
//��������Ϣ1 
	//����������
	fm.BNFTYPE1.value = '1';
	//����������
	fm.BNFNAME1.value = '�˴�ƽ';
	//�������Ա� 
	fm.BNFSEX1.value = '0';
	//������֤������
	fm.BNFIDKIND1.value = '110005';
	//������֤������
	fm.BNFIDCODE1.value = '220523850917341';
	//�����˳�������
	fm.BNFBIRTHDAY1.value = '1988-09-17';
	//����ٷ��� 
	fm.BNFPROP1.value = '100';
	//����˳��  
	fm.BNFSEQUENCE1.value = '1'; 
	//�������뱻���˵Ĺ�ϵ  
	fm.BnfRelationToInsured1.value = '02';
	//�������Ƿ�Ϊ������־ 
	//fm.BeneficiaryIndicator.value='N'; 
	
	//������֤��������Ч��
	fm.BnfValidYear1.value = '2030-09-16'; 
 
//��������Ϣ2
//��������Ϣ1 
	//����������
	fm.BNFTYPE2.value = '';
	//����������
	fm.BNFNAME2.value = '';
	//�������Ա� 
	fm.BNFSEX2.value = '';
	//������֤������
	fm.BNFIDKIND2.value = '';
	//������֤������
	fm.BNFIDCODE2.value = '';
	//�����˳�������
	fm.BNFBIRTHDAY2.value = '';
	//����ٷ��� 
	fm.BNFPROP2.value = '';
	//����˳��  
	fm.BNFSEQUENCE2.value = ''; 
	//�������뱻���˵Ĺ�ϵ  
	fm.BnfRelationToInsured2.value = '';
	//�������Ƿ�Ϊ������־ 
	//fm.BeneficiaryIndicator.value='N'; 
	
	//������֤��������Ч��
	fm.BnfValidYear2.value = ''; 
	 
//��������Ϣ3
	//����������
	fm.BNFTYPE3.value = '';
	//����������
	fm.BNFNAME3.value = '';
	//�������Ա� 
	fm.BNFSEX3.value = '';
	//������֤������
	fm.BNFIDKIND3.value = '';
	//������֤������
	fm.BNFIDCODE3.value = '';
	//�����˳�������
	fm.BNFBIRTHDAY3.value = '';
	//����ٷ��� 
	fm.BNFPROP3.value = '';
	//����˳��  
	fm.BNFSEQUENCE3.value = ''; 
	//�������뱻���˵Ĺ�ϵ  
	fm.BnfRelationToInsured3.value = '';
	//�������Ƿ�Ϊ������־ 
	//fm.BeneficiaryIndicator.value='N'; 
	
	//������֤��������Ч��
	fm.BnfValidYear3.value = ''; 

	//����
	fm.RISKSRISKCODE.value='221206';
	fm.RISKSSHARE.value='1';
	fm.RISKSPREM.value='30000.00';
	fm.RISKSAMNT.value='';
	fm.RISKSINSUDUETYPE.value='4';
	fm.RISKSINSUDUEDATE.value='10';
	fm.RISKSPAYDUETYPE.value='0';
	fm.RISKSPAYDUEDATE.value='1000';
	fm.BonusGetMode.value='2';
	fm.PayType.value='1';
	//����
    fm.LoanCom.value = '1200292038';
    fm.LoanContractNo.value = '201200000219';
    fm.LoanStartDate.value = '2012-12-11';
    fm.LoanEndDate.value = '2015-12-11';
    fm.LoanContractAmt.value = '2000000';

	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>'; 
	fm.TRANSCODE.value = '1002'; 

	}
	catch(re)
	{
		alert("NewAbcContInit.jsp-->initInputBox22 �����з����쳣:��ʼ���������!");
	}
}



function initBox(){
	try{
	fm.TranCom.value='05';
	fm.APPNO.value=fm.SERIALNO.value='<%=strTransRefGUID%>';

	fm.TRANSDATE.value='<%=strTransExeDate%>';
	fm.BRANCHNO.value='0102';
	fm.TLID.value='0005';
		
	fm.CONACCNAME.value='';
	fm.CONACCNO.value='';
	fm.APPLYDATE.value='';
	fm.SALER.value='';
	fm.SALERCERTNO.value='';
	fm.BRANCHCERTNO.value='';
	fm.BRANCHNAME.value='';
	fm.ProvCode.value='';
	fm.VCHNO.value='';
	//Ͷ����

	fm.APPLIDKIND.value='';
	fm.APPLIDCODE.value='';
	fm.APPLBEGINDATE.value='';
	fm.APPLINVALIDDATE.value='';
	fm.APPLNAME.value=''
	fm.APPLSEX.value='';
	fm.APPLBIRTHDAY.value='';
	fm.APPLCOUNTRY.value='';
	fm.APPLADDRESS.value='';
	fm.APPLPROV.value='';
	fm.APPLZIPCODE.value='';
	fm.APPLEMAIL.value='';
	fm.APPLPHONE.value='';
	fm.APPLMOBILE.value='';
	fm.APPLANNUALINCOME.value='';
	fm.ApplJobCode.value='';
	fm.APPLRELATOINSURED.value='';
	
	//������
	fm.INSUIDKIND.value='';
	fm.INSUIDCODE.value='';
	fm.INSUBEGINDATE.value='';
	fm.INSUINVALIDDATE.value='';
	fm.INSUNAME.value='';
	fm.INSUSEX.value='';
	fm.INSUBIRTHDAY.value='';
	fm.INSUCOUNTRY.value='';
	fm.INSUADDRESS.value='';
	fm.INSUPROV.value='';
	fm.INSUZIPCODE.value='';
	fm.INSUEMAIL.value='';
	fm.INSUPHONE.value='';
	fm.INSUMOBILE.value='';
	
	fm.INSUJOBCODE.value='';
	fm.INSUNOTICE.value='';
	fm.INSUANNUALINCOME.value='';	
	fm.INSUISRISKJOB.value='';
	fm.INSUHEALTHNOTICE.value='';


	//����
	fm.RISKSRISKCODE.value='221206';
	fm.RISKSSHARE.value='';
	fm.RISKSPREM.value='';
	fm.RISKSAMNT.value='';
	fm.RISKSINSUDUETYPE.value='';
	fm.RISKSINSUDUEDATE.value='';
	fm.RISKSPAYDUETYPE.value='';
	fm.RISKSPAYDUEDATE.value='';
	//����
    fm.LoanCom.value = '';
    fm.LoanContractNo.value = '';
    fm.LoanStartDate.value = '';
    fm.LoanEndDate.value = '';
    fm.LoanContractAmt.value = '';

	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>'; 
	fm.TRANSCODE.value = '1004'; 
		
	}catch(re){
		alert("NewAbcContInit.jsp-->initBox()�����з����쳣����ʼ������");
	}

}

function initContConfirm()
{
	try{
	fm.ReqsrNo.value = fm.InputTransrNo.value;
	fm.TranCom.value='05';
	fm.APPNO.value=fm.SERIALNO.value='<%=PubFun1.CreateMaxNo("TransNo",16)%>';

	fm.TRANSDATE.value='<%=strTransExeDate%>';
	fm.ProvCode.value='11';
	fm.BRANCHNO.value='0102';
	fm.TLID.value='0005';
  fm.PayAccount.value='6228480150218987623';		
	fm.CONACCNAME.value='';
	fm.CONACCNO.value='';
	fm.APPLYDATE.value='';
	fm.SALER.value='';
	fm.SALERCERTNO.value='';
	fm.BRANCHCERTNO.value='';
	fm.BRANCHNAME.value='';
	fm.VCHNO.value='';
	//Ͷ����

	fm.APPLIDKIND.value='';
	fm.APPLIDCODE.value='';
	fm.APPLBEGINDATE.value='';
	fm.APPLINVALIDDATE.value='';
	fm.APPLNAME.value=''
	fm.APPLSEX.value='';
	fm.APPLBIRTHDAY.value='';
	fm.APPLCOUNTRY.value='';
	fm.APPLADDRESS.value='';
	fm.APPLPROV.value='';
	fm.APPLZIPCODE.value='';
	fm.APPLEMAIL.value='';
	fm.APPLPHONE.value='';
	fm.APPLMOBILE.value='';
	fm.APPLANNUALINCOME.value='';
	fm.ApplJobCode.value='';
	fm.APPLRELATOINSURED.value='';
	
	//������
	fm.INSUIDKIND.value='';
	fm.INSUIDCODE.value='';
	fm.INSUBEGINDATE.value='';
	fm.INSUINVALIDDATE.value='';
	fm.INSUNAME.value='';
	fm.INSUSEX.value='';
	fm.INSUBIRTHDAY.value='';
	fm.INSUCOUNTRY.value='';
	fm.INSUADDRESS.value='';
	fm.INSUPROV.value='';
	fm.INSUZIPCODE.value='';
	fm.INSUEMAIL.value='';
	fm.INSUPHONE.value='';
	fm.INSUMOBILE.value='';
	
	fm.INSUJOBCODE.value='';
	fm.INSUNOTICE.value='';
	fm.INSUANNUALINCOME.value='';	
	fm.INSUISRISKJOB.value='';
	fm.INSUHEALTHNOTICE.value='';


	//����
	fm.RISKSRISKCODE.value='221206';
	fm.RISKSSHARE.value='';
	fm.RISKSPREM.value='';
	fm.RISKSAMNT.value='';
	fm.RISKSINSUDUETYPE.value='';
	fm.RISKSINSUDUEDATE.value='';
	fm.RISKSPAYDUETYPE.value='';
	fm.RISKSPAYDUEDATE.value='';
	//����
    fm.LoanCom.value = '';
    fm.LoanContractNo.value = '';
    fm.LoanStartDate.value = '';
    fm.LoanEndDate.value = '';
    fm.LoanContractAmt.value = '';

	fm.ip.value = '<%=tIp%>';
	fm.port.value = '<%=tPort%>'; 
	fm.TRANSCODE.value = '1004'; 
		
	}catch(re){
		alert("initContConfirm()�����з����쳣����ʼ������");
	}

}
    

</script>
