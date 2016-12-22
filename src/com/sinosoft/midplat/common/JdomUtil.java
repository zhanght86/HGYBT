/**
 * 此类当前仅支持JDOM 1.1
 */

package com.sinosoft.midplat.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.InputSource;

/**
 * Java文档对象模型工具
 * @author yuantongxin
 */
public class JdomUtil {
	private final static Logger cLogger = Logger.getLogger(JdomUtil.class);
	
	/**
	 * 采用GBK编码构建一个Document对象，忽略标签之间的空字符(空格、换行、制表符等)。
	 * 构建失败，返回null。
	 */
	public static Document build(byte[] pBytes) {
		return build(pBytes, "GBK");
	}
	
	/**
	 * 采用指定字符集编码构建一个Document对象，忽略标签之间的空字符(空格、换行、制表符等)。
	 * 构建失败，返回null。
	 */
	public static Document build(byte[] pBytes, String pCharset) {
		return build(pBytes, pCharset, true);
	}

	/**
	 * 采用指定字符集编码构建一个Document对象。
	 * OmitSpace: 标识是否忽略标签之间的空字符(空格、换行、制表符等)，true-忽略
	 * 构建失败，返回null。
	 */
	public static Document build(byte[] pBytes, String pCharset, boolean OmitSpace) {
		return build(new ByteArrayInputStream(pBytes), pCharset, OmitSpace);
	}
	
	/**
	 * 采用GBK编码构建一个Document对象，忽略标签之间的空字符(空格、换行、制表符等)。
	 * 构建失败，返回null。
	 */
	public static Document build(InputStream pIs) {
		return build(pIs, "GBK");
	}
	
	/**
	 * 采用指定字符集编码构建一个Document对象，忽略标签之间的空字符(空格、换行、制表符等)。
	 * 构建失败，返回null。
	 */
	public static Document build(InputStream pIs, String pCharset) {
		//构建一个文档对象[File:/task/20161216/newccb/local/1012/P53819113in_noStd.xml,UTF-8,忽略标签之间的空字符]
		return build(pIs, pCharset, true);//UTF-8编码文档
	}
	
	/**
	 * 采用指定字符集编码构建一个Document对象。
	 * OmitSpace: 标识是否忽略标签之间的空字符(空格、换行、制表符等)，true-忽略
	 * 构建失败，返回null。
	 */
	public static Document build(InputStream pIs, String pCharset, boolean OmitSpace) {
		try {
			//通过字节输入流构建XML 实体单一输入源
			InputSource mInputSource = new InputSource(pIs);
			//设置字符编码为UTF-8
			mInputSource.setEncoding(pCharset);
			//JDOM解析器
			SAXBuilder mSAXBuilder = new SAXBuilder();
			//设置忽略边界空白
			mSAXBuilder.setIgnoringBoundaryWhitespace(OmitSpace);
			return mSAXBuilder.build(mInputSource);//返回解析单一输入源生成的文档
		} catch(Exception ex) {
			cLogger.error("xml格式有误，解析失败！", ex);
			return null;
		}
	}
	
	/**
	 * 将指定字符串构建一个Document对象，忽略标签之间的空字符(空格、换行、制表符等)。
	 * 构建失败，返回null。
	 */
	public static Document build(String pXmlStr) {
		return build(pXmlStr, true);
	}
	
	/**
	 * 将指定字符串构建一个Document对象。
	 * OmitSpace: 标识是否忽略标签之间的空字符(空格、换行、制表符等)，true-忽略
	 * 构建失败，返回null。
	 */
	public static Document build(String pXmlStr, boolean OmitSpace) {
		try {
			SAXBuilder mSAXBuilder = new SAXBuilder();
			mSAXBuilder.setIgnoringBoundaryWhitespace(OmitSpace);
			return mSAXBuilder.build(
					new StringReader(pXmlStr));
		} catch(Exception ex) {
			cLogger.error("xml格式有误，解析失败！", ex);
			return null;
		}
	}
	
