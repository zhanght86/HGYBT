/*
 * <p>ClassName: RESCUESchema </p>
 * <p>Description: DB层 Schema 类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: rescue
 * @CreateDate：2014-08-05
 */
package com.sinosoft.lis.schema;

import java.sql.*;
import java.util.Date;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.RESCUEDB;

public class RESCUESchema implements Schema
{
	// @Field
	/** Rescueno */
	private int RESCUENO;
	/** Trancom */
	private String TRANCOM;
	/** Funcflag */
	private String FUNCFLAG;
	/** Tranno */
	private String TRANNO;
	/** Contno */
	private String CONTNO;
	/** Proposalprtno */
	private String PROPOSALPRTNO;
	/** Applycode */
	private String APPLYCODE;
	/** Result */
	private String RESULT;
	/** Type */
	private String TYPE;
	/** State */
	private String STATE;
	/** Recode */
	private String RECODE;
	/** Nodeno */
	private String NODENO;
	/** Operator */
	private String OPERATOR;
	/** Applydate */
	private String APPLYDATE;
	/** Validatedate */
	private String VALIDATEDATE;
	/** Trandate */
	private String TRANDATE;
	/** Trantime */
	private String TRANTIME;
	/** Agentcom */
	private String AGENTCOM;
	/** Agentcode */
	private String AGENTCODE;
	/** Managecom */
	private String MANAGECOM;
	/** Appntname */
	private String APPNTNAME;
	/** Appntidcode */
	private String APPNTIDCODE;
	/** Appntaccount */
	private String APPNTACCOUNT;
	/** Productid */
	private String PRODUCTID;
	/** Prem */
	private double PREM;
	/** Amnt */
	private double AMNT;
	/** Bak1 */
	private String BAK1;
	/** Bak2 */
	private String BAK2;
	/** Bak3 */
	private String BAK3;
	/** Bak4 */
	private String BAK4;
	/** Bak5 */
	private String BAK5;
	/** Bak6 */
	private String BAK6;
	/** Bak7 */
	private String BAK7;
	/** Bak8 */
	private String BAK8;
	/** Bak9 */
	private String BAK9;
	/** Bak10 */
	private String BAK10;
	/** Makedate */
	private String MAKEDATE;
	/** Maketime */
	private String MAKETIME;
	/** Modifydate */
	private String MODIFYDATE;
	/** Modifytime */
	private String MODIFYTIME;

	public static final int FIELDNUM = 40;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public RESCUESchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "RESCUENO";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** Rescueno<P>序列号 */
	public int getRESCUENO()
	{
		return RESCUENO;
	}
	/** Rescueno */
	public void setRESCUENO(int aRESCUENO)
	{
		RESCUENO = aRESCUENO;
	}
	/** Rescueno<P>序列号 */
	public void setRESCUENO(String aRESCUENO)
	{
		if (aRESCUENO != null && !aRESCUENO.equals(""))
		{
			Integer tInteger = new Integer(aRESCUENO);
			int i = tInteger.intValue();
			RESCUENO = i;
		}
	}

