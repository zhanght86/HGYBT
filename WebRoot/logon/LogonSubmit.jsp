<style type="text/css">
<!--
.style4 {color: #FF0000}
.style5 {
    font-weight: bold;
    font-size: 36px;
    color: #CC3333;
    font-family: "��������", "��������", "����";
}
-->
</style>

<%@ page contentType="text/html;charset=GBK" %>
<%
//******************************************************
// �������ƣ�LogonSubmit.jsp
// ������:�������û���¼�ύ
// ��������ˣ�DingZhong
// ����������ڣ�2002-10-15
//******************************************************//
%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.encrypt.*"%>
<%@page import="com.sinosoft.lis.logon.*"%>
<%@page import="com.sinosoft.lis.menumang.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.utility.*"%>

<%
boolean bSuccess = false;
String sErrorMsg = new String("");
//�û����ƺ�����
String UserCode = request.getParameter("UserCode");

if( UserCode != null && !UserCode.equals("") ) {
  UserCode = UserCode.trim().replaceAll(".*([';]+|(--)+).*", " ");
}

//System.out.println("************+" + UserCode + "+*********");
String Password = request.getParameter("PWD");
System.out.println("++++++++++++++++++Password :" + Password);
LisIDEA tIdea = new LisIDEA();
Password = tIdea.encryptString(Password);
String StationCode = request.getParameter("StationCode");
String ClientURL = request.getParameter("ClientURL");   //LQ 2004-04-19
System.out.println("Password:" + Password);
 
//�û�IP
String Ip = request.getRemoteAddr();
System.out.println("++++++++++++++++++IP :" + Ip);
String ls;  //���ص��ַ���
if (Password.length() == 0 || UserCode.length() == 0)
{
    bSuccess = false;
}
else
{
    VData tVData = new VData();
    LDUserSchema tLDUserSchema = new LDUserSchema();
    tLDUserSchema.setUserCode(UserCode);
    tLDUserSchema.setPassword(Password);
    tLDUserSchema.setComCode(StationCode);
    tVData.add(tLDUserSchema);
    LDUserUI tLDUserUI = new LDUserUI();
    bSuccess=tLDUserUI.submitData(tVData,"query");
    //XinYQ added on 2006-09-04
    if (!bSuccess)
    {
        sErrorMsg = tLDUserUI.mErrors.getFirstError();
    }
}
String title = UserCode + "���ã���ӭ��¼��ϵͳ��";
GlobalInput tG = new GlobalInput();
tG.Operator = UserCode;
tG.ComCode  = StationCode;
tG.ManageCom = StationCode;
session.putValue("GI",tG);
session.putValue("ClientURL",ClientURL);    //LQ 2004-04-19
GlobalInput tG1 = new GlobalInput();
tG1=(GlobalInput)session.getValue("GI");
//System.out.println("Current Operate is "+tG1.Operator);
//System.out.println("Current ComCode is "+tG1.ComCode);
if(bSuccess == true)
{
    //register info into lduserlog
    LDUserLogDB tLDUserLogDB = new LDUserLogDB();
    //for update ��Sql Server 2000 �в�����,����Ȩ��֮��.modify by hwf at 2008-7-29
    String maxno = PubFun1.CreateMaxNo("LOGNO",10);
    //String maxno = PubFun1.CreateSqlServerMaxNo("LOGNO","10");
//    String maxno = PubFun.getSeqNo("SEQ_LOGNO");
    if("-1".equals(maxno))
    {
        session.putValue("GI",null);
    }

    tLDUserLogDB.setLogNo(maxno);
    tLDUserLogDB.setManageCom(StationCode);
    tLDUserLogDB.setOperator(UserCode);
    tLDUserLogDB.setCientIP(Ip);
    tLDUserLogDB.setMakeDate(PubFun.getCurrentDate());
    tLDUserLogDB.setMakeTime(PubFun.getCurrentTime());
    tLDUserLogDB.insert();
    out.print(title);
    //���н�������
//  System.out.println("start unlock operate...");
    //VData inputData = new VData();
    //inputData.addElement(tG1);
    //logoutUI tlogoutUI = new logoutUI();
    //tlogoutUI.submitData(inputData,"LogOutProcess");
    //System.out.println("completed clear data");
%>
<script language=javascript>
if(parent.fraMain.rows == "0,0,0,0,*")
    parent.document.frames('fraTitle').showTitle();
if(parent.fraSet.cols== "0%,*,0%")
    parent.document.frames('fraTitle').showHideFrame();
parent.fraMenu.window.location="./menu2.jsp?userCode=<%=UserCode%>&Ip=<%=Ip%>";
</script>
<%
}
else
{
    session.putValue("GI",null);
%>
<script language=javascript>
alert("�û���/����/����������벻��ȷ��\n\n���ܵ�ԭ���ǣ�" + "<%=sErrorMsg%>");
parent.window.location ="../indexlis.jsp";
</script>
<%
}
%>

<script src="../common/javascript/Common.js"></script>
<script src="../common/cvar/CCodeOperate.js"></script>
<script src="../common/javascript/EasyQuery.js"></script>
<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>

<!--<body onload="initName()">
    <div style="display:none">
        <table width="100%" height="242"  border="0">
          <tr>
            <td width="23%">&nbsp;</td>
            <td width="59%">&nbsp;</td>
            <td width="18%">&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td rowspan="2" align="left" valign="middle" bgcolor="#FFFFFF"><p class="style5">�����ֲ� </p></td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td rowspan="6">
            <table width="433">
            <tr align="center">
              <td width="90" align="right" valign="top"><a href="../manual/Manual(nb).pdf" class="style4">����Լ����</a></td>
            </tr>
                <tr>
                  <td width="90" align="right" valign="middle"><a href="../manual/Manual(card).pdf" class="style4">��֤����</a></td>
                </tr>
                <tr>
                  <td width="90" align="right" valign="middle"><a href="../manual/Manual(rn).pdf" class="style4">���ڲ���</a></td>
                </tr>
                <tr>
                  <td width="90" align="right" valign="middle"><a href="../manual/Manual(pos).pdf" class="style4">��ȫ����</a></td>
                </tr>
                <tr>
                  <td width="90" align="right" valign="middle"><a href="../manual/Manual(claim).pdf" class="style4">�������</a></td>
                </tr>
                <tr>
                  <td width="90" align="right" valign="middle"><a href="../manual/Manual(agent).pdf" class="style4">��������</a></td>
                </tr>
                <tr>
                  <td width="90" align="right" valign="middle"><a href="../manual/Manual(finance).pdf" class="style4">�������</a></td>
                </tr>
            </table>
            </td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        </table>
    </div>

    <div>
        <center><iframe src="../whatsnew.xml" frameborder="no" scrolling="no" height="90%" width="100%"></iframe></center>
    </div>

<form name="fm">
    <input type="hidden" name="VirtualCode" value="0" />
    <input type="hidden" name="VirtualName" value="" />

</form>
</body>-->

<script language="javascript">
    function initName()
    {
        showOneCodeName('province','VirtualCode','VirtualName');
        showOneCodeName('city','VirtualCode','VirtualName');
        showOneCodeName('district','VirtualCode','VirtualName');
        showOneCodeName('incomeway','VirtualCode','VirtualName');
        showOneCodeName('comcode','VirtualCode','VirtualName');
        showOneCodeName('sellType','VirtualCode','VirtualName');
        showOneCodeName('vipvalue','VirtualCode','VirtualName');
        showOneCodeName('IDType','VirtualCode','VirtualName');
        showOneCodeName('Sex','VirtualCode','VirtualName');
        showOneCodeName('Marriage','VirtualCode','VirtualName');
        showOneCodeName('NativePlace','VirtualCode','VirtualName');
        showOneCodeName('OccupationCode','VirtualCode','VirtualName');
        showOneCodeName('LicenseType','VirtualCode','VirtualName');
        showOneCodeName('paymode','VirtualCode','VirtualName');
        showOneCodeName('continuepaymode','VirtualCode','VirtualName');
        showOneCodeName('bank','VirtualCode','VirtualName');
        showOneCodeName('SequenceNo','VirtualCode','VirtualName');
        showOneCodeName('vipvalue','VirtualCode','VirtualName');
        showOneCodeName('Relation','VirtualCode','VirtualName');
    }

</script>
