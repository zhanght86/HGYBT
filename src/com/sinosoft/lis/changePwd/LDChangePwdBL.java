package com.sinosoft.lis.changePwd;

import java.sql.Connection;

import com.sinosoft.lis.db.LDUserDB;
import com.sinosoft.lis.schema.LDUserSchema;
import com.sinosoft.lis.vschema.LDUserSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description:
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author DingZhong
 * @version 1.0
 * 
 * �����޸�����ͬʱ�޸�����ָ��
 * @author zkr02
 * @since 1.0.1
 */
public class LDChangePwdBL
{
    /** �������࣬ÿ����Ҫ����������ж����ø��� */
    public CErrors mErrors = new CErrors();
    /** �����洫�����ݵ����� */
    private VData mResult = new VData();
//    private VData mInputData = new VData();
    /** ���ݲ����ַ��� */
//    private String mOperate;

    /** ҵ������ر��� */
    /** �û��������Ϣ*/
    LDUserSchema mLDOldUserSchema = new LDUserSchema();
    LDUserSchema mLDNewUserSchema = new LDUserSchema();

    String mResultStr = "";
    int mResultNum = 0;

    public LDChangePwdBL()
    {
        // just for debug
    }

    /**
     * �������ݵĹ�������
     * @param cInputData VData
     * @param cOperate String
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        // �жϲ����ǲ��ǲ�ѯ
        if (cOperate.compareTo("changePwd") != 0)
        {
            return false;
        }

        System.out.println("start BL submit...");

        //���������ݿ�����������
//        this.mOperate = cOperate;

        //�õ��ⲿ���������,�����ݱ��ݵ�������
        if (!getInputData(cInputData))
        {
            return false;
        }
        System.out.println("start dealData");
        //����ҵ����
        if (!dealData())
        {
            return false;
        }

        System.out.println("After dealData��");

        return true;
    }


    private boolean dealData()
    {

        String usercode = mLDOldUserSchema.getUserCode();
        System.out.println("oldPwd plain is :" + mLDOldUserSchema.getPassword());
//        LisIDEA tLisIdea = new LisIDEA();

        String oldpwd = mLDOldUserSchema.getPassword();
        //oldpwd = tLisIdea.encryptString(oldpwd);

//        System.out.println("usercode:" + usercode);
//        System.out.println("oldpwd:" + oldpwd);
//        System.out.println(mLDNewUserSchema.getPassword());

//        String sqlStr = "select * from lduser where usercode =  '" + usercode +
//                        "' and password = '" + oldpwd + "'";
        StringBuffer tSBql = new StringBuffer();
        tSBql.append("select * from lduser where usercode =  '");
        tSBql.append(usercode);
        tSBql.append("' and password = '");
        tSBql.append(oldpwd);
        tSBql.append("'");

//        System.out.println(sqlStr);

        LDUserSchema tLDUserSchema = new LDUserSchema();
        LDUserDB tLDUserDB1 = tLDUserSchema.getDB();
        LDUserSet tLDUserSet = tLDUserDB1.executeQuery(tSBql.toString());
        if (tLDUserDB1.mErrors.needDealError() || tLDUserSet.size() != 1)
        {
            this.mErrors.copyAllErrors(tLDUserDB1.mErrors);
            CError tError = new CError();
            tError.moduleName = "LDChangePwdBL";
            tError.functionName = "dealData";
            tError.errorMessage = "ȷ��ԭ�������";
            this.mErrors.addOneError(tError);
            return false;
        }
//         System.out.println("old password is right");

        String newpwd = mLDNewUserSchema.getPassword();
//        System.out.println("newpwd :" + newpwd);
        if (newpwd.equals(""))
        {
            return false;
        }

        LDUserSchema newSchema = tLDUserSet.get(1);
        newSchema.setPassword(newpwd);
        LDUserDB ldUserDB = new LDUserDB();
        ldUserDB.setSchema(newSchema);

        /*//��ʼ�����û�����
        Connection conn = DBConnPool.getConnection();
        if (conn == null)
        {
            System.out.println("���������������ݿ�ʧ�ܣ�");
            return false;

        }
        try
        {
        	
             * add by pangmingshi
             * for add the password print
             * on 2009-02-11
             
//            String encryptNewPW = PubFun.hashEncrypt(newpwd.getBytes(), "SHA-1");
//        	System.out.println("������ָ�ƣ�" + encryptNewPW);
//            newSchema.setGEdorPopedom(encryptNewPW);
            
            conn.setAutoCommit(true);
//            System.out.println("Start �����û�����...");
//            LDUserDB tLDUserDB = new LDUserDB(conn);
//            tLDUserDB.setSchema(newSchema);
//
            //���²˵����
//            if (!tLDUserDB.update())
//            {
//                // @@������
//                CError tError = new CError();
//                tError.moduleName = "LDChangePwdBL";
//                tError.functionName = "dealData";
//                tError.errorMessage = "�û����������ʧ��!";
//                this.mErrors.addOneError(tError);
//                conn.rollback();
//                conn.close();
//                return false;
//            }
//            conn.commit();
//            conn.close();
//            System.out.println("commit end");
        	
        	String strSql = "update LDUser set Password = '" + newSchema.getPassword() +  "' where UserCode = '" + newSchema.getUserCode() + "'";
        	boolean b = new ExeSQL().execUpdateSQL(strSql);
        	if(!b){
        		System.out.println("��������ʧ�ܣ�");
                // @@������
                CError tError = new CError();
                tError.errorMessage = "��������ʧ��";
                this.mErrors.addOneError(tError);
                return false;
        	}
        }
        catch (Exception ex)
        {
            // @@������
            CError tError = new CError();
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            try
            {
                conn.rollback();
                conn.close();
            }
            catch (Exception e)
            {}
            return false;
        }
        */
        
