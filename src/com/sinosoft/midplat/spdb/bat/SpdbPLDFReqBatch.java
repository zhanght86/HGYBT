package com.sinosoft.midplat.spdb.bat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.spdb.SpdbConf;
import com.sinosoft.utility.ElementLis;

/**
 * @ClassName: SpdbPLDFReqBatch
 * @Description: ���������ļ�
 * @author sinosoft
 * @date 2017-4-21 ����5:37:03
 */
public class SpdbPLDFReqBatch extends SpdbPushFileToBankSvc
{

	public SpdbPLDFReqBatch()
	{
		super(SpdbConf.newInstance(), "3015");
	}

	@Override
	protected void deal(String ttLocalDir)
	{
		cLogger.info("Into SpdbPLDFReqBatch.deal()...");
		// ����������������
		Document cInXmlDoc = sendRequest();
		JdomUtil.print(cInXmlDoc);
		try
		{
			// ������ķ��ر���
			long mStartMillis = System.currentTimeMillis();
			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_spdbpldfReq).call(cInXmlDoc);
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			long tCurMillis = System.currentTimeMillis();
			int usedtime = (int) (tCurMillis - mStartMillis) / 1000;

			receive(cOutXmlDoc, ttLocalDir);// �����ļ�

			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setBak2("UBP���ú��Ľӿڣ�" + tHeadEle.getChildText(Desc));
			cTranLogDB.setUsedTime(usedtime);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update())
			{
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		cLogger.info("Out SpdbPLDFReqBatch.deal()...");
	}

	private Document sendRequest()
	{
		cLogger.info("Into SpdbPLDFReqBatch.sendRequest()...");
		ElementLis TranData = new ElementLis("TranData");
		Element Head = getHead();
		String trantime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		ElementLis Body = new ElementLis("Body", TranData);
		TranData.addContent(Head);
		new ElementLis(TranDate, trantime.substring(0, 8), Body);
		Document pXmlDoc = new Document(TranData);
		cLogger.info("Out SpdbPLDFReqBatch.sendRequest()..." + cTranDate);
		return pXmlDoc;
	}

