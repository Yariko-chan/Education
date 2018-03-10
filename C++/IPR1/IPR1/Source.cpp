#include <iostream>
#include <iomanip>

using namespace std;

ostream &message(ostream &stream)
{
	stream << "The number is  ";
	return stream;
}

ostream &dollar_hex(ostream &stream)
{
	stream << "The number in hex is ";
	stream << hex << setfill('$');
	return stream;
}

int main()
{
	cout << "Enter some number: ";
	int n;
	cin >> n;
	cout << message << n << endl;
	cout << dollar_hex << n << endl;
	cout << dollar_hex << setw(10) << n << endl;

	getchar();
	getchar();
	return 0;
}