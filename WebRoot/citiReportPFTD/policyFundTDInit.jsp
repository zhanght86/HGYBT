<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.db.LDComDB"%>
<%	
	GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
	if (null == cGlobalInput) {
		cGlobalInput = new GlobalInput();
	}
	//-----�û�Ȩ��------
	String sManageCom = cGlobalInput.ManageCom;
	if (!"86".equals(sManageCom)) {
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(sManageCom);
		if (!tLDComDB.getInfo()) {
			System.out.println("���û������������������,��ȷ��!");
		}
	}	
	String sDay = DateUtil.getCur10Date();
%>

<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
var cManageCom = "<%=cGlobalInput.ManageCom%>";	//ȫ�ֱ�������¼��½�������
var cOperator = "<%=cGlobalInput.Operator%>";	//ȫ�ֱ�������¼��¼�û�
function initForm() {
	initBox();
}

function initBox() {
	try {
		fm.all("StartDay").value = "<%=sDay%>";
		fm.all("EndDay").value = "<%=sDay%>";
	} catch (re) { 
		alert("��policyFundTDInit.jsp --> initBox �����з����쳣:��ʼ���������");
	}
}
</script>
