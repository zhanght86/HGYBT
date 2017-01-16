package com.sinosoft.midplat.newccb.net;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.transform.XSLTransformException;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.SocketNetImpl;
import com.sinosoft.midplat.newccb.format.ErrorOutXsl;
import com.sinosoft.midplat.newccb.security.SecurityMessageHeader;
import com.sinosoft.midplat.newccb.security.SecurityMessageHeaderUtils;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;

public class NewCcbNetImpl extends SocketNetImpl
{

	//13���س�����
	private byte crlf13 = (byte) 13;
	//10���س�����
	private byte crlf10 = (byte) 10;
	//�����ַ�������
	private StringBuffer request = new StringBuffer();
	private String mSYS_TX_CODE = "";
	private SecurityMessageHeader header;
	private String localSecNodeId;
	private String remoteSecNodeId;
	
	/**�������ʱ��*/
	public String mSYS_RECV_TIME = null;
	
	/**������Ӧʱ��*/
	public String mSYS_RESP_TIME = null;
	
	/**���յ���������*/
	public Document mInDoc = null;

	// private String mIns_Co_ID = "";

	public NewCcbNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException
	{
		super(pSocket, pThisConfRoot);
	}

	/**
	 * ����[���ܱ���]
	 */
	public Document receive() throws Exception
	{
		// �������ʱ��[�����ڸ�ʽ����ǰ����]
		mSYS_RECV_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		//Into NewCcbNetImpl.receive()...
		cLogger.info("Into NewCcbNetImpl.receive()...");
		// ���������
		//��ȡ�׽����ֽ�������
		InputStream mIs = cSocket.getInputStream();
		// ��ȡHTTP����ͷ
		//�����ַ���
		String mDataStr = "";
		//������һ������Ϊ1���ֽ�����
		byte[] crlf = new byte[1];
		//�س�������
		int crlfNum = 0; // �Ѿ����ӵĻس������� crlfNum=4Ϊͷ������
		//���������ж�ȡһ���ֽڲ�����洢�ڻ��������� crlf ��
		while (mIs.read(crlf) != -1) // ��ȡͷ��
		{
			//�׸��ֽ�Ϊ13���س����С�10���س�����
			if (crlf[0] == crlf13 || crlf[0] == crlf10)
			{
				//�س���������һ
				crlfNum++;
			}
			else//����
			{
				//�س���������Ϊ0
				crlfNum = 0;
			}
			//�����ַ�������׷��ʹ��UTF-8�ַ���������ֽ������ַ���
			request = request.append(new String(crlf, 0, 1, "UTF-8")); // byte�������
			//�س�������Ϊ4[ͷ������]
			if (crlfNum == 4)
				//�˳�ѭ��
				break;
		}
		// ��ð�ȫ����ͷ�ͱ��ĵĳ���
		//�����ַ�����������
		String mData = request.toString();
		/*
		 * POST / HTTP/1.1<!--HTTP Э�鱨��ͷ-->
			Host: 128.32.96.74:39871
			Server: BIP 1.0
			Date: Sun Jan 15 20:02:00 2017 GMT
			Content-Type: application/octet-stream; charset=UTF-8
			Content-Length: 2660
			Connection: keep-alive
		  */
		//����ͷ��Ϣ�������ַ�����������
		cLogger.info("����ͷ��Ϣ��" + request.toString());
		//==================20140901==================
		cLogger.info("==================20140901==================");
		//�����ַ��������������ݳ���
		//138+15=153
		int mContent_Length = mData.indexOf("Content-Length:") + 15;
		//��ȡ����:����β����֮����ַ�������Ϊ����
		mContent_Length = Integer.parseInt(mData.substring(mContent_Length, mData.indexOf("\r\n", mContent_Length)).trim());
		//�����峤�ȣ����ݳ���
		cLogger.info("�����峤�ȣ�" + mContent_Length);
		// ��ȡ��ȫ����ͷ�ͼ��ܵı���
		//���ֽ���������ȡ���ݳ��ȸ�����
		readLenData(mContent_Length, mIs);
		//�����ַ�����ֵΪ�����ַ�������
		mDataStr = request.toString();
		/*
		 * POST / HTTP/1.1<!--HTTP Э�鱨��ͷ-->
			Host: 128.32.96.74:39871
			Server: BIP 1.0
			Date: Sun Jan 15 20:02:00 2017 GMT
			Content-Type: application/octet-stream; charset=UTF-8
			Content-Length: 2660
			Connection: keep-alive
		 */
		//�յ�������Դ�������ַ���
		cLogger.debug("�յ�������Դ��" + mDataStr);
		//
		int index_Connection = mDataStr.indexOf("SEC_ERROR_CODE:");
		mDataStr = mDataStr.substring(index_Connection, mDataStr.length()).trim();
		/*
		 *	SEC_ERROR_CODE:000000000000<!--��ȫЭ�鱨��ͷ-->
			SEC_IS_MAC:1
			SEC_IS_CONTEXT:0
			SEC_IS_ENC:1
			SEC_MAC:DQOUVXrIYtfuviLMJDpuyAiPIGJ3ng==
			SEC_ID1:105005
			SEC_ID2:510096
			SEC_TRACE_ID:108011rv11484481690013205
			SEC_TX_CODE:P5381B123
			SEC_TX_TYPE:00
			SEC_LEN:2416 
			<!--���ܱ�����-->
			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
		 */
		cLogger.debug("��ȫ����ͷ + �����壺" + mDataStr);
		// ������ȫ����ͷ��������
		//���ܰ�ȫ����ͷ+������
		Object[] recv = SecurityMessageHeaderUtils.ummarshal(mDataStr.getBytes(), true, new SecurityMessageHeader());
		//��Ӧ�����ֽ����鸳ֵΪ�ڶ���Ԫ��[������]
		byte[] responseData = (byte[]) recv[1];
		//������ı����壺ʹ��UTF-8�ַ������뱨�����ֽ�����Ϊ�ַ���
		cLogger.debug("������ı����壺" + new String(responseData, "UTF-8"));
		//��ȫ����ͷ��ֵΪ�׸�Ԫ��[��ȫ����ͷ]
		header = (SecurityMessageHeader) recv[0];
		// ���ͷ���ȫ�ڵ���
		localSecNodeId = header.getRmtSecNodeId();
		// Ŀ�갲ȫ�ڵ��
		remoteSecNodeId = header.getSecNodeId();

		// ��ñ���������
		Document mInNoStd = JdomUtil.build(responseData, "UTF-8");
		
		//���ƽ��յ��ı��Ĵ����������
		mInDoc = (Document) mInNoStd.clone();
		
		mSYS_TX_CODE = mInNoStd.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_TX_CODE");
		Element mCOM_ENTITY = mInNoStd.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY");

		mInNoStd.getRootElement().getChild("TX_HEADER").addContent(new Element("LocalID").setText(remoteSecNodeId));
		mInNoStd.getRootElement().getChild("TX_HEADER").addContent(new Element("remoteID").setText(localSecNodeId));

		// ȡ��������
		Element mRootEle = mInNoStd.getRootElement();
		Element cHeader = mRootEle.getChild("TX_HEADER");
		mSYS_TX_CODE = cHeader.getChildText("SYS_TX_CODE");
		cLogger.info("�½��н�����Ϊ==" + mSYS_TX_CODE);
		// JdomUtil.print(cThisConfRoot);
		String mTranCom = cThisConfRoot.getChildText("TranCom");
		cLogger.info("mTranCom==============" + mTranCom);

		XPath mXPath2 = XPath.newInstance("business/funcFlag[@outcode='" + mSYS_TX_CODE + "']");
		cFuncFlag = mXPath2.valueOf(cThisConfRoot);

		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName()).append('_').append(NoFactory.nextAppNo()).append('_').append(cFuncFlag).append("_in.xml");
		SaveMessage.save(mInNoStd, cTranComEle.getText(), mSaveName.toString());
		cLogger.info("���汨����ϣ�" + mSaveName);

