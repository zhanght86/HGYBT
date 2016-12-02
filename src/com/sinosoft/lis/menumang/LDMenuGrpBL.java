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
 * <p>Title: Web业务系统</p>
 * <p>Description: 菜单组业务逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author DingZhong
 * @version 1.0
 */
public class LDMenuGrpBL
{
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
//    private VData mResult = new VData();
    private VData mInputData = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    private LDMenuGrpToMenuSet mLDMenuGrpToMenuSet = new LDMenuGrpToMenuSet();
    private LDMenuGrpSchema mLDMenuGrpSchema = new LDMenuGrpSchema();
    private String mLDMenuGrpToMenuSetStr = "";
    private String mRemoveSetStr = "";
    private LDMenuGrpToMenuSet mRemoveSet = new LDMenuGrpToMenuSet();
    //业务处理相关变量

    public LDMenuGrpBL()
    {
    }

    //传输数据的公共方法
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
try{
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }
//        System.out.println("After getinputdata");
} catch(Exception e){
	e.printStackTrace();
}
        //进行业务处理
        if (!dealData())
        {
            return false;
        }
//        System.out.println("After dealData！");
        //准备往后台的数据
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

        //如果有需要处理的错误，则返回
        if (tLDMenuGrpBLS.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tLDMenuGrpBLS.mErrors);
            return false;
        }

        mInputData = null;
        return true;
    }

    //根据前面的输入数据，进行逻辑处理
    //如果在处理过程中出错，则返回false,否则返回true
    private static boolean dealData()
    {
    	System.out.println("开始处理数据");
        return true;

    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     * @param mInputData VData
     * @return boolean
     */
    private boolean getInputData(VData mInputData)
    {
        System.out.println("LDMenuGrpBL.java:从输入数据中得到所有对象start BL get inputdata...");//2005

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
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDMenuGrpBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "没有得到足够的数据，请您确认!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    //准备往后层输出所需要的数据
    //输出：如果准备数据时发生错误则返回false,否则返回true
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
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LDMenuGrpBL";
            tError.functionName = "prepareData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
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
