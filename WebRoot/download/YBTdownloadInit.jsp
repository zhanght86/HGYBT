<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
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
%>                            

<script language="JavaScript">
	

function initForm()
{
  try
  { 
    initYBTGrid();
  }
  catch(ex)
  {
    alert("YBTdownloadInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
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
      iArray[1][0]="��������";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      //iArray[1][7]="downLoad";

      iArray[2]=new Array();
      iArray[2][0]="�����ϴ�����";         		//����
      iArray[2][1]="30px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="������С";         		//����
      iArray[4][1]="30px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="��ע";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="·��";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="����";         		//����
      iArray[7][1]="30px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

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