		// ���ɱ�׼����ͷ
		Element mTranComEle = new Element(TranCom);
		mTranComEle.setText(mTranCom);
		Element mInNoDoc = new Element("InNoDoc");
		mInNoDoc.setText(mSaveName.toString());
		Element mClientIpEle = new Element(ClientIp);
		mClientIpEle.setText(cClientIp);
		Element mFuncFlagEle = new Element(FuncFlag);
		mFuncFlagEle.setText(cFuncFlag);
		Element mAgentCom = new Element(AgentCom);
		Element mAgentCode = new Element(AgentCode);
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(mRootEle.getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No"));
		Element mHeadEle = new Element(Head);
		mHeadEle.addContent(mClientIpEle);
		mHeadEle.addContent(mTranComEle);
		mHeadEle.addContent(mFuncFlagEle);
		mHeadEle.addContent(mAgentCom);
		mHeadEle.addContent(mAgentCode);
		mHeadEle.addContent(mInNoDoc);
		mHeadEle.addContent(mTranNo);
		mRootEle.addContent(mHeadEle);
		cLogger.info("���ӱ�׼����ͷ�ڵ���ģ�");
		JdomUtil.print(mInNoStd);

		cLogger.info("Out NewCcbNetImpl.receive()...");
		return mInNoStd;
	}

