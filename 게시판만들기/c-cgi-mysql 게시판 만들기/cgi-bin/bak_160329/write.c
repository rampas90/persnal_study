#include "common.h"

//  gcc -o write.cgi write.c cgic.c -I/usr/include/mysql -L/usr/local/lib/mysql -lmysqlclient

int cgiMain(void) {
	
	/* 헤더 속성 최상단에 위치 - 헤더위에 print 문등이 있으면 500 error 발생 */
	printf("%s%c%c\n","Content-Type:text/html;charset=utf-8",13,10);
	
	int i;

	// 입력값 얻어냄 	
	cgiFormString("seq", seq, MAX_LEN);		// GET 전송 ( 수정할때 사용할 seq )
	cgiFormString("title", title, MAX_LEN);
	cgiFormString("name", name, MAX_LEN);
	cgiFormString("content", content, MAX_LEN);


	// seq 값 있을시 
	if(strcheck(seq)){

		// db 연결
		mysqlDbConn();

		sprintf(query, "SELECT * FROM bbsc WHERE seq='%s'", seq);
	
		if(mysql_query(connection, query) ){
			fprintf(stderr, "%s\n", mysql_error(&conn));
			exit(0);
		}
		
		res = mysql_store_result(connection);
		row = mysql_fetch_row(res);
		
		/*
			seq_val, title_val 등은 MAX_LENTH 를 지정한 "배열" 이기 때문에 문자열을 바로 대입할수 없다.
			즉 seq_val = row[0]  식의 대입은 안된다.
			따라서 배열에 문자열을 넣는 방법중의 하나인 문자열 복사 함수(strcpy)를 이용한다.
		*/
		strcpy(seq_val, row[0]);
		strcpy(title_val, row[1]);
		strcpy(name_val, row[2]);
		strcpy(content_val, row[3]);

		mysql_close(connection);
	}

	printf("<!doctype html>\n");
	printf("<html lang=\"ko\">\n");
	printf("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
	printf("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n");

	printf("<head> \n");
	printf("	<title>CGI - board</title>\n");
	//printf("	<link href=\"https://maxcdn.bootstrapcdn.com/bootswatch/3.3.6/paper/bootstrap.min.css\" rel=\"stylesheet\" > \n");
	printf("	<link href=\"https://maxcdn.bootstrapcdn.com/bootswatch/3.3.6/slate/bootstrap.min.css\" rel=\"stylesheet\" > \n");
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
	printf("		<form method='post' name='update_frm' id='update_frm' action='write_proc.cgi'> \n");
	printf("			<input type='hidden' name='seq' id='seq' value='%s'>\n", seq_val);
	printf("			<table class='table'>\n");
	printf("				<tr>");
	printf("					<th class='active col-xs-1'>제목</th>\n");
	printf("					<td><input name='title' id='title' class='form-control' value='%s'></td>\n", title_val);
	printf("				</tr>\n");
	printf("				<tr>\n");
	printf("					<th class='active col-xs-1'>이름</th>\n");
	printf("					<td><input name='name' id='name' class='form-control' value='%s'></td>\n", name_val);
	printf("				</tr>\n");
	printf("				<tr>\n");
	printf("					<th class='active col-xs-1'>본문</th>\n");
	printf("					<td><textarea name='content' id='content' class='form-control' cols='60' rows='10' >%s</textarea></td>\n", content_val);
	printf("				</tr>\n");
	printf("			</table>\n");
	printf("		</form>\n");
	printf("		<button class='btn btn-default' onclick='goWrite();' >작성</button> \n");
	printf("		<button class='btn btn-default' onclick='goList();' >리스트</button> \n");
	printf("	</div>\n");
	printf("</div>\n");
	printf("</body>\n</html>");
		



	/*
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
		//printf("	<link href=\"https://maxcdn.bootstrapcdn.com/bootswatch/3.3.6/paper/bootstrap.min.css\" rel=\"stylesheet\" > \n");
		printf("	<link href=\"https://maxcdn.bootstrapcdn.com/bootswatch/3.3.6/slate/bootstrap.min.css\" rel=\"stylesheet\" > \n");
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
		


		printf("		</table>\n");
		printf("		<button class='btn btn-default' onclick='goList();' >확인</button> \n");
		printf("	</div>\n");
		printf("</div>\n");
		printf("</body>\n</html>");

		//logView(title, name, content, update_title);

		if(!mysql_query(connection, query) ){
			//printf("진입\n");
			fprintf(stderr, "%s\n", mysql_error(&conn));
			exit(0);
		}
		exit(1);
	}
	*/

} 