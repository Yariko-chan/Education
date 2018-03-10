#include <stdio.h>
#include <string.h>
#include <ctype.h>

#define TYPE_LENGTH 20
#define RAW_LIST_LENGTH 10                             //count of raws in list

typedef struct
{
	char type[TYPE_LENGTH];
	float price;
	int count;
} raw;

void get_raw_list(raw[], int);                          //get list of raws from input
int compare(const void * x1, const void * x2);          //compare two structures by type for qsort
void sort_raw_list(raw*, int);                          //sort array of raw by type
void print_total(raw[], int);                           //print check
void get_exit_confirm();                                //exit program by signal from user

int main(void)
{
	raw list[RAW_LIST_LENGTH];

	get_raw_list(list, RAW_LIST_LENGTH);
	sort_raw_list(list, RAW_LIST_LENGTH);        
	print_total(list, RAW_LIST_LENGTH);

	get_exit_confirm();
}

void get_raw_list(raw list[], int N)
{
	int i;
	printf("Enter type of raw, price and count in one line, divide by space:\n");
	printf(" N |Type                 |Price |Count \n");
	printf("---|---------------------|------|------\n");
	for (i = 0; i < N; i++) {
		printf("%2d.|", i+1);
		scanf_s("%s %f %d",                                               //get one structure from input
			&list[i].type, TYPE_LENGTH, &list[i].price, &list[i].count);  //e. g., "aluminium 17.2 30" is 
	}                                                                     //raw {.type = "aluminium", .price = 17.2, .count = 30}
	printf("---------------------------------------\n");
}

void sort_raw_list(raw * list, int N)                   //pointer to first raw in argument
{
	qsort(list, N, sizeof(raw), compare);

}

//comparing structures
int compare(const void * x1, const void * x2)            // pointers to raws in arguments
{
	int result;
	result = strcmp(((raw*)x1)->type, ((raw*)x2)->type); //comparing uppercased type of two structures
	return result;                                       // 0: x1 = x2 | <0: x1 < x2 | >0: x1 > x2
}

//print full list of purchases and total sum
void print_total(raw list[], int N)
{
	int i;
	float cost, total = 0;
	printf("\n         ***PURCHASES CALCULATION***\n\n");
	printf(" N |Type                 |Price   |Count |Cost    \n");
	printf("---|---------------------|--------|------|--------\n");
	for (i = 0; i < N; i++) {
		cost = list[i].price*list[i].count;               //cost of those count of raw
		printf(" %2d|%*s |%7.2f |%2d    |%7.2f\n", 
			i+1, TYPE_LENGTH, list[i].type, list[i].price, list[i].count, cost);
		total += cost;                                    //total cost
	}
	printf("--------------------------------------------------\n");
	printf("TOTAL____________________________________ %7.2f\n", total);

}

void get_exit_confirm()
{
	char ch;
	while ((ch = getchar()) != '\n');                      //skip all characters typed previously
	printf("Press Enter to exit program.");
	getchar();
}