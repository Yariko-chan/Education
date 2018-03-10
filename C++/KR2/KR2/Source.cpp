#include <iostream>

using namespace std;

const int default_length = 10;

class Vect {
private:
	int* arr;
	int size;
public:
	Vect();
	Vect(int length);
	Vect(const Vect &);
	~Vect();

	void input();
	int get(int) const;

	friend ostream& operator << (ostream& os, Vect& v);
	Vect& operator >> (Vect& v2);
};

int main(void) {
	Vect a, b, c;

	a.input();
	b.input();
	c.input();
	cout << "a = " << a << endl;
	cout << "b = " << b << endl;
	cout << "c = " << c << endl;

	a >> b >> c;
	cout << "a >> b >> c" << endl;
	cout << "a = " << a << endl;
	cout << "b = " << b << endl;
	cout << "c = " << c << endl;

	getchar();
	getchar();
}

Vect::Vect()
{
	size = default_length;

	arr = new int[size];
	for (int i = 0; i < size; i++) {
		arr[i] = 0;
	}
}

Vect::Vect(int length)
{
	if (length <= 0) {
		cout << "arg length must be > 0 " << endl;
		size = default_length;
		cout << "size of Vect initiated to " << default_length << endl;
	}
	else {
		size = length;
	}

	arr = new int[size];
	for (int i = 0; i < size; i++) {
		arr[i] = 0;
	}
}

Vect::Vect(const Vect & v)
{
	size = v.size;
	delete[] arr;
	arr = new int[size];
	for (int i = 0; i < size; i++) {
		arr[i] = v.get(i);
	}
}

Vect::~Vect()
{
	delete [] arr;
}

void Vect::input()
{
	cout << "Enter " << size << " values for vector: " << endl;
	for (int i = 0; i < size; i++) {
		cout << i << ": ";
		cin >> arr[i];
	}
}

int Vect::get(int i) const
{
	if (i >= size || i < 0) return -1;
	return arr[i];
}

ostream & operator<<(ostream & os, Vect & v)
{
	os << "{ ";
	for (int i = 0; i < v.size - 1; i++) {
		os << v.get(i) << ", ";
	}
	os << v.get(v.size - 1) << " }" << endl;
	return os;
}

Vect & Vect::operator >> (Vect & v)
{
	// if not static, values would be deleted at the end of function
	static Vect temp = v; 

	// new size of arr
	v.size = size;
	delete[] v.arr;
	v.arr = new int[v.size];

	// copy this.arr to v.arr
	for (int i = 0; i < v.size; i++) {
		v.arr[i] = get(i);
	}
	return temp;
}
