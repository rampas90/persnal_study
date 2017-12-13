<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%request.setCharacterEncoding("utf-8"); %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Index page</title>
<jsp:include page="/cdn.jsp"></jsp:include>
<style type="text/css">
   .center{text-align:center;}

</style>
</head>
<body>
<div class="container">
	<div class="row center">
		<div class="jumbotron">
		  <h1>Struts</h1>
		  <div class="btn-group" role="group">
			  <a href="bbs-write.do?method=write" class="btn btn-lg btn-default">Write</a>
			  <a href="bbs-list.do?method=list" class="btn btn-lg btn-default">List</a>
		  </div>
		</div>
	</div>

</div>
</body>
</html>