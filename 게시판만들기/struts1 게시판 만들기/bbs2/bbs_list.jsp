<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, my.bbs2.*,java.io.*"%>
<%	
	ArrayList<BbsDTO> arr = (ArrayList<BbsDTO>)request.getAttribute("bbslist");
	
	Integer tgc = (Integer)request.getAttribute("tgc");
	String cpStr =(String)request.getAttribute("cp");
	String psStr = (String)request.getAttribute("ps");


	//페이지수 구하기
	int pageCount = 0;
	
	int totalCount = tgc.intValue();
	int cpage = Integer.parseInt(cpStr);
	int pageSize = Integer.parseInt(psStr);
	
	//pageSize : 5개
	//totalCount :1~ 4 |  5
	//pageCount : 1    | 1
	
	//if(totalCount%pageSize == 0){
	//	pageCount = totalCount/pageSize;
	//}else{
	//	pageCount = totalCount/pageSize+1;	
	//}
	
	pageCount = (totalCount-1)/pageSize+1;
	
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="/cdn.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="./css/style.css">
<title>bbs_list.jsp</title>

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
		   		<h1>Struts  <small>List page</small></h1>
			</div>
		</div>
	</div>

	<div class="row">
		<div class=" form_wrap well">
			
			<form name="f" method="get" class="form-horizontal">
			
				<input type=hidden name=method value=list>
				<div class="form-group">
					
					<div class="col-md-6">
					<select name="ps" onchange="submit()" class="form-control">
					<%if (pageSize == 3) {%>
						<option value="3" selected>페이지 SIZE 3</option>
					<%}else{ %>
						<option value="3">페이지 SIZE 3</option>	
					<%} 
						for(int i=5;i<=20;i+=5){
							if(pageSize==i){
					%>
								<option value="<%=i %>" selected>페이지 SIZE <%=i %>
					<%			
							}else{
					%>
								<option value="<%=i %>">페이지 SIZE <%=i %>
					<%
							}
						}

					%>
					</select>
					</div>
					<div class="col-md-6 btn_design01">
						<a href="bbs-write.do?method=write" class="btn btn-primary">글쓰기</a>
						<button class="btn btn-primary" type="button">
						  총 게시물수 <span class="badge"><%= totalCount %></span>
						</button>
						<button class="btn btn-success" type="button">
						  현재 페이지 <span class="badge"><%=cpage%>/<%=pageCount %></span>
						</button>
					</div>
				</div>
			</form>
			
		</div>
	</div>
	
	<div class="row">
		
	<table class="talbe table-bordered col-xs-12">
		<tr class="th_design">
			<th class="col-md-1">번호</th>
			<th class="col-md-6">제목</th>
			<th class="col-md-2">작성자</th>
			<th class="col-md-1">날짜</th>
			<th class="col-md-1">조회</th>
		</tr>
<%
		
	if( arr ==null || arr.size()==0){
%>	
	<tr class="td_design">
		<td colspan = 5 align="center">게시물이 없음</td>
	<tr>		
<%
	}else{
		ListIterator it = arr.listIterator();

		while(it.hasNext()){
			BbsDTO dto = (BbsDTO)it.next();
	
			String subject = dto.getSubject();
			String writer = dto.getWriter();
			
		if(subject != null && subject.length() >10){

			subject = subject.substring(0,11);
			subject += "...";
		}
		
		if(writer != null && writer.length() >10){

			writer = writer.substring(0,11);
			writer += "...";
		}
			
%>
		<tr align="center" class="td_design">
			<td><%= dto.getIdx() %></td>
			<td align="left">
			<%
				
				String qstr = "refer="+dto.getRefer()+"&sunbun="+dto.getSunbun()+"&lev="+dto.getLev()+"&";
			
				int lev = dto.getLev();
				for(int i=0; i<lev; i++){
					out.println("&nbsp;&nbsp;&nbsp;");
				}
				if(lev >0){
					out.println("-->");
				}
			%>
			<a href="bbs-content.do?method=content&<%=qstr%>idx=<%=dto.getIdx()%>&cp=<%=cpage%>&ps=<%=pageSize%>">
				<%= subject %>
			</a></td>
			<td><%= writer %></td>
			<td><%= dto.getWritedate() %></td>
			<td><%= dto.getReadnum() %></td>
		</tr>
		
<%
		}
	}
%>
		<tr>
		<td colspan="5" align="center">
			<nav>
			<ul class="pagination pagination-sm">
<% 
	if(cpage >1){
%>
		<li><a href="bbs-list.do?method=list&cp=<%=cpage-1%>&ps=<%=pageSize%>">◀</a></li>
<%		
	}

	for(int i =1; i<=pageCount; i++){

		if(cpage == i){
%>
			<li class="active"><a href=""><span><%= i %></span></a></li>

<%
		}else{
%>
			<li><a href="bbs-list.do?method=list&cp=<%=i%>&ps=<%=pageSize%>"><%= i %></a></li>
<%
		}
	}
	if(cpage < pageCount){
%>
		<li><a href="bbs-list.do?method=list&cp=<%=cpage+1%>&ps=<%=pageSize%>">▶</a></li>
<%
	}
%>
				</ul>
				</nav>
			</td>

		</tr>
	</table>	
	
	</div>
</div>
</body>
</html>