/**************************************************************************
 * ��������: VerifyWorkFlow.js
 * ������: ͨ��У�麯�����ڽ��й����Բ���ǰУ�鹤�����ڵ��Ƿ��ڣ���ֹ����ͬʱ��һ������������������������
 *           
 * ������  : 
 * �����������: 
**************************************************************************/

function verifyWorkFlow(Missionid,Submissionid,Activityid)
{

	var arrResult;
	var chkSQL = "select * from lwmission where missionid='"+Missionid+"' and submissionid='"+Submissionid+"' and activityid='"+Activityid+"'";
	arrResult = easyExecSql(chkSQL);
	if(arrResult==null||arrResult=="")
	{
		alert("�ô������Ѿ���������������иò���");
		return false;
	}
	return true;

}
