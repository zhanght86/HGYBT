/*
 * <p>ClassName: NodeMapSchema </p>
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
import com.sinosoft.lis.db.NodeMapDB;

public class NodeMapSchema implements Schema
{
	// @Field
	/** 映射号 */
	private int MapNo;
	/** 交易单位 */
	private int TranCom;
	/** 交易地区 */
	private String ZoneNo;
	/** 交易网点 */
	private String NodeNo;
	/** 网点类型 */
	private int Type;
	/** 代理机构 */
	private String AgentCom;
	/** 代理机构名称 */
	private String AgentComName;
	/** 代理人 */
	private String AgentCode;
	/** 代理人姓名 */
	private String AgentName;
	/** 管理机构 */
	private String ManageCom;
	/** 代理机构组别 */
	private String UnitCode;
	/** 代理机构级别 */
	private String AgentGrade;
	/** 代理人级别 */
	private String AgentCodeGrade;
	/** 兼业代理资格证号 */
	private String ConAgentNo;
	/** 资格证起始日 */
	private Date ConStartDate;
	/** 资格证结束日 */
	private Date ConEndDate;
	/** 销售资格 */
	private String SaleFlag;
	/** 网点系统状态 */
	private String State;
	/** 备用1 */
	private String Bak1;
	/** 备用2 */
	private String Bak2;
	/** 备用3 */
	private String Bak3;
	/** 备用4 */
	private String Bak4;
	/** 备用5 */
	private String Bak5;
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

	public static final int FIELDNUM = 28;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public NodeMapSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "MapNo";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 映射号<P>映射号 */
	public int getMapNo()
	{
		return MapNo;
	}
	/** 映射号 */
	public void setMapNo(int aMapNo)
	{
		MapNo = aMapNo;
	}
	/** 映射号<P>映射号 */
	public void setMapNo(String aMapNo)
	{
		if (aMapNo != null && !aMapNo.equals(""))
		{
			Integer tInteger = new Integer(aMapNo);
			int i = tInteger.intValue();
			MapNo = i;
		}
	}

	/** 交易单位<P>交易单位 */
	public int getTranCom()
	{
		return TranCom;
	}
	/** 交易单位 */
	public void setTranCom(int aTranCom)
	{
		TranCom = aTranCom;
	}
	/** 交易单位<P>交易单位 */
	public void setTranCom(String aTranCom)
	{
		if (aTranCom != null && !aTranCom.equals(""))
		{
			Integer tInteger = new Integer(aTranCom);
			int i = tInteger.intValue();
			TranCom = i;
		}
	}

	/** 交易地区<P> */
	public String getZoneNo()
	{
		if (ZoneNo != null && !ZoneNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ZoneNo = StrTool.unicodeToGBK(ZoneNo);
		}
		return ZoneNo;
	}
	/** 交易地区 */
	public void setZoneNo(String aZoneNo)
	{
		ZoneNo = aZoneNo;
	}
	/** 交易网点<P>交易网点 */
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
	/** 网点类型<P>网点类型 */
	public int getType()
	{
		return Type;
	}
	/** 网点类型 */
	public void setType(int aType)
	{
		Type = aType;
	}
	/** 网点类型<P>网点类型 */
	public void setType(String aType)
	{
		if (aType != null && !aType.equals(""))
		{
			Integer tInteger = new Integer(aType);
			int i = tInteger.intValue();
			Type = i;
		}
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
	/** 代理机构名称<P>代理机构名称 */
	public String getAgentComName()
	{
		if (AgentComName != null && !AgentComName.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentComName = StrTool.unicodeToGBK(AgentComName);
		}
		return AgentComName;
	}
	/** 代理机构名称 */
	public void setAgentComName(String aAgentComName)
	{
		AgentComName = aAgentComName;
	}
	/** 代理人<P>代理人 */
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
	/** 代理人姓名<P>代理人姓名 */
	public String getAgentName()
	{
		if (AgentName != null && !AgentName.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentName = StrTool.unicodeToGBK(AgentName);
		}
		return AgentName;
	}
	/** 代理人姓名 */
	public void setAgentName(String aAgentName)
	{
		AgentName = aAgentName;
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
	/** 代理机构组别<P> */
	public String getUnitCode()
	{
		if (UnitCode != null && !UnitCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			UnitCode = StrTool.unicodeToGBK(UnitCode);
		}
		return UnitCode;
	}
	/** 代理机构组别 */
	public void setUnitCode(String aUnitCode)
	{
		UnitCode = aUnitCode;
	}
	/** 代理机构级别<P> */
	public String getAgentGrade()
	{
		if (AgentGrade != null && !AgentGrade.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentGrade = StrTool.unicodeToGBK(AgentGrade);
		}
		return AgentGrade;
	}
	/** 代理机构级别 */
	public void setAgentGrade(String aAgentGrade)
	{
		AgentGrade = aAgentGrade;
	}
	/** 代理人级别<P> */
	public String getAgentCodeGrade()
	{
		if (AgentCodeGrade != null && !AgentCodeGrade.equals("") && SysConst.CHANGECHARSET == true)
		{
			AgentCodeGrade = StrTool.unicodeToGBK(AgentCodeGrade);
		}
		return AgentCodeGrade;
	}
	/** 代理人级别 */
	public void setAgentCodeGrade(String aAgentCodeGrade)
	{
		AgentCodeGrade = aAgentCodeGrade;
	}
	/** 兼业代理资格证号<P> */
	public String getConAgentNo()
	{
		if (ConAgentNo != null && !ConAgentNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ConAgentNo = StrTool.unicodeToGBK(ConAgentNo);
		}
		return ConAgentNo;
	}
	/** 兼业代理资格证号 */
	public void setConAgentNo(String aConAgentNo)
	{
		ConAgentNo = aConAgentNo;
	}
	/** 资格证起始日<P> */
	public String getConStartDate()
	{
		if( ConStartDate != null )
			return fDate.getString(ConStartDate);
		else
			return null;
	}
	/** 资格证起始日 */
	public void setConStartDate(Date aConStartDate)
	{
		ConStartDate = aConStartDate;
	}
	/** 资格证起始日<P> */
	public void setConStartDate(String aConStartDate)
	{
		if (aConStartDate != null && !aConStartDate.equals("") )
		{
			ConStartDate = fDate.getDate( aConStartDate );
		}
		else
			ConStartDate = null;
	}

	/** 资格证结束日<P> */
	public String getConEndDate()
	{
		if( ConEndDate != null )
			return fDate.getString(ConEndDate);
		else
			return null;
	}
	/** 资格证结束日 */
	public void setConEndDate(Date aConEndDate)
	{
		ConEndDate = aConEndDate;
	}
	/** 资格证结束日<P> */
	public void setConEndDate(String aConEndDate)
	{
		if (aConEndDate != null && !aConEndDate.equals("") )
		{
			ConEndDate = fDate.getDate( aConEndDate );
		}
		else
			ConEndDate = null;
	}

	/** 销售资格<P> */
	public String getSaleFlag()
	{
		if (SaleFlag != null && !SaleFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			SaleFlag = StrTool.unicodeToGBK(SaleFlag);
		}
		return SaleFlag;
	}
	/** 销售资格 */
	public void setSaleFlag(String aSaleFlag)
	{
		SaleFlag = aSaleFlag;
	}
	/** 网点系统状态<P> */
	public String getState()
	{
		if (State != null && !State.equals("") && SysConst.CHANGECHARSET == true)
		{
			State = StrTool.unicodeToGBK(State);
		}
		return State;
	}
	/** 网点系统状态 */
	public void setState(String aState)
	{
		State = aState;
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
	/** 备用4<P> */
	public String getBak4()
	{
		if (Bak4 != null && !Bak4.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak4 = StrTool.unicodeToGBK(Bak4);
		}
		return Bak4;
	}
	/** 备用4 */
	public void setBak4(String aBak4)
	{
		Bak4 = aBak4;
	}
	/** 备用5<P> */
	public String getBak5()
	{
		if (Bak5 != null && !Bak5.equals("") && SysConst.CHANGECHARSET == true)
		{
			Bak5 = StrTool.unicodeToGBK(Bak5);
		}
		return Bak5;
	}
	/** 备用5 */
	public void setBak5(String aBak5)
	{
		Bak5 = aBak5;
	}
	/** 入库日期<P>入库日期 */
	public int getMakeDate()
	{
		return MakeDate;
	}
	/** 入库日期 */
	public void setMakeDate(int aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	/** 入库日期<P>入库日期 */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals(""))
		{
			Integer tInteger = new Integer(aMakeDate);
			int i = tInteger.intValue();
			MakeDate = i;
		}
	}

	/** 入库时间<P>入库时间 */
	public int getMakeTime()
	{
		return MakeTime;
	}
	/** 入库时间 */
	public void setMakeTime(int aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	/** 入库时间<P>入库时间 */
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
	* 使用另外一个 NodeMapSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(NodeMapSchema aNodeMapSchema)
	{
		this.MapNo = aNodeMapSchema.getMapNo();
		this.TranCom = aNodeMapSchema.getTranCom();
		this.ZoneNo = aNodeMapSchema.getZoneNo();
		this.NodeNo = aNodeMapSchema.getNodeNo();
		this.Type = aNodeMapSchema.getType();
		this.AgentCom = aNodeMapSchema.getAgentCom();
		this.AgentComName = aNodeMapSchema.getAgentComName();
		this.AgentCode = aNodeMapSchema.getAgentCode();
		this.AgentName = aNodeMapSchema.getAgentName();
		this.ManageCom = aNodeMapSchema.getManageCom();
		this.UnitCode = aNodeMapSchema.getUnitCode();
		this.AgentGrade = aNodeMapSchema.getAgentGrade();
		this.AgentCodeGrade = aNodeMapSchema.getAgentCodeGrade();
		this.ConAgentNo = aNodeMapSchema.getConAgentNo();
		this.ConStartDate = fDate.getDate( aNodeMapSchema.getConStartDate());
		this.ConEndDate = fDate.getDate( aNodeMapSchema.getConEndDate());
		this.SaleFlag = aNodeMapSchema.getSaleFlag();
		this.State = aNodeMapSchema.getState();
		this.Bak1 = aNodeMapSchema.getBak1();
		this.Bak2 = aNodeMapSchema.getBak2();
		this.Bak3 = aNodeMapSchema.getBak3();
		this.Bak4 = aNodeMapSchema.getBak4();
		this.Bak5 = aNodeMapSchema.getBak5();
		this.MakeDate = aNodeMapSchema.getMakeDate();
		this.MakeTime = aNodeMapSchema.getMakeTime();
		this.ModifyDate = aNodeMapSchema.getModifyDate();
		this.ModifyTime = aNodeMapSchema.getModifyTime();
		this.Operator = aNodeMapSchema.getOperator();
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
			this.MapNo = rs.getInt("MapNo");
			this.TranCom = rs.getInt("TranCom");
			if( rs.getString("ZoneNo") == null )
				this.ZoneNo = null;
			else
				this.ZoneNo = rs.getString("ZoneNo").trim();

			if( rs.getString("NodeNo") == null )
				this.NodeNo = null;
			else
				this.NodeNo = rs.getString("NodeNo").trim();

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

			if( rs.getString("UnitCode") == null )
				this.UnitCode = null;
			else
				this.UnitCode = rs.getString("UnitCode").trim();

			if( rs.getString("AgentGrade") == null )
				this.AgentGrade = null;
			else
				this.AgentGrade = rs.getString("AgentGrade").trim();

			if( rs.getString("AgentCodeGrade") == null )
				this.AgentCodeGrade = null;
			else
				this.AgentCodeGrade = rs.getString("AgentCodeGrade").trim();

			if( rs.getString("ConAgentNo") == null )
				this.ConAgentNo = null;
			else
				this.ConAgentNo = rs.getString("ConAgentNo").trim();

			this.ConStartDate = rs.getDate("ConStartDate");
			this.ConEndDate = rs.getDate("ConEndDate");
			if( rs.getString("SaleFlag") == null )
				this.SaleFlag = null;
			else
				this.SaleFlag = rs.getString("SaleFlag").trim();

			if( rs.getString("State") == null )
				this.State = null;
			else
				this.State = rs.getString("State").trim();

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

			if( rs.getString("Bak4") == null )
				this.Bak4 = null;
			else
				this.Bak4 = rs.getString("Bak4").trim();

			if( rs.getString("Bak5") == null )
				this.Bak5 = null;
			else
				this.Bak5 = rs.getString("Bak5").trim();

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
			tError.moduleName = "NodeMapSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public NodeMapSchema getSchema()
	{
		NodeMapSchema aNodeMapSchema = new NodeMapSchema();
		aNodeMapSchema.setSchema(this);
		return aNodeMapSchema;
	}

	public NodeMapDB getDB()
	{
		NodeMapDB aDBOper = new NodeMapDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpNodeMap描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn =  ChgData.chgData(MapNo) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(TranCom) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ZoneNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NodeNo) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(Type) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentComName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ManageCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(UnitCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentGrade) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AgentCodeGrade) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ConAgentNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ConStartDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ConEndDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SaleFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(State) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak4) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak5) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(MakeTime) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyDate) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(ModifyTime) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Operator) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpNodeMap>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			MapNo= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			TranCom= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,2,SysConst.PACKAGESPILTER))).intValue();
			ZoneNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			NodeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			Type= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,5,SysConst.PACKAGESPILTER))).intValue();
			AgentCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			AgentComName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			AgentCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			AgentName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			UnitCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			AgentGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			AgentCodeGrade = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			ConAgentNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			ConStartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15,SysConst.PACKAGESPILTER));
			ConEndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16,SysConst.PACKAGESPILTER));
			SaleFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			State = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			Bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			Bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			Bak3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			Bak4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			Bak5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			MakeDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,24,SysConst.PACKAGESPILTER))).intValue();
			MakeTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).intValue();
			ModifyDate= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).intValue();
			ModifyTime= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,27,SysConst.PACKAGESPILTER))).intValue();
			Operator = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "NodeMapSchema";
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
		if (FCode.equals("MapNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MapNo));
		}
		if (FCode.equals("TranCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranCom));
		}
		if (FCode.equals("ZoneNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZoneNo));
		}
		if (FCode.equals("NodeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeNo));
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
		if (FCode.equals("UnitCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(UnitCode));
		}
		if (FCode.equals("AgentGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentGrade));
		}
		if (FCode.equals("AgentCodeGrade"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AgentCodeGrade));
		}
		if (FCode.equals("ConAgentNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ConAgentNo));
		}
		if (FCode.equals("ConStartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConStartDate()));
		}
		if (FCode.equals("ConEndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getConEndDate()));
		}
		if (FCode.equals("SaleFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SaleFlag));
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
		if (FCode.equals("Bak4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak4));
		}
		if (FCode.equals("Bak5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(Bak5));
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
				strFieldValue = String.valueOf(MapNo);
				break;
			case 1:
				strFieldValue = String.valueOf(TranCom);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ZoneNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(NodeNo);
				break;
			case 4:
				strFieldValue = String.valueOf(Type);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(AgentCom);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(AgentComName);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(AgentCode);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(AgentName);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(UnitCode);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(AgentGrade);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(AgentCodeGrade);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(ConAgentNo);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConStartDate()));
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getConEndDate()));
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(SaleFlag);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(State);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(Bak1);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(Bak2);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(Bak3);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(Bak4);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(Bak5);
				break;
			case 23:
				strFieldValue = String.valueOf(MakeDate);
				break;
			case 24:
				strFieldValue = String.valueOf(MakeTime);
				break;
			case 25:
				strFieldValue = String.valueOf(ModifyDate);
				break;
			case 26:
				strFieldValue = String.valueOf(ModifyTime);
				break;
			case 27:
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

		if (FCode.equals("MapNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				MapNo = i;
			}
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
		if (FCode.equals("ZoneNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ZoneNo = FValue.trim();
			}
			else
				ZoneNo = null;
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
		if (FCode.equals("AgentCodeGrade"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AgentCodeGrade = FValue.trim();
			}
			else
				AgentCodeGrade = null;
		}
		if (FCode.equals("ConAgentNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ConAgentNo = FValue.trim();
			}
			else
				ConAgentNo = null;
		}
		if (FCode.equals("ConStartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConStartDate = fDate.getDate( FValue );
			}
			else
				ConStartDate = null;
		}
		if (FCode.equals("ConEndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				ConEndDate = fDate.getDate( FValue );
			}
			else
				ConEndDate = null;
		}
		if (FCode.equals("SaleFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SaleFlag = FValue.trim();
			}
			else
				SaleFlag = null;
		}
		if (FCode.equals("State"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				State = FValue.trim();
			}
			else
				State = null;
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
		if (FCode.equals("Bak4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak4 = FValue.trim();
			}
			else
				Bak4 = null;
		}
		if (FCode.equals("Bak5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Bak5 = FValue.trim();
			}
			else
				Bak5 = null;
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
		NodeMapSchema other = (NodeMapSchema)otherObject;
		return
			MapNo == other.getMapNo()
			&& TranCom == other.getTranCom()
			&& ZoneNo.equals(other.getZoneNo())
			&& NodeNo.equals(other.getNodeNo())
			&& Type == other.getType()
			&& AgentCom.equals(other.getAgentCom())
			&& AgentComName.equals(other.getAgentComName())
			&& AgentCode.equals(other.getAgentCode())
			&& AgentName.equals(other.getAgentName())
			&& ManageCom.equals(other.getManageCom())
			&& UnitCode.equals(other.getUnitCode())
			&& AgentGrade.equals(other.getAgentGrade())
			&& AgentCodeGrade.equals(other.getAgentCodeGrade())
			&& ConAgentNo.equals(other.getConAgentNo())
			&& fDate.getString(ConStartDate).equals(other.getConStartDate())
			&& fDate.getString(ConEndDate).equals(other.getConEndDate())
			&& SaleFlag.equals(other.getSaleFlag())
			&& State.equals(other.getState())
			&& Bak1.equals(other.getBak1())
			&& Bak2.equals(other.getBak2())
			&& Bak3.equals(other.getBak3())
			&& Bak4.equals(other.getBak4())
			&& Bak5.equals(other.getBak5())
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
		if( strFieldName.equals("MapNo") ) {
			return 0;
		}
		if( strFieldName.equals("TranCom") ) {
			return 1;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return 2;
		}
		if( strFieldName.equals("NodeNo") ) {
			return 3;
		}
		if( strFieldName.equals("Type") ) {
			return 4;
		}
		if( strFieldName.equals("AgentCom") ) {
			return 5;
		}
		if( strFieldName.equals("AgentComName") ) {
			return 6;
		}
		if( strFieldName.equals("AgentCode") ) {
			return 7;
		}
		if( strFieldName.equals("AgentName") ) {
			return 8;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 9;
		}
		if( strFieldName.equals("UnitCode") ) {
			return 10;
		}
		if( strFieldName.equals("AgentGrade") ) {
			return 11;
		}
		if( strFieldName.equals("AgentCodeGrade") ) {
			return 12;
		}
		if( strFieldName.equals("ConAgentNo") ) {
			return 13;
		}
		if( strFieldName.equals("ConStartDate") ) {
			return 14;
		}
		if( strFieldName.equals("ConEndDate") ) {
			return 15;
		}
		if( strFieldName.equals("SaleFlag") ) {
			return 16;
		}
		if( strFieldName.equals("State") ) {
			return 17;
		}
		if( strFieldName.equals("Bak1") ) {
			return 18;
		}
		if( strFieldName.equals("Bak2") ) {
			return 19;
		}
		if( strFieldName.equals("Bak3") ) {
			return 20;
		}
		if( strFieldName.equals("Bak4") ) {
			return 21;
		}
		if( strFieldName.equals("Bak5") ) {
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
		if( strFieldName.equals("Operator") ) {
			return 27;
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
				strFieldName = "MapNo";
				break;
			case 1:
				strFieldName = "TranCom";
				break;
			case 2:
				strFieldName = "ZoneNo";
				break;
			case 3:
				strFieldName = "NodeNo";
				break;
			case 4:
				strFieldName = "Type";
				break;
			case 5:
				strFieldName = "AgentCom";
				break;
			case 6:
				strFieldName = "AgentComName";
				break;
			case 7:
				strFieldName = "AgentCode";
				break;
			case 8:
				strFieldName = "AgentName";
				break;
			case 9:
				strFieldName = "ManageCom";
				break;
			case 10:
				strFieldName = "UnitCode";
				break;
			case 11:
				strFieldName = "AgentGrade";
				break;
			case 12:
				strFieldName = "AgentCodeGrade";
				break;
			case 13:
				strFieldName = "ConAgentNo";
				break;
			case 14:
				strFieldName = "ConStartDate";
				break;
			case 15:
				strFieldName = "ConEndDate";
				break;
			case 16:
				strFieldName = "SaleFlag";
				break;
			case 17:
				strFieldName = "State";
				break;
			case 18:
				strFieldName = "Bak1";
				break;
			case 19:
				strFieldName = "Bak2";
				break;
			case 20:
				strFieldName = "Bak3";
				break;
			case 21:
				strFieldName = "Bak4";
				break;
			case 22:
				strFieldName = "Bak5";
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
		if( strFieldName.equals("MapNo") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TranCom") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeNo") ) {
			return Schema.TYPE_STRING;
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
		if( strFieldName.equals("UnitCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AgentCodeGrade") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConAgentNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ConStartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("ConEndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SaleFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("State") ) {
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
		if( strFieldName.equals("Bak4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("Bak5") ) {
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
				nFieldType = Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			case 24:
				nFieldType = Schema.TYPE_INT;
				break;
			case 25:
				nFieldType = Schema.TYPE_INT;
				break;
			case 26:
				nFieldType = Schema.TYPE_INT;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
