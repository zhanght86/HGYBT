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
	fm.iManageCom.value = cManageCom;
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
		mArray[1][0] = "����";
		mArray[1][1] = "30px";
		mArray[1][2] = 200;
		mArray[1][3] = 0;

		mArray[2] = new Array();
		mArray[2][0] = "��������";
		mArray[2][1] = "70px";
		mArray[2][2] = 200;
		mArray[2][3] = 0;

		mArray[3] = new Array();
		mArray[3][0] = "��������";
		mArray[3][1] = "150px";
		mArray[3][2] = 200;
		mArray[3][3] = 0;

		mArray[4] = new Array();
		mArray[4][0] = "������������";
		mArray[4][1] = "250px";
		mArray[4][2] = 300;
		mArray[4][3] = 0;
		
		mArray[5] = new Array();
		mArray[5][0] = "�������";
		mArray[5][1] = "70px";
		mArray[5][2] = 200;
		mArray[5][3] = 0;
		
		mArray[6] = new Array();
		mArray[6][0] = "�����������";
		mArray[6][1] = "200px";
		mArray[6][2] = 200;
		mArray[6][3] = 0;
		
		mArray[7] = new Array();
		mArray[7][0] = "ӳ���";
		mArray[7][1] = "50px";
		mArray[7][2] = 200;
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
