<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.db.LDComDB"%>

<%
GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
if (null == cGlobalInput) {
	cGlobalInput = new GlobalInput();
}

//-----�û�Ȩ��------
String sManageCom = cGlobalInput.ManageCom;
if(!"86".equals(sManageCom)){
	LDComDB tLDComDB = new LDComDB();
	tLDComDB.setComCode(sManageCom);
	if(!tLDComDB.getInfo()){
		System.out.println("���û������������������,��ȷ��!");
	}

}
//-----�û�Ȩ��------

String sStartDay = DateUtil.getCur10Date();
String sEndDay = DateUtil.getCur10Date();

String sArrayName1 = "��Ա��";	 
String sArrayName2 = "��Ա����";	
String sArrayName3 = "��������";	
String sArrayName4 = "��������";
String sArrayName5 = "��Ա�ʸ�֤����";
String sArrayName6 = "��Ա�ʸ�֤��";	
String sArrayName7 = "�ʸ�֤��Ч����";	
String sArrayName8 = "�ʸ�֤ʧЧ����";		
String sArrayName9 = "���д���";	
String sArrayName10 = "��������";



String sArrayLength1 = "100"; 
String sArrayLength2 = "50"; 
String sArrayLength3 = "100"; 
String sArrayLength4 = "40"; 
String sArrayLength5 = "60"; 
String sArrayLength6 = "100";
String sArrayLength7 = "80";
String sArrayLength8 = "80";  
String sArrayLength9 = "0"; 
String sArrayLength10 = "0";

%>

<script language="javascript">
var cManageCom = "<%=cGlobalInput.ManageCom%>";	//ȫ�ֱ�������¼��½�������
var cOperator = "<%=cGlobalInput.Operator%>";	//ȫ�ֱ�������¼��¼�û�


function resetForm(){

	//fm.TranCom.value = "";
	//fm.TranComName.value = "";
	
	//fm.ZoneNo.value="";
	//fm.ZoneName.value="";
	
	initForm();
	
}

function initForm() {
	//initUserAuthority();

	fm.TranCom.value = "";
	fm.TranComName.value = "";
	
	fm.ZoneNo.value="";
	fm.ZoneName.value="";
	
	fm.StartDay.value="";
	fm.EndDay.value="";
	initQueryGrid();
}

function initQueryGrid() {
	var mArray = new Array();
	
	try {
		mArray[0] = new Array();
		mArray[0][0] = "���";		//����
		mArray[0][1] = "30px";		//�п�
		mArray[0][2] = 10;			//�����ֵ
		mArray[0][3] = 0;		    	//�Ƿ���������,1��ʾ����0��ʾ������
		
		mArray[1] = new Array();
		mArray[1][0] = "<%=sArrayName1%>";
		mArray[1][1] = "<%=sArrayLength1%>";
		mArray[1][2] = 200;
		mArray[1][3] = 0;
		
		mArray[2] = new Array();
		mArray[2][0] = "<%=sArrayName2%>";
		mArray[2][1] = "<%=sArrayLength2%>";
		mArray[2][2] = 200;
		mArray[2][3] = 0;
		
		mArray[3] = new Array();
		mArray[3][0] = "<%=sArrayName3%>";
		mArray[3][1] = "<%=sArrayLength3%>";
		mArray[3][2] = 200;
		mArray[3][3] = 0;

		mArray[4] = new Array();
		mArray[4][0] = "<%=sArrayName4%>";
		mArray[4][1] = "<%=sArrayLength4%>";
		mArray[4][2] = 200;
		mArray[4][3] = 0;

		mArray[5] = new Array();
		mArray[5][0] = "<%=sArrayName5%>";
		mArray[5][1] = "<%=sArrayLength5%>";
		mArray[5][2] = 200;
		mArray[5][3] = 0;

		mArray[6] = new Array();
		mArray[6][0] = "<%=sArrayName6%>";
		mArray[6][1] = "<%=sArrayLength6%>";
		mArray[6][2] = 300;
		mArray[6][3] = 0;
		
		mArray[7] = new Array();
		mArray[7][0] = "<%=sArrayName7%>";
		mArray[7][1] = "<%=sArrayLength7%>";
		mArray[7][2] = 200;
		mArray[7][3] = 0;
		
		mArray[8] = new Array();
		mArray[8][0] = "<%=sArrayName8%>";
		mArray[8][1] = "<%=sArrayLength8%>";
		mArray[8][2] = 200;
		mArray[8][3] = 0;
		
		mArray[9] = new Array();
		mArray[9][0] = "<%=sArrayName9%>";
		mArray[9][1] = "<%=sArrayLength9%>";
		mArray[9][2] = 200;
		mArray[9][3] = 0;
		
		mArray[10] = new Array();
		mArray[10][0] = "<%=sArrayName10%>";
		mArray[10][1] = "<%=sArrayLength10%>";
		mArray[10][2] = 200;
		mArray[10][3] = 0;
		
		
		QueryGrid = new MulLineEnter("fm", "QueryGrid");
		QueryGrid.displayTitle = 1;
		QueryGrid.locked = 0;
		QueryGrid.mulLineCount = 0;
		QueryGrid.hiddenPlus = 1;
		QueryGrid.hiddenSubtraction = 1;
		QueryGrid.canSel = 1;
		QueryGrid.loadMulLine(mArray);
	} catch(ex) {
	  alert(ex);
	}
}
</script>