	/***
	 * ÿ1000����¼����һ���ļ�
	 * 
	 * @param cOutXmlDoc
	 * @param ttLocalDir
	 */
	@SuppressWarnings("unchecked")
	private void receive(Document cOutXmlDoc, String ttLocalDir)
	{
		cLogger.info("Into SpdbPLDFReqBatch.receive()...");
		String tComCode = AblifeCodeDef.spdb_ComCode;// ���չ�˾����
		String outHead = "";// ͷ��¼
		String serialNo = "";// ���
		String fileName = "";// �ļ�����
		String out = "";
		String outBody = "";
		String tAccNo;
		String tPrem;
		String tAppntName;
		String tProposalPrtNo;
		String tSumID;
		String tMarkRemark1;
		String tMarkRemark2;
		String tMark;
		Double tSingleFileSumPrem = 0.00;
		Double tSumPrem = 0.00;
		BigDecimal tSingle;
		BigDecimal sSumPrem;
		File file;
		FileOutputStream fos;
		int maxNo = 1000;// һ��txt������������ϸ��¼�� ���ԵĻ���ֻ��Ҫ�����ֵ��������
		int totalRound = 0;
		Element tRoot = cOutXmlDoc.getRootElement();
		Element tBody = tRoot.getChild("Body");
		List<Element> list = tBody.getChildren("Detail");
		int tCount = list.size();
		cLogger.info("���ɷ����ļ�ͷ��¼" + outHead);

		if (list.size() != 0)
		{
			if (tCount % maxNo == 0)
				totalRound = tCount / maxNo;
			else
				totalRound = tCount / maxNo + 1;

			for (int i = 0; i < totalRound; i++)
			{
				if (i + 1 < 10)
				{
					serialNo = "0" + Integer.toString(i);
					fileName = "UBD_" + tComCode + "_9800_" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "_" + serialNo + ".txt";
				}
				else
				{
					serialNo = Integer.toString(i);
					fileName = "UBD_" + tComCode + "_9800_" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "_" + serialNo + ".txt";
				}
				file = new File(ttLocalDir + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "/" + fileName);
				if (!file.exists())
					try
					{
						file.createNewFile();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				/*
				 * ��ѭ�������i���ļ�������(i+1)*maxNo - i*maxNo����ϸ��¼
				 * ��i���ļ���ȡ����ϸ��¼�Ǵӵ�i*maxNo������(i+1)*maxNo��, ��ȡ��ֵ��������ͬʱ����������ѭ��
				 */
				tSingle = new BigDecimal(tSingleFileSumPrem);
				for (int j = i * maxNo; j < (i + 1) * maxNo; j++)
				{
					if (j == list.size())
					{
						break;
					}
					Element tDetail = list.get(j);
					tAccNo = tDetail.getChildText("AccNo");
					tPrem = tDetail.getChildText("Prem");
					tSingle.add(new BigDecimal(Double.parseDouble(tPrem)));
					tSumPrem += Double.parseDouble(tPrem);
					tAppntName = tDetail.getChildText("AppntName");
					tProposalPrtNo = tDetail.getChildText("ProposalPrtNo");
					tSumID = tDetail.getChildText("SumID");
					tMarkRemark1 = tDetail.getChildText("Bak1");
					tMarkRemark2 = tDetail.getChildText("Bak2");
					tMark = "";
					outBody += tAccNo + "��" + tPrem + "��" + tAppntName + "��" + tProposalPrtNo + "��" + tSumID + "��" + tMarkRemark1 + "��"
							+ tMarkRemark2 + "��" + tMark + "\n";
				}
				sSumPrem = new BigDecimal(tSumPrem);
				// �����¼��txt�ļ�
				if ((i + 1) * maxNo < tCount)
					outHead = "F��" + tComCode + "��" + "1008��" + sSumPrem.doubleValue() + "��1000����1000������N" + "\n";
				else
					outHead = "F��" + tComCode + "��" + "1008��" + sSumPrem.doubleValue() + "��" + (tCount - (i * maxNo)) + "����"
							+ (tCount - (i * maxNo)) + "������N" + "\n";

				out = outHead + outBody;
				cLogger.info("��" + i + "���ļ����ɷ����ļ��ܼ�¼" + out);
				byte[] m = out.getBytes();
				try
				{
					fos = new FileOutputStream(file);
					fos.write(m);
					fos.flush();
					fos.close();
				}
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				if (i * maxNo == list.size())
				{
					file.delete();
				}
			}
		}
		cLogger.info("Out SpdbPLDFReqBatch.receive()..." + cTranDate);
	}

	public static void main(String args[]) throws MidplatException
	{
		SpdbPLDFReqBatch tBlc = new SpdbPLDFReqBatch();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		Logger mLogger = Logger.getLogger(SpdbPLDFReqBatch.class);

		mLogger.info("�ַ����������ϴ�������ʼ...");

		// ���ڲ����ˣ����ò���������
		if (0 != args.length)
		{
			mLogger.info("args[0] = " + args[0]);

			/**
			 * �ϸ�����У���������ʽ��\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))��
			 * 4λ��-2λ��-2λ�ա� 4λ�꣺4λ[0-9]�����֡�
			 * 1��2λ�£�������Ϊ0��[0-9]�����֣�˫���±�����1��ͷ��β��Ϊ0��1��2������֮һ��
			 * 1��2λ�գ���0��1��2��ͷ��[0-9]�����֣�������3��ͷ��0��1��
			 *
			 * ������У���������ʽ��\\d{4}\\d{2}\\d{2}��
			 */
			if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))"))
			{
				tBlc.setDate(args[0]);
				tBlc.run();
			}
			else
			{
				throw new MidplatException("���ڸ�ʽ����ӦΪyyyyMMdd��" + args[0]);
			}
		}
		mLogger.info("�ַ����˳ɹ�������");
	}
}