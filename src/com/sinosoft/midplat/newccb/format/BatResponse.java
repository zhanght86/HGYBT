
package com.sinosoft.midplat.newccb.format;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;

/**
 * @ClassName: BatResponse 
 * @Description: �������մ������̱���ת����
 * @author yuantongxin
 * @date 2017-1-17 ����7:10:08
 */
public class BatResponse extends XmlSimpFormat {
	
	//�Ǳ�׼���뱨��ͷ
	private Element cTransaction_Header = null;
	//�������ʱ��
	private String mSYS_RECV_TIME = null;
	//������Ӧʱ��
	private String mSYS_RESP_TIME = null;
	//������ˮ��
	private String tranNo = null;
	//��������
	private String tranDate = null;
	//������
	private String sysTxCode = null;
	//�Ǳ�׼���뱨��ͷ
	private Element oldTxHeader = null;
	//�Ǳ�׼���뱨�Ĺ�����
	private Element oldComEntity = null;
	
	/**
	 * <p>Title: BatResponse</p>
	 * <p>Description: �������մ������̱���ת���๹�캯��</p>
	 * @param pThisConf ��ǰ���������ļ�
	 */
	public BatResponse(Element pThisConf) {
		super(pThisConf);
	}
	
	/**
	 * �Ǳ�׼���뱨��ת��׼���뱨��
	 * @pNoStdXml �Ǳ�׼���뱨��
	 * @return ��׼���뱨��
	 * @exception �쳣
	 */
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		//
		cLogger.info("Into BatResponse.noStd2Std()...");
		//�˴�����һ��������ͷ�����Ϣ����֯���ر���ʱ���õ�
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		
//		JdomUtil.print(cTransaction_Header);
		
		//�������ʱ��
		mSYS_RECV_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		oldTxHeader = (Element)pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		oldComEntity = (Element)pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
		sysTxCode= oldTxHeader.getChildText("SYS_TX_CODE");
		
		//��ʱ���汣�չ�˾��������ˮ��
		tranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
		//��ʱ�������з�����������Ϊ���չ�˾�������� 
		tranDate = NewCcbFormatUtil.getTimeAndDate(pNoStdXml.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);		
		
		Document mStdXml = BatResponseInXsl.newInstance().getCache().transform(pNoStdXml);
		
		cLogger.info("Out BatResponse.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into BatResponse.std2NoStd()...");
		
		Document mNoStdXml = BatResponseOutXsl.newInstance().getCache().transform(pStdXml);
         
		//������Ӧʱ��
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		
		//����TX_HEADER��һЩ�ڵ���Ϣ
		mNoStdXml = NewCcbFormatUtil.setNoStdTxHeader(mNoStdXml, oldTxHeader, mSYS_RECV_TIME, mSYS_RESP_TIME);
		
		//���˱��ɹ���ʱ�򣬷���TX_BODYʱ������COM_ENTITY�ڵ�
//		String resultCode = pStdXml.getRootElement().getChild("Head").getChildText("Flag");
//		if(resultCode.equals("0")){
			mNoStdXml = NewCcbFormatUtil.setComEntity(mNoStdXml, pStdXml, oldComEntity, sysTxCode);
//		}
		
		//COM_ENTITY�ڵ���������ˮ��
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("SvPt_Jrnl_No").setText(tranNo);
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Acg_Dt").setText(tranDate);
		
		//COM_ENTITY�ڵ���뱣�չ�˾����ˮ��
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Jrnl_No").setText(tranNo);
		
		/*Start-��֯���ر���ͷ*/

		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//���׳ɹ�
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		} else {	//����ʧ��
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE").setText("ZZZ072000001");//����ͨ�ô������
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		}
		
		/*End-��֯���ر���ͷ*/
		cLogger.info("Out BatResponse.std2NoStd()!");
		return mNoStdXml;
	}
 
}