/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.menumang;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
//import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:菜单查询功能类
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Dingzhong
 * @version 1.0
 */
public class LDMenuQueryUI
{
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    int mResultNum = 0;
    String mResultStr = "";

    public LDMenuQueryUI()
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

        LDMenuQueryBL tMenuQueryBL = new LDMenuQueryBL();

        if (tMenuQueryBL.submitData(cInputData, mOperate))
        {
            mInputData = tMenuQueryBL.getResult();
            mResultNum = tMenuQueryBL.getResultNum();
            mResultStr = tMenuQueryBL.getResultStr();
        }
        else
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tMenuQueryBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LDMenuQueryUI";
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

    public String getResultStr()
    {
        return mResultStr;
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
//        LDMenuQueryUI tMenuQueryUI = new LDMenuQueryUI();
//        VData tData = new VData();
//        tMenuQueryUI.submitData(tData, "query");
//        if (tMenuQueryUI.mErrors.needDealError())
//        {
//            System.out.println(tMenuQueryUI.mErrors.getFirstError());
//        }
//        else
//        {
//            tData.clear();
//            tData = tMenuQueryUI.getResult();
//            String tStr = "";
//            tStr = (String) tData.get(0);
//            System.out.println(StrTool.unicodeToGBK(tStr));
//        }
    }
}
