<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<%
GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
if (null == cGlobalInput) {
	cGlobalInput = new GlobalInput();
}
System.out.println("ITPrintInput AgentCom:" + cGlobalInput.AgentCom);
System.out.println("ITPrintInput ComCode:" + cGlobalInput.ComCode);
System.out.println("ITPrintInput ManageCom:" + cGlobalInput.ManageCom);
System.out.println("ITPrintInput Operator:" + cGlobalInput.Operator);
%>

<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script src="MissInfoUp.js"></script> 
<script language="JavaScript">

function initForm() {
	initBox();
	initYBTGrid();
}

function initYBTGrid()
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
      iArray[1][0]="�˻��Ƽ���";         		//����
      iArray[1][1]="50px";            		//�п�
      iArray[1][2]=150;            			//�����ֵ
      iArray[1][3]=0; 
      
      iArray[2]=new Array();
      iArray[2][0]="�˻�����";         		//����
      iArray[2][1]="50px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="������";         		//����
      iArray[3][1]="50px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�����";         		//����
      iArray[4][1]="50px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

     YBTGrid = new MulLineEnter( "fm" , "YBTGrid" ); 

    //��Щ���Ա�����loadMulLineǰ
    YBTGrid.mulLineCount = 0;   
    YBTGrid.displayTitle = 1;
    YBTGrid.locked=1;
    YBTGrid.hiddenPlus=1;
    YBTGrid.hiddenSubtraction=1;
    YBTGrid.canSel=0;
    YBTGrid.canChk=0;
    YBTGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      } 
}

</script>