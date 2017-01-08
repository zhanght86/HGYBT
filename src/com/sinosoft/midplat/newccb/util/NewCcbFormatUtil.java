package com.sinosoft.midplat.newccb.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
/**
 * �½���ת������ʹ�õ��ķ���
 * @author
 *
 */
public class NewCcbFormatUtil {
	public NewCcbFormatUtil(){
		
	}
	
	public static Document setDom(Document pNoStdXml,Document mNoStdXml,String startTime,String endTime){
		
		//����TX_HEADER
		Element tTX_HEADER = pNoStdXml.getRootElement().getChild("TX_HEADER");
        Element mTX_HEADER = mNoStdXml.getRootElement().getChild("TX_HEADER");
//        setTxHeader(tTX_HEADER,mTX_HEADER,startTime,endTime);
        
		//����comEntity
		Element tComEntity = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY");
        Element mComEntity = mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY");
//        setComEntity(tComEntity,mComEntity);
  
        return mNoStdXml;
	}
	
	/**
	 *  ���ؼ�����COM_ENTITY�ڵ�ķǱ�׼����
	 * @param mNoStdXml ת����ķǱ�׼����
	 * @param pStdXml	���ķ��صı�׼����
	 * @param oldComEntity  body�ڵ��µ�COM_ENTITY�ڵ�
	 * @param SysTxCode		������
	 * @return ���طǱ�׼����
	 */
	public static Document setComEntity(Document mNoStdXml,Document pStdXml,Element oldComEntity,String SysTxCode){
		
		//������
		Element mSYS_TX_CODE = new Element("SYS_TX_CODE");
		mSYS_TX_CODE.setText(SysTxCode);
		oldComEntity.addContent(mSYS_TX_CODE);
		
		//���չ�˾��������  
		Element mIns_Co_Acg_Dt = new Element("Ins_Co_Acg_Dt");
		oldComEntity.addContent(mIns_Co_Acg_Dt);
		
		//���չ�˾��ˮ��  ����Ϊ������ˮ�� ����ӵ��ڵ���
		Element mIns_Co_Jrnl_No = new Element("Ins_Co_Jrnl_No");
//		mIns_Co_Jrnl_No.setText("");
		oldComEntity.addContent(mIns_Co_Jrnl_No);
		
		//���չ�˾�ͷ��绰
		Element mTelPhoneNo = new Element("Ins_Co_Cst_Svc_Tel");
		mTelPhoneNo.setText("4009-800-800");
		oldComEntity.addContent(mTelPhoneNo);
		
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").addContent(oldComEntity);
		
		return mNoStdXml;
	}
	
	
	/**
	 * ���÷Ǳ�׼����ͷ
	 * @param mNoStdXml  ���ظ����еķǱ�׼����
	 * @param oldTxHeader	���з������ķǱ�׼���ĵ�ͷ�ڵ�
	 * @param startTime		�������ʱ��[��ʼʱ�������]
	 * @param endTime	������Ӧʱ��[����ʱ�������]
	 * @return ���÷Ǳ�׼����ͷ��ķǱ�׼�������
	 */
	public static Document setNoStdTxHeader(Document mNoStdXml,Element oldTxHeader,String startTime,String endTime){
		//���÷Ǳ�׼��������ֶ�
		//ȫ���¼����ٺ�[in_noStd.xml:<SYS_EVT_TRACE_ID>1020018031483262927006140</SYS_EVT_TRACE_ID>]
		String mSYS_EVT_TRACE_ID = oldTxHeader.getChildText("SYS_EVT_TRACE_ID");
		//ȫ���¼����ٺ�
		mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_EVT_TRACE_ID").setText(mSYS_EVT_TRACE_ID);
		
		//���𷽰�ȫ�ڵ���[in_noStd.xml:<SYS_REQ_SEC_ID>102001</SYS_REQ_SEC_ID>]
		String mSYS_REQ_SEC_ID = oldTxHeader.getChildText("SYS_REQ_SEC_ID"); 
		//���𷽰�ȫ�ڵ���
		mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_REQ_SEC_ID").setText(mSYS_REQ_SEC_ID);
		
		//�ӽ������[in_noStd.xml:<SYS_SND_SERIAL_NO>1000000000</SYS_SND_SERIAL_NO>]
		String mSYS_SND_SERIAL_NO = oldTxHeader.getChildText("SYS_SND_SERIAL_NO");
		//�ӽ������
		mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_SND_SERIAL_NO").setText(mSYS_SND_SERIAL_NO);
		
		//�������ʱ��[��ʼʱ�������]
		mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RECV_TIME").setText(startTime);
		
		//������Ӧʱ��[����ʱ�������]
		mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_TIME").setText(endTime);
		
		 //Ӧ�ñ��ĳ��Ƚڵ�[in_noStd.xml:<SYS_MSG_LEN />][��ȡ�ڵ㣬������]
        Element mSYS_MSG_LEN = mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_MSG_LEN");
        
        //�Ǳ�׼�������ͷԪ��ת��ΪUTF-8������ֽ�����[����ԭ��ʽ]�ĳ���[�ֽ���]
        int length = JdomUtil.toBytes(mNoStdXml.getRootElement().getChild("TX_HEADER"),"utf-8").length;
        //
        length += String.valueOf(length).length()-1;
        mSYS_MSG_LEN.setText(Integer.toString(length));
		
		
		return mNoStdXml;
	}
	
	/**
	 * ���ڴ�������ʱ�䷽�������
	 * @param longTime  �����ύ������ʱ���ַ���
	 * @param start	��ȡ�ַ�����ʼλ��
	 * @param end  ��ȡ�ַ�������λ��
	 * @return
	 */
	public static String getTimeAndDate(String longTime,int start,int end){
		String thisStr =  longTime.substring(start,end);
		return thisStr;
	}
	/**
	 * ���ڴ���绰�еġ�-��
	 */
	
	public static String dealPhone(String phoneNo){
		if(phoneNo.contains("-")){
			phoneNo=phoneNo.replaceAll("-", "");
		}
		return phoneNo;
	}
	
}
