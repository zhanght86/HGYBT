<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<% 
	//�������ƣ�TestYBTContInit.jsp
	//�����ܣ����ȷ�� 
	//�������ڣ�2010-01-20 16:43:36
	//������  ����ٻ
	//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%  
	PubFun1 pubfun = new PubFun1();
	String strTransExeDate = DateUtil.getCur10Date();
	
	String strTransRefGUID = pubfun.CreateMaxNo("TransNo",16);
	String strHOAppFormNumber = "1003"+pubfun.CreateMaxNo("PrtNo",12);
	
%>
<script language="JavaScript">
function initForm()
{ 
	try 
	{
		initInputBox();
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->InitForm �����з����쳣:��ʼ���������!");
	}
}

function initBox()
{
try { 
//������Ϣ
	//���ж˽�������
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//������ˮ��
	fm.TransRefGUID.value = '<%=strTransRefGUID%>';
	//��������
	fm.RegionCode.value = '';
	//�������
	fm.Branch.value = '';
	//��Ա����
	fm.Teller.value = '';
	
	//Ͷ�����
	fm.HOAppFormNumber.value = '';
	//������
	fm.PolNumber.value = '';
 	//��֤ӡˢ�� 
	fm.ProviderFormNumber.value = '';
	//ԭ��֤ӡˢ�� 
	fm.OriginalProviderFormNumber.value = '';  

//
	fm.ip.value = '127.0.0.1';
	fm.port.value = '35000'; 
	fm.tranFlagCode.value = ''; 
	fm.tranFlagCodeName.value = '';
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initBox �����з����쳣:��ʼ���������!");
	}
}


function initInputBox() 
{
	try {
//������Ϣ
	//���ж˽�������
	fm.TransExeDate.value = '<%=strTransExeDate%>';
	//������ˮ��
	fm.TransRefGUID.value = '<%=strTransRefGUID%>';
	//�������� 
	fm.RegionCode.value = '10011';
	//�������  
	fm.Branch.value = '14800';
	//��Ա����
	fm.Teller.value = '00001';
	//Ͷ�����
	fm.HOAppFormNumber.value = '';
	//����ӡˢ�� 
	fm.PolNumber.value = '';
	//��֤ӡˢ�� 
	fm.ProviderFormNumber.value = '';
	//ԭ��֤ӡˢ�� 
	fm.OriginalProviderFormNumber.value = '';
    
	
//
	fm.ip.value = '127.0.0.1';
	fm.port.value = '35000'; 
	fm.tranFlagCode.value = '1015'; 
	fm.tranFlagCodeName.value = '���ճ���';
	}
	catch(re)
	{
		alert("TestYBTContInit.jsp-->initInputBox �����з����쳣:��ʼ���������!");
	}
}

// ����ҵ����Ϣ�б�ĳ�ʼ��
function initPolGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="���ɱ���";
		iArray[1][1]="0px";
		iArray[1][2]=100;
		iArray[1][3]=3;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="0px";
		iArray[2][2]=100;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="��������";
		iArray[3][1]="80px";
		iArray[3][2]=200;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="ʵ������";
		iArray[4][1]="100px";
		iArray[4][2]=200;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="���ѷ�ʽ";
		iArray[5][1]="80px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="���ѷ�ʽ����";
		iArray[6][1]="0px";
		iArray[6][2]=100;
		iArray[6][3]=3;

		iArray[7]=new Array();
		iArray[7][0]="���ѽ��";
		iArray[7][1]="80px";
		iArray[7][2]=100;
		iArray[7][3]=0;
		
		iArray[8]=new Array();
		iArray[8][0]="��������";
		iArray[8][1]="80px";
		iArray[8][2]=100;
		iArray[8][3]=0;		
		
		iArray[9]=new Array();
		iArray[9][0]="�����˻�";
		iArray[9][1]="80px";
		iArray[9][2]=100;
		iArray[9][3]=0;				

		PolGrid = new MulLineEnter( "fm" , "PolGrid" );
		//��Щ���Ա�����loadMulLineǰ
		PolGrid.mulLineCount = 0;
		PolGrid.displayTitle = 1;
		PolGrid.locked = 1;
		PolGrid.canSel = 0;
		PolGrid.canChk = 1;
		PolGrid.hiddenPlus = 1;
		PolGrid.hiddenSubtraction = 1;
		PolGrid.chkBoxEventFuncName = "AddMoney";
		PolGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

