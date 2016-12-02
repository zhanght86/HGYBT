/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.menumang;

import com.sinosoft.lis.db.LDMenuGrpDB;
import com.sinosoft.lis.schema.LDMenuGrpSchema;
import com.sinosoft.lis.schema.LDUserSchema;
import com.sinosoft.lis.vschema.LDMenuGrpSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description: �˵����ѯҵ���߼�������</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author DingZhong
 * @version 1.0
 */
public class LDMenuGrpQueryBL
{
    /** �������࣬ÿ����Ҫ����������ж����ø��� */
    public CErrors mErrors = new CErrors();
    /** �����洫�����ݵ����� */
    private VData mResult = new VData();
    /** ���ݲ����ַ��� */
    private String mOperate;

    /** ҵ������ر��� */
    /** �˵��顢�û����˵���������Ϣ*/
    LDMenuGrpSchema mLDMenuGrpSchema = new LDMenuGrpSchema();
    LDMenuGrpSet mLDMenuGrpSet = new LDMenuGrpSet();
    LDUserSchema mLDUserSchema = new LDUserSchema();


    String mResultStr = "";
    int mResultNum = 0;

    public LDMenuGrpQueryBL()
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
        if (!cOperate.equals("query") && !cOperate.equals("query_remain"))
        {
            System.out.println("Operater is denied");
            return false;
        }
        System.out.println("start submit...");
        //���������ݿ�����������
        this.mOperate = cOperate;

        //�õ��ⲿ���������,�����ݱ��ݵ�������
        if (!getInputData(cInputData))
        {
            return false;
        }
        System.out.println("---getInputData---");

        //����ҵ����
        if (cOperate.equals("query") && !queryMenuGrp())
        {
            System.out.println("query fail");
            return false;
        }

        else if (cOperate.equals("query_remain") && !queryRemainMenuGrp())
        {
            return false;
        }

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
        for (int i = 1; i <= mResultNum; i++)
        {
            mLDMenuGrpSchema = mLDMenuGrpSet.get(i);
        }
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
        // �����ѯ����
        mLDUserSchema = (LDUserSchema) cInputData.getObjectByObjectName(
                "LDUserSchema", 0);

        if (mLDUserSchema != null)
        {
            return true;
        }

        mLDMenuGrpSchema = (LDMenuGrpSchema) cInputData.getObjectByObjectName(
                "LDMenuGrpSchema", 0);

        if (mLDMenuGrpSchema != null)
        {
            return true;
        }

