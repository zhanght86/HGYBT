<%@page contentType="text/html;charset=GBK" %>
<!--�û�У����-->
<SCRIPT src="/common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
//���ذ�ť��ʼ��
var str = "";

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
    fm.all('StartDate').value = '';
    fm.all('EndDate').value = '';
	  fm.all('BankCode').value = '';
	  fm.all('BankName').value = '';
	  fm.all('ContNo').value = '';
  }
  catch(ex)
  {
  	<%
  		String errorMes = "��YBTContInfoQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!";
  	%>
    alert("<%= errorMes %>");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("��YBTContInfoQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	  initybtPolGrid();	
  }
  catch(re)
  {
    alert("YBTContInfoQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initybtPolGrid()
{       

<%
String order = "���";
String ProposalPrtNo = "Ͷ������";
String contno = "������";
String appntname = "Ͷ��������";
String insuredname = "����������";
String ProductId = "���ֱ���";
//String paymode = "�ɷѷ�ʽ";
String Bak1 = "����1";
String prem = " ����(Ԫ)";
String amnt = " ����(Ԫ)";
String State = "����״̬";
String SignDate = "ǩ������";
String TranCom = "���׻���";
String NodeNo = "��������";
String agentname ="�����������";
String BankOperator ="��Ա����";
%>
	                        
    var iArray = new Array();    
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="<%= order %>"; //���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

     
     
      iArray[1]=new Array();
      iArray[1][0]="<%= contno %>"; //������";         		//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=110;            			//�����ֵ
      iArray[1][3]=0;  

      iArray[2]=new Array();
      iArray[2][0]="<%=ProposalPrtNo %>"; //Ͷ������";         		//����
      iArray[2][1]="80";            		//�п�
      iArray[2][2]=110;            			//�����ֵ
      iArray[2][3]=0;  

      iArray[3]=new Array();
      iArray[3][0]="<%= appntname %>"; //Ͷ��������";         		//����
      iArray[3][1]="40px";            		//�п�
      iArray[3][2]=90;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="<%= insuredname %>"; //����������";         		//����
      iArray[4][1]="40px";            		//�п�
      iArray[4][2]=110;            			//�����ֵ
      iArray[4][3]=0;  
      
      iArray[5]=new Array();
      iArray[5][0]="<%= ProductId %>"; //���ֱ���";         		//����
      iArray[5][1]="40px";            		//�п�
      iArray[5][2]=90;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[6]=new Array();
      iArray[6][0]="<%= amnt %>"; //����";         		//����
      iArray[6][1]="50px";            		//�п�
      iArray[6][2]=70;            			//�����ֵ
      iArray[6][3]=0;  
   
      iArray[7]=new Array();
      iArray[7][0]="<%= prem %>"; //����";         		//����
      iArray[7][1]="50px";            		//�п�
      iArray[7][2]=110;            			//�����ֵ
      iArray[7][3]=0; 
      
      iArray[8]=new Array();
      iArray[8][0]="<%= State %>"; //����״̬";         		//����
      iArray[8][1]="40px";            		//�п�
      iArray[8][2]=110;            			//�����ֵ
      iArray[8][3]=0; 
      
      iArray[9]=new Array();
      iArray[9][0]="<%= SignDate %>"; //ԭ������Ч����  ��ǩ������";         		//����
      iArray[9][1]="60px";            		//�п�
      iArray[9][2]=110;            			//�����ֵ
      iArray[9][3]=0;  
      
      iArray[10]=new Array();
      iArray[10][0]="<%=TranCom%>"; //��������";         		//����
      iArray[10][1]="60px";            		//�п�
      iArray[10][2]=160;            			//�����ֵ
      iArray[10][3]=0;  
      

      iArray[11]=new Array();
      iArray[11][0]="<%= NodeNo %>"; //��������";         		//����
      iArray[11][1]="60px";            		//�п�
      iArray[11][2]=110;            			//�����ֵ
      iArray[11][3]=0;  
      
      iArray[12]=new Array();
      iArray[12][0]="<%= agentname %>"; //�ͻ����� ������";         		//����
      iArray[12][1]="60px";            		//�п�
      iArray[12][2]=110;            			//�����ֵ
      iArray[12][3]=0;  
      
      iArray[13]=new Array();
      iArray[13][0]="<%= BankOperator %>"; //��Ա����";         		//����
      iArray[13][1]="60px";            		//�п�
      iArray[13][2]=110;            			//�����ֵ
      iArray[13][3]=0;  
      
      ybtPolGrid1 = new MulLineEnter( "fm" , "ybtPolGrid1" ); 
      //��Щ���Ա�����loadMulLineǰ
      ybtPolGrid1.mulLineCount = 0;   
      ybtPolGrid1.displayTitle = 1;
      ybtPolGrid1.locked = 1;
      ybtPolGrid1.canSel = 1;
      ybtPolGrid1.hiddenPlus = 1;
      ybtPolGrid1.hiddenSubtraction = 1;
      ybtPolGrid1.loadMulLine(iArray); 
      
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>     