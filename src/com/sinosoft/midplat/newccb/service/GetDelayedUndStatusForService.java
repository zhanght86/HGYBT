package com.sinosoft.midplat.newccb.service;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import cn.ccb.secapi.SecAPI;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;

public class GetDelayedUndStatusForService extends ServiceImpl{
	public GetDelayedUndStatusForService(Element pThisBusiConf)
	{
		super(pThisBusiConf);
	}

	@Override
	public Document service(Document pInXmlDoc) {
		cLogger.info("Into GetDelayedUndStatusForService.service()!");
		cLogger.info("ת���ı�׼����:"+JdomUtil.toStringFmt(pInXmlDoc));
		//���ױ�־
		String mFuncFlag=pInXmlDoc.getRootElement().getChild("BaseInfo").getChildText("FunctionFlag");
		String mTranCom=pInXmlDoc.getRootElement().getChild("Head").getChildText("TranCom");
		long mStartMillis = System.currentTimeMillis();
		try {
			//�������
			cOutXmlDoc=CallWebsvcAtomSvc(pInXmlDoc);
			//У����ķ��صı�����ϸ����������������ϸ�����Ƿ����
			if(!pInXmlDoc.getRootElement().getChild("LCCont").getChild("ContList").getChildText("ContNum").equals(
			   cOutXmlDoc.getRootElement().getChild("LCCont").getChildText("ContNum"))){
				throw new MidplatException("���ķ��صı�����ϸ����������������ϸ������һ�£�");
			}
			cLogger.info("���ķ��ر���:"+JdomUtil.toStringFmt(cOutXmlDoc));
			//���������Ӧ���ĳɹ���������Ӧ�ļ�:���ķ���1���ɹ���0��ʧ��
			if("1".equals(cOutXmlDoc.getRootElement().getChild("RetData").getChildText("Flag"))){
			     createEncryptFile(pInXmlDoc,cOutXmlDoc,this.cThisBusiConf);
			}
		} catch (Exception e) {
			e.printStackTrace();
			cLogger.info(cThisBusiConf.getChildText("name")+"����ʧ�ܣ�"+e.getMessage());
			cOutXmlDoc=getSimpleErrorXml("0",e.getMessage());
			cLogger.info(JdomUtil.toStringFmt(cOutXmlDoc));
		}
		if (null != cTranLogDB)
		{ // ������־ʧ��ʱcTranLogDB=null
			Element RetDataEle = cOutXmlDoc.getRootElement().getChild("RetData");
			cTranLogDB.setRCode(RetDataEle.getChildText("Flag").equals("1")?"0":"1");
			cTranLogDB.setRText(RetDataEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update())
			{
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		cLogger.info("Out GetDelayedUndStatusForService.service()!");
		return cOutXmlDoc;
	}
	/**
	 * ������Ӧ���еļ����ļ�
	 * @param pInXmlDoc  ������ı���
	 * @param cOutXmlDoc ���ķ��ر���
	 * @param pThisBusiConf  ��ȡ��ʵʱ�˱���Ϣ���׵����ýڵ�
	 * @return 
	 */
	public void createEncryptFile(Document pInXmlDoc,Document cOutXmlDoc,Element pThisBusiConf) throws Exception{
		
		try {
			String localID=pInXmlDoc.getRootElement().getChild("Head").getChildText("LocalID");
			String remoteID=pInXmlDoc.getRootElement().getChild("Head").getChildText("RemoteID");
			cLogger.info("���ж˰�ȫ�ڵ��:"+remoteID);
			cLogger.info("���չ�˾�˰�ȫ�ڵ��:"+localID);
			String localDir=pThisBusiConf.getChildText("LocalDir");
			String ccbLocalDir=pThisBusiConf.getChildText("ccbLocalDir");
			cLogger.info("���������ļ�·��:"+localDir);
			cLogger.info("���л�ȡ�ļ�·��:"+ccbLocalDir);
			if(!localDir.endsWith("/")){
				localDir+="/";
			}
			if(!ccbLocalDir.endsWith("/")){
				ccbLocalDir+="/";
			}
			File localDirFile=new File(localDir);
		    if(!localDirFile.exists()){
		    	if(!localDirFile.mkdirs()){
		    		throw new MidplatException("���������ļ�·������ʧ�ܣ�");
		    	}
		    }
			//��֯��Ӧ���ĸ�ʽ
			Element LCContEle=cOutXmlDoc.getRootElement().getChild("LCCont");
			Element  RootEle=new Element("Root");
			Element  HeadEle=new Element("Head");
			Element Detail_ListEle=new Element("Detail_List");
			Element  NRlTmInsPlyDtlTot_NumEle=new Element("NRlTmInsPlyDtlTot_Num");
			NRlTmInsPlyDtlTot_NumEle.setText(LCContEle.getChildText("ContNum"));
			cLogger.info("������Ӧ��ʵʱ������ϸ����Ϊ��"+LCContEle.getChildText("ContNum"));
			HeadEle.addContent(NRlTmInsPlyDtlTot_NumEle);
			RootEle.addContent(HeadEle);
			RootEle.addContent(Detail_ListEle);
			List<Element> LCContLisList=LCContEle.getChildren("LCContLis");
			if(!LCContEle.getChildText("ContNum").equals(String.valueOf(LCContLisList.size()))){
				cLogger.info("������Ӧ��ʵʱ������ϸ������LCContLis�ڵ�������");
				throw new MidplatException("������Ӧ��ʵʱ������ϸ������LCContLis�ڵ�������");
			}
			String sql=null;
			String BAK1=null;
			ExeSQL exeSql=new ExeSQL();
			for (Element LCContLisEle : LCContLisList) {
				Element DetailEle=new Element("Detail");
				
				Element Ins_BillNoEle=new Element("Ins_BillNo");//Ͷ������
				Ins_BillNoEle.setText(LCContLisEle.getChildText("ProposalContNo").substring(0,13));
				Element AcIsAR_StCdEle=new Element("AcIsAR_StCd");//�����պ�Լ״̬����
				AcIsAR_StCdEle.setText(LCContLisEle.getChildText("NrtStatus"));
				Element Uwrt_InfEle=new Element("Uwrt_Inf");//�˱���Ϣ
				Element Tot_InsPrem_AmtEle=new Element("Tot_InsPrem_Amt");//�ܱ��ѽ��
				Tot_InsPrem_AmtEle.setText(NumberUtil.fenToYuan(LCContLisEle.getChildText("TotalMoney")));
				Element Ins_PyF_AmtEle=new Element("Ins_PyF_Amt");//���սɷѽ��
				Ins_PyF_AmtEle.setText(NumberUtil.fenToYuan(LCContLisEle.getChildText("Money")));
				Element PyF_CODtEle=new Element("PyF_CODt");//�ɷѽ�ֹ����
				PyF_CODtEle.setText(LCContLisEle.getChildText("PayDndDate").replaceAll("-",""));
				Element Ins_Yr_Prd_CgyCdEle=new Element("Ins_Yr_Prd_CgyCd");//�����������Ĭ��Ϊ03
				Ins_Yr_Prd_CgyCdEle.setText("03");
				Element Ins_DdlnEle=new Element("Ins_Ddln");
				Ins_DdlnEle.setText("10");//����ձ���������Ĭ��10��
				Element Ins_Cyc_CdEle=new Element("Ins_Cyc_Cd");
				Ins_Cyc_CdEle.setText("03");
			    sql="select BAK1 from TranLog where ProposalPrtNo='"+LCContLisEle.getChildText("ProposalContNo")+"' and funcflag='1060' and rcode='0'";
				BAK1=exeSql.getOneValue(sql);
				cLogger.info(BAK1);
			    String[] strs=BAK1.split("\\|");
				Element InsPrem_PyF_MtdCdEle=new Element("InsPrem_PyF_MtdCd");
				InsPrem_PyF_MtdCdEle.setText(strs[0]);
				Element InsPrem_PyF_Prd_NumEle=new Element("InsPrem_PyF_Prd_Num");//���ѽɷ�����Ĭ��Ϊ1
				InsPrem_PyF_Prd_NumEle.setText(strs[1]);
				Element InsPrem_PyF_Cyc_CdEle=new Element("InsPrem_PyF_Cyc_Cd");
				InsPrem_PyF_Cyc_CdEle.setText(strs[2]);
				Element PmNtc_BillNoEle=new Element("PmNtc_BillNo");
				
				DetailEle.addContent(Ins_BillNoEle);
				DetailEle.addContent(AcIsAR_StCdEle);
				DetailEle.addContent(Uwrt_InfEle);
				DetailEle.addContent(Tot_InsPrem_AmtEle);
				DetailEle.addContent(Ins_PyF_AmtEle);
				DetailEle.addContent(PyF_CODtEle);
				DetailEle.addContent(Ins_Yr_Prd_CgyCdEle);
				DetailEle.addContent(Ins_DdlnEle);
				DetailEle.addContent(Ins_Cyc_CdEle);
				DetailEle.addContent(InsPrem_PyF_MtdCdEle);
				DetailEle.addContent(InsPrem_PyF_Prd_NumEle);
				DetailEle.addContent(InsPrem_PyF_Cyc_CdEle);
				DetailEle.addContent(PmNtc_BillNoEle);
				
				Detail_ListEle.addContent(DetailEle);
			}
			Document outDoc=new Document(RootEle);
			cLogger.info("���ܱ���Ϊ:"+JdomUtil.toStringFmt(outDoc));
			//������Ӧ�ļ���
			File ccbLocalDirFile=new File(ccbLocalDir);
		    StringBuilder FileHeadStr=new StringBuilder(pThisBusiConf.getChild("funcFlag").getAttributeValue("outcode"))
			       .append("_").append(new SimpleDateFormat("yyyyMMdd").format(new Date()))
			       .append("_").append(pThisBusiConf.getParentElement().getChild("bank").getAttributeValue("insu"))
			       .append("_");
			final String fileName1=FileHeadStr.toString();
			//�����ļ������Դ��ڵ��ļ�������һ���ļ����
			File[] files=ccbLocalDirFile.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if(pathname.isFile()&&pathname.getName().startsWith(fileName1)){
						return true;
					}
					return false;
				}
			});
			String fileName=FileHeadStr.toString();
			int fileSum=files.length+1;
			for (int i = 0; i <3-String.valueOf((fileSum)).length(); i++) {
				fileName+="0";
			}
			fileName=fileName+fileSum+".xml";
			cLogger.info("�����ļ����ļ���Ϊ:"+fileName);
			cLogger.info("�����ļ�����·��:"+localDir+fileName);
			FileOutputStream output=new FileOutputStream(localDir+fileName);
			output.write(JdomUtil.toBytes(outDoc,"UTF-8"));
			output.flush();
			output.close();
			//�����ļ�
			cLogger.info("�����ļ����localID:"+localID+",remoteID:"+remoteID
					     +",localDir+fileName:"+localDir+fileName+",ccbLocalDir + fileName:"+ccbLocalDir + fileName);
			SecAPI.fileEnvelop(localID, remoteID,localDir+fileName, ccbLocalDir + fileName);
			cLogger.info("����ǰ�ļ�·��:"+localDir+fileName);
			cLogger.info("���ܺ��ļ�·��:"+ccbLocalDir + fileName);
			//�ں��ķ��ر�����RetData�ڵ������FileName��FilePath�ڵ�
			Element FileNameEle=new Element("FileName");
			FileNameEle.setText(fileName);
			Element FilePathEle=new Element("FilePath");
			FilePathEle.setText(ccbLocalDir.substring(0,ccbLocalDir.length()-1));
			cOutXmlDoc.getRootElement().getChild("RetData").addContent(FileNameEle);
			cOutXmlDoc.getRootElement().getChild("RetData").addContent(FilePathEle);
		} catch (Exception e) {
			e.printStackTrace();
			cLogger.info("��ȡ��ʵʱ�˱�״̬����������Ӧ�ļ�ʧ�ܣ�"+e.getMessage());
			throw new MidplatException("��ȡ��ʵʱ�˱�״̬����������Ӧ�ļ�ʧ�ܣ�");
		}
		cLogger.info("���ɼ�����Ӧ�ļ��ɹ���");
	}
	public Document getSimpleErrorXml(String mFlag,String mDesc){
		Element TranDataEle=new Element("TranData");
		Element RetDataEle=new Element("RetData");
		Element FlagEle=new Element("Flag");
		FlagEle.setText(mFlag);
		Element DescEle=new Element("Desc");
		DescEle.setText(mDesc);
		RetDataEle.addContent(FlagEle);
		RetDataEle.addContent(DescEle);
		TranDataEle.addContent(RetDataEle);
		return new Document(TranDataEle);
	}
    public Document CallWebsvcAtomSvc(Document inSvcDocXml) throws Exception{
    	cLogger.info("Into CallWebsvcAtomSvc.service()...");
        String cServiceId=inSvcDocXml.getRootElement().getChild("Head").getChildText("ServiceId");
    	String mTranCom=inSvcDocXml.getRootElement().getChild("Head").getChildText("TranCom");
    	//Ͷ������
		String mProposalContNo = inSvcDocXml.getRootElement().getChild("LCCont").getChildText("ProposalContNo");
		//��ȡ����WebService��·������������
    	XPath mXPath = XPath.newInstance("/midplat/atomservices/service[@id='"+cServiceId+"']");
		Element serviceEle = (Element) mXPath.selectSingleNode(MidplatConf.newInstance().getConf());
		String mServAddress = serviceEle.getAttributeValue(address);
		String mServMethod = serviceEle.getAttributeValue(method);
		//��׼�������
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName()).append('_').append(NoFactory.nextAppNo()).append('_').append(cServiceId).append("_inSvc.xml");
		SaveMessage.save(inSvcDocXml, mTranCom, mSaveName.toString());
		cLogger.info("������ı�����ϣ�" + mSaveName);
		System.out.println("start call " + serviceEle.getAttributeValue(name) + "(" + mServAddress + "." + mServMethod + ")...");
		cLogger.info("start call " + serviceEle.getAttributeValue(name) + "(" + mServAddress + "." + mServMethod + ")...");
		byte[] mInXmlByte = JdomUtil.toBytes(inSvcDocXml,"GBK");
		long mStartMillis = System.currentTimeMillis();
		// ʹ��RPC��ʽ����WebService
		RPCServiceClient serviceClient = new RPCServiceClient();
		Options options = serviceClient.getOptions();
		// ���ó�ʱʱ��
		options.setTimeOutInMilliSeconds(60000);
		// ָ������WebService��URL
		String servicePath = mServAddress + "?wsdl";
		EndpointReference targetEPR = new EndpointReference(servicePath);
		options.setTo(targetEPR);
		// ָ��sayHelloToPerson�����Ĳ���ֵ
		Object[] opAddEntryArgs = new Object[] { mInXmlByte };
		// ָ��sayHelloToPerson��������ֵ���������͵�Class����
		Class[] classes = new Class[] { byte[].class };
		// Class[] classes = new Class[] { String.class };
		// ָ��Ҫ���õ�sayHelloToPerson������WSDL�ļ��������ռ�
		QName opAddEntry = new QName("http://kernel.ablinkbank.sinosoft.com", mServMethod);
		// ����sayHelloToPerson����������÷����ķ���ֵ
		byte[] mOutStr = (byte[]) serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0];
		cLogger.info("Ͷ������" + mProposalContNo + serviceEle.getAttributeValue(name) + "(" + mServMethod + ")��ʱ��" + (System.currentTimeMillis() - mStartMillis) / 1000.0 + "s");
		Document mOutXmlDoc = JdomUtil.build(mOutStr);
		if (null == mOutXmlDoc)
		{
			throw new MidplatException(serviceEle.getAttributeValue(name) + "���񷵻ؽ���쳣��");
		}

		mSaveName = new StringBuffer(Thread.currentThread().getName()).append('_').append(NoFactory.nextAppNo()).append('_').append(cServiceId).append("_outSvc.xml");
		SaveMessage.save(mOutXmlDoc, mTranCom, mSaveName.toString());
		cLogger.info("������ķ��ر�����ϣ�" + mSaveName);

		cLogger.info("Out CallWebsvcAtomSvc.service()!");
		return mOutXmlDoc;
    }
	@Override
	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException
	{
		cLogger.debug("Into DelayedNewContInput.insertTranLog()...");

		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mBaseInfoEle = mTranDataEle.getChild("BaseInfo");
		Element mLCContEle = mTranDataEle.getChild("LCCont");
		Element mHeadEle = mTranDataEle.getChild("Head");

		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(Thread.currentThread().getName());
		System.out.println("��������" + Thread.currentThread().getName());
		mTranLogDB.setTranCom(mHeadEle.getChildText("TranCom"));
		mTranLogDB.setZoneNo("");
		mTranLogDB.setNodeNo(mBaseInfoEle.getChildText("BankCode"));
		mTranLogDB.setTranNo(mBaseInfoEle.getChildText("TransrNo"));
		mTranLogDB.setOperator(mBaseInfoEle.getChildText("TellerNo"));
		mTranLogDB.setFuncFlag(mBaseInfoEle.getChildText("FunctionFlag"));
		mTranLogDB.setTranDate(mBaseInfoEle.getChildText("BankDate"));
		mTranLogDB.setTranTime(mBaseInfoEle.getChildText("BankTime"));
		mTranLogDB.setInNoDoc(mBaseInfoEle.getChildText("InNoDoc"));
		System.out.println("trancom:" + mTranLogDB.getTranCom());
		System.out.println("FuncFlag:" + mTranLogDB.getFuncFlag());
		System.out.println("mHeadEle.getChildText" + mBaseInfoEle.getChildText("InNoDoc"));
	    mTranLogDB.setProposalPrtNo("");
		mTranLogDB.setContNo("");
		mTranLogDB.setOtherNo("");
		mTranLogDB.setBak2("");
		mTranLogDB.setAppntName("");
		mTranLogDB.setAppntIDNo("");
		mTranLogDB.setRCode(CodeDef.RCode_NULL);
		mTranLogDB.setUsedTime(-1);
		mTranLogDB.setRText("������");
		mTranLogDB.setBak1("");
		Date mCurDate = new Date();
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
		if (!mTranLogDB.insert())
		{
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("������־ʧ�ܣ�");
		}

		cLogger.debug("Out DelayedNewContInput.insertTranLog()!");
		return mTranLogDB;
	}
	public static void main(String[] args) {
//		File file=new File("C:/Users/anico/Desktop/�����ļ���/");
//		File[] fs =file.listFiles(new FileFilter() {
//			@Override
//			public boolean accept(File pathname) {
//				if(pathname.getName().startsWith("1")){
//				    return true;
//				}
//				return false;
//			}
//		});
//		System.out.println("++++++"+fs.length);
//		for (File file2 : fs) {
//			System.out.println("---"+file2.getName()+file2.isFile());
//		}
	}
}
