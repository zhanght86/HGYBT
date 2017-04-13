package com.sinosoft.midplat.newabc.bat;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newabc.format.NonRealTimeContRstDetailOutXsl;
import com.sinosoft.utility.ElementLis;

/**
 * @ClassName: NonRealTimeContRstDetail
 * @Description: ��ʵʱ���������ϸ�ļ�
 * @author sinosoft
 * @date 2017-3-27 ����2:38:04
 */
public class NonRealTimeContRstDetail extends TimerTask implements XmlTag {

	protected final static Logger cLogger=Logger.getLogger(NonRealTimeContRstDetail.class);
	protected Date cTranDate;
	protected String cResultMsg;
	protected static Element cConfigEle;
	private static String cCurDate = "";
	private TranLogDB cTranLogDB;
	private String sTranNo=null;
	@SuppressWarnings("unused")
	private String curDate=null;
	private String fileDate=null;
	
	@SuppressWarnings("static-access")
	private Document sendRequest() {
		cLogger.info("Into NonRealTimeContRstDetail.sendRequest()...");
		ElementLis tTranData=new ElementLis(TranData);
		ElementLis tHead=new ElementLis(Head,tTranData);
		String sTranTime=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		new ElementLis(TranDate,DateUtil.getDateStr(cTranDate, "yyyyMMdd"),tHead);
		new ElementLis(TranTime,DateUtil.getDateStr(cTranDate, "HHmmss"),tHead);
		new ElementLis(TranCom,"05",tHead);
		new ElementLis("BankCode","0102",tHead);
		new ElementLis(ZoneNo,"11",tHead);
		new ElementLis(NodeNo,cConfigEle.getChildText(NodeNo),tHead);
		new ElementLis(TellerNo,"11010102110",tHead);
		PubFun1 p=new PubFun1();
		sTranNo=p.CreateMaxNo("TransNo", 16);
		new ElementLis(TranNo,sTranNo,tHead);
		new ElementLis(FuncFlag,cConfigEle.getChildText(funcFlag),tHead);
		ElementLis tBody=new ElementLis(Body,tTranData);
		new ElementLis(TranDate,sTranTime.substring(0,8),tBody);
		Document pXmlDoc=new Document(tTranData);
		cLogger.info("Out NonRealTimeContRstDetail.sendRequest()!");
		return pXmlDoc;
	}
	
	protected TranLogDB insertTranLog() throws MidplatException {
		cLogger.debug("Into NonRealTimeContRstDetail.insertTranLog()...");
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom("05");
		mTranLogDB.setNodeNo("0000000");
		mTranLogDB.setFuncFlag("2005");
		mTranLogDB.setOperator("YBT");
		mTranLogDB.setTranNo(NoFactory.nextTranLogNo() + "");
		mTranLogDB.setTranDate(DateUtil.getCur8Date());
		mTranLogDB.setTranTime(DateUtil.getCur6Time());
		Date mCurDate = new Date();
		mTranLogDB.setTranTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setRCode(0);
		mTranLogDB.setUsedTime(0);
		mTranLogDB.setBak1("");
		mTranLogDB.setRCode("-1");
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
		if (!(mTranLogDB.insert())) {
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("������־ʧ�ܣ�");
		}
		cLogger.debug("Out NonRealTimeContRstDetail.insertTranLog()!");
		return mTranLogDB;
	}
	
