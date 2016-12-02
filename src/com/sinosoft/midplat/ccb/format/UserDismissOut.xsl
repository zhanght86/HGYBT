<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<Transaction>
			<!--������-->
			<xsl:if test="TranData/Head/Flag=0">
			<Transaction_Body>
				<BkFileNum>1</BkFileNum>
				<!--��ӡ�ı�����-->
				<Detail_List>
				<BkFileDesc>�ͻ���Լ</BkFileDesc>
				<BkType1></BkType1>
				<BkVchNo></BkVchNo>
				<BkRecNum>13</BkRecNum>
				<Detail>
				<BkDetail1>
				<xsl:text>��������</xsl:text>
						<xsl:text>���չ�˾���ƣ��������ٱ��չɷ����޹�˾</xsl:text></BkDetail1>
				<BkDetail1>
				<xsl:text>��������</xsl:text>
						<xsl:text>ҵ�����ࣺ�����˺�ǩԼ</xsl:text>
						</BkDetail1>
						<BkDetail1>
						<xsl:text>��������</xsl:text>
						<xsl:text>���֣�</xsl:text><xsl:value-of select="TranData/Body/RiskName"/>
						</BkDetail1>
				<BkDetail1><xsl:text>��������</xsl:text><xsl:text>�����ţ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/ContNo, 42)"/>
				<xsl:text>��������        </xsl:text><xsl:text> ��ˮ�ţ�</xsl:text><xsl:value-of select="TranData/Body/TranNo"/>
				</BkDetail1>
				<BkDetail1>
				<xsl:text>��������</xsl:text><xsl:text>Ͷ��������:</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/AppntName, 40)"/>
				<xsl:text>��������        </xsl:text><xsl:text>���������ƣ��й���������</xsl:text>
				</BkDetail1>
				<BkDetail1>
				<xsl:text>��������</xsl:text>
				<xsl:text>��Ч���ڣ�</xsl:text><xsl:value-of select="TranData/Body/CValidate"/>
				</BkDetail1>
							 <BkDetail1></BkDetail1>
								 <BkDetail1></BkDetail1>
								 								  <BkDetail1><xsl:text>��������        </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('', 20)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('������ǩ�£�', 40)"/><xsl:text></xsl:text><xsl:text>��������            	���и��£�</xsl:text></BkDetail1>
								  <BkDetail1></BkDetail1>
									       <BkDetail1><xsl:text>��������</xsl:text>    ������ı����������ʻ�����������ҵ����������ϵ�������չ���/��������򲦴�˾ͳһ��</BkDetail1>
					       <BkDetail1><xsl:text>��������</xsl:text>ѯ���ߴ�ѯ��Ҳ���Ե�½��˾��վ�����ǽ��߳�Ϊ���ṩ�����ʵķ���</BkDetail1>
					       <BkDetail1><xsl:text>��������</xsl:text>    ��˾ȫ��ͳһ��ѯ���� 4008256789;��˾��ַwww.soochowlife.com;</BkDetail1>
				</Detail>
				</Detail_List>
			</Transaction_Body>
			</xsl:if>
		</Transaction>
	</xsl:template>
</xsl:stylesheet>
