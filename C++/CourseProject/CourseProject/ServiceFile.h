#pragma once

#include <vector>
#include "File.h"

// class with info about car
struct Service
{
	char name[20];
	char address[40];
	bool petrol;
	bool gasPetrol;
	bool hybrid;
	bool electricity;

	Service();
};

class ServiceFile : File<Service>
{
private:
	std::vector<Service> serviceList;

	ServiceFile();
	ServiceFile(const ServiceFile& f) = delete;
public:
	static ServiceFile& getInstance();

	const std::vector<Service>& get_service_list();
	std::vector<Service> ServiceFile::get_services(short int fuel);
	void add(const Service service);
	void remove(const Service service);

	~ServiceFile();
};