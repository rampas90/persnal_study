#include <stdio.h>
#include "cgic.h"
#include <string.h>
#include <stdlib.h>
#include <math.h>

#define MAX_LEN 255 /* 문자열 배열의 최대 길이 */
#define SITE_URL "http://dev-smc.com/cgi-bin"

char seq[MAX_LEN], name[MAX_LEN], title[MAX_LEN], content[MAX_LEN];
char query[MAX_LEN];

int strcheck(char *str) { /* 문자열이 NULL이거나 길이가 0인지 체크 */
	if (str == NULL) return 0;
	if (strlen(str) == 0) return 0;	
	return 1;
}