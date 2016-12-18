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
		return build(pIs, pCharset, true);
	}
	
	/**
	 * 采用指定字符集编码构建一个Document对象。
	 * OmitSpace: 标识是否忽略标签之间的空字符(空格、换行、制表符等)，true-忽略
	 * 构建失败，返回null。
	 */
	public static Document build(InputStream pIs, String pCharset, boolean OmitSpace) {
		try {
			InputSource mInputSource = new InputSource(pIs);
			mInputSource.setEncoding(pCharset);
		
			SAXBuilder mSAXBuilder = new SAXBuilder();
			mSAXBuilder.setIgnoringBoundaryWhitespace(OmitSpace);
			return mSAXBuilder.build(mInputSource);
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
		ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
		try {
			mXMLOutputter.output(pXmlDoc, mBaos);
		} catch (IOException ex) {
			cLogger.error("Xml.Document-->byte[]异常！", ex);
		}
		return mBaos.toByteArray();
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
	 */
	public static String toString(Document pXmlDoc, String pEncodingDecl) {
		Format mFormat = Format.getRawFormat();
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
	 * 保持原格式。
	 */
	public static String toString(Element pElement) {
		Format mFormat = Format.getRawFormat();
		return new XMLOutputter(mFormat).outputString(pElement);
	}
}
