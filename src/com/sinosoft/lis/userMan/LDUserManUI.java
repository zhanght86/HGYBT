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
public class LDUserManUI
{
    /** �������࣬ÿ����Ҫ����������ж����ø��� */
    public CErrors mErrors = new CErrors();
    /** �����洫�����ݵ����� */
    private VData mInputData = new VData();
    /** ���ݲ����ַ��� */
    private String mOperate;

    int mResultNum = 0;

    public LDUserManUI()
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
        System.out.println("start UI submit...");
        this.mOperate = cOperate;

        LDUserManBL tUserManBL = new LDUserManBL();

        if (tUserManBL.submitData(cInputData, mOperate))
        {
            mInputData = tUserManBL.getResult();
            mResultNum = tUserManBL.getResultNum();
        }
        else
        {
            // @@������
            this.mErrors.copyAllErrors(tUserManBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LDUserUI";
            tError.functionName = "submitData";
            tError.errorMessage = "���ݲ�ѯʧ��!";
            System.out.println(tError.errorMessage);
            this.mErrors.addOneError(tError);
            mInputData.clear();
            return false;
        }
        System.out.println("end UI submit...");
        return true;
    }

    /**
     * ��ȡmInputData
     * @return VData
     */
    public VData getResult()
    {
        return mInputData;
    }

    /**
     * ��ȡmResultNum
     * @return int
     */
    public int getResultNum()
    {
        return mResultNum;
    }
}
