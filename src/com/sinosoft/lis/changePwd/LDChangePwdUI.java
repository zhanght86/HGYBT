package com.sinosoft.lis.changePwd;

import com.sinosoft.lis.encrypt.LisIDEA;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description:
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Dingzhong
 * @version 1.0
 */
public class LDChangePwdUI
{
    /** �������࣬ÿ����Ҫ����������ж����ø��� */
    public CErrors mErrors = new CErrors();
    /** �����洫�����ݵ����� */
    private VData mInputData = new VData();
    /** ���ݲ����ַ��� */
    private String mOperate;

    int mResultNum = 0;

    public LDChangePwdUI()
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

        LDChangePwdBL tChangePwdBL = new LDChangePwdBL();

        if (tChangePwdBL.submitData(cInputData, mOperate))
        {
            return true;
        }
        else
        {
            // @@������
            this.mErrors.copyAllErrors(tChangePwdBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "ChangePwdUI";
            tError.functionName = "submitData";
            tError.errorMessage = "���ݲ�ѯʧ��!";
            this.mErrors.addOneError(tError);
            mInputData.clear();
            return false;
        }
    }

    public static void main(String[] args)
    {
    	LisIDEA tLisIdea = new LisIDEA();
    	String sKey = tLisIdea.encryptString("1234567890");
    	String ssKey = tLisIdea.decryptString(sKey);
    	System.out.println(sKey);
    	System.out.println(ssKey);
    }
}
