/*
 * <p>ClassName: LDMenuGrpToMenuSet </p>
 * <p>Description: LDMenuGrpToMenuSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2011-11-10
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.LDMenuGrpToMenuSchema;
import com.sinosoft.utility.*;

public class LDMenuGrpToMenuSet extends SchemaSet
{
	// @Method
	public boolean add(LDMenuGrpToMenuSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LDMenuGrpToMenuSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LDMenuGrpToMenuSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LDMenuGrpToMenuSchema get(int index)
	{
		LDMenuGrpToMenuSchema tSchema = (LDMenuGrpToMenuSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LDMenuGrpToMenuSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LDMenuGrpToMenuSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDMenuGrpToMenu描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LDMenuGrpToMenuSchema aSchema = (LDMenuGrpToMenuSchema)this.get(i);
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
			LDMenuGrpToMenuSchema aSchema = new LDMenuGrpToMenuSchema();
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
		LDMenuGrpToMenuSchema tSchema = new LDMenuGrpToMenuSchema();
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
