package com.sinosoft.midplat.spdb.bat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.spdb.SpdbConf;
import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

/**
 * @ClassName: SpdbZLBLRspSvc
 * @Description: ֱ����¼�ش��ļ�
 * @author sinosoft
 * @date 2017-4-21 ����5:34:26
 */
public class SpdbZLBLRspSvc extends SpdbPushFileToBankSvc
{

	// �ش����������
	public SpdbZLBLRspSvc()
	{
		super(SpdbConf.newInstance(), "3009");
	}

	@Override
	protected void deal(String ttLocalDir)
	{
		cLogger.info("Into SpdbZLBLRspSvc.deal()...");
		// ����������������
		Document cInXmlDoc = sendRequest();
		try
		{
			// ������ķ��ر���
			long mStartMillis = System.currentTimeMillis();

			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_SpdbzlblResp).call(cInXmlDoc);
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setBak2("���ú��Ľӿڣ�" + tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update())
			{
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
			receive(cOutXmlDoc, ttLocalDir);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		cLogger.info("Out SpdbZLBLRspSvc.deal()...");
	}

	private Document sendRequest()
	{
		cLogger.info("Into SpdbZLBLRspSvc.sendRequest()...");
		ElementLis TranData = new ElementLis("TranData");
		Element Head = getHead();
		String trantime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		TranData.addContent(Head);
		ElementLis Body = new ElementLis("Body", TranData);
		new ElementLis(TranDate, trantime.substring(0, 8), Body);
		Document pXmlDoc = new Document(TranData);
		cLogger.info("Out SpdbZLBLRspSvc.sendRequest()..." + cTranDate);
		return pXmlDoc;
	}

	@SuppressWarnings(
	{ "unchecked" })
	private void receive(Document cOutXmlDoc, String ttLocalDir)
	{
		cLogger.info("Into SpdbZLBLRspSvc.receive()..." + cTranDate);

		String outHead = "";// ͷ��¼
		String serialNo = "";// ���
		String fileName = "";// �ļ�����
		String out = "";
		String outBody = "";
		int maxNo = 1000;// һ��txt������������ϸ��¼�� ���ԵĻ���ֻ��Ҫ�����ֵ��������

		Element tRoot = cOutXmlDoc.getRootElement();
		String tComCode = AblifeCodeDef.spdb_ComCode;// ���չ�˾����

		List<Element> list = tRoot.getChild("Body").getChildren("Detail");
		int tCount = list.size();
		cLogger.info("���ɷ����ļ�ͷ��¼" + outHead);

		Map<String, String> riskMap = new HashMap<String, String>();
		SSRS res = new ExeSQL().execSQL("select INSU_CODE,BANK_CODE from codemapping where COMCODE='SPDB' and CODETYPE='riskcode'");

		for (int i = 1; i <= res.MaxRow; i++)
		{
			riskMap.put(res.GetText(i, 1), res.GetText(i, 2));
		}

		if (list.size() != 0)
		{
			int totalRound = 0;
			if (tCount % maxNo == 0)
			{
				totalRound = tCount / maxNo;
			}
			else
			{
				totalRound = tCount / maxNo + 1;
			}

			for (int i = 0; i < totalRound; i++)
			{
				if (i + 1 < 10)
				{
					serialNo = "0" + Integer.toString(i);
					fileName = "DZLBL_" + tComCode + "_9800_" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "_" + serialNo + ".txt";
				}
				else
				{
					serialNo = Integer.toString(i);
					fileName = "DZLBL_" + tComCode + "_9800_" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "_" + serialNo + ".txt";
				}
				File tDir = new File(ttLocalDir + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
				// ����ļ������ڴ����ļ�
				if (!tDir.exists())
				{
					try
					{
						tDir.mkdir();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}

				File file = new File(ttLocalDir + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "/" + fileName);
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
					String tZoneNo = tDetail.getChildText("ZoneNo");
					String tProposalPrtNo = tDetail.getChildText("ProposalPrtNo");
					String tErrFlag = tDetail.getChildText("ErrFlag");
					// ���ʧ��
					if (tErrFlag.equals("1"))
					{
						String tErrDesc = tDetail.getChildText("ErrDesc");
						outBody += tZoneNo + "��" + tComCode + "��" + tProposalPrtNo + "��" + tErrFlag + "��" + tErrDesc + "��";
					}
					else
					{
						String tErrDesc = tDetail.getChildText("ErrDesc");
						String sContNo = tDetail.getChildText("ContNo");
						String tFlag1 = tDetail.getChildText("Flag1");
						String tInsuredName = tDetail.getChildText("InsuredName");
						String tInsuredIDType = exChangeNum(tDetail.getChildText("InsuredIDType"), "IDTYPE");
						String tInsuredIDNo = tDetail.getChildText("InsuredIDNo");
						String tPayMode = exChangeNum(tDetail.getChildText("PayMode"), "PayMode");
						String tPayDate = tDetail.getChildText("TrsDate").replace("-", "");
						outBody += tZoneNo + "��"// ������
								+ tComCode + "��" // ���չ�˾����
								+ tProposalPrtNo + "��" // Ͷ������
								+ tErrFlag + "��" // �������0���ɹ�1��ʧ��
								+ tErrDesc + "��" // ������Ϣ �ɹ�ʱ���ɹ� ʧ��ʱ������ʧ��ԭ��
								+ sContNo + "��"// ������
								+ tFlag1 + "��" // �˱�����1 2- �˱��ɹ� 3- �˱�ʧ�� a-�ǳ����˱�
												// b-��ԥ���˱� c-����
								+ tInsuredName + "��" // ����������
								+ tInsuredIDType + "��" // ������֤������
								+ tInsuredIDNo + "��" // ������֤������
								+ tPayMode + "��" // ���ѷ�ʽ0�������չ�˾���� 1�����д��շ�
								+ tPayDate + "��";// �۷�����
						List<Element> riskList = tDetail.getChild("Risks").getChildren();
						if (riskList.size() != 0)
						{
							outBody += riskList.size() + "��";// ����������Ʒ����
							for (int k = 0; k < riskList.size(); k++)
							{
								Element tRiskDetail = riskList.get(k);
								String tRiskCode = riskMap.get(tRiskDetail.getChildText("RiskCode"));
								String tPayEndYearFlag = tRiskDetail.getChildText("PayEndYearFlag");
								String tPayEndYear = tRiskDetail.getChildText("PayEndYear");
								String tInsuYearFlag = tRiskDetail.getChildText("InsuYearFlag");
								String tInsuYear = tRiskDetail.getChildText("InsuYear");
								// ��������0ת��4������
								if (tPayEndYearFlag.equals("0"))
								{
									tPayEndYearFlag = "4";
								}
								else if (tPayEndYearFlag.equals("12"))
								{
									tPayEndYearFlag = "3";
								}
								// ����L/1000������ת��1/-1������
								if (tInsuYearFlag.equals("L"))
								{
									tInsuYearFlag = "1";
									if (tInsuYear.equals("1000"))
									{
										tInsuYear = "-1";
									}
								}
								else if (tInsuYearFlag.equals("Y"))
								{
									tInsuYearFlag = "2";
								}
								else if (tInsuYearFlag.equals("A"))
								{
									tInsuYearFlag = "3";
								}

								String tAmnt = format2Npoint(Double.valueOf(tRiskDetail.getChildText("Amnt")), 2);
								String tPrem = format2Npoint(Double.valueOf(tRiskDetail.getChildText("Prem")), 2);
								String tMult = "";// ���ַ�ȷ�Ϻ���ֶο���Ϊ�գ�����Ϊ��
								// ��Ʒ���� Ͷ������ ���� ���� �ɷ��������� 3-��� 4-���� 6����ĳȷ������
								// 7�����ɷ� �ɷ����� ������������ ��������
								outBody += tRiskCode + "��" + tMult + "��" + tPrem + "��" + tAmnt + "��" + tPayEndYearFlag + "��" + tPayEndYear + "��"
										+ tInsuYearFlag + "��" + tInsuYear + "��";
							}
						}
					}

					String tMark = "";
					outBody += tMark + "\n";
				}
				// �����¼��txt�ļ�
				if ((i + 1) * maxNo < tCount)
				{
					outHead = "F��" + tComCode + "��" + "1007��1000��" + "��" + "��" + "\n";
				}
				else
				{
					outHead = "F��" + tComCode + "��" + "1007��" + (tCount - (i * maxNo)) + "��" + "��" + "��" + "\n";
				}
				out = outHead + outBody;
				cLogger.info("��" + (i + 1) + "���ļ����ɷ����ļ��ܼ�¼" + out);
				byte[] m = out.getBytes();
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
				if (i * maxNo == list.size())
				{
					file.delete();
				}
				outBody = "";
				out = "";
			}
		}
		cLogger.info("Out SpdbZLBLRspSvc.receive()...");
	}

	@SuppressWarnings("unused")
	private String fenToYuan(String pFen)
	{
		if (null == pFen || "".equals(pFen))
		{
			return pFen;
		}

		return new DecimalFormat("0.00").format(Long.parseLong(pFen) / 100.0D);
	}

	private static String format2Npoint(double tNumber, int num)
	{

		String tNo1 = null;

		tNo1 = String.format("%." + num + "f", tNumber);

		return tNo1;

	}

	private String exChangeNum(String num1, String type)
	{
		String tResult = null;
		if (type.trim().equals("IDTYPE"))
		{
			if (num1.trim().equals("0"))
			{// ���֤
				tResult = "1";
			}
			else if (num1.trim().equals("1"))
			{// ����
				tResult = "2";
			}
			else if (num1.trim().equals("2"))
			{// ����֤������֤��
				tResult = "3";
			}
			else if (num1.trim().equals("3"))
			{// ����
				tResult = "7";
			}
			else if (num1.trim().equals("4"))
			{// ���ڱ�
				tResult = "6";
			}
			else if (num1.trim().equals("5"))
			{// ѧ��֤
				tResult = "7";
			}
			else if (num1.trim().equals("6"))
			{// ����֤
				tResult = "7";
			}
			else if (num1.trim().equals("7"))
			{// ����֤
				tResult = "7";
			}
			else if (num1.trim().equals("8"))
			{// ����
				tResult = "7";
			}
			else if (num1.trim().equals("9"))
			{// ��֤��
				tResult = "7";
			}
			else if (num1.trim().equals("A"))
			{// ʿ��֤
				tResult = "A";
			}
			else if (num1.trim().equals("B"))
			{// ����֤
				tResult = "7";
			}
			else if (num1.trim().equals("C"))
			{// ��ʱ���֤
				tResult = "1";
			}
			else if (num1.trim().equals("D"))
			{// ����֤
				tResult = "4";
			}
			else if (num1.trim().equals("E"))
			{// ̨��֤
				tResult = "B";
			}
			else if (num1.trim().equals("F"))
			{// �ۡ��ġ�̨ͨ��֤
				tResult = "5";
			}
			else
			{
				tResult = "7";
			}
		}
		else if (type.trim().equals("PayMode"))
		{
			if (num1.trim().equals("0"))
			{// ����ת��
				tResult = "1";
			}
			else if (num1.trim().equals("2"))
			{// �ֽ𣬵����չ�˾�ɷ�
				tResult = "0";
			}
			else if (num1.trim().equals("B"))
			{// ����
				tResult = "1";
			}
			else
			{
				tResult = "1";
			}
		}
		return tResult;
	}

	public static void main(String args[]) throws Exception
	{
		SpdbZLBLRspSvc tBlc = new SpdbZLBLRspSvc();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));

		Logger mLogger = Logger.getLogger(SpdbZLBLRspSvc.class);
		mLogger.info("�ַ�ֱ����¼�ϴ�DZLBL������ʼ...");
//		args = new String[1];
//		args[0] = "20170407";
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
		mLogger.info("�ַ�ֱ����¼�ϴ�DZLBL������ɹ�������");
	}
}