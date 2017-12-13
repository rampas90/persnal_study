<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR" import="my.bbs2.*"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
   <%request.setCharacterEncoding("utf-8"); %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/cdn.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="./css/style.css">
<title>bbs_edit.jsp</title>
<!-- SCRIPT -->
<script type="text/javascript">
	function check(){
		if(!f.subject.value){
			alert("���� �Է�");
			f.subject.focus();
			return;
		}
		if(!f.content.value){
			alert("���� �Է�");
			f.content.focus();
			return;
		}
		if(!f.writer.value){
			alert("�̸� �Է�");
			f.writer.focus();
			return;
		}
		if(!f.email.value){
			alert("�̸��� �Է�");
			f.email.focus();
			return;
		}
		if(!f.pwd.value){
			alert("��й�ȣ �Է�");
			f.pwd.focus();
			return;
		}
		
		document.f.submit();
	}
</script>
</head>
<body>

<c:set var="gul" value="${requestScope.gul}"/>
<c:if test="${gul eq null}">
	<c:redirect url="bbs-list.do?method=list"/>
</c:if>

<div class="container">
	<div class="row">	
		<nav class="navbar navbar-inverse edit_navbar">
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
		   		<h1>Struts 1! <small>Edit page</small></h1>
			</div>
		</div>
	</div>
	
	
	<form name="f" method="post" action="bbs-editOk.do">
	
	<input type="hidden" name="method" value="editOk">
	<input type="hidden" name ="idx" value="${sessionScope.idx}">
	
	<div class="row">
		<table class="table table-bordered">
			<tr>
				<td width="20%" align="center"><B>�� ��</B></td>
				<td width="80%">
					<input type="text" name="subject" class="form-control" value="${gul.subject }">
				</td>
			</tr>
			<tr>
				<td width="20%" align="center"><B>�� ��</B></td>
				<td width="80%">
					<textarea name="content" class="form-control" cols="30" rows="4" >${gul.content }</textarea>
				</td>
			</tr>
			<tr>
				<td width="20%" align="center"><B>�̸�</B></td>
				<td width="80%">
					<input type="text" name="writer" class="form-control" size=60 value="${gul.writer }">
				</td>
			</tr>
			<tr>
				<td width="20%" align="center"><B>�̸���</B></td>
				<td width="80%">
					<input type="text" name="email" class="form-control" size=60 value="${gul.email }">
				</td>
			</tr>
			<tr>
				<td width="20%" align="center"><B>Ȩ������</B></td>
				<td width="80%">
					<input type="text" name="homepage" class="form-control" size=60 value="${gul.homepage }" >
				</td>
			</tr>
			<tr>
				<td width="20%" align="center"><B>��й�ȣ</B></td>
				<td width="80%">
					<input type="password" class="form-control" name="pwd" size=60 >
				</td>
			</tr>
			<tr>
				<td width=20% align="center">
				<b> ÷������</b>
				<td>
					${gul.filename }
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="���� �ϱ�" class="btn btn-info" onclick="check();">
					<input type ="reset" class="btn btn-info" value="�ٽ� ����">
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
</body>
</html>