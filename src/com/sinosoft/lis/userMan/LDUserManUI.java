/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.userMan;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:用户功能类
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Dingzhong
 * @version 1.0
 */
public class LDUserManUI
{
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    int mResultNum = 0;

    public LDUserManUI()
    {}

    /**
     * 传输数据的公共方法
     * @param cInputData VData
     * @param cOperate String
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        System.out.println("start UI submit...");
        this.mOperate = cOperate;

        LDUserManBL tUserManBL = new LDUserManBL();

        if (tUserManBL.submitData(cInputData, mOperate))
        {
            mInputData = tUserManBL.getResult();
            mResultNum = tUserManBL.getResultNum();
        }
        else
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tUserManBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LDUserUI";
            tError.functionName = "submitData";
            tError.errorMessage = "数据查询失败!";
            System.out.println(tError.errorMessage);
            this.mErrors.addOneError(tError);
            mInputData.clear();
            return false;
        }
        System.out.println("end UI submit...");
        return true;
    }

    /**
     * 获取mInputData
     * @return VData
     */
    public VData getResult()
    {
        return mInputData;
    }

    /**
     * 获取mResultNum
     * @return int
     */
    public int getResultNum()
    {
        return mResultNum;
    }
}
