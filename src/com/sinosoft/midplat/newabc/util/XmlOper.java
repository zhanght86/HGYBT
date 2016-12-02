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
	 * 方法名称：getNodeList
	 * <p>
	 * 方法功能：获取父结点parent的所有子结点
	 * <p>
	 * 参数说明：@param parent 参数说明：@return
	 * <p>
	 */
	public static NodeList getNodeList(Element parent) {
		return parent.getChildNodes();
	}

	/**
	 * 方法名称：getElementsByName
	 * <p>
	 * 方法功能：在父结点中查询指定名称的结点集
	 * <p>
	 * 参数说明：@param parent 参数说明：@param name 参数说明：@return
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
	 * 方法名称：getElementName
	 * <p>
	 * 方法功能：获取指定Element的名称
	 * <p>
	 * 参数说明：@param element 参数说明：@return
	 * <p>
	 * 返回：String
	 * <p>
	 */
	public static String getElementName(Element element) {
		return element.getNodeName();
	}

	/**
	 * 方法名称：getElementValue
	 * <p>
	 * 方法功能：获取指定Element的值
	 * <p>
	 * 参数说明：@param element 参数说明：@return
	 * <p>
	 * 返回：String
	 * <p>
	 */
	public static String getElementValue(Element element) {
		NodeList nl = element.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			if (nl.item(i).getNodeType() == Node.TEXT_NODE)// 是一个Text Node
			{
				System.out.println(element.getNodeName() + " has a Text Node.");
				return element.getFirstChild().getNodeValue();
			}
		}
		System.out.println(element.getNodeName() + " hasn't a Text Node.");
		return null;
	}

	/**
	 * 方法名称：getElementAttr
	 * <p>
	 * 方法功能：获取指定Element的属性attr的值
	 * <p>
	 * 参数说明：@param element 参数说明：@param attr 参数说明：@return
	 * <p>
	 * 返回：String
	 * <p>
	 */
	public static String getElementAttr(Element element, String attr) {
		return element.getAttribute(attr);
	}

	/**
	 * 方法名称：setElementValue
	 * <p>
	 * 方法功能：设置指定Element的值
	 * <p>
	 * 参数说明：@param element 参数说明：@param val
	 * <p>
	 * 返回：void
	 * <p>
	 */
	public static void setElementValue(Element element, String val) {
		Node node = element.getOwnerDocument().createTextNode(val);
		NodeList nl = element.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node nd = nl.item(i);
			if (nd.getNodeType() == Node.TEXT_NODE)// 是一个Text Node
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
	 * 方法名称：setElementAttr
	 * <p>
	 * 方法功能：设置结点Element的属性
	 * <p>
	 * 参数说明：@param element 参数说明：@param attr 参数说明：@param attrVal
	 * <p>
	 * 返回：void
	 * <p>
	 */
	public static void setElementAttr(Element element, String attr,
			String attrVal) {
		element.setAttribute(attr, attrVal);
	}

	/**
	 * 方法名称：addElement
	 * <p>
	 * 方法功能：在parent下增加结点child
	 * <p>
	 * 参数说明：@param parent 参数说明：@param child
	 * <p>
	 * 返回：void
	 * <p>
	 */
	public static void addElement(Element parent, Element child) {
		parent.appendChild(child);
	}

	/**
	 * 方法名称：addElement
	 * <p>
	 * 方法功能：在parent下增加字符串tagName生成的结点
	 * <p>
	 * 参数说明：@param parent 参数说明：@param tagName
	 * <p>
	 * 返回：void
	 * <p>
	 */
	public static void addElement(Element parent, String tagName) {
		Document doc = parent.getOwnerDocument();
		Element child = doc.createElement(tagName);
		parent.appendChild(child);
	}

	/**
	 * 方法名称：addElement
	 * <p>
	 * 方法功能：在parent下增加tagName的Text结点，且值为text
	 * <p>
	 * 参数说明：@param parent 参数说明：@param tagName 参数说明：@param text
	 * <p>
	 * 返回：void
	 * <p>
	 */
	public static void addElement(Element parent, String tagName, String text) {
		Document doc = parent.getOwnerDocument();
		Element child = doc.createElement(tagName);
		setElementValue(child, text);
		parent.appendChild(child);
	}

	/**
	 * 方法名称：removeElement
	 * <p>
	 * 方法功能：将父结点parent下的名称为tagName的结点移除
	 * <p>
	 * 参数说明：@param parent 参数说明：@param tagName
	 * <p>
	 * 返回：void
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
	 * 初始化一个空Document对象返回。
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
	 * 初始化一个DocumentBuilder
	 * 
	 * @return a DocumentBuilder
	 * @throws ParserConfigurationException
	 */
	public static DocumentBuilder newDocumentBuilder()
			throws ParserConfigurationException {
		return newDocumentBuilderFactory().newDocumentBuilder();
	}

	/**
	 * 初始化一个DocumentBuilderFactory
	 * 
	 * @return a DocumentBuilderFactory
	 */
	public static DocumentBuilderFactory newDocumentBuilderFactory() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		return dbf;
	}

	/**
	 * 将传入的一个XML String转换成一个org.w3c.dom.Document对象返回。
	 * 
	 * @param xmlString
	 *            一个符合XML规范的字符串表达。
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
	 * 给定一个输入流，解析为一个org.w3c.dom.Document对象返回。
	 * 
	 * @param input
	 * @return a org.w3c.dom.Document
	 */
	public static Document parseXMLDocument(InputStream input) {
		if (input == null) {
			throw new IllegalArgumentException("参数为null！");
		}
		try {
			return newDocumentBuilder().parse(input);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 给定一个文件名，获取该文件并解析为一个org.w3c.dom.Document对象返回。
	 * 
	 * @param fileName
	 *            待解析文件的文件名
	 * @return a org.w3c.dom.Document
	 */
	public static Document loadXMLDocumentFromFile(String fileName) {
		if (fileName == null) {
			throw new IllegalArgumentException("未指定文件名及其物理路径！");
		}
		try {
			return newDocumentBuilder().parse(new File(fileName));
		} catch (SAXException e) {
			throw new IllegalArgumentException("目标文件（" + fileName
					+ "）不能被正确解析为XML！\n" + e.getMessage());
		} catch (IOException e) {
			throw new IllegalArgumentException("不能获取目标文件（" + fileName + "）！\n"
					+ e.getMessage());
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 给定一个节点，将该节点加入新构造的Document中。
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
	 * 将传入的一个DOM Node对象输出成字符串。如果失败则返回一个空字符串""。
	 * 
	 * @param node
	 *            DOM Node 对象。
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
		return errXMLString("不能生成XML信息！");
	}

	/**
	 * 获取一个Transformer对象，由于使用时都做相同的初始化，所以提取出来作为公共方法。
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
	 * 返回一段XML表述的错误信息。提示信息的TITLE为：系统错误。之所以使用字符串拼装，主要是这样做一般 不会有异常出现。
	 * 
	 * @param errMsg
	 *            提示错误信息
	 * @return a XML String show err msg
	 */
	public static String errXMLString(String errMsg) {
		StringBuffer msg = new StringBuffer(100);
		msg.append("<?xml version=\"1.0\" encoding=\"gb2312\" ?>");
		msg.append("<errNode title=\"系统错误\" errMsg=\"" + errMsg + "\"/>");
		return msg.toString();
	}

	/**
	 * 返回一段XML表述的错误信息。提示信息的TITLE为：系统错误
	 * 
	 * @param errMsg
	 *            提示错误信息
	 * @param errClass
	 *            抛出该错误的类，用于提取错误来源信息。
	 * @return a XML String show err msg
	 */
	public static String errXMLString(String errMsg, Class errClass) {
		StringBuffer msg = new StringBuffer(100);
		msg.append("<?xml version=\"1.0\" encoding=\"gb2312\" ?>");
		msg.append("<errNode title=\"系统错误\" errMsg=\"" + errMsg
				+ "\" errSource=\"" + errClass.getName() + "\"/>");
		return msg.toString();
	}

	/**
	 * 返回一段XML表述的错误信息。
	 * 
	 * @param title
	 *            提示的title
	 * @param errMsg
	 *            提示错误信息
	 * @param errClass
	 *            抛出该错误的类，用于提取错误来源信息。
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
