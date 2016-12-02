//建行获取保单详细取数(寿险)
package com.sinosoft.midplat.newccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.newccb.util.CcbGetFile;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;
import com.sinosoft.midplat.newccb.util.PutContFile;

public class GetContList2 extends XmlSimpFormat
{
	private Element cTransaction_Header = null;
	private String mSYS_RECV_TIME = null;
	private String mSYS_RESP_TIME = null;
	private String tranNo = null;
	private String tranDate = null;
	private String sysTxCode = null;
	private String tPackNum = null;
	private String tBagName = null;
	private Element oldTxHeader = null;
	private Element oldComEntity = null;
	private Element cBusiConfRoot = null;
	private Element cThisBusiConf = null;
	/**请求的非标准报文*/
	private Document cNoStdXml = null;

	public GetContList2(Element pThisConf)
	{
		super(pThisConf);
	}

	public Document noStd2Std(Document pNoStdXml) throws Exception
	{
		cLogger.info("Into GetContList2.noStd2Std()...");
		cNoStdXml = pNoStdXml;
		// 此处备份一下请求报文头相关信息，组织返回报文时会用到
		cTransaction_Header = (Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();

		// JdomUtil.print(cTransaction_Header);

		// 服务接受时间
		mSYS_RECV_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

		oldTxHeader = (Element) pNoStdXml.getRootElement().getChild("TX_HEADER").clone();
		oldComEntity = (Element) pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").clone();
		sysTxCode = oldTxHeader.getChildText("SYS_TX_CODE");

		// 临时保存保险公司方交易流水号
		tranNo = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
		// 临时保存银行发起交易日期作为保险公司账务日期
		tranDate = NewCcbFormatUtil.getTimeAndDate(pNoStdXml.getRootElement().getChild("TX_HEADER").getChildText("SYS_REQ_TIME"), 0, 8);
		tBagName = pNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("AgIns_BtchBag_Nm");
		Document mStdXml = GetContList2InXsl.newInstance().getCache().transform(pNoStdXml);

		// 如果不是当天发查询交易的话，核心取的日期是TranDate，所以，我们对批量报名称substring一下把查询时间送给核心
		mStdXml.getRootElement().getChild("Body").getChild("QryStrDate").setText(tBagName.substring(10, 18));

		cLogger.info("Out GetContList.noStd2Std()!");
		return mStdXml;
	}

	public Document std2NoStd(Document pStdXml) throws Exception
	{
		cLogger.info("Into GetContList2.std2NoStd()...");
		JdomUtil.print(pStdXml);

		//非标准报文
		Document mNoStdXml = GetContList2OutXsl.newInstance().getCache().transform(pStdXml);

		// 服务响应时间
		mSYS_RESP_TIME = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());

		// 设置TX_HEADER的一些节点信息
		mNoStdXml = NewCcbFormatUtil.setNoStdTxHeader(mNoStdXml, oldTxHeader, mSYS_RECV_TIME, mSYS_RESP_TIME);

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

		// 服务状态
		Element mSYS_TX_STATUS = mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_TX_STATUS");
		// 服务响应描述
		Element mSYS_RESP_DESC = mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC");

		/* End-组织返回报文头 */
		Element tAPP_ENTITY = mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY");
		cBusiConfRoot = NewCcbConf.newInstance().getConf().getRootElement();
		//获取配置节点信息
		cThisBusiConf = (Element) XPath.selectSingleNode(cBusiConfRoot, "business[funcFlag='1043']");		
		cLogger.info("配置节点element:");
		JdomUtil.print(cThisBusiConf);

		//本地加密前路徑 
		String localPath = cThisBusiConf.getChildText("LocalDir");
		//建行取文件路徑
		String ccblocalPath = cThisBusiConf.getChildText("ccbLocalDir");
		String tTranCode = cThisBusiConf.getChild("funcFlag").getAttributeValue("outcode");
		System.out.println("加密前文件路径：" + localPath);
		System.out.println("交易码：" + tTranCode);
	
		String fileName = tBagName + ".xml";
		String filePath = localPath + fileName;
		System.out.println("文件绝对路径：" + filePath);
		
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChild("FILE_INFO").getChild("FILE_NAME").setText(fileName);
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChild("FILE_INFO").getChild("FILE_PATH").setText(ccblocalPath);
		//每次请求取数文件只有一个，所以该FILE_NUM设置为1
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChild("FILE_NUM").setText("1");
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChild("FILE_MODE").setText("0");


		JdomUtil.print(mNoStdXml);

		//获取保单详情循环节点内容
		List<Element> tDetail = mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChild("Insu_List").getChildren("Insu_Detail");
		//组织保单详情返回文件
		if (tDetail.size() != 0)
		{
			JdomUtil.print(cNoStdXml);
			boolean isTrue = new PutContFile().isSuccessed(tDetail, filePath, tBagName, mNoStdXml, cNoStdXml);
			if (isTrue = true)
			{
				//当前批明细总笔数
				Element tCur_Btch_Dtl_TDnum = tAPP_ENTITY.getChild("Cur_Btch_Dtl_TDnum").setText(Integer.toString(tDetail.size()));
				//批量包名称
				Element AgIns_BtchBag_Nm = tAPP_ENTITY.getChild("AgIns_BtchBag_Nm").setText(tBagName);
			}
			else
			{
				mSYS_TX_STATUS.setText("01");
				mSYS_RESP_DESC.setText("文件不存在");
			}
		}

		JdomUtil.print(mNoStdXml);
		mNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").removeChild("Insu_List");
		JdomUtil.print(mNoStdXml);

		cLogger.info("Out GetContList2.std2NoStd()!");
		return mNoStdXml;
	}

	public static void main(String[] args) throws Exception
	{
		System.out.println("程序开始…");

		// String mInFilePath =
		// "H:/李路/模拟建行报文/ccb_CCB000000000111111_P53819184_134728.xml";
		// String mOutFilePath = "H:/李路/模拟建行报文/P53819184inSvc.xml";
		// String mInFilePath = "H:/李路/任务/P53819184InNoStd.xml";
		// String mOutFilePath = "H:/李路/任务/P53819184inSvcStd.xml";
		String mInFilePath = "E:/1067817_13_38_outSvc.xml";
		String mOutFilePath = "E:/99999.xml";

		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();

		Document mOutXmlDoc = new GetContList2(null).std2NoStd(mInXmlDoc);
		// Document mOutXmlDoc = new GetContList2(null).noStd2Std(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);

		OutputStream mOs = new FileOutputStream(mOutFilePath);
		JdomUtil.output(mOutXmlDoc, mOs);
		mOs.flush();
		mOs.close();

		System.out.println("成功结束！");
	}

}