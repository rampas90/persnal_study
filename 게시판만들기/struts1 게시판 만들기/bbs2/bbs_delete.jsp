<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/cdn.jsp"></jsp:include>
<title>Password Check</title>
<script>
	function check(){
		if(!f.pwd.value){
			alert("비밀번호를 입력해야 해요");
			f.pwd.focus();
			return;
		}
		f.submit();
	}
</script>
</head>
<body onload="f.pwd.focus()">
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
		   		<h1>Struts <small>Password check</small></h1>
			</div>
		</div>
	</div>
	
	<div class="row">
		<form action="bbs-deleteOk.do" name="f" method="post">
				
		<%String idx = request.getParameter("idx"); %>
		
		<input type="hidden" name="idx" value="<%=idx%>">
		<input type="hidden" name="method" value=deleteOk>
		
		<lable for="pwd">비밀번호</lable>
		<input type="password" name="pwd" class="form-control">
		<input type="button" value="삭제" class="btn btn-default" onclick="check();">
		<input type="reset" value="다시쓰기" class="btn btn-default">		
		</form>
	</div>
</div>
</body>
</html>