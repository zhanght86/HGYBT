<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
xmlns:java="http://xml.apache.org/xslt/java"
exclude-result-prefixes="java">
<xsl:output method="xml" indent="yes"/>
<xsl:output encoding="gb2312"/>
<xsl:strip-space elements="*"/>

  <xsl:template match="/">
		<html>
			<head>
				<!-- <link rel='stylesheet' type='text/css' href='common/css/Project.css'/> -->
			</head>
			<body>
				<marquee id="marlist" align="top" direction="up" scrolldelay="150" height="100%" width="100%">
					<xsl:for-each select="items/item">
					  
					  <xsl:element name="br">
					  	<xsl:value-of select="date"/>
					  	
					  	<xsl:if test="string-length(link) != 0">
						  	<xsl:element name="a">
						  		<xsl:attribute name="href"><xsl:value-of select="link"/></xsl:attribute>
						  		<xsl:attribute name="target">_blank</xsl:attribute>
						  		<xsl:attribute name="onmouseover">marlist.stop()</xsl:attribute>
						  		<xsl:attribute name="onmouseout">marlist.start()</xsl:attribute>
						  		<xsl:value-of select="desc"/>
						  	</xsl:element>
						  </xsl:if>
						  
					  	<xsl:if test="string-length(link) = 0">
					  	  <xsl:element name="b">
					  	  	<xsl:attribute name="style">color:#FF7C80</xsl:attribute>
					  	  	<xsl:value-of select="desc"/>
					  	  </xsl:element>
						  </xsl:if>

					  </xsl:element>
					</xsl:for-each>
				</marquee>
			</body>
		</html>
  </xsl:template>
</xsl:stylesheet>
