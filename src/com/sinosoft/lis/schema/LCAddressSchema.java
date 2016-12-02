/*
 * <p>ClassName: LCAddressSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2011-11-10
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LCAddressDB;

public class LCAddressSchema implements Schema
{
	// @Field
	/** 客户号码 */
	private String CustomerNo;
	/** 地址号码 */
	private int AddressNo;
	/** 通讯地址 */
	private String PostalAddress;
	/** 通讯邮编 */
	private String ZipCode;
	/** 通讯电话 */
	private String Phone;
	/** 通讯传真 */
	private String Fax;
	/** 家庭地址 */
	private String HomeAddress;
	/** 家庭邮编 */
	private String HomeZipCode;
	/** 家庭电话 */
	private String HomePhone;
	/** 家庭传真 */
	private String HomeFax;
	/** 单位地址址 */
	private String CompanyAddress;
	/** 单位邮编 */
	private String CompanyZipCode;
	/** 单位电话 */
	private String CompanyPhone;
	/** 单位传真 */
	private String CompanyFax;
	/** 手机 */
	private String Mobile;
	/** 手机中文标示 */
	private String MobileChs;
	/** E_mail */
	private String EMail;
	/** 传呼 */
	private String BP;
	/** 手机2 */
	private String Mobile2;
	/** 手机中文标示2 */
	private String MobileChs2;
	/** E_mail2 */
	private String EMail2;
	/** 传呼2 */
	private String BP2;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;
	/** 单位名称 */
	private String GrpName;
	/** 省 */
	private String Province;
	/** City */
	private String City;
	/** 区/县 */
	private String County;

	public static final int FIELDNUM = 31;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LCAddressSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "CustomerNo";
		pk[1] = "AddressNo";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 客户号码<P>客户号码 */
	public String getCustomerNo()
	{
		if (CustomerNo != null && !CustomerNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			CustomerNo = StrTool.unicodeToGBK(CustomerNo);
		}
		return CustomerNo;
	}
	/** 客户号码 */
	public void setCustomerNo(String aCustomerNo)
	{
		CustomerNo = aCustomerNo;
	}
	/** 地址号码<P>地址号码 */
	public int getAddressNo()
	{
		return AddressNo;
	}
	/** 地址号码 */
	public void setAddressNo(int aAddressNo)
	{
		AddressNo = aAddressNo;
	}
	/** 地址号码<P>地址号码 */
	public void setAddressNo(String aAddressNo)
	{
		if (aAddressNo != null && !aAddressNo.equals(""))
		{
			Integer tInteger = new Integer(aAddressNo);
			int i = tInteger.intValue();
			AddressNo = i;
		}
	}

	/** 通讯地址<P>通讯地址 */
	public String getPostalAddress()
	{
		if (PostalAddress != null && !PostalAddress.equals("") && SysConst.CHANGECHARSET == true)
		{
			PostalAddress = StrTool.unicodeToGBK(PostalAddress);
		}
		return PostalAddress;
	}
	/** 通讯地址 */
	public void setPostalAddress(String aPostalAddress)
	{
		PostalAddress = aPostalAddress;
	}
	/** 通讯邮编<P>通讯邮编 */
	public String getZipCode()
	{
		if (ZipCode != null && !ZipCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			ZipCode = StrTool.unicodeToGBK(ZipCode);
		}
		return ZipCode;
	}
	/** 通讯邮编 */
	public void setZipCode(String aZipCode)
	{
		ZipCode = aZipCode;
	}
	/** 通讯电话<P>通讯电话 */
	public String getPhone()
	{
		if (Phone != null && !Phone.equals("") && SysConst.CHANGECHARSET == true)
		{
			Phone = StrTool.unicodeToGBK(Phone);
		}
		return Phone;
	}
	/** 通讯电话 */
	public void setPhone(String aPhone)
	{
		Phone = aPhone;
	}
	/** 通讯传真<P>通讯传真 */
	public String getFax()
	{
		if (Fax != null && !Fax.equals("") && SysConst.CHANGECHARSET == true)
		{
			Fax = StrTool.unicodeToGBK(Fax);
		}
		return Fax;
	}
	/** 通讯传真 */
	public void setFax(String aFax)
	{
		Fax = aFax;
	}
	/** 家庭地址<P>家庭地址 */
	public String getHomeAddress()
	{
		if (HomeAddress != null && !HomeAddress.equals("") && SysConst.CHANGECHARSET == true)
		{
			HomeAddress = StrTool.unicodeToGBK(HomeAddress);
		}
		return HomeAddress;
	}
	/** 家庭地址 */
	public void setHomeAddress(String aHomeAddress)
	{
		HomeAddress = aHomeAddress;
	}
	/** 家庭邮编<P>家庭邮编 */
	public String getHomeZipCode()
	{
		if (HomeZipCode != null && !HomeZipCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			HomeZipCode = StrTool.unicodeToGBK(HomeZipCode);
		}
		return HomeZipCode;
	}
	/** 家庭邮编 */
	public void setHomeZipCode(String aHomeZipCode)
	{
		HomeZipCode = aHomeZipCode;
	}
	/** 家庭电话<P>家庭电话 */
	public String getHomePhone()
	{
		if (HomePhone != null && !HomePhone.equals("") && SysConst.CHANGECHARSET == true)
		{
			HomePhone = StrTool.unicodeToGBK(HomePhone);
		}
		return HomePhone;
	}
	/** 家庭电话 */
	public void setHomePhone(String aHomePhone)
	{
		HomePhone = aHomePhone;
	}
	/** 家庭传真<P>家庭传真 */
	public String getHomeFax()
	{
		if (HomeFax != null && !HomeFax.equals("") && SysConst.CHANGECHARSET == true)
		{
			HomeFax = StrTool.unicodeToGBK(HomeFax);
		}
		return HomeFax;
	}
	/** 家庭传真 */
	public void setHomeFax(String aHomeFax)
	{
		HomeFax = aHomeFax;
	}
	/** 单位地址址<P>单位地址 */
	public String getCompanyAddress()
	{
		if (CompanyAddress != null && !CompanyAddress.equals("") && SysConst.CHANGECHARSET == true)
		{
			CompanyAddress = StrTool.unicodeToGBK(CompanyAddress);
		}
		return CompanyAddress;
	}
	/** 单位地址址 */
	public void setCompanyAddress(String aCompanyAddress)
	{
		CompanyAddress = aCompanyAddress;
	}
	/** 单位邮编<P>单位邮编 */
	public String getCompanyZipCode()
	{
		if (CompanyZipCode != null && !CompanyZipCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			CompanyZipCode = StrTool.unicodeToGBK(CompanyZipCode);
		}
		return CompanyZipCode;
	}
	/** 单位邮编 */
	public void setCompanyZipCode(String aCompanyZipCode)
	{
		CompanyZipCode = aCompanyZipCode;
	}
	/** 单位电话<P>单位电话 */
	public String getCompanyPhone()
	{
		if (CompanyPhone != null && !CompanyPhone.equals("") && SysConst.CHANGECHARSET == true)
		{
			CompanyPhone = StrTool.unicodeToGBK(CompanyPhone);
		}
		return CompanyPhone;
	}
	/** 单位电话 */
	public void setCompanyPhone(String aCompanyPhone)
	{
		CompanyPhone = aCompanyPhone;
	}
	/** 单位传真<P>单位传真 */
	public String getCompanyFax()
	{
		if (CompanyFax != null && !CompanyFax.equals("") && SysConst.CHANGECHARSET == true)
		{
			CompanyFax = StrTool.unicodeToGBK(CompanyFax);
		}
		return CompanyFax;
	}
	/** 单位传真 */
	public void setCompanyFax(String aCompanyFax)
	{
		CompanyFax = aCompanyFax;
	}
	/** 手机<P>手机 */
	public String getMobile()
	{
		if (Mobile != null && !Mobile.equals("") && SysConst.CHANGECHARSET == true)
		{
			Mobile = StrTool.unicodeToGBK(Mobile);
		}
		return Mobile;
	}
	/** 手机 */
	public void setMobile(String aMobile)
	{
		Mobile = aMobile;
	}
	/** 手机中文标示<P>手机中文标示 */
	public String getMobileChs()
	{
		if (MobileChs != null && !MobileChs.equals("") && SysConst.CHANGECHARSET == true)
		{
			MobileChs = StrTool.unicodeToGBK(MobileChs);
		}
		return MobileChs;
	}
	/** 手机中文标示 */
	public void setMobileChs(String aMobileChs)
	{
		MobileChs = aMobileChs;
	}
	/** E_mail<P>e_mail */
	public String getEMail()
	{
		if (EMail != null && !EMail.equals("") && SysConst.CHANGECHARSET == true)
		{
			EMail = StrTool.unicodeToGBK(EMail);
		}
		return EMail;
	}
	/** E_mail */
	public void setEMail(String aEMail)
	{
		EMail = aEMail;
	}
	/** 传呼<P>传呼 */
	public String getBP()
	{
		if (BP != null && !BP.equals("") && SysConst.CHANGECHARSET == true)
		{
			BP = StrTool.unicodeToGBK(BP);
		}
		return BP;
	}
	/** 传呼 */
	public void setBP(String aBP)
	{
		BP = aBP;
	}
	/** 手机2<P>手机2 */
	public String getMobile2()
	{
		if (Mobile2 != null && !Mobile2.equals("") && SysConst.CHANGECHARSET == true)
		{
			Mobile2 = StrTool.unicodeToGBK(Mobile2);
		}
		return Mobile2;
	}
	/** 手机2 */
	public void setMobile2(String aMobile2)
	{
		Mobile2 = aMobile2;
	}
	/** 手机中文标示2<P>手机中文标示2 */
	public String getMobileChs2()
	{
		if (MobileChs2 != null && !MobileChs2.equals("") && SysConst.CHANGECHARSET == true)
		{
			MobileChs2 = StrTool.unicodeToGBK(MobileChs2);
		}
		return MobileChs2;
	}
	/** 手机中文标示2 */
	public void setMobileChs2(String aMobileChs2)
	{
		MobileChs2 = aMobileChs2;
	}
	/** E_mail2<P>e_mail2 */
	public String getEMail2()
	{
		if (EMail2 != null && !EMail2.equals("") && SysConst.CHANGECHARSET == true)
		{
			EMail2 = StrTool.unicodeToGBK(EMail2);
		}
		return EMail2;
	}
	/** E_mail2 */
	public void setEMail2(String aEMail2)
	{
		EMail2 = aEMail2;
	}
	/** 传呼2<P>传呼2 */
	public String getBP2()
	{
		if (BP2 != null && !BP2.equals("") && SysConst.CHANGECHARSET == true)
		{
			BP2 = StrTool.unicodeToGBK(BP2);
		}
		return BP2;
	}
	/** 传呼2 */
	public void setBP2(String aBP2)
	{
		BP2 = aBP2;
	}
	/** 操作员<P>操作员 */
	public String getOperator()
	{
		if (Operator != null && !Operator.equals("") && SysConst.CHANGECHARSET == true)
		{
			Operator = StrTool.unicodeToGBK(Operator);
		}
		return Operator;
	}
	/** 操作员 */
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}
	/** 入机日期<P>入机日期 */
	public String getMakeDate()
	{
		if( MakeDate != null )
			return fDate.getString(MakeDate);
		else
			return null;
	}
	/** 入机日期 */
	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	/** 入机日期<P>入机日期 */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
	}

	/** 入机时间<P>入机时间 */
	public String getMakeTime()
	{
		if (MakeTime != null && !MakeTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			MakeTime = StrTool.unicodeToGBK(MakeTime);
		}
		return MakeTime;
	}
	/** 入机时间 */
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	/** 最后一次修改日期<P>最后一次修改日期 */
	public String getModifyDate()
	{
		if( ModifyDate != null )
			return fDate.getString(ModifyDate);
		else
			return null;
	}
	/** 最后一次修改日期 */
	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	/** 最后一次修改日期<P>最后一次修改日期 */
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals("") )
		{
			ModifyDate = fDate.getDate( aModifyDate );
		}
		else
			ModifyDate = null;
	}

	/** 最后一次修改时间<P> */
	public String getModifyTime()
	{
		if (ModifyTime != null && !ModifyTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			ModifyTime = StrTool.unicodeToGBK(ModifyTime);
		}
		return ModifyTime;
	}
	/** 最后一次修改时间 */
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
	}
	/** 单位名称<P>单位名称 */
	public String getGrpName()
	{
		if (GrpName != null && !GrpName.equals("") && SysConst.CHANGECHARSET == true)
		{
			GrpName = StrTool.unicodeToGBK(GrpName);
		}
		return GrpName;
	}
	/** 单位名称 */
	public void setGrpName(String aGrpName)
	{
		GrpName = aGrpName;
	}
	/** 省<P>省 */
	public String getProvince()
	{
		if (Province != null && !Province.equals("") && SysConst.CHANGECHARSET == true)
		{
			Province = StrTool.unicodeToGBK(Province);
		}
		return Province;
	}
	/** 省 */
	public void setProvince(String aProvince)
	{
		Province = aProvince;
	}
	/** City<P>市 */
	public String getCity()
	{
		if (City != null && !City.equals("") && SysConst.CHANGECHARSET == true)
		{
			City = StrTool.unicodeToGBK(City);
		}
		return City;
	}
	/** City */
	public void setCity(String aCity)
	{
		City = aCity;
	}
	/** 区/县<P>区/县 */
	public String getCounty()
	{
		if (County != null && !County.equals("") && SysConst.CHANGECHARSET == true)
		{
			County = StrTool.unicodeToGBK(County);
		}
		return County;
	}
	/** 区/县 */
	public void setCounty(String aCounty)
	{
		County = aCounty;
	}

	/**
	* 使用另外一个 LCAddressSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LCAddressSchema aLCAddressSchema)
	{
		this.CustomerNo = aLCAddressSchema.getCustomerNo();
		this.AddressNo = aLCAddressSchema.getAddressNo();
		this.PostalAddress = aLCAddressSchema.getPostalAddress();
		this.ZipCode = aLCAddressSchema.getZipCode();
		this.Phone = aLCAddressSchema.getPhone();
		this.Fax = aLCAddressSchema.getFax();
		this.HomeAddress = aLCAddressSchema.getHomeAddress();
		this.HomeZipCode = aLCAddressSchema.getHomeZipCode();
		this.HomePhone = aLCAddressSchema.getHomePhone();
		this.HomeFax = aLCAddressSchema.getHomeFax();
		this.CompanyAddress = aLCAddressSchema.getCompanyAddress();
		this.CompanyZipCode = aLCAddressSchema.getCompanyZipCode();
		this.CompanyPhone = aLCAddressSchema.getCompanyPhone();
		this.CompanyFax = aLCAddressSchema.getCompanyFax();
		this.Mobile = aLCAddressSchema.getMobile();
		this.MobileChs = aLCAddressSchema.getMobileChs();
		this.EMail = aLCAddressSchema.getEMail();
		this.BP = aLCAddressSchema.getBP();
		this.Mobile2 = aLCAddressSchema.getMobile2();
		this.MobileChs2 = aLCAddressSchema.getMobileChs2();
		this.EMail2 = aLCAddressSchema.getEMail2();
		this.BP2 = aLCAddressSchema.getBP2();
		this.Operator = aLCAddressSchema.getOperator();
		this.MakeDate = fDate.getDate( aLCAddressSchema.getMakeDate());
		this.MakeTime = aLCAddressSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLCAddressSchema.getModifyDate());
		this.ModifyTime = aLCAddressSchema.getModifyTime();
		this.GrpName = aLCAddressSchema.getGrpName();
		this.Province = aLCAddressSchema.getProvince();
		this.City = aLCAddressSchema.getCity();
		this.County = aLCAddressSchema.getCounty();
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
			if( rs.getString("CustomerNo") == null )
				this.CustomerNo = null;
			else
				this.CustomerNo = rs.getString("CustomerNo").trim();

			this.AddressNo = rs.getInt("AddressNo");
			if( rs.getString("PostalAddress") == null )
				this.PostalAddress = null;
			else
				this.PostalAddress = rs.getString("PostalAddress").trim();

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

			if( rs.getString("HomeAddress") == null )
				this.HomeAddress = null;
			else
				this.HomeAddress = rs.getString("HomeAddress").trim();

			if( rs.getString("HomeZipCode") == null )
				this.HomeZipCode = null;
			else
				this.HomeZipCode = rs.getString("HomeZipCode").trim();

			if( rs.getString("HomePhone") == null )
				this.HomePhone = null;
			else
				this.HomePhone = rs.getString("HomePhone").trim();

			if( rs.getString("HomeFax") == null )
				this.HomeFax = null;
			else
				this.HomeFax = rs.getString("HomeFax").trim();

			if( rs.getString("CompanyAddress") == null )
				this.CompanyAddress = null;
			else
				this.CompanyAddress = rs.getString("CompanyAddress").trim();

			if( rs.getString("CompanyZipCode") == null )
				this.CompanyZipCode = null;
			else
				this.CompanyZipCode = rs.getString("CompanyZipCode").trim();

			if( rs.getString("CompanyPhone") == null )
				this.CompanyPhone = null;
			else
				this.CompanyPhone = rs.getString("CompanyPhone").trim();

			if( rs.getString("CompanyFax") == null )
				this.CompanyFax = null;
			else
				this.CompanyFax = rs.getString("CompanyFax").trim();

			if( rs.getString("Mobile") == null )
				this.Mobile = null;
			else
				this.Mobile = rs.getString("Mobile").trim();

			if( rs.getString("MobileChs") == null )
				this.MobileChs = null;
			else
				this.MobileChs = rs.getString("MobileChs").trim();

			if( rs.getString("EMail") == null )
				this.EMail = null;
			else
				this.EMail = rs.getString("EMail").trim();

			if( rs.getString("BP") == null )
				this.BP = null;
			else
				this.BP = rs.getString("BP").trim();

			if( rs.getString("Mobile2") == null )
				this.Mobile2 = null;
			else
				this.Mobile2 = rs.getString("Mobile2").trim();

			if( rs.getString("MobileChs2") == null )
				this.MobileChs2 = null;
			else
				this.MobileChs2 = rs.getString("MobileChs2").trim();

			if( rs.getString("EMail2") == null )
				this.EMail2 = null;
			else
				this.EMail2 = rs.getString("EMail2").trim();

			if( rs.getString("BP2") == null )
				this.BP2 = null;
			else
				this.BP2 = rs.getString("BP2").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.ModifyDate = rs.getDate("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("GrpName") == null )
				this.GrpName = null;
			else
				this.GrpName = rs.getString("GrpName").trim();

			if( rs.getString("Province") == null )
				this.Province = null;
			else
				this.Province = rs.getString("Province").trim();

			if( rs.getString("City") == null )
				this.City = null;
			else
				this.City = rs.getString("City").trim();

			if( rs.getString("County") == null )
				this.County = null;
			else
				this.County = rs.getString("County").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAddressSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LCAddressSchema getSchema()
	{
		LCAddressSchema aLCAddressSchema = new LCAddressSchema();
		aLCAddressSchema.setSchema(this);
		return aLCAddressSchema;
	}

	public LCAddressDB getDB()
	{
		LCAddressDB aDBOper = new LCAddressDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCAddress描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(CustomerNo) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(AddressNo) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PostalAddress) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ZipCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Phone) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Fax) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(HomeAddress) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(HomeZipCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(HomePhone) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(HomeFax) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CompanyAddress) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CompanyZipCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CompanyPhone) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CompanyFax) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Mobile) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MobileChs) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(EMail) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BP) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Mobile2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MobileChs2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(EMail2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BP2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ModifyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(GrpName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Province) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(City) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(County) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCAddress>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CustomerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			AddressNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			PostalAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Phone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Fax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			HomeAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			HomeZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			HomePhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			HomeFax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			CompanyAddress = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			CompanyZipCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			CompanyPhone = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			CompanyFax = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Mobile = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			MobileChs = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			EMail = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			BP = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Mobile2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MobileChs2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			EMail2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			BP2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 25, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 26,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			GrpName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			Province = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			City = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			County = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCAddressSchema";
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
		if (FCode.equals("CustomerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CustomerNo));
		}
		if (FCode.equals("AddressNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AddressNo));
		}
		if (FCode.equals("PostalAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PostalAddress));
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
		if (FCode.equals("HomeAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HomeAddress));
		}
		if (FCode.equals("HomeZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HomeZipCode));
		}
		if (FCode.equals("HomePhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HomePhone));
		}
		if (FCode.equals("HomeFax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(HomeFax));
		}
		if (FCode.equals("CompanyAddress"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CompanyAddress));
		}
		if (FCode.equals("CompanyZipCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CompanyZipCode));
		}
		if (FCode.equals("CompanyPhone"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CompanyPhone));
		}
		if (FCode.equals("CompanyFax"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CompanyFax));
		}
		if (FCode.equals("Mobile"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mobile));
		}
		if (FCode.equals("MobileChs"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MobileChs));
		}
		if (FCode.equals("EMail"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EMail));
		}
		if (FCode.equals("BP"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BP));
		}
		if (FCode.equals("Mobile2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Mobile2));
		}
		if (FCode.equals("MobileChs2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MobileChs2));
		}
		if (FCode.equals("EMail2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EMail2));
		}
		if (FCode.equals("BP2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BP2));
		}
		if (FCode.equals("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
		}
		if (FCode.equals("MakeDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
		}
		if (FCode.equals("MakeTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MakeTime));
		}
		if (FCode.equals("ModifyDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
		}
		if (FCode.equals("ModifyTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ModifyTime));
		}
		if (FCode.equals("GrpName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GrpName));
		}
		if (FCode.equals("Province"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Province));
		}
		if (FCode.equals("City"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(City));
		}
		if (FCode.equals("County"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(County));
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
				strFieldValue = StrTool.GBKToUnicode(CustomerNo);
				break;
			case 1:
				strFieldValue = String.valueOf(AddressNo);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(PostalAddress);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ZipCode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(Phone);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Fax);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(HomeAddress);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(HomeZipCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(HomePhone);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(HomeFax);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(CompanyAddress);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(CompanyZipCode);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(CompanyPhone);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(CompanyFax);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Mobile);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(MobileChs);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(EMail);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(BP);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Mobile2);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MobileChs2);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(EMail2);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(BP2);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 24:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 25:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(GrpName);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(Province);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(City);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(County);
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

		if (FCode.equals("CustomerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CustomerNo = FValue.trim();
			}
			else
				CustomerNo = null;
		}
		if (FCode.equals("AddressNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				AddressNo = i;
			}
		}
		if (FCode.equals("PostalAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PostalAddress = FValue.trim();
			}
			else
				PostalAddress = null;
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
		if (FCode.equals("HomeAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HomeAddress = FValue.trim();
			}
			else
				HomeAddress = null;
		}
		if (FCode.equals("HomeZipCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HomeZipCode = FValue.trim();
			}
			else
				HomeZipCode = null;
		}
		if (FCode.equals("HomePhone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HomePhone = FValue.trim();
			}
			else
				HomePhone = null;
		}
		if (FCode.equals("HomeFax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				HomeFax = FValue.trim();
			}
			else
				HomeFax = null;
		}
		if (FCode.equals("CompanyAddress"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CompanyAddress = FValue.trim();
			}
			else
				CompanyAddress = null;
		}
		if (FCode.equals("CompanyZipCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CompanyZipCode = FValue.trim();
			}
			else
				CompanyZipCode = null;
		}
		if (FCode.equals("CompanyPhone"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CompanyPhone = FValue.trim();
			}
			else
				CompanyPhone = null;
		}
		if (FCode.equals("CompanyFax"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CompanyFax = FValue.trim();
			}
			else
				CompanyFax = null;
		}
		if (FCode.equals("Mobile"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Mobile = FValue.trim();
			}
			else
				Mobile = null;
		}
		if (FCode.equals("MobileChs"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MobileChs = FValue.trim();
			}
			else
				MobileChs = null;
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
		if (FCode.equals("BP"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BP = FValue.trim();
			}
			else
				BP = null;
		}
		if (FCode.equals("Mobile2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Mobile2 = FValue.trim();
			}
			else
				Mobile2 = null;
		}
		if (FCode.equals("MobileChs2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MobileChs2 = FValue.trim();
			}
			else
				MobileChs2 = null;
		}
		if (FCode.equals("EMail2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EMail2 = FValue.trim();
			}
			else
				EMail2 = null;
		}
		if (FCode.equals("BP2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BP2 = FValue.trim();
			}
			else
				BP2 = null;
		}
		if (FCode.equals("Operator"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Operator = FValue.trim();
			}
			else
				Operator = null;
		}
		if (FCode.equals("MakeDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				MakeDate = fDate.getDate( FValue );
			}
			else
				MakeDate = null;
		}
		if (FCode.equals("MakeTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
		}
		if (FCode.equals("ModifyDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ModifyDate = fDate.getDate( FValue );
			}
			else
				ModifyDate = null;
		}
		if (FCode.equals("ModifyTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
		}
		if (FCode.equals("GrpName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GrpName = FValue.trim();
			}
			else
				GrpName = null;
		}
		if (FCode.equals("Province"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Province = FValue.trim();
			}
			else
				Province = null;
		}
		if (FCode.equals("City"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				City = FValue.trim();
			}
			else
				City = null;
		}
		if (FCode.equals("County"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				County = FValue.trim();
			}
			else
				County = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LCAddressSchema other = (LCAddressSchema)otherObject;
		return
			CustomerNo.equals(other.getCustomerNo())
			&& AddressNo == other.getAddressNo()
			&& PostalAddress.equals(other.getPostalAddress())
			&& ZipCode.equals(other.getZipCode())
			&& Phone.equals(other.getPhone())
			&& Fax.equals(other.getFax())
			&& HomeAddress.equals(other.getHomeAddress())
			&& HomeZipCode.equals(other.getHomeZipCode())
			&& HomePhone.equals(other.getHomePhone())
			&& HomeFax.equals(other.getHomeFax())
			&& CompanyAddress.equals(other.getCompanyAddress())
			&& CompanyZipCode.equals(other.getCompanyZipCode())
			&& CompanyPhone.equals(other.getCompanyPhone())
			&& CompanyFax.equals(other.getCompanyFax())
			&& Mobile.equals(other.getMobile())
			&& MobileChs.equals(other.getMobileChs())
			&& EMail.equals(other.getEMail())
			&& BP.equals(other.getBP())
			&& Mobile2.equals(other.getMobile2())
			&& MobileChs2.equals(other.getMobileChs2())
			&& EMail2.equals(other.getEMail2())
			&& BP2.equals(other.getBP2())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& GrpName.equals(other.getGrpName())
			&& Province.equals(other.getProvince())
			&& City.equals(other.getCity())
			&& County.equals(other.getCounty());
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
		if( strFieldName.equals("CustomerNo") ) {
			return 0;
		}
		if( strFieldName.equals("AddressNo") ) {
			return 1;
		}
		if( strFieldName.equals("PostalAddress") ) {
			return 2;
		}
		if( strFieldName.equals("ZipCode") ) {
			return 3;
		}
		if( strFieldName.equals("Phone") ) {
			return 4;
		}
		if( strFieldName.equals("Fax") ) {
			return 5;
		}
		if( strFieldName.equals("HomeAddress") ) {
			return 6;
		}
		if( strFieldName.equals("HomeZipCode") ) {
			return 7;
		}
		if( strFieldName.equals("HomePhone") ) {
			return 8;
		}
		if( strFieldName.equals("HomeFax") ) {
			return 9;
		}
		if( strFieldName.equals("CompanyAddress") ) {
			return 10;
		}
		if( strFieldName.equals("CompanyZipCode") ) {
			return 11;
		}
		if( strFieldName.equals("CompanyPhone") ) {
			return 12;
		}
		if( strFieldName.equals("CompanyFax") ) {
			return 13;
		}
		if( strFieldName.equals("Mobile") ) {
			return 14;
		}
		if( strFieldName.equals("MobileChs") ) {
			return 15;
		}
		if( strFieldName.equals("EMail") ) {
			return 16;
		}
		if( strFieldName.equals("BP") ) {
			return 17;
		}
		if( strFieldName.equals("Mobile2") ) {
			return 18;
		}
		if( strFieldName.equals("MobileChs2") ) {
			return 19;
		}
		if( strFieldName.equals("EMail2") ) {
			return 20;
		}
		if( strFieldName.equals("BP2") ) {
			return 21;
		}
		if( strFieldName.equals("Operator") ) {
			return 22;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 23;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 24;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 25;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 26;
		}
		if( strFieldName.equals("GrpName") ) {
			return 27;
		}
		if( strFieldName.equals("Province") ) {
			return 28;
		}
		if( strFieldName.equals("City") ) {
			return 29;
		}
		if( strFieldName.equals("County") ) {
			return 30;
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
				strFieldName = "CustomerNo";
				break;
			case 1:
				strFieldName = "AddressNo";
				break;
			case 2:
				strFieldName = "PostalAddress";
				break;
			case 3:
				strFieldName = "ZipCode";
				break;
			case 4:
				strFieldName = "Phone";
				break;
			case 5:
				strFieldName = "Fax";
				break;
			case 6:
				strFieldName = "HomeAddress";
				break;
			case 7:
				strFieldName = "HomeZipCode";
				break;
			case 8:
				strFieldName = "HomePhone";
				break;
			case 9:
				strFieldName = "HomeFax";
				break;
			case 10:
				strFieldName = "CompanyAddress";
				break;
			case 11:
				strFieldName = "CompanyZipCode";
				break;
			case 12:
				strFieldName = "CompanyPhone";
				break;
			case 13:
				strFieldName = "CompanyFax";
				break;
			case 14:
				strFieldName = "Mobile";
				break;
			case 15:
				strFieldName = "MobileChs";
				break;
			case 16:
				strFieldName = "EMail";
				break;
			case 17:
				strFieldName = "BP";
				break;
			case 18:
				strFieldName = "Mobile2";
				break;
			case 19:
				strFieldName = "MobileChs2";
				break;
			case 20:
				strFieldName = "EMail2";
				break;
			case 21:
				strFieldName = "BP2";
				break;
			case 22:
				strFieldName = "Operator";
				break;
			case 23:
				strFieldName = "MakeDate";
				break;
			case 24:
				strFieldName = "MakeTime";
				break;
			case 25:
				strFieldName = "ModifyDate";
				break;
			case 26:
				strFieldName = "ModifyTime";
				break;
			case 27:
				strFieldName = "GrpName";
				break;
			case 28:
				strFieldName = "Province";
				break;
			case 29:
				strFieldName = "City";
				break;
			case 30:
				strFieldName = "County";
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
		if( strFieldName.equals("CustomerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AddressNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("PostalAddress") ) {
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
		if( strFieldName.equals("HomeAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HomeZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HomePhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("HomeFax") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CompanyAddress") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CompanyZipCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CompanyPhone") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CompanyFax") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Mobile") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MobileChs") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EMail") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BP") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Mobile2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MobileChs2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EMail2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BP2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GrpName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Province") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("City") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("County") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 24:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 25:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
