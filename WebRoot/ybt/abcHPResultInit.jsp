<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<%
GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
if (null == cGlobalInput) {
	cGlobalInput = new GlobalInput();
}
%>

<script language="javascript">
var cManageCom = "<%=cGlobalInput.ManageCom%>";	//ȫ�ֱ�������¼��½�������
var cOperator = "<%=cGlobalInput.Operator%>";	//ȫ�ֱ�������¼��¼�û�

function initForm() {
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
		mArray[1][0] = "���κ�";
		mArray[1][1] = "80px";
		mArray[1][2] = 200;
		mArray[1][3] = 0;

		mArray[2] = new Array();
		mArray[2][0] = "��������";
		mArray[2][1] = "100px";
		mArray[2][2] = 200;
		mArray[2][3] = 0;

		mArray[3] = new Array();
		mArray[3][0] = "����������";
		mArray[3][1] = "100px";
		mArray[3][2] = 200;
		mArray[3][3] = 0;

		mArray[4] = new Array();
		mArray[4][0] = "��������";
		mArray[4][1] = "80px";
		mArray[4][2] = 200;
		mArray[4][3] = 0;

		mArray[5] = new Array();
		mArray[5][0] = "������";
		mArray[5][1] = "100px";
		mArray[5][2] = 300;
		mArray[5][3] = 0;
		
		mArray[6] = new Array();
		mArray[6][0] = "Ͷ������";
		mArray[6][1] = "100px";
		mArray[6][2] = 200;
		mArray[6][3] = 0;
		
		mArray[7] = new Array();
		mArray[7][0] = "Ͷ��������";
		mArray[7][1] = "100px";
		mArray[7][2] = 200;
		mArray[7][3] = 0;
		
		mArray[8] = new Array();
		mArray[8][0] = "����������";
		mArray[8][1] = "100px";
		mArray[8][2] = 200;
		mArray[8][3] = 0;

		mArray[9] = new Array();
		mArray[9][0] = "��ȡ���";
		mArray[9][1] = "100px";
		mArray[9][2] = 200;
		mArray[9][3] = 0;

		mArray[10] = new Array();
		mArray[10][0] = "������";
		mArray[10][1] = "100px";
		mArray[10][2] = 200;
		mArray[10][3] = 0;

		mArray[11] = new Array();
		mArray[11][0] = "������Ϣ";
		mArray[11][1] = "100px";
		mArray[11][2] = 200;
		mArray[11][3] = 0;
		
	
		
		QueryGrid = new MulLineEnter("fm", "AbcHPResultGrid");
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
