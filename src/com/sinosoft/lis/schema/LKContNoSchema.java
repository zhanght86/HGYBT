/*
 * <p>ClassName: LKContNoSchema </p>
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
import com.sinosoft.lis.db.LKContNoDB;

public class LKContNoSchema implements Schema
{
	// @Field
	/** 合同号码 */
	private String ContNo;
	/** 城市代码 */
	private String CityCode;
	/** 系统标志 */
	private String SysFlag;
	/** 管理机构 */
	private String ManageCom;
	/** 投保单号 */
	private String ProposalNo;
	/** 保单状态 */
	private String Status;
	/** 备用字段 */
	private String bak1;
	/** 备用字段2 */
	private String bak2;
	/** 入机日期 */
	private Date MakeDate;
	/** 入机时间 */
	private String MakeTime;
	/** 最后一次修改日期 */
	private Date ModifyDate;
	/** 最后一次修改时间 */
	private String ModifyTime;

	public static final int FIELDNUM = 12;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LKContNoSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[2];
		pk[0] = "ContNo";
		pk[1] = "CityCode";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 合同号码<P>合同号码 */
	public String getContNo()
	{
		if (ContNo != null && !ContNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ContNo = StrTool.unicodeToGBK(ContNo);
		}
		return ContNo;
	}
	/** 合同号码 */
	public void setContNo(String aContNo)
	{
		ContNo = aContNo;
	}
	/** 城市代码<P>金盛外围系统编码 */
	public String getCityCode()
	{
		if (CityCode != null && !CityCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			CityCode = StrTool.unicodeToGBK(CityCode);
		}
		return CityCode;
	}
	/** 城市代码 */
	public void setCityCode(String aCityCode)
	{
		CityCode = aCityCode;
	}
	/** 系统标志<P> */
	public String getSysFlag()
	{
		if (SysFlag != null && !SysFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			SysFlag = StrTool.unicodeToGBK(SysFlag);
		}
		return SysFlag;
	}
	/** 系统标志 */
	public void setSysFlag(String aSysFlag)
	{
		SysFlag = aSysFlag;
	}
	/** 管理机构<P>管理机构 */
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
	/** 投保单号<P> */
	public String getProposalNo()
	{
		if (ProposalNo != null && !ProposalNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ProposalNo = StrTool.unicodeToGBK(ProposalNo);
		}
		return ProposalNo;
	}
	/** 投保单号 */
	public void setProposalNo(String aProposalNo)
	{
		ProposalNo = aProposalNo;
	}
	/** 保单状态<P>状态0 没用过 1已用过 */
	public String getStatus()
	{
		if (Status != null && !Status.equals("") && SysConst.CHANGECHARSET == true)
		{
			Status = StrTool.unicodeToGBK(Status);
		}
		return Status;
	}
	/** 保单状态 */
	public void setStatus(String aStatus)
	{
		Status = aStatus;
	}
	/** 备用字段<P>备用字段 */
	public String getbak1()
	{
		if (bak1 != null && !bak1.equals("") && SysConst.CHANGECHARSET == true)
		{
			bak1 = StrTool.unicodeToGBK(bak1);
		}
		return bak1;
	}
	/** 备用字段 */
	public void setbak1(String abak1)
	{
		bak1 = abak1;
	}
	/** 备用字段2<P>备用字段2 */
	public String getbak2()
	{
		if (bak2 != null && !bak2.equals("") && SysConst.CHANGECHARSET == true)
		{
			bak2 = StrTool.unicodeToGBK(bak2);
		}
		return bak2;
	}
	/** 备用字段2 */
	public void setbak2(String abak2)
	{
		bak2 = abak2;
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
	/** 最后一次修改日期<P> */
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
	/** 最后一次修改日期<P> */
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

	/**
	* 使用另外一个 LKContNoSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LKContNoSchema aLKContNoSchema)
	{
		this.ContNo = aLKContNoSchema.getContNo();
		this.CityCode = aLKContNoSchema.getCityCode();
		this.SysFlag = aLKContNoSchema.getSysFlag();
		this.ManageCom = aLKContNoSchema.getManageCom();
		this.ProposalNo = aLKContNoSchema.getProposalNo();
		this.Status = aLKContNoSchema.getStatus();
		this.bak1 = aLKContNoSchema.getbak1();
		this.bak2 = aLKContNoSchema.getbak2();
		this.MakeDate = fDate.getDate( aLKContNoSchema.getMakeDate());
		this.MakeTime = aLKContNoSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLKContNoSchema.getModifyDate());
		this.ModifyTime = aLKContNoSchema.getModifyTime();
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
			if( rs.getString("ContNo") == null )
				this.ContNo = null;
			else
				this.ContNo = rs.getString("ContNo").trim();

			if( rs.getString("CityCode") == null )
				this.CityCode = null;
			else
				this.CityCode = rs.getString("CityCode").trim();

			if( rs.getString("SysFlag") == null )
				this.SysFlag = null;
			else
				this.SysFlag = rs.getString("SysFlag").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ProposalNo") == null )
				this.ProposalNo = null;
			else
				this.ProposalNo = rs.getString("ProposalNo").trim();

			if( rs.getString("Status") == null )
				this.Status = null;
			else
				this.Status = rs.getString("Status").trim();

			if( rs.getString("bak1") == null )
				this.bak1 = null;
			else
				this.bak1 = rs.getString("bak1").trim();

			if( rs.getString("bak2") == null )
				this.bak2 = null;
			else
				this.bak2 = rs.getString("bak2").trim();

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

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LKContNoSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LKContNoSchema getSchema()
	{
		LKContNoSchema aLKContNoSchema = new LKContNoSchema();
		aLKContNoSchema.setSchema(this);
		return aLKContNoSchema;
	}

	public LKContNoDB getDB()
	{
		LKContNoDB aDBOper = new LKContNoDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKContNo描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(ContNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CityCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SysFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ManageCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ProposalNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Status) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(bak1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(bak2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ModifyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKContNo>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			ContNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			CityCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			SysFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ProposalNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			Status = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LKContNoSchema";
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
		if (FCode.equals("ContNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ContNo));
		}
		if (FCode.equals("CityCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CityCode));
		}
		if (FCode.equals("SysFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SysFlag));
		}
		if (FCode.equals("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equals("ProposalNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ProposalNo));
		}
		if (FCode.equals("Status"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Status));
		}
		if (FCode.equals("bak1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(bak1));
		}
		if (FCode.equals("bak2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(bak2));
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
				strFieldValue = StrTool.GBKToUnicode(ContNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(CityCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(SysFlag);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ProposalNo);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(Status);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(bak1);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(bak2);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
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

		if (FCode.equals("ContNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ContNo = FValue.trim();
			}
			else
				ContNo = null;
		}
		if (FCode.equals("CityCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CityCode = FValue.trim();
			}
			else
				CityCode = null;
		}
		if (FCode.equals("SysFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SysFlag = FValue.trim();
			}
			else
				SysFlag = null;
		}
		if (FCode.equals("ManageCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageCom = FValue.trim();
			}
			else
				ManageCom = null;
		}
		if (FCode.equals("ProposalNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ProposalNo = FValue.trim();
			}
			else
				ProposalNo = null;
		}
		if (FCode.equals("Status"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Status = FValue.trim();
			}
			else
				Status = null;
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
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LKContNoSchema other = (LKContNoSchema)otherObject;
		return
			ContNo.equals(other.getContNo())
			&& CityCode.equals(other.getCityCode())
			&& SysFlag.equals(other.getSysFlag())
			&& ManageCom.equals(other.getManageCom())
			&& ProposalNo.equals(other.getProposalNo())
			&& Status.equals(other.getStatus())
			&& bak1.equals(other.getbak1())
			&& bak2.equals(other.getbak2())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime());
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
		if( strFieldName.equals("ContNo") ) {
			return 0;
		}
		if( strFieldName.equals("CityCode") ) {
			return 1;
		}
		if( strFieldName.equals("SysFlag") ) {
			return 2;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 3;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return 4;
		}
		if( strFieldName.equals("Status") ) {
			return 5;
		}
		if( strFieldName.equals("bak1") ) {
			return 6;
		}
		if( strFieldName.equals("bak2") ) {
			return 7;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 8;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 9;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 10;
		}
		if( strFieldName.equals("ModifyTime") ) {
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
				strFieldName = "ContNo";
				break;
			case 1:
				strFieldName = "CityCode";
				break;
			case 2:
				strFieldName = "SysFlag";
				break;
			case 3:
				strFieldName = "ManageCom";
				break;
			case 4:
				strFieldName = "ProposalNo";
				break;
			case 5:
				strFieldName = "Status";
				break;
			case 6:
				strFieldName = "bak1";
				break;
			case 7:
				strFieldName = "bak2";
				break;
			case 8:
				strFieldName = "MakeDate";
				break;
			case 9:
				strFieldName = "MakeTime";
				break;
			case 10:
				strFieldName = "ModifyDate";
				break;
			case 11:
				strFieldName = "ModifyTime";
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
		if( strFieldName.equals("ContNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CityCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("SysFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ProposalNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Status") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("bak1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("bak2") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 9:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 10:
				nFieldType = Schema.TYPE_DATE;
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
