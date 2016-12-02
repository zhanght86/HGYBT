/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.menumang;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.schema.LDUserSchema;

/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description:�˵���ѯ������
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Dingzhong
 * @version 1.0
 */
public class LDUserAgentUI
{
    /** �������࣬ÿ����Ҫ����������ж����ø��� */
    public CErrors mErrors = new CErrors();

    /** �����洫�����ݵ����� */
    private VData mInputData = new VData();

    /** ���ݲ����ַ��� */
    private String mOperate;

    int mResultNum = 0;

    public LDUserAgentUI()
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
        this.mOperate = cOperate;

        LDUserAgentBL tUserBL = new LDUserAgentBL();

        if (tUserBL.submitData(cInputData, mOperate))
        {
            mInputData = tUserBL.getResult();
            mResultNum = tUserBL.getResultNum();
        }
        else
        {
            // @@������
            this.mErrors.copyAllErrors(tUserBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LDUserAgentUI";
            tError.functionName = "submitData";
            tError.errorMessage = "���ݲ�ѯʧ��!";
            this.mErrors.addOneError(tError);
            mInputData.clear();
            return false;
        }
        return true;
    }

    public VData getResult()
    {
        return mInputData;
    }

    public int getResultNum()
    {
        return mResultNum;
    }

    /**
     * ���Ժ���
     * @param args String[]
     */
    public static void main(String[] args)
    {
        LDUserAgentBL tLDUserAgentBL1 = new LDUserAgentBL();
        LDUserSchema tSchema = new LDUserSchema();
        tSchema.setUserCode("001");
        tSchema.setPassword("26B029A2DCE2820C");
//        tSchema.setComCode("86");
        VData tVData = new VData();
        tVData.add(tSchema);
        if (!tLDUserAgentBL1.submitData(tVData, "query"))
        {
            System.out.println("ppp");
        }
        System.out.println("kkkk");
        for (int i = 0; i < tLDUserAgentBL1.mErrors.getErrorCount(); i++)
        {
            CError tError = tLDUserAgentBL1.mErrors.getError(i);
            System.out.println(tError.errorMessage);
            System.out.println(tError.moduleName);
            System.out.println(tError.functionName);
            System.out.println("----------------");
        }
    }
}
