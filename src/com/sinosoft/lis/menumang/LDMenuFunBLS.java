/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 * �޸��ˣ�����
 * �޸����ڣ�2005-5-4
 * �޸�����1�����Ӹ��ݲ˵���־��NodeSign��������Ӧ�˵����ɾ�����߼��ж�
 */
package com.sinosoft.lis.menumang;

import java.sql.Connection;

import com.sinosoft.lis.db.LDMenuDB;
import com.sinosoft.lis.db.LDMenuGrpToMenuDB;
import com.sinosoft.lis.schema.LDMenuGrpToMenuSchema;
import com.sinosoft.lis.schema.LDMenuSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;


/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Fanym
 * @version 1.0
 */

public class LDMenuFunBLS
{
    //�������࣬ÿ����Ҫ����������ж����ø���
    public CErrors mErrors = new CErrors();

    /** ���ݲ����ַ��� */
    private String mOperate = null;
    private VData mInputData = new VData();

    public LDMenuFunBLS()
    {
    }

    public boolean submitData(VData cInputData, String cOperate)
    {
        boolean tReturn = false;
        //���������ݿ�����������
        this.mOperate = cOperate;
        this.mInputData = (VData) cInputData.clone();
//        System.out.println("Start LDMenu BLS Submit...");
        //�˵�����
        if (this.mOperate.equals("insert"))
        {
            tReturn = insertCode(this.mInputData);
        }

        //�˵�ɾ��
        if (this.mOperate.equals("delete"))
        {
            tReturn = deleteCode(this.mInputData);
        }

//        System.out.println("End LDMenu BLS Submit...");

        return tReturn;
    }


//ɾ������
    private boolean deleteCode(VData mInputData)
    {
        boolean tReturn = true;
//        System.out.println("start delete...");
        Connection conn = DBConnPool.getConnection();
        if (conn == null)
        {
            // @@������
            CError tError = new CError();
            tError.moduleName = "LDMenuFunBLS";
            tError.functionName = "saveData";
            tError.errorMessage = "���ݿ�����ʧ��!";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            conn.setAutoCommit(false);
            LDMenuSchema tLDMenuSchema = (LDMenuSchema) mInputData.
                                         getObjectByObjectName("LDMenuSchema",
                    0);
            String ChildFlag = tLDMenuSchema.getChildFlag();
            String NodeCode = tLDMenuSchema.getNodeCode();
            String ParentNodeCode = tLDMenuSchema.getParentNodeCode();
            String NodeSign = tLDMenuSchema.getNodeSign();
            
            System.out.println("�Ӳ˵�" + ChildFlag);
            
            if (!ChildFlag.equals("0"))
            {
            	System.out.println("�Ӳ˵���Ϊ0");
                CError tError = new CError();
                tError.moduleName = "LDMenuFunBLS";
                tError.functionName = "deleteCode";
                tError.errorMessage = "���Ӳ˵��Ĳ˵�����ɾ��!";
                this.mErrors.addOneError(tError);
                conn.close();
                return false;
            }
            else
            {
                System.out.println("Start ɾ���˵�...");
                //�����˵�
//                System.out.println("BLS insert inputdata...childflag:" +
//                                   ChildFlag);
//                System.out.println("BLS insert inputdata...NodeCode" + NodeCode);
//                System.out.println("BLS insert inputdata...ParentNodeCode" +
//                                   ParentNodeCode);
              //����˵�����͹�����
                System.out.println("����˵�����͹�����");
                System.out.println("Start ����˵�����Ͳ˵�������...");
                ExeSQL tExeSQL = new ExeSQL(conn);
                String sql = "delete from LDMenuGrpToMenu where NodeCode = '"
                             + NodeCode + "'";
                if (!tExeSQL.execUpdateSQL(sql))
                {
                    CError tError = new CError();
                    tError.moduleName = "LDMenuFunBLS";
                    tError.functionName = "deleteCode";
                    tError.errorMessage = "�˵�����͹�����ɾ��ʧ��!";
                    this.mErrors.addOneError(tError);
                    conn.rollback();
                    conn.close();
                    return false;
                }
                
                LDMenuDB tLDMenuDB = new LDMenuDB(conn);
                tLDMenuDB.setSchema(tLDMenuSchema);
                if (!tLDMenuDB.deleteSQL())
                {
                    // @@������
                    CError tError = new CError();
                    tError.moduleName = "LDMenuFunBLS";
                    tError.functionName = "deleteCode";
                    tError.errorMessage = "�˵�ɾ��ʧ��!";
                    this.mErrors.addOneError(tError);
                    conn.rollback();
                    conn.close();
                    return false;
                }
                if (ParentNodeCode.equals("0") || NodeSign.equals("2"))
                {
                	System.out.println("prentnodecode = " + 0);
                	System.out.println("NodeSign = " + 2);
                }
                else
                {
                    System.out.println("Start �����˵�...");
                    tExeSQL = new ExeSQL(conn);
                    sql =
                            "update LDMenu set ChildFlag = to_char((to_number(ChildFlag) - 1)) "
                            + "Where NodeCode = '"
                            + ParentNodeCode + "'";
                    if (!tExeSQL.execUpdateSQL(sql))
                    {
                        CError tError = new CError();
                        tError.moduleName = "LDMenuFunBLS";
                        tError.functionName = "deleteCode";
                        tError.errorMessage = "���˵�����ʧ��!";
                        this.mErrors.addOneError(tError);
                        conn.rollback();
                        conn.close();
                        return false;
                    }
                }
                //����˵�����͹�����
                System.out.println("Start ����˵�����Ͳ˵�������...");
                tExeSQL = new ExeSQL(conn);
                //ɾ��ĩ���˵���ҳ��Ȩ�޲˵�
                String sql2 ="delete from LDMenu where NodeCode = '" + NodeCode + "'"
                             + " or (ParentNodeCode = '" + NodeCode + "' and NodeSign = 2)";
                if (!tExeSQL.execUpdateSQL(sql2))
                {
                    CError tError = new CError();
                    tError.moduleName = "LDMenuFunBLS";
                    tError.functionName = "deleteCode";
                    tError.errorMessage = "�˵�����͹�����ɾ��ʧ��!";
                    this.mErrors.addOneError(tError);
                    conn.rollback();
                    conn.close();
                    return false;
                }
            }
            conn.commit();
            conn.close();
//            System.out.println("commit end");
        }
        catch (Exception ex)
        {
            // @@������
            CError tError = new CError();
            tError.moduleName = "LDMenuFunBLS";
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

    private boolean insertCode(VData mInputData)
    {
        boolean tReturn = true;
//        System.out.println("start insert...");
        Connection conn = DBConnPool.getConnection();
        if (conn == null)
        {
            // @@������
//            System.out.println("#####Conn error");
            CError tError = new CError();
            tError.moduleName = "LDMenuFunBLS";
            tError.functionName = "saveData";
            tError.errorMessage = "���ݿ�����ʧ��!";
            this.mErrors.addOneError(tError);
            return false;
        }
        try
        {
            conn.setAutoCommit(false);
            LDMenuSchema tLDMenuSchema = (LDMenuSchema) mInputData.
                                         getObjectByObjectName("LDMenuSchema",
                    0);
            TransferData t = (TransferData) mInputData.getObjectByObjectName(
                    "TransferData", 0);
            String ischild = (String) t.getValueByName("ischild");
            String ischild2 = (String) t.getValueByName("ischild2");
            String Node = (String) t.getValueByName("NodeCode");
            String RunScript = (String) t.getValueByName("RunScript");

            //��Ϊҳ��Ȩ�޲˵�����ʱ,RunScript����Ψһ
            if (ischild2.equalsIgnoreCase("true")){
                ExeSQL tExeSQL = new ExeSQL(conn);
                String sql = "select RunScript from LDMenu ";
                sql = sql + "where NodeSign = 2 and RunScript='"+RunScript+"'";
                String isgetnull = tExeSQL.getOneValue(sql);
                if (isgetnull != null){
                    System.out.println("��Ϊҳ��Ȩ�޲˵�����ʱ,RunScript(ӳ���ļ�)����Ψһ!");
                    return false;
                }
            }

            //ȡNodeCode��NodeOrder
            ExeSQL tExeSQL = new ExeSQL(conn);
            // to_number������SQLServer�в����� modify by hwf at 2008-7-24
//            String sql = "select max(to_number(NodeCode))+1 from LDMenu";
            String sql = "select max(to_number(NodeCode))+1 from LDMenu";
            String MaxCode = tExeSQL.getOneValue(sql);
//            System.out.println("Maxcode::" + MaxCode);
            sql = "select NodeOrder from LDMenu where NodeCode = '"
                  + Node + "'";
            int NodeOrder = Integer.parseInt(tExeSQL.getOneValue(sql));
//            System.out.println("NodeOrder::" + NodeOrder);
            //tLDMenuSchema.setChildFlag("0");
            tLDMenuSchema.setNodeCode(MaxCode);
            //���²˵����NodeOrder
            if (ischild.equalsIgnoreCase("true"))
            {
                sql = "update LDMenu set NodeOrder = NodeOrder+1 "
                      + "Where NodeOrder > "
                      + NodeOrder;
                tLDMenuSchema.setNodeOrder(NodeOrder + 1);
            }
            else
            {
                sql = "update LDMenu set NodeOrder = NodeOrder+1 "
                      + "Where NodeOrder >= "
                      + NodeOrder;
                tLDMenuSchema.setNodeOrder(NodeOrder);
            }
            if (!tExeSQL.execUpdateSQL(sql))
            {
                CError tError = new CError();
                tError.moduleName = "LDMenuFunBLS";
                tError.functionName = "insertCode";
                tError.errorMessage = "����NodeOrderʧ��!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }
            LDMenuDB tLDMenuDB = new LDMenuDB(conn);
            tLDMenuDB.setSchema(tLDMenuSchema);
            if (ischild.equalsIgnoreCase("true"))
            {
                //�����Ӳ˵�����
                //tLDMenuSchema.setParentNodeCode(Node);
                if (!tLDMenuDB.insert())
                {
                    CError tError = new CError();
                    tError.moduleName = "LDMenuFunBLS";
                    tError.functionName = "insertCode";
                    tError.errorMessage = "����˵�ʧ��!";
                    this.mErrors.addOneError(tError);
                    conn.rollback();
                    conn.close();
                    return false;
                }
                //�Ƿ�Ϊҳ�湦�ܲ˵�2005
                if (ischild2.equalsIgnoreCase("false"))
                {
                	// to_number ��SQLServer�в����� modify by hwf at 2008-7-24
//                    sql =
//                            "update LDMenu set ChildFlag = to_char(to_number(ChildFlag) +1) "
//                            + "Where NodeCode = '" + Node + "'";
                	
                	
                    sql =
                            "update LDMenu set ChildFlag = to_char((to_number(ChildFlag) +1)) "
                            + "Where NodeCode = '" + Node + "'";
                    if (!tExeSQL.execUpdateSQL(sql)) {
                    CError tError = new CError();
                    tError.moduleName = "LDMenuFunBLS";
                    tError.functionName = "insertCode";
                    tError.errorMessage = "���¸��˵�������ʧ��!";
                    this.mErrors.addOneError(tError);
                    conn.rollback();
                    conn.close();
                    return false;
                  }
                }
                else
                {
                    sql =
                            "update LDMenu set NodeSign = '1' "
                            + "Where NodeCode = '" + Node + "'";
                    if (!tExeSQL.execUpdateSQL(sql)) {
                    CError tError = new CError();
                    tError.moduleName = "LDMenuFunBLS";
                    tError.functionName = "insertCode";
                    tError.errorMessage = "���¸��˵���־λʧ��!";
                    this.mErrors.addOneError(tError);
                    conn.rollback();
                    conn.close();
                    return false;
                  }
                }
            }
            else
            {
                //����ͬ���˵�����
                //tLDMenuSchema.setParentNodeCode("0");
                //tLDMenuDB.setSchema(tLDMenuSchema);
                if (!tLDMenuDB.insert())
                {
                    CError tError = new CError();
                    tError.moduleName = "LDMenuFunBLS";
                    tError.functionName = "insertCode";
                    tError.errorMessage = "����˵�ʧ��!";
                    this.mErrors.addOneError(tError);
                    conn.rollback();
                    conn.close();
                    return false;
                }
                //�Ƿ�Ϊҳ�湦�ܲ˵�2005
                if (ischild2.equalsIgnoreCase("false"))
                {
                    sql =
                            "update LDMenu set ChildFlag = to_char((to_number(ChildFlag) +1))" +
                            "Where NodeCode = '" +
                            tLDMenuSchema.getParentNodeCode() +
                            "'";
                    if (!tExeSQL.execUpdateSQL(sql)) {
                        CError tError = new CError();
                        tError.moduleName = "LDMenuFunBLS";
                        tError.functionName = "insertCode";
                        tError.errorMessage = "���¸��˵�������ʧ��!";
                        this.mErrors.addOneError(tError);
                        conn.rollback();
                        conn.close();
                        return false;
                    }
                }
//                else
//                {
//                    sql =
//                            "update LDMenu set NodeSign = '1' "
//                            + "Where NodeCode = '" + Node + "'";
//                    if (!tExeSQL.execUpdateSQL(sql))
//                    {
//                    CError tError = new CError();
//                    tError.moduleName = "LDMenuFunBLS";
//                    tError.functionName = "insertCode";
//                    tError.errorMessage = "���¸��˵���־λʧ��!";
//                    this.mErrors.addOneError(tError);
//                    conn.rollback();
//                    conn.close();
//                    return false;
//                    }
//                }
            }
            //����˵�����������²˵�Ĭ�Ͻ��������8080�˵���
            LDMenuGrpToMenuSchema tLDMenuGrpToMenuSchema = new
                    LDMenuGrpToMenuSchema();
            tLDMenuGrpToMenuSchema.setMenuGrpCode("8080");
            tLDMenuGrpToMenuSchema.setNodeCode(MaxCode);
            LDMenuGrpToMenuDB tLDMenuGrpToMenuDB = new LDMenuGrpToMenuDB(conn);
            tLDMenuGrpToMenuDB.setSchema(tLDMenuGrpToMenuSchema);
            if (!tLDMenuGrpToMenuDB.insert())
            {
                CError tError = new CError();
                tError.moduleName = "LDMenuFunBLS";
                tError.functionName = "insertCode";
                tError.errorMessage = "����˵����������ʧ��!";
                this.mErrors.addOneError(tError);
                conn.rollback();
                conn.close();
                return false;
            }
            conn.commit();
            conn.close();
        }
        catch (Exception ex)
        {
            // @@������
            CError tError = new CError();
            tError.moduleName = "LDMenuFunBLS";
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