        if(!ldUserDB.update()){
    		System.out.println("��������ʧ�ܣ�");
            // @@������
            CError tError = new CError();
            tError.errorMessage = "��������ʧ��";
            this.mErrors.addOneError(tError);
            return false;
    	}
        return true;
    }


    public VData getResult()
    {
        return mResult;
    }

    public int getResultNum()
    {
        return mResultNum;
    }

    public String getResultStr()
    {
        String resultStr = "";
        for (int i = 1; i <= mResultNum; i++)
        {

        }
        return resultStr;
    }


    /**
     * �����������еõ����ж���
     * ��������û�еõ��㹻��ҵ�����ݶ����򷵻�false,���򷵻�true
     * @param cInputData VData
     * @return boolean
     */
    private boolean getInputData(VData cInputData)
    {
        // �����ѯ����
        mLDOldUserSchema = (LDUserSchema) cInputData.getObjectByObjectName(
                "LDUserSchema", 0);
        mLDNewUserSchema = (LDUserSchema) cInputData.getObjectByObjectName(
                "LDUserSchema", 1);
        if (mLDOldUserSchema == null || mLDNewUserSchema == null)
        {
            System.out.println("cant get password");
            return false;
        }
        System.out.println("completed get input data");
        return true;
    }

    /**
     * ���Ժ���
     * @param args String[]
     */
    public static void main(String[] args)
    {
//        LDChangePwdBL tLDChangePwdBL = new LDChangePwdBL();
//        LDUserSchema tOldSchema = new LDUserSchema();
//        LDUserSchema tNewSchema = new LDUserSchema();
//        String oldPwd = "222";
//        String newPwd = "333";
//        for (int i = 0; i < 110; i++)
//        {
//            oldPwd = "333";
//            newPwd = "333";
//            tOldSchema.setUserCode("002");
//            tOldSchema.setPassword(oldPwd);
//            tNewSchema.setUserCode("002");
//            tNewSchema.setPassword(newPwd);
//            VData tVData = new VData();
//            tVData.add(tOldSchema);
//            tVData.add(tNewSchema);
//            boolean suc = tLDChangePwdBL.submitData(tVData, "changePwd");
//            if (suc)
//            {
//                System.out.println("change successful");
//            }
//            else
//            {
//                System.out.println("change fail");
//            }
//        }
    }
}
