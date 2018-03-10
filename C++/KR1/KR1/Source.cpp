#include <iostream>
#include <string>

using namespace std;

class A {
private:
	string s;
public:
	A();
	A(string str);
	A(const A &);
	~A();

	string get_string();
	void set_string(string str);
};

class B {
private:
	string s;
public:
	B();
	B(string str);
	B(const B &);
	~B();

	string get_string();
	void set_string(string str);
};

class C {
private:
	string s;
public:
	C();
	C(string str);
	C(const C &);
	~C();

	string get_string();
	void set_string(string str);
	void set_string_from_AB(A a, B b);
};

int main(void) {
	A a("short string");
	B b("looong string");
	C c;
	c.set_string_from_AB(a, b);

	cout << "a = " << a.get_string() << endl;
	cout << "b = " << b.get_string() << endl;
	cout << "c = " << c.get_string() << endl;
	getchar();
}

A::A()
{
	s = "";
}

A::A(string str) : s(str) {}

A::A(const A & a)
{
	s = a.s;
}

A::~A()
{
}

string A::get_string()
{
	return s;
}

void A::set_string(string str)
{
	s = str;
}

B::B()
{
	s = "";
}

B::B(string str) : s(str) {}

B::B(const B & b)
{
	s = b.s;
}

B::~B()
{
}

string B::get_string()
{
	return s;
}

void B::set_string(string str)
{
	s = str;
}


C::C()
{
	s = "";
}

C::C(string str) : s(str) {}

C::C(const C & c)
{
	s = c.s;
}

C::~C()
{
}

string C::get_string()
{
	return s;
}

void C::set_string(string str)
{
	s = str;
}

void C::set_string_from_AB(A a, B b)
{
	string a_str = a.get_string();
	string b_str = b.get_string();

	if (a_str >= b_str) {
		s = a.get_string() + b.get_string();
	}
	else {
		s = b.get_string() + a.get_string();
	}
	
}

