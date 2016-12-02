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
 * <p>Title: �˵����������</p>
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
public class LDMenuGrpNewBL {

  //�������࣬ÿ����Ҫ����������ж����ø���
  public CErrors mErrors = new CErrors();
  /** ���ݲ����ַ��� */
  private String mOperate;
  //private String mUserCode = "";
  //private String mOperator = "";
  private String mMenuGrpCode = "";
  private String nMenuGrpCode = "";
  public LDMenuGrpNewBL() {
  }

  //�������ݵĹ�������
    public boolean submitData(VData cInputData)
    {
      boolean tReturn = false;
      //��Ϣ����
        tReturn = save(cInputData);
      return tReturn;
    }

    /**
    * �������
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
            // @@������
            CError tError = new CError();
            tError.moduleName = "LDMenuGrpNewBL";
            tError.functionName = "saveData";
            tError.errorMessage = "���ݿ�����ʧ��!";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            conn.setAutoCommit(false);

            //�������ɵĲ˵�������Ϣ���б���
            System.out.println("Start ����˵���...");
            LDMenuGrpDB tLDMenuGrpDB = new LDMenuGrpDB(conn);
            LDMenuGrpSchema tLDMenuGrpSchema = new LDMenuGrpSchema();
            tLDMenuGrpSchema = (LDMenuGrpSchema) mInputData.
                                   getObjectByObjectName("LDMenuGrpSchema", 0);
            tLDMenuGrpDB.setSchema(tLDMenuGrpSchema);
            if (!tLDMenuGrpDB.insert())
            {
                // @@������
                CError tError = new CError();
                tError.moduleName = "LDMenuGrpNewBL";
                tError.functionName = "saveData";
                tError.errorMessage = "�˵��鱣��ʧ��!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }
            //Ϊ�����ɵĲ˵����鸴�Ʋ˵��ڵ�
            System.out.println("Start ���Ʋ˵��ڵ�...");
            //��ȡҪ���ƵĲ˵��������
            mMenuGrpCode = (String) mInputData.getObjectByObjectName(
                    "String", 0);
            //��ȡ��¼��Ĳ˵��������
            nMenuGrpCode = tLDMenuGrpSchema.getMenuGrpCode();
            LDMenuGrpToMenuSet tLDMenuGrpToMenuSet = new LDMenuGrpToMenuSet();
            LDMenuGrpToMenuDB tLDMenuGrpToMenuDB = new LDMenuGrpToMenuDB();
            String sqlStr = "select * from ldmenugrptomenu where MenuGrpCode = '" +
                     mMenuGrpCode + "' order by nodecode ";
            System.out.println("str:" + sqlStr);
            tLDMenuGrpToMenuSet = tLDMenuGrpToMenuDB.executeQuery(sqlStr.toString());
            //��ÿ����¼��ʼ���и���
            for(int i=1;i<=tLDMenuGrpToMenuSet.size();i++)
            {
              LDMenuGrpToMenuSchema tLDMenuGrpToMenuSchema = new LDMenuGrpToMenuSchema();
              tLDMenuGrpToMenuSchema = tLDMenuGrpToMenuSet.get(i);
              tLDMenuGrpToMenuSchema.setMenuGrpCode(nMenuGrpCode);
              LDMenuGrpToMenuDB mLDMenuGrpToMenuDB = new LDMenuGrpToMenuDB(conn);
              mLDMenuGrpToMenuDB.setSchema(tLDMenuGrpToMenuSchema);
              if (!mLDMenuGrpToMenuDB.insert())
              {
                // @@������
                CError tError = new CError();
                tError.moduleName = "LDMenuGrpNewBL";
                tError.functionName = "saveData";
                tError.errorMessage = "�˵��ڵ㸴��ʧ��!";
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
            // @@������
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


      LDMenuGrpNewBL tLDMenuGrpNewBL = new LDMenuGrpNewBL();
      if(!tLDMenuGrpNewBL.submitData(tData))
      {
        System.out.println("����ʧ�ܣ�");
      }
      else
      {
        System.out.println("���Գɹ���");
      }




    }
}
