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
	private HashMap rowcolorMap = new HashMap(); // ��������ɫ����
	private HashMap titleMap = new HashMap(); // ����Excel�ı�ͷ��ʾ��������
	private HashMap dataMap = new HashMap(); // ����Excel����ʾ����
	private HashMap ColSizeMap = new HashMap(); // �����д�С
	private ArrayList contenArry = new ArrayList(); // ���õ�Ԫ����ʾ����
	private ArrayList mergeArry = new ArrayList(); // ���õ�Ԫ��ϲ�
	private ArrayList fontArry = new ArrayList(); // ���õ�Ԫ����ʾ����
	private ArrayList fontSizeArry = new ArrayList(); // ���õ�Ԫ�������С
	private ArrayList boldArry = new ArrayList(); // ���õ�Ԫ�������Ƿ�Ϊ����
	private ArrayList itaiicArry = new ArrayList(); // ���õ�Ԫ����Ϊб��
	private ArrayList alignArry = new ArrayList(); // ���õ�Ԫ��ˮƽ����
	private ArrayList borderArry = new ArrayList(); // ���õ�Ԫ����������ʾλ��
	private ArrayList borderLineArry = new ArrayList();// ���õ�Ԫ��߿��ʽ
	private ArrayList colorArry = new ArrayList();// ���õ�Ԫ����ɫ��ʽ

	private HashMap titleFontMap = new HashMap();// ����title����
	private HashMap titleFontSizeMap = new HashMap();// ����title�����С
	private HashMap titleBoldMap = new HashMap();// ����title�����Ƿ�Ϊ����
	private HashMap titleItaiicMap = new HashMap();// ����title�����Ƿ�б��
	private HashMap titleAlignMap = new HashMap();// ����title����ˮƽ����
	private HashMap titleBorderMap = new HashMap(); // ����title������ʾλ��
	private HashMap titleBLSMap = new HashMap(); // ����title�ı߿��ʽ
	private HashMap serinoMap = new HashMap();// ������������Ƿ������

	private ArrayList colNumArry = new ArrayList(); // ����Ϊ�������

	private String[] inSheet = null;// ����Excel�м���sheet
	private String filePath = null;// ����Excel���·��
	private int rowTitle = 0; // ��¼��������п�ʼ
	private int colTitle = 0; // ��¼��������п�ʼ
	private int rowData = 0;// ��¼������ݴ����п�ʼ
	private int colData = 0;// ��¼������ݴ����п�ʼ
	private int startcolSize = 0;// ÿ�д�С���ô����п�ʼ

	private final int defutSheet = 0;
	private final int defutRow = 0;
	private final int defutCol = 0;
	/**** ���õ�Ԫ��Ĭ�ϸ�ʽ ****/
	private final FontName DEFAULT_FN = WritableFont.ARIAL; // ����Ĭ������
	private final Colour DEFAULT_COLOUR = Colour.BLACK; // ����Ĭ����ɫ
	private final UnderlineStyle DEFAULT_UNDERLINE = UnderlineStyle.NO_UNDERLINE;// ����Ĭ���»���
	private final int DEFAULT_FONTSIZE = 10;// ����Ĭ�������С
	private final boolean DEFAULT_ITAIIC = false;// ����Ĭ���Ƿ�б��
	private final Alignment DEFAULT_ALIGN = Alignment.LEFT;// ����Ĭ��ˮƽ����
	private final Border DEFAULT_BORDER = Border.ALL;// ������ʾ�ڵ�Ԫ���е�ʲôλ��
	private final BorderLineStyle DEFAULT_BLS = BorderLineStyle.NONE;// ���õ�Ԫ��Ĭ��û�б߿�
	private final String DEFAULT_BOLD = "No_Bold"; // ���õ�Ԫ���Ǵ���

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
	 * ����sheet���е�Ԫ�������
	 * 
	 * @param i
	 *            ��ʾ����һ��sheet
	 * @param col
	 *            ��
	 * @param row
	 *            ��
	 * @param fn
	 *            ����
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
	 * ����sheet���е�Ԫ�����ɫ
	 * 
	 * @param i
	 *            ��ʾ����һ��sheet
	 * @param col
	 *            ��
	 * @param row
	 *            ��
	 * @param fn
	 *            ����
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
	 * ����sheet���е�Ԫ�������,Ĭ�ϵ�һ��sheet
	 * 
	 * @author changliang
	 * @serialData 2007-08-10
	 * @param col
	 *            ��
	 * @param row
	 *            ��
	 * @param fn
	 *            ����
	 */
	public void setCellFont(int row, int col, FontName fn) {
		this.setCellFont(this.defutSheet, row, col, fn);
	}

	/**
	 * �õ�sheet����ĳһ�е�����,���û�з���Ĭ�����塣
	 * 
	 * @author changliang
	 * @serialData 2007-08-10
	 * @param i
	 *            sheet����
	 * @param row
	 *            ��
	 * @param col
	 *            ��
	 * @return ����
	 */
	private FontName getFontName(int i, int row, int col) {
		FontName fn = null;
		Iterator itMerge = fontArry.iterator();
		while (itMerge.hasNext()) {
			ExcelParam ep = (ExcelParam) itMerge.next();
			if (ep != null && ep.sheet == i && ep.row == row && ep.col == col) {
				// System.out.println("ep�е�sheet���룺" + ep.sheet);
				fn = ep.fn;
				break;
			}
		}
		if (fn == null)
			fn = this.DEFAULT_FN;
		return fn;
	}

	/**
	 * �õ�sheet����ĳһ�е���ɫ,���û�з���Ĭ����ɫ��
	 * 
	 * @author changliang
	 * @serialData 2007-08-10
	 * @param i
	 *            sheet����
	 * @param row
	 *            ��
	 * @param col
	 *            ��
	 * @return ����
	 */
	private Colour getCellColor(int i, int row, int col) {
		Colour tcellColour = null;
		Iterator itMerge = colorArry.iterator();
		while (itMerge.hasNext()) {
			ExcelParam ep = (ExcelParam) itMerge.next();
			if (ep != null && ep.sheet == i && ep.row == row && ep.col == col) {
				// System.out.println("ep�е�sheet���룺" + ep.sheet);
				tcellColour = ep.cellColour;
				break;
			}
		}
		if (tcellColour == null)
			tcellColour = this.DEFAULT_COLOUR;
		return tcellColour;
	}

	/**
	 * ����sheet���е�Ԫ��Ĵ�С
	 * 
	 * @author changliang
	 * @serialData 2007-08-10
	 * @param i
	 *            ��ʾ����һ��sheet
	 * @param col
	 *            ��
	 * @param row
	 *            ��
	 * @param fn
	 *            ��С
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
	 * ����sheet���е�Ԫ��Ĵ�С,Ĭ�ϵ�һ��sheet
	 * 
	 * @author changliang
	 * @serialData 2007-08-10
	 * @param row
	 *            ��
	 * @param col
	 *            ��
	 * @param fn
	 *            ��С
	 */
	public void setFontSize(int row, int col, int FontSize) {
		this.setFontSize(this.defutSheet, row, col, FontSize);
	}

	/**
	 * ȡ�õ�Ԫ��������С
	 * 
	 * @author changliang
	 * @serialData 2007-08-10
	 * @param i
	 *            sheet ����
	 * @param row
	 *            ��
	 * @param col
	 *            ��
	 * @return
	 */
	public int getFontSize(int i, int row, int col) {
		int fontSize = 0;
		Iterator itMerge = fontSizeArry.iterator();
		while (itMerge.hasNext()) {
			ExcelParam ep = (ExcelParam) itMerge.next();
			if (ep != null && ep.sheet == i && ep.row == row && ep.col == col) {
				// System.out.println("ep�е�sheet���룺" + ep.sheet);
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
	 * ����sheet���е�Ԫ���Ƿ�Ϊ����
	 * 
	 * @param i
	 *            sheet����
	 * @param row
	 *            ��
	 * @param col
	 *            ��
	 * @param Bold
	 *            �Ƿ�Ϊ����
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
	 * ����sheet���е�Ԫ���Ƿ�Ϊ����,Ĭ�ϵ�һ��sheet
	 * 
	 * @param row
	 *            ��
	 * @param col
	 *            ��
	 * @param Bold
	 *            �Ƿ�Ϊ����
	 */
	public void setFontBold(int row, int col, String Bold) {
		this.setFontBold(0, row, col, Bold);
	}

	/**
	 * ȡ�õ�Ԫ��������Ƿ�Ϊ����
	 * 
	 * @author changliang
	 * @serialData 2007-08-13
	 * @param i
	 *            sheet����
	 * @param row
	 *            ��
	 * @param col
	 *            ��
	 * @return ���������Ƿ�Ϊ����
	 */
	private String getFontBold(int i, int row, int col) {
		String fontBold = null;
		Iterator itMerge = boldArry.iterator();
		while (itMerge.hasNext()) {
			ExcelParam ep = (ExcelParam) itMerge.next();
			if (ep != null && ep.sheet == i && ep.row == row && ep.col == col) {
				// System.out.println("ep�е�sheet���룺" + ep.sheet);
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
	 * ����sheet���е�Ԫ���Ƿ�Ϊб��
	 * 
	 * @author changliang
	 * @serialData 2007-08-13
	 * @param i
	 *            sheet����
	 * @param row
	 *            ��
	 * @param col
	 *            ��
	 * @param itaiic
	 *            �Ƿ�б�� true б�� false ����б��
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
	 * ����sheet���е�Ԫ���Ƿ�Ϊб�� Ĭ�ϵ�һ��sheet
	 * 
	 * @author changliang
	 * @serialData 2007-08-13
	 * @param row
	 *            ��
	 * @param col
	 *            ��
	 * @param itaiic
	 *            �Ƿ�б�� true б�� false ����б��
	 */
	public void setFontItaiic(int row, int col, boolean itaiic) {
		this.setFontItaiic(0, row, col, itaiic);
	}

	/**
	 * ȡ�õ�Ԫ��������Ƿ�Ϊб��
	 * 
	 * @param i
	 *            sheet����
	 * @param row
	 *            ��
	 * @param col
	 *            ��
	 * @return �Ƿ�б�� true б�� false ����б��
	 */
	private boolean getFontItaiic(int i, int row, int col) {
		boolean itaiic = false;
		Iterator itMerge = itaiicArry.iterator();
		while (itMerge.hasNext()) {
			ExcelParam ep = (ExcelParam) itMerge.next();
			if (ep != null && ep.sheet == i && ep.row == row && ep.col == col) {
				// System.out.println("ep�е�sheet���룺" + ep.sheet);
				itaiic = ep.itaiic;
				// System.out.println("row:" + row + ",col:" + col + ",size=" +
				// itaiic);
				break;
			}
		}
		return itaiic;
	}

	/**
	 * ����sheet���е�Ԫ��ˮƽ����
	 * 
	 * @param i
	 *            sheet����
	 * @param row
	 *            ��
	 * @param col
	 *            ��
	 * @param align
	 *            ˮƽ����
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
	 * ����sheet���е�Ԫ��ˮƽ���� Ĭ��Ϊ��һ��sheet
	 * 
	 * @param row
	 *            ��
	 * @param col
	 *            ��
	 * @param align
	 *            ˮƽ����
	 */
	public void setFontAlign(int row, int col, Alignment align) {
		this.setFontAlign(0, row, col, align);
	}

	/**
	 * ȡ�õ�Ԫ�������ˮƽ����
	 * 
	 * @param i
	 *            sheet ����
	 * @param row
	 *            ��
	 * @param col
	 *            ��
	 * @return ��Ԫ��ˮƽ����
	 */
	private Alignment getFontAlign(int i, int row, int col) {
		Alignment align = null;
		Iterator itMerge = alignArry.iterator();
		while (itMerge.hasNext()) {
			ExcelParam ep = (ExcelParam) itMerge.next();
			if (ep != null && ep.sheet == i && ep.row == row && ep.col == col) {
				// System.out.println("ep�е�sheet���룺" + ep.sheet);
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
	 * ����sheet���е�Ԫ�������ݴ�ֱ����
	 * 
	 * @param i
	 *            sheet����
	 * @param row
	 *            ��
	 * @param col
	 *            ��
	 * @param border
	 *            ��Ԫ�������ݴ�ֱ����
	 */
	public void setFontBorder(int i, int row, int col, Border border) {
		// System.out.println("��ʼ��������λ��");
		ExcelParam ep = new ExcelParam();
		ep.sheet = i;
		ep.row = row;
		ep.col = col;
		ep.border = border;
		// System.out.println("���ý���");
		borderArry.add(ep);
	}

	/**
	 * ����sheet���е�Ԫ�������ݴ�ֱ���� Ĭ�ϵ�һ��sheet
	 * 
	 * @param row
	 *            ��
	 * @param col
	 *            ��
	 * @param border
	 *            ��Ԫ�������ݴ�ֱ����
	 */
	public void setFontBorder(int row, int col, Border border) {
		this.setFontBorder(0, row, col, border);
	}

	/**
	 * ȡ��sheet���е�Ԫ�������ݴ�ֱ����
	 * 
	 * @param i
	 *            sheet����
	 * @param row
	 *            ��
	 * @param col
	 *            ��
	 * @return ��Ԫ�������ݴ�ֱ����
	 */
	private Border getFontBorder(int i, int row, int col) {
		Border border = null;
		Iterator itMerge = borderArry.iterator();
		while (itMerge.hasNext()) {
			ExcelParam ep = (ExcelParam) itMerge.next();
			if (ep != null && ep.sheet == i && ep.row == row && ep.col == col) {
				// System.out.println("ep�е�sheet���룺" + ep.sheet);
				border = ep.border;
				break;
			}
		}
		if (border == null)
			border = this.DEFAULT_BORDER;
		return border;
	}

	/**
	 * ����sheet���е�Ԫ��߿��ʽ
	 * 
	 * @param i
	 *            sheet����
	 * @param row
	 *            ��
	 * @param col
	 *            ��
	 * @param bls
	 *            �߿��ʽ
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
	 * ����sheet���е�Ԫ��߿��ʽ Ĭ�ϵ�һ��sheet
	 * 
	 * @param row
	 *            ��
	 * @param col
	 *            ��
	 * @param bls
	 *            �߿��ʽ
	 */
	public void setBorderLineStyle(int row, int col, BorderLineStyle bls) {
		this.setBorderLineStyle(0, row, col, bls);
	}

	/**
	 * ȡ��sheet���е�Ԫ��߿��ʽ
	 * 
	 * @param i
	 *            sheet����
	 * @param row
	 *            ��
	 * @param col
	 *            ��
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
			throws WriteException // �õ��е���ʽ
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
	 * ����sheet�б��������
	 * 
	 * @param i
	 *            sheet����
	 * @param fn
	 *            ����
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
	 * ����sheet�б��������,Ĭ��Ϊ��һ��sheet
	 * 
	 * @param fn
	 *            ����
	 */
	public void setTitleFont(FontName fn) {
		this.setTitleFont(0, fn);
	}

	/**
	 * �õ�sheet���б��������
	 * 
	 * @param i
	 *            sheet����
	 * @return ����
	 */
	private FontName getTitleFont(int i) {
		FontName fn = (FontName) titleFontMap.get(String.valueOf(i));
		if (fn == null)
			fn = this.DEFAULT_FN;
		return fn;
	}

	/**
	 * ����sheet�б���������С
	 * 
	 * @param i
	 *            sheet����
	 * @param size
	 *            �����С
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
	 * ����sheet�б���������С,Ĭ��Ϊ��һ��sheet
	 * 
	 * @param size
	 *            �����С
	 */
	public void setTitleFontSize(int size) {
		this.setTitleFontSize(0, size);

	}

	/**
	 * �õ�sheet���б���������С
	 * 
	 * @param i
	 *            sheet����
	 * @return �����С
	 */
	private int getTitleFontSize(int i) {
		String size = (String) titleFontSizeMap.get(String.valueOf(i));
		int intSize = this.DEFAULT_FONTSIZE;
		if (size != null && !size.equals(""))
			intSize = Integer.parseInt(size);
		return intSize;
	}

	/**
	 * ����sheet�б���������Ƿ�Ϊ����
	 * 
	 * @param i
	 *            sheet����
	 * @param bold
	 *            �����Ƿ�Ϊ����
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
	 * ����sheet�б���������Ƿ�Ϊ����, Ĭ��Ϊ��һ��sheet
	 * 
	 * @param bold
	 *            �����Ƿ�Ϊ����
	 */
	public void setTitleBold(String bold) {
		this.setTitleBold(0, bold);
	}

	/**
	 * �õ�sheet���б���������Ƿ�Ϊ����
	 * 
	 * @param i
	 *            sheet����
	 * @return �����Ƿ�Ϊ����
	 */
	private String getTitleBold(int i) {
		String bold = (String) titleBoldMap.get(String.valueOf(i));
		if (bold == null || bold.equals(""))
			bold = this.DEFAULT_BOLD;
		return bold;
	}

	/**
	 * ����sheet�б���ˮƽ����
	 * 
	 * @param i
	 *            sheet����
	 * @param align
	 *            ˮƽ����
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
	 * ����sheet�б���ˮƽ���� Ĭ�ϵ�һ��sheet
	 * 
	 * @param align
	 *            ˮƽ����
	 */
	public void setTitleAlign(Alignment align) {
		this.setTitleAlign(0, align);
	}

	/**
	 * �õ�sheet���б��������ˮƽ����
	 * 
	 * @param i
	 *            sheet����
	 * @return ����ˮƽ����
	 */
	private Alignment getTitleAlign(int i) {
		Alignment align = (Alignment) titleAlignMap.get(String.valueOf(i));
		if (align == null)
			align = this.DEFAULT_ALIGN;
		return align;
	}

	/**
	 * ����sheet�б��ⴹֱ����
	 * 
	 * @param i
	 *            sheet����
	 * @param border
	 *            ��ֱ����
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
	 * ����sheet�б��ⴹֱ���� Ĭ�ϵ�һ��sheet
	 * 
	 * @param border
	 */
	public void setTitleBorder(Border border) {
		this.setTitleBorder(0, border);
	}

	/**
	 * �õ�sheet�б��ⴹֱ����
	 * 
	 * @param i
	 *            sheet����
	 * @return ���ⴹֱ����
	 */
	private Border getTitleBorder(int i) {
		Border border = (Border) titleBorderMap.get(String.valueOf(i));
		if (border == null)
			border = this.DEFAULT_BORDER;
		return border;
	}

	/**
	 * ����sheet�б���ı߿��ʽ
	 * 
	 * @param i
	 *            sheet����
	 * @param bls
	 *            �߿��ʽ
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
	 * ����sheet�б���ı߿��ʽ Ĭ�ϵ�һ��sheet
	 * 
	 * @param bls
	 *            �߿��ʽ
	 */
	public void setTitleBorderStyle(BorderLineStyle bls) {
		this.setTitleBorderStyle(0, bls);
	}

	/**
	 * �õ�sheet�б���ı߿��ʽ
	 * 
	 * @param i
	 *            sheet����
	 * @return �߿��ʽ
	 */
	private BorderLineStyle getTitleBorderStyle(int i) {
		BorderLineStyle bls = (BorderLineStyle) titleBLSMap.get(String
				.valueOf(i));
		if (bls == null)
			bls = this.DEFAULT_BLS;
		return bls;
	}

	private WritableCellFormat getTitleFormat(int i) throws WriteException// �õ��е���ʽ
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
	 * ����sheet���������������Ϊ���ֺ����ָ�ʽ
	 * 
	 * @param i
	 *            sheet����
	 * @param col
	 *            ��
	 * @param typeStyle
	 *            ��ʽ
	 */
	public void setDataColNumer(int i, int col, String numberStyle) {
		ExcelParam ep = new ExcelParam();
		ep.sheet = i;
		ep.col = col;
		
		ep.numberStyle = numberStyle;
		colNumArry.add(ep);
	}

	/**
	 * ����sheet���������������Ϊ���ֺ����ָ�ʽ ,Ĭ��Ϊ��һ��sheet
	 * 
	 * @param col
	 *            ��
	 * @param colType
	 *            ��ʽ
	 */
	public void setDataColNumer(int col, String numberStyle) {
		this.setDataColNumer(0, col, numberStyle);
	}

	/**
	 * ȡ��sheet���������������Ϊ���ֺ����ָ�ʽ
	 * 
	 * @param i
	 *            sheet����
	 * @param col
	 *            ��
	 * @return ���ֺ����ָ�ʽ
	 */
	private String getDataColNumber(int i, int col) {
		String numberStyle = null;
		Iterator itMerge = colNumArry.iterator();
		while (itMerge.hasNext()) {
			ExcelParam ep = (ExcelParam) itMerge.next();
			if (ep != null && ep.sheet == i && ep.col == col) {
				// System.out.println("ep�е�sheet���룺" + ep.sheet);
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
	 * ����ÿһ�еĴ�С
	 * 
	 * @param i
	 *            ��ʾ����һ��sheet
	 * @param size
	 *            ÿ�еĴ�С
	 * @param col
	 *            ����һ�п�ʼ����
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
	 * ����ÿһ�д�С
	 * 
	 * @param i
	 *            ��ʾ����һ��sheet
	 * @param size
	 *            ÿ�еĴ�С
	 * @serialData 2008-08-08
	 */
	public void setColSize(int i, String[] size) {
		this.setColSize(i, size, this.defutCol);
	}

	/**
	 * ����ÿһ�д�С
	 * 
	 * @serialData 2007-08-08
	 * @param i
	 *            ��ʾ����һ��sheet
	 * @return ����ÿ�д�С
	 */
	private String[] getColSize(int i) {
		ExcelParam ep = (ExcelParam) ColSizeMap.get(String.valueOf(i));
		startcolSize = ep.col;
		return ep.size;
	}

	/**
	 * ����sheet�ı�����Ϣ
	 * 
	 * @author changliang
	 * @serialData 2007-08-08
	 * @param i
	 *            ��ʾ����һ��sheet
	 * @param title
	 *            ��������
	 * @param row
	 *            ��ʾ����һ��
	 * @param col
	 *            ����һ�п�ʼ��ʾ
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
		// this.rowTitle=row; //����title��ʾ����.
	}

	/**
	 * 
	 * ����sheet�ı�����Ϣ
	 * 
	 * @serialData 2007-08-08
	 * @param i
	 *            ��ʾ����һ��sheet
	 * @param title
	 *            ��������
	 * @param row
	 *            ��ʾ����һ��
	 * 
	 */
	public void setTitle(int i, String[] title, int row) {
		this.setTitle(i, title, row, this.defutCol);
	}

	/**
	 * 
	 * ����sheet�ı�����Ϣ
	 * 
	 * @serialData 2007-08-08
	 * @param i
	 *            ��ʾ����һ��sheet
	 * @param title
	 *            ��������
	 * 
	 */
	public void setTitle(int i, String[] title) {
		this.setTitle(i, title, this.defutRow, this.defutCol);
	}

	/**
	 * 
	 * ����sheet�ı�����Ϣ Ĭ�ϵ�һ��sheet�еĵ�0�к�ÿ0�п�ʼ
	 * 
	 * @serialData 2007-08-08
	 * @param title
	 *            ��������
	 * 
	 */
	public void setTitle(String[] title) {
		this.setTitle(this.defutSheet, title, this.defutRow, this.defutCol);
	}

	/**
	 * ����ĳһ��sheet�ĺϲ���Ԫ��
	 * 
	 * @author changliang
	 * @serialData 2007-08-09
	 * @param i
	 *            ��ʾ����һ��sheet
	 * @param minCol
	 *            �ϲ���ʼ��
	 * @param minRow
	 *            �ϲ���ʼ��
	 * @param maxCol
	 *            �ϲ���ֹ��
	 * @param maxRow
	 *            �ϲ���ֹ��
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
	 * ���úϲ���Ԫ��Ĭ���ڵ�0��sheet
	 * 
	 * @author changliang
	 * @serialData 2007-08-09
	 * @param minCol
	 *            �ϲ���ʼ��
	 * @param minRow
	 *            �ϲ���ʼ��
	 * @param maxCol
	 *            �ϲ���ֹ��
	 * @param maxRow
	 *            �ϲ���ֹ��
	 * 
	 */
	public void setMergeCells(int minCol, int minRow, int maxCol, int maxRow) {
		this.setMergeCells(this.defutSheet, minCol, minRow, maxCol, maxRow);
	}

	/**
	 * ����sheet���úϲ���Ԫ��
	 * 
	 * @author changliang
	 * @serialData 2007-08-09
	 * @param sheet
	 *            ��Ҫ�ϲ���Ԫ���sheet����
	 * @param i
	 *            sheet����
	 * @return ���غϲ���Ԫ���Ƿ�ɹ�
	 */
	private boolean MergeCells(WritableSheet sheet, int i) {
		Iterator itMerge = mergeArry.iterator();
		while (itMerge.hasNext()) {
			ExcelParam ep = (ExcelParam) itMerge.next();
			if (ep != null && ep.sheet == i) {
				// System.out.println("ep�е�sheet���룺" + ep.sheet);
				try {
					sheet.mergeCells(ep.minCol, ep.minRow, ep.maxCol, ep.maxRow);
				} catch (Exception ex) {
					System.out.println("sheet" + i + "�ϲ���Ԫ��ʱ����");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * �������ĸ�sheet�����ĳ����Ԫ����Ϣ��
	 * 
	 * @author changliang
	 * @serialData 2007-08-09
	 * @param i
	 *            ��ʾ����һ��sheet
	 * @param row
	 *            ������ĸ�������
	 * @param col
	 *            ������ĸ�������
	 * @param content
	 *            �������
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
	 * ���õ�Ԫ����Ϣ,Ĭ������ڵ�һ��sheet���档
	 * 
	 * @author changliang
	 * @serialData 2007-08-09
	 * @param row
	 *            ������ĸ�������
	 * @param col
	 *            ������ĸ�������
	 * @param content
	 *            �������
	 */
	public void setContent(int row, int col, String content) {
		this.setContent(this.defutSheet, row, col, content);
	}

	/**
	 * ���sheet���еĵ�Ԫ��
	 * 
	 * @author changliang
	 * @serialData 2007-08-09
	 * @param sheet
	 *            ��Ҫ�����Ԫ����Ϣ��sheet����
	 * @param i
	 *            sheet����
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
					System.out.println("���:" + ep.content + "ʱ����");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * ����Excel���ж��ٸ�sheet
	 * 
	 * @author changliang
	 * @serialData 2007-08-08
	 * @param sheet
	 *            Excel����Ҫ�����sheet�б�
	 */
	public void setSheet(String[] sheet) {
		this.inSheet = sheet;
	}

	/**
	 * �õ�Excel���ж��ٸ�sheet
	 * 
	 * @return
	 */
	public String[] getSheet() {
		return this.inSheet;
	}

	/**
	 * ����Excel����Ҫ��ʾ����������
	 * 
	 * @author changliang
	 * @serialData 2007-08-08
	 * @param i
	 *            ����Ҫ��ʾ����һ��sheet����
	 * @param data
	 *            ��Ҫ��ʾ������
	 * @param row
	 *            �ӵڼ��п�ʼ��ʾ
	 * @param col
	 *            �ӵڼ��п�ʼ��ʾ
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
	 * ����Excel����Ҫ��ʾ���������ݡ����������ƣ��ϲ�����ͬһ���ݵ���������Ϊ2�����ܴ��ڵ��������У�
	 * 
	 * @author LuJJ
	 * @serialData 2009-12-28
	 * @param sheetNo
	 *            ����Ҫ��ʾ����һ��sheet����
	 * @param data
	 *            ����ӡ������
	 * @param rowBegin
	 *            ������ʾ��ʼ��
	 * @param colBegin
	 *            ������ʾ��ʼ��
	 * @param mergeCol
	 *            ��Ҫ�ϲ�����
	 * @param allwords
	 *            �ϲ�������Ҫ�ظ����ֵ�����ͳ�Ƶ��ַ����Էֺŷָ�ɿա��磺ȫ�����ϼ�
	 * 
	 */
	public void setDateByFixArrays(int sheetNo, String[][] data, int rowBegin,
			int colBegin, int mergeCol, String allwords) {

		String strI = String.valueOf(sheetNo);
		ExcelParam ep = new ExcelParam();
		ep.row = rowBegin;
		ep.col = colBegin + mergeCol;

		// �Ժϲ�����Ϊ����������ڱ����ϲ��е�������
		for (int i = 0; i < mergeCol; i++) {
			// ȡ��Ҫ�ϲ������ݣ�����hashMap�У���keyΪ�ϲ�����ֵ��valueΪ�ϲ��������ꡣͬʱ��ArrayList��ȷ��ԭ������˳���Թ�����ʹ�á�
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
			// ����ArrayList�����仮�ֲ���ʼ�ϲ�
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
						System.out.println("�ϲ���Ӧ��Ϊ" + (mergeCol - 1) + "�С�");
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
		// �����˺ϲ��в������ʣ�����������ݷ���������
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
		// ----�˴�ֻ��setDate����----

		if (dataMap.size() == 0 || dataMap.get(strI) == null)
			dataMap.put(strI, ep);
		else {
			dataMap.remove(strI);
			dataMap.put(strI, ep);
		}
	}

	/**
	 * ����Excel����Ҫ��ʾ����������
	 * 
	 * @author changliang
	 * @serialData 2007-08-08
	 * @param i
	 *            ����Ҫ��ʾ����һ��sheet����
	 * @param data
	 *            ��Ҫ��ʾ������
	 * @param row
	 *            �ӵڼ��п�ʼ��ʾ
	 */
	public void setData(int i, String[][] data, int row) {
		this.getTitle(i);
		int col = colTitle;// �������������һ����ʾ�����Զ��ͱ�����ʾ��ͬ��
		this.setData(i, data, row, col);
	}

	/**
	 * ����Excel����Ҫ��ʾ����������
	 * 
	 * @author changliang
	 * @serialData 2007-08-08
	 * @param i
	 *            ����Ҫ��ʾ����һ��sheet����
	 * @param data
	 *            ��Ҫ��ʾ������
	 * 
	 */
	public void setData(int i, String[][] data) {
		this.getTitle(i);
		int row = rowTitle + 1; // ������������һ�п�ʼ��ʾ�����Զ���ʾ�ڱ�����һ�С�
		this.setData(i, data, row);
	}

	/**
	 * ����Excel����Ҫ��ʾ����������
	 * 
	 * @author changliang
	 * @serialData 2007-08-08
	 * @param data
	 *            ��Ҫ��ʾ������ Ĭ����ʾ�ڵ�1��sheet���档
	 */
	public void setData(String[][] data) {
		this.setData(this.defutSheet, data);
	}

	/**
	 * ����Excel�ļ������·��
	 * 
	 * @author changliang
	 * @serialData 2007-08-09
	 * @param filePath
	 *            Excel���·��
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * �õ�Excel�ļ������·��
	 * 
	 * @author changliang
	 * @serialData 2007-08-09
	 * @return �õ�Excel�����·��
	 */
	public String getFilePath() {
		return this.filePath;
	}

	/**
	 * �õ�sheet����Ҫ��ʾ�ı���
	 * 
	 * @param i
	 *            sheet����
	 * @return ��������
	 */
	private String[] getTitle(int i) // �õ�ĳһ��sheet������Ϣ
	{
		ExcelParam ep = (ExcelParam) titleMap.get(String.valueOf(i));
		rowTitle = ep.row;
		colTitle = ep.col;
		return ep.title;
	}

	/**
	 * �õ�sheet����Ҫ��ʾ������
	 * 
	 * @param i
	 *            sheet����
	 * @return ��ʾ����
	 */
	private String[][] getData(int i)// �õ�ĳһ��sheet������
	{
		ExcelParam ep = (ExcelParam) dataMap.get(String.valueOf(i));
		rowData = ep.row;
		colData = ep.col;
		return ep.data;
	}

	/**
	 * ����sheet����ʾ������������������ true �������� false ����������
	 * 
	 * @param i
	 *            sheet����
	 * @param serisno
	 *            true �������� false ����������
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
	 * ����sheet����ʾ������������������ true �������� false ���������� Ĭ�ϵ�1��sheet
	 * 
	 * @param serisno
	 *            true �������� false ����������
	 */
	public void setIsSerisno(boolean serisno) {
		this.setIsSerisno(0, serisno);
	}

	/**
	 * ����sheet����õ��Ƿ�Ҫ��������
	 * 
	 * @param i
	 *            sheet����
	 * @return
	 */
	private Boolean getIsSerisno(int i) {
		Boolean ble = (Boolean) serinoMap.get(String.valueOf(i));
		if (ble == null)
			ble = Boolean.FALSE;
		return ble;
	}

	/**
	 * ����Excel
	 * 
	 * @return �Ƿ����ɳɹ�
	 * @throws Exception
	 */
	public boolean createExcel() throws Exception {
		File mFile = new File(this.getFilePath());// �õ�Excel������·��
		/**
		 * add by dongming 2009-03-04 �ж�������Ƿ��ظ�
		 */
		long lastModifiedtime = 0;
		if (mFile.isFile()) {
			lastModifiedtime = mFile.lastModified();
			if (System.currentTimeMillis() - lastModifiedtime < 10000) {
				return true;
			}
		}
		WritableWorkbook wtwb = Workbook.createWorkbook(mFile);// �½�Excel����
		int length = inSheet.length;// �õ���Ҫ����sheet�ĸ�����
		for (int i = 0; i < length; i++)// ѭ����Ҫ������sheet��
		{
			WritableSheet Sheet = wtwb.createSheet(inSheet[i], i);// �½�sheet
			Boolean ble = this.getIsSerisno(i);// �õ��Ƿ�Ҫ��������
			if (!this.MergeCells(Sheet, i))// ���úϲ���Ԫ��
				return false;
			if (!this.creatContent(Sheet, i))// ���õ�Ԫ��������ݡ�
				return false;
			String[] strTitle = this.getTitle(i);// �õ���ǰsheet�ı��⡣
			String[] colSize = this.getColSize(i);// �õ���ǰsheetÿ�еĴ�С��
			String[][] strData = this.getData(i);// �õ���ǰsheet��Ҫ��ʾ�����ݡ�
			if (ble == Boolean.TRUE) // �����Ҫ��������
			{
				startcolSize = startcolSize + 1;
				colTitle = colTitle + 1;
				colData = colData + 1;
			}
			/****** ����ÿһ�д�С *********/
			if (colSize != null && !colSize.equals("")) {
				for (int j = 0; j < colSize.length; j++) {
					Sheet.setColumnView(j + startcolSize,
							Integer.parseInt(colSize[j]));// ����ÿһ�д�С��
				}
			}
			/***** ��ʼ�������� ***********/
			if (strTitle == null || strTitle.equals("")) {
				//System.out.println("������Sheet" + inSheet[i] + " �ı�����Ϣ��");
				return false;
			}
			WritableCellFormat titleFormat = this.getTitleFormat(i);// �õ��������ʽ
			titleFormat.setWrap(true);// ���ñ�ͷ���ݿ����Զ�����--����20071012
			titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);// ���ñ�ͷ���ݴ�ֱ����--����20071015
			if (ble == Boolean.TRUE) {
				Label laSerisno = new Label(colTitle - 1, rowTitle, "���",
						titleFormat);
				Sheet.addCell(laSerisno);
			}
			for (int j = 0; j < strTitle.length; j++)// ѭ����Ҫд��Excel�ı���.
			{

				Label laTitle = new Label(j + colTitle, rowTitle, strTitle[j],
						titleFormat);// ���ñ�����ʾλ�ã���ʾ�ڿգ���ʾ��ʽ��
				Sheet.addCell(laTitle);
			}
			/******** ����������� **********/
			
			/******** ��ʼ������� **********/
			WritableCellFormat dataFormat = this.getDataFormat();
			dataFormat.setWrap(true);// ���ñ��������ݿ����Զ�����--����20071012
			//System.out.println("����������" + strData.length);
			for (int n = 0; n < strData.length; n++) {
				if (ble == Boolean.TRUE)// �Ƿ�������к�
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
						// System.out.println("�ı�");
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
						// System.out.println("�У�" + m + "�������," + data + "----"
						// + numberF);
					
						Number laNumb = new Number(m + colData, rowData + n,
								Double.parseDouble(data), numberFormat);
						// System.out.println("����");
						Sheet.addCell(laNumb);
						// System.out.println("������");
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
