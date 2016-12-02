/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : sinosoft
 * @version  : 1.00
 * @date     : 2006-11-08
 * @direction: 核心业务管理系统多行显示/输入表格
 * @comment  : XinYQ formatted on 2006-11-08
 ******************************************************************************/



function MulLineEnter(iFormName, iInstanceName)
{
    //以下属性需要用户初始化
    this.formName = iFormName || "fm"; //表单控件名称
    this.instanceName = iInstanceName || ""; //实例名称
    this.mulLineCount = 0; //行输入对象的行数
    this.canAdd = 1; //是否可以允许增加，删除1表示可以，0表示不可以
    this.canSel = 0; //是否可以选择，1表示可以，0表示不可以
    this.showTitle = 1; //是否现实title 1表示显示，0表示不显示
    this.displayTitle = 1; //是否显示标题，1表示显示，0表示不显示
    this.locked = 0; //是否锁定，1表示锁定，0表示可编辑
    this.canChk = 0; //是否需要多行选择,1表示可以多行选择，0表示不可以
    this.colCount = 0; //新增，列的数目
    this.hiddenPlus = 0; //新增,是否隐藏添加一行的标志：0为显示，1为隐藏
    this.hiddenSubtraction = 0; //新增,是否隐藏删除一行的标志：0为显示，1为隐藏
    this.recordNo = 0; //新增,如果分页显示多条纪录，那么显示前将该值赋为基数,那么第2页显示的序号会接着上页的序号
    this.checkFlag = 0; //新增,和checkAll函数配合用
    this.state = 0; //新增,此参数对外部无任何实际意义,和_ResumeState函数一起使用
    this.arraySave = new Array(); //新增，保存传入的列数组
    this.arraySave2 = new Array(); //新增，保存参数的数组--用于是否显示中文
    this.chkBoxEventFuncName = ""; //新增，保存外部单击CheckBox框时响应的外部函数名
    this.chkBoxEventFuncParm = " "; //新增，保存外部单击CheckBox框时响应的外部函数传入的参数
    this.chkBoxAllEventFuncName = ""; //新增，保存外部单击标题栏全选CheckBox框时响应的外部函数名
    this.selBoxEventFuncName = ""; //新增，保存外部单击RadioBox框时响应的外部函数名
    this.selBoxEventFuncParm = " "; //新增，保存外部单击RadioBox框时响应的外部函数传入的参数
    this.addEventFuncName = ""; //新增，保存外部单击+按钮时响应的外部函数名
    this.addEventFuncParm = " "; //新增，保存外部单击+按钮框时响应的外部函数传入的参数
    this.delEventFuncName = ""; //新增，保存外部单击-按钮时响应的外部函数名
    this.delEventFuncParm = " "; //新增，保存外部单击-按钮框时响应的外部函数传入的参数
    this.AllowSort = ""; //排序
    this.SortPage = ""; //排序中Grid对应的turnpage
    this.allowsort = "AllowSortFun"; //Grid的排序函数通过它调用turnpage中的函数
    this.windowWidth = window.document.body.clientWidth;
    this.windowHeight = window.document.body.clientHeight;
    this.mulLineNum = 1; //新增,设置同一行的MulLine的个数，默认是1
    this.detailInfo = ""; //如果支持单击，则在此处设置提示信息
    this.tableWidth = ""; //设置table的宽度

    //以下属性不需要用户初始化
    this.mulLineText = ""; //行输入对象的一行模版的内容(内部使用）
    this.mulLineTextTitle = ""; //行输入对象的标题（内部使用）
    //2006-04 后新增属性
    this.lastFocusRowNo = -1; //最近一次得到焦点的行(从0开始)
    this.lastFocusColNo = -1; //最近一次得到焦点的列(从1开始)

    //初始化添加一行隐藏行，spanID由-1 改成-2
    this.maxSpanID = -1; //行输入对象的最大SpanID的值
    this.errorString = ""; //该变量为当执行发生错误时，提示的错误信息

    //方法
    this.loadPage = _LoadPage;
    this.setFieldValue = _SetFieldValue;

    this.clearData = _ClearData;
    this.findSpanID = _FindSpanID;
    this.delBlankLine = _DelBlankLine;
    this.delCheckTrueLine = _DelCheckTrueLine; //删除选中的CheckBox行
    this.delRadioTrueLine = _DelRadioTrueLine; //删除选中的RadioBox行
    this.lock = _Lock;
    this.unLock = _UnLock;
    this.getSelNo = _GetSelNo;
    this.getChkNo = _GetChkNo;
    this.checkAll = _CheckAll;
    this.checkBoxAll = _CheckBoxAll;
    this.checkBoxAllNot = _CheckBoxAllNot;
    this.checkBoxSel = _CheckBoxSel;
    this.loadMulLine = _LoadMulLine;
    this.updateField = _UpdateField;
    this.loadMulLineArr = _LoadMulLineArr;
    this.addOne = _AddOne;
    this.deleteOne = _DeleteOne;
    this.keyUp = _KeyUp;
    this.getErrStr = _GetErrStr;
    this.setRowColData = _SetRowColData;
    this.getRowColData = _GetRowColData;
    this.getRowData = _GetRowData;
    this.detailClick = _detailClick;
    this.checkBoxClick = _CheckBoxClick;
    this.radioBoxClick = _RadioBoxClick;
    this.radioClick = _radioClick;
    this.resumeState = _ResumeState;
    this.checkValue = _CheckValue;
    this.checkValue2 = _CheckValue2;
    this.setFocus = _SetFocus;
    this.getFocus = _GetFocus;
    this.moveFocus = _MoveFocus;
    this.setPageMark = _SetPageMark;
    //2006-04 后新增方法
    this.getChkCount = _GetChkCount;
    this.selOneRow = _SelOneRow;
    this.chkOneRow = _ChkOneRow;
}

//==============================================================================

function _detailClick(cObj) {}

//==============================================================================

/**
 * 禁用 MulLine 的 + - 号
 */
function _Lock(iTObj)
{
    var tObj = iTObj || this;
    if (tObj.locked != 1)
    //如果没有锁定，执行锁定
    {
        try
        {
            tObj.locked = 1;
            //注意：这里和_SetFieldValue函数中"请注意"的说明部分紧密相关
            //因为这里是将_SetFieldValue函数中模板部分的文本替换字符串，
            //如果_SetFieldValue()中，这部分的文本格式变了，这里也要相应变化
            eval(tObj.formName + ".all('" + tObj.instanceName + "addOne').disabled=true");
            tObj.mulLineText = replace(tObj.mulLineText, "type='button' value='-'", "type='button' disabled value='-'");
            if (tObj.mulLineCount > 0)
            {
                if (tObj.mulLineCount == 1)
                {
                    _ResumeState();
                    try
                    {
                        eval(tObj.formName + ".all('" + tObj.instanceName + "Del').disabled=true");
                    }
                    catch (ex)
                    {
                        eval(tObj.formName + ".all('" + tObj.instanceName + "Del')[0].disabled=true");
                    }
                }
                else
                {
                    for (i = 0; i < tObj.mulLineCount; i++)
                    {
                        eval(tObj.formName + ".all('" + tObj.instanceName + "Del')[" + i + "].disabled=true");
                    }
                }
            }
        }
        catch (ex)
        {
            _DisplayError("在 MulLine.js --> _Lock 函数中发生异常：" + ex, tObj);
        }
    }
}

//==============================================================================

/**
 * 激活 MulLine 的 + - 号
 */
function _UnLock(iTObj)
{
    var tObj = iTObj || this;
    if (tObj.locked != 0)
    //如果锁定，执行解锁
    {
        try
        {
            tObj.locked = 0;
            //注意：这里和_SetFieldValue函数中"请注意"的说明部分紧密相关
            //因为这里是将_SetFieldValue函数中模板部分的文本替换字符串，
            //如果_SetFieldValue()中，这部分的文本格式变了，这里也要相应变化
            eval(tObj.formName + ".all('" + tObj.instanceName + "addOne').disabled=false");
            tObj.mulLineText = replace(tObj.mulLineText, "type='button' disabled value='-'", "type='button' value='-'");
            if (tObj.mulLineCount > 0)
            {
                if (tObj.mulLineCount == 1)
                {
                    _ResumeState();
                    try
                    {
                        eval(tObj.formName + ".all('" + tObj.instanceName + "Del').disabled=false");
                    }
                    catch (ex)
                    {
                        eval(tObj.formName + ".all('" + tObj.instanceName + "Del')[0].disabled=false");
                    }
                }
                else
                {
                    for (i = 0; i < tObj.mulLineCount; i++)
                    {
                        eval(tObj.formName + ".all('" + tObj.instanceName + "Del')[" + i + "].disabled=false");
                    }
                }
            }
        }
        catch (ex)
        {
            _DisplayError("在 MulLine.js --> _UnLock 函数中发生异常：" + ex, tObj);
        }
    }
}

//==============================================================================

/**
 * 获取选中单选框的行数
 */
function _GetSelNo(ObjInstance)
{
    var tObjInstance = ObjInstance || this;
    var i = 0;
    try
    {
        if (tObjInstance.mulLineCount > 0)
        {

            if (tObjInstance.mulLineCount == 1)
            {
                _ResumeState();
                try
                {
                    if (eval(tObjInstance.formName + ".all('" + tObjInstance.instanceName + "Sel').checked"))
                    {
                        return 1;
                    }
                }
                catch (ex)
                {
                    if (eval(tObjInstance.formName + ".all('" + tObjInstance.instanceName + "Sel')[0].checked"))
                    {
                        return 1;
                    }
                }
            }
            else
            {
                for (i = 0; i < tObjInstance.mulLineCount; i++)
                {
                    if (eval(tObjInstance.formName + ".all('" + tObjInstance.instanceName + "Sel')[" + i + "].checked"))
                    {
                        return i + 1;
                    }
                }
            }
        }
        else
        {
            return 0;
        }
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _GetSelNo 函数中发生异常：" + ex, tObjInstance);
    }
    return 0;
}

//==============================================================================

/**
 * 选中指定行的单选框
 */
function _radioClick(cObj, colcount)
{
    var ele = document.getElementById("span" + this.instanceName);
    var oldPageNo = ele.getAttribute("PageNo");
    var pageNo;
    var iMax = 0;
    iMax = this.mulLineCount;
    var fieldCount = colcount;
    try
    {
        var i = 0;
        while (i < iMax)
        {
            if (iMax == 1)
            {
                pageNo = "0";
                _ResumeState();
                if (eval(this.formName + ".all('" + this.instanceName + "Sel').checked"))
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Sel').value='1'");
                    eval(this.formName + ".all('" + this.instanceName + "Sel').className='mulnotreadonlyt'");
                    eval(this.formName + ".all('" + this.instanceName + "No').className='mulnotreadonlyt'");
                    var j = 1;
                    while (j < fieldCount)
                    {
                        eval(this.formName + ".all('" + this.instanceName + j + "').className='mulnotreadonlyt'");
                        j++;
                    }
                }
                else
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Sel').value='0'");
                    eval(this.formName + ".all('" + this.instanceName + "Sel').className='mulreadonlyt'");
                    eval(this.formName + ".all('" + this.instanceName + "No').className='mulreadonlyt'");
                    var j = 1;
                    while (j < fieldCount)
                    {
                        eval(this.formName + ".all('" + this.instanceName + j + "').className='mulreadonlyt'");
                        j++;
                    }
                }
            }
            else
            {
                if (eval(this.formName + ".all('" + this.instanceName + "Sel')[" + i + "].checked"))
                {
                    pageNo = i;
                    //oldPageNo为0＆为空的时候，脚本判定的结果是一致的
                    if ((oldPageNo != undefined && oldPageNo != null && oldPageNo != "") || oldPageNo == "0")
                    {
                        eval(this.formName + ".all('Inp" + this.instanceName + "Sel')[" + oldPageNo + "].value='0'");
                        eval(this.formName + ".all('" + this.instanceName + "Sel')[" + oldPageNo + "].className='mulreadonlyt'");
                        eval(this.formName + ".all('" + this.instanceName + "No')[" + oldPageNo + "].className='mulreadonlyt'");
                        var j = 1;
                        while (j < fieldCount)
                        {
                            var tObj = this.formName + ".all('" + this.instanceName + j + "')[" + oldPageNo + "].className";
                            if (eval(tObj + "=='code8'") || eval(tObj + "=='codeselect'"))
                            {
                                eval(tObj + "='code8'");
                            }
                            else
                            {
                                eval(tObj + "='mulreadonlyt'");
                            }
                            j++;
                        }
                    }
                    eval(this.formName + ".all('Inp" + this.instanceName + "Sel')[" + pageNo + "].value='1'");
                    eval(this.formName + ".all('" + this.instanceName + "Sel')[" + pageNo + "].className='mulnotreadonlyt'");
                    eval(this.formName + ".all('" + this.instanceName + "No')[" + pageNo + "].className='mulnotreadonlyt'");
                    var j = 1;
                    while (j < fieldCount)
                    {
                        var tObj = this.formName + ".all('" + this.instanceName + j + "')[" + pageNo + "].className";
                        if (eval(tObj + "=='code8'") || eval(tObj + "=='codeselect'"))
                        {
                            eval(tObj + "='codeselect'");
                        }
                        else
                        {
                            eval(tObj + "='mulnotreadonlyt'");
                        }
                        j++;
                    }
                    break;
                }
            }
            i++;
        }
        if (pageNo != null && pageNo != undefined)
        {
            ele.setAttribute("PageNo", pageNo);
        }
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _radioClick函数中发生异常:" + ex.message, this);
    }
}

