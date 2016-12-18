/**
 * ���൱ǰ��֧��JDOM 1.1
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
 * Java�ĵ�����ģ�͹���
 * @author yuantongxin
 */
public class JdomUtil {
	private final static Logger cLogger = Logger.getLogger(JdomUtil.class);
	
	/**
	 * ����GBK���빹��һ��Document���󣬺��Ա�ǩ֮��Ŀ��ַ�(�ո񡢻��С��Ʊ����)��
	 * ����ʧ�ܣ�����null��
	 */
	public static Document build(byte[] pBytes) {
		return build(pBytes, "GBK");
	}
	
	/**
	 * ����ָ���ַ������빹��һ��Document���󣬺��Ա�ǩ֮��Ŀ��ַ�(�ո񡢻��С��Ʊ����)��
	 * ����ʧ�ܣ�����null��
	 */
	public static Document build(byte[] pBytes, String pCharset) {
		return build(pBytes, pCharset, true);
	}

	/**
	 * ����ָ���ַ������빹��һ��Document����
	 * OmitSpace: ��ʶ�Ƿ���Ա�ǩ֮��Ŀ��ַ�(�ո񡢻��С��Ʊ����)��true-����
	 * ����ʧ�ܣ�����null��
	 */
	public static Document build(byte[] pBytes, String pCharset, boolean OmitSpace) {
		return build(new ByteArrayInputStream(pBytes), pCharset, OmitSpace);
	}
	
	/**
	 * ����GBK���빹��һ��Document���󣬺��Ա�ǩ֮��Ŀ��ַ�(�ո񡢻��С��Ʊ����)��
	 * ����ʧ�ܣ�����null��
	 */
	public static Document build(InputStream pIs) {
		return build(pIs, "GBK");
	}
	
	/**
	 * ����ָ���ַ������빹��һ��Document���󣬺��Ա�ǩ֮��Ŀ��ַ�(�ո񡢻��С��Ʊ����)��
	 * ����ʧ�ܣ�����null��
	 */
	public static Document build(InputStream pIs, String pCharset) {
		//����һ���ĵ�����[File:/task/20161216/newccb/local/1012/P53819113in_noStd.xml,UTF-8,���Ա�ǩ֮��Ŀ��ַ�]
		return build(pIs, pCharset, true);
	}
	
	/**
	 * ����ָ���ַ������빹��һ��Document����
	 * OmitSpace: ��ʶ�Ƿ���Ա�ǩ֮��Ŀ��ַ�(�ո񡢻��С��Ʊ����)��true-����
	 * ����ʧ�ܣ�����null��
	 */
	public static Document build(InputStream pIs, String pCharset, boolean OmitSpace) {
		try {
			InputSource mInputSource = new InputSource(pIs);
			mInputSource.setEncoding(pCharset);
		
			SAXBuilder mSAXBuilder = new SAXBuilder();
			mSAXBuilder.setIgnoringBoundaryWhitespace(OmitSpace);
			return mSAXBuilder.build(mInputSource);
		} catch(Exception ex) {
			cLogger.error("xml��ʽ���󣬽���ʧ�ܣ�", ex);
			return null;
		}
	}
	
	/**
	 * ��ָ���ַ�������һ��Document���󣬺��Ա�ǩ֮��Ŀ��ַ�(�ո񡢻��С��Ʊ����)��
	 * ����ʧ�ܣ�����null��
	 */
	public static Document build(String pXmlStr) {
		return build(pXmlStr, true);
	}
	
	/**
	 * ��ָ���ַ�������һ��Document����
	 * OmitSpace: ��ʶ�Ƿ���Ա�ǩ֮��Ŀ��ַ�(�ո񡢻��С��Ʊ����)��true-����
	 * ����ʧ�ܣ�����null��
	 */
	public static Document build(String pXmlStr, boolean OmitSpace) {
		try {
			SAXBuilder mSAXBuilder = new SAXBuilder();
			mSAXBuilder.setIgnoringBoundaryWhitespace(OmitSpace);
			return mSAXBuilder.build(
					new StringReader(pXmlStr));
		} catch(Exception ex) {
			cLogger.error("xml��ʽ���󣬽���ʧ�ܣ�", ex);
			return null;
		}
	}
	
	/**
	 * ��Document�����ָ�����������GBK���룬����3�ո�
	 * ע�⣺�˷������Զ��ر�����������Ҫ�����ڵ��ú��ֶ��رա�
	 */
	public static void output(Document pXmlDoc, OutputStream pOs) throws IOException {
		output(pXmlDoc, pOs, "GBK");
	}
	
