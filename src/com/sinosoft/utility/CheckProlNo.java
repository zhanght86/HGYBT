package com.sinosoft.utility;

import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.midplat.ccb.service.ContBatResponse;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.exception.MidplatException;

public class CheckProlNo {
	/**
	 * Ͷ���������ɹ���
	 * @param ProlNo
	 * @return
	 */
public static String retCheck(String ProlNo){
	long prolno=Long.parseLong(ProlNo);
	long num=98l-(prolno* 1000000+1802l*100)%97;
	String retnum=""+num;
	if(retnum.length()==1){
		retnum="0"+retnum;
	}
	String retProlno=ProlNo+retnum+"1802";
	return retProlno;
}

/**
 * ����ˮ�ţ����ڣ��������ҵ�Ͷ������
 * 
 */
public static String FindPro(String Datetime,String Transno,String TranCom){
	String	sql="SELECT TR.ProposalPrtNo FROM tranlog TR WHERE TR.TRANNO='"+ Transno+"' and tr.trancom="+TranCom+" and tr.funcflag!=3 and tr.funcflag>0  and tr.trandate='"+Datetime+"'";
	String retProlno=new ExeSQL().getOneValue(sql);
	if(retProlno.equals("")){
		try {
			throw new MidplatException("�����ļ���һ�����߶���������ˮ����������");
		} catch (MidplatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return retProlno;
}
/**
 * ��֤�����ɹ���
 * @param ProlNo
 * @return
 */
public static String ReturnCheck(String ProlNo){
	long prolno=Long.parseLong(ProlNo);
	long num=98l-(prolno* 1000000+9902l*100)%97;
	String retnum=""+num;
	if(retnum.length()==1){
		retnum="0"+retnum;
	}
	String retProlno=ProlNo+retnum+"9902";
	return retProlno;
}
public static String RetBankCode(String ZoneNo,String BrNo,String TranCom){
	/**05��ʾΪ�������У�06��ʾΪ��ͨ����*/
//	if(TranCom.equals("05")){
//		TranCom=AblifeCodeDef.TranCom_BOS;
//	}else if(TranCom.equals("06")){
//		TranCom=AblifeCodeDef.TranCom_BOCOM;
//	}
	String NodeNo=ZoneNo+BrNo;
	String sql="select bak2 from nodemap" +
			" no where no.trancom='" +TranCom+
			"' and no.nodeno='"+NodeNo+"'";
	String BankCode=new ExeSQL().getOneValue(sql);
	return BankCode;
}
public static void main(String[] args) throws Exception {
	//Ͷ����������
	String str = "";
	StringBuffer str1 = new StringBuffer();
	String path = "C:\\Documents and Settings\\Administrator\\����\\����׼��\\1.txt";
	for (int i = 0; i < 10000; i++) {
//		str = "3"+PubFun1.CreateMaxNo("CheckNo", 9);
//		str = "4"+PubFun1.CreateMaxNo("CheckNo", 9);
		str = PubFun1.CreateMaxNo("CheckNo", 10);
		str1.append(ReturnCheck(str)).append("\r\n");
//		str1.append(retCheck(str)).append("\r\n");
	}
	System.out.println(str1);
	ContBatResponse.insertFile(path, str1);
//	String str=retCheck("1000006289");
//	System.out.println(str);
//	String st1=ReturnCheck("0000000031");
//	System.out.println(st1);
}
}
