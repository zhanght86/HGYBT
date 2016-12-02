package com.sinosoft.lis.print;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

import com.sinosoft.lis.excel.CreatExcel;
import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelBorder;
import com.sinosoft.lis.excel.ExcelFont;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.StrTool;

public class MatrixPrint {
	
	private List printList = new LinkedList();
	
	private List<Cell> rowList = new LinkedList<Cell>();
	
	private Node root = new Node("root");
	/**要查询的SQL*/
	private String sql = "";
	/***列关系定义*/
	private String[] colTemp;
	/***行关系定义*/
	private String[] rowTemp;
	private String[] rowName;
	/**值字段定义*/
	private String[] value;
	private String[] valueName;
	
	private int startRow =0;
	private int startCol = 0;
	
	private CreatExcel creatExcel = null;
	
	public MatrixPrint(String sql,String[] row,String[] rowName,String[] col,String[] value,String[] valueName,CreatExcel creatExcel)
	{
		this.sql = sql;
		this.colTemp = col;
		this.rowTemp = row;
		this.rowName = rowName;
		this.value = value;
		this.valueName = valueName;
		this.creatExcel = creatExcel;
	}
	
	public void setStart(int startRow,int startCol)
	{
		this.startRow = startRow;
		this.startCol = startCol;
	}
	public void parse()
	{ 
		Connection con = DBConnPool.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		String[] tt = new String[rowTemp.length];
		for (int row = 0; row < rowTemp.length; row++) {
			tt[row] = rowName[row];
		}
		Cell cell = new Cell(tt);
		int dealNow = getCellIndex(cell);
		
		try {
			pstmt = con.prepareStatement(StrTool.GBKToUnicode(sql),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				tt = new String[rowTemp.length];
				for (int row = 0; row < rowTemp.length; row++) {
					tt[row] = rs.getString(rowTemp[row]);
				}
				cell = new Cell(tt);
				dealNow = getCellIndex(cell);

				Node tNode = root;
				for (int col = 0; col < colTemp.length; col++) {
					String colValue = rs.getString(colTemp[col]);
					tNode = getNode(tNode, colValue);
				}

				if (tNode.getChildends().size() == 0) {
					for (String ttS : valueName) {
						Value tValue = new Value();
						tValue.addValue(0, ttS);
						tNode.addChild(tValue);
					}
				}
				for (int k = 0; k < value.length; k++) {
					Value tValue = (Value) tNode.getChildends().get(k);
					String tColValue = rs.getString(value[k]);
					tValue.addValue(dealNow, tColValue);
				}
			}
		} catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} 
//		finally {
//			try {
//				con.close();
//			} catch (SQLException e) {
//				// TODO 自动生成 catch 块
//				e.printStackTrace();
//			}
//		}
		print();

	}
	
	private void print()
	{
		printNode(root,rowTemp.length+this.startCol,this.startRow);
		//System.out.println();
		int row=0;
		int maxrow=rowList.size()+1;
		String[][] print = new String[maxrow][printList.size()+rowTemp.length];
		for(Cell cell:rowList)
		{
			int col = 0;
			for(String value:cell.getCell())
			{
				//System.out.print(value+"\t");
				print[row][col] = value;
				col++;
			}
			print[maxrow-1][col-1] = "合计";
			for(Object obj :printList)
			{
				Value value = (Value)obj;
				String tValue = value.getValue(row);
				if(tValue==null || tValue.equals("null") || tValue.equals(""))
				{
					tValue = "0";
				}				
				//System.out.print(tValue+"\t");
				
				print[row][col] = tValue;
				
				if(row!=0)
				{
					String sumvalue = print[maxrow-1][col];
					if (sumvalue == null || sumvalue.equals("")) {
						sumvalue = "0";
					}
					 
					double tSumValue = Double.parseDouble(sumvalue);
					tSumValue += Double.parseDouble(tValue);
					if(tSumValue==0.0){
						print[maxrow-1][col]="";
					}
					print[maxrow-1][col] = getRound(tSumValue);
				}
				col++;
			}
			//System.out.println();
			row++;

		}
		this.creatExcel.setData(0, print, this.colTemp.length+this.startRow,this.startCol);
	}
	
	private String getRound(double  value){
      String t = "0.00";
      DecimalFormat tDF = new DecimalFormat(t);
      return tDF.format(value);
	}
	
	private Node getNode(Node parent,String name)
	{
		if(name==null)
		{
			name = "";
		}
		Node tNode =null;
		for(Object node:parent.getChildends())
		{
			Node tempNode = (Node)node;
			if(tempNode.getName().equals(name))
			{
				tNode = tempNode;
				break;
			}
		}
		if(tNode==null)
		{
			
			tNode = new Node(name);
			parent.addChild(tNode);
		}
		return tNode;
	}
	
	private int printNode(Node node,int nowCol,int nowRow)
	{
		int count = 0;//底下列的个数
		for (Object obj : node.getChildends()) {
			if (obj instanceof Value) {
				count++;
				printList.add(obj);
			} else {
				Node tNode = (Node) obj;
				//System.out.print(tNode + "\t");
				int tCount = printNode(tNode,nowCol,nowRow+1);
				setValue(nowRow,nowCol,tCount,tNode.toString());
				nowCol+=tCount;
				count+=tCount;
			}

		}
		return count;
	}
	
	private int getCellIndex(Cell cell)
	{
		int dealNow = rowList.indexOf(cell);
		if(dealNow==-1)
		{
			rowList.add(cell);
			dealNow = rowList.indexOf(cell);
		}
		return dealNow;
	}
	
	/**设置单元格式
	 * add liup 20090731
	 * size 合并的个数
	 * */
	private void setValue(int Row,int Col,int size,String Content) {
		creatExcel.setContent  (0, Row, Col, Content);
		creatExcel.setFontSize(0, Row, Col, 10);
		creatExcel.setFontBold(0, Row, Col, ExcelFont.Bold);
		creatExcel.setFontAlign(0, Row, Col, ExcelAlignment.CENTRE);
		creatExcel.setFontItaiic(0, Row, Col, false);
		creatExcel.setFontBorder(0, Row, Col, ExcelBorder.BOTTOM);
		if(size>1)
		{
			creatExcel.setMergeCells(Col, Row, Col+size-1, Row);
		}
		
	}
	


	
	
	public static void main(String[] aasd)
	{
	  String sql = "select a,b from (select agentcom as a,name as b from lacom)";
		
//		String[] col = new String[]{"agentcom","riskcode"};
//
//		String[] row = new String[]{"managecom","managename"};
//		
//		String[] rowName = new String[]{"分行代码","分行名称"};
//
//		String[] value = new String[]{"num","prem","charge"};
//		
//		String[] valueName = new String[]{"件数","保费","手续费"};
	  
		String[] col = new String[]{};

		String[] row = new String[]{"a","b"};
		
		String[] rowName = new String[]{"保险公司代码","保险公司名称"};

		String[] value = new String[]{};
		
		String[] valueName = new String[]{};
		
		CreatExcel creatExcel = new CreatExcel();
		creatExcel.setFilePath("c:\\aa\\test11.xls");
		String[] Sheet = { "分行代理保险业务统计表(按缴费方式)" };
		String[] title = {"1234"};
		String[] colSize = { "10"};// 设置列数和宽度(列数:1列)
		creatExcel.setTitleFontSize(6);
		creatExcel.setSheet(Sheet);
		creatExcel.setTitle(title);
		creatExcel.setColSize(0, colSize);
		
		
		MatrixPrint matrixPrint = new MatrixPrint(sql,row,rowName,col,value,valueName,creatExcel);
		matrixPrint.setStart(5, 2);
		matrixPrint.parse();
		
		try {
			creatExcel.createExcel();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}

}