//==============================================================================

/**
 * 选中指定行的单选框
 */
function _RadioBoxClick(cObj, colcount)
{
    var fieldCount = colcount || this.colCount;
    //XinYQ modified on 2006-05-12 : 解决 _RadioBoxClick 只高亮单选框和号码的问题
    //OLD : var fieldCount=colcount;
    var i = 0, iMax = 0;
    iMax = this.mulLineCount;
    try
    {
        if (this.mulLineCount == 1)
        {
            _ResumeState();
            try
            {
                if (eval(this.formName + ".all('" + this.instanceName + "Sel').checked"))

                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Sel').value='1'");
                    eval(this.formName + ".all('" + this.instanceName + "Sel').className='mulnotreadonlyt'");
                    eval(this.formName + ".all('" + this.instanceName + "No').className='mulnotreadonlyt'");
                    for (j = 1; j < fieldCount; j++)
                    {
                        eval(this.formName + ".all('" + this.instanceName + j + "').className='mulnotreadonlyt'");
                    }
                }
                else
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Sel').value='0'");
                    eval(this.formName + ".all('" + this.instanceName + "Sel').className='mulreadonlyt'");
                    eval(this.formName + ".all('" + this.instanceName + "No').className='mulreadonlyt'");
                    for (j = 1; j < fieldCount; j++)
                    {
                        eval(this.formName + ".all('" + this.instanceName + j + "').className='mulreadonlyt'");
                    }
                 }
            }
            catch (ex)
            {
                if (eval(this.formName + ".all('" + this.instanceName + "Sel')[0].checked"))

                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Sel')[0].value='1'")
                }
                else
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Sel')[0].value='0'")
                }
            }
        }
        else
        {
            for (i = 0; i < iMax; i++)
            {
                if (eval(this.formName + ".all('" + this.instanceName + "Sel')[" + i + "].checked"))
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Sel')[" + i + "].value='1'");
                    eval(this.formName + ".all('" + this.instanceName + "Sel')[" + i + "].className='mulnotreadonlyt'");
                    eval(this.formName + ".all('" + this.instanceName + "No')[" + i + "].className='mulnotreadonlyt'");
                    for (j = 1; j < fieldCount; j++)
                    {
                        if (eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className=='code8'") || eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className=='codeselect'"))

                        {
                            eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className='codeselect'");
                        }
                        else
                        {
                            eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className='mulnotreadonlyt'");
                        }
                    }
                }
                else
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Sel')[" + i + "].value='0'");
                    eval(this.formName + ".all('" + this.instanceName + "Sel')[" + i + "].className='mulreadonlyt'");
                    eval(this.formName + ".all('" + this.instanceName + "No')[" + i + "].className='mulreadonlyt'");
                    for (j = 1; j < fieldCount; j++)
                    {
                        if (eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className=='code8'") || eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className=='codeselect'"))
                        {
                            eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className='code8'");

                        }
                        else
                        {
                            eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className='mulreadonlyt'");
                        }
                    }
                }
            }
        }
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _RadioBoxClick 函数中发生异常：" + ex, this);
    }
}

//==============================================================================

/**
 * 获取选中复选框的行数
 */
function _GetChkNo(cIndex, cObj)
{
    var tObj = cObj || this;
    var i = 0;
    i = cIndex;
    var tReturn;
    var tStr;
    if (tObj.canChk == 0)
    {
        alert("no checkBox!");
        return false;
    }
    if (cIndex < 0 || cIndex >= tObj.mulLineCount)
    {
        alert("在 MulLine.js --> getChkNo函数中指定了错误的行:" + cIndex);
        return false;
    }
    try
    {
        if (tObj.mulLineCount > 0 && i < tObj.mulLineCount)
        {
            if (tObj.mulLineCount == 1)
            {
                _ResumeState();
                tStr = tObj.formName + ".all('" + tObj.instanceName + "Chk').checked";
                tReturn = eval(tStr);
                //如果得到的值为null，说明当行数为1时，有下面的可能：
                //如果是从多行删除到一行，那么可能会继续认为这个单独行是数组的一个元素，所以还要加下标
                //除上述外，可能在javaScript中还有其它因素，因此，下面可以看作对各种情况的修正：
                try
                {
                    //把undefined单独提出来，是因为有的JS不支持undefined，所以要捕获例外
                    if (tReturn == undefined)
                    try
                    {
                        tStr = tObj.formName + ".all('" + tObj.instanceName + "Chk')[0].checked";
                        tReturn = eval(tStr);
                    }
                    catch (ex)
                    {
                        tStr = tObj.formName + ".all('" + tObj.instanceName + "Chk').checked";
                        tReturn = eval(tStr);
                    }
                }
                catch (ex) {}
                if (tReturn == null)
                {
                    try
                    {
                        tStr = tObj.formName + ".all('" + tObj.instanceName + "Chk')[0].checked";
                        tReturn = eval(tStr);
                    }
                    catch (ex)
                    {
                        tStr = tObj.formName + ".all('" + tObj.instanceName + "Chk').checked";
                        tReturn = eval(tStr);
                    }
                }
                return tReturn;
            }
            else
            {
                if (eval(tObj.formName + ".all('" + tObj.instanceName + "Chk')[" + i + "].checked"))
                {
                    return true;
                }
            }
        }
        else
        {
            return false;
        }
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _GetChkNo 函数中发生异常：" + ex, tObj);
    }
    return false;
}

//==============================================================================

/**
 * 选中指定行的复选框
 */
function _CheckBoxClick(cObj, colcount)
{
    var fieldCount = colcount || this.colCount;
    //XinYQ modified on 2006-05-11 : 解决 _CheckBoxClick 只高亮复选框和号码的问题
    //OLD : var fieldCount=colcount;
    var i = 0, iMax = 0;
    iMax = this.mulLineCount;
    try
    {
        if (this.mulLineCount == 1)
        {
            _ResumeState();
            try
            {
                if (eval(this.formName + ".all('" + this.instanceName + "Chk').checked"))
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Chk').value='1'");
                    eval(this.formName + ".all('" + this.instanceName + "Chk').className='mulnotreadonlyt'");
                    eval(this.formName + ".all('" + this.instanceName + "No').className='mulnotreadonlyt'");
                    /*for (j = 1; j < fieldCount; j++)
                    {
                        if (eval(this.formName + ".all('" + this.instanceName + j + "').className=='code8'") || eval(this.formName + ".all('" + this.instanceName + j + "').className=='codeselect'"))
                        {
                            eval(this.formName + ".all('" + this.instanceName + j + "').className='codeselect'");
                        }
                        else
                        {
                            eval(this.formName + ".all('" + this.instanceName + j + "').className='mulnotreadonlyt'");
                        }
                    }*/
                }
                else
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Chk').value='0'");
                    eval(this.formName + ".all('" + this.instanceName + "Chk').className='mulreadonlyt'");
                    eval(this.formName + ".all('" + this.instanceName + "No').className='mulreadonlyt'");
                    /*for (j = 1; j < fieldCount; j++)
                    {
                        if (eval(this.formName + ".all('" + this.instanceName + j + "').className=='code8'") || eval(this.formName + ".all('" + this.instanceName + j + "').className=='codeselect'"))
                        {
                            eval(this.formName + ".all('" + this.instanceName + j + "').className='code8'");
                        }
                        else
                        {
                            eval(this.formName + ".all('" + this.instanceName + j + "').className='mulreadonlyt'");
                        }
                    }*/
                }
            }
            catch (ex)
            {
                if (eval(this.formName + ".all('" + this.instanceName + "Chk')[0].checked"))
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[0].value='1'")
                }
                else
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[0].value='0'")
                }
            }
        }
        else
        {
            //for (i = 0; i < iMax; i++)
            //{
            	  i = this.lastFocusRowNo;
            	  if (eval(this.formName + ".all('" + this.instanceName + "Chk')[" + i + "].checked"))
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[" + i + "].value='1'");
                    eval(this.formName + ".all('" + this.instanceName + "Chk')[" + i + "].className='mulnotreadonlyt'");
                    eval(this.formName + ".all('" + this.instanceName + "No')[" + i + "].className='mulnotreadonlyt'");
                    /*for (j = 1; j < fieldCount; j++)
                    {
                        if (eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className=='code8'") || eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className=='codeselect'"))
                        {
                            eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className='codeselect'");
                        }
                        else
                        {
                            eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className='mulnotreadonlyt'");
                        }
                    }*/
                }
                else
                {
                    eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[" + i + "].value='0'");
                    eval(this.formName + ".all('" + this.instanceName + "Chk')[" + i + "].className='mulreadonlyt'");
                    eval(this.formName + ".all('" + this.instanceName + "No')[" + i + "].className='mulreadonlyt'");
                    /*for (j = 1; j < fieldCount; j++)
                    {
                        if (eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className=='code8'") || eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className=='codeselect'"))
                        {
                            eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className='code8'");
                        }
                        else
                        {
                            eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className='mulreadonlyt'");
                        }
                    }*/
                }
            //}
        }
        //XinYQ added on 2006-05-11
        try
        {
            //如果所有行都已被选择,标记"全选"框选中状态
            if (this.getChkCount() == this.mulLineCount)
            {
                try
                {
                    document.getElementsByName("checkAll" + this.instanceName)[0].value = 1;
                    document.getElementsByName("checkAll" + this.instanceName)[0].checked = true;
                }
                catch (ex){}
            }
            //如果所有行都取消选择或者所选个数不等于总行数,取消"全选"框选中状态
            else
            {
                try
                {
                    document.getElementsByName("checkAll" + this.instanceName)[0].value = 0;
                    document.getElementsByName("checkAll" + this.instanceName)[0].checked = false;
                }
                catch (ex){}
            }
        }
        catch (ex)
        {
            alert("error->_CheckBoxClick");
        }
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _checkBoxClick 函数中发生异常：" + ex, this);
    }
}

//==============================================================================

/**
 * 使所有行的 checkBox 变成选中的状态
 */
function _CheckBoxAll(cObj, colcount)
{
    var fieldCount = colcount || this.colCount;
    //XinYQ modified on 2006-05-11 : 解决 _CheckBoxAll 只高亮复选框和号码的问题
    //OLD : var fieldCount=colcount;
    if (this.canChk == 0)
    {
        alert("此 MulLime 不允许复选！ ");
        return;
    }
    var i = 0, iMax = 0;
    iMax = this.mulLineCount;
    try
    {
        try
        {
            eval(this.formName + ".all('checkAll" + this.instanceName + "').value='1'");
            eval(this.formName + ".all('checkAll" + this.instanceName + "').checked=true");
        }
        catch (ex)
        {
            _DisplayError("在 MulLine.js --> _checkBoxAll 函数中发生异常：" + ex, this);
        }
        if (this.mulLineCount == 1)
        {
            _ResumeState();
            try
            {
                eval(this.formName + ".all('Inp" + this.instanceName + "Chk').value='1'");
                eval(this.formName + ".all('" + this.instanceName + "Chk').checked=true");
                eval(this.formName + ".all('" + this.instanceName + "Chk').className='mulnotreadonlyt'");
                eval(this.formName + ".all('" + this.instanceName + "No').className='mulnotreadonlyt'");
                /*for (j = 1; j < fieldCount; j++)
                {
                    if (eval(this.formName + ".all('" + this.instanceName + j + "').className=='code8'") || eval(this.formName + ".all('" + this.instanceName + j + "').className=='codeselect'"))
                    {
                        eval(this.formName + ".all('" + this.instanceName + j + "').className='codeselect'");
                    }
                    else
                    {
                        eval(this.formName + ".all('" + this.instanceName + j + "').className='mulnotreadonlyt'");
                    }
                }*/
            }
            catch (ex)
            {
                eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[0].value='1'");
                eval(this.formName + ".all('" + this.instanceName + "Chk')[0].checked=true");
            }
        }
        else
        {
            for (i = 0; i < iMax; i++)
            {
                eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[" + i + "].value='1'");
                eval(this.formName + ".all('" + this.instanceName + "Chk')[" + i + "].checked=true");
                eval(this.formName + ".all('" + this.instanceName + "Chk')[" + i + "].className='mulnotreadonlyt'");
                eval(this.formName + ".all('" + this.instanceName + "No')[" + i + "].className='mulnotreadonlyt'");
                /*for (j = 1; j < fieldCount; j++)
                {
                    if (eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className=='code8'") || eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className=='codeselect'"))
                    {
                        eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className='codeselect'");
                    }
                    else
                    {
                        eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className='mulnotreadonlyt'");
                    }
                }*/
            }
        }
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _checkBoxAll 函数中发生异常：" + ex, this);
    }
}

//==============================================================================

/**
 * 使选择的行的 checkBox 变成选中的状态
 */
function _CheckBoxSel(row, cObj)
{
    if (this.canChk == 0)
    {
        alert("no checkBox!");
        return;
    }
    var i = 0, iMax = 0;
    var rowNo = row;
    iMax = this.mulLineCount;
    if (rowNo > iMax || rowNo <= 0)
    {
        alert("输入行号超出范围");
        return;
    }
    try
    {
        if (this.mulLineCount == 1)
        {
            _ResumeState();
            try
            {
                eval(this.formName + ".all('Inp" + this.instanceName + "Chk').value='1'");
                eval(this.formName + ".all('" + this.instanceName + "Chk').checked=true");
            }
            catch (ex)
            {
                eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[0].value='1'");
                eval(this.formName + ".all('" + this.instanceName + "Chk')[0].checked=true");
            }
        }
        else
        {
            eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[" + (rowNo - 1) + "].value='1'");
            eval(this.formName + ".all('" + this.instanceName + "Chk')[" + (rowNo - 1) + "].checked=true");
        }
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _CheckBoxSel 函数中发生异常：" + ex, this);
    }
}