	public void send(Document pOutNoStd) throws Exception
	{
		// ������Ӧʱ��
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		cLogger.info("Into NewCcbNetImpl.send()...");
		// ����ͷ����
		cLogger.info("���͸����еı��ģ�");
		JdomUtil.print(pOutNoStd);
		
		//�����ر��ĵ��쳣����,���ݸ��ڵ��Ƿ�Ϊ[TX]�����ж�,��Ϊ�쳣��׼���ĸ��ڵ�Ϊ[TranData]
		if(!"TX".equals(pOutNoStd.getRootElement().getName()) && "TranData".equals(pOutNoStd.getRootElement().getName()))
		{
			pOutNoStd = dealErrorMsg((Document)pOutNoStd.clone());
			cLogger.info("�쳣����ת����ı��ģ�");
		}
		
		Element mHeadEle = pOutNoStd.getRootElement().getChild("TX_HEADER");
		int mHeadEleLength = JdomUtil.toBytes(mHeadEle, "UTF-8").length;
		mHeadEleLength += String.valueOf(mHeadEleLength).length() - 1;
		mHeadEle.getChild("SYS_HDR_LEN").setText(String.valueOf(mHeadEleLength));
		// �����ܳ���
		byte[] mXmlBytes = JdomUtil.toBytes(pOutNoStd, "UTF-8");
		int mXmlDocLength = mXmlBytes.length;
		mXmlDocLength += String.valueOf(mXmlDocLength).length() - 1;
		mHeadEle.getChild("SYS_TTL_LEN").setText(String.valueOf(mXmlDocLength));

		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName()).append('_').append(NoFactory.nextAppNo()).append('_').append(cFuncFlag).append("_out.xml");
		SaveMessage.save(pOutNoStd, cTranComEle.getText(), mSaveName.toString());
		cLogger.info("���汨����ϣ�" + mSaveName);
		this.cOutNoStdDoc = mSaveName.toString();
		Format mFormat = Format.getRawFormat().setEncoding("UTF-8").setIndent("   ").setLineSeparator("\n");
		XMLOutputter mXMLOutputter = new XMLOutputter(mFormat);
		ByteArrayOutputStream mBaos = new ByteArrayOutputStream();
		mXMLOutputter.output(pOutNoStd, mBaos);
		mXmlBytes = mBaos.toByteArray();

		Document cXmlDoc = JdomUtil.build(mXmlBytes, "UTF-8");
		// cLogger.info("���չ�˾���룺"+cXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("Ins_Co_ID"));
		cLogger.info("====" + JdomUtil.toString(cXmlDoc));
		// ƴװ��ȫ����ͷ��������
		byte[] outSec = SecurityMessageHeaderUtils.marshal(header, JdomUtil.toBytes(cXmlDoc,"UTF-8"), true);

