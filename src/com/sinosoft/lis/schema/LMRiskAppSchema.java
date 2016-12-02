/*
 * <p>ClassName: LMRiskAppSchema </p>
 * <p>Description: DB�� Schema ���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: �����Ա����
 * @CreateDate��2012-06-14
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LMRiskAppDB;

public class LMRiskAppSchema implements Schema
{
	// @Field
	/** ���ֱ��� */
	private String RiskCode;
	/** ���ձ��� */
	private String MainRiskCode;
	/** ���ְ汾 */
	private String RiskVer;
	/** �������� */
	private String RiskName;
	/** �����ձ�� */
	private String SubRiskFlag;
	/** ���ַ��� */
	private String RiskType;
	/** ׷�ӱ��ѱ�� */
	private String FirstAddPremFlag;
	/** �ڽ�׷�ӱ��ѱ�� */
	private String AddPremFlag;
	/** Ͷ���˻���� */
	private String AccountFlag;
	/** ��Ʒ���� */
	private String GroupID;
	/** �ؼ���־ */
	private String CCIFlag;
	/** �����־ */
	private String ADDFlag;
	/** �ش������ձ�־ */
	private String SubRiskNecessary;
	/** ����Ʒ�ۻ���־ */
	private String PremADDFlag;
	/** �ּۼ�ֵ�� */
	private String CVTable;
	/** ��Ʒ���ʱ� */
	private String PATable;
	/** �����־ */
	private String CalFlag;
	/** �������� */
	private Date StartDate;
	/** ͣ������ */
	private Date EndDate;
	/** ҳ����� */
	private double WebMult;
	/** ҳ�汣�� */
	private double WebPrem;
	/** ҳ�汣�� */
	private double WebAmnt;
	/** ҳ�汣���������� */
	private String WebInsuYearFlag;
	/** ҳ�汣������ */
	private int WebInsuYear;
	/** ҳ��ɷ��������� */
	private String WebPayEndYearFlag;
	/** ҳ��ɷ����� */
	private int WebPayEndYear;
	/** ҳ��ɷѷ�ʽ */
	private String WebPayIntv;
	/** ҳ��Ͷ���˻����� */
	private String WebAccRate;
	/** ҳ��Ͷ���˻����� */
	private String WebAccCode;
	/** ҳ��Ͷ���˻�Ͷ������ */
	private String WebAccTimeFlag;

	public static final int FIELDNUM = 30;	// ���ݿ����ֶθ���

	private static String[] PK;				// ����

	private FDate fDate = new FDate();		// ��������

	public CErrors mErrors;			// ������Ϣ

	// @Constructor
	public LMRiskAppSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RiskCode";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** ���ֱ���<P>���ֱ��� */
	public String getRiskCode()
	{
		if (RiskCode != null && !RiskCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			RiskCode = StrTool.unicodeToGBK(RiskCode);
		}
		return RiskCode;
	}
	/** ���ֱ��� */
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/** ���ձ���<P> */
	public String getMainRiskCode()
	{
		if (MainRiskCode != null && !MainRiskCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			MainRiskCode = StrTool.unicodeToGBK(MainRiskCode);
		}
		return MainRiskCode;
	}
	/** ���ձ��� */
	public void setMainRiskCode(String aMainRiskCode)
	{
		MainRiskCode = aMainRiskCode;
	}
	/** ���ְ汾<P>���ְ汾 */
	public String getRiskVer()
	{
		if (RiskVer != null && !RiskVer.equals("") && SysConst.CHANGECHARSET == true)
		{
			RiskVer = StrTool.unicodeToGBK(RiskVer);
		}
		return RiskVer;
	}
	/** ���ְ汾 */
	public void setRiskVer(String aRiskVer)
	{
		RiskVer = aRiskVer;
	}
	/** ��������<P>�������� */
	public String getRiskName()
	{
		if (RiskName != null && !RiskName.equals("") && SysConst.CHANGECHARSET == true)
		{
			RiskName = StrTool.unicodeToGBK(RiskName);
		}
		return RiskName;
	}
	/** �������� */
	public void setRiskName(String aRiskName)
	{
		RiskName = aRiskName;
	}
	/** �����ձ��<P>�����ձ�� M--����(Main) S--����(Sub) A--���߶����ԡ� */
	public String getSubRiskFlag()
	{
		if (SubRiskFlag != null && !SubRiskFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			SubRiskFlag = StrTool.unicodeToGBK(SubRiskFlag);
		}
		return SubRiskFlag;
	}
	/** �����ձ�� */
	public void setSubRiskFlag(String aSubRiskFlag)
	{
		SubRiskFlag = aSubRiskFlag;
	}
	/** ���ַ���<P>1 -��ͳ��2 -�ֺ�3- Ͷ��4-���� */
	public String getRiskType()
	{
		if (RiskType != null && !RiskType.equals("") && SysConst.CHANGECHARSET == true)
		{
			RiskType = StrTool.unicodeToGBK(RiskType);
		}
		return RiskType;
	}
	/** ���ַ��� */
	public void setRiskType(String aRiskType)
	{
		RiskType = aRiskType;
	}
	/** ׷�ӱ��ѱ��<P>Y-������???��׷�ӱ��� N-������׷�ӱ���-�ݲ����� */
	public String getFirstAddPremFlag()
	{
		if (FirstAddPremFlag != null && !FirstAddPremFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			FirstAddPremFlag = StrTool.unicodeToGBK(FirstAddPremFlag);
		}
		return FirstAddPremFlag;
	}
	/** ׷�ӱ��ѱ�� */
	public void setFirstAddPremFlag(String aFirstAddPremFlag)
	{
		FirstAddPremFlag = aFirstAddPremFlag;
	}
	/** �ڽ�׷�ӱ��ѱ��<P> */
	public String getAddPremFlag()
	{
		if (AddPremFlag != null && !AddPremFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			AddPremFlag = StrTool.unicodeToGBK(AddPremFlag);
		}
		return AddPremFlag;
	}
	/** �ڽ�׷�ӱ��ѱ�� */
	public void setAddPremFlag(String aAddPremFlag)
	{
		AddPremFlag = aAddPremFlag;
	}
	/** Ͷ���˻����<P> */
	public String getAccountFlag()
	{
		if (AccountFlag != null && !AccountFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			AccountFlag = StrTool.unicodeToGBK(AccountFlag);
		}
		return AccountFlag;
	}
	/** Ͷ���˻���� */
	public void setAccountFlag(String aAccountFlag)
	{
		AccountFlag = aAccountFlag;
	}
	/** ��Ʒ����<P> */
	public String getGroupID()
	{
		if (GroupID != null && !GroupID.equals("") && SysConst.CHANGECHARSET == true)
		{
			GroupID = StrTool.unicodeToGBK(GroupID);
		}
		return GroupID;
	}
	/** ��Ʒ���� */
	public void setGroupID(String aGroupID)
	{
		GroupID = aGroupID;
	}
	/** �ؼ���־<P> */
	public String getCCIFlag()
	{
		if (CCIFlag != null && !CCIFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			CCIFlag = StrTool.unicodeToGBK(CCIFlag);
		}
		return CCIFlag;
	}
	/** �ؼ���־ */
	public void setCCIFlag(String aCCIFlag)
	{
		CCIFlag = aCCIFlag;
	}
	/** �����־<P> */
	public String getADDFlag()
	{
		if (ADDFlag != null && !ADDFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			ADDFlag = StrTool.unicodeToGBK(ADDFlag);
		}
		return ADDFlag;
	}
	/** �����־ */
	public void setADDFlag(String aADDFlag)
	{
		ADDFlag = aADDFlag;
	}
	/** �ش������ձ�־<P> */
	public String getSubRiskNecessary()
	{
		if (SubRiskNecessary != null && !SubRiskNecessary.equals("") && SysConst.CHANGECHARSET == true)
		{
			SubRiskNecessary = StrTool.unicodeToGBK(SubRiskNecessary);
		}
		return SubRiskNecessary;
	}
	/** �ش������ձ�־ */
	public void setSubRiskNecessary(String aSubRiskNecessary)
	{
		SubRiskNecessary = aSubRiskNecessary;
	}
	/** ����Ʒ�ۻ���־<P> */
	public String getPremADDFlag()
	{
		if (PremADDFlag != null && !PremADDFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			PremADDFlag = StrTool.unicodeToGBK(PremADDFlag);
		}
		return PremADDFlag;
	}
	/** ����Ʒ�ۻ���־ */
	public void setPremADDFlag(String aPremADDFlag)
	{
		PremADDFlag = aPremADDFlag;
	}
	/** �ּۼ�ֵ��<P> */
	public String getCVTable()
	{
		if (CVTable != null && !CVTable.equals("") && SysConst.CHANGECHARSET == true)
		{
			CVTable = StrTool.unicodeToGBK(CVTable);
		}
		return CVTable;
	}
	/** �ּۼ�ֵ�� */
	public void setCVTable(String aCVTable)
	{
		CVTable = aCVTable;
	}
	/** ��Ʒ���ʱ�<P> */
	public String getPATable()
	{
		if (PATable != null && !PATable.equals("") && SysConst.CHANGECHARSET == true)
		{
			PATable = StrTool.unicodeToGBK(PATable);
		}
		return PATable;
	}
	/** ��Ʒ���ʱ� */
	public void setPATable(String aPATable)
	{
		PATable = aPATable;
	}
	/** �����־<P> */
	public String getCalFlag()
	{
		if (CalFlag != null && !CalFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			CalFlag = StrTool.unicodeToGBK(CalFlag);
		}
		return CalFlag;
	}
	/** �����־ */
	public void setCalFlag(String aCalFlag)
	{
		CalFlag = aCalFlag;
	}
	/** ��������<P>�������� */
	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	/** �������� */
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	/** ��������<P>�������� */
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
	}

	/** ͣ������<P> */
	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	/** ͣ������ */
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	/** ͣ������<P> */
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}

	/** ҳ�����<P> */
	public double getWebMult()
	{
		return WebMult;
	}
	/** ҳ����� */
	public void setWebMult(double aWebMult)
	{
		WebMult = aWebMult;
	}
	/** ҳ�����<P> */
	public void setWebMult(String aWebMult)
	{
		if (aWebMult != null && !aWebMult.equals(""))
		{
			Double tDouble = new Double(aWebMult);
			double d = tDouble.doubleValue();
			WebMult = d;
		}
	}

	/** ҳ�汣��<P> */
	public double getWebPrem()
	{
		return WebPrem;
	}
	/** ҳ�汣�� */
	public void setWebPrem(double aWebPrem)
	{
		WebPrem = aWebPrem;
	}
	/** ҳ�汣��<P> */
	public void setWebPrem(String aWebPrem)
	{
		if (aWebPrem != null && !aWebPrem.equals(""))
		{
			Double tDouble = new Double(aWebPrem);
			double d = tDouble.doubleValue();
			WebPrem = d;
		}
	}

	/** ҳ�汣��<P> */
	public double getWebAmnt()
	{
		return WebAmnt;
	}
	/** ҳ�汣�� */
	public void setWebAmnt(double aWebAmnt)
	{
		WebAmnt = aWebAmnt;
	}
	/** ҳ�汣��<P> */
	public void setWebAmnt(String aWebAmnt)
	{
		if (aWebAmnt != null && !aWebAmnt.equals(""))
		{
			Double tDouble = new Double(aWebAmnt);
			double d = tDouble.doubleValue();
			WebAmnt = d;
		}
	}

	/** ҳ�汣����������<P> */
	public String getWebInsuYearFlag()
	{
		if (WebInsuYearFlag != null && !WebInsuYearFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			WebInsuYearFlag = StrTool.unicodeToGBK(WebInsuYearFlag);
		}
		return WebInsuYearFlag;
	}
	/** ҳ�汣���������� */
	public void setWebInsuYearFlag(String aWebInsuYearFlag)
	{
		WebInsuYearFlag = aWebInsuYearFlag;
	}
	/** ҳ�汣������<P> */
	public int getWebInsuYear()
	{
		return WebInsuYear;
	}
	/** ҳ�汣������ */
	public void setWebInsuYear(int aWebInsuYear)
	{
		WebInsuYear = aWebInsuYear;
	}
	/** ҳ�汣������<P> */
	public void setWebInsuYear(String aWebInsuYear)
	{
		if (aWebInsuYear != null && !aWebInsuYear.equals(""))
		{
			Integer tInteger = new Integer(aWebInsuYear);
			int i = tInteger.intValue();
			WebInsuYear = i;
		}
	}

	/** ҳ��ɷ���������<P> */
	public String getWebPayEndYearFlag()
	{
		if (WebPayEndYearFlag != null && !WebPayEndYearFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			WebPayEndYearFlag = StrTool.unicodeToGBK(WebPayEndYearFlag);
		}
		return WebPayEndYearFlag;
	}
	/** ҳ��ɷ��������� */
	public void setWebPayEndYearFlag(String aWebPayEndYearFlag)
	{
		WebPayEndYearFlag = aWebPayEndYearFlag;
	}
	/** ҳ��ɷ�����<P> */
	public int getWebPayEndYear()
	{
		return WebPayEndYear;
	}
	/** ҳ��ɷ����� */
	public void setWebPayEndYear(int aWebPayEndYear)
	{
		WebPayEndYear = aWebPayEndYear;
	}
	/** ҳ��ɷ�����<P> */
	public void setWebPayEndYear(String aWebPayEndYear)
	{
		if (aWebPayEndYear != null && !aWebPayEndYear.equals(""))
		{
			Integer tInteger = new Integer(aWebPayEndYear);
			int i = tInteger.intValue();
			WebPayEndYear = i;
		}
	}

	/** ҳ��ɷѷ�ʽ<P> */
	public String getWebPayIntv()
	{
		if (WebPayIntv != null && !WebPayIntv.equals("") && SysConst.CHANGECHARSET == true)
		{
			WebPayIntv = StrTool.unicodeToGBK(WebPayIntv);
		}
		return WebPayIntv;
	}
	/** ҳ��ɷѷ�ʽ */
	public void setWebPayIntv(String aWebPayIntv)
	{
		WebPayIntv = aWebPayIntv;
	}
	/** ҳ��Ͷ���˻�����<P> */
	public String getWebAccRate()
	{
		if (WebAccRate != null && !WebAccRate.equals("") && SysConst.CHANGECHARSET == true)
		{
			WebAccRate = StrTool.unicodeToGBK(WebAccRate);
		}
		return WebAccRate;
	}
	/** ҳ��Ͷ���˻����� */
	public void setWebAccRate(String aWebAccRate)
	{
		WebAccRate = aWebAccRate;
	}
	/** ҳ��Ͷ���˻�����<P> */
	public String getWebAccCode()
	{
		if (WebAccCode != null && !WebAccCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			WebAccCode = StrTool.unicodeToGBK(WebAccCode);
		}
		return WebAccCode;
	}
	/** ҳ��Ͷ���˻����� */
	public void setWebAccCode(String aWebAccCode)
	{
		WebAccCode = aWebAccCode;
	}
	/** ҳ��Ͷ���˻�Ͷ������<P> */
	public String getWebAccTimeFlag()
	{
		if (WebAccTimeFlag != null && !WebAccTimeFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			WebAccTimeFlag = StrTool.unicodeToGBK(WebAccTimeFlag);
		}
		return WebAccTimeFlag;
	}
	/** ҳ��Ͷ���˻�Ͷ������ */
	public void setWebAccTimeFlag(String aWebAccTimeFlag)
	{
		WebAccTimeFlag = aWebAccTimeFlag;
	}

	/**
	* ʹ������һ�� LMRiskAppSchema ����� Schema ��ֵ
	* @param: Schema ����
	* @return: ��
	**/
	public void setSchema(LMRiskAppSchema aLMRiskAppSchema)
	{
		this.RiskCode = aLMRiskAppSchema.getRiskCode();
		this.MainRiskCode = aLMRiskAppSchema.getMainRiskCode();
		this.RiskVer = aLMRiskAppSchema.getRiskVer();
		this.RiskName = aLMRiskAppSchema.getRiskName();
		this.SubRiskFlag = aLMRiskAppSchema.getSubRiskFlag();
		this.RiskType = aLMRiskAppSchema.getRiskType();
		this.FirstAddPremFlag = aLMRiskAppSchema.getFirstAddPremFlag();
		this.AddPremFlag = aLMRiskAppSchema.getAddPremFlag();
		this.AccountFlag = aLMRiskAppSchema.getAccountFlag();
		this.GroupID = aLMRiskAppSchema.getGroupID();
		this.CCIFlag = aLMRiskAppSchema.getCCIFlag();
		this.ADDFlag = aLMRiskAppSchema.getADDFlag();
		this.SubRiskNecessary = aLMRiskAppSchema.getSubRiskNecessary();
		this.PremADDFlag = aLMRiskAppSchema.getPremADDFlag();
		this.CVTable = aLMRiskAppSchema.getCVTable();
		this.PATable = aLMRiskAppSchema.getPATable();
		this.CalFlag = aLMRiskAppSchema.getCalFlag();
		this.StartDate = fDate.getDate( aLMRiskAppSchema.getStartDate());
		this.EndDate = fDate.getDate( aLMRiskAppSchema.getEndDate());
		this.WebMult = aLMRiskAppSchema.getWebMult();
		this.WebPrem = aLMRiskAppSchema.getWebPrem();
		this.WebAmnt = aLMRiskAppSchema.getWebAmnt();
		this.WebInsuYearFlag = aLMRiskAppSchema.getWebInsuYearFlag();
		this.WebInsuYear = aLMRiskAppSchema.getWebInsuYear();
		this.WebPayEndYearFlag = aLMRiskAppSchema.getWebPayEndYearFlag();
		this.WebPayEndYear = aLMRiskAppSchema.getWebPayEndYear();
		this.WebPayIntv = aLMRiskAppSchema.getWebPayIntv();
		this.WebAccRate = aLMRiskAppSchema.getWebAccRate();
		this.WebAccCode = aLMRiskAppSchema.getWebAccCode();
		this.WebAccTimeFlag = aLMRiskAppSchema.getWebAccTimeFlag();
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
			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("MainRiskCode") == null )
				this.MainRiskCode = null;
			else
				this.MainRiskCode = rs.getString("MainRiskCode").trim();

			if( rs.getString("RiskVer") == null )
				this.RiskVer = null;
			else
				this.RiskVer = rs.getString("RiskVer").trim();

			if( rs.getString("RiskName") == null )
				this.RiskName = null;
			else
				this.RiskName = rs.getString("RiskName").trim();

			if( rs.getString("SubRiskFlag") == null )
				this.SubRiskFlag = null;
			else
				this.SubRiskFlag = rs.getString("SubRiskFlag").trim();

			if( rs.getString("RiskType") == null )
				this.RiskType = null;
			else
				this.RiskType = rs.getString("RiskType").trim();

			if( rs.getString("FirstAddPremFlag") == null )
				this.FirstAddPremFlag = null;
			else
				this.FirstAddPremFlag = rs.getString("FirstAddPremFlag").trim();

			if( rs.getString("AddPremFlag") == null )
				this.AddPremFlag = null;
			else
				this.AddPremFlag = rs.getString("AddPremFlag").trim();

			if( rs.getString("AccountFlag") == null )
				this.AccountFlag = null;
			else
				this.AccountFlag = rs.getString("AccountFlag").trim();

			if( rs.getString("GroupID") == null )
				this.GroupID = null;
			else
				this.GroupID = rs.getString("GroupID").trim();

			if( rs.getString("CCIFlag") == null )
				this.CCIFlag = null;
			else
				this.CCIFlag = rs.getString("CCIFlag").trim();

			if( rs.getString("ADDFlag") == null )
				this.ADDFlag = null;
			else
				this.ADDFlag = rs.getString("ADDFlag").trim();

			if( rs.getString("SubRiskNecessary") == null )
				this.SubRiskNecessary = null;
			else
				this.SubRiskNecessary = rs.getString("SubRiskNecessary").trim();

			if( rs.getString("PremADDFlag") == null )
				this.PremADDFlag = null;
			else
				this.PremADDFlag = rs.getString("PremADDFlag").trim();

			if( rs.getString("CVTable") == null )
				this.CVTable = null;
			else
				this.CVTable = rs.getString("CVTable").trim();

			if( rs.getString("PATable") == null )
				this.PATable = null;
			else
				this.PATable = rs.getString("PATable").trim();

			if( rs.getString("CalFlag") == null )
				this.CalFlag = null;
			else
				this.CalFlag = rs.getString("CalFlag").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			this.WebMult = rs.getDouble("WebMult");
			this.WebPrem = rs.getDouble("WebPrem");
			this.WebAmnt = rs.getDouble("WebAmnt");
			if( rs.getString("WebInsuYearFlag") == null )
				this.WebInsuYearFlag = null;
			else
				this.WebInsuYearFlag = rs.getString("WebInsuYearFlag").trim();

			this.WebInsuYear = rs.getInt("WebInsuYear");
			if( rs.getString("WebPayEndYearFlag") == null )
				this.WebPayEndYearFlag = null;
			else
				this.WebPayEndYearFlag = rs.getString("WebPayEndYearFlag").trim();

			this.WebPayEndYear = rs.getInt("WebPayEndYear");
			if( rs.getString("WebPayIntv") == null )
				this.WebPayIntv = null;
			else
				this.WebPayIntv = rs.getString("WebPayIntv").trim();

			if( rs.getString("WebAccRate") == null )
				this.WebAccRate = null;
			else
				this.WebAccRate = rs.getString("WebAccRate").trim();

			if( rs.getString("WebAccCode") == null )
				this.WebAccCode = null;
			else
				this.WebAccCode = rs.getString("WebAccCode").trim();

			if( rs.getString("WebAccTimeFlag") == null )
				this.WebAccTimeFlag = null;
			else
				this.WebAccTimeFlag = rs.getString("WebAccTimeFlag").trim();

		}
		catch(SQLException sqle)
		{
			// @@������
			CError tError = new CError();
			tError.moduleName = "LMRiskAppSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LMRiskAppSchema getSchema()
	{
		LMRiskAppSchema aLMRiskAppSchema = new LMRiskAppSchema();
		aLMRiskAppSchema.setSchema(this);
		return aLMRiskAppSchema;
	}

	public LMRiskAppDB getDB()
	{
		LMRiskAppDB aDBOper = new LMRiskAppDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskApp����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(RiskCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MainRiskCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RiskVer) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RiskName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SubRiskFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RiskType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FirstAddPremFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AddPremFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AccountFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(GroupID) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CCIFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ADDFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SubRiskNecessary) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PremADDFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CVTable) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PATable) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CalFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( StartDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( EndDate )) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(WebMult) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(WebPrem) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(WebAmnt) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(WebInsuYearFlag) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(WebInsuYear) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(WebPayEndYearFlag) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(WebPayEndYear) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(WebPayIntv) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(WebAccRate) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(WebAccCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(WebAccTimeFlag) );
		return strReturn;
	}

	/**
	* ���ݽ�������˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskApp>��ʷ����ƾ֤������Ϣ</A>���ֶ�
	* @param: strMessage������һ����¼���ݵ��ַ���
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			MainRiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			RiskVer = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			RiskName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			SubRiskFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RiskType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			FirstAddPremFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AddPremFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AccountFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			GroupID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CCIFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			ADDFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			SubRiskNecessary = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			PremADDFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			CVTable = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			PATable = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			CalFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19,SysConst.PACKAGESPILTER));
			WebMult = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,20,SysConst.PACKAGESPILTER))).doubleValue();
			WebPrem = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,21,SysConst.PACKAGESPILTER))).doubleValue();
			WebAmnt = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,22,SysConst.PACKAGESPILTER))).doubleValue();
			WebInsuYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			WebInsuYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).intValue();
			WebPayEndYearFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			WebPayEndYear= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).intValue();
			WebPayIntv = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			WebAccRate = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			WebAccCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			WebAccTimeFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@������
			CError tError = new CError();
			tError.moduleName = "LMRiskAppSchema";
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
		if (FCode.equals("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equals("MainRiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MainRiskCode));
		}
		if (FCode.equals("RiskVer"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskVer));
		}
		if (FCode.equals("RiskName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskName));
		}
		if (FCode.equals("SubRiskFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubRiskFlag));
		}
		if (FCode.equals("RiskType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskType));
		}
		if (FCode.equals("FirstAddPremFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FirstAddPremFlag));
		}
		if (FCode.equals("AddPremFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddPremFlag));
		}
		if (FCode.equals("AccountFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AccountFlag));
		}
		if (FCode.equals("GroupID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GroupID));
		}
		if (FCode.equals("CCIFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CCIFlag));
		}
		if (FCode.equals("ADDFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ADDFlag));
		}
		if (FCode.equals("SubRiskNecessary"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SubRiskNecessary));
		}
		if (FCode.equals("PremADDFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PremADDFlag));
		}
		if (FCode.equals("CVTable"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CVTable));
		}
		if (FCode.equals("PATable"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PATable));
		}
		if (FCode.equals("CalFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalFlag));
		}
		if (FCode.equals("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equals("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equals("WebMult"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebMult));
		}
		if (FCode.equals("WebPrem"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebPrem));
		}
		if (FCode.equals("WebAmnt"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebAmnt));
		}
		if (FCode.equals("WebInsuYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebInsuYearFlag));
		}
		if (FCode.equals("WebInsuYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebInsuYear));
		}
		if (FCode.equals("WebPayEndYearFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebPayEndYearFlag));
		}
		if (FCode.equals("WebPayEndYear"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebPayEndYear));
		}
		if (FCode.equals("WebPayIntv"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebPayIntv));
		}
		if (FCode.equals("WebAccRate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebAccRate));
		}
		if (FCode.equals("WebAccCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebAccCode));
		}
		if (FCode.equals("WebAccTimeFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebAccTimeFlag));
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
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(MainRiskCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(RiskVer);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(RiskName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(SubRiskFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(FirstAddPremFlag);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AddPremFlag);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AccountFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(GroupID);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CCIFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ADDFlag);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(SubRiskNecessary);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(PremADDFlag);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(CVTable);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(PATable);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(CalFlag);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 19:
				strFieldValue = String.valueOf(WebMult);
				break;
			case 20:
				strFieldValue = String.valueOf(WebPrem);
				break;
			case 21:
				strFieldValue = String.valueOf(WebAmnt);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(WebInsuYearFlag);
				break;
			case 23:
				strFieldValue = String.valueOf(WebInsuYear);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(WebPayEndYearFlag);
				break;
			case 25:
				strFieldValue = String.valueOf(WebPayEndYear);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(WebPayIntv);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(WebAccRate);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(WebAccCode);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(WebAccTimeFlag);
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

		if (FCode.equals("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equals("MainRiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MainRiskCode = FValue.trim();
			}
			else
				MainRiskCode = null;
		}
		if (FCode.equals("RiskVer"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskVer = FValue.trim();
			}
			else
				RiskVer = null;
		}
		if (FCode.equals("RiskName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskName = FValue.trim();
			}
			else
				RiskName = null;
		}
		if (FCode.equals("SubRiskFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubRiskFlag = FValue.trim();
			}
			else
				SubRiskFlag = null;
		}
		if (FCode.equals("RiskType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskType = FValue.trim();
			}
			else
				RiskType = null;
		}
		if (FCode.equals("FirstAddPremFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FirstAddPremFlag = FValue.trim();
			}
			else
				FirstAddPremFlag = null;
		}
		if (FCode.equals("AddPremFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AddPremFlag = FValue.trim();
			}
			else
				AddPremFlag = null;
		}
		if (FCode.equals("AccountFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AccountFlag = FValue.trim();
			}
			else
				AccountFlag = null;
		}
		if (FCode.equals("GroupID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GroupID = FValue.trim();
			}
			else
				GroupID = null;
		}
		if (FCode.equals("CCIFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CCIFlag = FValue.trim();
			}
			else
				CCIFlag = null;
		}
		if (FCode.equals("ADDFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ADDFlag = FValue.trim();
			}
			else
				ADDFlag = null;
		}
		if (FCode.equals("SubRiskNecessary"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SubRiskNecessary = FValue.trim();
			}
			else
				SubRiskNecessary = null;
		}
		if (FCode.equals("PremADDFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PremADDFlag = FValue.trim();
			}
			else
				PremADDFlag = null;
		}
		if (FCode.equals("CVTable"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CVTable = FValue.trim();
			}
			else
				CVTable = null;
		}
		if (FCode.equals("PATable"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PATable = FValue.trim();
			}
			else
				PATable = null;
		}
		if (FCode.equals("CalFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalFlag = FValue.trim();
			}
			else
				CalFlag = null;
		}
		if (FCode.equals("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
		}
		if (FCode.equals("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		if (FCode.equals("WebMult"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				WebMult = d;
			}
		}
		if (FCode.equals("WebPrem"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				WebPrem = d;
			}
		}
		if (FCode.equals("WebAmnt"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				WebAmnt = d;
			}
		}
		if (FCode.equals("WebInsuYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WebInsuYearFlag = FValue.trim();
			}
			else
				WebInsuYearFlag = null;
		}
		if (FCode.equals("WebInsuYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				WebInsuYear = i;
			}
		}
		if (FCode.equals("WebPayEndYearFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WebPayEndYearFlag = FValue.trim();
			}
			else
				WebPayEndYearFlag = null;
		}
		if (FCode.equals("WebPayEndYear"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				WebPayEndYear = i;
			}
		}
		if (FCode.equals("WebPayIntv"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WebPayIntv = FValue.trim();
			}
			else
				WebPayIntv = null;
		}
		if (FCode.equals("WebAccRate"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WebAccRate = FValue.trim();
			}
			else
				WebAccRate = null;
		}
		if (FCode.equals("WebAccCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WebAccCode = FValue.trim();
			}
			else
				WebAccCode = null;
		}
		if (FCode.equals("WebAccTimeFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WebAccTimeFlag = FValue.trim();
			}
			else
				WebAccTimeFlag = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMRiskAppSchema other = (LMRiskAppSchema)otherObject;
		return
			RiskCode.equals(other.getRiskCode())
			&& MainRiskCode.equals(other.getMainRiskCode())
			&& RiskVer.equals(other.getRiskVer())
			&& RiskName.equals(other.getRiskName())
			&& SubRiskFlag.equals(other.getSubRiskFlag())
			&& RiskType.equals(other.getRiskType())
			&& FirstAddPremFlag.equals(other.getFirstAddPremFlag())
			&& AddPremFlag.equals(other.getAddPremFlag())
			&& AccountFlag.equals(other.getAccountFlag())
			&& GroupID.equals(other.getGroupID())
			&& CCIFlag.equals(other.getCCIFlag())
			&& ADDFlag.equals(other.getADDFlag())
			&& SubRiskNecessary.equals(other.getSubRiskNecessary())
			&& PremADDFlag.equals(other.getPremADDFlag())
			&& CVTable.equals(other.getCVTable())
			&& PATable.equals(other.getPATable())
			&& CalFlag.equals(other.getCalFlag())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& WebMult == other.getWebMult()
			&& WebPrem == other.getWebPrem()
			&& WebAmnt == other.getWebAmnt()
			&& WebInsuYearFlag.equals(other.getWebInsuYearFlag())
			&& WebInsuYear == other.getWebInsuYear()
			&& WebPayEndYearFlag.equals(other.getWebPayEndYearFlag())
			&& WebPayEndYear == other.getWebPayEndYear()
			&& WebPayIntv.equals(other.getWebPayIntv())
			&& WebAccRate.equals(other.getWebAccRate())
			&& WebAccCode.equals(other.getWebAccCode())
			&& WebAccTimeFlag.equals(other.getWebAccTimeFlag());
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
		if( strFieldName.equals("RiskCode") ) {
			return 0;
		}
		if( strFieldName.equals("MainRiskCode") ) {
			return 1;
		}
		if( strFieldName.equals("RiskVer") ) {
			return 2;
		}
		if( strFieldName.equals("RiskName") ) {
			return 3;
		}
		if( strFieldName.equals("SubRiskFlag") ) {
			return 4;
		}
		if( strFieldName.equals("RiskType") ) {
			return 5;
		}
		if( strFieldName.equals("FirstAddPremFlag") ) {
			return 6;
		}
		if( strFieldName.equals("AddPremFlag") ) {
			return 7;
		}
		if( strFieldName.equals("AccountFlag") ) {
			return 8;
		}
		if( strFieldName.equals("GroupID") ) {
			return 9;
		}
		if( strFieldName.equals("CCIFlag") ) {
			return 10;
		}
		if( strFieldName.equals("ADDFlag") ) {
			return 11;
		}
		if( strFieldName.equals("SubRiskNecessary") ) {
			return 12;
		}
		if( strFieldName.equals("PremADDFlag") ) {
			return 13;
		}
		if( strFieldName.equals("CVTable") ) {
			return 14;
		}
		if( strFieldName.equals("PATable") ) {
			return 15;
		}
		if( strFieldName.equals("CalFlag") ) {
			return 16;
		}
		if( strFieldName.equals("StartDate") ) {
			return 17;
		}
		if( strFieldName.equals("EndDate") ) {
			return 18;
		}
		if( strFieldName.equals("WebMult") ) {
			return 19;
		}
		if( strFieldName.equals("WebPrem") ) {
			return 20;
		}
		if( strFieldName.equals("WebAmnt") ) {
			return 21;
		}
		if( strFieldName.equals("WebInsuYearFlag") ) {
			return 22;
		}
		if( strFieldName.equals("WebInsuYear") ) {
			return 23;
		}
		if( strFieldName.equals("WebPayEndYearFlag") ) {
			return 24;
		}
		if( strFieldName.equals("WebPayEndYear") ) {
			return 25;
		}
		if( strFieldName.equals("WebPayIntv") ) {
			return 26;
		}
		if( strFieldName.equals("WebAccRate") ) {
			return 27;
		}
		if( strFieldName.equals("WebAccCode") ) {
			return 28;
		}
		if( strFieldName.equals("WebAccTimeFlag") ) {
			return 29;
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
				strFieldName = "RiskCode";
				break;
			case 1:
				strFieldName = "MainRiskCode";
				break;
			case 2:
				strFieldName = "RiskVer";
				break;
			case 3:
				strFieldName = "RiskName";
				break;
			case 4:
				strFieldName = "SubRiskFlag";
				break;
			case 5:
				strFieldName = "RiskType";
				break;
			case 6:
				strFieldName = "FirstAddPremFlag";
				break;
			case 7:
				strFieldName = "AddPremFlag";
				break;
			case 8:
				strFieldName = "AccountFlag";
				break;
			case 9:
				strFieldName = "GroupID";
				break;
			case 10:
				strFieldName = "CCIFlag";
				break;
			case 11:
				strFieldName = "ADDFlag";
				break;
			case 12:
				strFieldName = "SubRiskNecessary";
				break;
			case 13:
				strFieldName = "PremADDFlag";
				break;
			case 14:
				strFieldName = "CVTable";
				break;
			case 15:
				strFieldName = "PATable";
				break;
			case 16:
				strFieldName = "CalFlag";
				break;
			case 17:
				strFieldName = "StartDate";
				break;
			case 18:
				strFieldName = "EndDate";
				break;
			case 19:
				strFieldName = "WebMult";
				break;
			case 20:
				strFieldName = "WebPrem";
				break;
			case 21:
				strFieldName = "WebAmnt";
				break;
			case 22:
				strFieldName = "WebInsuYearFlag";
				break;
			case 23:
				strFieldName = "WebInsuYear";
				break;
			case 24:
				strFieldName = "WebPayEndYearFlag";
				break;
			case 25:
				strFieldName = "WebPayEndYear";
				break;
			case 26:
				strFieldName = "WebPayIntv";
				break;
			case 27:
				strFieldName = "WebAccRate";
				break;
			case 28:
				strFieldName = "WebAccCode";
				break;
			case 29:
				strFieldName = "WebAccTimeFlag";
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
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MainRiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskVer") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubRiskFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FirstAddPremFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddPremFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AccountFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GroupID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CCIFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ADDFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SubRiskNecessary") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PremADDFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CVTable") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PATable") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("WebMult") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("WebPrem") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("WebAmnt") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("WebInsuYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WebInsuYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("WebPayEndYearFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WebPayEndYear") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("WebPayIntv") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WebAccRate") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WebAccCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WebAccTimeFlag") ) {
			return Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 1:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 7:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 15:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 17:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 19:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 20:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 21:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 23:
				nFieldType = Schema.TYPE_INT;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_INT;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