//==============================================================================

/**
 * 选中所有 checkBox 框
 */
function _CheckAll(cObjInstance, fieldCount)
{
    var tObjInstance;
    if (cObjInstance == null)
    {
        tObjInstance = this;
    }
    else
    {
        tObjInstance = cObjInstance;
    }
    if (tObjInstance.canChk == 0)
    {
        alert("no checkBox!");
        return;
    }
    if (eval(this.formName + ".all('checkAll" + this.instanceName + "').checked==true"))
    {
        this.checkBoxAll(this, fieldCount);
        this.checkFlag = 1;
    }
    else
    {
        this.checkBoxAllNot(this, fieldCount);
        this.checkFlag = 0;
    }
    //if(tObjInstance.checkFlag==0)
    //{
    //  tObjInstance.checkBoxAll();
    //  tObjInstance.checkFlag=1;
    //}
    //else
    //{
    //  tObjInstance.checkBoxAllNot();
    //  tObjInstance.checkFlag=0;
    //}
}

//==============================================================================

/**
 * 使所有行的 checkBox 变成没有选中的状态
 */
function _CheckBoxAllNot(cObj, colcount)
{
    var fieldCount = colcount;
    if (this.canChk == 0)
    {
        alert("no checkBox!");
        return;
    }
    var i = 0, iMax = 0;
    iMax = this.mulLineCount;
    try
    {
        try
        {
            eval(this.formName + ".all('checkAll" + this.instanceName + "').value='0'");
            eval(this.formName + ".all('checkAll" + this.instanceName + "').checked=false");
        }
        catch (ex)
        {
            alert("error->_CheckBoxAllNot");
        }
        if (this.mulLineCount == 1)
        {
            _ResumeState();
            try
            {
                eval(this.formName + ".all('Inp" + this.instanceName + "Chk').value='0'");
                eval(this.formName + ".all('" + this.instanceName + "Chk').checked=false");
                eval(this.formName + ".all('" + this.instanceName + "Chk').className='mulreadonlyt'");
                eval(this.formName + ".all('" + this.instanceName + "No').className='mulreadonlyt'");
                /*for (j = 1; j < fieldCount; j++)
                {
                    if (eval(this.formName + ".all('" + this.instanceName + j + "').className=='code8'") || eval(this.formName + ".all('" + this.instanceName + j + "').className=='codeselect'"))
                    {
                        eval(this.formName + ".all('" + this.instanceName + j + "').className='code8'");
                    }
                    else
                    {
                        eval(this.formName + ".all('" + this.instanceName + j + "').className='mulreadonlyt'");
                    }
                }*/
            }
            catch (ex)
            {
                eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[0].value='0'");
                eval(this.formName + ".all('" + this.instanceName + "Chk')[0].checked=false");
            }
        }
        else
        {
            for (i = 0; i < iMax; i++)
            {
                eval(this.formName + ".all('Inp" + this.instanceName + "Chk')[" + i + "].value='0'");
                eval(this.formName + ".all('" + this.instanceName + "Chk')[" + i + "].checked=false");
                eval(this.formName + ".all('" + this.instanceName + "Chk')[" + i + "].className='mulreadonlyt'");
                eval(this.formName + ".all('" + this.instanceName + "No')[" + i + "].className='mulreadonlyt'");
                /*for (j = 1; j < fieldCount; j++)
                {
                    if (eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className=='code8'") || eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className=='codeselect'"))
                    {
                        eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className='code8'");
                    }
                    else
                    {
                        eval(this.formName + ".all('" + this.instanceName + j + "')[" + i + "].className='mulreadonlyt'");
                    }
                }*/
            }

        }
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _checkBoxAllNot 函数中发生异常：" + ex, this);
    }
}

//==============================================================================

/**
 * 显示多行输入对象
 */
function _LoadMulLine(arrCols)
{
    //设置行模版到this.mulLineText中
    //设置标题到this.mulLineTextTitle中
    this.arraySave = arrCols;
    _SetFieldValue(this.instanceName, arrCols, this);
    _LoadPage(this.instanceName, this);
}

//==============================================================================

function _UpdateField(cIndex, arrCol)
{
    var i = 0;
    i = cIndex;
    this.arraySave[i] = arrCol;
    _SetFieldValue(this.instanceName, this.arraySave, this);
    _LoadPage(this.instanceName, this);
}

//==============================================================================

/**
 * 方法：显示多行输入对象，采用多行的模式
 * 输入：列描述数组，装载数据的对象
 */
function _LoadMulLineArr(arrCols, cData)
{
    this.arraySave = arrCols; //描述的数组信息
    //设置行模版到this.mulLineText中
    //设置标题到this.mulLineTextTitle中
    _SetFieldValue(this.instanceName, arrCols, this);
    //转载数据
    _LoadPageArr(this.instanceName, this, cData);
}

//==============================================================================

/**
 * 方法：根据传入的数组，形成每行输入域的模版
 * 二维数组格式：列名，列宽，列最大值，列是否能够输入
 */
