package com.sinosoft.midplat.newccb.format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class DelayedNewCont extends XmlSimpFormat {
	private Document pNoStdXmlCopy=null;
	private String receiveTime=null;
	public DelayedNewCont(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into DelayedNewCont.noStd2Std()...");
		pNoStdXmlCopy=pNoStdXml;//备份银行传进非标准报文
		//服务接受时间
		receiveTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String insuID=this.cThisBusiConf.getParentElement().getChild("bank").getAttributeValue("insu");
		cLogger.info("银行编码:"+insuID);
		pNoStdXml.getRootElement().getChild("Head").addContent(new Element("InsuID").setText(insuID));
		Document mStdXml = DelayedNewContInXsl.newInstance().getCache().transform(pNoStdXml);
		cLogger.info("Out DelayedNewCont.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into DelayedNewCont.std2NoStd()...");
		Document mNoStdXml = DelayedNewContOutXsl.newInstance().getCache().transform(pStdXml);
		//服务响应时间
		String responseTime=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		//返回非标准报文节点值设置
		stdXmlFill(mNoStdXml,pNoStdXmlCopy,receiveTime,responseTime);
		cLogger.info("Out DelayedNewCont.std2NoStd()!");
		return mNoStdXml;
	}
	/**
	 * 返回非标准报文节点值设置
	 * @param stdXml 返回给银行的非标准报文
	 * @param noStdXml 银行传入的非标准报文
	 * @param requestTime 报文接收时间
	 * @param responseTime 报文响应时间
	 */
	public void stdXmlFill(Document stdXml,Document noStdXml,String receiveTime,String responseTime) throws Exception{
		/**协议报文头信息设置*/
		Element newTX_HEADER=stdXml.getRootElement().getChild("TX_HEADER");
		Element oldTX_HEADER=noStdXml.getRootElement().getChild("TX_HEADER");
		//全局事件跟踪号
		String mSYS_EVT_TRACE_ID = oldTX_HEADER.getChildText("SYS_EVT_TRACE_ID");
		newTX_HEADER.getChild("SYS_EVT_TRACE_ID").setText(mSYS_EVT_TRACE_ID);
		//发起方安全节点编号
		String mSYS_REQ_SEC_ID = oldTX_HEADER.getChildText("SYS_REQ_SEC_ID"); 
		newTX_HEADER.getChild("SYS_REQ_SEC_ID").setText(mSYS_REQ_SEC_ID);
		//子交易序号
		String mSYS_SND_SERIAL_NO = oldTX_HEADER.getChildText("SYS_SND_SERIAL_NO");
		newTX_HEADER.getChild("SYS_SND_SERIAL_NO").setText(mSYS_SND_SERIAL_NO);
		//服务接受时间
		newTX_HEADER.getChild("SYS_RECV_TIME").setText(receiveTime);
		//服务响应时间
		newTX_HEADER.getChild("SYS_RESP_TIME").setText(responseTime);
		//服务响应描述长度
		int SYS_RESP_DESC_LENLength=newTX_HEADER.getChildText("SYS_RESP_DESC").getBytes("UTF-8").length;
		newTX_HEADER.getChild("SYS_RESP_DESC_LEN").setText(SYS_RESP_DESC_LENLength+"");
		
		/**应用报文中公共域信息设置*/
		Element newENTITY=stdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY");
		Element oldCOM_ENTITY=noStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY");
		//服务名
		Element mSYS_TX_CODE = new Element("SYS_TX_CODE");
		mSYS_TX_CODE.setText(oldTX_HEADER.getChildText("SYS_TX_CODE"));
		oldCOM_ENTITY.addContent(mSYS_TX_CODE);
		//保险公司账务日期  
		Element mIns_Co_Acg_DtEle = new Element("Ins_Co_Acg_Dt");
		mIns_Co_Acg_DtEle.setText(oldTX_HEADER.getChildText("SYS_REQ_TIME").substring(0,8));
		oldCOM_ENTITY.addContent(mIns_Co_Acg_DtEle);
		//保险公司流水号
		Element mIns_Co_Jrnl_No = new Element("Ins_Co_Jrnl_No");
		mIns_Co_Jrnl_No.setText(oldCOM_ENTITY.getChildText("SvPt_Jrnl_No"));
		oldCOM_ENTITY.addContent(mIns_Co_Jrnl_No);
		//保险公司客服电话
		Element mTelPhoneNo = new Element("Ins_Co_Cst_Svc_Tel");
		mTelPhoneNo.setText("4009-800-800");
		oldCOM_ENTITY.addContent(mTelPhoneNo);
		//替换返回报文中公共域节点
		newENTITY.removeChild("COM_ENTITY");
		newENTITY.addContent((Element)oldCOM_ENTITY.clone());
		
		/**设置实体域中应用域信息*/
		Element newAPP_ENTITY=stdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY");
		Element oldAPP_ENTITY=noStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY");
		//如果交易成功返回实体域中的应用域
	    if("00".equals(newTX_HEADER.getChildText("SYS_TX_STATUS"))){
	    	Element APP_ENTITYEle=new Element("APP_ENTITY");
	    	Element AgIns_Pkg_IDEle=new Element("AgIns_Pkg_ID");
	    	//险种个数
	    	Element Cvr_NumEle=new Element("Cvr_Num");
	    	Cvr_NumEle.setText(oldAPP_ENTITY.getChildText("Cvr_Num"));
	    	//险种节点
	    	Element Bu_ListEle=new Element("Bu_List");
	    	APP_ENTITYEle.addContent(AgIns_Pkg_IDEle);
	    	APP_ENTITYEle.addContent(Cvr_NumEle);
	    	APP_ENTITYEle.addContent(Bu_ListEle);
	    	//获取银行传进报文中险种节点集合
	    	List<Element> insuranceCodeList=oldAPP_ENTITY.getChild("Bu_List").getChildren("Bu_Detail");
	    	for (Element element : insuranceCodeList) {
	    		Element Bu_DetailEle=new Element("Bu_Detail");
	    		Element Cvr_IDEle=new Element("Cvr_ID");
	    		Cvr_IDEle.setText(element.getChildText("Cvr_ID"));
	    		Bu_DetailEle.addContent(Cvr_IDEle);
	    		Bu_ListEle.addContent((Element)Bu_DetailEle);
			}
	    	//添加实体域中应用域节点
	    	newAPP_ENTITY.addContent(APP_ENTITYEle);
	    	//投保单号节点
	    	Element Ins_BillNoEle=new Element("Ins_BillNo");
	    	Ins_BillNoEle.setText(oldAPP_ENTITY.getChildText("Ins_Bl_Prt_No"));
	    	Element Ins_Co_Acrdt_Stff_NmEle=new Element("Ins_Co_Acrdt_Stff_Nm");
	    	Element InsCoAcrStCrQuaCtf_IDEle=new Element("InsCoAcrStCrQuaCtf_ID");
	    	APP_ENTITYEle.addContent(Ins_BillNoEle);
	    	APP_ENTITYEle.addContent(Ins_Co_Acrdt_Stff_NmEle);
	    	APP_ENTITYEle.addContent(InsCoAcrStCrQuaCtf_IDEle);
	    }
	    //设置TX_HEADER协议报文头中应用报文长度
	    int TX_BODYLength=JdomUtil.toBytes(stdXml.getRootElement().getChild("TX_BODY")).length;
	    newTX_HEADER.getChild("SYS_MSG_LEN").setText(TX_BODYLength+"");
	}
}
