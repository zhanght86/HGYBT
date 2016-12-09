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

  /**
   * ����
   */
  public final void run()
  {
	//����һ����ǰ�ĺ���
    long mStartMillis = System.currentTimeMillis();
    this.cLogger.info("Into Ybt4Socket.run()...");
    //����Ǳ�׼����
    Document mOutNoStd = null;
    try {
      //����Ǳ�׼����
      Document tInNoStd = this.cPreNet.receive();
      //����Ǳ�׼������ͷ
      Element tHeadEle = tInNoStd.getRootElement().getChild("Head");
      JdomUtil.print(tHeadEle);//��Element��ӡ������̨�� GBK���룬����3�ո�
      //��ʽ��������
      String tFormatClassName = "com.sinosoft.midplat.format.XmlSimpFormat";
      //����������
      String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";
      //ȡ�м�ƽ̨���ڵ���Ԫ�ظ�ʽ�������� 
      String tFormatValue = this.cMidplatRoot.getChildText("format");
      //ȡ�м�ƽ̨���ڵ���Ԫ�ط��������
      String tServiceValue = this.cMidplatRoot.getChildText("service");
      //��ʽ�������ݷǿ��ҷǿ��ַ�
      if ((tFormatValue != null) && (!"".equals(tFormatValue))) {
        tFormatClassName = tFormatValue;//��ʽ��ʽ�������ݵ�ֵ������ʽ��ʽ��������
      }
      //��������ݷǿ��ҷǿ��ַ�
      if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
        tServiceClassName = tServiceValue;//��ʽ��������ݵ�ֵ������ʽ����������
      }
      //ȡ��ǰ�����ļ����ڵ���Ԫ�ظ�ʽ��������
      tFormatValue = this.cThisConfRoot.getChildText("format");
      //ȡ��ǰ�����ļ����ڵ���Ԫ�ط��������
      tServiceValue = this.cThisConfRoot.getChildText("service");
      //��ʽ�������ݷǿ��ҷǿ��ַ�
      if ((tFormatValue != null) && (!"".equals(tFormatValue))) {
        tFormatClassName = tFormatValue;//��ʽ��ʽ�������ݵ�ֵ������ʽ��ʽ��������
      }
      //��������ݷǿշǿ��ַ�
      if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
        tServiceClassName = tServiceValue;//��ʽ��������ݵ�ֵ������ʽ����������
      }
      //�ҵ��������Ͷ�Ӧ��ҵ��
      XPath tXPath = XPath.newInstance(
        "business[funcFlag='" + tHeadEle.getChildText("FuncFlag") + "']");
      //����һ��xpath��䷵�ط��������ĵ�һ��ҵ��Ԫ��
      Element tBusinessEle = (Element)tXPath.selectSingleNode(this.cThisConfRoot);
      //ҵ��Ԫ�طǿ�
      if (tBusinessEle != null) {
    	 //ȡҵ��Ԫ����Ԫ�ظ�ʽ�������� 
        String ttClass = tBusinessEle.getChildText("format");
        //��ʽ�������ݷǿ��ҷǿ��ַ�
        if ((ttClass != null) && (!"".equals(ttClass))) {
          tFormatClassName = ttClass;//��ʽ��ʽ�������ݵ�ֵ������ʽ��ʽ��������
        }
        //ȡҵ��Ԫ����Ԫ�ط�������� 
        ttClass = tBusinessEle.getChildText("service");
        //��������ݷǿ��ҷǿ��ַ�
        if ((ttClass != null) && (!"".equals(ttClass))) {
          tServiceClassName = ttClass;//��ʽ��������ݵ�ֵ������ʽ����������
        }
      }
      //����ת��ģ�飺com.sinosoft.midplat.newccb.format.CardControl
      this.cLogger.info("����ת��ģ�飺" + tFormatClassName);
      //��ʽ��������
      Constructor tFormatConstructor = Class.forName(
        tFormatClassName).getConstructor(new Class[] { Element.class });
      //ͨ����ʽ������������ʵ������ʼ������
      Format tFormat = (Format)tFormatConstructor.newInstance(new Object[] { tBusinessEle });
      Document tInStd = tFormat.noStd2Std(tInNoStd);//ҵ��Ԫ�ظ�ʽ������Ǳ�׼����ת��׼����
      //ҵ����ģ�飺com.sinosoft.midplat.newccb.service.ContCancelCardControlCcb
      this.cLogger.info("ҵ����ģ�飺" + tServiceClassName);
      //��������
      Constructor tServiceConstructor = Class.forName(
        tServiceClassName).getConstructor(new Class[] { Element.class });
     //ͨ��������������ʵ������ʼ������
      Service tService = (Service)tServiceConstructor.newInstance(new Object[] { tBusinessEle });
      Document tOutStd = tService.service(tInStd);//ҵ��Ԫ�ط���������ȡ�����׼����

      mOutNoStd = tFormat.std2NoStd(tOutStd);//ҵ��Ԫ�ظ�ʽ�������׼����ת�Ǳ�׼����
    } catch (NetException ex) {
      this.cLogger.error("ͨѶ�ӿ��쳣!", ex);
    } catch (MidplatException ex) {
      this.cLogger.error("Ԥ��У��!", ex);
      mOutNoStd = MidplatUtil.getSimpOutXml(1, ex.getMessage());
    } catch (Throwable ex) {
      this.cLogger.error("ϵͳ���ַ�Ԥ���쳣��", ex);
      mOutNoStd = MidplatUtil.getSimpOutXml(1, "ϵͳ�쳣������ϵ���չ�˾��");
    } finally {
      //����Ǳ�׼���ķǿ�
      if (mOutNoStd != null) {
	        try {
	          this.cPreNet.send(mOutNoStd);//����[Element: <TX/>]
	        } catch (Exception ex) {
	          this.cLogger.error("���ͷ��ر����쳣!", ex);
	        }
      }
      		//������־���ݿ����
      		TranLogDB tranLogDB = new TranLogDB();//��ʼ���ֶ�
      		//������־��
			tranLogDB.setLogNo(Thread.currentThread().getName());//1406
			//��ȡ��Ϣʧ��
			if(!tranLogDB.getInfo()){
				cLogger.error("��ѯTranlog���쳣!");
			}else{
				//���ñ���3[0.078]
				tranLogDB.setBak3((System.currentTimeMillis()-mStartMillis)/1000.0+"");
				tranLogDB.setOutNoDoc(cPreNet.cOutNoStdDoc);//���÷��ر��� 
				//����ʧ��
				if(!tranLogDB.update()){
					cLogger.error("����Tranlog���쳣!");
				}
			}
      this.cPreNet.close();//�ر�
    }
    //�����ܺ�ʱ��3850.487s
    this.cLogger.info("�����ܺ�ʱ��" + (System.currentTimeMillis() - mStartMillis) / 1000.0D + "s");
    //Out Ybt4Socket.run()!
    this.cLogger.info("Out Ybt4Socket.run()!");
  }
}