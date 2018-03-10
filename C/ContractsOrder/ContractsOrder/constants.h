#pragma once

#define NUM_LENGTH 11//includes terminating null
#define DATE_LENGTH 11 
#define TYPE_LENGTH 5
#define S_LENGTH 21 
#define FILE_NAME "list.co"

#define true 1
#define false 0

typedef struct {
	char num[NUM_LENGTH];  //1234-12/01
	char date[DATE_LENGTH];//20/12/2016
	char company[S_LENGTH];
	char type[TYPE_LENGTH]; // buy/sell
							//BETTER: replace type with work
							//char work_name[S_LENGTH];//haven't enoughp place when display table
	double cost; //or double?
} Contract;

typedef int bool;