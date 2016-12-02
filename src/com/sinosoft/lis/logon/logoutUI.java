/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.logon;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;


/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description:�ǳ�������
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author dingzhong
 * @version 1.0
 */

public class logoutUI
{
    private VData mInputData;
    public CErrors mErrors = new CErrors();

    public logoutUI()
    {
    }

    //�������ݵĹ�������
    public boolean submitData(VData cInputData, String cOperate)
    {
        //���Ƚ������ڱ�������һ������
        mInputData = (VData) cInputData.clone();

        //��BL�㴫������
//        System.out.println("Start logout UI Submit...");
        logoutBL tlogoutBL = new logoutBL();
        tlogoutBL.submitData(mInputData, cOperate);
//        System.out.println("End logout UI Submit...");

        //�������Ҫ����Ĵ����򷵻�
        if (tlogoutBL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tlogoutBL.mErrors);
        }

//        System.out.println(mErrors.getErrorCount());
        mInputData = null;
        return true;
    }

    public static void main(String[] args)
    {
    }
}
