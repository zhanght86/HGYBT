package com.sinosoft.lis.menumang;


import java.sql.Connection;

import com.sinosoft.lis.db.LDMenuGrpDB;
import com.sinosoft.lis.db.LDMenuGrpToMenuDB;
import com.sinosoft.lis.schema.LDMenuGrpSchema;
import com.sinosoft.lis.schema.LDMenuGrpToMenuSchema;
import com.sinosoft.lis.vschema.LDMenuGrpToMenuSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.VData;
/**
 * <p>Title: 菜单分组管理类</p>
 *
 * <p>Description: 实现菜单分组的整体拷贝</p>
 *
 * <p>Copyright: Sinosoft Copyright (c) 2006</p>
 *
 * <p>Company: Sinosoft</p>
 *
 * @author ML
 * @version 1.0
 */
public class LDMenuGrpNewBL {

  //错误处理类，每个需要错误处理的类中都放置该类
  public CErrors mErrors = new CErrors();
  /** 数据操作字符串 */
  private String mOperate;
  //private String mUserCode = "";
  //private String mOperator = "";
  private String mMenuGrpCode = "";
  private String nMenuGrpCode = "";
  public LDMenuGrpNewBL() {
  }

  //传输数据的公共方法
    public boolean submitData(VData cInputData)
    {
      boolean tReturn = false;
      //信息保存
        tReturn = save(cInputData);
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
        System.out.println("Start Save...");

        Connection conn = DBConnPool.getConnection();
        if (conn == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDMenuGrpNewBL";
            tError.functionName = "saveData";
            tError.errorMessage = "数据库连接失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            conn.setAutoCommit(false);

            //对新生成的菜单分组信息进行保存
            System.out.println("Start 保存菜单组...");
            LDMenuGrpDB tLDMenuGrpDB = new LDMenuGrpDB(conn);
            LDMenuGrpSchema tLDMenuGrpSchema = new LDMenuGrpSchema();
            tLDMenuGrpSchema = (LDMenuGrpSchema) mInputData.
                                   getObjectByObjectName("LDMenuGrpSchema", 0);
            tLDMenuGrpDB.setSchema(tLDMenuGrpSchema);
            if (!tLDMenuGrpDB.insert())
            {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDMenuGrpNewBL";
                tError.functionName = "saveData";
                tError.errorMessage = "菜单组保存失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }
            //为新生成的菜单分组复制菜单节点
            System.out.println("Start 复制菜单节点...");
            //获取要复制的菜单分组编码
            mMenuGrpCode = (String) mInputData.getObjectByObjectName(
                    "String", 0);
            //获取新录入的菜单分组编码
            nMenuGrpCode = tLDMenuGrpSchema.getMenuGrpCode();
            LDMenuGrpToMenuSet tLDMenuGrpToMenuSet = new LDMenuGrpToMenuSet();
            LDMenuGrpToMenuDB tLDMenuGrpToMenuDB = new LDMenuGrpToMenuDB();
            String sqlStr = "select * from ldmenugrptomenu where MenuGrpCode = '" +
                     mMenuGrpCode + "' order by nodecode ";
            System.out.println("str:" + sqlStr);
            tLDMenuGrpToMenuSet = tLDMenuGrpToMenuDB.executeQuery(sqlStr.toString());
            //对每条记录开始进行复制
            for(int i=1;i<=tLDMenuGrpToMenuSet.size();i++)
            {
              LDMenuGrpToMenuSchema tLDMenuGrpToMenuSchema = new LDMenuGrpToMenuSchema();
              tLDMenuGrpToMenuSchema = tLDMenuGrpToMenuSet.get(i);
              tLDMenuGrpToMenuSchema.setMenuGrpCode(nMenuGrpCode);
              LDMenuGrpToMenuDB mLDMenuGrpToMenuDB = new LDMenuGrpToMenuDB(conn);
              mLDMenuGrpToMenuDB.setSchema(tLDMenuGrpToMenuSchema);
              if (!mLDMenuGrpToMenuDB.insert())
              {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LDMenuGrpNewBL";
                tError.functionName = "saveData";
                tError.errorMessage = "菜单节点复制失败!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
              }
            }
            conn.commit();
            conn.close();
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDMenuGrpNewBL";
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
    public static void main(String[] agre)
    {
      LDMenuGrpSchema tGrpSchema = new LDMenuGrpSchema();
      tGrpSchema.setMenuGrpCode("fawef");
      tGrpSchema.setMenuGrpName("测试");
      tGrpSchema.setMenuGrpDescription("菜单分组测试");
      tGrpSchema.setMenuSign("1");
      tGrpSchema.setOperator("001");
      tGrpSchema.setMakeTime("12:00:00");
      String tDate = "2006-03-03";
      tGrpSchema.setMakeDate(tDate);

      VData tData = new VData();
      tData.add(tGrpSchema);
      tData.add("hbg");


      LDMenuGrpNewBL tLDMenuGrpNewBL = new LDMenuGrpNewBL();
      if(!tLDMenuGrpNewBL.submitData(tData))
      {
        System.out.println("测试失败！");
      }
      else
      {
        System.out.println("测试成功！");
      }




    }
}
