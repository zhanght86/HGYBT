



//�ύ�����水ť��Ӧ����
function submitForm()
{
  if(!verifyInput())
  {
	 // alert("false");
  	return false;
  }
  	
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  

  fm.submit(); //�ύ
}
 
function  verifyInput(){
	var vIp = fm.ip.value; 
	if (vIp.trim() == ""){
		alert("���ձ���ip��ַ����Ϊ��");
		return false;
	}
	
	var vPort = fm.port.value; 
	if (vPort.trim() == ""){    
		alert("���ձ��Ķ˿ڲ���Ϊ��");
		return false; 
	}
	 
	var vTRANSDATE = fm.mSYS_REQ_TIME.value; 
	if (vTRANSDATE.trim() == ""){    
		alert("���ж˽������ڲ���Ϊ��");
		return false; 
	}   
	  
	var vSERIALNO = fm.mSvPt_Jrnl_No.value; 
	if (vSERIALNO.trim() == ""){    
		alert("������ˮ�Ų���Ϊ��");
		return false; 
	}   

	var vBRANCHNO = fm.mCCBIns_ID.value; 
	if (vBRANCHNO.trim() == ""){    
		alert("������벻��Ϊ��");
		return false; 
	} 
	
	var vTLID = fm.mCCB_EmpID.value; 
	if (vTLID.trim() == ""){    
		alert("��Ա���벻��Ϊ��");
		return false; 
	}  
	
	var vPOLICYAPPLYSERIAL = fm.Enqr_Dt.value; 
	if (vPOLICYAPPLYSERIAL.trim() == ""){    
		alert("��ѯ���ڲ���Ϊ��");
		return false;  
	} 
	

	
	 
	return true;
}  
 
//�ύ�����,���������ݷ��غ�ִ�еĲ���

 

function afterSubmit(ResultCode,ResultInfoDesc)
{ 	
	  showInfo.close(); 
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" +ResultInfoDesc;   
	  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	  		  
}  

function queryPrtNo()
{
	var vBankCode = fm.BankCode.value;
	var vZoneNo = fm.ZoneNo.value;
	var vBrNo = fm.BrNo.value;
	
	if( vBankCode == "" || vZoneNo == "" || vBrNo == "" ) {
		alert("����������������Ϣ��");
		return false;
	}
	
	var vSQL = "SELECT AgentCom FROM LKCodeMapping WHERE BankCode = '" + vBankCode
			+ "' AND ZoneNo = '" + vZoneNo
			+ "' AND BankNode = '" + vBrNo + "'";
			
	var vResult = easyExecSql(vSQL, 1, 0, 1, 1);
	
	if( vResult == null ) {
		alert("������������Ϣ�����ڣ�");
		return false;
	}
	
	// ��ѯ����Ӧ�ĵ�֤���ջ���
	var vAgentCom = "F" + vResult[1][0];
	
	// ע��SQL����д������֤�п���ֱ�ӷ��ŵ������ϣ�Ҳ�п���ֻ���ŵ��ϼ������ϡ����ԣ��˴���LIKE������
	vSQL = "SELECT MIN(StartNO) FROM LZCard WHERE CertifyCode IN ('109001') AND '" + vAgentCom + "' LIKE ReceiveCom || '%%'";
	
	vResult = easyExecSql(vSQL, 1, 0, 1, 1);
	
	if( vResult == null ) {
		alert("û�в�ѯ�������������ӡˢ�ţ���ֱ���ں�̨��ѯ��");
		return false;
	}
	
	// ����ѯ�õ��ı���ӡˢ�Ÿ�ֵ����Ӧ�������
	fm.PrtNo.value = vResult[1][0];
	return true;
}
