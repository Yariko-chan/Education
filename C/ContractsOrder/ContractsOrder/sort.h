#pragma once

#include "constants.h"

//sort Contract array
void q_sort(Contract * c, long count, char mode);

int compare_num(const void * x1, const void * x2);
int compare_companies(const void * x1, const void * x2);

//compare Contracts
// 0: x1 = x2 | <0: x1 < x2 | >0: x1 > x2
int compare(const void * x1, const void * x2, char mode);

void selection_sort(Contract * c, long count, char mode);

//get index of min element in Contracts array from i to end
int get_min(Contract * c, long i, long count, char mode);

//swap two elements
void exch(Contract * c, long i, long j);

void shaker_sort(Contract * c, long count, char mode);

void shell_sort(Contract * c, long count, char mode);