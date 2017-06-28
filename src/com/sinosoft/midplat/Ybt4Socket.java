package com.sinosoft.midplat;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.exception.NetException;
import com.sinosoft.midplat.format.Format;
import com.sinosoft.midplat.net.SocketNetImpl;
import com.sinosoft.midplat.service.Service;
import java.lang.reflect.Constructor;
import java.net.Socket;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

public abstract class Ybt4Socket extends Thread
  implements XmlTag
{
  protected final Logger cLogger = Logger.getLogger(getClass());
  private final SocketNetImpl cPreNet;
  protected final Element cMidplatRoot;
  protected final Element cThisConfRoot;

  public Ybt4Socket(Socket pSocket, XmlConf pThisConf)
    throws Exception
  {
    setName(String.valueOf(NoFactory.nextTranLogNo()));
    cLogger.info("TranLogNo:"+getName());
    this.cThisConfRoot = pThisConf.getConf().getRootElement();
    this.cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();

    String mPreNetClass = "com.sinosoft.midplat.net.SocketNetImpl";
    String mClassName = this.cMidplatRoot.getChildText("preNet");
    if ((mClassName != null) && (!"".equals(mClassName))) {
      mPreNetClass = mClassName;
    }
    mClassName = this.cThisConfRoot.getChildText("preNet");
    if ((mClassName != null) && (!"".equals(mClassName))) {
      mPreNetClass = mClassName;
    }
    this.cLogger.info("ǰ��ͨѶģ�飺" + mPreNetClass);
    Constructor tPreNetConstructor = Class.forName(
      mPreNetClass).getConstructor(new Class[] { Socket.class, Element.class });
    this.cPreNet = ((SocketNetImpl)tPreNetConstructor.newInstance(new Object[] { pSocket, this.cThisConfRoot }));
  }

  public final void run()
  {
    long mStartMillis = System.currentTimeMillis();
    this.cLogger.info("Into Ybt4Socket.run()...");

    Document mOutNoStd = null;
    String tranNo=null;
    try {
      Document tInNoStd = this.cPreNet.receive();
      Element tHeadEle = tInNoStd.getRootElement().getChild("Head");
      tranNo=tInNoStd.getRootElement().getChild("Head").getChildText("TranNo");
      tInNoStd.getRootElement().getChild("Head").removeChild("TranNo");
      String tFormatClassName = "com.sinosoft.midplat.format.XmlSimpFormat";
      String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";

      String tFormatValue = this.cMidplatRoot.getChildText("format");
      String tServiceValue = this.cMidplatRoot.getChildText("service");
      if ((tFormatValue != null) && (!"".equals(tFormatValue))) {
        tFormatClassName = tFormatValue;
      }
      if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
        tServiceClassName = tServiceValue;
      }

      tFormatValue = this.cThisConfRoot.getChildText("format");
      tServiceValue = this.cThisConfRoot.getChildText("service");
      if ((tFormatValue != null) && (!"".equals(tFormatValue))) {
        tFormatClassName = tFormatValue;
      }
      if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
        tServiceClassName = tServiceValue;
      }
      
      XPath tXPath = XPath.newInstance("business[funcFlag='"+tHeadEle.getChildText("FuncFlag")+ "']");
      Element tBusinessEle = (Element)tXPath.selectSingleNode(this.cThisConfRoot);
      if (tBusinessEle != null) {
        String ttClass = tBusinessEle.getChildText("format");
        if ((ttClass != null) && (!"".equals(ttClass))) {
          tFormatClassName = ttClass;
        }

        ttClass = tBusinessEle.getChildText("service");
        if ((ttClass != null) && (!"".equals(ttClass))) {
          tServiceClassName = ttClass;
        }
      }

      this.cLogger.info("����ת��ģ�飺" + tFormatClassName);
      Constructor tFormatConstructor = Class.forName(
        tFormatClassName).getConstructor(new Class[] { Element.class });
      Format tFormat = (Format)tFormatConstructor.newInstance(new Object[] { tBusinessEle });
      Document InStd=tFormat.noStd2Std(tInNoStd);
      
      this.cLogger.info("ҵ����ģ�飺" + tServiceClassName);
      Constructor tServiceConstructor = Class.forName(
        tServiceClassName).getConstructor(new Class[] { Element.class });
      Service tService = (Service)tServiceConstructor.newInstance(new Object[] { tBusinessEle });
      Document tOutStd = tService.service(InStd);
      mOutNoStd = tFormat.std2NoStd(tOutStd);
    } catch (NetException ex) {
      this.cLogger.error("ͨѶ�ӿ��쳣!", ex);
      mOutNoStd = MidplatUtil.getSimpOutXml(1, ex.getMessage());
    } catch (MidplatException ex) {
      this.cLogger.error("Ԥ��У��!", ex);
      mOutNoStd = MidplatUtil.getSimpOutXml(1, ex.getMessage());
    } catch (Throwable ex) {
      this.cLogger.error("ϵͳ���ַ�Ԥ���쳣��", ex);
      mOutNoStd = MidplatUtil.getSimpOutXml(1, "ϵͳ�쳣������ϵ���չ�˾��");
    } finally {
      if (mOutNoStd != null) {
        try {
          this.cPreNet.send(mOutNoStd);
        } catch (Exception ex) {
          this.cLogger.error("���ͷ��ر����쳣!", ex);
        }
      }
//      TranLogDB tranLogDB = new TranLogDB();
//	  tranLogDB.setTranNo(tranNo);
//	  if(!tranLogDB.getInfo()){
//		  cLogger.error("��ѯTranlog���쳣!");
//	  } else {
//		  tranLogDB.setBak3((System.currentTimeMillis()-mStartMillis)/1000.0+"");
//		  tranLogDB.setOutNoDoc(cPreNet.cOutNoStdDoc);
//		  if(!tranLogDB.update()){
//			  cLogger.error("����Tranlog���쳣!");
//		  }
//	  }
	 
      this.cPreNet.close();
    }

    this.cLogger.info("�����ܺ�ʱ��" + (System.currentTimeMillis() - mStartMillis) / 1000.0D + "s");
    this.cLogger.info("Out Ybt4Socket.run()!");
  }
}