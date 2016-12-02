/*
 * <p>ClassName: LDComSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-05-22
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LDComDB;

public class LDComSchema implements Schema
{
	// @Field
	/** 机构编码 */
	private String ComCode;
	/** 对外公布的机构代码 */
	private String OutComCode;
	/** 机构名称 */
	private String Name;
	/** 短名称 */
	private String ShortName;
	/** Englishname */
	private String ENGLISHNAME;
	/** 机构地址 */
	private String Address;
	/** 机构邮编 */
	private String ZipCode;
	/** 机构电话 */
	private String Phone;
	/** 机构传真 */
	private String Fax;
	/** 电子邮件 */
	private String EMail;
	/** 网址 */
	private String WebAddress;
	/** 主管人姓名 */
	private String SatrapName;
	/** Comcodeisc */
	private String COMCODEISC;
	/** Othercomcode */
	private String OTHERCOMCODE;
	/** 公司性质 */
	private String ComNature;
	/** 机构级别 */
	private String ComGrade;
	/** 机构地区类型 */
	private String ComAreaType;
	/** 上级管理机构 */
	private String UpComCode;
	/** Comstate */
	private String COMSTATE;
	/** Province */
	private String PROVINCE;
	/** 所在城市 */
	private String CITY;
	/** County */
	private String COUNTY;
	/** Findb */
	private String FINDB;
	/** 广东保监会开关 */
	private String SamePersonFlag;
	/** 柜员资质开关 */
	private String TellerFlag;
	/** Rlscode */
	private String RLSCODE;
	/** Axaphone */
	private String AXAPHONE;
	/** Axazipcode */
	private String AXAZIPCODE;
	/** Axashortname */
	private String AXASHORTNAME;
	/** Operator */
	private String OPERATOR;
	/** Makedate */
	private Date MAKEDATE;
	/** Maketime */
	private String MAKETIME;
	/** Modifydate */
	private Date MODIFYDATE;
	/** Modifytime */
	private String MODIFYTIME;

	public static final int FIELDNUM = 34;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDComSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "ComCode";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 机构编码<P>机构编码 */
	public String getComCode()
	{
		if (ComCode != null && !ComCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			ComCode = StrTool.unicodeToGBK(ComCode);
		}
		return ComCode;
	}
	/** 机构编码 */
	public void setComCode(String aComCode)
	{
		ComCode = aComCode;
	}
	/** 对外公布的机构代码<P>对外公布的机构代码-未启用 */
	public String getOutComCode()
	{
		if (OutComCode != null && !OutComCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			OutComCode = StrTool.unicodeToGBK(OutComCode);
		}
		return OutComCode;
	}
	/** 对外公布的机构代码 */
	public void setOutComCode(String aOutComCode)
	{
		OutComCode = aOutComCode;
	}
	/** 机构名称<P>机构名称 */
	public String getName()
	{
		if (Name != null && !Name.equals("") && SysConst.CHANGECHARSET == true)
		{
			Name = StrTool.unicodeToGBK(Name);
		}
		return Name;
	}
	/** 机构名称 */
	public void setName(String aName)
	{
		Name = aName;
	}
	/** 短名称<P>短名称-未启用 */
	public String getShortName()
	{
		if (ShortName != null && !ShortName.equals("") && SysConst.CHANGECHARSET == true)
		{
			ShortName = StrTool.unicodeToGBK(ShortName);
		}
		return ShortName;
	}
	/** 短名称 */
	public void setShortName(String aShortName)
	{
		ShortName = aShortName;
	}
	/** Englishname<P> */
	public String getENGLISHNAME()
	{
		if (ENGLISHNAME != null && !ENGLISHNAME.equals("") && SysConst.CHANGECHARSET == true)
		{
			ENGLISHNAME = StrTool.unicodeToGBK(ENGLISHNAME);
		}
		return ENGLISHNAME;
	}
	/** Englishname */
	public void setENGLISHNAME(String aENGLISHNAME)
	{
		ENGLISHNAME = aENGLISHNAME;
	}
	/** 机构地址<P>机构地址 */
	public String getAddress()
	{
		if (Address != null && !Address.equals("") && SysConst.CHANGECHARSET == true)
		{
			Address = StrTool.unicodeToGBK(Address);
		}
		return Address;
	}
	/** 机构地址 */
	public void setAddress(String aAddress)
	{
		Address = aAddress;
	}
	/** 机构邮编<P>机构邮编 */
	public String getZipCode()
	{
		if (ZipCode != null && !ZipCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			ZipCode = StrTool.unicodeToGBK(ZipCode);
		}
		return ZipCode;
	}
	/** 机构邮编 */
	public void setZipCode(String aZipCode)
	{
		ZipCode = aZipCode;
	}
	/** 机构电话<P>机构电话 */
	public String getPhone()
	{
		if (Phone != null && !Phone.equals("") && SysConst.CHANGECHARSET == true)
		{
			Phone = StrTool.unicodeToGBK(Phone);
		}
		return Phone;
	}
	/** 机构电话 */
	public void setPhone(String aPhone)
	{
		Phone = aPhone;
	}
	/** 机构传真<P>机构传真 */
	public String getFax()
	{
		if (Fax != null && !Fax.equals("") && SysConst.CHANGECHARSET == true)
		{
			Fax = StrTool.unicodeToGBK(Fax);
		}
		return Fax;
	}
	/** 机构传真 */
	public void setFax(String aFax)
	{
		Fax = aFax;
	}
	/** 电子邮件<P>电子邮件 */
	public String getEMail()
	{
		if (EMail != null && !EMail.equals("") && SysConst.CHANGECHARSET == true)
		{
			EMail = StrTool.unicodeToGBK(EMail);
		}
		return EMail;
	}
	/** 电子邮件 */
	public void setEMail(String aEMail)
	{
		EMail = aEMail;
	}
	/** 网址<P>网址 */
	public String getWebAddress()
	{
		if (WebAddress != null && !WebAddress.equals("") && SysConst.CHANGECHARSET == true)
		{
			WebAddress = StrTool.unicodeToGBK(WebAddress);
		}
		return WebAddress;
	}
	/** 网址 */
	public void setWebAddress(String aWebAddress)
	{
		WebAddress = aWebAddress;
	}
	/** 主管人姓名<P>主管人姓名 */
	public String getSatrapName()
	{
		if (SatrapName != null && !SatrapName.equals("") && SysConst.CHANGECHARSET == true)
		{
			SatrapName = StrTool.unicodeToGBK(SatrapName);
		}
		return SatrapName;
	}
	/** 主管人姓名 */
	public void setSatrapName(String aSatrapName)
	{
		SatrapName = aSatrapName;
	}
	/** Comcodeisc<P>对应保监办代码-未启用 */
	public String getCOMCODEISC()
	{
		if (COMCODEISC != null && !COMCODEISC.equals("") && SysConst.CHANGECHARSET == true)
		{
			COMCODEISC = StrTool.unicodeToGBK(COMCODEISC);
		}
		return COMCODEISC;
	}
	/** Comcodeisc */
	public void setCOMCODEISC(String aCOMCODEISC)
	{
		COMCODEISC = aCOMCODEISC;
	}
	/** Othercomcode<P>保险公司id-未启用 */
	public String getOTHERCOMCODE()
	{
		if (OTHERCOMCODE != null && !OTHERCOMCODE.equals("") && SysConst.CHANGECHARSET == true)
		{
			OTHERCOMCODE = StrTool.unicodeToGBK(OTHERCOMCODE);
		}
		return OTHERCOMCODE;
	}
	/** Othercomcode */
	public void setOTHERCOMCODE(String aOTHERCOMCODE)
	{
		OTHERCOMCODE = aOTHERCOMCODE;
	}
	/** 公司性质<P>公司性质 */
	public String getComNature()
	{
		if (ComNature != null && !ComNature.equals("") && SysConst.CHANGECHARSET == true)
		{
			ComNature = StrTool.unicodeToGBK(ComNature);
		}
		return ComNature;
	}
	/** 公司性质 */
	public void setComNature(String aComNature)
	{
		ComNature = aComNature;
	}
	/** 机构级别<P>机构级别 */
	public String getComGrade()
	{
		if (ComGrade != null && !ComGrade.equals("") && SysConst.CHANGECHARSET == true)
		{
			ComGrade = StrTool.unicodeToGBK(ComGrade);
		}
		return ComGrade;
	}
	/** 机构级别 */
	public void setComGrade(String aComGrade)
	{
		ComGrade = aComGrade;
	}
	/** 机构地区类型<P>机构地区类型-未使用 */
	public String getComAreaType()
	{
		if (ComAreaType != null && !ComAreaType.equals("") && SysConst.CHANGECHARSET == true)
		{
			ComAreaType = StrTool.unicodeToGBK(ComAreaType);
		}
		return ComAreaType;
	}
	/** 机构地区类型 */
	public void setComAreaType(String aComAreaType)
	{
		ComAreaType = aComAreaType;
	}
	/** 上级管理机构<P>上级管理机构 */
	public String getUpComCode()
	{
		if (UpComCode != null && !UpComCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			UpComCode = StrTool.unicodeToGBK(UpComCode);
		}
		return UpComCode;
	}
	/** 上级管理机构 */
	public void setUpComCode(String aUpComCode)
	{
		UpComCode = aUpComCode;
	}
	/** Comstate<P>直属属性-未使用 */
	public String getCOMSTATE()
	{
		if (COMSTATE != null && !COMSTATE.equals("") && SysConst.CHANGECHARSET == true)
		{
			COMSTATE = StrTool.unicodeToGBK(COMSTATE);
		}
		return COMSTATE;
	}
	/** Comstate */
	public void setCOMSTATE(String aCOMSTATE)
	{
		COMSTATE = aCOMSTATE;
	}
	/** Province<P> */
	public String getPROVINCE()
	{
		if (PROVINCE != null && !PROVINCE.equals("") && SysConst.CHANGECHARSET == true)
		{
			PROVINCE = StrTool.unicodeToGBK(PROVINCE);
		}
		return PROVINCE;
	}
	/** Province */
	public void setPROVINCE(String aPROVINCE)
	{
		PROVINCE = aPROVINCE;
	}
	/** 所在城市<P> */
	public String getCITY()
	{
		if (CITY != null && !CITY.equals("") && SysConst.CHANGECHARSET == true)
		{
			CITY = StrTool.unicodeToGBK(CITY);
		}
		return CITY;
	}
	/** 所在城市 */
	public void setCITY(String aCITY)
	{
		CITY = aCITY;
	}
	/** County<P> */
	public String getCOUNTY()
	{
		if (COUNTY != null && !COUNTY.equals("") && SysConst.CHANGECHARSET == true)
		{
			COUNTY = StrTool.unicodeToGBK(COUNTY);
		}
		return COUNTY;
	}
	/** County */
	public void setCOUNTY(String aCOUNTY)
	{
		COUNTY = aCOUNTY;
	}
	/** Findb<P> */
	public String getFINDB()
	{
		if (FINDB != null && !FINDB.equals("") && SysConst.CHANGECHARSET == true)
		{
			FINDB = StrTool.unicodeToGBK(FINDB);
		}
		return FINDB;
	}
	/** Findb */
	public void setFINDB(String aFINDB)
	{
		FINDB = aFINDB;
	}
	/** 广东保监会开关<P> */
	public String getSamePersonFlag()
	{
		if (SamePersonFlag != null && !SamePersonFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			SamePersonFlag = StrTool.unicodeToGBK(SamePersonFlag);
		}
		return SamePersonFlag;
	}
	/** 广东保监会开关 */
	public void setSamePersonFlag(String aSamePersonFlag)
	{
		SamePersonFlag = aSamePersonFlag;
	}
	/** 柜员资质开关<P> */
	public String getTellerFlag()
	{
		if (TellerFlag != null && !TellerFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			TellerFlag = StrTool.unicodeToGBK(TellerFlag);
		}
		return TellerFlag;
	}
	/** 柜员资质开关 */
	public void setTellerFlag(String aTellerFlag)
	{
		TellerFlag = aTellerFlag;
	}
	/** Rlscode<P> */
	public String getRLSCODE()
	{
		if (RLSCODE != null && !RLSCODE.equals("") && SysConst.CHANGECHARSET == true)
		{
			RLSCODE = StrTool.unicodeToGBK(RLSCODE);
		}
		return RLSCODE;
	}
	/** Rlscode */
	public void setRLSCODE(String aRLSCODE)
	{
		RLSCODE = aRLSCODE;
	}
	/** Axaphone<P> */
	public String getAXAPHONE()
	{
		if (AXAPHONE != null && !AXAPHONE.equals("") && SysConst.CHANGECHARSET == true)
		{
			AXAPHONE = StrTool.unicodeToGBK(AXAPHONE);
		}
		return AXAPHONE;
	}
	/** Axaphone */
	public void setAXAPHONE(String aAXAPHONE)
	{
		AXAPHONE = aAXAPHONE;
	}
	/** Axazipcode<P> */
	public String getAXAZIPCODE()
	{
		if (AXAZIPCODE != null && !AXAZIPCODE.equals("") && SysConst.CHANGECHARSET == true)
		{
			AXAZIPCODE = StrTool.unicodeToGBK(AXAZIPCODE);
		}
		return AXAZIPCODE;
	}
	/** Axazipcode */
	public void setAXAZIPCODE(String aAXAZIPCODE)
	{
		AXAZIPCODE = aAXAZIPCODE;
	}
	/** Axashortname<P> */
	public String getAXASHORTNAME()
	{
		if (AXASHORTNAME != null && !AXASHORTNAME.equals("") && SysConst.CHANGECHARSET == true)
		{
			AXASHORTNAME = StrTool.unicodeToGBK(AXASHORTNAME);
		}
		return AXASHORTNAME;
	}
	/** Axashortname */
	public void setAXASHORTNAME(String aAXASHORTNAME)
	{
		AXASHORTNAME = aAXASHORTNAME;
	}
	/** Operator<P> */
	public String getOPERATOR()
	{
		if (OPERATOR != null && !OPERATOR.equals("") && SysConst.CHANGECHARSET == true)
		{
			OPERATOR = StrTool.unicodeToGBK(OPERATOR);
		}
		return OPERATOR;
	}
	/** Operator */
	public void setOPERATOR(String aOPERATOR)
	{
		OPERATOR = aOPERATOR;
	}
	/** Makedate<P> */
	public String getMAKEDATE()
	{
		if( MAKEDATE != null )
			return fDate.getString(MAKEDATE);
		else
			return null;
	}
	/** Makedate */
	public void setMAKEDATE(Date aMAKEDATE)
	{
		MAKEDATE = aMAKEDATE;
	}
	/** Makedate<P> */
	public void setMAKEDATE(String aMAKEDATE)
	{
		if (aMAKEDATE != null && !aMAKEDATE.equals("") )
		{
			MAKEDATE = fDate.getDate( aMAKEDATE );
		}
		else
			MAKEDATE = null;
	}

	/** Maketime<P> */
	public String getMAKETIME()
	{
		if (MAKETIME != null && !MAKETIME.equals("") && SysConst.CHANGECHARSET == true)
		{
			MAKETIME = StrTool.unicodeToGBK(MAKETIME);
		}
		return MAKETIME;
	}
	/** Maketime */
	public void setMAKETIME(String aMAKETIME)
	{
		MAKETIME = aMAKETIME;
	}
	/** Modifydate<P> */
	public String getMODIFYDATE()
	{
		if( MODIFYDATE != null )
			return fDate.getString(MODIFYDATE);
		else
			return null;
	}
	/** Modifydate */
	public void setMODIFYDATE(Date aMODIFYDATE)
	{
		MODIFYDATE = aMODIFYDATE;
	}
	/** Modifydate<P> */
	public void setMODIFYDATE(String aMODIFYDATE)
	{
		if (aMODIFYDATE != null && !aMODIFYDATE.equals("") )
		{
			MODIFYDATE = fDate.getDate( aMODIFYDATE );
		}
		else
			MODIFYDATE = null;
	}

	/** Modifytime<P> */
	public String getMODIFYTIME()
	{
		if (MODIFYTIME != null && !MODIFYTIME.equals("") && SysConst.CHANGECHARSET == true)
		{
			MODIFYTIME = StrTool.unicodeToGBK(MODIFYTIME);
		}
		return MODIFYTIME;
	}
	/** Modifytime */
	public void setMODIFYTIME(String aMODIFYTIME)
	{
		MODIFYTIME = aMODIFYTIME;
	}

	/**
	* 使用另外一个 LDComSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LDComSchema aLDComSchema)
	{
		this.ComCode = aLDComSchema.getComCode();
		this.OutComCode = aLDComSchema.getOutComCode();
		this.Name = aLDComSchema.getName();
		this.ShortName = aLDComSchema.getShortName();
		this.ENGLISHNAME = aLDComSchema.getENGLISHNAME();
		this.Address = aLDComSchema.getAddress();
		this.ZipCode = aLDComSchema.getZipCode();
		this.Phone = aLDComSchema.getPhone();
		this.Fax = aLDComSchema.getFax();
		this.EMail = aLDComSchema.getEMail();
		this.WebAddress = aLDComSchema.getWebAddress();
		this.SatrapName = aLDComSchema.getSatrapName();
		this.COMCODEISC = aLDComSchema.getCOMCODEISC();
		this.OTHERCOMCODE = aLDComSchema.getOTHERCOMCODE();
		this.ComNature = aLDComSchema.getComNature();
		this.ComGrade = aLDComSchema.getComGrade();
		this.ComAreaType = aLDComSchema.getComAreaType();
		this.UpComCode = aLDComSchema.getUpComCode();
		this.COMSTATE = aLDComSchema.getCOMSTATE();
		this.PROVINCE = aLDComSchema.getPROVINCE();
		this.CITY = aLDComSchema.getCITY();
		this.COUNTY = aLDComSchema.getCOUNTY();
		this.FINDB = aLDComSchema.getFINDB();
		this.SamePersonFlag = aLDComSchema.getSamePersonFlag();
		this.TellerFlag = aLDComSchema.getTellerFlag();
		this.RLSCODE = aLDComSchema.getRLSCODE();
		this.AXAPHONE = aLDComSchema.getAXAPHONE();
		this.AXAZIPCODE = aLDComSchema.getAXAZIPCODE();
		this.AXASHORTNAME = aLDComSchema.getAXASHORTNAME();
		this.OPERATOR = aLDComSchema.getOPERATOR();
		this.MAKEDATE = fDate.getDate( aLDComSchema.getMAKEDATE());
		this.MAKETIME = aLDComSchema.getMAKETIME();
		this.MODIFYDATE = fDate.getDate( aLDComSchema.getMODIFYDATE());
		this.MODIFYTIME = aLDComSchema.getMODIFYTIME();
	}

	/**
	* 使用 ResultSet 中的第 i 行给 Schema 赋值
	* @param: ResultSet 对象; i 行数
	* @return: boolean
	**/
	public boolean setSchema(ResultSet rs,int i)
	{
		try
		{
			//rs.absolute(i);		// 非滚动游标
			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("OutComCode") == null )
				this.OutComCode = null;
			else
				this.OutComCode = rs.getString("OutComCode").trim();

			if( rs.getString("Name") == null )
				this.Name = null;
			else
				this.Name = rs.getString("Name").trim();

			if( rs.getString("ShortName") == null )
				this.ShortName = null;
			else
				this.ShortName = rs.getString("ShortName").trim();

			if( rs.getString("ENGLISHNAME") == null )
				this.ENGLISHNAME = null;
			else
				this.ENGLISHNAME = rs.getString("ENGLISHNAME").trim();

			if( rs.getString("Address") == null )
				this.Address = null;
			else
				this.Address = rs.getString("Address").trim();

			if( rs.getString("ZipCode") == null )
				this.ZipCode = null;
			else
				this.ZipCode = rs.getString("ZipCode").trim();

			if( rs.getString("Phone") == null )
				this.Phone = null;
			else
				this.Phone = rs.getString("Phone").trim();

			if( rs.getString("Fax") == null )
				this.Fax = null;
			else
				this.Fax = rs.getString("Fax").trim();

			if( rs.getString("EMail") == null )
				this.EMail = null;
			else
				this.EMail = rs.getString("EMail").trim();

			if( rs.getString("WebAddress") == null )
				this.WebAddress = null;
			else
				this.WebAddress = rs.getString("WebAddress").trim();

			if( rs.getString("SatrapName") == null )
				this.SatrapName = null;
			else
				this.SatrapName = rs.getString("SatrapName").trim();

			if( rs.getString("COMCODEISC") == null )
				this.COMCODEISC = null;
			else
				this.COMCODEISC = rs.getString("COMCODEISC").trim();

			if( rs.getString("OTHERCOMCODE") == null )
				this.OTHERCOMCODE = null;
			else
				this.OTHERCOMCODE = rs.getString("OTHERCOMCODE").trim();

			if( rs.getString("ComNature") == null )
				this.ComNature = null;
			else
				this.ComNature = rs.getString("ComNature").trim();

			if( rs.getString("ComGrade") == null )
				this.ComGrade = null;
			else
				this.ComGrade = rs.getString("ComGrade").trim();

			if( rs.getString("ComAreaType") == null )
				this.ComAreaType = null;
			else
				this.ComAreaType = rs.getString("ComAreaType").trim();

			if( rs.getString("UpComCode") == null )
				this.UpComCode = null;
			else
				this.UpComCode = rs.getString("UpComCode").trim();

			if( rs.getString("COMSTATE") == null )
				this.COMSTATE = null;
			else
				this.COMSTATE = rs.getString("COMSTATE").trim();

			if( rs.getString("PROVINCE") == null )
				this.PROVINCE = null;
			else
				this.PROVINCE = rs.getString("PROVINCE").trim();

			if( rs.getString("CITY") == null )
				this.CITY = null;
			else
				this.CITY = rs.getString("CITY").trim();

			if( rs.getString("COUNTY") == null )
				this.COUNTY = null;
			else
				this.COUNTY = rs.getString("COUNTY").trim();

			if( rs.getString("FINDB") == null )
				this.FINDB = null;
			else
				this.FINDB = rs.getString("FINDB").trim();

			if( rs.getString("SamePersonFlag") == null )
				this.SamePersonFlag = null;
			else
				this.SamePersonFlag = rs.getString("SamePersonFlag").trim();

			if( rs.getString("TellerFlag") == null )
				this.TellerFlag = null;
			else
				this.TellerFlag = rs.getString("TellerFlag").trim();

			if( rs.getString("RLSCODE") == null )
				this.RLSCODE = null;
			else
				this.RLSCODE = rs.getString("RLSCODE").trim();

			if( rs.getString("AXAPHONE") == null )
				this.AXAPHONE = null;
			else
				this.AXAPHONE = rs.getString("AXAPHONE").trim();

			if( rs.getString("AXAZIPCODE") == null )
				this.AXAZIPCODE = null;
			else
				this.AXAZIPCODE = rs.getString("AXAZIPCODE").trim();

			if( rs.getString("AXASHORTNAME") == null )
				this.AXASHORTNAME = null;
			else
				this.AXASHORTNAME = rs.getString("AXASHORTNAME").trim();

			if( rs.getString("OPERATOR") == null )
				this.OPERATOR = null;
			else
				this.OPERATOR = rs.getString("OPERATOR").trim();

			this.MAKEDATE = rs.getDate("MAKEDATE");
			if( rs.getString("MAKETIME") == null )
				this.MAKETIME = null;
			else
				this.MAKETIME = rs.getString("MAKETIME").trim();

			this.MODIFYDATE = rs.getDate("MODIFYDATE");
			if( rs.getString("MODIFYTIME") == null )
				this.MODIFYTIME = null;
			else
				this.MODIFYTIME = rs.getString("MODIFYTIME").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDComSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LDComSchema getSchema()
	{
		LDComSchema aLDComSchema = new LDComSchema();
		aLDComSchema.setSchema(this);
		return aLDComSchema;
	}

	public LDComDB getDB()
	{
		LDComDB aDBOper = new LDComDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCom描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(ComCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OutComCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Name) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ShortName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ENGLISHNAME) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Address) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ZipCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Phone) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Fax) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(EMail) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(WebAddress) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SatrapName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(COMCODEISC) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OTHERCOMCODE) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ComNature) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ComGrade) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ComAreaType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UpComCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(COMSTATE) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PROVINCE) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CITY) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(COUNTY) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FINDB) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SamePersonFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TellerFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RLSCODE) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AXAPHONE) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AXAZIPCODE) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AXASHORTNAME) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OPERATOR) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MAKEDATE )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MAKETIME) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MODIFYDATE )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MODIFYTIME) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCom>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			OutComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Name = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ShortName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ENGLISHNAME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Address = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			Fax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			EMail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			WebAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			SatrapName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			COMCODEISC = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			OTHERCOMCODE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ComNature = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			ComGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			ComAreaType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			UpComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			COMSTATE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			PROVINCE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			CITY = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			COUNTY = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			FINDB = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			SamePersonFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			TellerFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			RLSCODE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26, SysConst.PACKAGESPILTER );
			AXAPHONE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			AXAZIPCODE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			AXASHORTNAME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			OPERATOR = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			MAKEDATE = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31,SysConst.PACKAGESPILTER));
			MAKETIME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			MODIFYDATE = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33,SysConst.PACKAGESPILTER));
			MODIFYTIME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDComSchema";
			tError.functionName = "decode";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	/**
	* 取得对应传入参数的String形式的字段值
	* @param: FCode 希望取得的字段名
	* @return: FValue String形式的字段值
	*			FValue = ""		没有匹配的字段
	*			FValue = "null"	字段值为null
	**/
	public String getV(String FCode)
	{
		String strReturn = "";
		if (FCode.equals("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equals("OutComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OutComCode));
		}
		if (FCode.equals("Name"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Name));
		}
		if (FCode.equals("ShortName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ShortName));
		}
		if (FCode.equals("ENGLISHNAME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ENGLISHNAME));
		}
		if (FCode.equals("Address"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Address));
		}
		if (FCode.equals("ZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZipCode));
		}
		if (FCode.equals("Phone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Phone));
		}
		if (FCode.equals("Fax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Fax));
		}
		if (FCode.equals("EMail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EMail));
		}
		if (FCode.equals("WebAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(WebAddress));
		}
		if (FCode.equals("SatrapName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SatrapName));
		}
		if (FCode.equals("COMCODEISC"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(COMCODEISC));
		}
		if (FCode.equals("OTHERCOMCODE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OTHERCOMCODE));
		}
		if (FCode.equals("ComNature"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComNature));
		}
		if (FCode.equals("ComGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComGrade));
		}
		if (FCode.equals("ComAreaType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComAreaType));
		}
		if (FCode.equals("UpComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpComCode));
		}
		if (FCode.equals("COMSTATE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(COMSTATE));
		}
		if (FCode.equals("PROVINCE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PROVINCE));
		}
		if (FCode.equals("CITY"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CITY));
		}
		if (FCode.equals("COUNTY"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(COUNTY));
		}
		if (FCode.equals("FINDB"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FINDB));
		}
		if (FCode.equals("SamePersonFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SamePersonFlag));
		}
		if (FCode.equals("TellerFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TellerFlag));
		}
		if (FCode.equals("RLSCODE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RLSCODE));
		}
		if (FCode.equals("AXAPHONE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AXAPHONE));
		}
		if (FCode.equals("AXAZIPCODE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AXAZIPCODE));
		}
		if (FCode.equals("AXASHORTNAME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AXASHORTNAME));
		}
		if (FCode.equals("OPERATOR"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OPERATOR));
		}
		if (FCode.equals("MAKEDATE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMAKEDATE()));
		}
		if (FCode.equals("MAKETIME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MAKETIME));
		}
		if (FCode.equals("MODIFYDATE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMODIFYDATE()));
		}
		if (FCode.equals("MODIFYTIME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MODIFYTIME));
		}
		if (strReturn.equals(""))
		{
			strReturn = "null";
		}

		return strReturn;
	}


	/**
	* 取得Schema中指定索引值所对应的字段值
	* @param: nFieldIndex 指定的字段索引值
	* @return: 字段值。
	*          如果没有对应的字段，返回""
	*          如果字段值为空，返回"null"
	**/
	public String getV(int nFieldIndex)
	{
		String strFieldValue = "";
		switch(nFieldIndex) {
			case 0:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(OutComCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Name);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ShortName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ENGLISHNAME);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Address);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ZipCode);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(Fax);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(EMail);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(WebAddress);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(SatrapName);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(COMCODEISC);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(OTHERCOMCODE);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(ComNature);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(ComGrade);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(ComAreaType);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(UpComCode);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(COMSTATE);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(PROVINCE);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(CITY);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(COUNTY);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(FINDB);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(SamePersonFlag);
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(TellerFlag);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(RLSCODE);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(AXAPHONE);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(AXAZIPCODE);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(AXASHORTNAME);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(OPERATOR);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMAKEDATE()));
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(MAKETIME);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMODIFYDATE()));
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(MODIFYTIME);
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
	* 设置对应传入参数的String形式的字段值
	* @param: FCode 希望取得的字段名
	* @return: FValue String形式的字段值
	*			FValue = ""		没有匹配的字段
	*			FValue = "null"	字段值为null
	**/
	public boolean setV(String FCode ,String FValue)
	{
		if( StrTool.cTrim( FCode ).equals( "" ))
			return false;

		if (FCode.equals("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equals("OutComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OutComCode = FValue.trim();
			}
			else
				OutComCode = null;
		}
		if (FCode.equals("Name"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Name = FValue.trim();
			}
			else
				Name = null;
		}
		if (FCode.equals("ShortName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ShortName = FValue.trim();
			}
			else
				ShortName = null;
		}
		if (FCode.equals("ENGLISHNAME"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ENGLISHNAME = FValue.trim();
			}
			else
				ENGLISHNAME = null;
		}
		if (FCode.equals("Address"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Address = FValue.trim();
			}
			else
				Address = null;
		}
		if (FCode.equals("ZipCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZipCode = FValue.trim();
			}
			else
				ZipCode = null;
		}
		if (FCode.equals("Phone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Phone = FValue.trim();
			}
			else
				Phone = null;
		}
		if (FCode.equals("Fax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Fax = FValue.trim();
			}
			else
				Fax = null;
		}
		if (FCode.equals("EMail"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EMail = FValue.trim();
			}
			else
				EMail = null;
		}
		if (FCode.equals("WebAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				WebAddress = FValue.trim();
			}
			else
				WebAddress = null;
		}
		if (FCode.equals("SatrapName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SatrapName = FValue.trim();
			}
			else
				SatrapName = null;
		}
		if (FCode.equals("COMCODEISC"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				COMCODEISC = FValue.trim();
			}
			else
				COMCODEISC = null;
		}
		if (FCode.equals("OTHERCOMCODE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OTHERCOMCODE = FValue.trim();
			}
			else
				OTHERCOMCODE = null;
		}
		if (FCode.equals("ComNature"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComNature = FValue.trim();
			}
			else
				ComNature = null;
		}
		if (FCode.equals("ComGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComGrade = FValue.trim();
			}
			else
				ComGrade = null;
		}
		if (FCode.equals("ComAreaType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComAreaType = FValue.trim();
			}
			else
				ComAreaType = null;
		}
		if (FCode.equals("UpComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpComCode = FValue.trim();
			}
			else
				UpComCode = null;
		}
		if (FCode.equals("COMSTATE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				COMSTATE = FValue.trim();
			}
			else
				COMSTATE = null;
		}
		if (FCode.equals("PROVINCE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PROVINCE = FValue.trim();
			}
			else
				PROVINCE = null;
		}
		if (FCode.equals("CITY"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CITY = FValue.trim();
			}
			else
				CITY = null;
		}
		if (FCode.equals("COUNTY"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				COUNTY = FValue.trim();
			}
			else
				COUNTY = null;
		}
		if (FCode.equals("FINDB"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FINDB = FValue.trim();
			}
			else
				FINDB = null;
		}
		if (FCode.equals("SamePersonFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SamePersonFlag = FValue.trim();
			}
			else
				SamePersonFlag = null;
		}
		if (FCode.equals("TellerFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TellerFlag = FValue.trim();
			}
			else
				TellerFlag = null;
		}
		if (FCode.equals("RLSCODE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RLSCODE = FValue.trim();
			}
			else
				RLSCODE = null;
		}
		if (FCode.equals("AXAPHONE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AXAPHONE = FValue.trim();
			}
			else
				AXAPHONE = null;
		}
		if (FCode.equals("AXAZIPCODE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AXAZIPCODE = FValue.trim();
			}
			else
				AXAZIPCODE = null;
		}
		if (FCode.equals("AXASHORTNAME"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AXASHORTNAME = FValue.trim();
			}
			else
				AXASHORTNAME = null;
		}
		if (FCode.equals("OPERATOR"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OPERATOR = FValue.trim();
			}
			else
				OPERATOR = null;
		}
		if (FCode.equals("MAKEDATE"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MAKEDATE = fDate.getDate( FValue );
			}
			else
				MAKEDATE = null;
		}
		if (FCode.equals("MAKETIME"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MAKETIME = FValue.trim();
			}
			else
				MAKETIME = null;
		}
		if (FCode.equals("MODIFYDATE"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MODIFYDATE = fDate.getDate( FValue );
			}
			else
				MODIFYDATE = null;
		}
		if (FCode.equals("MODIFYTIME"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MODIFYTIME = FValue.trim();
			}
			else
				MODIFYTIME = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDComSchema other = (LDComSchema)otherObject;
		return
			ComCode.equals(other.getComCode())
			&& OutComCode.equals(other.getOutComCode())
			&& Name.equals(other.getName())
			&& ShortName.equals(other.getShortName())
			&& ENGLISHNAME.equals(other.getENGLISHNAME())
			&& Address.equals(other.getAddress())
			&& ZipCode.equals(other.getZipCode())
			&& Phone.equals(other.getPhone())
			&& Fax.equals(other.getFax())
			&& EMail.equals(other.getEMail())
			&& WebAddress.equals(other.getWebAddress())
			&& SatrapName.equals(other.getSatrapName())
			&& COMCODEISC.equals(other.getCOMCODEISC())
			&& OTHERCOMCODE.equals(other.getOTHERCOMCODE())
			&& ComNature.equals(other.getComNature())
			&& ComGrade.equals(other.getComGrade())
			&& ComAreaType.equals(other.getComAreaType())
			&& UpComCode.equals(other.getUpComCode())
			&& COMSTATE.equals(other.getCOMSTATE())
			&& PROVINCE.equals(other.getPROVINCE())
			&& CITY.equals(other.getCITY())
			&& COUNTY.equals(other.getCOUNTY())
			&& FINDB.equals(other.getFINDB())
			&& SamePersonFlag.equals(other.getSamePersonFlag())
			&& TellerFlag.equals(other.getTellerFlag())
			&& RLSCODE.equals(other.getRLSCODE())
			&& AXAPHONE.equals(other.getAXAPHONE())
			&& AXAZIPCODE.equals(other.getAXAZIPCODE())
			&& AXASHORTNAME.equals(other.getAXASHORTNAME())
			&& OPERATOR.equals(other.getOPERATOR())
			&& fDate.getString(MAKEDATE).equals(other.getMAKEDATE())
			&& MAKETIME.equals(other.getMAKETIME())
			&& fDate.getString(MODIFYDATE).equals(other.getMODIFYDATE())
			&& MODIFYTIME.equals(other.getMODIFYTIME());
	}

	/**
	* 取得Schema拥有字段的数量
	**/
	public int getFieldCount()
	{
 		return FIELDNUM;
	}

	/**
	* 取得Schema中指定字段名所对应的索引值
	* 如果没有对应的字段，返回-1
	**/
	public int getFieldIndex(String strFieldName)
	{
		if( strFieldName.equals("ComCode") ) {
			return 0;
		}
		if( strFieldName.equals("OutComCode") ) {
			return 1;
		}
		if( strFieldName.equals("Name") ) {
			return 2;
		}
		if( strFieldName.equals("ShortName") ) {
			return 3;
		}
		if( strFieldName.equals("ENGLISHNAME") ) {
			return 4;
		}
		if( strFieldName.equals("Address") ) {
			return 5;
		}
		if( strFieldName.equals("ZipCode") ) {
			return 6;
		}
		if( strFieldName.equals("Phone") ) {
			return 7;
		}
		if( strFieldName.equals("Fax") ) {
			return 8;
		}
		if( strFieldName.equals("EMail") ) {
			return 9;
		}
		if( strFieldName.equals("WebAddress") ) {
			return 10;
		}
		if( strFieldName.equals("SatrapName") ) {
			return 11;
		}
		if( strFieldName.equals("COMCODEISC") ) {
			return 12;
		}
		if( strFieldName.equals("OTHERCOMCODE") ) {
			return 13;
		}
		if( strFieldName.equals("ComNature") ) {
			return 14;
		}
		if( strFieldName.equals("ComGrade") ) {
			return 15;
		}
		if( strFieldName.equals("ComAreaType") ) {
			return 16;
		}
		if( strFieldName.equals("UpComCode") ) {
			return 17;
		}
		if( strFieldName.equals("COMSTATE") ) {
			return 18;
		}
		if( strFieldName.equals("PROVINCE") ) {
			return 19;
		}
		if( strFieldName.equals("CITY") ) {
			return 20;
		}
		if( strFieldName.equals("COUNTY") ) {
			return 21;
		}
		if( strFieldName.equals("FINDB") ) {
			return 22;
		}
		if( strFieldName.equals("SamePersonFlag") ) {
			return 23;
		}
		if( strFieldName.equals("TellerFlag") ) {
			return 24;
		}
		if( strFieldName.equals("RLSCODE") ) {
			return 25;
		}
		if( strFieldName.equals("AXAPHONE") ) {
			return 26;
		}
		if( strFieldName.equals("AXAZIPCODE") ) {
			return 27;
		}
		if( strFieldName.equals("AXASHORTNAME") ) {
			return 28;
		}
		if( strFieldName.equals("OPERATOR") ) {
			return 29;
		}
		if( strFieldName.equals("MAKEDATE") ) {
			return 30;
		}
		if( strFieldName.equals("MAKETIME") ) {
			return 31;
		}
		if( strFieldName.equals("MODIFYDATE") ) {
			return 32;
		}
		if( strFieldName.equals("MODIFYTIME") ) {
			return 33;
		}
		return -1;
	}

	/**
	* 取得Schema中指定索引值所对应的字段名
	* 如果没有对应的字段，返回""
	**/
	public String getFieldName(int nFieldIndex)
	{
		String strFieldName = "";
		switch(nFieldIndex) {
			case 0:
				strFieldName = "ComCode";
				break;
			case 1:
				strFieldName = "OutComCode";
				break;
			case 2:
				strFieldName = "Name";
				break;
			case 3:
				strFieldName = "ShortName";
				break;
			case 4:
				strFieldName = "ENGLISHNAME";
				break;
			case 5:
				strFieldName = "Address";
				break;
			case 6:
				strFieldName = "ZipCode";
				break;
			case 7:
				strFieldName = "Phone";
				break;
			case 8:
				strFieldName = "Fax";
				break;
			case 9:
				strFieldName = "EMail";
				break;
			case 10:
				strFieldName = "WebAddress";
				break;
			case 11:
				strFieldName = "SatrapName";
				break;
			case 12:
				strFieldName = "COMCODEISC";
				break;
			case 13:
				strFieldName = "OTHERCOMCODE";
				break;
			case 14:
				strFieldName = "ComNature";
				break;
			case 15:
				strFieldName = "ComGrade";
				break;
			case 16:
				strFieldName = "ComAreaType";
				break;
			case 17:
				strFieldName = "UpComCode";
				break;
			case 18:
				strFieldName = "COMSTATE";
				break;
			case 19:
				strFieldName = "PROVINCE";
				break;
			case 20:
				strFieldName = "CITY";
				break;
			case 21:
				strFieldName = "COUNTY";
				break;
			case 22:
				strFieldName = "FINDB";
				break;
			case 23:
				strFieldName = "SamePersonFlag";
				break;
			case 24:
				strFieldName = "TellerFlag";
				break;
			case 25:
				strFieldName = "RLSCODE";
				break;
			case 26:
				strFieldName = "AXAPHONE";
				break;
			case 27:
				strFieldName = "AXAZIPCODE";
				break;
			case 28:
				strFieldName = "AXASHORTNAME";
				break;
			case 29:
				strFieldName = "OPERATOR";
				break;
			case 30:
				strFieldName = "MAKEDATE";
				break;
			case 31:
				strFieldName = "MAKETIME";
				break;
			case 32:
				strFieldName = "MODIFYDATE";
				break;
			case 33:
				strFieldName = "MODIFYTIME";
				break;
			default:
				strFieldName = "";
		};
		return strFieldName;
	}

	/**
	* 取得Schema中指定字段名所对应的字段类型
	* 如果没有对应的字段，返回Schema.TYPE_NOFOUND
	**/
	public int getFieldType(String strFieldName)
	{
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OutComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Name") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ShortName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ENGLISHNAME") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Address") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Phone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Fax") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EMail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("WebAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SatrapName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("COMCODEISC") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OTHERCOMCODE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComNature") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComAreaType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("COMSTATE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PROVINCE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CITY") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("COUNTY") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FINDB") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SamePersonFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TellerFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RLSCODE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AXAPHONE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AXAZIPCODE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AXASHORTNAME") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OPERATOR") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MAKEDATE") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MAKETIME") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MODIFYDATE") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MODIFYTIME") ) {
			return Schema.TYPE_STRING;
		}
		return Schema.TYPE_NOFOUND;
	}

	/**
	* 取得Schema中指定索引值所对应的字段类型
	* 如果没有对应的字段，返回Schema.TYPE_NOFOUND
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_STRING;
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
			case 30:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
