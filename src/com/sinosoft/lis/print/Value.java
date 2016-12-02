package com.sinosoft.lis.print;

public class Value {
	private String[] values = null;
	private int maxSize = 100;
	private int add  = 30;
	public Value()
	{
		values = new String[maxSize];
	}
	public Value(int size) {
		maxSize = size;
		values = new String[maxSize];
	}
	
	public void addValue(int index,String value)
	{
		System.out.println("index��"+index);
		/**�Զ���������*/
		if(index>=maxSize)
		{
			System.out.println("����");
			maxSize = maxSize+add;
			String[] tValues = new String[maxSize];
			System.arraycopy(values, 0, tValues, 0, index);
			values = tValues;
		}
		values[index]=value;
	}
	
	public String getValue(int index)
	{
		String value = values[index];
		return value;
	}

}
