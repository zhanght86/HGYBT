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
    //TranLogNo:6863
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
    //ǰ��ͨѶģ�飺com.sinosoft.midplat.newccb.newNet.CcbNetImpl
    this.cLogger.info("ǰ��ͨѶģ�飺" + mPreNetClass);
    Constructor tPreNetConstructor = Class.forName(
      mPreNetClass).getConstructor(new Class[] { Socket.class, Element.class });
    this.cPreNet = ((SocketNetImpl)tPreNetConstructor.newInstance(new Object[] { pSocket, this.cThisConfRoot }));
  }

  public final void run()
  {
	 //��ʼ������:1481593022084[2016-12-13 09:37:02]
    long mStartMillis = System.currentTimeMillis();
    //Into Ybt4Socket.run()...
    this.cLogger.info("Into Ybt4Socket.run()...");
   //��ʼ������Ǳ�׼���� 
    Document mOutNoStd = null;
    try {
      //��������Ǳ�׼����
      Document tInNoStd = this.cPreNet.receive();
      Element tHeadEle = tInNoStd.getRootElement().getChild("Head");
      String tFormatClassName = "com.sinosoft.midplat.format.XmlSimpFormat";//com.sinosoft.midplat.format.XmlSimpFormat
      String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";//com.sinosoft.midplat.service.ServiceImpl
      
      //null
      String tFormatValue = this.cMidplatRoot.getChildText("format");
      //com.sinosoft.midplat.service.SimpService
      String tServiceValue = this.cMidplatRoot.getChildText("service");
      //false[null != null]
      if ((tFormatValue != null) && (!"".equals(tFormatValue))) {
        tFormatClassName = tFormatValue;
      }
      //true[com.sinosoft.midplat.service.SimpService != null]
      if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
    	//com.sinosoft.midplat.service.ServiceImpl=com.sinosoft.midplat.service.SimpService
        tServiceClassName = tServiceValue;
      }
      //null[<newccb/business/format>]
      tFormatValue = this.cThisConfRoot.getChildText("format");
      //null[<newccb/business/service>]
      tServiceValue = this.cThisConfRoot.getChildText("service");
      //null != null
      if ((tFormatValue != null) && (!"".equals(tFormatValue))) {
        tFormatClassName = tFormatValue;
      }
      //null != null
      if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
        tServiceClassName = tServiceValue;
      }
      
      //business[funcFlag='������']
      //child::business[(child::funcFlag = "1012")]
      XPath tXPath = XPath.newInstance("business[funcFlag='"+tHeadEle.getChildText("FuncFlag")+ "']");
      //[Element: <business/>]
      Element tBusinessEle = (Element)tXPath.selectSingleNode(this.cThisConfRoot);
      //<business/> != null
      if (tBusinessEle != null) {
    	 //com.sinosoft.midplat.newccb.format.NewCont
        String ttClass = tBusinessEle.getChildText("format");
        //com.sinosoft.midplat.newccb.format.NewCont != null
        if ((ttClass != null) && (!"".equals(ttClass))) {
        	//com.sinosoft.midplat.format.XmlSimpFormat=com.sinosoft.midplat.newccb.format.NewCont
          tFormatClassName = ttClass;
        }
        //[Element: <business/service>]
        //com.sinosoft.midplat.newccb.service.NewContInput
        ttClass = tBusinessEle.getChildText("service");
        //com.sinosoft.midplat.newccb.service.NewContInput != null
        if ((ttClass != null) && (!"".equals(ttClass))) {
          //com.sinosoft.midplat.service.SimpService=com.sinosoft.midplat.newccb.service.NewContInput
          tServiceClassName = ttClass;
        }
      }
      //����ת��ģ�飺com.sinosoft.midplat.newccb.format.NewCont
      this.cLogger.info("����ת��ģ�飺" + tFormatClassName);
      //public com.sinosoft.midplat.newccb.format.NewCont(org.jdom.Element)
      Constructor tFormatConstructor = Class.forName(
        tFormatClassName).getConstructor(new Class[] { Element.class });
      //��ʽ������
      Format tFormat = (Format)tFormatConstructor.newInstance(new Object[] { tBusinessEle });
      //��ʽ���������׼����Ϊ�����׼����
      Document InStd=tFormat.noStd2Std(tInNoStd);
      //ҵ����ģ�飺com.sinosoft.midplat.newccb.service.NewContInput
      this.cLogger.info("ҵ����ģ�飺" + tServiceClassName);
      //public com.sinosoft.midplat.newccb.service.NewContInput(org.jdom.Element)
      Constructor tServiceConstructor = Class.forName(
        tServiceClassName).getConstructor(new Class[] { Element.class });
      //com.sinosoft.midplat.newccb.service.NewContInput
      Service tService = (Service)tServiceConstructor.newInstance(new Object[] { tBusinessEle });
      //
      Document tOutStd = tService.service(InStd);
      //����Ǳ�׼����
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
          //���ͷ��ر����쳣!
          this.cLogger.error("���ͷ��ر����쳣!", ex);
        }
      }
      //��ʼ�����ݿ������ʵ��
      TranLogDB tranLogDB = new TranLogDB();
      //������־��[5357]
	  tranLogDB.setLogNo(Thread.currentThread().getName());
	  //!false=true
	  if(!tranLogDB.getInfo()){
		  //��ѯTranlog���쳣!
		  cLogger.error("��ѯTranlog���쳣!");
	  } else {
		  tranLogDB.setBak3((System.currentTimeMillis()-mStartMillis)/1000.0+"");
		  tranLogDB.setOutNoDoc(cPreNet.cOutNoStdDoc);
		  if(!tranLogDB.update()){
			  cLogger.error("����Tranlog���쳣!");
		  }
	  }
	 
      this.cPreNet.close();
    }
    //�����ܺ�ʱ��529.874s
    //�����ܺ�ʱ��19629.266s
    this.cLogger.info("�����ܺ�ʱ��" + (System.currentTimeMillis() - mStartMillis) / 1000.0D + "s");
    //Out Ybt4Socket.run()!
    this.cLogger.info("Out Ybt4Socket.run()!");
  }
}