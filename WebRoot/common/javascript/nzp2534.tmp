/**************************************************************************
 * 程序名称: VerifyWorkFlow.js
 * 程序功能: 通用校验函数，在进行功能性操作前校验工作流节点是否还在，防止出现同时对一条任务操作所可能引起的问题
 *           
 * 创建人  : 
 * 最近更新日期: 
**************************************************************************/

function verifyWorkFlow(Missionid,Submissionid,Activityid)
{

	var arrResult;
	var chkSQL = "select * from lwmission where missionid='"+Missionid+"' and submissionid='"+Submissionid+"' and activityid='"+Activityid+"'";
	arrResult = easyExecSql(chkSQL);
	if(arrResult==null||arrResult=="")
	{
		alert("该次任务已经结束，不允许进行该操作");
		return false;
	}
	return true;

}
