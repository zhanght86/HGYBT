/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.userMan;

import java.sql.Connection;

import com.sinosoft.lis.db.LDMenuGrpDB;
import com.sinosoft.lis.db.LDMenuGrpToMenuDB;
import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.db.LDUserTOMenuGrpDB;
import com.sinosoft.lis.encrypt.LisIDEA;
import com.sinosoft.lis.menumang.MenuGrpUpdate;
import com.sinosoft.lis.schema.LDUserSchema;
import com.sinosoft.lis.vdb.LDUserTOMenuGrpDBSet;
import com.sinosoft.lis.vschema.LDMenuGrpToMenuSet;
import com.sinosoft.lis.vschema.LDUserTOMenuGrpSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Dingzhong
 * @version 1.0
 */

public class LDUserManBLS
{
    //错误处理类，每个需要错误处理的类中都放置该类
    public CErrors mErrors = new CErrors();
    /** 数据操作字符串 */
    private String mOperate;

    public LDUserManBLS()
    {
    }

    //传输数据的公共方法
    public boolean submitData(VData cInputData, String cOperate)
    {
        boolean tReturn = false;
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
//        System.out.println("Start LDMenuGrp BLS Submit...");
        System.out.println("LDUserManBLS 1111111...");
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

//        System.out.println("End LDUserMan BLS Submit...");
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
            tError.moduleName = "LDUserManBLS";
            tError.functionName = "saveData";
            tError.errorMessage = "数据库连接失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            conn.setAutoCommit(false);
//            System.out.println("Start 删除用户...");
            
            LDUserDB tLDUserDB = new LDUserDB(conn);
            LDUserSchema tUserSchema = (LDUserSchema) mInputData.
                                       getObjectByObjectName("LDUserSchema", 0);
            tLDUserDB.setSchema((LDUserSchema) mInputData.getObjectByObjectName(
                    "LDUserSchema", 0));
            
//            LDUserTOMenuGrpDBSet tLDUsertomenuSet = new LDUserTOMenuGrpDBSet(conn);
//           LDUserTOMenuGrpSet tLDUsertomenuSchema = (LDUserTOMenuGrpSet) mInputData.
//                                       getObjectByObjectName("LDUserSchema", 0);
           
//            tLDUserDB.setSchema((LDUserSchema) mInputData.getObjectByObjectName(
           // LDUserTOMenuGrpDBSet ldmenu=new LDUserTOMenuGrpDBSet();
//                    "LDUserSchema", 0));
            if (!tLDUserDB.deleteSQL())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDUserBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "用户删除失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

//            System.out.println("start 转移用户关联菜单组及用户创建菜单组....");
//            System.out.println("start 转移用户关联菜单组及用户创建菜单组....");
            String delUserCode = tUserSchema.getUserCode();
            String deletor = (String) mInputData.getObjectByObjectName("String",
                    0);
//            System.out.println("deletor : " + deletor);
            if (deletor == null)
            {
                conn.rollback();
                conn.close();
                return false;
            }

           String  msqlStr = "delete ldusertomenugrp "  +
            " where usercode = '" + delUserCode + "'";
            System.out.println("被删除的用户"+delUserCode);
            if(!(new ExeSQL(conn).execUpdateSQL(msqlStr))){
//            	 @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDUserBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "用户删除失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }
            
            
            //将被删用户创建的用户的operator改为当前操作员，相当于用户由操作员deletor接管
            String sqlStr = "update lduser set operator = '" + deletor +
                            "' where operator = '" + delUserCode + "'";
//            System.out.println(sqlStr);
            tLDUserDB.executeQuery(sqlStr);

            //将被删用户创建的菜单组由当前操作员deletor接管
            sqlStr = "update ldmenugrp set operator = '" + deletor +
                     "' where operator = '" + delUserCode + "'";
//            System.out.println(sqlStr);
            LDMenuGrpDB tMenuGrpDB = new LDMenuGrpDB(conn);
            tMenuGrpDB.executeQuery(sqlStr);

            sqlStr = "update ldusertomenugrp set usercode = '" + deletor +
                     "' where usercode = '" + delUserCode + "'";
//            System.out.println(sqlStr);
            LDMenuGrpToMenuDB tMenuGrpToMenuDB = new LDMenuGrpToMenuDB(conn);
            tMenuGrpToMenuDB.executeQuery(sqlStr);

          
//   System.out.println(sqlStr);
       
         
            
            conn.commit();
            conn.close();
//            System.out.println("commit end");
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDUserBLS";
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
            tError.moduleName = "LDUserBLS";
            tError.functionName = "saveData";
            tError.errorMessage = "数据库连接失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            conn.setAutoCommit(false);

//            System.out.println("Start 保存用户...");
            LDUserDB tLDUserDB = new LDUserDB(conn);
            LDUserSchema tLDUserSchema = (LDUserSchema) mInputData.
                                         getObjectByObjectName("LDUserSchema",
                    0);
            if (tLDUserSchema == null)
            {
                return false;
            }

            // 开始进行密码加密
            String plainPwd = tLDUserSchema.getPassword();
            LisIDEA tIdea = new LisIDEA();
            String encryptPwd = tIdea.encryptString(plainPwd);
            tLDUserSchema.setPassword(encryptPwd);
            tLDUserDB.setSchema(tLDUserSchema);

            if (!tLDUserDB.insert())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDUserBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "用户保存失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

//            System.out.println("Start 用户菜单组插入....");
            LDUserTOMenuGrpDBSet tLDUserToMenuGrpDBSet = new
                    LDUserTOMenuGrpDBSet(conn);

            tLDUserToMenuGrpDBSet.set((LDUserTOMenuGrpSet) mInputData.
                                      getObjectByObjectName(
                                              "LDUserTOMenuGrpSet", 0));

            if (!tLDUserToMenuGrpDBSet.insert())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLDUserToMenuGrpDBSet.mErrors);
                CError tError = new CError();
                tError.moduleName = "LDUserBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "用户菜单组关联表保存失败!";
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
            tError.moduleName = "LDUserBLS";
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
            tError.moduleName = "LDUserBLS";
            tError.functionName = "saveData";
            tError.errorMessage = "数据库连接失败!";
            this.mErrors.addOneError(tError);
            return false;

        }
        try
        {
            conn.setAutoCommit(false);

//            System.out.println("Start 更新用户...");

            LDUserDB tLDUserDB = new LDUserDB(conn);
            LDUserSchema tLDUserSchema = new LDUserSchema();
           
            tLDUserSchema = (LDUserSchema) mInputData.getObjectByObjectName(
                    "LDUserSchema", 0);
            System.out.println("tLDUserSchema.getComCode():" + tLDUserSchema.getComCode());
            String usercode = tLDUserSchema.getUserCode();
            String operator = (String) mInputData.getObjectByObjectName(
                    "String", 0);

//            System.out.println("go go go");
//            System.out.println("password is :" + tLDUserSchema.getPassword());

            //更新菜单组表,如果当前操作员和用户创建者一致
            String curOperator = (String) mInputData.getObjectByObjectName(
                    "String", 0);
            String crtOperator = tLDUserSchema.getOperator();
            boolean updateUser = true;
            System.out.println(curOperator.compareTo(crtOperator));
          //  if (curOperator.compareTo(crtOperator) == 0)
           // {
//                System.out.println("we start tLDusertDB.update!");
                // 开始进行密码加密
                String plainPwd = tLDUserSchema.getPassword();
                LisIDEA tIdea = new LisIDEA();
                String encryptPwd = tIdea.encryptString(plainPwd);
                tLDUserSchema.setPassword(encryptPwd);
                tLDUserDB.setSchema(tLDUserSchema);
                updateUser = tLDUserDB.update();

            //}
//            else
//            {
////                System.out.println("we dont start tLDusertDB.update!");
//            }
            if (!updateUser)
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDUserBLS";
                tError.functionName = "update";
                tError.errorMessage = "用户表更新失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }
//001理解成代表具有admin用户的操作组
            //在更新前，必须先得到实际此用户失去的菜单节点集合，以便进行菜单组的更新
//        System.out.println("在更新前，必须先得到实际此用户失去的菜单节点集合，以便进行菜单组的更新");
            String sqlStr =
                    " select * from ldmenugrptomenu where menugrpcode in " +
                    " (select menugrpcode  from ldusertomenugrp where usercode = '" +
                    usercode + "' and " +
                    " menugrpcode in (select menugrpcode from ldmenugrp where operator = '" +
                    "001" + "'))";
//            System.out.println(sqlStr);
            LDMenuGrpToMenuDB tRemoveSetDB = new LDMenuGrpToMenuDB(conn);

            //tRemoveSet是原来所有菜单集合的总集，并不是真正的删除集合，但没关系,
            //不过必须在插入新菜单组以后
            LDMenuGrpToMenuSet tRemoveSet = tRemoveSetDB.executeQuery(sqlStr);

            //更新首先删除关联表中的相关关联，然后再插入最新的数据
            sqlStr = "delete from ldusertomenugrp where usercode = '" +
                     usercode + "' and " +
                     " menugrpcode in (select menugrpcode from ldmenugrp where operator = '" 
                     +"001" + "')";

//            System.out.println("****" + sqlStr + "***");

            ExeSQL exeSQL = new ExeSQL(conn);
            exeSQL.execUpdateSQL(sqlStr);

            LDUserTOMenuGrpDBSet tLDUserToMenuGrpDBSet = new
                    LDUserTOMenuGrpDBSet(
                            conn);
            tLDUserToMenuGrpDBSet.set((LDUserTOMenuGrpSet) mInputData.
                                      getObjectByObjectName(
                                              "LDUserTOMenuGrpSet", 0));
//            System.out.println("开始插入...");
//            System.out.println("BLS DBSet size:" + tLDUserToMenuGrpDBSet.size());

            if (!tLDUserToMenuGrpDBSet.insert())
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tLDUserToMenuGrpDBSet.mErrors);
                CError tError = new CError();
                tError.moduleName = "LDUserBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "用户菜单组关联表更新失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

            //开始更新此用户创建的菜单组
            MenuGrpUpdate tMenuGrpUpdate = new MenuGrpUpdate();
            boolean suc = tMenuGrpUpdate.userToMenuGrp(tRemoveSet, usercode,
                    conn);
            if (!suc)
            {
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
            tError.moduleName = "LDUserBLS";
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
}
