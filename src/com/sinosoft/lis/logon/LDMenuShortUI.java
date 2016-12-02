/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.logon;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;


/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description:��ݲ˵��ࣨ�������룩
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author hubo
 * @version 1.0
 */
public class LDMenuShortUI
{
    private VData mInputData;
    public CErrors mErrors = new CErrors();

    public LDMenuShortUI()
    {
    }

    //�������ݵĹ�������
    public boolean submitData(VData cInputData, String cOperate)
    {
        //���Ƚ������ڱ�������һ������
        mInputData = (VData) cInputData.clone();

        //��BL�㴫������
//        System.out.println("Start LDMenuShort UI Submit...");
        LDMenuShortBL tLDMenuShortBL = new LDMenuShortBL();
        tLDMenuShortBL.submitData(mInputData, cOperate);
//        System.out.println("End LDPerson UI Submit...");

        //�������Ҫ����Ĵ����򷵻�
        if (tLDMenuShortBL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tLDMenuShortBL.mErrors);
        }

//        System.out.println(mErrors.getErrorCount());
        mInputData = null;
        return true;
    }

    public boolean deleteData(VData cInputData, String cOperate)
    {
        //���Ƚ������ڱ�������һ������
        mInputData = (VData) cInputData.clone();

        //��BL�㴫������
//        System.out.println("Start LDMenuShort UI deleteData...");
        LDMenuShortBL tLDMenuShortBL = new LDMenuShortBL();
        tLDMenuShortBL.deleteData(mInputData, cOperate);
//        System.out.println("End LDMenuShort UI deleteData...");

        //�������Ҫ����Ĵ����򷵻�
        if (tLDMenuShortBL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tLDMenuShortBL.mErrors);
        }

//        System.out.println(mErrors.getErrorCount());
        mInputData = null;
        return true;
    }

    public static void main(String[] args)
    {
    }
}
