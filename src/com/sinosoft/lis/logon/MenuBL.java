/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.logon;

import com.sinosoft.lis.db.LDMenuDB;
import com.sinosoft.lis.schema.LDMenuSchema;
import com.sinosoft.lis.vschema.LDMenuSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 菜单查询业务逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author YT
 * @version 1.0
 */
public class MenuBL
{
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mResult = new VData();

    /** 数据操作字符串 */
//    private String mOperate;


    /** 业务处理相关变量 */
    /** 菜单 */
    private LDMenuSchema mLDMenuSchema = new LDMenuSchema();
    private LDMenuSet mLDMenuSet = new LDMenuSet();


    /** 返回的数据*/
//    private String mResultStr = "";
    private StringBuffer mResultStr = new StringBuffer(256);

    public MenuBL()
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
//        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }
//        System.out.println("---getInputData---");

        //进行业务处理
        if (cOperate.equals("QUERY||MAIN"))
        {
            if (!queryData())
            {
                return false;
            }
//            System.out.println("---queryData---");
        }
        if (cOperate.equals("QUERY||DETAIL"))
        {
            if (!queryDetail())
            {
                return false;
            }
//            System.out.println("---queryDetail---");
        }

        return true;
    }

    public VData getResult()
    {
        return mResult;
    }


    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     * @param cInputData VData
     * @return boolean
     */
    private static boolean getInputData(VData cInputData)
    {
        // 保单查询条件
        return true;
    }


    /**
     * 查询符合条件的保单
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     * @return boolean
     */
    private boolean queryData()
    {
        // 菜单表
        mLDMenuSchema = new LDMenuSchema();
        LDMenuDB tLDMenuDB = mLDMenuSchema.getDB();
        mLDMenuSet = tLDMenuDB.executeQuery("select * from ldmenu order by nodeorder");
        if (tLDMenuDB.mErrors.needDealError())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLDMenuDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "MenuBL";
            tError.functionName = "queryData";
            tError.errorMessage = "保单查询失败!";
            this.mErrors.addOneError(tError);
            mLDMenuSet.clear();
            return false;
        }

        int i, iMax;
        iMax = mLDMenuSet.size();
        if (iMax == 0)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "MenuBL";
            tError.functionName = "queryData";
            tError.errorMessage = "未找到相关数据!";
            this.mErrors.addOneError(tError);
            mLDMenuSet.clear();
            return false;
        }
//        mResultStr = "";
        mResultStr = new StringBuffer(256);

        for (i = 1; i <= iMax; i++)
        {
            mLDMenuSchema = mLDMenuSet.get(i);
            mResultStr.append(mLDMenuSchema.getParentNodeCode());
            mResultStr.append("|");
            mResultStr.append(mLDMenuSchema.getNodeCode());
            mResultStr.append("|");
            mResultStr.append(mLDMenuSchema.getNodeName());
            mResultStr.append("|");
            mResultStr.append(mLDMenuSchema.getChildFlag());
            mResultStr.append("|");
            mResultStr.append(mLDMenuSchema.getRunScript());
            mResultStr.append("|");

//            mResultStr += mLDMenuSchema.getParentNodeCode() + "|";
//            mResultStr += mLDMenuSchema.getNodeCode() + "|";
//            mResultStr += mLDMenuSchema.getNodeName() + "|";
//            mResultStr += mLDMenuSchema.getChildFlag() + "|";
//            mResultStr += mLDMenuSchema.getRunScript() + "|";
        }
        mResult.clear();
        mResult.add(mResultStr.toString());

        return true;
    }

    /**
     * 查询保单明细信息
     * 输出：如果发生错误则返回false,否则返回true
     * @return boolean
     */
    private static boolean queryDetail()
    {
        return true;
    }
}
