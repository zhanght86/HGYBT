/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.userMan;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description:�û�������
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Dingzhong
 * @version 1.0
 */
public class LDUserUI
{
    /** �������࣬ÿ����Ҫ����������ж����ø��� */
    public CErrors mErrors = new CErrors();

    /** �����洫�����ݵ����� */
    private VData mInputData = new VData();

    /** ���ݲ����ַ��� */
    private String mOperate;

    int mResultNum = 0;

    public LDUserUI()
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
//        System.out.println("start user UI submit1...");
        this.mOperate = cOperate;

        LDUserBL tUserBL = new LDUserBL();

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
            tError.moduleName = "LDUserUI";
            tError.functionName = "submitData";
            tError.errorMessage = "���ݲ�ѯʧ��!";
            System.out.println(tError.errorMessage);
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
}
