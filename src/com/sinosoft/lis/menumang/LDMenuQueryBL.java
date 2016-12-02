/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.menumang;

import com.sinosoft.lis.db.LDMenuDB;
import com.sinosoft.lis.schema.LDMenuGrpSchema;
import com.sinosoft.lis.schema.LDMenuSchema;
import com.sinosoft.lis.schema.LDUserSchema;
import com.sinosoft.lis.vschema.LDMenuSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description: �˵���ѯҵ���߼�������</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author DingZhong
 * @version 1.0
 */
public class LDMenuQueryBL
{
    /** �������࣬ÿ����Ҫ����������ж����ø��� */
    public CErrors mErrors = new CErrors();
    /** �����洫�����ݵ����� */
    private VData mResult = new VData();
    /** ���ݲ����ַ��� */
//    private String mOperate;

    /** ҵ������ر��� */
    /** �˵��顢�˵��鵽�˵��������Ϣ*/
    LDMenuSchema mLDMenuSchema = new LDMenuSchema();
    LDMenuSet mLDMenuSet = new LDMenuSet();
    LDUserSchema mLDUserSchema = new LDUserSchema();
    LDMenuGrpSchema mLDMenuGrpSchema = new LDMenuGrpSchema();

//    String mResultStr = "";
    int mResultNum = 0;
    private StringBuffer mResultStr = new StringBuffer(256);
    private StringBuffer mSBql = new StringBuffer(256);

    public LDMenuQueryBL()
    {
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
//            System.out.println("Operater is denied");
            return false;
        }
//        System.out.println("start submit...");
        //���������ݿ�����������
//        this.mOperate = cOperate;

        //�õ��ⲿ���������,�����ݱ��ݵ�������
        if (!getInputData(cInputData))
        {
            return false;
        }
//        System.out.println("---getInputData---");

        //����ҵ����
        if (cOperate.equals("query") && !queryMenu())
        {
            return false;
        }
        else if (cOperate.equals("query_remain") && !queryRemainMenu())
        {
            return false;
        }

//        System.out.println("---queryMenuGrpToMenu---");

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
//        String resultStr = "";
        mResultStr = new StringBuffer(256);
        for (int i = 1; i <= mResultNum; i++)
        {
            mLDMenuSchema = mLDMenuSet.get(i);
            if (i > 1)
            {
//                resultStr += SysConst.RECORDSPLITER;
                mResultStr.append(SysConst.RECORDSPLITER);
            }
//            resultStr += mLDMenuSchema.getParentNodeCode();
//            resultStr += "|" + mLDMenuSchema.getNodeCode();
//            resultStr += "|" + mLDMenuSchema.getNodeName();
//            resultStr += "|" + mLDMenuSchema.getChildFlag();
//            resultStr += "|" + mLDMenuSchema.getRunScript();
            mResultStr.append(mLDMenuSchema.getParentNodeCode());
            mResultStr.append("|");
            mResultStr.append(mLDMenuSchema.getNodeCode());
            mResultStr.append("|");
            mResultStr.append(mLDMenuSchema.getNodeName());
            mResultStr.append("|");
            mResultStr.append(mLDMenuSchema.getChildFlag());
            mResultStr.append("|");
            mResultStr.append(mLDMenuSchema.getRunScript());
        }
        return mResultStr.toString();
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
        System.out.println("start get input data...");
        mLDUserSchema = (LDUserSchema) cInputData.getObjectByObjectName(
                "LDUserSchema", 0);
        System.out.println("mLDUserSchema:"+mLDUserSchema);
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

        mLDMenuSchema = (LDMenuSchema) cInputData.getObjectByObjectName(
                "LDMenuSchema", 0);

