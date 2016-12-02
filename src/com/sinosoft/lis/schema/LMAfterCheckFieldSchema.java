/*
 * <p>ClassName: LMAfterCheckFieldSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-01-13
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LMAfterCheckFieldDB;

public class LMAfterCheckFieldSchema implements Schema
{
	// @Field
	/** 算法编码 */
	private String CalCode;
	/** 险种code */
	private String RiskCode;
	/** 算法描述 */
	private String Remark;
	/** Remark2 */
	private String Remark2;
	/** 备份字段1 */
	private String bak1;
	/** 备份字段2 */
	private String bak2;
	/** 备份字段3 */
	private String bak3;
	/** ��份字段4 */
	private String bak4;
	/** Uwflag */
	private String UWFlag;
	/** Uwcode */
	private String UWCode;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LMAfterCheckFieldSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "CalCode";
		pk[1] = "RiskCode";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 算法编码<P> */
	public String getCalCode()
	{
		if (CalCode != null && !CalCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			CalCode = StrTool.unicodeToGBK(CalCode);
		}
		return CalCode;
	}
	/** 算法编码 */
	public void setCalCode(String aCalCode)
	{
		CalCode = aCalCode;
	}
	/** 险种code<P> */
	public String getRiskCode()
	{
		if (RiskCode != null && !RiskCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			RiskCode = StrTool.unicodeToGBK(RiskCode);
		}
		return RiskCode;
	}
	/** 险种code */
	public void setRiskCode(String aRiskCode)
	{
		RiskCode = aRiskCode;
	}
	/** 算法描述<P> */
	public String getRemark()
	{
		if (Remark != null && !Remark.equals("") && SysConst.CHANGECHARSET == true)
		{
			Remark = StrTool.unicodeToGBK(Remark);
		}
		return Remark;
	}
	/** 算法描述 */
	public void setRemark(String aRemark)
	{
		Remark = aRemark;
	}
	/** Remark2<P> */
	public String getRemark2()
	{
		if (Remark2 != null && !Remark2.equals("") && SysConst.CHANGECHARSET == true)
		{
			Remark2 = StrTool.unicodeToGBK(Remark2);
		}
		return Remark2;
	}
	/** Remark2 */
	public void setRemark2(String aRemark2)
	{
		Remark2 = aRemark2;
	}
	/** 备份字段1<P> */
	public String getbak1()
	{
		if (bak1 != null && !bak1.equals("") && SysConst.CHANGECHARSET == true)
		{
			bak1 = StrTool.unicodeToGBK(bak1);
		}
		return bak1;
	}
	/** 备份字段1 */
	public void setbak1(String abak1)
	{
		bak1 = abak1;
	}
	/** 备份字段2<P> */
	public String getbak2()
	{
		if (bak2 != null && !bak2.equals("") && SysConst.CHANGECHARSET == true)
		{
			bak2 = StrTool.unicodeToGBK(bak2);
		}
		return bak2;
	}
	/** 备份字段2 */
	public void setbak2(String abak2)
	{
		bak2 = abak2;
	}
	/** 备份字段3<P> */
	public String getbak3()
	{
		if (bak3 != null && !bak3.equals("") && SysConst.CHANGECHARSET == true)
		{
			bak3 = StrTool.unicodeToGBK(bak3);
		}
		return bak3;
	}
	/** 备份字段3 */
	public void setbak3(String abak3)
	{
		bak3 = abak3;
	}
	/** ��份字段4<P> */
	public String getbak4()
	{
		if (bak4 != null && !bak4.equals("") && SysConst.CHANGECHARSET == true)
		{
			bak4 = StrTool.unicodeToGBK(bak4);
		}
		return bak4;
	}
	/** ��份字段4 */
	public void setbak4(String abak4)
	{
		bak4 = abak4;
	}
	/** Uwflag<P> */
	public String getUWFlag()
	{
		if (UWFlag != null && !UWFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			UWFlag = StrTool.unicodeToGBK(UWFlag);
		}
		return UWFlag;
	}
	/** Uwflag */
	public void setUWFlag(String aUWFlag)
	{
		UWFlag = aUWFlag;
	}
	/** Uwcode<P> */
	public String getUWCode()
	{
		if (UWCode != null && !UWCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			UWCode = StrTool.unicodeToGBK(UWCode);
		}
		return UWCode;
	}
	/** Uwcode */
	public void setUWCode(String aUWCode)
	{
		UWCode = aUWCode;
	}

	/**
	* 使用另外一个 LMAfterCheckFieldSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LMAfterCheckFieldSchema aLMAfterCheckFieldSchema)
	{
		this.CalCode = aLMAfterCheckFieldSchema.getCalCode();
		this.RiskCode = aLMAfterCheckFieldSchema.getRiskCode();
		this.Remark = aLMAfterCheckFieldSchema.getRemark();
		this.Remark2 = aLMAfterCheckFieldSchema.getRemark2();
		this.bak1 = aLMAfterCheckFieldSchema.getbak1();
		this.bak2 = aLMAfterCheckFieldSchema.getbak2();
		this.bak3 = aLMAfterCheckFieldSchema.getbak3();
		this.bak4 = aLMAfterCheckFieldSchema.getbak4();
		this.UWFlag = aLMAfterCheckFieldSchema.getUWFlag();
		this.UWCode = aLMAfterCheckFieldSchema.getUWCode();
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
			if( rs.getString("CalCode") == null )
				this.CalCode = null;
			else
				this.CalCode = rs.getString("CalCode").trim();

			if( rs.getString("RiskCode") == null )
				this.RiskCode = null;
			else
				this.RiskCode = rs.getString("RiskCode").trim();

			if( rs.getString("Remark") == null )
				this.Remark = null;
			else
				this.Remark = rs.getString("Remark").trim();

			if( rs.getString("Remark2") == null )
				this.Remark2 = null;
			else
				this.Remark2 = rs.getString("Remark2").trim();

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

			if( rs.getString("UWFlag") == null )
				this.UWFlag = null;
			else
				this.UWFlag = rs.getString("UWFlag").trim();

			if( rs.getString("UWCode") == null )
				this.UWCode = null;
			else
				this.UWCode = rs.getString("UWCode").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMAfterCheckFieldSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LMAfterCheckFieldSchema getSchema()
	{
		LMAfterCheckFieldSchema aLMAfterCheckFieldSchema = new LMAfterCheckFieldSchema();
		aLMAfterCheckFieldSchema.setSchema(this);
		return aLMAfterCheckFieldSchema;
	}

	public LMAfterCheckFieldDB getDB()
	{
		LMAfterCheckFieldDB aDBOper = new LMAfterCheckFieldDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMAfterCheckField描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(CalCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RiskCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Remark) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Remark2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(bak1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(bak2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(bak3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(bak4) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UWFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UWCode) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMAfterCheckField>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			CalCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			RiskCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			Remark = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			Remark2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			bak3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			bak4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			UWFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			UWCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LMAfterCheckFieldSchema";
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
		if (FCode.equals("CalCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CalCode));
		}
		if (FCode.equals("RiskCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RiskCode));
		}
		if (FCode.equals("Remark"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark));
		}
		if (FCode.equals("Remark2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Remark2));
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
		if (FCode.equals("UWFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWFlag));
		}
		if (FCode.equals("UWCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UWCode));
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
				strFieldValue = StrTool.GBKToUnicode(CalCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(RiskCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(Remark);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(Remark2);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(bak1);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(bak2);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(bak3);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(bak4);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(UWFlag);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(UWCode);
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

		if (FCode.equals("CalCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CalCode = FValue.trim();
			}
			else
				CalCode = null;
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
		if (FCode.equals("Remark"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark = FValue.trim();
			}
			else
				Remark = null;
		}
		if (FCode.equals("Remark2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Remark2 = FValue.trim();
			}
			else
				Remark2 = null;
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
		if (FCode.equals("UWFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWFlag = FValue.trim();
			}
			else
				UWFlag = null;
		}
		if (FCode.equals("UWCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UWCode = FValue.trim();
			}
			else
				UWCode = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LMAfterCheckFieldSchema other = (LMAfterCheckFieldSchema)otherObject;
		return
			CalCode.equals(other.getCalCode())
			&& RiskCode.equals(other.getRiskCode())
			&& Remark.equals(other.getRemark())
			&& Remark2.equals(other.getRemark2())
			&& bak1.equals(other.getbak1())
			&& bak2.equals(other.getbak2())
			&& bak3.equals(other.getbak3())
			&& bak4.equals(other.getbak4())
			&& UWFlag.equals(other.getUWFlag())
			&& UWCode.equals(other.getUWCode());
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
		if( strFieldName.equals("CalCode") ) {
			return 0;
		}
		if( strFieldName.equals("RiskCode") ) {
			return 1;
		}
		if( strFieldName.equals("Remark") ) {
			return 2;
		}
		if( strFieldName.equals("Remark2") ) {
			return 3;
		}
		if( strFieldName.equals("bak1") ) {
			return 4;
		}
		if( strFieldName.equals("bak2") ) {
			return 5;
		}
		if( strFieldName.equals("bak3") ) {
			return 6;
		}
		if( strFieldName.equals("bak4") ) {
			return 7;
		}
		if( strFieldName.equals("UWFlag") ) {
			return 8;
		}
		if( strFieldName.equals("UWCode") ) {
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
				strFieldName = "CalCode";
				break;
			case 1:
				strFieldName = "RiskCode";
				break;
			case 2:
				strFieldName = "Remark";
				break;
			case 3:
				strFieldName = "Remark2";
				break;
			case 4:
				strFieldName = "bak1";
				break;
			case 5:
				strFieldName = "bak2";
				break;
			case 6:
				strFieldName = "bak3";
				break;
			case 7:
				strFieldName = "bak4";
				break;
			case 8:
				strFieldName = "UWFlag";
				break;
			case 9:
				strFieldName = "UWCode";
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
		if( strFieldName.equals("CalCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RiskCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Remark2") ) {
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
		if( strFieldName.equals("UWFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UWCode") ) {
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
