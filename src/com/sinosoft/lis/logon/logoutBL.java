/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.logon;


import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;


/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description: ϵͳ�ǳ�������</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author dingzhong
 * @version 1.0
 */
public class logoutBL
{
    /** �������࣬ÿ����Ҫ����������ж����ø��� */
    public CErrors mErrors = new CErrors();

    /** �����洫�����ݵ����� */
    private VData mResult = new VData();

    /** ���ݲ����ַ��� */
//    private String mOperate;
//    private VData mInputData = new VData();


    /** ҵ������ر��� */
    /** �˵� */
//    private LDMenuSchema mLDMenuSchema = new LDMenuSchema();
//    private LDMenuSet mLDMenuSet = new LDMenuSet();

//    private LDSysTraceSchema mLDSysTraceSchema = new LDSysTraceSchema();
    private GlobalInput mGlobalInput = new GlobalInput();


    /** ���ص�����*/
//    private String mResultStr = "";
    public logoutBL()
    {}


    /**
     * �������ݵĹ�������
     * @param cInputData VData
     * @param cOperate String
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //���������ݿ�����������
//        this.mOperate = cOperate;

        //�õ��ⲿ���������,�����ݱ��ݵ�������
        if (!getInputData(cInputData))
        {
            return false;
        }

        if (!dealData())
        {
            return false;
        }

        //����ҵ����
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }


    /**
     * �����������еõ����ж���
     * ��������û�еõ��㹻��ҵ�����ݶ����򷵻�false,���򷵻�true
     * @param cInputData VData
     * @return boolean
     */
    private boolean getInputData(VData cInputData)
    {
        // ������ѯ����
//        System.out.println("start get input data");
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput", 0));
//        System.out.println("get one");
        if (mGlobalInput == null)
        {
            return false;
        }

 ///       mLDSysTraceSchema.setOperator(mGlobalInput.Operator);
  //      mLDSysTraceSchema.setPolState(1001);

        return true;
    }

    /**
     * ����׼��
     * @return boolean
     */
    private boolean dealData()
    {
 //       String strSql = "delete from LDSYSTrace where Operator = '" +
  //                      mLDSysTraceSchema.getOperator()
   //                     + "' and PolState = 1001";
//        System.out.println(strSql);
  //      LDSysTraceDB tLDSYSTraceDB = new LDSysTraceDB();
//        tLDSYSTraceDB.executeQuery(strSql);
        return true;
    }
}
