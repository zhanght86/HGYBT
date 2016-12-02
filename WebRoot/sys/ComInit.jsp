<%
//程序名称：ComInput.jsp
//程序功能：
//创建日期：2002-08-16 17:44:40
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
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
    alert("在ComInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initSelBox()
{
  try
  {
//    setOption("t_sex","0=男&1=女&2=不详");
//    setOption("sex","0=男&1=女&2=不详");
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");
  }
  catch(ex)
  {
    alert("在ComInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
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
    alert("ComInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
</script>