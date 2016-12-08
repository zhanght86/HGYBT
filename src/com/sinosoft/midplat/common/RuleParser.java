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
 * 规则解析器类
 * @author yuantongxin
 */
public class RuleParser implements XmlTag {
	private final static Logger cLogger = Logger.getLogger(RuleParser.class);
	
	private final XslCache cRuleXsl;//规则XSL
	private final XmlConf cRuleXml;//规则XML
	
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
	 * 检验
	 * @param pXmlDoc XML文档
	 * @throws Exception
	 */
	public void check(Document pXmlDoc) throws Exception {
		cLogger.info("Into RuleParser.check()...");
		//规则XSL非空且规则XSL获取端口-处理类 配置非空
		if (null!=cRuleXsl && null!=cRuleXsl.getCache()) {
			//通过XSL检验XML文档
			checkByXsl(pXmlDoc);
			//规则XML非空且规则XML获取xml配置文件的缓存对象非空
		} else if (null!=cRuleXml && null!=cRuleXml.getConf()) {
			//通过XML检验
			checkByXml(pXmlDoc);
		} else {
			cLogger.warn("未配置校验规则");
		}
		
		cLogger.info("Out RuleParser.check()!");
	}
	
	/**
	 * 通过XSL检验
	 * @param pXmlDoc
	 * @throws Exception
	 */
	public void checkByXsl(Document pXmlDoc) throws Exception {
		cLogger.info("Into RuleParser.checkByXsl()...");
		
		Document mMsgXml = 
			cRuleXsl.getCache().transform(pXmlDoc);
		cLogger.info(JdomUtil.toString(mMsgXml));
		//投保日期必须为当天
		String mErrMsg = mMsgXml.getRootElement().getChildText(msg);
		if (null != mErrMsg) {
			throw new MidplatException(mErrMsg);
		}
		
		cLogger.info("Out RuleParser.checkByXsl()!");
	}
	
	/**
	 * 通过XML检验
	 * @param pXmlDoc XML文档
	 * @throws MidplatException
	 */
	public void checkByXml(Document pXmlDoc) throws MidplatException {
		cLogger.info("Into RuleParser.checkByXml()...");
		
		//XML文档根元素
		Element mXmlRootEle = pXmlDoc.getRootElement();
		//规则XML根元素
		Element mRuleRootEle = cRuleXml.getConf().getRootElement();
		//通过XML检验
		checkByXml(mXmlRootEle, mRuleRootEle);
		
		cLogger.info("Out RuleParser.checkByXml()!");
	}
	
	/**
	 * 通过XML检验
	 * @param pXmlEle XML元素
	 * @param pRuleEle 规则元素
	 * @throws MidplatException
	 */
	@SuppressWarnings("unchecked")
	private void checkByXml(Element pXmlEle, Element pRuleEle) throws MidplatException {
		//规则元素列表
		List<Element> mRuleEleList= pRuleEle.getChildren();
		//遍历规则元素列表
		for (Element tRuleEle : mRuleEleList) {
			//规则元素标签
			String tEleTag = tRuleEle.getName();
			//元素标签为规则
			if ("rule".equals(tEleTag)) {
				Object tObj = null;;
				try {
					//根据一个xpath语句返回符合条件的第一个节点
					tObj = XPath.selectSingleNode(pXmlEle, ".["+tRuleEle.getChildText(value)+']');
				} catch (Exception ex) {
					cLogger.warn("校验条件有误！"+tRuleEle.getChildText(value), ex);
				}
				//规则元素子元素值的内容非空
				if (null != tObj) {
					//抛出新中间平台异常消息取规则元素子元素信息的内容 
					throw new MidplatException(tRuleEle.getChildText(msg));
				}
			} else {
				//XML元素标签列表
				List<Element> tXmlEleList = pXmlEle.getChildren(tEleTag);
				//遍历XML元素标签列表
				for (Element ttXmlEle : tXmlEleList) {
					//通过XML检验
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
		
		//是否输出配置文件
		if (MidplatConf.newInstance().outConf()) {
			try {
				cLogger.info(
						IOTrans.toString(
								new InputStreamReader(
										new FileInputStream(cXslFile), "GBK")));
			} catch (IOException ex) {
				cLogger.error("输出配置文件异常！", ex);
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
		
		//是否输出配置文件
		if (MidplatConf.newInstance().outConf()) {
			cLogger.info(JdomUtil.toString(cConfDoc));
		}
		
		cLogger.info("Out RuleXml.load()!");
	}
	
	public static RuleXml newInstance() {
		return cThisIns;
	}
}