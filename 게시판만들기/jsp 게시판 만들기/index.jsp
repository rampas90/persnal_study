<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>  
<%@ page import="java.lang.Math.*" %>
<%@ page import="java.util.Date"%>    


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script>

<style>
	.idx{width:10%;}
	.title{width:70%;}
	.date{width:20%;}
	.tx_c{text-align:center;}
</style>
</head>
<body>

<%!
	public Integer toInt(String x){
		int a = 0;
		try{
			a = Integer.parseInt(x);
		}catch(Exception e){}
		return a;
	}
%>

<%@include file="dbcon.jsp"%>
<%
	request.setCharacterEncoding("UTF-8");
	
try{	
	
	String sql_total = "select count(idx) from board ";
	int rowcount = 0;
	pstmt = conn.prepareStatement(sql_total);
	rs = pstmt.executeQuery();
	if(rs.next()){
		rowcount = rs.getInt(1);								// 총갯수
	}
	
	int page_no = toInt(request.getParameter("page"));
	int page_list = 5;

	if(page_no < 1){
		page_no = 1;
	}
	
	int total_record = rowcount;   // 총레코드 배열에 담기 
	int page_per_record_cnt = 5;   // 페이지당 레코드 수
	int group_per_page_cnt = 5;    // 그룹갯수 

	int record_end_no = page_no * page_per_record_cnt;
	int record_start_no = record_end_no - (page_per_record_cnt -1);
	if(record_end_no > total_record){
		record_end_no = total_record;
	}
	
	int total_page = total_record / page_per_record_cnt + (total_record % page_per_record_cnt>0 ? 1 : 0); // 총 페이지수 
	
	int group_no = page_no/group_per_page_cnt+( page_no%group_per_page_cnt>0 ? 1:0);                      // 현재 블록 번호 

	if(page_no>total_page){
		page_no = total_page;
	}

	int page_eno = group_no * group_per_page_cnt;			//현재 블록 끝
	int page_sno = page_eno - (group_per_page_cnt - 1);	//현재 블록 시작

	if(page_eno > total_page){
		page_eno = total_page;
	}

	int prev_pageno = page_no-1;
	int next_pageno = page_no+1;

	if(prev_pageno<1){
		prev_pageno = 1;
	}

	if(next_pageno > total_page){
		next_pageno = total_page;
	}
	
	int limit = (page_no - 1 ) * page_list;


	String sql = "select * from board order by idx desc LIMIT ?,?";
	pstmt = conn.prepareStatement(sql);
	pstmt.setInt(1,limit);
	pstmt.setInt(2,page_list);
	rs = pstmt.executeQuery();
	
%>
	<div class="container">
		<h2>JSP board List</h2>
		<div class="row">
			<div class="col-md-10">
				<table class="table table-bordered table-hover" style="margin-bottom: 0;" >
					<tr>
						<th class="idx tx_c">번호</th>
						<th class="title">제목</th>
						<th class="date">작성일</th>
					</tr>
<%

	
		while(rs.next()){
			String idx = rs.getString("idx");
			String title = rs.getString("title");
			String contents = rs.getString("contents");
			String register_date = rs.getString("register_date");
			String password = rs.getString("password");		
%>
				<tr>
					<td class="tx_c"><%=idx%></td>
					<td><a href="write.jsp?idx=<%=idx%>&mode=modify"><%=title%></a></td>
					<td class=""><%=register_date%></td>
				</tr>

<%
		}
	
%>
	
<%
	
%>
			</table>
			
			<nav style="text-align:center;height:55px;">
				<ul class="pagination">
					<% 
						if(total_record>0){
					%>
					<li>
						<a href="index.jsp?page=1">
							<span>처음</span>
						</a>
					</li>
					<%	
						}
					%>
					<li>
						<a href="index.jsp?page=<%=prev_pageno%>" aria-label="Previous">
							<span aria-hidden="true">이전</span>
						</a>
					</li>
					<%	for(int i = page_sno; i<=page_eno; i++){ %>
						
						<% if(page_no == i){ %>
								<li class="active"><a href="index.jsp?page=<%=i%>"><%=i%></a></li>
						<% }else{ %>
								<li class=""><a href="index.jsp?page=<%=i%>"><%=i%></a></li>
						<% } %>
							
					<%	}	%>

					<li>
						<a href="index.jsp?page=<%=next_pageno%>" aria-label="Next">
							<span aria-hidden="true">다음</span>
						</a>
					</li>
					<li>
						<a href="index.jsp?page=<%=total_page%>">
							<span>마지막</span>
						</a>
					</li>
				</ul>
			</nav>
			<button type="button" class="btn btn-default " id="paging-btn" >?</button>
			

			<a href="write.jsp?mode=write" class="btn btn-default pull-right ">글쓰기</a>
			<div class="pagings " style="margin-top: 10px;">
현재 페이지   (page_no)   : <%=page_no%><br />
전체 데이터 수   (total_record) : <%=total_record %><br />
한페이지 당 레코드 수   (page_per_record_cnt) : <%=page_per_record_cnt %><br />
한페이지 당 보여줄 페지 번호 수   (group_per_page_cnt) : <%=group_per_page_cnt %><br />

<hr />
데이터 시작 번호  (record_start_no) : <%=record_start_no%><br />
데이터 끝 번호    (record_end_no) : <%=record_end_no %><br />
전체페이지 수     (total_page)  : <%=total_page %><br />
<hr />
현재 블록 시작 번호(page_sno): <%= page_sno%><br />
현재 블록 끝 번호  (page_eno): <%= page_eno%><br />
이전 페이지 번호   (prev_pageno) <%=prev_pageno%><br />
다음 페이지 번호   (next_pageno) <%=next_pageno%><br />
<hr />
			</div>
		</div>
	</div>
<%
	
}catch(SQLException e){
	e.printStackTrace();
	out.println("select query false!");
}finally{
	if(rs != null) try{rs.close();}catch(SQLException sqle){}            // Resultset 객체 해제
	if(pstmt!=null)try{pstmt.close();}catch(SQLException e){}
	if(conn!=null)try{conn.close();}catch(SQLException e){}
}



%>


	  
<script>/*
	$(function(){
		$('#paging-btn').click(function(){
			$('.pagings').toggle("fast");
		});
	});*/
</script>	
   
</body>
</html>
