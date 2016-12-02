package com.sinosoft.lis.print;

public class Cell {
	private String[] cell;
	public Cell(String[] cell)
	{
		this.cell = cell;
	}
	
	public String[] getCell()
	{
		return cell;
	}
	
	public boolean equals(Object obj)
	{
		if(obj instanceof Cell)
		{
			if(cell == null)
			{
				return false;
			}
			Cell des = (Cell)obj;
			for(int i=0;i<cell.length;i++)
			{
				if(cell[i]==null && des.getCell()[i] == null)
				{
					continue;
				}
				if(cell[i]==null && des.getCell()[i] != null)
				{
					return false;
				}
				if(!cell[i].equals(des.getCell()[i]))
					return false;
			}
			return true;
		}
		return false;
	}
}
