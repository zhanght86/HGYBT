<%
//�������ƣ�ComClientQuery.js
//�����ܣ�
//�������ڣ�2003-1-10
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {                                   
  	fm.all('BlacklistNo').value="";
  	fm.all('BlacklistType').value="";
  }
  catch(ex)
  {
    alert("��ComClientQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");      
//    setOption("sex","0=��&1=Ů&2=����");        
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");   
  }
  catch(ex)
  {
    alert("��ComClientQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	  initComClientGrid();
  }
  catch(re)
  {
    alert("ComClientQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
/************************************************************
 *               
 *���룺          û��
 *�����          û��
 *���ܣ�          ��ʼ��ComClientGrid
 ************************************************************
 */
var ComClientGrid;          //����Ϊȫ�ֱ������ṩ��displayMultilineʹ��
function initComClientGrid()
  {                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";         //����
        iArray[0][1]="30px";         //����
        iArray[0][2]=100;         //����
        iArray[0][3]=0;         //����

        iArray[1]=new Array();
		    iArray[1][0]="�ͻ�����";         //����
		    iArray[1][1]="150px";         //���
		    iArray[1][2]=100;         //��󳤶�
		    iArray[1][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
		
		    iArray[2]=new Array();
		    iArray[2][0]="�ͻ�����";         //����
		    iArray[2][1]="150px";         //���
		    iArray[2][2]=100;         //��󳤶�
		    iArray[2][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
		
		    iArray[3]=new Array();
		    iArray[3][0]="�ͻ�����";         //����
		    iArray[3][1]="100px";         //���
		    iArray[3][2]=100;         //��󳤶�
		    iArray[3][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����



  
        ComClientGrid = new MulLineEnter( "fm" , "ComClientGrid" ); 

        //��Щ���Ա�����loadMulLineǰ
        ComClientGrid.mulLineCount = 10;   
        ComClientGrid.displayTitle = 1;
        ComClientGrid.hiddenPlus=1;
        ComClientGrid.hiddenSubtraction=1;
        ComClientGrid.canSel=1; 
        ComClientGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("��ʼ��ComClientGridʱ����"+ ex);
      }
    }


</script>