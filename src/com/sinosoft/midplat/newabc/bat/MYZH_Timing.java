//���ջ������е�  ��ԥ�����˱����� ���޸�
//xiaowq 20120827
package com.sinosoft.midplat.newabc.bat;

import org.apache.log4j.Logger;

import com.sinosoft.midplat.newabc.NewAbcConf;


/**
 * ��Կ���ý���
 * @author chenjin
 *
 */
public class MYZH_Timing extends MYZH_TimingImpl{
	private static Logger cLogger = Logger.getLogger(MYZH_Timing.class);
	
	public MYZH_Timing() {
		super(NewAbcConf.newInstance(), "1001");	//pgwt_blnc ��Ӧbc.xml��������ԥ�����˱����˵�funcflag
	}//SpdbConf.newInstance() ��Ӧbc.xml
	public static void main(String[] args){
		
		cLogger.info("��ʼ��Կ�������ݴ��� ...");	//û�����ã�δ��ӡ
		
		MYZH_Timing pt = new MYZH_Timing();	//û������
		pt.run();	//û������
//			pt.makeLocalBalanceFile("D:\\file\\", "abc.txt", "1234567890");
		
		/*
		//��������
		String fileName = pt.getFileName();
		String localPath = PgwtTiming.class.getResource("/").getFile();
		File file=new File(localPath+fileName);
		mLogger.info("�ļ�����"+file.getName());
		mLogger.info("�ļ�·����"+file.getAbsolutePath());
		if(file.exists())			
		{	
			mLogger.info("file.exists()=="+file.exists());
			boolean delFlag = file.delete();
			mLogger.info("ɾ�����ر��ݵ��̳������ļ��ɹ�����"+delFlag);
		}*/
		
		cLogger.info("������Կ���� ...");	//û�����ã�δ��ӡ
	}
	
}
