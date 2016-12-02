/*
 * <p>ClassName: LMUWSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-01-29
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LMUWDB;

public class LMUWSchema implements Schema
{
	// @Field
	/** 核保编码 */
	private String UWCode;
	/** 险种编码 */
	private String RiskCode;
	/** 算法 */
	private String CalCode;
	/** 未通过返回信息 */
	private String MSG;
	/** 备注 */
	private String UWMSG;
	/** Returnflag */
	private String ReturnFlag;
	/** 备份1 */
	private String bak1;
	/** 备份2 */
	private String bak2;
	/** 备份3 */
	private String bak3;
	/** 备份4 */
	private String bak4;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMUWSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "UWCode";
		pk[1] = "RiskCode";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 核保编码<P>核保编码，与实施核保lmcheckfield表中的calcode相关 */
	public String getUWCode()
	{
		if (UWCode != null && !UWCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			UWCode = StrTool.unicodeToGBK(UWCode);
		}
		return UWCode;
	}
	/** 核保编码 */
	public void setUWCode(String aUWCode)
	{
		UWCode = aUWCode;
	}
	/** 险种编码<P>险种编码 */
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
	/** 算法<P>算法 */
	public String getCalCode()
	{
		if (CalCode != null && !CalCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			CalCode = StrTool.unicodeToGBK(CalCode);
		}
		return CalCode;
	}
	/** 算法 */
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
	}
	/** 未通过返回信息<P>若有相关非实时核保规则但核保未通过，则返回MSG拒保 */
	public String getMSG()
	{
		if (MSG != null && !MSG.equals("") && SysConst.CHANGECHARSET == true)
		{
			MSG = StrTool.unicodeToGBK(MSG);
		}
		return MSG;
	}
	/** 未通过返回信息 */
	public void setMSG(String aMSG)
	{
		MSG = aMSG;
	}
	/** 备注<P>非实时核保通过，返回提示进行非实时核保 */
	public String getUWMSG()
	{
		if (UWMSG != null && !UWMSG.equals("") && SysConst.CHANGECHARSET == true)
		{
			UWMSG = StrTool.unicodeToGBK(UWMSG);
		}
		return UWMSG;
	}
	/** 备注 */
	public void setUWMSG(String aUWMSG)
	{
		UWMSG = aUWMSG;
	}
	/** Returnflag<P> */
	public String getReturnFlag()
	{
		if (ReturnFlag != null && !ReturnFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			ReturnFlag = StrTool.unicodeToGBK(ReturnFlag);
		}
		return ReturnFlag;
	}
	/** Returnflag */
	public void setReturnFlag(String aReturnFlag)
	{
		ReturnFlag = aReturnFlag;
	}
	/** 备份1<P>核保核赔时有用,分级核保核赔. 核保级别 */
	public String getbak1()
	{
		if (bak1 != null && !bak1.equals("") && SysConst.CHANGECHARSET == true)
		{
			bak1 = StrTool.unicodeToGBK(bak1);
		}
		return bak1;
	}
	/** 备份1 */
	public void setbak1(String abak1)
	{
		bak1 = abak1;
	}
	/** 备份2<P>对核保核赔,返回上报级别.<br>��控制描述,返回控制类型:<br>对 checkfield<br>   F(f)  返回值,不显示. 控制附���，定��单等<br>   N(n)  显示提示信息,display p_rta1t<br>   H(h)  不显示提示信息,不display p_rta1t<br>   Y(y)  不合要求才显示,��示提示信息 */
	public String getbak2()
	{
		if (bak2 != null && !bak2.equals("") && SysConst.CHANGECHARSET == true)
		{
			bak2 = StrTool.unicodeToGBK(bak2);
		}
		return bak2;
	}
	/** 备份2 */
	public void setbak2(String abak2)
	{
		bak2 = abak2;
	}
	/** 备份3<P>核保可通过标记(“N”-不能核保通过标志)在remark中记录标记, 在外部关��程序修改此标记后,可以由核保员决定是否核保通过 */
	public String getbak3()
	{
		if (bak3 != null && !bak3.equals("") && SysConst.CHANGECHARSET == true)
		{
			bak3 = StrTool.unicodeToGBK(bak3);
		}
		return bak3;
	}
	/** 备份3 */
	public void setbak3(String abak3)
	{
		bak3 = abak3;
	}
	/** 备份4<P>该字段描述哪些结果对于该控制是有效的，如果有多个有效值，则通过“;”将多个有效值分开。 */
	public String getbak4()
	{
		if (bak4 != null && !bak4.equals("") && SysConst.CHANGECHARSET == true)
		{
			bak4 = StrTool.unicodeToGBK(bak4);
		}
		return bak4;
	}
	/** 备份4 */
	public void setbak4(String abak4)
	{
		bak4 = abak4;
	}

	/**
	* 使用另外一个 LMUWSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LMUWSchema aLMUWSchema)
	{
		this.UWCode = aLMUWSchema.getUWCode();
		this.RiskCode = aLMUWSchema.getRiskCode();
		this.CalCode = aLMUWSchema.getCalCode();
		this.MSG = aLMUWSchema.getMSG();
		this.UWMSG = aLMUWSchema.getUWMSG();
		this.ReturnFlag = aLMUWSchema.getReturnFlag();
		this.bak1 = aLMUWSchema.getbak1();
		this.bak2 = aLMUWSchema.getbak2();
		this.bak3 = aLMUWSchema.getbak3();
		this.bak4 = aLMUWSchema.getbak4();
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
			if( rs.getString("UWCode") == null )
				this.UWCode = null;
			else
				this.UWCode = rs.getString("UWCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("MSG") == null )
				this.MSG = null;
			else
				this.MSG = rs.getString("MSG").trim();

			if( rs.getString("UWMSG") == null )
				this.UWMSG = null;
			else
				this.UWMSG = rs.getString("UWMSG").trim();

			if( rs.getString("ReturnFlag") == null )
				this.ReturnFlag = null;
			else
				this.ReturnFlag = rs.getString("ReturnFlag").trim();

			if( rs.getString("bak1") == null )
				this.bak1 = null;
			else
				this.bak1 = rs.getString("bak1").trim();

			if( rs.getString("bak2") == null )
				this.bak2 = null;
			else
				this.bak2 = rs.getString("bak2").trim();

			if( rs.getString("bak3") == null )
				this.bak3 = null;
			else
				this.bak3 = rs.getString("bak3").trim();

			if( rs.getString("bak4") == null )
				this.bak4 = null;
			else
				this.bak4 = rs.getString("bak4").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMUWSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LMUWSchema getSchema()
	{
		LMUWSchema aLMUWSchema = new LMUWSchema();
		aLMUWSchema.setSchema(this);
		return aLMUWSchema;
	}

	public LMUWDB getDB()
	{
		LMUWDB aDBOper = new LMUWDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMUW描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(UWCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RiskCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CalCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MSG) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UWMSG) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ReturnFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(bak1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(bak2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(bak3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(bak4) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMUW>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			UWCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			MSG = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			UWMSG = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ReturnFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			bak3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			bak4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMUWSchema";
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
		if (FCode.equals("UWCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWCode));
		}
		if (FCode.equals("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equals("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equals("MSG"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MSG));
		}
		if (FCode.equals("UWMSG"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWMSG));
		}
		if (FCode.equals("ReturnFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ReturnFlag));
		}
		if (FCode.equals("bak1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(bak1));
		}
		if (FCode.equals("bak2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(bak2));
		}
		if (FCode.equals("bak3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(bak3));
		}
		if (FCode.equals("bak4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(bak4));
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
				strFieldValue = StrTool.GBKToUnicode(UWCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(MSG);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(UWMSG);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ReturnFlag);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(bak1);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(bak2);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(bak3);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(bak4);
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

		if (FCode.equals("UWCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWCode = FValue.trim();
			}
			else
				UWCode = null;
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
		if (FCode.equals("CalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode = FValue.trim();
			}
			else
				CalCode = null;
		}
		if (FCode.equals("MSG"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MSG = FValue.trim();
			}
			else
				MSG = null;
		}
		if (FCode.equals("UWMSG"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWMSG = FValue.trim();
			}
			else
				UWMSG = null;
		}
		if (FCode.equals("ReturnFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ReturnFlag = FValue.trim();
			}
			else
				ReturnFlag = null;
		}
		if (FCode.equals("bak1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				bak1 = FValue.trim();
			}
			else
				bak1 = null;
		}
		if (FCode.equals("bak2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				bak2 = FValue.trim();
			}
			else
				bak2 = null;
		}
		if (FCode.equals("bak3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				bak3 = FValue.trim();
			}
			else
				bak3 = null;
		}
		if (FCode.equals("bak4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				bak4 = FValue.trim();
			}
			else
				bak4 = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMUWSchema other = (LMUWSchema)otherObject;
		return
			UWCode.equals(other.getUWCode())
			&& RiskCode.equals(other.getRiskCode())
			&& CalCode.equals(other.getCalCode())
			&& MSG.equals(other.getMSG())
			&& UWMSG.equals(other.getUWMSG())
			&& ReturnFlag.equals(other.getReturnFlag())
			&& bak1.equals(other.getbak1())
			&& bak2.equals(other.getbak2())
			&& bak3.equals(other.getbak3())
			&& bak4.equals(other.getbak4());
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
		if( strFieldName.equals("UWCode") ) {
			return 0;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 1;
		}
		if( strFieldName.equals("CalCode") ) {
			return 2;
		}
		if( strFieldName.equals("MSG") ) {
			return 3;
		}
		if( strFieldName.equals("UWMSG") ) {
			return 4;
		}
		if( strFieldName.equals("ReturnFlag") ) {
			return 5;
		}
		if( strFieldName.equals("bak1") ) {
			return 6;
		}
		if( strFieldName.equals("bak2") ) {
			return 7;
		}
		if( strFieldName.equals("bak3") ) {
			return 8;
		}
		if( strFieldName.equals("bak4") ) {
			return 9;
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
				strFieldName = "UWCode";
				break;
			case 1:
				strFieldName = "RiskCode";
				break;
			case 2:
				strFieldName = "CalCode";
				break;
			case 3:
				strFieldName = "MSG";
				break;
			case 4:
				strFieldName = "UWMSG";
				break;
			case 5:
				strFieldName = "ReturnFlag";
				break;
			case 6:
				strFieldName = "bak1";
				break;
			case 7:
				strFieldName = "bak2";
				break;
			case 8:
				strFieldName = "bak3";
				break;
			case 9:
				strFieldName = "bak4";
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
		if( strFieldName.equals("UWCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MSG") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWMSG") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ReturnFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("bak1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("bak2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("bak3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("bak4") ) {
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
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
