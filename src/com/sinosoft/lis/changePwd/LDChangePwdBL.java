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
 * <p>Title: Web业务系统</p>
 * <p>Description:
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author DingZhong
 * @version 1.0
 * 
 * 增加修改密码同时修改密码指纹
 * @author zkr02
 * @since 1.0.1
 */
public class LDChangePwdBL
{
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mResult = new VData();
//    private VData mInputData = new VData();
    /** 数据操作字符串 */
//    private String mOperate;

    /** 业务处理相关变量 */
    /** 用户的相关信息*/
    LDUserSchema mLDOldUserSchema = new LDUserSchema();
    LDUserSchema mLDNewUserSchema = new LDUserSchema();

    String mResultStr = "";
    int mResultNum = 0;

    public LDChangePwdBL()
    {
        // just for debug
    }

    /**
     * 传输数据的公共方法
     * @param cInputData VData
     * @param cOperate String
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        // 判断操作是不是查询
        if (cOperate.compareTo("changePwd") != 0)
        {
            return false;
        }

        System.out.println("start BL submit...");

        //将操作数据拷贝到本类中
//        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }
        System.out.println("start dealData");
        //进行业务处理
        if (!dealData())
        {
            return false;
        }

        System.out.println("After dealData！");

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
            tError.errorMessage = "确认原密码出错";
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

        /*//开始更新用户密码
        Connection conn = DBConnPool.getConnection();
        if (conn == null)
        {
            System.out.println("更新密码连接数据库失败！");
            return false;

        }
        try
        {
        	
             * add by pangmingshi
             * for add the password print
             * on 2009-02-11
             
//            String encryptNewPW = PubFun.hashEncrypt(newpwd.getBytes(), "SHA-1");
//        	System.out.println("新密码指纹：" + encryptNewPW);
//            newSchema.setGEdorPopedom(encryptNewPW);
            
            conn.setAutoCommit(true);
//            System.out.println("Start 更新用户密码...");
//            LDUserDB tLDUserDB = new LDUserDB(conn);
//            tLDUserDB.setSchema(newSchema);
//
            //更新菜单组表
//            if (!tLDUserDB.update())
//            {
//                // @@错误处理
//                CError tError = new CError();
//                tError.moduleName = "LDChangePwdBL";
//                tError.functionName = "dealData";
//                tError.errorMessage = "用户表密码更新失败!";
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
        		System.out.println("更新密码失败！");
                // @@错误处理
                CError tError = new CError();
                tError.errorMessage = "更新密码失败";
                this.mErrors.addOneError(tError);
                return false;
        	}
        }
        catch (Exception ex)
        {
            // @@错误处理
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
    		System.out.println("更新密码失败！");
            // @@错误处理
            CError tError = new CError();
            tError.errorMessage = "更新密码失败";
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
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     * @param cInputData VData
     * @return boolean
     */
    private boolean getInputData(VData cInputData)
    {
        // 检验查询条件
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
     * 测试函数
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
