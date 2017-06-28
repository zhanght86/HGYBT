package com.sinosoft.midplat.newccb.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;
import cn.ccb.secapi.SecAPI;
import cn.ccb.secapi.SecException;

import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.utility.ElementLis;

public class FileUtil
{
	protected final Logger cLogger = Logger.getLogger(getClass());
	private String typeCode = null;
	private String secNodeId = null;
	private String rmtSecNodeId = null;
	/**文件名*/
	private String fileName = null;
	/**文件路径*/
	private String filePath = null;
	private Element cBusiConfRoot = null;
	protected Element cThisBusiConf = null;
	/**解密后的非标准报文*/
	private Document returnNoStd = null;
	/**转换后的标准报文*/
	private Document cInXmlDoc = null;
	private java.util.List<Element> Detail = new ArrayList<Element>();
	private Element cTransaction_Header = null;

	public FileUtil(Document pInXmlDoc) throws JDOMException{
		cLogger.info("into init FileUtil()..");
		System.out.println("传给FileUtil的报文：");
		JdomUtil.print(pInXmlDoc);
		if (!pInXmlDoc.getRootElement().getChild("TX_HEADER").getChildText("SYS_TX_CODE").equals("P53816107")){   
			//备份Head节点
			cTransaction_Header = (Element) pInXmlDoc.getRootElement().getChild("Head").clone();
		}
		cBusiConfRoot = NewCcbConf.newInstance().getConf().getRootElement();
		Element TX_HEADER = pInXmlDoc.getRootElement().getChild("TX_HEADER");
		typeCode = TX_HEADER.getChildTextTrim("SYS_TX_CODE");
		secNodeId = TX_HEADER.getChildTextTrim("LocalID");
		rmtSecNodeId = TX_HEADER.getChildTextTrim("remoteID");
		//获取保单详情取数(寿险)
		if (typeCode.equals("P53816107")){
			cThisBusiConf = (Element) XPath.selectSingleNode(cBusiConfRoot, "business[funcFlag='1043']");
			fileName = pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY").getChildText("AgIns_BtchBag_Nm") + ".xml";

			filePath = cThisBusiConf.getChildText("ccbLocalDir");
			if (!filePath.endsWith("/")){
				filePath += '/';
			}
		}else{
			Element mTranNo = new Element("TranNo");
			mTranNo.setText(pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No"));
			cTransaction_Header.addContent(mTranNo);
			//加密报文的文件名
			fileName = pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChild("FILE_INFO").getChildTextTrim("FILE_NAME");
			// 加密报文路径
			filePath = pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChild("FILE_INFO").getChildTextTrim("FILE_PATH");
			if (!filePath.endsWith("/")){
				filePath += '/';
			}
		}
		cLogger.info("out init FileUtil()..");
	}
	// 解密银行发送过来的加密文件，并且读取文件，根据不同交易转换成不同标准报文。
	public Document fileSecurity() throws MidplatException{
		cLogger.info("Into FileUtil.fileSecurity()...");
		try{
			if (typeCode.equals("P53817103") || typeCode.equals("P53817104")){
				try{
					// 日终对账
					if(typeCode.equals("P53817103")){
						cThisBusiConf = (Element) XPath.selectSingleNode(cBusiConfRoot, "business[funcFlag='3005']");
					}
					// 保全对账
					if(typeCode.equals("P53817104")){
						cThisBusiConf = (Element) XPath.selectSingleNode(cBusiConfRoot, "business[funcFlag='1048']");
					}
				}catch (JDOMException e){
					e.printStackTrace();
				}
				boolean flag = true;
				try{
					cLogger.info("FileName = " + fileName);
					cLogger.info("FilePath = " + filePath);
					// 解密后的报文路径
					String mFilePath = cThisBusiConf.getChildTextTrim("LocalDir");
					// 解密：本地节点，对端节点，密文文件存放路径，解密后明文文件绝对路径
					cLogger.info("解密前的对账文件存放路径：" + filePath + fileName);
					cLogger.info("解密后的对账文件存放路径：" + mFilePath + fileName);
					//文件解密
					SecAPI.fileUnEnvelop(secNodeId, rmtSecNodeId, filePath + "/" + fileName, mFilePath + fileName);
				}catch (Exception ex){
					ex.printStackTrace();
					cLogger.error("解密文件失败！", ex);
					flag = false;
				}
				if(flag){
					cInXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_OK, "解密文件成功");
				}else{
					cInXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, "解密文件失败");
				}
			}else if (typeCode.equals("P53817105")){
				// 发送银行端单证信息
				try{
					cThisBusiConf = (Element) XPath.selectSingleNode(cBusiConfRoot, "business[funcFlag='3006']");
				}catch (JDOMException e){
					e.printStackTrace();
				}
				try{
					cLogger.info("FileName = " + fileName);
					cLogger.info("FilePath==" + filePath);
					// 解密后的报文路径
					String mFilePath = cThisBusiConf.getChildTextTrim("LocalDir");
					// 解密
					cLogger.info("解密前的对账文件存放路径：" + filePath + fileName);
					cLogger.info("解密后的对账文件存放路径：" + mFilePath + fileName);
					SecAPI.fileUnEnvelop(secNodeId, rmtSecNodeId, filePath + "/" + fileName, mFilePath + fileName);
					// 根据解密后报文路径和名称获取数据流
					InputStream ttBatIns = getLocalFile(mFilePath, fileName);
					// 获取非标准报文
					returnNoStd = JdomUtil.build(ttBatIns, "UTF-8");
				}catch (Exception ex){
					cLogger.error("获得标准对账报文出错！", ex);
					throw new MidplatException("获取解密报文出错");
				}
				try{
					// 组装核心标准报文
					cInXmlDoc = docuInfoTo(returnNoStd);
				}catch (Exception ex){
					throw new MidplatException("获取标准报文出错");
				}
			}
		}catch (Exception ex){
			throw new MidplatException("文件解密过程报错！");
		}
		cLogger.info("Out FileUtil.fileSecurity()...");
		return cInXmlDoc;
	}
	// 对发给银行文件进行加密，使用获取保单详情取数
	public Void fileEncrpSecurity() throws MidplatException{
		cLogger.info("Into FileUtil.fileEncrpSecurity()...");
		try{
			if (typeCode.equals("P53816107")){// 获取保单详情取数
				try{
					cThisBusiConf = (Element) XPath.selectSingleNode(cBusiConfRoot, "business[funcFlag='1043']");
				}catch (JDOMException e){
					e.printStackTrace();
				}
				try{
					cLogger.info("FileName = " + fileName);
					cLogger.info("加密后存放路径:FilePath==" + filePath);
					// 解密后的报文路径
					String mFilePath = cThisBusiConf.getChildTextTrim("LocalDir");
					// 解密
					cLogger.info("加密前的文件存放路径：" + mFilePath + fileName);
					cLogger.info("加密后的文件存放路径：" + filePath + fileName);
					cLogger.info("secNodeId：" + secNodeId + "       rmtSecNodeId:" + rmtSecNodeId);
					SecAPI.fileEnvelop(secNodeId, rmtSecNodeId, mFilePath + fileName, filePath + fileName);
				}catch (Exception ex){
					ex.printStackTrace();
					cLogger.error("加密保单详情文件出错！", ex);
					throw new MidplatException("加密保单详情文件出错");
				}
			}
		}catch (Exception ex){
			ex.printStackTrace();
			throw new MidplatException("文件加密过程报错！");
		}
		cLogger.info("Out FileUtil.fileEncrpSecurity()...");
		return null;
	}
	// 单证对账
	@SuppressWarnings("unchecked")
	private Document docuInfoTo(Document returnNoStd){
		Document tInXmlDoc = null;
		Element Detail_List = returnNoStd.getRootElement().getChild("Detail_List");
		ElementLis TranData = new ElementLis("TranData");
		ElementLis Body = new ElementLis("Body", TranData);
		// 生成标准报文头
		Date tCurDate = new Date();
		new ElementLis("TranDate", String.valueOf(DateUtil.get8Date(tCurDate)), cTransaction_Header);
		new ElementLis("TranTime", String.valueOf(DateUtil.get6Time(tCurDate)), cTransaction_Header);
		Element mNodeNo = new Element("NodeNo");
		mNodeNo.setText(cThisBusiConf.getChildText("NodeNo"));// newccb.xml配置
		Element mTellerNo = new Element("TellerNo");
		mTellerNo.setText(CodeDef.SYS);
		cTransaction_Header.addContent(mNodeNo);
		cTransaction_Header.addContent(mTellerNo);
		TranData.addContent(cTransaction_Header);
		if (Detail_List != null){// 当对账文件为空的时候是没有Detail_List节点的
			try{
				Detail = Detail_List.getChildren("Detail");
				int size = Detail.size();
				ElementLis Count = new ElementLis("Count", Body);
				Count.setText(String.valueOf(size));
				/*
				 * 格式 <Body> <Count>1</Count> <Detail>
				 * <CardType>0101141</CardType> <CardNo>010114100001474</CardNo>
				 * <CardState>4</CardState> <OtherNo>2014112102000001</OtherNo>
				 * <AgentCom>0390101205</AgentCom> <TellerNo>02670</TellerNo>
				 * <TranNo>20495730</TranNo> </Detail> </Body>
				 */
				for (int i = 0; i < size; i++)
				{
					Element tempEle = Detail.get(i);
					ElementLis listDetail = new ElementLis("Detail", Body);
					// 重空类型
					new ElementLis("CardType", tempEle.getChildTextTrim("Ins_IBVoch_ID").substring(0,5), listDetail);
					// 重空印刷号
					new ElementLis("CardNo",NumberUtil.no13To15(tempEle.getChildTextTrim("Ins_IBVoch_ID")), listDetail);
					// 重空状态
					ElementLis CardState = new ElementLis("CardState", tempEle.getChildTextTrim("IpOpR_Crcl_StCd"), listDetail);
					if (CardState.getText().equals("01"))
					{// 未使用
						CardState.setText("9");
					}
					if (CardState.getText().equals("03"))
					{// 已使用---回銷
						CardState.setText("4");
					}
					if (CardState.getText().equals("04"))
					{// 已作废---作廢
						CardState.setText("6");
					}
					// 单证关联号
					new ElementLis("OtherNo", listDetail);
					// 记帐机构号
					new ElementLis("AgentCom", listDetail);

					new ElementLis("TellerNo", listDetail);
					// 建行方记帐流水号
					new ElementLis("TranNo", tempEle.getChildTextTrim("RqPtTcNum"), listDetail);
				}
				tInXmlDoc = new Document(TranData);
			}catch (Exception e){
				e.printStackTrace();
			}
		}else{
			// 对账文件内容为空
			tInXmlDoc = new Document(TranData);
		}
		return tInXmlDoc;
	}
	private InputStream getLocalFile(String pDir, String pName) throws MidplatException{
		cLogger.info("Into Balance.getLocalFile()...");
		pDir = pDir.replace('\\', '/');
		if (!pDir.endsWith("/")){
			pDir += '/';
		}
		String mPathName = pDir + pName;
		cLogger.info("LocalPath = " + mPathName);

		InputStream mIns = null;
		try{
			mIns = new FileInputStream(mPathName);
		}catch (IOException ex){
			// 未找到对账文件
			throw new MidplatException("未找到对账文件！" + mPathName);
		}
		cLogger.info("Out Balance.getLocalFile()!");
		return mIns;
	}

}
