package com.sinosoft.lis.excel;

import jxl.write.WritableFont.FontName;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;

public class ExcelParam {

	public int row=0;
	public int col=0;
	public int minCol=0;
	public int maxCol=0;
	public int minRow=0;
	public int maxRow=0;
	public int sheet=0;
	public int FontSize=0;
	public String []title=null;
	public String [][]data=null;
	public String []size=null;
	public String content=null;
	public String Bold = null;
	public FontName fn=null;
	public boolean itaiic=false;
	public Alignment align=null;
	public Border border=null;
	public BorderLineStyle bls=null;
	public String numberStyle="CURRENCY_DOLLAR";
	public Colour cellColour = null;
	
	
}