	/**
	 * 将Document输出到指定的输出流，GBK编码，缩进3空格。
	 * 注意：此方法不自动关闭流，如有需要，请在调用后手动关闭。
	 */
	public static void output(Document pXmlDoc, OutputStream pOs) throws IOException {
		output(pXmlDoc, pOs, "GBK");
	}
	
	/**
	 * 将Document输出到指定的输出流，pCharset指定编码，缩进3空格。
	 * 注意：此方法不自动关闭流，如有需要，请在调用后手动关闭。
	 */
	public static void output(Document pXmlDoc, OutputStream pOs, String pCharset) throws IOException {
		//得到原格式指定编码缩进3空格
		Format mFormat = Format.getRawFormat().setEncoding(pCharset).setIndent("   ");
		//使用格式创建XML输出器
		XMLOutputter mXMLOutputter = new XMLOutputter(mFormat);
		//输出XML文档到字节输出流
		mXMLOutputter.output(pXmlDoc, pOs);
	}
	
	/**
	 * 将Element输出到指定的输出流， GBK编码，缩进3空格。
	 * 注意：此方法不自动关闭流，如有需要，请在调用后手动关闭。
	 */
	public static void output(Element pElement, OutputStream pOs) throws IOException {
		output(pElement, pOs, "GBK");
	}
	
	/**
	 * 将Element输出到指定的输出流，pCharset指定编码，缩进3空格。
	 * 注意：此方法不自动关闭流，如有需要，请在调用后手动关闭。
	 */
	public static void output(Element pElement, OutputStream pOs, String pCharset) throws IOException {
		Format mFormat = Format.getRawFormat().setEncoding(pCharset).setIndent("   ");
		XMLOutputter mXMLOutputter = new XMLOutputter(mFormat);
		mXMLOutputter.output(pElement, pOs);
	}
	
	/**
	 * 将Document打印到控制台， GBK编码，缩进3空格。
	 */
	public static void print(Document pXmlDoc) {
		System.out.print(toStringFmt(pXmlDoc));
	}
	
	/**
	 * 将Element打印到控制台， GBK编码，缩进3空格。
	 */
	public static void print(Element pElement) {
		System.out.print(toStringFmt(pElement));
	}
	
	/**
	 * 将Document转换为GBK编码的字节数组，保持原格式。
	 * @param pXmlDoc 非标准输出报文
	 */
	public static byte[] toBytes(Document pXmlDoc) {
		return toBytes(pXmlDoc, "GBK");
	}
	
	/**
	 * 将Element转换为GBK编码的字节数组，保持原格式。
	 */
	public static byte[] toBytes(Element pElement) {
		return toBytes(pElement, "GBK");
	}
	
