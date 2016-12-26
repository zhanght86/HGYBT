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
  //�׽�������ʵ����ʵ��
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
	//[1482742347013:2016-12-26 04:52:27,1482752389487:2016-12-26 07:39:49]
    long mStartMillis = System.currentTimeMillis();
    //Into Ybt4Socket.run()...[����Ybt4Socket.run]
    this.cLogger.info("Into Ybt4Socket.run()...");
    //�Ǳ�׼�������[out_Std.xml]
    Document mOutNoStd = null;
    //������ˮ��
    String tranNo=null;
    try {
      //�׽�������ʵ����ʵ������[���зǱ�׼]���ģ����ӱ�׼����ͷ
      Document tInNoStd = this.cPreNet.receive();
      /*��ȡ�Ǳ�׼���뱨�Ľڵ�:Head��TranNo*/
      Element tHeadEle = tInNoStd.getRootElement().getChild("Head");//[TX/Head]
      tranNo=tInNoStd.getRootElement().getChild("Head").getChildText("TranNo");//[TX/Head/TranNo]
      //�Ǳ�׼���뱨���Ƴ�TranNo�ӽڵ�
      tInNoStd.getRootElement().getChild("Head").removeChild("TranNo");
      //����ת������
      String tFormatClassName = "com.sinosoft.midplat.format.XmlSimpFormat";//��ʼֵ
      //���Ĵ�������
      String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";//��ʼֵ
      
      //midplat.xml�ı���ת����
      String tFormatValue = this.cMidplatRoot.getChildText("format");//[midplat/format]
      //midplat.xml�ı��Ĵ�����
      String tServiceValue = this.cMidplatRoot.getChildText("service");//[midplat/service]
      //����ת����ǿա����ַ�
      if ((tFormatValue != null) && (!"".equals(tFormatValue))) {
    	 //Ϊ��Ա����ת��������ֵ
        tFormatClassName = tFormatValue;//Ĭ��:��
      }
      //���Ĵ�����ǿա����ַ�
      if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
    	  //Ϊ��Ա���Ĵ���������ֵ
        tServiceClassName = tServiceValue;//Ĭ��:��
      }
      //newccb.xml�ı���ת����
      tFormatValue = this.cThisConfRoot.getChildText("format");//[newccb/format]
    //newccb.xml�ı��Ĵ�����
      tServiceValue = this.cThisConfRoot.getChildText("service");//[newccb/service]
      //����ת����ǿա����ַ�
      if ((tFormatValue != null) && (!"".equals(tFormatValue))) {
    	  //Ϊ��Ա����ת��������ֵ
        tFormatClassName = tFormatValue;//Ĭ��:��
      }
      //���Ĵ�����ǿա����ַ�
      if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
    	//Ϊ��Ա���Ĵ���������ֵ
        tServiceClassName = tServiceValue;//Ĭ��:��
      }
      
      //business[funcFlag='1080']
      //ȡfuncFlag�ڵ�ֵΪ1080��business�ڵ�·��
      XPath tXPath = XPath.newInstance("business[funcFlag='"+tHeadEle.getChildText("FuncFlag")+ "']");
      //��newccb.xml��ʹ��·�����ʽѡ��funcFlagΪ1080�ĵ�һbusiness�ڵ�
      //·�����ʽѡ��һ�ڵ�
      Element tBusinessEle = (Element)tXPath.selectSingleNode(this.cThisConfRoot);
      //business�ڵ�ǿ�
      if (tBusinessEle != null) {
    	 //��ȡ����ת����
        String ttClass = tBusinessEle.getChildText("format");//ȡֵ
        //����ת����ǿա����ַ�
        if ((ttClass != null) && (!"".equals(ttClass))) {
          //Ϊ��Ա����ת��������ֵ
          tFormatClassName = ttClass;//����
        }
        //��ȡ���Ĵ�����
        ttClass = tBusinessEle.getChildText("service");//ȡֵ
        //���Ĵ�����ǿա����ַ�
        if ((ttClass != null) && (!"".equals(ttClass))) {
          //Ϊ��Ա���Ĵ���������ֵ
          tServiceClassName = ttClass;//����
        }
      }
      //����ת��ģ�飺
      this.cLogger.info("����ת��ģ�飺" + tFormatClassName);//ת����
      //����ת���๹����
      Constructor tFormatConstructor = Class.forName(
        tFormatClassName).getConstructor(new Class[] { Element.class });//������
      //����ת����ʵ��
      Format tFormat = (Format)tFormatConstructor.newInstance(new Object[] { tBusinessEle });//ʵ��
      //�Ǳ�׼���뱨��ת��Ϊ��׼���뱨��
      Document InStd=tFormat.noStd2Std(tInNoStd);//ת��
      //ҵ����ģ�飺
      this.cLogger.info("ҵ����ģ�飺" + tServiceClassName);//������
      //���Ĵ����๹����
      Constructor tServiceConstructor = Class.forName(
        tServiceClassName).getConstructor(new Class[] { Element.class });//������
      //���Ĵ�����ʵ��
      Service tService = (Service)tServiceConstructor.newInstance(new Object[] { tBusinessEle });//ʵ��
      //ҵ�����ཫ��׼���뱨�Ĵ���ɱ�׼�������
      Document tOutStd = tService.service(InStd);//����
      //��׼�������ת��Ϊ�Ǳ�׼�������
      mOutNoStd = tFormat.std2NoStd(tOutStd);//ת��
    } catch (NetException ex) {
     /*����[ͨѶ]�쳣*/
      this.cLogger.error("ͨѶ�ӿ��쳣!", ex);
      mOutNoStd = MidplatUtil.getSimpOutXml(1, ex.getMessage());
    } catch (MidplatException ex) {
      /*midplat.xml[����]�쳣*/
      this.cLogger.error("Ԥ��У��!", ex);
      mOutNoStd = MidplatUtil.getSimpOutXml(1, ex.getMessage());
    } catch (Throwable ex) {
      /*�쳣�������[ϵͳ]�쳣*/
      this.cLogger.error("ϵͳ���ַ�Ԥ���쳣��", ex);
      mOutNoStd = MidplatUtil.getSimpOutXml(1, "ϵͳ�쳣������ϵ���չ�˾��");
    } finally {
      /*���:����[�Ǳ�׼�������]����ѯTranLog��¼���ر�SocketNetImpl */
      //�Ǳ�׼������ķǿ�
      if (mOutNoStd != null) {
        try {
          //�׽�������ʵ����ʵ��:����[�Ǳ�׼�������]
          this.cPreNet.send(mOutNoStd);
        } catch (Exception ex) {
          this.cLogger.error("���ͷ��ر����쳣!", ex);
        }
      }
      //������־��¼
      TranLogDB tranLogDB = new TranLogDB();
      //���ý�����ˮ��
	  tranLogDB.setTranNo(tranNo);
	  //������ˮ�Ż�ȡ��Ϣʧ��[!false=true]
	  if(!tranLogDB.getInfo()){
		  cLogger.error("��ѯTranlog���쳣!");
	  } else {
		  /*����TranLog��2���ֶ�:Bak3��OutNoDoc*/
		  tranLogDB.setBak3((System.currentTimeMillis()-mStartMillis)/1000.0+"");
		  tranLogDB.setOutNoDoc(cPreNet.cOutNoStdDoc);
		  //����TranLog���¼ʧ��[!false=true]
		  if(!tranLogDB.update()){
			  cLogger.error("����Tranlog���쳣!");
		  }
	  }
	 //�ر��׽�������ʵ����ʵ��
      this.cPreNet.close();
    }
    //�����ܺ�ʱ��[��ǰʱ��-��ʼʱ��]
    this.cLogger.info("�����ܺ�ʱ��" + (System.currentTimeMillis() - mStartMillis) / 1000.0D + "s");
    //Out Ybt4Socket.run()![��Ybt4Socket.run����]
    this.cLogger.info("Out Ybt4Socket.run()!");
  }
}