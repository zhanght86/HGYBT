/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.menumang;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description:
 * �Ӵ����������̳У���������������,��ÿ�����ж�����
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author DingZhong
 * @version 1.0
 */

public class LDMenuGrpUI
{

    //ҵ������ر���
    private VData mInputData;
    public CErrors mErrors = new CErrors();

    public LDMenuGrpUI()
    {
    }

    //�������ݵĹ�������
    public boolean submitData(VData cInputData, String cOperate)
    {
        //���Ƚ������ڱ�������һ������
        mInputData = (VData) cInputData.clone();
        LDMenuGrpBL tLDMenuGrpBL = new LDMenuGrpBL();
//        System.out.println("Start LDMenuGrp UI Submit...");
        boolean tag = tLDMenuGrpBL.submitData(mInputData, cOperate);
        if (!tag)
        {
            return false;
        }
//        System.out.println("End LDMenuGrp UI Submit...");
        //�������Ҫ����Ĵ����򷵻�
        if (tLDMenuGrpBL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tLDMenuGrpBL.mErrors);
            return false;
        }
//        System.out.println("error num=" + mErrors.getErrorCount());
        mInputData = null;
        return true;
    }

    public static void main(String[] args)
    {
    }
}
