#include <stdio.h>
#include "cgic.h"
#include <string.h>
#include <stdlib.h>
#include <math.h>
#include "/usr/include/mysql/mysql.h"

#define MAX_LEN 255 /* 문자열 배열의 최대 길이 */
#define SITE_URL "http://dev-smc.com/cgi-bin"

#define server "dev-smc.com"
#define user "root"
#define pwd "audtls2A"
#define database "main"

char seq[MAX_LEN];
char name[MAX_LEN], title[MAX_LEN], content[MAX_LEN];
char email[MAX_LEN], homepage[MAX_LEN], mode[MAX_LEN];
char search_type[MAX_LEN], search[MAX_LEN], search_query[MAX_LEN];
char table[MAX_LEN], query[MAX_LEN], final_query[MAX_LEN];
char update_seq[MAX_LEN], update_name[MAX_LEN], update_title[MAX_LEN], update_content[MAX_LEN];
char alertMsg[MAX_LEN];
int i, j ,k;		// 반복문에 사용할 int 변수



int strcheck(char *str) { /* 문자열이 NULL이거나 길이가 0인지 체크 */
	if (str == NULL) return 0;
	if (strlen(str) == 0) return 0;	
	return 1;
}

MYSQL *connection=NULL, conn;
MYSQL_RES *res;							// (SELECT, SHOW, DESCRIBE, EXPLAIN)등의 쿼리를 내렸을때 그 결과를 다루기 위해 사용되는 구조체이다.
MYSQL_RES *final_res;					// LIMIT 가 반영된 최종 쿼리
MYSQL_ROW row;							// 데이터의 하나의 row 값을 가리킨다. 만약 row 값이 없다면 null 을 가르키게 된다.


// DB 접속 및 접속실패 체크
int mysqlDbConn(){
	// mysql 한글문자열 깨짐 방지
	mysql_init(&conn);
	mysql_options(&conn, MYSQL_SET_CHARSET_NAME, "utf8");
	mysql_options(&conn, MYSQL_INIT_COMMAND, "SET NAMES utf8");
	
	connection = mysql_real_connect(&conn, server, user, pwd, database, 3306, (char *)NULL, 0);

	if(connection == NULL){
		fprintf(stderr, "%s\n", mysql_error(&conn));
		exit(1);
	}
}

// select 쿼리
void mysqlDbSelect(char search_type[MAX_LEN] , char search[MAX_LEN] ){
	if(strcheck(search_type)){
		sprintf(query, "SELECT * FROM bbsc WHERE %s LIKE '%c%s%c' ORDER BY seq desc", search_type, '%', search, '%');
		if( mysql_query(connection, query) ){
			fprintf(stderr, "%s\n", mysql_error(&conn));
			exit(1);
		}
	}
	else{
		sprintf(query, "SELECT * FROM bbsc ORDER BY seq desc");
		if( mysql_query(connection, query) ){
			fprintf(stderr, "%s\n", mysql_error(&conn));
			exit(1);
		}
	}
}

// select 쿼리 limit
void mysqlDbSelectLimit(char search_type[MAX_LEN] , char search[MAX_LEN], int limit, int list){
	if(strcheck(search_type)){
		sprintf(final_query, "SELECT * FROM bbsc WHERE %s LIKE '%c%s%c' ORDER BY seq desc limit %d, %d", search_type, '%', search, '%', limit, list);
		if( mysql_query(connection, final_query) ){
			fprintf(stderr, "%s\n", mysql_error(&conn));
			exit(1);
		}
	}
	else{
		sprintf(final_query, "SELECT * FROM bbsc ORDER BY seq desc limit %d, %d", limit, list);
		if(mysql_query(connection, final_query)){
			fprintf(stderr, "%s\n", mysql_error(&conn));
			exit(1);
		}
	}
}

void mysqlSelectOne( int seq){
	//int seq = seq;
	sprintf(query, "SELECT * FROM bbsc WHERE seq='%d'", seq);
		
	if(mysql_query(connection, query) ){
		fprintf(stderr, "%s\n", mysql_error(&conn));
		exit(0);
	}
	
}

