/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.userMan;

import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.schema.LDUserSchema;
import com.sinosoft.lis.vschema.LDUserSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 用户业务逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author DingZhong
 * @version 1.0
 */
public class LDUserBL
{
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mResult = new VData();

    /** 数据操作字符串 */
//    private String mOperate;

    /** 业务处理相关变量 */
    /** 菜单组、菜单组到菜单的相关信息*/
    LDUserSchema mLDUserSchema = new LDUserSchema();
    LDUserSet mLDUserSet = new LDUserSet();

    String mResultStr = "";
    int mResultNum = 0;

    public LDUserBL()
    {
        // just for debug
    }

    /**
     * 传输数据的公共方法
     * @param cInputData VData
     * @param cOperate String
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate)
    {

        // 判断操作是不是查询
    	System.out.println("11");
        if (!cOperate.equals("query"))
        {
        	System.out.println("11");
            return false;
        }

        //将操作数据拷贝到本类中
//        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中

//        System.out.println("start get inputdata...");
        if (!getInputData(cInputData))
        {
            return false;
        }
//        System.out.println("---getInputData---");

        //进行业务处理
        if (!queryUser())
        {
            return false;
        }
//        System.out.println("---queryUser---");

        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    public int getResultNum()
    {
        return mResultNum;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     * @param cInputData VData
     * @return boolean
     */
    private boolean getInputData(VData cInputData)
    {
        // 检验查询条件
    	System.out.println("1.11");
        mLDUserSchema = (LDUserSchema) cInputData.getObjectByObjectName(
                "LDUserSchema", 0);

    	System.out.println("1.22");
        if (mLDUserSchema == null)
        {
            return false;
        }

        return true;
    }

    /**
     * 查询符合条件的信息
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     * @return boolean
     */
    @SuppressWarnings("unchecked")
	private boolean queryUser()
    {
    	System.out.println("1.33");
        LDUserDB tLDUserDB = mLDUserSchema.getDB();
        mLDUserSet = tLDUserDB.query();
    	System.out.println("1.44");
        if (tLDUserDB.mErrors.needDealError())
        {
        
            // @@错误处理
            this.mErrors.copyAllErrors(tLDUserDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LDUserBL";
            tError.functionName = "queryUser";
            tError.errorMessage = "用户查询失败!";
            this.mErrors.addOneError(tError);
        	System.out.println(mErrors.getFirstError()+"]");
            mLDUserSet.clear();
            return false;
        }

        if (mLDUserSet.size() == 0)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDUserBL";
            tError.functionName = "queryUser";
            tError.errorMessage = "未找到相关数据!";
            this.mErrors.addOneError(tError);
            System.out.println(mErrors.getFirstError()+"]2");
            mLDUserSet.clear();
            return false;
        }
        mResult.add(mLDUserSet);
        mResultNum = mLDUserSet.size();

        System.out.println(mResult);
        return true;
    }

    @SuppressWarnings("unchecked")
	public static void main(String[] args)
    {
    LDUserBL tLDUserBL1 = new LDUserBL();
    LDUserSchema tSchema = new LDUserSchema();
    tSchema.setUserCode("0011");
    tSchema.setPassword("8551EF81DAF69093");
    VData tVData = new VData();
    tVData.add(tSchema);
    if (!tLDUserBL1.submitData(tVData, "query"))
      System.out.println("ppp");
    System.out.println("kkkk");
    for (int i = 0; i < tLDUserBL1.mErrors.getErrorCount(); i++) {
      CError tError = tLDUserBL1.mErrors.getError(i);
      System.out.println(tError.errorMessage);
      System.out.println(tError.moduleName);
      System.out.println(tError.functionName);
      System.out.println("----------------");
    }
    }
}
