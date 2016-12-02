package com.sinosoft.lis.menumang;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.schema.LDMenuGrpSchema;
/**
 * <p>Title:�˵���������� </p>
 *
 * <p>Description: ʵ�ֲ˵���������忽��</p>
 *
 * <p>Copyright: Sinosoft Copyright (c) 2006</p>
 *
 * <p>Company: Sinosoft</p>
 *
 * @author ML
 * @version 1.0
 */
public class LDMenuGrpNewUI {

    //ҵ������ر���
  private VData mInputData;
  public CErrors mErrors = new CErrors();

  public LDMenuGrpNewUI() {
  }

  //�������ݵĹ�������
    public boolean submitData(VData cInputData)
    {
        //���Ƚ������ڱ�������һ������
        mInputData = (VData) cInputData.clone();
        LDMenuGrpNewBL tLDMenuGrpNewBL = new LDMenuGrpNewBL();
        if(!tLDMenuGrpNewBL.submitData(mInputData))
        {
          return false;
        }
        //�������Ҫ����Ĵ����򷵻�
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
      tGrpSchema.setMenuGrpName("����");
      tGrpSchema.setMenuGrpDescription("�˵��������");
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
        System.out.println("����ʧ�ܣ�");
      }
      else
      {
        System.out.println("���Գɹ���");
      }

   }

}
