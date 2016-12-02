<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">

 
<xsl:template match="TranData">
	  <TranData>
	  <Head>
			 <xsl:copy-of select="Head/*"/> <!-- -->
	  </Head>
  		<!--Ͷ����Ϣ-->
		<Body>
		<xsl:for-each select="Body/Detail">
			<Detail>
				 <!-- ҵ����� -->
				<BusiType >
						<xsl:call-template name="busitype">
							<xsl:with-param name="BusiType">
								<xsl:value-of select="BusiType" />
							</xsl:with-param>
						</xsl:call-template>
				</BusiType >
				<!-- ��������  -->
				<TranDate><xsl:value-of select="TranDate"/></TranDate> 
				<!-- ������-->
				<ContNo><xsl:value-of select="ContNo"/></ContNo>
				<!-- ����������-->
				<AppntName><xsl:value-of select="AppntName"/></AppntName>
				<!-- ����״̬-->
				<Status><xsl:value-of select="Status"/></Status>
				<!-- ��ȡ���-->
				<Prem><xsl:value-of select="Prem"/></Prem>
				<!-- �������� -->
				<Channel><xsl:value-of select="Channel"/></Channel>
			</Detail>

 			</xsl:for-each>
		</Body>
	</TranData>
</xsl:template>	


	<!--  ҵ������ -->
	<xsl:template name="busitype" >
		<xsl:param name="BusiType"></xsl:param>
		<xsl:if test="$BusiType = 07">01</xsl:if><!-- �̳�  -->
		<xsl:if test="$BusiType = 09">02</xsl:if><!-- ���� -->
		<xsl:if test="$BusiType = 10">03</xsl:if><!-- �˱� -->
	</xsl:template>


</xsl:stylesheet>