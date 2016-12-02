/*
 * <p>ClassName: LKBankComSchema </p>
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
import com.sinosoft.lis.db.LKBankComDB;

public class LKBankComSchema implements Schema
{
	// @Field
	/** Bankcomno */
	private int RecordNo;
	/** 银行编码 */
	private String TranCom;
	/** 地区编码 */
	private String ZoneNo;
	/** 行政划区标志 */
	private int ZoneFlag;
	/** 地区名称 */
	private String ZoneName;
	/** 地区名称缩写 */
	private String ZoneShortName;
	/** 上级地区 */
	private String UpZoneNo;
	/** 上级地区所属机构 */
	private String UpTranCom;
	/** 状态 */
	private int State;
	/** 备用1 */
	private String Bak1;
	/** 备用2 */
	private String Bak2;
	/** 备用3 */
	private String Bak3;
	/** 入库日期 */
	private int MakeDate;
	/** 入库时间 */
	private int MakeTime;
	/** 最后修改日期 */
	private int ModifyDate;
	/** 最后修改时间 */
	private int ModifyTime;
	/** 操作员 */
	private String Operator;

	public static final int FIELDNUM = 17;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LKBankComSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RecordNo";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** Bankcomno<P> */
	public int getRecordNo()
	{
		return RecordNo;
	}
	/** Bankcomno */
	public void setRecordNo(int aRecordNo)
	{
		RecordNo = aRecordNo;
	}
	/** Bankcomno<P> */
	public void setRecordNo(String aRecordNo)
	{
		if (aRecordNo != null && !aRecordNo.equals(""))
		{
			Integer tInteger = new Integer(aRecordNo);
			int i = tInteger.intValue();
			RecordNo = i;
		}
	}

	/** 银行编码<P>银行编码-011工行   -012农行 */
	public String getTranCom()
	{
		if (TranCom != null && !TranCom.equals("") && SysConst.CHANGECHARSET == true)
		{
			TranCom = StrTool.unicodeToGBK(TranCom);
		}
		return TranCom;
	}
	/** 银行编码 */
	public void setTranCom(String aTranCom)
	{
		TranCom = aTranCom;
	}
	/** 地区编码<P>地区编码 */
	public String getZoneNo()
	{
		if (ZoneNo != null && !ZoneNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ZoneNo = StrTool.unicodeToGBK(ZoneNo);
		}
		return ZoneNo;
	}
	/** 地区编码 */
	public void setZoneNo(String aZoneNo)
	{
		ZoneNo = aZoneNo;
	}
	/** 行政划区标志<P>0-国家<br>1-省/直辖市<br>2-地区<br>3-城市<br>4-�� */
	public int getZoneFlag()
	{
		return ZoneFlag;
	}
	/** 行政划区标志 */
	public void setZoneFlag(int aZoneFlag)
	{
		ZoneFlag = aZoneFlag;
	}
	/** 行政划区标志<P>0-国家<br>1-省/直辖市<br>2-地区<br>3-城市<br>4-�� */
	public void setZoneFlag(String aZoneFlag)
	{
		if (aZoneFlag != null && !aZoneFlag.equals(""))
		{
			Integer tInteger = new Integer(aZoneFlag);
			int i = tInteger.intValue();
			ZoneFlag = i;
		}
	}

	/** 地区名称<P>地区名称 */
	public String getZoneName()
	{
		if (ZoneName != null && !ZoneName.equals("") && SysConst.CHANGECHARSET == true)
		{
			ZoneName = StrTool.unicodeToGBK(ZoneName);
		}
		return ZoneName;
	}
	/** 地区名称 */
	public void setZoneName(String aZoneName)
	{
		ZoneName = aZoneName;
	}
	/** 地区名称缩写<P>地区名称缩写 */
	public String getZoneShortName()
	{
		if (ZoneShortName != null && !ZoneShortName.equals("") && SysConst.CHANGECHARSET == true)
		{
			ZoneShortName = StrTool.unicodeToGBK(ZoneShortName);
		}
		return ZoneShortName;
	}
	/** 地区名称缩写 */
	public void setZoneShortName(String aZoneShortName)
	{
		ZoneShortName = aZoneShortName;
	}
	/** 上级地区<P>代理机构名称 */
	public String getUpZoneNo()
	{
		if (UpZoneNo != null && !UpZoneNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			UpZoneNo = StrTool.unicodeToGBK(UpZoneNo);
		}
		return UpZoneNo;
	}
	/** 上级地区 */
	public void setUpZoneNo(String aUpZoneNo)
	{
		UpZoneNo = aUpZoneNo;
	}
	/** 上级地区所属机构<P> */
	public String getUpTranCom()
	{
		if (UpTranCom != null && !UpTranCom.equals("") && SysConst.CHANGECHARSET == true)
		{
			UpTranCom = StrTool.unicodeToGBK(UpTranCom);
		}
		return UpTranCom;
	}
	/** 上级地区所属机构 */
	public void setUpTranCom(String aUpTranCom)
	{
		UpTranCom = aUpTranCom;
	}
	/** 状态<P> 网点状态0-有效;1-无效 */
	public int getState()
	{
		return State;
	}
	/** 状态 */
	public void setState(int aState)
	{
		State = aState;
	}
	/** 状态<P> 网点状态0-有效;1-无效 */
	public void setState(String aState)
	{
		if (aState != null && !aState.equals(""))
		{
			Integer tInteger = new Integer(aState);
			int i = tInteger.intValue();
			State = i;
		}
	}

	/** 备用1<P> */
	public String getBak1()
	{
		if (Bak1 != null && !Bak1.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak1 = StrTool.unicodeToGBK(Bak1);
		}
		return Bak1;
	}
	/** 备用1 */
	public void setBak1(String aBak1)
	{
		Bak1 = aBak1;
	}
	/** 备用2<P> */
	public String getBak2()
	{
		if (Bak2 != null && !Bak2.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak2 = StrTool.unicodeToGBK(Bak2);
		}
		return Bak2;
	}
	/** 备用2 */
	public void setBak2(String aBak2)
	{
		Bak2 = aBak2;
	}
	/** 备用3<P> */
	public String getBak3()
	{
		if (Bak3 != null && !Bak3.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak3 = StrTool.unicodeToGBK(Bak3);
		}
		return Bak3;
	}
	/** 备用3 */
	public void setBak3(String aBak3)
	{
		Bak3 = aBak3;
	}
	/** 入库日期<P> */
	public int getMakeDate()
	{
		return MakeDate;
	}
	/** 入库日期 */
	public void setMakeDate(int aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	/** 入库日期<P> */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals(""))
		{
			Integer tInteger = new Integer(aMakeDate);
			int i = tInteger.intValue();
			MakeDate = i;
		}
	}

	/** 入库时间<P> */
	public int getMakeTime()
	{
		return MakeTime;
	}
	/** 入库时间 */
	public void setMakeTime(int aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	/** 入库时间<P> */
	public void setMakeTime(String aMakeTime)
	{
		if (aMakeTime != null && !aMakeTime.equals(""))
		{
			Integer tInteger = new Integer(aMakeTime);
			int i = tInteger.intValue();
			MakeTime = i;
		}
	}

	/** 最后修改日期<P> */
	public int getModifyDate()
	{
		return ModifyDate;
	}
	/** 最后修改日期 */
	public void setModifyDate(int aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	/** 最后修改日期<P> */
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals(""))
		{
			Integer tInteger = new Integer(aModifyDate);
			int i = tInteger.intValue();
			ModifyDate = i;
		}
	}

	/** 最后修改时间<P> */
	public int getModifyTime()
	{
		return ModifyTime;
	}
	/** 最后修改时间 */
	public void setModifyTime(int aModifyTime)
	{
		ModifyTime = aModifyTime;
	}
	/** 最后修改时间<P> */
	public void setModifyTime(String aModifyTime)
	{
		if (aModifyTime != null && !aModifyTime.equals(""))
		{
			Integer tInteger = new Integer(aModifyTime);
			int i = tInteger.intValue();
			ModifyTime = i;
		}
	}

	/** 操作员<P> */
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

	/**
	* 使用另外一个 LKBankComSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LKBankComSchema aLKBankComSchema)
	{
		this.RecordNo = aLKBankComSchema.getRecordNo();
		this.TranCom = aLKBankComSchema.getTranCom();
		this.ZoneNo = aLKBankComSchema.getZoneNo();
		this.ZoneFlag = aLKBankComSchema.getZoneFlag();
		this.ZoneName = aLKBankComSchema.getZoneName();
		this.ZoneShortName = aLKBankComSchema.getZoneShortName();
		this.UpZoneNo = aLKBankComSchema.getUpZoneNo();
		this.UpTranCom = aLKBankComSchema.getUpTranCom();
		this.State = aLKBankComSchema.getState();
		this.Bak1 = aLKBankComSchema.getBak1();
		this.Bak2 = aLKBankComSchema.getBak2();
		this.Bak3 = aLKBankComSchema.getBak3();
		this.MakeDate = aLKBankComSchema.getMakeDate();
		this.MakeTime = aLKBankComSchema.getMakeTime();
		this.ModifyDate = aLKBankComSchema.getModifyDate();
		this.ModifyTime = aLKBankComSchema.getModifyTime();
		this.Operator = aLKBankComSchema.getOperator();
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
			this.RecordNo = rs.getInt("RecordNo");
			if( rs.getString("TranCom") == null )
				this.TranCom = null;
			else
				this.TranCom = rs.getString("TranCom").trim();

			if( rs.getString("ZoneNo") == null )
				this.ZoneNo = null;
			else
				this.ZoneNo = rs.getString("ZoneNo").trim();

			this.ZoneFlag = rs.getInt("ZoneFlag");
			if( rs.getString("ZoneName") == null )
				this.ZoneName = null;
			else
				this.ZoneName = rs.getString("ZoneName").trim();

			if( rs.getString("ZoneShortName") == null )
				this.ZoneShortName = null;
			else
				this.ZoneShortName = rs.getString("ZoneShortName").trim();

			if( rs.getString("UpZoneNo") == null )
				this.UpZoneNo = null;
			else
				this.UpZoneNo = rs.getString("UpZoneNo").trim();

			if( rs.getString("UpTranCom") == null )
				this.UpTranCom = null;
			else
				this.UpTranCom = rs.getString("UpTranCom").trim();

			this.State = rs.getInt("State");
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

			this.MakeDate = rs.getInt("MakeDate");
			this.MakeTime = rs.getInt("MakeTime");
			this.ModifyDate = rs.getInt("ModifyDate");
			this.ModifyTime = rs.getInt("ModifyTime");
			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LKBankComSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LKBankComSchema getSchema()
	{
		LKBankComSchema aLKBankComSchema = new LKBankComSchema();
		aLKBankComSchema.setSchema(this);
		return aLKBankComSchema;
	}

	public LKBankComDB getDB()
	{
		LKBankComDB aDBOper = new LKBankComDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKBankCom描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn =  ChgData.chgData(RecordNo) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TranCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ZoneNo) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ZoneFlag) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ZoneName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ZoneShortName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UpZoneNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UpTranCom) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(State) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak3) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeTime) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyTime) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLKBankCom>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RecordNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			TranCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ZoneNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			ZoneFlag= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			ZoneName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			ZoneShortName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			UpZoneNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			UpTranCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			State= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,9,SysConst.PACKAGESPILTER))).intValue();
			Bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			Bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Bak3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,13,SysConst.PACKAGESPILTER))).intValue();
			MakeTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,14,SysConst.PACKAGESPILTER))).intValue();
			ModifyDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			ModifyTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LKBankComSchema";
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
		if (FCode.equals("RecordNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RecordNo));
		}
		if (FCode.equals("TranCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranCom));
		}
		if (FCode.equals("ZoneNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZoneNo));
		}
		if (FCode.equals("ZoneFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZoneFlag));
		}
		if (FCode.equals("ZoneName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZoneName));
		}
		if (FCode.equals("ZoneShortName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZoneShortName));
		}
		if (FCode.equals("UpZoneNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpZoneNo));
		}
		if (FCode.equals("UpTranCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UpTranCom));
		}
		if (FCode.equals("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
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
				strFieldValue = String.valueOf(RecordNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TranCom);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ZoneNo);
				break;
			case 3:
				strFieldValue = String.valueOf(ZoneFlag);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ZoneName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(ZoneShortName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(UpZoneNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(UpTranCom);
				break;
			case 8:
				strFieldValue = String.valueOf(State);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(Bak1);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(Bak2);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Bak3);
				break;
			case 12:
				strFieldValue = String.valueOf(MakeDate);
				break;
			case 13:
				strFieldValue = String.valueOf(MakeTime);
				break;
			case 14:
				strFieldValue = String.valueOf(ModifyDate);
				break;
			case 15:
				strFieldValue = String.valueOf(ModifyTime);
				break;
			case 16:
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

		if (FCode.equals("RecordNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RecordNo = i;
			}
		}
		if (FCode.equals("TranCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TranCom = FValue.trim();
			}
			else
				TranCom = null;
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
		if (FCode.equals("ZoneFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				ZoneFlag = i;
			}
		}
		if (FCode.equals("ZoneName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZoneName = FValue.trim();
			}
			else
				ZoneName = null;
		}
		if (FCode.equals("ZoneShortName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZoneShortName = FValue.trim();
			}
			else
				ZoneShortName = null;
		}
		if (FCode.equals("UpZoneNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpZoneNo = FValue.trim();
			}
			else
				UpZoneNo = null;
		}
		if (FCode.equals("UpTranCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UpTranCom = FValue.trim();
			}
			else
				UpTranCom = null;
		}
		if (FCode.equals("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				State = i;
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
		LKBankComSchema other = (LKBankComSchema)otherObject;
		return
			RecordNo == other.getRecordNo()
			&& TranCom.equals(other.getTranCom())
			&& ZoneNo.equals(other.getZoneNo())
			&& ZoneFlag == other.getZoneFlag()
			&& ZoneName.equals(other.getZoneName())
			&& ZoneShortName.equals(other.getZoneShortName())
			&& UpZoneNo.equals(other.getUpZoneNo())
			&& UpTranCom.equals(other.getUpTranCom())
			&& State == other.getState()
			&& Bak1.equals(other.getBak1())
			&& Bak2.equals(other.getBak2())
			&& Bak3.equals(other.getBak3())
			&& MakeDate == other.getMakeDate()
			&& MakeTime == other.getMakeTime()
			&& ModifyDate == other.getModifyDate()
			&& ModifyTime == other.getModifyTime()
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
		if( strFieldName.equals("RecordNo") ) {
			return 0;
		}
		if( strFieldName.equals("TranCom") ) {
			return 1;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return 2;
		}
		if( strFieldName.equals("ZoneFlag") ) {
			return 3;
		}
		if( strFieldName.equals("ZoneName") ) {
			return 4;
		}
		if( strFieldName.equals("ZoneShortName") ) {
			return 5;
		}
		if( strFieldName.equals("UpZoneNo") ) {
			return 6;
		}
		if( strFieldName.equals("UpTranCom") ) {
			return 7;
		}
		if( strFieldName.equals("State") ) {
			return 8;
		}
		if( strFieldName.equals("Bak1") ) {
			return 9;
		}
		if( strFieldName.equals("Bak2") ) {
			return 10;
		}
		if( strFieldName.equals("Bak3") ) {
			return 11;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 12;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 13;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 14;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 15;
		}
		if( strFieldName.equals("Operator") ) {
			return 16;
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
				strFieldName = "RecordNo";
				break;
			case 1:
				strFieldName = "TranCom";
				break;
			case 2:
				strFieldName = "ZoneNo";
				break;
			case 3:
				strFieldName = "ZoneFlag";
				break;
			case 4:
				strFieldName = "ZoneName";
				break;
			case 5:
				strFieldName = "ZoneShortName";
				break;
			case 6:
				strFieldName = "UpZoneNo";
				break;
			case 7:
				strFieldName = "UpTranCom";
				break;
			case 8:
				strFieldName = "State";
				break;
			case 9:
				strFieldName = "Bak1";
				break;
			case 10:
				strFieldName = "Bak2";
				break;
			case 11:
				strFieldName = "Bak3";
				break;
			case 12:
				strFieldName = "MakeDate";
				break;
			case 13:
				strFieldName = "MakeTime";
				break;
			case 14:
				strFieldName = "ModifyDate";
				break;
			case 15:
				strFieldName = "ModifyTime";
				break;
			case 16:
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
		if( strFieldName.equals("RecordNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TranCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZoneFlag") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ZoneName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZoneShortName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpZoneNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UpTranCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 1:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 2:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 3:
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 13:
				nFieldType = Schema.TYPE_INT;
				break;
			case 14:
				nFieldType = Schema.TYPE_INT;
				break;
			case 15:
				nFieldType = Schema.TYPE_INT;
				break;
			case 16:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
