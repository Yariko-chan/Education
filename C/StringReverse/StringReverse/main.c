#include <stdio.h>
#include <string.h>

void string_reverse(char *);

int main(void) {
	char string[] = "";
	string_reverse(string);
	puts(string);
	getchar();
}

void string_reverse(char * str)
{
	int length = strlen(str);
	for (int i = 0; i < (int)length / 2; i++) {
		str[length] = str[i];
		str[i] = str[length - i - 1];
		str[length - i - 1] = str[length];
	}
	str[length] = '\0';
}
