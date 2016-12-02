package com.sinosoft.midplat.util;

import com.sinosoft.utility.*;

/**
 * <p>
 * Title: �ϴ��������ļ����ʴ��ṩAPI��
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 20090807
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Wanghb
 * @version 1.0
 * @date 2009-08-07
 */

public class PostDataToPSBC {

	private VData mInputData = new VData();

	private String mRemoteFilePath = "";

	private String mRemoteFileName = "";

	private String mLocalFilePath = "";

	private String mLocalFileName = "";

	private String mOperate = "";

	private String mPostFtpIP = "";

	private int mPostFtpPort = 0;

	/** �ļ����䳬ʱʱ�� ��λ S */
	private int mPostFtpTimeOut = 60;

	private CErrors mErrors = new CErrors();

	public boolean submitData(VData cVData, String cOperate) {

		mInputData = cVData;
		mOperate = cOperate;

		if (!getInputData()) {

			return false;
		}

		if (!checkData()) {

			return false;
		}

		if (!dealData()) {

			return false;
		}

		return true;
	}

	private boolean getInputData() {

		TransferData tTransferData = (TransferData) mInputData
				.getObjectByObjectName("TransferData", 0);

		mRemoteFilePath = (String) tTransferData
				.getValueByName("RemoteFilePath");
		mRemoteFileName = (String) tTransferData
				.getValueByName("RemoteFileName");
		mLocalFilePath = (String) tTransferData.getValueByName("LocalFilePath");
		mLocalFileName = (String) tTransferData.getValueByName("LocalFileName");
		mPostFtpIP = (String) tTransferData.getValueByName("FileServerIP");
		mPostFtpPort = Integer.parseInt((String) tTransferData
				.getValueByName("FileServerPort"));
		mPostFtpTimeOut = Integer.parseInt((String) tTransferData
				.getValueByName("FileServerTimeOut"));

		System.out.println("RemoteFilePath:" + mRemoteFilePath);
		System.out.println("RemoteFileName:" + mRemoteFileName);
		System.out.println("LocalFilePath:" + mLocalFilePath);
		System.out.println("LocalFileName:" + mLocalFileName);
		System.out.println("PostFtpIP:" + mPostFtpIP);
		System.out.println("PostFtpPort:" + mPostFtpPort);
		System.out.println("PostFtpTimeOut:" + mPostFtpTimeOut);
		return true;
	}

	private boolean checkData() {

		if (mRemoteFilePath == null || mRemoteFilePath.equals("")) {
			System.out.println("Զ���ļ�·������Ϊ��");

			CError tError = new CError();
			tError.moduleName = "YBTPostGetContInfo";
			tError.functionName = "checkData";
			tError.errorMessage = "Զ���ļ�·������Ϊ��";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mRemoteFileName == null || mRemoteFileName.equals("")) {
			System.out.println("Զ���ļ����Ʋ���Ϊ��");
			CError tError = new CError();
			tError.moduleName = "YBTPostGetContInfo";
			tError.functionName = "checkData";
			tError.errorMessage = "Զ���ļ����Ʋ���Ϊ��";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLocalFilePath == null || mLocalFilePath.equals("")) {
			System.out.println("�����ļ�·������Ϊ��");
			CError tError = new CError();
			tError.moduleName = "YBTPostGetContInfo";
			tError.functionName = "checkData";
			tError.errorMessage = "�����ļ�·������Ϊ��";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLocalFileName == null || mLocalFileName.equals("")) {
			System.out.println("�����ļ����Ʋ���Ϊ��");
			CError tError = new CError();
			tError.moduleName = "YBTPostGetContInfo";
			tError.functionName = "checkData";
			tError.errorMessage = "�����ļ����Ʋ���Ϊ��";
			this.mErrors.addOneError(tError);
			return false;
		}
		System.out.println("PostDataToPSBC.checkData() ͨ��");
		return true;
	}

	private boolean dealData() {

		PSBCSocketClient fc = new PSBCSocketClient(mPostFtpIP, mPostFtpPort,
				mPostFtpTimeOut);

		PSBCFileTransCmd tFileTransCmd = new PSBCFileTransCmd();

		/* ���ʴ�ϵͳ�����ļ� */
		if (mOperate.equals("DownLoad")) {

			tFileTransCmd.setVs_outsys_code("9940001"); /* �������ϵͳ���룬�̶� */
			tFileTransCmd.setVs_req_flag("0"); /* �����־���̶� */
			tFileTransCmd.setVs_send_recv_flag("1"); /* ����ϵͳ�����ļ���־���̶� */
			tFileTransCmd.setVs_local_file_dir(mRemoteFilePath); /* ������ϵͳ�ļ�Ŀ¼��insu�̶���0001Ϊ�����չ�˾���� */
			tFileTransCmd.setVs_local_file_name(mRemoteFileName); /* �����ļ��� */
			tFileTransCmd.setVs_remote_file_dir(mLocalFilePath); /* ���ر��ر����ļ�·�� */
			tFileTransCmd.setVs_remote_file_name(mLocalFileName); /* �����ļ��� */

		} else if (mOperate.equals("UpLoad")) { /* ���ʴ�ϵͳ�ϴ��ļ� */

			tFileTransCmd.setVs_outsys_code("9940001"); /* �������ϵͳ���룬�̶� */
			tFileTransCmd.setVs_req_flag("0"); /* �����־���̶� */
			tFileTransCmd.setVs_send_recv_flag("0"); /* ����ϵͳ�����ļ���־,�̶� */
			tFileTransCmd.setVs_local_file_dir(mRemoteFilePath); /* ����ϵͳ�ļ�����Ŀ¼,insu�̶���0001Ϊ�����չ�˾���� */
			tFileTransCmd.setVs_local_file_name(mRemoteFileName); /* ����ϵͳ�����ļ��� */
			tFileTransCmd.setVs_remote_file_dir(mLocalFilePath); /* �ϴ��ļ�����·�� */
			tFileTransCmd.setVs_remote_file_name(mLocalFileName); /* �ϴ��ļ��� */
		}

		fc.createSocketClient(fc.ipAddress, fc.port);
		try {
			if (mOperate.equals("DownLoad")) {
				fc.recvFileFromHost(fc.clientSocket, tFileTransCmd);
			} else if (mOperate.equals("UpLoad")) {
				fc.sendFileToHost(fc.clientSocket, tFileTransCmd);
			}
		} catch (Exception e) {

			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "YBTPostGetContInfo";
			tError.functionName = "dealData";
			tError.errorMessage = e.getMessage();
			this.mErrors.addOneError(tError);

			return false;
		}
		fc.closeSocketClient(fc.clientSocket);

		return true;
	}

	public CErrors getError() {

		return this.mErrors;
	}

	public static void main(String[] args) {

	}
}
