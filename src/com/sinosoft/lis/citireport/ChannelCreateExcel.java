package com.sinosoft.lis.citireport;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import jxl.DateCell;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.WritableFont.FontName;

import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelFont;
import com.sinosoft.lis.excel.ExcelParam;
import com.sinosoft.midplat.common.DateUtil;

public class ChannelCreateExcel {
	public String getColorFlag() {
		return ColorFlag;
	}

	public void setColorFlag(String colorFlag) {
		this.ColorFlag = colorFlag;
	}
	private File tFile = null;
	public File gettFile() {
		return tFile;
	}

	public void settFile(File tFile) {
		this.tFile = tFile;
	}
	public String [][] sData = null;
	public WritableSheet thisSheet1 = null;
	private String ColorFlag = "N";
	private HashMap rowcolorMap = new HashMap(); // 设置行颜色容器
	private HashMap titleMap = new HashMap(); // 设置Excel的标头显示内容容器
	private HashMap dataMap = new HashMap(); // 设置Excel的显示内容
	private HashMap ColSizeMap = new HashMap(); // 设置列大小
	private ArrayList contenArry = new ArrayList(); // 设置单元格显示内容
	private ArrayList mergeArry = new ArrayList(); // 设置单元格合并
	private ArrayList fontArry = new ArrayList(); // 设置单元格显示字体
	private ArrayList fontSizeArry = new ArrayList(); // 设置单元格字体大小
	private ArrayList boldArry = new ArrayList(); // 设置单元格字体是否为粗体
	private ArrayList itaiicArry = new ArrayList(); // 设置单元格是为斜体
	private ArrayList alignArry = new ArrayList(); // 设置单元格水平对齐
	private ArrayList borderArry = new ArrayList(); // 设置单元格中内容显示位置
	private ArrayList borderLineArry = new ArrayList();// 设置单元格边框格式
	private ArrayList colorArry = new ArrayList();// 设置单元格颜色格式

	private HashMap titleFontMap = new HashMap();// 设置title字体
	private HashMap titleFontSizeMap = new HashMap();// 设置title字体大小
	private HashMap titleBoldMap = new HashMap();// 设置title字体是否为粗体
	private HashMap titleItaiicMap = new HashMap();// 设置title字体是否斜体
	private HashMap titleAlignMap = new HashMap();// 设置title字体水平对齐
	private HashMap titleBorderMap = new HashMap(); // 设置title内容显示位置
	private HashMap titleBLSMap = new HashMap(); // 设置title的边框格式
	private HashMap serinoMap = new HashMap();// 设置输出数据是否有序号

	private ArrayList colNumArry = new ArrayList(); // 设置为数字输出

	private String[] inSheet = null;// 设置Excel有几个sheet
	private String filePath = null;// 设置Excel输出路径
	private int rowTitle = 0; // 记录标题从哪行开始
	private int colTitle = 0; // 记录标题从哪列开始
	private int rowData = 0;// 记录输出内容从哪行开始
	private int colData = 0;// 记录输出内容从哪列开始
	private int startcolSize = 0;// 每列大小设置从哪列开始

	private final int defutSheet = 0;
	private final int defutRow = 0;
	private final int defutCol = 0;
	/**** 设置单元格默认格式 ****/
	private final FontName DEFAULT_FN = WritableFont.ARIAL; // 设置默认字体
	private final Colour DEFAULT_COLOUR = Colour.BLACK; // 设置默认颜色
	private final UnderlineStyle DEFAULT_UNDERLINE = UnderlineStyle.NO_UNDERLINE;// 设置默认下划线
	private final int DEFAULT_FONTSIZE = 10;// 设置默认字体大小
	private final boolean DEFAULT_ITAIIC = false;// 设置默认是否斜体
	private final Alignment DEFAULT_ALIGN = Alignment.LEFT;// 设置默认水平对齐
	private final Border DEFAULT_BORDER = Border.ALL;// 设置显示在单元格中的什么位置
	private final BorderLineStyle DEFAULT_BLS = BorderLineStyle.NONE;// 设置单元格默认没有边框
	private final String DEFAULT_BOLD = "No_Bold"; // 设置单元格不是粗体

	public ChannelCreateExcel() {
	}

	// public void setRowColour(int row,Colour colur) throws Exception
	// {
	// if(rowcolorMap.size()==0||rowcolorMap.get(String.valueOf(row))==null)
	// rowcolorMap.put(String.valueOf(row), colur);
	//
	// }
	// public void setColColour(String col,Colour colur)
	// {
	//
	// }

	/**
	 * 设置sheet当中单元格的字体
	 * 
	 * @param i
	 *            显示在哪一个sheet
	 * @param col
	 *            列
	 * @param row
	 *            行
	 * @param fn
	 *            字体
	 */
	public void setCellFont(int i, int row, int col, FontName fn) {
		ExcelParam ep = new ExcelParam();
		ep.col = col;
		ep.row = row;
		ep.sheet = i;
		ep.fn = fn;
		fontArry.add(ep);
	}

	/**
	 * 设置sheet当中单元格的颜色
	 * 
	 * @param i
	 *            显示在哪一个sheet
	 * @param col
	 *            列
	 * @param row
	 *            行
	 * @param fn
	 *            字体
	 */
	public void setCellColor(int i, int row, int col, Colour fn) {
		ExcelParam ep = new ExcelParam();
		ep.col = col;
		ep.row = row;
		ep.sheet = i;
		ep.cellColour = fn;
		colorArry.add(ep);
	}

	/**
	 * 设置sheet当中单元格的字体,默认第一个sheet
	 * 
	 * @author changliang
	 * @serialData 2007-08-10
	 * @param col
	 *            列
	 * @param row
	 *            行
	 * @param fn
	 *            字体
	 */
	public void setCellFont(int row, int col, FontName fn) {
		this.setCellFont(this.defutSheet, row, col, fn);
	}

	/**
	 * 得到sheet当中某一列的字体,如果没有返回默认字体。
	 * 
	 * @author changliang
	 * @serialData 2007-08-10
	 * @param i
	 *            sheet编码
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @return 字体
	 */
	private FontName getFontName(int i, int row, int col) {
		FontName fn = null;
		Iterator itMerge = fontArry.iterator();
		while (itMerge.hasNext()) {
			ExcelParam ep = (ExcelParam) itMerge.next();
			if (ep != null && ep.sheet == i && ep.row == row && ep.col == col) {
				// System.out.println("ep中的sheet编码：" + ep.sheet);
				fn = ep.fn;
				break;
			}
		}
		if (fn == null)
			fn = this.DEFAULT_FN;
		return fn;
	}

