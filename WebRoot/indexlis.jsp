<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : sinosoft
 * @version  : 1.00
 * @date     : 2006-10-30
 * @direction: ����ͨϵͳ������
 ******************************************************************************/
%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>�п���ͨ���м�ҵ��ƽ̨</title>
    <script language="JavaScript">
    <!--
        //�ж�ҳ���Ƿ��ʼ�����
        var achieveEX = false;
        var achieveVD = false;
        //��������������, ���ڴ���ѡ���ܲ���������, ��������
        window.name = "Lis";
        try
        {
            //window.resizeTo(screen.availWidth, screen.availHeight);
        }
        catch (ex) {}
        window.focus();
    -->
    </script>
</head>
<frameset name="fraMain" framespacing="0" frameborder="no" rows="0,0,0,0,*" cols="*" border="1">
    <!-- ����ͻ��˱��������� -->
    <frame name="VD" src="common/cvar/CVarData.jsp" noresize />
    <!-- ʵ�ַ��������������� -->
    <frame name="EX" src="common/cvar/CExec.jsp" noresize />
    <!-- �ύ�ͻ��˲�ѯ������ -->
    <frame name="fraSubmit" src="about:blank" scrolling="no" noresize />
    <!-- ������ʾ������������ -->
    <frame name="fraTitle" src="logon/Title.jsp" scrolling="no" noresize />
    <!-- ��ʾ�˵��ͽ��������� -->
    <frameset name="fraSet" framespacing="0" frameborder="no" rows="*" cols="0%,*,0%" border="1">
        <!-- ��ʾ�˵������� -->
        <frameset name="fraMenuMain" framespacing="0" frameborder="no" rows="25,*" border="1">
            <frame name="fraMenuTop" src="logon/menutop.jsp" scrolling="no" noresize />
            <frame name="fraMenu" src="about:blank" scrolling="auto" noresize />
        </frameset>
        <!-- ʵ�ֽ��������� -->
        <frameset name="fraTalk" framespacing="0" frameborder="no" rows="0,*" border="1">
            <frame name="fraQuick" src="logon/quick.jsp" scrolling="no" noresize />
            <frameset name="fraTalkCol" framespacing="0" frameborder="no" cols="0,*" border="1">
                <frame name="fraPic" src="about:blank" scrolling="auto" noresize />
                <frame name="fraInterface" src="logon/ybtmain.jsp" scrolling="auto" noresize />
            </frameset>
        </frameset>
        <!-- ��һ���������� -->
        <frame name="fraNext" src="about:blank" scrolling="auto" noresize />
    </frameset>
</frameset>
<noframes>
    <body bgcolor="#FFFFFF">
        <br><br><br><br><br>
        <center>
            <font size="2">�Բ��������������֧�ֿ����ҳ��</font>
            <br><br>
            <font size="2">��ʹ�� IE 5.0 �������������ϵͳ��</font>
        </center>
    </body>
</noframes>
</html>
