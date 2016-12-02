
function submitForm()
{  
    if(fm.oldPwd.value.length == 0){
		alert("请填写原密码.");
		return false;
	}
	
	var newPwd = fm.newPwd.value;
	var confirmPwd = fm.confirmPwd.value;
	if (newPwd == "") {
	    alert("请输入新密码.");
	    return false;
	}
	
	if (newPwd != confirmPwd){
		alert("新密码与确认密码输入不一致.");
		fm.confirmPwd.value = "";
		fm.newPwd.value = "";
		return false;
	}
	
	fm.submit();
	return true;
}


function afterSubmit(FlagStr) 
{
	if (FlagStr == "false") {
		alert("密码更改失败，可能的原因是原密码输入有误。");
	} else {
		alert("密码更新成功！");		
	}
}

function resetForm()
{
	fm.oldPwd.value = "";
	fm.newPwd.value = "";
	fm.confirmPwd.value = "";
}