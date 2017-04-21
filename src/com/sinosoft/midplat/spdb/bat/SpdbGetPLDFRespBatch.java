package com.sinosoft.midplat.spdb.bat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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

/**
 * @ClassName: SpdbGetPLDFRespBatch
 * @Description: �������������ļ�
 * @author sinosoft
 * @date 2017-4-21 ����5:25:43
 */
public class SpdbGetPLDFRespBatch extends SpdbGetFileFromBankSvc
{

	public SpdbGetPLDFRespBatch()
	{
		super(SpdbConf.newInstance(), "3016");
	}

	@SuppressWarnings("resource")
	@Override
	protected void deal(File cLocalFile)
	{
		cLogger.info("Into SpdbGetPLDFRespBatch.deal()...���Ի�ҵ����ģ��..��.");
		// ����������������
		Document cInXmlDoc = sendRequest();
		try
		{
			// ���ݷ��ص��ļ�������֯������Ϣ...
			FileReader fr = new FileReader(cLocalFile);
			BufferedReader br = new BufferedReader(fr);
			String tSumInfo = br.readLine();// Ϊ���Թ��ļ�ͷ
			Element tBody = new Element("Body");
			String[] tSumStr = tSumInfo.split("��", tSumInfo.length());
			new ElementLis("PayDate", tSumStr[2], tBody);
			new ElementLis("SCount", tSumStr[3], tBody);
			new ElementLis("SAmount", tSumStr[4], tBody);
			new ElementLis("SSuccCount", tSumStr[5], tBody);
			new ElementLis("SSuccAmount", tSumStr[6], tBody);
			String info;
			while ((info = br.readLine()) != null)
			{
				String[] str = info.split("��", info.length());
				Element tDtls = new Element("Detail");
				if (str[4].equals("1"))
				{
					str[4] = "0";
				}
				else if (str[4].equals("0"))
				{
					str[4] = "1";
				}
				new ElementLis("AccNo", str[0], tDtls);
				new ElementLis("Prem", str[1], tDtls);
				new ElementLis("AppntName", str[2], tDtls);
				new ElementLis("ProposalPrtNo", str[3], tDtls);
				new ElementLis("Flag", str[4], tDtls);
				new ElementLis("Remark", str[5], tDtls);
				new ElementLis("SumID", str[6], tDtls);
				new ElementLis("Bak1", str[7], tDtls);
				new ElementLis("Bak2", str[8], tDtls);

				tBody.addContent(tDtls);
			}
			cInXmlDoc.getRootElement().addContent(tBody);

			long mStartMillis = System.currentTimeMillis();
			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_spdbpldfResp).call(cInXmlDoc);
			long tCurMillis = System.currentTimeMillis();
			int usedtime = (int) (tCurMillis - mStartMillis) / 1000;
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			// ��Ϊ�����е�TranLogDB���ܰ�����Ƚ��׵������������Ҫ���²�ѯ
			String updateSql = "update tranlog set rcode='" + tHeadEle.getChildText(Flag) + "',bak2 = '" + "���ú��Ľӿڣ�"
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

	private Document sendRequest()
	{
		cLogger.info("Into SpdbGetPLDFRespBatch.sendRequest()...");
		ElementLis TranData = new ElementLis("TranData");
		Element Head = getHead();
		TranData.addContent(Head);
		Document pXmlDoc = new Document(TranData);
		cLogger.info("Out SpdbGetPLDFRespBatch.sendRequest()..." + cTranDate);
		return pXmlDoc;
	}

	public static void main(String[] args) throws Exception
	{
		SpdbGetPLDFRespBatch tBlc = new SpdbGetPLDFRespBatch();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		Logger mLogger = Logger.getLogger(SpdbGetPLDFRespBatch.class);

		mLogger.info("�ַ����������������������ʼ...");

		// ���ڲ����������������������ò�����������������������
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
		mLogger.info("�ַ�������������������ɹ�������");
	}
}