  
<%
//�������ƣ�
//�����ܣ�ҳ����¶���
//�������ڣ�2002-03-12
//������  ��yt
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page import="java.io.File"%>

<%

String ShowJspPage   = request.getParameter("pageName");           //��ǰ���ֵı༭ҳ��
                                                        //��ǰϵͳ��ʱ��Ϊ��ҳ��

//����ض��򵽶�Ӧ���ֵı༭ҳ�� 
//String PathName = ( new File(request.getPathTranslated()) ).getParent();     //ȡ��ǰ·��
//File inputFile  = new File(PathName,ShowJspPage);                            //�õ���Ӧ���ļ�����

//if( inputFile.exists() )
//{
//  System.out.println(ShowJspPage);
  System.out.println(ShowJspPage);
  response.sendRedirect(ShowJspPage);
//}
//else 
//{    
//  String Content = "û����Ӧ��ҳ��" ;
//%>  
                         
