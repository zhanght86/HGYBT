package com.sinosoft.lis.excel;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 * poi-3.7
 * 
 * @author Administrator
 * 
 */
public class BCExcelUtils {
	private String name = "";
	private String path = "";
	public BCExcelUtils() {

	}
	public BCExcelUtils(String name,String path) {
		this.name = name;
		this.path = path;
	}

	private int sumWidth;

	public static void main(String args[]) {
		String filepath = "C:\\Users\\asus\\Desktop\\aa.xls";
		BCExcelUtils eu = new BCExcelUtils("BC",filepath);
		
		HSSFWorkbook workbook = eu.readExcelFile(filepath);
		String exceltitle = eu.getFirstRowContent(workbook, 0);
		StringBuffer htmlsource = eu.excelToHtmlJs(workbook, 0);
		StringBuffer htmlbuf = new StringBuffer("");
		htmlbuf.append(eu.headerHtmlStart(exceltitle));

		htmlbuf.append(htmlsource);

		htmlbuf.append(eu.headerHtmlEnd());

		htmlbuf.append(eu.bodyHtml());
		htmlbuf.append(eu.bodyHtmlEnd());

		System.out.println(htmlbuf);
	}

	public StringBuffer headerHtmlStart(String title) {
		StringBuffer sb = new StringBuffer("");
		sb.append("<html>\n");
		sb.append("<head>\n");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\" />\n");
		sb.append("<META HTTP-EQUIV=\"pragma\" CONTENT=\"no-cache\">\n");
		sb.append("<META HTTP-EQUIV=\"Cache-Control\" CONTENT=\"no-cache, must-revalidate\">\n");
		sb.append("<META HTTP-EQUIV=\"expires\" CONTENT=\"0\">\n");

		sb.append("<title>" + title + "</title>\n");
		sb.append("<SCRIPT type=\"text/javascript\" src=\"../js/jquery-1.3.2.min.js\"></SCRIPT>\n");
		sb.append("<script type=\"text/javascript\" src=\"../js/scripts-pack.js\"></script>\n");
		sb.append("<script type=\"text/javascript\" src=\"../js/jquery-ui-1.7.2.custom.min.js\"></script>\n");
		sb.append("<script type=\"text/javascript\" src=\"../js/jquery.chromatable.js\"></script>\n");
		sb.append("<SCRIPT src=\"../common/easyQueryVer3/EasyQueryCache.js\"></SCRIPT>\n");
		sb.append("<SCRIPT src=\"../common/javascript/Common.js\"></SCRIPT>\n");
		sb.append("<SCRIPT src=\"../common/javascript/MulLine.js\"></SCRIPT>\n");
		sb.append("<SCRIPT src=\"../common/cvar/CCodeOperate.js\"></SCRIPT>\n");
		sb.append("<SCRIPT src=\"../common/Calendar/Calendar.js\"></SCRIPT>\n");
		sb.append("<SCRIPT src=\"./BCPreview.js\"></SCRIPT>\n");
		sb.append("<SCRIPT src=\"../common/easyQueryVer3/EasyQueryVer3.js\"></SCRIPT>\n");
		sb.append("<SCRIPT src=\"../common/javascript/VerifyInput.js\"></SCRIPT>\n");
		sb.append("<LINK href=\"../common/css/Project.css\" rel=stylesheet type=text/css>\n");
		sb.append("<LINK href=\"../common/css/mulLine.css\" rel=stylesheet type=text/css>\n");