	/**
	 * 将Document转换为指定字符集编码的字节数组，保持原格式。
	 */
	public static byte[] toBytes(Document pXmlDoc, String pCharset) {
		Format mFormat = Format.getRawFormat().setEncoding(pCharset);
		XMLOutputter mXMLOutputter = new XMLOutputter(mFormat);
		//字节数组输出流
		ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
		try {
			/*<?xml version="1.0" encoding="GBK"?>
			   <TX><TX_HEADER><SYS_HDR_LEN>0</SYS_HDR_LEN><SYS_PKG_VRSN>01</SYS_PKG_VRSN><SYS_TTL_LEN>0</SYS_TTL_LEN><SYS_REQ_SEC_ID>102001</SYS_REQ_SEC_ID><SYS_SND_SEC_ID>108011</SYS_SND_SEC_ID><SYS_TX_CODE>P53819113</SYS_TX_CODE><SYS_TX_VRSN>01</SYS_TX_VRSN><SYS_TX_TYPE>020000</SYS_TX_TYPE><SYS_RESERVED>0</SYS_RESERVED><SYS_EVT_TRACE_ID>1020018041483154197003560</SYS_EVT_TRACE_ID><SYS_SND_SERIAL_NO>1000000000</SYS_SND_SERIAL_NO><SYS_PKG_TYPE>1</SYS_PKG_TYPE><SYS_MSG_LEN>0</SYS_MSG_LEN><SYS_IS_ENCRYPTED>0</SYS_IS_ENCRYPTED><SYS_ENCRYPT_TYPE>3</SYS_ENCRYPT_TYPE><SYS_COMPRESS_TYPE>0</SYS_COMPRESS_TYPE><SYS_EMB_MSG_LEN>0</SYS_EMB_MSG_LEN><SYS_REQ_TIME>20161220111637210</SYS_REQ_TIME><!--���෽½���ʱ��--><SYS_TIME_LEFT>000118916</SYS_TIME_LEFT><SYS_PKG_STS_TYPE>00</SYS_PKG_STS_TYPE><LocalID>510096</LocalID><remoteID>105005</remoteID></TX_HEADER><TX_BODY><COMMON><FILE_LIST_PACK><FILE_NUM>0</FILE_NUM><FILE_MODE>0</FILE_MODE><FILE_NODE /><FILE_NAME_PACK /><FILE_PATH_PACK /></FILE_LIST_PACK></COMMON><ENTITY><COM_ENTITY><Inst_Eng_ShrtNm>CCB</Inst_Eng_ShrtNm><Ins_Co_ID>010079</Ins_Co_ID><SvPt_Jrnl_No>108011rv11481771876015539</SvPt_Jrnl_No><!--#������ˮ��--><TXN_ITT_CHNL_ID>002911037830000        </TXN_ITT_CHNL_ID><TXN_ITT_CHNL_CGY_CODE>20170029</TXN_ITT_CHNL_CGY_CODE><CCBIns_ID>110378300</CCBIns_ID><CCB_EmpID>93910092</CCB_EmpID><OprgDay_Prd>20161218</OprgDay_Prd><!--#Ӫҵʱ��--><LNG_ID>zh-cn</LNG_ID></COM_ENTITY><APP_ENTITY><Plchd_Nm>������</Plchd_Nm><Plchd_Gnd_Cd>01</Plchd_Gnd_Cd><Plchd_Brth_Dt>19630626</Plchd_Brth_Dt><Plchd_Crdt_TpCd>1010</Plchd_Crdt_TpCd><Plchd_Crdt_No>320177196306261011</Plchd_Crdt_No><Plchd_Crdt_EfDt>20070924</Plchd_Crdt_EfDt><Plchd_Crdt_ExpDt>20181212</Plchd_Crdt_ExpDt><Plchd_Nat_Cd>156</Plchd_Nat_Cd><PlchdCtcAdrCtyRgon_Cd>156</PlchdCtcAdrCtyRgon_Cd><Plchd_Prov_Cd>110000</Plchd_Prov_Cd><Plchd_City_Cd>110000</Plchd_City_Cd><Plchd_CntyAndDstc_Cd>110101</Plchd_CntyAndDstc_Cd><Plchd_Dtl_Adr_Cntnt>���ĵĵĵĵ�������</Plchd_Dtl_Adr_Cntnt><Plchd_Comm_Adr /><Plchd_ZipECD>100000</Plchd_ZipECD><PlchdFixTelItnlDstcNo>86</PlchdFixTelItnlDstcNo><PlchdFixTelDmstDstcNo>000</PlchdFixTelDmstDstcNo><Plchd_Fix_TelNo>00000000</Plchd_Fix_TelNo><PlchdMoveTelItlDstcNo>86</PlchdMoveTelItlDstcNo><Plchd_Move_TelNo>13639075617</Plchd_Move_TelNo><Plchd_Email_Adr /><Plchd_Ocp_Cd>A0000</Plchd_Ocp_Cd><Plchd_Yr_IncmAm>0.00</Plchd_Yr_IncmAm><Fam_Yr_IncmAm>0.00</Fam_Yr_IncmAm><InsPrem_Bdgt_Amt>0.00</InsPrem_Bdgt_Amt><Rsdnt_TpCd>1</Rsdnt_TpCd><RspbPsn_Nm /><Plchd_And_Rcgn_ReTpCd>0133011</Plchd_And_Rcgn_ReTpCd><Rcgn_Nm>������</Rcgn_Nm><Rcgn_CPA_FullNm /><Rcgn_Gnd_Cd>02</Rcgn_Gnd_Cd><Rcgn_Brth_Dt>19811231</Rcgn_Brth_Dt><Rcgn_Crdt_TpCd>1010</Rcgn_Crdt_TpCd><Rcgn_Crdt_No>110101198112310024</Rcgn_Crdt_No><Rcgn_Crdt_EfDt /><Rcgn_Crdt_ExpDt>20200520</Rcgn_Crdt_ExpDt><Rcgn_Nat_Cd>156</Rcgn_Nat_Cd><RcgnCtcAdr_CtyRgon_Cd>156</RcgnCtcAdr_CtyRgon_Cd><Rcgn_Prov_Cd>110000</Rcgn_Prov_Cd><Rcgn_City_Cd>110000</Rcgn_City_Cd><Rcgn_CntyAndDstc_Cd>110101</Rcgn_CntyAndDstc_Cd><Rcgn_Dtl_Adr_Cntnt>ȥ����������������</Rcgn_Dtl_Adr_Cntnt><Rcgn_Comm_Adr /><Rcgn_ZipECD>110000</Rcgn_ZipECD><RcgnFixTelItnl_DstcNo /><RcgnFixTelDmst_DstcNo /><Rcgn_Fix_TelNo /><RcgnMoveTelItnlDstcNo>86</RcgnMoveTelItnlDstcNo><Rcgn_Move_TelNo>00000000000</Rcgn_Move_TelNo><Rcgn_Email_Adr /><Rcgn_Ocp_Cd>Y0000</Rcgn_Ocp_Cd><Rcgn_Yr_IncmAm>0.00</Rcgn_Yr_IncmAm><Rcgn_LvFr_Pps_Lnd_Num /><Pps_List /><Benf_Num>1</Benf_Num><Benf_List><Benf_Detail><AgIns_Benf_TpCd>1</AgIns_Benf_TpCd><Benf_SN>1</Benf_SN><Benf_Bnft_Seq>1</Benf_Bnft_Seq><Benf_Nm>����</Benf_Nm><Benf_Gnd_Cd>02</Benf_Gnd_Cd><Benf_Brth_Dt>19640101</Benf_Brth_Dt><Benf_Crdt_TpCd>1010</Benf_Crdt_TpCd><Benf_Crdt_No>110101196401010046</Benf_Crdt_No><Benf_Crdt_EfDt>19640101</Benf_Crdt_EfDt><Benf_Crdt_ExpDt>20200820</Benf_Crdt_ExpDt><Benf_Nat_Cd>156</Benf_Nat_Cd><Benf_And_Rcgn_ReTpCd>0133015</Benf_And_Rcgn_ReTpCd><Bnft_Pct>1.0000</Bnft_Pct><BenfCtcAdr_CtyRgon_Cd>156</BenfCtcAdr_CtyRgon_Cd><Benf_Prov_Cd>110000</Benf_Prov_Cd><Benf_City_Cd>110000</Benf_City_Cd><Benf_CntyAndDstc_Cd>110101</Benf_CntyAndDstc_Cd><Benf_Dtl_Adr_Cntnt>��������������������</Benf_Dtl_Adr_Cntnt><Benf_Comm_Adr /></Benf_Detail></Benf_List><AgIns_Pkg_ID /><Cvr_Num>1</Cvr_Num><Busi_List><Busi_Detail><Cvr_ID>011A0100</Cvr_ID><Cvr_Nm>�����ʱ�2����ȫ���գ�������)</Cvr_Nm><MainAndAdlIns_Ind>1</MainAndAdlIns_Ind><Ins_Cps>20</Ins_Cps><InsPrem_Amt>20000.00</InsPrem_Amt><Ins_Cvr /><Ins_Scm_Inf /><Opt_Part_DieIns_Amt /><FTm_Extr_Adl_InsPrem /><Emgr_CtcPsn /><EmgrCtcPsnAndRcReTpCd /><Emgr_Ctc_Tel /><AgIns_Ln_Ctr_ID /><Ln_Ctr_ExpDt /><Upd_Loan_Amt /><PrimBlInsPolcyVchr_No /><InsPrem_PyF_MtdCd>02</InsPrem_PyF_MtdCd><InsPrem_PyF_Prd_Num>1</InsPrem_PyF_Prd_Num><InsPrem_PyF_Cyc_Cd>0100</InsPrem_PyF_Cyc_Cd><ExpPrmmRcvModCgyCd /><SvBnf_Drw_Cyc_Cd /><ApntInsPremPyAdvnInd /><XtraDvdn_Pcsg_MtdCd /><RdAmtPyCls_Ind /><Anuty_Drw_CgyCd /><Anuty_Drw_Prd_Num /><Anuty_Drw_Cyc_Cd /><Anuty_Pcsg_MtdCd /><Ins_Yr_Prd_CgyCd>03</Ins_Yr_Prd_CgyCd><Ins_Ddln>5</Ins_Ddln><Ins_Cyc_Cd>03</Ins_Cyc_Cd><Ivs_MtdCd /><Auto_RnwCv_Ind /><Rsrv_Fld_1 /><Rsrv_Fld_2 /><Rsrv_Fld_3 /><Rsrv_Fld_4 /><Rsrv_Fld_5 /><Rsrv_Fld_6 /><Rsrv_Fld_7 /><Rsrv_Fld_8 /><Rsrv_Fld_9 /><Rsrv_Fld_10 /><IvsAc_Num>0</IvsAc_Num><PayAcctCode_List /></Busi_Detail></Busi_List><Ins_Bl_Prt_No>1010190000120</Ins_Bl_Prt_No><!--Ͷ����ӡˢ��--><Minr_Acm_Cvr /><InsPolcy_Pswd /><Inv_No /><Ntf_Itm_Ind>0</Ntf_Itm_Ind><Dspt_Pcsg_MtdCd>03</Dspt_Pcsg_MtdCd><Dspt_Arbtr_Inst_Nm /><InsPolcy_Rcv_MtdCd /><Ins_Prj_CgyCd /><PydFeeOutBill_CgyCd /><InsPolcyActSaleRgonID /><Ins_CsLs_Prvd_Rgon_ID /><Pstcrpt_Rmrk /><InsPolcy_Intnd_EfDt /><InsPolcy_Medm_TpCd>2</InsPolcy_Medm_TpCd><CstMgr_Blng_CCBIns_ID /><Lv1_Br_No>110000000</Lv1_Br_No><BO_Nm>�й������йɷ����޹�˾�������֧��</BO_Nm><BOInsPrAgnBsnLcns_ECD>110106667505023002</BOInsPrAgnBsnLcns_ECD><BOInsPrAgBsnLcnVld_Dt>20991231</BOInsPrAgBsnLcnVld_Dt><BO_Sale_Stff_Nm>������</BO_Sale_Stff_Nm><BO_Sale_Stff_ID>93910092</BO_Sale_Stff_ID><Sale_Stff_AICSQCtf_ID>02008101100000153255</Sale_Stff_AICSQCtf_ID><InsAgnCrStQuaCtVld_Dt>20221005</InsAgnCrStQuaCtVld_Dt><BOIChOfAgInsBsnPnp_ID>60516155</BOIChOfAgInsBsnPnp_ID><BOIChOfAgInsBsnPnp_Nm /><Rnew_PyF_PyMd_Cd /><Plchd_PyF_AccNo /><Plchd_Drw_AccNo /><Rcgn_AccNo /><Benf_AccNo /><Rsrv_Fld_11 /><Rsrv_Fld_12 /><Rsrv_Fld_13 /><Rsrv_Fld_14 /><Rsrv_Fld_15 /><Rsrv_Fld_16 /><Rsrv_Fld_17 /><Rsrv_Fld_18 /><Rsrv_Fld_19 /><Rsrv_Fld_20 /><Cst_Rsk_Tlrnc_Cpy_Cd>02</Cst_Rsk_Tlrnc_Cpy_Cd><Rsk_Evlt_AvlDt>20171118</Rsk_Evlt_AvlDt></APP_ENTITY></ENTITY></TX_BODY><TX_EMB /></TX>
			 */
			mXMLOutputter.output(pXmlDoc, mBaos);//文档数据被写入一个字节数组输出流
		} catch (IOException ex) {
			cLogger.error("Xml.Document-->byte[]异常！", ex);
		}
		return mBaos.toByteArray();//返回新分配的字节数组
	}
	
