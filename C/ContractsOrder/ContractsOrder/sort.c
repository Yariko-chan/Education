#pragma once

#include <string.h>
#include <stdlib.h>

#include "constants.h"
#include "sort.h"

//sort Contract array
void q_sort(Contract * c, long count, char mode)
{
	switch (mode)
	{
	case 'n': qsort(c, count, sizeof(Contract), compare_num); break;
	case 'c': qsort(c, count, sizeof(Contract), compare_companies); break;
	default: break;
	}
}

//comparing Contracts by number
// 0: x1 = x2 | <0: x1 < x2 | >0: x1 > x2
int compare_num(const void * x1, const void * x2)
{
	int result;
	result = strcmp(((Contract *)x1)->num, ((Contract *)x2)->num);
	return result;
}

//comparing Contracts by company name
// 0: x1 = x2 | <0: x1 < x2 | >0: x1 > x2
int compare_companies(const void * x1, const void * x2)
{
	int result;
	result = strcmp(((Contract *)x1)->company, ((Contract *)x2)->company);
	return result;
}

//compare Contracts
// 0: x1 = x2 | <0: x1 < x2 | >0: x1 > x2
int compare(const void * x1, const void * x2, char mode)
{
	int res;
	switch (mode)
	{
	case 'c': 
		res = compare_companies(x1, x2);
		break;
	case 'n':
		res = compare_num(x1, x2);
		break;
	default:
		res = 0;//undefined result
		break;
	}
	return res;
}

void selection_sort(Contract * c, long count, char mode)
{
	int i, min;
	for (i = 0; i < count; i++)
	{
		min = get_min(c, i, count, mode);
		exch(c, i, min);
	}

}

//get index of min element in Contracts array from i to end
int get_min(Contract * c, long i, long count, char mode)
{
	int min = i;
	int res;

	for (i++; i < count; i++)
	{
		res = compare(c + min, c + i, mode);
		if (res > 0) min = i;
	}
	return min;
}

//swap two elements
void exch(Contract * c, long i, long j)
{
	Contract temp;
	temp = c[i];
	c[i] = c[j];
	c[j] = temp;
}

void shaker_sort(Contract * c, long count, char mode)
{
	int left, right, i, res;

	left = 0;
	right = count - 1;

	while (left <= right)
	{
		for (i = left; i < right; i++)
		{
			res = compare(c + i, c + i + 1, mode);
			if (res > 0) 
				exch(c, i, i + 1);  //if c[i] > c[i+1] then swap them
		}
		right--;
		for (i = right; i > left; i--)
		{
			res = compare(c + i - 1, c + i, mode);
			if (res > 0)
				exch(c, i, i - 1);  //if c[i - 1] > c[i] then swap them
		}
		left++;
	}
}

void shell_sort(Contract * c, long count, char mode)
{
	int base, i, j, res;

	base = 1;
	while (base < count / 3) //getting initial base
		base = 3 * base + 1;
	while (base >= 1)
	{
		for (i = base; i < count; i++)
		{
			//insert c[i] to his place in sequence c[i-base], c[i-2*base], c[i-3*base},...
			for (j = i; j >= base; j -= base)
			{
				res = compare(c + j - base, c + j, mode);
				if (res > 0)
					exch(c, j, j - base);
			}
		}
		base /= 3;
	}
}