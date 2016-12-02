package com.sinosoft.midplat.bat;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.common.cache.FileCacheManage;
import java.io.File;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

public class BatConf extends XmlConf
  implements XmlTag
{
  private static final BatConf cThisIns = new BatConf();
  private static final String cPath = "conf/bat.xml";
  private ServletContextListener cBatListener = null;

  private BatConf() {
    load();
    FileCacheManage.newInstance().register("conf/bat.xml", this);
  }

  public void load() {
    this.cLogger.info("Into BatConf.load()...");

    String mFilePath = SysInfo.cHome + "conf/bat.xml";
    this.cLogger.info("Start load " + mFilePath + "...");

    this.cConfFile = new File(mFilePath);

    recordStatus();

    this.cConfDoc = loadXml(this.cConfFile);
    this.cLogger.info("End load " + mFilePath + "!");

    if (MidplatConf.newInstance().outConf()) {
      this.cLogger.info(JdomUtil.toString(this.cConfDoc));
    }

    if ((this.cBatListener != null) && 
      ("true".equals(this.cConfDoc.getRootElement().getAttributeValue("autoRestart")))) {
      this.cBatListener.contextDestroyed(null);
      this.cBatListener.contextInitialized(null);
    }

    this.cLogger.info("Out BatConf.load()!");
  }

  public void setListener(ServletContextListener pListener) {
    this.cBatListener = pListener;
  }

  public static BatConf newInstance() {
    return cThisIns;
  }
}