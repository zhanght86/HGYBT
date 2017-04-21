package com.sinosoft.midplat.net;

import java.io.IOException;

import java.net.Socket;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.exception.NetException;

/**
 * �׽�������ͨѶʵ����
 * @author yuantongxin
 */
public class SocketNetImpl implements XmlTag {
	//����һ���������־��¼��
	protected final Logger cLogger = Logger.getLogger(getClass());
	
	//�׽���
	protected final Socket cSocket;
	//[�ͻ���/���ж�]IP
	protected final String cClientIp;
	
	//��ǰ�����ļ����ڵ�
	protected final Element cThisConfRoot;
	
	//���׻�������ڵ�
	protected final Element cTranComEle;
	//��������
	protected String cFuncFlag;
	//�Ǳ�׼�������
	public  String cOutNoStdDoc;
	
	/**
	 * <p>Title: SocketNetImpl</p>
	 * <p>Description: �׽�������ͨѶʵ���๹����</p>
	 * @param pSocket �׽���(���)
	 * @param pThisConfRoot ��ǰ�����ļ����ڵ�(���)
	 * @throws MidplatException
	 */
	public SocketNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException {
		//Ϊ��Ա�׽��ָ�ֵ
		cSocket = pSocket;
		//��ȡ�׽�������IP��ַ
		cClientIp = cSocket.getInetAddress().getHostAddress();
		//�ͻ���IP��127.0.0.1������˿ڣ�39871
		cLogger.info("�ͻ���IP��" + cClientIp + "������˿ڣ�" + cSocket.getLocalPort());
		//Ϊ��Ա��ǰ�����ļ����ڵ㸳ֵ
		cThisConfRoot = pThisConfRoot;
		//Ϊ��Ա���׻�������ڵ㸳ֵ[��¡��ǰ�����ļ����ڵ���TranCom�ӽڵ�]
		cTranComEle = (Element) cThisConfRoot.getChild(TranCom).clone();
		
		//��ȡ��ǰ�����ļ����ڵ�ip�ӽڵ��ı�����
		String mOkIp = cThisConfRoot.getChildText(ip);
		//ȷ��IP�ǿ��Ҳ������ͻ���IP
		if (null!=mOkIp && !mOkIp.contains(cClientIp)) {
			//�׳������쳣[�Ƿ�ip���ͻ���IP]
			throw new NetException("�Ƿ�ip��"+cClientIp);
		}
	}
	
	/**
	 * ����[���зǱ�׼����]���ģ����ӱ�׼����ͷ
	 * @return
	 * @throws Exception
	 */
	public Document receive() throws Exception {
		//Into SocketNetImp.receive()...
		cLogger.info("Into SocketNetImp.receive()...");
		//�������ת�����յ�������Ϊ�ֽ����鲻�ر���
		byte[] mBodyBytes = 
			IOTrans.toBytes5Close(cSocket.getInputStream());
		//�ر�������
		cSocket.shutdownInput();
		//����GBK���뽫���������ֽ����鹹���ɷǱ�׼���뱨�Ķ��󣬺��Ա�ǩ֮��Ŀ��ַ�(�ո񡢻��С��Ʊ����)
		Document mXmlDoc = JdomUtil.build(mBodyBytes);
		
		//����
		JdomUtil.print(mXmlDoc);
		//��ȡ[�Ǳ�׼���뱨��]ͷ�ڵ�
		Element mHeadEle = mXmlDoc.getRootElement().getChild(Head);
		//��ȡ[�Ǳ�׼���뱨��]�������ͽڵ�
		Element mFuncFlagEle = mHeadEle.getChild(FuncFlag);
		//�½�XML·�����ʽ(���׽ڵ�/���������ӽڵ�[�ⲿ����Ϊ�������ͽڵ��ı�����])
		XPath mXPath = XPath.newInstance(
				"business/funcFlag[@outcode='" + mFuncFlagEle.getText() + "']");
		//������ʽ��ֵ����һ����������
		cFuncFlag = mXPath.valueOf(cThisConfRoot);
		//��������Ϊ��
		if (null == cFuncFlag) {	//δ�����ڲ������룬ֱ��ȡ������������
			//�������͸�ֵΪ[�Ǳ�׼���뱨��]�������ͽڵ��ı�����
			cFuncFlag = mFuncFlagEle.getText();
		}
		
		//�����ļ���[�߳���_��һ��˳���_������_in.xml]
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(cFuncFlag)
			.append("_in.xml");
		//����{[�Ǳ�׼]+[����]}���ģ���[���׻�������]Ŀ¼�£��߳���_��һ��˳���_[������]_{[in]}.xml�ļ���
		SaveMessage.save(mXmlDoc, cTranComEle.getText(), mSaveName.toString());
		//���汨����ϣ������ļ���
		cLogger.info("���汨����ϣ�"+mSaveName);
		
		//���Head
		mFuncFlagEle.setText(cFuncFlag);
		//�½����ж�IP�ڵ�
		Element mClientIpEle = new Element(ClientIp);
		//�����ı�����Ϊ���ж�IP
		mClientIpEle.setText(cClientIp);
		//[�Ǳ�׼���뱨��]ͷ�ڵ�������ж�IP�ڵ�
		mHeadEle.addContent(mClientIpEle);
		//[�Ǳ�׼���뱨��]ͷ�ڵ���뽻�׻�������ڵ�
		mHeadEle.addContent(cTranComEle);
		
		cLogger.info("Out SocketNetImp.receive()!");
		//���طǱ�׼���뱨��
		return mXmlDoc;
	}
	
	/**
	 * ����[���зǱ�׼���]���ĵ����ж�
	 * @param pOutNoStd ����Ǳ�׼����
	 * @throws Exception 
	 */
	public void send(Document pOutNoStd) throws Exception {
		//Into SocketNetImp.send()...
		cLogger.info("Into SocketNetImp.send()...");
		//����[�Ǳ�׼�������]�ļ���[�߳���_��һ˳���_������_out.xml]
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(cFuncFlag)
			.append("_out.xml");
		//����{[�Ǳ�׼]+[���]}���ģ���[���׻�������]Ŀ¼�£��߳���_��һ��˳���_[������]_{[out]}.xml�ļ���
		SaveMessage.save(pOutNoStd, cTranComEle.getText(), mSaveName.toString());
		//���汨����ϣ�����[�Ǳ�׼�������]�ļ���
		cLogger.info("���汨����ϣ�"+mSaveName);
		//�׽��������д��Ǳ�׼�������GBK����������
		cSocket.getOutputStream().write(JdomUtil.toBytes(pOutNoStd));
		//�����׽��������
		cSocket.shutdownOutput();
		//Out SocketNetImp.send()![ ��SocketNetImp.send��������]
		cLogger.info("Out SocketNetImp.send()!");
	}
	
	/**
	 * �ر��׽���
	 */
	public final void close() {
		try {
			//�ر��׽���
			cSocket.close();
		} catch (IOException ex) {
			//Socket�����ѹرգ�
			cLogger.debug("Socket�����ѹرգ�", ex);
		}
	}
}

