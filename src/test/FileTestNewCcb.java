package test;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

import cn.ccb.secapi.SecAPI;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.bat.BatConf;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.newccb.util.FileUtil;
import com.sinosoft.utility.ElementLis;

public class FileTestNewCcb extends TimerTask implements XmlTag{
	protected final Logger cLogger = Logger.getLogger(getClass());

	protected Date cTranDate;
	private String typeCode = null;
	private String secNodeId = null;
	private String rmtSecNodeId = null;
	private String fileName = null;
	private String filePath = null;
	private Element cBusiConfRoot = null;
//	protected Element cThisConfRoot = null;
	protected Element cThisBusiConf = null;
	private Document returnNoStd = null;//解密后的非标准报文
	private Document cInXmlDoc = null;//转换后的标准报文
	private java.util.List<Element> Detail = new ArrayList<Element>(); 
	private Document cNoStdXml ;
	private Element cTransaction_Header = null;
	protected Element cMidplatRoot = null;
	protected Element cBatchConfRoot = null;
	protected Element cNewCcbConfRoot = null;
	
	
	private void bakFiles(String pFileDir) {
		cLogger.info("Into NewCCB.bakFiles()...");
		
		if (null==pFileDir || "".equals(pFileDir)) {
			cLogger.warn("本地文件目录为空，不进行备份操作！");
			return;
		}
		File mDirFile = new File(pFileDir);
		if (!mDirFile.exists() || !mDirFile.isDirectory()) {
			cLogger.warn("本地文件目录不存在，不进行备份操作！" + mDirFile);
			return;
		}
		
		File[] mOldFiles = mDirFile.listFiles(
				new FileFilter(){
					public boolean accept(File pFile) {
						if (!pFile.isFile()) {
							return false;
						}
						
						Calendar tCurCalendar = Calendar.getInstance();
						tCurCalendar.setTime(cTranDate);
						
						Calendar tFileCalendar = Calendar.getInstance();
						tFileCalendar.setTime(new Date(pFile.lastModified()));
						
						return tFileCalendar.before(tCurCalendar);
					}
				});
		
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.add(Calendar.MONTH, -1);
		File mNewDir = 
			new File(mDirFile, DateUtil.getDateStr(mCalendar, "yyyy/yyyyMM"));
		for (File tFile : mOldFiles) {
			try {
				String fileName_date=tFile.getName().substring(tFile.getName().length()-8);
				Date date=new Date();
				if(!fileName_date.equals(String.valueOf(DateUtil.get8Date(date)))){
					cLogger.info(tFile.getAbsoluteFile() + " start move...");
					IOTrans.fileMove(tFile, mNewDir);
					cLogger.info(tFile.getAbsoluteFile() + " end move!");
				}
			} catch (IOException ex) {
				cLogger.error(tFile.getAbsoluteFile()+"备份失败！", ex);
			}
		}
		
		cLogger.info("Out Balance.bakFiles()!");
	}
	
	
	//解密银行发送过来的加密文件，并且读取文件，根据不同交易转换成不同标准报文。
	public Document fileSecurity(String type,String filePath,String fileName) throws MidplatException{
		cLogger.info("Into FileUtil.fileSecurity()...");

		typeCode=type;
		try{

			if(typeCode.equals("Rcn")){//日终对账
				try {
					cBusiConfRoot = (Element) XPath.selectSingleNode(
							cNewCcbConfRoot, "business[funcFlag='3005']");
				} catch (JDOMException e) {
					e.printStackTrace();
				}
				try{
					cLogger.info("FileName = "+fileName);
					cLogger.info("FilePath=="+filePath);
					//解密后的报文路径
					String mFilePath = cBusiConfRoot.getChildTextTrim("LocalDir");
					//解密：本地节点，对端节点，密文文件存放路径，解密后明文文件绝对路径
					cLogger.info("解密前的对账文件存放路径："+filePath+fileName);
					cLogger.info("解密后的对账文件存放路径："+mFilePath+fileName);
					SecAPI.fileUnEnvelop(secNodeId, rmtSecNodeId, filePath+"/"+fileName, mFilePath+fileName);
					//根据解密后报文路径和名称获取数据流
					InputStream ttBatIns = getLocalFile(mFilePath, fileName);
					if(ttBatIns == null){
						cLogger.info("===============null");
					}else{
						cLogger.info(ttBatIns);
					}
					//获取非标准报文
					returnNoStd = JdomUtil.build(ttBatIns);
					JdomUtil.print(returnNoStd);
				}catch(Exception ex){
					cLogger.error("获得标准对账报文出错！", ex);
					throw new MidplatException("获取解密报文出错");
				}
				try{
					//组装核心标准报文
					cLogger.info("开始解析对账文件里的报文，并组装成标准报文！");
					cInXmlDoc = balanceTo(returnNoStd);
					cLogger.info("发往核心的保准对账报文:");
					JdomUtil.print(cInXmlDoc);
				}catch(Exception ex){
					throw new MidplatException("获取标准报文出错");
				}
			}else	 if(typeCode.equals("Pre")){//保全对账
				try {
					cThisBusiConf = (Element) XPath.selectSingleNode(
							cNewCcbConfRoot, "business[funcFlag='1048']");
				} catch (JDOMException e) {
					e.printStackTrace();
				}
				try{
					cLogger.info("FileName = "+fileName);
					cLogger.info("FilePath=="+filePath);
					//解密后的报文路径
					String mFilePath = cThisBusiConf.getChildTextTrim("LocalDir");

					//解密：本地节点，对端节点，密文文件存放路径，解密后明文文件绝对路径
					cLogger.info("解密前的对账文件存放路径："+filePath+fileName);
					cLogger.info("解密后的对账文件存放路径："+mFilePath+fileName);
					SecAPI.fileUnEnvelop(secNodeId, rmtSecNodeId, filePath+"/"+fileName, mFilePath+fileName);
					//根据解密后报文路径和名称获取数据流
					InputStream ttBatIns = getLocalFile(mFilePath, fileName);

					//获取非标准报文
					returnNoStd = JdomUtil.build(ttBatIns);
					JdomUtil.print(returnNoStd);
				}catch(Exception ex){
					cLogger.error("获得标准对账报文出错！", ex);
					throw new MidplatException("获取解密报文出错");
				}
				try{
//					System.out.println("---------------------------------");
					//组装核心标准报文
					cInXmlDoc = balanceTo(returnNoStd);
//					JdomUtil.print(cInXmlDoc);
				}catch(Exception ex){
					throw new MidplatException("获取标准报文出错");
				}
			}else if(typeCode.equals("Bnk")){//发送银行端单证信息
				try {
					cThisBusiConf = (Element) XPath.selectSingleNode(
							cNewCcbConfRoot, "business[funcFlag='3006']");
				} catch (JDOMException e) {
					e.printStackTrace();
				}
				try{
					cLogger.info("FileName = "+fileName);
					cLogger.info("FilePath=="+filePath);
					//解密后的报文路径
					System.out.println(cThisBusiConf.getChildText("name"));
					String mFilePath = cThisBusiConf.getChildTextTrim("LocalDir");
//					String mFilePath = new StringBuilder(SysInfo.cHome)
					//解密
					cLogger.info("解密前的对账文件存放路径："+filePath+fileName);
					cLogger.info("解密后的对账文件存放路径："+mFilePath+fileName);
					SecAPI.fileUnEnvelop(secNodeId, rmtSecNodeId, filePath+"/"+fileName, mFilePath+fileName);
					//根据解密后报文路径和名称获取数据流
					InputStream ttBatIns = getLocalFile(mFilePath, fileName);
					//获取非标准报文
					returnNoStd = JdomUtil.build(ttBatIns, "UTF-8");
//					JdomUtil.print(returnNoStd);
				}catch(Exception ex){
					cLogger.error("获得标准对账报文出错！", ex);
					throw new MidplatException("获取解密报文出错");
				}
				try{
					//组装核心标准报文
					cInXmlDoc = docuInfoTo(returnNoStd);
				}catch(Exception ex){
					throw new MidplatException("获取标准报文出错");
				}
			}
		}catch(Exception ex){
			throw new MidplatException("文件解密过程报错！");
		}
		
		cLogger.info("Out FileUtil.fileSecurity()...");
		return cInXmlDoc;
	}	
	