	/**
	 * 得到sheet当中某一列的颜色,如果没有返回默认颜色。
	 * 
	 * @author changliang
	 * @serialData 2007-08-10
	 * @param i
	 *            sheet编码
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @return 字体
	 */
	private Colour getCellColor(int i, int row, int col) {
		Colour tcellColour = null;
		Iterator itMerge = colorArry.iterator();
		while (itMerge.hasNext()) {
			ExcelParam ep = (ExcelParam) itMerge.next();
			if (ep != null && ep.sheet == i && ep.row == row && ep.col == col) {
				// System.out.println("ep中的sheet编码：" + ep.sheet);
				tcellColour = ep.cellColour;
				break;
			}
		}
		if (tcellColour == null)
			tcellColour = this.DEFAULT_COLOUR;
		return tcellColour;
	}

	/**
	 * 设置sheet当中单元格的大小
	 * 
	 * @author changliang
	 * @serialData 2007-08-10
	 * @param i
	 *            显示在哪一个sheet
	 * @param col
	 *            列
	 * @param row
	 *            行
	 * @param fn
	 *            大小
	 */
	public void setFontSize(int i, int row, int col, int FontSize) {
		ExcelParam ep = new ExcelParam();
		ep.sheet = i;
		ep.row = row;
		ep.col = col;
		ep.FontSize = FontSize;
		fontSizeArry.add(ep);
	}

	/**
	 * 设置sheet当中单元格的大小,默认第一个sheet
	 * 
	 * @author changliang
	 * @serialData 2007-08-10
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @param fn
	 *            大小
	 */
	public void setFontSize(int row, int col, int FontSize) {
		this.setFontSize(this.defutSheet, row, col, FontSize);
	}

	/**
	 * 取得单元格的字体大小
	 * 
	 * @author changliang
	 * @serialData 2007-08-10
	 * @param i
	 *            sheet 编码
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @return
	 */
	public int getFontSize(int i, int row, int col) {
		int fontSize = 0;
		Iterator itMerge = fontSizeArry.iterator();
		while (itMerge.hasNext()) {
			ExcelParam ep = (ExcelParam) itMerge.next();
			if (ep != null && ep.sheet == i && ep.row == row && ep.col == col) {
				// System.out.println("ep中的sheet编码：" + ep.sheet);
				fontSize = ep.FontSize;
				// System.out.println("row:" + row + ",col:" + col + ",size=" +
				// fontSize);
				break;
			}
		}
		if (fontSize == 0)
			fontSize = this.DEFAULT_FONTSIZE;
		return fontSize;
	}

	/**
	 * 设置sheet当中单元格是否为粗体
	 * 
	 * @param i
	 *            sheet编码
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @param Bold
	 *            是否为粗体
	 */
	public void setFontBold(int i, int row, int col, String Bold) {
		ExcelParam ep = new ExcelParam();
		ep.sheet = i;
		ep.row = row;
		ep.col = col;
		ep.Bold = Bold;
		boldArry.add(ep);
	}

	/**
	 * 设置sheet当中单元格是否为粗体,默认第一个sheet
	 * 
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @param Bold
	 *            是否为粗体
	 */
	public void setFontBold(int row, int col, String Bold) {
		this.setFontBold(0, row, col, Bold);
	}

	/**
	 * 取得单元格的字体是否为粗体
	 * 
	 * @author changliang
	 * @serialData 2007-08-13
	 * @param i
	 *            sheet编码
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @return 返回字体是否为粗体
	 */
	private String getFontBold(int i, int row, int col) {
		String fontBold = null;
		Iterator itMerge = boldArry.iterator();
		while (itMerge.hasNext()) {
			ExcelParam ep = (ExcelParam) itMerge.next();
			if (ep != null && ep.sheet == i && ep.row == row && ep.col == col) {
				// System.out.println("ep中的sheet编码：" + ep.sheet);
				fontBold = ep.Bold;
				// System.out.println("row:" + row + ",col:" + col + ",size=" +
				// fontBold);
				break;
			}
		}
		if (fontBold == null || fontBold.equals(""))
			fontBold = this.DEFAULT_BOLD;
		return fontBold;
	}

	/**
	 * 设置sheet当中单元格是否为斜体
	 * 
	 * @author changliang
	 * @serialData 2007-08-13
	 * @param i
	 *            sheet编码
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @param itaiic
	 *            是否斜体 true 斜体 false 不是斜体
	 */
	public void setFontItaiic(int i, int row, int col, boolean itaiic) {
		ExcelParam ep = new ExcelParam();
		ep.sheet = i;
		ep.row = row;
		ep.col = col;
		ep.itaiic = itaiic;
		itaiicArry.add(ep);
	}

	/**
	 * 设置sheet当中单元格是否为斜体 默认第一个sheet
	 * 
	 * @author changliang
	 * @serialData 2007-08-13
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @param itaiic
	 *            是否斜体 true 斜体 false 不是斜体
	 */
	public void setFontItaiic(int row, int col, boolean itaiic) {
		this.setFontItaiic(0, row, col, itaiic);
	}

	/**
	 * 取得单元格的字体是否为斜体
	 * 
	 * @param i
	 *            sheet编码
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @return 是否斜体 true 斜体 false 不是斜体
	 */
	private boolean getFontItaiic(int i, int row, int col) {
		boolean itaiic = false;
		Iterator itMerge = itaiicArry.iterator();
		while (itMerge.hasNext()) {
			ExcelParam ep = (ExcelParam) itMerge.next();
			if (ep != null && ep.sheet == i && ep.row == row && ep.col == col) {
				// System.out.println("ep中的sheet编码：" + ep.sheet);
				itaiic = ep.itaiic;
				// System.out.println("row:" + row + ",col:" + col + ",size=" +
				// itaiic);
				break;
			}
		}
		return itaiic;
	}

	/**
	 * 设置sheet当中单元格水平对齐
	 * 
	 * @param i
	 *            sheet编码
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @param align
	 *            水平对齐
	 */
	public void setFontAlign(int i, int row, int col, Alignment align) {
		ExcelParam ep = new ExcelParam();
		ep.sheet = i;
		ep.row = row;
		ep.col = col;
		ep.align = align;
		alignArry.add(ep);
	}

