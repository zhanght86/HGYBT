/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.userMan;

import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.schema.LDUserSchema;
import com.sinosoft.lis.vschema.LDUserSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description: �û�ҵ���߼�������</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author DingZhong
 * @version 1.0
 */
public class LDUserBL
{
    /** �������࣬ÿ����Ҫ����������ж����ø��� */
    public CErrors mErrors = new CErrors();

    /** �����洫�����ݵ����� */
    private VData mResult = new VData();

    /** ���ݲ����ַ��� */
//    private String mOperate;

    /** ҵ������ر��� */
    /** �˵��顢�˵��鵽�˵��������Ϣ*/
    LDUserSchema mLDUserSchema = new LDUserSchema();
    LDUserSet mLDUserSet = new LDUserSet();

    String mResultStr = "";
    int mResultNum = 0;

    public LDUserBL()
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
    	System.out.println("11");
        if (!cOperate.equals("query"))
        {
        	System.out.println("11");
            return false;
        }

        //���������ݿ�����������
//        this.mOperate = cOperate;

        //�õ��ⲿ���������,�����ݱ��ݵ�������

//        System.out.println("start get inputdata...");
        if (!getInputData(cInputData))
        {
            return false;
        }
//        System.out.println("---getInputData---");

        //����ҵ����
        if (!queryUser())
        {
            return false;
        }
//        System.out.println("---queryUser---");

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

    /**
     * �����������еõ����ж���
     * ��������û�еõ��㹻��ҵ�����ݶ����򷵻�false,���򷵻�true
     * @param cInputData VData
     * @return boolean
     */
    private boolean getInputData(VData cInputData)
    {
        // �����ѯ����
    	System.out.println("1.11");
        mLDUserSchema = (LDUserSchema) cInputData.getObjectByObjectName(
                "LDUserSchema", 0);

    	System.out.println("1.22");
        if (mLDUserSchema == null)
        {
            return false;
        }

        return true;
    }

    /**
     * ��ѯ������������Ϣ
     * ��������׼������ʱ���������򷵻�false,���򷵻�true
     * @return boolean
     */
    @SuppressWarnings("unchecked")
	private boolean queryUser()
    {
    	System.out.println("1.33");
        LDUserDB tLDUserDB = mLDUserSchema.getDB();
        mLDUserSet = tLDUserDB.query();
    	System.out.println("1.44");
        if (tLDUserDB.mErrors.needDealError())
        {
        
            // @@������
            this.mErrors.copyAllErrors(tLDUserDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LDUserBL";
            tError.functionName = "queryUser";
            tError.errorMessage = "�û���ѯʧ��!";
            this.mErrors.addOneError(tError);
        	System.out.println(mErrors.getFirstError()+"]");
            mLDUserSet.clear();
            return false;
        }

        if (mLDUserSet.size() == 0)
        {
            // @@������
            CError tError = new CError();
            tError.moduleName = "LDUserBL";
            tError.functionName = "queryUser";
            tError.errorMessage = "δ�ҵ��������!";
            this.mErrors.addOneError(tError);
            System.out.println(mErrors.getFirstError()+"]2");
            mLDUserSet.clear();
            return false;
        }
        mResult.add(mLDUserSet);
        mResultNum = mLDUserSet.size();

        System.out.println(mResult);
        return true;
    }

    @SuppressWarnings("unchecked")
	public static void main(String[] args)
    {
    LDUserBL tLDUserBL1 = new LDUserBL();
    LDUserSchema tSchema = new LDUserSchema();
    tSchema.setUserCode("0011");
    tSchema.setPassword("8551EF81DAF69093");
    VData tVData = new VData();
    tVData.add(tSchema);
    if (!tLDUserBL1.submitData(tVData, "query"))
      System.out.println("ppp");
    System.out.println("kkkk");
    for (int i = 0; i < tLDUserBL1.mErrors.getErrorCount(); i++) {
      CError tError = tLDUserBL1.mErrors.getError(i);
      System.out.println(tError.errorMessage);
      System.out.println(tError.moduleName);
      System.out.println(tError.functionName);
      System.out.println("----------------");
    }
    }
}
