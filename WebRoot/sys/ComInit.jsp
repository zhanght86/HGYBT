<%
//�������ƣ�ComInput.jsp
//�����ܣ�
//�������ڣ�2002-08-16 17:44:40
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>

<script language="JavaScript">
function initInpBox()
{
  try
  {
    fm.all('ComCode').value = '';
    fm.all('OutComCode').value = '';
    fm.all('Name').value = '';
    fm.all('ShortName').value = '';
    //fm.all('ComGrade').value = '';
    //fm.all('ComGradeName').value = '';
    fm.all('Address').value = '';
    fm.all('ZipCode').value = '';
    fm.all('Phone').value = '';
    fm.all('Fax').value = '';
    fm.all('EMail').value = '';
    fm.all('WebAddress').value = '';
    //fm.all('SatrapName').value = '';
    //fm.all('Sign').value = '';
    fm.all('UpComCode').value = '';
    fm.all('DelFlg').value = '';
    //fm.all('ComAreaType').value = '';
    //fm.all('ComAreaTypeName').value = '';
    //fm.all('ComCitySize').value = '';
    //fm.all('ComCitySizeName').value = '';
    //fm.all('IsDirUnder').value = '';
    //fm.all('IsDirUnderName').value = '';
	}
  catch(ex)
  {
    alert("��ComInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}

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
    alert("��ComInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
  }
  catch(re)
  {
    alert("ComInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
</script>