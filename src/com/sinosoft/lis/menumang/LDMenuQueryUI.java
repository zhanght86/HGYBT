/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.menumang;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
//import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description:�˵���ѯ������
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Dingzhong
 * @version 1.0
 */
public class LDMenuQueryUI
{
    /** �������࣬ÿ����Ҫ����������ж����ø��� */
    public CErrors mErrors = new CErrors();
    /** �����洫�����ݵ����� */
    private VData mInputData = new VData();
    /** ���ݲ����ַ��� */
    private String mOperate;

    int mResultNum = 0;
    String mResultStr = "";

    public LDMenuQueryUI()
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

        LDMenuQueryBL tMenuQueryBL = new LDMenuQueryBL();

        if (tMenuQueryBL.submitData(cInputData, mOperate))
        {
            mInputData = tMenuQueryBL.getResult();
            mResultNum = tMenuQueryBL.getResultNum();
            mResultStr = tMenuQueryBL.getResultStr();
        }
        else
        {
            // @@������
            this.mErrors.copyAllErrors(tMenuQueryBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LDMenuQueryUI";
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

    public String getResultStr()
    {
        return mResultStr;
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
//        LDMenuQueryUI tMenuQueryUI = new LDMenuQueryUI();
//        VData tData = new VData();
//        tMenuQueryUI.submitData(tData, "query");
//        if (tMenuQueryUI.mErrors.needDealError())
//        {
//            System.out.println(tMenuQueryUI.mErrors.getFirstError());
//        }
//        else
//        {
//            tData.clear();
//            tData = tMenuQueryUI.getResult();
//            String tStr = "";
//            tStr = (String) tData.get(0);
//            System.out.println(StrTool.unicodeToGBK(tStr));
//        }
    }
}
