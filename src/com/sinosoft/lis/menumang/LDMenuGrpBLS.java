/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.menumang;

import java.sql.Connection;

import com.sinosoft.lis.db.LDMenuGrpDB;
import com.sinosoft.lis.db.LDMenuGrpToMenuDB;
import com.sinosoft.lis.db.LDUserTOMenuGrpDB;
import com.sinosoft.lis.schema.LDMenuGrpSchema;
import com.sinosoft.lis.schema.LDMenuGrpToMenuSchema;
import com.sinosoft.lis.schema.LDUserTOMenuGrpSchema;
import com.sinosoft.lis.vdb.LDMenuGrpToMenuDBSet;
import com.sinosoft.lis.vschema.LDMenuGrpSet;
import com.sinosoft.lis.vschema.LDMenuGrpToMenuSet;
import com.sinosoft.lis.vschema.LDUserTOMenuGrpSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Dingzhong
 * @version 1.0
 */

public class LDMenuGrpBLS
{
    //错误处理类，每个需要错误处理的类中都放置该类
    public CErrors mErrors = new CErrors();
    /** 数据操作字符串 */
    private String mOperate;

    public LDMenuGrpBLS()
    {
    }

    //传输数据的公共方法
    public boolean submitData(VData cInputData, String cOperate)
    {
        boolean tReturn = false;
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
//        System.out.println("Start LDMenuGrp BLS Submit...");

        //信息保存
        if (this.mOperate.equals("insert"))
        {
            tReturn = save(cInputData);
        }

        //信息删除
        if (this.mOperate.equals("delete"))
        {
            tReturn = remove(cInputData);
        }

        //信息更新
        if (this.mOperate.equals("update"))
        {
            tReturn = update(cInputData);
        }

//        System.out.println("End LDMenuGrp BLS Submit...");

        return tReturn;
    }

