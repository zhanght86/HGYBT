/*****************************************************************
 *               Program NAME: ѡȡ���벢��ʾ
 *                 programmer:
 *                Create DATE:
 *             Create address: Beijing
 *                Modify DATE:
 *             Modify address:
 *****************************************************************
 *
 *     ͨ�ô����ѯ����ҳ��,���������صĿ����,����
 *     ������Ҫ��ʾ�����嵥ʱ���ô�ҳ��
 *
 *****************************************************************
 */
//Ѱ��������
var win = searchMainWindow(this);
if (win == false) { win = this; }
//�õ���ű������ݵ��ڴ�����,Ҫ��CVarData.js����Ҫ��һ������ΪVD��֡��
//��ŵ��Ǵ����ݿ�ı���ȡ���ı�������飬���ڲ����ݵĴ��
mVs=win.parent.VD.gVCode;
//�õ���ű������ݵ��ڴ�����,Ҫ��CVarData.js����Ҫ��һ������ΪVD��֡��
//��ŵ���ҳ���ϵı�����λ�ã������ݿ����ȡ������������һЩ�ⲿ��������
mCs=win.parent.VD.gVSwitch;
var _Code_FIELDDELIMITER = "|";	//��֮��ķָ���
var _Code_RECORDDELIMITER = "^";	//��¼֮��ķָ���
var arrayBmCode;	//����������Ĵ���
var arrEasyQuery = new Array();	// ���ʹ��easyQuery()�õ��Ĳ�ѯ�������
var showFirstIndex=0;
var oEventField;	//�����¼���ԭʼ�ؼ�, Added by XinYQ on 2006-10-23

/*************************************************************
 *  ��ʾ���루�ú���Ϊ��ʾ�������ں�����
 *  ����  ��  Field �����¼��Ŀؼ�;
 *            strCodeName ��������;
 *            arrShowCodeObj ��������ʾ����Ӧ�Ŀؼ���
 *            arrShowCodeOrder ������ʾ��Ӧ�ؼ��ͱ���˳��Ķ�Ӧ��ϵ
 *            arrShowCodeFrame ������ʾ��Ӧ��Frameָ��
 *  ����ֵ��  ���û�з���false,���򷵻�true
 *************************************************************
 */
function showCodeList(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven)
{
	if (arrShowCodeObj != null && typeof(arrShowCodeObj) != "undefined")
	{
		oEventField = arrShowCodeObj[0];
	}
	var ex,ey;
	var tCode;
	var Field;
	//����ƥ��Sql��䣬������where�Ӿ��Ժ�Ĳ���
	var strCondition = "";	//����Ĳ�ѯ����ֵ
	var strConditionField = "";	//����Ĳ�ѯ�������ֶ���
	//���Ӵ���ѡ��Ĳ�ѯ����
	if (objCondition != null)
	{
		if (typeof(objCondition) == "object")
		{
			for(var m=0;m<objCondition.length;m++)
			{
				strCondition = strCondition+objCondition[m];
				if(m<objCondition.length-1) strCondition=strCondition+"|";
			}
		}
		else
		{
			strCondition = objCondition;
		}
	}
	if (conditionField != null)
	{
		strConditionField = conditionField;
	}
	//����������ݿ��ѯ���ڶ��β����������
	if (arrShowCodeObj != null)
	{
		//Field  ��һ�����鼯�ϣ���ʾҪ��ʾ�������ҳ���ϵĶ���
		Field = arrShowCodeObj[0];
		setFieldPos(Field);
		oldField = Field;
		oldFieldKey = "";
//		Field.onblur="";
		Field.onblur=closeCodeList;
		if (arrShowCodeOrder == null)
		{
			arrShowCodeOrder = [0];
		}
		if (objShowCodeFrame == null)
		{
			objShowCodeFrame = parent.fraInterface;	//window.self;
		}
		try
		{
			//��һЩ�����ŵ��ͻ��˴�����
			mCs.updateVar("ShowCodeObj","0",arrShowCodeObj);
			mCs.updateVar("ShowCodeOrder","0",arrShowCodeOrder);
			mCs.updateVar("ShowCodeFrame","0",objShowCodeFrame);    //Frame��ָ��
		}
		catch(e)
		{
			//���³�ʼ����������
			mVs=parent.VD.gVCode;
			mCs=parent.VD.gVSwitch;
			var _Code_FIELDDELIMITER = "|";	//��֮��ķָ���
			var _Code_RECORDDELIMITER = "^";	//��¼֮��ķָ���
			var arrayBmCode;	//����������Ĵ���
			var arrEasyQuery = new Array();	// ���ʹ��easyQuery()�õ��Ĳ�ѯ�������
			var showFirstIndex=0;
			showCodeList(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven);
		}
	}
	tCode=searchCode(strCodeName,strCondition,strConditionField);  //�Ӵ�������ȡ����
	//ǿ��ˢ�²�ѯ
	if (refresh)
	{
		tCode = false;
		try
		{
			//����newһ��codeselect�����ʱ�򣬻���������������������ǿ��ˢ�µ�ʱ��Ҳ��Ҫdelete��������
			mVs.deleteVar(strCodeName+strCondition+strConditionField);
			mVs.deleteVar(strCodeName+strCondition+strConditionField+"Select");
		}
		catch(ex){}
	}
	if (tCode == false && arrShowCodeObj != null)
	{
		//����������ˣ���ȡ����
		requestServer(strCodeName, strCondition, strConditionField, showWidth,changeEven);
		//��js��ֹ��ת������������������ҳ��ִ�У���ҳ���ѯ���ݺ󱣴浽ҳ���ϵ��ڴ�����
		//���ٴε��ø�js,������initializeCode��Ȼ��showCodeList,��ע��initializeCode�����ٵ����ã�
		//����ִ��js��������δ������Ѿ����˴��룬����������������
		return false;
	}
	//���������õ�������ͱ������ƴ��룬��ʾ����
	showCodeList1(tCode,strCodeName,strCondition,strConditionField,showWidth,changeEven);
	//���Ҫ��ʾ�����ҳ��������
	//��ʾ���������ҳ������е�ֵ��ʹ�����λ����ӦField�е�ֵ����һ��
	if(Field!=null)
	{
		goToSelect(strCodeName,Field,strCondition,strConditionField);
	}
	//XinYQ added on 2006-05-16
	//�������������б����, ��ͬʱ�������ʾ�������б�����Ӧ������
	var oFieldCodeArea, oFieldCodeName;
	try
	{
		oFieldCodeArea = arrShowCodeObj[0];
		oFieldCodeName = arrShowCodeObj[1];
	}
	catch(ex){}
	if (oFieldCodeArea.value == null || typeof(oFieldCodeArea) == "undefined" || oFieldCodeArea.value == "")
	{
		try
		{
			oFieldCodeName.value = "";
		}
		catch(ex){}
	}
	return true;
}