	@Override
	public void run() {
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into NonRealTimeContRstDetail.run()...");
		cResultMsg=null;
		try {
			cConfigEle = BatUtils.getConfigEle("2005"); // �õ�bat.xml�ļ��еĶ�Ӧ�ڵ�.
			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			}
			System.out.println(cConfigEle.getContent());
			String nextDate = cConfigEle.getChildText("nextDate");
			if(null==cTranDate)
				if((null!=nextDate)&&("Y".equals(nextDate))){
					cTranDate=new Date();
					cTranDate=new Date(cTranDate.getTime()-1000*3600*24);
				}else{
					cTranDate=new Date();
				}
			try {
				String ttLocalDir=cConfigEle.getChildTextTrim("FilePath");
				deal(ttLocalDir);
			} catch (Exception e) {
				cLogger.error("���ɱ�׼���˱��ĳ���!",e);
				Element tError=new Element(Error);
				String tErrorStr=e.getMessage();
				if("".equals(tErrorStr))
					tErrorStr=e.toString();
				tError.setText(tErrorStr);
				Element tTranData = new Element(TranData);
				Element tHeadEle = ((Document) sendRequest().clone()).getRootElement().getChild("Head");
				tTranData.addContent(tHeadEle);
				tTranData.addContent(tErrorStr);
			}
			
			if("01".equals(DateUtil.getDateStr(cTranDate, "dd"))){
				bakFiles(cConfigEle.getChildTextTrim("FilePath"));
			}
		} catch (Throwable e) {
			cLogger.error("���׳���!",e);
			cResultMsg=e.toString();
		}
		cTranDate=null;
		cLogger.info("Out NonRealTimeContRstDetail.run()!");
	}

	protected void deal(String ttLocalDir) {
		cLogger.info("Into NonRealTimeContRstDetail.deal()...");
		Document cInXmlDoc=sendRequest();
		JdomUtil.print(cInXmlDoc);
		try {
			cTranLogDB = insertTranLog();
			Document cOutXmlDoc=new CallWebsvcAtomSvc(AblifeCodeDef.SID_NonRealTimeContRstDtl).call(cInXmlDoc);
			cLogger.info("���ķ��صķ�ʵʱ���˽����ϸ����:");
			JdomUtil.print(cOutXmlDoc);
			cOutXmlDoc=NonRealTimeContRstDetailOutXsl.newInstance().getCache().transform(cOutXmlDoc);
			cLogger.info("ת����Ķ��˽����ϸ����:");
			JdomUtil.print(cOutXmlDoc);
			
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag)))
			{ // ����ʧ��
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			receive(cOutXmlDoc,ttLocalDir);
			
			Element mComCodeEle=cConfigEle.getChild("ComCode");
			String mFileName="FRESULTKZ"+mComCodeEle.getText()+"."+fileDate;
			cLogger.info("�������ɵ��ļ���Ϊ��" + mFileName);
			if(!new BatUtils().upLoadFile(ttLocalDir+"/"+mFileName, "02","2005",new SimpleDateFormat("yyyyMMdd").format(new Date()),mFileName)){
				throw new MidplatException(cConfigEle.getChildText(name)+"�ϴ�ʧ�ܣ�");
			}
			
			String reCode = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			String reMsg = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cLogger.info("��ʵʱ���������ϸ�ļ����˽�����룺" + reCode + "      ���˵����" + reMsg);
			if (cTranLogDB != null) {
				cTranLogDB.setRCode(reCode);
				cTranLogDB.setRText(reMsg);
				cTranLogDB.setModifyDate(DateUtil.getCur8Date());
				cTranLogDB.setModifyTime(DateUtil.getCur6Time());
				cTranLogDB.update();
			}
			cLogger.info(cConfigEle.getChildText(name)+"�ļ��ϴ��ɹ���");
		}  catch (Exception e) {
			cLogger.error(cConfigEle.getChildTextTrim("name") + "  ���˴����쳣!");
			e.printStackTrace();
			cTranLogDB.setRCode("1");
			cTranLogDB.setRText("��ʵʱ���������ϸ�ļ�����ʧ��");
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
		}
		cLogger.info("Out NonRealTimeContRstDetail.deal()!");
	}
	
	private void receive(Document cOutXmlDoc, String ttLocalDir) throws Exception{
		cLogger.info("Into NonRealTimeContRstDetail.receive()..."+cTranDate);
		JdomUtil.print(cOutXmlDoc);
		fileDate=String.valueOf(DateUtil.get8Date(cTranDate))	;
		String sOutHead=null;
		@SuppressWarnings("unused")
		String sSerialNo="";
		String sFileName="";
		String sOut="";
		String sOutBody="";
		int maxNo=100;
		Element tRoot=cOutXmlDoc.getRootElement();
		Element tHead=tRoot.getChild(Head);
		System.out.println(tRoot+"           ===========      "+tHead);
		String tComCode=cConfigEle.getChildText("ComCode");// ���չ�˾����
		String tCount=null;
		String tSumMoney=null;
		Element tBody=tRoot.getChild(Body);
		@SuppressWarnings("unchecked")
		List<Element> list=tBody.getChildren("Detail");
		tCount=Integer.toString(list.size());
		tSumMoney="0000";
		long sum=0;
		cLogger.info("���ɷ����ļ�ͷ��¼:"+sOutHead);
		cLogger.info("���ɷ����ļ��ܼ�¼:"+list.size());
		/*
		 *�ļ���һ�У���������Ϣ����ʽ�����չ�˾����|���д���|�ܼ�¼��|�ܽ��|
		 *�ļ��������ݣ�����ϸ��¼�����ִ���|������|�ɷѷ�ʽ|�ɷ��ڼ�����|�ɷ��ڼ�|�����ڼ�����|
		 *�����ڼ�|����|����|
		 */
		if(list.size()!=0){
			for (int i = 0; i <= list.size()/maxNo; i++) {
				if(i<10){
					sSerialNo="0"+Integer.toString(i);
					sFileName="FRESULTKZ"+tComCode+"."+fileDate;
				}else{
					sSerialNo=Integer.toString(i);
					sFileName="FRESULTKZ"+tComCode+"."+fileDate;
				}
				File file=new File(ttLocalDir+"/"+sFileName);
				if(!file.exists()){
					try {
						file.createNewFile();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for (int j = i*maxNo; j < (i+1)*maxNo; j++) {
					if(j==list.size()){
						break;
					}
					Element tDetail=list.get(j);
					String tRiskCode=tDetail.getChildText(RiskCode);
					String tContNo=tDetail.getChildText(ContNo);
					String tPayIntv=tDetail.getChildText(PayIntv);
					String tPayEndYearFlag=tDetail.getChildText(PayEndYearFlag);
					String tPayEndYear=tDetail.getChildText(PayEndYear);
					String tInsuYearFlag=tDetail.getChildText(InsuYearFlag);
					String tInsuYear=tDetail.getChildText(InsuYear);
					String tPrem=tDetail.getChildText(Prem);
					long prem=Long.parseLong(tPrem);
					sum+=prem;
					tPrem=NumberUtil.fenToYuan(tPrem);
					String tAmnt=tDetail.getChildText(Amnt);
					tAmnt=NumberUtil.fenToYuan(tAmnt);
					sOutBody+=tRiskCode+"|"+tContNo+"|"+tPayIntv+"|"+tPayEndYearFlag+"|"+tPayEndYear+"|"+tInsuYearFlag+"|"+tInsuYear+"|"+tPrem+"|"+tAmnt+"|"+"\n";
				}
				tSumMoney=String.valueOf(sum);
				tSumMoney=NumberUtil.fenToYuan(tSumMoney);
				System.out.println("�ܽ��:"+tSumMoney);
				if("".equals(tSumMoney)||tSumMoney==null)
					tSumMoney="0.00";
				sOutHead=tComCode+"|"+"03"+"|"+tCount+"|"+tSumMoney+"|"+"\n";
				sOut=sOutHead+sOutBody;
				cLogger.info("��"+i+"���ļ����ɷ����ļ��ܼ�¼:"+sOut);
				byte[] m=sOut.getBytes("GBK");
				try {
					FileOutputStream fos=new FileOutputStream(file);
					fos.write(m);
					fos.flush();
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					cLogger.error("�Ҳ����ļ�!");
				}catch (IOException e) {
					e.printStackTrace();
					cLogger.error("IO�쳣!");
				}
				if(i*maxNo==list.size())
					file.delete();
				sOutBody="";
				sOut="";
			}
		}else{	
			sFileName="FRESULTKZ"+tComCode+"."+fileDate;
			File file=new File(ttLocalDir+"/"+sFileName);
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			tCount="0";
			tSumMoney="0.00";
			sOutHead=tComCode+"|"+"03"+"|"+tCount+"|"+tSumMoney+"|"+"\n";
			sOut=sOutHead;
			cLogger.info("ֻ�����ļ�ͷ������δ���ر���������Ϣ!"+sOut);
			byte[] m=sOut.getBytes("GBK");
			try {
				FileOutputStream fos=new FileOutputStream(file);
				fos.write(m);
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				cLogger.error("�Ҳ����ļ�");
			}catch (IOException e) {
				e.printStackTrace();
				cLogger.error("IO�쳣");
			}
		}
		System.out.println("�ļ���:"+sFileName);
		cLogger.info("Out NonRealTimeContRstDetail.receive()!");
	}

	private void bakFiles(String pFileDir) {
		cLogger.info("Into NonRealTimeContRstDetail.bakFiles()...");
		if(null==pFileDir||"".equals(pFileDir)){
			cLogger.warn("�����ļ�Ŀ¼Ϊ�գ������б��ݲ���!");
			return;
		}
		File mDirFile=new File(pFileDir);
		if(!mDirFile.exists()||!mDirFile.isDirectory()){
			cLogger.info("�����ļ�Ŀ¼�����ڣ������б��ݲ���!"+mDirFile);
			return;
		}
		File[] mOldFiles=mDirFile.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pFile) {
				if(!pFile.isFile())
					return false;
				Calendar cCurCalendar=Calendar.getInstance();
				cCurCalendar.setTime(cTranDate);
				Calendar cFileCalendar=Calendar.getInstance();
				cFileCalendar.setTime(new Date(pFile.lastModified()));
				return cFileCalendar.before(cCurCalendar);
			}
		});
		Calendar mCalendar=Calendar.getInstance();
		mCalendar.add(Calendar.MONTH, -1);
		File mNewDir=new File(mDirFile,DateUtil.getDateStr(mCalendar, "yyyy/yyyyMM"));
		for (File tFile : mOldFiles) {
			try {
				String fileName_date=tFile.getName().substring(tFile.getName().length());
				Date date=new Date();
				if(!fileName_date.equals(String.valueOf(DateUtil.get8Date(date)))){
					cLogger.info(tFile.getAbsoluteFile()+" start move...");
					IOTrans.fileMove(tFile, mNewDir);
					cLogger.info(tFile.getAbsoluteFile()+" end move!");
				}
			} catch (IOException e) {
				cLogger.error(tFile.getAbsoluteFile()+"����ʧ��!",e);
			}
		}
		cLogger.info("Out NonRealTimeContRstDetail.bakFiles()!");
	}
	
	public static void main(String[] args) throws Exception{
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.NonRealTimeContRstDetail.main");
		cLogger.info("��ʼ��ʵʱ���������ϸ�ļ����˳���...");	//û�����ã�δ��ӡ
		
		NonRealTimeContRstDetail abcAES=new NonRealTimeContRstDetail();	//û������
		
		// ���ڲ����ˣ����ò���������
		if (0 != args.length) {
			mLogger.info("args[0] = " + args[0]);

			/**
			 * �ϸ�����У���������ʽ��\\d{4}-((0\\d)|(1[012]))-(([012]\\d)|(3[01]))��
			 * 4λ��-2λ��-2λ�ա� 4λ�꣺4λ[0-9]�����֡�
			 * 1��2λ�£�������Ϊ0��[0-9]�����֣�˫���±�����1��ͷ��β��Ϊ0��1��2������֮һ��
			 * 1��2λ�գ���0��1��2��ͷ��[0-9]�����֣�������3��ͷ��0��1��
			 * 
			 * ������У���������ʽ��\\d{4}-\\d{2}-\\d{2}��
			 */
			if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))")) {
				cCurDate = args[0];
			} else {
				throw new MidplatException("���ڸ�ʽ����ӦΪyyyy-MM-dd��" + args[0]);
			}
		}
		abcAES.run();
		
		cLogger.info("������ʵʱ���������ϸ�ļ����˳���!");	//û�����ã�δ��ӡ
	}
	
}
