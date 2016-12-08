package com.sinosoft.midplat.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.cache.FileCacheManage;
import com.sinosoft.midplat.exception.MidplatException;

/**
 * �����������
 * @author yuantongxin
 */
public class RuleParser implements XmlTag {
	private final static Logger cLogger = Logger.getLogger(RuleParser.class);
	
	private final XslCache cRuleXsl;//����XSL
	private final XmlConf cRuleXml;//����XML
	
	public RuleParser() {
		cRuleXsl = RuleXsl.newInstance();
		cRuleXml = RuleXml.newInstance();
	}
	
	public RuleParser(XslCache pRuleXsl) {
		cRuleXsl = pRuleXsl;
		cRuleXml = null;
	}
	
	public RuleParser(XmlConf pRuleXml) {
		cRuleXsl = null;
		cRuleXml = pRuleXml;
	}
	
	/**
	 * ����
	 * @param pXmlDoc XML�ĵ�
	 * @throws Exception
	 */
	public void check(Document pXmlDoc) throws Exception {
		cLogger.info("Into RuleParser.check()...");
		//����XSL�ǿ��ҹ���XSL��ȡ�˿�-������ ���÷ǿ�
		if (null!=cRuleXsl && null!=cRuleXsl.getCache()) {
			//ͨ��XSL����XML�ĵ�
			checkByXsl(pXmlDoc);
			//����XML�ǿ��ҹ���XML��ȡxml�����ļ��Ļ������ǿ�
		} else if (null!=cRuleXml && null!=cRuleXml.getConf()) {
			//ͨ��XML����
			checkByXml(pXmlDoc);
		} else {
			cLogger.warn("δ����У�����");
		}
		
		cLogger.info("Out RuleParser.check()!");
	}
	
	/**
	 * ͨ��XSL����
	 * @param pXmlDoc
	 * @throws Exception
	 */
	public void checkByXsl(Document pXmlDoc) throws Exception {
		cLogger.info("Into RuleParser.checkByXsl()...");
		
		Document mMsgXml = 
			cRuleXsl.getCache().transform(pXmlDoc);
		cLogger.info(JdomUtil.toString(mMsgXml));
		//Ͷ�����ڱ���Ϊ����
		String mErrMsg = mMsgXml.getRootElement().getChildText(msg);
		if (null != mErrMsg) {
			throw new MidplatException(mErrMsg);
		}
		
		cLogger.info("Out RuleParser.checkByXsl()!");
	}
	
	/**
	 * ͨ��XML����
	 * @param pXmlDoc XML�ĵ�
	 * @throws MidplatException
	 */
	public void checkByXml(Document pXmlDoc) throws MidplatException {
		cLogger.info("Into RuleParser.checkByXml()...");
		
		//XML�ĵ���Ԫ��
		Element mXmlRootEle = pXmlDoc.getRootElement();
		//����XML��Ԫ��
		Element mRuleRootEle = cRuleXml.getConf().getRootElement();
		//ͨ��XML����
		checkByXml(mXmlRootEle, mRuleRootEle);
		
		cLogger.info("Out RuleParser.checkByXml()!");
	}
	
	/**
	 * ͨ��XML����
	 * @param pXmlEle XMLԪ��
	 * @param pRuleEle ����Ԫ��
	 * @throws MidplatException
	 */
	@SuppressWarnings("unchecked")
	private void checkByXml(Element pXmlEle, Element pRuleEle) throws MidplatException {
		//����Ԫ���б�
		List<Element> mRuleEleList= pRuleEle.getChildren();
		//��������Ԫ���б�
		for (Element tRuleEle : mRuleEleList) {
			//����Ԫ�ر�ǩ
			String tEleTag = tRuleEle.getName();
			//Ԫ�ر�ǩΪ����
			if ("rule".equals(tEleTag)) {
				Object tObj = null;;
				try {
					//����һ��xpath��䷵�ط��������ĵ�һ���ڵ�
					tObj = XPath.selectSingleNode(pXmlEle, ".["+tRuleEle.getChildText(value)+']');
				} catch (Exception ex) {
					cLogger.warn("У����������"+tRuleEle.getChildText(value), ex);
				}
				//����Ԫ����Ԫ��ֵ�����ݷǿ�
				if (null != tObj) {
					//�׳����м�ƽ̨�쳣��Ϣȡ����Ԫ����Ԫ����Ϣ������ 
					throw new MidplatException(tRuleEle.getChildText(msg));
				}
			} else {
				//XMLԪ�ر�ǩ�б�
				List<Element> tXmlEleList = pXmlEle.getChildren(tEleTag);
				//����XMLԪ�ر�ǩ�б�
				for (Element ttXmlEle : tXmlEleList) {
					//ͨ��XML����
					checkByXml(ttXmlEle, tRuleEle);
				}
			}
		}
	}
}

class RuleXsl extends XslCache {
	private static final RuleXsl cThisIns = new RuleXsl();
	
	private static final String cPath = "conf/rule.xsl";
	
	private RuleXsl() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	public void load() {
		cLogger.info("Into RuleXsl.load()...");
		
		String mFilePath = SysInfo.cHome + cPath;
		cLogger.info("Start load " + mFilePath + "...");
		
		cXslFile = new File(mFilePath);
		
		recordStatus();
		
		cXslTrsf = loadXsl(cXslFile);
		cLogger.info("End load " + mFilePath + "!");
		
		//�Ƿ���������ļ�
		if (MidplatConf.newInstance().outConf()) {
			try {
				cLogger.info(
						IOTrans.toString(
								new InputStreamReader(
										new FileInputStream(cXslFile), "GBK")));
			} catch (IOException ex) {
				cLogger.error("��������ļ��쳣��", ex);
			}
		}
		
		cLogger.info("Out RuleXsl.load()!");
	}
	
	public static RuleXsl newInstance() {
		return cThisIns;
	}
}

class RuleXml extends XmlConf {
	private static final RuleXml cThisIns = new RuleXml();
	
	private static final String cPath = "conf/rule.xml";
	
	private RuleXml() {
		load();
		FileCacheManage.newInstance().register(cPath, this);
	}
	
	public void load() {
		cLogger.info("Into RuleXml.load()...");
		
		String mFilePath = SysInfo.cHome + cPath;
		cLogger.info("Start load " + mFilePath + "...");
		
		cConfFile = new File(mFilePath);
		
		recordStatus();
		
		cConfDoc = loadXml(cConfFile);
		cLogger.info("End load " + mFilePath + "!");
		
		//�Ƿ���������ļ�
		if (MidplatConf.newInstance().outConf()) {
			cLogger.info(JdomUtil.toString(cConfDoc));
		}
		
		cLogger.info("Out RuleXml.load()!");
	}
	
	public static RuleXml newInstance() {
		return cThisIns;
	}
}