/*
 * <p>ClassName: CardTraceSchema </p>
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
import com.sinosoft.lis.db.CardTraceDB;

public class CardTraceSchema implements Schema
{
	// @Field
	/** 轨迹号 */
	private int TraceNo;
	/** 单证类型 */
	private int Type;
	/** 单证号 */
	private String CardNo;
	/** 操作代码 */
	private int OperType;
	/** 单证状态 */
	private int State;
	/** 关联号 */
	private String OtherNo;
	/** 交易单位 */
	private int TranCom;
	/** 交易网点 */
	private String NodeNo;
	/** 代理机构 */
	private String AgentCom;
	/** 代理人 */
	private String AgentCode;
	/** 管理机构 */
	private String ManageCom;
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
	/** 操作员 */
	private String Operator;

	public static final int FIELDNUM = 17;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public CardTraceSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "TraceNo";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 轨迹号<P> */
	public int getTraceNo()
	{
		return TraceNo;
	}
	/** 轨迹号 */
	public void setTraceNo(int aTraceNo)
	{
		TraceNo = aTraceNo;
	}
	/** 轨迹号<P> */
	public void setTraceNo(String aTraceNo)
	{
		if (aTraceNo != null && !aTraceNo.equals(""))
		{
			Integer tInteger = new Integer(aTraceNo);
			int i = tInteger.intValue();
			TraceNo = i;
		}
	}

	/** 单证类型<P>00-银保通保单;01-银保通批单 */
	public int getType()
	{
		return Type;
	}
	/** 单证类型 */
	public void setType(int aType)
	{
		Type = aType;
	}
	/** 单证类型<P>00-银保通保单;01-银保通批单 */
	public void setType(String aType)
	{
		if (aType != null && !aType.equals(""))
		{
			Integer tInteger = new Integer(aType);
			int i = tInteger.intValue();
			Type = i;
		}
	}

	/** 单证号<P> */
	public String getCardNo()
	{
		if (CardNo != null && !CardNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			CardNo = StrTool.unicodeToGBK(CardNo);
		}
		return CardNo;
	}
	/** 单证号 */
	public void setCardNo(String aCardNo)
	{
		CardNo = aCardNo;
	}
	/** 操作代码<P>00-下发；01-核销；02-作废；04-回收 */
	public int getOperType()
	{
		return OperType;
	}
	/** 操作代码 */
	public void setOperType(int aOperType)
	{
		OperType = aOperType;
	}
	/** 操作代码<P>00-下发；01-核销；02-作废；04-回收 */
	public void setOperType(String aOperType)
	{
		if (aOperType != null && !aOperType.equals(""))
		{
			Integer tInteger = new Integer(aOperType);
			int i = tInteger.intValue();
			OperType = i;
		}
	}

	/** 单证状态<P>0-可用;1-核销;2-作废 */
	public int getState()
	{
		return State;
	}
	/** 单证状态 */
	public void setState(int aState)
	{
		State = aState;
	}
	/** 单证状态<P>0-可用;1-核销;2-作废 */
	public void setState(String aState)
	{
		if (aState != null && !aState.equals(""))
		{
			Integer tInteger = new Integer(aState);
			int i = tInteger.intValue();
			State = i;
		}
	}

	/** 关联号<P>保单号、保全号等 */
	public String getOtherNo()
	{
		if (OtherNo != null && !OtherNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			OtherNo = StrTool.unicodeToGBK(OtherNo);
		}
		return OtherNo;
	}
	/** 关联号 */
	public void setOtherNo(String aOtherNo)
	{
		OtherNo = aOtherNo;
	}
	/** 交易单位<P> */
	public int getTranCom()
	{
		return TranCom;
	}
	/** 交易单位 */
	public void setTranCom(int aTranCom)
	{
		TranCom = aTranCom;
	}
	/** 交易单位<P> */
	public void setTranCom(String aTranCom)
	{
		if (aTranCom != null && !aTranCom.equals(""))
		{
			Integer tInteger = new Integer(aTranCom);
			int i = tInteger.intValue();
			TranCom = i;
		}
	}

	/** 交易网点<P> */
	public String getNodeNo()
	{
		if (NodeNo != null && !NodeNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			NodeNo = StrTool.unicodeToGBK(NodeNo);
		}
		return NodeNo;
	}
	/** 交易网点 */
	public void setNodeNo(String aNodeNo)
	{
		NodeNo = aNodeNo;
	}
	/** 代理机构<P> */
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
	/** 代理人<P> */
	public String getAgentCode()
	{
		if (AgentCode != null && !AgentCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentCode = StrTool.unicodeToGBK(AgentCode);
		}
		return AgentCode;
	}
	/** 代理人 */
	public void setAgentCode(String aAgentCode)
	{
		AgentCode = aAgentCode;
	}
	/** 管理机构<P> */
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
	* 使用另外一个 CardTraceSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(CardTraceSchema aCardTraceSchema)
	{
		this.TraceNo = aCardTraceSchema.getTraceNo();
		this.Type = aCardTraceSchema.getType();
		this.CardNo = aCardTraceSchema.getCardNo();
		this.OperType = aCardTraceSchema.getOperType();
		this.State = aCardTraceSchema.getState();
		this.OtherNo = aCardTraceSchema.getOtherNo();
		this.TranCom = aCardTraceSchema.getTranCom();
		this.NodeNo = aCardTraceSchema.getNodeNo();
		this.AgentCom = aCardTraceSchema.getAgentCom();
		this.AgentCode = aCardTraceSchema.getAgentCode();
		this.ManageCom = aCardTraceSchema.getManageCom();
		this.Bak1 = aCardTraceSchema.getBak1();
		this.Bak2 = aCardTraceSchema.getBak2();
		this.Bak3 = aCardTraceSchema.getBak3();
		this.MakeDate = aCardTraceSchema.getMakeDate();
		this.MakeTime = aCardTraceSchema.getMakeTime();
		this.Operator = aCardTraceSchema.getOperator();
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
			this.TraceNo = rs.getInt("TraceNo");
			this.Type = rs.getInt("Type");
			if( rs.getString("CardNo") == null )
				this.CardNo = null;
			else
				this.CardNo = rs.getString("CardNo").trim();

			this.OperType = rs.getInt("OperType");
			this.State = rs.getInt("State");
			if( rs.getString("OtherNo") == null )
				this.OtherNo = null;
			else
				this.OtherNo = rs.getString("OtherNo").trim();

			this.TranCom = rs.getInt("TranCom");
			if( rs.getString("NodeNo") == null )
				this.NodeNo = null;
			else
				this.NodeNo = rs.getString("NodeNo").trim();

			if( rs.getString("AgentCom") == null )
				this.AgentCom = null;
			else
				this.AgentCom = rs.getString("AgentCom").trim();

			if( rs.getString("AgentCode") == null )
				this.AgentCode = null;
			else
				this.AgentCode = rs.getString("AgentCode").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

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
			if( rs.getString("Operator") == null )
				this.Operator = null;
			else
				this.Operator = rs.getString("Operator").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CardTraceSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public CardTraceSchema getSchema()
	{
		CardTraceSchema aCardTraceSchema = new CardTraceSchema();
		aCardTraceSchema.setSchema(this);
		return aCardTraceSchema;
	}

	public CardTraceDB getDB()
	{
		CardTraceDB aDBOper = new CardTraceDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpCardTrace描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn =  ChgData.chgData(TraceNo) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Type) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CardNo) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(OperType) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(State) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OtherNo) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TranCom) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NodeNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ManageCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak3) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeTime) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpCardTrace>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			TraceNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			Type= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			CardNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			OperType= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,4,SysConst.PACKAGESPILTER))).intValue();
			State= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			OtherNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			TranCom= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,7,SysConst.PACKAGESPILTER))).intValue();
			NodeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			Bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Bak3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,15,SysConst.PACKAGESPILTER))).intValue();
			MakeTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,16,SysConst.PACKAGESPILTER))).intValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CardTraceSchema";
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
		if (FCode.equals("TraceNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TraceNo));
		}
		if (FCode.equals("Type"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Type));
		}
		if (FCode.equals("CardNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CardNo));
		}
		if (FCode.equals("OperType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OperType));
		}
		if (FCode.equals("State"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(State));
		}
		if (FCode.equals("OtherNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OtherNo));
		}
		if (FCode.equals("TranCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranCom));
		}
		if (FCode.equals("NodeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeNo));
		}
		if (FCode.equals("AgentCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCom));
		}
		if (FCode.equals("AgentCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCode));
		}
		if (FCode.equals("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
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
				strFieldValue = String.valueOf(TraceNo);
				break;
			case 1:
				strFieldValue = String.valueOf(Type);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(CardNo);
				break;
			case 3:
				strFieldValue = String.valueOf(OperType);
				break;
			case 4:
				strFieldValue = String.valueOf(State);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(OtherNo);
				break;
			case 6:
				strFieldValue = String.valueOf(TranCom);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(NodeNo);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
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

		if (FCode.equals("TraceNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TraceNo = i;
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
		if (FCode.equals("CardNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CardNo = FValue.trim();
			}
			else
				CardNo = null;
		}
		if (FCode.equals("OperType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				OperType = i;
			}
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
		if (FCode.equals("OtherNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OtherNo = FValue.trim();
			}
			else
				OtherNo = null;
		}
		if (FCode.equals("TranCom"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				TranCom = i;
			}
		}
		if (FCode.equals("NodeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeNo = FValue.trim();
			}
			else
				NodeNo = null;
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
		if (FCode.equals("AgentCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCode = FValue.trim();
			}
			else
				AgentCode = null;
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
		CardTraceSchema other = (CardTraceSchema)otherObject;
		return
			TraceNo == other.getTraceNo()
			&& Type == other.getType()
			&& CardNo.equals(other.getCardNo())
			&& OperType == other.getOperType()
			&& State == other.getState()
			&& OtherNo.equals(other.getOtherNo())
			&& TranCom == other.getTranCom()
			&& NodeNo.equals(other.getNodeNo())
			&& AgentCom.equals(other.getAgentCom())
			&& AgentCode.equals(other.getAgentCode())
			&& ManageCom.equals(other.getManageCom())
			&& Bak1.equals(other.getBak1())
			&& Bak2.equals(other.getBak2())
			&& Bak3.equals(other.getBak3())
			&& MakeDate == other.getMakeDate()
			&& MakeTime == other.getMakeTime()
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
		if( strFieldName.equals("TraceNo") ) {
			return 0;
		}
		if( strFieldName.equals("Type") ) {
			return 1;
		}
		if( strFieldName.equals("CardNo") ) {
			return 2;
		}
		if( strFieldName.equals("OperType") ) {
			return 3;
		}
		if( strFieldName.equals("State") ) {
			return 4;
		}
		if( strFieldName.equals("OtherNo") ) {
			return 5;
		}
		if( strFieldName.equals("TranCom") ) {
			return 6;
		}
		if( strFieldName.equals("NodeNo") ) {
			return 7;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 8;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 9;
		}
		if( strFieldName.equals("ManageCom") ) {
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
				strFieldName = "TraceNo";
				break;
			case 1:
				strFieldName = "Type";
				break;
			case 2:
				strFieldName = "CardNo";
				break;
			case 3:
				strFieldName = "OperType";
				break;
			case 4:
				strFieldName = "State";
				break;
			case 5:
				strFieldName = "OtherNo";
				break;
			case 6:
				strFieldName = "TranCom";
				break;
			case 7:
				strFieldName = "NodeNo";
				break;
			case 8:
				strFieldName = "AgentCom";
				break;
			case 9:
				strFieldName = "AgentCode";
				break;
			case 10:
				strFieldName = "ManageCom";
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
		if( strFieldName.equals("TraceNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("Type") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("CardNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OperType") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("State") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("OtherNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TranCom") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("NodeNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 4:
				nFieldType = Schema.TYPE_INT;
				break;
			case 5:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 6:
				nFieldType = Schema.TYPE_INT;
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
