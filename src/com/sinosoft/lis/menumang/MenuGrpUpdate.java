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


/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description:
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author DingZhong
 * @version 1.0
 */
public class MenuGrpUpdate
{

    public MenuGrpUpdate()
    {
        // just for debug

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
//                        	System.out.println("nodecode 2 :" + nodecode2 + " nodecode1 : " + nodecode1);
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
                    return false;
                }
                LDMenuGrpToMenuDBSet tLDMenuGrpToMenuDBSet = new
                        LDMenuGrpToMenuDBSet(conn);
                tLDMenuGrpToMenuDBSet.set(menuSet);
                if (!tLDMenuGrpToMenuDBSet.insert())
                {
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
}
