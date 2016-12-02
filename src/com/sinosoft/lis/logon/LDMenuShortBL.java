/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.logon;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 快捷菜单类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author hubo
 * @version 1.0
 */
public class LDMenuShortBL
{
    //错误处理类，每个需要错误处理的类中都放置该类
    public CErrors mErrors = new CErrors();
    private VData mInputData;

//    private LDMenuShortSchema mLDMenuShortSchema = new LDMenuShortSchema();

    public LDMenuShortBL()
    {
    }

    //传输数据的公共方法
    public boolean submitData(VData cInputData, String cOperate)
    {
        //首先将数据在本类中做一个备份
        mInputData = (VData) cInputData.clone();

        //向BLS层传输数据
//        System.out.println("Start LDMenuShort BL Submit...");
        LDMenuShortBLS tLDMenuShortBLS = new LDMenuShortBLS();
        tLDMenuShortBLS.submitData(mInputData, cOperate);
//        System.out.println("End LDMenuShort BL Submit...");

        //如果有需要处理的错误，则返回
        if (tLDMenuShortBLS.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tLDMenuShortBLS.mErrors);
        }
        mInputData = null;
        return true;
    }

    public boolean deleteData(VData cInputData, String cOperate)
    {
        //首先将数据在本类中做一个备份
        mInputData = (VData) cInputData.clone();

        //向BLS层传输数据
//        System.out.println("Start LDPerson BL deleteData...");
        LDMenuShortBLS tLDMenuShortBLS = new LDMenuShortBLS();
        tLDMenuShortBLS.deleteData(mInputData, cOperate);
//        System.out.println("End LDPerson BL deleteData...");

        //如果有需要处理的错误，则返回
        if (tLDMenuShortBLS.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tLDMenuShortBLS.mErrors);
        }
        mInputData = null;
        return true;
    }

}