		cLogger.debug("���ܺ����ݣ�\n" + new String(outSec));

		// ƴװHTTPЭ��ͷ
		StringBuffer sb_http = new StringBuffer();
		sb_http.append("HTTP/1.1 200 OK\r\n");
		sb_http.append("Host: 15.128.4.2: 9000\r\n");
		sb_http.append("Server: BIP 1.0\r\n");
		sb_http.append("Date: (2014-6-3 10:44:05)\r\n");
		sb_http.append("Content-Type: application/x-www-form-urlencoded\r\n");
		sb_http.append("Content-Length:");

		sb_http.append(outSec.length + "\r\n");
		sb_http.append("Connection: keep-alive\r\n");
		sb_http.append("\r\n");

		byte[] sb_httpBytes = sb_http.toString().getBytes();
		byte[] outData = new byte[sb_httpBytes.length + outSec.length];
		System.arraycopy(sb_httpBytes, 0, outData, 0, sb_httpBytes.length);
		System.arraycopy(outSec, 0, outData, sb_httpBytes.length, outSec.length);
		cLogger.debug("ƴװ����Ϊ��" + sb_http.toString());

		cSocket.getOutputStream().write(outData);
		cSocket.getOutputStream().flush();

		cLogger.info("Into NewCcbNetImpl.send()...");
	}

	/**
	 * @Title: readLenData 
	 * @Description: ��ȡָ����������
	 * @param size ����
	 * @param input �ֽ�������
	 * @return void 
	 * @throws �쳣
	 */
	private void readLenData(int size, InputStream input) // ��ȡ��������
	{
		int readed = 0; // �Ѿ���ȡ��
		try
		{
			int available = 0;// input.available(); //�ɶ���
			if (available > (size - readed))
				available = size - readed;
			while (readed < size)
			{
				while (available == 0)
				{ // �ȵ������ݿɶ�
					available = input.available(); // �ɶ���
				}
				if (available > (size - readed))
					available = size - readed; // size-readed--ʣ����
				if (available > 2048)
					available = 2048; // size-readed--ʣ����
				byte[] buffer = new byte[available];
				int reading = input.read(buffer);
				request = request.append(new String(buffer, 0, reading)); // byte�������
				readed += reading; // �Ѷ��ַ�
			}
		}
		catch (java.io.IOException e)
		{
			cLogger.info("Read readLenData Error!");
		}
	}

	/**
	 * 
	 * dealErrorMsg �����쳣������Ϣ
	 *
	 * @param outDoc �쳣����
	 * @return ���طǱ�׼����
	 */
	private  Document dealErrorMsg(Document outStdDoc)
	{
		Document outNoStdDoc = null;
		
		if(outStdDoc != null)
		{
			try
			{
				outNoStdDoc = ErrorOutXsl.newInstance().getCache().transform(outStdDoc);
				
				//��������ı���ͷ
				if(mInDoc != null)
				{
					Element header = (Element) mInDoc.getRootElement().getChild("TX_HEADER").clone();
					
					//���÷��ر��ı���ͷ��Ϣ
					outNoStdDoc = NewCcbFormatUtil.setNoStdTxHeader(outNoStdDoc, header, mSYS_RECV_TIME, mSYS_RESP_TIME);
					
					Element mRetData = outStdDoc.getRootElement().getChild("Head");
					if (mRetData.getChildText(Flag).equals("0"))
					{ // ���׳ɹ�
						outNoStdDoc.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText("���׳ɹ���");
					}
					else
					{ // ����ʧ��
						outNoStdDoc.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE").setText("ZZZ072000001");// ����ͨ�ô������
						outNoStdDoc.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
					}
				}
				
			}
			catch (XSLTransformException e)
			{
				cLogger.error("���ر���ת���쳣��" + e.getMessage());
				e.printStackTrace();
			}
		}
		
		return outNoStdDoc;
	}	
}
