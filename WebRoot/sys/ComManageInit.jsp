<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.schema.LDComSchema"%>
<%@page import="com.sinosoft.lis.db.LDComDB"%>

<%
GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
if (null == cGlobalInput) {
	cGlobalInput = new GlobalInput();
}


String sArrayName1 = "�����������";
String sArrayName2 = "�����������";
String sArrayName3 = "��������";
String sArrayName4 = "�ϼ��������";
String sArrayName5 = "�������";
String sArrayName6=  "�ϼ���������";
String sArrayName7=  "��������";


String sArrayLength1 = "25"; 
String sArrayLength2 = "40"; 
String sArrayLength3 = "25"; 
String sArrayLength4 = "40"; 
String sArrayLength5 = "40"; 
String sArrayLength6 = "0"; 
String sArrayLength7 = "0"; 

String sManageCom = cGlobalInput.ManageCom;
%>

<script language="javascript">
var cManageCom = "<%=cGlobalInput.ManageCom%>";	//ȫ�ֱ�������¼��½�������
var cOperator = "<%=cGlobalInput.Operator%>";	//ȫ�ֱ�������¼��¼�û�

function initForm() {
	
	initGrid();
}

function initOK() {
	fm.iComGrade.value = "";
	fm.iComGradeName.value = "";
	fm.iAreaNo.value = "";
	fm.iAreaName.value = "";
	fm.iComCode.value = "";
	fm.iOutComCode.value = "";
	fm.iName.value = "";
	fm.iShortName.value = "";
	fm.UpComCode.value = "";
	fm.UpComCodeName.value = "";

} 

 
function initGrid() {
	var mArray = new Array();
 
	try {
		mArray[0] = new Array();
		mArray[0][0] = "���";		//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		mArray[0][1] = "30px";		//�п�
		mArray[0][2] = 10;			//�����ֵ
		mArray[0][3] = 0;				//�Ƿ���������,1��ʾ����0��ʾ������

		
		mArray[1] = new Array();
		mArray[1][0] = "<%=sArrayName1%>";
		mArray[1][1] = "<%=sArrayLength1%>";
		mArray[1][2] = 40;
		mArray[1][3] = 0;
		
		mArray[2] = new Array();
		mArray[2][0] = "<%=sArrayName2%>";
		mArray[2][1] = "<%=sArrayLength2%>";
		mArray[2][2] = 40;
		mArray[2][3] = 0;
		
		mArray[3] = new Array();
		mArray[3][0] = "<%=sArrayName3%>";
		mArray[3][1] = "<%=sArrayLength3%>";
		mArray[3][2] = 40;
		mArray[3][3] = 0;

		mArray[4] = new Array();
		mArray[4][0] = "<%=sArrayName4%>";
		mArray[4][1] = "<%=sArrayLength4%>";
		mArray[4][2] = 40;
		mArray[4][3] = 0;

		mArray[5] = new Array();
		mArray[5][0] = "<%=sArrayName5%>";
		mArray[5][1] = "<%=sArrayLength5%>";
		mArray[5][2] = 40;
		mArray[5][3] = 0;
		
		mArray[6] = new Array();
		mArray[6][0] = "<%=sArrayName6%>";
		mArray[6][1] = "<%=sArrayLength6%>";
		mArray[6][2] = 0;
		mArray[6][3] = 0;
		
		mArray[7] = new Array();
		mArray[7][0] = "<%=sArrayName7%>";
		mArray[7][1] = "<%=sArrayLength7%>";
		mArray[7][2] = 0;
		mArray[7][3] = 0;
		
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
