<%@page contentType="text/html;charset=GBK"%>
<%
  //�������ƣ�ybtAuthorInputInit.jsp
  //�����ܣ�����ͨ���׿���
  //�������ڣ�2006-03-13 15:12:33
  //������  ��liuyx���򴴽�
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT><%//���ҳ��ؼ��ĳ�ʼ����%>
<script language="JavaScript">
function initInpBox()
{
  try
  { 
    fm.all('ManageCom').value = '8621';
    fm.all('BankCode').value = '';
   fm.all('BankBranch').value = '';
    fm.all('BankNode').value = '';
    fm.all('FuncFlag').value = '';
    fm.all('BankCodeName').value = '';
    fm.all('FuncFlagName').value = '';
    
   
  }
  catch(ex)
  {
    alert("��LAComInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}
function initSelBox()
{
  try
  {
  
  }
  catch(ex)
  {
    alert("��LAComInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    
    initInpBox();    
    
    initSelBox();
    initLKAuthorGrid();
    
    
  }
  catch(re)
  {
    alert("LAComInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
var LKAuthorGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
// Ͷ������Ϣ�б�ĳ�ʼ��
function initLKAuthorGrid()
{
    var iArray = new Array();
    //var i11Array = getAgentGradeStr();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���д���";         		//����
      iArray[1][1]="50px";            		//�п� 
      iArray[1][2]=200;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
 	  iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=200;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

 	  iArray[3]=new Array();
      iArray[3][0]="���е�����";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;   
                 			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4]=new Array();
      iArray[4][0]="����������";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="��������";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="��ֹ��������";         		//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      iArray[7]=new Array();
      iArray[7][0]="��ֹ��������";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    
      LKAuthorGrid = new MulLineEnter( "fm" , "LKAuthorGrid" );
      //��Щ���Ա�����loadMulLineǰ
      LKAuthorGrid.displayTitle = 1;
      LKAuthorGrid.locked = 0;
      LKAuthorGrid.mulLineCount = 0;
      LKAuthorGrid.hiddenPlus = 1;
      LKAuthorGrid.hiddenSubtraction = 1;
      LKAuthorGrid.canSel = 1;

      LKAuthorGrid.loadMulLine(iArray);


     
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
