<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%
//�������ƣ�LAComInInit.jsp
//�����ܣ�
//�������ڣ�2009-09-30
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     String gToday = PubFun.getCurrentDate(); //���ҳ��ؼ��ĳ�ʼ����
    GlobalInput tGI = new GlobalInput();
     tGI=(GlobalInput)session.getValue("GI");//���ҳ��ؼ��ĳ�ʼ����
     
    	String sTranDay = DateUtil.getCur10Date();
     	String sArrayName1 = "������";	 
		String sArrayName2 = "���ý��";	
		String sArrayName3 = "�������";	
		String sArrayName4 = "��������";	
		String sArrayName5 = "��Ʒ����";	
		String sArrayName6 = "��Ʒ����";	
		String sArrayName7 = "��������";	
		String sArrayName8 = "��������";	
		
		
		String sArrayLength1 = "80"; 
		String sArrayLength2 = "50"; 
		String sArrayLength3 = "50"; 
		String sArrayLength4 = "50"; 
		String sArrayLength5 = "50"; 
		String sArrayLength6 = "200";
		String sArrayLength7 = "60";
		String sArrayLength8 = "60";
		
%>
<script language="JavaScript">
function resetForm(){
	
	fm.PolicyNo.value = "";
	fm.PremTypeCode.value="";
	fm.PremTypeCodeName.value="";
	fm.ProductCode.value="";
	fm.ProductName.value="";

	initForm();
	
}

function initForm() {
	fm.TranDay.value = "<%=sTranDay%>";
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
		
		
		QueryGrid = new MulLineEnter("fm", "QueryGrid");
		QueryGrid.displayTitle = 1;
		QueryGrid.locked = 0;
		QueryGrid.mulLineCount = 0;
		QueryGrid.hiddenPlus = 1;
		QueryGrid.hiddenSubtraction = 1;
		QueryGrid.loadMulLine(mArray);
	} catch(ex) {
	  alert(ex);
	}
}
</script>