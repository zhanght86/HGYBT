/*
 * ÿ���賿��ʱ���ǰһ�ո������µ�����������������ʧ�ܻ���δ���ˣ�
 * ��ô��ʱ�����ˣ�������������Ϊǰһ�� 
 * �����ߣ�lilu
 * ����ʱ�䣺2015��2��26�� 15:52:20
 */
package com.sinosoft.midplat.bat;

import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public  class BalanceCheck extends TimerTask
  implements XmlTag
{
  protected final Logger cLogger = Logger.getLogger(getClass());
  protected Date cTranDate;
  protected String cResultMsg;
  protected String cTranBank;
  protected String cTranlogNo;
  protected Element cThisConfRoot = null;
  protected Element cThisBusiConf = null;


  public BalanceCheck() {
  }

  public void run()
  {
	cTranlogNo=String.valueOf(NoFactory.nextTranLogNo());
    Thread.currentThread().setName(cTranlogNo);
    this.cLogger.info("Into BalanceCheck.run()...");

    this.cResultMsg = "";
    try
    {
    	
    	this.cThisConfRoot = BatConf.newInstance().getConf().getRootElement();
        this.cThisBusiConf = ((Element)XPath.selectSingleNode(
        this.cThisConfRoot, "batch[funcFlag='" + "0007" + "']"));

//        JdomUtil.print(this.cThisConfRoot);
//        JdomUtil.print(this.cThisBusiConf);
        
    	TranLogDB mTranLogDB = new TranLogDB();
    	long mStartMillis = System.currentTimeMillis();
    	
    	   //�������׼��֮ǰ������־
        try{
      	  
    		mTranLogDB.setLogNo(cTranlogNo);
    		mTranLogDB.setTranCom("0");
    		mTranLogDB.setZoneNo("-");
    		mTranLogDB.setNodeNo("0000000000");
    		mTranLogDB.setTranNo(cTranlogNo);
    		mTranLogDB.setOperator("sys");
    		mTranLogDB.setFuncFlag("0007");
    		mTranLogDB.setTranDate(DateUtil.getCur8Date());
    		mTranLogDB.setTranTime(DateUtil.getCur6Time());
    		mTranLogDB.setInNoDoc("No_InNoStd_Document");

    		mTranLogDB.setRCode(CodeDef.RCode_NULL);
    		mTranLogDB.setRText(cResultMsg);
    		mTranLogDB.setUsedTime(-1);
    		Date mCurDate = new Date();
    		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
    		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
    		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
    		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
    		if (!mTranLogDB.insert()) {
    			cLogger.error(mTranLogDB.mErrors.getFirstError());
    			throw new MidplatException("������־ʧ�ܣ�");
    		}
    		
        }catch(Exception ex){
      	  this.cLogger.error("���˼�齻�ײ�����־ʧ�ܣ�",ex);
        }
    	
      try
      {
    	  
    	  String[] mTranCom = new String[]{"01","01","01","05","06","07"};
    	  String[] mBank=new String[]{"��������","��������","��������","ũҵ����","��������","������"};
    	  String[] mFuncFlag = new String[]{"1005","1006","1104","2001","4005","5005"};
    	  String[] mTranName = new String[]{"�µ�����","��֤����","��ȫ����","�µ�����","�µ�����","�µ�����"};
      	  String mTranDate = String.valueOf(DateUtil.get8Date(new Date().getTime() - 86400000L));
    	  
      	  System.out.println("��������:"+mTranDate+"  �����"+mFuncFlag.length+"������");
      	  
    	  for (int i = 0; i < mFuncFlag.length; i++) {
    		  
    		  String mClassName = XPath.newInstance(
    				  "//batch[com='"+mTranCom[i]+"' and funcFlag='"+mFuncFlag[i]+"']/class").valueOf(
    						  BatConf.newInstance().getConf());
    		  
    		  String mExtendClass = XPath.newInstance(
    				  "//batch[com='"+mTranCom[i]+"' and funcFlag='"+mFuncFlag[i]+"']/extendClass").valueOf(
    						  BatConf.newInstance().getConf());
    		  
    		  System.out.println(mClassName + ": " + mBank[i] + "-" + mTranCom[i] + "-" + mFuncFlag[i] + "-" + mTranDate +" - mExtendClass:"+mExtendClass);
    		  
    		  String mResultMsg = "";
			
    		  String sqlStr = "select * from tranlog where trancom='"+mTranCom[i]+"' and rcode='0' and funcflag='"+mFuncFlag[i]+"' and TranDate='"+mTranDate+"' order by logno desc";
    		  SSRS ssrs0=new ExeSQL().execSQL(sqlStr);
//    		  ssrs0.GetText(1, 1);
    		  System.out.println("TranCom:"+mTranCom[i]);
    		  System.out.println("FuncFlag:"+mFuncFlag[i]);
    		  
    		  if(ssrs0.MaxNumber<1){

    	    	  if (null==mClassName || "".equals(mClassName)) {
    	    		  mResultMsg = "�ݲ�֧�ָ����У�";
    	    	  } else {
    	    		  if("01".equals(mTranCom[i])){
    	    			  if("ToBankBalance".equals(mExtendClass)){
    	    				  cLogger.info(mTranDate+mBank[i]+mTranName[i]+"���ɹ������ڿ�ʼ�����ˣ�");
    	    				  ToBankBalance tBalance = (ToBankBalance)Class.forName(mClassName).newInstance();
    	    				  tBalance.setDate(mTranDate);
    	    				  tBalance.run();
    	    				  mResultMsg = tBalance.getResultMsg();
    	    				  cResultMsg=cResultMsg+"["+mBank[i]+mTranName[i]+":"+mResultMsg+"];";
    	    			  }else{
    	    				  if("1005".equals(mFuncFlag)){
    	    					  cLogger.info(mTranDate+mBank[i]+mTranName[i]+"���ɹ������ڿ�ʼ�����ˣ�");
    	    				  }
    	    				  if("1006".equals(mFuncFlag)){
    	    					  cLogger.info(mTranDate+mBank[i]+mTranName[i]+"���ɹ������ڿ�ʼ�����ˣ�");
    	    				  }
    	    				  if("1104".equals(mFuncFlag)){
    	    					  cLogger.info(mTranDate+mBank[i]+mTranName[i]+"���ɹ������ڿ�ʼ�����ˣ�");
    	    				  }
    	    				  Balance tBalance = (Balance)Class.forName(mClassName).newInstance();
    	    				  tBalance.setDate(mTranDate);
    	    				  tBalance.run();
    	    				  mResultMsg = tBalance.getResultMsg();
    	    				  cResultMsg=cResultMsg+"["+mBank[i]+mTranName[i]+":"+mResultMsg+"];";
    	    			  }
    	    		  }
    	    		  if("05".equals(mTranCom[i])){
    	    			  cLogger.info(mTranDate+mBank[i]+mTranName[i]+"���ɹ������ڿ�ʼ�����ˣ�");
//    	    			  NewAbcBusiBlc tNewAbcBlc=new NewAbcBusiBlc();
//    	    			  tNewAbcBlc.setDate(mTranDate);
//    	    			  tNewAbcBlc.run();
//    	    			  mResultMsg = tNewAbcBlc.getResultMsg();
	    				  cResultMsg=cResultMsg+"["+mBank[i]+mTranName[i]+":"+mResultMsg+"];";
    	    		  }
//    	    		  if("07".equals(mTranCom[i])){
//    	    			  cLogger.info(mTranDate+mBank[i]+mTranName[i]+"���ɹ������ڿ�ʼ�����ˣ�");
//    	    			  JhyhBusiBlc tJhyhBlc=new JhyhBusiBlc();
//    	    			  tJhyhBlc.setDate(mTranDate);
//    	    			  tJhyhBlc.run();
//    	    			  mResultMsg = tJhyhBlc.getResultMsg();
//	    				  cResultMsg=cResultMsg+"["+mBank[i]+mTranName[i]+":"+mResultMsg+"];";
//    	    		  }
    	    		  
    	    	  }
    	    	  cLogger.info(mBank[i]+"�����˽����"+mResultMsg);
    		  }else{
    				  cLogger.info(mBank[i]+"���ն��˳ɹ������貹���ˣ�");
    				  cResultMsg=cResultMsg+"["+mBank[i]+mTranName[i]+":"+"���貹����"+"];";
    		  }
    	  }
    		

      } catch (Exception ex) {
        this.cLogger.error("�µ����˴����Բ�ʧ�ܣ�", ex);

      }
      
      //������֮�������־
      try{
    	  
  		mTranLogDB.setRCode(CodeDef.RCode_OK);
  		mTranLogDB.setRText(cResultMsg);
  		
		long tCurMillis = System.currentTimeMillis();
		mTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
		mTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
		mTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
		
		if (!mTranLogDB.update()) {
			cLogger.error("������־��Ϣʧ�ܣ�" + mTranLogDB.mErrors.getFirstError());
		}
  		
      }catch(Exception ex){
    	  this.cLogger.error("���˼�齻�ײ�����־ʧ�ܣ�",ex);
      }

    }
    catch (Throwable ex) {
      this.cLogger.error("���˼�����", ex);
      this.cResultMsg = ex.toString();
    }

    this.cTranDate = null;

    this.cLogger.info("Out BalanceCheck.run()!");
  }


  public String getResultMsg() {
    return this.cResultMsg;
  }
}