	/**
	 * 将Element转换为指定字符集编码的字节数组，保持原格式。
	 */
	public static byte[] toBytes(Element pElement, String pCharset) {
		Format mFormat = Format.getRawFormat().setEncoding(pCharset);
		XMLOutputter mXMLOutputter = new XMLOutputter(mFormat);
		ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
		try {
			mXMLOutputter.output(pElement, mBaos);
		} catch (IOException ex) {
			cLogger.error("Xml.Element-->byte[]异常！", ex);
		}
		return mBaos.toByteArray();
	}
	
	/**
	 * 缩进3空格，忽略声明中的编码。
	 */
	public static String toStringFmt(Document pXmlDoc) {
		return toStringFmt(pXmlDoc, "");
	}
	
	/**
	 * 缩进3空格。
	 * pEncodingDecl：声明中的编码，null-忽略整个xml声明，""-忽略声明中的编码
	 */
	public static String toStringFmt(Document pXmlDoc, String pEncodingDecl) {
		Format mFormat = Format.getRawFormat().setIndent("   ");
		if (null == pEncodingDecl) {
			mFormat.setOmitDeclaration(true);
		} else if ("".equals(pEncodingDecl)) {
			mFormat.setOmitEncoding(true);
		} else {
			mFormat.setEncoding(pEncodingDecl);
		}
		return new XMLOutputter(mFormat).outputString(pXmlDoc);
	}
	
	
	/**
	 * 缩进3空格。
	 * pEncodingDecl：声明中的编码，null-忽略整个xml声明，""-忽略声明中的编码
	 */
	public static String toStringFmtNull(Document pXmlDoc, String pEncodingDecl) {
		Format mFormat = Format.getRawFormat().setIndent("");
		if (null == pEncodingDecl) {
			mFormat.setOmitDeclaration(true);
		} else if ("".equals(pEncodingDecl)) {
			mFormat.setOmitEncoding(true);
		} else {
			mFormat.setEncoding(pEncodingDecl);
		}
		return new XMLOutputter(mFormat).outputString(pXmlDoc);
	}
	
