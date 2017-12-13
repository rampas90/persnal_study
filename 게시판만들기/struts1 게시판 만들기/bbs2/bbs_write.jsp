<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/cdn.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="./css/style.css">

<title>bbs_write.jsp</title>
<!-- SCRIPT -->
<script type="text/javascript">
	function check(){
		if(!f.subject.value){
			alert("제목 입력");
			f.subject.focus();
			return;
		}
		if(!f.content.value){
			alert("내용 입력");
			f.content.focus();
			return;
		}
		if(!f.writer.value){
			alert("이름 입력");
			f.writer.focus();
			return;
		}
		if(!f.email.value){
			alert("이메일 입력");
			f.email.focus();
			return;
		}
		if(!f.pwd.value){
			alert("비밀번호 입력");
			f.pwd.focus();
			return;
		}
		
		document.f.submit();
	} // check() ----------------------
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
		            <li><a href="bbs-list.do?method=list">List</a></li>
		         </ul>
		      </div>
		   </div>
		</nav>
	</div>
	
	<div class="row">
		<div class="jumbotron">
		    <div class="page-header">
		   		<h1>Struts 1! <small>Write page</small></h1>
			</div>
		</div>
	</div>
	
	<div class="row">
		
		<form name="f" class="" method="post"  enctype="multipart/form-data" action="bbs-writeOk.do">
			<table class="table table-bordered">
			
				<tr>
					<td width="20%" align="center"><label for="subject">제 목</label></td>
					<td width="80%">
						<input type="text" class="form-control" name="subject" placeholder="제목">
					</td>
				</tr>
				
				<tr>
					<td width="20%" align="center"><label for="content">내 용</label></td>
					<td width="80%">
						<textarea name="content" class="form-control" rows="4" cols="30"></textarea>
					</td>
				</tr>
				
				<tr>
					<td width="20%" align="center"><label for="writer">이름</label></td>
					<td width="80%">
						<input type="text" class="form-control"  name="writer">
					</td>
				</tr>
				
				<tr>
					<td width="20%" align="center"><label for="email">이메일</label></td>
					<td width="80%">
						<input type="text" class="form-control"  name="email">
					</td>
				</tr>
				
				<tr>
					<td width="20%" align="center"><label for="homepage">홈페이지</label></td>
					<td width="80%">
						<input type="text" class="form-control"  name="homepage"value="http://" >
					</td>
				</tr>
				
				<tr>
					<td width="20%" align="center"><label for="pwd">비밀번호</label></td>
					<td width="80%">
						<input type="password" class="form-control"  name="pwd">
					</td>
				</tr>
				
				<tr>
					<td width="20%" align="center"><label for="filename">첨부파일</label></td>
					<td width="80%">
						<input type="file"   name="filename">
					</td>
				</tr>
				
				<tr>
					<td colspan="2" align="center">
						<input type="button" class="btn btn-primary" value="글쓰기" onclick="check();">
						<input type="reset" class="btn btn-primary" value="다시 쓰기">
					</td>
				</tr>	
			</table>


		</form>
		
	</div>
</div>
</body>
</html>