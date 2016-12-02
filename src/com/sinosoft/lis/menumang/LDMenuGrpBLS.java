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
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Dingzhong
 * @version 1.0
 */

public class LDMenuGrpBLS
{
    //�������࣬ÿ����Ҫ����������ж����ø���
    public CErrors mErrors = new CErrors();
    /** ���ݲ����ַ��� */
    private String mOperate;

    public LDMenuGrpBLS()
    {
    }

    //�������ݵĹ�������
    public boolean submitData(VData cInputData, String cOperate)
    {
        boolean tReturn = false;
        //���������ݿ�����������
        this.mOperate = cOperate;
//        System.out.println("Start LDMenuGrp BLS Submit...");

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

//        System.out.println("End LDMenuGrp BLS Submit...");

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
            tError.moduleName = "LDMenuGrpBLS";
            tError.functionName = "saveData";
            tError.errorMessage = "���ݿ�����ʧ��!";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            conn.setAutoCommit(false);
//            System.out.println("Start ɾ���˵���...");
            LDMenuGrpDB tLDMenuGrpDB = new LDMenuGrpDB(conn);
            tLDMenuGrpDB.setSchema((LDMenuGrpSchema) mInputData.
                                   getObjectByObjectName("LDMenuGrpSchema", 0));
            if (!tLDMenuGrpDB.deleteSQL())
            {
                // @@������
                CError tError = new CError();
                tError.moduleName = "LDMenuGrpBLS";
                tError.functionName = "removeData";
                tError.errorMessage = "�˵���ɾ��ʧ��!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

//            System.out.println("Start �˵���˵�������ɾ��....");

            LDMenuGrpToMenuDB tLDMenuGrpToMenuDB = new LDMenuGrpToMenuDB(conn);
            LDMenuGrpToMenuSchema tLDMenuGrpToMenuSchema = new
                    LDMenuGrpToMenuSchema();
            LDMenuGrpSchema tLDMenuGrpSchema = new LDMenuGrpSchema();

            tLDMenuGrpSchema = (LDMenuGrpSchema) mInputData.
                               getObjectByObjectName("LDMenuGrpSchema", 0);
            tLDMenuGrpToMenuSchema.setMenuGrpCode(tLDMenuGrpSchema.
                                                  getMenuGrpCode());
            tLDMenuGrpToMenuDB.setSchema(tLDMenuGrpToMenuSchema);

            //������ɾ������ǰ�õ����²˵����removeset����
            //Ϊ������ز˵�����׼������
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
                // @@������
                this.mErrors.copyAllErrors(tLDMenuGrpToMenuDB.mErrors);
                CError tError = new CError();
                tError.moduleName = "LDMenuGrpBLS";
                tError.functionName = "RemoveData";
                tError.errorMessage = "�˵���˵�������ɾ��ʧ��!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

            //���ڿ�ʼ������صĲ˵���
//            System.out.println("���ڿ�ʼ�ݹ�ĸ�����صĲ˵���...");
            sqlStr = "select * from ldusertoMenugrp where menugrpcode = '" +
                     grpcode + "'";
//            System.out.println(sqlStr);

            LDUserTOMenuGrpDB tLDUserDB = new LDUserTOMenuGrpDB(conn);
            LDUserTOMenuGrpSet tUserSet = tLDUserDB.executeQuery(sqlStr);
//            System.out.println("ʹ�ô˲˵�����û���Ϊ" + tUserSet.size());

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
            { // ��ز˵������ʧ��
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
            tError.moduleName = "LDMenuGrpBLS";
            tError.functionName = "saveData";
            tError.errorMessage = "���ݿ�����ʧ��!";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            conn.setAutoCommit(false);

//            System.out.println("Start ����˵���...");
            LDMenuGrpDB tLDMenuGrpDB = new LDMenuGrpDB(conn);
            tLDMenuGrpDB.setSchema((LDMenuGrpSchema) mInputData.
                                   getObjectByObjectName("LDMenuGrpSchema", 0));
            if (!tLDMenuGrpDB.insert())
            {
                // @@������
                CError tError = new CError();
                tError.moduleName = "LDMenuGrpBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "�˵��鱣��ʧ��!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
//                System.out.println(tError.functionName);
                return false;
            }

//            System.out.println("Start �˵��ڵ����....");
            LDMenuGrpToMenuDBSet tLDMenuGrpToMenuDBSet = new
                    LDMenuGrpToMenuDBSet(conn);

            tLDMenuGrpToMenuDBSet.set((LDMenuGrpToMenuSet) mInputData.
                                      getObjectByObjectName(
                                              "LDMenuGrpToMenuSet", 0));

            int len = tLDMenuGrpToMenuDBSet.size();
//            System.out.println("len:" + len);

            if (!tLDMenuGrpToMenuDBSet.insert())
            {
                // @@������
                this.mErrors.copyAllErrors(tLDMenuGrpToMenuDBSet.mErrors);
                CError tError = new CError();
                tError.moduleName = "LDMenuGrpBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "�˵���˵���������ʧ��!";
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
            tError.moduleName = "LDMenuGrpBLS";
            tError.functionName = "saveData";
            tError.errorMessage = "���ݿ�����ʧ��!";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            conn.setAutoCommit(false);

//            System.out.println("Start ���²˵���...");
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

            //���²˵����
            if (!tLDMenuGrpDB.update())
            {
                // @@������
                CError tError = new CError();
                tError.moduleName = "LDMenuGrpBLS";
                tError.functionName = "update";
                tError.errorMessage = "�˵�������ʧ��!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

            //��������ɾ���������еĹ�����Ȼ���ٲ������µ�����
            if (!tLDMenuGrpToMenuDB.deleteSQL())
            {
                // @@������
                CError tError = new CError();
                tError.moduleName = "LDMenuGrpBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "�˵�����������ʧ��!";
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
//            System.out.println("��ʼ����...");
            if (!tLDMenuGrpToMenuDBSet.insert())
            {
                // @@������
                this.mErrors.copyAllErrors(tLDMenuGrpToMenuDBSet.mErrors);
                CError tError = new CError();
                tError.moduleName = "LDMenuGrpBLS";
                tError.functionName = "saveData";
                tError.errorMessage = "�˵���˵����������ʧ��!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }

            //���ڿ�ʼ�ݹ�ĸ�����صĲ˵���
//            System.out.println("���ڿ�ʼ�ݹ�ĸ�����صĲ˵���...");
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
//            System.out.println("ʹ�ô˲˵�����û���Ϊ" + tUserSet.size());

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
                // ��ز˵������ʧ��
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

        //�õ������������Ӻ�ɾȥ�Ĳ˵��ڵ㼯��
        //LDMenuSet allMenu =  ���еĲ˵�����
        //����ÿһ��addMenuSet�е�Ԫ�أ������allMenu�У���ɾ��
        //�õ���hisAddMenuSet,hisRemoveMenuSet;
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

            // �õ����б���Ϊusercode���û������Ĳ˵�����뼯��
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

            //����ÿһ���˵��飬�������Ĳ˵��ڵ㼯��
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

                //����˲˵���ڵ㼯�����е���realRemoveMenuSet�У���ɾ��������
                //������뵽nextRemoveMenuSet��
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
                        menuSet.remove(delMenuSchema); //���������⣬ָ������
//                        System.out.println("menuSet.size is :" + menuSet.size());
                    }
                }
//                System.out.println("nextRemoveSet size is" + nextRemoveSet.size());
                if (nextRemoveSet.size() == 0)
                {
                    continue;
                }
//                System.out.println("here");
                //����menuSet,�����´˲˵���
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
                //�����������ô˲˵�����û������еݹ���ý��и���
                //���ȵõ����е��û���
                sqlStr = "select * from ldusertoMenugrp where menugrpcode = '" +
                         tMenuGrpCode + "'";

//                System.out.println(sqlStr);

                LDUserTOMenuGrpDB tLDUserDB = new LDUserTOMenuGrpDB(conn);
                LDUserTOMenuGrpSet tUserSet = tLDUserDB.executeQuery(sqlStr);
//                System.out.println("ʹ�ô˲˵�����û���Ϊ" + tUserSet.size());
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