// list Html 만들어주는 함수
void listData(){
	printf("		<tr>");
	printf("			<td  style='text-align:center' >%s</td>\n", row[0]);
	printf("			<td><a href='write.cgi?seq=%s'>%s</a></td> \n", row[0], row[1]);
	printf("			<td>%s</td> \n", row[2]);
	printf("		<tr>");
}

void updateViewData(char update_title[MAX_LEN], char update_name[MAX_LEN], char update_content[MAX_LEN], int update_seq){
	printf("			<tr><th class='active col-xs-1'>제목</th> <td>%s</td></tr>\n", update_title);
	printf("			<tr><th class='active col-xs-1'>작성자</th> <td>%s</td></tr>\n", update_name);
	printf("			<tr><th class='active col-xs-1'>내용</th> <td>%s</td></tr>\n", update_content);
	sprintf(query, "UPDATE bbsc SET title='%s', name='%s', content='%s' WHERE seq='%d'", update_title, update_name, update_content, update_seq);
}

void insertData(char title[MAX_LEN], char name[MAX_LEN], char content[MAX_LEN]){
	printf("			<tr><th class='active col-xs-1'>제목</th> <td>%s</td></tr>\n", title);
	printf("			<tr><th class='active col-xs-1'>작성자</th> <td>%s</td></tr>\n", name);
	printf("			<tr><th class='active col-xs-1'>내용</th> <td>%s</td></tr>\n", content);
	sprintf(query, "INSERT INTO bbsc (title, name, content) values('%s', '%s', '%s')", title, name, content);		
}

void pagingData(page_num, list, block, block_start_page, block_end_page, total_block, total_page){
	// 처음으로 가기 버튼
	if(page_num <= 1){
		printf("		<li class='disabled'> \n");
		printf("			<a href='#' aria-label='Previous'> \n");
		printf("				<span aria-hidden='true'>처음</span> \n");
		printf("			</a> \n");
		printf("		</li> \n");
	}else{
		printf("		<li> \n");
		printf("			<a href='%s/list.cgi?page=&list=%d%s' aria-label='Previous'> \n", SITE_URL,  list, search_query);
		printf("				<span aria-hidden='true'>처음</span> \n");
		printf("			</a> \n");
		printf("		</li> \n");
	}

	// 이전 블록으로 이동
	if(block <= 1){
		
	}else{
		printf("		<li> \n");
		printf("			<a href='%s/list.cgi?page=%d&list=%d%s' aria-label='Previous'> \n", SITE_URL, block_start_page-1, list, search_query);
		printf("				<span aria-hidden='true'>이전</span> \n");
		printf("			</a> \n");
		printf("		</li> \n");
	}

	// 페이징 출력 부분
	for( j=block_start_page; j <= block_end_page; j++ ){
		if(page_num == j) {
			printf("<li class='active'><a href='%s/list.cgi?page=%d&list=%d%s '>%d</a></li> \n", SITE_URL, j, list, search_query, j );
		}else{
			printf("<li><a href='%s/list.cgi?page=%d&list=%d%s'>%d</a></li> \n", SITE_URL, j, list, search_query, j );
		}
	}
	

	// 다음 블록으로 이동
	if( block >= total_block ){	}

	else{
		printf("		<li> \n");
		printf("			<a href='%s/list.cgi?page=%d&list=%d%s' aria-label='Next'> \n", SITE_URL, block_end_page+1, list, search_query);
		printf("				<span aria-hidden='true'>다음</span> \n");
		printf("			</a> \n");
		printf("		</li> \n");
	}
	
	// 마지막으로 가기 버튼
	if(page_num >= total_page){
		printf("		<li class='disabled'> \n");
		printf("			<a href='' aria-label='Next'> \n");
		printf("				<span aria-hidden='true'>마지막</span> \n");
		printf("			</a> \n");
		printf("		</li> \n");
	}
	else{
		printf("		<li> \n");
		printf("			<a href='%s/list.cgi?page=%d&list=%d%s' aria-label='Next'> \n", SITE_URL, total_page, list, search_query);
		printf("				<span aria-hidden='true'>마지막</span> \n");
		printf("			</a> \n");
		printf("		</li> \n");
	}
}