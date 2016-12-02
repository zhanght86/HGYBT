/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.userMan;

import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.encrypt.LisIDEA;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDUserSchema;
import com.sinosoft.lis.vschema.LDUserSet;
import com.sinosoft.lis.vschema.LDUserTOMenuGrpSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description: �û���ѯҵ���߼�������</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author DingZhong
 * @version 1.0
 */
public class LDUserManBL
{
    /** �������࣬ÿ����Ҫ����������ж����ø��� */
    public CErrors mErrors = new CErrors();
    /** �����洫�����ݵ����� */
    private VData mResult = new VData();
    private VData mInputData = new VData();
    
    /** ���ݲ����ַ��� */
    private String mOperate;
//    private String mDeletor;

    /** ҵ������ر��� */
    /** �û��������Ϣ*/
    LDUserSchema mLDUserSchema = new LDUserSchema();
    LDUserTOMenuGrpSet mLDUserToMenuGrpSet = new LDUserTOMenuGrpSet();
    String mOperator; //ָʾ���б������Ĳ���Ա
    String mAreaNo = "";
    String mCityNo = "";
    String mResultStr = "";
    int mResultNum = 0;

    public LDUserManBL()
    {
        // just for debug
    }

    /**
     * �������ݵĹ�������
     * @param cInputData VData
     * @param cOperate String
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate)
    {

        // �жϲ����ǲ��ǲ�ѯ
        if (cOperate.compareTo("query") != 0
            && cOperate.compareTo("insert") != 0
            && cOperate.compareTo("update") != 0
            && cOperate.compareTo("delete") != 0)
        {
//            System.out.println("Operate is not permitted");
            return false;
        }

//        System.out.println("start BL submit...");

        //���������ݿ�����������
        this.mOperate = cOperate;

        //�õ��ⲿ���������,�����ݱ��ݵ�������
        if (!getInputData(cInputData))
        {
            return false;
        }
//        System.out.println("---getInputData---");

        if (mOperate.equals("query"))
        {
            if (!queryData())
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        //����ҵ����
        if (!dealData())
        {
            return false;
        }
//        System.out.println("After dealData��");
        //׼������̨������
        if (!prepareOutputData())
        {
            return false;
        }
//        System.out.println("After prepareOutputData");

//        System.out.println("Start LDUser BL Submit...");
        System.out.println("UI 1111111...");
        LDUserManBLS tLDUserManBLS = new LDUserManBLS();
        System.out.println("UI 222222...");
//        System.out.println("tLDuserManBLs.submit...");
//        String str = (String) mInputData.getObjectByObjectName("String", 0);
//        System.out.println("deletor is " + str);
        tLDUserManBLS.submitData(mInputData, cOperate);
        System.out.println("UI 333333...");
//        System.out.println("End LDUserMan BL Submit...");

        //�������Ҫ����Ĵ����򷵻�
        if (tLDUserManBLS.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tLDUserManBLS.mErrors);
            return false;
        }
        mInputData = null;
        return true;
    }

    private static boolean dealData()
    {
        return true;
    }

    /**
     * ��ѯ
     * @return boolean
     */
    @SuppressWarnings("unchecked")
	private boolean queryData()
    {
        String usercode = mLDUserSchema.getUserCode();
        System.out.println("��ʼ��ѯ�û�");
        String username = mLDUserSchema.getUserName();
        String comcode = mLDUserSchema.getComCode();
        String agentcom = mLDUserSchema.getAgentCom();
        LDUserDB tLDUserDB = new LDUserDB();
        LDUserSet tLDUserSet = new LDUserSet();
        String strSql, wherePart = " where 1 = 1 ";

        if ((usercode != null) && (!usercode.trim().equals("")))
        {
            wherePart = wherePart + " and  ld.usercode like '" + usercode + "%' ";
//            System.out.println(wherePart);
        }

        if ((username != null) && (!username.trim().equals("")))
        {
            wherePart = wherePart + " and  ld.username like '" + username + "%' ";
        }
        wherePart = wherePart +
        		"and ld.comcode in (select comcode from ldcom where 1=1";
       
        if ((mAreaNo != null) && (!mAreaNo.trim().equals("")))
        {
            wherePart = wherePart + " and  comgrade='" + mAreaNo + "'";
        }
        if ((mCityNo != null) && (!mCityNo.trim().equals("")))
        {
            wherePart = wherePart + " and  comcode like '" + mCityNo + 
            "%' and comgrade =((select comgrade from ldcom where comcode='"+ mCityNo +"')+1)";
        }

        wherePart = wherePart +") "+"and usercode!='001'";
        strSql = "select usercode, username, comcode," +
        		" password, userdescription, userstate," +
        		"uwpopedom, " +
        		"  (select D.NAME from LDCOM D where  D.comcode = ld.comcode) claimpopedom," +
        		"   (select WMSYS.WM_CONCAT((select menugrpname from ldmenugrp " +
        		"p where p.menugrpcode=M.MENUGRPCODE)) from ldusertomenugrp m " +
        		"where m.usercode=ld.usercode) otherpopedom," +
        		"certifyflag, edorpopedom, " +
        		"popuwflag, superpopedomflag, operator," +
        		"  makedate, maketime, " +
        		" certifyflag, edorpopedom,"+
        		" agentcom, gedorpopedom"+
        		"   from lduser ld " + 
        wherePart+" order by ld.comcode,ld.username,ld.usercode";
        System.out.println(strSql);
        tLDUserSet = tLDUserDB.executeQuery(strSql);

//        System.out.println("tLDUserSet size :" + tLDUserSet.size());

        if (tLDUserSet == null)
        {
            return false;
        }

        LisIDEA tIdea = new LisIDEA();
        for (int i = 1; i <= tLDUserSet.size(); i++)
        { 
        	
            String encryptPwd = tLDUserSet.get(i).getPassword();
           // if (encryptPwd.length() != 16)
           // {
          //      continue;
          //  }
            String decryptPwd = tIdea.decryptString(encryptPwd);
//            System.out.println(encryptPwd);
//            System.out.println(decryptPwd);
            tLDUserSet.get(i).setPassword(decryptPwd);
        }

        String tReturn = tLDUserSet.encode();
        tReturn = "0|" + tLDUserSet.size() + "^" + tReturn;
       // System.out.println("------------****" + tReturn);
        mResult.clear();
        mResult.add(tReturn);
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    public int getResultNum()
    {
        return mResultNum;
    }

    public String getResultStr()
    {
        String resultStr = "";
//        for (int i = 1; i <= mResultNum; i++)
//        {
//
//        }
        return resultStr;
    }


    /**
     * �����������еõ����ж���
     * ��������û�еõ��㹻��ҵ�����ݶ����򷵻�false,���򷵻�true
     * @param cInputData VData
     * @return boolean
     */
    private boolean getInputData(VData cInputData)
    {
        //�õ����Ӹ���ʱ�Ĳ���Ա��ɾ��ʱ�ĵ�ǰ����Ա
        mOperator = (String) cInputData.getObjectByObjectName("String", 0);
        TransferData vTransferData = new TransferData();
        vTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
        this.mAreaNo = (String) vTransferData.getValueByName("AreaNo");
        this.mCityNo = (String) vTransferData.getValueByName("CityNo");
        // �����ѯ����
        mLDUserSchema = (LDUserSchema) cInputData.getObjectByObjectName(
                "LDUserSchema", 0);

        if (mLDUserSchema == null)
        {
//            System.out.println("cant get userschema");
            return false;
        }

        String curDate = PubFun.getCurrentDate();
        String curTime = PubFun.getCurrentTime();

        if (mOperate.compareTo("insert") == 0)
        {
//            System.out.println("insert operate");
            mLDUserSchema.setMakeTime(curTime);
            mLDUserSchema.setMakeDate(curDate);
        }
//        System.out.println("come here");
//        System.out.println("password is:" + mLDUserSchema.getPassword());
        // decrypt password if possible

//      System.out.println("comeHere");

        mLDUserToMenuGrpSet = (LDUserTOMenuGrpSet) cInputData.
                              getObjectByObjectName("LDUserTOMenuGrpSet", 0);
//        System.out.println("completed get input data");
        return true;
    }

    /**
     * ׼��������������Ҫ������
     * ��������׼������ʱ���������򷵻�false,���򷵻�true
     * @return boolean
     */
    @SuppressWarnings("unchecked")
	private boolean prepareOutputData()
    {
        mResult.clear();
        mInputData.clear();
        try
        {
            mInputData.add(mOperator);
            mInputData.add(mLDUserSchema);
            mInputData.add(mLDUserToMenuGrpSet);
//            System.out.println("prepareOutput deletor : " + mOperator);
//            String str = (String) mInputData.getObjectByObjectName("String", 0);
//            System.out.println("deletor is : " + str);
        }
        catch (Exception ex)
        {
            // @@������
//            System.out.println("BL excetion happend");

            CError tError = new CError();
            tError.moduleName = "MenuQueryBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "��׼������㴦������Ҫ������ʱ����";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }
}
