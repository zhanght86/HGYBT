package com.sinosoft.utility;

import java.util.*;
import org.jdom.*;

import com.sinosoft.midplat.common.JdomUtil;

/**
 * <p>Title: 节点列表</p>
 *
 * <p>Description: </p>
 * 
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author chl
 * @version 1.0 
 */
public class ElementLis
    extends Element
{
  /**
   * <p>Title: 新建指定名称的节点列表</p>
   * <p>Description: </p>
   * @param name
   */
  public ElementLis(String name)
  {
    super(name);
  }

  public Element setText(String text)
  {
    final String nullstr="null";
    if(text==null||nullstr.equalsIgnoreCase(text))text="";
    super.setText(text);
    return null;
  }
  
  public ElementLis (String elementName,String elementValue){
	  
	  super(elementName);    
	  final String nullstr="null";{
	   if(elementValue==null||nullstr.equalsIgnoreCase(elementValue))elementValue="";
	    this.setText(elementValue);  
	  } 
	  this.setText(elementValue); 
  }  
  
  /**
   * <p>Title: 新建指定名称的节点加入到父节点</p>
   * <p>Description: </p>
   * @param elementName
   * @param parentElement
   */
  public ElementLis (String elementName,Element parentElement){
	  
	  super(elementName);    
	  parentElement.addContent(this);   
  }   
  
  /**
   * <p>Title: 新建指定名称的节点并赋值加入到父节点</p>
   * <p>Description: </p>
   * @param elementName 元素名称 
   * @param elementValue 元素值
   * @param parentElement 父元素
   */
  public ElementLis (String elementName,String elementValue,Element parentElement){
	  //新建指定元素名称的节点
	  super(elementName);    
	  //空字符串
	  final String nullstr="null";
	  //元素值为空、为忽略大小写的空字符串
	   if(elementValue==null||nullstr.equalsIgnoreCase(elementValue)){
		   //元素值置为空字符串
		   elementValue="";
	   }
	   //设置指定元素文本为元素值
	   this.setText(elementValue);
	   //加入到父元素
	   parentElement.addContent(this); 
  }     
  
  public ElementLis (String elementName,String attributeName,String attributeValue,ElementLis parentElement){
	  super(elementName); 
	
	   
	  if((attributeValue==null||attributeValue.equals(""))){
		  attributeValue = "";  
	  }     
	  this.setAttribute(attributeName,attributeValue);
	//  System.out.println("------1--------》》》"+this.getName()+">"+this.getAttributeValue(attributeName)+">"+this.getValue());
	  parentElement.addContent(this);
  
  } 
   
public ElementLis (String elementName,String elementValue,String attributeName,String attributeValue ,ElementLis parentElement){
	 super(elementName); 
			   
	  if((attributeValue==null||attributeValue.equals(""))){
		  attributeValue = "";   
	  } 
	  if((elementValue==null||elementValue.equals(""))){
		  elementValue = "";   
	  }  
	  this.setAttribute(attributeName,attributeValue);
	  this.setText(elementValue);  
	 // System.out.println("------1--------》》》"+this.getName()+">"+this.getAttributeValue(attributeName)+">"+this.getValue());
	  parentElement.addContent(this);
  }

public ElementLis (String holding,String party,String id,String holdingtype,String partyType,String relation,ElementLis parentElement){
	super("Relation"); 

	  
	  if(!((holding==null||holding.equals(""))
		 ||(party==null||party.equals(""))
		 ||(id==null||id.equals(""))
		 ||(holdingtype==null||holdingtype.equals(""))
		 ||(partyType==null||partyType.equals(""))))
		 { 
		  this.setAttribute("OriginatingObjectID",holding); 
		  this.setAttribute("RelatedObjectID",party); 
		  this.setAttribute("id",id); 

		  ElementLis OriginatingObjectType = new ElementLis("OriginatingObjectType",holdingtype,"tc",holdingtype,this);
		  ElementLis RelatedObjectType = new ElementLis("RelatedObjectType",partyType,"tc",partyType,this);
		  ElementLis RelationRoleCode = new ElementLis("RelationRoleCode",relation,"tc",relation,this);

	  } 	   
	  parentElement.addContent(this);
}
 /*   
  public ElementLis (String elementName,String attributeName,String attributeValue,ElementLis parentElement){
	  final String nullstr="null";
	  this.setName(elementName);  
	  if(!(attributeValue==null||nullstr.equalsIgnoreCase(attributeValue))){
		  Attribute attribute = new Attribute(attributeName,attributeValue);
		  attribute.setValue(attributeValue);
		  this.setAttribute(attribute);
		  parentElement.setContent(this);
	  }  	   
	 

  }
  
  public ElementLis (String elementName,String elementValue,ElementLis parentElement){
	  final String nullstr="null";
	  this.setName(elementName);
	  
	  if(elementValue==null||nullstr.equalsIgnoreCase(elementValue)){
		  elementValue="";
		  this.setText(elementValue);
	  }else{
		  this.setText(elementValue);
	  }
	  parentElement.setContent(this);
 
  }
  
  public ElementLis (String elementName,ElementLis parentElement){

	  this.setName(elementName);
	  parentElement.setContent(this);
 
  }

 */
  public static void main(String args[]){
/*	  ElementLis parentElement = new ElementLis("Trans");
	//  ElementLis elementLis1 = new ElementLis("TransType",null,"tc",null,parentElement);
	  System.out.println(elementLis1.getName());
	  System.out.println(elementLis1.getValue());
	  System.out.println(elementLis1.getAttributeValue("tc"));
	  System.out.println(elementLis1.getAttributes().toString());
	  System.out.println(elementLis1.getParentElement().getName());
	  
	//  ElementLis element = new ElementLis("Holding_1","Party_1","Relation_2","4","6","80",parentElement);
	*/
	  
	 
	  
  }

}
