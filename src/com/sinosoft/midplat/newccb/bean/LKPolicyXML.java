/**
 * $RCSfile: LKPolicyXML.java
 * $Revision: 1.0
 * $Date: 2015-4-12
 *
 * Copyright (C) 2010 SinoSoft, Inc. All rights reserved.
 *
 * This software is the proprietary information of SinoSoft, Inc.
 * Use is subject to license terms.
 */
package com.sinosoft.midplat.newccb.bean;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>Title: zhybt</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: SinoSoft</p>
 *
 * @author apple
 * @version 1.0
 */
public class LKPolicyXML
{
	/** ������ */
	private String contNo;
	/** Ͷ������ */
	private String proposalPrtNo;
	/** ���л������� */
	private String tranCom;
	/** ������ */
	private String zoneNo;
	/** ������� */
	private String nodeNo;
	/** �������� */
	private Date tranDate;
	/** �������� */
	private byte[] xmlContent;
	/** ������� */
	private Date makeDate;
	/** ���ʱ�� */
	private String makeTime;
	/** �޸����� */
	private Date modifyDate;
	/** �޸�ʱ�� */
	private String modifyTime;
	
	
	/**
	 * ȡ��contNo
	 * 
	 * @return String contNo 
	 */
	public String getContNo()
	{
		if(StringUtils.isEmpty(contNo))
		{
			contNo = "";
		}
		return contNo;
	}
	/**
	 * �趨contNo 
	 * 
	 * @param contNo 
	 */
	public void setContNo(String contNo)
	{
		this.contNo = contNo;
	}
	/**
	 * ȡ��proposalPrtNo
	 * 
	 * @return String proposalPrtNo 
	 */
	public String getProposalPrtNo()
	{
		if(StringUtils.isEmpty(proposalPrtNo))
		{
			proposalPrtNo = "";
		}
		return proposalPrtNo;
	}
	/**
	 * �趨proposalPrtNo 
	 * 
	 * @param proposalPrtNo 
	 */
	public void setProposalPrtNo(String proposalPrtNo)
	{
		this.proposalPrtNo = proposalPrtNo;
	}
	/**
	 * ȡ��tranCom
	 * 
	 * @return String tranCom 
	 */
	public String getTranCom()
	{
		if(StringUtils.isEmpty(tranCom))
		{
			tranCom = "";
		}
		return tranCom;
	}
	/**
	 * �趨tranCom 
	 * 
	 * @param tranCom 
	 */
	public void setTranCom(String tranCom)
	{
		this.tranCom = tranCom;
	}
	/**
	 * ȡ��zoneNo
	 * 
	 * @return String zoneNo 
	 */
	public String getZoneNo()
	{
		if(StringUtils.isEmpty(zoneNo))
		{
			zoneNo = "";
		}
		return zoneNo;
	}
	/**
	 * �趨zoneNo 
	 * 
	 * @param zoneNo 
	 */
	public void setZoneNo(String zoneNo)
	{
		this.zoneNo = zoneNo;
	}
	/**
	 * ȡ��nodeNo
	 * 
	 * @return String nodeNo 
	 */
	public String getNodeNo()
	{
		if(StringUtils.isEmpty(nodeNo))
		{
			nodeNo = "";
		}
		return nodeNo;
	}
	/**
	 * �趨nodeNo 
	 * 
	 * @param nodeNo 
	 */
	public void setNodeNo(String nodeNo)
	{
		this.nodeNo = nodeNo;
	}
	/**
	 * ȡ��tranDate
	 * 
	 * @return Date tranDate 
	 */
	public Date getTranDate()
	{
		return tranDate;
	}
	/**
	 * �趨tranDate 
	 * 
	 * @param tranDate 
	 */
	public void setTranDate(Date tranDate)
	{
		this.tranDate = tranDate;
	}
	/**
	 * ȡ��xmlContent
	 * 
	 * @return byte xmlContent 
	 */
	public byte[] getXmlContent()
	{
		return xmlContent;
	}
	/**
	 * �趨xmlContent 
	 * 
	 * @param xmlContent 
	 */
	public void setXmlContent(byte xmlContent[])
	{
		this.xmlContent = xmlContent;
	}
	/**
	 * ȡ��makeDate
	 * 
	 * @return Date makeDate 
	 */
	public Date getMakeDate()
	{
		return makeDate;
	}
	/**
	 * �趨makeDate 
	 * 
	 * @param makeDate 
	 */
	public void setMakeDate(Date makeDate)
	{
		this.makeDate = makeDate;
	}
	/**
	 * ȡ��makeTime
	 * 
	 * @return String makeTime 
	 */
	public String getMakeTime()
	{
		if(StringUtils.isEmpty(makeTime))
		{
			makeTime = "";
		}
		return makeTime;
	}
	/**
	 * �趨makeTime 
	 * 
	 * @param makeTime 
	 */
	public void setMakeTime(String makeTime)
	{
		this.makeTime = makeTime;
	}
	/**
	 * ȡ��modifyDate
	 * 
	 * @return Date modifyDate 
	 */
	public Date getModifyDate()
	{
		return modifyDate;
	}
	/**
	 * �趨modifyDate 
	 * 
	 * @param modifyDate 
	 */
	public void setModifyDate(Date modifyDate)
	{
		this.modifyDate = modifyDate;
	}
	/**
	 * ȡ��modifyTime
	 * 
	 * @return String modifyTime 
	 */
	public String getModifyTime()
	{
		if(StringUtils.isEmpty(modifyTime))
		{
			modifyTime = "";
		}
		return modifyTime;
	}
	/**
	 * �趨modifyTime 
	 * 
	 * @param modifyTime 
	 */
	public void setModifyTime(String modifyTime)
	{
		this.modifyTime = modifyTime;
	}
}
