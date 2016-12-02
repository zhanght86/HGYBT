package com.sinosoft.lis.print;

import java.util.LinkedList;
import java.util.List;

public class Node {
	private List childends = new LinkedList();
	private String name;
	public Node(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}
	public List getChildends()
	{
		return childends;
	}
	public void addChild(Object obj)
	{
		childends.add(obj);
	}
	public String toString()
	{
		return this.name;
	}
}
