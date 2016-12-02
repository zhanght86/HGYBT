package com.sinosoft.midplat.common;

public interface ICCodeDef extends CodeDef {
	/*IC������*/
	String ICOpen = "7010";
	
	/*IC����ʼ��*/
	String ICInit = "7000";  
	
	/*IC������*/
	String ICActive = "7001";
	
	/*IC��״̬���*/
	String ICStateChange = "7013";
	
	/*IC������*/
	String 	ICChange = "7002";
	
	/*���շ�����*/
	String ICClosed = "7019";
	 	 
	/*IC��״̬����ļ�(����)*/ 	
	String ICBlcStateChange = "7101";
	
	/*�����ļ�(����)*/
	String ICBlcChange = "7102";	
}
