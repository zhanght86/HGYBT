package com.sinosoft.lis.menumang;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.schema.LDMenuGrpSchema;
/**
 * <p>Title:菜单分组管理类 </p>
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
public class LDMenuGrpNewUI {

    //业务处理相关变量
  private VData mInputData;
  public CErrors mErrors = new CErrors();

  public LDMenuGrpNewUI() {
  }

  //传输数据的公共方法
    public boolean submitData(VData cInputData)
    {
        //首先将数据在本类中做一个备份
        mInputData = (VData) cInputData.clone();
        LDMenuGrpNewBL tLDMenuGrpNewBL = new LDMenuGrpNewBL();
        if(!tLDMenuGrpNewBL.submitData(mInputData))
        {
          return false;
        }
        //如果有需要处理的错误，则返回
        if (tLDMenuGrpNewBL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tLDMenuGrpNewBL.mErrors);
            return false;
        }
        mInputData = null;
        return true;
    }

    public static void main(String[] args)
   {
      LDMenuGrpSchema tGrpSchema = new LDMenuGrpSchema();
      tGrpSchema.setMenuGrpCode("asd");
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

      LDMenuGrpNewUI tLDMenuGrpNewUI = new LDMenuGrpNewUI();
      if(!tLDMenuGrpNewUI.submitData(tData))
      {
        System.out.println("测试失败！");
      }
      else
      {
        System.out.println("测试成功！");
      }

   }

}
