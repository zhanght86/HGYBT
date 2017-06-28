package com.sinosoft.midplat.newccb.net;

import java.io.ByteArrayOutputStream;
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

	private byte crlf13 = (byte) 13;
	private byte crlf10 = (byte) 10;
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

	public Document receive() throws Exception
	{
		// �������ʱ��
		mSYS_RECV_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		cLogger.info("Into NewCcbNetImpl.receive()...");
		// ���������
		InputStream mIs = cSocket.getInputStream();
		// ��ȡHTTP����ͷ
		String mDataStr = "";
		byte[] crlf = new byte[1];
		int crlfNum = 0; // �Ѿ����ӵĻس������� crlfNum=4Ϊͷ������
		while (mIs.read(crlf) != -1) // ��ȡͷ��
		{
			if (crlf[0] == crlf13 || crlf[0] == crlf10)
			{
				crlfNum++;
			}
			else
			{
				crlfNum = 0;
			}
			request = request.append(new String(crlf, 0, 1, "UTF-8")); // byte�������
			if (crlfNum == 4)
				break;
		}
		// ��ð�ȫ����ͷ�ͱ��ĵĳ���
		String mData = request.toString();
		cLogger.info("����ͷ��Ϣ��" + request.toString());
		int mContent_Length = mData.indexOf("Content-Length:") + 15;
		mContent_Length = Integer.parseInt(mData.substring(mContent_Length, mData.indexOf("\r\n", mContent_Length)).trim());
		cLogger.info("�����峤�ȣ�" + mContent_Length);
		// ��ȡ��ȫ����ͷ�ͼ��ܵı���
		readLenData(mContent_Length, mIs);
		mDataStr = request.toString();
		cLogger.debug("�յ�������Դ��" + mDataStr);
		int index_Connection = mDataStr.indexOf("SEC_ERROR_CODE:");
		mDataStr = mDataStr.substring(index_Connection, mDataStr.length()).trim();
		cLogger.debug("��ȫ����ͷ + �����壺" + mDataStr);
		// ������ȫ����ͷ��������
		Object[] recv = SecurityMessageHeaderUtils.ummarshal(mDataStr.getBytes(), true, new SecurityMessageHeader());
		byte[] responseData = (byte[]) recv[1];
		cLogger.debug("������ı����壺" + new String(responseData, "UTF-8"));
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
		String mAgentCom=this.cThisConfRoot.getChildText("AgentCom");
		this.cLogger.info("AgentCom=============="+mAgentCom);
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
		Element mAgentComEle = new Element(AgentCom);
		mAgentComEle.setText(mAgentCom);
		Element mAgentCode = new Element(AgentCode);
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(mRootEle.getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No"));
		Element mHeadEle = new Element(Head);
		mHeadEle.addContent(mClientIpEle);
		mHeadEle.addContent(mTranComEle);
		mHeadEle.addContent(mFuncFlagEle);
		mHeadEle.addContent(mAgentComEle);
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