	/** Trancom<P>银行代码 */
	public String getTRANCOM()
	{
		if (TRANCOM != null && !TRANCOM.equals("") && SysConst.CHANGECHARSET == true)
		{
			TRANCOM = StrTool.unicodeToGBK(TRANCOM);
		}
		return TRANCOM;
	}
	/** Trancom */
	public void setTRANCOM(String aTRANCOM)
	{
		TRANCOM = aTRANCOM;
	}
	/** Funcflag<P>交易码 */
	public String getFUNCFLAG()
	{
		if (FUNCFLAG != null && !FUNCFLAG.equals("") && SysConst.CHANGECHARSET == true)
		{
			FUNCFLAG = StrTool.unicodeToGBK(FUNCFLAG);
		}
		return FUNCFLAG;
	}
	/** Funcflag */
	public void setFUNCFLAG(String aFUNCFLAG)
	{
		FUNCFLAG = aFUNCFLAG;
	}
	/** Tranno<P>交易流水号 */
	public String getTRANNO()
	{
		if (TRANNO != null && !TRANNO.equals("") && SysConst.CHANGECHARSET == true)
		{
			TRANNO = StrTool.unicodeToGBK(TRANNO);
		}
		return TRANNO;
	}
	/** Tranno */
	public void setTRANNO(String aTRANNO)
	{
		TRANNO = aTRANNO;
	}
	/** Contno<P>保单号 */
	public String getCONTNO()
	{
		if (CONTNO != null && !CONTNO.equals("") && SysConst.CHANGECHARSET == true)
		{
			CONTNO = StrTool.unicodeToGBK(CONTNO);
		}
		return CONTNO;
	}
	/** Contno */
	public void setCONTNO(String aCONTNO)
	{
		CONTNO = aCONTNO;
	}
	/** Proposalprtno<P>投保单号 */
	public String getPROPOSALPRTNO()
	{
		if (PROPOSALPRTNO != null && !PROPOSALPRTNO.equals("") && SysConst.CHANGECHARSET == true)
		{
			PROPOSALPRTNO = StrTool.unicodeToGBK(PROPOSALPRTNO);
		}
		return PROPOSALPRTNO;
	}
	/** Proposalprtno */
	public void setPROPOSALPRTNO(String aPROPOSALPRTNO)
	{
		PROPOSALPRTNO = aPROPOSALPRTNO;
	}
	/** Applycode<P>核心返回的唯一申请编号 */
	public String getAPPLYCODE()
	{
		if (APPLYCODE != null && !APPLYCODE.equals("") && SysConst.CHANGECHARSET == true)
		{
			APPLYCODE = StrTool.unicodeToGBK(APPLYCODE);
		}
		return APPLYCODE;
	}
	/** Applycode */
	public void setAPPLYCODE(String aAPPLYCODE)
	{
		APPLYCODE = aAPPLYCODE;
	}
	/** Result<P>是否对过账，1为是，0或者null为否 */
	public String getRESULT()
	{
		if (RESULT != null && !RESULT.equals("") && SysConst.CHANGECHARSET == true)
		{
			RESULT = StrTool.unicodeToGBK(RESULT);
		}
		return RESULT;
	}
	/** Result */
	public void setRESULT(String aRESULT)
	{
		RESULT = aRESULT;
	}
	/** Type<P>交易划分：1新单，2续期，3保全 */
	public String getTYPE()
	{
		if (TYPE != null && !TYPE.equals("") && SysConst.CHANGECHARSET == true)
		{
			TYPE = StrTool.unicodeToGBK(TYPE);
		}
		return TYPE;
	}
	/** Type */
	public void setTYPE(String aTYPE)
	{
		TYPE = aTYPE;
	}
	/** State<P>保全状态：2为生效，3为撤销 */
	public String getSTATE()
	{
		if (STATE != null && !STATE.equals("") && SysConst.CHANGECHARSET == true)
		{
			STATE = StrTool.unicodeToGBK(STATE);
		}
		return STATE;
	}
	/** State */
	public void setSTATE(String aSTATE)
	{
		STATE = aSTATE;
	}
	/** Recode<P>对账是否成功：1成功，2失败 */
	public String getRECODE()
	{
		if (RECODE != null && !RECODE.equals("") && SysConst.CHANGECHARSET == true)
		{
			RECODE = StrTool.unicodeToGBK(RECODE);
		}
		return RECODE;
	}
	/** Recode */
	public void setRECODE(String aRECODE)
	{
		RECODE = aRECODE;
	}
	/** Nodeno<P>网点号 */
	public String getNODENO()
	{
		if (NODENO != null && !NODENO.equals("") && SysConst.CHANGECHARSET == true)
		{
			NODENO = StrTool.unicodeToGBK(NODENO);
		}
		return NODENO;
	}
	/** Nodeno */
	public void setNODENO(String aNODENO)
	{
		NODENO = aNODENO;
	}
	/** Operator<P>操作人 */
	public String getOPERATOR()
	{
		if (OPERATOR != null && !OPERATOR.equals("") && SysConst.CHANGECHARSET == true)
		{
			OPERATOR = StrTool.unicodeToGBK(OPERATOR);
		}
		return OPERATOR;
	}
	/** Operator */
	public void setOPERATOR(String aOPERATOR)
	{
		OPERATOR = aOPERATOR;
	}
	/** Applydate<P>保全申请日期 */
	public String getAPPLYDATE()
	{
		if (APPLYDATE != null && !APPLYDATE.equals("") && SysConst.CHANGECHARSET == true)
		{
			APPLYDATE = StrTool.unicodeToGBK(APPLYDATE);
		}
		return APPLYDATE;
	}
	/** Applydate */
	public void setAPPLYDATE(String aAPPLYDATE)
	{
		APPLYDATE = aAPPLYDATE;
	}
	/** Validatedate<P>保全生效日期 */
	public String getVALIDATEDATE()
	{
		if (VALIDATEDATE != null && !VALIDATEDATE.equals("") && SysConst.CHANGECHARSET == true)
		{
			VALIDATEDATE = StrTool.unicodeToGBK(VALIDATEDATE);
		}
		return VALIDATEDATE;
	}
	/** Validatedate */
	public void setVALIDATEDATE(String aVALIDATEDATE)
	{
		VALIDATEDATE = aVALIDATEDATE;
	}
	/** Trandate<P>交易日期 */
	public String getTRANDATE()
	{
		if (TRANDATE != null && !TRANDATE.equals("") && SysConst.CHANGECHARSET == true)
		{
			TRANDATE = StrTool.unicodeToGBK(TRANDATE);
		}
		return TRANDATE;
	}
	/** Trandate */
	public void setTRANDATE(String aTRANDATE)
	{
		TRANDATE = aTRANDATE;
	}
	/** Trantime<P>交易时间 */
	public String getTRANTIME()
	{
		if (TRANTIME != null && !TRANTIME.equals("") && SysConst.CHANGECHARSET == true)
		{
			TRANTIME = StrTool.unicodeToGBK(TRANTIME);
		}
		return TRANTIME;
	}
	/** Trantime */
	public void setTRANTIME(String aTRANTIME)
	{
		TRANTIME = aTRANTIME;
	}
	/** Agentcom<P> */
	public String getAGENTCOM()
	{
		if (AGENTCOM != null && !AGENTCOM.equals("") && SysConst.CHANGECHARSET == true)
		{
			AGENTCOM = StrTool.unicodeToGBK(AGENTCOM);
		}
		return AGENTCOM;
	}
	/** Agentcom */
	public void setAGENTCOM(String aAGENTCOM)
	{
		AGENTCOM = aAGENTCOM;
	}
	/** Agentcode<P> */
	public String getAGENTCODE()
	{
		if (AGENTCODE != null && !AGENTCODE.equals("") && SysConst.CHANGECHARSET == true)
		{
			AGENTCODE = StrTool.unicodeToGBK(AGENTCODE);
		}
		return AGENTCODE;
	}
	/** Agentcode */
	public void setAGENTCODE(String aAGENTCODE)
	{
		AGENTCODE = aAGENTCODE;
	}
	/** Managecom<P>机构代码 */
	public String getMANAGECOM()
	{
		if (MANAGECOM != null && !MANAGECOM.equals("") && SysConst.CHANGECHARSET == true)
		{
			MANAGECOM = StrTool.unicodeToGBK(MANAGECOM);
		}
		return MANAGECOM;
	}
	/** Managecom */
	public void setMANAGECOM(String aMANAGECOM)
	{
		MANAGECOM = aMANAGECOM;
	}
	/** Appntname<P>申请人名称 */
	public String getAPPNTNAME()
	{
		if (APPNTNAME != null && !APPNTNAME.equals("") && SysConst.CHANGECHARSET == true)
		{
			APPNTNAME = StrTool.unicodeToGBK(APPNTNAME);
		}
		return APPNTNAME;
	}
	/** Appntname */
	public void setAPPNTNAME(String aAPPNTNAME)
	{
		APPNTNAME = aAPPNTNAME;
	}
	/** Appntidcode<P>申请人证件号码 */
	public String getAPPNTIDCODE()
	{
		if (APPNTIDCODE != null && !APPNTIDCODE.equals("") && SysConst.CHANGECHARSET == true)
		{
			APPNTIDCODE = StrTool.unicodeToGBK(APPNTIDCODE);
		}
		return APPNTIDCODE;
	}
	/** Appntidcode */
	public void setAPPNTIDCODE(String aAPPNTIDCODE)
	{
		APPNTIDCODE = aAPPNTIDCODE;
	}
	/** Appntaccount<P>申请人账户号 */
	public String getAPPNTACCOUNT()
	{
		if (APPNTACCOUNT != null && !APPNTACCOUNT.equals("") && SysConst.CHANGECHARSET == true)
		{
			APPNTACCOUNT = StrTool.unicodeToGBK(APPNTACCOUNT);
		}
		return APPNTACCOUNT;
	}
	/** Appntaccount */
	public void setAPPNTACCOUNT(String aAPPNTACCOUNT)
	{
		APPNTACCOUNT = aAPPNTACCOUNT;
	}
	/** Productid<P>主险代码 */
	public String getPRODUCTID()
	{
		if (PRODUCTID != null && !PRODUCTID.equals("") && SysConst.CHANGECHARSET == true)
		{
			PRODUCTID = StrTool.unicodeToGBK(PRODUCTID);
		}
		return PRODUCTID;
	}
	/** Productid */
	public void setPRODUCTID(String aPRODUCTID)
	{
		PRODUCTID = aPRODUCTID;
	}
	/** Prem<P>保费 */
	public double getPREM()
	{
		return PREM;
	}
	/** Prem */
	public void setPREM(double aPREM)
	{
		PREM = aPREM;
	}
	/** Prem<P>保费 */
	public void setPREM(String aPREM)
	{
		if (aPREM != null && !aPREM.equals(""))
		{
			Double tDouble = new Double(aPREM);
			double d = tDouble.doubleValue();
			PREM = d;
		}
	}

