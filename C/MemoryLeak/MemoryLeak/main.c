#include <stdio.h>

int main(void){
	unsigned long int n;
	while (n = malloc(10000)) printf("%ul\n", n);

	getchar();
	getchar();
}