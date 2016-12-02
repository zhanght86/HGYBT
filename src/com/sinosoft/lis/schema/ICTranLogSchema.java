/*
 * <p>ClassName: ICTranLogSchema </p>
 * <p>Description: DB�� Schema ���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ
 * @CreateDate��2012-07-05
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.ICTranLogDB;

public class ICTranLogSchema implements Schema
{
	// @Field
	/** ��־�� */
	private int LogNo;
	/** ���׵�λ */
	private int TranCom;
	/** �������� */
	private String ZoneNo;
	/** �������� */
	private String NodeNo;
	/** ������ˮ�� */
	private String TranNo;
	/** ����Ա */
	private String TellerNo;
	/** �������� */
	private int FuncFlag;
	/** �������� */
	private int TranDate;
	/** ����ʱ�� */
	private int TranTime;
	/** ���� */
	private String Channel;
	/** ��Ӧ�����к� */
	private String BankAccountNum;
	/** �����ն˺� */
	private String TerminalCode;
	/** ���׽�� */
	private String RCode;
	/** ������� */
	private String RText;
	/** �����ʱ */
	private int UsedTime;
	/** ����1 */
	private String Bak1;
	/** ����2 */
	private String Bak2;
	/** ����3 */
	private String Bak3;
	/** ����4 */
	private String Bak4;
	/** ����5 */
	private String Bak5;
	/** ���뱨�� */
	private String InNoDoc;
	/** �����׼���� */
	private String InDoc;
	/** ���ر��� */
	private String OutNoDoc;
	/** ���ر�׼���� */
	private String OutDoc;
	/** ������� */
	private int MakeDate;
	/** ���ʱ�� */
	private int MakeTime;
	/** ����޸����� */
	private int ModifyDate;
	/** ����޸�ʱ�� */
	private int ModifyTime;

	public static final int FIELDNUM = 28;	// ���ݿ����ֶθ���

	private static String[] PK;				// ����

	private FDate fDate = new FDate();		// ��������

	public CErrors mErrors;			// ������Ϣ

	// @Constructor
	public ICTranLogSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "LogNo";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** ��־��<P>��־�� */
	public int getLogNo()
	{
		return LogNo;
	}
	/** ��־�� */
	public void setLogNo(int aLogNo)
	{
		LogNo = aLogNo;
	}
	/** ��־��<P>��־�� */
	public void setLogNo(String aLogNo)
	{
		if (aLogNo != null && !aLogNo.equals(""))
		{
			Integer tInteger = new Integer(aLogNo);
			int i = tInteger.intValue();
			LogNo = i;
		}
	}

	/** ���׵�λ<P>���׵�λ */
	public int getTranCom()
	{
		return TranCom;
	}
	/** ���׵�λ */
	public void setTranCom(int aTranCom)
	{
		TranCom = aTranCom;
	}
	/** ���׵�λ<P>���׵�λ */
	public void setTranCom(String aTranCom)
	{
		if (aTranCom != null && !aTranCom.equals(""))
		{
			Integer tInteger = new Integer(aTranCom);
			int i = tInteger.intValue();
			TranCom = i;
		}
	}

	/** ��������<P>�������� */
	public String getZoneNo()
	{
		if (ZoneNo != null && !ZoneNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ZoneNo = StrTool.unicodeToGBK(ZoneNo);
		}
		return ZoneNo;
	}
	/** �������� */
	public void setZoneNo(String aZoneNo)
	{
		ZoneNo = aZoneNo;
	}
	/** ��������<P>�������� */
	public String getNodeNo()
	{
		if (NodeNo != null && !NodeNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			NodeNo = StrTool.unicodeToGBK(NodeNo);
		}
		return NodeNo;
	}
	/** �������� */
	public void setNodeNo(String aNodeNo)
	{
		NodeNo = aNodeNo;
	}
	/** ������ˮ��<P>������ˮ�� */
	public String getTranNo()
	{
		if (TranNo != null && !TranNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			TranNo = StrTool.unicodeToGBK(TranNo);
		}
		return TranNo;
	}
	/** ������ˮ�� */
	public void setTranNo(String aTranNo)
	{
		TranNo = aTranNo;
	}
	/** ����Ա<P>����Ա */
	public String getTellerNo()
	{
		if (TellerNo != null && !TellerNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			TellerNo = StrTool.unicodeToGBK(TellerNo);
		}
		return TellerNo;
	}
	/** ����Ա */
	public void setTellerNo(String aTellerNo)
	{
		TellerNo = aTellerNo;
	}
	/** ��������<P>�������� */
	public int getFuncFlag()
	{
		return FuncFlag;
	}
	/** �������� */
	public void setFuncFlag(int aFuncFlag)
	{
		FuncFlag = aFuncFlag;
	}
	/** ��������<P>�������� */
	public void setFuncFlag(String aFuncFlag)
	{
		if (aFuncFlag != null && !aFuncFlag.equals(""))
		{
			Integer tInteger = new Integer(aFuncFlag);
			int i = tInteger.intValue();
			FuncFlag = i;
		}
	}

	/** ��������<P>�������� */
	public int getTranDate()
	{
		return TranDate;
	}
	/** �������� */
	public void setTranDate(int aTranDate)
	{
		TranDate = aTranDate;
	}
	/** ��������<P>�������� */
	public void setTranDate(String aTranDate)
	{
		if (aTranDate != null && !aTranDate.equals(""))
		{
			Integer tInteger = new Integer(aTranDate);
			int i = tInteger.intValue();
			TranDate = i;
		}
	}

	/** ����ʱ��<P>����ʱ�� */
	public int getTranTime()
	{
		return TranTime;
	}
	/** ����ʱ�� */
	public void setTranTime(int aTranTime)
	{
		TranTime = aTranTime;
	}
	/** ����ʱ��<P>����ʱ�� */
	public void setTranTime(String aTranTime)
	{
		if (aTranTime != null && !aTranTime.equals(""))
		{
			Integer tInteger = new Integer(aTranTime);
			int i = tInteger.intValue();
			TranTime = i;
		}
	}

	/** ����<P>Ͷ����(ӡˢ)�� */
	public String getChannel()
	{
		if (Channel != null && !Channel.equals("") && SysConst.CHANGECHARSET == true)
		{
			Channel = StrTool.unicodeToGBK(Channel);
		}
		return Channel;
	}
	/** ���� */
	public void setChannel(String aChannel)
	{
		Channel = aChannel;
	}
	/** ��Ӧ�����к�<P>������ */
	public String getBankAccountNum()
	{
		if (BankAccountNum != null && !BankAccountNum.equals("") && SysConst.CHANGECHARSET == true)
		{
			BankAccountNum = StrTool.unicodeToGBK(BankAccountNum);
		}
		return BankAccountNum;
	}
	/** ��Ӧ�����к� */
	public void setBankAccountNum(String aBankAccountNum)
	{
		BankAccountNum = aBankAccountNum;
	}
	/** �����ն˺�<P>����������ĺţ��籣����ͬ��ӡˢ�š������ŵ� */
	public String getTerminalCode()
	{
		if (TerminalCode != null && !TerminalCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			TerminalCode = StrTool.unicodeToGBK(TerminalCode);
		}
		return TerminalCode;
	}
	/** �����ն˺� */
	public void setTerminalCode(String aTerminalCode)
	{
		TerminalCode = aTerminalCode;
	}
	/** ���׽��<P>0-�ɹ���1-ʧ�� */
	public String getRCode()
	{
		if (RCode != null && !RCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			RCode = StrTool.unicodeToGBK(RCode);
		}
		return RCode;
	}
	/** ���׽�� */
	public void setRCode(String aRCode)
	{
		RCode = aRCode;
	}
	/** �������<P>������� */
	public String getRText()
	{
		if (RText != null && !RText.equals("") && SysConst.CHANGECHARSET == true)
		{
			RText = StrTool.unicodeToGBK(RText);
		}
		return RText;
	}
	/** ������� */
	public void setRText(String aRText)
	{
		RText = aRText;
	}
	/** �����ʱ<P>�����ʱ����λ��(s) */
	public int getUsedTime()
	{
		return UsedTime;
	}
	/** �����ʱ */
	public void setUsedTime(int aUsedTime)
	{
		UsedTime = aUsedTime;
	}
	/** �����ʱ<P>�����ʱ����λ��(s) */
	public void setUsedTime(String aUsedTime)
	{
		if (aUsedTime != null && !aUsedTime.equals(""))
		{
			Integer tInteger = new Integer(aUsedTime);
			int i = tInteger.intValue();
			UsedTime = i;
		}
	}

	/** ����1<P> */
	public String getBak1()
	{
		if (Bak1 != null && !Bak1.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak1 = StrTool.unicodeToGBK(Bak1);
		}
		return Bak1;
	}
	/** ����1 */
	public void setBak1(String aBak1)
	{
		Bak1 = aBak1;
	}
	/** ����2<P> */
	public String getBak2()
	{
		if (Bak2 != null && !Bak2.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak2 = StrTool.unicodeToGBK(Bak2);
		}
		return Bak2;
	}
	/** ����2 */
	public void setBak2(String aBak2)
	{
		Bak2 = aBak2;
	}
	/** ����3<P> */
	public String getBak3()
	{
		if (Bak3 != null && !Bak3.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak3 = StrTool.unicodeToGBK(Bak3);
		}
		return Bak3;
	}
	/** ����3 */
	public void setBak3(String aBak3)
	{
		Bak3 = aBak3;
	}
	/** ����4<P> */
	public String getBak4()
	{
		if (Bak4 != null && !Bak4.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak4 = StrTool.unicodeToGBK(Bak4);
		}
		return Bak4;
	}
	/** ����4 */
	public void setBak4(String aBak4)
	{
		Bak4 = aBak4;
	}
	/** ����5<P> */
	public String getBak5()
	{
		if (Bak5 != null && !Bak5.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak5 = StrTool.unicodeToGBK(Bak5);
		}
		return Bak5;
	}
	/** ����5 */
	public void setBak5(String aBak5)
	{
		Bak5 = aBak5;
	}
	/** ���뱨��<P> */
	public String getInNoDoc()
	{
		if (InNoDoc != null && !InNoDoc.equals("") && SysConst.CHANGECHARSET == true)
		{
			InNoDoc = StrTool.unicodeToGBK(InNoDoc);
		}
		return InNoDoc;
	}
	/** ���뱨�� */
	public void setInNoDoc(String aInNoDoc)
	{
		InNoDoc = aInNoDoc;
	}
	/** �����׼����<P> */
	public String getInDoc()
	{
		if (InDoc != null && !InDoc.equals("") && SysConst.CHANGECHARSET == true)
		{
			InDoc = StrTool.unicodeToGBK(InDoc);
		}
		return InDoc;
	}
	/** �����׼���� */
	public void setInDoc(String aInDoc)
	{
		InDoc = aInDoc;
	}
	/** ���ر���<P> */
	public String getOutNoDoc()
	{
		if (OutNoDoc != null && !OutNoDoc.equals("") && SysConst.CHANGECHARSET == true)
		{
			OutNoDoc = StrTool.unicodeToGBK(OutNoDoc);
		}
		return OutNoDoc;
	}
	/** ���ر��� */
	public void setOutNoDoc(String aOutNoDoc)
	{
		OutNoDoc = aOutNoDoc;
	}
	/** ���ر�׼����<P> */
	public String getOutDoc()
	{
		if (OutDoc != null && !OutDoc.equals("") && SysConst.CHANGECHARSET == true)
		{
			OutDoc = StrTool.unicodeToGBK(OutDoc);
		}
		return OutDoc;
	}
	/** ���ر�׼���� */
	public void setOutDoc(String aOutDoc)
	{
		OutDoc = aOutDoc;
	}
	/** �������<P> */
	public int getMakeDate()
	{
		return MakeDate;
	}
	/** ������� */
	public void setMakeDate(int aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	/** �������<P> */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals(""))
		{
			Integer tInteger = new Integer(aMakeDate);
			int i = tInteger.intValue();
			MakeDate = i;
		}
	}

	/** ���ʱ��<P> */
	public int getMakeTime()
	{
		return MakeTime;
	}
	/** ���ʱ�� */
	public void setMakeTime(int aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	/** ���ʱ��<P> */
	public void setMakeTime(String aMakeTime)
	{
		if (aMakeTime != null && !aMakeTime.equals(""))
		{
			Integer tInteger = new Integer(aMakeTime);
			int i = tInteger.intValue();
			MakeTime = i;
		}
	}

	/** ����޸�����<P> */
	public int getModifyDate()
	{
		return ModifyDate;
	}
	/** ����޸����� */
	public void setModifyDate(int aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	/** ����޸�����<P> */
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals(""))
		{
			Integer tInteger = new Integer(aModifyDate);
			int i = tInteger.intValue();
			ModifyDate = i;
		}
	}

	/** ����޸�ʱ��<P> */
	public int getModifyTime()
	{
		return ModifyTime;
	}
	/** ����޸�ʱ�� */
	public void setModifyTime(int aModifyTime)
	{
		ModifyTime = aModifyTime;
	}
	/** ����޸�ʱ��<P> */
	public void setModifyTime(String aModifyTime)
	{
		if (aModifyTime != null && !aModifyTime.equals(""))
		{
			Integer tInteger = new Integer(aModifyTime);
			int i = tInteger.intValue();
			ModifyTime = i;
		}
	}


	/**
	* ʹ������һ�� ICTranLogSchema ����� Schema ��ֵ
	* @param: Schema ����
	* @return: ��
	**/
	public void setSchema(ICTranLogSchema aICTranLogSchema)
	{
		this.LogNo = aICTranLogSchema.getLogNo();
		this.TranCom = aICTranLogSchema.getTranCom();
		this.ZoneNo = aICTranLogSchema.getZoneNo();
		this.NodeNo = aICTranLogSchema.getNodeNo();
		this.TranNo = aICTranLogSchema.getTranNo();
		this.TellerNo = aICTranLogSchema.getTellerNo();
		this.FuncFlag = aICTranLogSchema.getFuncFlag();
		this.TranDate = aICTranLogSchema.getTranDate();
		this.TranTime = aICTranLogSchema.getTranTime();
		this.Channel = aICTranLogSchema.getChannel();
		this.BankAccountNum = aICTranLogSchema.getBankAccountNum();
		this.TerminalCode = aICTranLogSchema.getTerminalCode();
		this.RCode = aICTranLogSchema.getRCode();
		this.RText = aICTranLogSchema.getRText();
		this.UsedTime = aICTranLogSchema.getUsedTime();
		this.Bak1 = aICTranLogSchema.getBak1();
		this.Bak2 = aICTranLogSchema.getBak2();
		this.Bak3 = aICTranLogSchema.getBak3();
		this.Bak4 = aICTranLogSchema.getBak4();
		this.Bak5 = aICTranLogSchema.getBak5();
		this.InNoDoc = aICTranLogSchema.getInNoDoc();
		this.InDoc = aICTranLogSchema.getInDoc();
		this.OutNoDoc = aICTranLogSchema.getOutNoDoc();
		this.OutDoc = aICTranLogSchema.getOutDoc();
		this.MakeDate = aICTranLogSchema.getMakeDate();
		this.MakeTime = aICTranLogSchema.getMakeTime();
		this.ModifyDate = aICTranLogSchema.getModifyDate();
		this.ModifyTime = aICTranLogSchema.getModifyTime();
	}

	/**
	* ʹ�� ResultSet �еĵ� i �и� Schema ��ֵ
	* @param: ResultSet ����; i ����
	* @return: boolean
	**/
	public boolean setSchema(ResultSet rs,int i)
	{
		try
		{
			//rs.absolute(i);		// �ǹ����α�
			this.LogNo = rs.getInt("LogNo");
			this.TranCom = rs.getInt("TranCom");
			if( rs.getString("ZoneNo") == null )
				this.ZoneNo = null;
			else
				this.ZoneNo = rs.getString("ZoneNo").trim();

			if( rs.getString("NodeNo") == null )
				this.NodeNo = null;
			else
				this.NodeNo = rs.getString("NodeNo").trim();

			if( rs.getString("TranNo") == null )
				this.TranNo = null;
			else
				this.TranNo = rs.getString("TranNo").trim();

			if( rs.getString("TellerNo") == null )
				this.TellerNo = null;
			else
				this.TellerNo = rs.getString("TellerNo").trim();

			this.FuncFlag = rs.getInt("FuncFlag");
			this.TranDate = rs.getInt("TranDate");
			this.TranTime = rs.getInt("TranTime");
			if( rs.getString("Channel") == null )
				this.Channel = null;
			else
				this.Channel = rs.getString("Channel").trim();

			if( rs.getString("BankAccountNum") == null )
				this.BankAccountNum = null;
			else
				this.BankAccountNum = rs.getString("BankAccountNum").trim();

			if( rs.getString("TerminalCode") == null )
				this.TerminalCode = null;
			else
				this.TerminalCode = rs.getString("TerminalCode").trim();

			if( rs.getString("RCode") == null )
				this.RCode = null;
			else
				this.RCode = rs.getString("RCode").trim();

			if( rs.getString("RText") == null )
				this.RText = null;
			else
				this.RText = rs.getString("RText").trim();

			this.UsedTime = rs.getInt("UsedTime");
			if( rs.getString("Bak1") == null )
				this.Bak1 = null;
			else
				this.Bak1 = rs.getString("Bak1").trim();

			if( rs.getString("Bak2") == null )
				this.Bak2 = null;
			else
				this.Bak2 = rs.getString("Bak2").trim();

			if( rs.getString("Bak3") == null )
				this.Bak3 = null;
			else
				this.Bak3 = rs.getString("Bak3").trim();

			if( rs.getString("Bak4") == null )
				this.Bak4 = null;
			else
				this.Bak4 = rs.getString("Bak4").trim();

			if( rs.getString("Bak5") == null )
				this.Bak5 = null;
			else
				this.Bak5 = rs.getString("Bak5").trim();

			if( rs.getString("InNoDoc") == null )
				this.InNoDoc = null;
			else
				this.InNoDoc = rs.getString("InNoDoc").trim();

			if( rs.getString("InDoc") == null )
				this.InDoc = null;
			else
				this.InDoc = rs.getString("InDoc").trim();

			if( rs.getString("OutNoDoc") == null )
				this.OutNoDoc = null;
			else
				this.OutNoDoc = rs.getString("OutNoDoc").trim();

			if( rs.getString("OutDoc") == null )
				this.OutDoc = null;
			else
				this.OutDoc = rs.getString("OutDoc").trim();

			this.MakeDate = rs.getInt("MakeDate");
			this.MakeTime = rs.getInt("MakeTime");
			this.ModifyDate = rs.getInt("ModifyDate");
			this.ModifyTime = rs.getInt("ModifyTime");
		}
		catch(SQLException sqle)
		{
			// @@������
			CError tError = new CError();
			tError.moduleName = "ICTranLogSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public ICTranLogSchema getSchema()
	{
		ICTranLogSchema aICTranLogSchema = new ICTranLogSchema();
		aICTranLogSchema.setSchema(this);
		return aICTranLogSchema;
	}

	public ICTranLogDB getDB()
	{
		ICTranLogDB aDBOper = new ICTranLogDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpICTranLog����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn =  ChgData.chgData(LogNo) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TranCom) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ZoneNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NodeNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TranNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TellerNo) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(FuncFlag) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TranDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TranTime) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Channel) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BankAccountNum) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TerminalCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RText) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(UsedTime) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak4) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak5) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InNoDoc) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(InDoc) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OutNoDoc) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OutDoc) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeTime) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyTime);
		return strReturn;
	}

	/**
	* ���ݽ�������˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpICTranLog>��ʷ����ƾ֤������Ϣ</A>���ֶ�
	* @param: strMessage������һ����¼���ݵ��ַ���
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			LogNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			TranCom= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			ZoneNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			NodeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			TranNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			TellerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FuncFlag= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			TranDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			TranTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			Channel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			BankAccountNum = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			TerminalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			RCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			RText = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			UsedTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			Bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			Bak3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Bak4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Bak5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			InNoDoc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			InDoc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			OutNoDoc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			OutDoc = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).intValue();
			MakeTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).intValue();
			ModifyDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).intValue();
			ModifyTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,28,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@������
			CError tError = new CError();
			tError.moduleName = "ICTranLogSchema";
			tError.functionName = "decode";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	/**
	* ȡ�ö�Ӧ���������String��ʽ���ֶ�ֵ
	* @param: FCode ϣ��ȡ�õ��ֶ���
	* @return: FValue String��ʽ���ֶ�ֵ
	*			FValue = ""		û��ƥ����ֶ�
	*			FValue = "null"	�ֶ�ֵΪnull
	**/
	public String getV(String FCode)
	{
		String strReturn = "";
		if (FCode.equals("LogNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(LogNo));
		}
		if (FCode.equals("TranCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranCom));
		}
		if (FCode.equals("ZoneNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZoneNo));
		}
		if (FCode.equals("NodeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeNo));
		}
		if (FCode.equals("TranNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranNo));
		}
		if (FCode.equals("TellerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TellerNo));
		}
		if (FCode.equals("FuncFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FuncFlag));
		}
		if (FCode.equals("TranDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranDate));
		}
		if (FCode.equals("TranTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranTime));
		}
		if (FCode.equals("Channel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Channel));
		}
		if (FCode.equals("BankAccountNum"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankAccountNum));
		}
		if (FCode.equals("TerminalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TerminalCode));
		}
		if (FCode.equals("RCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RCode));
		}
		if (FCode.equals("RText"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RText));
		}
		if (FCode.equals("UsedTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UsedTime));
		}
		if (FCode.equals("Bak1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak1));
		}
		if (FCode.equals("Bak2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak2));
		}
		if (FCode.equals("Bak3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak3));
		}
		if (FCode.equals("Bak4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak4));
		}
		if (FCode.equals("Bak5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak5));
		}
		if (FCode.equals("InNoDoc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InNoDoc));
		}
		if (FCode.equals("InDoc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(InDoc));
		}
		if (FCode.equals("OutNoDoc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutNoDoc));
		}
		if (FCode.equals("OutDoc"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutDoc));
		}
		if (FCode.equals("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeDate));
		}
		if (FCode.equals("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equals("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyDate));
		}
		if (FCode.equals("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (strReturn.equals(""))
		{
			strReturn = "null";
		}

		return strReturn;
	}


	/**
	* ȡ��Schema��ָ������ֵ����Ӧ���ֶ�ֵ
	* @param: nFieldIndex ָ�����ֶ�����ֵ
	* @return: �ֶ�ֵ��
	*          ���û�ж�Ӧ���ֶΣ�����""
	*          ����ֶ�ֵΪ�գ�����"null"
	**/
	public String getV(int nFieldIndex)
	{
		String strFieldValue = "";
		switch(nFieldIndex) {
			case 0:
				strFieldValue = String.valueOf(LogNo);
				break;
			case 1:
				strFieldValue = String.valueOf(TranCom);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ZoneNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(NodeNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(TranNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(TellerNo);
				break;
			case 6:
				strFieldValue = String.valueOf(FuncFlag);
				break;
			case 7:
				strFieldValue = String.valueOf(TranDate);
				break;
			case 8:
				strFieldValue = String.valueOf(TranTime);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Channel);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(BankAccountNum);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(TerminalCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(RCode);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(RText);
				break;
			case 14:
				strFieldValue = String.valueOf(UsedTime);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Bak1);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Bak2);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(Bak3);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Bak4);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Bak5);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(InNoDoc);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(InDoc);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(OutNoDoc);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(OutDoc);
				break;
			case 24:
				strFieldValue = String.valueOf(MakeDate);
				break;
			case 25:
				strFieldValue = String.valueOf(MakeTime);
				break;
			case 26:
				strFieldValue = String.valueOf(ModifyDate);
				break;
			case 27:
				strFieldValue = String.valueOf(ModifyTime);
				break;
			default:
				strFieldValue = "";
		};
		if( strFieldValue.equals("") ) {
			strFieldValue = "null";
		}
		return strFieldValue;
	}

	/**
	* ���ö�Ӧ���������String��ʽ���ֶ�ֵ
	* @param: FCode ϣ��ȡ�õ��ֶ���
	* @return: FValue String��ʽ���ֶ�ֵ
	*			FValue = ""		û��ƥ����ֶ�
	*			FValue = "null"	�ֶ�ֵΪnull
	**/
	public boolean setV(String FCode ,String FValue)
	{
		if( StrTool.cTrim( FCode ).equals( "" ))
			return false;

		if (FCode.equals("LogNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				LogNo = i;
			}
		}
		if (FCode.equals("TranCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TranCom = i;
			}
		}
		if (FCode.equals("ZoneNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZoneNo = FValue.trim();
			}
			else
				ZoneNo = null;
		}
		if (FCode.equals("NodeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeNo = FValue.trim();
			}
			else
				NodeNo = null;
		}
		if (FCode.equals("TranNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TranNo = FValue.trim();
			}
			else
				TranNo = null;
		}
		if (FCode.equals("TellerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TellerNo = FValue.trim();
			}
			else
				TellerNo = null;
		}
		if (FCode.equals("FuncFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				FuncFlag = i;
			}
		}
		if (FCode.equals("TranDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TranDate = i;
			}
		}
		if (FCode.equals("TranTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TranTime = i;
			}
		}
		if (FCode.equals("Channel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Channel = FValue.trim();
			}
			else
				Channel = null;
		}
		if (FCode.equals("BankAccountNum"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankAccountNum = FValue.trim();
			}
			else
				BankAccountNum = null;
		}
		if (FCode.equals("TerminalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TerminalCode = FValue.trim();
			}
			else
				TerminalCode = null;
		}
		if (FCode.equals("RCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RCode = FValue.trim();
			}
			else
				RCode = null;
		}
		if (FCode.equals("RText"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RText = FValue.trim();
			}
			else
				RText = null;
		}
		if (FCode.equals("UsedTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				UsedTime = i;
			}
		}
		if (FCode.equals("Bak1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak1 = FValue.trim();
			}
			else
				Bak1 = null;
		}
		if (FCode.equals("Bak2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak2 = FValue.trim();
			}
			else
				Bak2 = null;
		}
		if (FCode.equals("Bak3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak3 = FValue.trim();
			}
			else
				Bak3 = null;
		}
		if (FCode.equals("Bak4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak4 = FValue.trim();
			}
			else
				Bak4 = null;
		}
		if (FCode.equals("Bak5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak5 = FValue.trim();
			}
			else
				Bak5 = null;
		}
		if (FCode.equals("InNoDoc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InNoDoc = FValue.trim();
			}
			else
				InNoDoc = null;
		}
		if (FCode.equals("InDoc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				InDoc = FValue.trim();
			}
			else
				InDoc = null;
		}
		if (FCode.equals("OutNoDoc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutNoDoc = FValue.trim();
			}
			else
				OutNoDoc = null;
		}
		if (FCode.equals("OutDoc"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutDoc = FValue.trim();
			}
			else
				OutDoc = null;
		}
		if (FCode.equals("MakeDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MakeDate = i;
			}
		}
		if (FCode.equals("MakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MakeTime = i;
			}
		}
		if (FCode.equals("ModifyDate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ModifyDate = i;
			}
		}
		if (FCode.equals("ModifyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ModifyTime = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		ICTranLogSchema other = (ICTranLogSchema)otherObject;
		return
			LogNo == other.getLogNo()
			&& TranCom == other.getTranCom()
			&& ZoneNo.equals(other.getZoneNo())
			&& NodeNo.equals(other.getNodeNo())
			&& TranNo.equals(other.getTranNo())
			&& TellerNo.equals(other.getTellerNo())
			&& FuncFlag == other.getFuncFlag()
			&& TranDate == other.getTranDate()
			&& TranTime == other.getTranTime()
			&& Channel.equals(other.getChannel())
			&& BankAccountNum.equals(other.getBankAccountNum())
			&& TerminalCode.equals(other.getTerminalCode())
			&& RCode.equals(other.getRCode())
			&& RText.equals(other.getRText())
			&& UsedTime == other.getUsedTime()
			&& Bak1.equals(other.getBak1())
			&& Bak2.equals(other.getBak2())
			&& Bak3.equals(other.getBak3())
			&& Bak4.equals(other.getBak4())
			&& Bak5.equals(other.getBak5())
			&& InNoDoc.equals(other.getInNoDoc())
			&& InDoc.equals(other.getInDoc())
			&& OutNoDoc.equals(other.getOutNoDoc())
			&& OutDoc.equals(other.getOutDoc())
			&& MakeDate == other.getMakeDate()
			&& MakeTime == other.getMakeTime()
			&& ModifyDate == other.getModifyDate()
			&& ModifyTime == other.getModifyTime();
	}

	/**
	* ȡ��Schemaӵ���ֶε�����
	**/
	public int getFieldCount()
	{
 		return FIELDNUM;
	}

	/**
	* ȡ��Schema��ָ���ֶ�������Ӧ������ֵ
	* ���û�ж�Ӧ���ֶΣ�����-1
	**/
	public int getFieldIndex(String strFieldName)
	{
		if( strFieldName.equals("LogNo") ) {
			return 0;
		}
		if( strFieldName.equals("TranCom") ) {
			return 1;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return 2;
		}
		if( strFieldName.equals("NodeNo") ) {
			return 3;
		}
		if( strFieldName.equals("TranNo") ) {
			return 4;
		}
		if( strFieldName.equals("TellerNo") ) {
			return 5;
		}
		if( strFieldName.equals("FuncFlag") ) {
			return 6;
		}
		if( strFieldName.equals("TranDate") ) {
			return 7;
		}
		if( strFieldName.equals("TranTime") ) {
			return 8;
		}
		if( strFieldName.equals("Channel") ) {
			return 9;
		}
		if( strFieldName.equals("BankAccountNum") ) {
			return 10;
		}
		if( strFieldName.equals("TerminalCode") ) {
			return 11;
		}
		if( strFieldName.equals("RCode") ) {
			return 12;
		}
		if( strFieldName.equals("RText") ) {
			return 13;
		}
		if( strFieldName.equals("UsedTime") ) {
			return 14;
		}
		if( strFieldName.equals("Bak1") ) {
			return 15;
		}
		if( strFieldName.equals("Bak2") ) {
			return 16;
		}
		if( strFieldName.equals("Bak3") ) {
			return 17;
		}
		if( strFieldName.equals("Bak4") ) {
			return 18;
		}
		if( strFieldName.equals("Bak5") ) {
			return 19;
		}
		if( strFieldName.equals("InNoDoc") ) {
			return 20;
		}
		if( strFieldName.equals("InDoc") ) {
			return 21;
		}
		if( strFieldName.equals("OutNoDoc") ) {
			return 22;
		}
		if( strFieldName.equals("OutDoc") ) {
			return 23;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 24;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 25;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 26;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 27;
		}
		return -1;
	}

	/**
	* ȡ��Schema��ָ������ֵ����Ӧ���ֶ���
	* ���û�ж�Ӧ���ֶΣ�����""
	**/
	public String getFieldName(int nFieldIndex)
	{
		String strFieldName = "";
		switch(nFieldIndex) {
			case 0:
				strFieldName = "LogNo";
				break;
			case 1:
				strFieldName = "TranCom";
				break;
			case 2:
				strFieldName = "ZoneNo";
				break;
			case 3:
				strFieldName = "NodeNo";
				break;
			case 4:
				strFieldName = "TranNo";
				break;
			case 5:
				strFieldName = "TellerNo";
				break;
			case 6:
				strFieldName = "FuncFlag";
				break;
			case 7:
				strFieldName = "TranDate";
				break;
			case 8:
				strFieldName = "TranTime";
				break;
			case 9:
				strFieldName = "Channel";
				break;
			case 10:
				strFieldName = "BankAccountNum";
				break;
			case 11:
				strFieldName = "TerminalCode";
				break;
			case 12:
				strFieldName = "RCode";
				break;
			case 13:
				strFieldName = "RText";
				break;
			case 14:
				strFieldName = "UsedTime";
				break;
			case 15:
				strFieldName = "Bak1";
				break;
			case 16:
				strFieldName = "Bak2";
				break;
			case 17:
				strFieldName = "Bak3";
				break;
			case 18:
				strFieldName = "Bak4";
				break;
			case 19:
				strFieldName = "Bak5";
				break;
			case 20:
				strFieldName = "InNoDoc";
				break;
			case 21:
				strFieldName = "InDoc";
				break;
			case 22:
				strFieldName = "OutNoDoc";
				break;
			case 23:
				strFieldName = "OutDoc";
				break;
			case 24:
				strFieldName = "MakeDate";
				break;
			case 25:
				strFieldName = "MakeTime";
				break;
			case 26:
				strFieldName = "ModifyDate";
				break;
			case 27:
				strFieldName = "ModifyTime";
				break;
			default:
				strFieldName = "";
		};
		return strFieldName;
	}

	/**
	* ȡ��Schema��ָ���ֶ�������Ӧ���ֶ�����
	* ���û�ж�Ӧ���ֶΣ�����Schema.TYPE_NOFOUND
	**/
	public int getFieldType(String strFieldName)
	{
		if( strFieldName.equals("LogNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TranCom") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TranNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TellerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FuncFlag") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TranDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TranTime") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Channel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankAccountNum") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TerminalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RText") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UsedTime") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Bak1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bak2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bak3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bak4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bak5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InNoDoc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("InDoc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutNoDoc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutDoc") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_INT;
		}
		return Schema.TYPE_NOFOUND;
	}

	/**
	* ȡ��Schema��ָ������ֵ����Ӧ���ֶ�����
	* ���û�ж�Ӧ���ֶΣ�����Schema.TYPE_NOFOUND
	**/
	public int getFieldType(int nFieldIndex)
	{
		int nFieldType = Schema.TYPE_NOFOUND;
		switch(nFieldIndex) {
			case 0:
				nFieldType = Schema.TYPE_INT;
				break;
			case 1:
				nFieldType = Schema.TYPE_INT;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 4:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_INT;
				break;
			case 7:
				nFieldType = Schema.TYPE_INT;
				break;
			case 8:
				nFieldType = Schema.TYPE_INT;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 12:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_INT;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_INT;
				break;
			case 25:
				nFieldType = Schema.TYPE_INT;
				break;
			case 26:
				nFieldType = Schema.TYPE_INT;
				break;
			case 27:
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