	/** Amnt<P>保额 */
	public double getAMNT()
	{
		return AMNT;
	}
	/** Amnt */
	public void setAMNT(double aAMNT)
	{
		AMNT = aAMNT;
	}
	/** Amnt<P>保额 */
	public void setAMNT(String aAMNT)
	{
		if (aAMNT != null && !aAMNT.equals(""))
		{
			Double tDouble = new Double(aAMNT);
			double d = tDouble.doubleValue();
			AMNT = d;
		}
	}

	/** Bak1<P> */
	public String getBAK1()
	{
		if (BAK1 != null && !BAK1.equals("") && SysConst.CHANGECHARSET == true)
		{
			BAK1 = StrTool.unicodeToGBK(BAK1);
		}
		return BAK1;
	}
	/** Bak1 */
	public void setBAK1(String aBAK1)
	{
		BAK1 = aBAK1;
	}
	/** Bak2<P> */
	public String getBAK2()
	{
		if (BAK2 != null && !BAK2.equals("") && SysConst.CHANGECHARSET == true)
		{
			BAK2 = StrTool.unicodeToGBK(BAK2);
		}
		return BAK2;
	}
	/** Bak2 */
	public void setBAK2(String aBAK2)
	{
		BAK2 = aBAK2;
	}
	/** Bak3<P> */
	public String getBAK3()
	{
		if (BAK3 != null && !BAK3.equals("") && SysConst.CHANGECHARSET == true)
		{
			BAK3 = StrTool.unicodeToGBK(BAK3);
		}
		return BAK3;
	}
	/** Bak3 */
	public void setBAK3(String aBAK3)
	{
		BAK3 = aBAK3;
	}
	/** Bak4<P> */
	public String getBAK4()
	{
		if (BAK4 != null && !BAK4.equals("") && SysConst.CHANGECHARSET == true)
		{
			BAK4 = StrTool.unicodeToGBK(BAK4);
		}
		return BAK4;
	}
	/** Bak4 */
	public void setBAK4(String aBAK4)
	{
		BAK4 = aBAK4;
	}
	/** Bak5<P> */
	public String getBAK5()
	{
		if (BAK5 != null && !BAK5.equals("") && SysConst.CHANGECHARSET == true)
		{
			BAK5 = StrTool.unicodeToGBK(BAK5);
		}
		return BAK5;
	}
	/** Bak5 */
	public void setBAK5(String aBAK5)
	{
		BAK5 = aBAK5;
	}
	/** Bak6<P> */
	public String getBAK6()
	{
		if (BAK6 != null && !BAK6.equals("") && SysConst.CHANGECHARSET == true)
		{
			BAK6 = StrTool.unicodeToGBK(BAK6);
		}
		return BAK6;
	}
	/** Bak6 */
	public void setBAK6(String aBAK6)
	{
		BAK6 = aBAK6;
	}
	/** Bak7<P> */
	public String getBAK7()
	{
		if (BAK7 != null && !BAK7.equals("") && SysConst.CHANGECHARSET == true)
		{
			BAK7 = StrTool.unicodeToGBK(BAK7);
		}
		return BAK7;
	}
	/** Bak7 */
	public void setBAK7(String aBAK7)
	{
		BAK7 = aBAK7;
	}
	/** Bak8<P> */
	public String getBAK8()
	{
		if (BAK8 != null && !BAK8.equals("") && SysConst.CHANGECHARSET == true)
		{
			BAK8 = StrTool.unicodeToGBK(BAK8);
		}
		return BAK8;
	}
	/** Bak8 */
	public void setBAK8(String aBAK8)
	{
		BAK8 = aBAK8;
	}
	/** Bak9<P> */
	public String getBAK9()
	{
		if (BAK9 != null && !BAK9.equals("") && SysConst.CHANGECHARSET == true)
		{
			BAK9 = StrTool.unicodeToGBK(BAK9);
		}
		return BAK9;
	}
	/** Bak9 */
	public void setBAK9(String aBAK9)
	{
		BAK9 = aBAK9;
	}
	/** Bak10<P> */
	public String getBAK10()
	{
		if (BAK10 != null && !BAK10.equals("") && SysConst.CHANGECHARSET == true)
		{
			BAK10 = StrTool.unicodeToGBK(BAK10);
		}
		return BAK10;
	}
	/** Bak10 */
	public void setBAK10(String aBAK10)
	{
		BAK10 = aBAK10;
	}
	/** Makedate<P> */
	public String getMAKEDATE()
	{
		if (MAKEDATE != null && !MAKEDATE.equals("") && SysConst.CHANGECHARSET == true)
		{
			MAKEDATE = StrTool.unicodeToGBK(MAKEDATE);
		}
		return MAKEDATE;
	}
	/** Makedate */
	public void setMAKEDATE(String aMAKEDATE)
	{
		MAKEDATE = aMAKEDATE;
	}
	/** Maketime<P> */
	public String getMAKETIME()
	{
		if (MAKETIME != null && !MAKETIME.equals("") && SysConst.CHANGECHARSET == true)
		{
			MAKETIME = StrTool.unicodeToGBK(MAKETIME);
		}
		return MAKETIME;
	}
	/** Maketime */
	public void setMAKETIME(String aMAKETIME)
	{
		MAKETIME = aMAKETIME;
	}
	/** Modifydate<P> */
	public String getMODIFYDATE()
	{
		if (MODIFYDATE != null && !MODIFYDATE.equals("") && SysConst.CHANGECHARSET == true)
		{
			MODIFYDATE = StrTool.unicodeToGBK(MODIFYDATE);
		}
		return MODIFYDATE;
	}
	/** Modifydate */
	public void setMODIFYDATE(String aMODIFYDATE)
	{
		MODIFYDATE = aMODIFYDATE;
	}
	/** Modifytime<P> */
	public String getMODIFYTIME()
	{
		if (MODIFYTIME != null && !MODIFYTIME.equals("") && SysConst.CHANGECHARSET == true)
		{
			MODIFYTIME = StrTool.unicodeToGBK(MODIFYTIME);
		}
		return MODIFYTIME;
	}
	/** Modifytime */
	public void setMODIFYTIME(String aMODIFYTIME)
	{
		MODIFYTIME = aMODIFYTIME;
	}

