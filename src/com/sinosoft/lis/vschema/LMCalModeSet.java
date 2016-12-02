/*
 * <p>ClassName: LMCalModeSet </p>
 * <p>Description: LMCalModeSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2011-11-10
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.LMCalModeSchema;
import com.sinosoft.utility.*;

public class LMCalModeSet extends SchemaSet
{
	// @Method
	public boolean add(LMCalModeSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LMCalModeSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LMCalModeSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LMCalModeSchema get(int index)
	{
		LMCalModeSchema tSchema = (LMCalModeSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LMCalModeSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LMCalModeSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMCalMode描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LMCalModeSchema aSchema = (LMCalModeSchema)this.get(i);
			strReturn += aSchema.encode();
			if( i != n ) strReturn += SysConst.RECORDSPLITER;
		}

		return strReturn;
	}

	/**
	* 数据解包
	* @param: 打包后字符串
	* @return: boolean
	**/
	public boolean decode( String str )
	{
		int nBeginPos = 0;
		int nEndPos = str.indexOf('^');
		this.clear();

		while( nEndPos != -1 )
		{
			LMCalModeSchema aSchema = new LMCalModeSchema();
			if( aSchema.decode(str.substring(nBeginPos, nEndPos)) == false )
			{
				// @@错误处理
				this.mErrors.copyAllErrors( aSchema.mErrors );
				return false;
			}
			this.add(aSchema);
			nBeginPos = nEndPos + 1;
			nEndPos = str.indexOf('^', nEndPos + 1);
		}
		LMCalModeSchema tSchema = new LMCalModeSchema();
		if( tSchema.decode(str.substring(nBeginPos)) == false )
		{
			// @@错误处理
			this.mErrors.copyAllErrors( tSchema.mErrors );
			return false;
		}
		this.add(tSchema);

		return true;
	}

}
