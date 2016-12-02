//�����е�֤���ˣ��˽�������û�У�������ͨ�Լ�����ÿ��ȡ���ն����ļ��еı����Ų��ҵ�֤�Ŷ���
package com.sinosoft.midplat.jhyh.bat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.jhyh.bat.Balance;
import com.sinosoft.midplat.bat.GetSFTPConnection;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.jhyh.JhyhConf;
import com.sinosoft.midplat.service.Service;

import com.sinosoft.utility.ExeSQL;

public class JhyhCardBlc extends Balance
{

	public JhyhCardBlc()
	{
		super(JhyhConf.newInstance(), "5006");
	}

	@Override
	protected String getFileName()
	{
		// TODO Auto-generated method stub
		// Element mBankEle = cThisConfRoot.getChild("bank");
		return "ZHRS" + DateUtil.getDateStr(cTranDate, "yyyyMMdd") + ".txt";
	}

	public void run()
	{
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		this.cLogger.info("Into JhyhCardBlc.run()...");

		this.cResultMsg = null;
		try
		{
			this.cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
			this.cThisConfRoot = this.cThisConf.getConf().getRootElement();
			this.cThisBusiConf = ((Element) XPath.selectSingleNode(this.cThisConfRoot, "business[funcFlag='" + this.cFuncFlag + "']"));

			String nextDate = this.cThisBusiConf.getChildText("nextDate");

			if (this.cTranDate == null)
				if ((nextDate != null) && ("Y".equals(nextDate)))
				{
					this.cTranDate = new Date();
					this.cTranDate = new Date(this.cTranDate.getTime() - 86400000L);
				}
				else
				{
					this.cTranDate = new Date();
				}
			Element tTranData = new Element("TranData");
			Document tInStdXml = new Document(tTranData);

			Element tHeadEle = getHead();
			tTranData.addContent(tHeadEle);
			try
			{
				// Element mBankEle = cThisConfRoot.getChild("bank");
				String ttFileName = getFileName();
				this.cLogger.info("FileName = " + ttFileName);
				String ttLocalDir = this.cThisBusiConf.getChildTextTrim("localDir");
				this.cLogger.info("localDir = " + ttLocalDir);
				InputStream ttBatIns = null;

				ttBatIns = new FileInputStream(ttLocalDir + ttFileName);

				Element ttBodyEle = parse(ttBatIns);
				tTranData.addContent(ttBodyEle);
			}
			catch (Exception ex)
			{
				this.cLogger.error("���ɱ�׼���˱��ĳ���!", ex);

				Element ttError = new Element("Error");
				String ttErrorStr = ex.getMessage();
				if ("".equals(ttErrorStr))
				{
					ttErrorStr = ex.toString();
				}
				ttError.setText(ttErrorStr);
				tTranData.addContent(ttError);
			}

			String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";

			String tServiceValue = this.cMidplatRoot.getChildText("service");
			if ((tServiceValue != null) && (!"".equals(tServiceValue)))
			{
				tServiceClassName = tServiceValue;
			}

			tServiceValue = this.cThisConfRoot.getChildText("service");
			if ((tServiceValue != null) && (!"".equals(tServiceValue)))
			{
				tServiceClassName = tServiceValue;
			}
			tServiceValue = this.cThisBusiConf.getChildText("service");
			if ((tServiceValue != null) && (!"".equals(tServiceValue)))
			{
				tServiceClassName = tServiceValue;
			}
			this.cLogger.info("ҵ����ģ��" + tServiceClassName);
			Constructor tServiceConstructor = Class.forName(tServiceClassName).getConstructor(new Class[] { Element.class });
			Service tService = (Service) tServiceConstructor.newInstance(new Object[] { this.cThisBusiConf });
			Document tOutStdXml = tService.service(tInStdXml);

			this.cResultMsg = tOutStdXml.getRootElement().getChild("Head").getChildText("Desc");

		}
		catch (Throwable ex)
		{
			this.cLogger.error("���׳���", ex);
			this.cResultMsg = ex.toString();
		}

		this.cTranDate = null;

		this.cLogger.info("Out JhyhCardBlc.run()!");
	}

