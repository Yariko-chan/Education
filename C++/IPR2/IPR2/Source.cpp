#include <iostream>

using namespace std;

class Rectangle {
private:
	int width;
	int height;
public:
	Rectangle();
	Rectangle(int, int);
	Rectangle(const Rectangle &);
	~Rectangle();

	int get_width() const;
	int get_height() const;
	void set_width(int);
	void set_height(int);

	int square() const;
	int perimeter() const;
	void print_rectangle() const;
};

int main(void) {
	int width, height;

	cout << "Rectangle" << endl;
	cout << "Enter width: ";
	cin >> width;
	cout << "Enter height: ";
	cin >> height;

	Rectangle r;
	try {
		r.set_width(width);
		r.set_height(height);
	}
	catch (invalid_argument e) {
		cout << "Exception: " << e.what() << endl;
		cout << "Created rectangular with default values" << endl;
		r.set_width(1);
		r.set_height(1);
	}

	r.print_rectangle();
	int sq = r.square();
	int per = r.perimeter();
	cout << "Square " << sq << endl;
	cout << "Perimeter " << per << endl;

	getchar();
	getchar();
}

Rectangle::Rectangle()
{
	width = 1;
	height = 1;
}

Rectangle::Rectangle(int w, int h)
{
	if (w <= 0 || h <= 0) {
		throw invalid_argument("Width and height must be greater than 0.");
	}
	else {
		width = w;
		height = h;
	}
}

Rectangle::Rectangle(const Rectangle & r)
{
	width = r.get_width();
	height = r.get_height();
}

Rectangle::~Rectangle()
{
}

int Rectangle::get_width() const
{
	return width;
}

int Rectangle::get_height() const
{
	return height;
}

void Rectangle::set_width(int w)
{
	if (w <= 0) {
		throw invalid_argument("Width must be greater than 0.");
	}
	else {
		width = w;
	}
}

void Rectangle::set_height(int h)
{
	if (h <= 0) {
		throw invalid_argument("Height must be greater than 0.");
	}
	else {
		height = h;
	}
}

int Rectangle::square() const
{
	return width*height;
}

int Rectangle::perimeter() const
{
	return 2 * (width + height);
}

void Rectangle::print_rectangle() const
{
	cout << "Rectangle" << endl;
	cout << "width = " << width << endl;
	cout << "height = " << height << endl;

	cout << " ";
	for (int i = 0; i < width; i++) {
		cout << "_";
	}
	cout << endl;
	for (int i = 0; i < height; i++) {
		cout << "|"; 
		for (int j = 0; j < width; j++) {
			cout << " ";
		}
		cout << "|" << endl;
	}
	cout << " ";
	for (int i = 0; i < width; i++) {
		cout << "_";
	}
	cout << endl;
}
