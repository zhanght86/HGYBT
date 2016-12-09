<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="TranData">
    <TX>
	<TX_HEADER>
		<!-- ����ͷ���� -->
		<SYS_HDR_LEN></SYS_HDR_LEN>
		<!-- Э��汾�� -->
		<SYS_PKG_VRSN>01</SYS_PKG_VRSN>
		<!-- �����ܳ��� -->
		<SYS_TTL_LEN></SYS_TTL_LEN>				
		<!-- ���ͷ���ȫ�ڵ��� -->
		<SYS_SND_SEC_ID>510051</SYS_SND_SEC_ID>
		<!-- ���𷽰�ȫ�ڵ��� -->
		<SYS_REQ_SEC_ID></SYS_REQ_SEC_ID>
		<!-- �������� -->
		<SYS_TX_TYPE>020000</SYS_TX_TYPE>
		<!-- ȫ���¼����ٺ� -->
		<SYS_EVT_TRACE_ID></SYS_EVT_TRACE_ID>
		<!-- �ӽ������ -->
		<SYS_SND_SERIAL_NO></SYS_SND_SERIAL_NO>
		<!-- Ӧ�ñ��ĸ�ʽ���� -->
		<SYS_PKG_TYPE>1</SYS_PKG_TYPE>
		<!-- Ӧ�ñ��ĳ��� -->
		<SYS_MSG_LEN></SYS_MSG_LEN>
		<!-- Ӧ�ñ����Ƿ���� -->
		<SYS_IS_ENCRYPTED>3</SYS_IS_ENCRYPTED>
		<!-- Ӧ�ñ��ļ��ܷ�ʽ -->
		<SYS_ENCRYPT_TYPE>3</SYS_ENCRYPT_TYPE>
		<!-- Ӧ�ñ���ѹ����ʽ -->
		<SYS_COMPRESS_TYPE>0</SYS_COMPRESS_TYPE>
		<!-- �Ӵ����ĳ��� -->
		<SYS_EMB_MSG_LEN>0</SYS_EMB_MSG_LEN>
		<!-- �������ʱ�� -->
		<SYS_RECV_TIME></SYS_RECV_TIME>
		<!-- ������Ӧʱ�� -->
		<SYS_RESP_TIME></SYS_RESP_TIME>
		<!-- ����״̬���� -->
		<SYS_RESP_TIME>01</SYS_RESP_TIME>
		<!-- ����״̬ -->
		<SYS_TX_STATUS></SYS_TX_STATUS>
		<!-- ������Ӧ�� -->
		<SYS_RESP_CODE></SYS_RESP_CODE>
		<!-- ������Ӧ�������� -->
		<SYS_RESP_DESC_LEN></SYS_RESP_DESC_LEN>
		<!-- ������Ӧ���� -->
		<SYS_RESP_DESC></SYS_RESP_DESC>
	</TX_HEADER>
	<TX_BODY>
		<COMMON>
			<FILE_LIST_PACK>
			<!-- �ļ����� -->
			<FILE_NUM></FILE_NUM>
			<!-- �ļ�����ʽ -->
			<FILE_MODE>0</FILE_MODE>
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
				<!-- ����Ӣ�ļ�� -->
				<Inst_Eng_ShrtNm>CCB</Inst_Eng_ShrtNm>
				<!-- ���չ�˾��� -->
				<Ins_Co_ID>73</Ins_Co_ID>
				<!-- #������ˮ�� -->
				<SvPt_Jrnl_No></SvPt_Jrnl_No>
				<!-- ������ -->
				<SYS_TX_CODE>P53818152</SYS_TX_CODE>
				<!-- ���׷���������� -->
				<TXN_ITT_CHNL_ID></TXN_ITT_CHNL_ID>
				<!-- ���׷���������� -->
				<TXN_ITT_CHNL_CGY_CODE></TXN_ITT_CHNL_CGY_CODE>
				<!-- ���л������ -->
				<CCBIns_ID><xsl:value-of select="Head/NodeNo"/></CCBIns_ID>
				<!-- ����Ա����� -->
				<CCB_EmpID><xsl:value-of select="Head/TellerNo"/></CCB_EmpID>
				<!-- ���չ�˾�������� -->
				<Ins_Co_Acg_Dt><xsl:value-of select="Head/TranDate"/></Ins_Co_Acg_Dt>
				<!-- ���չ�˾��ˮ�� -->
				<Ins_Co_Jrnl_No><xsl:value-of select="Head/TranNo"/></Ins_Co_Jrnl_No>
			</COM_ENTITY>
			<APP_ENTITY/>
		</ENTITY>
	</TX_BODY>
</TX>
</xsl:template>
</xsl:stylesheet>