// �����б���Ϣ�ĳ�ʼ��
function initRiskList()
{                               
    var iArray = new Array();
      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         		  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";        			//�п�
      iArray[0][2]=30;          			  //�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���ֱ���";    	        //����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=50;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��
      iArray[1][4]="ProductCode";           //���ô���

      iArray[2]=new Array();
      iArray[2][0]="���ձ���";    	        //����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=50;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��
      iArray[2][4]="ProductCode";           //���ô���

      iArray[3]=new Array();
      iArray[3][0]="���ڽɷ��˺�";    	            //����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=50;            			//�����ֵ
      iArray[3][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��Ч����";    	            //����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=50;            			//�����ֵ
      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������3��ʾ����

      iArray[5]=new Array();
      iArray[5][0]="����";    	            //����
      iArray[5][1]="70px";            		//�п�
      iArray[5][2]=50;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="����";    	            //����
      iArray[6][1]="70px";            		//�п�
      iArray[6][2]=50;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="����";    	            //����
      iArray[7][1]="50px";            		//�п�
      iArray[7][2]=50;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="�ɷѷ�ʽ";    	            //����
      iArray[8][1]="60px";            		//�п�
      iArray[8][2]=50;            			//�����ֵ
      iArray[8][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[8][10]="payintv";
      iArray[8][11]="0|^0|�޹�|^1|����|^2|�½�|^3|����|^4|�����|^5|���|^7|�����ڽ�|^9|����"; 

      iArray[9]=new Array();
      iArray[9][0]="�ɷ���������";    	            //����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=50;            			//�����ֵ
      iArray[9][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[9][10]="payflag";
      iArray[9][11]="0|^0|�޹�|^1|����|^2|�����޽�|^3|����ĳȷ������|^4|�����ɷ�|^5|�����ڽ�";

      iArray[10]=new Array();
      iArray[10][0]="�ɷ�����";    	            //����
      iArray[10][1]="60px";            		//�п�
      iArray[10][2]=50;            			//�����ֵ
      iArray[10][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[11]=new Array();
      iArray[11][0]="��ȡ��������";    	            //����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=50;            			//�����ֵ
      iArray[11][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[11][10]="getflag";
      iArray[11][11]="0|^0|�޹�|^1|һ������ȡ|^2|����|^3|����|^4|������|^5|����";

      iArray[12]=new Array();
      iArray[12][0]="��ȡ����";    	            //����
      iArray[12][1]="60px";            		//�п�
      iArray[12][2]=50;            			//�����ֵ
      iArray[12][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[13]=new Array();
      iArray[13][0]="������������";    	            //����
      iArray[13][1]="80px";            		//�п�
      iArray[13][2]=50;            			//�����ֵ
      iArray[13][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[13][10]="insuflag";
      iArray[13][11]="0|^0|�޹�|^1|������|^2|�����ޱ�|^3|����ĳȷ������|^4|���±�|^5|���챣";
      
      iArray[14]=new Array();
      iArray[14][0]="��������";    	            //����
      iArray[14][1]="60px";            		//�п�
      iArray[14][2]=50;            			//�����ֵ
      iArray[14][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      iArray[15]=new Array();
      iArray[15][0]="�Զ��潻���";    	            //����
      iArray[15][1]="80px";            		//�п�
      iArray[15][2]=50;            			//�����ֵ
      iArray[15][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      iArray[15][10]="AutoPayFlag";
      iArray[15][11]="0|^0|����|^1|�潻";
      iArray[15][14]="0";
      
      iArray[16]=new Array();
      iArray[16][0]="����������";    	            //����
      iArray[16][1]="80px";            		//�п�
      iArray[16][2]=50;            			//�����ֵ
      iArray[16][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[16][10]="BonusPayMode";
      iArray[16][11]="0|^0|��������|^1|�޺�������";       
      iArray[16][14]="0";
      
      iArray[17]=new Array();
      iArray[17][0]="������ȡ��ʽ";    	            //����
      iArray[17][1]="80px";            		//�п�
      iArray[17][2]=50;            			//�����ֵ
      iArray[17][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[17][10]="BonusGetMode";
      iArray[17][11]="0|^1|�ֽ�����|^2|�ۻ���Ϣ|^3|�������|^4|�ֽ�";       
      iArray[17][14]="1";
      
      iArray[18]=new Array();
      iArray[18][0]="�������";    	            //����
      iArray[18][1]="80px";            		//�п�
      iArray[18][2]=50;            			//�����ֵ
      iArray[18][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[18][10]="SubFlag";
      iArray[18][11]="0|^0|����|^1|�����";       
      iArray[18][14]="0";
      
      iArray[19]=new Array();
      iArray[19][0]="��ͬ���鴦��ʽ";    	            //����
      iArray[19][1]="100px";            		//�п�
      iArray[19][2]=50;            			//�����ֵ
      iArray[19][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[19][10]="DEALTYPE";
      iArray[19][11]="0|^1|����|^2|�ٲ�";       
      iArray[19][14]="1";
      
      iArray[20]=new Array();
      iArray[20][0]="������֪��־";    	            //����
      iArray[20][1]="80px";            		//�п�
      iArray[20][2]=50;            			//�����ֵ
      iArray[20][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[20][10]="HealthFlag";
      iArray[20][11]="0|^N|��|^Y|��";       
      iArray[20][14]="N";
      
      iArray[21]=new Array();
      iArray[21][0]="�ر�Լ��";    	            //����
      iArray[21][1]="200px";            		//�п�
      iArray[21][2]=50;            			//�����ֵ
      iArray[21][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      RiskList = new MulLineEnter( "fm" , "RiskList" ); 
      //��Щ���Ա�����loadMulLineǰ
      RiskList.mulLineCount = 0;   
      RiskList.displayTitle = 1;
      RiskList.loadMulLine(iArray);  
      
    } catch(ex) {
      alert(ex);
    }
   }
    function initBnfList(){
     var iArray = new Array();
      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         		  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";        			//�п�
      iArray[0][2]=30;          			  //�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="����������";    	        //����
      iArray[1][1]="70px";            		//�п�
      iArray[1][2]=50;            			//�����ֵ
      iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��
      iArray[1][10]="BnfType";
      iArray[1][11]="0|^0|����������|^1|����������|^2|����������"; 
      iArray[1][14]="1";          

      iArray[2]=new Array();
      iArray[2][0]="����˳��";    	        //����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=50;            			//�����ֵ
      iArray[2][3]=2;   
      iArray[2][14]="1";                    //���ô���

      iArray[3]=new Array();
      iArray[3][0]="����������";    	            //����
      iArray[3][1]="70px";            		//�п�
      iArray[3][2]=50;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�������Ա�";    	            //����
      iArray[4][1]="70px";            		//�п�
      iArray[4][2]=40;            			//�����ֵ
      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������3��ʾ����
      iArray[4][10]="BnfSex";
      iArray[4][11]="0|^0|��|^1|Ů|^2|����"; 
      iArray[4][14]="1";   
      
      iArray[5]=new Array();
      iArray[5][0]="����������(YYYY-MM-DD)";  //����
      iArray[5][1]="150px";            		//�п�
      iArray[5][2]=50;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][9]="����������|NOTNULL";

      iArray[6]=new Array();
      iArray[6][0]="������֤������";    	            //����
      iArray[6][1]="120px";            		//�п�
      iArray[6][2]=30;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[6][10]="BnfIDType";
      iArray[6][11]="0|^15|�������֤^16|��ʱ���֤^17|�������֤��^18|�侯���֤��^19|ͨ��֤^20|����^21|����^22|��ʱ����^23|���ڲ�^24|�߾�֤^25|����˾���֤^26|���֤��"; 
      iArray[6][14]="15";   

      iArray[7]=new Array();
      iArray[7][0]="������֤������";    	            //����
      iArray[7][1]="120px";            		//�п�
      iArray[7][2]=50;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="����ٷ���";    	            //����
      iArray[8][1]="70px";            		//�п�
      iArray[8][2]=30;            			//�����ֵ
      iArray[8][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[8][14]="100";
      
      iArray[9]=new Array();
      iArray[9][0]="�뱻���˹�ϵ(����)";    	            //����
      iArray[9][1]="120px";            		//�п�
      iArray[9][2]=30;            			//�����ֵ
      iArray[9][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[9][14]="1";

     
      
      
      BnfList = new MulLineEnter( "fm" , "BnfList" ); 
      //��Щ���Ա�����loadMulLineǰ
      BnfList.mulLineCount = 0;   
      BnfList.displayTitle = 1;
      BnfList.loadMulLine(iArray);  
       }catch(ex) {
      alert(ex);
    }
  }
    

</script>