/*************************************************************
 *  ��ʾ���루�ú���Ϊ��ʾ�������ں���
 *  ����  ��  Field �����¼��Ŀؼ�;
 *            strCodeName ��������;
 *            arrShowCodeObj ��������ʾ����Ӧ�Ŀؼ���
 *            arrShowCodeOrder ������ʾ��Ӧ�ؼ��ͱ���˳��Ķ�Ӧ��ϵ
 *            arrShowCodeFrame ������ʾ��Ӧ��Frameָ��
 *  ����ֵ��  ���û�з���false,���򷵻�true
 *************************************************************
 */
function showCodeListEx(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven)
{
	if (arrShowCodeObj != null && typeof(arrShowCodeObj) != "undefined")
	{
		oEventField = arrShowCodeObj[0];
	}
	var ex,ey;
	var tCode;
	var Field;
	var strCondition = "";
	var strConditionField = "";
	//���Ӵ���ѡ��Ĳ�ѯ������MINIM
	if (objCondition != null)
	{
		if (typeof(objCondition) == "object")
		{
			strCondition = objCondition.value;
		}
		else
		{
			strCondition = objCondition;
		}
	}
	if (conditionField != null)
	{
		strConditionField = conditionField;
	}
	//����������ݿ��ѯ���ڶ��β����������
	if (arrShowCodeObj != null)
	{
		Field=arrShowCodeObj[0];
		oldField = Field;	//����������⣬MINIM����
//		Field.onblur="";
		Field.onblur=closeCodeList;
		oldFieldKey = "";
		setFieldPos(Field);
		if (arrShowCodeOrder == null)
		{
			arrShowCodeOrder = [0];
		}
		if (objShowCodeFrame == null)
		{
			objShowCodeFrame = parent.fraInterface;//window.self;
		}
		try
		{
			//��һЩ�����ŵ��ͻ��˴�����
			mCs.updateVar("ShowCodeObj","0",arrShowCodeObj);
			mCs.updateVar("ShowCodeOrder","0",arrShowCodeOrder);
			mCs.updateVar("ShowCodeFrame","0",objShowCodeFrame);  //Frame��ָ��
		}
		catch(e)
		{
			//���³�ʼ����������
			mVs=parent.VD.gVCode;
			mCs=parent.VD.gVSwitch;
			var _Code_FIELDDELIMITER = "|";	//��֮��ķָ���
			var _Code_RECORDDELIMITER = "^";	//��¼֮��ķָ���
			var arrayBmCode;	//����������Ĵ���
			var arrEasyQuery = new Array();	// ���ʹ��easyQuery()�õ��Ĳ�ѯ�������
			var showFirstIndex=0;
			showCodeListEx(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven);
		}
	}
	tCode=searchCode(strCodeName,strCondition,strConditionField);	//�Ӵ�������ȡ����
	//ǿ��ˢ�²�ѯ
	if (refresh)
	{
		tCode = false;
		try
		{
			mVs.deleteVar(strCodeName+strCondition+strConditionField);
			mVs.deleteVar(strCodeName+strCondition+strConditionField+"Select");
		}
		catch(ex){}
	}
	if (tCode == false && arrShowCodeObj != null)
	{
		tCode=initializeCodeEx(strCodeName,Field);
		try
		{
			if(tCode==false) return false;
		}
		catch(ex1)
		{
			return false;
		}
	}
	showCodeList1(tCode,strCodeName,strCondition,strConditionField,showWidth,changeEven);
	if(Field!=null)
	{
		goToSelect(strCodeName,Field,strCondition,strConditionField);
	}
	//XinYQ added on 2006-05-16
	//�������������б����, ��ͬʱ�������ʾ�������б�����Ӧ������
	var oFieldCodeArea, oFieldCodeName;
	try
	{
		oFieldCodeArea = arrShowCodeObj[0];
		oFieldCodeName = arrShowCodeObj[1];
	}
	catch(ex){}
	if (oFieldCodeArea.value == null || typeof(oFieldCodeArea) == "undefined" || oFieldCodeArea.value == "")
	{
		try
		{
			oFieldCodeName.value = "";
		}
		catch(ex){}
	}
	return true;
}

