/*
 * <p>ClassName: LdTellerSchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-05-25
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.LdTellerDB;

public class LdTellerSchema implements Schema
{
	// @Field
	/** Srs柜员编号 */
	private String SRSTellerNo;
	/** 银行编码 */
	private String TranCom;
	/** 银行地区代码 */
	private String ZoneNo;
	/** 银行柜员编号 */
	private String TellerNo;
	/** 柜员姓名 */
	private String TellerName;
	/** 柜员资格证类型 */
	private String QuType;
	/** 柜员资格证号 */
	private String TellerQuNo;
	/** 生效日期 */
	private Date StartDate;
	/** 失效日期 */
	private Date EndDate;
	/** 销售资格 */
	private String SellFlag;
	/** 公司机构编码 */
	private String ManageCom;
	/** 银行网点代码 */
	private String NodeNo;
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
	private Date MakeDate;
	/** 入库时间 */
	private String MakeTime;
	/** 最后修改日期 */
	private Date ModifyDate;
	/** 最后修改时间 */
	private String ModifyTime;
	/** 同步日期 */
	private Date SynchroDate;
	/** 同步时间 */
	private String SynchroTime;

	public static final int FIELDNUM = 23;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LdTellerSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "SRSTellerNo";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** Srs柜员编号<P> */
	public String getSRSTellerNo()
	{
		if (SRSTellerNo != null && !SRSTellerNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			SRSTellerNo = StrTool.unicodeToGBK(SRSTellerNo);
		}
		return SRSTellerNo;
	}
	/** Srs柜员编号 */
	public void setSRSTellerNo(String aSRSTellerNo)
	{
		SRSTellerNo = aSRSTellerNo;
	}
	/** 银行编码<P>交易类型 */
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
	/** 银行地区代码<P>交易日期 */
	public String getZoneNo()
	{
		if (ZoneNo != null && !ZoneNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			ZoneNo = StrTool.unicodeToGBK(ZoneNo);
		}
		return ZoneNo;
	}
	/** 银行地区代码 */
	public void setZoneNo(String aZoneNo)
	{
		ZoneNo = aZoneNo;
	}
	/** 银行柜员编号<P> */
	public String getTellerNo()
	{
		if (TellerNo != null && !TellerNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			TellerNo = StrTool.unicodeToGBK(TellerNo);
		}
		return TellerNo;
	}
	/** 银行柜员编号 */
	public void setTellerNo(String aTellerNo)
	{
		TellerNo = aTellerNo;
	}
	/** 柜员姓名<P>结果描述 */
	public String getTellerName()
	{
		if (TellerName != null && !TellerName.equals("") && SysConst.CHANGECHARSET == true)
		{
			TellerName = StrTool.unicodeToGBK(TellerName);
		}
		return TellerName;
	}
	/** 柜员姓名 */
	public void setTellerName(String aTellerName)
	{
		TellerName = aTellerName;
	}
	/** 柜员资格证类型<P> */
	public String getQuType()
	{
		if (QuType != null && !QuType.equals("") && SysConst.CHANGECHARSET == true)
		{
			QuType = StrTool.unicodeToGBK(QuType);
		}
		return QuType;
	}
	/** 柜员资格证类型 */
	public void setQuType(String aQuType)
	{
		QuType = aQuType;
	}
	/** 柜员资格证号<P>服务耗时，单位秒(s) */
	public String getTellerQuNo()
	{
		if (TellerQuNo != null && !TellerQuNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			TellerQuNo = StrTool.unicodeToGBK(TellerQuNo);
		}
		return TellerQuNo;
	}
	/** 柜员资格证号 */
	public void setTellerQuNo(String aTellerQuNo)
	{
		TellerQuNo = aTellerQuNo;
	}
	/** 生效日期<P> */
	public String getStartDate()
	{
		if( StartDate != null )
			return fDate.getString(StartDate);
		else
			return null;
	}
	/** 生效日期 */
	public void setStartDate(Date aStartDate)
	{
		StartDate = aStartDate;
	}
	/** 生效日期<P> */
	public void setStartDate(String aStartDate)
	{
		if (aStartDate != null && !aStartDate.equals("") )
		{
			StartDate = fDate.getDate( aStartDate );
		}
		else
			StartDate = null;
	}

	/** 失效日期<P> */
	public String getEndDate()
	{
		if( EndDate != null )
			return fDate.getString(EndDate);
		else
			return null;
	}
	/** 失效日期 */
	public void setEndDate(Date aEndDate)
	{
		EndDate = aEndDate;
	}
	/** 失效日期<P> */
	public void setEndDate(String aEndDate)
	{
		if (aEndDate != null && !aEndDate.equals("") )
		{
			EndDate = fDate.getDate( aEndDate );
		}
		else
			EndDate = null;
	}

	/** 销售资格<P> */
	public String getSellFlag()
	{
		if (SellFlag != null && !SellFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			SellFlag = StrTool.unicodeToGBK(SellFlag);
		}
		return SellFlag;
	}
	/** 销售资格 */
	public void setSellFlag(String aSellFlag)
	{
		SellFlag = aSellFlag;
	}
	/** 公司机构编码<P>日志号 */
	public String getManageCom()
	{
		if (ManageCom != null && !ManageCom.equals("") && SysConst.CHANGECHARSET == true)
		{
			ManageCom = StrTool.unicodeToGBK(ManageCom);
		}
		return ManageCom;
	}
	/** 公司机构编码 */
	public void setManageCom(String aManageCom)
	{
		ManageCom = aManageCom;
	}
	/** 银行网点代码<P>交易时间 */
	public String getNodeNo()
	{
		if (NodeNo != null && !NodeNo.equals("") && SysConst.CHANGECHARSET == true)
		{
			NodeNo = StrTool.unicodeToGBK(NodeNo);
		}
		return NodeNo;
	}
	/** 银行网点代码 */
	public void setNodeNo(String aNodeNo)
	{
		NodeNo = aNodeNo;
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
	/** 入库日期<P> */
	public String getMakeDate()
	{
		if( MakeDate != null )
			return fDate.getString(MakeDate);
		else
			return null;
	}
	/** 入库日期 */
	public void setMakeDate(Date aMakeDate)
	{
		MakeDate = aMakeDate;
	}
	/** 入库日期<P> */
	public void setMakeDate(String aMakeDate)
	{
		if (aMakeDate != null && !aMakeDate.equals("") )
		{
			MakeDate = fDate.getDate( aMakeDate );
		}
		else
			MakeDate = null;
	}

	/** 入库时间<P> */
	public String getMakeTime()
	{
		if (MakeTime != null && !MakeTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			MakeTime = StrTool.unicodeToGBK(MakeTime);
		}
		return MakeTime;
	}
	/** 入库时间 */
	public void setMakeTime(String aMakeTime)
	{
		MakeTime = aMakeTime;
	}
	/** 最后修改日期<P> */
	public String getModifyDate()
	{
		if( ModifyDate != null )
			return fDate.getString(ModifyDate);
		else
			return null;
	}
	/** 最后修改日期 */
	public void setModifyDate(Date aModifyDate)
	{
		ModifyDate = aModifyDate;
	}
	/** 最后修改日期<P> */
	public void setModifyDate(String aModifyDate)
	{
		if (aModifyDate != null && !aModifyDate.equals("") )
		{
			ModifyDate = fDate.getDate( aModifyDate );
		}
		else
			ModifyDate = null;
	}

	/** 最后修改时间<P> */
	public String getModifyTime()
	{
		if (ModifyTime != null && !ModifyTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			ModifyTime = StrTool.unicodeToGBK(ModifyTime);
		}
		return ModifyTime;
	}
	/** 最后修改时间 */
	public void setModifyTime(String aModifyTime)
	{
		ModifyTime = aModifyTime;
	}
	/** 同步日期<P> */
	public String getSynchroDate()
	{
		if( SynchroDate != null )
			return fDate.getString(SynchroDate);
		else
			return null;
	}
	/** 同步日期 */
	public void setSynchroDate(Date aSynchroDate)
	{
		SynchroDate = aSynchroDate;
	}
	/** 同步日期<P> */
	public void setSynchroDate(String aSynchroDate)
	{
		if (aSynchroDate != null && !aSynchroDate.equals("") )
		{
			SynchroDate = fDate.getDate( aSynchroDate );
		}
		else
			SynchroDate = null;
	}

	/** 同步时间<P> */
	public String getSynchroTime()
	{
		if (SynchroTime != null && !SynchroTime.equals("") && SysConst.CHANGECHARSET == true)
		{
			SynchroTime = StrTool.unicodeToGBK(SynchroTime);
		}
		return SynchroTime;
	}
	/** 同步时间 */
	public void setSynchroTime(String aSynchroTime)
	{
		SynchroTime = aSynchroTime;
	}

	/**
	* 使用另外一个 LdTellerSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LdTellerSchema aLdTellerSchema)
	{
		this.SRSTellerNo = aLdTellerSchema.getSRSTellerNo();
		this.TranCom = aLdTellerSchema.getTranCom();
		this.ZoneNo = aLdTellerSchema.getZoneNo();
		this.TellerNo = aLdTellerSchema.getTellerNo();
		this.TellerName = aLdTellerSchema.getTellerName();
		this.QuType = aLdTellerSchema.getQuType();
		this.TellerQuNo = aLdTellerSchema.getTellerQuNo();
		this.StartDate = fDate.getDate( aLdTellerSchema.getStartDate());
		this.EndDate = fDate.getDate( aLdTellerSchema.getEndDate());
		this.SellFlag = aLdTellerSchema.getSellFlag();
		this.ManageCom = aLdTellerSchema.getManageCom();
		this.NodeNo = aLdTellerSchema.getNodeNo();
		this.Bak1 = aLdTellerSchema.getBak1();
		this.Bak2 = aLdTellerSchema.getBak2();
		this.Bak3 = aLdTellerSchema.getBak3();
		this.Bak4 = aLdTellerSchema.getBak4();
		this.Bak5 = aLdTellerSchema.getBak5();
		this.MakeDate = fDate.getDate( aLdTellerSchema.getMakeDate());
		this.MakeTime = aLdTellerSchema.getMakeTime();
		this.ModifyDate = fDate.getDate( aLdTellerSchema.getModifyDate());
		this.ModifyTime = aLdTellerSchema.getModifyTime();
		this.SynchroDate = fDate.getDate( aLdTellerSchema.getSynchroDate());
		this.SynchroTime = aLdTellerSchema.getSynchroTime();
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
			if( rs.getString("SRSTellerNo") == null )
				this.SRSTellerNo = null;
			else
				this.SRSTellerNo = rs.getString("SRSTellerNo").trim();

			if( rs.getString("TranCom") == null )
				this.TranCom = null;
			else
				this.TranCom = rs.getString("TranCom").trim();

			if( rs.getString("ZoneNo") == null )
				this.ZoneNo = null;
			else
				this.ZoneNo = rs.getString("ZoneNo").trim();

			if( rs.getString("TellerNo") == null )
				this.TellerNo = null;
			else
				this.TellerNo = rs.getString("TellerNo").trim();

			if( rs.getString("TellerName") == null )
				this.TellerName = null;
			else
				this.TellerName = rs.getString("TellerName").trim();

			if( rs.getString("QuType") == null )
				this.QuType = null;
			else
				this.QuType = rs.getString("QuType").trim();

			if( rs.getString("TellerQuNo") == null )
				this.TellerQuNo = null;
			else
				this.TellerQuNo = rs.getString("TellerQuNo").trim();

			this.StartDate = rs.getDate("StartDate");
			this.EndDate = rs.getDate("EndDate");
			if( rs.getString("SellFlag") == null )
				this.SellFlag = null;
			else
				this.SellFlag = rs.getString("SellFlag").trim();

			if( rs.getString("ManageCom") == null )
				this.ManageCom = null;
			else
				this.ManageCom = rs.getString("ManageCom").trim();

			if( rs.getString("NodeNo") == null )
				this.NodeNo = null;
			else
				this.NodeNo = rs.getString("NodeNo").trim();

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

			this.SynchroDate = rs.getDate("SynchroDate");
			if( rs.getString("SynchroTime") == null )
				this.SynchroTime = null;
			else
				this.SynchroTime = rs.getString("SynchroTime").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LdTellerSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LdTellerSchema getSchema()
	{
		LdTellerSchema aLdTellerSchema = new LdTellerSchema();
		aLdTellerSchema.setSchema(this);
		return aLdTellerSchema;
	}

	public LdTellerDB getDB()
	{
		LdTellerDB aDBOper = new LdTellerDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLdTeller描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(SRSTellerNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TranCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ZoneNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TellerNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TellerName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(QuType) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TellerQuNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( StartDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( EndDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SellFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ManageCom) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NodeNo) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak4) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(Bak5) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( MakeDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MakeTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( ModifyDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ModifyTime) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(fDate.getString( SynchroDate )) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(SynchroTime) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLdTeller>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			SRSTellerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			TranCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			ZoneNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			TellerNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			TellerName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			QuType = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			TellerQuNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			StartDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8,SysConst.PACKAGESPILTER));
			EndDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9,SysConst.PACKAGESPILTER));
			SellFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			ManageCom = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			NodeNo = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			Bak1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			Bak2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			Bak3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			Bak4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			Bak5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			MakeDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18,SysConst.PACKAGESPILTER));
			MakeTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			ModifyDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20,SysConst.PACKAGESPILTER));
			ModifyTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			SynchroDate = fDate.getDate(StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22,SysConst.PACKAGESPILTER));
			SynchroTime = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LdTellerSchema";
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
		if (FCode.equals("SRSTellerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SRSTellerNo));
		}
		if (FCode.equals("TranCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TranCom));
		}
		if (FCode.equals("ZoneNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ZoneNo));
		}
		if (FCode.equals("TellerNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TellerNo));
		}
		if (FCode.equals("TellerName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TellerName));
		}
		if (FCode.equals("QuType"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(QuType));
		}
		if (FCode.equals("TellerQuNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TellerQuNo));
		}
		if (FCode.equals("StartDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
		}
		if (FCode.equals("EndDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
		}
		if (FCode.equals("SellFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SellFlag));
		}
		if (FCode.equals("ManageCom"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ManageCom));
		}
		if (FCode.equals("NodeNo"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeNo));
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
		if (FCode.equals("SynchroDate"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf( this.getSynchroDate()));
		}
		if (FCode.equals("SynchroTime"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(SynchroTime));
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
				strFieldValue = StrTool.GBKToUnicode(SRSTellerNo);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TranCom);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(ZoneNo);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(TellerNo);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(TellerName);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(QuType);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(TellerQuNo);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getStartDate()));
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getEndDate()));
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(SellFlag);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(ManageCom);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(NodeNo);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(Bak1);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(Bak2);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(Bak3);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(Bak4);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(Bak5);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getMakeDate()));
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(MakeTime);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getModifyDate()));
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(ModifyTime);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(String.valueOf( this.getSynchroDate()));
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(SynchroTime);
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

		if (FCode.equals("SRSTellerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SRSTellerNo = FValue.trim();
			}
			else
				SRSTellerNo = null;
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
		if (FCode.equals("TellerNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TellerNo = FValue.trim();
			}
			else
				TellerNo = null;
		}
		if (FCode.equals("TellerName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TellerName = FValue.trim();
			}
			else
				TellerName = null;
		}
		if (FCode.equals("QuType"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				QuType = FValue.trim();
			}
			else
				QuType = null;
		}
		if (FCode.equals("TellerQuNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TellerQuNo = FValue.trim();
			}
			else
				TellerQuNo = null;
		}
		if (FCode.equals("StartDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				StartDate = fDate.getDate( FValue );
			}
			else
				StartDate = null;
		}
		if (FCode.equals("EndDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				EndDate = fDate.getDate( FValue );
			}
			else
				EndDate = null;
		}
		if (FCode.equals("SellFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SellFlag = FValue.trim();
			}
			else
				SellFlag = null;
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
		if (FCode.equals("NodeNo"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeNo = FValue.trim();
			}
			else
				NodeNo = null;
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
		if (FCode.equals("SynchroDate"))
		{
			if( FValue != null && !FValue.equals("") )
			{
				SynchroDate = fDate.getDate( FValue );
			}
			else
				SynchroDate = null;
		}
		if (FCode.equals("SynchroTime"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				SynchroTime = FValue.trim();
			}
			else
				SynchroTime = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LdTellerSchema other = (LdTellerSchema)otherObject;
		return
			SRSTellerNo.equals(other.getSRSTellerNo())
			&& TranCom.equals(other.getTranCom())
			&& ZoneNo.equals(other.getZoneNo())
			&& TellerNo.equals(other.getTellerNo())
			&& TellerName.equals(other.getTellerName())
			&& QuType.equals(other.getQuType())
			&& TellerQuNo.equals(other.getTellerQuNo())
			&& fDate.getString(StartDate).equals(other.getStartDate())
			&& fDate.getString(EndDate).equals(other.getEndDate())
			&& SellFlag.equals(other.getSellFlag())
			&& ManageCom.equals(other.getManageCom())
			&& NodeNo.equals(other.getNodeNo())
			&& Bak1.equals(other.getBak1())
			&& Bak2.equals(other.getBak2())
			&& Bak3.equals(other.getBak3())
			&& Bak4.equals(other.getBak4())
			&& Bak5.equals(other.getBak5())
			&& fDate.getString(MakeDate).equals(other.getMakeDate())
			&& MakeTime.equals(other.getMakeTime())
			&& fDate.getString(ModifyDate).equals(other.getModifyDate())
			&& ModifyTime.equals(other.getModifyTime())
			&& fDate.getString(SynchroDate).equals(other.getSynchroDate())
			&& SynchroTime.equals(other.getSynchroTime());
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
		if( strFieldName.equals("SRSTellerNo") ) {
			return 0;
		}
		if( strFieldName.equals("TranCom") ) {
			return 1;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return 2;
		}
		if( strFieldName.equals("TellerNo") ) {
			return 3;
		}
		if( strFieldName.equals("TellerName") ) {
			return 4;
		}
		if( strFieldName.equals("QuType") ) {
			return 5;
		}
		if( strFieldName.equals("TellerQuNo") ) {
			return 6;
		}
		if( strFieldName.equals("StartDate") ) {
			return 7;
		}
		if( strFieldName.equals("EndDate") ) {
			return 8;
		}
		if( strFieldName.equals("SellFlag") ) {
			return 9;
		}
		if( strFieldName.equals("ManageCom") ) {
			return 10;
		}
		if( strFieldName.equals("NodeNo") ) {
			return 11;
		}
		if( strFieldName.equals("Bak1") ) {
			return 12;
		}
		if( strFieldName.equals("Bak2") ) {
			return 13;
		}
		if( strFieldName.equals("Bak3") ) {
			return 14;
		}
		if( strFieldName.equals("Bak4") ) {
			return 15;
		}
		if( strFieldName.equals("Bak5") ) {
			return 16;
		}
		if( strFieldName.equals("MakeDate") ) {
			return 17;
		}
		if( strFieldName.equals("MakeTime") ) {
			return 18;
		}
		if( strFieldName.equals("ModifyDate") ) {
			return 19;
		}
		if( strFieldName.equals("ModifyTime") ) {
			return 20;
		}
		if( strFieldName.equals("SynchroDate") ) {
			return 21;
		}
		if( strFieldName.equals("SynchroTime") ) {
			return 22;
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
				strFieldName = "SRSTellerNo";
				break;
			case 1:
				strFieldName = "TranCom";
				break;
			case 2:
				strFieldName = "ZoneNo";
				break;
			case 3:
				strFieldName = "TellerNo";
				break;
			case 4:
				strFieldName = "TellerName";
				break;
			case 5:
				strFieldName = "QuType";
				break;
			case 6:
				strFieldName = "TellerQuNo";
				break;
			case 7:
				strFieldName = "StartDate";
				break;
			case 8:
				strFieldName = "EndDate";
				break;
			case 9:
				strFieldName = "SellFlag";
				break;
			case 10:
				strFieldName = "ManageCom";
				break;
			case 11:
				strFieldName = "NodeNo";
				break;
			case 12:
				strFieldName = "Bak1";
				break;
			case 13:
				strFieldName = "Bak2";
				break;
			case 14:
				strFieldName = "Bak3";
				break;
			case 15:
				strFieldName = "Bak4";
				break;
			case 16:
				strFieldName = "Bak5";
				break;
			case 17:
				strFieldName = "MakeDate";
				break;
			case 18:
				strFieldName = "MakeTime";
				break;
			case 19:
				strFieldName = "ModifyDate";
				break;
			case 20:
				strFieldName = "ModifyTime";
				break;
			case 21:
				strFieldName = "SynchroDate";
				break;
			case 22:
				strFieldName = "SynchroTime";
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
		if( strFieldName.equals("SRSTellerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TranCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ZoneNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TellerNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TellerName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("QuType") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TellerQuNo") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("StartDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("EndDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SellFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ManageCom") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeNo") ) {
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
		if( strFieldName.equals("SynchroDate") ) {
			return Schema.TYPE_DATE;
		}
		if( strFieldName.equals("SynchroTime") ) {
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 8:
				nFieldType = Schema.TYPE_DATE;
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
				nFieldType = Schema.TYPE_DATE;
				break;
			case 18:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 19:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 20:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 21:
				nFieldType = Schema.TYPE_DATE;
				break;
			case 22:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
