<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String idx = (String)session.getAttribute("idx");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>bbs_rewrite.jsp</title>
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
<center>
		<br>
		<font color="navy" size="5">
			<b>답글 쓰기</b>
		</font>
		<br>
	<hr width=500 color="red">
		<a href="bbs-list.do?method=list">LIST</a>|<a href="index.do">HOME</a>
	<hr width=500 color="red">
	<!-- 파일 전송까지 하기 위해서~~enctype추가 -->
	<form name="f" method="post" enctype="multipart/form-data" action="bbs-rewriteOk.do">
	
	<!-- hidden으로 idx ,ps, cp 넘기기 MultipartRequest가 껴안고 간다...-->
	<input type="hidden" name="idx" value="<%=idx.trim() %>">
	<!-- ----------------------------- -->
	
		<table border=1 width=600>
			<tr>
				<td width="20%" align="center" ><B>제 목</B></td>
				<td width="80%">
					<input type="text" name="subject" size=60 value="[RE]">
				</td>
			</tr>
			<tr>
				<td width="20%" align="center"><B>내 용</B></td>
				<td width="80%">
					<textarea name = content rows=10 cols=60></textarea>
				</td>
			</tr>
			<tr>
				<td width="20%" align="center"><B>글쓴이</B></td>
				<td width="80%">
					<input type="text" name="writer" size=60>
				</td>
			</tr>
			<tr>
				<td width="20%" align="center"><B>이메일</B></td>
				<td width="80%">
					<input type="text" name="email" size=60>
				</td>
			</tr>
			<tr>
				<td width="20%" align="center"><B>홈페이지</B></td>
				<td width="80%">
					<input type="text" name="homepage" size=60 value="http://" >
				</td>
			</tr>
			<tr>
				<td width="20%" align="center"><B>비밀번호</B></td>
				<td width="80%">
					<input type="password" name="pwd" size=60>
				</td>
			</tr>
			<tr>
				<td width="20%" align="center"><B>첨부파일</B></td>
				<td width="80%">
					<input type="file" name="filename" size=40>
				</td>
			</tr>
			<tR>
				<td colspan="2" align="center">
					<input type="button" value="글쓰기" onclick="check();">
					<input type ="reset" value="다시 쓰기">
				</td>
			</tR>
		</table>
	</form>
</center>
</body>
</html>