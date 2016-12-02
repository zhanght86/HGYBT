/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.menumang;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.schema.LDUserSchema;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:菜单查询功能类
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Dingzhong
 * @version 1.0
 */
public class LDUserAgentUI
{
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();

    /** 数据操作字符串 */
    private String mOperate;

    int mResultNum = 0;

    public LDUserAgentUI()
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
        this.mOperate = cOperate;

        LDUserAgentBL tUserBL = new LDUserAgentBL();

        if (tUserBL.submitData(cInputData, mOperate))
        {
            mInputData = tUserBL.getResult();
            mResultNum = tUserBL.getResultNum();
        }
        else
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tUserBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LDUserAgentUI";
            tError.functionName = "submitData";
            tError.errorMessage = "数据查询失败!";
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

    public int getResultNum()
    {
        return mResultNum;
    }

    /**
     * 测试函数
     * @param args String[]
     */
    public static void main(String[] args)
    {
        LDUserAgentBL tLDUserAgentBL1 = new LDUserAgentBL();
        LDUserSchema tSchema = new LDUserSchema();
        tSchema.setUserCode("001");
        tSchema.setPassword("26B029A2DCE2820C");
//        tSchema.setComCode("86");
        VData tVData = new VData();
        tVData.add(tSchema);
        if (!tLDUserAgentBL1.submitData(tVData, "query"))
        {
            System.out.println("ppp");
        }
        System.out.println("kkkk");
        for (int i = 0; i < tLDUserAgentBL1.mErrors.getErrorCount(); i++)
        {
            CError tError = tLDUserAgentBL1.mErrors.getError(i);
            System.out.println(tError.errorMessage);
            System.out.println(tError.moduleName);
            System.out.println(tError.functionName);
            System.out.println("----------------");
        }
    }
}
