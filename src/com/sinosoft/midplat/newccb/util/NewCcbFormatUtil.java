package com.sinosoft.midplat.newccb.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
/**
 * 新建行转换类中使用到的方法
 * @author
 *
 */
public class NewCcbFormatUtil {
	public NewCcbFormatUtil(){
		
	}
	
	public static Document setDom(Document pNoStdXml,Document mNoStdXml,String startTime,String endTime){
		
		//处理TX_HEADER
		Element tTX_HEADER = pNoStdXml.getRootElement().getChild("TX_HEADER");
        Element mTX_HEADER = mNoStdXml.getRootElement().getChild("TX_HEADER");
//        setTxHeader(tTX_HEADER,mTX_HEADER,startTime,endTime);
        
		//处理comEntity
		Element tComEntity = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY");
        Element mComEntity = mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY");
//        setComEntity(tComEntity,mComEntity);
  
        return mNoStdXml;
	}
	
	/**
	 *  返回加入了COM_ENTITY节点的非标准报文
	 * @param mNoStdXml 转换后的非标准报文
	 * @param pStdXml	核心返回的标准报文
	 * @param oldComEntity  body节点下的COM_ENTITY节点
	 * @param SysTxCode		服务名
	 * @return 返回非标准报文
	 */
	public static Document setComEntity(Document mNoStdXml,Document pStdXml,Element oldComEntity,String SysTxCode){
		
		//服务名
		Element mSYS_TX_CODE = new Element("SYS_TX_CODE");
		mSYS_TX_CODE.setText(SysTxCode);
		oldComEntity.addContent(mSYS_TX_CODE);
		
		//保险公司账务日期  
		Element mIns_Co_Acg_Dt = new Element("Ins_Co_Acg_Dt");
		oldComEntity.addContent(mIns_Co_Acg_Dt);
		
		//保险公司流水号  设置为服务方流水号 并添加到节点中
		Element mIns_Co_Jrnl_No = new Element("Ins_Co_Jrnl_No");
//		mIns_Co_Jrnl_No.setText("");
		oldComEntity.addContent(mIns_Co_Jrnl_No);
		
		//保险公司客服电话
		Element mTelPhoneNo = new Element("Ins_Co_Cst_Svc_Tel");
		mTelPhoneNo.setText("4009-800-800");
		oldComEntity.addContent(mTelPhoneNo);
		
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").addContent(oldComEntity);
		
		return mNoStdXml;
	}
	
	
	/**
	 * 设置非标准报文头
	 * @param mNoStdXml  返回给银行的非标准报文
	 * @param oldTxHeader	银行发过来的非标准报文的头节点
	 * @param startTime		服务接受时间[开始时间毫秒数]
	 * @param endTime	服务响应时间[结束时间毫秒数]
	 * @return 设置非标准报文头后的非标准输出报文
	 */
	public static Document setNoStdTxHeader(Document mNoStdXml,Element oldTxHeader,String startTime,String endTime){
		//设置非标准输出报文字段
		//全局事件跟踪号[in_noStd.xml:<SYS_EVT_TRACE_ID>1020018031483262927006140</SYS_EVT_TRACE_ID>]
		String mSYS_EVT_TRACE_ID = oldTxHeader.getChildText("SYS_EVT_TRACE_ID");
		//全局事件跟踪号
		mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_EVT_TRACE_ID").setText(mSYS_EVT_TRACE_ID);
		
		//发起方安全节点编号[in_noStd.xml:<SYS_REQ_SEC_ID>102001</SYS_REQ_SEC_ID>]
		String mSYS_REQ_SEC_ID = oldTxHeader.getChildText("SYS_REQ_SEC_ID"); 
		//发起方安全节点编号
		mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_REQ_SEC_ID").setText(mSYS_REQ_SEC_ID);
		
		//子交易序号[in_noStd.xml:<SYS_SND_SERIAL_NO>1000000000</SYS_SND_SERIAL_NO>]
		String mSYS_SND_SERIAL_NO = oldTxHeader.getChildText("SYS_SND_SERIAL_NO");
		//子交易序号
		mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_SND_SERIAL_NO").setText(mSYS_SND_SERIAL_NO);
		
		//服务接受时间[开始时间毫秒数]
		mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RECV_TIME").setText(startTime);
		
		//服务响应时间[结束时间毫秒数]
		mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_TIME").setText(endTime);
		
		 //应用报文长度节点[in_noStd.xml:<SYS_MSG_LEN />][获取节点，无内容]
        Element mSYS_MSG_LEN = mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_MSG_LEN");
        
        //非标准输出报文头元素转换为UTF-8编码的字节数组[保持原格式]的长度[字节数]
        int length = JdomUtil.toBytes(mNoStdXml.getRootElement().getChild("TX_HEADER"),"utf-8").length;
        //
        length += String.valueOf(length).length()-1;
        mSYS_MSG_LEN.setText(Integer.toString(length));
		
		
		return mNoStdXml;
	}
	
	/**
	 * 用于处理建行中时间方面的数据
	 * @param longTime  银行提交过来的时间字符串
	 * @param start	截取字符串开始位置
	 * @param end  截取字符串结束位置
	 * @return
	 */
	public static String getTimeAndDate(String longTime,int start,int end){
		String thisStr =  longTime.substring(start,end);
		return thisStr;
	}
	/**
	 * 用于处理电话中的“-”
	 */
	
	public static String dealPhone(String phoneNo){
		if(phoneNo.contains("-")){
			phoneNo=phoneNo.replaceAll("-", "");
		}
		return phoneNo;
	}
	
}
