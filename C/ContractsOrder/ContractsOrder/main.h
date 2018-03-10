#pragma once

#include "constants.h"

void add_contract(void);

void view_contracts_list(void);

//full procedure of deletion
void delete(void);

//delete contract from contracts array
void delete_contract(Contract * c, long index, long count);

//full procedure of editing
void edit(void);

//edit contract in array
void edit_contract(Contract * c, int i);

void sort(void);