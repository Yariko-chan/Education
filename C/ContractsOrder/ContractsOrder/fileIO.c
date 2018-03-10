#pragma once

#include <stdio.h>
#include <stdlib.h>
#include "constants.h"


//open file by filename in selected mode
//returns pointer to FILE
FILE * open_file(const char * filename, const char * mode) {
	FILE * fp;
	errno_t err;
	err = fopen_s(&fp, filename, mode);
	if (err != 0)
	{
		printf("Error occured while opening file.\n");
		confirmed_exit(EXIT_FAILURE);
	}
	return fp;
}

//close file by FILE pointer
void close_file(FILE * fp)
{
	if (fclose(fp) != 0)
		fprintf(stderr, "Error closing file\n");
}


//write Contract to end of file
void save_contract(Contract * c)
{
	FILE *fp;
	size_t err;

	fp = open_file(FILE_NAME, "ab"); 

	err = fwrite(c, sizeof(Contract), 1, fp);
	if (1 == err)
		puts("Contract successfully added.");
	else puts("Error occured, contract wasn't saved.");

	close_file(fp);
}

//return array of Contracts read from file
//long in arguments for saving length of array
Contract * get_contracts_list(long * count)
{
	FILE *fp;
	Contract * c; //array of contracts
	size_t readed;

	fp = open_file(FILE_NAME, "rb");

	//get count of contracts in file
	fseek(fp, 0L, SEEK_END);
	*count = ftell(fp) / sizeof(Contract);
	if (0 == *count)
		puts("NO CONTRACTS ADDED YET");
	rewind(fp);

	//allocate memory for array
	c = (Contract*)calloc(count, sizeof(Contract));
	
	//read array from file
	readed = fread_s(c, (long) count * sizeof(Contract), sizeof(Contract), count, fp);
	if (readed != *count)
		printf("Warning! File corrupted, some data can't be read.");

	close_file(fp);
	return c;
}

//rewrite file with changed array
void save_changes(Contract * c, long count)
{
	FILE *fp;
	size_t err;

	fp = open_file(FILE_NAME, "wb");

	//write array to file
	err = fwrite(c, sizeof(Contract), count, fp);
	if (count == err)
		puts("Changes successfully saved.");
	else puts("Error occured.");

	close_file(fp);
}

//try to open file for apppend
//creates void file if no file
void create_file_if_need(void)
{
	FILE * fp;
	fp = open_file(FILE_NAME, "ab");
	close_file(fp);

};