function initClientCode(cCodeName,cField)
{}

/*************************************************************
 *  ���Ҵ���(���̰����¼�)
 *  ����  ��  Field ��Ҫ��ʾ����Ŀؼ�;
 *            strCodeName ��������(Lx_Code);
 *            intFunctionIndex ��Ҫ��ֵ�ؼ���˳�� 1-ǰһ���ͱ���
 *                                                2-����ͺ�һ��;
 *            stationCode ����������վ.
 *  ����ֵ��  string  ��code �� null
 *************************************************************
 */
function showCodeListKey(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven)
{
	if (arrShowCodeObj != null && typeof(arrShowCodeObj) != "undefined")
	{
		oEventField = arrShowCodeObj[0];
	}
	var ex,ey,i,intElementIndex;
	var Field;
	var strCondition = "";
	var strConditionField = "";
	Field=arrShowCodeObj[0];
	oldFieldKey = Field;
	oldField = "";
	//���Ӵ���ѡ��Ĳ�ѯ������MINIM
	if (objCondition != null)
	{
		if (typeof(objCondition) == "object")
		{
			//strCondition = objCondition.value; /*deleted by liurx 2006-01-24*/
			for(var m=0;m<objCondition.length;m++)
			{
				strCondition = strCondition+objCondition[m];
				if(m<objCondition.length-1) strCondition=strCondition+"|";
			}
		}
		else
		{
			strCondition = objCondition;
		}
	}
	if (conditionField != null)
	{
		strConditionField = conditionField;
	}
	eobj = window.event;
	key  = eobj.keyCode;
	if (  document.all("spanCode").style.display=="" && (key == 13 || key==16))
	{
		document.all("codeselect").focus();
		document.all("codeselect").onclick();
		try { Field.focus(); } catch(ex){}
	}
	if ( document.all("spanCode").style.display=="" && (key == 38 || key == 40))
	{
		Field.onblur=null;
		document.all("codeselect").focus();
		try
		{
			if(key == 38)
			{
			    goToPreciousSelect(strCodeName,Field,strCondition,strConditionField);
			}
			if(key == 40)
			{
			    goToNextSelect(strCodeName,Field,strCondition,strConditionField);
			}
		}
		catch(e)
		{
			document.all("codeselect").children[0].selected=true;
		}
	}
	else
	{
//		Field.onblur=null;
		Field.onblur=closeCodeList;
	}
	if (document.all("spanCode").style.display=="none" && (key >= 48 || key==8 || key==46 || key==40 ))
	{
		setFieldPos(Field);
		//�ύ�������Ƽ���Ϣ
		if (arrShowCodeOrder == null)
		{
			arrShowCodeOrder = [0];
		}
		if (objShowCodeFrame == null)
		{
			objShowCodeFrame = parent.fraInterface;//window.self;
		}
		setFieldPos(Field);
		try
		{
			mCs.updateVar("ShowCodeObj","0",arrShowCodeObj);
			mCs.updateVar("ShowCodeOrder","0",arrShowCodeOrder);
			mCs.updateVar("ShowCodeFrame","0",objShowCodeFrame);  //Frame��ָ��
		}
		catch(e)
		{
			//���³�ʼ����������
			mVs=parent.VD.gVCode;
			mCs=parent.VD.gVSwitch;
			var _Code_FIELDDELIMITER = "|";	//��֮��ķָ���
			var _Code_RECORDDELIMITER = "^";	//��¼֮��ķָ���
			var arrayBmCode;	//����������Ĵ���
			var arrEasyQuery = new Array();	// ���ʹ��easyQuery()�õ��Ĳ�ѯ�������
			var showFirstIndex=0;
			showCodeListKey(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven);
		}
		//�ύ�������Ƽ���Ϣ
		getCode(strCodeName,arrShowCodeObj,strCondition,strConditionField,refresh,showWidth,changeEven);
		goToSelect(strCodeName,Field,strCondition,strConditionField);
	}
	else if ( document.all("spanCode").style.display=="" && (key >= 48 || key==8 || key==46))
	{
		if ( Field.value != null)
		{
			goToSelect(strCodeName,Field,strCondition,strConditionField);
		}
	}
	//XinYQ added on 2006-05-16
	//�������������б����, ��ͬʱ�������ʾ�������б�����Ӧ������
	var oFieldCodeArea, oFieldCodeName;
	try
	{
		oFieldCodeArea = arrShowCodeObj[0];
		oFieldCodeName = arrShowCodeObj[1];
	}
	catch(ex){}
	if (oFieldCodeArea.value == null || typeof(oFieldCodeArea) == "undefined" || oFieldCodeArea.value == "")
	{
		try
		{
			oFieldCodeName.value = "";
		}
		catch(ex){}
	}
}

