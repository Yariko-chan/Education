#pragma once

#include <stdio.h>
#include "constants.h"

/*
Select operation to continue:
a Add new contract.      v View list of contracts.
e Edit record.           d Delete record.
q Exit program.
*/
void print_main_menu(void)
{
	printf("\n\nSelect operation to continue:\n");
	printf("a Add new contract.      ");
	printf("v View list of contracts.\n");
	printf("e Edit record.           ");
	printf("d Delete record.         \n");
	printf("s Sort list of contracts ");
	printf("q Exit program. \n");
}

/*
               ************CONTRACTS************
|          N|      Date|           Partner|Type|   Cost|
--------------------------------------------------------
|           |          |                  |    |       |
--------------------------------------------------------
*/
void display_contracts_list(Contract * c, long count)
{
	int i;

	puts("\n               ************CONTRACTS************\n");
	printf("|%*s|%*s|%*s|%*s|%*s|\n", 
		NUM_LENGTH, "N", DATE_LENGTH, "Date", S_LENGTH, "Company", TYPE_LENGTH, "Type", 8, "Cost");
	puts("--------------------------------------------------------------");
	for (i = 0; i < count; i++) {
		Contract * temp = c + i;
		printf("|%*s|%*s|%*s|%*s|%8.2f|\n",
			NUM_LENGTH, c[i].num, DATE_LENGTH, c[i].date, S_LENGTH, c[i].company, TYPE_LENGTH, c[i].type, c[i].cost);
	}
	puts("--------------------------------------------------------------");
}

/*
We need to find contract.
How would we search?

n By number       c By company name
q Exit
*/
void print_search_mode_menu(void)
{
	puts("We need to find contract.");
	puts("How would we search?\n");
	puts("n By number       c By company name ");
	puts("q Exit to main.");
}

/*
On which basis would you like to sort?

n By number       c By company name
q Exit
*/
void print_sort_mode_menu(void)
{
	puts("On which basis would you like to sort?\n");
	puts("n By number       c By company name ");
	puts("q Exit to main");
}

/*
Select sorting algorithm:
s Selection sort.      h Shaker sort.
q Quick sort.          d Shell sort.
q Exit program.
*/
void print_sort_type_menu(void)
{
	printf("\n\nSelect sorting algorithm:\n");
	printf("s Selection sort.      ");
	printf("h Shaker sort.\n");
	printf("u Quick sort.          ");
	printf("d Shell sort.\n");
	printf("q Exit to main. \n");
}