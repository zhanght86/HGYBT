package com.sinosoft.midplat.ccb;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public   class   FindCheckFile   
  {   
          public   static   void   main(String[]   args)   
          {   
//                  if(args.length==0)   args=new   String[]{"c:\\Thread"};   
//                  try   
//                  {   
                          File pathName = new File("c:\\Thread");   
                          String[]   fileNames=pathName.list();    
                          System.out.println(fileNames.length);
                          //enumerate   all   files   in   the   directory   
                          for   (int   i=0;i<fileNames.length;i++)   
                          {   
                                  File   f=new   File(pathName.getPath(),fileNames[i]);  
                                  
                                  Pattern pattern = Pattern.compile("[0-9]{4}[A-Z]{3}20100111[0-9]{4}[0-9]{3}.i");
                                  Matcher matcher = pattern.matcher(f.getName());
                                  if(matcher.matches()){
                                	  System.out.println(f.getName());
                                  }
                                  
//                                  if(f.getName().matches("2008-12-20.htm")){
//                                	  System.out.println(f.getName());   
//                                  }
                                  
                                  //if   the   file   is   again   directory,call   the   main   method   recursively   
                                  //if(f.isDirectory())   
                                  //{   
                                  //       main(new   String[]{f.getPath()});   
                                  //}   
                          }   
//                  }   
//                  catch(IOException   e)   
//                  {   
//                          e.printStackTrace();   
//                  }   
          }   
  }  