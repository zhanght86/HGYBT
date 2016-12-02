/*
 * <p>ClassName: LCInsureAccSet </p>
 * <p>Description: LCInsureAccSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2011-11-10
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.utility.*;

public class LCInsureAccSet extends SchemaSet
{
	// @Method
	public boolean add(LCInsureAccSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LCInsureAccSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LCInsureAccSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LCInsureAccSchema get(int index)
	{
		LCInsureAccSchema tSchema = (LCInsureAccSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LCInsureAccSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LCInsureAccSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLCInsureAcc描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LCInsureAccSchema aSchema = (LCInsureAccSchema)this.get(i);
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
			LCInsureAccSchema aSchema = new LCInsureAccSchema();
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
		LCInsureAccSchema tSchema = new LCInsureAccSchema();
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
