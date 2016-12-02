/*
 * <p>ClassName: LDComSet </p>
 * <p>Description: LDComSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-05-22
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.utility.*;

public class LDComSet extends SchemaSet
{
	// @Method
	public boolean add(LDComSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LDComSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LDComSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LDComSchema get(int index)
	{
		LDComSchema tSchema = (LDComSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LDComSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LDComSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLDCom描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LDComSchema aSchema = (LDComSchema)this.get(i);
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
			LDComSchema aSchema = new LDComSchema();
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
		LDComSchema tSchema = new LDComSchema();
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