/*************************************************************
 *  ���Ҵ���(���̰����¼�)
 *  ����  ��  Field ��Ҫ��ʾ����Ŀؼ�;
 *            strCodeName ��������(Lx_Code);
 *            intFunctionIndex ��Ҫ��ֵ�ؼ���˳�� 1-ǰһ���ͱ���
 *                                                2-����ͺ�һ��;
 *            stationCode ����������վ.
 *  ����ֵ��  string  ��code �� null
 *************************************************************
 */
function showCodeListKeyEx(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven)
{
	if (arrShowCodeObj != null && typeof(arrShowCodeObj) != "undefined")
	{
		oEventField = arrShowCodeObj[0];
	}
	var ex,ey,i,intElementIndex;
	var Field;
	var strCondition = "";
	var strConditionField = "";
	Field=arrShowCodeObj[0];
	oldFieldKey = Field;
	oldField = "";
	//���Ӵ���ѡ��Ĳ�ѯ������MINIM
	if (objCondition != null)
	{
		if (typeof(objCondition) == "object")
		{
			strCondition = objCondition.value;
		}
		else
		{
			strCondition = objCondition;
		}
	}
	if (conditionField != null)
	{
		strConditionField = conditionField;
	}
    eobj = window.event;
    key  = eobj.keyCode;
    if (  document.all("spanCode").style.display=="" && (key == 13|| key==16) )
    {
       document.all("codeselect").focus();
       document.all("codeselect").onclick();
       try { Field.focus(); } catch(ex){}
    }
	if ( (key == 38 || key == 40) && document.all("spanCode").style.display=="")
	{
		Field.onblur=null;
		document.all("codeselect").focus();
		try
		{
			if(key == 38)
			{
				goToPreciousSelect(strCodeName,Field,strCondition,strConditionField);
			}
			else if(key == 40)
			{
				goToNextSelect(strCodeName,Field,strCondition,strConditionField);
			}
		}
		catch(e)
		{
			document.all("codeselect").children[0].selected=true;
		}
		//showFirstIndex += showFirstIndex;
	}
	else
	{
//		Field.onblur=null;
		Field.onblur=closeCodeList;
    }
	if (  document.all("spanCode").style.display=="none" && (key >= 48 || key==8 || key==46 || key==40 ))
	{
		setFieldPos(Field);
		//�ύ�������Ƽ���Ϣ
		if (arrShowCodeOrder == null)
		{
			arrShowCodeOrder = [0];
		}

		if (objShowCodeFrame == null)
		{
			objShowCodeFrame = parent.fraInterface;//window.self;
		}
		setFieldPos(Field);
		try
		{
			mCs.updateVar("ShowCodeObj","0",arrShowCodeObj);
			mCs.updateVar("ShowCodeOrder","0",arrShowCodeOrder);
			mCs.updateVar("ShowCodeFrame","0",objShowCodeFrame);  //Frame��ָ��
		}
		catch(e)
		{
			//���³�ʼ����������
			mVs=parent.VD.gVCode;
			mCs=parent.VD.gVSwitch;
			var _Code_FIELDDELIMITER = "|";	//��֮��ķָ���
			var _Code_RECORDDELIMITER = "^";	//��¼֮��ķָ���
			var arrayBmCode;	//����������Ĵ���
			var arrEasyQuery = new Array();	// ���ʹ��easyQuery()�õ��Ĳ�ѯ�������
			var showFirstIndex=0;
			showCodeListKeyEx(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven);
		}
		//�ύ�������Ƽ���Ϣ
		showCodeListEx(strCodeName,arrShowCodeObj,arrShowCodeOrder,objShowCodeFrame,objCondition,conditionField,refresh,showWidth,changeEven);
		goToSelect(strCodeName,Field,strCondition,strConditionField);
	}
	else if ( document.all("spanCode").style.display=="" && (key >= 48 || key==8 || key==46))
	{
		if ( Field.value != null)
		{
			goToSelect(strCodeName,Field,strCondition,strConditionField);
		}
	}
	//XinYQ added on 2006-05-16
	//�������������б����, ��ͬʱ�������ʾ�������б�����Ӧ������
	var oFieldCodeArea, oFieldCodeName;
	try
	{
		oFieldCodeArea = arrShowCodeObj[0];
		oFieldCodeName = arrShowCodeObj[1];
	}
	catch(ex){}
	if (oFieldCodeArea.value == null || typeof(oFieldCodeArea) == "undefined" || oFieldCodeArea.value == "")
	{
		try
		{
			oFieldCodeName.value = "";
		}
		catch(ex){}
	}
}

