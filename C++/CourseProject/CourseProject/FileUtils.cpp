#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <ios>
#include "FileUtils.h"

using namespace std;

#define FILENAME "Cars"

int maxId = 0;

Car::Car() :
	id(-1),
	transmissionType(0),
	driveType(0),
	fuelType(0),
	price(0)
{
	firm[0] = '\0';
	model[0] = '\0';
	bodyType[0] = '\0';
}

Car::Car(const char* f, const char* m, const char* bt,
	int tt, int dt, int ft, float p) :
	id(maxId++),
	transmissionType(tt),
	driveType(dt),
	fuelType(ft),
	price(p)
{
	strncpy_s(firm, sizeof(firm) - 1, f, sizeof(firm) - 1);
	strncpy_s(model, sizeof(model) - 1, m, sizeof(model) - 1);
	strncpy_s(bodyType, sizeof(bodyType) - 1, bt, sizeof(bodyType) - 1);
}

void add(const Car& car)
{
	std::ofstream file(FILENAME, std::ios::app | std::ios::binary); // open for adding to the end
	if (!file) throw FileException("Cannot open file for writing");

	file.write(reinterpret_cast<const char *>(&car), sizeof(car));
}

std::vector<Car> get_all()
{
	std::ifstream file(FILENAME, std::ios::binary | std::ios::in);
	if (!file) throw FileException("Cannot open file for reading");

	file.seekg(0, std::ios::end);
	auto file_size = (size_t)file.tellg();
	file.seekg(0, std::ios::beg);

	std::vector<Car> cars;
	if (file_size == 0)
		return cars;

	cars.resize(file_size / sizeof(Car));
	file.read(reinterpret_cast<char *>(&cars[0]), file_size);
	return cars;
}

bool seek_by_id(int id, std::fstream& file) {

	Car car;
	int car_index = 0;
	while (!file.eof()) {

		file.read(reinterpret_cast<char *>(&car), sizeof(Car));
		if (car.id == id) {
			file.seekg(-(std::streamoff)sizeof(Car), std::ios::cur);
			break;
		}
		car_index++;

	}
	return car.id == id;
}

// get_by_id
Car get_by_id(int id) {
	std::fstream file(FILENAME, std::ios::binary | std::ios::in);
	if (!file) throw FileException("Cannot open file for writing");

	Car car;
	if (seek_by_id(id, file)) {
		file.read(reinterpret_cast<char *>(&car), sizeof(Car));
	}
	return car;
}

// update_by_id
void update_by_id(Car car) {
	std::fstream file(FILENAME, std::ios::binary | std::ios::in | std::ios::out);
	if (!file) throw FileException("Cannot open file");

	if (seek_by_id(car.id, file)) {
		file.write(reinterpret_cast<const char *>(&car), sizeof(car));
	}
}

// delete 
void delete_by_id(int id) {
	std::fstream file(FILENAME, std::ios::binary | std::ios::in | std::ios::out);
	if (!file) throw FileException("Cannot open file");

	std::vector<Car> cars = get_all();
	bool needRewrite = false; // if such car not found, no need to rewrite
	for (size_t i = 0; i < cars.size(); i++) {
		if (cars[i].id == id) {
			cars.erase(cars.begin() + i);
			needRewrite = true;
			break;
		}
	}
	if (!needRewrite) return;

	// rewrite
	file.close();
	file.open(FILENAME, std::ios::binary | std::ios::out | std::ios::trunc);
	if (!file) throw FileException("Cannot open file");
	if (cars.size() == 0) return;
	file.write(reinterpret_cast<const char *>(&cars[0]), sizeof(Car)*cars.size());
}
/*
// test
int main()
{
	int ch;
	do {

		cout << "\n\nITEMS" << "\n";

		// show existing cars
		try
		{
			vector<Car> cars = get_all();
			if (cars.size() == 0) {
				cout << "No data saved for now \n";
			}
			else {
				for (size_t i = 0; i < cars.size(); i++) {
					cout << cars[i];
				}
			}
		}
		catch (const FileException& ex)
		{
			cout << "No data saved for now \n";
		}

		// show menu
		cout << "\n\n"
			<< "Select operation:\n"
			<< "1. Add new                         2. Delete by id\n"
			<< "3. Update existing car by id      0. Exit\n";
		cin >> ch;
		switch (ch) {
		case 1:
		{
			int id, count;
			string name;
			float price;
			cout << "Id: ";
			cin >> id;
			cout << "Name: ";
			cin >> name;
			cout << "Count: ";
			cin >> count;
			cout << "Price: ";
			cin >> price;
			Car car(id, name.c_str(), count, price);
			add(car);
		}
		break;
		case 2:
		{
			cout << "Enter id for delete: ";
			int id;
			cin >> id;
			delete_by_id(id);
			cout << "Deleted\n";
		}
		break;
		case 3:
		{
			int id, count;
			string name;
			float price;
			cout << "Enter id for update: ";
			cin >> id;
			cout << "New name: ";
			cin >> name;
			cout << "Count: ";
			cin >> count;
			cout << "Price: ";
			cin >> price;
			Car car(id, name.c_str(), count, price);

			update_by_id(car);
			cout << "Updated\n";
		}
		break;
		default:
			break;
		}

	} while (ch != 0);
}
*/