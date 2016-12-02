/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.menumang;

//import com.sinosoft.lis.schema.LDMenuSchema;
import com.sinosoft.utility.CErrors;
//import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;


/**
 * <p>Title: Webҵ��ϵͳ</p>
 * <p>Description:
 * �Ӵ����������̳У���������������,��ÿ�����ж�����
 * </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Sinosoft</p>
 * @author Fanym
 * @version 1.0
 */
public class LDMenuFunUI
{

    //ҵ������ر���
    private VData mInputData;
    public CErrors mErrors = new CErrors();

    public LDMenuFunUI()
    {
    }

    //�������ݵĹ�������
    public boolean submitData(VData cInputData, String cOperate)
    {
        //���Ƚ������ڱ�������һ������
        mInputData = (VData) cInputData.clone();
//        System.out.println("UI get inputdata...:");
        LDMenuFunBL tLDMenuFunBL = new LDMenuFunBL();
//        System.out.println("Start LDMenu UI Submit...");
        boolean tag = tLDMenuFunBL.submitData(mInputData, cOperate);
        if (!tag)
        {
            return false;
        }
//        System.out.println("End LDMenu UI Submit...");

        //�������Ҫ����Ĵ����򷵻�
        if (tLDMenuFunBL.mErrors.needDealError())
        {
            this.mErrors.copyAllErrors(tLDMenuFunBL.mErrors);
            return false;
        }
//        System.out.println("error num=" + mErrors.getErrorCount());
        mInputData = null;
        return true;
    }

    public static void main(String[] args)
    {
//        LDMenuFunUI tLDMenuFunUI = new LDMenuFunUI();
//        LDMenuSchema tLDMenuSchema = new LDMenuSchema();
//        tLDMenuSchema.setNodeName("aaaaa");
//        tLDMenuSchema.setRunScript("bbbbbb");
//        tLDMenuSchema.setChildFlag("0");
//        tLDMenuSchema.setParentNodeCode("1001");
//        VData tVData = new VData();
//        TransferData tTransferData = new TransferData();
//        tTransferData.setNameAndValue("ischild", "false");
//        tTransferData.setNameAndValue("NodeCode", "1010");
//        tVData.add(tLDMenuSchema);
//        tVData.add(tTransferData);
//        tLDMenuFunUI.submitData(tVData, "insert");
    }
}