	protected Element parse(InputStream pBatIs) throws Exception
	{

		cLogger.info("Into JhyhCardBlc.parse()...");

		int sumCount = 0;

		String mCharset = cThisBusiConf.getChildText(charset);
		if (null == mCharset || "".equals(mCharset))
		{
			mCharset = "GBK";
		}
		// ��ʽ�����չ�˾����|�ܼ�¼��|�ܽ��|�ɹ��ܼ�¼��|�ɹ��ܽ��
		// �ļ��������ݣ�����ϸ��¼��
		// ��������|��������|�������|������ˮ��|������|���׽��(��С����)|��������|

		System.out.println(pBatIs);
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));

		// String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		// �ѳɹ��ļ�¼��ȡ������������
		Element mCountEle = new Element(Count);
		// mCountEle.setText(mSubMsgs[5].trim());
		// mSumPremEle.setText(String.valueOf(NumberUtil.yuanToFen(mSubMsgs[4].trim())));

		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(mCountEle);

		for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());)
		{
			// cLogger.info("���Ǹ�ô������"+tLineMsg);
			// �ܱ�����һ
			sumCount++;
			// ���У�ֱ������
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg))
			{
				cLogger.warn("���У�ֱ��������������һ����");
				continue;
			}

			String[] tSubMsgs = tLineMsg.split("\\|", -1);

			Element tTranNoEle = new Element(TranNo);
			tTranNoEle.setText(tSubMsgs[3]);

			// ��֤����ʱȡ������Ϊ��֤�������ֶ�ֵ
			Element tOtherNoEle = new Element("OtherNo");
			tOtherNoEle.setText(tSubMsgs[4]);

			String tSqlStr = new StringBuilder("select otherno from tranlog where funcflag='").append("1014").append('\'').append(" and contno='").append(tSubMsgs[4]).append('\'').append(" and trancom='").append("07").append('\'').append(" and rcode='").append("0").append('\'').toString();
			String contPrtNo = new ExeSQL().getOneValue(tSqlStr);

			Element tAgentCom = new Element(AgentCom);
			tAgentCom.setText(tSubMsgs[2]);

			Element tTellerNo = new Element("TellerNo");
			tTellerNo.setText(tSubMsgs[2]);

			Element tCardNoEle = new Element("CardNo");
			tCardNoEle.setText(contPrtNo);

			Element tCardTypeEle = new Element("CardType");
			if (!"".equals(contPrtNo) && contPrtNo != null)
			{
				tCardTypeEle.setText(contPrtNo.substring(0, 7));
			}

			Element tCardStateEle = new Element("CardState");
			tCardStateEle.setText("4");// ��֤��ʹ��

			Element tDetailEle = new Element(Detail);
			tDetailEle.addContent(tAgentCom);
			tDetailEle.addContent(tOtherNoEle);
			tDetailEle.addContent(tCardNoEle);
			tDetailEle.addContent(tCardTypeEle);
			tDetailEle.addContent(tCardStateEle);
			tDetailEle.addContent(tTellerNo);
			tDetailEle.addContent(tTranNoEle);

			mBodyEle.addContent(tDetailEle);
		}

		mCountEle.setText(String.valueOf(sumCount));
		mBufReader.close(); // �ر���n(mSumPrem)+"������������" +
							// String.valueOf(mCount));

		cLogger.info("Out JhyhCardBlc.parse()!");
		return mBodyEle;
	}

	protected Element getHead()
	{
		cLogger.info("Into JhyhCardBlc.getHead()...");

		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));

		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));

		Element mTranCom = (Element) cThisConfRoot.getChild(TranCom).clone();

		Element mZoneNo = (Element) cThisBusiConf.getChild(ZoneNo).clone();

		Element mNodeNo = (Element) cThisBusiConf.getChild(NodeNo).clone();

		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText(CodeDef.SYS);

		Element mTranNo = new Element(TranNo);
		mTranNo.setText(getFileName());

		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cThisBusiConf.getChildText(funcFlag));

		Element mTranLogNo = new Element("TranLogNo");
		mTranLogNo.setText(Thread.currentThread().getName());

		// ����ͷ������Ӻ��ĵ����б���
		Element mBankCode = new Element("BankCode");
		mBankCode.setText("0122");

		Element mHead = new Element(Head);
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);
		mHead.addContent(mTranCom);
		mHead.addContent(mZoneNo);
		mHead.addContent(mNodeNo);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		mHead.addContent(mTranLogNo);
		// ����ͷ������Ӻ��ĵ����б���
		mHead.addContent(mBankCode);

		cLogger.info("Out JhyhCardBlc.getHead()!");
		return mHead;
	}

	public static void main(String[] args) throws Exception
	{
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.jhyh.bat.JhyhCardBlc.main");
		mLogger.info("����ʼ...");

		JhyhCardBlc mBatch = new JhyhCardBlc();
		mBatch.run();
		// File file = new File("D://cest//down//DAYCHECKNBYH100020140812.xml");
		// InputStream in = new FileInputStream(file);
		// Element mBodyEle = mBatch.parse(in);
		// JdomUtil.print(mBodyEle);
		mLogger.info("�ɹ�������");
	}

}
