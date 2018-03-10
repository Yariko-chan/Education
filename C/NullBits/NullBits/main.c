#include <stdio.h>

int get_non_null_count(char);

int main(void) {
	char ch = '_';
	printf("%c includes %d non-null bits", ch, get_non_null_count(ch));
	getchar();
	getchar();
}

int get_non_null_count(char ch) {
	int res = 0;
	for (int i = 0; i < 8; i++) {
		if (ch & '1' == '1') res++;
		ch >>= 1;
	}
	return res;
}