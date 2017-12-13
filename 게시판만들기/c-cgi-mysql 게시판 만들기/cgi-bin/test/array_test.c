#include <stdio.h>

int main(void){
	char arr[4][17] = {"Korea", "Brazil", "Germany", "papua new Guinea"};
	char *ct[] = {"Korea", "Brazil", "Germany", "papua new Guinea"};
	printf("na[2] : %s \n", arr[2]);
	printf("na[2][3] : %c \n", arr[2][3]);
	printf("ct[2] : %s \n", ct[2]);
	printf("ct[2][3] : %c \n", ct[2][3]);
	return 0;
}