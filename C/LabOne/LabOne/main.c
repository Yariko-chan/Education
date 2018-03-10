#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define GROUP_SIZE 3        //size of student group
#define NAME_LENGTH 14      //max length of name
#define PHONE_LENGTH 10     //max length of phone - 2 for operator code, 7 for number, f. e. 33 3455667
#define MAIL_LENGTH 21      //max length of mail
#define GROUP_LENGTH 7      //max length of group number
#define LONG_SEPARATOR "-------------------------------------------------------"
#define SHORT_SEPARATOR "--------------------"

struct student
{
	char name[NAME_LENGTH];
	char phone[PHONE_LENGTH];
	char mail[MAIL_LENGTH];
};

//allocates memory for one struct student
struct student * get_mem(void);
//allocates memory for array of struct student
void get_mem_group(struct student *st[], int);
//cleans memory
void cleanup(struct student *st[], int);
//initialize struct student by input
void init_student(struct student *);
//initialize array of struct student
void init_group(struct student *st[], int);
//prints info about one student in one line
void print_student(struct student *);
//prints info about all students of group in a table
void print_group(struct student *st[], int);
//skip all characters from input entered previously (they can be scanned instead of entered further)
void clean_scan();

void main(void)
{
	struct student * student1[GROUP_SIZE]; //array of pointers to struct student
	struct student * student2[GROUP_SIZE];
	char group1_num[GROUP_LENGTH + 1];       //group number
	char group2_num[GROUP_LENGTH + 1];

	//allocating memory for two arrays of struct student
	get_mem_group(student1, GROUP_SIZE);
	get_mem_group(student2, GROUP_SIZE);

	//getting number of first group
	puts(LONG_SEPARATOR);
	printf("\nEnter number of first group (%d digits): ", GROUP_LENGTH - 1);
	scanf_s("%s", &group1_num, sizeof(group1_num));
	//getting first list of students
	init_group(student1, GROUP_SIZE);
	clean_scan();

	//getting number of second group
	puts(LONG_SEPARATOR);
	printf("\nEnter number of second group (%d digits): ", GROUP_LENGTH - 1);
	scanf_s("%s", &group2_num, sizeof(group1_num));
	//getting second list of students
	init_group(student2, GROUP_SIZE);

	//printing list of student of first group
	puts(LONG_SEPARATOR);
	printf("\nGroup %s\n", group1_num);  //group number
	print_group(student1, GROUP_SIZE);   //group list in a table

										 //printing list of student of second group
	puts(LONG_SEPARATOR);
	printf("\nGroup %s\n", group2_num);  //group number
	print_group(student2, GROUP_SIZE);   //group list in a table

										 //clean memory
	cleanup(student1, GROUP_SIZE);
	cleanup(student2, GROUP_SIZE);

	clean_scan();
	getchar();
}

//allocates memory for one struct student
struct student * get_mem(void)
{
	struct student * st = (struct student *)malloc(sizeof(struct student));
	if (st == NULL) {
		printf("Out of memory\r\n");
		printf("Press Enter to close program");
		getchar();
		exit(EXIT_FAILURE);                  //if no memory can be allocated, close program with failure
	}
	return st;
}

//allocates memory for array of struct student
void get_mem_group(struct student * st[], int N)
{
	int i;
	for (i = 0; i < N; i++)
	{
		st[i] = get_mem();                   //initialize every pointer in array by pointer to allocated memory
	}
}

//clean memory
void cleanup(struct student * st[], int N) {
	int i;
	for (i = 0; i < N; i++)
	{
		free(st[i]);                         //free memory of every struct student in array
	}
}

//initialize struct student by input
void init_student(struct student * st)
{
	clean_scan();
	printf("    Name (up to %2d characters): ", NAME_LENGTH - 1);
	scanf_s("%s", st->name, NAME_LENGTH);   //get name of student

	clean_scan();
	printf("   Phone (up to %2d characters): +375", PHONE_LENGTH - 1);
	scanf_s("%s", st->phone, PHONE_LENGTH); //get phone of student

	clean_scan();
	printf("  E-mail (up to %2d characters): ", MAIL_LENGTH - 1);
	scanf_s("%s", st->mail, MAIL_LENGTH);   //get mail of student
}

//initialize array of struct student
void init_group(struct student * st[], int N)
{
	int i;
	for (i = 0; i < N; i++)
	{
		puts(SHORT_SEPARATOR);
		printf("Student %d\n", i + 1);
		init_student(st[i]);                  //get info for every student
	}
}

//print one student info in one line of table
void print_student(struct student * st)
{
	printf("|%*s| +375%*s|\n", NAME_LENGTH, st->name, PHONE_LENGTH, st->phone); //   |Name_______|+375__________|
}


//print info about all students of group in a table
void print_group(struct student * st[], int N)
{
	int i;
	printf("|%*s| %*s|\n", NAME_LENGTH, "Name", PHONE_LENGTH + 4, "Phone");     //header of table with names of columns
	puts(LONG_SEPARATOR);
	for (i = 0; i < N; i++)
	{
		print_student(st[i]);                                                   //info about every student
	}
}

//skip all characters from input entered previously (they can be scanned instead of entered further)
void clean_scan(void)
{
	char ch;
	while ((ch = getchar()) != '\n');
}
