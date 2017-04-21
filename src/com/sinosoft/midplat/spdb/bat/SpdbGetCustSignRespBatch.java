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
 * @ClassName: SpdbGetCustSignRespBatch
 * @Description: ����ǩԼ�����ļ�
 * @author sinosoft
 * @date 2017-4-21 ����5:21:49
 */
public class SpdbGetCustSignRespBatch extends SpdbGetFileFromBankSvc
{

	public SpdbGetCustSignRespBatch()
	{
		super(SpdbConf.newInstance(), "3011");
	}

	@SuppressWarnings("resource")
	@Override
	protected void deal(File cLocalFile)
	{
		cLogger.info("Into SpdbGetCustSignRespBatch.deal()...���Ի�ҵ����ģ��...");
		// ����������������
		Document cInXmlDoc = sendRequest();
		try
		{
			// ���ݷ��ص��ļ�������֯������Ϣ...
			FileReader fr = new FileReader(cLocalFile);
			BufferedReader br = new BufferedReader(fr);
			br.readLine();// Ϊ���Թ��ļ�ͷ
			String info;
			Element tBody = new Element("Body");
			cInXmlDoc.getRootElement().addContent(tBody);
			while ((info = br.readLine()) != null)
			{
				cLogger.info(info);
				String[] str = info.split("��", info.length());
				Element tDtls = new Element("Detail");
				new ElementLis("SignNo", str[0], tDtls);
				new ElementLis("AccType", str[1], tDtls);
				new ElementLis("AccNo", str[2], tDtls);
				new ElementLis("AppntName", str[3], tDtls);
				new ElementLis("AppntIDType", str[4], tDtls);
				new ElementLis("AppntIDNo", str[5], tDtls);
				new ElementLis("ProposalPrtNo", str[6], tDtls);
				new ElementLis("Flag", str[7], tDtls);
				new ElementLis("IsBankCust", str[8], tDtls);
				new ElementLis("ErrFlag", str[9], tDtls);
				new ElementLis("ErrDesc", str[10], tDtls);
				new ElementLis("Bak1", str[11], tDtls);
				new ElementLis("Bak2", str[12], tDtls);
				tBody.addContent(tDtls);
			}
			long mStartMillis = System.currentTimeMillis();
			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_SpdbCustSignResp).call(cInXmlDoc);
			long tCurMillis = System.currentTimeMillis();
			int usedtime = (int) (tCurMillis - mStartMillis) / 1000;
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			// ��Ϊ�����е�TranLogDB���ܰ�����ʽ��׵������������Ҫ���²�ѯ
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
		cLogger.info("Into SpdbGetCustSignRespBatch.sendRequest()...");
		ElementLis TranData = new ElementLis("TranData");
		Element Head = getHead();
		TranData.addContent(Head);
		Document pXmlDoc = new Document(TranData);
		cLogger.info("Out SpdbGetCustSignRespBatch.sendRequest()..." + cTranDate);
		return pXmlDoc;
	}

	public static void main(String[] args) throws MidplatException
	{
		SpdbGetCustSignRespBatch tBlc = new SpdbGetCustSignRespBatch();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		Logger mLogger = Logger.getLogger(SpdbGetCustSignRespBatch.class);

		mLogger.info("�ַ�����ǩԼ�������������ʼ...");

		// args = new String[1];
		// args[0] = "20161212";

		// ���ڲ�����ǩԼ�������������ò�����ǩԼ��������������
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
		mLogger.info("�ַ�����ǩԼ����������ɹ�������");
	}
}