function _SetFieldValue(strPageName, iArray, cObjInstance)
{
    var text = "";
    var textTitle = "";
    cObjInstance.errorString = "";
    var boxWidth = 20; //radioBox 和checkBox 定义的宽度
    var userWidth = 0; //用户定义的列宽总和
    var rate = 1 / cObjInstance.mulLineNum; //窗口body宽度和用户定义宽度的比率
    var fieldCount = iArray.length;
    //设置列的数目
    cObjInstance.colCount = fieldCount;
    var i = 0;
    var status = "";
    //判断是否禁用删除/增加 button
    //如果用户初始化选择禁用,那么模板中也随之变化
    if (cObjInstance.locked == 1)
    {
        status = "disabled";
    }
    try
    {
        if (fieldCount > 0)
        {
            //设置索引列
            var tempText0 = iArray[0][0]; //索引列的名称
            var tempText1 = iArray[0][1]; //索引列宽
            var tempText2 = iArray[0][2]; //索引列最大允许值
            var tempText4 = iArray[0][3]; //索引列是否允许输入
            var tempText5 = "";
            var tempText6 = "";
            var tempText7 = "";
            var tempText8 = "";
            var tempText9 = "";
            var tempText10 = "";
            var tempText11 = "";
            var tempText12 = "";
            var tempText13 = "";
            var tempText14 = "";
            var tempText15 = "";
            var tempText16 = "";
            var tempText17 = "";
            var tempText18 = "";
            var tempText19 = "";
            var tempText20 = "";
            var tempText21 = ""; //设置当前列是否显示时调用编码转汉字的函数，用在setRowColData函数
            var tempText22 = "";
            var tempText23 = "";
            var showTitleText = "";
            var strFocusEvent = "_CalcFocusRowColNo(" + cObjInstance.instanceName + ", this)";
            if (cObjInstance.showTitle == 1)
            {
                showTitleText = "onmouseover=_showtitle(this);";
            }
            text = "";
            if (cObjInstance.tableWidth == "")
            {
                text = text + "  <table class='muline' border='0' cellspacing='0' cellpadding='0'>";
                textTitle = textTitle + "  <table class='muline' border='0' cellspacing='0' cellpadding='0'>";
            }
            else
            {
                text = text + " <table class='muline' align='center' border='0' cellspacing='0' cellpadding='0' style=\"width:" + cObjInstance.tableWidth + "\">";
                textTitle = textTitle + " <table class='muline' align='center' border='0' cellspacing='0' cellpadding='1' style=\"width:" + cObjInstance.tableWidth + "\">";
            }
            text = text + " <tr align='left'> ";
            textTitle = textTitle + " <tr align='left'>";
            if (cObjInstance.canSel == 1)
            {
                userWidth = userWidth + parseInt(boxWidth); //加上radioBox宽
                if (cObjInstance.selBoxEventFuncName != null && cObjInstance.selBoxEventFuncName != "")
                {
                    text = text + "<td class='muline' style='width:" + boxWidth + "'>";
                    text = text + "<input type='hidden' name='Inp" + strPageName + "Sel' value='0'><input class='mulcommon' style='width:" + boxWidth + "' type='radio' name=" + strPageName + "Sel onclick=\"" + strFocusEvent + "; " + cObjInstance.instanceName + ".radioBoxClick(this," + fieldCount + "); " + cObjInstance.selBoxEventFuncName + "('span$PageName$$SpanId$','" + cObjInstance.selBoxEventFuncParm + "');\"";
                    text = text + "</td>";
                    textTitle = textTitle + "<td class='mulinetitle' style='width:" + boxWidth + "'> ";
                    textTitle = textTitle + "<input class='mulinetitle' tabindex='-1' readonly style='width:" + boxWidth + "'>";
                    textTitle = textTitle + "</td>";
                }
                else
                {
                    text = text + "<td class='muline' style='width:" + boxWidth + "'>";
                    text = text + "<input type='hidden' name='Inp" + strPageName + "Sel' value='0'><input class='mulcommon' style='width:" + boxWidth + "' type='radio' name=" + strPageName + "Sel onclick=\"" + strFocusEvent + "; " + cObjInstance.instanceName + ".radioBoxClick(this," + fieldCount + ");\"";
                    text = text + "</td>";
                    textTitle = textTitle + "<td class='mulinetitle' style='width:" + boxWidth + "'> ";
                    textTitle = textTitle + "<input class='mulinetitle' tabindex='-1' readonly style='width:" + boxWidth + "'>";
                    textTitle = textTitle + "</td>";
                }
            }
            if (cObjInstance.canChk == 1)
            {
                userWidth = userWidth + parseInt(boxWidth); //加上checkBox宽
                //全选框
                if (cObjInstance.chkBoxAllEventFuncName != null && cObjInstance.chkBoxAllEventFuncName != "")
                {
                    textTitle = textTitle + "<td class='mulinetitle' style='width:" + boxWidth + "'> ";
                    textTitle = textTitle + "<input class='title' type='checkbox' name='checkAll" + cObjInstance.instanceName + "' onclick=\"" + strFocusEvent + "; " + cObjInstance.instanceName + ".checkAll(this," + fieldCount + ");" + cObjInstance.chkBoxAllEventFuncName + "(this.checked,this);\"" + " style='width:" + boxWidth + "'>";
                    textTitle = textTitle + "</td>";
                }
                else
                {
                    textTitle = textTitle + "<td class='mulinetitle' style='width:" + boxWidth + "'> ";
                    textTitle = textTitle + "<input class='title' type='checkbox' name='checkAll" + cObjInstance.instanceName + "' onclick=\"" + strFocusEvent + "; " + cObjInstance.instanceName + ".checkAll(this," + fieldCount + ");\" style='width:" + boxWidth + "'>";
                    textTitle = textTitle + "</td>";
                }
                //复选框
                if (cObjInstance.chkBoxEventFuncName != null && cObjInstance.chkBoxEventFuncName != "")
                {
                    text = text + "<td class='muline' style='width:" + boxWidth + "'>";
                    text = text + "<input type='hidden' name='Inp" + strPageName + "Chk' value='0'><input class='mulcommon' style='width:" + boxWidth + "' type='checkbox' name=" + strPageName + "Chk onclick=\"" + strFocusEvent + "; " + cObjInstance.instanceName + ".checkBoxClick(this," + fieldCount + ");" + cObjInstance.chkBoxEventFuncName + "('span$PageName$$SpanId$','" + cObjInstance.chkBoxEventFuncParm + "');\"";
                    text = text + "</td>";
                }
                else
                {
                    //CheckBox不响应外部的函数
                    text = text + "<td class='muline' style='width:" + boxWidth + "'>";
                    text = text + "<input type='hidden' name='Inp" + strPageName + "Chk' value='0'><input class='mulcommon' style='width:" + boxWidth + "' type='checkbox' name=" + strPageName + "Chk onclick=\"" + strFocusEvent + "; " + cObjInstance.instanceName + ".checkBoxClick(this," + fieldCount + ");\"";
                    text = text + "</td>";
                }
            }
            //序号列
            tempText1 = replace(tempText1, "px", " "); //如果宽度加上px单位，替换
            tempText1 = replace(tempText1, "PX", " "); //如果宽度加上px单位，替换
            tempText1 = trim(tempText1);
            userWidth = userWidth + parseInt(tempText1); //加上索引列宽
            text = text + " <td class='muline' style=\"width:" + tempText1 + "\"> ";
            textTitle = textTitle + " <td class='mulinetitle' style=\"width:" + tempText1 + "\"> " + "<input class='mulinetitle' readonly tabindex='-1' value= '" + tempText0 + "' style='width:" + tempText1 + "'>";
            text = text + " <input class='mulreadonly' tabindex='-1' name=" + strPageName + "No " + isReadOnly(tempText4) + " maxlength=" + tempText2 + " style='width:" + tempText1 + "; text-align:right; padding-right:2px' onclick=\"" + cObjInstance.instanceName + ".detailClick(this);\" title='" + cObjInstance.detailInfo + "'> ";
            text = text + " </td>";
            textTitle = textTitle + " </td>";
            for (i = 1; i < fieldCount; i++)
            {
                tempText1 = iArray[i][1]; //索引列宽
                tempText1 = replace(tempText1, "px", " "); //如果宽度加上px单位，替换
                tempText1 = replace(tempText1, "PX", " "); //如果宽度加上px单位，替换
                tempText1 = trim(tempText1);
                userWidth = userWidth + parseInt(tempText1); //加上各个列宽
            }
            //if(cObjInstance.hiddenSubtraction==0)
            // {userWidth=userWidth+30; }//加上最后一列"-"的宽度
            userWidth = userWidth + 40; //增加宽度
            if (userWidth < cObjInstance.windowWidth)
            {
                rate = (cObjInstance.windowWidth / userWidth) / cObjInstance.mulLineNum;
            }
            for (i = 1; i < fieldCount; i++)
            {
                tempText0 = iArray[i][0]; //索引列的名称
                tempText1 = iArray[i][1]; //索引列宽
                tempText2 = iArray[i][2]; //索引列最大允许值
                tempText4 = iArray[i][3]; //设置当前列的样式: 0-不允许输入; 1-允许输入; 2-代码选择; 3-隐藏; 4-密码输入; 5-需两次输入确认
                tempText5 = iArray[i][4]; //代码引用(数据从后台数据库取)--代码名
                tempText6 = iArray[i][5]; //代码引用对应的多列 (数据从后台数据库取)
                tempText7 = iArray[i][6]; //代码引用对应的多列的内部值(数据从后台数据库取)
                tempText8 = iArray[i][7]; //对应的外部的js函数（参数是当前行的spanID,传入的数组）
                tempText9 = iArray[i][8]; //对应的外部的js函数的第2个参数
                tempText10 = iArray[i][9]; //格式校验
                tempText11 = iArray[i][10]; //代码引用(数据从前台传入)--代码名
                tempText12 = iArray[i][11]; //代码引用(数据从前台传入)
                tempText13 = iArray[i][12]; //代码引用(数据从前台传入)--排列多列
                tempText14 = iArray[i][13]; //代码引用(数据从前台传入)
                tempText15 = iArray[i][14]; //用户设置该列常量
                tempText16 = iArray[i][15]; //设置当前列的双击下拉显示依赖于其它控件或列的名字
                tempText17 = iArray[i][16]; //设置当前列的双击下拉显示依赖于其它控件的值
                tempText18 = iArray[i][17]; //设置当前列的双击下拉显示依赖于其它列的值
                tempText19 = iArray[i][18]; //设置当前列的双击下调整弹出下拉框的宽度（专为codeSelect度身打造:第8个参数）
                tempText20 = iArray[i][19]; //设置当前列的双击下强制刷新codeSelect数据源（专为codeSelect度身打造:第7个参数）
                tempText21 = iArray[i][20]; //此处不用，用于setRowColData函数（判断该参数，是否将编码转为中文）。
                tempText22 = iArray[i][21]; //设置当前列字段对齐方式
                tempText23 = iArray[i][22]; //设置当前列失去焦点触发的外部函数
                //this.arraySave2[i-1]=new Array();
                try
                {
                    if (tempText19 == undefined)
                    {
                        tempText19 = null;
                    }
                    if (tempText20 == undefined)
                    {
                        tempText20 = null;
                    }
                    if (tempText21 == undefined)
                    {
                        tempText21 = null;
                    }
                    if (tempText22 == undefined)
                    {
                        tempText22 = null;
                    }
                    if (tempText23 == undefined)
                    {
                        tempText23 = null;
                    }
                }
                catch (ex)
                {
                    tempText19 = null;
                    tempText20 = null;
                    tempText21 = null;
                    tempText22 = null;
                    tempText23 = null;
                }
                if (tempText21 == '1')
                {
                    //this.arraySave2[i][0]='1';//该列编码显示为汉字
                    //this.arraySave2[i][1]=tempText5;//保存代码引用的编码，如果需要转换汉字
                }
                else
                {
                    //this.arraySave2[i][0]='0';//该列编码不显示为汉字
                }
                if (tempText15 == null || tempText15 == "")
                {
                    tempText15 = "";
                }
                tempText1 = replace(tempText1, "px", " ");
                tempText1 = replace(tempText1, "PX", " ");
                tempText1 = trim(tempText1);
                tempText1 = parseInt(tempText1) * rate; //用实际宽度扩充用户填充的宽度
                if (tempText4 == '3')
                {
                    textTitle = textTitle + " <td class='mulinetitle' style=\"display:'none'\"> ";

                    text = text + "<td class='mulinetitle' style=\"display:'none'\"><input name=" + strPageName + i + " value='" + tempText15 + "' type='hidden' " + "  maxlength=" + tempText2 + "";
                }
                else
                {
                    if (cObjInstance.AllowSort == "True")
                    {
                        textTitle = textTitle + " <td class='mulinetitle' style=\"width:" + tempText1 + "\"> " + "<input class='mulinetitle1' readonly tabindex='-1' value=" + tempText0 + " style='cursor:hand;width :" + tempText1 + "' onclick='" + cObjInstance.allowsort + "(" + strPageName + "," + i + ");'>";
                    }
                    else
                    {
                        textTitle = textTitle + " <td class='mulinetitle' style=\"width:" + tempText1 + "\"> " + "<input class='mulinetitle' readonly tabindex='-1' value=" + tempText0 + " style='width :" + tempText1 + "' " + showTitleText + ">";
                    }
                    text = text + "<td class='muline' style=\"width:" + tempText1 + "\">" + "<input name=" + strPageName + i + " value='" + tempText15 + "' " + isReadOnly(tempText4) + " class=" + isReadOnlyClass(tempText4) + " maxlength=" + tempText2 + " onClick=\"" + strFocusEvent + ";\"" + " onFocus=\"" + strFocusEvent + ";\"";
                    //<!-- XinYQ added on 2006-08-22 : 支持密码输入和字段对齐方式 : BGN -->
                    if (tempText4 == "4")
                    {
                        text = text + " type='password'";
                    }
                    else
                    {
                        //如果不是密码,允许提示
                        text = text + " " + showTitleText;
                    }
                    if (tempText22 == "2")
                    {
                        text = text + " style='text-align:center;'";
                    }
                    else if (tempText22 == "3")
                    {
                        text = text + " style='text-align:right; padding-right:2px;'";
                    }
                    else
                    {
                        text = text + " style='text-align:left; padding-left:2px;'";
                    }
                    //<!-- XinYQ added on 2006-08-22 : 支持密码输入和字段对齐方式 : END -->
                }
                if (tempText5 == null || tempText5 == "")
                {
                    if (tempText8 != null && tempText8 != "")
                    //如果引用，那么就使用自己编写的javaScript函数在.js文件中 ,（传入参数是当前行的spanID）
                    {
                        if (tempText8.indexOf("()") != -1)
                        {
                            tempText8 = tempText8.replace("()", "");
                        }
                        text = text + " ondblclick=\"" + tempText8 + "('span$PageName$$SpanId$'," + tempText9 + ")" + ";\"";
                    }
                }
                else
                {
                    if (tempText6 == null || tempText6 == "" || tempText7 == null || tempText7 == "")
                    //如果代码引用只应用在1列上
                    {
                        if (tempText16 == null || tempText16 == "")
                        //如果不根据其它控件或列做判断
                        {
                            text = text + " ondblclick=\"showCodeList('" + tempText5 + "',[this],null,null,null,null," + tempText20 + "," + tempText19 + ");\"";
                            text = text + " onkeyup=\"showCodeListKey('" + tempText5 + "',[this],null,null,null,null," + tempText20 + "," + tempText19 + ");\"";
                        }
                        else
                        {
                            if (tempText17 != null && tempText17 != "")
                            //如果根据其它空间的值做判断
                            {
                                text = text + " ondblclick=\"showCodeList('" + tempText5 + "',[this],null,null,'" + tempText17 + "','" + tempText16 + "'," + tempText20 + "," + tempText19 + ");\"";
                                text = text + " onkeyup=\"showCodeListKey('" + tempText5 + "',[this],null,null,'" + tempText17 + "','" + tempText16 + "'," + tempText20 + "," + tempText19 + ");\"";
                            }
                            else
                            {
                                if (tempText18 != null && tempText18 != "")
                                //如果根据其它列的值做判断
                                {
                                    var tempValue = "[";
                                    arrText18 = tempText18.split(FIELDDELIMITER);
                                    for (var m = 0; m < arrText18.length; m++)
                                    {
                                        tempValue = tempValue + cObjInstance.formName + ".all('span$PageName$$SpanId$')" + ".all('" + "$PageName$" + arrText18[m] + "').value";
                                        if (m != arrText18.length - 1)
                                        {
                                            tempValue = tempValue + ",";
                                        }
                                    }
                                    tempValue = tempValue + "]";
                                    text = text + " ondblclick=\"showCodeList('" + tempText5 + "',[this],null,null," + tempValue + ",'" + tempText16 + "'," + tempText20 + "," + tempText19 + ");\"";
                                    text = text + " onkeyup=\"showCodeListKey('" + tempText5 + "',[this],null,null," + tempValue + ",'" + tempText16 + "'," + tempText20 + "," + tempText19 + ");\"";
                                }
                            }
                        }
                    }
                    else
                    //如果代码引用应用在多列上
                    {
                        var arrColName = "["; //对应列的集合的格式
                        var arrCodeName = "["; //对应代码选择的项的名称
                        //分割数组，得到对应列数的数组
                        var arrayField = tempText6.split(FIELDDELIMITER);
                        var arrayCode = tempText7.split(FIELDDELIMITER);
                        //格式化代码选择数组 从 0|1 转到[0,1]
                        for (var m = 0; m < arrayCode.length; m++)
                        {
                            arrCodeName = arrCodeName + arrayCode[m];
                            if (m != arrayCode.length - 1)
                            {
                                arrCodeName = arrCodeName + ",";
                            }
                        }
                        arrCodeName = arrCodeName + "]";
                        //格式化列对象数组 从0|1 转到[列对象，列对象]
                        for (var n = 0; n < arrayField.length; n++)
                        {
                            //arrColName=fm.all('spanXXXID').all('XXX+Col') 即对应spanID的列对象
                            arrColName = arrColName + cObjInstance.formName + ".all('span$PageName$$SpanId$')" + ".all('" + "$PageName$" + arrayField[n] + "')";
                            if (n != arrayField.length - 1)
                            {
                                arrColName = arrColName + ",";
                            }
                        }
                        arrColName = arrColName + "]";
                        if (tempText16 == null || tempText16 == "")
                        //如果不根据其它控件或列做判断
                        {
                            text = text + " ondblclick=\"showCodeList('" + tempText5 + "'," + arrColName + "," + arrCodeName + ",null,null,null," + tempText20 + "," + tempText19 + ");\"";
                            text = text + " onkeyup=\"showCodeListKey('" + tempText5 + "'," + arrColName + "," + arrCodeName + ",null,null,null," + tempText20 + "," + tempText19 + ");\"";
                        }
                        else
                        {
                            if (tempText17 != null && tempText17 != "")
                            //如果根据其它空间的值做判断
                            {
                                text = text + " ondblclick=\"showCodeList('" + tempText5 + "'," + arrColName + "," + arrCodeName + ",null,'" + tempText17 + "','" + tempText16 + "'," + tempText20 + "," + tempText19 + ");\"";
                                text = text + " onkeyup=\"showCodeListKey('" + tempText5 + "'," + arrColName + "," + arrCodeName + ",null,'" + tempText17 + "','" + tempText16 + "'," + tempText20 + "," + tempText19 + ");\"";
                            }
                            else
                            {
                                if (tempText18 != null && tempText18 != "")
                                //如果根据其它列的值做判断
                                {
                                    var tempValue = "[";
                                    arrText18 = tempText18.split(FIELDDELIMITER);
                                    for (var m = 0; m < arrText18.length; m++)
                                    {
                                        tempValue = tempValue + cObjInstance.formName + ".all('span$PageName$$SpanId$')" + ".all('" + "$PageName$" + arrText18[m] + "').value";
                                        if (m != arrText18.length - 1)
                                        {
                                            tempValue = tempValue + ",";
                                        }
                                    }
                                    tempValue = tempValue + "]";
                                    text = text + " ondblclick=\"showCodeList('" + tempText5 + "'," + arrColName + "," + arrCodeName + ",null," + tempValue + ",'" + tempText16 + "'," + tempText20 + "," + tempText19 + ");\"";
                                    text = text + " onkeyup=\"showCodeListKey('" + tempText5 + "'," + arrColName + "," + arrCodeName + ",null," + tempValue + ",'" + tempText16 + "'," + tempText20 + "," + tempText19 + ");\"";
                                }
                            }
                        }
                    } //对应else
                } //对应else

                //如果前面的数组第5,6,7,8,9项是空，那么判断第10项即代码引用是否可用
                if ((tempText5 == null || tempText5 == "") && (tempText6 == null || tempText6 == "") && (tempText7 == null || tempText7 == "") && (tempText8 == null || tempText8 == "") && (tempText9 == null || tempText9 == ""))
                {
                    if (tempText12 != null && tempText12 != "" && tempText11 != null && tempText11 != "")
                    {
                        if (tempText13 == null || tempText13 == "" || tempText14 == null || tempText14 == "")
                        {
                            //只对应当前单列代码选择
                            text = text + " CodeData='" + tempText12 + "' ondblClick=\"showCodeListEx('" + tempText11 + "',[this],null,null,null,null," + tempText20 + "," + tempText19 + ");\" ";
                            text = text + " onkeyup=\"showCodeListKeyEx('" + tempText11 + "',[this],null,null,null,null," + tempText20 + "," + tempText19 + ");\" ";
                        }
                        else
                        {
                            //对应多列或者不是当前列代码选择
                            var arrColName = "["; //对应列的集合的格式
                            var arrCodeName = "["; //对应代码选择的项的名称
                            //分割数组，得到对应列数的数组
                            var arrayField = tempText13.split(FIELDDELIMITER);
                            var arrayCode = tempText14.split(FIELDDELIMITER);
                            //格式化代码选择数组 从 0|1 转到[0,1]
                            for (var m = 0; m < arrayCode.length; m++)
                            {
                                arrCodeName = arrCodeName + arrayCode[m];
                                if (m != arrayCode.length - 1)
                                {
                                    arrCodeName = arrCodeName + ",";
                                }
                            }
                            arrCodeName = arrCodeName + "]";
                            //格式化列对象数组 从0|1 转到[列对象，列对象]
                            for (var n = 0; n < arrayField.length; n++)
                            {
                                //arrColName=fm.all('spanXXXID').all('XXX+Col') 即对应spanID的列对象
                                arrColName = arrColName + cObjInstance.formName + ".all('span$PageName$$SpanId$')" + ".all('" + "$PageName$" + arrayField[n] + "')";
                                if (n != arrayField.length - 1)
                                {
                                    arrColName = arrColName + ",";
                                }
                            }
                            arrColName = arrColName + "]";
                            text = text + " CodeData='" + tempText12 + "' ondblclick=\"showCodeListEx('" + tempText11 + "'," + arrColName + "," + arrCodeName + ",null,null,null," + tempText20 + "," + tempText19 + ");\"";
                            text = text + " onkeyup=\"showCodeListKeyEx('" + tempText11 + "'," + arrColName + "," + arrCodeName + ",null,null,null," + tempText20 + "," + tempText19 + ");\"";
                        }
                    }
                }
                if (tempText10 != null && tempText10 != "")
                //如果需要校验
                {
                    textTitle = textTitle + "<input type='hidden' name=" + strPageName + "verify" + i + " value='" + tempText10 + "'>";
                }
                else
                {
                    textTitle = textTitle + "<input type='hidden' name='" + strPageName + "verify" + i + "' value=''>";
                }
                //<!-- XinYQ added on 2006-11-08 : 支持字段失去焦点触发外部函数 : BGN -->
                if (tempText23 != null && tempText23 != "")
                {
                    if (tempText23.indexOf("()") != -1)
                    {
                        tempText23 = tempText23.replace("()", "");
                    }
                    text = text + " onblur='" + tempText23 + "(this)'";
                }
                //<!-- XinYQ added on 2006-11-08 : 支持字段失去焦点触发外部函数 : END -->
                text = text + " onkeyup='" + cObjInstance.instanceName + ".keyUp(\"$PageName$\");'";
                text = text + " style='width:" + tempText1 + "'>";
                text = text + " </td>";
                textTitle = textTitle + " </td>";
            }
            if (cObjInstance.hiddenSubtraction == 0)
            //如果隐藏减号"-"的标志=0，那么显示，否则隐藏
            {
                text = text + "      <td class='muline' width=15>";
                textTitle = textTitle + "      <td class='mulinetitle' width=15> <input class='mulinetitle' disabled readonly value='-' style='width :15'></td>";
                //请注意，下面这行的格式是不能随意改动的，它和_Lock(),UnLock()函数密切相关
                //如果改动下面这行，则必须修改_Lock(),UnLock()函数的相关部分
                text = text + "        <input class='button' type='button' " + status + " value='-' name='$PageName$Del' tabindex='-1' ";
                //如果需要点击-号响应外部函数
                if (cObjInstance.delEventFuncName != null && cObjInstance.delEventFuncName != "")
                {
                    text = text + " onclick='" + cObjInstance.instanceName + ".deleteOne(\"$PageName$\",span$PageName$$SpanId$);"
                     + cObjInstance.delEventFuncName + "(\"span$PageName$$SpanId$\",\"" + cObjInstance.delEventFuncParm + "\");'>";
                }
                else
                {
                    text = text + " onclick='" + cObjInstance.instanceName + ".deleteOne(\"$PageName$\",span$PageName$$SpanId$);'>";
                }
                //-----------------------------------------------------------------------
                text = text + "  <input type='hidden' name=$PageName$SpanID value=span$PageName$$SpanId$> ";
                //-----------------------------------------------------------------------
                text = text + "      </td>";
            }
            else
            {
                text = text + "      <td class='muline' width=15 style='display:none'>";
                textTitle = textTitle + "      <td class='mulinetitle' width=15 style='display:none'><input class='mulinetitle' type='hidden' readonly value='-' style='width:15'></td>";
                //请注意，下面这行的格式是不能随意改动的，它和_Lock(),UnLock()函数密切相关
                //如果改动下面这行，则必须修改_Lock(),UnLock()函数的相关部分0
                text = text + "        <input class='button' type='button' " + status + " value='-' name='$PageName$Del' style='display:none'";
                text = text + "          onclick='" + cObjInstance.instanceName + ".deleteOne(\"$PageName$\",span$PageName$$SpanId$);'>";
                //-----------------------------------------------------------------------
                text = text + "  <input type='hidden' name=$PageName$SpanID value=span$PageName$$SpanId$> ";
                //-----------------------------------------------------------------------
                text = text + "      </td>";
            }
            text = text + "    </tr>";
            textTitle = textTitle + "    </tr>";
            text = text + "  </table>";
            textTitle = textTitle + "  </table>";
        }
        cObjInstance.mulLineText = text; //行内容
        cObjInstance.mulLineTextTitle = textTitle; //行标题
        //alert(text);
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _SetFieldValue 函数中发生异常：" + ex, cObjInstance);
    }
}

