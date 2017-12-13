#include<stdio.h>
#include<stdlib.h>
#include "/usr/include/mysql/mysql.h"

/* gcc -o mysqltest mysqltest.c -I /usr/include/mysql -L /usr/local/lib/mysql -l mysqlclient*/

void main() {

	MYSQL *conn;
	MYSQL_RES *res;
	MYSQL_ROW row;

	char *server = "dev-smc.com:10022";
	char *user = "root";
	char *pwd = "audtls2A";
	char *database = "main";

	conn = mysql_init(NULL);

	if(!mysql_real_connect(conn, server, user, pwd, database, 0, NULL, 0)){
			fprintf(stderr, "%s\n", mysql_error(conn));
			exit(1);
	}

	if(mysql_query(conn, "show tables")){
			fprintf(stderr, "%s\n", mysql_error(conn));
			exit(1);
	}

	res = mysql_use_result(conn);

	printf("MySQL의 mysql 데이터베이스 리스트\n");
	while((row = mysql_fetch_row(res)) != NULL){
			printf("%s\n", row[0]);
	}

	mysql_free_result(res);
	mysql_close(conn);

}