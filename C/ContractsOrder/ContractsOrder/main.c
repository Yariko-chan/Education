#include <stdio.h>
#include <string.h>
#include <malloc.h>
#include "constants.h"
#include "fileIO.h"
#include "input.h"
#include "main.h"
#include "output.h"
#include "search.h"
#include "sort.h"

int main(void) {
	char choice;

	create_file_if_need();

	printf("\n         Welcome to ContractsOrder!\n");
	do {
		print_main_menu();
		choice = getchar();
		switch (choice)
		{
		case 'a': add_contract(); break;
		case 'v': view_contracts_list(); break;
		case 'e': edit(); break;
		case 'd': delete(); break;
		case 's': sort(); break;
		case 'q': printf("Exit\n"); break;
		default: printf("No such operation. Try again.\n"); break;
			break;
		}
		clean_scan();
	} while (choice != 'q');
}

void add_contract(void)
{
	Contract c;

	puts("\n               ***NEW CONTRACT***\n");
	input_contract(&c);
	save_contract(&c);
}

void view_contracts_list(void)
{
	Contract * c;
	long count;

	c = get_contracts_list(&count);
	display_contracts_list(c, count); 

	//free allocated array
	free(c);
}

//full procedure of deletion
void delete(void)
{
	Contract * c;
	long count;
	long i;

	puts("\n            ***DELETE CONTRACT***\n");

	c = get_contracts_list(&count);

	//index of contract to delete
	i = search_contract(c, count);
	//exit to main menu
	if (-1 == i)
	{
		//free allocated array
		free(c);
		return;
	}

    //deletion
	if (ask_confirm("Are you sure you want delete this contract?"))
	{
		delete_contract(c, i, count);
		save_changes(c, count - 1);
	}

	//free allocated array
	free(c);
}

//delete contract from list
void delete_contract(Contract * c, long index, long count)
{
	int i;

	//move all elements after deleted to previuos position
	for (i = index; i < (count - 1); i++)
	{
		c[i] = c[i + 1];
	}

	puts("\nContract successfully deleted.");
}

//full procedure of editing
void edit(void)
{
	Contract * c;
	long count;
	long i;

	puts("\n              ***EDIT CONTRACT***\n");

	c = get_contracts_list(&count);

	//index of contract to delete
	i = search_contract(c, count);
	//exit to main menu
	if (-1 == i)
	{
		free(c);
		return;
	}

	edit_contract(c, i);
	save_changes(c, count);

	//free allocated memory
	free(c);
}

//edit contract in array
void edit_contract(Contract * c, int i) {

	puts("Enter new values or press Enter to skip.\n");
	clean_scan();
	edit_str(c[i].num, "Number", NUM_LENGTH);
	edit_str(c[i].date, "Date", DATE_LENGTH);
	edit_str(c[i].company, "Company", S_LENGTH);
	edit_str(c[i].type, "Number", NUM_LENGTH);
	edit_double(&(c[i].cost), "Cost");
}

void sort(void)
{
	Contract * c;
	long count;
	char mode;
	char choice;

	c = get_contracts_list(&count);

	do {

	    //select attribute for search
	    mode = get_sort_mode();
	    
	    //exit to main
		if ('q' == mode)
		{
			free(c);
			return;
		}

		//select sort algorithm
		print_sort_type_menu();
		clean_scan();
		choice = getchar();

		//sort
		switch (choice)
		{
		case 's': selection_sort(c, count, mode); break;
		case 'h': shaker_sort(c, count, mode); break;
		case 'u': q_sort(c, count, mode); break;
		case 'd': shell_sort(c, count, mode); break;
		case 'q': printf("Exit\n"); break;
		default: printf("No such operation. Try again.\n"); break;
			break;
		}
		if ('s' == choice || 'h' == choice || 'u' == choice || 'd' == choice)
			display_contracts_list(c, count);
	} while (choice != 'q');

	save_changes(c, count);

	//free allocated array
	free(c);
}