<%@page contentType="text/html;charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.lis.db.LDComDB"%>
<%
	//�������ƣ�FIN_PrintInit.jsp
	//�����ܣ�����ͨÿ��ʵʱ���ݵ�����
	//�������ڣ�2011-07-26
	//������  ��ZHANGHJ
	//���¼�¼��  ������    ��������     ����ԭ��/����

	
	GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
	if (null == cGlobalInput) {
		cGlobalInput = new GlobalInput();
	}
	//-----�û�Ȩ��------
	String sManageCom = cGlobalInput.ManageCom;
	String sAreaNo = "";
	String sAreaName = "";
	String sCityNo = "";
	String sCityName = "";
	if (!"86".equals(sManageCom)) {
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(sManageCom);
		if (!tLDComDB.getInfo()) {
			System.out.println("���û������������������,��ȷ��!");
		}
	}
	String sStartDay = DateUtil.getCur10Date();
	String sEndDay = DateUtil.getCur10Date();
	String sNowTime = DateUtil.getCur8Time();
	String sEndHour = sNowTime.substring(0,2);
	if(sEndHour != null && !"".equals(sEndHour)){
		int iHour = Integer.valueOf(sEndHour);
		int iEndHour = iHour + 1;
		if(iEndHour == 24){
			iEndHour =0;
		}
		sEndHour = String.valueOf(iEndHour);
		if(sEndHour.length()==1){
			sEndHour = "0"+sEndHour;
		}
	}
	System.out.println(sNowTime+"_"+sEndHour);
	
%>

<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">
var cManageCom = "<%=cGlobalInput.ManageCom%>";	//ȫ�ֱ�������¼��½�������
var cOperator = "<%=cGlobalInput.Operator%>";	//ȫ�ֱ�������¼��¼�û�
var cManageComName="<%=cGlobalInput.ManageComName%>";
function initForm() {
	fm.ManageCodeName.value=cManageCom+cManageComName;
	fm.ManageCodeNo.value=cManageCom;
	fm.all("StartDay").value = "<%=sStartDay%>";
	fm.all("EndDay").value = "<%=sEndDay%>";
	initUserAuthority();
	initBox();
}

function initUserAuthority() {
	if(cManageCom.length == 2){
		fm.UserAuthority.value = 1;
		document.getElementById("AreaNoId").readOnly=false;
		document.getElementById("CityNoId").readOnly=false;
	}
	if(cManageCom.length >= 4){
		fm.UserAuthority.value = 2;
		fm.AreaNo.value = "<%=sAreaNo%>";
		fm.AreaName.value = "<%=sAreaName%>";
		document.getElementById("AreaNoId").readOnly=true;
		document.getElementById("CityNoId").readOnly=false;
	}
	if(cManageCom.length >=7){
		fm.UserAuthority.value = 4;
		fm.CityNo.value = "<%=sCityNo%>";
		fm.CityName.value = "<%=sCityName%>";
		document.getElementById("AreaNoId").readOnly=true;
		document.getElementById("CityNoId").readOnly=true;

	}
}
function initBox() {
	try {
		fm.all("TranCom").value = "";
		fm.all("TranComName").value = "***��������***";
		fm.all("AreaNo").value = "";
		fm.all("AreaName").value = "***��������***";
		fm.all("CityNo").value = "";
		fm.all("CityName").value = "***���г���***";
		fm.all("ContStatue").value = "";
		fm.all("ContStatueName").value = "***����״̬***";
		fm.all("StartDay").value = "<%=sStartDay%>";
		fm.all("EndDay").value = "<%=sEndDay%>";
		fm.all("StartHour").value = "00";
		fm.all("EndHour").value = "<%=sEndHour%>";
		initUserAuthority();
	} catch (re) { 
		alert("��FIN_PrintInit.jsp --> initBox �����з����쳣:��ʼ���������");
	}
}
</script>
