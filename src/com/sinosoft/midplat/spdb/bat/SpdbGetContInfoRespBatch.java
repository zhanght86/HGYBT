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
 * @ClassName: SpdbGetContInfoRespBatch
 * @Description: ����������Ϣͬ�������ļ�
 * @author sinosoft
 * @date 2017-4-21 ����5:19:33
 */
public class SpdbGetContInfoRespBatch extends SpdbGetFileFromBankSvc
{

	public SpdbGetContInfoRespBatch()
	{
		super(SpdbConf.newInstance(), "3010");
	}

	@SuppressWarnings("resource")
	@Override
	protected void deal(File cLocalFile)
	{
		cLogger.info("Into SpdbGetContInfoRespBatch.deal()...���Ի�ҵ����ģ��...");
		// ����������������
		Document cInXmlDoc = sendRequest();
		try
		{
			// ���ݷ��ص��ļ�������֯������Ϣ...
			FileReader fr = new FileReader(cLocalFile);
			BufferedReader br = new BufferedReader(fr);
			br.readLine();// Ϊ���Թ��ļ�ͷ
			String info;
			Element tBody = cInXmlDoc.getRootElement().addContent(new Element("Body"));
			while ((info = br.readLine()) != null)
			{
				String[] str = info.split("��", info.length());
				Element tDtls = new Element("Detail");
				new ElementLis("AppntName", str[0], tDtls);
				new ElementLis("ProposalPrtNo", str[1], tDtls);
				new ElementLis("Flag", str[2], tDtls);
				new ElementLis("ErrFlag", str[3], tDtls);
				new ElementLis("ErrDesc", str[4], tDtls);
				new ElementLis("Bak1", str[5], tDtls);
				new ElementLis("Bak2", str[6], tDtls);
				tBody.addContent(tDtls);
			}
			long mStartMillis = System.currentTimeMillis();
			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_SpdbContInfoResp).call(cInXmlDoc);
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
		cLogger.info("Into SpdbGetContInfoRespBatch.sendRequest()...");
		ElementLis TranData = new ElementLis("TranData");
		Element Head = getHead();
		TranData.addContent(Head);
		Document pXmlDoc = new Document(TranData);
		cLogger.info("Out SpdbGetContInfoRespBatch.sendRequest()..." + cTranDate);
		return pXmlDoc;
	}

	public static void main(String[] args) throws Exception
	{
		SpdbGetContInfoRespBatch tBlc = new SpdbGetContInfoRespBatch();
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		Logger mLogger = Logger.getLogger(SpdbGetContInfoRespBatch.class);
		
		mLogger.info("�ַ�������Ϣͬ���������������ʼ...");

		// ���ڲ�������Ϣͬ���������������ò�������Ϣͬ����������������
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
		mLogger.info("�ַ�������Ϣͬ������������ɹ�������");
	}
}