/*************************************************************
 *  ��ʼ������
 *  ����  ��  strCodeName����������
 *  ����ֵ��  boolean   true���ҵ�����Ĵ���   false��δ�ҵ�
 *************************************************************
 */
function initializeCode(strCodeName, codeCondition, conditionField)
{
	var i,i1,j,j1;
	var strValue;	//��ŷ������˷��صĴ�������
	var arrField;
	var arrRecord;
	var arrCode = new Array();	//��ų�ʼ������ʱ��
	var t_Str;
	var strCodeSelect = "";
	clearShowCodeError();
	strValue = getCodeValue(strCodeName);	//�õ��������˶�ȡ������
	arrRecord = strValue.split(_Code_RECORDDELIMITER);	//����ַ������γɷ��ص�����
	t_Str = getStr(arrRecord[0],1,_Code_FIELDDELIMITER);
	//�����Ϊ0��ʾ��������ִ�з�������
	if (t_Str!="0")
	{
		mCs.updateVar("ShowCodeError",getStr(arrRecord[0],2,_Code_FIELDDELIMITER));	//�����󱣴浽�ñ�����
		mCs.updateVar("ShowCodeErrorCode",t_Str);	//�����󱣴浽�ñ�����
		return false;
	}
	i1=arrRecord.length;
	for (i=1;i<i1;i++)
	{
		arrField  = arrRecord[i].split(_Code_FIELDDELIMITER);	//����ַ���,��ÿ����¼���Ϊһ������
		j1=arrField.length;
		arrCode[i-1] = new Array();
		for (j=0;j<j1;j++)
		{
			arrCode[i-1][j] = arrField[j];
		}
		strCodeSelect = strCodeSelect + "<option value=" + arrCode[i-1][0] + ">";
		strCodeSelect = strCodeSelect + arrCode[i-1][0] + "-" + arrCode[i-1][1];
		strCodeSelect = strCodeSelect + "</option>";
	}
	mVs.addArrVar(strCodeName+codeCondition+conditionField,"",arrCode); //�����Ƿ������ݴӷ������˵õ�,�����øñ���
	mVs.addVar(strCodeName+codeCondition+conditionField); //�����Ƿ������ݴӷ������˵õ�,�����øñ���
	mVs.addVar(strCodeName+codeCondition+conditionField+"Select","",strCodeSelect); //�����Ƿ������ݴӷ������˵õ�,�����øñ���
	return true;
}

/*************************************************************
 *                     ��ʼ������
 *  ����  ��  strCodeName����������
 *  ����ֵ��  boolean   true���ҵ�����Ĵ���   false��δ�ҵ�
 *************************************************************
 */
function initializeCodeEx(strCodeName,cField)
{
	var i,i1,j,j1;
	var strValue;	//��ŷ������˷��صĴ�������
	var arrField;
	var arrRecord;
	var arrCode = new Array();	//��ų�ʼ������ʱ��
	var t_Str;
	var strCodeSelect = "";
	clearShowCodeError();
	try
	{
		strValue = cField.CodeData;	//�õ��������˶�ȡ������
	}
	catch(ex)
	{
		alert("û���ڿͻ���������������!");
		return false;
	}
	arrRecord = strValue.split(_Code_RECORDDELIMITER);	//����ַ������γɷ��ص�����
	t_Str = getStr(arrRecord[0],1,_Code_FIELDDELIMITER);
	//�����Ϊ0��ʾ��������ִ�з�������
	if (t_Str!="0")
	{
		mCs.updateVar("ShowCodeError",getStr(arrRecord[0],2,_Code_FIELDDELIMITER));	//�����󱣴浽�ñ�����
		mCs.updateVar("ShowCodeErrorCode",t_Str);	//�����󱣴浽�ñ�����
		return false;
	}
	i1=arrRecord.length;
	for (i=1;i<i1;i++)
	{
		arrField  = arrRecord[i].split(_Code_FIELDDELIMITER);	//����ַ���,��ÿ����¼���Ϊһ������
		j1=arrField.length;
		arrCode[i-1] = new Array();
		for (j=0;j<j1;j++)
		{
			arrCode[i-1][j] = arrField[j];
		}
		strCodeSelect = strCodeSelect + "<option value=" + arrCode[i-1][0] + ">";
		strCodeSelect = strCodeSelect + arrCode[i-1][0] + "-" + arrCode[i-1][1];
		strCodeSelect = strCodeSelect + "</option>";
	}
	mVs.deleteVar(strCodeName);
	mVs.addArrVar(strCodeName,"",arrCode);	//�����Ƿ������ݴӷ������˵õ�,�����øñ���
	mVs.deleteVar(strCodeName+"Select");
	mVs.addVar(strCodeName+"Select","",strCodeSelect);	//�����Ƿ������ݴӷ������˵õ�,�����øñ���
	return arrCode;
}