//==============================================================================

/**
 * 多行输入区的初始化
 */
function _LoadPage(strPageName, cObjInstance)
{
    var t_StrPageName = strPageName || this.instanceName; //实例名称
    var innerHTML = "";
    cObjInstance.errorString = "";
    var status = "";
    //判断是否禁用删除/增加 button
    //如果用户初始化选择禁用,那么模板中也随之变化
    if (cObjInstance.locked == 1)
    {
        status = "disabled";
    }
    if (cObjInstance.displayTitle == 1)
    {
        innerHTML = innerHTML + cObjInstance.mulLineTextTitle;
    }
    innerHTML = innerHTML + "<span id='span" + t_StrPageName + "Field'></span>";
    if (cObjInstance.hiddenPlus == 0)
    //如果添加一行标志"+"不隐藏
    {
        innerHTML = innerHTML + "  <div align='left'>";
        if (cObjInstance.addEventFuncName != null && cObjInstance.addEventFuncName != "")
        {
            innerHTML = innerHTML + "  <input class='button' type='button' name='" + t_StrPageName + "addOne' value='+' " + status + " onclick=\" " + cObjInstance.instanceName + ".addOne('" + t_StrPageName + "');  " + cObjInstance.instanceName + ".moveFocus(); " + cObjInstance.addEventFuncName + "('span$PageName$$SpanId$','" + cObjInstance.addEventFuncParm + "'); \">";
        }
        else
        {
            innerHTML = innerHTML + "  <input type='button' class='button' name='" + t_StrPageName + "addOne' value='+' " + status + " onclick=\" " + cObjInstance.instanceName + ".addOne('" + t_StrPageName + "');  " + cObjInstance.instanceName + ".moveFocus();\">";
        }
        innerHTML = innerHTML + "</div>";
    }
    else
    //隐藏添加一行标志"+"
    {
        innerHTML = innerHTML + "  <div align='left'>";
        innerHTML = innerHTML + "  <input type='button' class='button' style='display:none' name='" + t_StrPageName + "addOne' value='+' " + status + " onclick=\" " + cObjInstance.instanceName + ".addOne('" + t_StrPageName + "');" + cObjInstance.instanceName + ".moveFocus();\">";
        innerHTML = innerHTML + "</div>";
    }
    //_DisplayError("在 MulLine.js --> _LoadPage 函数中发生异常: " + innerHTML,cObjInstance);
    try
    {
        document.all("span" + t_StrPageName).innerHTML = innerHTML;

        _AddOne(cObjInstance.instanceName, cObjInstance.mulLineCount, cObjInstance);
    }
    catch (ex)
    {
        // _DisplayError("在 MulLine.js --> _LoadPage 函数中发生异常：" + ex, cObjInstance);//edit by yaory
    }
}

//==============================================================================

/**
 * 多行输入区的初始化，采用多行装载数据模式
 */
function _LoadPageArr(strPageName, cObjInstance, cData)
{
    var t_StrPageName = strPageName || this.instanceName; //实例名称
    var tHTML = "";
    cObjInstance.errorString = "";
    var tStatus = "";
    //判断是否禁用删除/增加 button
    //如果用户初始化选择禁用,那么模板中也随之变化
    if (cObjInstance.locked == 1)
    {
        tStatus = "disabled";
    }
    if (cObjInstance.displayTitle == 1)
    {
        tHTML += cObjInstance.mulLineTextTitle;
    }
    tHTML += "<span id='span" + t_StrPageName + "Field'></span>";
    if (cObjInstance.hiddenPlus == 0)
    {
        //如果添加一行标志"+"不隐藏
        tHTML += "<div align='left'>";
        if (cObjInstance.addEventFuncName != null && cObjInstance.addEventFuncName != "")
        {
            tHTML += "<input type='button' class='button' name='" + t_StrPageName + "addOne' value='+' " + tStatus + " onclick=\"" + cObjInstance.instanceName + ".addOne('" + t_StrPageName + "'); " + cObjInstance.instanceName + ".moveFocus(); " + cObjInstance.addEventFuncName + "('span$PageName$$SpanId$','" + cObjInstance.addEventFuncParm + "');\">";
        }
        else
        {
            tHTML += "<input type='button' class='button' name='" + t_StrPageName + "addOne' value='+' " + tStatus + " onclick=\"" + cObjInstance.instanceName + ".addOne('" + t_StrPageName + "'); " + cObjInstance.instanceName + ".moveFocus();\">";
        }
        tHTML += "</div>";
    }
    else
    {
        //隐藏添加一行标志"+"
        tHTML += "<div align='left'><input type='button' class='button' style='display:none' name='" + t_StrPageName + "addOne' value='+' " + tStatus + " onclick=\" " + cObjInstance.instanceName + ".addOne('" + t_StrPageName + "');" + cObjInstance.instanceName + ".moveFocus();\"></div>";
    }
    try
    {
        document.all("span" + t_StrPageName).innerHTML = tHTML;
        _AddOneArr(cObjInstance.instanceName, cObjInstance.mulLineCount, cObjInstance, cData);
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _LoadPage 函数中发生异常:" + ex, cObjInstance);
    }
}

//==============================================================================

/**
 * 添加一行
 */
function _AddOne(strPageName, intNumber, cObjInstance)
{
    var t_StrPageName = strPageName || this.instanceName; //实例名称
    var i, j;
    var strText; //每行内容
    var strFunctionName = ""; //在执行完addone后调用的函数名
    var spanID = -1; //spanID序号
    var intCount; //添加的行个数
    var tObjInstance; //对象指针
    var isInit; //判断是否是在初始化过程中
    if (cObjInstance == null)
    {
        tObjInstance = this;
        isInit = false;
    }
    else
    {
        tObjInstance = cObjInstance;
        isInit = true;
    }
    tObjInstance.errorString = "";
    (intNumber == null) ? intCount = 1: intCount = intNumber; //得到行的个数
    //对变量赋值
    strText = tObjInstance.mulLineText;
    spanID = tObjInstance.maxSpanID;
    try
    {
        //得到原来的内容
        var strOldText = document.all("span" + t_StrPageName + "Field").innerHTML;

        //添加intCount行
        for (i = 1; i <= intCount; i++)
        {
            spanID++;
            tObjInstance.maxSpanID = spanID;
            strText = tObjInstance.mulLineText;
            strText = replace(strText, "$PageName$", t_StrPageName);
            strText = replace(strText, "$SpanId$", spanID);
            //strText = replace(strText,"$i$",tObjInstance.mulLineCount+1);
            strText = "<span id='span" + t_StrPageName + spanID + "'>" + strText + "</span>";
            strOldText = strOldText + strText;
        }
        if (!isInit)
        //如果是初始化，行数已经指定
        {
            tObjInstance.mulLineCount = tObjInstance.mulLineCount + intCount;
        }
        //加载变化后的文本
        document.all("span" + t_StrPageName + "Field").innerHTML = strOldText;
        _ModifyCount(tObjInstance.formName, t_StrPageName, tObjInstance.mulLineCount, tObjInstance); //调用函数名为strFunctionName的函数
        //注意顺序，必须是在加载变化后的文本后指定焦点
        //if (!isInit)//初始化时新添加的行不得到焦点，在点击"+"后新增加的行得到焦点
        //{
        //  if(tObjInstance.mulLineCount>0)
        //  tObjInstance.setFocus(tObjInstance.mulLineCount-1);
        //}
        //如果要求锁定，那么将新增的每一行锁定
        //if(tObjInstance.locked==1)
        //{
        //}
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _AddOne 函数中发生异常：" + ex, tObjInstance);
    }
}

//==============================================================================

/**
 * 添加多行
 */
