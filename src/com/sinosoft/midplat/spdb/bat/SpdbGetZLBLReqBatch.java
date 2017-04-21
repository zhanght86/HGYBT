package com.sinosoft.midplat.spdb.bat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
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
 * @ClassName: SpdbGetZLBLReqBatch
 * @Description: ֱ����¼�ϴ��ļ�
 * @author sinosoft
 * @date 2017-4-21 ����5:32:52
 */
public class SpdbGetZLBLReqBatch extends SpdbGetFileFromBankSvc
{

	public SpdbGetZLBLReqBatch()
	{
		super(SpdbConf.newInstance(), "3012");
	}

	@SuppressWarnings("resource")
	@Override
	protected void deal(File cLocalFile)
	{
		cLogger.info("Into SpdbGetZLBLReqBatch.deal()...���Ի�ҵ����ģ��...");
		try
		{
			// ������Ҫ�ı���
			String tRiskCode;
			String tPayEndYearFlag;
			String tPayEndYear;
			String tInsuYearFlag;
			String tInsuYear;
			String tMRiskCode;
			Element tDtls;
			Element tRisks;
			String[] str;
			Element tBody = new Element("Body");
			String info;
			Document cInXmlDoc = sendRequest();
			FileReader fr = new FileReader(cLocalFile);
			BufferedReader br = new BufferedReader(fr);
			Map<String, String> riskMap = new HashMap<String, String>();
			SSRS res = new ExeSQL().execSQL("select BANK_CODE,MID_CODE from codemapping where COMCODE='SPDB' and CODETYPE='riskcode'");
			for (int i = 1; i <= res.MaxRow; i++)
			{
				riskMap.put(res.GetText(i, 1), res.GetText(i, 2));
			}
			cInXmlDoc.getRootElement().addContent(tBody);
			br.readLine();// �Թ��ļ�ͷ
			while ((info = br.readLine()) != null)
			{
				cLogger.info("��ʼ�����ļ�����֯����......");
				str = info.split("��", info.length());
				tDtls = new Element("Detail");
				tRisks = new Element("Risks");
				new ElementLis("BankCode", str[0], tDtls);
				new ElementLis("ZoneNo", str[1], tDtls);
				new ElementLis("NodeNo", str[2], tDtls);
				new ElementLis("TellerNo", str[3], tDtls);
				new ElementLis("PolApplyDate", str[4], tDtls);
				new ElementLis("ProposalPrtNo", str[5], tDtls);
				new ElementLis("AppntName", str[6], tDtls);
				new ElementLis("AppntIDType", tranIdType(str[7]), tDtls);
				new ElementLis("AppntIDNo", str[8], tDtls);
				new ElementLis("AccNo", str[9], tDtls);
				tDtls.addContent(tRisks);
				int productCount = Integer.parseInt(str[10]);
				for (int i = 0; i < productCount; i++)
				{
					Element tRisk = new Element("Risk");
					tRisks.addContent(tRisk);
					tRiskCode = str[10 + i * 6 + 1];
					tPayEndYearFlag = str[10 + i * 6 + 2];
					tPayEndYear = str[10 + i * 6 + 3];
					tInsuYearFlag = str[10 + i * 6 + 4];
					tInsuYear = str[10 + i * 6 + 5];
					tMRiskCode = str[11];
					tRiskCode = riskMap.get(tRiskCode);
					tMRiskCode = riskMap.get(tMRiskCode);
					// �ɷ������������� ����4/1 ת��0/1������
					if (tPayEndYearFlag.equals("3"))
					{
						tPayEndYearFlag = "12";
					}
					else if (tPayEndYearFlag.equals("4"))
					{
						tPayEndYearFlag = "0";
					}
					// ������������ת�� ������1/-1 ת��L/1000
					if (tInsuYearFlag.equals("1"))
					{
						tInsuYearFlag = "L";
						if (tInsuYear.equals("-1"))
						{
							tInsuYear = "1000";
						}
					}
					else if (tInsuYearFlag.equals("2"))
					{
						tInsuYearFlag = "Y";
					}
					else if (tInsuYearFlag.equals("3"))
					{
						tInsuYearFlag = "A";
					}
					if (tRiskCode == null || "".equals(tRiskCode))
						tRiskCode = str[10 + i * 6 + 1];
					if (tRiskCode == null || "".equals(tRiskCode))
						tMRiskCode = str[11];
					new ElementLis("RiskCode", tRiskCode, tRisk);
					new ElementLis("MainRiskCode", tMRiskCode, tRisk);
					new ElementLis("PayEndYearFlag", tPayEndYearFlag, tRisk);
					new ElementLis("PayEndYear", tPayEndYear, tRisk);
					new ElementLis("InsuYearFlag", tInsuYearFlag, tRisk);
					new ElementLis("InsuYear", tInsuYear, tRisk);
					new ElementLis("Prem", str[10 + i * 6 + 6], tRisk);
				}
				tBody.addContent(tDtls);
			}
			
			long mStartMillis = System.currentTimeMillis();
			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_Spdbzlbl).call(cInXmlDoc);
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			long tCurMillis = System.currentTimeMillis();
			int usedtime = (int) (tCurMillis - mStartMillis) / 1000;

			String updateSql = "update tranlog set rcode='" + tHeadEle.getChildText(Flag) + "',bak2 = '" + "UZLBL���ú��Ľӿڣ�"
					+ tHeadEle.getChildText(Desc) + "', usedtime='" + usedtime + "',modifydate='" + DateUtil.get8Date(tCurMillis)
					+ "',modifytime='" + DateUtil.get6Time(tCurMillis) + "' where indoc='" + cLocalFile.getName() + "' and funcflag='"
					+ cFuncFlag + "'";
			boolean tExeFlag = new ExeSQL().execUpdateSQL(updateSql);
			if (!tExeFlag)
			{
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private String tranIdType(String idType)
	{
		if (idType.equals("1"))
			idType = "0";
		else if (idType.equals("2"))
			idType = "1";
		else if (idType.equals("3"))
			idType = "2";
		else if (idType.equals("4") || idType.equals("8"))
			idType = "D";
		else if (idType.equals("5"))
			idType = "F";
		else if (idType.equals("6"))
			idType = "4";
		else if (idType.equals("7") || idType.equals("9") || idType.equals("D"))
			idType = "8";
		else if (idType.equals("A"))
			idType = "A";
		else if (idType.equals("B"))
			idType = "E";
		else if (idType.equals("C"))
			idType = "C";
		return idType;
	}

	private Document sendRequest()
	{
		cLogger.info("Into SpdbGetZLBLReqBatch.sendRequest()...");
		ElementLis TranData = new ElementLis("TranData");
		Element Head = getHead();
		TranData.addContent(Head);
		Document pXmlDoc = new Document(TranData);
		cLogger.info("Out SpdbGetZLBLReqBatch.sendRequest()..." + cTranDate);
		return pXmlDoc;
	}

	public static void main(String[] args) throws MidplatException
	{
		SpdbGetZLBLReqBatch tBlc = new SpdbGetZLBLReqBatch();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		Logger mLogger = Logger.getLogger(SpdbGetZLBLReqBatch.class);
		mLogger.info("�ַ�ֱ����¼����UZLBL���������ʼ...");
		// ���ڲ�ֱ����¼�ϴ����������ò�ֱ����¼�ϴ�����������
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
		mLogger.info("�ַ�ֱ����¼����UZLBL������ɹ�������");
	}
}