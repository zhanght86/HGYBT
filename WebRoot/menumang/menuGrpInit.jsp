<%@page import="com.sinosoft.utility.*"%>
<%
//�������ƣ�menuInit.jsp
//�����ܣ�
//�������ڣ�2002-10-10
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script language="JavaScript">
function initInpBox()
{
	try
	{
		fm.all("MenuGrpCode").value = "";
		fm.all("MenuGrpName").value = "";
		fm.all("MenuGrpDescription").value = "";
		fm.all("MenuSign").value = "";
	}
	catch(ex)
	{
		alert("��menuGrpInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initQueryGrpGrid();
		initHideMenuGrpGrid1();
		initHideMenuGrpGrid2();
	}
	catch(re)
	{
		alert("menuGrpInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}

<%
String order = StrTool.unicodeToGBK("���");
String menuName = StrTool.unicodeToGBK("�˵�����");
String menuNodeNum = StrTool.unicodeToGBK("�˵��ڵ����");
String menuGrpName = StrTool.unicodeToGBK("�˵�������");
String menuGrpNum = StrTool.unicodeToGBK("�˵������");
String menuGrpDesc = StrTool.unicodeToGBK("�˵�������");
String menuGrpFlag = StrTool.unicodeToGBK("�˵���־");
String oper = StrTool.unicodeToGBK("����Ա");
%>

function  initHideMenuGrpGrid1()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="<%= order %>";	//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";	//�п�
		iArray[0][2]=10;		//�����ֵ
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="<%= menuName %>"; //"�˵�����";
		iArray[1][1]="140px";
		iArray[1][2]=10;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="<%= menuNodeNum %>"; //"�˵��ڵ����";
		iArray[2][1]="120px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		HideMenuGrpGrid1 = new MulLineEnter( "fm" , "HideMenuGrpGrid1" );
		//��Щ���Ա�����loadMulLineǰ
		HideMenuGrpGrid1.mulLineCount = 0;
		HideMenuGrpGrid1.displayTitle = 1;
		HideMenuGrpGrid1.canChk =1;
		HideMenuGrpGrid1.canSel =0;
		HideMenuGrpGrid1.locked =1;				//�Ƿ�������1Ϊ���� 0Ϊ������
		HideMenuGrpGrid1.hiddenPlus=1;			//�Ƿ�����"+"���һ�б�־��1Ϊ���أ�0Ϊ������
		HideMenuGrpGrid1.hiddenSubtraction=0;	//�Ƿ�����"-"���һ�б�־��1Ϊ���أ�0Ϊ������
		HideMenuGrpGrid1.recordNo=0;			//���������ʼ����Ϊ10�����Ҫ��ҳ��ʾ��������
		HideMenuGrpGrid1.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

function initHideMenuGrpGrid2()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="<%= order %>"; //"���";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="<%= menuName %>"; //"�˵�����";
		iArray[1][1]="140px";
		iArray[1][2]=10;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="<%= menuNodeNum %>"; //"�˵��ڵ����";
		iArray[2][1]="140px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		HideMenuGrpGrid2 = new MulLineEnter( "fm" , "HideMenuGrpGrid2" );
		HideMenuGrpGrid2.mulLineCount = 0;
		HideMenuGrpGrid2.displayTitle = 1;
		HideMenuGrpGrid2.canChk =1;
		HideMenuGrpGrid2.canSel =0;
		HideMenuGrpGrid2.locked =1;
		HideMenuGrpGrid2.hiddenPlus=1;
		HideMenuGrpGrid2.hiddenSubtraction=0;
		HideMenuGrpGrid2.recordNo=0;
		HideMenuGrpGrid2.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

function initQueryGrpGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="<%= order %>"; //"���";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="<%= menuGrpName %>"; //"�˵�������";
		iArray[1][1]="150px";
		iArray[1][2]=10;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="<%= menuGrpNum %>"; //"�˵������";
		iArray[2][1]="100px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="<%= menuGrpDesc %>"; //"�˵�������";
		iArray[3][1]="200px";
		iArray[3][2]=100;
		iArray[3][3]=0;


		iArray[4]=new Array();
		iArray[4][0]="<%= menuGrpFlag %>"; //"�˵���־";
		iArray[4][1]="100px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="<%= oper %>"; //"����Ա";
		iArray[5][1]="100px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		QueryGrpGrid = new MulLineEnter( "fm" , "QueryGrpGrid" );
		QueryGrpGrid.mulLineCount = 0;
		QueryGrpGrid.displayTitle = 1;
		QueryGrpGrid.canChk =0;
		QueryGrpGrid.canSel =1;
		QueryGrpGrid.locked =1;
		QueryGrpGrid.hiddenPlus=1;
		QueryGrpGrid.hiddenSubtraction=1;
		QueryGrpGrid.recordNo=0;
		QueryGrpGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>