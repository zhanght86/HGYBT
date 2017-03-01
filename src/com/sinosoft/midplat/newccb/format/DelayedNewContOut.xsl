<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

<xsl:template match="TranData">
  <TX>
	<!-- ����ͷ -->
	<TX_HEADER>
      <!-- ����ͷ���� -->
	  <SYS_HDR_LEN></SYS_HDR_LEN>
      <!-- Э��汾�� -->
      <SYS_PKG_VRSN>01</SYS_PKG_VRSN>
	  <!-- �����ܳ��� -->
	  <SYS_TTL_LEN></SYS_TTL_LEN>
	  <!-- ���ͷ���ȫ�ڵ��� -->
	  <SYS_SND_SEC_ID>510096</SYS_SND_SEC_ID>
	  <!-- ���𷢰�ȫ�ڵ���  ת����������-->
	  <SYS_REQ_SEC_ID></SYS_REQ_SEC_ID>
	  <!--��������-->
	  <SYS_TX_TYPE>020000</SYS_TX_TYPE>
	  <!-- ȫ���¼����ٺ�  ת����������-->
	  <SYS_EVT_TRACE_ID></SYS_EVT_TRACE_ID>
	  <!-- �ӽ������  ת����������-->
	  <SYS_SND_SERIAL_NO></SYS_SND_SERIAL_NO>
	  <!-- Ӧ�ñ��ĸ�ʽ���� -->
	  <SYS_PKG_TYPE>1</SYS_PKG_TYPE>    
	  <!-- Ӧ�ñ��ĳ���  ת����������-->
	  <SYS_MSG_LEN></SYS_MSG_LEN>
      <!-- Ӧ�ñ����Ƿ���� -->
      <SYS_IS_ENCRYPTED>3</SYS_IS_ENCRYPTED>    
      <!-- Ӧ�ñ��ļ��ܷ�ʽ -->
	  <SYS_ENCRYPT_TYPE>3</SYS_ENCRYPT_TYPE>
	  <!-- Ӧ�ñ���ѹ����ʽ -->
	  <SYS_COMPRESS_TYPE>0</SYS_COMPRESS_TYPE>    
	  <!-- �Ӵ����ĳ��� -->
	  <SYS_EMB_MSG_LEN>0</SYS_EMB_MSG_LEN>
	  <!-- �������ʱ�� ת����������-->
	  <SYS_RECV_TIME></SYS_RECV_TIME>    
	  <!-- ������Ӧʱ��  ת����������-->
	  <SYS_RESP_TIME></SYS_RESP_TIME>
	  <!-- ����״̬����  -->
	  <SYS_PKG_STS_TYPE>01</SYS_PKG_STS_TYPE>    
	  <xsl:if test = "RetData/Flag='1'">
	  <!-- ����״̬ -->
	  <SYS_TX_STATUS>00</SYS_TX_STATUS>    
	  <!-- ������Ӧ�� -->
	  <SYS_RESP_CODE>000000000000</SYS_RESP_CODE>    
	  </xsl:if>
	  <xsl:if test = "RetData/Flag='0'">
	  <!-- ����״̬ -->
	  <SYS_TX_STATUS>01</SYS_TX_STATUS>    
	  <!-- ������Ӧ�� -->
	  <SYS_RESP_CODE>ZZZ072000001</SYS_RESP_CODE>    
	  </xsl:if>    
	  <!-- ������Ӧ��������  ת����������-->
	  <SYS_RESP_DESC_LEN></SYS_RESP_DESC_LEN>    
	  <!-- ������Ӧ���� -->
	  <SYS_RESP_DESC><xsl:value-of select="RetData/Desc"/></SYS_RESP_DESC>    
   </TX_HEADER>
   <!-- ������ -->
   <TX_BODY>
	  <COMMON>
	    <FILE_LIST_PACK>
	    <!-- �ļ����� -->
		<FILE_NUM>0</FILE_NUM>
		<!-- �ļ�������ʽ -->
		<FILE_MODE></FILE_MODE>
		<!-- �ļ��ڵ� -->
		<FILE_NODE></FILE_NODE>
		<!-- �������ļ��� -->
		<FILE_NAME_PACK></FILE_NAME_PACK>
		<!-- ����ļ���ȡ·�� -->
		<FILE_PATH_PACK></FILE_PATH_PACK>
		<!-- �ļ���Ϣ -->
		<FILE_INFO>
		<!-- �ļ���Ϣ -->
		<FILE_NAME></FILE_NAME>
		<!-- �ļ�·�� -->
		<FILE_PATH></FILE_PATH>
		</FILE_INFO>
	    </FILE_LIST_PACK>
	  </COMMON>
	  <ENTITY>
	     <COM_ENTITY>
            <!--������-->
            <Inst_Eng_ShrtNm></Inst_Eng_ShrtNm>
            <Ins_Co_ID></Ins_Co_ID>
            <!--���չ�˾���-->
            <SvPt_Jrnl_No></SvPt_Jrnl_No>
            <!--������ˮ��-->
            <TXN_ITT_CHNL_ID></TXN_ITT_CHNL_ID>
            <!--���׷����������-->
            <TXN_ITT_CHNL_CGY_CODE></TXN_ITT_CHNL_CGY_CODE>
            <!--���׷����������-->
            <CCBIns_ID></CCBIns_ID>
            <!--���л������&�������-->
            <CCB_EmpID></CCB_EmpID>
            <!--����Ա�����&��Ա����-->
            <OprgDay_Prd></OprgDay_Prd>
            <!--Ӫҵ����-->
            <LNG_ID></LNG_ID>
            <SYS_TX_CODE></SYS_TX_CODE>
            <Ins_Co_Acg_Dt></Ins_Co_Acg_Dt>
            <Ins_Co_Jrnl_No></Ins_Co_Jrnl_No>
            <Ins_Co_Cst_Svc_Tel></Ins_Co_Cst_Svc_Tel>
         </COM_ENTITY>
	   </ENTITY>
	 </TX_BODY>
  </TX>
</xsl:template>		
</xsl:stylesheet>