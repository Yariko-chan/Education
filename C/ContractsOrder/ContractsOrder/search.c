#include <stdio.h>
#include "constants.h"

//linear search
int search(Contract * c, long count, char * condition, char mode)
{
	int i, res;

	for (i = 0; i < count; i++)
	{
		switch (mode)
		{
		case 'n': res = strcmp(c[i].num, condition); break;       //compare by num
		case 'c': res = strcmp(c[i].company, condition); break;   //compare by company name
		default:/*Error*/ break;
		}
		if (0 == res) return i;
	}
	return -1;
}