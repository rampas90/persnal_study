#include <stdio.h>
#include "cgic.h"
#include <string.h>
#include <stdlib.h>
#include <mysql.h>
#include "common.h"

//  gcc -o write.cgi write.c cgic.c -I/usr/include/mysql -L/usr/local/lib/mysql -lmysqlclient

int cgiMain(void) {
	
	/* 헤더 속성 최상단에 위치 - 헤더위에 print 문등이 있으면 500 error 발생 */
	printf("%s%c%c\n","Content-Type:text/html;charset=utf-8",13,10);	

	char seq[MAX_LEN];
	char name[MAX_LEN], title[MAX_LEN], content[MAX_LEN];
	char update_seq[MAX_LEN], update_name[MAX_LEN], update_title[MAX_LEN], update_content[MAX_LEN];
	char email[MAX_LEN], homepage[MAX_LEN], mode[MAX_LEN];
	char alertMsg[MAX_LEN];
	char query[MAX_LEN];

	// 입력값 얻어냄 	
	cgiFormString("seq", seq, MAX_LEN);		// GET 전송 ( 수정할때 사용할 seq )
	
	MYSQL *connection=NULL, conn;
	MYSQL_RES *res;							// (SELECT, SHOW, DESCRIBE, EXPLAIN)등의 쿼리를 내렸을때 그 결과를 다루기 위해 사용되는 구조체이다.
	MYSQL_ROW row;							// 데이터의 하나의 row 값을 가리킨다. 만약 row 값이 없다면 null 을 가르키게 된다.

	// mysql 접속정보
	char *server = "dev-smc.com";
	char *user = "root";
	char *pwd = "audtls2A";
	char *database = "main";
	int i;
	
	// mysql 한글문자열 깨짐 방지
	mysql_init(&conn);
	mysql_options(&conn, MYSQL_SET_CHARSET_NAME, "utf8");
	mysql_options(&conn, MYSQL_INIT_COMMAND, "SET NAMES utf8");
	
	connection = mysql_real_connect(&conn, server, user, pwd, database, 3306, (char *)NULL, 0);

	if(connection == NULL){
		fprintf(stderr, "%s\n", mysql_error(&conn));
		exit(1);
	}

	// modify
	if(strcheck(seq)){

		//printf("MODIFY \n");
		//exit(1);
		
		sprintf(query, "SELECT * FROM bbsc WHERE seq='%s'", seq);
		
		if(mysql_query(connection, query) ){
			fprintf(stderr, "%s\n", mysql_error(&conn));
			exit(0);
		}

		res = mysql_store_result(connection);
		row = mysql_fetch_row(res);
	

		printf("<!doctype html>\n");
		printf("<html lang=\"ko\">\n");
		printf("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
		printf("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n");

		printf("<head> \n");
		printf("	<title>CGI - board</title>\n");
		printf("	<link href=\"https://maxcdn.bootstrapcdn.com/bootswatch/3.3.6/paper/bootstrap.min.css\" rel=\"stylesheet\" > \n");
		printf("	<script src=\"https://code.jquery.com/jquery-1.12.1.min.js\"></script> \n");
		printf("	<script > \n");
		printf("			//alert('123') \n");
		printf("			function goList(){ \n");
		printf("				location.href='list.cgi'; \n");
		printf("			}; \n");
		printf("			function goWrite(){ \n");
		printf("				var form = document.update_frm \n");
		printf("				form.submit(); \n");
		printf("			}; \n");
		printf("	</script> \n");
		printf("<head>\n");

		printf("<body>\n");
		printf("<div class='container'>\n");
		printf("	<div class='row'>\n");
		printf("		<h2>CGI - board</h2>\n");
		printf("		<form method='post' name='update_frm' id='update_frm' action='write.cgi'> \n");
		printf("			<input type='hidden' name='update_seq' value='%s'>\n", seq);
		printf("			<table class='table'>\n");
		printf("				<tr>");
		printf("					<th class='active col-xs-1'>제목</th>\n");
		printf("					<td><input name='update_title' id='update_title' class='form-control' value='%s'></td>\n", row[1]);
		printf("				</tr>\n");
		printf("				<tr>\n");
		printf("					<th class='active col-xs-1'>이름</th>\n");
		printf("					<td><input name='update_name' id='update_name' class='form-control' value='%s'></td>\n", row[2]);
		printf("				</tr>\n");
		printf("				<tr>\n");
		printf("					<th class='active col-xs-1'>본문</th>\n");
		printf("					<td><textarea name='update_content' id='update_content' class='form-control' cols='60' rows='10' >%s</textarea></td>\n", row[3]);
		printf("				</tr>\n");
		printf("			</table>\n");
		printf("		</form>\n");
		printf("		<button class='btn btn-default' onclick='goWrite();' >작성</button> \n");
		printf("		<button class='btn btn-default' onclick='goList();' >리스트</button> \n");
		printf("	</div>\n");
		printf("</div>\n");
		printf("</body>\n</html>");
		
		
	}

	// insert
	else{
		cgiFormString("title", title, MAX_LEN);
		cgiFormString("name", name, MAX_LEN);
		cgiFormString("content", content, MAX_LEN);
		
		cgiFormString("update_seq", update_seq, MAX_LEN);
		cgiFormString("update_title", update_title, MAX_LEN);
		cgiFormString("update_name", update_name, MAX_LEN);
		cgiFormString("update_content", update_content, MAX_LEN);

		printf("<!doctype html>\n");
		printf("<html lang=\"ko\">\n");
		printf("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
		printf("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n");

		printf("<head> \n");
		printf("	<title>CGI - board</title>\n");
		printf("	<link href=\"https://maxcdn.bootstrapcdn.com/bootswatch/3.3.6/paper/bootstrap.min.css\" rel=\"stylesheet\" > \n");
		printf("	<script src=\"https://code.jquery.com/jquery-1.12.1.min.js\"></script> \n");
		printf("	<script > \n");
		printf("		function goList(){ \n");
		printf("			location.href='list.cgi'; \n");
		printf("		}; \n");
		printf("	</script> \n");
		printf("<head>\n");

		printf("<body>\n");
		printf("<div class='container'>\n");
		printf("	<div class='row'>\n");
		printf("		<h2>CGI - board</h2>\n");
		printf("		<div class='panel panel-default'>");
		printf("			<div class='panel-heading'>입력이 완료되었습니다.</div>");
		printf("			<div class='panel-body'>");
		printf("			 리스트 페이지로 이동하려면 확인버튼을 누르세요.  \n");
		printf("			</div>");
		printf("		</div>");
		printf("		<table width='600' class='table table-bordered'>\n");
		
		if(strcheck(update_title)){
			printf("			<tr><th class='active col-xs-1'>제목</th> <td>%s</td></tr>\n", update_title);
			printf("			<tr><th class='active col-xs-1'>작성자</th> <td>%s</td></tr>\n", update_name);
			printf("			<tr><th class='active col-xs-1'>내용</th> <td>%s</td></tr>\n", update_content);
			sprintf(query, "UPDATE bbsc SET title='%s', name='%s', content='%s' WHERE seq='%s'", update_title, update_name, update_content, update_seq);
			//printf("%s", query);
		}else{
			printf("			<tr><th class='active col-xs-1'>제목</th> <td>%s</td></tr>\n", title);
			printf("			<tr><th class='active col-xs-1'>작성자</th> <td>%s</td></tr>\n", name);
			printf("			<tr><th class='active col-xs-1'>내용</th> <td>%s</td></tr>\n", content);
			sprintf(query, "INSERT INTO bbsc (title, name, content) values('%s', '%s', '%s')", title, name, content);		
		}

		printf("		</table>\n");
		printf("		<button class='btn btn-default' onclick='goList();' >확인</button> \n");
		printf("	</div>\n");
		printf("</div>\n");
		printf("</body>\n</html>");

		/* 로그확인용
		printf("title 타입 확인 : %s \n", title);
		printf("update_title 타입 확인 : %s \n", update_title);
		printf("title : %s \n name : %s \n content : %s \n", title, name, content);
		*/	

		if(!mysql_query(connection, query) ){
			//printf("진입\n");
			fprintf(stderr, "%s\n", mysql_error(&conn));
			exit(0);
		}
		exit(1);
	}

    mysql_close(connection);
} 