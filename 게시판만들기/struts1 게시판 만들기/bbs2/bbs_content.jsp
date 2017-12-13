<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="my.bbs2.*, java.io.*, java.util.*" %>

<%

	BbsDTO dto = (BbsDTO)request.getAttribute("gul");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/cdn.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="./css/style.css">
<title>Insert title here</title>

<script type="text/javascript">
	function check(){
		if(!reply.reply_content.value){
			alert("내용을 입력하세요");
			reply.reply_content.focus();
			return;
		}
		if(!reply.reply_pwd.value){
			alert("비밀번호를 입력하세요");
			reply.reply_pwd.focus();
			return;
		}
		if(!reply.reply_writer.value){
			alert("이름을 입력하세요");
			reply.reply_writer.focus();
			return;
		}
		
		document.reply.submit();
	}
	
	function check1(){
		if(!replyDel.delPwd.value){
			alert(replyDel.delPwd.value);
			return false;
		}
		document.replyDel.action ="reply_delOk.jsp";
		return true;
	}
</script>


</head>
<body>
<div class="container">
	<div class="row">	
		<nav class="navbar navbar-inverse">
		   <div class="container-fluid">
			   <div class="navbar-header">
			      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-2">
			         <span class="sr-only">Toggle navigation</span>
			         <span class="icon-bar"></span>
			         <span class="icon-bar"></span>
			         <span class="icon-bar"></span>
			      </button>
			      <a class="navbar-brand" href="intro.do">SMC</a>
				</div>
		      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-2">
		         <ul class="nav navbar-nav navbar-right">
		            <li class="active"><a href="index.do" >HOME <span class="sr-only">(current)</span></a></li>
		            <li><a href="bbs-write.do?method=write">Write</a></li>
		         </ul>
		      </div>
		   </div>
		</nav>
	</div>

	<div class="row">
		<div class="jumbotron">
		    <div class="page-header">
		   		<h1>Struts 1! <small>View page</small></h1>
			</div>
		</div>
	</div>
	
	<div class="row">
	
		<table class="table table-bordered">
			<tr>
				<td width="20%" align="center"><B>글번호</B></td>
				<td width="30%" align="center"><%=dto.getIdx() %></td>
				<td width="20%" align="center">날짜</td>
				<td width="30%" align="center"><%= dto.getWritedate() %></td>
			</tr>
			<tr>
				<td width="20%" align="center"><B>글쓴이</B></td>
				<td width="30%" align="center"><%= dto.getWriter() %></td>
				<td width="20%" align="center">홈페이지</td>
				<td width="30%" align="center"><%=dto.getHomepage() %></td>
			</tr>
			<tr>
				<td colspan=3>&nbsp;</td>
				<td colspan=1>조회수 : <%=dto.getReadnum() %></td>
			</tr>
			<tr>
				<td colspan=4 align="center" bgcolor="#efefef">
					<b>제 목 : <%=dto.getSubject() %></b>
				</td>
			</tr>
			<tr>
				<td colspan="4" align="center">
				<%= dto.getContent().replaceAll("\r\n","<br>") %>
				</td>
			</tr>
			<tr>
				<td align=center>첨부파일</td>
				<%
					String filename = dto.getFilename();
					if(filename ==null){
						filename = "";
					}
					
					String qstr = "refer="+dto.getRefer()+"&sunbun="+dto.getSunbun()+"&lev="+dto.getLev()+"&";					
					
					String refer = request.getParameter("refer");
					String sunbun = request.getParameter("sunbun");
					String lev = request.getParameter("lev");
					String idx = request.getParameter("idx");
					String cp = (String)session.getAttribute("cp");
					String ps = (String)session.getAttribute("ps");
					
					 
				%>
				<td colspan=3><a href="bbs-content-down.do?method=contentDown&"+qstr><%=filename %></a> [<%= dto.getFilesize() %> bytes]</td>
			</tr>
	<%
		
		
	
	%>
	
			<tr>
				<td colspan=4 align=center>
					<a href="bbs-list.do?method=list" class="btn btn-primary">목록</a>
					<a href="bbs-edit.do?method=edit" class="btn btn-info">편집</a>
					<a href="bbs-delete.do?method=delete" class="btn btn-danger">삭제</a>
					<!-- 답변형 추가-->  
					<a href="bbs-rewrite.do?method=rewrite" class="btn btn-warning">답변</a>
					
				</td>
			</tr>
			</table>
		</div>
		
		<!-- 꼬리말 달기 테이블 -->
		<div class="row">
			<div class="col-md-6 custom_p">
			<form action="bbs-replyOk.do" method="POST" name="reply">
				<input type=hidden name="idx" value="<%=dto.getIdx()%>">
				<input type=hidden name=method value=replyOk>
				<table class="table table-bordered">
					
						<tr style="background:#dddced;"><td colspan="2"><b>리플 달기</b></td></tr>					
						<tr>
							<td><lable for="reply_content" class="">내용</lable></td>
							<td><textarea name=reply_content row="5" class="form-control"></textarea></td>
						</tr>
						<tr>
							<td><lable for="reply_writer" class="">작성자</lable></td>
							<td><input type=text name="reply_writer" class="form-control"></td>
						</tr>
						<tr>
							<td><lable for="reply_pwd" class="">비밀번호</lable></td>
							<td><input type="password" name="reply_pwd" class="form-control"></td>
						</tr>				
						<tr>
							<td colspan="2"><input type="button" class="btn btn-default" value="등록" onclick="javascript:check();"></td>
						</tr>							

				</table>
			</form>
			</div>
		</div>
		<!-- 꼬리말 목록 보기 테이블  -->
		
		<div class="row">
			<table class="table table-bordered">
				<tr align=center>
					<td colspan=2 bgcolor="dgegeg"><b>REPLY</b></td>
				</tr>
				<%
					java.util.ArrayList<ReplyDTO> replyList = (java.util.ArrayList<ReplyDTO>)request.getAttribute("reply");
				
					if(replyList == null || replyList.size() == 0){
				%>
					<tr>
						<td colspan=2><b>꼬리글 없음</b></td>
					</tr>
				<%
						return;
					}
				
					for(ReplyDTO rdto : replyList){ 
				%>
				
				<tr>
					<td width="80%" align="left">[<%=rdto.getWriter() %>]:[<%=rdto.getContent() %>]<br>
					작성일:<%=rdto.getWritedate().toString() %>
					</td>
					<td>
					<form name="replyDel"  method="post" action="bbs-reply-delOk.do">
					PASSWORD
					
					<input type=hidden name=method value=replyDelOk>
					<input type=hidden name="no" value="<%=rdto.getNo() %>">
					<input type=hidden name="idx" value="<%=dto.getIdx()%>">
					
					<input type="password" class="form-control" size=6 name="delPwd">
					<input type="submit" class="form-control" value="삭제">
					</form>
					</td>
				</tr>
				<%} %>
				
			</table>
	</div>
	
</div>
</body>
</html>