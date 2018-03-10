#pragma once

#include <stdio.h>
#include "constants.h"

//open file by filename in selected mode
//returns pointer to FILE
FILE * open_file(const char * filename, const char * mode);

//close file by FILE pointer
void close_file(FILE * fp);

//write Contract to end of file
void save_contract(Contract * c);

//return array of Contracts read from file
//long in arguments for saving length of array
Contract * get_contracts_list(long *);

//rewrite file with changed array
void save_changes(Contract * c, long count);

//try to open file for apppend
//creates void file if no file
void create_file_if_need(void);
