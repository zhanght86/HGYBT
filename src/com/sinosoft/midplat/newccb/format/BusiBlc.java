package com.sinosoft.midplat.newccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;

/**
 * 对账服务类：保全对账、业务对账、单证对账 由于该交易都是通过文件来进行对账的，则对请求报文不作转换
 * 
 * @author
 * 
 */
public class BusiBlc extends XmlSimpFormat
{
	/**服务接收时间*/
	private String mSYS_RECV_TIME = null;
	/**服务响应时间*/
	private String mSYS_RESP_TIME = null;
	private Document noStdDoc = new Document();
	private String typeCode = null;
	
	/**交易流水号*/
	private String tranNo = null;
	/**交易日期*/
	private String tranDate = null;
	private String sFuncflag = null;
	private String sysTxCode = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;

	public BusiBlc(Element pThisConf)
	{
		super(pThisConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception
	{
		cLogger.info("Into BusiBlc.noStd2Std()...");
		noStdDoc = pNoStdXml;

		oldTxHeader = (Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		oldComEntity = (Element) pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
		sysTxCode = oldTxHeader.getChildText("SYS_TX_CODE");

		// 临时保存保险公司方交易流水号
		tranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");

		// 临时保存银行发起交易日期作为保险公司账务日期
		tranDate = NewCcbFormatUtil.getTimeAndDate(pNoStdXml.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);

		// 服务接受时间
		mSYS_RECV_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		typeCode = pNoStdXml.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_TX_CODE");
		cLogger.info("Out BusiBlc.noStd2Std()!");
		return pNoStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception
	{
		cLogger.info("Into BusiBlc.std2NoStd()...");
		Document mNoStdXml = null;
		//日终与保险公司对账（账务类）
		if (typeCode.equals("P53817103"))
		{
			mNoStdXml = DailyZWBlcOutXsl.newInstance().getCache().transform(pStdXml);
		}
		//日终与保险公司对账（保全类）
		if (typeCode.equals("P53817104"))
		{
			mNoStdXml = DailyBQBlcOutXsl.newInstance().getCache().transform(pStdXml);
		}
		//发送银行端单证信息
		if (typeCode.equals("P53817105"))
		{
			mNoStdXml = SendDocumentOutXsl.newInstance().getCache().transform(pStdXml);
		}

		// 服务响应时间
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

		// 设置TX_HEADER的一些节点信息
		mNoStdXml = NewCcbFormatUtil.setNoStdTxHeader(mNoStdXml, oldTxHeader, mSYS_RECV_TIME, mSYS_RESP_TIME);

		//增加COM_ENTITY节点
		mNoStdXml = NewCcbFormatUtil.setComEntity(mNoStdXml, pStdXml, oldComEntity, sysTxCode);


		// COM_ENTITY节点加入服务方流水号
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("SvPt_Jrnl_No").setText(tranNo);
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Acg_Dt").setText(tranDate);

		// COM_ENTITY节点加入保险公司方流水号
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChild("Ins_Co_Jrnl_No").setText(tranNo);

		/* Start-组织返回报文头 */

		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0"))
		{ // 交易成功
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		}
		else
		{ // 交易失败
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE").setText("ZZZ072000001");// 返回通用错误代码
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC").setText(mRetData.getChildText("Desc"));
			mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN").setText(Integer.toString(mRetData.getChildText(Desc).length()));
		}

		/* End-组织返回报文头 */
		cLogger.info("Out BusiBlc.std2NoStd()!");
		return mNoStdXml;
	}

	 public static void main(String[] args) throws Exception {
	 System.out.println("程序开始…");
	
	 String mInFile = "D:\\work\\中韩\\报文\\60333_537_1003_in.xml";
	 String mOutFile = "D:\\work\\中韩\\报文\\11111.xml";
	
	 Document mInXmlDoc = JdomUtil.build(new FileInputStream(mInFile));

	 JdomUtil.print(mInXmlDoc);
	
	 System.out.println("-----------------------------------------------------------");
	 Element w = new Element("e");
//	 Document mOutXmlDoc = new BusiBlc(w).noStd2Std(mInXmlDoc);
	  Document mOutXmlDoc = new BusiBlc(w).std2NoStd(mInXmlDoc);
	
	 JdomUtil.print(mOutXmlDoc);
	 JdomUtil.output(mOutXmlDoc, new FileOutputStream(mOutFile));
	
	 System.out.println("成功结束！");
	 }
}