function _AddOneArr(strPageName, intNumber, cObjInstance, cData)
{
    var t_StrPageName = strPageName || this.instanceName; //实例名称
    var i, j;
    var strText; //每行内容
    var strFunctionName = ""; //在执行完addone后调用的函数名
    var spanID = -1; //spanID序号
    var intCount; //添加的行个数
    var tObjInstance; //对象指针
    var isInit; //判断是否是在初始化过程中
    if (cObjInstance == null)
    {
        tObjInstance = this;
        isInit = false;
    }
    else
    {
        tObjInstance = cObjInstance;
        isInit = true;
    }
    tObjInstance.errorString = "";
    (intNumber == null) ? intCount = 1: intCount = intNumber; //得到行的个数
    //对变量赋值
    strText = tObjInstance.mulLineText;
    spanID = tObjInstance.maxSpanID;
    try
    {
        //得到原来的内容
        var strOldText = document.all("span" + t_StrPageName + "Field").innerHTML;
        //添加intCount行
        var i = 1;
        while (i <= intCount)
        {
            spanID++;
            tObjInstance.maxSpanID = spanID;
            strText = tObjInstance.mulLineText;
            strText = strText.replace(/\$PageName\$/g, t_StrPageName);
            strText = strText.replace(/\$SpanId\$/g, spanID);
            var j = 0;
            var tLength = cData[i - 1].length;
            while (j < tLength)
            {
                k = j + 1;
                //保证对象的顺序排列，且方式固定的情况下，才可以采用这样的方式替换
                strText = strText.replace(t_StrPageName + k + " value=''", t_StrPageName + k + " value='" + cData[i - 1][j] + "'");
                j++;
            }
            strText = "<span id='span" + t_StrPageName + spanID + "'>" + strText + "</span>";
            strOldText += strText;
            i++;
        }
        if (!isInit)
        {
            //如果是初始化，行数已经指定
            tObjInstance.mulLineCount = tObjInstance.mulLineCount + intCount;
        }
        //加载变化后的文本
        document.all("span" + t_StrPageName + "Field").innerHTML = strOldText;
        _ModifyCount(tObjInstance.formName, t_StrPageName, tObjInstance.mulLineCount, tObjInstance); //调用函数名为strFunctionName的函数
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _AddOne函数中发生异常:" + ex, tObjInstance);
    }
}

//==============================================================================

/**
 * 删除一行
 */
function _DeleteOne(strPageName, spanID, cObjInstance)
{
    var tStr;
    var t_StrPageName = strPageName || this.instanceName; //实例名称
    var tObjInstance; //对象指针
    if (cObjInstance == null)
    {
        tObjInstance = this;
    }
    else
    {
        tObjInstance = cObjInstance;
        var spanName = spanID;
        spanID = eval(tObjInstance.formName + ".all('" + spanName + "')");
    }
    tObjInstance.errorString = "";
    var spanObj = eval(tObjInstance.formName + ".all('span" + t_StrPageName + "')");
    try
    {
        spanID.innerHTML = "";
        tObjInstance.errorString = "";
        tObjInstance.mulLineCount = tObjInstance.mulLineCount - 1;
        tStr = "<SPAN id=" + spanID.id + "></SPAN>";
        spanObj.innerHTML = replace(spanObj.innerHTML, tStr, "");
        _ModifyCount(tObjInstance.formName, t_StrPageName, tObjInstance.mulLineCount, tObjInstance);
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _DeleteOne 函数中发生异常：" + ex, tObjInstance);
    }
}

//==============================================================================

/**
 * 按 Enter 或 ↓ 添加一行
 */
function _KeyUp(strPageName)
{
    var t_StrPageName = strPageName || this.instanceName; //实例名称
    if ((window.event.keyCode == 40) || (window.event.keyCode == 13)) {}
}

//==============================================================================

/**
 * 移动焦点到新增行
 */
function _MoveFocus(Col, cObjInstance)
{
    var cCol;
    var tObjInstance; //对象指针
    if (cObjInstance == null || cObjInstance == '')
    {
        tObjInstance = this;
    }
    else
    {
        tObjInstance = cObjInstance;
    }
    tObjInstance.errorString = "";
    cRow = tObjInstance.mulLineCount - 1; //行号从0开始
    if (Col == "" || Col == null)
    {
        cCol = 1;
    }
    else
    {
        cCol = Col;
    }
    try
    {
        tObjInstance.setFocus(cRow, cCol, tObjInstance);
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _MoveFocus 函数中发生异常：" + ex, tObjInstance);
    }
}

//==============================================================================

/**
 * 得到错误信息
 */
function _GetErrStr()
{
    return this.errorString;
}

//==============================================================================

/**
 * 修改索引信息(内部调用)
 */
function _ModifyCount(iFormName, iStrPageName, iCount, cObjInstance)
{
    var t_StrPageName = iStrPageName || this.instanceName; //实例名称
    //每次初始化数据完毕的时候，需要对PageNo对象进行初始化，呵呵，这样比较好的说
    var ele = document.getElementById("span" + t_StrPageName);
    ele.setAttribute("PageNo", "");
    var tObjInstance; //对象指针
    if (cObjInstance == null)
    {
        tObjInstance = this;
    }
    else
    {
        tObjInstance = cObjInstance;
    }
    var i;
    var len = iCount;
    var No;
    try
    {
        //注意，这里对应_SetRowColData函数的行为
        if (iCount == 1)
        {
            No = tObjInstance.recordNo + 1;
            try
            {
                eval(iFormName + ".all('" + t_StrPageName + "No').value=" + No);
            }
            catch (ex)
            {
                eval(iFormName + ".all('" + t_StrPageName + "No')[0].value=" + No);
            }
            //如果下面这样，将会出现异常：参见_SetRowColData函数的行为。
        }
        else
        {
            if (isInteger(len))
            {
                for (i = 1; i <= len; i++)

                {
                    No = tObjInstance.recordNo + i;
                    eval(iFormName + ".all('" + t_StrPageName + "No')[i-1].value=" + No);
                    //这里可以用下标引用，可能是行数iCount>1，所以看作数组，=1的时候，不能用下标，当作普通类型
                }
            }
        }
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _ModifyCount 函数中发生异常：" + ex, cObjInstance);
    }
}

//==============================================================================

/**
 * 判断是否是只读属性
 * 输入：如果参数为0，函数返回"readonly",否则返回""
 */
function isReadOnly(strReadOnly)
{
    var tempText;
    if (strReadOnly == "0")
    {
        tempText = "readonly tabindex='-1'";
    }
    else
    {
        tempText = "";
    }
    return tempText;
}

//==============================================================================

/**
 * 判断显示属性
 * 输入：如果参数为0，函数返回"readonly",否则返回""
 */
function isReadOnlyClass(strReadOnly)
{
    var tempText;
    if (strReadOnly == "0")
    {
        tempText = "mulreadonly";
    }
    else if (strReadOnly == "2")
    {
        tempText = "code8";
    }
    else if (strReadOnly == "5")
    {
        tempText = "coolConfirmBox";
    }
    else
    {
        tempText = "mulcommon";
    }
    return tempText;
}

//==============================================================================

/**
 * 方法：显示多行输入的错误信息
 * 输入：strError 需要显示的错误信息
 *       cObj     实例指针
 */
function _DisplayError(strError, cObj)
{
    cObj.errorString = strError + " ";
    alert(strError);
}

//==============================================================================

/**
 * 方法：得到指定行列的数据(外部/内部调用)
 * 输入：cRow:  行
 *       cCol:  列
 *       cObjInstance Muline对象，外部调用时为空；内部调用时不能为空
 *       顶级函数一般是外部调用，则cObjInstance为空，内部tObjInstance=this;
 *       则调用子函数时，将当前对象this作为cObjInstance参数传入调用子函数
 * 输出：指定行，列的值
 */
function _GetRowColData(cRow, cCol, cObjInstance)
{
    var tStr, tReturn;
    var tObjInstance; //对象指针
    if (cObjInstance == null)
    {
        tObjInstance = this;
    }
    else
    {
        tObjInstance = cObjInstance;
    }
    tObjInstance.errorString = "";

    if (cRow < 0 || cRow >= tObjInstance.mulLineCount)
    {
        alert("在 MulLine.js --> getRowColData() 中指定了错误的行：" + cRow);
        tReturn = "";
        return trim(tReturn);
    }
    if (cCol < 0 || cCol >= tObjInstance.colCount)
    {
        alert("在 MulLine.js --> getRowColData() 中指定了错误的列：" + cCol);
        tReturn = "";
        return trim(tReturn);
    }
    try
    {
        //注意：必须添加下面的判断条件，否则在初始化为0行时，会出现异常
        if (tObjInstance.mulLineCount == 1)
        {
            _ResumeState();
            try
            {
                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').value";
                tReturn = eval(tStr);
            }
            catch (ex)
            {
                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[0].value";
                tReturn = eval(tStr);
            }
            //如果得到的值为null或者undefined，说明当行数为1时，有下面的可能：
            //如果是从多行删除到一行，那么可能会继续认为是数组的一个元素，所以还要加下标
            //除上述外，可能在javaScript中还有其它因素，因此，可以看作对各种情况的修正：
            try
            {
                //把undefined单独提出来，是因为有的JS不支持undefined，所以要捕获例外
                if (tReturn == undefined)
                try
                {
                    tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[0].value";
                    tReturn = eval(tStr);
                }
                catch (ex)
                {
                    tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').value";
                    tReturn = eval(tStr);
                }
            }
            catch (ex){}
            if (tReturn == null)
            {
                try
                {
                    tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[0].value";
                    tReturn = eval(tStr);
                }
                catch (ex)
                {
                    tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').value";
                    tReturn = eval(tStr);
                }
            }
            //即使通过上面的转换，还是存在漏洞，返回值依然可能是null或者undefined
            //因此对传出去的值应该先判断是否null或者undefined
        }
    }
    catch (ex){}
    try
    {
        if (tObjInstance.mulLineCount > 1)
        {
            tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[" + cRow + "].value";
            tReturn = eval(tStr);
        }
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _GetRowColData 函数中发生异常：" + ex, tObjInstance);
    }
    //通过转换后，依然会有特殊值。对javascript的这个特点尚不清楚，为安全性必须加以验证
    try
    {
        //把undefined单独提出来，是因为有的JS不支持undefined，所以要捕获例外
        if (tReturn == undefined)
        {
            tReturn = "";
        }
    }
    catch (ex){}
    try
    {
        if (tReturn == null)
        {
            tReturn = "";
        }
    }
    catch (ex)
    {
        tReturn = "";
    }
    return trim(tReturn);
}

//==============================================================================

/**
 * 方法：得到指定行的数据(外部/内部调用)
 * 输入：cRow:  行
 *       cObjInstance Muline对象，外部调用时为空；内部调用时不能为空
 *       顶级函数一般是外部调用，则cObjInstance为空，内部tObjInstance=this;
 *       则调用子函数时，将当前对象this作为cObjInstance参数传入调用子函数
 * 输出：返回该行的输组
 */
function _GetRowData(cRow, cObjInstance)
{
    var tStr, tReturn, n, cCol;
    var tObjInstance; //对象指针
    if (cObjInstance == null)
    {
        tObjInstance = this;
    }
    else
    {
        tObjInstance = cObjInstance;
    }
    tObjInstance.errorString = "";
    if (cRow < 0 || cRow >= tObjInstance.mulLineCount)
    {
        alert("在 MulLine.js --> getRowColData() 中指定了错误的行：" + cRow);
        tReturn = "";
        return trim(tReturn);
    }
    var iArray = new Array(); //返回的数组
    for (n = 1; n < tObjInstance.colCount; n++)
    {
        cCol = n;
        try
        {
            //注意：必须添加下面的判断条件，否则在初始化为0行时，会出现异常
            if (tObjInstance.mulLineCount == 1)
            {
                _ResumeState();
                try
                {
                    tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').value";
                    tReturn = eval(tStr);
                }
                catch (ex)
                {
                    tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[0].value";
                    tReturn = eval(tStr);
                }
                //如果得到的值为null或者undefined，说明当行数为1时，有下面的可能：
                //如果是从多行删除到一行，那么可能会继续认为是数组的一个元素，所以还要加下标
                //除上述外，可能在javaScript中还有其它因素，因此，可以看作对各种情况的修正：
                try
                {
                    //把undefined单独提出来，是因为有的JS不支持undefined，所以要捕获例外
                    if (tReturn == undefined)
                    try
                    {
                        tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[0].value";
                        tReturn = eval(tStr);
                    }
                    catch (ex)
                    {
                        tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').value";
                        tReturn = eval(tStr);
                    }
                }
                catch (ex){}
                if (tReturn == null)
                {
                    try
                    {
                        tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[0].value";
                        tReturn = eval(tStr);
                    }
                    catch (ex)
                    {
                        tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').value";
                        tReturn = eval(tStr);
                    }
                }
                //即使通过上面的转换，还是存在漏洞，返回值依然可能是null或者undefined
                //因此对传出去的值应该先判断是否null或者undefined
            }
        }
        catch (ex){}
        try
        {
            if (tObjInstance.mulLineCount > 1)
            {
                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[" + cRow + "].value";
                tReturn = eval(tStr);
            }
        }
        catch (ex)
        {
            _DisplayError("在 MulLine.js --> _GetRowColData 函数中发生异常：" + ex, tObjInstance);
            return;
        }
        //通过转换后，依然会有特殊值。对javascript的这个特点尚不清楚，为安全性必须加以验证
        try
        {
            //把undefined单独提出来，是因为有的JS不支持undefined，所以要捕获例外
            if (tReturn == undefined)
            {
                tReturn = "";
            }
        }
        catch (ex){}
        try
        {
            if (tReturn == null)
            {
                tReturn = "";
            }
        }
        catch (ex)
        {
            tReturn = "";
        }
        iArray[n - 1] = trim(tReturn);
    } //end for
    return iArray;
}

