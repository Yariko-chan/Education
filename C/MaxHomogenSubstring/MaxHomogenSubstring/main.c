#include <stdio.h>

char max_substr(char*, int*);

int main(void) {
	char * str = "aaaaaaaaaaaaaabbbbcccddeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeef";
	int count = 0;
	printf("The max homogen substring of %s is %d %c's", str, count, max_substr(str, &count));
	getchar();
}

char max_substr(char * str, int * max)
{
	int n = strlen(str);
	*max = 0;
	char max_ch = ' ';

	for (int i = 1; i < n;) {
		int count = 1;
		while (str[i - 1] == str[i]) {
			count++;
			i++;
		}
		if (*max < count) {
			max_ch = str[i - 1];
			*max = count;
		}
		i++;
	}

	return max_ch;
}
