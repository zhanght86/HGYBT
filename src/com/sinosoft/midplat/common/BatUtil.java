package com.sinosoft.midplat.common;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

public class BatUtil implements XmlTag {
	private final static Logger cLogger = Logger.getLogger(BatUtil.class);

	public static void main(String[] args) {

		System.out.println("*******");
	}

	/**
	 * ����pFlag��pMessage�����ɼ򵥵ı�׼���ر��ġ�
	 */
	public static Document getSimpOutXml(int pFlag, String pMessage) {
		Element mFlag = new Element(Flag);
		mFlag.addContent(String.valueOf(pFlag));

		Element mDesc = new Element(Desc);
		mDesc.addContent(pMessage);

		Element mHead = new Element(Head);
		mHead.addContent(mFlag);
		mHead.addContent(mDesc);

		Element mTranData = new Element(TranData);
		mTranData.addContent(mHead);

		return new Document(mTranData);
	}
	

}