	/**
	 * 缩进3空格。
	 */
	public static String toStringFmt(Element pElement) {
		Format mFormat = Format.getRawFormat().setIndent("   ");
		return new XMLOutputter(mFormat).outputString(pElement);
	}
	
	/**
	 * 保持原格式，忽略声明中的编码。
	 */
	public static String toString(Document pXmlDoc) {
		return toString(pXmlDoc,"");
	}
	
	/**
	 * 保持原格式。
	 * pEncodingDecl：声明中的编码，null-忽略整个xml声明，""-忽略声明中的编码
	 * @param pXmlDoc XSL文件
	 * @param pEncodingDecl 声明编码
	 */
	public static String toString(Document pXmlDoc, String pEncodingDecl) {
		//得到原格式对象
		Format mFormat = Format.getRawFormat();
		//声明编码为空
		if (null == pEncodingDecl) {
			//设置忽略声明
			mFormat.setOmitDeclaration(true);
		//或者声明编码为空字符
		} else if ("".equals(pEncodingDecl)) {
			//设置忽略声明
			mFormat.setOmitEncoding(true);
		} else {
			//否则设置编码为声明编码
			mFormat.setEncoding(pEncodingDecl);
		}
		//原格式对象构建XML输出器输出XSL文件字符串
		return new XMLOutputter(mFormat).outputString(pXmlDoc);
	}
	
	/**
	 * 保持原格式。
	 */
	public static String toString(Element pElement) {
		Format mFormat = Format.getRawFormat();
		return new XMLOutputter(mFormat).outputString(pElement);
	}
}