	/**
	* 使用另外一个 RESCUESchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(RESCUESchema aRESCUESchema)
	{
		this.RESCUENO = aRESCUESchema.getRESCUENO();
		this.TRANCOM = aRESCUESchema.getTRANCOM();
		this.FUNCFLAG = aRESCUESchema.getFUNCFLAG();
		this.TRANNO = aRESCUESchema.getTRANNO();
		this.CONTNO = aRESCUESchema.getCONTNO();
		this.PROPOSALPRTNO = aRESCUESchema.getPROPOSALPRTNO();
		this.APPLYCODE = aRESCUESchema.getAPPLYCODE();
		this.RESULT = aRESCUESchema.getRESULT();
		this.TYPE = aRESCUESchema.getTYPE();
		this.STATE = aRESCUESchema.getSTATE();
		this.RECODE = aRESCUESchema.getRECODE();
		this.NODENO = aRESCUESchema.getNODENO();
		this.OPERATOR = aRESCUESchema.getOPERATOR();
		this.APPLYDATE = aRESCUESchema.getAPPLYDATE();
		this.VALIDATEDATE = aRESCUESchema.getVALIDATEDATE();
		this.TRANDATE = aRESCUESchema.getTRANDATE();
		this.TRANTIME = aRESCUESchema.getTRANTIME();
		this.AGENTCOM = aRESCUESchema.getAGENTCOM();
		this.AGENTCODE = aRESCUESchema.getAGENTCODE();
		this.MANAGECOM = aRESCUESchema.getMANAGECOM();
		this.APPNTNAME = aRESCUESchema.getAPPNTNAME();
		this.APPNTIDCODE = aRESCUESchema.getAPPNTIDCODE();
		this.APPNTACCOUNT = aRESCUESchema.getAPPNTACCOUNT();
		this.PRODUCTID = aRESCUESchema.getPRODUCTID();
		this.PREM = aRESCUESchema.getPREM();
		this.AMNT = aRESCUESchema.getAMNT();
		this.BAK1 = aRESCUESchema.getBAK1();
		this.BAK2 = aRESCUESchema.getBAK2();
		this.BAK3 = aRESCUESchema.getBAK3();
		this.BAK4 = aRESCUESchema.getBAK4();
		this.BAK5 = aRESCUESchema.getBAK5();
		this.BAK6 = aRESCUESchema.getBAK6();
		this.BAK7 = aRESCUESchema.getBAK7();
		this.BAK8 = aRESCUESchema.getBAK8();
		this.BAK9 = aRESCUESchema.getBAK9();
		this.BAK10 = aRESCUESchema.getBAK10();
		this.MAKEDATE = aRESCUESchema.getMAKEDATE();
		this.MAKETIME = aRESCUESchema.getMAKETIME();
		this.MODIFYDATE = aRESCUESchema.getMODIFYDATE();
		this.MODIFYTIME = aRESCUESchema.getMODIFYTIME();
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
			this.RESCUENO = rs.getInt("RESCUENO");
			if( rs.getString("TRANCOM") == null )
				this.TRANCOM = null;
			else
				this.TRANCOM = rs.getString("TRANCOM").trim();

			if( rs.getString("FUNCFLAG") == null )
				this.FUNCFLAG = null;
			else
				this.FUNCFLAG = rs.getString("FUNCFLAG").trim();

			if( rs.getString("TRANNO") == null )
				this.TRANNO = null;
			else
				this.TRANNO = rs.getString("TRANNO").trim();

			if( rs.getString("CONTNO") == null )
				this.CONTNO = null;
			else
				this.CONTNO = rs.getString("CONTNO").trim();

			if( rs.getString("PROPOSALPRTNO") == null )
				this.PROPOSALPRTNO = null;
			else
				this.PROPOSALPRTNO = rs.getString("PROPOSALPRTNO").trim();

			if( rs.getString("APPLYCODE") == null )
				this.APPLYCODE = null;
			else
				this.APPLYCODE = rs.getString("APPLYCODE").trim();

			if( rs.getString("RESULT") == null )
				this.RESULT = null;
			else
				this.RESULT = rs.getString("RESULT").trim();

			if( rs.getString("TYPE") == null )
				this.TYPE = null;
			else
				this.TYPE = rs.getString("TYPE").trim();

			if( rs.getString("STATE") == null )
				this.STATE = null;
			else
				this.STATE = rs.getString("STATE").trim();

			if( rs.getString("RECODE") == null )
				this.RECODE = null;
			else
				this.RECODE = rs.getString("RECODE").trim();

			if( rs.getString("NODENO") == null )
				this.NODENO = null;
			else
				this.NODENO = rs.getString("NODENO").trim();

			if( rs.getString("OPERATOR") == null )
				this.OPERATOR = null;
			else
				this.OPERATOR = rs.getString("OPERATOR").trim();

			if( rs.getString("APPLYDATE") == null )
				this.APPLYDATE = null;
			else
				this.APPLYDATE = rs.getString("APPLYDATE").trim();

			if( rs.getString("VALIDATEDATE") == null )
				this.VALIDATEDATE = null;
			else
				this.VALIDATEDATE = rs.getString("VALIDATEDATE").trim();

			if( rs.getString("TRANDATE") == null )
				this.TRANDATE = null;
			else
				this.TRANDATE = rs.getString("TRANDATE").trim();

			if( rs.getString("TRANTIME") == null )
				this.TRANTIME = null;
			else
				this.TRANTIME = rs.getString("TRANTIME").trim();

			if( rs.getString("AGENTCOM") == null )
				this.AGENTCOM = null;
			else
				this.AGENTCOM = rs.getString("AGENTCOM").trim();

			if( rs.getString("AGENTCODE") == null )
				this.AGENTCODE = null;
			else
				this.AGENTCODE = rs.getString("AGENTCODE").trim();

			if( rs.getString("MANAGECOM") == null )
				this.MANAGECOM = null;
			else
				this.MANAGECOM = rs.getString("MANAGECOM").trim();

			if( rs.getString("APPNTNAME") == null )
				this.APPNTNAME = null;
			else
				this.APPNTNAME = rs.getString("APPNTNAME").trim();

			if( rs.getString("APPNTIDCODE") == null )
				this.APPNTIDCODE = null;
			else
				this.APPNTIDCODE = rs.getString("APPNTIDCODE").trim();

			if( rs.getString("APPNTACCOUNT") == null )
				this.APPNTACCOUNT = null;
			else
				this.APPNTACCOUNT = rs.getString("APPNTACCOUNT").trim();

			if( rs.getString("PRODUCTID") == null )
				this.PRODUCTID = null;
			else
				this.PRODUCTID = rs.getString("PRODUCTID").trim();

			this.PREM = rs.getDouble("PREM");
			this.AMNT = rs.getDouble("AMNT");
			if( rs.getString("BAK1") == null )
				this.BAK1 = null;
			else
				this.BAK1 = rs.getString("BAK1").trim();

			if( rs.getString("BAK2") == null )
				this.BAK2 = null;
			else
				this.BAK2 = rs.getString("BAK2").trim();

			if( rs.getString("BAK3") == null )
				this.BAK3 = null;
			else
				this.BAK3 = rs.getString("BAK3").trim();

			if( rs.getString("BAK4") == null )
				this.BAK4 = null;
			else
				this.BAK4 = rs.getString("BAK4").trim();

			if( rs.getString("BAK5") == null )
				this.BAK5 = null;
			else
				this.BAK5 = rs.getString("BAK5").trim();

			if( rs.getString("BAK6") == null )
				this.BAK6 = null;
			else
				this.BAK6 = rs.getString("BAK6").trim();

			if( rs.getString("BAK7") == null )
				this.BAK7 = null;
			else
				this.BAK7 = rs.getString("BAK7").trim();

			if( rs.getString("BAK8") == null )
				this.BAK8 = null;
			else
				this.BAK8 = rs.getString("BAK8").trim();

			if( rs.getString("BAK9") == null )
				this.BAK9 = null;
			else
				this.BAK9 = rs.getString("BAK9").trim();

			if( rs.getString("BAK10") == null )
				this.BAK10 = null;
			else
				this.BAK10 = rs.getString("BAK10").trim();

			if( rs.getString("MAKEDATE") == null )
				this.MAKEDATE = null;
			else
				this.MAKEDATE = rs.getString("MAKEDATE").trim();

			if( rs.getString("MAKETIME") == null )
				this.MAKETIME = null;
			else
				this.MAKETIME = rs.getString("MAKETIME").trim();

			if( rs.getString("MODIFYDATE") == null )
				this.MODIFYDATE = null;
			else
				this.MODIFYDATE = rs.getString("MODIFYDATE").trim();

			if( rs.getString("MODIFYTIME") == null )
				this.MODIFYTIME = null;
			else
				this.MODIFYTIME = rs.getString("MODIFYTIME").trim();

		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RESCUESchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public RESCUESchema getSchema()
	{
		RESCUESchema aRESCUESchema = new RESCUESchema();
		aRESCUESchema.setSchema(this);
		return aRESCUESchema;
	}

	public RESCUEDB getDB()
	{
		RESCUEDB aDBOper = new RESCUEDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRESCUE描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn =  ChgData.chgData(RESCUENO) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TRANCOM) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(FUNCFLAG) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TRANNO) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(CONTNO) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PROPOSALPRTNO) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(APPLYCODE) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RESULT) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TYPE) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(STATE) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RECODE) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NODENO) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(OPERATOR) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(APPLYDATE) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(VALIDATEDATE) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TRANDATE) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(TRANTIME) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AGENTCOM) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(AGENTCODE) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MANAGECOM) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(APPNTNAME) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(APPNTIDCODE) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(APPNTACCOUNT) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(PRODUCTID) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(PREM) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(AMNT) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BAK1) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BAK2) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BAK3) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BAK4) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BAK5) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BAK6) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BAK7) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BAK8) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BAK9) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(BAK10) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MAKEDATE) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MAKETIME) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MODIFYDATE) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(MODIFYTIME) );
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpRESCUE>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			RESCUENO= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,1,SysConst.PACKAGESPILTER))).intValue();
			TRANCOM = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			FUNCFLAG = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			TRANNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			CONTNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			PROPOSALPRTNO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			APPLYCODE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			RESULT = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			TYPE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			STATE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 10, SysConst.PACKAGESPILTER );
			RECODE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 11, SysConst.PACKAGESPILTER );
			NODENO = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 12, SysConst.PACKAGESPILTER );
			OPERATOR = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 13, SysConst.PACKAGESPILTER );
			APPLYDATE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 14, SysConst.PACKAGESPILTER );
			VALIDATEDATE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 15, SysConst.PACKAGESPILTER );
			TRANDATE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 16, SysConst.PACKAGESPILTER );
			TRANTIME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 17, SysConst.PACKAGESPILTER );
			AGENTCOM = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 18, SysConst.PACKAGESPILTER );
			AGENTCODE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 19, SysConst.PACKAGESPILTER );
			MANAGECOM = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 20, SysConst.PACKAGESPILTER );
			APPNTNAME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 21, SysConst.PACKAGESPILTER );
			APPNTIDCODE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 22, SysConst.PACKAGESPILTER );
			APPNTACCOUNT = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 23, SysConst.PACKAGESPILTER );
			PRODUCTID = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 24, SysConst.PACKAGESPILTER );
			PREM = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,25,SysConst.PACKAGESPILTER))).doubleValue();
			AMNT = new Double(ChgData.chgNumericStr(StrTool.getStr(strMessage,26,SysConst.PACKAGESPILTER))).doubleValue();
			BAK1 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 27, SysConst.PACKAGESPILTER );
			BAK2 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 28, SysConst.PACKAGESPILTER );
			BAK3 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 29, SysConst.PACKAGESPILTER );
			BAK4 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 30, SysConst.PACKAGESPILTER );
			BAK5 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 31, SysConst.PACKAGESPILTER );
			BAK6 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 32, SysConst.PACKAGESPILTER );
			BAK7 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 33, SysConst.PACKAGESPILTER );
			BAK8 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 34, SysConst.PACKAGESPILTER );
			BAK9 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 35, SysConst.PACKAGESPILTER );
			BAK10 = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 36, SysConst.PACKAGESPILTER );
			MAKEDATE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 37, SysConst.PACKAGESPILTER );
			MAKETIME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 38, SysConst.PACKAGESPILTER );
			MODIFYDATE = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 39, SysConst.PACKAGESPILTER );
			MODIFYTIME = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 40, SysConst.PACKAGESPILTER );
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "RESCUESchema";
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
		if (FCode.equals("RESCUENO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RESCUENO));
		}
		if (FCode.equals("TRANCOM"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TRANCOM));
		}
		if (FCode.equals("FUNCFLAG"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(FUNCFLAG));
		}
		if (FCode.equals("TRANNO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TRANNO));
		}
		if (FCode.equals("CONTNO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(CONTNO));
		}
		if (FCode.equals("PROPOSALPRTNO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PROPOSALPRTNO));
		}
		if (FCode.equals("APPLYCODE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(APPLYCODE));
		}
		if (FCode.equals("RESULT"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RESULT));
		}
		if (FCode.equals("TYPE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TYPE));
		}
		if (FCode.equals("STATE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(STATE));
		}
		if (FCode.equals("RECODE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RECODE));
		}
		if (FCode.equals("NODENO"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NODENO));
		}
		if (FCode.equals("OPERATOR"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(OPERATOR));
		}
		if (FCode.equals("APPLYDATE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(APPLYDATE));
		}
		if (FCode.equals("VALIDATEDATE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(VALIDATEDATE));
		}
		if (FCode.equals("TRANDATE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TRANDATE));
		}
		if (FCode.equals("TRANTIME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(TRANTIME));
		}
		if (FCode.equals("AGENTCOM"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AGENTCOM));
		}
		if (FCode.equals("AGENTCODE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AGENTCODE));
		}
		if (FCode.equals("MANAGECOM"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MANAGECOM));
		}
		if (FCode.equals("APPNTNAME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(APPNTNAME));
		}
		if (FCode.equals("APPNTIDCODE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(APPNTIDCODE));
		}
		if (FCode.equals("APPNTACCOUNT"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(APPNTACCOUNT));
		}
		if (FCode.equals("PRODUCTID"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PRODUCTID));
		}
		if (FCode.equals("PREM"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(PREM));
		}
		if (FCode.equals("AMNT"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(AMNT));
		}
		if (FCode.equals("BAK1"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BAK1));
		}
		if (FCode.equals("BAK2"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BAK2));
		}
		if (FCode.equals("BAK3"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BAK3));
		}
		if (FCode.equals("BAK4"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BAK4));
		}
		if (FCode.equals("BAK5"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BAK5));
		}
		if (FCode.equals("BAK6"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BAK6));
		}
		if (FCode.equals("BAK7"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BAK7));
		}
		if (FCode.equals("BAK8"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BAK8));
		}
		if (FCode.equals("BAK9"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BAK9));
		}
		if (FCode.equals("BAK10"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(BAK10));
		}
		if (FCode.equals("MAKEDATE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MAKEDATE));
		}
		if (FCode.equals("MAKETIME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MAKETIME));
		}
		if (FCode.equals("MODIFYDATE"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MODIFYDATE));
		}
		if (FCode.equals("MODIFYTIME"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(MODIFYTIME));
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
				strFieldValue = String.valueOf(RESCUENO);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(TRANCOM);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(FUNCFLAG);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(TRANNO);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(CONTNO);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(PROPOSALPRTNO);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(APPLYCODE);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(RESULT);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(TYPE);
				break;
			case 9:
				strFieldValue = StrTool.GBKToUnicode(STATE);
				break;
			case 10:
				strFieldValue = StrTool.GBKToUnicode(RECODE);
				break;
			case 11:
				strFieldValue = StrTool.GBKToUnicode(NODENO);
				break;
			case 12:
				strFieldValue = StrTool.GBKToUnicode(OPERATOR);
				break;
			case 13:
				strFieldValue = StrTool.GBKToUnicode(APPLYDATE);
				break;
			case 14:
				strFieldValue = StrTool.GBKToUnicode(VALIDATEDATE);
				break;
			case 15:
				strFieldValue = StrTool.GBKToUnicode(TRANDATE);
				break;
			case 16:
				strFieldValue = StrTool.GBKToUnicode(TRANTIME);
				break;
			case 17:
				strFieldValue = StrTool.GBKToUnicode(AGENTCOM);
				break;
			case 18:
				strFieldValue = StrTool.GBKToUnicode(AGENTCODE);
				break;
			case 19:
				strFieldValue = StrTool.GBKToUnicode(MANAGECOM);
				break;
			case 20:
				strFieldValue = StrTool.GBKToUnicode(APPNTNAME);
				break;
			case 21:
				strFieldValue = StrTool.GBKToUnicode(APPNTIDCODE);
				break;
			case 22:
				strFieldValue = StrTool.GBKToUnicode(APPNTACCOUNT);
				break;
			case 23:
				strFieldValue = StrTool.GBKToUnicode(PRODUCTID);
				break;
			case 24:
				strFieldValue = String.valueOf(PREM);
				break;
			case 25:
				strFieldValue = String.valueOf(AMNT);
				break;
			case 26:
				strFieldValue = StrTool.GBKToUnicode(BAK1);
				break;
			case 27:
				strFieldValue = StrTool.GBKToUnicode(BAK2);
				break;
			case 28:
				strFieldValue = StrTool.GBKToUnicode(BAK3);
				break;
			case 29:
				strFieldValue = StrTool.GBKToUnicode(BAK4);
				break;
			case 30:
				strFieldValue = StrTool.GBKToUnicode(BAK5);
				break;
			case 31:
				strFieldValue = StrTool.GBKToUnicode(BAK6);
				break;
			case 32:
				strFieldValue = StrTool.GBKToUnicode(BAK7);
				break;
			case 33:
				strFieldValue = StrTool.GBKToUnicode(BAK8);
				break;
			case 34:
				strFieldValue = StrTool.GBKToUnicode(BAK9);
				break;
			case 35:
				strFieldValue = StrTool.GBKToUnicode(BAK10);
				break;
			case 36:
				strFieldValue = StrTool.GBKToUnicode(MAKEDATE);
				break;
			case 37:
				strFieldValue = StrTool.GBKToUnicode(MAKETIME);
				break;
			case 38:
				strFieldValue = StrTool.GBKToUnicode(MODIFYDATE);
				break;
			case 39:
				strFieldValue = StrTool.GBKToUnicode(MODIFYTIME);
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

		if (FCode.equals("RESCUENO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				RESCUENO = i;
			}
		}
		if (FCode.equals("TRANCOM"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TRANCOM = FValue.trim();
			}
			else
				TRANCOM = null;
		}
		if (FCode.equals("FUNCFLAG"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				FUNCFLAG = FValue.trim();
			}
			else
				FUNCFLAG = null;
		}
		if (FCode.equals("TRANNO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TRANNO = FValue.trim();
			}
			else
				TRANNO = null;
		}
		if (FCode.equals("CONTNO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				CONTNO = FValue.trim();
			}
			else
				CONTNO = null;
		}
		if (FCode.equals("PROPOSALPRTNO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PROPOSALPRTNO = FValue.trim();
			}
			else
				PROPOSALPRTNO = null;
		}
		if (FCode.equals("APPLYCODE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				APPLYCODE = FValue.trim();
			}
			else
				APPLYCODE = null;
		}
		if (FCode.equals("RESULT"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RESULT = FValue.trim();
			}
			else
				RESULT = null;
		}
		if (FCode.equals("TYPE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TYPE = FValue.trim();
			}
			else
				TYPE = null;
		}
		if (FCode.equals("STATE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				STATE = FValue.trim();
			}
			else
				STATE = null;
		}
		if (FCode.equals("RECODE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RECODE = FValue.trim();
			}
			else
				RECODE = null;
		}
		if (FCode.equals("NODENO"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NODENO = FValue.trim();
			}
			else
				NODENO = null;
		}
		if (FCode.equals("OPERATOR"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				OPERATOR = FValue.trim();
			}
			else
				OPERATOR = null;
		}
		if (FCode.equals("APPLYDATE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				APPLYDATE = FValue.trim();
			}
			else
				APPLYDATE = null;
		}
		if (FCode.equals("VALIDATEDATE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				VALIDATEDATE = FValue.trim();
			}
			else
				VALIDATEDATE = null;
		}
		if (FCode.equals("TRANDATE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TRANDATE = FValue.trim();
			}
			else
				TRANDATE = null;
		}
		if (FCode.equals("TRANTIME"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				TRANTIME = FValue.trim();
			}
			else
				TRANTIME = null;
		}
		if (FCode.equals("AGENTCOM"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AGENTCOM = FValue.trim();
			}
			else
				AGENTCOM = null;
		}
		if (FCode.equals("AGENTCODE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				AGENTCODE = FValue.trim();
			}
			else
				AGENTCODE = null;
		}
		if (FCode.equals("MANAGECOM"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MANAGECOM = FValue.trim();
			}
			else
				MANAGECOM = null;
		}
		if (FCode.equals("APPNTNAME"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				APPNTNAME = FValue.trim();
			}
			else
				APPNTNAME = null;
		}
		if (FCode.equals("APPNTIDCODE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				APPNTIDCODE = FValue.trim();
			}
			else
				APPNTIDCODE = null;
		}
		if (FCode.equals("APPNTACCOUNT"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				APPNTACCOUNT = FValue.trim();
			}
			else
				APPNTACCOUNT = null;
		}
		if (FCode.equals("PRODUCTID"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				PRODUCTID = FValue.trim();
			}
			else
				PRODUCTID = null;
		}
		if (FCode.equals("PREM"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				PREM = d;
			}
		}
		if (FCode.equals("AMNT"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Double tDouble = new Double( FValue );
				double d = tDouble.doubleValue();
				AMNT = d;
			}
		}
		if (FCode.equals("BAK1"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BAK1 = FValue.trim();
			}
			else
				BAK1 = null;
		}
		if (FCode.equals("BAK2"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BAK2 = FValue.trim();
			}
			else
				BAK2 = null;
		}
		if (FCode.equals("BAK3"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BAK3 = FValue.trim();
			}
			else
				BAK3 = null;
		}
		if (FCode.equals("BAK4"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BAK4 = FValue.trim();
			}
			else
				BAK4 = null;
		}
		if (FCode.equals("BAK5"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BAK5 = FValue.trim();
			}
			else
				BAK5 = null;
		}
		if (FCode.equals("BAK6"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BAK6 = FValue.trim();
			}
			else
				BAK6 = null;
		}
		if (FCode.equals("BAK7"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BAK7 = FValue.trim();
			}
			else
				BAK7 = null;
		}
		if (FCode.equals("BAK8"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BAK8 = FValue.trim();
			}
			else
				BAK8 = null;
		}
		if (FCode.equals("BAK9"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BAK9 = FValue.trim();
			}
			else
				BAK9 = null;
		}
		if (FCode.equals("BAK10"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				BAK10 = FValue.trim();
			}
			else
				BAK10 = null;
		}
		if (FCode.equals("MAKEDATE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MAKEDATE = FValue.trim();
			}
			else
				MAKEDATE = null;
		}
		if (FCode.equals("MAKETIME"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MAKETIME = FValue.trim();
			}
			else
				MAKETIME = null;
		}
		if (FCode.equals("MODIFYDATE"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MODIFYDATE = FValue.trim();
			}
			else
				MODIFYDATE = null;
		}
		if (FCode.equals("MODIFYTIME"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				MODIFYTIME = FValue.trim();
			}
			else
				MODIFYTIME = null;
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		RESCUESchema other = (RESCUESchema)otherObject;
		return
			RESCUENO == other.getRESCUENO()
			&& TRANCOM.equals(other.getTRANCOM())
			&& FUNCFLAG.equals(other.getFUNCFLAG())
			&& TRANNO.equals(other.getTRANNO())
			&& CONTNO.equals(other.getCONTNO())
			&& PROPOSALPRTNO.equals(other.getPROPOSALPRTNO())
			&& APPLYCODE.equals(other.getAPPLYCODE())
			&& RESULT.equals(other.getRESULT())
			&& TYPE.equals(other.getTYPE())
			&& STATE.equals(other.getSTATE())
			&& RECODE.equals(other.getRECODE())
			&& NODENO.equals(other.getNODENO())
			&& OPERATOR.equals(other.getOPERATOR())
			&& APPLYDATE.equals(other.getAPPLYDATE())
			&& VALIDATEDATE.equals(other.getVALIDATEDATE())
			&& TRANDATE.equals(other.getTRANDATE())
			&& TRANTIME.equals(other.getTRANTIME())
			&& AGENTCOM.equals(other.getAGENTCOM())
			&& AGENTCODE.equals(other.getAGENTCODE())
			&& MANAGECOM.equals(other.getMANAGECOM())
			&& APPNTNAME.equals(other.getAPPNTNAME())
			&& APPNTIDCODE.equals(other.getAPPNTIDCODE())
			&& APPNTACCOUNT.equals(other.getAPPNTACCOUNT())
			&& PRODUCTID.equals(other.getPRODUCTID())
			&& PREM == other.getPREM()
			&& AMNT == other.getAMNT()
			&& BAK1.equals(other.getBAK1())
			&& BAK2.equals(other.getBAK2())
			&& BAK3.equals(other.getBAK3())
			&& BAK4.equals(other.getBAK4())
			&& BAK5.equals(other.getBAK5())
			&& BAK6.equals(other.getBAK6())
			&& BAK7.equals(other.getBAK7())
			&& BAK8.equals(other.getBAK8())
			&& BAK9.equals(other.getBAK9())
			&& BAK10.equals(other.getBAK10())
			&& MAKEDATE.equals(other.getMAKEDATE())
			&& MAKETIME.equals(other.getMAKETIME())
			&& MODIFYDATE.equals(other.getMODIFYDATE())
			&& MODIFYTIME.equals(other.getMODIFYTIME());
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
		if( strFieldName.equals("RESCUENO") ) {
			return 0;
		}
		if( strFieldName.equals("TRANCOM") ) {
			return 1;
		}
		if( strFieldName.equals("FUNCFLAG") ) {
			return 2;
		}
		if( strFieldName.equals("TRANNO") ) {
			return 3;
		}
		if( strFieldName.equals("CONTNO") ) {
			return 4;
		}
		if( strFieldName.equals("PROPOSALPRTNO") ) {
			return 5;
		}
		if( strFieldName.equals("APPLYCODE") ) {
			return 6;
		}
		if( strFieldName.equals("RESULT") ) {
			return 7;
		}
		if( strFieldName.equals("TYPE") ) {
			return 8;
		}
		if( strFieldName.equals("STATE") ) {
			return 9;
		}
		if( strFieldName.equals("RECODE") ) {
			return 10;
		}
		if( strFieldName.equals("NODENO") ) {
			return 11;
		}
		if( strFieldName.equals("OPERATOR") ) {
			return 12;
		}
		if( strFieldName.equals("APPLYDATE") ) {
			return 13;
		}
		if( strFieldName.equals("VALIDATEDATE") ) {
			return 14;
		}
		if( strFieldName.equals("TRANDATE") ) {
			return 15;
		}
		if( strFieldName.equals("TRANTIME") ) {
			return 16;
		}
		if( strFieldName.equals("AGENTCOM") ) {
			return 17;
		}
		if( strFieldName.equals("AGENTCODE") ) {
			return 18;
		}
		if( strFieldName.equals("MANAGECOM") ) {
			return 19;
		}
		if( strFieldName.equals("APPNTNAME") ) {
			return 20;
		}
		if( strFieldName.equals("APPNTIDCODE") ) {
			return 21;
		}
		if( strFieldName.equals("APPNTACCOUNT") ) {
			return 22;
		}
		if( strFieldName.equals("PRODUCTID") ) {
			return 23;
		}
		if( strFieldName.equals("PREM") ) {
			return 24;
		}
		if( strFieldName.equals("AMNT") ) {
			return 25;
		}
		if( strFieldName.equals("BAK1") ) {
			return 26;
		}
		if( strFieldName.equals("BAK2") ) {
			return 27;
		}
		if( strFieldName.equals("BAK3") ) {
			return 28;
		}
		if( strFieldName.equals("BAK4") ) {
			return 29;
		}
		if( strFieldName.equals("BAK5") ) {
			return 30;
		}
		if( strFieldName.equals("BAK6") ) {
			return 31;
		}
		if( strFieldName.equals("BAK7") ) {
			return 32;
		}
		if( strFieldName.equals("BAK8") ) {
			return 33;
		}
		if( strFieldName.equals("BAK9") ) {
			return 34;
		}
		if( strFieldName.equals("BAK10") ) {
			return 35;
		}
		if( strFieldName.equals("MAKEDATE") ) {
			return 36;
		}
		if( strFieldName.equals("MAKETIME") ) {
			return 37;
		}
		if( strFieldName.equals("MODIFYDATE") ) {
			return 38;
		}
		if( strFieldName.equals("MODIFYTIME") ) {
			return 39;
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
				strFieldName = "RESCUENO";
				break;
			case 1:
				strFieldName = "TRANCOM";
				break;
			case 2:
				strFieldName = "FUNCFLAG";
				break;
			case 3:
				strFieldName = "TRANNO";
				break;
			case 4:
				strFieldName = "CONTNO";
				break;
			case 5:
				strFieldName = "PROPOSALPRTNO";
				break;
			case 6:
				strFieldName = "APPLYCODE";
				break;
			case 7:
				strFieldName = "RESULT";
				break;
			case 8:
				strFieldName = "TYPE";
				break;
			case 9:
				strFieldName = "STATE";
				break;
			case 10:
				strFieldName = "RECODE";
				break;
			case 11:
				strFieldName = "NODENO";
				break;
			case 12:
				strFieldName = "OPERATOR";
				break;
			case 13:
				strFieldName = "APPLYDATE";
				break;
			case 14:
				strFieldName = "VALIDATEDATE";
				break;
			case 15:
				strFieldName = "TRANDATE";
				break;
			case 16:
				strFieldName = "TRANTIME";
				break;
			case 17:
				strFieldName = "AGENTCOM";
				break;
			case 18:
				strFieldName = "AGENTCODE";
				break;
			case 19:
				strFieldName = "MANAGECOM";
				break;
			case 20:
				strFieldName = "APPNTNAME";
				break;
			case 21:
				strFieldName = "APPNTIDCODE";
				break;
			case 22:
				strFieldName = "APPNTACCOUNT";
				break;
			case 23:
				strFieldName = "PRODUCTID";
				break;
			case 24:
				strFieldName = "PREM";
				break;
			case 25:
				strFieldName = "AMNT";
				break;
			case 26:
				strFieldName = "BAK1";
				break;
			case 27:
				strFieldName = "BAK2";
				break;
			case 28:
				strFieldName = "BAK3";
				break;
			case 29:
				strFieldName = "BAK4";
				break;
			case 30:
				strFieldName = "BAK5";
				break;
			case 31:
				strFieldName = "BAK6";
				break;
			case 32:
				strFieldName = "BAK7";
				break;
			case 33:
				strFieldName = "BAK8";
				break;
			case 34:
				strFieldName = "BAK9";
				break;
			case 35:
				strFieldName = "BAK10";
				break;
			case 36:
				strFieldName = "MAKEDATE";
				break;
			case 37:
				strFieldName = "MAKETIME";
				break;
			case 38:
				strFieldName = "MODIFYDATE";
				break;
			case 39:
				strFieldName = "MODIFYTIME";
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
		if( strFieldName.equals("RESCUENO") ) {
			return Schema.TYPE_INT;
		}
		if( strFieldName.equals("TRANCOM") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("FUNCFLAG") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TRANNO") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("CONTNO") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PROPOSALPRTNO") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("APPLYCODE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RESULT") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TYPE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("STATE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RECODE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NODENO") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("OPERATOR") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("APPLYDATE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("VALIDATEDATE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TRANDATE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("TRANTIME") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AGENTCOM") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("AGENTCODE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MANAGECOM") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("APPNTNAME") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("APPNTIDCODE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("APPNTACCOUNT") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PRODUCTID") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("PREM") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("AMNT") ) {
			return Schema.TYPE_DOUBLE;
		}
		if( strFieldName.equals("BAK1") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BAK2") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BAK3") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BAK4") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BAK5") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BAK6") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BAK7") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BAK8") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BAK9") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("BAK10") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MAKEDATE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MAKETIME") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MODIFYDATE") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("MODIFYTIME") ) {
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
				nFieldType = Schema.TYPE_STRING;
				break;
			case 24:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 25:
				nFieldType = Schema.TYPE_DOUBLE;
				break;
			case 26:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 27:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 28:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 29:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 30:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 31:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 32:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 33:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 34:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 35:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 36:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 37:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 38:
				nFieldType = Schema.TYPE_STRING;
				break;
			case 39:
				nFieldType = Schema.TYPE_STRING;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
