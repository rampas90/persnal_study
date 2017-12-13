#include "/usr/include/mysql/mysql.h"

#define server "dev-smc.com"
#define user "root"
#define pwd "audtls2A"
#define database "main"

MYSQL *connection=NULL, conn;
MYSQL_RES *res;						// (SELECT, SHOW, DESCRIBE, EXPLAIN)등의 쿼리를 내렸을때 그 결과를 다루기 위해 사용되는 구조체이다.
MYSQL_ROW row;						// 데이터의 하나의 row 값을 가리킨다. 만약 row 값이 없다면 null 을 가르키게 된다.
int total_count;
unsigned int num_fields;
unsigned int num_rows;

char search_type[MAX_LEN], search[MAX_LEN];

// DB 접속 및 접속실패 체크
void mysqlDbConn(){
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

/* 
 * mysqlQuery 함수를 분기처리한 이유 ?
 * mysql_query 실행을 select,insert,updqte 모두 한꺼번에 함수화해서 처리 하고 싶으나.
 * resultset 이 있는지(select) 없는지(insert, update)에 따라 분기처리
 * 
 * mysqlQuery 의 주요 사용함수
 * mysql_store_result	:	row들을 한번에 모두 얻어와서 클라이언트의 메모리에 저장한다.
 *                          매번 row 값을 얻기위해 서버에 접근할 필요가 없으므로 속도가 빠르다.
 *                          또한, mysql_data_seek()이나 mysql_row_seek() 등을 이용해서 현재 row의 앞이나 뒤로 자유자재로 컨트롤이 가능하며,
 *                          myql_num_rows()를 이용해서, 몇 개의 row가 리턴됬는지 알 수도 있다.
 *							대신 결과로 넘어온 row의 크기가 클 경우 메모리가 많이 필요하게 된다.
 *			 연관함수   :   mysql_data_seek(), mysql_row_seek(), myql_num_rows(), mysql_num_fields()
 *
 * mysql_use_result		:	한번에 한개의 row를 서버로 부터 가져온다. 
 *							따라서 메모리를 많이 사용하지 않는다. 하지만, 그만큼 mysql_store_result()만큼의 효용성이 없으므로,
 *                          row가 특별히 크지 않은 경우라면 보통 mysql_store_result()를 호출하는 것이 좋다.
 *
 * mysql_num_fields		:	현재의 res에 몇개의 field가 있는지 확인 가능한 함수
 *							흔히 최종적으로 쿼리를 수행하고난 후 mysql_fetch_row()를 사용할 경우
 *                          배열형태로 각 필드에 접근 하므로 배열의 인덱스를 알아야 정확한 데이터를 얻을 수 있다. 이러한 용도로 사용..
 *
 * 참고 사이트   MySQL reference manual  - http://dev.mysql.com/doc/refman/5.7/en/mysql-num-fields.html
 */

int mysqlQuery(MYSQL *connection, char *query){
	
	if(mysql_query(connection, query)){
		// mysql_query 는 성공적으로 수행될 경우 0을 리턴하므로 에러핸들링 로직
		fprintf(stderr, "%s\n", mysql_error(connection));
		exit(1);
	}
	else{
		res = mysql_store_result(connection);	
		// 리턴된 row가 있다.
		if(res){								
			/*
			 * row 값들을 얻어오는 루틴 이경우 반드시 mysql_free_result()를 이용해 메모리를 해제시켜줘야 한다.(mysqlDbClose)
			 */
			//num_fields = mysql_num_fields(res);		// 필드갯수( index 참고용)
			total_count = mysql_num_rows(res);		// 최종 쿼리 row 개수
		}
		// 리턴된 row가 없다. ( row가 없는 쿼리거나 query 수행중에 에러가 났거나..)
		else{
			// row를 리턴하지 않는 쿼리를 실행 (update,insert등...)
			if (mysql_field_count(connection) == 0){
				num_rows = mysql_affected_rows(connection);		// 최근 MySQL 작업으로 변경된 행 개수
			}
			// row를 리턴하지 않는 쿼리도 아닌데 리턴된 row가 없다? 즉 에러라는 소리
			else{
				fprintf(stderr, "%s\n", mysql_error(connection));
				exit(1);
			}
		}
	}
	return total_count;
}

void mysqlDbClose(){
	if(res){
		mysql_free_result(res);				// 페이징 데이터를 위한 쿼리 해제 ( limit 가 없는 쿼리 )
	}
	mysql_close(connection);			// mysql 접속해제
}

// select 쿼리
int mysqlDbSelect(char search_type[MAX_LEN] , char search[MAX_LEN] ){
	if(strcheck(search_type)){
		sprintf(query, "SELECT * FROM bbsc WHERE %s LIKE '%c%s%c' ORDER BY seq desc", search_type, '%', search, '%');
	}
	else{
		sprintf(query, "SELECT * FROM bbsc ORDER BY seq desc");
	}
	mysqlQuery(connection, query);
}

// select 쿼리 limit
void mysqlDbSelectLimit(char search_type[MAX_LEN] , char search[MAX_LEN], int limit, int list){
	if(strcheck(search_type)){
		sprintf(query, "SELECT * FROM bbsc WHERE %s LIKE '%c%s%c' ORDER BY seq desc limit %d, %d", search_type, '%', search, '%', limit, list);
	}
	else{
		sprintf(query, "SELECT * FROM bbsc ORDER BY seq desc limit %d, %d", limit, list);
	}
	mysqlQuery(connection, query);
}

void mysqlSelectOne(char seq[MAX_LEN]){	
	sprintf(query, "SELECT * FROM bbsc WHERE seq='%s'", seq);
	mysqlQuery(connection, query);
}

//void updateData(char *arr[]){
void updateData(char title[MAX_LEN], char name[MAX_LEN], char content[MAX_LEN], char seq[MAX_LEN]){
	sprintf(query, "UPDATE bbsc SET title='%s', name='%s', content='%s' WHERE seq='%s'", title, name, content, seq);
	mysqlQuery(connection, query);
}

void insertData(char title[MAX_LEN], char name[MAX_LEN], char content[MAX_LEN]){
	sprintf(query, "INSERT INTO bbsc (title, name, content) values('%s', '%s', '%s')", title, name, content);		
	mysqlQuery(connection, query);
}

