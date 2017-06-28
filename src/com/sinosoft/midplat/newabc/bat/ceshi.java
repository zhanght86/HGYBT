package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ContBlcDtlSchema;
import com.sinosoft.lis.vschema.ContBlcDtlSet;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.utility.VData;

/**
 * @ClassName: ceshi
 * @Description: �����ϴ�����10M���ļ�
 * @author sinosoft
 * @date 2017-4-7 ����12:07:19
 */
public class ceshi extends TimerTask implements XmlTag {

	protected final Logger cLogger = Logger.getLogger(getClass());

	protected String cResultMsg;
	protected static Element cConfigEle;
	private static String cCurDate = "";
	@SuppressWarnings("unused")
	private static String cCurTime="";

	public void run() {
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into ceshi.run()...");
		try {
			cResultMsg = null;
			cConfigEle = BatUtils.getConfigEle("2010"); // �õ�bat.xml�ļ��еĶ�Ӧ�ڵ�.
			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
			}

			cLogger.info("�����ϴ��ļ���ʼ...");
			String mFIleName = cConfigEle.getChildText("filename").trim(); 
			if(!new BatUtils().upLoadFile("/home/weblogic/ybttest/newabc/ceshi/"+mFIleName, "02","2010",cCurDate,mFIleName)){
				throw new MidplatException(cConfigEle.getChildText(name)+"�ϴ�ʧ�ܣ�");
			}
			cResultMsg = "�ϴ��ɹ�";
		} catch (Exception e) {
			cResultMsg = e.toString();
			cLogger.error(cConfigEle.getChildTextTrim("name") + "  �����쳣..."+ e.getMessage());
			e.printStackTrace();
		} finally {
			cCurDate = "";
		}
		
		cLogger.info("�����ϴ��ļ�����������");
		cLogger.info("Out ceshi.run()!");
	}

	
	public final void setDate(String p8DateStr){
		cCurDate = p8DateStr; 
	}
	
	public String getResultMsg() {
		return this.cResultMsg;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.newabc.bat.ceshi.main");
		mLogger.info("��ũ�в����ϴ�10M�ļ���������...");

		ceshi abcAES = new ceshi();

		// ���ڲ����ˣ����ò���������
		if (0 != args.length) {
			mLogger.info("args[0] = " + args[0]);

			/**
			 * �ϸ�����У���������ʽ��\\d{4}-((0\\d)|(1[012]))-(([012]\\d)|(3[01]))��
			 * 4λ��-2λ��-2λ�ա� 4λ�꣺4λ[0-9]�����֡�
			 * 1��2λ�£�������Ϊ0��[0-9]�����֣�˫���±�����1��ͷ��β��Ϊ0��1��2������֮һ��
			 * 1��2λ�գ���0��1��2��ͷ��[0-9]�����֣�������3��ͷ��0��1��
			 * 
			 * ������У���������ʽ��\\d{4}-\\d{2}-\\d{2}��
			 */
			if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))")) {
				cCurDate = args[0];
			} else {
				throw new MidplatException("���ڸ�ʽ����ӦΪyyyyMMdd��" + args[0]);
			}
		}
		abcAES.run();
		System.out.println("��ũ�в����ϴ�10M�ļ��������!");
	}

}
