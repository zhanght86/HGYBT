/**
 * ��ũ���˱��̳�����ļ�
 */

package com.sinosoft.midplat.newabc.bat;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.axis.utils.StringUtils;
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
import com.sinosoft.midplat.newabc.format.NoTakenBalanceRstOutXsl;
import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class NoTakenBalanceRst extends TimerTask implements XmlTag
{
	protected final static Logger cLogger = Logger.getLogger(NoTakenBalanceRst.class);
	
	protected String cResultMsg;
	protected static Element cConfigEle;
	private static String cCurDate = "";
	private TranLogDB cTranLogDB;
	private String sTranNo = null;
	private String curDate = null;
	
	protected String getCoreNum()
	{
		return cConfigEle.getChildText("ComCode");
	}
	
	protected Element getBody()
	{
		cLogger.info("Into NoTakenBalanceRst.getBody()...");

		Element mBody = new Element(Body);
		try {
			// contblcdtl������������л����ļ���ʧ�ܵ�
			String rTranDate = String.valueOf(DateUtil.get8Date(new SimpleDateFormat("yyyyMMdd").parse(cCurDate).getTime() - 86400000L));
			String mSqlStr10 = "select * from ContBlcDtl where trancom='05' " + " and trandate='" + rTranDate + "'" + " and type='24'" + " and bak5='1'";
			SSRS ssrs = new ExeSQL().execSQL(mSqlStr10);
			for (int i = 0; i < ssrs.getMaxRow(); i++)
			{
				ElementLis Detail = new ElementLis("Detail", mBody);
				new ElementLis("BusiType", ssrs.GetText(i + 1, 18), Detail);
				new ElementLis("ContNo", ssrs.GetText(i + 1, 3), Detail);
				new ElementLis("AppntName", ssrs.GetText(i + 1, 12), Detail);
				new ElementLis("Status", ssrs.GetText(i + 1, 18), Detail);
				new ElementLis("TranDate", ssrs.GetText(i + 1, 5), Detail);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		cLogger.info("Out NoTakenBalanceRst.getBody()!");
		return mBody;
	}

	protected void deal(String ttLocalDir)
	{
		cLogger.info("Into NoTakenBalanceRst.deal()...");
		// ����������������
		Document cInXmlDoc = sendRequest();
		try
		{
			cTranLogDB = insertTranLog();
			// ������ķ��ر���
			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_NoTakenBalanceRst).call(cInXmlDoc);

			// ʹ����ʽ��Թ�ϵ���룬֤�����ͣ����ִ��룬�ɷѷ�ʽת����ũ�еģ����ı���ı�׼���Ľṹ
			cLogger.info("���ķ��صķ�ʵʱ���˽�����ģ�");
			JdomUtil.print(cOutXmlDoc);
			cOutXmlDoc = NoTakenBalanceRstOutXsl.newInstance().getCache().transform(cOutXmlDoc);
			cLogger.info("ת����Ķ��˽�����ģ�");
			JdomUtil.print(cOutXmlDoc);
			
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag)))
			{ // ����ʧ��
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}

			receive(cOutXmlDoc, ttLocalDir);
			
			Element mComCodeEle = cConfigEle.getChild("ComCode");
			String mFileName = "INVALID" + mComCodeEle.getText() + "." + cCurDate;
			cLogger.info("�������ɵ��ļ���Ϊ��" + mFileName);
			if(!new BatUtils().upLoadFile(ttLocalDir+"/"+mFileName, "02", "2004",cCurDate,mFileName)){
				throw new MidplatException(cConfigEle.getChildText(name)+"�ϴ�ʧ�ܣ�");
			}
			
			String reCode = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Flag");
			String reMsg = cOutXmlDoc.getRootElement().getChild("Head").getChildText("Desc");
			cLogger.info("�˱��̳������ļ����˽�����룺" + reCode + "      ���˵����" + reMsg);
			
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
			cTranLogDB.setRText("�˱��̳������ļ�����ʧ��");
			cTranLogDB.setModifyDate(DateUtil.getCur8Date());
			cTranLogDB.setModifyTime(DateUtil.getCur6Time());
			cTranLogDB.update();
		}
		
		cLogger.info(cConfigEle.getChildText(name)+"�ļ��ϴ��ɹ���");
		cLogger.info("Out NoTakenBalanceRst.deal()!");
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
	
	// ����������
	@SuppressWarnings("static-access")
	private Document sendRequest()
	{
		cLogger.info("Into NoTakenBalanceRst.sendRequest()...");
		ElementLis TranData = new ElementLis("TranData");
		ElementLis Head = new ElementLis("Head", TranData);
		new ElementLis("TranDate",cCurDate, Head);
		new ElementLis("TranTime", DateUtil.getDateStr(new Date(), "HHmmss"), Head);
		new ElementLis("TranCom", "05", Head);
		new ElementLis("ZoneNo", "11", Head);
		new ElementLis("NodeNo", cConfigEle.getChildText("NodeNo"), Head);
		new ElementLis("BankCode", "0102", Head);
		new ElementLis("TellerNo", "11010102110", Head);
		PubFun1 p = new PubFun1();
		sTranNo = p.CreateMaxNo("TransNo", 16);
		new ElementLis("TranNo", sTranNo, Head);
		new ElementLis("FuncFlag", cConfigEle.getChildText("funcFlag"), Head);// ���״���
		Element Body = getBody();
		TranData.addContent(Body);
		Document pXmlDoc = new Document(TranData);
		cLogger.info("Out NoTakenBalanceRst.sendRequest()!" + cCurDate);
		return pXmlDoc;
	}

	protected TranLogDB insertTranLog() throws MidplatException {
		cLogger.debug("Into NoTakenBalanceRst.insertTranLog()...");
		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(NoFactory.nextTranLogNo());
		mTranLogDB.setTranCom("05");
		mTranLogDB.setNodeNo("0000000");
		mTranLogDB.setFuncFlag("2004");
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
		cLogger.debug("Out NoTakenBalanceRst.insertTranLog()!");
		return mTranLogDB;
	}
	
	// ������ķ��ر��Ĳ��洢�ڹ̶�·��
	private void receive(Document cOutXmlDoc, String ttLocalDir) throws Exception
	{
		cLogger.info("Into NoTakenBalanceRst.receive()..." + cCurDate);
		JdomUtil.print(cOutXmlDoc);

		// fileDate=dealFileName();
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
		String tSumCount = "0";// ��ϸ��¼�ܱ���
		String tSumMoney = "0";// ����ܱ���
		String tCount1 = "0";// �̳���ϸ��¼�ܱ���
		String tCount2 = "0";// ������ϸ��¼�ܱ���
		String tCount3 = "0";// �˱���ϸ��¼�ܱ���
		String tSumMoney1 = "0";// �̳��ܽ��
		String tSumMoney2 = "0";// �����ܽ��
		String tSumMoney3 = "0";// �˱��ܽ��
		Element tBody = tRoot.getChild("Body");
		@SuppressWarnings("unchecked")
		List<Element> list = tBody.getChildren("Detail");
		tSumCount = Integer.toString(list.size());
		long sum1 = 0;
		long sum2 = 0;
		long sum3 = 0;
		long count1 = 0;
		long count2 = 0;
		long count3 = 0;

		cLogger.info("���ɷ����ļ�ͷ��¼" + outHead);
		cLogger.info("���ɷ����ļ��ܼ�¼" + list.size());

		/*
		 * �ļ���һ�У���������Ϣ��
		 * ��ʽ�����չ�˾����|���д���|�ܼ�¼��|�ܽ��|�̳��ܼ�¼��|�̳��ܽ��|�����ܼ�¼��|�����ܽ��|�˱��ܼ�¼��|�˱��ܽ��|
		 * �ļ��������ݣ�����ϸ��¼��
		 * ҵ�����|��������|������|����������|��������|Ͷ��������|Ͷ����֤������|Ͷ����֤������|����״̬|��ȡ���|
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
					// fileName =
					// "INVALID"+tComCode+"."+String.valueOf((DateUtil.get8Date(System.currentTimeMillis())+1));
					fileName = "INVALID" + tComCode + "." + cCurDate;
				}
				else
				{
					serialNo = Integer.toString(i);
					// fileName =
					// "INVALID"+tComCode+"."+String.valueOf((DateUtil.get8Date(System.currentTimeMillis())+1));
					fileName = "INVALID" + tComCode + "." + cCurDate;
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
					String tBusiType = tDetail.getChildText("BusiType");
					String tTranDate = tDetail.getChildText("TranDate");
					tTranDate = DateUtil.date10to8(tTranDate);
					String tContNo = tDetail.getChildText("ContNo");
					String tAppntName = tDetail.getChildText("AppntName");
					String tApplyState = tDetail.getChildText("Status");
					// ��������0���չ�˾��1����
					String tChannel = tDetail.getChildText("Channel");
					//�������Ϊ�գ���Ĭ����������Ϊ0-���չ�˾
					if(StringUtils.isEmpty(tChannel))
					{
						tChannel = "0";
					}
					String tPrem = tDetail.getChildText("Prem");
					// ������ʱû������ҵ�� ����WT�̳���CT�˱�
					if (tBusiType.equals("01"))
					{
						// tBusiType="01";
						// ������
						long prem1 = Long.parseLong(tPrem);
						sum1 = sum1 + prem1;
						count1++;
						tSumMoney1 = String.valueOf(sum1);
					}
					else if (tBusiType.equals("03"))
					{
						// tBusiType="03";
						// ������
						long prem3 = Long.parseLong(tPrem);
						sum3 = sum3 + prem3;
						count3++;
						tSumMoney3 = String.valueOf(sum3);
					}
					tPrem = NumberUtil.fenToYuan(tPrem);
					if (tChannel == null || tChannel.equals(""))
					{
						tChannel = "0";
					}
					outBody += tBusiType + "|" + tTranDate + "|" + tContNo + "|" + tAppntName + "|" + tChannel + "|" + "|" + "|" + "|" + tApplyState + "|" + tPrem + "|" + "\n";
				}
				// �����¼��txt�ļ�
				tCount1 = String.valueOf(count1);
				tCount2 = String.valueOf(count2);
				tCount3 = String.valueOf(count3);
				tSumCount = String.valueOf(count1 + count2 + count3);
				System.out.println("a:" + tSumMoney1 + "b:" + tSumMoney2 + "b:" + tSumMoney3);
				tSumMoney1 = NumberUtil.fenToYuan(tSumMoney1);
				tSumMoney2 = NumberUtil.fenToYuan(tSumMoney2);
				tSumMoney3 = NumberUtil.fenToYuan(tSumMoney3);
				tSumMoney = NumberUtil.fenToYuan(String.valueOf(sum1 + sum2 + sum3));
				System.out.println("�ܽ�" + tSumMoney);
				if (tSumMoney.equals("") || tSumMoney == null)
				{
					tSumMoney = "0.00";
				}
				if (tSumMoney1.equals("") || tSumMoney1 == null)
				{
					tSumMoney1 = "0.00";
				}
				if (tSumMoney2.equals("") || tSumMoney2 == null)
				{
					tSumMoney2 = "0.00";
				}
				if (tSumMoney3.equals("") || tSumMoney3 == null)
				{
					tSumMoney3 = "0.00";
				}
				if (tCount1.equals("") || tCount1 == null)
				{
					tCount1 = "0";
				}
				if (tCount2.equals("") || tCount2 == null)
				{
					tCount2 = "0";
				}
				if (tCount3.equals("") || tCount3 == null)
				{
					tCount3 = "0";
				}
				outHead = tComCode + "|" + "03" + "|" + tSumCount + "|" + tSumMoney + "|" + tCount1 + "|" + tSumMoney1 + "|" + tCount2 + "|" + tSumMoney2 + "|" + tCount3 + "|" + tSumMoney3 + "|" + "\n";
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
			// "INVALID"+tComCode+"."+String.valueOf((DateUtil.get8Date(System.currentTimeMillis())+1));
			fileName = "INVALID" + tComCode + "." + cCurDate;
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
			tSumCount = "0";
			tSumMoney = "0.00";
			tCount1 = "0";
			tSumMoney1 = "0.00";
			tCount2 = "0";
			tSumMoney2 = "0.00";
			tCount3 = "0";
			tSumMoney3 = "0.00";
			System.out.println("�ܽ�" + tSumMoney);
			outHead = tComCode + "|" + "03" + "|" + tSumCount + "|" + tSumMoney + "|" + tCount1 + "|" + tSumMoney1 + "|" + tCount2 + "|" + tSumMoney2 + "|" + tCount3 + "|" + tSumMoney3 + "|" + "\n";
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
		cLogger.info("Out NoTakenBalanceRst.receive()!");
	}

	@Override
	public void run()
	{
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into NoTakenBalanceRst.run()...");

		// �����һ�ν����Ϣ
		cResultMsg = null;

		try
		{
			cConfigEle = BatUtils.getConfigEle("2004"); // �õ�bat.xml�ļ��еĶ�Ӧ�ڵ�.
			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			}
			System.out.println(cConfigEle.getContent());
			
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

		cLogger.info("Out NoTakenBalanceRst.run()!");
	}

	// �������ļ�������yyyyMM01���գ�ϵͳ����Ŀ¼localDir ����/yyyy/yyyyMM��
	// Ȼ��������ļ��ƶ�����Ŀ¼������yyyyMM01���ļ�����
	private void bakFiles(String pFileDir)
	{
		cLogger.info("Into NoTakenBalanceRst.bakFiles()...");

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
				Date date = new Date();
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

		cLogger.info("Out NoTakenBalanceRst.bakFiles()!");
	}
	
	public final void setDate(String p8DateStr){
		cCurDate = p8DateStr; 
	}
	
	public String getResultMsg() {
		return this.cResultMsg;
	}
	
	public static void main(String[] args) throws Exception{
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.NoTakenBalanceRst.main");
		cLogger.info("��ʼ�˱��̳������ļ����˳���...");
		
		NoTakenBalanceRst abcAES = new NoTakenBalanceRst();
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
		
		cLogger.info("�����˱��̳������ļ����˳���!");
	}
}
