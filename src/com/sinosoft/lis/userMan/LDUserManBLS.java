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
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Dingzhong
 * @version 1.0
 */

public class LDUserManBLS
{
    //�������࣬ÿ����Ҫ����������ж����ø���
    public CErrors mErrors = new CErrors();
    /** ���ݲ����ַ��� */
    private String mOperate;

    public LDUserManBLS()
    {
    }

    //�������ݵĹ�������
    public boolean submitData(VData cInputData, String cOperate)
    {
        boolean tReturn = false;
        //���������ݿ�����������
        this.mOperate = cOperate;
//        System.out.println("Start LDMenuGrp BLS Submit...");
        System.out.println("LDUserManBLS 1111111...");
        //��Ϣ����
        if (this.mOperate.equals("insert"))
        {
            tReturn = save(cInputData);
        }

        //��Ϣɾ��
        if (this.mOperate.equals("delete"))
        {
            tReturn = remove(cInputData);
        }

        //��Ϣ����
        if (this.mOperate.equals("update"))
        {
            tReturn = update(cInputData);
        }

//        System.out.println("End LDUserMan BLS Submit...");
        return tReturn;
    }

    /**
     * ɾ������
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
            // @@������
            CError tError = new CError();
            tError.moduleName = "LDUserManBLS";
            tError.functionName = "saveData";
            tError.errorMessage = "���ݿ�����ʧ��!";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            conn.setAutoCommit(false);
//            System.out.println("Start ɾ���û�...");
            
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
                // @@������
                CError tError = new CError();
                tError.moduleName = "LDUserBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "�û�ɾ��ʧ��!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

//            System.out.println("start ת���û������˵��鼰�û������˵���....");
//            System.out.println("start ת���û������˵��鼰�û������˵���....");
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
            System.out.println("��ɾ�����û�"+delUserCode);
            if(!(new ExeSQL(conn).execUpdateSQL(msqlStr))){
//            	 @@������
                CError tError = new CError();
                tError.moduleName = "LDUserBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "�û�ɾ��ʧ��!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }
            
            
            //����ɾ�û��������û���operator��Ϊ��ǰ����Ա���൱���û��ɲ���Աdeletor�ӹ�
            String sqlStr = "update lduser set operator = '" + deletor +
                            "' where operator = '" + delUserCode + "'";
//            System.out.println(sqlStr);
            tLDUserDB.executeQuery(sqlStr);

            //����ɾ�û������Ĳ˵����ɵ�ǰ����Աdeletor�ӹ�
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
            // @@������
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
     * �������
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
            // @@������
            CError tError = new CError();
            tError.moduleName = "LDUserBLS";
            tError.functionName = "saveData";
            tError.errorMessage = "���ݿ�����ʧ��!";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            conn.setAutoCommit(false);

//            System.out.println("Start �����û�...");
            LDUserDB tLDUserDB = new LDUserDB(conn);
            LDUserSchema tLDUserSchema = (LDUserSchema) mInputData.
                                         getObjectByObjectName("LDUserSchema",
                    0);
            if (tLDUserSchema == null)
            {
                return false;
            }

            // ��ʼ�����������
            String plainPwd = tLDUserSchema.getPassword();
            LisIDEA tIdea = new LisIDEA();
            String encryptPwd = tIdea.encryptString(plainPwd);
            tLDUserSchema.setPassword(encryptPwd);
            tLDUserDB.setSchema(tLDUserSchema);

            if (!tLDUserDB.insert())
            {
                // @@������
                CError tError = new CError();
                tError.moduleName = "LDUserBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "�û�����ʧ��!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

//            System.out.println("Start �û��˵������....");
            LDUserTOMenuGrpDBSet tLDUserToMenuGrpDBSet = new
                    LDUserTOMenuGrpDBSet(conn);

            tLDUserToMenuGrpDBSet.set((LDUserTOMenuGrpSet) mInputData.
                                      getObjectByObjectName(
                                              "LDUserTOMenuGrpSet", 0));

            if (!tLDUserToMenuGrpDBSet.insert())
            {
                // @@������
                this.mErrors.copyAllErrors(tLDUserToMenuGrpDBSet.mErrors);
                CError tError = new CError();
                tError.moduleName = "LDUserBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "�û��˵����������ʧ��!";
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
            // @@������
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
     * ���²���
     * @param mInputData VData
     * @return boolean
     */
    private boolean update(VData mInputData)
    {
        boolean tReturn = true;
        Connection conn = DBConnPool.getConnection();
        if (conn == null)
        {
            // @@������
            CError tError = new CError();
            tError.moduleName = "LDUserBLS";
            tError.functionName = "saveData";
            tError.errorMessage = "���ݿ�����ʧ��!";
            this.mErrors.addOneError(tError);
            return false;

        }
        try
        {
            conn.setAutoCommit(false);

//            System.out.println("Start �����û�...");

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

            //���²˵����,�����ǰ����Ա���û�������һ��
            String curOperator = (String) mInputData.getObjectByObjectName(
                    "String", 0);
            String crtOperator = tLDUserSchema.getOperator();
            boolean updateUser = true;
            System.out.println(curOperator.compareTo(crtOperator));
          //  if (curOperator.compareTo(crtOperator) == 0)
           // {
//                System.out.println("we start tLDusertDB.update!");
                // ��ʼ�����������
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
                // @@������
                CError tError = new CError();
                tError.moduleName = "LDUserBLS";
                tError.functionName = "update";
                tError.errorMessage = "�û������ʧ��!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }
//001���ɴ������admin�û��Ĳ�����
            //�ڸ���ǰ�������ȵõ�ʵ�ʴ��û�ʧȥ�Ĳ˵��ڵ㼯�ϣ��Ա���в˵���ĸ���
//        System.out.println("�ڸ���ǰ�������ȵõ�ʵ�ʴ��û�ʧȥ�Ĳ˵��ڵ㼯�ϣ��Ա���в˵���ĸ���");
            String sqlStr =
                    " select * from ldmenugrptomenu where menugrpcode in " +
                    " (select menugrpcode  from ldusertomenugrp where usercode = '" +
                    usercode + "' and " +
                    " menugrpcode in (select menugrpcode from ldmenugrp where operator = '" +
                    "001" + "'))";
//            System.out.println(sqlStr);
            LDMenuGrpToMenuDB tRemoveSetDB = new LDMenuGrpToMenuDB(conn);

            //tRemoveSet��ԭ�����в˵����ϵ��ܼ���������������ɾ�����ϣ���û��ϵ,
            //���������ڲ����²˵����Ժ�
            LDMenuGrpToMenuSet tRemoveSet = tRemoveSetDB.executeQuery(sqlStr);

            //��������ɾ���������е���ع�����Ȼ���ٲ������µ�����
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
//            System.out.println("��ʼ����...");
//            System.out.println("BLS DBSet size:" + tLDUserToMenuGrpDBSet.size());

            if (!tLDUserToMenuGrpDBSet.insert())
            {
                // @@������
                this.mErrors.copyAllErrors(tLDUserToMenuGrpDBSet.mErrors);
                CError tError = new CError();
                tError.moduleName = "LDUserBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "�û��˵�����������ʧ��!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

            //��ʼ���´��û������Ĳ˵���
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
            // @@������
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