	/**
	 * 设置sheet当中单元格水平对齐 默认为第一个sheet
	 * 
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @param align
	 *            水平对齐
	 */
	public void setFontAlign(int row, int col, Alignment align) {
		this.setFontAlign(0, row, col, align);
	}

	/**
	 * 取得单元格的字体水平对齐
	 * 
	 * @param i
	 *            sheet 编码
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @return 单元格水平对齐
	 */
	private Alignment getFontAlign(int i, int row, int col) {
		Alignment align = null;
		Iterator itMerge = alignArry.iterator();
		while (itMerge.hasNext()) {
			ExcelParam ep = (ExcelParam) itMerge.next();
			if (ep != null && ep.sheet == i && ep.row == row && ep.col == col) {
				// System.out.println("ep中的sheet编码：" + ep.sheet);
				align = ep.align;
				// System.out.println("row:" + row + ",col:" + col + ",size=" +
				// align.getValue());
				break;
			}
		}
		if (align == null)
			align = this.DEFAULT_ALIGN;
		return align;
	}

	/**
	 * 设置sheet当中单元格中内容垂直对齐
	 * 
	 * @param i
	 *            sheet编码
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @param border
	 *            单元格中内容垂直对齐
	 */
	public void setFontBorder(int i, int row, int col, Border border) {
		// System.out.println("开始设置内容位置");
		ExcelParam ep = new ExcelParam();
		ep.sheet = i;
		ep.row = row;
		ep.col = col;
		ep.border = border;
		// System.out.println("设置结束");
		borderArry.add(ep);
	}

	/**
	 * 设置sheet当中单元格中内容垂直对齐 默认第一个sheet
	 * 
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @param border
	 *            单元格中内容垂直对齐
	 */
	public void setFontBorder(int row, int col, Border border) {
		this.setFontBorder(0, row, col, border);
	}

	/**
	 * 取得sheet当中单元格中内容垂直对齐
	 * 
	 * @param i
	 *            sheet编码
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @return 单元格中内容垂直对齐
	 */
	private Border getFontBorder(int i, int row, int col) {
		Border border = null;
		Iterator itMerge = borderArry.iterator();
		while (itMerge.hasNext()) {
			ExcelParam ep = (ExcelParam) itMerge.next();
			if (ep != null && ep.sheet == i && ep.row == row && ep.col == col) {
				// System.out.println("ep中的sheet编码：" + ep.sheet);
				border = ep.border;
				break;
			}
		}
		if (border == null)
			border = this.DEFAULT_BORDER;
		return border;
	}

	/**
	 * 设置sheet当中单元格边框格式
	 * 
	 * @param i
	 *            sheet编码
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @param bls
	 *            边框格式
	 */
	public void setBorderLineStyle(int i, int row, int col, BorderLineStyle bls) {
		ExcelParam ep = new ExcelParam();
		ep.sheet = i;
		ep.row = row;
		ep.col = col;
		ep.bls = bls;
		borderLineArry.add(ep);
	}

	/**
	 * 设置sheet当中单元格边框格式 默认第一个sheet
	 * 
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @param bls
	 *            边框格式
	 */
	public void setBorderLineStyle(int row, int col, BorderLineStyle bls) {
		this.setBorderLineStyle(0, row, col, bls);
	}

	/**
	 * 取得sheet当中单元格边框格式
	 * 
	 * @param i
	 *            sheet编码
	 * @param row
	 *            行
	 * @param col
	 *            列
	 */
	private BorderLineStyle getBorderLineStyle(int i, int row, int col) {
		BorderLineStyle bls = null;
		Iterator itMerge = borderLineArry.iterator();
		while (itMerge.hasNext()) {
			ExcelParam ep = (ExcelParam) itMerge.next();
			if (ep != null && ep.sheet == i && ep.row == row && ep.col == col) {
				bls = ep.bls;
				// System.out.println("row:" + row + ",col:" + col + ",size=" +
				// align.getValue());
				break;
			}
		}
		if (bls == null)
			bls = this.DEFAULT_BLS;
		return bls;
	}

	private WritableCellFormat getCellFormat(int i, int row, int col)
			throws WriteException // 得到行的样式
	{
		WritableFont wf = new WritableFont(this.getFontName(i, row, col),
				this.getFontSize(i, row, col), WritableFont.NO_BOLD,
				this.getFontItaiic(i, row, col));

		if (this.getFontBold(i, row, col).equals("Bold")) {
			wf = new WritableFont(this.getFontName(i, row, col),
					this.getFontSize(i, row, col), WritableFont.BOLD,
					this.getFontItaiic(i, row, col));
		}
		WritableCellFormat rowFormat = new WritableCellFormat(wf);
		rowFormat.setAlignment(this.getFontAlign(i, row, col));
		rowFormat.setBorder(this.getFontBorder(i, row, col),
				this.getBorderLineStyle(i, row, col));
		return rowFormat;
	}

	/**
	 * 设置sheet中标题的字体
	 * 
	 * @param i
	 *            sheet编码
	 * @param fn
	 *            字体
	 */
	public void setTitleFont(int i, FontName fn) {
		String strI = String.valueOf(i);
		if (titleFontMap.size() == 0 || titleFontMap.get(strI) == null) {
			titleFontMap.put(strI, fn);
		} else {
			titleFontMap.remove(strI);
			titleFontMap.put(strI, fn);
		}
	}

	/**
	 * 设置sheet中标题的字体,默认为第一个sheet
	 * 
	 * @param fn
	 *            字体
	 */
	public void setTitleFont(FontName fn) {
		this.setTitleFont(0, fn);
	}

	/**
	 * 得到sheet当中标题的字体
	 * 
	 * @param i
	 *            sheet编码
	 * @return 字体
	 */
	private FontName getTitleFont(int i) {
		FontName fn = (FontName) titleFontMap.get(String.valueOf(i));
		if (fn == null)
			fn = this.DEFAULT_FN;
		return fn;
	}

