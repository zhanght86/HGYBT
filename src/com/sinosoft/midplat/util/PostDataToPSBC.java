package com.sinosoft.midplat.util;

import com.sinosoft.utility.*;

/**
 * <p>
 * Title: 上传和下载文件（邮储提供API）
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

	/** 文件传输超时时间 单位 S */
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
			System.out.println("远程文件路径不能为空");

			CError tError = new CError();
			tError.moduleName = "YBTPostGetContInfo";
			tError.functionName = "checkData";
			tError.errorMessage = "远程文件路径不能为空";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mRemoteFileName == null || mRemoteFileName.equals("")) {
			System.out.println("远程文件名称不能为空");
			CError tError = new CError();
			tError.moduleName = "YBTPostGetContInfo";
			tError.functionName = "checkData";
			tError.errorMessage = "远程文件名称不能为空";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLocalFilePath == null || mLocalFilePath.equals("")) {
			System.out.println("本地文件路径不能为空");
			CError tError = new CError();
			tError.moduleName = "YBTPostGetContInfo";
			tError.functionName = "checkData";
			tError.errorMessage = "本地文件路径不能为空";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLocalFileName == null || mLocalFileName.equals("")) {
			System.out.println("本地文件名称不能为空");
			CError tError = new CError();
			tError.moduleName = "YBTPostGetContInfo";
			tError.functionName = "checkData";
			tError.errorMessage = "本地文件名称不能为空";
			this.mErrors.addOneError(tError);
			return false;
		}
		System.out.println("PostDataToPSBC.checkData() 通过");
		return true;
	}

	private boolean dealData() {

		PSBCSocketClient fc = new PSBCSocketClient(mPostFtpIP, mPostFtpPort,
				mPostFtpTimeOut);

		PSBCFileTransCmd tFileTransCmd = new PSBCFileTransCmd();

		/* 从邮储系统下载文件 */
		if (mOperate.equals("DownLoad")) {

			tFileTransCmd.setVs_outsys_code("9940001"); /* 请求接收系统代码，固定 */
			tFileTransCmd.setVs_req_flag("0"); /* 请求标志，固定 */
			tFileTransCmd.setVs_send_recv_flag("1"); /* 向保险系统接收文件标志，固定 */
			tFileTransCmd.setVs_local_file_dir(mRemoteFilePath); /* 请求保险系统文件目录，insu固定，0001为各保险公司代码 */
			tFileTransCmd.setVs_local_file_name(mRemoteFileName); /* 请求文件名 */
			tFileTransCmd.setVs_remote_file_dir(mLocalFilePath); /* 下载本地保存文件路径 */
			tFileTransCmd.setVs_remote_file_name(mLocalFileName); /* 本地文件名 */

		} else if (mOperate.equals("UpLoad")) { /* 向邮储系统上传文件 */

			tFileTransCmd.setVs_outsys_code("9940001"); /* 请求接收系统代码，固定 */
			tFileTransCmd.setVs_req_flag("0"); /* 请求标志，固定 */
			tFileTransCmd.setVs_send_recv_flag("0"); /* 向保险系统发送文件标志,固定 */
			tFileTransCmd.setVs_local_file_dir(mRemoteFilePath); /* 保险系统文件保存目录,insu固定，0001为各保险公司代码 */
			tFileTransCmd.setVs_local_file_name(mRemoteFileName); /* 保险系统接受文件名 */
			tFileTransCmd.setVs_remote_file_dir(mLocalFilePath); /* 上传文件本地路径 */
			tFileTransCmd.setVs_remote_file_name(mLocalFileName); /* 上传文件名 */
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
