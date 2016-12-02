/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.menumang;

//import java.sql.Connection;

import com.sinosoft.lis.schema.LDMenuGrpSchema;
import com.sinosoft.lis.schema.LDMenuGrpToMenuSchema;
import com.sinosoft.lis.vschema.LDMenuGrpToMenuSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
//import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description: �˵���ҵ���߼�������</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author DingZhong
 * @version 1.0
 */
public class LDMenuGrpBL
{
    /** �������࣬ÿ����Ҫ����������ж����ø��� */
    public CErrors mErrors = new CErrors();
    /** �����洫�����ݵ����� */
//    private VData mResult = new VData();
    private VData mInputData = new VData();
    /** ���ݲ����ַ��� */
    private String mOperate;

    private LDMenuGrpToMenuSet mLDMenuGrpToMenuSet = new LDMenuGrpToMenuSet();
    private LDMenuGrpSchema mLDMenuGrpSchema = new LDMenuGrpSchema();
    private String mLDMenuGrpToMenuSetStr = "";
    private String mRemoveSetStr = "";
    private LDMenuGrpToMenuSet mRemoveSet = new LDMenuGrpToMenuSet();
    //ҵ������ر���

    public LDMenuGrpBL()
    {
    }

    //�������ݵĹ�������
    public boolean submitData(VData cInputData, String cOperate)
    {
        //���������ݿ�����������
        this.mOperate = cOperate;
try{
        //�õ��ⲿ���������,�����ݱ��ݵ�������
        if (!getInputData(cInputData))
        {
            return false;
        }
//        System.out.println("After getinputdata");
} catch(Exception e){
	e.printStackTrace();
}
        //����ҵ����
        if (!dealData())
        {
            return false;
        }
//        System.out.println("After dealData��");
        //׼������̨������
        if (!prepareOutputData())
        {
            return false;
        }
//        System.out.println("After prepareOutputData");

//        System.out.println("Start LDMenuGrp BL Submit...");

        LDMenuGrpBLS tLDMenuGrpBLS = new LDMenuGrpBLS();
        boolean tag = tLDMenuGrpBLS.submitData(mInputData, cOperate);
//        System.out.println("tag : " + tag);
        if (!tag)
        {
            return false;
        }

//        System.out.println("End LDMenuGrp BL Submit...");

        //�������Ҫ����Ĵ����򷵻�
        if (tLDMenuGrpBLS.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tLDMenuGrpBLS.mErrors);
            return false;
        }

        mInputData = null;
        return true;
    }

    //����ǰ����������ݣ������߼�����
    //����ڴ�������г����򷵻�false,���򷵻�true
    private static boolean dealData()
    {
    	System.out.println("��ʼ��������");
        return true;

    }

    /**
     * �����������еõ����ж���
     * ��������û�еõ��㹻��ҵ�����ݶ����򷵻�false,���򷵻�true
     * @param mInputData VData
     * @return boolean
     */
    private boolean getInputData(VData mInputData)
    {
        System.out.println("LDMenuGrpBL.java:�����������еõ����ж���start BL get inputdata...");//2005

        mLDMenuGrpSchema = (LDMenuGrpSchema) mInputData.getObjectByObjectName(
                "LDMenuGrpSchema", 0);
        mLDMenuGrpToMenuSet.set((LDMenuGrpToMenuSet) mInputData.
                                getObjectByObjectName("LDMenuGrpToMenuSet", 0));

        if ((mOperate.compareTo("delete") != 0) &&
            (mLDMenuGrpToMenuSet == null || mLDMenuGrpToMenuSet.size() == 0))
        {

            mLDMenuGrpToMenuSetStr = (String) mInputData.getObjectByObjectName(
                    "String", 0);
            mRemoveSetStr = (String) mInputData.getObjectByObjectName("String",
                    1);
//            System.out.println("**mRemoveSetStr :" + mRemoveSetStr);
            if (mLDMenuGrpToMenuSetStr == null || mLDMenuGrpToMenuSetStr.equals(""))
            {
                return false;
            }

            stringToSet(mLDMenuGrpToMenuSetStr, mLDMenuGrpToMenuSet);

            if (mRemoveSetStr != null && !mRemoveSetStr.equals(""))
            {
                stringToSet(mRemoveSetStr, mRemoveSet);
//                System.out.println("mRemoveSet size is " + mRemoveSet.size());
            }
        }

    //        System.out.println(mLDMenuGrpToMenuSet.size());

        if (mLDMenuGrpSchema == null ||
            (!mOperate.equals("delete") && mLDMenuGrpToMenuSet == null))
        {
            // @@������
            CError tError = new CError();
            tError.moduleName = "LDMenuGrpBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "û�еõ��㹻�����ݣ�����ȷ��!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    //׼��������������Ҫ������
    //��������׼������ʱ���������򷵻�false,���򷵻�true
    private boolean prepareOutputData()
    {
        mInputData = new VData();
        try
        {
            mInputData.add(mLDMenuGrpSchema);
            mInputData.add(mLDMenuGrpToMenuSet);
            mInputData.add(mRemoveSet);
        }
        catch (Exception ex)
        {
            // @@������
            CError tError = new CError();
            tError.moduleName = "LDMenuGrpBL";
            tError.functionName = "prepareData";
            tError.errorMessage = "��׼������㴦������Ҫ������ʱ����";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public void stringToSet(String schemaString, LDMenuGrpToMenuSet stringSet)
    {
        stringSet.clear();

        int serialNo = 1;
        String schemaStr = StrTool.decodeStr(schemaString, "^", serialNo);
        while (!schemaStr.equals("") && serialNo < schemaString.length())
        {
            LDMenuGrpToMenuSchema tSchema = new LDMenuGrpToMenuSchema();

            String menuGrpCode = StrTool.decodeStr(schemaStr, "|", 1);
            String menuCode = StrTool.decodeStr(schemaStr, "|", 2);

            tSchema.setMenuGrpCode(menuGrpCode);
            tSchema.setNodeCode(menuCode);
            stringSet.add(tSchema);

            serialNo++;
            schemaStr = StrTool.decodeStr(schemaString, "^", serialNo);
        }
    }
}
