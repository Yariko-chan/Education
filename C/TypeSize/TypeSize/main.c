/*prints sizes for primitive types*/
#include <stdio.h>

int main(void) {
	/* %zd is for sizes*/

	printf("Type int has size of %u bytes.\n", sizeof(int));
	printf("Type short has size of %u bytes.\n", sizeof(short));
	printf("Type long has size of %u bytes.\n", sizeof(long));
	printf("Type float has size of %u bytes.\n", sizeof(float));
	printf("Type double has size of %u bytes.\n", sizeof(double));
	printf("Type char has size of %u bytes.\n", sizeof(char));


	getchar();
	return 0;
}