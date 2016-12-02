/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.logon;

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
 * @author YT
 * @version 1.0
 */
public class MenuUI
{
    /** �������࣬ÿ����Ҫ����������ж����ø��� */
    public CErrors mErrors = new CErrors();

    /** �����洫�����ݵ����� */
    private VData mInputData = new VData();

    /** ���ݲ����ַ��� */
    private String mOperate;

    public MenuUI()
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

        MenuBL tMenuBL = new MenuBL();

        if (tMenuBL.submitData(cInputData, mOperate))
        {
            mInputData = tMenuBL.getResult();
        }
        else
        {
            // @@������
            this.mErrors.copyAllErrors(tMenuBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "MenuUI";
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

    /**
     * ���Ժ���
     * @param args String[]
     */
    public static void main(String[] args)
    {
//        MenuUI tMenuUI = new MenuUI();
//        VData tData = new VData();
//        tMenuUI.submitData(tData, "QUERY||MAIN");
//        if (tMenuUI.mErrors.needDealError())
//        {
//            System.out.println(tMenuUI.mErrors.getFirstError());
//        }
//        else
//        {
//            tData.clear();
//            tData = tMenuUI.getResult();
//            String tStr = "";
//            tStr = (String) tData.get(0);
//            System.out.println(StrTool.unicodeToGBK(tStr));
//        }
    }
}