	/**
	 * 设置sheet中标题的字体大小
	 * 
	 * @param i
	 *            sheet编码
	 * @param size
	 *            字体大小
	 */
	public void setTitleFontSize(int i, int size) {
		String strI = String.valueOf(i);
		if (titleFontSizeMap.size() == 0 || titleFontSizeMap.get(strI) == null) {
			titleFontSizeMap.put(strI, String.valueOf(size));
		} else {
			titleFontSizeMap.remove(strI);
			titleFontSizeMap.put(strI, String.valueOf(size));
		}
	}

	/**
	 * 设置sheet中标题的字体大小,默认为第一个sheet
	 * 
	 * @param size
	 *            字体大小
	 */
	public void setTitleFontSize(int size) {
		this.setTitleFontSize(0, size);

	}

	/**
	 * 得到sheet当中标题的字体大小
	 * 
	 * @param i
	 *            sheet编码
	 * @return 字体大小
	 */
	private int getTitleFontSize(int i) {
		String size = (String) titleFontSizeMap.get(String.valueOf(i));
		int intSize = this.DEFAULT_FONTSIZE;
		if (size != null && !size.equals(""))
			intSize = Integer.parseInt(size);
		return intSize;
	}

	/**
	 * 设置sheet中标题的字体是否为粗体
	 * 
	 * @param i
	 *            sheet编码
	 * @param bold
	 *            字体是否为粗体
	 */
	public void setTitleBold(int i, String bold) {
		String strI = String.valueOf(i);
		if (titleBoldMap.size() == 0 || titleBoldMap.get(strI) == null) {
			titleBoldMap.put(strI, bold);
		} else {
			titleBoldMap.remove(strI);
			titleBoldMap.put(strI, bold);
		}
	}

	/**
	 * 设置sheet中标题的字体是否为粗体, 默认为第一个sheet
	 * 
	 * @param bold
	 *            字体是否为粗体
	 */
	public void setTitleBold(String bold) {
		this.setTitleBold(0, bold);
	}

	/**
	 * 得到sheet当中标题的字体是否为粗体
	 * 
	 * @param i
	 *            sheet编码
	 * @return 字体是否为粗体
	 */
	private String getTitleBold(int i) {
		String bold = (String) titleBoldMap.get(String.valueOf(i));
		if (bold == null || bold.equals(""))
			bold = this.DEFAULT_BOLD;
		return bold;
	}

	/**
	 * 设置sheet中标题水平对齐
	 * 
	 * @param i
	 *            sheet编码
	 * @param align
	 *            水平对齐
	 */
	public void setTitleAlign(int i, Alignment align) {
		String strI = String.valueOf(i);
		if (titleAlignMap.size() == 0 || titleAlignMap.get(strI) == null) {
			titleAlignMap.put(strI, align);
		} else {
			titleAlignMap.remove(strI);
			titleAlignMap.put(strI, align);
		}
	}

	/**
	 * 设置sheet中标题水平对齐 默认第一个sheet
	 * 
	 * @param align
	 *            水平对齐
	 */
	public void setTitleAlign(Alignment align) {
		this.setTitleAlign(0, align);
	}

	/**
	 * 得到sheet当中标题的字体水平对齐
	 * 
	 * @param i
	 *            sheet编码
	 * @return 字体水平对齐
	 */
	private Alignment getTitleAlign(int i) {
		Alignment align = (Alignment) titleAlignMap.get(String.valueOf(i));
		if (align == null)
			align = this.DEFAULT_ALIGN;
		return align;
	}

	/**
	 * 设置sheet中标题垂直对齐
	 * 
	 * @param i
	 *            sheet编码
	 * @param border
	 *            垂直对齐
	 */
	public void setTitleBorder(int i, Border border) {
		String strI = String.valueOf(i);
		if (titleBorderMap.size() == 0 || titleBorderMap.get(strI) == null) {
			titleBorderMap.put(strI, border);
		} else {
			titleBorderMap.remove(strI);
			titleBorderMap.put(strI, border);
		}
	}

	/**
	 * 设置sheet中标题垂直对齐 默认第一个sheet
	 * 
	 * @param border
	 */
	public void setTitleBorder(Border border) {
		this.setTitleBorder(0, border);
	}

	/**
	 * 得到sheet中标题垂直对齐
	 * 
	 * @param i
	 *            sheet编码
	 * @return 标题垂直对齐
	 */
	private Border getTitleBorder(int i) {
		Border border = (Border) titleBorderMap.get(String.valueOf(i));
		if (border == null)
			border = this.DEFAULT_BORDER;
		return border;
	}

	/**
	 * 设置sheet中标题的边框格式
	 * 
	 * @param i
	 *            sheet编码
	 * @param bls
	 *            边框格式
	 */
	public void setTitleBorderStyle(int i, BorderLineStyle bls) {
		String strI = String.valueOf(i);
		if (titleBLSMap.size() == 0 || titleBLSMap.get(strI) == null) {
			titleBLSMap.put(strI, bls);
		} else {
			titleBLSMap.remove(strI);
			titleBLSMap.put(strI, bls);
		}
	}

	/**
	 * 设置sheet中标题的边框格式 默认第一个sheet
	 * 
	 * @param bls
	 *            边框格式
	 */
	public void setTitleBorderStyle(BorderLineStyle bls) {
		this.setTitleBorderStyle(0, bls);
	}

	/**
	 * 得到sheet中标题的边框格式
	 * 
	 * @param i
	 *            sheet编码
	 * @return 边框格式
	 */
	private BorderLineStyle getTitleBorderStyle(int i) {
		BorderLineStyle bls = (BorderLineStyle) titleBLSMap.get(String
				.valueOf(i));
		if (bls == null)
			bls = this.DEFAULT_BLS;
		return bls;
	}

	private WritableCellFormat getTitleFormat(int i) throws WriteException// 得到列的样式
	{
		WritableFont wf = new WritableFont(this.getTitleFont(i),
				this.getTitleFontSize(i), WritableFont.NO_BOLD, false);
		if (this.getTitleBold(i).equals("Bold")) {
			wf = new WritableFont(this.getTitleFont(i),
					this.getTitleFontSize(i), WritableFont.BOLD, false);
		}
		WritableCellFormat titleFormat = new WritableCellFormat(wf);
		titleFormat.setAlignment(this.getTitleAlign(i));
		titleFormat.setBorder(this.getTitleBorder(i),
				this.getTitleBorderStyle(i));
	
		return titleFormat;
	}