/*************************************************************
 *                     �õ�����ֵ��
 *  ����  ��  strCodeName����������
 *  ����ֵ��  string     ������ֵ��
 *************************************************************
 */
function getCodeValue(strCodeName)
{
	var reStr;
	reStr= parent.EX.fm.all("txtVarData").value;	//�Ӹ�frame��ȡ�ôӷ�������ȡ�õ�ֵ
	return reStr;
}

/*************************************************************
 *                     ���������
 *  ����  ��  intElementIndex ��Ҫ��ʾ����Ŀؼ���������;
 *            strCodeName ��������(Lx_Code);
 *            intFunctionIndex ��Ҫ��ֵ�ؼ���˳�� 1-ǰһ���ͱ���
 *                                                2-����ͺ�һ��;
 *            stationCode ����������վ��
 *            ex,ey ��ʾ��λ��.
 *  ����ֵ��  ��
 *************************************************************
 */
function requestServer(strCodeName, strCondition, strConditionField, showWidth,changeEven)
{
	var objFrame;
	objFrame=mCs.getVar("ShowCodeFrame");
	//���������
	parent.EX.fm.txtCodeName.value = strCodeName;	//��������
	parent.EX.fm.txtFrameName.value = objFrame.name;	//Frame������
	parent.EX.fm.txtVarData.value = "";	//����ʱ��Ҫ�Ŀռ�
	parent.EX.fm.txtOther.value = "";	//�ύʱ����������
	parent.EX.fm.txtCodeCondition.value = strCondition;	//��ѯ����
	parent.EX.fm.txtConditionField.value = strConditionField;	//��ѯ�����ֶ�
	parent.EX.fm.txtShowWidth.value = showWidth;	//��ѯ�����ֶ�
	parent.EX.fm.changeEven.value = changeEven;
	parent.EX.fm.submit();
}

/*************************************************************
 *  ������ķ�ʽ��ʾ�����б�
 *  ����  ��  arrCode     ���������ͱ�������б�����Ϣ������;
 *            strCodeName ��Ҫ��ʾ�Ĵ�������
 *  ����ֵ��  ��
 *************************************************************
 */
function showCodeList1(arrCode,strCodeName,strCondition,strConditionField,showWidth,changeEven)
{
	document.body.onclick=function (){closeCodeList();}
	var strValue;
	var flag=false;
	var strText;
	var arrCount;
	var fm;
	//add by yt ,���ӿ�ȵ�����Ӧ
	var strText1;
	var tStr;
	var tMaxLen = 0;
	var tCurLen = 0;
	var strChange="";
	if(changeEven != null) strChange = ",1";
	fm=mCs.getVar("ShowCodeFrame");
	strText = "" ;
	strText = strText
		+ strCodeName+"','"+strCondition+"','"+strConditionField+"'"+strChange+");}\""
		+ " onclick=\"setFieldValue(this,'" +strCodeName + "','" + strCondition + "','" + strConditionField + "'"+strChange+")\""
		+ " onblur=\"closeCodeList();\">";
//		+ ">";
	//�����ݿ�ȡ���ݵ����
	var strCode = searchCode(strCodeName,strCondition,strConditionField,1);
	if (strCode)
	{
		strText = strText + strCode;
		flag = true;
	}
	if (!flag)
	{
		//��������Դ�����
		strCode = searchCode(strCodeName,strCondition,strConditionField,"EX");
		if (strCode)
		{
			strText = strText + strCode;
			flag = true;
		}
	}
	//add by yt , ʹ��CodeSelect�Ŀ���ܹ��Զ���Ӧ
	arrCount=arrCode.length;
	if (arrCount>100 ) arrCount = 100;//����Ч�ʣ�ֻ����ǰ100�����
	for(i=0;i<arrCount;i++)
	{
		tStr = arrCode[i][1];
		try{
			tCurLen = tStr.length;
		}
		catch (ex){}
		if (tCurLen>tMaxLen) tMaxLen = tCurLen;
	}
	tCurLen = 10 * tMaxLen ;	//XinYQ modified on 2006-06-12 : ԭ���Ļ��� 25 ����,������� Station ����
	if (tCurLen <= 255) tCurLen = 255;  //ȡ��С���    //XinYQ modified on 2006-06-12 : Ĭ��һ�� title + һ�� input �߽���
	if (tCurLen >= 2000) tCurLen = 2000;	//ȡ�����
	//��showWidthΪnullʱ���⴦��
	try{
		if (showWidth == "null") showWidth = 255;
	}
	catch(ex1){}
	if (typeof(showWidth)!="undefined" && showWidth!="undefined")
	{
		strText1 = "<select name=codeselect style='width:" + showWidth + "px' size=8  onkeyup=\"if (window.event.keyCode==13||window.event.keyCode==16){setFieldValue(this,'";
	}
	else
	{
		strText1 = "<select name=codeselect style='width:" + tCurLen + "px' size=8  onkeyup=\"if (window.event.keyCode==13||window.event.keyCode==16){setFieldValue(this,'";
	}
	strText=strText1 + strText+"</select>"
	if(flag)
	{
		document.all("spanCode").innerHTML =strText;
		document.all("spanCode").style.left=mCs.getVar("ShowCodeX");	//��ȡ����������������X
		document.all("spanCode").style.top=mCs.getVar("ShowCodeY");	//��ȡ����������������Y
		document.all("spanCode").style.display ='';
	}
	else
	{
		document.all("spanCode").style.display ='none';
	}
}

