<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2006-12-13
 * @direction: 系统菜单主框架
 ******************************************************************************/
%>

<%@ page import="com.sinosoft.lis.db.*" %>
<%@ page import="com.sinosoft.lis.schema.*" %>
<%@ page import="com.sinosoft.lis.vschema.*" %>

<%@ include file="../common/jsp/UsrCheck.jsp" %>

<%
    //接受传入参数
    String sUserCode = request.getParameter("userCode");
    String sIPAddress = request.getParameter("Ip");

    //用户菜单容器
    String sMenuNodeData = new String("");

    //查询用户菜单
    String QuerySQL = new String("");
    if (sUserCode == null || sUserCode.trim().equals(""))
    {
        QuerySQL = "select * from LDMenu where 1 = 2 order by NodeOrder asc";
    }
    else if (sUserCode.trim().equals("001"))
    {
        QuerySQL = "select * from LDMenu where 1 = 1 order by NodeOrder asc";
    }
    else
    {
        QuerySQL = "select * "
                 +   "from LDMenu "
                 +  "where NodeCode in "
                 +        "(select NodeCode "
                 +           "from LDMenuGrpToMenu "
                 +          "where trim(MenuGrpCode) in "
                 +                "(select trim(MenuGrpCode) "
                 +                   "from LDMenuGrp "
                 +                  "where trim(MenuGrpCode) in "
                 +                        "(select trim(MenuGrpCode) "
                 +                           "from LDUserToMenuGrp "
                 +                          "where trim(UserCode) = '" + sUserCode + "'))) "
                 +  "order by NodeOrder asc";
    }
    System.out.println("菜单SQL：" + QuerySQL);

    //查询 LDMenu
    LDMenuDB tLDMenuDB = new LDMenuDB();
    LDMenuSet tLDMenuSet = new LDMenuSet();
    try
    {
        tLDMenuSet = tLDMenuDB.executeQuery(QuerySQL);
    }
    catch (Exception ex) {}
    if (tLDMenuSet != null && tLDMenuSet.size() > 0)
    {
        StringBuffer sbMenuNodeData = new StringBuffer(256);
        for (int i = 1; i <= tLDMenuSet.size(); i++)
        {
            LDMenuSchema tLDMenuSchema = new LDMenuSchema();
            tLDMenuSchema = tLDMenuSet.get(i);
            sbMenuNodeData.append("\r\n");
            sbMenuNodeData.append("                ");
            sbMenuNodeData.append("nodes[\"");
            String sParentNodeCode = tLDMenuSchema.getParentNodeCode();
            if (sParentNodeCode == null || sParentNodeCode.trim().equals("") || sParentNodeCode.trim().equals("0"))
            {
                sbMenuNodeData.append("1");
            }
            else
            {
                sbMenuNodeData.append(sParentNodeCode);
            }
            sbMenuNodeData.append("_");
            sbMenuNodeData.append(tLDMenuSchema.getNodeCode());
            sbMenuNodeData.append("\"] = \"");
            sbMenuNodeData.append("text:");
            sbMenuNodeData.append(tLDMenuSchema.getNodeName());
            sbMenuNodeData.append("; url:");
            String sRunScript = tLDMenuSchema.getRunScript();
            if (sRunScript == null || sRunScript.trim().equals(""))
            {
               
                sbMenuNodeData.append("../news.txt");
                
               // sbMenuNodeData.append("");
                
            }
            else if (sRunScript.indexOf("?") != -1)
            {
                String sLinkPage = sRunScript.substring(0, sRunScript.indexOf("?"));
                String sLinkData = sRunScript.substring(sRunScript.indexOf("?") + 1);
                sbMenuNodeData.append(sLinkPage);
                sbMenuNodeData.append("; data:");
                sbMenuNodeData.append(sLinkData);
            }
            else
            {
                sbMenuNodeData.append(sRunScript);
            }
            sbMenuNodeData.append("; hint:");
            String sNodeHint = tLDMenuSchema.getNodeDescription();
            if (sNodeHint == null || sNodeHint.trim().equals(""))
            {
                sbMenuNodeData.append(tLDMenuSchema.getNodeName());
            }
            else
            {
                sbMenuNodeData.append(sNodeHint);
            }
            sbMenuNodeData.append("; method:showStation('");
            sbMenuNodeData.append(tLDMenuSchema.getNodeCode());
            sbMenuNodeData.append("');\";");
            tLDMenuSchema = null;
        }
        sMenuNodeData = sbMenuNodeData.toString();
    }
    tLDMenuDB = null;
    tLDMenuSet = null;
