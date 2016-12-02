/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.logon;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;


/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description: ��ݲ˵���</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author hubo
 * @version 1.0
 */
public class LDMenuShortBL
{
    //�������࣬ÿ����Ҫ����������ж����ø���
    public CErrors mErrors = new CErrors();
    private VData mInputData;

//    private LDMenuShortSchema mLDMenuShortSchema = new LDMenuShortSchema();

    public LDMenuShortBL()
    {
    }

    //�������ݵĹ�������
    public boolean submitData(VData cInputData, String cOperate)
    {
        //���Ƚ������ڱ�������һ������
        mInputData = (VData) cInputData.clone();

        //��BLS�㴫������
//        System.out.println("Start LDMenuShort BL Submit...");
        LDMenuShortBLS tLDMenuShortBLS = new LDMenuShortBLS();
        tLDMenuShortBLS.submitData(mInputData, cOperate);
//        System.out.println("End LDMenuShort BL Submit...");

        //�������Ҫ����Ĵ����򷵻�
        if (tLDMenuShortBLS.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tLDMenuShortBLS.mErrors);
        }
        mInputData = null;
        return true;
    }

    public boolean deleteData(VData cInputData, String cOperate)
    {
        //���Ƚ������ڱ�������һ������
        mInputData = (VData) cInputData.clone();

        //��BLS�㴫������
//        System.out.println("Start LDPerson BL deleteData...");
        LDMenuShortBLS tLDMenuShortBLS = new LDMenuShortBLS();
        tLDMenuShortBLS.deleteData(mInputData, cOperate);
//        System.out.println("End LDPerson BL deleteData...");

        //�������Ҫ����Ĵ����򷵻�
        if (tLDMenuShortBLS.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tLDMenuShortBLS.mErrors);
        }
        mInputData = null;
        return true;
    }

}
