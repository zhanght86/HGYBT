/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.logon;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description:快捷菜单类（界面输入）
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

    //传输数据的公共方法
    public boolean submitData(VData cInputData, String cOperate)
    {
        //首先将数据在本类中做一个备份
        mInputData = (VData) cInputData.clone();

        //向BL层传输数据
//        System.out.println("Start LDMenuShort UI Submit...");
        LDMenuShortBL tLDMenuShortBL = new LDMenuShortBL();
        tLDMenuShortBL.submitData(mInputData, cOperate);
//        System.out.println("End LDPerson UI Submit...");

        //如果有需要处理的错误，则返回
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
        //首先将数据在本类中做一个备份
        mInputData = (VData) cInputData.clone();

        //向BL层传输数据
//        System.out.println("Start LDMenuShort UI deleteData...");
        LDMenuShortBL tLDMenuShortBL = new LDMenuShortBL();
        tLDMenuShortBL.deleteData(mInputData, cOperate);
//        System.out.println("End LDMenuShort UI deleteData...");

        //如果有需要处理的错误，则返回
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
