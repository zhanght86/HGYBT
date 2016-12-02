/*
 * <p>ClassName: LKTransAuthorizationSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 * @CreateDate：2010-11-12
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LKTransAuthorizationDB;

public class LKTransAuthorizationSchema implements Schema
{
	// @Field
	/** 管理机构 */
	private String ManageCom;
	/** 银行编码 */
	private String BankCode;
	/** 地区编码 */
	private String BankBranch;
	/** 网点编码 */
	private String BankNode;
	/** 交易码 */
	private String FuncFlag;
	/** 险种编码 */
	private String RiskCode;
	/** 备用 */
	private String StandByFlag;
	/** 入机日期 */
	private int MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 修改日期 */
	private int ModifyDate;
	/** 修改时间 */
	private String ModifyTime;
	/** 操作员号 */
	private String Operator;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LKTransAuthorizationSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[5];
		pk[0] = "ManageCom";
		pk[1] = "BankCode";
		pk[2] = "BankBranch";
		pk[3] = "BankNode";
		pk[4] = "FuncFlag";
		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 管理机构<P>中支公司机构 */
	public String getManageCom()
	{
		if (ManageCom != null && !ManageCom.equals("") && SysConst.CHANGECHARSET == true)
		{
			ManageCom = StrTool.unicodeToGBK(ManageCom);
		}
		return ManageCom;
	}
	/** 管理机构 */
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	/** 银行编码<P>大行类别 */
	public String getBankCode()
	{
		if (BankCode != null && !BankCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			BankCode = StrTool.unicodeToGBK(BankCode);
		}
		return BankCode;
	}
	/** 银行编码 */
	public void setBankCode(String aBankCode)
	{
		BankCode = aBankCode;
	}
	/** 地区编码<P> */
	public String getBankBranch()
	{
		if (BankBranch != null && !BankBranch.equals("") && SysConst.CHANGECHARSET == true)
		{
			BankBranch = StrTool.unicodeToGBK(BankBranch);
		}
		return BankBranch;
	}
	/** 地区编码 */
	public void setBankBranch(String aBankBranch)
	{
		BankBranch = aBankBranch;
	}
	/** 网点编码<P>总公司、分公司、中支公司 */
	public String getBankNode()
	{
		if (BankNode != null && !BankNode.equals("") && SysConst.CHANGECHARSET == true)
		{
			BankNode = StrTool.unicodeToGBK(BankNode);
		}
		return BankNode;
	}
	/** 网点编码 */
	public void setBankNode(String aBankNode)
	{
		BankNode = aBankNode;
	}
	/** 交易码<P> */
	public String getFuncFlag()
	{
		if (FuncFlag != null && !FuncFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			FuncFlag = StrTool.unicodeToGBK(FuncFlag);
		}
		return FuncFlag;
	}
	/** 交易码 */
	public void setFuncFlag(String aFuncFlag)
	{
		FuncFlag = aFuncFlag;
	}
	/** 险种编码<P> */
	public String getRiskCode()
	{
		if (RiskCode != null && !RiskCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			RiskCode = StrTool.unicodeToGBK(RiskCode);
		}
		return RiskCode;
	}
	/** 险种编码 */
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/** 备用<P> */
	public String getStandByFlag()
	{
		if (StandByFlag != null && !StandByFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			StandByFlag = StrTool.unicodeToGBK(StandByFlag);
		}
		return StandByFlag;
	}
	/** 备用 */
	public void setStandByFlag(String aStandByFlag)
	{
		StandByFlag = aStandByFlag;
	}
	/** 入机日期<P> */
	public int getMakeDate()
	{
		return MakeDate;
	}
	/** 入机日期 */
	public void setMakeDate(int aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	/** 入机日期<P> */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals(""))
		{
			Integer tInteger = new Integer(aMakeDate);
			int i = tInteger.intValue();
			MakeDate = i;
		}
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
	/** 修改日期<P> */
	public int getModifyDate()
	{
		return ModifyDate;
	}
	/** 修改日期 */
	public void setModifyDate(int aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	/** 修改日期<P> */
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals(""))
		{
			Integer tInteger = new Integer(aModifyDate);
			int i = tInteger.intValue();
			ModifyDate = i;
		}
	}

	/** 修改时间<P> */
	public String getModifyTime()
	{
		if (ModifyTime != null && !ModifyTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			ModifyTime = StrTool.unicodeToGBK(ModifyTime);
		}
		return ModifyTime;
	}
	/** 修改时间 */
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
	}
	/** 操作员号<P> */
	public String getOperator()
	{
		if (Operator != null && !Operator.equals("") && SysConst.CHANGECHARSET == true)
		{
			Operator = StrTool.unicodeToGBK(Operator);
		}
		return Operator;
	}
	/** 操作员号 */
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}

	/**
	* 使用另外一个 LKTransAuthorizationSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LKTransAuthorizationSchema aLKTransAuthorizationSchema)
	{
		this.ManageCom = aLKTransAuthorizationSchema.getManageCom();
		this.BankCode = aLKTransAuthorizationSchema.getBankCode();
		this.BankBranch = aLKTransAuthorizationSchema.getBankBranch();
		this.BankNode = aLKTransAuthorizationSchema.getBankNode();
		this.FuncFlag = aLKTransAuthorizationSchema.getFuncFlag();
		this.RiskCode = aLKTransAuthorizationSchema.getRiskCode();
		this.StandByFlag = aLKTransAuthorizationSchema.getStandByFlag();
		this.MakeDate = aLKTransAuthorizationSchema.getMakeDate();
		this.MakeTime = aLKTransAuthorizationSchema.getMakeTime();
		this.ModifyDate = aLKTransAuthorizationSchema.getModifyDate();
		this.ModifyTime = aLKTransAuthorizationSchema.getModifyTime();
		this.Operator = aLKTransAuthorizationSchema.getOperator();
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
			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("BankCode") == null )
				this.BankCode = null;
			else
				this.BankCode = rs.getString("BankCode").trim();

			if( rs.getString("BankBranch") == null )
				this.BankBranch = null;
			else
				this.BankBranch = rs.getString("BankBranch").trim();

			if( rs.getString("BankNode") == null )
				this.BankNode = null;
			else
				this.BankNode = rs.getString("BankNode").trim();

			if( rs.getString("FuncFlag") == null )
				this.FuncFlag = null;
			else
				this.FuncFlag = rs.getString("FuncFlag").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("StandByFlag") == null )
				this.StandByFlag = null;
			else
				this.StandByFlag = rs.getString("StandByFlag").trim();

			this.MakeDate = rs.getInt("MakeDate");
			if( rs.getString("MakeTime") == null )
				this.MakeTime = null;
			else
				this.MakeTime = rs.getString("MakeTime").trim();

			this.ModifyDate = rs.getInt("ModifyDate");
			if( rs.getString("ModifyTime") == null )
				this.ModifyTime = null;
			else
				this.ModifyTime = rs.getString("ModifyTime").trim();

			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LKTransAuthorizationSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LKTransAuthorizationSchema getSchema()
	{
		LKTransAuthorizationSchema aLKTransAuthorizationSchema = new LKTransAuthorizationSchema();
		aLKTransAuthorizationSchema.setSchema(this);
		return aLKTransAuthorizationSchema;
	}

	public LKTransAuthorizationDB getDB()
	{
		LKTransAuthorizationDB aDBOper = new LKTransAuthorizationDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKTransAuthorization描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(ManageCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BankCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BankBranch) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BankNode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FuncFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RiskCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(StandByFlag) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyDate) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKTransAuthorization>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			BankCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			BankBranch = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			BankNode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			FuncFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			StandByFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,8,SysConst.PACKAGESPILTER))).intValue();
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ModifyDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LKTransAuthorizationSchema";
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
		if (FCode.equals("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equals("BankCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankCode));
		}
		if (FCode.equals("BankBranch"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankBranch));
		}
		if (FCode.equals("BankNode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BankNode));
		}
		if (FCode.equals("FuncFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FuncFlag));
		}
		if (FCode.equals("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equals("StandByFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(StandByFlag));
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
		if (FCode.equals("Operator"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Operator));
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
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(BankCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(BankBranch);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(BankNode);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(FuncFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(StandByFlag);
				break;
			case 7:
				strFieldValue = String.valueOf(MakeDate);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 9:
				strFieldValue = String.valueOf(ModifyDate);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Operator);
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

		if (FCode.equals("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equals("BankCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankCode = FValue.trim();
			}
			else
				BankCode = null;
		}
		if (FCode.equals("BankBranch"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankBranch = FValue.trim();
			}
			else
				BankBranch = null;
		}
		if (FCode.equals("BankNode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BankNode = FValue.trim();
			}
			else
				BankNode = null;
		}
		if (FCode.equals("FuncFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FuncFlag = FValue.trim();
			}
			else
				FuncFlag = null;
		}
		if (FCode.equals("RiskCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RiskCode = FValue.trim();
			}
			else
				RiskCode = null;
		}
		if (FCode.equals("StandByFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				StandByFlag = FValue.trim();
			}
			else
				StandByFlag = null;
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
				MakeTime = FValue.trim();
			}
			else
				MakeTime = null;
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
				ModifyTime = FValue.trim();
			}
			else
				ModifyTime = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LKTransAuthorizationSchema other = (LKTransAuthorizationSchema)otherObject;
		return
			ManageCom.equals(other.getManageCom())
			&& BankCode.equals(other.getBankCode())
			&& BankBranch.equals(other.getBankBranch())
			&& BankNode.equals(other.getBankNode())
			&& FuncFlag.equals(other.getFuncFlag())
			&& RiskCode.equals(other.getRiskCode())
			&& StandByFlag.equals(other.getStandByFlag())
			&& MakeDate == other.getMakeDate()
			&& MakeTime.equals(other.getMakeTime())
			&& ModifyDate == other.getModifyDate()
			&& ModifyTime.equals(other.getModifyTime())
			&& Operator.equals(other.getOperator());
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
		if( strFieldName.equals("ManageCom") ) {
			return 0;
		}
		if( strFieldName.equals("BankCode") ) {
			return 1;
		}
		if( strFieldName.equals("BankBranch") ) {
			return 2;
		}
		if( strFieldName.equals("BankNode") ) {
			return 3;
		}
		if( strFieldName.equals("FuncFlag") ) {
			return 4;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 5;
		}
		if( strFieldName.equals("StandByFlag") ) {
			return 6;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 7;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 8;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 9;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 10;
		}
		if( strFieldName.equals("Operator") ) {
			return 11;
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
				strFieldName = "ManageCom";
				break;
			case 1:
				strFieldName = "BankCode";
				break;
			case 2:
				strFieldName = "BankBranch";
				break;
			case 3:
				strFieldName = "BankNode";
				break;
			case 4:
				strFieldName = "FuncFlag";
				break;
			case 5:
				strFieldName = "RiskCode";
				break;
			case 6:
				strFieldName = "StandByFlag";
				break;
			case 7:
				strFieldName = "MakeDate";
				break;
			case 8:
				strFieldName = "MakeTime";
				break;
			case 9:
				strFieldName = "ModifyDate";
				break;
			case 10:
				strFieldName = "ModifyTime";
				break;
			case 11:
				strFieldName = "Operator";
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
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankBranch") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BankNode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FuncFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StandByFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MakeDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("MakeTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Operator") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 8:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 9:
				nFieldType = Schema.TYPE_INT;
				break;
			case 10:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 11:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
