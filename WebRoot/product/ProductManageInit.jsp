<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.schema.LDComSchema"%>
<%@page import="com.sinosoft.lis.db.LDComDB"%>

<%
GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
if (null == cGlobalInput) {
	cGlobalInput = new GlobalInput();
}


String sManageCom = cGlobalInput.ManageCom;
%>

<script language="javascript">
var cManageCom = "<%=cGlobalInput.ManageCom%>";	//ȫ�ֱ�������¼��½�������
var cOperator = "<%=cGlobalInput.Operator%>";	//ȫ�ֱ�������¼��¼�û�
var cManageComName="<%=cGlobalInput.ManageComName%>";
var bArrayName1="��������";
var bArrayName2="���в�Ʒ����";
var bArrayName3="��Ʒ����"; 
var bArrayName4="��Ʒ����";
var bArrayName5="��Ʒ��������";
var bArrayName6="��Ʒͣ������";
var bArrayName7="��Ʒϵͳ״̬";
var bArrayName8="���д���";
var bArrayName9 = "��Ʒ����";
var bArrayName10 = "��������";


var bArrayLength1 = "20"; 
var bArrayLength2 = "20"; 
var bArrayLength3 = "20"; 
var bArrayLength4 = "80"; 
var bArrayLength5 = "30"; 
var bArrayLength6 = "30";
var bArrayLength7 = "20";
var bArrayLength8 = "0";
var bArrayLength9 = "0";
var bArrayLength10 = "0";




function initForm() {
    initOK();
	initGrid();
}

function insertForm(){
	
	fm.BankCode.value="";
	fm.BankName.value="";
	
	 bArrayName1="��Ʒ";
     bArrayName2="��������";
     bArrayName3="�ϼ�����";
     bArrayName4="��������";
  
     bArrayName5="��Ʒϵͳ״̬";
     bArrayName6= "��ʼ����";
     bArrayName7= "��������";
     bArrayName8="���д���";
     bArrayName9 = "��Ʒ����";
     bArrayName10= "��������";
    

     
     bArrayLength1 = "80"; 
     bArrayLength2 = "20"; 
     bArrayLength3 = "20"; 
     bArrayLength4 = "20"; 
     bArrayLength5 = "20";
     bArrayLength6 = "0";
     bArrayLength7 = "0";
     bArrayLength8 = "0";
     bArrayLength9 = "0";
     bArrayLength10 = "0";

     initGrid();
   
}
function initComcode(){
    fm.iComCode.value = cManageCom;
	fm.iComCodeName.value = cManageCom+cManageComName;
}
function initOK() {
	fm.iBankCode.value = "";
	fm.iBankName.value = "";
	fm.iRiskCode.value = "";
	fm.iRiskName.value = "";
	fm.iActivityCode.value = "";
	fm.ProStateCode.value="0";
	fm.ProStateName.value="����ͣ��";
	fm.iComCode.value = cManageCom;
	fm.iComCodeName.value = cManageCom+cManageComName;
	fm.iStartDate.value = "";
	fm.iEndDate.value = "";
	fm.ProCode.value="";
	fm.iProductCode.value="";
	//fm.iComCodeName.value = "";
	
	   bArrayName1="��������";
       bArrayName2="��Ʒ����";
       bArrayName3="AS400����";
       bArrayName4="��Ʒ����";
       bArrayName5="��Ʒ��������";
       bArrayName6="��Ʒͣ������";
       bArrayName7="��Ʒϵͳ״̬";
       bArrayName8="���д���";
       bArrayName9 = "��Ʒ����";
       bArrayName10 = "��������";


       bArrayLength1 = "20"; 
       bArrayLength2 = "20"; 
       bArrayLength3 = "20"; 
       bArrayLength4 = "80"; 
       bArrayLength5 = "30"; 
       bArrayLength6 = "30";
       bArrayLength7 = "20";
       bArrayLength8 = "0";
       bArrayLength9 = "0";
       bArrayLength10 = "0";

       initGrid();
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
		mArray[1][0] = bArrayName1;
		mArray[1][1] = bArrayLength1;
		mArray[1][2] = 40;
		mArray[1][3] = 0;
		
		mArray[2] = new Array();
		mArray[2][0] = bArrayName2;
		mArray[2][1] = bArrayLength2;
		mArray[2][2] = 40;
		mArray[2][3] = 0;
		
		mArray[3] = new Array();
		mArray[3][0] = bArrayName3;
		mArray[3][1] = bArrayLength3;
		mArray[3][2] = 40;
		mArray[3][3] = 0;

		mArray[4] = new Array();
		mArray[4][0] = bArrayName4;
		mArray[4][1] = bArrayLength4;
		mArray[4][2] = 40;
		mArray[4][3] = 0;

		mArray[5] = new Array();
		mArray[5][0] = bArrayName5;
		mArray[5][1] = bArrayLength5;
		mArray[5][2] = 40;
		mArray[5][3] = 0;

		mArray[6] = new Array();
		mArray[6][0] = bArrayName6;
		mArray[6][1] = bArrayLength6;
		mArray[6][2] = 40;
		mArray[6][3] = 0;
		
		mArray[7] = new Array();
		mArray[7][0] = bArrayName7;
		mArray[7][1] = bArrayLength7;
		mArray[7][2] = 200;
		mArray[7][3] = 0;
		
		mArray[8] = new Array();
		mArray[8][0] = bArrayName8;
		mArray[8][1] = bArrayLength8;
		mArray[8][2] = 200;
		mArray[8][3] = 0;
		
		mArray[9] = new Array();
		mArray[9][0] = bArrayName9;
		mArray[9][1] = bArrayLength9;
		mArray[9][2] = 200;
		mArray[9][3] = 0;
		
		mArray[10] = new Array();
		mArray[10][0] = bArrayName10;
		mArray[10][1] = bArrayLength10;
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