//==============================================================================

/**
 * 方法：设置指定行列的数据(外部/内部调用)
 * 输入：cRow:  行
 *       cCol:  列
 *       cData: 数据
 *       cObjInstance Muline对象，外部调用时为空；内部调用时不能为空
 *       顶级函数一般是外部调用，则cObjInstance为空，内部tObjInstance=this;
 *       则调用子函数时，将当前对象this作为cObjInstance参数传入调用子函数
 */
function _SetRowColData(cRow, cCol, cData, cObjInstance)
{
    var tStr;
    var tObj;
    var tReturn = false;
    var tObjInstance; //对象指针
    if (cObjInstance == null)
    {
        tObjInstance = this;
    }
    else
    {
        tObjInstance = cObjInstance;
    }
    tObjInstance.errorString = "";
    if (cData == null)
    {
        cData = "";
    }
    if (cRow < 0 || cRow >= tObjInstance.mulLineCount)
    {
        alert("在 MulLine.js --> setRowColData() 时指定了错误的行:" + cRow);
        return tReturn;
    }
    if (cCol < 0 || cCol >= tObjInstance.colCount)
    {
        alert("在 MulLine.js --> setRowColData() 时指定了错误的列:" + cCol);
        return tReturn;
    }
    try
    {
        var newData = replace(cData, "\r\n", "");
        // 2006-02-16 Kevin
        // calling replace(cData, "\\", "\\\\") results in IE hangs
        // so we have to replace "\\" with "\\\\" by following method
        if (chkzh(newData) == false)
        {
            cData = newData;
            newData = "";
            var vIndex = 0;
            var vSubStr = "";
            for (vIndex = 0; vIndex < cData.length; vIndex++)
            {
                vSubStr = cData.substring(vIndex, vIndex + 1);
                if (vSubStr == "\\")
                {
                    newData += "\\\\";
                }
                else
                {
                    newData += vSubStr;
                }
            }
        }
        //注意：必须添加下面的判断条件，否则在初始化为0行时，会出现异常
        if (tObjInstance.mulLineCount == 1)
        {
            _ResumeState();
            try
            {
                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "').value='" + newData + "'";
            }
            catch (ex)
            {
                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[0].value='" + newData + "'";
            }
        }
        else
        if (tObjInstance.mulLineCount > 1)
        {
            tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + cCol + "')[" + cRow + "].value='" + newData + "'";
        }
        eval(tStr);
        tReturn = true;
    }
    catch (ex)
    {
        //_DisplayError("在 MulLine.js --> _SetRowColData 函数中发生异常：" + ex, tObjInstance);//edit by yaory
    }
    return tReturn;
}

//==============================================================================

/**
 * 方法：清空 Muline 的数据(可外部/内部调用)
 * 输入：strPageName:  页面上span后面所跟的字符串
 *       cObjInstance Muline对象，外部调用时为空；内部调用时不能为空
 *       顶级函数一般是外部调用，则cObjInstance为空，内部tObjInstance=this;
 *       则调用字函数时，将当前对象this作为cObjInstance参数传入调用子函数
 */
function _ClearData(strPageName, cObjInstance)
{
    var t_StrPageName = strPageName || this.instanceName; //实例名称
    var strNewText = "";
    var tObjInstance; //对象指针
    if (cObjInstance == null)
    {
        tObjInstance = this;
    }
    else
    {
        tObjInstance = cObjInstance;
    }
    tObjInstance.errorString = "";
    try
    {
        document.all("span" + t_StrPageName + "Field").innerHTML = strNewText;
        tObjInstance.mulLineCount = 0;
        tObjInstance.maxSpanID = - 1;
        try
        {
            if (tObjInstance.checkFlag == 1 && tObjInstance.canChk == 1)
            {
                tObjInstance.checkFlag = 0;
                eval(tObjInstance.formName + ".all('checkAll" + tObjInstance.instanceName + "').checked=false");
            }
        }
        catch (ex){}
    }
    catch (ex)
    {
        //_DisplayError("在 MulLine.js --> _clearData 函数中发生异常：" + ex, tObjInstance);
    }
}

//==============================================================================

/**
 * 方法：将 Muline 的空白行清处(可以外部/内部调用)
 * 输入：strPageName:页面上Muline的对象名，不能为空
 *       cObjInstance Muline对象，外部调用时为空；内部调用时不能为空
 *       顶级函数一般是外部调用，则cObjInstance为空，内部tObjInstance=this;
 *       则调用字函数时，将当前对象this作为cObjInstance参数传入调用子函数
 */
function _DelBlankLine(strPageName, cObjInstance)
{
    var t_StrPageName = strPageName || this.instanceName; //实例名称
    var tObjInstance;
    if (cObjInstance == null)
    {
        tObjInstance = this;
    }
    else
    {
        tObjInstance = cObjInstance;
    }
    tObjInstance.errorString = "";
    var rowCount = tObjInstance.mulLineCount; //行数
    var colCount = tObjInstance.colCount; //列数
    var i, j;
    var blankFlag = true; //空行标志
    var lineSpanID; //行的spanID
    var data = "";
    try
    {
        //循环查询每一行是否为空行,即该行的每一列都为空，除了0列（序号列）
        for (i = 0; i < rowCount; i++)
        //从行开始循环,0行开始
        {
            for (j = 1; j < colCount; j++)
            //从列开始循环，1列开始
            {
                data = _GetRowColData(i, j, tObjInstance);
                if (data != null && data != "")
                //如果不为空，空行标志设为false
                {
                    blankFlag = false;
                    break;
                }
            }
            if (blankFlag)
            {
                lineSpanID = _FindSpanID(i, tObjInstance); //得到该行的spanID
                _DeleteOne(t_StrPageName, lineSpanID, tObjInstance); //删除这一行
                //删除一行，循环减一
                rowCount = rowCount - 1;
                //回退一行检查
                i = i - 1;
            }
            blankFlag = true;
        }
        try
        {
            if (tObjInstance.checkFlag == 1 && tObjInstance.canChk == 1)
            {
                tObjInstance.checkFlag = 0;
                eval(tObjInstance.formName + ".all('checkAll" + tObjInstance.instanceName + "').checked=false");
            }
        }
        catch (ex){}
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _DelBlankLine 函数中发生异常：" + ex, tObjInstance);
    }
}

//==============================================================================

/**
 * 方法：将Muline的选中行清空(可以外部/内部调用)
 * 输入：strPageName:页面上Muline的对象名，不能为空
 *       cObjInstance Muline对象，外部调用时为空；内部调用时不能为空
 *       顶级函数一般是外部调用，则cObjInstance为空，内部tObjInstance=this;
 *       则调用字函数时，将当前对象this作为cObjInstance参数传入调用子函数
 */
function _DelCheckTrueLine(strPageName, cObjInstance)
{
    var t_StrPageName = strPageName || this.instanceName; //实例名称
    var tObjInstance;
    if (cObjInstance == null)
    {
        tObjInstance = this;
    }
    else
    {
        tObjInstance = cObjInstance;
    }
    tObjInstance.errorString = "";
    var rowCount = tObjInstance.mulLineCount; //行数
    var i;
    var checkTrueFlag = true; //选中行标志
    var lineSpanID; //行的spanID
    if (tObjInstance.canChk == 0)
    {
        alert("no checkBox!");
        return;
    }
    try
    {
        //循环查询每一行是否为空行,即该行的每一列都为空，除了0列（序号列）
        for (i = 0; i < rowCount; i++)
        //从行开始循环,0行开始
        {
            checkTrueFlag = _GetChkNo(i, tObjInstance);
            if (checkTrueFlag)
            {
                lineSpanID = _FindSpanID(i, tObjInstance); //得到该行的spanID
                _DeleteOne(t_StrPageName, lineSpanID, tObjInstance); //删除这一行
                //删除一行，循环减一
                rowCount = rowCount - 1;
                //回退一行检查
                i = i - 1;
            }
        }
        try
        {
            if (tObjInstance.checkFlag == 1)
            {
                tObjInstance.checkFlag = 0;
                eval(tObjInstance.formName + ".all('checkAll" + tObjInstance.instanceName + "').checked=false");
            }
        }
        catch (ex){}
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _DelBlankLine 函数中发生异常：" + ex, tObjInstance);
    }
}

//==============================================================================

/**
 * 方法：将Muline的选中的radiobox行清空(可以外部/内部调用)
 * 输入：strPageName:页面上Muline的对象名，不能为空
 *       cObjInstance Muline对象，外部调用时为空；内部调用时不能为空
 *       顶级函数一般是外部调用，则cObjInstance为空，内部tObjInstance=this;
 *       则调用字函数时，将当前对象this作为cObjInstance参数传入调用子函数
 */
function _DelRadioTrueLine(strPageName, cObjInstance)
{
    var t_StrPageName = strPageName || this.instanceName; //实例名称
    var tObjInstance;
    if (cObjInstance == null)
    {
        tObjInstance = this;
    }
    else
    {
        tObjInstance = cObjInstance;
    }
    tObjInstance.errorString = "";
    var rowCount = tObjInstance.mulLineCount; //行数
    var selno = 0; //选中的行数
    var lineSpanID; //行的spanID
    if (tObjInstance.canSel == 0)
    {
        alert("no radioBox!");
        return;
    }
    try
    {
        selno = _GetSelNo(tObjInstance);
        if (selno == 0)
        {
            selno = 1;
        }
        lineSpanID = _FindSpanID(selno - 1, tObjInstance); //得到该行的spanID
        _DeleteOne(t_StrPageName, lineSpanID, tObjInstance); //删除这一行
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _DelRadioTrueLine 函数中发生异常：" + ex, tObjInstance);
    }
}

//==============================================================================

/**
 * 方法：         返回指定行的SpanID(可外部/内部调用)
 * 输入：         cRow:  指定的行数
 *                cObjInstance Muline对象，外部调用时为空；内部调用时不能为空
 *                顶级函数一般是外部调用，则cObjInstance为空，内部tObjInstance=this;
 *                则调用字函数时，将当前对象this作为cObjInstance参数传入调用子函数
 * 输出：         tReturn：指定行的span值
 * 简要说明：     在行模板中删除一行的标志"-"后面，添加隐藏的INPUT域，其name为
 *                this.instanceName+"SpanID'，其value为对应该行的span值
 *                得到该行的span值后，传给_DeleteOne()函数，即将该行删除
 *                目的是为了动态删除多个符合条件的行，通过循环实现
 */
function _FindSpanID(cRow, cObjInstance)
{
    var tStr;
    var tReturn = "";
    var tObjInstance;
    if (cObjInstance == null)
    {
        tObjInstance = this;
    }
    else
    {
        tObjInstance = cObjInstance;
    }
    tObjInstance.errorString = "";
    if (cRow < 0 || cRow >= tObjInstance.mulLineCount)
    {
        alert("在 MulLine.js --> findSpanID() 时指定了错误的行:" + cRow);
        return tReturn;
    }
    try
    {
        //注意：必须添加下面的判断条件，否则在初始化为0行时，会出现异常
        if (tObjInstance.mulLineCount == 1)
        {
            _ResumeState();
            try
            {
                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + "SpanID').value";
                tReturn = eval(tStr);
            }
            catch (ex)
            {
                tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + "SpanID')[0].value";
                tReturn = eval(tStr);
            }
            //如果得到的值为null，说明当行数为1时，有下面的可能：
            //如果是从多行删除到一行，那么可能会继续认为这个单独行是数组的一个元素，所以还要加下标
            //除上述外，可能在javaScript中还有其它因素，因此，下面可以看作对各种情况的修正：
            try
            {
                //把undefined单独提出来，是因为有的JS不支持undefined，所以要捕获例外
                if (tReturn == undefined)
                try
                {
                    tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + "SpanID')[0].value";
                    tReturn = eval(tStr);
                }
                catch (ex)
                {
                    tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + "SpanID').value";
                    tReturn = eval(tStr);
                }
            }
            catch (ex){}
            if (tReturn == null)
            {
                try
                {
                    tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + "SpanID')[0].value";
                    tReturn = eval(tStr);
                }
                catch (ex)
                {
                    tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + "SpanID').value";
                    tReturn = eval(tStr);
                }
            }
        }
        else
        if (tObjInstance.mulLineCount > 1)
        {
            tStr = tObjInstance.formName + ".all('" + tObjInstance.instanceName + "SpanID')[" + cRow + "].value";
            tReturn = eval(tStr);
        }
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _FindSpanID 函数中发生异常：" + ex, tObjInstance);
    }
    return tReturn;
}

//==============================================================================

/**
 * 方法：检验表格中输入的值是否符合规范
 * 输入：可以为空，或对象名
 */