        System.out.println("completed get input data");
        return true;
    }

    /**
     * ��ѯ���������Ĳ˵������Ϣ
     * ��������׼������ʱ���������򷵻�false,���򷵻�true
     * @return boolean
     */
    private boolean queryMenuGrp()
    {
        LDMenuGrpSchema tLDMenuGrpSchema = new LDMenuGrpSchema();
        LDMenuGrpDB tLDMenuGrpDB = tLDMenuGrpSchema.getDB();
        System.out.println("aaa");
        if (mLDUserSchema != null)
        {
            //ͨ���û������ѯ�˵��鼯��
            System.out.println("ldMenuGrpQueryBL.java:ͨ���û������ѯ�˵��鼯��query by LDUserSchema");//2005
            // �����ѯ���
//            String sqlStr = "select * from LDMenuGrp where MenuGrpCode in ( select MenuGrpCode from LDUserToMenuGrp where UserCode = " +
//                            mLDUserSchema.getUserCode() +
//                            ") ";
            StringBuffer tSql = new StringBuffer();
            tSql.append("select * from LDMenuGrp where MenuGrpCode in ( select MenuGrpCode from LDUserToMenuGrp where UserCode = ");
            tSql.append(mLDUserSchema.getUserCode());
            tSql.append(") ");

            mLDMenuGrpSet = tLDMenuGrpDB.executeQuery(tSql.toString());
            System.out.println("StringBuffer : " + tSql.toString());
        }
        else if (mLDMenuGrpSchema != null)
        {
            //ͨ���˵�������ѯ�˵�����
            System.out.println("ldMenuGrpQueryBL.java:ͨ���û������ѯ�˵��鼯��query by mLDMenuGrpSchema");//2005
            String sqlStr = "select * from LDMenuGrp where MenuGrpCode = " +
                            mLDMenuGrpSchema.getMenuGrpCode() + "";
//            LDMenuGrpSet tLDMenuGrpSet = tLDMenuGrpDB.executeQuery(sqlStr);
            System.out.println(sqlStr);
        }
        else
        {
            //Ĭ�Ϸ���ȫ���ڵ�
            System.out.print("ldMenuGrpQueryBL.java:Ĭ�Ϸ���ȫ���ڵ�");//2005
            mLDMenuGrpSet = tLDMenuGrpDB.executeQuery(
                    "select * from ldmenugrp ");
        }

        if (tLDMenuGrpDB.mErrors.needDealError())
        {
            // @@������
            this.mErrors.copyAllErrors(tLDMenuGrpDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LDMenuGrpQueryBL";
            tError.functionName = "queryGrpMenu";
            tError.errorMessage = "�û��˵����ѯʧ��!";
            this.mErrors.addOneError(tError);
            mLDMenuGrpSet.clear();
            return false;
        }

        if (mLDMenuGrpSet.size() == 0)
        {
            // @@������
            CError tError = new CError();
            tError.moduleName = "LDMenuBL";
            tError.functionName = "queryMenu";
            tError.errorMessage = "δ�ҵ��������!";
            this.mErrors.addOneError(tError);
            mLDMenuGrpSet.clear();
            return false;
        }
        mResultNum = mLDMenuGrpSet.size();
        mResult.add(mLDMenuGrpSet);
        System.out.println(mResult);
        return true;
    }


    /**
     * ��ѯ���������Ĳ˵�����Ϣ
     * ��������׼������ʱ���������򷵻�false,���򷵻�true
     * @return boolean
     */
    private boolean queryRemainMenuGrp()
    {
        LDMenuGrpSchema tLDMenuGrpSchema = new LDMenuGrpSchema();
        LDMenuGrpDB tLDMenuGrpDB = tLDMenuGrpSchema.getDB();

        if (mLDUserSchema != null)
        {
            //ͨ���û������ѯ�˵��鼯��
            System.out.println("query by LDUserSchema");
            // �����ѯ���
//            String sqlStr = "select * from LDMenuGrp where MenuGrpCode not in ( select MenuGrpCode from LDUserToMenuGrp where MenuGrpCode = " +
//                     mLDUserSchema.getUserCode() + ") ";
            StringBuffer tSql = new StringBuffer();
            tSql.append("select * from LDMenuGrp where MenuGrpCode not in ( select MenuGrpCode from LDUserToMenuGrp where MenuGrpCode = ");
            tSql.append(mLDUserSchema.getUserCode());
            tSql.append(") ");

            mLDMenuGrpSet = tLDMenuGrpDB.executeQuery(tSql.toString());
            System.out.println("StringBuffer : " + tSql.toString());
        }
        else if (mLDMenuGrpSchema != null)
        {
            //ͨ���˵�������ѯ�˵�����
            String sqlStr = "select * from LDMenuGrp where MenuGrpCode <>" +
                            mLDMenuGrpSchema.getMenuGrpCode() + "";
            LDMenuGrpSet tLDMenuGrpSet = tLDMenuGrpDB.executeQuery(sqlStr);
            System.out.println(tLDMenuGrpSet.size());

        }
        else
        {
            //Ĭ�Ϸ���ȫ���ڵ�
            mLDMenuGrpSet = tLDMenuGrpDB.executeQuery(
                    "select * from ldmenugrp ");
        }

        if (tLDMenuGrpDB.mErrors.needDealError())
        {
            // @@������
            this.mErrors.copyAllErrors(tLDMenuGrpDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LDMenuGrpQueryBL";
            tError.functionName = "queryGrpMenu";
            tError.errorMessage = "�û��˵����ѯʧ��!";
            this.mErrors.addOneError(tError);
            mLDMenuGrpSet.clear();
            return false;
        }

        if (mLDMenuGrpSet.size() == 0)
        {
            // @@������
            CError tError = new CError();
            tError.moduleName = "LDMenuBL";
            tError.functionName = "queryMenu";
            tError.errorMessage = "δ�ҵ��������!";
            this.mErrors.addOneError(tError);
            mLDMenuGrpSet.clear();
            return false;
        }
        mResultNum = mLDMenuGrpSet.size();
        mResult.add(mLDMenuGrpSet);
        System.out.println(mResult);
        return true;
    }

    public static void main(String[] args)
    {
//        LDMenuGrpQueryBL tLDMenuGrpQueryBL1 = new LDMenuGrpQueryBL();
//
//        LDMenuGrpSchema tSchema = new LDMenuGrpSchema();
//        tSchema.setMenuGrpCode("007");
//
//        LDUserSchema tUserSchema = new LDUserSchema();
//        tUserSchema.setUserCode("101");
//
//        VData tVData = new VData();
//        tVData.add(tSchema);
//
//        tLDMenuGrpQueryBL1.submitData(tVData, "query");
//        System.out.println(tLDMenuGrpQueryBL1.getResultNum());
//        for (int i = 0; i < tLDMenuGrpQueryBL1.mErrors.getErrorCount(); i++)
//        {
//            CError tError = tLDMenuGrpQueryBL1.mErrors.getError(i);
//            System.out.println(tError.errorMessage);
//            System.out.println(tError.moduleName);
//            System.out.println(tError.functionName);
//            System.out.println("----------------");
//        }
    }
}
