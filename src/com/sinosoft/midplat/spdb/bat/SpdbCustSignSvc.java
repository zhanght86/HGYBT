package com.sinosoft.midplat.spdb.bat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

/**
 * @ClassName: SpdbCustSignSvc
 * @Description: ����ǩԼ�ļ�
 * @author sinosoft
 * @date 2017-4-21 ����5:18:02
 */
public class SpdbCustSignSvc extends SpdbPushFileToBankSvc
{

	/**
	 * <p>Title: ����ǩԼ�ļ�������</p>
	 * <p>Description: ��ʼ����ǰ�����ļ������״���</p>
	 */
	public SpdbCustSignSvc()
	{
		super(SpdbConf.newInstance(), "3008");
	}

	/**
	 * ����
	 * @param ttLocalDir ���ر����ļ�Ŀ¼
	 */
	@Override
	protected void deal(String ttLocalDir)
	{
		cLogger.info("Into SpdbCustSignSvc.deal()...");

		// ����������������
		Document cInXmlDoc = sendRequest();
		try
		{
			// ������ķ��ر���
			long mStartMillis = System.currentTimeMillis();
			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_SpdbCustSign).call(cInXmlDoc);

			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
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
		cLogger.info("Out SpdbCustSignSvc.deal()...");
	}

	/**
	 * @Title: sendRequest
	 * @Description: ���ͺ���������
	 * @return ��׼���뱨��
	 * @return Document ���ı�׼���뱨��
	 * @throws
	 */
	private Document sendRequest()
	{
		cLogger.info("Into SpdbCustSignSvc.sendRequest()...");
		//���ڵ�
		ElementLis TranData = new ElementLis("TranData");
		//����ͷ[���׻�����Ϣ]
		Element Head = getHead();
		String trantime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		TranData.addContent(Head);
		//������[��������]
		ElementLis Body = new ElementLis("Body", TranData);
		//��������
		new ElementLis(TranDate, trantime.substring(0, 8), Body);
		//������׼���뱨��
		Document pXmlDoc = new Document(TranData);
		cLogger.info("Out SpdbCustSignSvc.sendRequest()..." + cTranDate);
		//���ر�׼���뱨��
		return pXmlDoc;
	}

	/**
	 * @Title: receive
	 * @Description: ����
	 * @param cOutXmlDoc ��׼�������
	 * @param ttLocalDir ���ر����ļ�Ŀ¼
	 * @return void  
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private void receive(Document cOutXmlDoc, String ttLocalDir)
	{
		cLogger.info("Into SpdbCustSignSvc.receive()..." + cTranDate);
		//ͷ��¼
		String outHead = "";
		//���
		String serialNo = "";
		//�ļ�����
		String fileName = "";
		String out = "";
		String outBody = "";
		// һ��txt������������ϸ��¼�� ���ԵĻ���ֻ��Ҫ�����ֵ��������
		int maxNo = 500;

		//��׼������ĸ��ڵ�
		Element tRoot = cOutXmlDoc.getRootElement();
		//���չ�˾����
		String tComCode = AblifeCodeDef.spdb_ComCode;

		//����ǩԼ�ļ���ʵ��
		SpdbCustSignSvc tRun = new SpdbCustSignSvc();
		
		//������
		Element tBody = tRoot.getChild("Body");
		//��ϸ
		List<Element> list = tBody.getChildren("Detail");
		//��ϸ��
		int tCount = list.size();
		//���ɷ����ļ�ͷ��¼
		cLogger.info("���ɷ����ļ�ͷ��¼" + outHead);
		//��ϸ����0
		if (list.size() != 0)
		{
			//ȫ��
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
				//��Ų���2��λ[С��10]
				if (i + 1 < 10)
				{
					//���ǰ��0
					serialNo = "0" + Integer.toString(i);
					//�ļ�����[	 UBCS_���չ�˾���(4λ) _�������루4λ��_���ڣ�8λ��_��ţ�2λ��										 �ļ����ͣ�TXT]
					fileName = "UBCS_" + tComCode + "_9800_" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "_" + serialNo + ".txt";
				}
				else
				{
					//ȡ��λ���
					serialNo = Integer.toString(i);
					//�ļ�����[	 UBCS_���չ�˾���(4λ) _�������루4λ��_���ڣ�8λ��_��ţ�2λ��										 �ļ����ͣ�TXT]
					fileName = "UBCS_" + tComCode + "_9800_" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "_" + serialNo + ".txt";
				}
				//���ر����ļ�Ŀ¼/yyyyMMdd
				File tDir = new File(ttLocalDir + "/" + DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
				// ����ļ��в�����
				if (!tDir.exists())
				{
					try
					{
						//�����ļ���
						tDir.mkdir();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}

				//���ر����ļ�Ŀ¼/yyyyMMdd/UBCS_���չ�˾���(4λ) _�������루4λ��_���ڣ�8λ��_��ţ�2λ��.TXT
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
					String tSignNo = tDetail.getChildText("SignNo");
					String tAccType = tDetail.getChildText("AccType");
					String sAccNo = tDetail.getChildText("AccNo");
					String tAppntName = tDetail.getChildText("AppntName");
					String tAppntIDType = tRun.exChangeNum(tDetail.getChildText("AppntIDType"), null, "IDTYPE", null);
					String tAppntIDNo = tDetail.getChildText("AppntIDNo");
					String tProposalPrtNo = tDetail.getChildText("ProposalPrtNo");

					String tFlag = tDetail.getChildText("Flag");

					String tIsBankCust = tDetail.getChildText("IsBankCust");

					String tMarkRemark1 = tDetail.getChildText("Bak1");
					String tMarkRemark2 = tDetail.getChildText("Bak1");

					String tMark = "";
					outBody += tSignNo + "��" + tAccType + "��" + sAccNo + "��" + tAppntName + "��" + tAppntIDType + "��" + tAppntIDNo + "��"
							+ tProposalPrtNo + "��" + tFlag + "��" + tIsBankCust + "��" + tMarkRemark1 + "��" + tMarkRemark2 + "��" + tMark + "\n";
				}
				// �����¼��txt�ļ�
				if ((i + 1) * maxNo < tCount)
				{
					outHead = "F��" + tComCode + "��" + "1002��500��" + "��" + "��N" + "\n";
				}
				else
				{
					outHead = "F��" + tComCode + "��" + "1002��" + (tCount - (i * maxNo)) + "��" + "��" + "��N" + "\n";
				}
				out = outHead + outBody;
				cLogger.info("��" + i + "���ļ����ɷ����ļ��ܼ�¼" + out);
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
		cLogger.info("Out SpdbCustSignSvc.receive()...");
	}

	private String exChangeNum(String num1, String num2, String type, String type2)
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
		return tResult;
	}

	public static void main(String args[]) throws MidplatException
	{
		SpdbCustSignSvc tBlc = new SpdbCustSignSvc();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		Logger mLogger = Logger.getLogger(SpdbCustSignSvc.class);

		mLogger.info("�ַ�����ǩԼ�ϴ����������ʼ...");
		args = new String[1];
		args[0] = "20170407";
		// ���ڲ�����ǩԼ�ϴ����������ò�����ǩԼ�ϴ�����������
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
		mLogger.info("�ַ�����ǩԼ�ϴ�������ɹ�������");
	}
}