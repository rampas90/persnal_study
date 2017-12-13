<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="msg" value="${requestScope['bbs-msg'] }"/>
<c:set var="url" value="${requestScope['bbs-url'] }"/>

<c:choose>
	<c:when test="${msg ne null && url ne null }">
		<script>
			alert("${msg}");
			location.href = "${url}";
		</script>
	</c:when>
	
	<c:otherwise>
		<script>
			alert("msg 또는 url이 널");
			location.href = "index.do";
		</script>
	</c:otherwise>
</c:choose>