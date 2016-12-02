/*
 * <p>ClassName: LKCodeUI </p>
 * <p>Description: LKCodeUI类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 银行网点机构
 * @CreateDate：2005-08-30
 */
package com.sinosoft.lis.ybt.bl;

import com.sinosoft.lis.pubfun.GlobalInput;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.schema.LKTransAuthorizationSchema;


public class YBTLKAuthorUI
{
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 存放查询结果 */
    private VData mResult = new VData();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    //业务处理相关变量
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private LKTransAuthorizationSchema mLKTransAuthorSchema = new LKTransAuthorizationSchema();

 
    public YBTLKAuthorUI()
    {
    }

    /**
       传输数据的公共方法
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }
        System.out.println("over getInputData");
        //进行业务处理
        if (!dealData())
        {
            return false;
        }
        System.out.println("over dealData");
        //准备往后台的数据
        //if (!prepareOutputData())
        //{/
        //    return false;
        //}
        System.out.println("over prepareOutputData");
        YBTLKAuthorBL tYBTLKAuthorBL = new YBTLKAuthorBL();
        System.out.println("Start LKCode UI Submit...");
        tYBTLKAuthorBL.submitData(cInputData, mOperate);
        System.out.println("End LKCode UI Submit...");
        //如果有需要处理的错误，则返回
        if (tYBTLKAuthorBL.mErrors.needDealError())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tYBTLKAuthorBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "YBTLKCodeUI";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        this.mResult.clear();
        this.mResult = tYBTLKAuthorBL.getResult();
        mInputData = null;
        return true;
    }

    public static void main(String[] args)
    {

    }

    /**
     * 准备往后层输出所需要的数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData()
    {
        try
        {
            mInputData.clear();
            mInputData.add(this.mGlobalInput);
            mInputData.add(this.mLKTransAuthorSchema);

        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "YBTLKCodeUI";
            tError.functionName = "prepareData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 根据前面的输入数据，进行UI逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
        boolean tReturn = false;
        String SQL = "";
        tReturn = true;
        return tReturn;
    }


    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        //全局变量
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput", 0));
       // this.mLKCodeMappingSchema.setSchema((LKCodeMappingSchema) cInputData.getObjectByObjectName("LKCodeMappingSchema", 0));

        if (mGlobalInput == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "YBTLKCodeUI";
            tError.functionName = "getInputData";
            tError.errorMessage = "没得到足够的信息！";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public VData getResult()
    {
        return this.mResult;
    }

}