    /**
     * 删除操作
     * @param mInputData VData
     * @return boolean
     */
    private boolean remove(VData mInputData)
    {
        boolean tReturn = true;
//        System.out.println("start remove...");
        Connection conn = DBConnPool.getConnection();
        if (conn == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDMenuGrpBLS";
            tError.functionName = "saveData";
            tError.errorMessage = "数据库连接失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            conn.setAutoCommit(false);
//            System.out.println("Start 删除菜单组...");
            LDMenuGrpDB tLDMenuGrpDB = new LDMenuGrpDB(conn);
            tLDMenuGrpDB.setSchema((LDMenuGrpSchema) mInputData.
                                   getObjectByObjectName("LDMenuGrpSchema", 0));
            if (!tLDMenuGrpDB.deleteSQL())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDMenuGrpBLS";
                tError.functionName = "removeData";
                tError.errorMessage = "菜单组删除失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

//            System.out.println("Start 菜单组菜单关联表删除....");

            LDMenuGrpToMenuDB tLDMenuGrpToMenuDB = new LDMenuGrpToMenuDB(conn);
            LDMenuGrpToMenuSchema tLDMenuGrpToMenuSchema = new
                    LDMenuGrpToMenuSchema();
            LDMenuGrpSchema tLDMenuGrpSchema = new LDMenuGrpSchema();

            tLDMenuGrpSchema = (LDMenuGrpSchema) mInputData.
                               getObjectByObjectName("LDMenuGrpSchema", 0);
            tLDMenuGrpToMenuSchema.setMenuGrpCode(tLDMenuGrpSchema.
                                                  getMenuGrpCode());
            tLDMenuGrpToMenuDB.setSchema(tLDMenuGrpToMenuSchema);

            //必须在删除操作前得到更新菜单组的removeset集合
            //为更新相关菜单组作准备工作
            LDMenuGrpSchema removeGrpSchema = (LDMenuGrpSchema) mInputData.
                                              getObjectByObjectName(
                    "LDMenuGrpSchema", 0);
            LDMenuGrpToMenuSet tRemoveSet = null;
            String grpcode = removeGrpSchema.getMenuGrpCode();
//            System.out.println("****grpcode is " + grpcode);

            String sqlStr =
                    "select * from ldmenugrptomenu where menugrpcode = '" +
                    grpcode + "'";
            LDMenuGrpToMenuDB tRemoveDB = new LDMenuGrpToMenuDB(conn);
            tRemoveSet = tRemoveDB.executeQuery(sqlStr);
//            System.out.println("***tRemoveSet size is " + tRemoveSet.size());

            if (!tLDMenuGrpToMenuDB.deleteSQL())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLDMenuGrpToMenuDB.mErrors);
                CError tError = new CError();
                tError.moduleName = "LDMenuGrpBLS";
                tError.functionName = "RemoveData";
                tError.errorMessage = "菜单组菜单关联表删除失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

            //现在开始更新相关的菜单组
//            System.out.println("现在开始递归的更新相关的菜单组...");
            sqlStr = "select * from ldusertoMenugrp where menugrpcode = '" +
                     grpcode + "'";
//            System.out.println(sqlStr);

            LDUserTOMenuGrpDB tLDUserDB = new LDUserTOMenuGrpDB(conn);
            LDUserTOMenuGrpSet tUserSet = tLDUserDB.executeQuery(sqlStr);
//            System.out.println("使用此菜单组的用户数为" + tUserSet.size());

            int i = 1;
            for (; i <= tUserSet.size(); i++)
            {
                LDUserTOMenuGrpSchema nextUser = tUserSet.get(i);
                String nextUserCode = nextUser.getUserCode();
                boolean suc = userToMenuGrp(tRemoveSet, nextUserCode, conn);
                if (!suc)
                {
                    break;
                }
            }
            if (i <= tUserSet.size())
            { // 相关菜单组更新失败
                conn.rollback();
                conn.close();
                return false;
            }

            conn.commit();
            conn.close();
//            System.out.println("commit end");
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDMenuGrpBLS";
            tError.functionName = "submitData";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            try
            {
                conn.rollback();
                conn.close();
            }
            catch (Exception e)
            {}
            tReturn = false;
        }
        return tReturn;
    }

    /**
     * 保存操作
     * @param mInputData VData
     * @return boolean
     */
    private boolean save(VData mInputData)
    {
        boolean tReturn = true;
//        System.out.println("Start Save...");

        Connection conn = DBConnPool.getConnection();
        if (conn == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDMenuGrpBLS";
            tError.functionName = "saveData";
            tError.errorMessage = "数据库连接失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            conn.setAutoCommit(false);

//            System.out.println("Start 保存菜单组...");
            LDMenuGrpDB tLDMenuGrpDB = new LDMenuGrpDB(conn);
            tLDMenuGrpDB.setSchema((LDMenuGrpSchema) mInputData.
                                   getObjectByObjectName("LDMenuGrpSchema", 0));
            if (!tLDMenuGrpDB.insert())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDMenuGrpBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "菜单组保存失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
//                System.out.println(tError.functionName);
                return false;
            }

//            System.out.println("Start 菜单节点插入....");
            LDMenuGrpToMenuDBSet tLDMenuGrpToMenuDBSet = new
                    LDMenuGrpToMenuDBSet(conn);

            tLDMenuGrpToMenuDBSet.set((LDMenuGrpToMenuSet) mInputData.
                                      getObjectByObjectName(
                                              "LDMenuGrpToMenuSet", 0));

            int len = tLDMenuGrpToMenuDBSet.size();
//            System.out.println("len:" + len);

            if (!tLDMenuGrpToMenuDBSet.insert())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLDMenuGrpToMenuDBSet.mErrors);
                CError tError = new CError();
                tError.moduleName = "LDMenuGrpBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "菜单组菜单关联表保存失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

            conn.commit();
            conn.close();
