#pragma once

#include "constants.h"

// initialize one Contract from input
void input_contract(Contract * c);

//write message with tag and example
// e. g., "        Tag (    Example):"
//save input to string from arguments
void get_string(char * str, char tag[10], char example[10], int length);

//write message with tag
// e. g., "        Tag (    Example):"
//save input to double from arguments
void get_double(double * f, char tag[10], char example[10]);

//skip all characters from input entered previously 
//(they can be scanned instead of entered further)
void clean_scan(void);

//exit program when user is ready
//result is 0 for suñcess and other for errors
void confirmed_exit(int result);

//get confirmation (or not) for query in arguments
bool ask_confirm(char * str);

//returns 'c', 'n' or 'q'
char get_search_mode(void);

//returns 'c', 'n' or 'q'
char get_sort_mode(void);

//scan string of given length
//skips if enter pressed
void read_str_or_skip(char * str, int length);

//scan float
//skips if enter pressed
void read_fl_or_skip(double * d);

//search contract for user
//return index of contract 
int search_contract(Contract *c, long count);

//print current value
//get new value or skip
void edit_str(char * editable, char tag[15], int length);

//print current value
//get new value or skip
void edit_double(double * editable, char tag[15]);