		return sb;
	}

	public StringBuffer headerHtmlEnd() {
		StringBuffer sb = new StringBuffer("");
		sb.append("</head>\n");
		sb.append("<body onload=\"initElementtype();\">\n");// 进入页面就刷新下
		sb.append("<form action=\"\" method=post name=fm target=\"fraSubmit\">\n");
		sb.append("<table> \n");
		
		sb.append("<TD  width=\"26%\">");
		sb.append("<INPUT VALUE=\"下载报表\" class=\"cssbutton\" TYPE=button onclick=\"DownloadThis();\"></TD> ");
		sb.append("<TD  width=\"26%\">");
		sb.append("<INPUT VALUE=\"关闭页面\" class=\"cssbutton\" TYPE=button onclick=\"returnParent();\"></TD> ");
		sb.append("</table> \n");
		return sb;
	}

	public StringBuffer bodyHtml() {
		StringBuffer sb = new StringBuffer("");

		sb.append("<br/>\n");
		sb.append("<table id='exceltitletable' width=\"" + this.getSumWidth()
				+ "\"   border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
		sb.append("</table>\n");
		sb.append("<table id='exceltable'  width=\"" + this.getSumWidth()
				+ "\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");

		sb.append("<thead>  \n");
		sb.append("</thead>\n");
		sb.append("<tbody>\n");
		sb.append("</tbody>\n");
		sb.append("</table>\n");
		return sb;
	}

	public StringBuffer bodyHtml(HSSFWorkbook workbook, int sheetindex) {
		int trwidth = this.getTrWidth(workbook, sheetindex);
		StringBuffer sb = new StringBuffer("");
		sb.append("<a id=\"deldata\" href=\"#\">查看全部结果</a>\n");
		sb.append("<br/>\n");
		sb.append("<table id='exceltitletable' border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");
		sb.append("</table>\n");
		sb.append("<table id='exceltable' ");
		sb.append("border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n");

		sb.append("<thead>  \n");
		sb.append("</thead>\n");
		sb.append("<tbody>\n");
		sb.append("</tbody>\n");
		sb.append("</table>\n");
		return sb;
	}

	public StringBuffer bodyHtmlEnd() {
		StringBuffer sb = new StringBuffer("");
		sb.append("</form>\n");
		sb.append("</body>\n");
		sb.append("</html>\n");
		return sb;
	}

	private int getTdWidth(Sheet sheet, int startCol, int endCol) {
		int tdwidth = 0;
		for (int i = startCol; i <= endCol; i++) {
			int tempwidth = sheet.getColumnWidth(i) / 32;
			tdwidth = tdwidth + tempwidth;

		}
		return tdwidth;
	}

	public StringBuffer excelToHtmlJs(HSSFWorkbook workbook, int sheetindex) {
		StringBuffer sb = new StringBuffer("");

		HSSFSheet sheet = workbook.getSheetAt(sheetindex);

		int lastRowNum = sheet.getLastRowNum();

		Map<String, String> map[] = getRowSpanColSpanMap(sheet);

		HSSFRow row1 = null;
		HSSFCell cell1 = null;
		int lastColNums = 0;
		for (int rowNum = sheet.getFirstRowNum(), i = 0; rowNum <= lastRowNum; rowNum++, i++) {
			row1 = (HSSFRow) sheet.getRow(rowNum);

			if (!" ".equals(row1)) {
				lastColNums = row1.getLastCellNum();
				int sumWidth = 0;
				//System.out.println("lastColNums:" + lastColNums);
			}
			for (int colNum = 0, j = 0; colNum < lastColNums; colNum++, j++) {
				cell1 = row1.getCell(colNum);
				int tdwidth = sheet.getColumnWidth(colNum) / 32;

				sumWidth += tdwidth;
				this.setSumWidth(sumWidth);

			}
			break;
		}
		      
	
	

		
		sb.append("<script type=\"text/javascript\">\n");

		int sum = this.getSumWidth();//
		System.out.println("sum=" + sum);
		sb.append(" $(document).ready(function(){\n");
		sb.append(" $(\"#exceltitletable\").chromatable({\n");
		sb.append(" width: '" + sum + "px',\n"); // specify 100%, auto, or a
													// fixed pixel amount
		sb.append(" height: \"100%\",\n");
		sb.append(" scrolling: \"yes\"\n"); // must have the jquery-1.3.2.min.js
											// script installed to use
		sb.append(" });\n");
		sb.append(" });\n");
		sb.append("  $(document).ready(function() {\n");

		HSSFRow row = null;

		HSSFCell cell = null;
		List rangennumList = this.getRangeRows(sheet);
		int rangenum = rangennumList.size();

		for (int rowNum = sheet.getFirstRowNum(); rowNum <= lastRowNum; rowNum++) {
			StringBuffer strbuf = new StringBuffer("");
			int trwidth = 0;
			row = (HSSFRow) sheet.getRow(rowNum);
			if (row == null) {

				strbuf.append("<tr><td > &nbsp;</td></tr>");

				continue;
			}

			strbuf.append("<tr>");
			int lastColNum = row.getLastCellNum();

			int ii = 0;
			if(name.equals("IT")){
				ii=1;
			}
			System.out.println("lastColNum:" + lastColNum);
			for (int colNum = ii; colNum < lastColNum; colNum++) {

				cell = row.getCell(colNum);
				int tdwidth = sheet.getColumnWidth(colNum) / 32;
				if (cell == null) {
					cell = row.createCell(colNum);
					cell.setCellValue("");
				}

				HSSFCellStyle cellStyle = cell.getCellStyle();
				cellStyle.setWrapText(true);
				cell.setCellStyle(cellStyle);
				String stringValue = getCellValue(cell);

				if (map[0].containsKey(rowNum + "," + colNum)) {
					String pointString = map[0].get(rowNum + "," + colNum);
					map[0].remove(rowNum + "," + colNum);
					int bottomeRow = Integer.valueOf(pointString.split(",")[0]);
					int bottomeCol = Integer.valueOf(pointString.split(",")[1]);
					int rowSpan = bottomeRow - rowNum + 1;
					int colSpan = bottomeCol - colNum + 1;
					strbuf.append("<td width=\""
							+ getTdWidth(sheet, colNum, bottomeCol)
							+ "\" rowspan=\"" + rowSpan + "\" colspan=\""
							+ colSpan + "\" ");

				} else if (map[1].containsKey(rowNum + "," + colNum)) {

					map[1].remove(rowNum + "," + colNum);

					continue;

				} else {

					if ("甲".equals(stringValue) || "乙".equals(stringValue)) {
						int tdwidths = tdwidth - 10;
						strbuf.append("<th  filter-type=\"ddl\" width=\""
								+ (tdwidths) + "\" ");
					} else if ("&nbsp;".equals(stringValue)
							|| " ".equals(stringValue)) {
						strbuf.append("<td");
					} else {
						strbuf.append("<td width=\"" + tdwidth + "\" ");

					}

				}

				if (cellStyle != null) {

					short alignment = cellStyle.getAlignment();

					strbuf.append("align=\"" + convertAlignToHtml(alignment)
							+ "\" ");

					short verticalAlignment = cellStyle.getVerticalAlignment();
					strbuf.append("valign=\""
							+ convertVerticalAlignToHtml(verticalAlignment)
							+ "\" ");

					HSSFFont hf = cellStyle.getFont(workbook);

					short boldWeight = hf.getBoldweight();

					short fontColor = hf.getColor();

					strbuf.append("style=\"");

					HSSFPalette palette = workbook.getCustomPalette(); // 类HSSFPalette用于求的颜色的国际标准形式

					HSSFColor hc = palette.getColor(fontColor);
					// System.out.println(boldWeight);
					// sb.append("font-weight:" + boldWeight + ";"); //字体加粗

					strbuf.append("font-size: " + hf.getFontHeightInPoints()
							+ "pt;");
					strbuf.append("font-family:" + hf.getFontName() + ";");

					String fontColorStr = convertToStardColor(hc);

					if (fontColorStr != null && !"".equals(fontColorStr.trim())) {

						strbuf.append("color:" + fontColorStr + ";"); // 字体颜色
					}

					short bgColor = cellStyle.getFillForegroundColor();

					hc = palette.getColor(bgColor);

					String bgColorStr = convertToStardColor(hc);

					if (bgColorStr != null && !"".equals(bgColorStr.trim())) {

						strbuf.append("background-color:" + bgColorStr + ";"); // 背景颜色
					}


					short	 bordertop = cellStyle.getBorderTop();
					short	 borderleft = cellStyle.getBorderLeft();
					short	 borderright = cellStyle.getBorderRight();
					short	 borderbottom = cellStyle.getBorderBottom();		
						
						
					if(rowNum != 0 && name.equals("BC")){
						bordertop = 0;
						borderleft = 0;
						if(colNum ==0){
							borderleft = 1;
						}
						if(colNum == lastRowNum){
							borderright = 1;
						}
						if(rowNum == 1){
							borderright = 1;
						}
					}
					if(rowNum != 0 && name.equals("FIN")){
						bordertop = 0;
						borderleft = 0;
						if(colNum ==0){
							borderleft = 1;
						}
						if(colNum == lastRowNum){
							borderright = 1;
						}
						if(rowNum == 1){
							borderright = 1;
						}
					}
					if(rowNum != 0 && name.equals("LO")){
						bordertop = 0;
						borderleft = 0;
						if(colNum ==0){
							borderleft = 1;
						}
						if(colNum == lastRowNum){
							borderright = 1;
						}
					}
					if(name.equals("IT")){
					if(rowNum != 0 && colNum !=0){
						bordertop = 0;
						borderleft = 0;
					}
					if(rowNum == 0 && colNum ==1){
						borderright = 5;
					}
					if( rowNum != 0 && colNum ==1 && rowNum!=lastRowNum){
						borderleft = 5;
					}
					}

					
					
					short borderColor = cellStyle.getBottomBorderColor();
					short colorbottom = cellStyle.getBottomBorderColor();
					short colortop = cellStyle.getTopBorderColor();
					short colorleft = cellStyle.getLeftBorderColor();
					short colorright = cellStyle.getRightBorderColor();

					String borstyletop = this
							.convertBorderStyleToHtml(bordertop);
					String borstyleleft = this
							.convertBorderStyleToHtml(borderleft);
					String borstyleright = this
							.convertBorderStyleToHtml(borderright);
					String borstylebottom = this
							.convertBorderStyleToHtml(borderbottom);
					String bortopcolor = this
							.convertBorderColorToHtml(colortop);
					String borleftcolor = this
							.convertBorderColorToHtml(colorleft);
					String borrightcolor = this
							.convertBorderColorToHtml(colorright);
					String borbottomcolor = this
							.convertBorderColorToHtml(colorbottom);
					// System.out.print(borstyletop+"-"+borstyleleft+"-"+borstyleright+"-"+borstylebottom+",");

					strbuf.append("border-top:"
							+ this.getBorderStyle(borstyletop, bortopcolor)
							+ ";");
					strbuf.append("border-left:"
							+ this.getBorderStyle(borstyleleft, borleftcolor)
							+ ";");
					strbuf.append("border-right:"
							+ this.getBorderStyle(borstyleright, borrightcolor)
							+ ";");
					strbuf.append("border-bottom:"
							+ this.getBorderStyle(borstylebottom,
									borbottomcolor) + ";");

					// sb.append("border:" +
					// this.convertBorderStyleToHtml(bordertop)+" "+
					// convertBorderColorToHtml(colorbottom)+ ";");
					// String

					hc = palette.getColor(borderColor);

					String borderColorStr = convertToStardColor(hc);

					if (borderColorStr != null
							&& !"".equals(borderColorStr.trim())) {

					}

					strbuf.append("\" ");
				}

				strbuf.append(">");
				if (stringValue == null || "".equals(stringValue.trim())) {

					strbuf.append(" &nbsp; ");
				} else {
					// System.out.println("StringValue="+stringValue);
					strbuf.append(stringValue.replaceAll("\\s*", "") + " ");
					/*
					 * strbuf.append(" " +
					 * stringValue.replace(String.valueOf((char) 160), "&nbsp;")
					 * + " ");
					 */

				}

				if ("甲".equals(stringValue) || "乙".equals(stringValue)) {
					strbuf.append("</th>");
				} else {
					strbuf.append("</td>");
				}

			}

			strbuf.append("</tr>");

	
				sb.append("$('#exceltitletable').append('" + strbuf.toString()
						+ "');\n");


		}

		// sb.append("</table>\n");
		System.out.println("sumwidth=" + this.getSumWidth());// 取出总宽度的行数

		sb.append("var options1 = {\n");
		sb.append("    additionalFilterTriggers: [ ],\n");
		sb.append("    clearFiltersControls: [$('#deldata')],\n");
		sb.append("    matchingRow: function(state, tr, textTokens) {  \n");
		sb.append("      if (!state || !state.id) { return true; }\n");
		sb.append("var val =  tr.children('td:eq(2)').text();\n");
		sb.append("  switch (state.id) {   \n");
		sb.append("   default: return true;\n");
		sb.append("  }\n");
		sb.append(" }\n");
		sb.append("};\n");
		sb.append("$('#exceltable').tableFilter(options1);\n");
		sb.append("});\n");

		sb.append("</script>\n");
		return sb;
	}

	@Override
	public String toString() {
		return "ExcelUtils []";
	}

	public int getTrWidth(HSSFWorkbook workbook, int sheetindex) {
		int trwidth = 0;
		Sheet sheet = workbook.getSheetAt(sheetindex);
		int lastRowNum = sheet.getLastRowNum();
		HSSFRow row = (HSSFRow) sheet.getRow(lastRowNum);
		if (row != null) {
			int lastColNum = row.getLastCellNum();
			for (int colNum = 0; colNum < lastColNum; colNum++) {
				HSSFCell cell = row.getCell(colNum);
				int tdwidth = sheet.getColumnWidth(colNum) / 32;
				trwidth = trwidth + tdwidth;
			}
		}
		return trwidth;
	}



	public static String getHex(String strHex) {
		if (strHex.length() > 0) {
			String[] a = strHex.split(":");
			strHex = "";
			for (int n = 0; n < a.length; n++) {
				if (a[n].length() > 0) {
					if (a[n].length() < 2) {
						strHex += "0" + a[n];
					} else

					{
						strHex += a[n].substring(0, 2);
					}
				}
			}
		}
		return strHex.length() > 0 ? strHex : null;

	}

	@SuppressWarnings("unchecked")
	private Map<String, String>[] getRowSpanColSpanMap(Sheet sheet) {

		Map<String, String> map0 = new HashMap<String, String>();
		Map<String, String> map1 = new HashMap<String, String>();

		int mergedNum = sheet.getNumMergedRegions();
		// System.out.println("mergedNum="+mergedNum);
		CellRangeAddress range = null;

		for (int i = 0; i < mergedNum; i++) {

			range = sheet.getMergedRegion(i);

			int topRow = range.getFirstRow();

			int topCol = range.getFirstColumn();

			int bottomRow = range.getLastRow();

			int bottomCol = range.getLastColumn();

			map0.put(topRow + "," + topCol, bottomRow + "," + bottomCol);

			// System.out.println(topRow + "," + topCol + "," + bottomRow + ","
			// + bottomCol);

			int tempRow = topRow;

			while (tempRow <= bottomRow) {

				int tempCol = topCol;

				while (tempCol <= bottomCol) {

					map1.put(tempRow + "," + tempCol, "");

					tempCol++;
				}

				tempRow++;
			}

			map1.remove(topRow + "," + topCol);

		}

		Map[] map = { map0, map1 };

		return map;
	}

	private String convertAlignToHtml(short alignment) {

		String align = "left";

		switch (alignment) {

		case HSSFCellStyle.ALIGN_LEFT:
			align = "left";
			break;
		case HSSFCellStyle.ALIGN_CENTER:
			align = "center";
			break;
		case HSSFCellStyle.ALIGN_RIGHT:
			align = "right";
			break;

		default:
			break;
		}

		return align;
	}

	private String getBorderStyle(String bortype, String borcolor) {
		String borstyle = "";
		if ("thin".equals(bortype)) {
			borstyle = "1px solid " + borcolor;
		} else if ("medium".equals(bortype)) {
			borstyle = "2px solid " + borcolor;
		} else if ("double".equals(bortype)) {
			borstyle = "double solid " + borcolor;
		} else if ("dotted".equals(bortype)) {
			borstyle = "1px dotted " + borcolor;
		} else if ("dashed".equals(bortype)) {
			borstyle = "1px dashed " + borcolor;
		}
		return borstyle;
	}

	private String convertBorderStyleToHtml(short bordertype) {
		String type = "none";
		switch (bordertype) {

		case HSSFCellStyle.BORDER_THIN:
			type = "thin";
			break;
		case HSSFCellStyle.BORDER_DOTTED:
			type = "dotted";
			break;
		case HSSFCellStyle.BORDER_DASHED:
			type = "dashed";
			break;
		case HSSFCellStyle.BORDER_NONE:
			type = "none";
			break;
		case HSSFCellStyle.BORDER_MEDIUM:
			type = "medium";
			break;
		case HSSFCellStyle.BORDER_DOUBLE:
			type = "double";
			break;
		case 5:
			type = "double";
			break;
		default:
			break;
		}

		return type;
	}

	private String convertBorderColorToHtml(short bordercolor) {
		String type = "black";

		switch (bordercolor) {
		case HSSFColor.BLACK.index:
			type = "black";
			break;
		case HSSFColor.BLUE.index:
			type = "blue";
			break;
		case HSSFColor.RED.index:
			type = "red";
			break;

		default:
			break;
		}

		return type;
	}

	private String convertVerticalAlignToHtml(short verticalAlignment) {

		String valign = "middle";

		switch (verticalAlignment) {

		case HSSFCellStyle.VERTICAL_BOTTOM:
			valign = "bottom";
			break;
		case HSSFCellStyle.VERTICAL_CENTER:
			valign = "center";
			break;
		case HSSFCellStyle.VERTICAL_TOP:
			valign = "top";
			break;
		default:
			break;
		}

		return valign;
	}

	private String convertToStardColor(HSSFColor hc) {

		StringBuffer sb = new StringBuffer("");

		if (hc != null) {

			if (HSSFColor.AUTOMATIC.index == hc.getIndex()) {

				return null;
			}

			sb.append("#");

			for (int i = 0; i < hc.getTriplet().length; i++) {

				sb.append(fillWithZero(Integer.toHexString(hc.getTriplet()[i])));
			}
		}

		return sb.toString();
	}

	private String fillWithZero(String str) {

		if (str != null && str.length() < 2) {

			return "0" + str;
		}
		return str;
	}

	/**
	 * 获取Cell的内容
	 * 
	 * @param cell
	 * @return
	 */
	public String getStringCellValue(HSSFCell cell) {
		String cellvalue = "";
		if (cell == null) {
			return "";
		}
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			cellvalue = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			cellvalue = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			cellvalue = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			cellvalue = "";
			break;
		default:
			cellvalue = "";
			break;
		}
		if (cellvalue == null) {
			return "";
		}
		return cellvalue;
	}

	private String getCellValue(HSSFCell cell) {

		switch (cell.getCellType()) {

		case HSSFCell.CELL_TYPE_NUMERIC:

			DecimalFormat format = new DecimalFormat("#0.##");

			return format.format(cell.getNumericCellValue());
			// return String.valueOf(cell.getNumericCellValue());

		case HSSFCell.CELL_TYPE_STRING:

			return cell.getStringCellValue();

			// case HSSFCell.CELL_TYPE_FORMULA:
			//
			// return cell.getCellFormula();

		default:
			return "";
		}
	}

	/**
	 * 读取excel
	 * 
	 * @param filepath
	 *            excel文件地址
	 * @return HSSFWorkbook
	 */
	public HSSFWorkbook readExcelFile(String filepath) {
		HSSFWorkbook workbook = null;
		try {
			FileInputStream input = new FileInputStream(new File(filepath));
			POIFSFileSystem fs = new POIFSFileSystem(input);
			workbook = new HSSFWorkbook(fs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	/**
	 * 读取excel
	 * 
	 * @param filepath
	 *            excel文件地址
	 * @return HSSFWorkbook
	 */
	public HSSFWorkbook readExcelFile2(File file) {
		HSSFWorkbook workbook = null;
		try {
			FileInputStream input = new FileInputStream(file);
			POIFSFileSystem fs = new POIFSFileSystem(input);
			workbook = new HSSFWorkbook(fs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	/**
	 * 获取某Sheet，第一行的内容
	 * 
	 * @param workbook
	 * @param sheetindex
	 * @return
	 */
	public String getFirstRowContent(HSSFWorkbook workbook, int sheetindex) {
		String exceltitle = "";
		if (workbook != null) {
			HSSFSheet sheet = workbook.getSheetAt(sheetindex);
			int firstrownum = sheet.getFirstRowNum();
			HSSFRow row = sheet.getRow(firstrownum);
			short fcellnum = row.getFirstCellNum();
			short lcellnum = row.getLastCellNum();
			for (int j = fcellnum; j < lcellnum; j++) {
				HSSFCell cell = row.getCell(j);
				exceltitle += getStringCellValue(cell);
			}
		}
		return exceltitle;
	}

	public CellRangeAddress getRange(HSSFSheet sheet) {
		if (sheet != null) {
			int rangenum = sheet.getNumMergedRegions();
			for (int m = 0; m < rangenum; m++) {
				CellRangeAddress range = sheet.getMergedRegion(m);
			}
		}
		return null;
	}

	public List getRangeRows(HSSFSheet sheet) {
		if (sheet != null) {
			int rangenum = sheet.getNumMergedRegions();// 找到当前sheet单元格中共有多少个合并区域
			// System.out.println("RangenNum="+rangenum);
			ArrayList list = new ArrayList();
			if (rangenum > 0) {
				for (int i = 0; i < rangenum; i++) {
					CellRangeAddress range = sheet.getMergedRegion(i);// 一个合并单元格代表
																		// CellRangeAddress
					list.add(range.getFirstRow());
					list.add(range.getLastRow());
					list.add(range.getLastColumn());
					list.add(range.getFirstColumn());

					// range.
					// range.isInRange(rowInd, colInd)()

				}
				Collections.sort(list);
				this.removeDuplicateWithOrder(list);
				return list;
			}
		}
		return null;
	}

	public int getSumWidth(int sum) {
		return sum;
	}

	/** List order maintained **/
	public static void removeDuplicateWithOrder(ArrayList arlList) {
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = arlList.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		arlList.clear();
		arlList.addAll(newList);
	}

	public java.awt.Color toAWTColor(int R, int G, int B, int A) {
		return new java.awt.Color(R, G, B, A);
	}

	public int toRGB(int R, int G, int B) {
		return R << 16 | G << 8 | B;
	}

	public String getTdString(HSSFCell cell) {
		String tdstr = "";

		return tdstr;
	}

	public int getSumWidth() {
		return sumWidth;
	}

	public void setSumWidth(int sumWidth) {
		this.sumWidth = sumWidth;
	}

}
