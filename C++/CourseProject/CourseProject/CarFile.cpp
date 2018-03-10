#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <ios>
#include "CarFile.h"

using namespace std;

static const char* FILENAME = "Cars";

Car::Car() :
	transmissionType(0),
	driveType(0),
	fuelType(0),
	price(0)
{
	firm[0] = '\0';
	model[0] = '\0';
	bodyType[0] = '\0';
}

Car::Car(const Car & car) :
	transmissionType(car.transmissionType),
	driveType(car.driveType),
	fuelType(car.fuelType),
	price(car.price)
{
	strncpy_s(firm, sizeof(firm) - 1, car.firm, sizeof(firm) - 1);
	strncpy_s(model, sizeof(model) - 1, car.model, sizeof(model) - 1);
	strncpy_s(bodyType, sizeof(bodyType) - 1, car.bodyType, sizeof(bodyType) - 1);
}

CarFile::CarFile(void)
{
	try {
		carList = get_all(FILENAME);
	}
	catch (const FileException& e) {}
}

CarFile::~CarFile()
{
	save_changes(FILENAME, carList);
}

CarFile & CarFile::getInstance()
{
	static CarFile instance;
	return instance;
}

const vector<Car>& CarFile::get_car_list()
{
	return carList;
}

const Car& CarFile::get_car(int index)
{
	if (index < 0 || index > carList.size()) {
		throw out_of_range("Incorrect index");
	} else {
		return carList[index];
	}
}

void CarFile::add(Car car)
{
	carList.push_back(car);
}

void CarFile::remove(const int index)
{
	if (index < 0 || index >= carList.size())
		throw out_of_range("Incorrect index");
	carList.erase(carList.begin() + index);
}

void CarFile::update(const int index, Car car)
{
	if (index < 0 || index >= carList.size())
		throw out_of_range("Incorrect index");
	carList[index] = car;
}