	/**
	 * ��Document�����ָ�����������pCharsetָ�����룬����3�ո�
	 * ע�⣺�˷������Զ��ر�����������Ҫ�����ڵ��ú��ֶ��رա�
	 */
	public static void output(Document pXmlDoc, OutputStream pOs, String pCharset) throws IOException {
		//�õ�ԭ��ʽָ����������3�ո�
		Format mFormat = Format.getRawFormat().setEncoding(pCharset).setIndent("   ");
		//ʹ�ø�ʽ����XML�����
		XMLOutputter mXMLOutputter = new XMLOutputter(mFormat);
		//���XML�ĵ����ֽ������
		mXMLOutputter.output(pXmlDoc, pOs);
	}
	
	/**
	 * ��Element�����ָ����������� GBK���룬����3�ո�
	 * ע�⣺�˷������Զ��ر�����������Ҫ�����ڵ��ú��ֶ��رա�
	 */
	public static void output(Element pElement, OutputStream pOs) throws IOException {
		output(pElement, pOs, "GBK");
	}
	
	/**
	 * ��Element�����ָ�����������pCharsetָ�����룬����3�ո�
	 * ע�⣺�˷������Զ��ر�����������Ҫ�����ڵ��ú��ֶ��رա�
	 */
	public static void output(Element pElement, OutputStream pOs, String pCharset) throws IOException {
		Format mFormat = Format.getRawFormat().setEncoding(pCharset).setIndent("   ");
		XMLOutputter mXMLOutputter = new XMLOutputter(mFormat);
		mXMLOutputter.output(pElement, pOs);
	}
	
	/**
	 * ��Document��ӡ������̨�� GBK���룬����3�ո�
	 */
	public static void print(Document pXmlDoc) {
		System.out.print(toStringFmt(pXmlDoc));
	}
	
	/**
	 * ��Element��ӡ������̨�� GBK���룬����3�ո�
	 */
	public static void print(Element pElement) {
		System.out.print(toStringFmt(pElement));
	}
	
	/**
	 * ��Documentת��ΪGBK������ֽ����飬����ԭ��ʽ��
	 */
	public static byte[] toBytes(Document pXmlDoc) {
		return toBytes(pXmlDoc, "GBK");
	}
	
	/**
	 * ��Elementת��ΪGBK������ֽ����飬����ԭ��ʽ��
	 */
	public static byte[] toBytes(Element pElement) {
		return toBytes(pElement, "GBK");
	}
	
	/**
	 * ��Documentת��Ϊָ���ַ���������ֽ����飬����ԭ��ʽ��
	 */
	public static byte[] toBytes(Document pXmlDoc, String pCharset) {
		Format mFormat = Format.getRawFormat().setEncoding(pCharset);
		XMLOutputter mXMLOutputter = new XMLOutputter(mFormat);
		ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
		try {
			mXMLOutputter.output(pXmlDoc, mBaos);
		} catch (IOException ex) {
			cLogger.error("Xml.Document-->byte[]�쳣��", ex);
		}
		return mBaos.toByteArray();
	}
	
	/**
	 * ��Elementת��Ϊָ���ַ���������ֽ����飬����ԭ��ʽ��
	 */
	public static byte[] toBytes(Element pElement, String pCharset) {
		Format mFormat = Format.getRawFormat().setEncoding(pCharset);
		XMLOutputter mXMLOutputter = new XMLOutputter(mFormat);
		ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
		try {
			mXMLOutputter.output(pElement, mBaos);
		} catch (IOException ex) {
			cLogger.error("Xml.Element-->byte[]�쳣��", ex);
		}
		return mBaos.toByteArray();
	}
	
	/**
	 * ����3�ո񣬺��������еı��롣
	 */
	public static String toStringFmt(Document pXmlDoc) {
		return toStringFmt(pXmlDoc, "");
	}
	
	/**
	 * ����3�ո�
	 * pEncodingDecl�������еı��룬null-��������xml������""-���������еı���
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
	 * ����3�ո�
	 * pEncodingDecl�������еı��룬null-��������xml������""-���������еı���
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
	 * ����3�ո�
	 */
	public static String toStringFmt(Element pElement) {
		Format mFormat = Format.getRawFormat().setIndent("   ");
		return new XMLOutputter(mFormat).outputString(pElement);
	}
	
	/**
	 * ����ԭ��ʽ�����������еı��롣
	 */
	public static String toString(Document pXmlDoc) {
		return toString(pXmlDoc,"");
	}
	
	/**
	 * ����ԭ��ʽ��
	 * pEncodingDecl�������еı��룬null-��������xml������""-���������еı���
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
	 * ����ԭ��ʽ��
	 */
	public static String toString(Element pElement) {
		Format mFormat = Format.getRawFormat();
		return new XMLOutputter(mFormat).outputString(pElement);
	}
}
