<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.io.*"%>
<%
	String path = request.getContextPath();
	String separator = System.getProperties().getProperty("file.separator");
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + separator;
	String realPath = request.getRealPath("/");//·��
	String content = "�ɹ�";
	String FlagStr="";
	try {
		
		response.setContentType("text/html");
		javax.servlet.ServletOutputStream ou = response
				.getOutputStream();
		
		String filepath = (String) request.getParameter("path");
		//filepath = "d:/a.txt";
		//String xiazaipath = String.valueOf(request.getRealPath("/file/"))+"\\";
		//System.out.println("xiazaipath = " + xiazaipath);
		if(!realPath.endsWith("/") && !realPath.endsWith("\\")){
			realPath += separator;
		}
		File file = new File(filepath);//new File(basePath + filepath);
		System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
		System.out.println("filepath = " + filepath);
		System.out.println("basePath = " + basePath);
		System.out.println("realPath = " + realPath);
		if (!file.exists()) {
			System.out.println(file.getAbsolutePath() + " �ļ�������!");
			content = "�ļ�������!";
		}
		System.out.println();
		FileInputStream fileInputStream = new FileInputStream(file);
		out.clear();//���������е��ֽ�  zhangd 20091027
		BufferedOutputStream bos = new BufferedOutputStream(ou);
		if (filepath != null && filepath.length() > 0) {
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition",
					"attachment; filename="+ new String(file.getName().getBytes(
									"GBK"), "ISO8859_1") + "");
			try{
				byte[] b = new byte[1];
		        int intLength; 
		        while((intLength=fileInputStream.read(b))>0)
		        {
					  String s = String.valueOf(Integer.valueOf(b[0]));
		        	  if(s!=null && "10".equals(s)){	
		        		  bos.write(13);    // �س�
		        		  bos.write(10);    
		        	  }else{
		        		  bos.write(b,0,intLength);
		        	  }  
		        }
				fileInputStream.close();
				bos.flush();
				bos.close();
			}catch(Exception e){
				System.out.println("ȡ����������");
			}finally{
				try{
					fileInputStream.close();
					ou.close();
					out.clear();
					out = pageContext.pushBody();
				}catch(Exception ex){
					
				}
				
			}

		}
	} catch (Exception e) {
		//System.out.println("ȡ��2");
		//e.printStackTrace();
		FlagStr = "Fail";
		content = "�ļ������ڣ���ȷ���ļ��Ƿ����";
	}
%>

<script language="javascript">
parent.fraInterface.afterSubmit("<%=FlagStr%>", "<%=content%>";


</script>
