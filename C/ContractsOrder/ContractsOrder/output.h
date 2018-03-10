#pragma once

#include "constants.h"

/*
Select operation to continue:
a Add new contract.      v View list of contracts.
e Edit record.           d Delete record.
q Exit program.
*/
void print_main_menu();

/*
************CONTRACTS************
|          N|      Date|           Partner|Type|   Cost|
--------------------------------------------------------
|           |          |                  |    |       |
--------------------------------------------------------
*/
void display_contracts_list(Contract *, long count);

/*
First, we need to find contract.
How would we search?
n By number       c By company name
q Exit
*/
void print_search_mode_menu(void);

/*
On which basis would you like to sort?

n By number       c By company name
q Exit
*/
void print_sort_mode_menu(void);

/*
Select sorting algorithm:
s Selection sort.      h Shaker sort.
q Quick sort.          d Shell sort.
q Exit program.
*/
void print_sort_type_menu(void);