package com.sinosoft.midplat.newabc.util;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlOper {
	private XmlOper() {
	}

	/**
	 * �������ƣ�getNodeList
	 * <p>
	 * �������ܣ���ȡ�����parent�������ӽ��
	 * <p>
	 * ����˵����@param parent ����˵����@return
	 * <p>
	 */
	public static NodeList getNodeList(Element parent) {
		return parent.getChildNodes();
	}

	/**
	 * �������ƣ�getElementsByName
	 * <p>
	 * �������ܣ��ڸ�����в�ѯָ�����ƵĽ�㼯
	 * <p>
	 * ����˵����@param parent ����˵����@param name ����˵����@return
	 * <p>
	 */
	public static Element[] getElementsByName(Element parent, String name) {
		ArrayList resList = new ArrayList();
		NodeList nl = getNodeList(parent);
		for (int i = 0; i < nl.getLength(); i++) {
			Node nd = nl.item(i);
			if (nd.getNodeName().equals(name)) {
				resList.add(nd);
			}
		}
		Element[] res = new Element[resList.size()];
		for (int i = 0; i < resList.size(); i++) {
			res[0] = (Element) resList.get(i);
		}
		System.out.println(parent.getNodeName() + "'s children of " + name
				+ "'s num:" + res.length);
		return res;
	}

	/**
	 * �������ƣ�getElementName
	 * <p>
	 * �������ܣ���ȡָ��Element������
	 * <p>
	 * ����˵����@param element ����˵����@return
	 * <p>
	 * ���أ�String
	 * <p>
	 */
	public static String getElementName(Element element) {
		return element.getNodeName();
	}

	/**
	 * �������ƣ�getElementValue
	 * <p>
	 * �������ܣ���ȡָ��Element��ֵ
	 * <p>
	 * ����˵����@param element ����˵����@return
	 * <p>
	 * ���أ�String
	 * <p>
	 */
	public static String getElementValue(Element element) {
		NodeList nl = element.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			if (nl.item(i).getNodeType() == Node.TEXT_NODE)// ��һ��Text Node
			{
				System.out.println(element.getNodeName() + " has a Text Node.");
				return element.getFirstChild().getNodeValue();
			}
		}
		System.out.println(element.getNodeName() + " hasn't a Text Node.");
		return null;
	}

	/**
	 * �������ƣ�getElementAttr
	 * <p>
	 * �������ܣ���ȡָ��Element������attr��ֵ
	 * <p>
	 * ����˵����@param element ����˵����@param attr ����˵����@return
	 * <p>
	 * ���أ�String
	 * <p>
	 */
	public static String getElementAttr(Element element, String attr) {
		return element.getAttribute(attr);
	}

	/**
	 * �������ƣ�setElementValue
	 * <p>
	 * �������ܣ�����ָ��Element��ֵ
	 * <p>
	 * ����˵����@param element ����˵����@param val
	 * <p>
	 * ���أ�void
	 * <p>
	 */
	public static void setElementValue(Element element, String val) {
		Node node = element.getOwnerDocument().createTextNode(val);
		NodeList nl = element.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node nd = nl.item(i);
			if (nd.getNodeType() == Node.TEXT_NODE)// ��һ��Text Node
			{
				nd.setNodeValue(val);
				System.out.println("modify " + element.getNodeName()
						+ "'s node value succe.");
				return;
			}
		}
		System.out.println("new " + element.getNodeName()
				+ "'s node value succe.");
		element.appendChild(node);
	}

	/**
	 * �������ƣ�setElementAttr
	 * <p>
	 * �������ܣ����ý��Element������
	 * <p>
	 * ����˵����@param element ����˵����@param attr ����˵����@param attrVal
	 * <p>
	 * ���أ�void
	 * <p>
	 */
	public static void setElementAttr(Element element, String attr,
			String attrVal) {
		element.setAttribute(attr, attrVal);
	}

	/**
	 * �������ƣ�addElement
	 * <p>
	 * �������ܣ���parent�����ӽ��child
	 * <p>
	 * ����˵����@param parent ����˵����@param child
	 * <p>
	 * ���أ�void
	 * <p>
	 */
	public static void addElement(Element parent, Element child) {
		parent.appendChild(child);
	}

	/**
	 * �������ƣ�addElement
	 * <p>
	 * �������ܣ���parent�������ַ���tagName���ɵĽ��
	 * <p>
	 * ����˵����@param parent ����˵����@param tagName
	 * <p>
	 * ���أ�void
	 * <p>
	 */
	public static void addElement(Element parent, String tagName) {
		Document doc = parent.getOwnerDocument();
		Element child = doc.createElement(tagName);
		parent.appendChild(child);
	}

	/**
	 * �������ƣ�addElement
	 * <p>
	 * �������ܣ���parent������tagName��Text��㣬��ֵΪtext
	 * <p>
	 * ����˵����@param parent ����˵����@param tagName ����˵����@param text
	 * <p>
	 * ���أ�void
	 * <p>
	 */
	public static void addElement(Element parent, String tagName, String text) {
		Document doc = parent.getOwnerDocument();
		Element child = doc.createElement(tagName);
		setElementValue(child, text);
		parent.appendChild(child);
	}

	/**
	 * �������ƣ�removeElement
	 * <p>
	 * �������ܣ��������parent�µ�����ΪtagName�Ľ���Ƴ�
	 * <p>
	 * ����˵����@param parent ����˵����@param tagName
	 * <p>
	 * ���أ�void
	 * <p>
	 */
	public static void removeElement(Element parent, String tagName) {
		System.out.println("remove " + parent.getNodeName()
				+ "'s children by tagName " + tagName + " begin...");
		NodeList nl = parent.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node nd = nl.item(i);
			if (nd.getNodeName().equals(tagName)) {
				parent.removeChild(nd);
				System.out.println("remove child '" + nd + "' success.");
			}
		}
		System.out.println("remove " + parent.getNodeName()
				+ "'s children by tagName " + tagName + " end.");
	}

	/**
	 * ��ʼ��һ����Document���󷵻ء�
	 * 
	 * @return a Document
	 */
	public static Document newXMLDocument() {
		try {
			return newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * ��ʼ��һ��DocumentBuilder
	 * 
	 * @return a DocumentBuilder
	 * @throws ParserConfigurationException
	 */
	public static DocumentBuilder newDocumentBuilder()
			throws ParserConfigurationException {
		return newDocumentBuilderFactory().newDocumentBuilder();
	}

	/**
	 * ��ʼ��һ��DocumentBuilderFactory
	 * 
	 * @return a DocumentBuilderFactory
	 */
	public static DocumentBuilderFactory newDocumentBuilderFactory() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		return dbf;
	}

	/**
	 * �������һ��XML Stringת����һ��org.w3c.dom.Document���󷵻ء�
	 * 
	 * @param xmlString
	 *            һ������XML�淶���ַ�����
	 * @return a Document
	 */
	public static Document parseXMLDocument(String xmlString) {
		if (xmlString == null) {
			throw new IllegalArgumentException();
		}
		try {
			return newDocumentBuilder().parse(
					new InputSource(new StringReader(xmlString)));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * ����һ��������������Ϊһ��org.w3c.dom.Document���󷵻ء�
	 * 
	 * @param input
	 * @return a org.w3c.dom.Document
	 */
	public static Document parseXMLDocument(InputStream input) {
		if (input == null) {
			throw new IllegalArgumentException("����Ϊnull��");
		}
		try {
			return newDocumentBuilder().parse(input);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * ����һ���ļ�������ȡ���ļ�������Ϊһ��org.w3c.dom.Document���󷵻ء�
	 * 
	 * @param fileName
	 *            �������ļ����ļ���
	 * @return a org.w3c.dom.Document
	 */
	public static Document loadXMLDocumentFromFile(String fileName) {
		if (fileName == null) {
			throw new IllegalArgumentException("δָ���ļ�����������·����");
		}
		try {
			return newDocumentBuilder().parse(new File(fileName));
		} catch (SAXException e) {
			throw new IllegalArgumentException("Ŀ���ļ���" + fileName
					+ "�����ܱ���ȷ����ΪXML��\n" + e.getMessage());
		} catch (IOException e) {
			throw new IllegalArgumentException("���ܻ�ȡĿ���ļ���" + fileName + "����\n"
					+ e.getMessage());
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * ����һ���ڵ㣬���ýڵ�����¹����Document�С�
	 * 
	 * @param node
	 *            a Document node
	 * @return a new Document
	 */
	public static Document newXMLDocument(Node node) {
		Document doc = newXMLDocument();
		doc.appendChild(doc.importNode(node, true));
		return doc;
	}

	/**
	 * �������һ��DOM Node����������ַ��������ʧ���򷵻�һ�����ַ���""��
	 * 
	 * @param node
	 *            DOM Node ����
	 * @return a XML String from node
	 */
	public static String nodeToString(Node node) {
		if (node == null) {
			throw new IllegalArgumentException();
		}
		Transformer transformer = newTransformer();
		if (transformer != null) {
			try {
				StringWriter sw = new StringWriter();
				transformer
						.transform(new DOMSource(node), new StreamResult(sw));
				return sw.toString();
			} catch (TransformerException te) {
				throw new RuntimeException(te.getMessage());

			}
		}
		return errXMLString("��������XML��Ϣ��");
	}

	/**
	 * ��ȡһ��Transformer��������ʹ��ʱ������ͬ�ĳ�ʼ����������ȡ������Ϊ����������
	 * 
	 * @return a Transformer encoding gb2312
	 */
	public static Transformer newTransformer() {
		try {
			Transformer transformer = TransformerFactory.newInstance()
					.newTransformer();
			Properties properties = transformer.getOutputProperties();
			properties.setProperty(OutputKeys.ENCODING, "gb2312");
			properties.setProperty(OutputKeys.METHOD, "xml");
			properties.setProperty(OutputKeys.VERSION, "1.0");
			properties.setProperty(OutputKeys.INDENT, "no");
			transformer.setOutputProperties(properties);
			return transformer;
		} catch (TransformerConfigurationException tce) {
			throw new RuntimeException(tce.getMessage());
		}
	}

	/**
	 * ����һ��XML�����Ĵ�����Ϣ����ʾ��Ϣ��TITLEΪ��ϵͳ����֮����ʹ���ַ���ƴװ����Ҫ��������һ�� �������쳣���֡�
	 * 
	 * @param errMsg
	 *            ��ʾ������Ϣ
	 * @return a XML String show err msg
	 */
	public static String errXMLString(String errMsg) {
		StringBuffer msg = new StringBuffer(100);
		msg.append("<?xml version=\"1.0\" encoding=\"gb2312\" ?>");
		msg.append("<errNode title=\"ϵͳ����\" errMsg=\"" + errMsg + "\"/>");
		return msg.toString();
	}

	/**
	 * ����һ��XML�����Ĵ�����Ϣ����ʾ��Ϣ��TITLEΪ��ϵͳ����
	 * 
	 * @param errMsg
	 *            ��ʾ������Ϣ
	 * @param errClass
	 *            �׳��ô�����࣬������ȡ������Դ��Ϣ��
	 * @return a XML String show err msg
	 */
	public static String errXMLString(String errMsg, Class errClass) {
		StringBuffer msg = new StringBuffer(100);
		msg.append("<?xml version=\"1.0\" encoding=\"gb2312\" ?>");
		msg.append("<errNode title=\"ϵͳ����\" errMsg=\"" + errMsg
				+ "\" errSource=\"" + errClass.getName() + "\"/>");
		return msg.toString();
	}

	/**
	 * ����һ��XML�����Ĵ�����Ϣ��
	 * 
	 * @param title
	 *            ��ʾ��title
	 * @param errMsg
	 *            ��ʾ������Ϣ
	 * @param errClass
	 *            �׳��ô�����࣬������ȡ������Դ��Ϣ��
	 * @return a XML String show err msg
	 */
	public static String errXMLString(String title, String errMsg,
			Class errClass) {
		StringBuffer msg = new StringBuffer(100);
		msg.append("<?xml version=\"1.0\" encoding=\"gb2312\" ?>");
		msg.append("<errNode title=\"" + title + "\" errMsg=\"" + errMsg
				+ "\" errSource=\"" + errClass.getName() + "\"/>");
		return msg.toString();
	}
}
