/**
 * 中行日终业务对账
 */

package com.sinosoft.midplat.boc.bat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.sinosoft.midplat.boc.BocConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.boc.bat.Balance;

public class BocDownBusiBlc extends Balance {
	public BocDownBusiBlc() {
		super(BocConf.newInstance(), "802");
	}
	
	protected String getFileName() {
		Element mBankEle = cThisConfRoot.getChild("bank");
		return "RZDZ"+mBankEle.getAttributeValue("insu")+"BOC"+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+".txt";
	}
	
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into BocDownBusiBlc.parse()...");
		
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));
		Element mBodyEle = new Element(Body);
		Element mCountEle = new Element(Count);
		Element mPremEle = new Element(Prem);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mPremEle);

		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			
			//空行，直接跳过
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}
			int number = tLineMsg.length();
			cLogger.info("此行长度为:"+number);
			if(number<60)
			{
				mCountEle.setText(tLineMsg.substring(46,54));
				mPremEle.setText(tLineMsg.substring(33,46));
				cLogger.info("成功总数:"+tLineMsg.substring(46,54));
				cLogger.info("成功总保费:"+tLineMsg.substring(33,46));
			}
			if(number>60){
				if(number==157)
				{
					String type =  tLineMsg.substring(154,156);
					cLogger.info("对账类型:"+type);
					if(type.equals("01"))
					{
					    String state = tLineMsg.substring(156,157);
					    cLogger.info("状态:"+state);
					    if(state.equals("W"))
					    {
					     	continue;
					    }
					    if(state.equals("S"))
					    {
						    Element tTranNoEle = new Element(TranNo);
						    tTranNoEle.setText(tLineMsg.substring(0, 16));
						    Element tNodeNoEle = new Element(NodeNo);
						    tNodeNoEle.setText(tLineMsg.substring(16, 25));
							Element tTranDateEle = new Element(TranDate);
							tTranDateEle.setText(tLineMsg.substring(25, 33));
							Element tContNoEle = new Element(ContNo);
							tContNoEle.setText(tLineMsg.substring(111, 141).trim());
							Element tPremEle = new Element(Prem);
							tPremEle.setText(tLineMsg.substring(141, 154));
							cLogger.info("输出"+tLineMsg.substring(0,16));
							cLogger.info("输出"+tLineMsg.substring(16,25));
							cLogger.info("输出"+tLineMsg.substring(25,33));
							cLogger.info("输出"+tLineMsg.substring(111,141).trim());
							cLogger.info("输出"+tLineMsg.substring(141,154));

							Element tDetailEle = new Element(Detail);
							tDetailEle.addContent(tTranDateEle);
							tDetailEle.addContent(tNodeNoEle);
							tDetailEle.addContent(tTranNoEle);
							tDetailEle.addContent(tContNoEle);
							tDetailEle.addContent(tPremEle);
						
							mBodyEle.addContent(tDetailEle);
							JdomUtil.print(mBodyEle);
					    }
					}
					if(type.equals("02"))
					{
						Element tTranNoEle = new Element(TranNo);
						tTranNoEle.setText(tLineMsg.substring(0, 16));
						Element tNodeNoEle = new Element(NodeNo);
						tNodeNoEle.setText(tLineMsg.substring(16, 25));
						Element tTranDateEle = new Element(TranDate);
						tTranDateEle.setText(tLineMsg.substring(25, 33));
						Element tContNoEle = new Element(ContNo);
						tContNoEle.setText(tLineMsg.substring(112, 142).trim());
						Element tPremEle = new Element(Prem);
						tPremEle.setText(tLineMsg.substring(142, 155));
						cLogger.info("输出"+tLineMsg.substring(0,16));
						cLogger.info("输出"+tLineMsg.substring(16,25));
						cLogger.info("输出"+tLineMsg.substring(25,33));
						cLogger.info("输出"+tLineMsg.substring(112,142).trim());
						cLogger.info("输出"+tLineMsg.substring(142,155));

						Element tDetailEle = new Element(Detail);
						tDetailEle.addContent(tTranDateEle);
						tDetailEle.addContent(tNodeNoEle);
						tDetailEle.addContent(tTranNoEle);
						tDetailEle.addContent(tContNoEle);
						tDetailEle.addContent(tPremEle);
						
						mBodyEle.addContent(tDetailEle);
						JdomUtil.print(mBodyEle);
					}
				}
				if(number==156)
				{
					String type =  tLineMsg.substring(154,156);
					cLogger.info("对账类型:"+type);
					if(type.equals("1W"))
					{
						continue;
					}
					if(type.equals("1S"))
					{
						Element tTranNoEle = new Element(TranNo);
						tTranNoEle.setText(tLineMsg.substring(0, 16));
						Element tNodeNoEle = new Element(NodeNo);
						tNodeNoEle.setText(tLineMsg.substring(16, 25));
						Element tTranDateEle = new Element(TranDate);
						tTranDateEle.setText(tLineMsg.substring(25, 33));
						Element tContNoEle = new Element(ContNo);
						tContNoEle.setText(tLineMsg.substring(110, 140).trim());
						Element tPremEle = new Element(Prem);
						tPremEle.setText(tLineMsg.substring(140, 153));
						cLogger.info("输出"+tLineMsg.substring(0,16));
						cLogger.info("输出"+tLineMsg.substring(16,25));
						cLogger.info("输出"+tLineMsg.substring(25,33));
						cLogger.info("输出"+tLineMsg.substring(110,140).trim());
						cLogger.info("输出"+tLineMsg.substring(140,153));

						Element tDetailEle = new Element(Detail);
						tDetailEle.addContent(tTranDateEle);
						tDetailEle.addContent(tNodeNoEle);
						tDetailEle.addContent(tTranNoEle);
						tDetailEle.addContent(tContNoEle);
						tDetailEle.addContent(tPremEle);
						
						mBodyEle.addContent(tDetailEle);
						JdomUtil.print(mBodyEle);
					}
					if(type.equals("02"))
					{
						Element tTranNoEle = new Element(TranNo);
						tTranNoEle.setText(tLineMsg.substring(0, 16));
						Element tNodeNoEle = new Element(NodeNo);
						tNodeNoEle.setText(tLineMsg.substring(16, 25));
						Element tTranDateEle = new Element(TranDate);
						tTranDateEle.setText(tLineMsg.substring(25, 33));
						Element tContNoEle = new Element(ContNo);
						tContNoEle.setText(tLineMsg.substring(111, 141).trim());
						Element tPremEle = new Element(Prem);
						tPremEle.setText(tLineMsg.substring(141, 154));
						cLogger.info("输出"+tLineMsg.substring(0,16));
						cLogger.info("输出"+tLineMsg.substring(16,25));
						cLogger.info("输出"+tLineMsg.substring(25,33));
						cLogger.info("输出"+tLineMsg.substring(111,141).trim());
						cLogger.info("输出"+tLineMsg.substring(141,154));

						Element tDetailEle = new Element(Detail);
						tDetailEle.addContent(tTranDateEle);
						tDetailEle.addContent(tNodeNoEle);
						tDetailEle.addContent(tTranNoEle);
						tDetailEle.addContent(tContNoEle);
						tDetailEle.addContent(tPremEle);
						
						mBodyEle.addContent(tDetailEle);
						JdomUtil.print(mBodyEle);
					}
				}
				if(number==155)
				{
					String type =  tLineMsg.substring(153,155);
					cLogger.info("对账类型:"+type);
					if(type.equals("1W"))
					{
						continue;
					}
					if(type.equals("1S"))
					{
						Element tTranNoEle = new Element(TranNo);
						tTranNoEle.setText(tLineMsg.substring(0, 16));
						Element tNodeNoEle = new Element(NodeNo);
						tNodeNoEle.setText(tLineMsg.substring(16, 25));
						Element tTranDateEle = new Element(TranDate);
						tTranDateEle.setText(tLineMsg.substring(25, 33));
						Element tContNoEle = new Element(ContNo);
						tContNoEle.setText(tLineMsg.substring(109, 139).trim());
						Element tPremEle = new Element(Prem);
						tPremEle.setText(tLineMsg.substring(139, 152));
						cLogger.info("输出"+tLineMsg.substring(0,16));
						cLogger.info("输出"+tLineMsg.substring(16,25));
						cLogger.info("输出"+tLineMsg.substring(25,33));
						cLogger.info("输出"+tLineMsg.substring(109,139).trim());
						cLogger.info("输出"+tLineMsg.substring(139,152));

						Element tDetailEle = new Element(Detail);
						tDetailEle.addContent(tTranDateEle);
						tDetailEle.addContent(tNodeNoEle);
						tDetailEle.addContent(tTranNoEle);
						tDetailEle.addContent(tContNoEle);
						tDetailEle.addContent(tPremEle);
						
						mBodyEle.addContent(tDetailEle);
						JdomUtil.print(mBodyEle);
					}
					if(type.equals("02"))
					{
						Element tTranNoEle = new Element(TranNo);
						tTranNoEle.setText(tLineMsg.substring(0, 16));
						Element tNodeNoEle = new Element(NodeNo);
						tNodeNoEle.setText(tLineMsg.substring(16, 25));
						Element tTranDateEle = new Element(TranDate);
						tTranDateEle.setText(tLineMsg.substring(25, 33));
						Element tContNoEle = new Element(ContNo);
						tContNoEle.setText(tLineMsg.substring(110, 140).trim());
						Element tPremEle = new Element(Prem);
						tPremEle.setText(tLineMsg.substring(140, 153));
						cLogger.info("输出"+tLineMsg.substring(0,16));
						cLogger.info("输出"+tLineMsg.substring(16,25));
						cLogger.info("输出"+tLineMsg.substring(25,33));
						cLogger.info("输出"+tLineMsg.substring(110,140).trim());
						cLogger.info("输出"+tLineMsg.substring(140,153));

						Element tDetailEle = new Element(Detail);
						tDetailEle.addContent(tTranDateEle);
						tDetailEle.addContent(tNodeNoEle);
						tDetailEle.addContent(tTranNoEle);
						tDetailEle.addContent(tContNoEle);
						tDetailEle.addContent(tPremEle);
						
						mBodyEle.addContent(tDetailEle);
						JdomUtil.print(mBodyEle);
					}
				}
				if(number==154)
				{
					String type =  tLineMsg.substring(152,154);
					cLogger.info("对账类型:"+type);
					if(type.equals("1W"))
					{
						continue;
					}
					if(type.equals("1S"))
					{
						Element tTranNoEle = new Element(TranNo);
						tTranNoEle.setText(tLineMsg.substring(0, 16));
						Element tNodeNoEle = new Element(NodeNo);
						tNodeNoEle.setText(tLineMsg.substring(16, 25));
						Element tTranDateEle = new Element(TranDate);
						tTranDateEle.setText(tLineMsg.substring(25, 33));
						Element tContNoEle = new Element(ContNo);
						tContNoEle.setText(tLineMsg.substring(108, 138).trim());
						Element tPremEle = new Element(Prem);
						tPremEle.setText(tLineMsg.substring(138, 151));
						cLogger.info("输出"+tLineMsg.substring(0,16));
						cLogger.info("输出"+tLineMsg.substring(16,25));
						cLogger.info("输出"+tLineMsg.substring(25,33));
						cLogger.info("输出"+tLineMsg.substring(108,138).trim());
						cLogger.info("输出"+tLineMsg.substring(138,151));

						Element tDetailEle = new Element(Detail);
						tDetailEle.addContent(tTranDateEle);
						tDetailEle.addContent(tNodeNoEle);
						tDetailEle.addContent(tTranNoEle);
						tDetailEle.addContent(tContNoEle);
						tDetailEle.addContent(tPremEle);
						
						mBodyEle.addContent(tDetailEle);
						JdomUtil.print(mBodyEle);
					}
					if(type.equals("02"))
					{
						Element tTranNoEle = new Element(TranNo);
						tTranNoEle.setText(tLineMsg.substring(0, 16));
						Element tNodeNoEle = new Element(NodeNo);
						tNodeNoEle.setText(tLineMsg.substring(16, 25));
						Element tTranDateEle = new Element(TranDate);
						tTranDateEle.setText(tLineMsg.substring(25, 33));
						Element tContNoEle = new Element(ContNo);
						tContNoEle.setText(tLineMsg.substring(109, 139).trim());
						Element tPremEle = new Element(Prem);
						tPremEle.setText(tLineMsg.substring(139, 152));
						cLogger.info("输出"+tLineMsg.substring(0,16));
						cLogger.info("输出"+tLineMsg.substring(16,25));
						cLogger.info("输出"+tLineMsg.substring(25,33));
						cLogger.info("输出"+tLineMsg.substring(109,139).trim());
						cLogger.info("输出"+tLineMsg.substring(139,152));

						Element tDetailEle = new Element(Detail);
						tDetailEle.addContent(tTranDateEle);
						tDetailEle.addContent(tNodeNoEle);
						tDetailEle.addContent(tTranNoEle);
						tDetailEle.addContent(tContNoEle);
						tDetailEle.addContent(tPremEle);
						
						mBodyEle.addContent(tDetailEle);
						JdomUtil.print(mBodyEle);
					}
				}
			}
		} 
		
		mBufReader.close();	//关闭流 
		JdomUtil.print(mBodyEle);
		cLogger.info("Out BocDownBusiBlc.parse()!");
		return mBodyEle;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.boc.bat.BocDownBusiBlc.main");
		mLogger.info("程序开始...");
		
		BocDownBusiBlc mBatch = new BocDownBusiBlc();
		mBatch.setDate(new Date());
		//用于补对账，设置补对账日期
		if (0 != args.length) {
			mLogger.info("args[0] = " + args[0]);
			
			/**
			 * 严格日期校验的正则表达式：\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))。
			 * 4位年-2位月-2位日。
			 * 4位年：4位[0-9]的数字。
			 * 1或2位月：单数月为0加[0-9]的数字；双数月必须以1开头，尾数为0、1或2三个数之一。
			 * 1或2位日：以0、1或2开头加[0-9]的数字，或者以3开头加0或1。
			 * 
			 * 简单日期校验的正则表达式：\\d{4}\\d{2}\\d{2}。
			 */ 
			if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))")) {
				mBatch.setDate(args[0]);
			} else {
				throw new MidplatException("日期格式有误，应为yyyyMMdd！" + args[0]);
			}
		}  
		
		mBatch.run();
		
		mLogger.info("成功结束！");
	}
}
