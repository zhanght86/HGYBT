/*
 * <p>ClassName: LMPublicCheckFieldSet </p>
 * <p>Description: LMPublicCheckFieldSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-01-29
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.LMPublicCheckFieldSchema;
import com.sinosoft.utility.*;

public class LMPublicCheckFieldSet extends SchemaSet
{
	// @Method
	public boolean add(LMPublicCheckFieldSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LMPublicCheckFieldSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LMPublicCheckFieldSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LMPublicCheckFieldSchema get(int index)
	{
		LMPublicCheckFieldSchema tSchema = (LMPublicCheckFieldSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LMPublicCheckFieldSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LMPublicCheckFieldSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLMPublicCheckField描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LMPublicCheckFieldSchema aSchema = (LMPublicCheckFieldSchema)this.get(i);
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
			LMPublicCheckFieldSchema aSchema = new LMPublicCheckFieldSchema();
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
		LMPublicCheckFieldSchema tSchema = new LMPublicCheckFieldSchema();
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
