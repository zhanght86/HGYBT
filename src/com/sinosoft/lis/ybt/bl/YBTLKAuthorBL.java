/*
 * <p>ClassName: LKCodeBL </p>
 * <p>Description: LKCodeBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 银行网点机构
 * @CreateDate：2005-08-30
 */
package com.sinosoft.lis.ybt.bl;


import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.ExeSQL;
import java.math.BigInteger;
import com.sinosoft.utility.TransferData;

public class YBTLKAuthorBL
{
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    /** 往后面传输数据的容器 */
    private VData mInputData;
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    /** 数据操作字符串 */
    private String mOperate;
    /** 业务处理相关变量 */
    private LKTransAuthorizationSchema mLKTransAuthorizationSchema = new LKTransAuthorizationSchema();
    private LKTransAuthorizationSet mLKTransAuthorizationSet = new LKTransAuthorizationSet();
    private TransferData mTransferData = null;
    private MMap map = new MMap();

    public YBTLKAuthorBL()
    {}

    public static void main(String[] args)
    {

    }

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
        System.out.println(mOperate + "_ " + cOperate);
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }


        if (!dealData())
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "YBTLKCodeBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据处理失败YBTLKCodeBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }
        mInputData = null;
        return true;
    }




    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
        String currentTime = PubFun.getCurrentTime();
        
        if (this.mOperate.equals("INSERT||MAIN"))
        { 
           /* mLKTransAuthorizationSchema.setBankCode((String)mTransferData.getValueByName("BankCode"));
            mLKTransAuthorizationSchema.setBankBranch((String)mTransferData.getValueByName("BankBranch"));
            mLKTransAuthorizationSchema.setBankNode((String)mTransferData.getValueByName("BankNode"));
            mLKTransAuthorizationSchema.setManageCom((String)mTransferData.getValueByName("ManageCom"));
            mLKTransAuthorizationSchema.setFuncFlag((String)mTransferData.getValueByName("FuncFlag"));
			mLKTransAuthorizationSchema.setOperator(mGlobalInput.Operator);
             if (mLKTransAuthorizationSchema.getBankNode()==null || mLKTransAuthorizationSchema.getBankNode().trim().equals(""))
			 {
				 mLKTransAuthorizationSchema.setBankNode("***");
			 }
			 if(mLKTransAuthorizationSchema.getBankBranch()==null || mLKTransAuthorizationSchema.getBankBranch().trim().equals(""))
			 {
				 mLKTransAuthorizationSchema.setBankBranch("***");
			 }

            this.mLKTransAuthorizationSchema.setMakeDate(DateUtil.getCur8Date());
            this.mLKTransAuthorizationSchema.setMakeTime(currentTime);
            this.mLKTransAuthorizationSchema.setModifyDate(DateUtil.getCur8Date()); 
            this.mLKTransAuthorizationSchema.setModifyTime(currentTime);
            map.put(mLKTransAuthorizationSchema,"INSERT");*/
        }

        else if (this.mOperate.equals("DEL||MAIN"))
        {
            String BankCode=(String)mTransferData.getValueByName("BankCode");
           String BankBranch=(String)mTransferData.getValueByName("BankBranch");
           String BankNode=(String)mTransferData.getValueByName("BankNode");
		   String ManageCom = (String)mTransferData.getValueByName("ManageCom");
		   String FuncFlag = (String)mTransferData.getValueByName("FuncFlag");
        if(BankNode == null || BankNode.trim().equals("") )
		{
			BankNode = "***";
		}
		if(BankBranch == null || BankBranch.trim().equals(""))
		{
			BankBranch = "***";
		}

            String sql= "DELETE from LKTransAuthorization  where BankCode='"+BankCode.trim()+"' and  bankbranch='"+BankBranch.trim()+"' and BankNode='"+BankNode.trim()+"' and managecom ='"+ManageCom.trim()+"' and funcflag = '" + FuncFlag + "'" ;

            map.put(sql,"DELETE");
        }


        PubSubmit tSubmit = new PubSubmit();
        VData tInputData = new VData();
        tInputData.add(map);
        if (!tSubmit.submitData(tInputData, ""))
        {
            this.mErrors.copyAllErrors(tSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "YBTLKCodeBL";
            tError.functionName = "dealData";
            tError.errorMessage = "数据提交失败。";
            this.mErrors.addOneError(tError);
            return false;
        }


        return true;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        //全局变量
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
        this.mTransferData=(TransferData) cInputData.getObjectByObjectName("TransferData", 0);

        if (mGlobalInput == null||mTransferData==null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "YBTLKCodeBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "没得到足够的信息！";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;

    }

    /**
     * 准备往后层输出所需要的数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     */

    private boolean prepareOutputData()
    {
        try
        {
            this.mInputData = new VData();
            this.mInputData.add(mLKTransAuthorizationSchema);
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "YBTLKCodeBL";
            tError.functionName = "prrea";
            tError.errorMessage = "在准往后层处理所需要的数据时出错";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public VData getResult()
    {
        mResult = new VData();
        mResult.add(mLKTransAuthorizationSchema);
        return this.mResult;
    }

}
