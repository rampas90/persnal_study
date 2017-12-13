#include "common.h"

//  gcc -o list.cgi list.c cgic.c -I/usr/include/mysql -L/usr/local/lib/mysql -lmysqlclient
//  gcc -o mysqltest mysqltest.c -I /usr/include/mysql -L /usr/local/lib/mysql -lmysqlclient

int cgiMain(void) {
	
	/* 헤더 속성 최상단에 위치 - 헤더위에 print 문등이 있으면 500 error 발생 */
	printf("%s%c%c\n","Content-Type:text/html;charset=utf-8",13,10);

	// 페이징 관련 변수
	int page, limit;
	int page_num = 1;
	int list = 10;
	int block_page_num_list = 5;
	int block, block_start_page, block_end_page, total_page, total_block;
	int total_count = 0;
	int query_stat;

	// form 데이터
	cgiFormString("title", title, MAX_LEN);
	cgiFormString("name", name, MAX_LEN);
	cgiFormString("content", content, MAX_LEN);
	cgiFormInteger("page", &page, 1);
	cgiFormString("search", search, MAX_LEN);
	cgiFormString("search_type", search_type, MAX_LEN);	
		
	// DB연결 및 연결실패시 에러 console 출력 함수
	mysqlDbConn();

	// 검색값이 있을경우
	if( strcheck(search_type) ){
		mysqlDbSelect(search_type, search);
	}
	// 검색값이 없을경우
	else{
		mysqlDbSelect(search_type, search);
	}
	
	/*
	 *	페이징 시작
	 */	
	res = mysql_store_result(connection);	// 쿼리 문 실행 후 결과값을 가져온다. 쿼리 결과의 모든 ROW들를 한번에 얻어 온다
	total_count = mysql_num_rows(res);		// 최종 쿼리 row 갯수 
	if(&page == NULL){
		page_num = 1;
	}else{
		page_num = page;
	}
	block = ceil((float)page_num/block_page_num_list);
	if(block < 1){
		block = 1;
	}
	block_start_page = ( (block - 1) * block_page_num_list )+1;
	block_end_page = (block_start_page + block_page_num_list) -1;
	total_page = ceil( (float)total_count/list );
	total_block = ceil( (float)total_page/block_page_num_list );
	if( block_end_page > total_page ){
		block_end_page = total_page;
	}
	limit = (page -1)*list;
	
	// Limit 적용한 최종 쿼리(페이징 데이터용)
	if( strcheck(search_type) ){
		mysqlDbSelectLimit( search_type, search, limit, list );
		sprintf(search_query, "&search_type=%s&search=%s", search_type, search);
	}
	else{
		mysqlDbSelectLimit( search_type, search, limit, list );
		sprintf(search_query, "");
	}
	/*
	 *	페이징 끝
	 */	

	final_res = mysql_store_result(connection);				// 최종 출력되는 쿼리 ( mysql 의 limit 가 반영된 쿼리 )
	
	/* 
	// 페이징 변수 확인
	printf("page : %d  - 1,2,3 등의 실제 페이지 번호 <br>", page);
	printf("page_num : %d - 페이지번호를 페이징에서 사용하기위해 구분한 변수 <br>", page_num);
	printf("block_page_num_list : %d - 한블록에 노출될<br>", block_page_num_list);
	printf("block : %d <br>", block);	
	printf("block_start_page : %d <br>", block_start_page);
	printf("block_end_page : %d <br>", block_end_page);
	printf("total_count : %d <br>", total_count);
	printf("total_page : %d <br>", total_page);
	printf("limit : %d <br>", limit);
	printf("list : %d <br>", list);
	*/


	printf("<!DOCTYPE html>\n\n");
	printf("<HTML lang=\"ko\">\n\n");
	printf("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n\n");
	printf("<HEAD> \n\n");
	printf("	<TITLE>CGI - board</TITLE>\n\n");
	//printf("	<link href=\"https://maxcdn.bootstrapcdn.com/bootswatch/3.3.6/paper/bootstrap.min.css\" rel=\"stylesheet\" > \n");
	printf("	<link href=\"https://maxcdn.bootstrapcdn.com/bootswatch/3.3.6/slate/bootstrap.min.css\" rel=\"stylesheet\" > \n");
	printf("	<script src=\"https://code.jquery.com/jquery-1.12.1.min.js\"></script> \n\n");
	printf("	<script > \n");
	printf("		function goWrite(){ \n");
	printf("			location.href='write.cgi'; \n");
	printf("		}; \n");
	printf("		$(function(){ \n");
	printf("			var form = document.search_frm; \n");
	printf("			form.submit \n");
	printf("		}); \n");
	printf("	</script> \n");
	printf("<HEAD>\n\n");
	printf("<BODY>\n");

	printf("<div class='container' style='margin-bottom: 20px;'>\n");
	printf("	<div class='row' >\n");
	printf("		<h2 class='pull-left '>CGI - board</h2>\n");
	
	printf("		<form class='form-inline pull-right' style='margin-top:30px;' name='search_frm' id='search_frm' > \n");
	printf("			<div class='form-group '> \n");
	printf("					<select class='form-control' name='search_type' id='search_type'>");
	printf("						<option value=''>선택</option>");
	printf("						<option value='title'>제목</option>");
	printf("						<option value='name'>작성자</option>");
	printf("					</select> \n");
	printf("					<input type='text' name='search' class='form-control' id='search' value='%s'> \n", search);
	printf("				</div> \n");
	printf("			<button class='btn btn-default' >검색</button> \n");
	printf("		</form> \n");

	printf("		<table width='600' class='table table-bordered'>\n");
	printf("			<tr>");
	printf("				<th class='col-xs-1 active' style='text-align:center' >번호</th>");
	printf("				<th class='col-xs-10 active'>제목</th>");
	printf("				<th class='col-xs-1 active'>작성자</th>");
	printf("			</tr>\n");	
	
	// 데이터 뿌리기
	while( (row = mysql_fetch_row(final_res)) != NULL ){
		listData(final_res);		
	}

	printf("		</table> \n");

	printf("<nav style='text-align:center; height:24px' > \n");
	printf("	<ul class='pagination' > \n");
	
	// 페이징	
	pagingData(page_num, list, block, block_start_page, block_end_page, total_block, total_page);
	

	printf("	</ul> \n");
	printf("</nav> \n");
	printf("		<button class='btn btn-default pull-right' onclick='goWrite();' >글쓰기</button> \n");
	printf("	</div>\n");
	printf("</div>\n");
	printf("</body>\n\n</html>");
	

	mysql_free_result(final_res);		// mysql_store_result() 함수에 의해 할당된 메모리를 해제한다 - 최종 출력되는 쿼리 해제
	mysql_free_result(res);				// 페이징 데이터를 위한 쿼리 해제 ( limit 가 없는 쿼리 )
	mysql_close(connection);			// mysql 접속해제

} 