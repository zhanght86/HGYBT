package com.sinosoft.midplat.newabc.bat;
 
 import com.sinosoft.midplat.MidplatConf;
 import com.sinosoft.midplat.common.DateUtil;
 import com.sinosoft.midplat.common.IOTrans;
 import com.sinosoft.midplat.common.NoFactory;
 import com.sinosoft.midplat.common.XmlConf;
 import com.sinosoft.midplat.common.XmlTag;
 import com.sinosoft.midplat.exception.MidplatException;
 import com.sinosoft.midplat.service.Service;
 import java.io.BufferedReader;
 import java.io.ByteArrayInputStream;
 import java.io.ByteArrayOutputStream;
 import java.io.File;
 import java.io.FileFilter;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.lang.reflect.Constructor;
 import java.net.SocketTimeoutException;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.TimerTask;
 import org.apache.commons.net.ftp.FTPClient;
 import org.apache.commons.net.ftp.FTPReply;
 import org.apache.log4j.Logger;
 import org.jdom.Document;
 import org.jdom.Element;
import org.jdom.xpath.XPath;
 
/**
 * @ClassName: Balance
 * @Description: 
 * @author sinosoft
 * @date 2017-2-27 ����10:49:19
 */
 public abstract class Balance extends TimerTask
   implements XmlTag
 {
	 //����һ���������־����
     protected final Logger cLogger = Logger.getLogger(getClass());
   //��ǰ���н��������ļ�
   protected final XmlConf cThisConf;
   //������
   protected final int cFuncFlag;
   //��������
   protected Date cTranDate;
   //������Ϣ
   protected String cResultMsg;
   	  //�м�ƽ̨�����ļ����ڵ�
      protected Element cMidplatRoot = null;
      //��ǰ���н��������ļ����ڵ�
      protected Element cThisConfRoot = null;
      //��ǰ�������ýڵ�
      protected Element cThisBusiConf = null;
 
    /**
     * <p>Title: </p>
     * <p>Description: </p>
     * @param pThisConf ��ǰ���н��������ļ�
     * @param pFuncFlag ������(�ַ���)
     */
   public Balance(XmlConf pThisConf, String pFuncFlag) {
        this(pThisConf, Integer.parseInt(pFuncFlag));
   }
 
   /**
    * <p>Title: </p>
    * <p>Description: </p>
    * @param pThisConf ��ǰ���н��������ļ�
    * @param pFuncFlag ������(��ֵ)
    */
   public Balance(XmlConf pThisConf, int pFuncFlag) {
        this.cThisConf = pThisConf;
        this.cFuncFlag = pFuncFlag;
   }
 
   /**
    * ����
    */
   public void run()
   {
	   //���õ�ǰ�߳���[��һ��������־��]
        Thread.currentThread().setName(
          String.valueOf(NoFactory.nextTranLogNo()));
        //��ʼBalanceִ��...
        this.cLogger.info("Into Balance.run()...");
        //������Ϣ��Ϊ��
        this.cResultMsg = null;
     try
     {
    	 //��ȡ�м�ƽ̨�����ļ����ڵ�
         this.cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
         //��ȡ��ǰ���н��������ļ����ڵ�
         this.cThisConfRoot = this.cThisConf.getConf().getRootElement();
         //��ȡ��ǰ�������ýڵ�[�ڵ�ǰ���н��������ļ����ڵ���,���ݽ�����Ϊ��ǰ������ѡ�񵥸��ڵ�]
         this.cThisBusiConf = ((Element)XPath.selectSingleNode(
           this.cThisConfRoot, "business[funcFlag='" + this.cFuncFlag + "']"));
         //��ȡ��һ������[��ǰ�������ýڵ��ȡnextDate�ӽڵ��ı�]
         String nextDate = this.cThisBusiConf.getChildText("nextDate");
         //��������Ϊ��
         if (this.cTranDate == null)
           //��һ�����ڷǿ���ΪY
           if ((nextDate != null) && ("Y".equals(nextDate))) {
        	 //��������Ϊ��ǰ���ڶ���
             this.cTranDate = new Date();
             //��������Ϊ����
             this.cTranDate = new Date(this.cTranDate.getTime() - 86400000L);
      } else {
    	  	 //��������Ϊ��ǰ���ڶ���
             this.cTranDate = new Date();
      }
         //�½����ڵ�
         Element tTranData = new Element("TranData");
         //�½���׼���뱨��
         Document tInStdXml = new Document(tTranData);
         //��ȡ����ͷ�ڵ�
         Element tHeadEle = getHead();
         //���ڵ���뱨��ͷ�ڵ�
         tTranData.addContent(tHeadEle);
         //����������
         String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";
         //������ֵ[��ȡ�м�ƽ̨�����ļ����ڵ���service�ӽڵ��ı�:com.sinosoft.midplat.service.SimpService]
         String tServiceValue = this.cMidplatRoot.getChildText("service");
         //������ֵ�ǿա����ַ���
         if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
        	 //������ֵ��ֵ������������
           tServiceClassName = tServiceValue;
         }
         //������ֵ[��ȡ��ǰ���н��������ļ����ڵ���service�ӽڵ��ı�:��]
         tServiceValue = this.cThisConfRoot.getChildText("service");
         //������ֵ�ǿա����ַ���
         if ((tServiceValue != null) && (!"".equals(tServiceValue))) {//Ϊ�գ���ִ�д˴����
           //������ֵ��ֵ������������
           tServiceClassName = tServiceValue;
         }
         //������ֵ[��ȡ��ǰ�������ýڵ���service�ӽڵ��ı�:�з��ظ�ֵ,�޷��ؿ�]
         tServiceValue = this.cThisBusiConf.getChildText("service");
         //������ֵ�ǿա����ַ���
         if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
        	//������ֵ��ֵ������������
           tServiceClassName = tServiceValue;
         }
         //ҵ����ģ��+����������
         this.cLogger.info("ҵ����ģ��" + tServiceClassName);
         //����һ�����������󣬷�ӳ�������������������ʾ�����ָ���������췽��
         Constructor tServiceConstructor = Class.forName(
           tServiceClassName).getConstructor(new Class[] { Element.class });
         //ʹ�ô˹����������ʾ�Ĺ��췽���������ù��췽�������������ʵ�������õ�ǰ�������ýڵ��ʼ����ʵ��
         Service tService = (Service)tServiceConstructor.newInstance(new Object[] { this.cThisBusiConf });
         //ҵ�����ദ���׼���뱨�ķ��ر�׼�������
         Document tOutStdXml = tService.service(tInStdXml);
         //��ȡ������Ϣ[��׼������ĸ�����±���ͷ�ڵ��½��׽�������ӽڵ��ı�]
         this.cResultMsg = tOutStdXml.getRootElement().getChild(
           "Head").getChildText("Desc");
         //����[��׼�������]
         ending(tOutStdXml);
         //��������Ϊÿ�µĵ�һ��
         if ("01".equals(DateUtil.getDateStr(this.cTranDate, "dd")))
           //�����ļ���[��ǰ�������ýڵ���localDir�ӽڵ��ı�]Ŀ¼
           bakFiles(this.cThisBusiConf.getChildTextTrim("localDir"));
  }
  catch (Throwable ex) {
	  	 //����:���׳���
         this.cLogger.error("���׳���", ex);
         //������Ϣ��ֵΪ�쳣�������
         this.cResultMsg = ex.toString();
  }
       //����������Ϊ��
       this.cTranDate = null;
       //��Balanceִ�з�������!
       this.cLogger.info("Out Balance.run()!");
   }
 
   /**
    * @Title: getHead
    * @Description: ��ȡ����ͷ
    * @return ����ͷ�ڵ�
    * @throws MidplatException
    * @return Element
    * @throws
    */
   protected Element getHead()throws MidplatException {
	   //����Balance��ȡ����ͷ����...
       this.cLogger.info("Into Balance.getHead()...");
 
       Element mTranDate = new Element("TranDate");
       mTranDate.setText(DateUtil.getDateStr(this.cTranDate, "yyyyMMdd"));
 
       Element mTranTime = new Element("TranTime");
       mTranTime.setText(DateUtil.getDateStr(this.cTranDate, "HHmmss"));
 
       Element mTranCom = (Element)this.cThisConfRoot.getChild("TranCom").clone();
 
       Element mNodeNo = (Element)this.cThisBusiConf.getChild("NodeNo").clone();
 
       Element mTellerNo = new Element("TellerNo");
       mTellerNo.setText("sys");
 
       Element mTranNo = new Element("TranNo");
       try {
		  mTranNo.setText(getFileName());
	   } catch (Exception e) {
		throw new  MidplatException(e.getMessage()); 
	  }
 
       Element mFuncFlag = new Element("FuncFlag");
       mFuncFlag.setText(this.cThisBusiConf.getChildText("funcFlag"));
 
       Element mHead = new Element("Head");
       mHead.addContent(mTranDate);
       mHead.addContent(mTranTime);
       mHead.addContent(mTranCom);
       mHead.addContent(mNodeNo);
       mHead.addContent(mTellerNo);
       mHead.addContent(mTranNo);
       mHead.addContent(mFuncFlag);
 
       this.cLogger.info("Out Balance.getHead()!");
       return mHead;
   }
 
   	/**
   	 * @Title: parse
   	 * @Description: ����
   	 * @param pBatIs
   	 * @return
   	 * @throws Exception
   	 * @return Element
   	 * @throws
   	 */
     protected Element parse(InputStream pBatIs) throws Exception {
       this.cLogger.info("Into Balance.parse()...");

       String mCharset = this.cThisBusiConf.getChildText("charset");
       if ((mCharset == null) || ("".equals(mCharset))) {
         mCharset = "GBK";
       }

       BufferedReader mBufReader = new BufferedReader(
         new InputStreamReader(pBatIs, mCharset));

       Element mBodyEle = new Element("Body");
       String tLineMsg;
       while ((tLineMsg = mBufReader.readLine()) != null) {
         this.cLogger.info(tLineMsg);

         tLineMsg = tLineMsg.trim();
         if ("".equals(tLineMsg)) {
           this.cLogger.warn("���У�ֱ��������������һ����");
         }
         else
         {
           String[] tSubMsgs = tLineMsg.split("\\|", -1);

           Element tTranDate = new Element("TranDate");
           tTranDate.setText(tSubMsgs[1]);

           Element tNodeNo = new Element("NodeNo");
           tNodeNo.setText(tSubMsgs[3]);

           Element tTranNo = new Element("TranNo");
           tTranNo.setText(tSubMsgs[5]);

           Element tContNo = new Element("ContNo");
           tContNo.setText(tSubMsgs[6]);

           Element tPrem = new Element("Prem");
           tPrem.setText(tSubMsgs[7]);

           Element tDetail = new Element("Detail");
           tDetail.addContent(tTranDate);
           tDetail.addContent(tNodeNo);
           tDetail.addContent(tTranNo);
           tDetail.addContent(tContNo);
           tDetail.addContent(tPrem);

           mBodyEle.addContent(tDetail);
         }
       }
       mBufReader.close();

       this.cLogger.info("Out Balance.parse()!");
       return mBodyEle;
     }

     /**
      * @Title: bakFiles
      * @Description:  �����ļ�
      * @param pFileDir �ļ�Ŀ¼
      * @return void
      * @throws
      */
     private void bakFiles(String pFileDir) {
    	//����Balance�����ļ�����...
       this.cLogger.info("Into Balance.bakFiles()...");
       //�ļ�Ŀ¼Ϊ�ա����ַ�
       if ((pFileDir == null) || ("".equals(pFileDir))) {
    	 //����[�����ļ�Ŀ¼Ϊ�գ������б��ݲ�����]
         this.cLogger.warn("�����ļ�Ŀ¼Ϊ�գ������б��ݲ�����");
         //�˳��ó��������
         return;
       }
       //�½�Ŀ¼�ļ�����
       File mDirFile = new File(pFileDir);
       //Ŀ¼�ļ����󲻴��ڡ�����Ŀ¼
       if ((!mDirFile.exists()) || (!mDirFile.isDirectory())) {
    	 //�����ļ�Ŀ¼�����ڣ������б��ݲ�����+Ŀ¼�ļ�����
         this.cLogger.warn("�����ļ�Ŀ¼�����ڣ������б��ݲ�����" + mDirFile);
         //�˳��ó��������
         return;
       }
       //���ļ�[���س���·�������飬��Щ·������ʾ�˳���·������ʾ��Ŀ¼������ָ�����������ļ���Ŀ¼]
       File[] mOldFiles = mDirFile.listFiles(
         new FileFilter() {
         public boolean accept(File pFile) {
           if (!pFile.isFile()) {
             return false;
           }

           Calendar tCurCalendar = Calendar.getInstance();
           tCurCalendar.setTime(Balance.this.cTranDate);
           tCurCalendar.set(11, 8);

           Calendar tFileCalendar = Calendar.getInstance();
           tFileCalendar.setTimeInMillis(pFile.lastModified());

           return tFileCalendar.before(tCurCalendar);
         }
       });
       Calendar mCalendar = Calendar.getInstance();
       mCalendar.add(2, -1);
       File mNewDir = 
         new File(mDirFile, DateUtil.getDateStr(mCalendar, "yyyy/yyyyMM"));
       for (File tFile : mOldFiles) {
         try {
				String fileName_date=tFile.getName().substring(tFile.getName().length()-8);
				Date date=new Date();
				if(!fileName_date.equals(String.valueOf(DateUtil.get8Date(date)))){
					cLogger.info(tFile.getAbsoluteFile() + " start move...");
					IOTrans.fileMove(tFile, mNewDir);
					cLogger.info(tFile.getAbsoluteFile() + " end move!");
				}
         } catch (IOException ex) {
           this.cLogger.error(tFile.getAbsoluteFile() + "����ʧ�ܣ�", ex);
         }
       }

       this.cLogger.info("Out Balance.bakFiles()!");
     }

     public final void setDate(Date pDate) {
       this.cTranDate = pDate;
     }

     public final void setDate(String p8DateStr) {
       this.cTranDate = DateUtil.parseDate(p8DateStr, "yyyyMMdd");
     }

     protected abstract String getFileName()throws Exception;
     
     /**
      * @Title: ending ����
      * @Description: 
      * @param pOutStdXml ��׼�������
      * @throws Exception �쳣
      * @return void
      * @throws
      */
     protected void ending(Document pOutStdXml)
       throws Exception
     {
    	//����Balance��������...
       this.cLogger.info("Into Balance.ending()...");
       //ʲô��������ֻ�ǳ��� 
       this.cLogger.info("do nothing, just out!");
       //��Balance������������!
       this.cLogger.info("Out Balance.ending()!");
     }

     public String getResultMsg() {
       return this.cResultMsg;
     }
   }