function _CheckValue2(strPageName, cObjInstance)
{
    var t_StrPageName = strPageName || this.instanceName; //实例名称
    var tObj = cObjInstance || this;
    _DelBlankLine(t_StrPageName, tObj); //清除空行
    var tRule = "";
    var strInfo = "";
    var rowNo = 0;
    var tReturn;
    if (tObj.mulLineCount == 0)
    {
        //return false;
        return true;
    }
    else if (tObj.mulLineCount == 1)
    {
        for (var i = 1; i < tObj.colCount; i++)
        //从第1列开始，第0列是序列，不检验
        {
            tRule = eval(tObj.formName + ".all('" + t_StrPageName + "verify" + i + "').value");
            if (tRule == null || tRule == "")
            {
                continue;
            }
            else
            {
                try
                {
                    strInfo = "第一行的" + tRule;
                    var dd = tObj.formName + "." + t_StrPageName + i;
                    if (!verifyElementWrap2(strInfo, _GetRowColData(n, i, tObj), dd))
                    {
                        return false;
                    }
                    //如果错误，返回
                }
                catch (ex)
                {
                    alert("请确认 verifyInput.js 文件被包含或数据库连接正常");
                    return false;
                }
            }
        }
    }
    else
    {
        for (var i = 1; i < tObj.colCount; i++)
        //从第1列开始，第0列是序列，不检验
        {
            try
            {
                //注意:初始化时，如果不检验该列，在textTitle中设置verify=''
                tRule = eval(tObj.formName + ".all('" + t_StrPageName + "verify" + i + "').value");
                if (tRule == null || tRule == "")
                {
                    continue;
                }
                //即不校验
                else
                {
                    for (var n = 0; n < tObj.mulLineCount; n++)
                    {
                        // 外部函数，请察看verifyInput.js parm1:位置|检验规则 parm2: 要检验的值(即第N行i列的值)
                        try
                        {
                            rowNo = n + 1;
                            strInfo = "第" + rowNo + "行的" + tRule; //提示信息中确定第几行
                            //if(!verifyElement(strInfo,_GetRowColData(n,i,tObj)))
                            var dd = tObj.formName + "." + t_StrPageName + i + "[" + n + "]";
                            if (!verifyElementWrap2(strInfo, _GetRowColData(n, i, tObj), dd))
                            {
                                return false;
                            }
                            //如果错误，返回
                        }
                        catch (ex)
                        {
                            alert("请确认verifyInput.js 文件被包含或数据库连接正常");
                            return false;
                        }
                    }
                }
            }
            catch (ex)
            {
                alert("_CheckValue函数出错");
                return false;
            }
        }
    }
    return true;
}

//==============================================================================

/**
 * 方法：检验表格中输入的值是否符合规范
 * 输入：可以为空，或对象名
 */
function _CheckValue(strPageName, cObjInstance)
{
    var t_StrPageName = strPageName || this.instanceName; //实例名称
    var tObj = cObjInstance || this;
    _DelBlankLine(t_StrPageName, tObj); //清除空行
    var tRule = "";
    var strInfo = "";
    var rowNo = 0;
    var tReturn;
    if (tObj.mulLineCount == 0)
    {
        return true;
    }
    for (var i = 1; i < tObj.colCount; i++)
    //从第1列开始，第0列是序列，不检验
    {
        try
        {
            //注意:初始化时，如果不检验该列，在textTitle中设置verify=''
            tRule = eval(tObj.formName + ".all('" + t_StrPageName + "verify" + i + "').value");
            if (tRule == null || tRule == "")
            {
                continue;
            }
            //即不校验
            else
            {
                for (var n = 0; n < tObj.mulLineCount; n++)
                {
                    // 外部函数，请察看verifyInput.js parm1:位置|检验规则 parm2: 要检验的值(即第N行i列的值)
                    try
                    {
                        rowNo = n + 1;
                        strInfo = "第" + rowNo + "行的" + tRule; //提示信息中确定第几行
                        if (!verifyElement(strInfo, _GetRowColData(n, i, tObj)))
                        {
                            return false;
                        }
                        //如果错误，返回
                    }
                    catch (ex)
                    {
                        alert("请确认 verifyInput.js 文件被包含或数据库连接正常");
                        return false;
                    }
                }
            }
        }
        catch (ex)
        {
            alert("_CheckValue函数出错");
            return false;
        }
    }
    return true;
}

//==============================================================================

/**
 * 辅助函数(内部调用):
 * 该函数可以去掉，或者扩展成其它附加的功能
 * 当MulLine从多行减少至一行时，再调用内部函数
 * 是会出错的。可能的原因是：多行是数组形式，单行是一个变量形式
 * 当由多行编成单行后又接着调用内部函数，其剩下的单列还保持数组的
 * 形式，即[0]，而我们在内部判断时，当行数=1，则直接用变量形式调用
 * 会出现错误。该函数放在xxx.mulLineCount==1后。即调用该对象的内部
 * 函数，以便再调用相关函数时。该单行形式成为变量形式
 * 输入：可以为空，或对象名
 */
function _ResumeState(cObjInstance)
{
    var tObjInstance;
    if (cObjInstance == null)
    {
        tObjInstance = this;
    }
    else
    {
        tObjInstance = cObjInstance;
    }
    tObjInstance.errorString = "";
    tObjInstance.state = 0;
}

//==============================================================================

/**
 * 方法：检验 MulLine 中某行某列是否得到焦点
 * 输入：行数(从 0 开始)
 * 输入：列数(从 1 开始)
 * 输出：true or false
 * 备注：XinYQ added on 2006-11-14
 */
function _GetFocus(nRowNumber, nColNumber, tObjInstance)
{
    try
    {
        var oInstance = this; //var oInstance = tObjInstance || this;
        if (oInstance.lastFocusRowNo != nRowNumber || oInstance.lastFocusColNo != nColNumber)
        {
            return false;
        }
        return true;
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _GetFocus 函数中发生异常：" + ex, tObjInstance);
    }
}

//==============================================================================

/**
 * 方法：设置 MulLine 中某行某列得到焦点
 * 输入：行数(从 0 开始)
 * 输入：列数(从 1 开始)
 * 备注：XinYQ rewrote on 2006-11-08
 */
function _SetFocus(nRowNumber, nColNumber, tObjInstance)
{
    try
    {
        var oInstance = this; //var oInstance = tObjInstance || this;
        if (nRowNumber == null || nRowNumber < 0 || oInstance.mulLineCount <= 0)
        {
            nRowNumber = 0;
        }
        else if (nRowNumber >= oInstance.mulLineCount)
        {
            nRowNumber = oInstance.mulLineCount - 1;
        }
        if (nColNumber == null || nColNumber <= 0 || nColNumber > oInstance.colCount)
        {
            nColNumber = 1;
        }
        if (nRowNumber > 0)
        {
            document.getElementsByName(oInstance.instanceName + nColNumber)[nRowNumber].focus();
        }
        return true;
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _SetFocus 函数中发生异常：" + ex, tObjInstance);
    }
}

//==============================================================================

/**
 * 如果 MulLine中某行文字超出显示范围，则显示title
 */
function _showtitle(obj)
{
    obj.title = obj.value;
}

//==============================================================================

function AllowSortFun(obj, i)
{
    var sortturnpage = obj.SortPage;
    if (sortturnpage == null || sortturnpage == "")
    {
        alert("请先查询！");
        return false;
    }
    sortturnpage.allowsort(i);
}

//==============================================================================

/**
 * 增加页数显示，根据页号跳转到相应页
 */
function _SetPageMark(cTurnPage)
{
    var tStrPageName = this.instanceName;
    try
    {
        if (this.SortPage == null || this.SortPage == "")
        {
            return false;
        }
    }
    catch (ex)
    {
        alert(ex);
        return false;
    }
    var tTotalPageNum = Math.ceil(cTurnPage.queryAllRecordCount / cTurnPage.pageLineNum);
    var tPageIndex = cTurnPage.pageIndex + 1;
    var tHTML = document.all("span" + tStrPageName).innerHTML;
    if (tHTML == "" || tHTML == null)
    {
        return;
    }
    tHTML += "<div align=right>第&nbsp;" + tPageIndex + "/" + tTotalPageNum + "&nbsp;页&nbsp;&nbsp;转到&nbsp;<input type='common' style='{border: 1px #9999CC solid;height: 18px}' name='GotoPage" + tStrPageName + "' size='3'>&nbsp;页<input type='button' class=cssbutton value='->' onclick='" + tStrPageName + ".SortPage.gotoPage(document.all.GotoPage" + tStrPageName + ".value);'></div>";
    document.all("span" + tStrPageName).innerHTML = tHTML;
}

//==============================================================================

/**
 * 方法：返回选中的 CheckBox 总个数
 * 输入：无
 * 输出：整数
 * 备注：XinYQ added on 2006-05-11
 */
function _GetChkCount(tObjInstance)
{
    var oInstance = this; //var oInstance = tObjInstance || this;
    var nSelectedCount = 0;
    try
    {
        for (var i = 0; i < oInstance.mulLineCount; i++)
        {
            if (this.getChkNo(i))
            {
                nSelectedCount += 1;
            }
        }
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _GetChkCount 函数中发生异常:  " + ex, tObjInstance);
    }
    return nSelectedCount;
}

//==============================================================================

/**
 * 方法：使指定的 radioBox 变成选中的状态
 * 输入：行数(从 1 开始)
 * 输出：如果选中，返回 true，否则返回 false
 * 备注：XinYQ added on 2006-05-15
 */
function _SelOneRow(nRowNumber, tObjInstance)
{
    try
    {
        var oInstance = this; //var oInstance = tObjInstance || this;
        if (oInstance.canSel != 1)
        {
            alert("在 MulLine.js --> _SelOneRow 函数中发生错误：" + oInstance.instanceName + " 不允许单选！ ");
            return false;
        }
        if (nRowNumber == null || nRowNumber <= 0 || oInstance.mulLineCount <= 0 || nRowNumber > oInstance.mulLineCount)
        {
            alert("在 MulLine.js --> _SelOneRow 函数中指定了错误的行：" + nRowNumber + " ");
            return false;
        }
        else
        {
            document.getElementsByName(oInstance.instanceName + "Sel")[nRowNumber - 1].click();
        }
        return true;
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _SelOneRow 函数中发生异常：" + ex, tObjInstance);
    }
}

//==============================================================================

/**
 * 方法：使指定的 checkBox 变成选中的状态
 * 输入：行数(从 1 开始)
 * 输出：如果选中，返回 true，否则返回 false
 * 备注：XinYQ added on 2006-05-15
 */
function _ChkOneRow(nRowNumber, tObjInstance)
{
    try
    {
        var oInstance = this; //var oInstance = tObjInstance || this;
        if (oInstance.canChk != 1)
        {
            alert("在 MulLine.js --> _ChkOneRow 函数中发生错误：" + oInstance.instanceName + " 不允许复选！ ");
            return false;
        }
        if (nRowNumber == null || nRowNumber <= 0 || oInstance.mulLineCount <= 0 || nRowNumber > oInstance.mulLineCount)
        {
            alert("在 MulLine.js --> _ChkOneRow 函数中指定了错误的行：" + nRowNumber + " ");
            return false;
        }
        else
        {
            document.getElementsByName(oInstance.instanceName + "Chk")[nRowNumber - 1].click();
        }
        return true;
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _ChkOneRow 函数中发生异常：" + ex, tObjInstance);
    }
}

//==============================================================================

/**
 * 方法：当其子控件任意一个得到焦点时计算该子控件的行和列
 * 输入：MulLine 对象
 * 输入：子控件对象
 * 输出：无
 * 备注：XinYQ added on 2006-10-10
 */
function _CalcFocusRowColNo(oInstance, oEventCtrl)
{
    try
    {
        if (oInstance == null || oEventCtrl == null || typeof(oInstance) == "undefined" || typeof(oEventCtrl) == "undefined")
        {
            //alert("在 MulLine.js --> _CalcFocusRowColNo 函数中发生错误：实例名和控件名不允许为空！ ");
            return;
        }
        if (oInstance.mulLineCount <= 0)
        {
            oInstance.lastFocusRowNo = -1;
            oInstance.lastFocusColNo = -1;
            return;
        }
        else
        {
            //计算焦点行
            try
            {
                for (var i = 0; i < oInstance.mulLineCount; i++)
                {
                    if (document.getElementsByName(oEventCtrl.name)[i] == oEventCtrl)
                    {
                        oInstance.lastFocusRowNo = i;
                    }
                }
                //alert("lastFocusRowNo = " + oInstance.lastFocusRowNo);
            }
            catch (ex)
            {
                //alert("在 MulLine.js --> _CalcFocusRowColNo 函数中发生错误：计算焦点行异常！ ");
                return;
            }
            //计算焦点列
            try
            {
                var sInstanceName = (oInstance.instanceName != null) ? oInstance.instanceName : oInstance.name;
                var sEventCtrlName = oEventCtrl.name;
                if (sInstanceName != null && typeof(sInstanceName) != "undefined")
                {
                    var sLastFocusColNo = sEventCtrlName.substring(sInstanceName.length);
                    if (sLastFocusColNo != null && isInteger(sLastFocusColNo))
                    {
                        oInstance.lastFocusColNo = parseInt(sLastFocusColNo);
                    }
                    else
                    {
                        oInstance.lastFocusColNo = 0;
                    }
                    //alert("lastFocusColNo = " + oInstance.lastFocusColNo);
                }
            }
            catch (ex)
            {
                //alert("在 MulLine.js --> _CalcFocusRowColNo 函数中发生错误：计算焦点列异常！ ");
                return;
            }
        }
    }
    catch (ex)
    {
        _DisplayError("在 MulLine.js --> _CalcFocusRowColNo 函数中发生异常：", oInstance);
    }
}

//==============================================================================

