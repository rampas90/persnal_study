#include "common.h"
#include "db_lib.h"

// gcc -o write_proc.cgi write_proc.c cgic.c -I/usr/include/mysql -L/usr/local/lib/mysql -lmysqlclient

/*
	1. 2차원배열
		char arr[4][MAX_LEN]
		설명 - 배열의 크기를 미리 정해놓고 위 의 경우는 4개 문자열이 담길 2depth 부분의 크기를 지정한다.
		문제점 -  사실상 2차원 배열을 쓰려고 하는 것 자체가 배열에 담긴 값을 반복문으로 처리해서 쿼리문을 실행하기 위함인데,
				  일단 1depth 크기부터 지정을 해야 하니 'key'에 해당하는 부분이 고정되어 버리고
				  (예를 들면 title, name, content  세개의 값으로 고정...여기서 date 라는 항목을 추가 하고 싶으면 배열을 또 수정해줘야 한다.


	2. 포인터 배열
		char *arr[]
		설명 - 포인터를 배열로 담는다. 즉 2차원 배열이지만 1depth arr[] 부분이 동적으로 할당된다.
		문제점 - 동적할당 부분은 배열이 아닌 포인터가 들어가기때문에 length 측정이 안된다.
				 sizeof(arr) / sizeof(arr[0] 로 측정시 선언한 포인터의 데이터형의 크기만 측정된다.... 							 
				 즉 char 포인터 배열에 4개의 문자열이 들어가 있다면 원하는 결과값은 4 지만 실제로는 char 형 포인터 4개이므로 8이 찍혀버린다.						    
				 _msize 라는 함수가 있지만 표준함수가 아니고, 
				 리눅스에서 사용할 또다른 함수도 완전하지 안하고 결과값이 다르게 찍힐때가 많아서 사실상 이 방법은 쓸 수 없다
		

*/

int cgiMain(void) {

	printf("%s%c%c\n","Content-Type:text/html;charset=utf-8;",13,10);

	char alertMsg[MAX_LEN];

	// 입력값 얻어냄 	
	cgiFormString("seq", seq, MAX_LEN);		// GET 전송 ( 수정할때 사용할 seq )
	cgiFormString("title", title, MAX_LEN);
	cgiFormString("name", name, MAX_LEN);
	cgiFormString("content", content, MAX_LEN);

	// DB연결 및 연결실패시 에러 console 출력 함수
	mysqlDbConn();

	//printf("%s / %s / %s / %s", seq, title, name, content);

	if(strcheck(seq)){	
		updateData(title, name, content, seq);
		strcpy(alertMsg, "글 수정이 완료되었습니다.");
		
	}else{
		insertData(title, name, content);
		strcpy(alertMsg, "글 등록이 완료되었습니다.");
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
	printf("		alert('%s') \n", alertMsg);
	printf("		window.location.href='list.cgi'; \n");
	printf("	</script> \n");
	printf("<head>\n");
	printf("<body>\n");
	printf("</body>\n</html>");

	// db 열결 해제
	mysqlDbClose();
		
}