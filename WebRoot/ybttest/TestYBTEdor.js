
//�ύ�����水ť��Ӧ����
function submitForm()
{
	
	if(!verifyInputForm()){
		return false;
	}
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   

  fm.submit(); //�ύ
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���




function afterSubmit(ResultCode,ResultInfoDesc)
{ 	
	  showInfo.close(); 
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" +ResultInfoDesc;   
	  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	  		  
}  


function  verifyInputForm(){
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
	 
	var vTransExeDate = fm.TransExeDate.value; 
	if (vTransExeDate.trim() == ""){    
		alert("���ж˽������ڲ���Ϊ��");
		return false; 
	}   
	  
	var vTransRefGUID = fm.TransNo.value; 
	if (vTransRefGUID.trim() == ""){    
		alert("������ˮ�Ų���Ϊ��");
		return false; 
	}   
	
	var vRegionCode = fm.ZoneNo.value; 
	if (vRegionCode.trim() == ""){    
		alert("�������벻��Ϊ��");
		return false; 
	} 
	
	var vBranch = fm.NodeNo.value; 
	if (vBranch.trim() == ""){    
		alert("������벻��Ϊ��");
		return false; 
	} 
	
	var vtranFlagCode = fm.TranType.value; 
	if (vtranFlagCode.trim() == ""){    
		alert("�����벻��Ϊ��");
		return false; 
	} 
	
		var vPolNumber = fm.PolNumber.value; 
	if (vPolNumber.trim() == ""){    
		alert("�����Ų���Ϊ��");
		return false; 
	} 

	 
	return true;
}  




function TranFlag(transno){

	 if("1004"==transno){
		 insureInfoTitle.style.display="none";
		 insurenameTitle.style.display="none";
		 insurenameInput.style.display="none";
		 insureIDTypeTitle.style.display="none";
		 insureIDTypeInput.style.display="none";
		 insureIDTitle.style.display="none";
		 insureIDInput.style.display="none";
		 AccountNameTitle.style.display="";
		 AccountName.style.display="";
		 appntInfoTitle.style.display="";
		 appntnameTitle.style.display="";
		 appntnameInput.style.display="";
		 appntIDTypeTitle.style.display="";
		 appntIDTypeInput.style.display="";
		 appntIDTitle.style.display="";
		 appntIDInput.style.display="";
		 CortransrnoName.style.display="none";
		 Cortransrno.style.display="none";
	 
	 }	 if("1003"==transno){
		 insureInfoTitle.style.display="";
		 insurenameTitle.style.display="";
		 insurenameInput.style.display="";
		 insureIDTypeTitle.style.display="";
		 insureIDTypeInput.style.display="";
		 insureIDTitle.style.display="";
		 insureIDInput.style.display="";
		 
		 appntInfoTitle.style.display="";
		 appntnameTitle.style.display="";
		 appntnameInput.style.display="";
		 appntIDTypeTitle.style.display="";
		 appntIDTypeInput.style.display="";
		 appntIDTitle.style.display="";
		 appntIDInput.style.display="";
		 
		 AccountNameTitle.style.display="";
		 AccountName.style.display="";
		 
		 CortransrnoName.style.display="none";
		 Cortransrno.style.display="none";
		 
	 }	 if("1021"==transno){
		 insureInfoTitle.style.display="";
		 insurenameTitle.style.display="";
		 insurenameInput.style.display="";
		 insureIDTypeTitle.style.display="";
		 insureIDTypeInput.style.display="";
		 insureIDTitle.style.display="";
		 insureIDInput.style.display="";
		 
		 appntInfoTitle.style.display="";
		 appntnameTitle.style.display="";
		 appntnameInput.style.display="";
		 appntIDTypeTitle.style.display="";
		 appntIDTypeInput.style.display="";
		 appntIDTitle.style.display="";
		 appntIDInput.style.display="";
		 
		 AccountNameTitle.style.display="";
		 AccountName.style.display="";
		 
		 CortransrnoName.style.display="none";
		 Cortransrno.style.display="none";
		 
	 }	 if("1017"==transno){
		 insureInfoTitle.style.display="none";
		 insurenameTitle.style.display="none";
		 insurenameInput.style.display="none";
		 insureIDTypeTitle.style.display="none";
		 insureIDTypeInput.style.display="none";
		 insureIDTitle.style.display="none";
		 insureIDInput.style.display="none";
		 
		 appntInfoTitle.style.display="";
		 appntnameTitle.style.display="";
		 appntnameInput.style.display="";
		 appntIDTypeTitle.style.display="";
		 appntIDTypeInput.style.display="";
		 appntIDTitle.style.display="";
		 appntIDInput.style.display="";
		 
		 AccountNameTitle.style.display="";
		 AccountName.style.display="";
		 
		 CortransrnoName.style.display="none";
		 Cortransrno.style.display="none";
		 
	 }	 if("1030"==transno){
		 insureInfoTitle.style.display="none";
		 insurenameTitle.style.display="none";
		 insurenameInput.style.display="none";
		 insureIDTypeTitle.style.display="none";
		 insureIDTypeInput.style.display="none";
		 insureIDTitle.style.display="none";
		 insureIDInput.style.display="none";
		 
		 appntInfoTitle.style.display="none";
		 appntnameTitle.style.display="none";
		 appntnameInput.style.display="none";
		 appntIDTypeTitle.style.display="none";
		 appntIDTypeInput.style.display="none";
		 appntIDTitle.style.display="none";
		 appntIDInput.style.display="none";
		 
		 AccountNameTitle.style.display="none";
		 AccountName.style.display="none";
		 CortransrnoName.style.display="";
		 Cortransrno.style.display="";
		 
	 }	 if("1026"==transno){
		 insureInfoTitle.style.display="none";
		 insurenameTitle.style.display="none";
		 insurenameInput.style.display="none";
		 insureIDTypeTitle.style.display="none";
		 insureIDTypeInput.style.display="none";
		 insureIDTitle.style.display="none";
		 insureIDInput.style.display="none";
		 
		 appntInfoTitle.style.display="none";
		 appntnameTitle.style.display="none";
		 appntnameInput.style.display="none";
		 appntIDTypeTitle.style.display="none";
		 appntIDTypeInput.style.display="none";
		 appntIDTitle.style.display="none";
		 appntIDInput.style.display="none";
		 
		 AccountNameTitle.style.display="none";
		 AccountName.style.display="none";
		 
		 CortransrnoName.style.display="";
		 Cortransrno.style.display="";
		 
	 }
	 
  }

