/*
 * <p>ClassName: LDUserSchema </p>
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
import com.sinosoft.lis.db.LDUserDB;

public class LDUserSchema implements Schema
{
	// @Field
	/** 用户编码 */
	private String UserCode;
	/** 用户姓名 */
	private String UserName;
	/** 机构编码 */
	private String ComCode;
	/** 口令 */
	private String Password;
	/** 用户描述 */
	private String UserDescription;
	/** 用户状态 */
	private String UserState;
	/** 核保权限 */
	private String UWPopedom;
	/** 核赔权限 */
	private String ClaimPopedom;
	/** 其它权限 */
	private String OtherPopedom;
	/** 首席核保标志 */
	private String PopUWFlag;
	/** 超级权限标志 */
	private String SuperPopedomFlag;
	/** 操作员 */
	private String Operator;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 有效开始日期 */
	private Date ValidStartDate;
	/** 有效结束日期 */
	private Date ValidEndDate;
	/** 单证管理员标志 */
	private String CertifyFlag;
	/** 个单保全权限 */
	private String EdorPopedom;
	/** 代理机构 */
	private String AgentCom;
	/** 团单保全权限 */
	private String GEdorPopedom;

	public static final int FIELDNUM = 20;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDUserSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "UserCode";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 用户编码<P>用户编码 */
	public String getUserCode()
	{
		if (UserCode != null && !UserCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			UserCode = StrTool.unicodeToGBK(UserCode);
		}
		return UserCode;
	}
	/** 用户编码 */
	public void setUserCode(String aUserCode)
	{
		UserCode = aUserCode;
	}
	/** 用户姓名<P>用户姓名 */
	public String getUserName()
	{
		if (UserName != null && !UserName.equals("") && SysConst.CHANGECHARSET == true)
		{
			UserName = StrTool.unicodeToGBK(UserName);
		}
		return UserName;
	}
	/** 用户姓名 */
	public void setUserName(String aUserName)
	{
		UserName = aUserName;
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
	/** 口令<P>口令 */
	public String getPassword()
	{
		if (Password != null && !Password.equals("") && SysConst.CHANGECHARSET == true)
		{
			Password = StrTool.unicodeToGBK(Password);
		}
		return Password;
	}
	/** 口令 */
	public void setPassword(String aPassword)
	{
		Password = aPassword;
	}
	/** 用户描述<P>用户描述 */
	public String getUserDescription()
	{
		if (UserDescription != null && !UserDescription.equals("") && SysConst.CHANGECHARSET == true)
		{
			UserDescription = StrTool.unicodeToGBK(UserDescription);
		}
		return UserDescription;
	}
	/** 用户描述 */
	public void setUserDescription(String aUserDescription)
	{
		UserDescription = aUserDescription;
	}
	/** 用户状态<P>用户状态<br>0	有效<br>1	失效 */
	public String getUserState()
	{
		if (UserState != null && !UserState.equals("") && SysConst.CHANGECHARSET == true)
		{
			UserState = StrTool.unicodeToGBK(UserState);
		}
		return UserState;
	}
	/** 用户状态 */
	public void setUserState(String aUserState)
	{
		UserState = aUserState;
	}
	/** 核保权限<P>核保权限<br>A	A级核保员<br>B	B级核保员<br>C	C级核保员<br>D	D级核保员<br>E	E级核保员<br>F	F级核保员 */
	public String getUWPopedom()
	{
		if (UWPopedom != null && !UWPopedom.equals("") && SysConst.CHANGECHARSET == true)
		{
			UWPopedom = StrTool.unicodeToGBK(UWPopedom);
		}
		return UWPopedom;
	}
	/** 核保权限 */
	public void setUWPopedom(String aUWPopedom)
	{
		UWPopedom = aUWPopedom;
	}
	/** 核赔权限<P>核赔权限 */
	public String getClaimPopedom()
	{
		if (ClaimPopedom != null && !ClaimPopedom.equals("") && SysConst.CHANGECHARSET == true)
		{
			ClaimPopedom = StrTool.unicodeToGBK(ClaimPopedom);
		}
		return ClaimPopedom;
	}
	/** 核赔权限 */
	public void setClaimPopedom(String aClaimPopedom)
	{
		ClaimPopedom = aClaimPopedom;
	}
	/** 其它权限<P>其它权限 */
	public String getOtherPopedom()
	{
		if (OtherPopedom != null && !OtherPopedom.equals("") && SysConst.CHANGECHARSET == true)
		{
			OtherPopedom = StrTool.unicodeToGBK(OtherPopedom);
		}
		return OtherPopedom;
	}
	/** 其它权限 */
	public void setOtherPopedom(String aOtherPopedom)
	{
		OtherPopedom = aOtherPopedom;
	}
	/** 首席核保标志<P>首席核保标志 */
	public String getPopUWFlag()
	{
		if (PopUWFlag != null && !PopUWFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			PopUWFlag = StrTool.unicodeToGBK(PopUWFlag);
		}
		return PopUWFlag;
	}
	/** 首席核保标志 */
	public void setPopUWFlag(String aPopUWFlag)
	{
		PopUWFlag = aPopUWFlag;
	}
	/** 超级权限标志<P>超级权限标志<br>0	没有超级权限<br>1	有超级权限 */
	public String getSuperPopedomFlag()
	{
		if (SuperPopedomFlag != null && !SuperPopedomFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			SuperPopedomFlag = StrTool.unicodeToGBK(SuperPopedomFlag);
		}
		return SuperPopedomFlag;
	}
	/** 超级权限标志 */
	public void setSuperPopedomFlag(String aSuperPopedomFlag)
	{
		SuperPopedomFlag = aSuperPopedomFlag;
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
	/** 入机日期<P> */
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
	/** 入机日期<P> */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
	}

	/** 入机时间<P> */
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
	/** 有效开始日期<P>有效开始日期 */
	public String getValidStartDate()
	{
		if( ValidStartDate != null )
			return fDate.getString(ValidStartDate);
		else
			return null;
	}
	/** 有效开始日期 */
	public void setValidStartDate(Date aValidStartDate)
	{
		ValidStartDate = aValidStartDate;
	}
	/** 有效开始日期<P>有效开始日期 */
	public void setValidStartDate(String aValidStartDate)
	{
		if (aValidStartDate != null && !aValidStartDate.equals("") )
		{
			ValidStartDate = fDate.getDate( aValidStartDate );
		}
		else
			ValidStartDate = null;
	}

	/** 有效结束日期<P>有效结束日期 */
	public String getValidEndDate()
	{
		if( ValidEndDate != null )
			return fDate.getString(ValidEndDate);
		else
			return null;
	}
	/** 有效结束日期 */
	public void setValidEndDate(Date aValidEndDate)
	{
		ValidEndDate = aValidEndDate;
	}
	/** 有效结束日期<P>有效结束日期 */
	public void setValidEndDate(String aValidEndDate)
	{
		if (aValidEndDate != null && !aValidEndDate.equals("") )
		{
			ValidEndDate = fDate.getDate( aValidEndDate );
		}
		else
			ValidEndDate = null;
	}

	/** 单证管理员标志<P>单证管理员标志 */
	public String getCertifyFlag()
	{
		if (CertifyFlag != null && !CertifyFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			CertifyFlag = StrTool.unicodeToGBK(CertifyFlag);
		}
		return CertifyFlag;
	}
	/** 单证管理员标志 */
	public void setCertifyFlag(String aCertifyFlag)
	{
		CertifyFlag = aCertifyFlag;
	}
	/** 个单保全权限<P>个单保全权限 */
	public String getEdorPopedom()
	{
		if (EdorPopedom != null && !EdorPopedom.equals("") && SysConst.CHANGECHARSET == true)
		{
			EdorPopedom = StrTool.unicodeToGBK(EdorPopedom);
		}
		return EdorPopedom;
	}
	/** 个单保全权限 */
	public void setEdorPopedom(String aEdorPopedom)
	{
		EdorPopedom = aEdorPopedom;
	}
	/** 代理机构<P>代理机构 */
	public String getAgentCom()
	{
		if (AgentCom != null && !AgentCom.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentCom = StrTool.unicodeToGBK(AgentCom);
		}
		return AgentCom;
	}
	/** 代理机构 */
	public void setAgentCom(String aAgentCom)
	{
		AgentCom = aAgentCom;
	}
	/** 团单保全权限<P>团单保全权限 */
	public String getGEdorPopedom()
	{
		if (GEdorPopedom != null && !GEdorPopedom.equals("") && SysConst.CHANGECHARSET == true)
		{
			GEdorPopedom = StrTool.unicodeToGBK(GEdorPopedom);
		}
		return GEdorPopedom;
	}
	/** 团单保全权限 */
	public void setGEdorPopedom(String aGEdorPopedom)
	{
		GEdorPopedom = aGEdorPopedom;
	}

	/**
	* 使用另外一个 LDUserSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LDUserSchema aLDUserSchema)
	{
		this.UserCode = aLDUserSchema.getUserCode();
		this.UserName = aLDUserSchema.getUserName();
		this.ComCode = aLDUserSchema.getComCode();
		this.Password = aLDUserSchema.getPassword();
		this.UserDescription = aLDUserSchema.getUserDescription();
		this.UserState = aLDUserSchema.getUserState();
		this.UWPopedom = aLDUserSchema.getUWPopedom();
		this.ClaimPopedom = aLDUserSchema.getClaimPopedom();
		this.OtherPopedom = aLDUserSchema.getOtherPopedom();
		this.PopUWFlag = aLDUserSchema.getPopUWFlag();
		this.SuperPopedomFlag = aLDUserSchema.getSuperPopedomFlag();
		this.Operator = aLDUserSchema.getOperator();
		this.MakeDate = fDate.getDate( aLDUserSchema.getMakeDate());
		this.MakeTime = aLDUserSchema.getMakeTime();
		this.ValidStartDate = fDate.getDate( aLDUserSchema.getValidStartDate());
		this.ValidEndDate = fDate.getDate( aLDUserSchema.getValidEndDate());
		this.CertifyFlag = aLDUserSchema.getCertifyFlag();
		this.EdorPopedom = aLDUserSchema.getEdorPopedom();
		this.AgentCom = aLDUserSchema.getAgentCom();
		this.GEdorPopedom = aLDUserSchema.getGEdorPopedom();
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
			if( rs.getString("UserCode") == null )
				this.UserCode = null;
			else
				this.UserCode = rs.getString("UserCode").trim();

			if( rs.getString("UserName") == null )
				this.UserName = null;
			else
				this.UserName = rs.getString("UserName").trim();

			if( rs.getString("ComCode") == null )
				this.ComCode = null;
			else
				this.ComCode = rs.getString("ComCode").trim();

			if( rs.getString("Password") == null )
				this.Password = null;
			else
				this.Password = rs.getString("Password").trim();

			if( rs.getString("UserDescription") == null )
				this.UserDescription = null;
			else
				this.UserDescription = rs.getString("UserDescription").trim();

			if( rs.getString("UserState") == null )
				this.UserState = null;
			else
				this.UserState = rs.getString("UserState").trim();

			if( rs.getString("UWPopedom") == null )
				this.UWPopedom = null;
			else
				this.UWPopedom = rs.getString("UWPopedom").trim();

			if( rs.getString("ClaimPopedom") == null )
				this.ClaimPopedom = null;
			else
				this.ClaimPopedom = rs.getString("ClaimPopedom").trim();

			if( rs.getString("OtherPopedom") == null )
				this.OtherPopedom = null;
			else
				this.OtherPopedom = rs.getString("OtherPopedom").trim();

			if( rs.getString("PopUWFlag") == null )
				this.PopUWFlag = null;
			else
				this.PopUWFlag = rs.getString("PopUWFlag").trim();

			if( rs.getString("SuperPopedomFlag") == null )
				this.SuperPopedomFlag = null;
			else
				this.SuperPopedomFlag = rs.getString("SuperPopedomFlag").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

			this.MakeDate = rs.getDate("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();
            
			this.ValidStartDate = rs.getDate("ValidStartDate");
			this.ValidEndDate = rs.getDate("ValidEndDate");
			if( rs.getString("CertifyFlag") == null )
				this.CertifyFlag = null;
			else
				this.CertifyFlag = rs.getString("CertifyFlag").trim();

			if( rs.getString("EdorPopedom") == null )
				this.EdorPopedom = null;
			else
				this.EdorPopedom = rs.getString("EdorPopedom").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("GEdorPopedom") == null )
				this.GEdorPopedom = null;
			else
				this.GEdorPopedom = rs.getString("GEdorPopedom").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUserSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LDUserSchema getSchema()
	{
		LDUserSchema aLDUserSchema = new LDUserSchema();
		aLDUserSchema.setSchema(this);
		return aLDUserSchema;
	}

	public LDUserDB getDB()
	{
		LDUserDB aDBOper = new LDUserDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUser描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(UserCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UserName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ComCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Password) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UserDescription) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UserState) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UWPopedom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ClaimPopedom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OtherPopedom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PopUWFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SuperPopedomFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ValidStartDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ValidEndDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CertifyFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(EdorPopedom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(GEdorPopedom) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDUser>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			UserCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			UserName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ComCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Password = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			UserDescription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			UserState = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			UWPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ClaimPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			OtherPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			PopUWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			SuperPopedomFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ValidStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			ValidEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			CertifyFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			EdorPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			GEdorPopedom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDUserSchema";
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
		if (FCode.equals("UserCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UserCode));
		}
		if (FCode.equals("UserName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UserName));
		}
		if (FCode.equals("ComCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ComCode));
		}
		if (FCode.equals("Password"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Password));
		}
		if (FCode.equals("UserDescription"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UserDescription));
		}
		if (FCode.equals("UserState"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UserState));
		}
		if (FCode.equals("UWPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWPopedom));
		}
		if (FCode.equals("ClaimPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ClaimPopedom));
		}
		if (FCode.equals("OtherPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherPopedom));
		}
		if (FCode.equals("PopUWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PopUWFlag));
		}
		if (FCode.equals("SuperPopedomFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SuperPopedomFlag));
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
		if (FCode.equals("ValidStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getValidStartDate()));
		}
		if (FCode.equals("ValidEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getValidEndDate()));
		}
		if (FCode.equals("CertifyFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CertifyFlag));
		}
		if (FCode.equals("EdorPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(EdorPopedom));
		}
		if (FCode.equals("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equals("GEdorPopedom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(GEdorPopedom));
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
				strFieldValue = StrTool.GBKToUnicode(UserCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(UserName);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ComCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Password);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(UserDescription);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(UserState);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(UWPopedom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ClaimPopedom);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(OtherPopedom);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(PopUWFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(SuperPopedomFlag);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Operator);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getValidStartDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getValidEndDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(CertifyFlag);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(EdorPopedom);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(GEdorPopedom);
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

		if (FCode.equals("UserCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UserCode = FValue.trim();
			}
			else
				UserCode = null;
		}
		if (FCode.equals("UserName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UserName = FValue.trim();
			}
			else
				UserName = null;
		}
		if (FCode.equals("ComCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ComCode = FValue.trim();
			}
			else
				ComCode = null;
		}
		if (FCode.equals("Password"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Password = FValue.trim();
			}
			else
				Password = null;
		}
		if (FCode.equals("UserDescription"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UserDescription = FValue.trim();
			}
			else
				UserDescription = null;
		}
		if (FCode.equals("UserState"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UserState = FValue.trim();
			}
			else
				UserState = null;
		}
		if (FCode.equals("UWPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWPopedom = FValue.trim();
			}
			else
				UWPopedom = null;
		}
		if (FCode.equals("ClaimPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ClaimPopedom = FValue.trim();
			}
			else
				ClaimPopedom = null;
		}
		if (FCode.equals("OtherPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherPopedom = FValue.trim();
			}
			else
				OtherPopedom = null;
		}
		if (FCode.equals("PopUWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PopUWFlag = FValue.trim();
			}
			else
				PopUWFlag = null;
		}
		if (FCode.equals("SuperPopedomFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SuperPopedomFlag = FValue.trim();
			}
			else
				SuperPopedomFlag = null;
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
		if (FCode.equals("ValidStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ValidStartDate = fDate.getDate( FValue );
			}
			else
				ValidStartDate = null;
		}
		if (FCode.equals("ValidEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ValidEndDate = fDate.getDate( FValue );
			}
			else
				ValidEndDate = null;
		}
		if (FCode.equals("CertifyFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CertifyFlag = FValue.trim();
			}
			else
				CertifyFlag = null;
		}
		if (FCode.equals("EdorPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				EdorPopedom = FValue.trim();
			}
			else
				EdorPopedom = null;
		}
		if (FCode.equals("AgentCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCom = FValue.trim();
			}
			else
				AgentCom = null;
		}
		if (FCode.equals("GEdorPopedom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				GEdorPopedom = FValue.trim();
			}
			else
				GEdorPopedom = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDUserSchema other = (LDUserSchema)otherObject;
		return
			UserCode.equals(other.getUserCode())
			&& UserName.equals(other.getUserName())
			&& ComCode.equals(other.getComCode())
			&& Password.equals(other.getPassword())
			&& UserDescription.equals(other.getUserDescription())
			&& UserState.equals(other.getUserState())
			&& UWPopedom.equals(other.getUWPopedom())
			&& ClaimPopedom.equals(other.getClaimPopedom())
			&& OtherPopedom.equals(other.getOtherPopedom())
			&& PopUWFlag.equals(other.getPopUWFlag())
			&& SuperPopedomFlag.equals(other.getSuperPopedomFlag())
			&& Operator.equals(other.getOperator())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ValidStartDate).equals(other.getValidStartDate())
			&& fDate.getString(ValidEndDate).equals(other.getValidEndDate())
			&& CertifyFlag.equals(other.getCertifyFlag())
			&& EdorPopedom.equals(other.getEdorPopedom())
			&& AgentCom.equals(other.getAgentCom())
			&& GEdorPopedom.equals(other.getGEdorPopedom());
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
		if( strFieldName.equals("UserCode") ) {
			return 0;
		}
		if( strFieldName.equals("UserName") ) {
			return 1;
		}
		if( strFieldName.equals("ComCode") ) {
			return 2;
		}
		if( strFieldName.equals("Password") ) {
			return 3;
		}
		if( strFieldName.equals("UserDescription") ) {
			return 4;
		}
		if( strFieldName.equals("UserState") ) {
			return 5;
		}
		if( strFieldName.equals("UWPopedom") ) {
			return 6;
		}
		if( strFieldName.equals("ClaimPopedom") ) {
			return 7;
		}
		if( strFieldName.equals("OtherPopedom") ) {
			return 8;
		}
		if( strFieldName.equals("PopUWFlag") ) {
			return 9;
		}
		if( strFieldName.equals("SuperPopedomFlag") ) {
			return 10;
		}
		if( strFieldName.equals("Operator") ) {
			return 11;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 12;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 13;
		}
		if( strFieldName.equals("ValidStartDate") ) {
			return 14;
		}
		if( strFieldName.equals("ValidEndDate") ) {
			return 15;
		}
		if( strFieldName.equals("CertifyFlag") ) {
			return 16;
		}
		if( strFieldName.equals("EdorPopedom") ) {
			return 17;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 18;
		}
		if( strFieldName.equals("GEdorPopedom") ) {
			return 19;
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
				strFieldName = "UserCode";
				break;
			case 1:
				strFieldName = "UserName";
				break;
			case 2:
				strFieldName = "ComCode";
				break;
			case 3:
				strFieldName = "Password";
				break;
			case 4:
				strFieldName = "UserDescription";
				break;
			case 5:
				strFieldName = "UserState";
				break;
			case 6:
				strFieldName = "UWPopedom";
				break;
			case 7:
				strFieldName = "ClaimPopedom";
				break;
			case 8:
				strFieldName = "OtherPopedom";
				break;
			case 9:
				strFieldName = "PopUWFlag";
				break;
			case 10:
				strFieldName = "SuperPopedomFlag";
				break;
			case 11:
				strFieldName = "Operator";
				break;
			case 12:
				strFieldName = "MakeDate";
				break;
			case 13:
				strFieldName = "MakeTime";
				break;
			case 14:
				strFieldName = "ValidStartDate";
				break;
			case 15:
				strFieldName = "ValidEndDate";
				break;
			case 16:
				strFieldName = "CertifyFlag";
				break;
			case 17:
				strFieldName = "EdorPopedom";
				break;
			case 18:
				strFieldName = "AgentCom";
				break;
			case 19:
				strFieldName = "GEdorPopedom";
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
		if( strFieldName.equals("UserCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UserName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ComCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Password") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UserDescription") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UserState") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ClaimPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OtherPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PopUWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SuperPopedomFlag") ) {
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
		if( strFieldName.equals("ValidStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ValidEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("CertifyFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("EdorPopedom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("GEdorPopedom") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 13:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 14:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 15:
				nFieldType = Schema.TYPE_DATE;
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