	private InputStream getLocalFile(String pDir, String pName) throws MidplatException {
		cLogger.info("Into Balance.getLocalFile()...");
		
		pDir = pDir.replace('\\', '/');
		if (!pDir.endsWith("/")) {
			pDir += '/';
		}
		String mPathName = pDir + pName;
		cLogger.info("LocalPath = " + mPathName);
		
		InputStream mIns = null;
		try {
			mIns = new FileInputStream(mPathName);
		} catch (IOException ex) {
			//未找到对账文件
			throw new MidplatException("未找到对账文件！" + mPathName);
		}
		
		cLogger.info("Out Balance.getLocalFile()!");
		return mIns;
	}
	
	//单证对账
	private Document docuInfoTo(Document returnNoStd){
		Document tInXmlDoc = null;
		
		Element TX_HEADER = cNoStdXml.getRootElement().getChild("TX_HEADER");
		Element COM_ENTITY = cNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY");
		Element Detail_List = returnNoStd.getRootElement().getChild("Detail_List");
		Element mHead = returnNoStd.getRootElement().getChild("Head");
		ElementLis TranData = new ElementLis("TranData");
		ElementLis Body = new ElementLis("Body",TranData);
		//生成标准报文头
		Date tCurDate = new Date();
		Element mTranDate = new ElementLis("TranDate",String.valueOf(DateUtil.get8Date(tCurDate)),cTransaction_Header);
		Element mTranTime = new ElementLis("TranTime",String.valueOf(DateUtil.get6Time(tCurDate)),cTransaction_Header);
		Element mNodeNo = new Element("NodeNo");
		mNodeNo.setText(cThisBusiConf.getChildText("NodeNo"));//newccb.xml配置
		Element mTellerNo = new Element("TellerNo");
		mTellerNo.setText(CodeDef.SYS);
		cTransaction_Header.addContent(mNodeNo);
		cTransaction_Header.addContent(mTellerNo);
		TranData.addContent(cTransaction_Header);
		
		if(Detail_List!=null){//当对账文件为空的时候是没有Detail_List节点的
			
			try {
				Detail = Detail_List.getChildren("Detail");
//				Element APP_ENTITY = returnNoStd.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("APP_ENTITY");
				int size = Detail.size();
				
				ElementLis Count = new ElementLis("Count",Body);
				Count.setText(String.valueOf(size));
				
/*	格式   	   <Body>
				      <Count>1</Count>
				      <Detail>
				         <CardType>0101141</CardType>
				         <CardNo>010114100001474</CardNo>
				         <CardState>4</CardState>
				         <OtherNo>2014112102000001</OtherNo>
				         <AgentCom>0390101205</AgentCom>
				         <TellerNo>02670</TellerNo>
				         <TranNo>20495730</TranNo>
				      </Detail>
				   </Body>*/
				
					for(int i=0;i<size;i++){
						Element tempEle = Detail.get(i);
						ElementLis listDetail = new ElementLis("Detail",Body);
							//重空类型
							ElementLis CardType = new ElementLis("CardType",tempEle.getChildTextTrim("Ins_IBVoch_ID").substring(0, 7),listDetail);
							//重空印刷号
							ElementLis CardNo = new ElementLis("CardNo",tempEle.getChildTextTrim("Ins_IBVoch_ID"),listDetail);
							//重空状态
							ElementLis CardState = new ElementLis("CardState",tempEle.getChildTextTrim("IpOpR_Crcl_StCd"),listDetail);
							if (CardState.getText().equals("01"))
							{// 未使用
								CardState.setText("9");
							}
							if(CardState.getText().equals("03")){//已使用--回銷
								CardState.setText("4");
							}
							if(CardState.getText().equals("04")){//已作废---作廢
								CardState.setText("6");
							}
							//单证关联号
							ElementLis OtherNo = new ElementLis("OtherNo",listDetail);
							//记帐机构号
							ElementLis AgentCom = new ElementLis("AgentCom",listDetail);
							
							ElementLis TellerNo = new ElementLis("TellerNo",listDetail);
							//建行方记帐流水号
							ElementLis TranNo = new ElementLis("TranNo",tempEle.getChildTextTrim("RqPtTcNum"),listDetail);
					}
					 tInXmlDoc = new Document(TranData);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{//对账文件内容为空
			 tInXmlDoc = new Document(TranData);
		}

		return tInXmlDoc;	
	}
	
	//日终对账和保全对账
	private Document balanceTo(Document returnNoStd){
//		JdomUtil.print(returnNoStd);
		cLogger.info("请求报文：");
		JdomUtil.print(cNoStdXml);
		Document tInXmlDoc=null;
		
		Element TX_HEADER = cNoStdXml.getRootElement().getChild("TX_HEADER");
		Element COM_ENTITY = cNoStdXml.getRootElement().getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY");
		Element Detail_List=returnNoStd.getRootElement().getChild("Detail_List");
		
		//封装标准报文
		
		ElementLis TranData = new ElementLis("TranData");
		ElementLis Body = new ElementLis("Body",TranData);
		Date tCurDate = new Date();
		Element mTranDate = new ElementLis("TranDate",String.valueOf(DateUtil.get8Date(tCurDate)),cTransaction_Header);
		Element mTranTime = new ElementLis("TranTime",String.valueOf(DateUtil.get6Time(tCurDate)),cTransaction_Header);
		Element mNodeNo = new Element("NodeNo");
		mNodeNo.setText(cThisBusiConf.getChildText("NodeNo"));//newccb.xml配置
		Element mTellerNo = new Element("TellerNo");
		mTellerNo.setText(CodeDef.SYS);
		cTransaction_Header.addContent(mNodeNo);
		cTransaction_Header.addContent(mTellerNo);
		TranData.addContent(cTransaction_Header);
		
		if(Detail_List!=null){//当对账文件为空的时候是没有Detail_List节点的
			
			try{
				Detail = Detail_List.getChildren("Detail");
				Element mHead = returnNoStd.getRootElement().getChild("Head");
				int size = Detail.size();
				int cnt=0;
				cLogger.info("共有"+size+"笔对账！");
				String typecode = TX_HEADER.getChildTextTrim("SYS_TX_CODE");
					
						if(size>0){
							for(int i=0;i<size;i++){
								ElementLis listDetail = new ElementLis("Detail",Body);
								Element tempEle = Detail.get(i);
//								ElementLis check_trans = new ElementLis("check-trans",check_trans_list);
								//日终对账
								if(typecode.equals("P53817103")){
//									ElementLis Count = new ElementLis("Count",Body);
//									ElementLis sumPrem = new ElementLis("Prem",Body);
									cLogger.info("========进入账务对账报文拼装=========");									
									ElementLis BusiType = new ElementLis("BusiType" ,listDetail);
									if(tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819152")){//新单缴费
										BusiType.setText("01");
									}
									if(tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819156")){//续期缴费
										BusiType.setText("11");
									}
									cnt++;
									ElementLis ContNo = new ElementLis("ContNo",tempEle.getChildTextTrim("InsPolcy_No"),listDetail);
									ElementLis Prem = new ElementLis("Prem",tempEle.getChildTextTrim("TxnAmt"),listDetail);
									ElementLis AgentCom = new ElementLis("AgentCom",tempEle.getChildTextTrim("CCBIns_ID"),listDetail);
									ElementLis ProposalPrtNo = new ElementLis("ProposalPrtNo","",listDetail);
									ElementLis AppntName = new ElementLis("AppntName","",listDetail);
									ElementLis InsuredName = new ElementLis("InsuredName","",listDetail);
									ElementLis TranDate = new ElementLis("TranDate",tempEle.getChildTextTrim("Txn_Dt"),listDetail);
//									mClientIpEle.setText("3005");
									}
								//保全对账
								if(typecode.equals("P53817104")){//建行把修改保单基本信息P53819161也放到保全里面来了，但是核心并不对账此类型
//									mClientIpEle.setText("3006");
									cLogger.info("========进入保全对账报文拼装=========");
									ElementLis BusiType = new ElementLis("BusiType" ,listDetail);
									if(tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819192")){//满期给付
										BusiType.setText("09");
										cnt++;
									}
									if(tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819144")){//退保
										BusiType.setText("10");
										cnt++;
									}
									if(tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819161")){//修改保单基本信息
										BusiType.setText("07");
										cnt++;
									}
									ElementLis TranNo = new ElementLis("TranNo",tempEle.getChildTextTrim("Ins_Co_Jrnl_No"),listDetail);
									ElementLis NodeNo = new ElementLis("NodeNo",tempEle.getChildTextTrim("CCBIns_ID"),listDetail);
									ElementLis ContNo = new ElementLis("ContNo",tempEle.getChildTextTrim("InsPolcy_No"),listDetail);
									ElementLis EdorNo = new ElementLis("EdorNo",tempEle.getChildTextTrim("InsPolcy_Vchr_No"),listDetail);
									ElementLis AccNo = new ElementLis("AccNo",listDetail);
									ElementLis AccName = new ElementLis("AccName","",listDetail);
									ElementLis Prem = new ElementLis("Prem","0",listDetail);//其实保全不需要这个，只是在saveDetail的时候取值，所以给个节点，不然空指针异常
									ElementLis ProposalPrtNo = new ElementLis("ProposalPrtNo","",listDetail);//其实保全不需要这个，只是在saveDetail的时候取值，所以给个节点，不然空指针异常
									ElementLis TranDate = new ElementLis("TranDate",tempEle.getChildTextTrim("Txn_Dt"),listDetail);
									
								}
									
							}
						}
				tInXmlDoc = new Document(TranData);	
				if(typecode.equals("P53817103")){
					cLogger.info("========进入账务对账Count和Prem报文拼装=========");
					JdomUtil.print(tInXmlDoc);
					Element tBody = tInXmlDoc.getRootElement().getChild("Body");
					java.util.List<Element> tDetail = tBody.getChildren("Detail");
					long mSumPrem = 0;
					for(int i = 0;i<tDetail.size();i++){
						long tPremFen = NumberUtil.yuanToFen(((Element)tDetail.get(i)).getChildText("Prem"));
						System.out.println("对账保费："+tPremFen);
						tDetail.get(i).getChild("Prem").setText(String.valueOf(tPremFen));
						mSumPrem += tPremFen; 			
					}
					cLogger.info("====最终总保费==="+mSumPrem);
					try {
						ElementLis sumCount = new ElementLis("Count",Body);//总比数
						ElementLis sumPrem = new ElementLis("Prem",Body);//总保费
						tBody.getChild("Prem").setText(String.valueOf(mSumPrem));
						tBody.getChild("Count").setText(String.valueOf(cnt));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(typecode.equals("P53817104")){
					cLogger.info("========进入保全对账Count和Prem报文拼装=========");
					JdomUtil.print(tInXmlDoc);
					Element tBody = tInXmlDoc.getRootElement().getChild("Body");
					java.util.List<Element> tDetail = tBody.getChildren("Detail");
					try {
						ElementLis sumCount = new ElementLis("Count",Body);//总比数
						ElementLis sumPrem = new ElementLis("Prem",Body);//总保费  插入contblcdtl是后取prem，所以不能没有
						tBody.getChild("Prem").setText("0");
						tBody.getChild("Count").setText(String.valueOf(cnt));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} 
			catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}else{//对账文件内容为空
			tInXmlDoc = new Document(TranData);	
			ElementLis sumCount = new ElementLis("Count",Body);//总比数
			ElementLis sumPrem = new ElementLis("Prem",Body);//总保费
			tInXmlDoc.getRootElement().getChild("Body").getChild("Prem").setText("0");
			tInXmlDoc.getRootElement().getChild("Body").getChild("Count").setText("0");
		}

		cLogger.info("大家看看,标准报文咋样：");
		JdomUtil.print(tInXmlDoc);
		return tInXmlDoc;
	}
	
	public static void main(String[] args) throws IOException, JDOMException, MidplatException {
		
		String ccbDirFile="D:/YBT_SAVE_XML/ZHH/newccb/localrcv/";
		File oldFile=new File(ccbDirFile);
		File[] file=oldFile.listFiles();
		
		for (File tFile : file) {
			String pLocalDir=tFile.getParent();
		    if ((pLocalDir != null) && (!"".equals(pLocalDir))) {
		        pLocalDir = pLocalDir.replace('\\', '/');
		        if (!pLocalDir.endsWith("/")) {
		          pLocalDir = pLocalDir + '/';
		        }
			System.out.println(pLocalDir+tFile.getName());
		    }
		}
//		FileUtil fu=new FileUtil(null);
//		Document cInXml=null;
		
	}

	@Override
	public void run() {
		try {
			
				secNodeId="510050";
				rmtSecNodeId="105005";
			 cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
	         cBatchConfRoot = BatConf.newInstance().getConf().getRootElement();
	         cNewCcbConfRoot = NewCcbConf.newInstance().getConf().getRootElement();
	         cThisBusiConf = ((Element)XPath.selectSingleNode(
	         cBatchConfRoot, "batch[funcFlag='" + "3009" + "']"));
	         
//			String ccbDirFile="D:/YBT_SAVE_XML/ZHH/newccb/localrcv/";
	       System.out.println(cThisBusiConf.getChildText("name"));
			String ccbDirFile=cThisBusiConf.getChildText("ccbLocal");
			File oldFile=new File(ccbDirFile);
			File[] file=oldFile.listFiles();
			
			for (File tFile : file) {
				String pLocalDir=tFile.getParent();
			    if ((pLocalDir != null) && (!"".equals(pLocalDir))) {
			        pLocalDir = pLocalDir.replace('\\', '/');
			        if (!pLocalDir.endsWith("/")) {
			          pLocalDir = pLocalDir + '/';
			        }
				System.out.println(pLocalDir+tFile.getName());
				fileSecurity(tFile.getName().substring(0,3),pLocalDir,tFile.getName());
			    }
			}
			
			
			
		} catch (MidplatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