        if (mLDMenuSchema != null)
        {
            return true;
        }
        System.out.println("completed get input data");
        return true;
    }

    /**
     * ��ѯ���������Ĳ˵�����Ϣ
     * ��������׼������ʱ���������򷵻�false,���򷵻�true
     * @return boolean
     */
    private boolean queryMenu()
    {
        LDMenuSchema tLDMenuSchema = new LDMenuSchema();
        LDMenuDB tLDMenuDB = tLDMenuSchema.getDB();

        if (mLDUserSchema != null)
        {
            //ͨ���û������ѯ�˵�����
            // �����ѯ���
//            String sqlStr = "select * from LDMenu where NodeCode in ( select NodeCode from LDMenuGrpToMenu ";
//            sqlStr = sqlStr +
//                     "where MenuGrpCode in ( select MenuGrpCode from LDMenuGrp ";
//            sqlStr = sqlStr +
//                     "where MenuGrpCode in (select MenuGrpCode from LDUserToMenuGrp where UserCode = '" +
//                     mLDUserSchema.getUserCode() +
//                     "')  )   ) order by nodeorder";
            mSBql.append("select * from LDMenu where NodeCode in ( select NodeCode from LDMenuGrpToMenu ");
            mSBql.append("where MenuGrpCode in ( select MenuGrpCode from LDMenuGrp ");
            mSBql.append("where MenuGrpCode in (select MenuGrpCode from LDUserToMenuGrp where UserCode = '");
            mSBql.append(mLDUserSchema.getUserCode());
            mSBql.append("') ) ) order by nodeorder");

            mLDMenuSet = tLDMenuDB.executeQuery(mSBql.toString());
        }
        else if (mLDMenuGrpSchema != null)
        {
            //ͨ���˵�������ѯ�˵�����
            // �����ѯ���
//            String sqlStr = "select * from LDMenu where NodeCode in ( select NodeCode from LDMenuGrpToMenu ";
//            sqlStr = sqlStr +
//                     "where MenuGrpCode in ( select MenuGrpCode from LDMenuGrp where MenuGrpCode ='" +
//                     mLDMenuGrpSchema.getMenuGrpCode() + "') ) order by nodeorder";
            mSBql.append("select * from LDMenu where NodeCode in ( select NodeCode from LDMenuGrpToMenu ");
            mSBql.append("where MenuGrpCode in ( select MenuGrpCode from LDMenuGrp where MenuGrpCode ='");
            mSBql.append(mLDMenuGrpSchema.getMenuGrpCode());
            mSBql.append("') ) order by nodeorder");

//            LDMenuSet tLDMenuSet = tLDMenuDB.executeQuery(sqlStr);
//            System.out.println(tLDMenuSet.size());
            mLDMenuSet = tLDMenuDB.executeQuery(mSBql.toString());

        }
        else if (mLDMenuSchema != null)
        {
            //ͨ���ڵ�����ѯ
            mLDMenuSet = tLDMenuDB.query();
        }
        else
        {
            //Ĭ�Ϸ���ȫ���ڵ�
            mLDMenuSet = tLDMenuDB.executeQuery(
                    "select * from ldmenu order by nodeorder");
        }

        if (tLDMenuDB.mErrors.needDealError())
        {
            // @@������
            this.mErrors.copyAllErrors(tLDMenuDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LDMenuQueryBL";
            tError.functionName = "queryMenu";
            tError.errorMessage = "�û��˵���ѯʧ��!";
            this.mErrors.addOneError(tError);
            mLDMenuSet.clear();
            return false;
        }

        if (mLDMenuSet.size() == 0)
        {
            // @@������
            CError tError = new CError();
            tError.moduleName = "LDMenuBL";
            tError.functionName = "queryMenu";
            tError.errorMessage = "δ�ҵ��������!";
            this.mErrors.addOneError(tError);
            mLDMenuSet.clear();
            return false;
        }
        mResultNum = mLDMenuSet.size();
        mResult.add(mLDMenuSet);
//        System.out.println(mResult);
        return true;
    }


    /**
     * ��ѯ���������Ĳ˵�����Ϣ
     * ��������׼������ʱ���������򷵻�false,���򷵻�true
     * @return boolean
     */
    private boolean queryRemainMenu()
    {
        LDMenuSchema tLDMenuSchema = new LDMenuSchema();
        LDMenuDB tLDMenuDB = tLDMenuSchema.getDB();

        if (mLDUserSchema != null)
        {
            //ͨ���û������ѯ�˵�����
//            System.out.println("query by LDUserSchema");
            // �����ѯ���
//            String sqlStr = "select * from LDMenu where NodeCode not in ( select NodeCode from LDMenuGrpToMenu ";
//            sqlStr = sqlStr +
//                     "where MenuGrpCode in ( select MenuGrpCode from LDMenuGrp ";
//            sqlStr = sqlStr +
//                     "where MenuGrpCode in (select MenuGrpCode from LDUserToMenuGrp where UserCode = " +
//                     mLDUserSchema.getUserCode() +
//                     ") ) ) order by nodeorder";
            mSBql.append("select * from LDMenu where NodeCode not in ( select NodeCode from LDMenuGrpToMenu ");
            mSBql.append("where MenuGrpCode in ( select MenuGrpCode from LDMenuGrp ");
            mSBql.append("where MenuGrpCode in (select MenuGrpCode from LDUserToMenuGrp where UserCode = ");
            mSBql.append(mLDUserSchema.getUserCode());
            mSBql.append(") ) ) order by nodeorder");

            mLDMenuSet = tLDMenuDB.executeQuery(mSBql.toString());

        }
        else if (mLDMenuGrpSchema != null)
        {
            //ͨ���˵�������ѯ�˵�����
            // �����ѯ���
//            String sqlStr = "select * from LDMenu where NodeCode not in ( select NodeCode from LDMenuGrpToMenu ";
//            sqlStr = sqlStr +
//                     "where MenuGrpCode in ( select MenuGrpCode from LDMenuGrp where MenuGrpCode ='" +
//                     mLDMenuGrpSchema.getMenuGrpCode() + "') ) order by nodeorder";
            mSBql.append("select * from LDMenu where NodeCode not in ( select NodeCode from LDMenuGrpToMenu ");
            mSBql.append("where MenuGrpCode in ( select MenuGrpCode from LDMenuGrp where MenuGrpCode ='");
            mSBql.append(mLDMenuGrpSchema.getMenuGrpCode());
            mSBql.append("') ) order by nodeorder");

//            System.out.println(sqlStr);
//            LDMenuSet tLDMenuSet = tLDMenuDB.executeQuery(sqlStr);
            mLDMenuSet = tLDMenuDB.executeQuery(mSBql.toString());

        }
        else if (mLDMenuSchema != null)
        {
            //ͨ���ڵ�����ѯ
            String sqlStr = "select * from LDMenu where NodeCode <> mDLMenuSchema.getNodeCode order by nodeorder";
            mLDMenuSet = tLDMenuDB.executeQuery(sqlStr);
        }
        else
        {
            //Ĭ�Ϸ���ȫ���ڵ�
            mLDMenuSet = tLDMenuDB.executeQuery(
                    "select * from ldmenu order by nodeorder");
        }

        if (tLDMenuDB.mErrors.needDealError())
        {
            // @@������
            this.mErrors.copyAllErrors(tLDMenuDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LDMenuQueryBL";
            tError.functionName = "queryRemainMenu";
            tError.errorMessage = "�û��˵���ѯʧ��!";
            this.mErrors.addOneError(tError);
            mLDMenuSet.clear();
            return false;
        }

        if (mLDMenuSet.size() == 0)
        {
            // @@������
            CError tError = new CError();
            tError.moduleName = "LDMenuBL";
            tError.functionName = "queryRemainMenu";
            tError.errorMessage = "δ�ҵ��������!";
            this.mErrors.addOneError(tError);
            mLDMenuSet.clear();
            return false;
        }
        mResultNum = mLDMenuSet.size();
        mResult.add(mLDMenuSet);
//        System.out.println(mResult);
        return true;
    }

    public static void main(String[] args)
    {
//        LDMenuQueryBL tLDMenuQueryBL1 = new LDMenuQueryBL();
//        LDMenuGrpSchema tSchema = new LDMenuGrpSchema();
//        tSchema.setMenuGrpCode("007");
//        VData tVData = new VData();
//        tVData.add(tSchema);
//        tLDMenuQueryBL1.submitData(tVData, "query");
//        String str = tLDMenuQueryBL1.getResultStr();
//        System.out.println(str);
//        for (int i = 0; i < tLDMenuQueryBL1.mErrors.getErrorCount(); i++)
//        {
//            CError tError = tLDMenuQueryBL1.mErrors.getError(i);
//            System.out.println(tError.errorMessage);
//            System.out.println(tError.moduleName);
//            System.out.println(tError.functionName);
//            System.out.println("----------------");
//        }
    }
}
