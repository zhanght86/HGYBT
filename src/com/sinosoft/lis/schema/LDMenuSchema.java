/*
 * <p>ClassName: LDMenuSchema </p>
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
import com.sinosoft.lis.db.LDMenuDB;

public class LDMenuSchema implements Schema
{
	// @Field
	/** 菜单编码 */
	private String NodeCode;
	/** 父菜单编码 */
	private String ParentNodeCode;
	/** 菜单层次 */
	private String NodeLevel;
	/** 菜单名称 */
	private String NodeName;
	/** 子菜单个数 */
	private String ChildFlag;
	/** 菜单热健 */
	private String NodeKey;
	/** 执行语句 */
	private String RunScript;
	/** 菜单节点说明 */
	private String NodeDescription;
	/** 菜单标志 */
	private String NodeSign;
	/** 菜单顺序号 */
	private int NodeOrder;

	public static final int FIELDNUM = 10;	// 数据库表的字段个数

	private static String[] PK;				// 主键

	private FDate fDate = new FDate();		// 处理日期

	public CErrors mErrors;			// 错误信息

	// @Constructor
	public LDMenuSchema()
	{
		mErrors = new CErrors();

		String[] pk = new String[1];
		pk[0] = "NodeCode";

		PK = pk;
	}

	// @Method
	public String[] getPK()
	{
		return PK;
	}

	/** 菜单编码<P>菜单编码 */
	public String getNodeCode()
	{
		if (NodeCode != null && !NodeCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			NodeCode = StrTool.unicodeToGBK(NodeCode);
		}
		return NodeCode;
	}
	/** 菜单编码 */
	public void setNodeCode(String aNodeCode)
	{
		NodeCode = aNodeCode;
	}
	/** 父菜单编码<P>父菜单编码 */
	public String getParentNodeCode()
	{
		if (ParentNodeCode != null && !ParentNodeCode.equals("") && SysConst.CHANGECHARSET == true)
		{
			ParentNodeCode = StrTool.unicodeToGBK(ParentNodeCode);
		}
		return ParentNodeCode;
	}
	/** 父菜单编码 */
	public void setParentNodeCode(String aParentNodeCode)
	{
		ParentNodeCode = aParentNodeCode;
	}
	/** 菜单层次<P>菜单层次 */
	public String getNodeLevel()
	{
		if (NodeLevel != null && !NodeLevel.equals("") && SysConst.CHANGECHARSET == true)
		{
			NodeLevel = StrTool.unicodeToGBK(NodeLevel);
		}
		return NodeLevel;
	}
	/** 菜单层次 */
	public void setNodeLevel(String aNodeLevel)
	{
		NodeLevel = aNodeLevel;
	}
	/** 菜单名称<P>菜单名称 */
	public String getNodeName()
	{
		if (NodeName != null && !NodeName.equals("") && SysConst.CHANGECHARSET == true)
		{
			NodeName = StrTool.unicodeToGBK(NodeName);
		}
		return NodeName;
	}
	/** 菜单名称 */
	public void setNodeName(String aNodeName)
	{
		NodeName = aNodeName;
	}
	/** 子菜单个数<P>子菜单个数 */
	public String getChildFlag()
	{
		if (ChildFlag != null && !ChildFlag.equals("") && SysConst.CHANGECHARSET == true)
		{
			ChildFlag = StrTool.unicodeToGBK(ChildFlag);
		}
		return ChildFlag;
	}
	/** 子菜单个数 */
	public void setChildFlag(String aChildFlag)
	{
		ChildFlag = aChildFlag;
	}
	/** 菜单热健<P>菜单热健 */
	public String getNodeKey()
	{
		if (NodeKey != null && !NodeKey.equals("") && SysConst.CHANGECHARSET == true)
		{
			NodeKey = StrTool.unicodeToGBK(NodeKey);
		}
		return NodeKey;
	}
	/** 菜单热健 */
	public void setNodeKey(String aNodeKey)
	{
		NodeKey = aNodeKey;
	}
	/** 执行语句<P>执行语句 */
	public String getRunScript()
	{
		if (RunScript != null && !RunScript.equals("") && SysConst.CHANGECHARSET == true)
		{
			RunScript = StrTool.unicodeToGBK(RunScript);
		}
		return RunScript;
	}
	/** 执行语句 */
	public void setRunScript(String aRunScript)
	{
		RunScript = aRunScript;
	}
	/** 菜单节点说明<P>菜单节点说明 */
	public String getNodeDescription()
	{
		if (NodeDescription != null && !NodeDescription.equals("") && SysConst.CHANGECHARSET == true)
		{
			NodeDescription = StrTool.unicodeToGBK(NodeDescription);
		}
		return NodeDescription;
	}
	/** 菜单节点说明 */
	public void setNodeDescription(String aNodeDescription)
	{
		NodeDescription = aNodeDescription;
	}
	/** 菜单标志<P>菜单标志 */
	public String getNodeSign()
	{
		if (NodeSign != null && !NodeSign.equals("") && SysConst.CHANGECHARSET == true)
		{
			NodeSign = StrTool.unicodeToGBK(NodeSign);
		}
		return NodeSign;
	}
	/** 菜单标志 */
	public void setNodeSign(String aNodeSign)
	{
		NodeSign = aNodeSign;
	}
	/** 菜单顺序号<P>菜单顺序号 */
	public int getNodeOrder()
	{
		return NodeOrder;
	}
	/** 菜单顺序号 */
	public void setNodeOrder(int aNodeOrder)
	{
		NodeOrder = aNodeOrder;
	}
	/** 菜单顺序号<P>菜单顺序号 */
	public void setNodeOrder(String aNodeOrder)
	{
		if (aNodeOrder != null && !aNodeOrder.equals(""))
		{
			Integer tInteger = new Integer(aNodeOrder);
			int i = tInteger.intValue();
			NodeOrder = i;
		}
	}


	/**
	* 使用另外一个 LDMenuSchema 对象给 Schema 赋值
	* @param: Schema 对象
	* @return: 无
	**/
	public void setSchema(LDMenuSchema aLDMenuSchema)
	{
		this.NodeCode = aLDMenuSchema.getNodeCode();
		this.ParentNodeCode = aLDMenuSchema.getParentNodeCode();
		this.NodeLevel = aLDMenuSchema.getNodeLevel();
		this.NodeName = aLDMenuSchema.getNodeName();
		this.ChildFlag = aLDMenuSchema.getChildFlag();
		this.NodeKey = aLDMenuSchema.getNodeKey();
		this.RunScript = aLDMenuSchema.getRunScript();
		this.NodeDescription = aLDMenuSchema.getNodeDescription();
		this.NodeSign = aLDMenuSchema.getNodeSign();
		this.NodeOrder = aLDMenuSchema.getNodeOrder();
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
			if( rs.getString("NodeCode") == null )
				this.NodeCode = null;
			else
				this.NodeCode = rs.getString("NodeCode").trim();

			if( rs.getString("ParentNodeCode") == null )
				this.ParentNodeCode = null;
			else
				this.ParentNodeCode = rs.getString("ParentNodeCode").trim();

			if( rs.getString("NodeLevel") == null )
				this.NodeLevel = null;
			else
				this.NodeLevel = rs.getString("NodeLevel").trim();

			if( rs.getString("NodeName") == null )
				this.NodeName = null;
			else
				this.NodeName = rs.getString("NodeName").trim();

			if( rs.getString("ChildFlag") == null )
				this.ChildFlag = null;
			else
				this.ChildFlag = rs.getString("ChildFlag").trim();

			if( rs.getString("NodeKey") == null )
				this.NodeKey = null;
			else
				this.NodeKey = rs.getString("NodeKey").trim();

			if( rs.getString("RunScript") == null )
				this.RunScript = null;
			else
				this.RunScript = rs.getString("RunScript").trim();

			if( rs.getString("NodeDescription") == null )
				this.NodeDescription = null;
			else
				this.NodeDescription = rs.getString("NodeDescription").trim();

			if( rs.getString("NodeSign") == null )
				this.NodeSign = null;
			else
				this.NodeSign = rs.getString("NodeSign").trim();

			this.NodeOrder = rs.getInt("NodeOrder");
		}
		catch(SQLException sqle)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMenuSchema";
			tError.functionName = "setSchema";
			tError.errorMessage = sqle.toString();
			this.mErrors .addOneError(tError);

			return false;
		}
		return true;
	}

	public LDMenuSchema getSchema()
	{
		LDMenuSchema aLDMenuSchema = new LDMenuSchema();
		aLDMenuSchema.setSchema(this);
		return aLDMenuSchema;
	}

	public LDMenuDB getDB()
	{
		LDMenuDB aDBOper = new LDMenuDB();
		aDBOper.setSchema(this);
		return aDBOper;
	}


	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMenu描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		strReturn = StrTool.cTrim( StrTool.unicodeToGBK(NodeCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ParentNodeCode) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NodeLevel) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NodeName) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(ChildFlag) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NodeKey) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(RunScript) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NodeDescription) ) + SysConst.PACKAGESPILTER
					+ StrTool.cTrim( StrTool.unicodeToGBK(NodeSign) ) + SysConst.PACKAGESPILTER
					+  ChgData.chgData(NodeOrder);
		return strReturn;
	}

	/**
	* 数据解包，解包顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMenu>历史记账凭证主表信息</A>表字段
	* @param: strMessage：包含一条纪录数据的字符串
	* @return: boolean
	**/
	public boolean decode(String strMessage)
	{
		try
		{
			NodeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 1, SysConst.PACKAGESPILTER );
			ParentNodeCode = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 2, SysConst.PACKAGESPILTER );
			NodeLevel = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 3, SysConst.PACKAGESPILTER );
			NodeName = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 4, SysConst.PACKAGESPILTER );
			ChildFlag = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 5, SysConst.PACKAGESPILTER );
			NodeKey = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 6, SysConst.PACKAGESPILTER );
			RunScript = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 7, SysConst.PACKAGESPILTER );
			NodeDescription = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 8, SysConst.PACKAGESPILTER );
			NodeSign = StrTool.getStr(StrTool.GBKToUnicode(strMessage), 9, SysConst.PACKAGESPILTER );
			NodeOrder= new Integer(ChgData.chgNumericStr(StrTool.getStr(strMessage,10,SysConst.PACKAGESPILTER))).intValue();
		}
		catch(NumberFormatException ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDMenuSchema";
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
		if (FCode.equals("NodeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeCode));
		}
		if (FCode.equals("ParentNodeCode"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ParentNodeCode));
		}
		if (FCode.equals("NodeLevel"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeLevel));
		}
		if (FCode.equals("NodeName"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeName));
		}
		if (FCode.equals("ChildFlag"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(ChildFlag));
		}
		if (FCode.equals("NodeKey"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeKey));
		}
		if (FCode.equals("RunScript"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(RunScript));
		}
		if (FCode.equals("NodeDescription"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeDescription));
		}
		if (FCode.equals("NodeSign"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeSign));
		}
		if (FCode.equals("NodeOrder"))
		{
			strReturn = StrTool.GBKToUnicode(String.valueOf(NodeOrder));
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
				strFieldValue = StrTool.GBKToUnicode(NodeCode);
				break;
			case 1:
				strFieldValue = StrTool.GBKToUnicode(ParentNodeCode);
				break;
			case 2:
				strFieldValue = StrTool.GBKToUnicode(NodeLevel);
				break;
			case 3:
				strFieldValue = StrTool.GBKToUnicode(NodeName);
				break;
			case 4:
				strFieldValue = StrTool.GBKToUnicode(ChildFlag);
				break;
			case 5:
				strFieldValue = StrTool.GBKToUnicode(NodeKey);
				break;
			case 6:
				strFieldValue = StrTool.GBKToUnicode(RunScript);
				break;
			case 7:
				strFieldValue = StrTool.GBKToUnicode(NodeDescription);
				break;
			case 8:
				strFieldValue = StrTool.GBKToUnicode(NodeSign);
				break;
			case 9:
				strFieldValue = String.valueOf(NodeOrder);
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

		if (FCode.equals("NodeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeCode = FValue.trim();
			}
			else
				NodeCode = null;
		}
		if (FCode.equals("ParentNodeCode"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ParentNodeCode = FValue.trim();
			}
			else
				ParentNodeCode = null;
		}
		if (FCode.equals("NodeLevel"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeLevel = FValue.trim();
			}
			else
				NodeLevel = null;
		}
		if (FCode.equals("NodeName"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeName = FValue.trim();
			}
			else
				NodeName = null;
		}
		if (FCode.equals("ChildFlag"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				ChildFlag = FValue.trim();
			}
			else
				ChildFlag = null;
		}
		if (FCode.equals("NodeKey"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeKey = FValue.trim();
			}
			else
				NodeKey = null;
		}
		if (FCode.equals("RunScript"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				RunScript = FValue.trim();
			}
			else
				RunScript = null;
		}
		if (FCode.equals("NodeDescription"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeDescription = FValue.trim();
			}
			else
				NodeDescription = null;
		}
		if (FCode.equals("NodeSign"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				NodeSign = FValue.trim();
			}
			else
				NodeSign = null;
		}
		if (FCode.equals("NodeOrder"))
		{
			if( FValue != null && !FValue.equals(""))
			{
				Integer tInteger = new Integer( FValue );
				int i = tInteger.intValue();
				NodeOrder = i;
			}
		}
		return true;
	}

	public boolean equals(Object otherObject)
	{
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (getClass() != otherObject.getClass()) return false;
		LDMenuSchema other = (LDMenuSchema)otherObject;
		return
			NodeCode.equals(other.getNodeCode())
			&& ParentNodeCode.equals(other.getParentNodeCode())
			&& NodeLevel.equals(other.getNodeLevel())
			&& NodeName.equals(other.getNodeName())
			&& ChildFlag.equals(other.getChildFlag())
			&& NodeKey.equals(other.getNodeKey())
			&& RunScript.equals(other.getRunScript())
			&& NodeDescription.equals(other.getNodeDescription())
			&& NodeSign.equals(other.getNodeSign())
			&& NodeOrder == other.getNodeOrder();
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
		if( strFieldName.equals("NodeCode") ) {
			return 0;
		}
		if( strFieldName.equals("ParentNodeCode") ) {
			return 1;
		}
		if( strFieldName.equals("NodeLevel") ) {
			return 2;
		}
		if( strFieldName.equals("NodeName") ) {
			return 3;
		}
		if( strFieldName.equals("ChildFlag") ) {
			return 4;
		}
		if( strFieldName.equals("NodeKey") ) {
			return 5;
		}
		if( strFieldName.equals("RunScript") ) {
			return 6;
		}
		if( strFieldName.equals("NodeDescription") ) {
			return 7;
		}
		if( strFieldName.equals("NodeSign") ) {
			return 8;
		}
		if( strFieldName.equals("NodeOrder") ) {
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
				strFieldName = "NodeCode";
				break;
			case 1:
				strFieldName = "ParentNodeCode";
				break;
			case 2:
				strFieldName = "NodeLevel";
				break;
			case 3:
				strFieldName = "NodeName";
				break;
			case 4:
				strFieldName = "ChildFlag";
				break;
			case 5:
				strFieldName = "NodeKey";
				break;
			case 6:
				strFieldName = "RunScript";
				break;
			case 7:
				strFieldName = "NodeDescription";
				break;
			case 8:
				strFieldName = "NodeSign";
				break;
			case 9:
				strFieldName = "NodeOrder";
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
		if( strFieldName.equals("NodeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ParentNodeCode") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeLevel") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeName") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("ChildFlag") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeKey") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("RunScript") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeDescription") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeSign") ) {
			return Schema.TYPE_STRING;
		}
		if( strFieldName.equals("NodeOrder") ) {
			return Schema.TYPE_INT;
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
				nFieldType = Schema.TYPE_INT;
				break;
			default:
				nFieldType = Schema.TYPE_NOFOUND;
		};
		return nFieldType;
	}
}
