<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
	//�������ƣ�ITPrintInit.jsp
	//�����ܣ�����ʹ���������
	//�������ڣ�2011-07-26
	//������  ��ZHANGHJ
	//���¼�¼��  ������    ��������     ����ԭ��/����
	
%>
<%
GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
if (null == cGlobalInput) {
	cGlobalInput = new GlobalInput();
}
String sArrayName1 = "������";			
String sArrayName2 = "������״̬";
String sArrayName3 = "���д���";
String sArrayName4 = "ϵͳ����"; 


String sArrayLength1 = "70"; 
String sArrayLength2 = "60"; 
String sArrayLength3 = "60"; 
String sArrayLength4 = "60"; 

System.out.println("ITPrintInput AgentCom:" + cGlobalInput.AgentCom);
System.out.println("ITPrintInput ComCode:" + cGlobalInput.ComCode);
System.out.println("ITPrintInput ManageCom:" + cGlobalInput.ManageCom);
System.out.println("ITPrintInput Operator:" + cGlobalInput.Operator);
%>

<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
var cManageCom = "<%=cGlobalInput.ManageCom%>"; //ȫ�ֱ�������¼��½�������
var cOperator = "<%=cGlobalInput.Operator%>"; //ȫ�ֱ�������¼��¼�û�
var cManageComName="<%=cGlobalInput.ManageComName%>";



function initForm() {
    fm.SysFlag.value="";
    fm.SysFlagName.value="***����ϵͳ***";
   // alert(fm.all('FileName').value);
   // var obj = document.getElementById('fm').FileName[1];
    //obj.outerHTML = obj.outerHTML;
     fm.ImportPath.value="";
     document.getElementById('fm').FileName.value="";
   // fm.FileName.value="";
    fm.AreaNo.value="";
    fm.AreaName.value=""; 
    fm.CityNo.value=""; 
    fm.CityName.value="";
    
    fm.ManageCodeName.value=cManageCom+cManageComName;
	fm.ManageCodeNo.value=cManageCom;
	fm.ManageCodeNoOut.value=cManageCom;
	fm.ManageForCheckName.value=cManageCom+cManageComName;
	fm.ManageForCheck.value=cManageCom;
	initBox();
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



function initBox() {
	try {
		fm.all("AreaNo").value = "";
		fm.all("CityNo").value = "";
		fm.all("SysFlag").value = "";
	} catch (re) {
		alert("��ITPrintInit.jsp --> initBox �����з����쳣:��ʼ���������");
	}
}
</script>
