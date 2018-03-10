#pragma once

#include "File.h"

const int AUTOMATIC_TT = 0;
const int  MECHANIC_TT = 1;

const int  RWD = 0;
const int  FWD = 1;
const int  AWD = 2;

const int  PETROL = 0;
const int  GAS_PETROL = 1;
const int  HYBRID = 2;
const int  ELECTRICITY = 3;



// class with info about car
struct Car
{
	char firm[20];
	char model[10];
	char bodyType[20];
	short int transmissionType;
	short int driveType;
	short int fuelType;
	int price;

	Car();
	Car(const Car& car);
};

class CarFile : File<Car>
{
private:


	std::vector<Car> carList;

	CarFile();
	CarFile(const CarFile& f) = delete;
public:
	static CarFile& getInstance();

	const std::vector<Car>& get_car_list();
	const Car& get_car(int index);
	void add(const Car car);
	void remove(const int index);
	void update(const int index, const Car car);

	~CarFile();
};