/*************************************************************
 *  Ϊ�ؼ���ֵ
 *  ����  ��  Field ��Ҫ��ֵ�Ŀؼ�
 *  ����ֵ��  ��
 *************************************************************
 */
function setFieldValue(Field,strCodeName,strCondition,strConditionField,changeEven)
{
	var tFldCode;	//Ϊһ�������ļ�¼����001,�ܹ�˾,�ܹ�˾��Ϣ
	var tArrDisplayObj;	//��Ҫ��ʾ���Ķ���
	var tArrDisplayOrder;	//��ʾ��˳��
	var i,iMax;
	var strChange="";
	//�õ�һ�������¼
	tFldCode = getOneCode(strCodeName,Field.value,strCondition,strConditionField);
	tArrDisplayObj   = mCs.getVar("ShowCodeObj");	//�õ���Ҫ��ʾ�Ķ���
	tArrDisplayOrder = mCs.getVar("ShowCodeOrder");	//�õ���ʾʱ��Ӧ��˳��
	iMax = tArrDisplayObj.length;
	try
	{
		for (i=0; i<iMax; i++)
		{
			tArrDisplayObj[i].value = tFldCode[tArrDisplayOrder[i]];	//������ʾ˳��������ʾ����
		}
		if (changeEven != null)
		{
			strChange = "change"+tArrDisplayObj[0].name+"();";
			eval(strChange);
		}
		//XinYQ modified on 2006-10-23
		if (oEventField != null && typeof(oEventField) != "undefined")
		{
			afterCodeSelect(strCodeName, oEventField);
		}
		else
		{
			afterCodeSelect(strCodeName, Field);
		}
	}
	catch (ex)
	{}
	document.all("spanCode").style.display = 'none';
	//ʹ�û�ѡ��ֵ�󣬿ؼ��Ա��ֽ���
	try { if (oldField != "") oldField.focus(); } catch(ex){}
	try { if (oldFieldKey != "") oldFieldKey.focus(); } catch(ex){}
}

/*************************************************************
 *              ��Code�ڴ��ж�ȡ��Code����Ϣ
 *  ����  ��  strCodeName:Code������(����)
 *            strCode    :Code�ı���
 *            index �ؼ���������
 *  ����ֵ��  ��
 *************************************************************
 */
function getOneCode(strCodeName,strCode,strCondition,strConditionField)
{
	var tArrCode;
	var i,iMax;
	var tArrReturn;
	tArrCode = mVs.getVar(strCodeName+strCondition+strConditionField);
	iMax = tArrCode.length;
	for (i=0 ; i<iMax;i++)
	{
		if (tArrCode[i][0]==strCode)
		{
			tArrReturn = tArrCode[i];
			break;
		}
	}
	return tArrReturn;
}

/*************************************************************
 *                      ���ұ���
 *  ����  ��  strValue����������
 *  ����ֵ��  string  ��code �� false
 *************************************************************
 */
function searchCode(strValue,strCondition,strConditionField,isShortShow)
{
	if (typeof(isShortShow) == "undefined")
	{
		//ȡ�ñ��룬���û���ҵ�������-1
		return mVs.getVar(strValue+strCondition+strConditionField);
	}
	else if (isShortShow == "EX")
	{
		return mVs.getVar(strValue+"Select");
	}
	else
	{
		return mVs.getVar(strValue+strCondition+strConditionField+"Select");
	}
}

/*************************************************************
 *                     ��մ�����Ϣ
 *  ����  ��  û��
 *  ����ֵ��  û��
 *************************************************************
 */
function clearShowCodeError()
{
	mCs.updateVar("ShowCodeError","","");	//��մ�����Ϣ
	mCs.updateVar("ShowCodeErrorCode","","");	//��մ�����Ϣ
}

/*************************************************************
 *                     ��������λ��
 *  ����  ��  ��Ҫ���յĶ���
 *  ����ֵ��  ����ex,ey��ֵ
 *************************************************************
 */
function setFieldPos(Field)
{
	var ex,ey,i,intElementIndex;
	try
	{
		var posLeft, posTop;
		var oParent;
		oParent = Field;
		posLeft = 0;
		posTop = oParent.offsetHeight;
		do
		{
			if ( oParent.tagName.toLowerCase( ) != "tr" && oParent.tagName.toLowerCase( ) != "form" && oParent.tagName.toLowerCase( ) != "span" && oParent.tagName.toLowerCase( ) != "div")
			{
				posLeft += parseInt(oParent.offsetLeft);
				posTop  += parseInt(oParent.offsetTop);
			}
			oParent = oParent.parentElement;
		}
		while( oParent.tagName.toLowerCase( ) != "body" );
		ex = posLeft;
		ey = posTop - 5;
		mCs.updateVar("ShowCodeX","0",ex);
		mCs.updateVar("ShowCodeY","0",ey);
	}
	catch(ex)
	{}
}

