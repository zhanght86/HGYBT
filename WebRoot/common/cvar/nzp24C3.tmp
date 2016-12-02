/*****************************************************************
 *               Program NAME: 类CVar
 *                 programmer:
 *                Create DATE:
 *             Create address: Beijing
 *                Modify DATE:
 *             Modify address:
 *****************************************************************
 *
 *         该类完成变量的分配，读取，修改，释放等操作。
 *
 *****************************************************************
 */
function CVar(cArrayLength,cArrayVars,cErrorString)
{
	this.marrayLength= cArrayLength || -1;	//存放当前数组的长度
	this.arrayVars   = cArrayVars || new Array();	//存放所有变量的数组,变量名：变量类型：变量值
	this._CVar_mErrorString = cErrorString || "";	//该变量为当执行发生错误时，提示的错误信息

	this.initVar = _CVar_InitVar;
	this.addVar = _CVar_AddVar;
	this.updateVar = _CVar_UpdateVar;
	this.deleteVar = _CVar_DeleteVar;
	this.getVar = _CVar_GetVar;
	this.getIndex = _CVar_GetIndex;
	this.getError = _CVar_GetError;

	//用于主页面缓存所有代码选择结果，解决二维数组不能直接跨页面保存的问题
	this.addArrVar = _CVar_AddArrVar;
}

/*************************************************************
 *			增加一个数组变量
 *  参数  ：	cVarName	:变量名
 *				cVarType	:变量类型
 *				cVarValue	:变量值
 *  返回值：  	如果执行成功，返回true,否则返回false
 *************************************************************
 */
function _CVar_AddArrVar(cVarName,cVarType,cVarValue)
{
	var i;
	this._CVar_mErrorString="";
	try
	{
		i=_CVar_GetIndex(cVarName,this);	//如果变量存在，则不允许新增
		if (i!=-1)
		{
			this._CVar_mErrorString="CVarOperate.js-->AddVar:变量"+cVarName+"存在，不允许新增."
			return false;
		}
		i=this.marrayLength+1;
		this.marrayLength=i;
		this.arrayVars[i]=new Array();
		this.arrayVars[i][0]=cVarName;
		this.arrayVars[i][1]=cVarType;
		this.arrayVars[i][2] = new Array();
		var tArr = new Array();
		for (m=0; m<cVarValue.length; m++)
		{
			tArr[m] = new Array();
			for (n=0; n<cVarValue[m].length; n++)
			{
				tArr[m][n] = cVarValue[m][n];
			}
		}
		this.arrayVars[i][2] = tArr;
	}
    catch(ex)
	{
		alert("_CVar_AddArrVar throw Error");
		return false;
	}
	return true;
}

/*************************************************************
 *			增加一个变量
 *  参数  ：	cVarName	:变量名
 *				cVarType	:变量类型
 *				cVarValue	:变量值
 *  返回值：  	如果执行成功，返回true,否则返回false
 *************************************************************
 */
function _CVar_AddVar(cVarName,cVarType,cVarValue)
{
	var i;
	this._CVar_mErrorString="";
	try
	{
		i=_CVar_GetIndex(cVarName,this);	//如果变量存在，则不允许新增
		if (i!=-1)
		{
			this._CVar_mErrorString="CVarOperate.js-->AddVar:变量"+cVarName+"存在，不允许新增."
			return false;
		}
		i=this.marrayLength+1;
		this.marrayLength=i;
		this.arrayVars[i]=new Array();
		this.arrayVars[i][0]=cVarName;
		this.arrayVars[i][1]=cVarType;
		this.arrayVars[i][2]=cVarValue
	}
	catch(ex)
	{
		return false;
	}
	return true;
}

/*************************************************************
 *			修改一个变量
 *  参数  ：	cVarName	:变量名
 *				cVarType	:变量类型
 *				cVarValue	:变量值
 *  返回值：  	没有
 *************************************************************
 */