	private WritableCellFormat getDataFormat() throws WriteException {
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.NO_BOLD, false);
		WritableCellFormat dataFormat = new WritableCellFormat(wf);
		dataFormat.setAlignment(Alignment.CENTRE);
		dataFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		return dataFormat;
	}

	/**
	 * 设置sheet数据内容中列输出为数字和数字格式
	 * 
	 * @param i
	 *            sheet编码
	 * @param col
	 *            列
	 * @param typeStyle
	 *            格式
	 */
	public void setDataColNumer(int i, int col, String numberStyle) {
		ExcelParam ep = new ExcelParam();
		ep.sheet = i;
		ep.col = col;
		
		ep.numberStyle = numberStyle;
		colNumArry.add(ep);
	}

	/**
	 * 设置sheet数据内容中列输出为数字和数字格式 ,默认为第一个sheet
	 * 
	 * @param col
	 *            列
	 * @param colType
	 *            格式
	 */
	public void setDataColNumer(int col, String numberStyle) {
		this.setDataColNumer(0, col, numberStyle);
	}

	/**
	 * 取得sheet数据内容中列输出为数字和数字格式
	 * 
	 * @param i
	 *            sheet编码
	 * @param col
	 *            列
	 * @return 数字和数字格式
	 */
	private String getDataColNumber(int i, int col) {
		String numberStyle = null;
		Iterator itMerge = colNumArry.iterator();
		while (itMerge.hasNext()) {
			ExcelParam ep = (ExcelParam) itMerge.next();
			if (ep != null && ep.sheet == i && ep.col == col) {
				// System.out.println("ep中的sheet编码：" + ep.sheet);
				numberStyle = ep.numberStyle;
				// System.out.println("col:" + col + ",number=" + numberStyle);
				break;
			}
		}
		return numberStyle;
	}

	/**
	 * 
	 * @param i
	 * @param col
	 * @return
	 * @throws WriteException
	 */
	private WritableCellFormat getNumberFormat(String numberF)
			throws WriteException {
		NumberFormat nf = new NumberFormat(numberF);
		WritableCellFormat numberFormat = new WritableCellFormat(nf);
		numberFormat.setAlignment(Alignment.CENTRE);
		numberFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		return numberFormat;
	}

	/**
	 * 
	 * @param i
	 * @param col
	 * @return
	 * @throws WriteException
	 */
	private WritableCellFormat getDateFormat(String numberF)
			throws WriteException {
		DateFormat df = new DateFormat(numberF);
		WritableCellFormat numberFormat = new WritableCellFormat(df);
		numberFormat.setAlignment(Alignment.CENTRE);
		numberFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		return numberFormat;
	}
	/**
	 * 
	 * 设置每一列的大小
	 * 
	 * @param i
	 *            显示在哪一个sheet
	 * @param size
	 *            每列的大小
	 * @param col
	 *            从哪一列开始设置
	 * @serialData 2008-08-08
	 */
	public void setColSize(int i, String[] size, int col) {
		String sheet = String.valueOf(i);
		ExcelParam ep = new ExcelParam();
		ep.size = size;
		ep.col = col;
		if (ColSizeMap.size() == 0 || ColSizeMap.get(sheet) == null) {
			ColSizeMap.put(sheet, ep);
		} else {
			ColSizeMap.remove(sheet);
			ColSizeMap.put(sheet, ep);
		}
	}

	/**
	 * 
	 * 设置每一列大小
	 * 
	 * @param i
	 *            显示在哪一个sheet
	 * @param size
	 *            每列的大小
	 * @serialData 2008-08-08
	 */
	public void setColSize(int i, String[] size) {
		this.setColSize(i, size, this.defutCol);
	}

	/**
	 * 设置每一列大小
	 * 
	 * @serialData 2007-08-08
	 * @param i
	 *            显示在哪一个sheet
	 * @return 返回每列大小
	 */
	private String[] getColSize(int i) {
		ExcelParam ep = (ExcelParam) ColSizeMap.get(String.valueOf(i));
		startcolSize = ep.col;
		return ep.size;
	}

	/**
	 * 设置sheet的标题信息
	 * 
	 * @author changliang
	 * @serialData 2007-08-08
	 * @param i
	 *            显示在那一个sheet
	 * @param title
	 *            标题内容
	 * @param row
	 *            显示在哪一行
	 * @param col
	 *            从哪一列开始显示
	 */
	public void setTitle(int i, String[] title, int row, int col) {
		String strI = String.valueOf(i);
		ExcelParam ep = new ExcelParam();
		ep.row = row;
		ep.title = title;
		ep.col = col;

		if (titleMap.size() == 0 || titleMap.get(strI) == null) {
			titleMap.put(strI, ep);
		} else {
			titleMap.remove(strI);
			titleMap.put(strI, ep);
		}
		// this.rowTitle=row; //设置title显示的行.
	}

	/**
	 * 
	 * 设置sheet的标题信息
	 * 
	 * @serialData 2007-08-08
	 * @param i
	 *            显示在那一个sheet
	 * @param title
	 *            标题内容
	 * @param row
	 *            显示在哪一行
	 * 
	 */
	public void setTitle(int i, String[] title, int row) {
		this.setTitle(i, title, row, this.defutCol);
	}

	/**
	 * 
	 * 设置sheet的标题信息
	 * 
	 * @serialData 2007-08-08
	 * @param i
	 *            显示在那一个sheet
	 * @param title
	 *            标题内容
	 * 
	 */
	public void setTitle(int i, String[] title) {
		this.setTitle(i, title, this.defutRow, this.defutCol);
	}

	/**
	 * 
	 * 设置sheet的标题信息 默认第一个sheet中的第0行和每0列开始
	 * 
	 * @serialData 2007-08-08
	 * @param title
	 *            标题内容
	 * 
	 */
	public void setTitle(String[] title) {
		this.setTitle(this.defutSheet, title, this.defutRow, this.defutCol);
	}

	/**
	 * 设置某一个sheet的合并单元格。
	 * 
	 * @author changliang
	 * @serialData 2007-08-09
	 * @param i
	 *            显示在哪一个sheet
	 * @param minCol
	 *            合并起始列
	 * @param minRow
	 *            合并起始行
	 * @param maxCol
	 *            合并终止列
	 * @param maxRow
	 *            合并终止行
	 * 
	 */
	public void setMergeCells(int i, int minCol, int minRow, int maxCol,
			int maxRow) {

		ExcelParam ep = new ExcelParam();
		ep.sheet = i;
		ep.minCol = minCol;
		ep.minRow = minRow;
		ep.maxCol = maxCol;
		ep.maxRow = maxRow;
		mergeArry.add(ep);
	}

	/**
	 * 设置合并单元格，默认在第0个sheet
	 * 
	 * @author changliang
	 * @serialData 2007-08-09
	 * @param minCol
	 *            合并起始列
	 * @param minRow
	 *            合并起始行
	 * @param maxCol
	 *            合并终止列
	 * @param maxRow
	 *            合并终止行
	 * 
	 */
	public void setMergeCells(int minCol, int minRow, int maxCol, int maxRow) {
		this.setMergeCells(this.defutSheet, minCol, minRow, maxCol, maxRow);
	}

	/**
	 * 根据sheet设置合并单元格。
	 * 
	 * @author changliang
	 * @serialData 2007-08-09
	 * @param sheet
	 *            需要合并单元格的sheet对象
	 * @param i
	 *            sheet编码
	 * @return 返回合并单元格是否成功
	 */
	private boolean MergeCells(WritableSheet sheet, int i) {
		Iterator itMerge = mergeArry.iterator();
		while (itMerge.hasNext()) {
			ExcelParam ep = (ExcelParam) itMerge.next();
			if (ep != null && ep.sheet == i) {
				// System.out.println("ep中的sheet编码：" + ep.sheet);
				try {
					sheet.mergeCells(ep.minCol, ep.minRow, ep.maxCol, ep.maxRow);
				} catch (Exception ex) {
					System.out.println("sheet" + i + "合并单元格时报错！");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 设置在哪个sheet，输出某个单元格信息。
	 * 
	 * @author changliang
	 * @serialData 2007-08-09
	 * @param i
	 *            显示在哪一个sheet
	 * @param row
	 *            输出在哪个行上面
	 * @param col
	 *            输出在哪个列上面
	 * @param content
	 *            输出内容
	 */
	public void setContent(int i, int row, int col, String content) {
		ExcelParam ep = new ExcelParam();
		ep.sheet = i;
		ep.row = row;
		ep.col = col;
		ep.content = content;
		contenArry.add(ep);
	}

	/**
	 * 设置单元格信息,默认输出在第一个sheet上面。
	 * 
	 * @author changliang
	 * @serialData 2007-08-09
	 * @param row
	 *            输出在哪个行上面
	 * @param col
	 *            输出在哪个列上面
	 * @param content
	 *            输出内容
	 */
	public void setContent(int row, int col, String content) {
		this.setContent(this.defutSheet, row, col, content);
	}

	/**
	 * 输出sheet当中的单元格
	 * 
	 * @author changliang
	 * @serialData 2007-08-09
	 * @param sheet
	 *            需要输出单元格信息的sheet对象
	 * @param i
	 *            sheet编码
	 * @return
	 * @throws WriteException
	 */
	private boolean creatContent(WritableSheet sheet, int i)
			throws WriteException {
		Iterator itContent = contenArry.iterator();
		while (itContent.hasNext()) {
			ExcelParam ep = (ExcelParam) itContent.next();
			if (ep != null && ep.sheet == i) {
				WritableCellFormat contFormat = this.getCellFormat(ep.sheet,
						ep.row, ep.col);
				Label contLabel = new Label(ep.col, ep.row, ep.content,
						contFormat);
				try {
					sheet.addCell(contLabel);
				} catch (Exception ex) {
					System.out.println("输出:" + ep.content + "时出错");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 设置Excel中有多少个sheet
	 * 
	 * @author changliang
	 * @serialData 2007-08-08
	 * @param sheet
	 *            Excel中需要输出的sheet列表
	 */
	public void setSheet(String[] sheet) {
		this.inSheet = sheet;
	}

	/**
	 * 得到Excel中有多少个sheet
	 * 
	 * @return
	 */
	public String[] getSheet() {
		return this.inSheet;
	}

	/**
	 * 设置Excel中需要显示的数据内容
	 * 
	 * @author changliang
	 * @serialData 2007-08-08
	 * @param i
	 *            数据要显示在哪一个sheet上面
	 * @param data
	 *            需要显示的内容
	 * @param row
	 *            从第几行开始显示
	 * @param col
	 *            从第几列开始显示
	 */
	public void setData(int i, String[][] data, int row, int col) {
		String strI = String.valueOf(i);
		ExcelParam ep = new ExcelParam();
		this.sData = data;
		ep.row = row;
		ep.col = col;
		ep.data = data;
		if (dataMap.size() == 0 || dataMap.get(strI) == null)
			dataMap.put(strI, ep);
		else {
			dataMap.remove(strI);
			dataMap.put(strI, ep);
		}
	}

	/**
	 * 设置Excel中需要显示的数据内容。（条件限制：合并列中同一数据的最少行数为2，不能存在单独数据行）
	 * 
	 * @author LuJJ
	 * @serialData 2009-12-28
	 * @param sheetNo
	 *            数据要显示在哪一个sheet上面
	 * @param data
	 *            待打印的数据
	 * @param rowBegin
	 *            数据显示起始行
	 * @param colBegin
	 *            数据显示起始列
	 * @param mergeCol
	 *            需要合并列数
	 * @param allwords
	 *            合并列中需要重复出现的用于统计的字符，以分号分割，可空。如：全国，合计
	 * 
	 */
	public void setDateByFixArrays(int sheetNo, String[][] data, int rowBegin,
			int colBegin, int mergeCol, String allwords) {

		String strI = String.valueOf(sheetNo);
		ExcelParam ep = new ExcelParam();
		ep.row = rowBegin;
		ep.col = colBegin + mergeCol;

		// 以合并列数为外遍历，再内遍历合并列的数据行
		for (int i = 0; i < mergeCol; i++) {
			// 取出要合并的数据，放入hashMap中，其key为合并行列值，value为合并行列坐标。同时以ArrayList来确定原数组存放顺序，以供遍历使用。
			HashMap tmpData = new HashMap();
			ArrayList tmpDataIndex = new ArrayList();
			int mergeIndex = 0;
			String[] words = null;
			if (allwords != null && allwords.length() != 0)
				words = allwords.split(";");
			for (int j = 0; j < data.length; j++) {
				if (tmpDataIndex == null || tmpDataIndex.size() == 0
						|| tmpDataIndex.isEmpty()) {
					tmpDataIndex.add(data[j][i]);
					tmpData.put(data[j][i], mergeIndex + "--" + j);
					mergeIndex = j;
				}
				if (allwords.indexOf(data[j][i]) != -1) {
					for (int k = 0; k < words.length; k++) {
						if (data[j][i].equals(words[k])) {
							mergeIndex = j;
							tmpDataIndex.add(data[j][i]);
							tmpData.put(data[j][i] + "--" + mergeIndex,
									mergeIndex + "--" + j);
						}
					}
				} else {
					if (!tmpDataIndex.contains(data[j][i])) {
						tmpDataIndex.add(data[j][i]);
						mergeIndex = j;
					} else {
						tmpData.put(data[j][i], mergeIndex + "--" + j);
					}
				}
			}
			// 遍历ArrayList，将其划分并开始合并
			int pos = 0;
			for (int j = 0; j < tmpDataIndex.size(); j++) {
				String mergeData = (String) tmpDataIndex.get(j);
				int mergeBeginRow = 0;
				int mergeEndRow = 0;
				if (allwords.indexOf(mergeData) != -1) {
					words = allwords.split(";");
					for (int k = 0; k < words.length; k++) {
						if (mergeData.equals(words[k])) {
							mergeBeginRow = pos + 1;
							mergeEndRow = pos + 1;
						}
					}
				} else {
					try {
						mergeBeginRow = Integer.parseInt(((String) tmpData
								.get(mergeData)).split("--")[0]);
						mergeEndRow = Integer.parseInt(((String) tmpData
								.get(mergeData)).split("--")[1]);
					} catch (NullPointerException e) {
						System.out.println("合并列应该为" + (mergeCol - 1) + "列。");
						throw e;
					}
				}
				pos = mergeEndRow;
				this.setMergeCells(sheetNo, i + colBegin, mergeBeginRow
						+ rowBegin, i + colBegin, mergeEndRow + rowBegin);
				this.setContent(sheetNo, mergeBeginRow + rowBegin,
						i + colBegin, mergeData);
				this.setCellFont(sheetNo, i + colBegin, mergeBeginRow
						+ rowBegin, ExcelFont.ARIAL);
				this.setFontSize(sheetNo, i + colBegin, mergeBeginRow
						+ rowBegin, 10);
				this.setFontAlign(sheetNo, mergeBeginRow + rowBegin, i
						+ colBegin, ExcelAlignment.CENTRE);
				this.setBorderLineStyle(sheetNo, mergeBeginRow + rowBegin, i
						+ colBegin, BorderLineStyle.THIN);
			}
		}
		// 将除了合并列部分外的剩余数组中数据放入新数组
		if (data.length > 0) {
			String[][] dateLastPart = new String[data.length][data[0].length
					- mergeCol];
			for (int i = 0; i < data.length; i++) {
				for (int j = mergeCol; j < data[i].length; j++) {
					dateLastPart[i][j - mergeCol] = data[i][j];
				}
			}
			ep.data = dateLastPart;
		} else {
			ep.data = data;
		}
		// ----此处只需setDate即可----

		if (dataMap.size() == 0 || dataMap.get(strI) == null)
			dataMap.put(strI, ep);
		else {
			dataMap.remove(strI);
			dataMap.put(strI, ep);
		}
	}

	/**
	 * 设置Excel中需要显示的数据内容
	 * 
	 * @author changliang
	 * @serialData 2007-08-08
	 * @param i
	 *            数据要显示在哪一个sheet上面
	 * @param data
	 *            需要显示的内容
	 * @param row
	 *            从第几行开始显示
	 */
	public void setData(int i, String[][] data, int row) {
		this.getTitle(i);
		int col = colTitle;// 如果不设置在哪一列显示，则自动和标题显示相同列
		this.setData(i, data, row, col);
	}

	/**
	 * 设置Excel中需要显示的数据内容
	 * 
	 * @author changliang
	 * @serialData 2007-08-08
	 * @param i
	 *            数据要显示在哪一个sheet上面
	 * @param data
	 *            需要显示的内容
	 * 
	 */
	public void setData(int i, String[][] data) {
		this.getTitle(i);
		int row = rowTitle + 1; // 如果不传入从哪一行开始显示，则自动显示在标题下一行。
		this.setData(i, data, row);
	}

	/**
	 * 设置Excel中需要显示的数据内容
	 * 
	 * @author changliang
	 * @serialData 2007-08-08
	 * @param data
	 *            需要显示的内容 默认显示在第1个sheet上面。
	 */
	public void setData(String[][] data) {
		this.setData(this.defutSheet, data);
	}

	/**
	 * 设置Excel文件输出的路径
	 * 
	 * @author changliang
	 * @serialData 2007-08-09
	 * @param filePath
	 *            Excel输出路径
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 得到Excel文件输出的路径
	 * 
	 * @author changliang
	 * @serialData 2007-08-09
	 * @return 得到Excel的输出路径
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * 得到sheet中需要显示的标题
	 * 
	 * @param i
	 *            sheet编码
	 * @return 标题内容
	 */
	private String[] getTitle(int i) // 得到某一个sheet标题信息
	{
		ExcelParam ep = (ExcelParam) titleMap.get(String.valueOf(i));
		rowTitle = ep.row;
		colTitle = ep.col;
		return ep.title;
	}

	/**
	 * 得到sheet中需要显示的内容
	 * 
	 * @param i
	 *            sheet编码
	 * @return 显示内容
	 */
	private String[][] getData(int i)// 得到某一个sheet的内容
	{
		ExcelParam ep = (ExcelParam) dataMap.get(String.valueOf(i));
		rowData = ep.row;
		colData = ep.col;
		return ep.data;
	}

	/**
	 * 设置sheet中显示的内容是有序号列输出 true 输出序号列 false 不输出序号列
	 * 
	 * @param i
	 *            sheet编码
	 * @param serisno
	 *            true 输出序号列 false 不输出序号列
	 */
	public void setIsSerisno(int i, boolean serisno) {
		Boolean ble = Boolean.valueOf(serisno);
		String strI = String.valueOf(i);
		if (serinoMap.size() == 0 || serinoMap.get(strI) == null) {
			serinoMap.put(strI, ble);
		} else {
			serinoMap.remove(strI);
			serinoMap.put(strI, ble);
		}
	}

	/**
	 * 设置sheet中显示的内容是有序号列输出 true 输出序号列 false 不输出序号列 默认第1个sheet
	 * 
	 * @param serisno
	 *            true 输出序号列 false 不输出序号列
	 */
	public void setIsSerisno(boolean serisno) {
		this.setIsSerisno(0, serisno);
	}

	/**
	 * 根据sheet编码得到是否要输出序号列
	 * 
	 * @param i
	 *            sheet编码
	 * @return
	 */
	private Boolean getIsSerisno(int i) {
		Boolean ble = (Boolean) serinoMap.get(String.valueOf(i));
		if (ble == null)
			ble = Boolean.FALSE;
		return ble;
	}

	/**
	 * 生成Excel
	 * 
	 * @return 是否生成成功
	 * @throws Exception
	 */
	public boolean createExcel() throws Exception {
		File mFile = new File(this.getFilePath());// 得到Excel创建的路径
		/**
		 * add by dongming 2009-03-04 判断问题件是否重复
		 */
		long lastModifiedtime = 0;
		if (mFile.isFile()) {
			lastModifiedtime = mFile.lastModified();
			if (System.currentTimeMillis() - lastModifiedtime < 10000) {
				return true;
			}
		}
		WritableWorkbook wtwb = Workbook.createWorkbook(mFile);// 新建Excel对象
		int length = inSheet.length;// 得到需要创建sheet的个数。
		for (int i = 0; i < length; i++)// 循环需要创建的sheet。
		{
			WritableSheet Sheet = wtwb.createSheet(inSheet[i], i);// 新建sheet
			Boolean ble = this.getIsSerisno(i);// 得到是否要输出序号列
			if (!this.MergeCells(Sheet, i))// 设置合并单元格。
				return false;
			if (!this.creatContent(Sheet, i))// 设置单元格输出内容。
				return false;
			String[] strTitle = this.getTitle(i);// 得到当前sheet的标题。
			String[] colSize = this.getColSize(i);// 得到当前sheet每列的大小。
			String[][] strData = this.getData(i);// 得到当前sheet需要显示的内容。
			if (ble == Boolean.TRUE) // 如果需要输出序号列
			{
				startcolSize = startcolSize + 1;
				colTitle = colTitle + 1;
				colData = colData + 1;
			}
			/****** 设置每一列大小 *********/
			if (colSize != null && !colSize.equals("")) {
				for (int j = 0; j < colSize.length; j++) {
					Sheet.setColumnView(j + startcolSize,
							Integer.parseInt(colSize[j]));// 设置每一列大小。
				}
			}
			/***** 开始创建标题 ***********/
			if (strTitle == null || strTitle.equals("")) {
				//System.out.println("请设置Sheet" + inSheet[i] + " 的标题信息！");
				return false;
			}
			WritableCellFormat titleFormat = this.getTitleFormat(i);// 得到标题的样式
			titleFormat.setWrap(true);// 设置表头内容可以自动换行--何宁20071012
			titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);// 设置表头内容垂直居中--何宁20071015
			if (ble == Boolean.TRUE) {
				Label laSerisno = new Label(colTitle - 1, rowTitle, "序号",
						titleFormat);
				Sheet.addCell(laSerisno);
			}
			for (int j = 0; j < strTitle.length; j++)// 循环需要写到Excel的标题.
			{

				Label laTitle = new Label(j + colTitle, rowTitle, strTitle[j],
						titleFormat);// 设置标题显示位置，显示内空，显示样式。
				Sheet.addCell(laTitle);
			}
			/******** 创建标题结束 **********/
			
			/******** 开始输出内容 **********/
			WritableCellFormat dataFormat = this.getDataFormat();
			dataFormat.setWrap(true);// 设置表正文内容可以自动换行--何宁20071012
			//System.out.println("数据行数：" + strData.length);
			for (int n = 0; n < strData.length; n++) {
				if (ble == Boolean.TRUE)// 是否输出序列号
				{
					Label laDataNo = new Label(colData - 1, n + rowData,
							String.valueOf(n + 1), dataFormat);
					Sheet.addCell(laDataNo);
				}
				for (int m = 0; m < strData[n].length; m++) {
					String numberF = this.getDataColNumber(i, m + colData);
					
					if (numberF == null || numberF.equals("")
							|| numberF.equals("null")) {
						
						Label laData = new Label(m + colData, rowData + n,
								strData[n][m], dataFormat);
						// System.out.println("文本");
						Sheet.addCell(laData);
					} else if("0".equals(numberF)){
						
						WritableCellFormat numberFormat = this
						.getNumberFormat(numberF);
						
						String data = strData[n][m];
						if (data == null || data.equals("")
								|| data.equals("null"))
							data = "0";
//						Number laNumb = new Number(m + colData, rowData + n,
//								Double.parseDouble(data));
						
						Number laNumb = new Number(m + colData, rowData + n,
								Double.parseDouble(data), numberFormat);
											
						Sheet.addCell(laNumb);
					}else if("yyyy/mm/dd".equals(numberF)){
						
						WritableCellFormat numberFormat = this
						.getDateFormat(numberF);
						
						String data = strData[n][m];
						System.out.println(data);
//						SimpleDateFormat in=new SimpleDateFormat("yyyy/MM/dd");
//						data = in.format(new Date(data));
						data=data.replace("-", "/");

						
						DateTime laNumb = new DateTime(m + colData, rowData + n,
								DateUtil.parseDate(data, "yyyy/MM/dd"), numberFormat);
					
						Sheet.addCell(laNumb);
					}
					
					else {
						WritableCellFormat numberFormat = this
								.getNumberFormat(numberF);
						String data = strData[n][m];
						if (data == null || data.equals("")
								|| data.equals("null"))
							data = "0";
						// System.out.println("列：" + m + "数字输出," + data + "----"
						// + numberF);
					
						Number laNumb = new Number(m + colData, rowData + n,
								Double.parseDouble(data), numberFormat);
						// System.out.println("数字");
						Sheet.addCell(laNumb);
						// System.out.println("数字列");
					}
				}
			}
		}
		wtwb.write();
		wtwb.close();
		this.settFile(mFile);
		return true;
	}

	public static void main(String args[]) {
	}
}