//            System.out.println("commit end");
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDMenuGrpBLS";
            tError.functionName = "submitData";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            try
            {
                conn.rollback();
                conn.close();
            }
            catch (Exception e)
            {}

            tReturn = false;
        }
        return tReturn;
    }

    /**
     * 更新操作
     * @param mInputData VData
     * @return boolean
     */
    private boolean update(VData mInputData)
    {
        boolean tReturn = true;
        Connection conn = DBConnPool.getConnection();
        if (conn == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDMenuGrpBLS";
            tError.functionName = "saveData";
            tError.errorMessage = "数据库连接失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            conn.setAutoCommit(false);

//            System.out.println("Start 更新菜单组...");
            LDMenuGrpToMenuSchema tLDMenuGrpToMenuSchema = new
                    LDMenuGrpToMenuSchema();
            LDMenuGrpDB tLDMenuGrpDB = new LDMenuGrpDB(conn);
            LDMenuGrpSchema tLDMenuGrpSchema = new LDMenuGrpSchema();
            LDMenuGrpToMenuDB tLDMenuGrpToMenuDB = new LDMenuGrpToMenuDB(conn);
            tLDMenuGrpSchema = (LDMenuGrpSchema) mInputData.
                               getObjectByObjectName("LDMenuGrpSchema", 0);
            tLDMenuGrpDB.setSchema(tLDMenuGrpSchema);
            tLDMenuGrpToMenuSchema.setMenuGrpCode(tLDMenuGrpSchema.
                                                  getMenuGrpCode());
            tLDMenuGrpToMenuDB.setSchema(tLDMenuGrpToMenuSchema);

            //更新菜单组表
            if (!tLDMenuGrpDB.update())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDMenuGrpBLS";
                tError.functionName = "update";
                tError.errorMessage = "菜单组表更新失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

            //更新首先删除关联表中的关联，然后再插入最新的数据
            if (!tLDMenuGrpToMenuDB.deleteSQL())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDMenuGrpBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "菜单组关联表更新失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

            LDMenuGrpToMenuDBSet tLDMenuGrpToMenuDBSet = new
                    LDMenuGrpToMenuDBSet(conn);
            tLDMenuGrpToMenuDBSet.set((LDMenuGrpToMenuSet) mInputData.
                                      getObjectByObjectName(
                                              "LDMenuGrpToMenuSet", 0));
//            System.out.println("开始插入...");
            if (!tLDMenuGrpToMenuDBSet.insert())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLDMenuGrpToMenuDBSet.mErrors);
                CError tError = new CError();
                tError.moduleName = "LDMenuGrpBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "菜单组菜单关联表更新失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

            //现在开始递归的更新相关的菜单组
//            System.out.println("现在开始递归的更新相关的菜单组...");
            LDMenuGrpToMenuSet tRemoveSet = (LDMenuGrpToMenuSet) mInputData.
                                            getObjectByObjectName(
                    "LDMenuGrpToMenuSet", 2);
            String menugrpcode = tLDMenuGrpSchema.getMenuGrpCode();
            String sqlStr =
                    "select * from ldusertoMenugrp where menugrpcode = '" +
                    menugrpcode + "'";

//            System.out.println(sqlStr);

            LDUserTOMenuGrpDB tLDUserDB = new LDUserTOMenuGrpDB(conn);
            LDUserTOMenuGrpSet tUserSet = tLDUserDB.executeQuery(sqlStr);
//            System.out.println("使用此菜单组的用户数为" + tUserSet.size());

            int i = 1;
            for (; i <= tUserSet.size(); i++)
            {
                LDUserTOMenuGrpSchema nextUser = tUserSet.get(i);
                String nextUserCode = nextUser.getUserCode();
                boolean suc = userToMenuGrp(tRemoveSet, nextUserCode, conn);
                if (!suc)
                {
                    break;
                }
            }
            if (i <= tUserSet.size())
            {
                // 相关菜单组更新失败
                conn.rollback();
                conn.close();
                return false;
            }
            conn.commit();
            conn.close();
//            System.out.println("commit end");
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDMenuGrpBLS";
            tError.functionName = "submitData";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            try
            {
                conn.rollback();
                conn.close();
            }
            catch (Exception e)
            {}
            tReturn = false;
        }
        return tReturn;
    }


    public boolean userToMenuGrp(LDMenuGrpToMenuSet removeMenuSet,
                                 String usercode, Connection conn)
    {
//        System.out.println("start userToMenuGrp procedure");
        if (conn == null)
        {
//            System.out.println("conn is null");
            return false;
        }
//        System.out.println("removeMenuSet's size is " + removeMenuSet.size());
        if (removeMenuSet == null || removeMenuSet.size() == 0)
        {
            return true;
        }

        //得到真正的需增加和删去的菜单节点集合
        //LDMenuSet allMenu =  所有的菜单集合
        //对于每一个addMenuSet中的元素，如果在allMenu中，则删除
        //得到了hisAddMenuSet,hisRemoveMenuSet;
        try
        {
            LDMenuGrpToMenuSet allMenuSet = null;
            LDMenuGrpToMenuDB tMenuGrpToMenuDB = new LDMenuGrpToMenuDB(conn);
            String sqlStr =
                    "select * from ldmenugrptomenu where menugrpcode in " +
                    "(select menugrpcode from ldusertomenugrp where usercode = '" +
                    usercode + "')";
//            System.out.println(sqlStr);
            allMenuSet = tMenuGrpToMenuDB.executeQuery(sqlStr);
//            System.out.println("allMenuSet's size is :" + allMenuSet.size());

            if (allMenuSet == null)
            {
                allMenuSet = new LDMenuGrpToMenuSet();
            }

            LDMenuGrpToMenuSet realRemoveSet = new LDMenuGrpToMenuSet();

            for (int i = 1; i <= removeMenuSet.size(); i++)
            {
                LDMenuGrpToMenuSchema chooseSchema = removeMenuSet.get(i);
                LDMenuGrpToMenuSchema addSchema = null;
                String userCode1 = chooseSchema.getNodeCode();
                int j = 1;
                for (; j <= allMenuSet.size(); j++)
                {
                    addSchema = allMenuSet.get(j);
                    String userCode2 = addSchema.getNodeCode();
                    if (userCode1.compareTo(userCode2) != 0)
                    {
                        continue;
                    }
                    break;
                }

                if (j > allMenuSet.size())
                {
                    realRemoveSet.add(chooseSchema);
                }
            }

            if (realRemoveSet.size() == 0)
            {
//                System.out.println("realRemoveSet is empty");
                //  conn.close();
                return true;
            }
//            System.out.println("realRemoveSet's size is " + realRemoveSet.size());

            // 得到所有编码为usercode的用户创建的菜单组编码集合
            LDMenuGrpSet tAllCreateGrpSet = new LDMenuGrpSet();
            sqlStr = "select * from LDMenugrp where Operator = '" + usercode +
                     "'";
            LDMenuGrpDB tLDMenuGrpDB = new LDMenuGrpDB(conn);
            tAllCreateGrpSet = tLDMenuGrpDB.executeQuery(sqlStr);
//            System.out.println("tAllCreateGrpSet size is " +
//                               tAllCreateGrpSet.size());
            if (tAllCreateGrpSet.size() == 0)
            {
                // conn.close();
                return true;
            }

            //对于每一个菜单组，更新它的菜单节点集合
            for (int ii = 1; ii <= tAllCreateGrpSet.size(); ii++)
            {
//                System.out.println("*********************");
                LDMenuGrpSchema tMenuGrpSchema = tAllCreateGrpSet.get(ii);
                String tMenuGrpCode = tMenuGrpSchema.getMenuGrpCode();
                sqlStr = "select * from ldmenugrptomenu where menugrpcode = '" +
                         tMenuGrpCode + "'";
                LDMenuGrpToMenuDB tMenuDB = new LDMenuGrpToMenuDB(conn);

                LDMenuGrpToMenuSet menuSet = tMenuDB.executeQuery(sqlStr);
//                System.out.println("menuSet size is " + menuSet.size());
                if (menuSet.size() == 0)
                {
                    continue;
                }
                LDMenuGrpToMenuSet nextRemoveSet = new LDMenuGrpToMenuSet();

                //如果此菜单组节点集合中有的在realRemoveMenuSet中，则删除掉，并
                //将其加入到nextRemoveMenuSet中
                for (int i = 1; i <= realRemoveSet.size(); i++)
                {
                    LDMenuGrpToMenuSchema chooseMenuSchema = realRemoveSet.get(
                            i);
                    LDMenuGrpToMenuSchema delMenuSchema = null;
                    String nodecode1 = chooseMenuSchema.getNodeCode();
                    int j = 1;
                    for (; j <= menuSet.size(); j++)
                    {
                        delMenuSchema = menuSet.get(j);
                        String nodecode2 = delMenuSchema.getNodeCode();
//                        System.out.println("nodecode 2 :" + nodecode2 + " nodecode1 : " + nodecode1);
                        if (nodecode1.compareTo(nodecode2) != 0)
                        {
                            continue;
                        }
                        break;
                    }
                    if (j <= menuSet.size())
                    {
                        nextRemoveSet.add(chooseMenuSchema);
                        menuSet.remove(delMenuSchema); //可能有问题，指针问题
//                        System.out.println("menuSet.size is :" + menuSet.size());
                    }
                }
//                System.out.println("nextRemoveSet size is" + nextRemoveSet.size());
                if (nextRemoveSet.size() == 0)
                {
                    continue;
                }
//                System.out.println("here");
                //保存menuSet,即更新此菜单组
                LDMenuGrpToMenuDB tSaveDB = new LDMenuGrpToMenuDB(conn);
                LDMenuGrpToMenuSchema tDelSchema = new LDMenuGrpToMenuSchema();
                tDelSchema.setMenuGrpCode(tMenuGrpCode);
                tSaveDB.setSchema(tDelSchema);
                if (!tSaveDB.deleteSQL())
                {
//           	 conn.close();
                    return false;
                }
                LDMenuGrpToMenuDBSet tLDMenuGrpToMenuDBSet = new
                        LDMenuGrpToMenuDBSet(conn);
                tLDMenuGrpToMenuDBSet.set(menuSet);
                if (!tLDMenuGrpToMenuDBSet.insert())
                {
//	       conn.close();
                    return false;
                }
                //对于所有引用此菜单组的用户，进行递归调用进行更新
                //首先得到所有的用户组
                sqlStr = "select * from ldusertoMenugrp where menugrpcode = '" +
                         tMenuGrpCode + "'";

//                System.out.println(sqlStr);

                LDUserTOMenuGrpDB tLDUserDB = new LDUserTOMenuGrpDB(conn);
                LDUserTOMenuGrpSet tUserSet = tLDUserDB.executeQuery(sqlStr);
//                System.out.println("使用此菜单组的用户数为" + tUserSet.size());
                if (tUserSet.size() == 0)
                {
                    continue;
                }

                for (int i = 1; i <= tUserSet.size(); i++)
                {
                    LDUserTOMenuGrpSchema nextUser = tUserSet.get(i);
                    String nextUserCode = nextUser.getUserCode();
                    userToMenuGrp(nextRemoveSet, nextUserCode, conn);
                }
            }
//     conn.close();
        }
        catch (Exception ex)
        {
            try
            {
//                System.out.println("excrption");
                conn.close();
            }
            catch (Exception e)
            {
                return false;
            }
            ;
            return false;
        }
        return true;
    }

    public static void main(String[] args)
    {
//        LDMenuGrpBLS mLDMenuGrpBLS1 = new LDMenuGrpBLS();
//        VData inputData = new VData();
//        LDMenuGrpSchema tLDMenuGrpSchema = new LDMenuGrpSchema();
//        tLDMenuGrpSchema.setMenuGrpCode("2009");
//
//        inputData.add(tLDMenuGrpSchema);
//
//        LDMenuGrpToMenuSchema MM = new LDMenuGrpToMenuSchema();
//        MM.setMenuGrpCode("2009");
//        MM.setNodeCode("1151");
//        LDMenuGrpToMenuSet set = new LDMenuGrpToMenuSet();
//        set.add(MM);
//        inputData.add(set);
//
//        inputData.add(tLDMenuGrpSchema);
//
//        String operate = "insert";
//        mLDMenuGrpBLS1.submitData(inputData, operate);
    }
}
