<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<%
GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
if (null == cGlobalInput) {
	cGlobalInput = new GlobalInput();
}

String day = DateUtil.getCur10Date();
//System.out.println("ITPrintInput AgentCom:" + cGlobalInput.AgentCom);
//System.out.println("ITPrintInput ComCode:" + cGlobalInput.ComCode);
//System.out.println("ITPrintInput ManageCom:" + cGlobalInput.ManageCom);
//System.out.println("ITPrintInput Operator:" + cGlobalInput.Operator);
%>

<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script src="MissInfoUp.js"></script> 
<script language="JavaScript">

function initForm() {
	fm.StartDay.value ="<%=day%>";
	fm.EndDay.value="<%=day%>";
	initBox();
	initQueryGrid();
}

function initQueryGrid()
{
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=20;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	  iArray[1]=new Array();
      iArray[1][0]="������";         		//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=150;            			//�����ֵ
      iArray[1][3]=0; 
      
      iArray[2]=new Array();
      iArray[2][0]="������֤������";         		//����
      iArray[2][1]="90px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ����֤������";         		//����
      iArray[3][1]="90px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="Ͷ����֤����";         		//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="Ͷ���������˺�";         		//����
      iArray[5][1]="120px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;
      
      iArray[6]=new Array();
      iArray[6][0]="Ͷ������";         		//����
      iArray[6][1]="150px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=0;
      
      iArray[7]=new Array();
      iArray[7][0]="�ͻ����";         		//����
      iArray[7][1]="120px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;
      
      iArray[8]=new Array();
      iArray[8][0]="�˱�ԭ��";         		//����
      iArray[8][1]="150px";            		//�п�
      iArray[8][2]=300;            			//�����ֵ�˱����ɱ���
      iArray[8][3]=0;
      
      iArray[9]=new Array();
      iArray[9][0]="�˱����ɱ���";         		//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=300;            			//�����ֵ
      iArray[9][3]=0;
      
      iArray[10]=new Array();
      iArray[10][0]="�ϴ�����";         		//����
      iArray[10][1]="80px";            		//�п�
      iArray[10][2]=200;            			//�����ֵ
      iArray[10][3]=0;

     QueryGrid = new MulLineEnter( "fm" , "QueryGrid" ); 

    //��Щ���Ա�����loadMulLineǰ
    QueryGrid.displayTitle = 1;
    QueryGrid.locked=0;
    QueryGrid.mulLineCount = 0;
    QueryGrid.hiddenPlus=1;
    QueryGrid.hiddenSubtraction=1;
    QueryGrid.canSel=1;
    QueryGrid.loadMulLine(iArray);  
  
      }
      catch(ex)
      {
        alert(ex);
      } 
}

</script>