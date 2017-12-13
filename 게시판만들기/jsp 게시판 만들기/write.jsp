<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>

<style>
	.table{border:1px solid #000;}
	.btn_grp{float:right;}
	a, a:hover, a:active{text-decoration:none;}
</style>

</head>
<body>

<%@include file="dbcon.jsp"%>

<%
	String idx_str = request.getParameter("idx");
	String mode = request.getParameter("mode");


	if(mode.equals("modify")){
		
		try{	

			String sql = "select * from board where idx =" + idx_str;
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
		%>
		
		<%
			while(rs.next()){
				String idx = rs.getString("idx");
				String title = rs.getString("title");
				String contents = rs.getString("contents");
				String register_date = rs.getString("register_date");

		%>
				<div class="container">
					<div class="row">
						<h2><a href="index.jsp">JSP board <%=mode%></a></h2>
						<form action="action.jsp" method="POST" name="board" id="board" onsubmit="return check()">
							<input type="hidden" name="mode" value="<%=mode%>">
							<input type="hidden" name="idx" value="<%=idx%>">
							<table class="table table-bordered">
								<tr>
									<td>
										<label for="title">제목</label>
									</td>
									<td>
										<input type="text" name="title" id="title" class="form-control" value="<%=title%>">
									</td>
									
								</tr>

								<tr>
									<td>
										<label for="contents">내용</label>
									</td>
									<td>
										<textarea name="contents" id="contents" class="form-control" cols="50" rows="10"><%=contents%></textarea>
									</td>
								</tr>

								<tr>
									<td>
										<label for="pw">비밀번호</label>
									</td>
									<td>
										<input type="password" name="pw" id="pw" class="form-control" value="">
									</td>
								</tr>
								
							</table>

							<div class="btn_grp">
								<a onclick="move_back();" class="btn btn-default" value="뒤로가기">뒤로가기</a>
								
								<a href="#" onclick="move_del();" class="btn btn-default" value="삭제">삭제</a>
								<input type="submit" class="btn btn-default" value="수정">
							</div>

						</form>
					</div>
				</div>

		<%

			}

		}catch(Exception e){
			e.printStackTrace();
			out.println("select table false!!");
		}finally{       
			if(rs != null) try{rs.close();}catch(SQLException sqle){}
			if(pstmt != null) try{pstmt.close();}catch(SQLException sqle){}
			if(conn != null) try{conn.close();}catch(SQLException sqle){}
		}

	
	}
	else if(mode.equals("write")){
	


%>


<div class="container">
	<div class="row">
		<h2><a href="index.jsp">JSP board <%=mode%></a></h2>
		<form action="action.jsp" method="POST" name="board" id="board" onsubmit="return check()">
			<input type="hidden" name="mode" value="<%=mode%>">
			<table class="table table-bordered"> 
				<tr>
					<td>
						<label for="title">제목</label>
					</td>
					<td>
						<input type="text" name="title" class="form-control" id="">
					</td>
					
				</tr>

				<tr>
					<td>
						<label for="contents">내용</label>
					</td>
					<td>
						<textarea name="contents" id="contents" class="form-control" cols="50" rows="10"></textarea>
					</td>
				</tr>

				<tr>
					<td>
						<label for="pw">비밀번호</label>
					</td>
					<td>
						<input type="password" name="pw" id="pw" class="form-control" >
					</td>
				</tr>
	
			</table>
			<div class="btn_grp">
				<a onclick="move_back();" class="btn btn-default" value="뒤로가기">뒤로가기</a>
				<input type="submit" class="btn btn-default" value="작성">
			</div>
		</form>
	</div>
</div>

<%
	}

%>
<script>
	function move_back(){
		window.history.back();	
	}

	
	function check(){
		var form = document.board;

		if(form.title.value == ""){
			alert('제목을 입력해주세요');
			form.title.focus();
			return false;
		}

		if(form.title.value.length > 25){
			alert('제목은 25자 미만으로 작성해주세요');
			form.title.focus();
			return false;
		}
		
		if(form.contents.value == ""){
			alert('내용을 입력해주세요');
			form.contents.focus();
			return false;
		}

		if(form.pw.value == ""){
			alert('비밀번호를 입력해주세요');
			form.pw.focus();
			return false;
		}		


		return true;
	}

	function move_del(){
		var form = document.board;
		form.mode.value = "delete";
		
		if(form.pw.value == ""){
			alert('비밀번호를 입력해주세요');
			form.pw.focus();
			return false;
		}
		form.submit();
	}


</script>

</body>
</html>
