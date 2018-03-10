#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "constants.h"
#include "input.h"
#include "output.h"
#include "search.h"

// initialize one Contract from input
void input_contract(Contract * c)
{
	get_string(c->num, "Num", "0112-16/01", NUM_LENGTH);
	get_string(c->date, "Date", "30/11/2016", DATE_LENGTH);
	get_string(c->company, "Company", "NewCompany", S_LENGTH);
	get_string(c->type, "Type", "buy/sell", TYPE_LENGTH);
	get_double(&c->cost, "Cost", "350.57");
}

//write message with tag and example
// e. g., "        Tag (    Example):"
//save input to string from arguments
void get_string(char * str, char tag[10], char example[10], int length) {
	clean_scan();
	printf("%11s (e. g., %11s): ", tag, example); 
	scanf_s("%s", str, length);
}

//write message with tag
// e. g., "        Tag (    Example):"
//save input to double from arguments
void get_double(double * f, char tag[10], char example[10])
{
	clean_scan();
	printf("%11s (e. g., %11s): ", tag, example); 
	scanf_s("%lf", f);
}

//skip all characters from input entered previously 
//(they can be scanned instead of entered further)
void clean_scan(void)
{
	char ch;
	while ((ch = getchar()) != '\n');
}

//exit program when user is ready
//result is 0 for suñcess and other for errors
void confirmed_exit(int result)
{
	clean_scan();
	printf("Press Enter to exit program.");
	getchar();
	exit(result);
}

//get confirmation (or not) for query in arguments
bool ask_confirm(char * str)
{
	char ans;
	do
	{
	    printf("%s (y/n): ", str);
		clean_scan();
		ans = getchar();
	} while (ans != 'y' && ans != 'n');
	if ('n' == ans) 
		return false;
	return true;
}

//returns 'c', 'n' or 'q'
char get_search_mode(void)
{
	char mode;
	do {
		print_search_mode_menu();
		clean_scan();
		mode = getchar();
	} while ((mode != 'n') && (mode != 'c') && (mode != 'q'));
	return mode;
}

//returns 'c', 'n' or 'q'
char get_sort_mode(void)
{
	char mode;
	do {
		print_sort_mode_menu();
		clean_scan();
		mode = getchar();
	} while ((mode != 'n') && (mode != 'c') && (mode != 'q'));
	return mode;
}

//scan string of given length
//skips if enter pressed
void read_str_or_skip(char * str, int length)
{
	char ch;
	int i;
	
	i = 0;
	while ((ch = getchar()) != '\n' && length != i)
	{
		str[i] = ch;
		i++;
	}
}

//scan float
//skips if enter pressed
void read_fl_or_skip(double * d)
{
	char ch;
	while ((ch = getchar()) != '\n') // read first char
	{                                // if enter skip
		ungetc(ch, stdin);           // else push readed char back to input strem
		scanf_s("%lf", d);           // read float
	}
}

//search contract for user
//return index of contract 
int search_contract(Contract *c, long count)
{
	char mode;
	char condition[S_LENGTH];
	int i;

	do {
		//select attribute to search
		mode = get_search_mode();

		//exit to main
		if ('q' == mode) return -1;

		//get condition for search
		if ('n' == mode)
			puts("Enter num for search:");
		else
			puts("Enter company name for search:");
		do {
			scanf_s("%s", condition, S_LENGTH);
		} while ('\n' == (char) condition);

		//search
		i = search(c, count, condition, mode);

		//print result
		if (-1 == i)
		{
			puts("Not found.");
			if (!ask_confirm("Do you want to search another contract ?"))
			{
				//if don't want, exit to main menu
				//else start while loop again
				return -1;
			}

		}
		else {
			puts("\n     FOUND:");
			printf("\n%*s %*s %*s %*s %8.2f\n\n",
				NUM_LENGTH, c[i].num, DATE_LENGTH, c[i].date, S_LENGTH, c[i].company,
				TYPE_LENGTH, c[i].type, c[i].cost);

			return i;
		}
	} while (true);
}

//print current value
//get new value or skip
void edit_str(char * editable, char tag[15], int length)
{
	char * temp; //temporary string
	temp = (char*)calloc(length, sizeof(char));

	printf("%*s %*s New: ", 15, tag, S_LENGTH, editable);
	read_str_or_skip(temp, length);
	if (strcmp(temp, ""))
		strcpy_s(editable, length, temp);

	free(temp);
}

//print current value
//get new value or skip
void edit_double(double * editable, char tag[15])
{
	printf("%*s %*.2f New: ", 15, tag, S_LENGTH, *editable);
	read_fl_or_skip(editable);
}

