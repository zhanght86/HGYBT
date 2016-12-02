/*
 * <p>ClassName: AgentSchema </p>
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
import com.sinosoft.lis.db.AgentDB;

public class AgentSchema implements Schema
{
	// @Field
	/** Recordno */
	private int RecordNo;
	/** Type */
	private int Type;
	/** Agentcom */
	private String AgentCom;
	/** Agentcomname */
	private String AgentComName;
	/** Agentcode */
	private String AgentCode;
	/** Agentname */
	private String AgentName;
	/** Managecom */
	private String ManageCom;
	/** Managecomname */
	private String ManageComName;
	/** Unitcode */
	private String UnitCode;
	/** Agentgrade */
	private String AgentGrade;
	/** State */
	private int State;
	/** Bak1 */
	private String Bak1;
	/** Bak2 */
	private String Bak2;
	/** Bak3 */
	private String Bak3;
	/** Makedate */
	private int MakeDate;
	/** Maketime */
	private int MakeTime;
	/** Modifydate */
	private int ModifyDate;
	/** Modifytime */
	private int ModifyTime;
	/** Operator */
	private String Operator;

	public static final int FIELDNUM = 19;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public AgentSchema()
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

	/** Recordno<P>记录号 */
	public int getRecordNo()
	{
		return RecordNo;
	}
	/** Recordno */
	public void setRecordNo(int aRecordNo)
	{
		RecordNo = aRecordNo;
	}
	/** Recordno<P>记录号 */
	public void setRecordNo(String aRecordNo)
	{
		if (aRecordNo != null && !aRecordNo.equals(""))
		{
			Integer tInteger = new Integer(aRecordNo);
			int i = tInteger.intValue();
			RecordNo = i;
		}
	}

	/** Type<P>代理类型 */
	public int getType()
	{
		return Type;
	}
	/** Type */
	public void setType(int aType)
	{
		Type = aType;
	}
	/** Type<P>代理类型 */
	public void setType(String aType)
	{
		if (aType != null && !aType.equals(""))
		{
			Integer tInteger = new Integer(aType);
			int i = tInteger.intValue();
			Type = i;
		}
	}

	/** Agentcom<P>代理机构 */
	public String getAgentCom()
	{
		if (AgentCom != null && !AgentCom.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentCom = StrTool.unicodeToGBK(AgentCom);
		}
		return AgentCom;
	}
	/** Agentcom */
	public void setAgentCom(String aAgentCom)
	{
		AgentCom = aAgentCom;
	}
	/** Agentcomname<P>代理机构名称 */
	public String getAgentComName()
	{
		if (AgentComName != null && !AgentComName.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentComName = StrTool.unicodeToGBK(AgentComName);
		}
		return AgentComName;
	}
	/** Agentcomname */
	public void setAgentComName(String aAgentComName)
	{
		AgentComName = aAgentComName;
	}
	/** Agentcode<P>代理人 */
	public String getAgentCode()
	{
		if (AgentCode != null && !AgentCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentCode = StrTool.unicodeToGBK(AgentCode);
		}
		return AgentCode;
	}
	/** Agentcode */
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	/** Agentname<P>代理人姓名 */
	public String getAgentName()
	{
		if (AgentName != null && !AgentName.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentName = StrTool.unicodeToGBK(AgentName);
		}
		return AgentName;
	}
	/** Agentname */
	public void setAgentName(String aAgentName)
	{
		AgentName = aAgentName;
	}
	/** Managecom<P>管理机构 */
	public String getManageCom()
	{
		if (ManageCom != null && !ManageCom.equals("") && SysConst.CHANGECHARSET == true)
		{
			ManageCom = StrTool.unicodeToGBK(ManageCom);
		}
		return ManageCom;
	}
	/** Managecom */
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	/** Managecomname<P>管理机构名称 */
	public String getManageComName()
	{
		if (ManageComName != null && !ManageComName.equals("") && SysConst.CHANGECHARSET == true)
		{
			ManageComName = StrTool.unicodeToGBK(ManageComName);
		}
		return ManageComName;
	}
	/** Managecomname */
	public void setManageComName(String aManageComName)
	{
		ManageComName = aManageComName;
	}
	/** Unitcode<P> */
	public String getUnitCode()
	{
		if (UnitCode != null && !UnitCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			UnitCode = StrTool.unicodeToGBK(UnitCode);
		}
		return UnitCode;
	}
	/** Unitcode */
	public void setUnitCode(String aUnitCode)
	{
		UnitCode = aUnitCode;
	}
	/** Agentgrade<P> */
	public String getAgentGrade()
	{
		if (AgentGrade != null && !AgentGrade.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentGrade = StrTool.unicodeToGBK(AgentGrade);
		}
		return AgentGrade;
	}
	/** Agentgrade */
	public void setAgentGrade(String aAgentGrade)
	{
		AgentGrade = aAgentGrade;
	}
	/** State<P> 网点状态0-有效;1-无效 */
	public int getState()
	{
		return State;
	}
	/** State */
	public void setState(int aState)
	{
		State = aState;
	}
	/** State<P> 网点状态0-有效;1-无效 */
	public void setState(String aState)
	{
		if (aState != null && !aState.equals(""))
		{
			Integer tInteger = new Integer(aState);
			int i = tInteger.intValue();
			State = i;
		}
	}

	/** Bak1<P> */
	public String getBak1()
	{
		if (Bak1 != null && !Bak1.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak1 = StrTool.unicodeToGBK(Bak1);
		}
		return Bak1;
	}
	/** Bak1 */
	public void setBak1(String aBak1)
	{
		Bak1 = aBak1;
	}
	/** Bak2<P> */
	public String getBak2()
	{
		if (Bak2 != null && !Bak2.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak2 = StrTool.unicodeToGBK(Bak2);
		}
		return Bak2;
	}
	/** Bak2 */
	public void setBak2(String aBak2)
	{
		Bak2 = aBak2;
	}
	/** Bak3<P> */
	public String getBak3()
	{
		if (Bak3 != null && !Bak3.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak3 = StrTool.unicodeToGBK(Bak3);
		}
		return Bak3;
	}
	/** Bak3 */
	public void setBak3(String aBak3)
	{
		Bak3 = aBak3;
	}
	/** Makedate<P> */
	public int getMakeDate()
	{
		return MakeDate;
	}
	/** Makedate */
	public void setMakeDate(int aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	/** Makedate<P> */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals(""))
		{
			Integer tInteger = new Integer(aMakeDate);
			int i = tInteger.intValue();
			MakeDate = i;
		}
	}

	/** Maketime<P> */
	public int getMakeTime()
	{
		return MakeTime;
	}
	/** Maketime */
	public void setMakeTime(int aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	/** Maketime<P> */
	public void setMakeTime(String aMakeTime)
	{
		if (aMakeTime != null && !aMakeTime.equals(""))
		{
			Integer tInteger = new Integer(aMakeTime);
			int i = tInteger.intValue();
			MakeTime = i;
		}
	}

	/** Modifydate<P> */
	public int getModifyDate()
	{
		return ModifyDate;
	}
	/** Modifydate */
	public void setModifyDate(int aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	/** Modifydate<P> */
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals(""))
		{
			Integer tInteger = new Integer(aModifyDate);
			int i = tInteger.intValue();
			ModifyDate = i;
		}
	}

	/** Modifytime<P> */
	public int getModifyTime()
	{
		return ModifyTime;
	}
	/** Modifytime */
	public void setModifyTime(int aModifyTime)
	{
		ModifyTime = aModifyTime;
	}
	/** Modifytime<P> */
	public void setModifyTime(String aModifyTime)
	{
		if (aModifyTime != null && !aModifyTime.equals(""))
		{
			Integer tInteger = new Integer(aModifyTime);
			int i = tInteger.intValue();
			ModifyTime = i;
		}
	}

	/** Operator<P> */
	public String getOperator()
	{
		if (Operator != null && !Operator.equals("") && SysConst.CHANGECHARSET == true)
		{
			Operator = StrTool.unicodeToGBK(Operator);
		}
		return Operator;
	}
	/** Operator */
	public void setOperator(String aOperator)
	{
		Operator = aOperator;
	}

	/**
	* 使用另外一个 AgentSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(AgentSchema aAgentSchema)
	{
		this.RecordNo = aAgentSchema.getRecordNo();
		this.Type = aAgentSchema.getType();
		this.AgentCom = aAgentSchema.getAgentCom();
		this.AgentComName = aAgentSchema.getAgentComName();
		this.AgentCode = aAgentSchema.getAgentCode();
		this.AgentName = aAgentSchema.getAgentName();
		this.ManageCom = aAgentSchema.getManageCom();
		this.ManageComName = aAgentSchema.getManageComName();
		this.UnitCode = aAgentSchema.getUnitCode();
		this.AgentGrade = aAgentSchema.getAgentGrade();
		this.State = aAgentSchema.getState();
		this.Bak1 = aAgentSchema.getBak1();
		this.Bak2 = aAgentSchema.getBak2();
		this.Bak3 = aAgentSchema.getBak3();
		this.MakeDate = aAgentSchema.getMakeDate();
		this.MakeTime = aAgentSchema.getMakeTime();
		this.ModifyDate = aAgentSchema.getModifyDate();
		this.ModifyTime = aAgentSchema.getModifyTime();
		this.Operator = aAgentSchema.getOperator();
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
			this.Type = rs.getInt("Type");
			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("AgentComName") == null )
				this.AgentComName = null;
			else
				this.AgentComName = rs.getString("AgentComName").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("AgentName") == null )
				this.AgentName = null;
			else
				this.AgentName = rs.getString("AgentName").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("ManageComName") == null )
				this.ManageComName = null;
			else
				this.ManageComName = rs.getString("ManageComName").trim();

			if( rs.getString("UnitCode") == null )
				this.UnitCode = null;
			else
				this.UnitCode = rs.getString("UnitCode").trim();

			if( rs.getString("AgentGrade") == null )
				this.AgentGrade = null;
			else
				this.AgentGrade = rs.getString("AgentGrade").trim();

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
			tError.moduleName = "AgentSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public AgentSchema getSchema()
	{
		AgentSchema aAgentSchema = new AgentSchema();
		aAgentSchema.setSchema(this);
		return aAgentSchema;
	}

	public AgentDB getDB()
	{
		AgentDB aDBOper = new AgentDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpAgent描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn =  ChgData.chgData(RecordNo) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Type) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentComName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ManageCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ManageComName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UnitCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentGrade) ) + SysConst.PACKAGESPILTER
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
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpAgent>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RecordNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			Type= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			AgentComName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			AgentName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			ManageComName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			UnitCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AgentGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			State= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,11,SysConst.PACKAGESPILTER))).intValue();
			Bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Bak3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			MakeTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			ModifyDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,17,SysConst.PACKAGESPILTER))).intValue();
			ModifyTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,18,SysConst.PACKAGESPILTER))).intValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AgentSchema";
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
		if (FCode.equals("Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type));
		}
		if (FCode.equals("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equals("AgentComName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentComName));
		}
		if (FCode.equals("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equals("AgentName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentName));
		}
		if (FCode.equals("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equals("ManageComName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageComName));
		}
		if (FCode.equals("UnitCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnitCode));
		}
		if (FCode.equals("AgentGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGrade));
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
				strFieldValue = String.valueOf(Type);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(AgentComName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AgentName);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(ManageComName);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(UnitCode);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AgentGrade);
				break;
			case 10:
				strFieldValue = String.valueOf(State);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(Bak1);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Bak2);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Bak3);
				break;
			case 14:
				strFieldValue = String.valueOf(MakeDate);
				break;
			case 15:
				strFieldValue = String.valueOf(MakeTime);
				break;
			case 16:
				strFieldValue = String.valueOf(ModifyDate);
				break;
			case 17:
				strFieldValue = String.valueOf(ModifyTime);
				break;
			case 18:
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
		if (FCode.equals("Type"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				Type = i;
			}
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
		if (FCode.equals("AgentComName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentComName = FValue.trim();
			}
			else
				AgentComName = null;
		}
		if (FCode.equals("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
		}
		if (FCode.equals("AgentName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentName = FValue.trim();
			}
			else
				AgentName = null;
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
		if (FCode.equals("ManageComName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ManageComName = FValue.trim();
			}
			else
				ManageComName = null;
		}
		if (FCode.equals("UnitCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				UnitCode = FValue.trim();
			}
			else
				UnitCode = null;
		}
		if (FCode.equals("AgentGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentGrade = FValue.trim();
			}
			else
				AgentGrade = null;
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
		AgentSchema other = (AgentSchema)otherObject;
		return
			RecordNo == other.getRecordNo()
			&& Type == other.getType()
			&& AgentCom.equals(other.getAgentCom())
			&& AgentComName.equals(other.getAgentComName())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentName.equals(other.getAgentName())
			&& ManageCom.equals(other.getManageCom())
			&& ManageComName.equals(other.getManageComName())
			&& UnitCode.equals(other.getUnitCode())
			&& AgentGrade.equals(other.getAgentGrade())
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
		if( strFieldName.equals("Type") ) {
			return 1;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 2;
		}
		if( strFieldName.equals("AgentComName") ) {
			return 3;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 4;
		}
		if( strFieldName.equals("AgentName") ) {
			return 5;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 6;
		}
		if( strFieldName.equals("ManageComName") ) {
			return 7;
		}
		if( strFieldName.equals("UnitCode") ) {
			return 8;
		}
		if( strFieldName.equals("AgentGrade") ) {
			return 9;
		}
		if( strFieldName.equals("State") ) {
			return 10;
		}
		if( strFieldName.equals("Bak1") ) {
			return 11;
		}
		if( strFieldName.equals("Bak2") ) {
			return 12;
		}
		if( strFieldName.equals("Bak3") ) {
			return 13;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 14;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 15;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 16;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 17;
		}
		if( strFieldName.equals("Operator") ) {
			return 18;
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
				strFieldName = "Type";
				break;
			case 2:
				strFieldName = "AgentCom";
				break;
			case 3:
				strFieldName = "AgentComName";
				break;
			case 4:
				strFieldName = "AgentCode";
				break;
			case 5:
				strFieldName = "AgentName";
				break;
			case 6:
				strFieldName = "ManageCom";
				break;
			case 7:
				strFieldName = "ManageComName";
				break;
			case 8:
				strFieldName = "UnitCode";
				break;
			case 9:
				strFieldName = "AgentGrade";
				break;
			case 10:
				strFieldName = "State";
				break;
			case 11:
				strFieldName = "Bak1";
				break;
			case 12:
				strFieldName = "Bak2";
				break;
			case 13:
				strFieldName = "Bak3";
				break;
			case 14:
				strFieldName = "MakeDate";
				break;
			case 15:
				strFieldName = "MakeTime";
				break;
			case 16:
				strFieldName = "ModifyDate";
				break;
			case 17:
				strFieldName = "ModifyTime";
				break;
			case 18:
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
		if( strFieldName.equals("Type") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentComName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageComName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("UnitCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGrade") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 16:
				nFieldType = Schema.TYPE_INT;
				break;
			case 17:
				nFieldType = Schema.TYPE_INT;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
