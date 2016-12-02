/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.menumang;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:
 * 从错误对象处理类继承，用来保存错误对象,在每个类中都存在
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author DingZhong
 * @version 1.0
 */

public class LDMenuGrpUI
{

    //业务处理相关变量
    private VData mInputData;
    public CErrors mErrors = new CErrors();

    public LDMenuGrpUI()
    {
    }

    //传输数据的公共方法
    public boolean submitData(VData cInputData, String cOperate)
    {
        //首先将数据在本类中做一个备份
        mInputData = (VData) cInputData.clone();
        LDMenuGrpBL tLDMenuGrpBL = new LDMenuGrpBL();
//        System.out.println("Start LDMenuGrp UI Submit...");
        boolean tag = tLDMenuGrpBL.submitData(mInputData, cOperate);
        if (!tag)
        {
            return false;
        }
//        System.out.println("End LDMenuGrp UI Submit...");
        //如果有需要处理的错误，则返回
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
