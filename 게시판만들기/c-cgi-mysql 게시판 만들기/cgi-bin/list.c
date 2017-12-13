#include "common.h"
#include "db_lib.h"
#include "pagination.h"

//  gcc -o list.cgi list.c cgic.c -I/usr/include/mysql -L/usr/local/lib/mysql -lmysqlclient
//  gcc -o mysqltest mysqltest.c -I /usr/include/mysql -L /usr/local/lib/mysql -lmysqlclient

int cgiMain(void) {
	
	// 헤더 속성 최상단에 위치 - 헤더위에 print 문등이 있으면 500 error 발생 
	printf("%s%c%c\n","Content-Type:text/html;charset=utf-8",13,10);

	unsigned int count;

	// form 데이터
	cgiFormString("title", title, MAX_LEN);
	cgiFormString("name", name, MAX_LEN);
	cgiFormString("content", content, MAX_LEN);
	cgiFormInteger("page", &page, 1);
	cgiFormString("search", search, MAX_LEN);
	cgiFormString("search_type", search_type, MAX_LEN);	
		
	// DB연결 및 연결실패시 에러 console 출력 함수
	mysqlDbConn();
	// DB 셀렉트 함수
	total_count = mysqlDbSelect(search_type, search);

		
	/* 
	 *	페이징에 필요한 변수
	 *	page				: 현재 페이지
	 *  list					: 한 페이지에 보여질 게시물수  (defaultt 10)
	 *  block_page_num_list		: 한번에 보여질 블록(페이지묶음)의 수 (default 5)
	 *  total_count				: 쿼리로 계산되는 총 게시물수	
     *	
	 *  list는 10  block_page_num_list는 5로 기본값이 설정되어있다. 필요시 아래처럼 두개의 변수값만 바꿔서 사용한다.
	 *  list = 5;
	 *  block_page_num_list = 10;
	 */
	
	// 페이징 수식 계산함수 //
	pagingInit(page, list, block_page_num_list, total_count);
	

	// Limit 적용한 최종 쿼리(페이징 데이터용)
	mysqlDbSelectLimit( search_type, search, limit, list );

	if( strcheck(search_type) ){		
		sprintf(search_query, "&search_type=%s&search=%s", search_type, search);
	}
	else{
		sprintf(search_query, "");
	}
	
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
	printf("			<tr>\n");
	printf("				<th class='col-xs-1 active' style='text-align:center' >번호</th>\n");
	printf("				<th class='col-xs-10 active'>제목</th>\n");
	printf("				<th class='col-xs-1 active'>작성자</th>\n");
	printf("			</tr>\n");	
	
	// 데이터 뿌리기 //
	while( (row = mysql_fetch_row(res)) != NULL ){
		printf("		<tr>");
		printf("			<td  style='text-align:center' >%s</td>\n", row[0]);
		printf("			<td><a href='write.cgi?seq=%s'>%s</a></td> \n", row[0], row[1]);
		printf("			<td>%s</td> \n", row[2]);
		printf("		<tr>");
	}
	printf("		</table> \n");
	printf("<nav style='text-align:center; height:24px' > \n");
	printf("	<ul class='pagination' > \n");
	
	// 페이징 //
	pagingData();	

	printf("	</ul> \n");
	printf("</nav> \n");
	printf("		<button class='btn btn-default pull-right' onclick='goWrite();' >글쓰기</button> \n");
	printf("	</div>\n");
	printf("</div>\n");
	printf("</body>\n\n</html>");
	
	// db 열결 해제 //
	mysqlDbClose();

} 