function getCode(strCodeName,arrShowCodeObj,strCondition,strConditionField,refresh,showWidth,changeEven)
{
	var tCode;
	tCode=searchCode(strCodeName,strCondition,strConditionField);  //�Ӵ�������ȡ����
	if (refresh)
	{
		tCode = false;
		try
		{
			mVs.deleteVar(strCodeName+strCondition+strConditionField);
			mVs.deleteVar(strCodeName+strCondition+strConditionField+"Select");
		}
		catch(ex){}
	}
	if (tCode == false && arrShowCodeObj != null)
	{
		//����������ˣ���ȡ����
		requestServer(strCodeName, strCondition, strConditionField, showWidth,changeEven);
		return false;
	}
	showCodeList1(tCode,strCodeName,strCondition,strConditionField,showWidth,changeEven);
}

/**
 * �ر�������ʾ��
 */
function closeCodeList()
{
	try
	{
		var disFlag = 0;
		showFirstIndex=0;
		arrayBmCode=null;
//		alert(document.activeElement.name);
		// ���ݽ�������ж��Ƿ�ִ��������Ĺرղ���
		if(document.activeElement.name==undefined||(oldFieldKey.name != document.activeElement.name && document.activeElement.name != "codeselect"))
		{
//			alert(document.activeElement.name);
			if (document.all("spanCode").style.display == '')
			{ 
				disFlag = 1;
				document.all("spanCode").style.display ='none';
			}
		}
		try
		{
			if(oldFieldKey != "")
			{
//				if(disFlag) oldFieldKey.focus();
			}
			// ���ö���Ľ��㶪ʧ�¼�Ϊ��
			oldFieldKey.onblur="";
		}
		catch(ex)
		{}
	}
	catch(ex)
	{}
}

/**
 * ��λ��ָ���Ĵ���λ��
 */
function goToSelect(strCodeName,Field,strCondition,strConditionField)
{
	i=0;
	//�ҵ���Ӧ�������Ƶı�������
	arrayBmCode=searchCode(strCodeName,strCondition,strConditionField);
	if (arrayBmCode!=null)
	{
		for(i=0;i<arrayBmCode.length;i++)
		{
			var t_len = trim(Field.value).length;
			//����ؼ������е�ֵ���ڱ��������е�ĳһ��
			//���Ǵ���ֵ�Ѵ�����������
			if( arrayBmCode[i][0].substring(0,t_len) == trim(Field.value))
			{
				showFirstIndex = i;
				//��ô��ҳ������ʾ�ñ�����ʱ�򣬶�λ����һ��
				document.all("codeselect").children[showFirstIndex].selected=true;
				return;
			}
		}
	}
}

/**
 * ��λ��ָ���Ĵ���λ�� ������һ�� steven ��
 */
function goToNextSelect(strCodeName,Field,strCondition,strConditionField)
{
	i=0;
	//�ҵ���Ӧ�������Ƶı�������
	arrayBmCode=searchCode(strCodeName,strCondition,strConditionField);
	if (arrayBmCode!=null)
	{
		var tLength = arrayBmCode.length;
		for(i=0;i<tLength;i++)
		{
			var t_len = trim(Field.value).length;
			//����ؼ������е�ֵ���ڱ��������е�ĳһ��
			//���Ǵ���ֵ�Ѵ�����������
			if( arrayBmCode[i][0].substring(0,t_len) == trim(Field.value))
			{
				showFirstIndex = i+1;
				//��ô��ҳ������ʾ�ñ�����ʱ�򣬶�λ����һ��
				document.all("codeselect").children[showFirstIndex].selected=true;
				return;
			}
		}
	}
}

/**
 * ��λ��ָ���Ĵ���λ�� ������һ�� steven ��
 */
function goToPreciousSelect(strCodeName,Field,strCondition,strConditionField)
{
	i=0;
	//�ҵ���Ӧ�������Ƶı�������
	arrayBmCode=searchCode(strCodeName,strCondition,strConditionField);
	if (arrayBmCode!=null)
	{
		var tLength = arrayBmCode.length;
		for(i=0;i<tLength;i++)
		{
			var t_len = trim(Field.value).length;
			//����ؼ������е�ֵ���ڱ��������е�ĳһ��
			//���Ǵ���ֵ�Ѵ�����������
			if( arrayBmCode[i][0].substring(0,t_len) == trim(Field.value))
			{
			    if (i>0)
			    {
				    showFirstIndex = i-1;
					//��ô��ҳ������ʾ�ñ�����ʱ�򣬶�λ����һ��
					document.all("codeselect").children[showFirstIndex].selected=true;
			    }
				return;
			}
		}
	}
}