function _CVar_UpdateVar(cVarName,cVarType,cVarValue)
{
	var i;
	this._CVar_mErrorString="";
	try
	{
		i=_CVar_GetIndex(cVarName,this);	//得到变量的位置索引
		if(i==-1)
		{
			//如果变量名没有找到，则返回false
			this._CVar_mErrorString="CVarOperate.js-->UpdateVar:变量名"+cVarName+"没有找到."
			return false;
		}
		this.arrayVars[i][0]=cVarName;
		this.arrayVars[i][1]=cVarType;
		this.arrayVars[i][2]=cVarValue
	}
	catch(ex)
	{
		return false;
	}
	return true;
}

/*************************************************************
 *			查找一个变量
 *  参数  ：		cVarName	:变量名
 *  返回值：  	返回该变量在变量缓冲区中的位置
 *************************************************************
 */
function _CVar_GetIndex(cVarName,cObj)
{
	var i;
	var j;
	if (cObj==null)
	{
		cObj=this;
	}
	try
	{
		i=cObj.marrayLength;
		for (j=0;j<=i;j++)
		{
			if(cObj.arrayVars[j][0]==cVarName)
			{
				return j;
			}
		}
		cObj._CVar_mErrorString="CVarOperate.js-->GetIndex():没有找到变量"+cVarName+"的索引.";
		return -1;
	}
	catch(ex)
	{
		cObj._CVar_mErrorString="CVarOperate.js-->GetIndex():在查找索引时出错.";
		return -1;
	}
}

/*************************************************************
 *			得到一个变量的值
 *  参数  ：		cVarName	:变量名
 *  返回值：  	任意对象,如果没有发现该变量，则返回false
 *************************************************************
 */
function _CVar_GetVar(cVarName)
{
	var tReturn;
	var i;
	this._CVar_mErrorString="";
	try
	{
		i=_CVar_GetIndex(cVarName,this);	//得到变量的位置索引
		if(i==-1)
		{
			//如果变量名没有找到，则返回false
			return false;
		}
		tReturn=this.arrayVars[i][2];
	}
	catch(ex)
	{
		return false;
	}
	return tReturn;
}

/*************************************************************
 *			删除(释放)一个变量,如果该变量不是最后一个，
 *			则将后面的变量前移，并且把最后的变量释放。
 *  参数  ：		cVarName	:变量名
 *  返回值：  	没有
 *************************************************************
 */
function _CVar_DeleteVar(cVarName)
{
	var i;
	var j;
	var k;
	this._CVar_mErrorString="";
	try
	{
		i=_CVar_GetIndex(cVarName,this);	//得到变量的位置索引
		if(i==-1)
		{
			//如果变量名没有找到，则返回false
			return false;
		}
		j=this.marrayLength;
		if (j > 0)
		{
			for (k=i;k<j;k++)
			{
				this.arrayVars[k][0]=this.arrayVars[k+1][0];
				this.arrayVars[k][1]=this.arrayVars[k+1][1];
				this.arrayVars[k][2]=this.arrayVars[k+1][2];
			}
		}
		this.marrayLength=j-1;
		delete this.arrayVars[j];
	}
	catch(ex)
	{
		return false;
	}
	return true;
}

/*************************************************************
 *			将最近执行后的错误信息读取出来
 *  参数  ：
 *  返回值：  	返回当前的错误信息字符串
 *************************************************************
 */
function _CVar_GetError()
{
	return this._CVar_mErrorString;
}

/*************************************************************
 *			信息域初始化
 *  参数  ：	无
 *  返回值：  	无
 *************************************************************
 */
function _CVar_InitVar()
{
	this.marrayLength= -1;	//存放当前数组的长度
	this.arrayVars =  new Array();	//存放所有变量的数组,变量名：变量类型：变量值
	this._CVar_mErrorString =  "";	//该变量为当执行发生错误时，提示的错误信息
}