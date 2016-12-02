package com.sinosoft.lis.citi;

import java.io.File;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class HandCreat {
	
	SSRS tSSRS = new SSRS(); 
	private String fileTypeCode="";
	private TransferData mTransferData = new TransferData();
	
	private ExeSQL exe = new ExeSQL();

	public HandCreat(VData tVData){
		this.mTransferData = (TransferData)tVData.getObjectByObjectName("TransferData",0);
		this.fileTypeCode = (String)mTransferData.getValueByName("FileTypeCode");
	}
	public boolean checkFile()throws MidplatException{//�ֹ����ɣ��ļ��Ѵ����򱨴�����false
		String date = DateUtil.getCur10Date();
		String citiLogSql = "select * from CitiIFLog cf where cf.IFtype = '"+fileTypeCode+"' and cf.MakeDate='"+date+"' and cf.RCode='Y' and cf.DelFlag is null ";
		SSRS ssRS = new SSRS();
		ssRS = exe.execSQL(citiLogSql);
		if(ssRS.getMaxRow()==1){//�ļ��Ѿ�������,���ر�����Ϣ
			return false;
		}else{
			return true;//�ļ�û�����ɣ�
		}
}
	
}