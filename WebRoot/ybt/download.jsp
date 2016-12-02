<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.io.*"%>
<%
	String path = request.getContextPath();
	String separator = System.getProperties().getProperty("file.separator");
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + separator;
	String realPath = request.getRealPath("/");//路径
	String message = "";
	String fileinName=request.getParameter("fileinName");
	String fileoutName=request.getParameter("fileoutName");
	try {
		
		response.setContentType("text/html");
		javax.servlet.ServletOutputStream ou = response
				.getOutputStream();
		String filepath = "";
		//filepath = "d:/a.txt";
		//String xiazaipath = String.valueOf(request.getRealPath("/file/"))+"\\";
		//System.out.println("xiazaipath = " + xiazaipath);
		if(!realPath.endsWith("/") && !realPath.endsWith("\\")){
			realPath += separator;
		}
		//File file = new File(realPath + filepath);//new File(basePath + filepath);
		File file = new File(fileinName);
		if(fileinName!=""){
			file = new File(fileinName);
			filepath=fileinName.substring(0,fileinName.lastIndexOf('/'));
		}else if(fileoutName!=""){
		    file = new File(fileoutName);
		    filepath=fileoutName.substring(0,fileoutName.lastIndexOf('/'));
		}
		
		System.out.println("file.getAbsolutePath() = " + file.getAbsolutePath());
		System.out.println("filepath = " + filepath);
		if (!file.exists()) {
			System.out.println(file.getAbsolutePath() + " 文件不存在!");
			message = "文件不存在!";
			return;
		}
		FileInputStream fileInputStream = new FileInputStream(file);
		out.clear();//清空输出流中的字节  zhangd 20091027
		BufferedOutputStream bos = new BufferedOutputStream(ou);
		if (filepath != null && filepath.length() > 0) {
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition",
					"attachment; filename="
							+ new String(file.getName().getBytes(
									"GBK"), "GBK") + "");
			try{
				byte[] b = new byte[1];
		        int intLength; 
		        while((intLength=fileInputStream.read(b))!=-1)
		        {
			          bos.write(b,0,intLength); 
		        }
				fileInputStream.close();
				bos.flush();
				bos.close();
			}catch(Exception e){
				System.out.println("取消附件下载");
			}finally{
				try{
					fileInputStream.close();
					ou.close();
					out.clear();
					out = pageContext.pushBody();
					//out = pageContext.forward("./TranLogRequest.jsp");
				}catch(Exception ex){
					
				}
				
			}

		}
	} catch (Exception e) {
		System.out.println("取消2");
		e.printStackTrace();
	}
%>
