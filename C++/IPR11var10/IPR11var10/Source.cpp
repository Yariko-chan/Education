#include <iostream>
using namespace std;

/* 
* Написать функцию-шаблон, 
* переставляющую элементы в массиве.
*/

template <class T> void exch(T* array, int size, int i, int j) 
{
	if (i >= size || j >= size ||
		i < 0 || j < 0) {
		throw out_of_range("One or both idexes are out of bounds");
	}
	T i_value = array[i];
	array[i] = array[j];
	array[j] = i_value;
}

// test
int main()
{
	// create two arrays of different types
	int size = 5;
	int* int_array = new int[size];
	char* char_array = new char[size];

	// fill int array with numbers 1..5
	cout << "Initial int array: ";
	for (int i = 0; i < size; i++) {
		int_array[i] = i;
		cout << int_array[i] << " ";
	}
	cout << "\n";

	// fill char arrays with symbols A...E
	cout << "Initial char array: ";
	for (int i = 0; i < size; i++) {
		char_array[i] = i+65;
		cout << char_array[i] << " ";
	}
	cout << "\n";

	// replace 1st and 3rd elements in both
	// using one generic function
	cout << "\nReplace 1st and 3rd elements...\n\n";
	exch(int_array, size, 0, 2);
	exch(char_array, size, 0, 2);

	// print result
	cout << "Result int array: ";
	for (int i = 0; i < size; i++) {
		cout << int_array[i] << " ";
	}
	cout << "\n";
	cout << "Result char array: ";
	for (int i = 0; i < size; i++) {
		cout << char_array[i] << " ";
	}
	cout << "\n";

	getchar();
	return 0;
}