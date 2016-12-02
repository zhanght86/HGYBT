/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.logon;

import com.sinosoft.lis.db.LDMenuDB;
import com.sinosoft.lis.schema.LDMenuSchema;
import com.sinosoft.lis.vschema.LDMenuSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;


/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description: �˵���ѯҵ���߼�������</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author YT
 * @version 1.0
 */
public class MenuBL
{
    /** �������࣬ÿ����Ҫ����������ж����ø��� */
    public CErrors mErrors = new CErrors();

    /** �����洫�����ݵ����� */
    private VData mResult = new VData();

    /** ���ݲ����ַ��� */
//    private String mOperate;


    /** ҵ������ر��� */
    /** �˵� */
    private LDMenuSchema mLDMenuSchema = new LDMenuSchema();
    private LDMenuSet mLDMenuSet = new LDMenuSet();


    /** ���ص�����*/
//    private String mResultStr = "";
    private StringBuffer mResultStr = new StringBuffer(256);

    public MenuBL()
    {}


    /**
     * �������ݵĹ�������
     * @param cInputData VData
     * @param cOperate String
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //���������ݿ�����������
//        this.mOperate = cOperate;

        //�õ��ⲿ���������,�����ݱ��ݵ�������
        if (!getInputData(cInputData))
        {
            return false;
        }
//        System.out.println("---getInputData---");

        //����ҵ����
        if (cOperate.equals("QUERY||MAIN"))
        {
            if (!queryData())
            {
                return false;
            }
//            System.out.println("---queryData---");
        }
        if (cOperate.equals("QUERY||DETAIL"))
        {
            if (!queryDetail())
            {
                return false;
            }
//            System.out.println("---queryDetail---");
        }

        return true;
    }

    public VData getResult()
    {
        return mResult;
    }


    /**
     * �����������еõ����ж���
     * ��������û�еõ��㹻��ҵ�����ݶ����򷵻�false,���򷵻�true
     * @param cInputData VData
     * @return boolean
     */
    private static boolean getInputData(VData cInputData)
    {
        // ������ѯ����
        return true;
    }


    /**
     * ��ѯ���������ı���
     * ��������׼������ʱ���������򷵻�false,���򷵻�true
     * @return boolean
     */
    private boolean queryData()
    {
        // �˵���
        mLDMenuSchema = new LDMenuSchema();
        LDMenuDB tLDMenuDB = mLDMenuSchema.getDB();
        mLDMenuSet = tLDMenuDB.executeQuery("select * from ldmenu order by nodeorder");
        if (tLDMenuDB.mErrors.needDealError())
        {
            // @@������
            this.mErrors.copyAllErrors(tLDMenuDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "MenuBL";
            tError.functionName = "queryData";
            tError.errorMessage = "������ѯʧ��!";
            this.mErrors.addOneError(tError);
            mLDMenuSet.clear();
            return false;
        }

        int i, iMax;
        iMax = mLDMenuSet.size();
        if (iMax == 0)
        {
            // @@������
            CError tError = new CError();
            tError.moduleName = "MenuBL";
            tError.functionName = "queryData";
            tError.errorMessage = "δ�ҵ��������!";
            this.mErrors.addOneError(tError);
            mLDMenuSet.clear();
            return false;
        }
//        mResultStr = "";
        mResultStr = new StringBuffer(256);

        for (i = 1; i <= iMax; i++)
        {
            mLDMenuSchema = mLDMenuSet.get(i);
            mResultStr.append(mLDMenuSchema.getParentNodeCode());
            mResultStr.append("|");
            mResultStr.append(mLDMenuSchema.getNodeCode());
            mResultStr.append("|");
            mResultStr.append(mLDMenuSchema.getNodeName());
            mResultStr.append("|");
            mResultStr.append(mLDMenuSchema.getChildFlag());
            mResultStr.append("|");
            mResultStr.append(mLDMenuSchema.getRunScript());
            mResultStr.append("|");

//            mResultStr += mLDMenuSchema.getParentNodeCode() + "|";
//            mResultStr += mLDMenuSchema.getNodeCode() + "|";
//            mResultStr += mLDMenuSchema.getNodeName() + "|";
//            mResultStr += mLDMenuSchema.getChildFlag() + "|";
//            mResultStr += mLDMenuSchema.getRunScript() + "|";
        }
        mResult.clear();
        mResult.add(mResultStr.toString());

        return true;
    }

    /**
     * ��ѯ������ϸ��Ϣ
     * �����������������򷵻�false,���򷵻�true
     * @return boolean
     */
    private static boolean queryDetail()
    {
        return true;
    }
}