%>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <meta name="MzTreeView Author" content="meizz: http://www.meizz.com">
    <title>系统菜单</title>
    <!-- 私有使用样式 -->
    <style type=text/css>
        body
        {
            font: tahoma,verdana,arial,helvetica,sans-serif;
            font-size: 12px;
            scrollbar-face-color: #799AE1;
            scrollbar-highlight-color: #799AE1;
            scrollbar-shadow-color: #799AE1;
            scrollbar-darkshadow-color: #799AE1;
            scrollbar-3dlight-color: #799AE1;
            scrollbar-arrow-color: #FFFFFF;
            scrollbar-track-color: #AABFEC;
        }
        a:link, a:visited, a:active
        {
            color: #000000;
            padding-left: 2px;
            text-decoration: none;
        }
        a:hover
        {
            color: #FF6600;
            padding-left: 2px;
            text-decoration: none;
        }
    </style>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="../common/TreeView/MzTreeView.js"></script>
    <!-- 私有使用脚本 -->
    <script language="JavaScript">
    <!--
        //检查访问地址
        if (top.location == self.location)
        {
            top.location = "../indexlis.jsp";
        }

        //显示状态栏信息
        defaultStatus = "欢迎使用本系统";
        top.window.status = defaultStatus;

        var MenuTree = new MzTreeView("MenuTree");

        //显示用户菜单
        function initMenuTree()
        {
            with (MenuTree)
            {
                wordLine = false;
                icons["root"] = "root.png";
                icons["folder"] = "folder_blue.png";
                iconsExpand["folder"] = "folderopen_blue.png";
                icons["file"] = "dot.gif";
                setIconPath("../common/TreeView/images/");
                setTarget("fraInterface");
              
                // nodes["0_1"] = "text:业务系统首页; url:../whatsnew.xml;";
               
                nodes["0_1"] = "text:业务系统首页; url:../news.txt";
                <%=sMenuNodeData%>
                nodes["1_60001"] = "text:密码修改; icon:pwd; url:../changePwd/PwdInput.jsp; method:showStation('60001');";
                nodes["1_60002"] = "text:重新登录; icon:exit; url:../logon/logout.jsp;";
            }
            try
            {
                window.document.getElementById("divMenuTree").innerHTML = MenuTree.toString();
            }
            catch (ex) {}
        }

        //显示用户位置
        function showStation(sNodeCode)
        {
            var sLinkURL;
            if (sNodeCode == null || sNodeCode == "")
            {
                sLinkURL = "station.jsp";
            }
            else
            {
                sLinkURL = "station.jsp?nodecode=" + sNodeCode + "&Ip=<%=sIPAddress%>";
            }
            try
            {
                parent.fraQuick.window.location = sLinkURL;
            }
            catch (ex) {}
        }

        //退出业务系统
        function destroySession()
        {
            try
            {
                window.showModelessDialog("close.jsp", window, "status=0; help=0; close=0; dialogWidth=160px; dialogHeight=100px");
            }
            catch (ex) {}
        }
    -->
    </script>
</head>
<body topmargin="2" bgcolor="#EFF1FA" onunload="destroySession()" oncontextmenu="return false" ondragstart="return false">
    <div id="divMenuTree"></div>
</body>
</html>
<script language="JavaScript">initMenuTree();showStation();</script>
