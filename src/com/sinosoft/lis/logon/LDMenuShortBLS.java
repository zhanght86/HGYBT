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
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description: ��ݲ˵���</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author hubo
 * @version 1.0
 */
public class LDMenuShortBLS
{
    //�������࣬ÿ����Ҫ����������ж����ø���
    public CErrors mErrors = new CErrors();
    private VData mInputData;

    public LDMenuShortBLS()
    {
    }

    //�������ݵĹ�������
    public boolean submitData(VData cInputData, String cOperate)
    {
        //���Ƚ������ڱ�������һ������
        mInputData = (VData) cInputData.clone();

//        System.out.println("Start LDMenuShort BLS Submit...");
        //ִ����Ӽ�¼�
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
        //���Ƚ������ڱ�������һ������
        mInputData = (VData) cInputData.clone();

//        System.out.println("Start LDMenuShort BLS deleteData...");
        //��ȡ���ݣ�����Ա�ǹؼ���
       // LDMenuShortSchema tLDMenuShortSchema = (LDMenuShortSchema) mInputData.
        //                                       getObjectByObjectName(
        //        "com.sinosoft.lis.schema.LDMenuShortSchema", 0);
//        String strOperator = tLDMenuShortSchema.getOperator();
//        String strSql = "delete from LDMenuShort where Operator = '" +
//                        strOperator + "'";
//        System.out.println(strSql);
        //ִ��ɾ������
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
            //�������ݿ�����
            Connection conn = DBConnPool.getConnection();
            //��ȡ���ݣ�����Ա�ǹؼ���
 //           LDMenuShortSchema tLDMenuShortSchema = (LDMenuShortSchema)
 //                   mInputData.getObjectByObjectName(
 //                           "com.sinosoft.lis.schema.LDMenuShortSchema", 0);
            //�������ݿ����ӽ���DB���󣬲�ʹ�øö����insert()�����������ݲ������
 //           LDMenuShortDB tLDMenuShortDB = new LDMenuShortDB(conn);
  //          tLDMenuShortDB.setSchema(tLDMenuShortSchema);
   //         tLDMenuShortDB.insert();
            //�ر����ݿ����ӣ��ͷ����ӳأ�һ��Ҫд
            conn.close();
        }
        catch (Exception ex)
        {
//            System.out.println("Exception in BLS");
//            System.out.println("Exception:" + ex.toString());
            // @@������
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
