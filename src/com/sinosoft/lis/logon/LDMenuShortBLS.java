/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.logon;

import java.sql.Connection;


import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 快捷菜单类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author hubo
 * @version 1.0
 */
public class LDMenuShortBLS
{
    //错误处理类，每个需要错误处理的类中都放置该类
    public CErrors mErrors = new CErrors();
    private VData mInputData;

    public LDMenuShortBLS()
    {
    }

    //传输数据的公共方法
    public boolean submitData(VData cInputData, String cOperate)
    {
        //首先将数据在本类中做一个备份
        mInputData = (VData) cInputData.clone();

//        System.out.println("Start LDMenuShort BLS Submit...");
        //执行添加纪录活动
        if (cOperate.equals("insert"))
        {
            if (!save())
            {
                return false;
            }
        }
//        System.out.println("End LDMenuShort BLS Submit...");

        mInputData = null;
        return true;
    }

    public boolean deleteData(VData cInputData, String cOperate)
    {
        //首先将数据在本类中做一个备份
        mInputData = (VData) cInputData.clone();

//        System.out.println("Start LDMenuShort BLS deleteData...");
        //获取数据，操作员是关键字
       // LDMenuShortSchema tLDMenuShortSchema = (LDMenuShortSchema) mInputData.
        //                                       getObjectByObjectName(
        //        "com.sinosoft.lis.schema.LDMenuShortSchema", 0);
//        String strOperator = tLDMenuShortSchema.getOperator();
//        String strSql = "delete from LDMenuShort where Operator = '" +
//                        strOperator + "'";
//        System.out.println(strSql);
        //执行删除操作
     //   LDMenuShortDB tLDMenuShortDB = tLDMenuShortSchema.getDB();
     //   tLDMenuShortDB.deleteSQL();
//        System.out.println("End LDMenuShort BLS deleteData...");
        return true;
    }

    private boolean save()
    {
        boolean tReturn = true;
//        System.out.println("Start Save LDMenuShort...");
        try
        {
            //建立数据库连接
            Connection conn = DBConnPool.getConnection();
            //获取数据，操作员是关键字
 //           LDMenuShortSchema tLDMenuShortSchema = (LDMenuShortSchema)
 //                   mInputData.getObjectByObjectName(
 //                           "com.sinosoft.lis.schema.LDMenuShortSchema", 0);
            //根据数据库连接建立DB对象，并使用该对象的insert()方法进行数据插入操作
 //           LDMenuShortDB tLDMenuShortDB = new LDMenuShortDB(conn);
  //          tLDMenuShortDB.setSchema(tLDMenuShortSchema);
   //         tLDMenuShortDB.insert();
            //关闭数据库连接，释放连接池，一定要写
            conn.close();
        }
        catch (Exception ex)
        {
//            System.out.println("Exception in BLS");
//            System.out.println("Exception:" + ex.toString());
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDMenuShortBLS";
            tError.functionName = "submitData";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            tReturn = false;
        }
        return tReturn;
    }
}
