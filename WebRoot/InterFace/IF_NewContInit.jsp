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
     
     String sStartDay = DateUtil.getCur10Date();
 	 String sEndDay = DateUtil.getCur10Date();
%>                            

<script language="JavaScript">
	

function initForm()
{
  try
  {
      fm.StartDay.value = "<%=sStartDay%>";
      fm.EndDay.value = "<%=sEndDay%>";
      fm.currentDate.value = "<%=sStartDay%>";
	  initQueryGrid();
  }
  catch(ex)
  {
    alert("YBTdownloadInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
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
		mArray[1][0] = "����";
		mArray[1][1] = "40px";
		mArray[1][2] = 50;
		mArray[1][3] = 0;
		
		mArray[2] = new Array();
		mArray[2][0] = "��������";
		mArray[2][1] = "60px";
		mArray[2][2] = 100;
		mArray[2][3] = 0;
		
		mArray[3] = new Array();
		mArray[3][0] = "�ӿ��ļ�״̬";
		mArray[3][1] = "80px";
		mArray[3][2] = 100;
		mArray[3][3] = 0;

		mArray[4] = new Array();
		mArray[4][0] = "�ļ�����";
		mArray[4][1] = "120px";
		mArray[4][2] = 200;
		mArray[4][3] = 0;

		mArray[5] = new Array();
		mArray[5][0] = "�ļ���";
		mArray[5][1] = "90px";
		mArray[5][2] = 200;
		mArray[5][3] = 0;

		mArray[6] = new Array();
		mArray[6][0] = "��������";
		mArray[6][1] = "60px";
		mArray[6][2] = 200;
		mArray[6][3] = 0;
		
		mArray[7] = new Array();
		mArray[7][0] = "����ʱ��";
		mArray[7][1] = "50px";
		mArray[7][2] = 200;
		mArray[7][3] = 0;
	
		// mArray[8]=new Array();
    //  mArray[8][0]="����";         		
    //  mArray[8][1]="30px";            		
   //   mArray[8][2]=200;            			
   //   mArray[8][3]=0;              			
      
      mArray[8]=new Array();
      mArray[8][0]="�������";         		
      mArray[8][1]="0px";            		
      mArray[8][2]=200;            			
      mArray[8][3]=0;              			
      
      mArray[9]=new Array();
      mArray[9][0]="�ļ�״̬��־";         	
      mArray[9][1]="0px";            		
      mArray[9][2]=200;            			
      mArray[9][3]=0; 
      
      mArray[10]=new Array();
      mArray[10][0]="LogNo";         	
      mArray[10][1]="0px";            		
      mArray[10][2]=200;            	
      mArray[10][3]=0; 
      
      mArray[11]=new Array();
      mArray[11][0]="�ļ�·��";         	
      mArray[11][1]="0px";            		
      mArray[11][2]=200;            	
      mArray[11][3]=0; 
		

		QueryGrid = new MulLineEnter("fm", "QueryGrid");
		QueryGrid.displayTitle = 1;
		QueryGrid.locked = 0;
		QueryGrid.mulLineCount = 0;
		QueryGrid.hiddenPlus = 1;
		QueryGrid.hiddenSubtraction = 1;
		QueryGrid.canSel = 1;
		QueryGrid.loadMulLine(mArray); 
      }
      catch(ex)
      {
        alert(ex);
      } 
}
</script>