log4j日志：
   log4j-1.2.15.jar  -log4j日志系统主模块
   mail.jar  -支持log4j的邮件功能。可以不要，但缺少此包，无法使用log4j的自动发送邮件日志功能

servlet-api.jar  -支持servlet的接口(实际发布到服务器上时不需要此包)

xml(jdom)
   jdom-1.1.jar  -jdom主类
   xml-apis.jar;xercesImpl-2.9.1.jar  -xml解析器
   xalan-j_2_7_1.jar;serializer.jar  -XSLT处理器
   jaxen-jdom.jar;jaxen-core.jar;saxpath.jar  -XPath支持类。缺少这个三个类，不能使用jdom的XPath

commons-net-ftp-2.0.jar  -apache的net操作组件，目前使用其ftp api

WebService(axis)
   axis.jar  -axis主类
   wsdl4j-1.5.1.jar;saaj.jar;jaxrpc.jar;activation.jar  -WebService协议实现类
   commons-logging-1.0.4.jar;commons-discovery-0.2.jar  -日志处理(log4j、java.util.logging等的上层通用框架)

midplat-1.4.jar  -中间平台公用框架，基于新标准报文，jre 1.5(含)及以上支持，当前版本v1.4.0。

psbc-ftp-j1.5.jar  -邮储上传下载文件的api，jre 1.5(含)及以上支持

adtecsec-java141_03.jar;bcprov-jdk14-140.jar  -建总行加解密支持包(具体细节不详)

poi.jar;poi-2.5.1-final-20040804.jar  -与office交互

oracle.jar  -oracle数据连接驱动程序

mysql-connector-java-5.1.11-bin.jar  -mysql数据连接驱动程序