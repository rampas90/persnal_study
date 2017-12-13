<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ page import = "java.sql.*" %>
<%@ page import = "java.lang.*" %>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>action</title>

</head>
<body>

 
<%@include file="dbcon.jsp"%>
<%
	request.setCharacterEncoding("utf-8");

	
	String method_chk = request.getMethod();
	
	if(method_chk.equals("POST")){

	String idx_str = request.getParameter("idx");
	String title = request.getParameter("title");
	String contents = request.getParameter("contents");
	String pw = request.getParameter("pw");
	String mode = request.getParameter("mode");
	

	if(mode.equals("modify")){

		try{	

			String sql = "select * from board where idx = "+idx_str;
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while(rs.next()){
				int idx = rs.getInt("idx");
				String db_pass = rs.getString("password");
				
				
				
				if(pw.equals(db_pass)){
					
					String sql_m = "update board set title=?, contents=? where idx = "+idx_str;


					pstmt = conn.prepareStatement(sql_m);
					pstmt.setString(1,title);
					pstmt.setString(2,contents);
					pstmt.executeUpdate();

					out.println("<script>alert('수정되었습니다.');</script>");
					out.println("<script>window.location.href='index.jsp';</script>");

				}
				else{
					out.println("<script>alert('비밀번호가 틀렸습니다.');</script>");
					out.println("<script>window.history.back();</script>");		
				}
			}
	
			
		}catch(SQLException e){
			e.printStackTrace();
			out.println("select query false!");
		}finally{
			if(rs != null) try{rs.close();}catch(SQLException sqle){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException e){}
			if(conn!=null)try{conn.close();}catch(SQLException e){}
		}
		

	}

	if(mode.equals("write")){

		try{	

			DecimalFormat df = new DecimalFormat("00");
			Calendar currentCalendar = Calendar.getInstance();

			String strYear = Integer.toString(currentCalendar.get(Calendar.YEAR));
			String strMonth = df.format(currentCalendar.get(Calendar.MONTH) + 1);
			String strDay = df.format(currentCalendar.get(Calendar.DATE));

			String strDate = strYear + '-' + strMonth + '-' + strDay;

			/*
			String strHour = ""; 
			if( currentCalendar.get(currentCalendar.AM_PM) == currentCalendar.PM){
				strHour = df.format(currentCalendar.get(Calendar.HOUR)+12); //Calendar.PM이면 12를 더한다
			} else {
				strHour = df.format(currentCalendar.get(Calendar.HOUR));
			}

			String strMin = df.format(currentCalendar.get(Calendar.MINUTE));
			String strSec = df.format(currentCalendar.get(Calendar.SECOND));
			String strDate = strYear + '-' + strMonth + '-' + strDay + ' ' + strHour + ':' + strMin + ':' + strSec;	
			*/

			String sql_w = "insert into board( title, contents, register_date, password) values (?,?,?,?)";

			pstmt = conn.prepareStatement(sql_w);
			pstmt.setString(1, title);
			pstmt.setString(2, contents);
			pstmt.setString(3, strDate);
			pstmt.setString(4, pw);

			// setString 방식으로 쿼리에 넘길경우 mysql 함수 사용여부 알아보기


			pstmt.executeUpdate();
			
			//out.println("insert query ok!");
			out.println("<script>alert('게시물이 등록되었습니다.');</script>");
			out.println("<script>window.location.href='index.jsp'</script>");

			
		}catch(SQLException e){
			e.printStackTrace();
			out.println("insert query false!");
		}finally{
			if(rs != null) try{rs.close();}catch(SQLException sqle){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException e){}
			if(conn!=null)try{conn.close();}catch(SQLException e){}
		}

	}

	if(mode.equals("delete")){
		
		try{
			String sql_d1 = "select * from board where idx = "+idx_str;
			pstmt = conn.prepareStatement(sql_d1);

			rs = pstmt.executeQuery();
			

			while(rs.next()){
				int idx = rs.getInt("idx");
				String db_pass = rs.getString("password");
				
				
				if(pw.equals(db_pass)){
					
					String sql_d2 = "delete from board where idx = "+idx_str;
					pstmt = conn.prepareStatement(sql_d2);
					pstmt.executeUpdate();

					out.println("<script>alert('게시물이 삭제되었습니다.');</script>");
					out.println("<script>window.location.href='index.jsp'</script>");

				}
				else{
					out.println("<script>alert('비밀번호가 틀렸습니다.');</script>");
					out.println("<script>window.history.back();</script>");		
				}
			}

		}catch(SQLException e){
			e.printStackTrace();
			out.println("select query false!");
		}finally{
			if(rs != null) try{rs.close();}catch(SQLException sqle){}
			if(pstmt!=null)try{pstmt.close();}catch(SQLException e){}
			if(conn!=null)try{conn.close();}catch(SQLException e){}
		}
		
		
	}

}
else{
	out.println("<script>alert('잘못된 접근입니다.');</script>");
	out.println("<script>window.location.href='index.jsp'</script>");
}


%>

</div>

<script>
	
	function check(){
		var form = document.modify;

		if(form.pw.value == ""){
			alert('비밀번호를 입력해주세요');
			form.pw.focus();
			return false;
		}
		
		return true;
	}
	
</script>

</body>
</html>