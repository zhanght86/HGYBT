/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.menumang;

import com.sinosoft.lis.db.LDMenuGrpDB;
import com.sinosoft.lis.schema.LDMenuGrpSchema;
import com.sinosoft.lis.schema.LDUserSchema;
import com.sinosoft.lis.vschema.LDMenuGrpSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 菜单组查询业务逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author DingZhong
 * @version 1.0
 */
public class LDMenuGrpQueryBL
{
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    /** 业务处理相关变量 */
    /** 菜单组、用户到菜单组的相关信息*/
    LDMenuGrpSchema mLDMenuGrpSchema = new LDMenuGrpSchema();
    LDMenuGrpSet mLDMenuGrpSet = new LDMenuGrpSet();
    LDUserSchema mLDUserSchema = new LDUserSchema();


    String mResultStr = "";
    int mResultNum = 0;

    public LDMenuGrpQueryBL()
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
        if (!cOperate.equals("query") && !cOperate.equals("query_remain"))
        {
            System.out.println("Operater is denied");
            return false;
        }
        System.out.println("start submit...");
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }
        System.out.println("---getInputData---");

        //进行业务处理
        if (cOperate.equals("query") && !queryMenuGrp())
        {
            System.out.println("query fail");
            return false;
        }

        else if (cOperate.equals("query_remain") && !queryRemainMenuGrp())
        {
            return false;
        }

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

    public String getResultStr()
    {
        String resultStr = "";
        for (int i = 1; i <= mResultNum; i++)
        {
            mLDMenuGrpSchema = mLDMenuGrpSet.get(i);
        }
        return resultStr;
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
        mLDUserSchema = (LDUserSchema) cInputData.getObjectByObjectName(
                "LDUserSchema", 0);

        if (mLDUserSchema != null)
        {
            return true;
        }

        mLDMenuGrpSchema = (LDMenuGrpSchema) cInputData.getObjectByObjectName(
                "LDMenuGrpSchema", 0);

        if (mLDMenuGrpSchema != null)
        {
            return true;
        }

        System.out.println("completed get input data");
        return true;
    }

    /**
     * 查询符合条件的菜单组的信息
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     * @return boolean
     */
    private boolean queryMenuGrp()
    {
        LDMenuGrpSchema tLDMenuGrpSchema = new LDMenuGrpSchema();
        LDMenuGrpDB tLDMenuGrpDB = tLDMenuGrpSchema.getDB();
        System.out.println("aaa");
        if (mLDUserSchema != null)
        {
            //通过用户编码查询菜单组集合
            System.out.println("ldMenuGrpQueryBL.java:通过用户编码查询菜单组集合query by LDUserSchema");//2005
            // 构造查询语句
//            String sqlStr = "select * from LDMenuGrp where MenuGrpCode in ( select MenuGrpCode from LDUserToMenuGrp where UserCode = " +
//                            mLDUserSchema.getUserCode() +
//                            ") ";
            StringBuffer tSql = new StringBuffer();
            tSql.append("select * from LDMenuGrp where MenuGrpCode in ( select MenuGrpCode from LDUserToMenuGrp where UserCode = ");
            tSql.append(mLDUserSchema.getUserCode());
            tSql.append(") ");

            mLDMenuGrpSet = tLDMenuGrpDB.executeQuery(tSql.toString());
            System.out.println("StringBuffer : " + tSql.toString());
        }
        else if (mLDMenuGrpSchema != null)
        {
            //通过菜单组编码查询菜单集合
            System.out.println("ldMenuGrpQueryBL.java:通过用户编码查询菜单组集合query by mLDMenuGrpSchema");//2005
            String sqlStr = "select * from LDMenuGrp where MenuGrpCode = " +
                            mLDMenuGrpSchema.getMenuGrpCode() + "";
//            LDMenuGrpSet tLDMenuGrpSet = tLDMenuGrpDB.executeQuery(sqlStr);
            System.out.println(sqlStr);
        }
        else
        {
            //默认返回全部节点
            System.out.print("ldMenuGrpQueryBL.java:默认返回全部节点");//2005
            mLDMenuGrpSet = tLDMenuGrpDB.executeQuery(
                    "select * from ldmenugrp ");
        }

        if (tLDMenuGrpDB.mErrors.needDealError())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLDMenuGrpDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LDMenuGrpQueryBL";
            tError.functionName = "queryGrpMenu";
            tError.errorMessage = "用户菜单组查询失败!";
            this.mErrors.addOneError(tError);
            mLDMenuGrpSet.clear();
            return false;
        }

        if (mLDMenuGrpSet.size() == 0)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDMenuBL";
            tError.functionName = "queryMenu";
            tError.errorMessage = "未找到相关数据!";
            this.mErrors.addOneError(tError);
            mLDMenuGrpSet.clear();
            return false;
        }
        mResultNum = mLDMenuGrpSet.size();
        mResult.add(mLDMenuGrpSet);
        System.out.println(mResult);
        return true;
    }


    /**
     * 查询符合条件的菜单的信息
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     * @return boolean
     */
    private boolean queryRemainMenuGrp()
    {
        LDMenuGrpSchema tLDMenuGrpSchema = new LDMenuGrpSchema();
        LDMenuGrpDB tLDMenuGrpDB = tLDMenuGrpSchema.getDB();

        if (mLDUserSchema != null)
        {
            //通过用户编码查询菜单组集合
            System.out.println("query by LDUserSchema");
            // 构造查询语句
//            String sqlStr = "select * from LDMenuGrp where MenuGrpCode not in ( select MenuGrpCode from LDUserToMenuGrp where MenuGrpCode = " +
//                     mLDUserSchema.getUserCode() + ") ";
            StringBuffer tSql = new StringBuffer();
            tSql.append("select * from LDMenuGrp where MenuGrpCode not in ( select MenuGrpCode from LDUserToMenuGrp where MenuGrpCode = ");
            tSql.append(mLDUserSchema.getUserCode());
            tSql.append(") ");

            mLDMenuGrpSet = tLDMenuGrpDB.executeQuery(tSql.toString());
            System.out.println("StringBuffer : " + tSql.toString());
        }
        else if (mLDMenuGrpSchema != null)
        {
            //通过菜单组编码查询菜单集合
            String sqlStr = "select * from LDMenuGrp where MenuGrpCode <>" +
                            mLDMenuGrpSchema.getMenuGrpCode() + "";
            LDMenuGrpSet tLDMenuGrpSet = tLDMenuGrpDB.executeQuery(sqlStr);
            System.out.println(tLDMenuGrpSet.size());

        }
        else
        {
            //默认返回全部节点
            mLDMenuGrpSet = tLDMenuGrpDB.executeQuery(
                    "select * from ldmenugrp ");
        }

        if (tLDMenuGrpDB.mErrors.needDealError())
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLDMenuGrpDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LDMenuGrpQueryBL";
            tError.functionName = "queryGrpMenu";
            tError.errorMessage = "用户菜单组查询失败!";
            this.mErrors.addOneError(tError);
            mLDMenuGrpSet.clear();
            return false;
        }

        if (mLDMenuGrpSet.size() == 0)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDMenuBL";
            tError.functionName = "queryMenu";
            tError.errorMessage = "未找到相关数据!";
            this.mErrors.addOneError(tError);
            mLDMenuGrpSet.clear();
            return false;
        }
        mResultNum = mLDMenuGrpSet.size();
        mResult.add(mLDMenuGrpSet);
        System.out.println(mResult);
        return true;
    }

    public static void main(String[] args)
    {
//        LDMenuGrpQueryBL tLDMenuGrpQueryBL1 = new LDMenuGrpQueryBL();
//
//        LDMenuGrpSchema tSchema = new LDMenuGrpSchema();
//        tSchema.setMenuGrpCode("007");
//
//        LDUserSchema tUserSchema = new LDUserSchema();
//        tUserSchema.setUserCode("101");
//
//        VData tVData = new VData();
//        tVData.add(tSchema);
//
//        tLDMenuGrpQueryBL1.submitData(tVData, "query");
//        System.out.println(tLDMenuGrpQueryBL1.getResultNum());
//        for (int i = 0; i < tLDMenuGrpQueryBL1.mErrors.getErrorCount(); i++)
//        {
//            CError tError = tLDMenuGrpQueryBL1.mErrors.getError(i);
//            System.out.println(tError.errorMessage);
//            System.out.println(tError.moduleName);
//            System.out.println(tError.functionName);
//            System.out.println("----------------");
//        }
    }
}
