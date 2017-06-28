/**
 * ��ũ�з�ʵʱ��������ļ�
 */

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
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newabc.format.NonRealTimeContRstOutXsl;
import com.sinosoft.utility.ElementLis;

/**
 * @ClassName: NonRealTimeContRst
 * @Description: ��ʵʱ��������ļ�
 * @author sinosoft
 * @date 2017-2-27 ����8:12:08
 */
public class NonRealTimeContRst extends TimerTask implements XmlTag
{
	//����һ���������־����
	protected final static Logger cLogger = Logger.getLogger(NonRealTimeContRst.class);
	
	/**
	 * �ṩһ��ȫ�ַ��ʵ㣬ֻ��ÿ�ζ��˿�ʼʱ��ʼ���� ȷ���ڸôζ��˴������������������һ���ԣ� ���ܿ���(ǰ��Ĵ�����0��ǰ���������0���)��Ӱ�졣
	 */
	//������Ϣ
	protected String cResultMsg;
	protected static Element cConfigEle;
	private static String cCurDate = "";
	private TranLogDB cTranLogDB;
	//������ˮ��
	private String stranNo = null;
	//��ǰ����
	private String curDate = null;
	
	protected String getCoreNum()
	{
		return cConfigEle.getChildText("ComCode");
	}

	protected void deal(String ttLocalDir)
	{
		cLogger.info("Into NonRealTimeContRst.deal()...");
		// ����������������
		Document cInXmlDoc = sendRequest();
		//����
		JdomUtil.print(cInXmlDoc);
		try
		{
			cTranLogDB = insertTranLog();
			// ������ķ��ر���
			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_NonRealTimeContRst).call(cInXmlDoc);

			cLogger.info("���ķ��صķ�ʵʱ���˽�����ģ�");
			JdomUtil.print(cOutXmlDoc);
			cOutXmlDoc = NonRealTimeContRstOutXsl.newInstance().getCache().transform(cOutXmlDoc);
			cLogger.info("ת����Ķ��˽�����ģ�");
			JdomUtil.print(cOutXmlDoc);
			
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag)))
			{ // ����ʧ��
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			receive(cOutXmlDoc, ttLocalDir);
			
			Element mComCodeEle = cConfigEle.getChild("ComCode");
			String mFileName = "FRESULT" + mComCodeEle.getText() + "." + cCurDate;
			cLogger.info("�������ɵ��ļ���Ϊ��" + mFileName);
			if(!new BatUtils().upLoadFile(ttLocalDir+"/"+mFileName, "02","2008",cCurDate,mFileName)){
				throw new MidplatException(cConfigEle.getChildText(name)+"�ϴ�ʧ�ܣ�");
			}
			
			String reCode = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			String reMsg = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cLogger.info("��ʵʱ��������ļ����˽�����룺" + reCode + "      ���˵����" + reMsg);
			
			if (cTranLogDB != null) {
				cTranLogDB.setRCode(reCode);
				cTranLogDB.setRText(reMsg);
				cTranLogDB.setModifyDate(DateUtil.getCur8Date());
				cTranLogDB.setModifyTime(DateUtil.getCur6Time());
				cTranLogDB.update();
			}
			
			cResultMsg = reMsg;
		}catch (Exception e){
			cResultMsg = e.toString();
			cLogger.error(cConfigEle.getChildTextTrim("name") + "  ���˴����쳣!");
			e.printStackTrace();
			cTranLogDB.setRCode("1");
			cTranLogDB.setRText(e.getMessage());
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
		}
		
		cLogger.info(cConfigEle.getChildText(name)+"�ļ��ϴ��ɹ���");
		cLogger.info("Out NonRealTimeContRst.deal()...");
	}

	/**
	 * ���ö����ļ���������ÿ�������һ�����ɵ��ļ���Ϊ�¸��µ�һ��
	 */
	@SuppressWarnings("deprecation")
	protected String dealFileName() throws Exception
	{

		// ���������ļ����ڣ�һ��ע��ÿ�������һ������ڣ���ũ�и��ܽ�Լ����
		// �������ɵ��˱��̳������ļ��ͷ�ʵʱ��������ļ����ļ���Ϊ���գ�ũ�л��ڴ��մ���
		// curDate=��ǰ����
		// fileDate=�����ļ�����
		String gfileDate = "";
		Calendar c = Calendar.getInstance();
		curDate = String.valueOf(DateUtil.get8Date(c.getTime()));
		System.out.println("�����ǣ�" + curDate);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(new Date());
		final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date lastDate = cDay1.getTime();
		lastDate.setDate(lastDay);
		System.out.println("���һ�죺" + DateUtil.get8Date(lastDate));

		if (String.valueOf(DateUtil.get8Date(lastDate)) == curDate || String.valueOf(DateUtil.get8Date(lastDate)).equals(curDate))
		{
			System.out.println("�����Ǳ������һ��");
			c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
			c.set(Calendar.DAY_OF_MONTH, 1);
			gfileDate = String.valueOf(DateUtil.get8Date(c.getTime()));
			System.out.println("�¸��µĵ�һ��: " + gfileDate);
			System.out.println("������ļ����ڣ�" + gfileDate);
		}
		else
		{
			System.out.println("���첻�Ǳ������һ��");

			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date date = calendar.getTime();
			gfileDate = String.valueOf(DateUtil.date10to8((sdf.format(date))));
			System.out.println("�������ɵ��ļ����ڣ�" + gfileDate);
		}
		return gfileDate;
	}

	/**
	 * 
	 * sendRequest ��֯����������
	 * ����������
	 * @return ��׼������
	 */
	@SuppressWarnings("static-access")
	private Document sendRequest()
	{
		//����NonRealTimeContRst���������ķ���...
		cLogger.info("Into NonRealTimeContRst.sendRequest()...");
		//�½����ڵ�
		ElementLis TranData = new ElementLis("TranData");
		//�½�����ͷ�ڵ���뵽���ڵ�
		ElementLis Head = new ElementLis("Head", TranData);
		//�½��������ڽڵ���뵽����ͷ�ڵ�
		new ElementLis("TranDate", cCurDate, Head);
		//�½�����ʱ��ڵ���뵽����ͷ�ڵ�
		new ElementLis("TranTime", DateUtil.getDateStr(new Date(), "HHmmss"), Head);
		//�½����׻�������ڵ���뵽����ͷ�ڵ�
		new ElementLis("TranCom", "05", Head);
		//�½�ʡ�д���ڵ���뵽����ͷ�ڵ�
		new ElementLis("ZoneNo", "11", Head);
		//�½���������ڵ���뵽����ͷ�ڵ�
		new ElementLis("NodeNo", cConfigEle.getChildText("NodeNo"), Head);
		//�½����д���ڵ���뵽����ͷ�ڵ�
		new ElementLis("BankCode", "0102", Head);
		//�½���Ա����ڵ���뵽����ͷ�ڵ�
		new ElementLis("TellerNo", "11010102110", Head);
		//��������
		PubFun1 p = new PubFun1();
		//��ȡ����16λ����Ϊ������ˮ��
		stranNo = p.CreateMaxNo("TransNo", 16);
		//�½�������ˮ�Žڵ���뵽����ͷ�ڵ�
		new ElementLis("TranNo", stranNo, Head);
		//�½�������ڵ���뵽����ͷ�ڵ�
		new ElementLis("FuncFlag", cConfigEle.getChildText("funcFlag"), Head);// ���״���
		//�½�������ڵ���뵽���ڵ�
		new ElementLis("Body", TranData);
		//�½�����
		Document pXmlDoc = new Document(TranData);
		//��NonRealTimeContRst���������ķ�������+��������
		cLogger.info("Out NonRealTimeContRst.sendRequest()!" + cCurDate);
		return pXmlDoc;
	}

	protected TranLogDB insertTranLog() throws MidplatException {
		cLogger.debug("Into NonRealTimeContRst.insertTranLog()...");
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom("05");
		mTranLogDB.setNodeNo("0000000");
		mTranLogDB.setFuncFlag("2008");
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
		cLogger.debug("Out NonRealTimeContRst.insertTranLog()!");
		return mTranLogDB;
	}
	
	// ������ķ��ر��Ĳ��洢�ڹ̶�·��
	private void receive(Document cOutXmlDoc, String ttLocalDir) throws Exception
	{
		cLogger.info("Into NonRealTimeContRst.receive()..." + cCurDate);
		JdomUtil.print(cOutXmlDoc);

		String outHead = null;// ͷ��¼
		@SuppressWarnings("unused")
		String serialNo = "";// ���
		String fileName = "";// �ļ�����
		String out = "";
		String outBody = "";
		int maxNo = 100;// һ��txt������������ϸ��¼�� ���ԵĻ���ֻ��Ҫ�����ֵ��������
		// ͷ��¼����
		Element tRoot = cOutXmlDoc.getRootElement();
		Element tHead = tRoot.getChild("Head");
		System.out.println(tRoot + "           ===========      " + tHead);
		String tComCode = cConfigEle.getChildText("ComCode");// ���չ�˾����
		String tCount = null;// ��ϸ��¼�ܱ���
		String tSumMoney = null;// �ܽ��
		Element tBody = tRoot.getChild("Body");
		// try {

		@SuppressWarnings("unchecked")
		List<Element> list = tBody.getChildren("Detail");

		tCount = Integer.toString(list.size());
		tSumMoney = "0000";
		long sum = 0;

		cLogger.info("���ɷ����ļ�ͷ��¼" + outHead);
		cLogger.info("���ɷ����ļ��ܼ�¼" + list.size());

		/*
		 * �ļ���һ�У���������Ϣ�� ��ʽ�����չ�˾����|���д���|�ܼ�¼��|�ܽ��| �ļ��������ݣ�����ϸ��¼��
		 * ��������|��������˳���|Ͷ��������
		 * |Ͷ����֤������|Ͷ����֤������|���ֱ���|��Ʒ����|������|��������|Ͷ�����뱻���˹�ϵ|����������|
		 * ������֤������|������֤������|����|����|�ɷ��˻�|�ɷѷ�ʽ|�ɷ�����|����������|Ͷ������|���Ի�����|����ӡˢ��|�����ո���|
		 */
		if (list.size() != 0)
		{
			/*
			 * ͨ�������������ȡ�����������ļ�������
			 */
			for (int i = 0; i <= list.size() / maxNo; i++)
			{
				if (i < 10)
				{
					serialNo = "0" + Integer.toString(i);

					fileName = "FRESULT" + tComCode + "." + cCurDate;
				}
				else
				{
					serialNo = Integer.toString(i);
					// fileName =
					// "FRESULT"+tComCode+"."+String.valueOf((DateUtil.get8Date(System.currentTimeMillis())+1));
					fileName = "FRESULT" + tComCode + "." + cCurDate;
				}
				File file = new File(ttLocalDir + "/" + fileName);
				// ����ļ������ڴ����ļ�
				if (!file.exists())
				{
					try
					{
						file.createNewFile();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				/*
				 * ��ѭ�������i���ļ�������(i+1)*maxNo - i*maxNo����ϸ��¼
				 * ��i���ļ���ȡ����ϸ��¼�Ǵӵ�i*maxNo������(i+1)*maxNo��, ��ȡ��ֵ��������ͬʱ����������ѭ��
				 */
				for (int j = i * maxNo; j < (i + 1) * maxNo; j++)
				{
					if (j == list.size())
					{
						break;
					}
					Element tDetail = list.get(j);
					String tTranDate = tDetail.getChildText("TranDate");
					tTranDate = DateUtil.date10to8(tTranDate);
					String tApplyNo = tDetail.getChildText("ApplyNo");
					String tAppName = tDetail.getChild("Appnt").getChildText("AppntName");
					String tAppIDtype = tDetail.getChild("Appnt").getChildText("IDType");
					String tAppIDno = tDetail.getChild("Appnt").getChildText("IDNo");
					String tRiskCode = tDetail.getChildText("RiskCode");
					String tProdCode = tDetail.getChildText("ProdCode");
					String tContNo = tDetail.getChildText("ContNo");
					String tApplyDate = tDetail.getChildText("ApplyDate");
					tApplyDate = DateUtil.date10to8(tApplyDate);
					String tRelationToInusre = tDetail.getChildText("RelationToInusre");
					String tInsuName = tDetail.getChild("Insured").getChildText("Name");
					String tInsuDtype = tDetail.getChild("Insured").getChildText("IDType");
					String tInsuDno = tDetail.getChild("Insured").getChildText("IDNo");
					String tPrem = tDetail.getChildText("Prem");
					// ������
					long prem = Long.parseLong(tPrem);
					sum = sum + prem;
					tPrem = NumberUtil.fenToYuan(tPrem);
					String tAmnt = tDetail.getChildText("Amnt");
					tAmnt = NumberUtil.fenToYuan(tAmnt);
					String tAccNo = tDetail.getChildText("AccNo");
					String tPayIntv = tDetail.getChildText("PayIntv");
					String tPayEndDate = tDetail.getChildText("PayEndDate");
					tPayEndDate = DateUtil.date10to8(tPayEndDate);
					String tContEndDate = tDetail.getChildText("ContEndDate");
					tContEndDate = DateUtil.date10to8(tContEndDate);
					String tMult = tDetail.getChildText("Mult");
					double dmult = Double.parseDouble(tMult);
					int imult = (int) dmult;
					tMult = String.valueOf(imult);
					String tContPrtNo = tDetail.getChildText("ContPrtNo");
					if(tContPrtNo.length()>13){
						tContPrtNo = tContPrtNo.substring(0, 13);
					}else{
						tContPrtNo="";
					}
					String tState = tDetail.getChildText("State");
					String tRtext = tDetail.getChildText("Rtext");
					String tSpecialRate = tDetail.getChildText("SpecialRate");
					String tEtraRiskCnt = tDetail.getChildText("EtraRiskCnt");

					outBody += tTranDate + "|" + tApplyNo + "|" + tAppName + "|" + tAppIDtype + "|" + tAppIDno + "|" + tRiskCode + "|" + tProdCode + "|" + tContNo + "|" + tApplyDate + "|" + tRelationToInusre + "|" + tInsuName + "|" + tInsuDtype + "|" + tInsuDno + "|" + tPrem + "|" + tAmnt + "|" + tAccNo + "|" + tPayIntv + "|" + tPayEndDate + "|" + tContEndDate + "|" + tMult + "|" + tSpecialRate + "|" + tContPrtNo + "|"  + tState + "|"  + tRtext + "|" + tEtraRiskCnt + "|" + "\n";
				}
				// �����¼��txt�ļ�
				tSumMoney = String.valueOf(sum);
				tSumMoney = NumberUtil.fenToYuan(tSumMoney);
				System.out.println("�ܽ�" + tSumMoney);
				if (tSumMoney.equals("") || tSumMoney == null)
				{
					tSumMoney = "0.00";
				}
				outHead = tComCode + "|" + "03" + "|" + tCount + "|" + tSumMoney + "|" + "\n";
				out = outHead + outBody;
				cLogger.info("��" + (i+1) + "���ļ����ɷ����ļ��ܼ�¼" + out);
				byte[] m = out.getBytes("GBK");
				try
				{
					FileOutputStream fos = new FileOutputStream(file);
					fos.write(m);
					fos.flush();
					fos.close();
				}
				catch (FileNotFoundException e1)
				{
					e1.printStackTrace();
					cLogger.error("�Ҳ����ļ�");
				}
				catch (IOException e)
				{
					e.printStackTrace();
					cLogger.error("I/O�쳣");
				}
				// ɾ���ļ����������ɶ����ļ�
				if (i * maxNo == list.size())
				{
					file.delete();
				}
				// �����ϸ��¼���ܼ�¼
				outBody = "";
				out = "";
			}
		}
		else
		{
			// fileName =
			// "FRESULT"+tComCode+"."+String.valueOf((DateUtil.get8Date(System.currentTimeMillis())+1));
			fileName = "FRESULT" + tComCode + "." + cCurDate;
			File file = new File(ttLocalDir + "/" + fileName);
			// ����ļ������ڴ����ļ�
			if (!file.exists())
			{
				try
				{
					file.createNewFile();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			// �����¼��txt�ļ�
			tCount = "0";
			tSumMoney = "0.00";
			outHead = tComCode + "|" + "03" + "|" + tCount + "|" + tSumMoney + "|" + "\n";
			out = outHead;
			cLogger.info("ֻ����ͷ�ļ�������δ�����˱�ֵ" + out);
			byte[] m = out.getBytes("GBK");
			try
			{
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(m);
				fos.flush();
				fos.close();
			}
			catch (FileNotFoundException e1)
			{
				e1.printStackTrace();
				cLogger.error("�Ҳ����ļ�");
			}
			catch (IOException e)
			{
				e.printStackTrace();
				cLogger.error("I/O�쳣");
			}
		}

		System.out.println("�ļ�����" + fileName);
		cLogger.info("Out NonRealTimeContRst.receive()!");
	}

	@Override
	public void run()
	{
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into NonRealTimeContRst.run()...");

		// �����һ�ν����Ϣ
		cResultMsg = null;

		try
		{
			cConfigEle = BatUtils.getConfigEle("2008"); // �õ�bat.xml�ļ��еĶ�Ӧ�ڵ�.
			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			}
			cLogger.info(cConfigEle.getContent());
			
			try
			{
				String ttLocalDir = cConfigEle.getChildTextTrim("FilePath");

				deal(ttLocalDir);

			}
			catch (Exception ex)
			{
				cLogger.error("���ɱ�׼���˱��ĳ���", ex);

				// ��ȡ��׼���˱���
				Element ttError = new Element(Error);
				String ttErrorStr = ex.getMessage();
				if ("".equals(ttErrorStr))
				{
					ttErrorStr = ex.toString();
				}
				ttError.setText(ttErrorStr);
				Element tTranData = new Element(TranData);
				Element tHeadEle = ((Document) sendRequest().clone()).getRootElement().getChild("Head");
				tTranData.addContent(tHeadEle);
				tTranData.addContent(ttError);
			}
			
			// ÿ��1�ձ������µĶ����ļ�
			if ("01".equals(DateUtil.getDateStr(new Date(), "dd"))){
				bakFiles(cConfigEle.getChildTextTrim("FilePath"));
			}
		}
		catch (Throwable ex)
		{
			cResultMsg = ex.toString();
			cLogger.error("���׳���", ex);
		}

		cCurDate = null; // ÿ�����꣬�������

		cLogger.info("Out NonRealTimeContRst.run()!");
	}

	// �������ļ�������yyyyMM01���գ�ϵͳ����Ŀ¼localDir ����/yyyy/yyyyMM��
	// Ȼ��������ļ��ƶ�����Ŀ¼������yyyyMM01���ļ�����
	private void bakFiles(String pFileDir)
	{
		cLogger.info("Into NonRealTimeContRst.bakFiles()...");

		if (null == pFileDir || "".equals(pFileDir))
		{
			cLogger.warn("�����ļ�Ŀ¼Ϊ�գ������б��ݲ�����");
			return;
		}
		File mDirFile = new File(pFileDir);
		if (!mDirFile.exists() || !mDirFile.isDirectory())
		{
			cLogger.warn("�����ļ�Ŀ¼�����ڣ������б��ݲ�����" + mDirFile);
			return;
		}

		File[] mOldFiles = mDirFile.listFiles(new FileFilter()
		{
			public boolean accept(File pFile)
			{
				if (!pFile.isFile())
				{
					return false;
				}

				Calendar tCurCalendar = Calendar.getInstance();
				tCurCalendar.setTime(new Date());

				Calendar tFileCalendar = Calendar.getInstance();
				tFileCalendar.setTime(new Date(pFile.lastModified()));

				return tFileCalendar.before(tCurCalendar);
			}
		});

		Calendar mCalendar = Calendar.getInstance();
		mCalendar.add(Calendar.MONTH, -1);
		File mNewDir = new File(mDirFile, DateUtil.getDateStr(mCalendar, "yyyy/yyyyMM"));
		for (File tFile : mOldFiles)
		{
			try
			{
				String fileName_date = tFile.getName().substring(tFile.getName().length() - 8);
				Date date=new Date();
				if (!fileName_date.equals(String.valueOf(DateUtil.get8Date(date))))
				{
					cLogger.info(tFile.getAbsoluteFile() + " start move...");
					IOTrans.fileMove(tFile, mNewDir);
					cLogger.info(tFile.getAbsoluteFile() + " end move!");
				}
			}
			catch (IOException ex)
			{
				cLogger.error(tFile.getAbsoluteFile() + "����ʧ�ܣ�", ex);
			}
		}

		cLogger.info("Out NonRealTimeContRst.bakFiles()!");
	}
	
	public final void setDate(String p8DateStr){
		cCurDate = p8DateStr; 
	}
	
	public String getResultMsg() {
		return this.cResultMsg;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.NonRealTimeContRst.main");
		cLogger.info("��ʼ��ʵʱ��������ļ����˳���...");
		
		NonRealTimeContRst abcAES = new NonRealTimeContRst();

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
		
		cLogger.info("������ʵʱ��������ļ����˳���!");
	}
	
}
