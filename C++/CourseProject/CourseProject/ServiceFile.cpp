#include <iostream>
#include "ServiceFile.h"
#include "CarFile.h"

static const char* FILENAME = "Services";

Service::Service() :
	petrol(true),
	gasPetrol(true),
	hybrid(true),
	electricity(true)
{
	name[0] = '\0';
	address[0] = '\0';
}
/*
Service::Service(const Service & service) :
	petrol(service.petrol),
	gasPetrol(service.gasPetrol),
	hybrid(service.hybrid),
	electricity(service.electricity)
{
	strncpy_s(name, sizeof(name) - 1, service.name, sizeof(name) - 1);
	strncpy_s(address, sizeof(address) - 1, service.address, sizeof(address) - 1);
}*/


ServiceFile::ServiceFile(void)
{
	try {
		serviceList = get_all(FILENAME);
	}
	catch (const FileException& e) {}
}

ServiceFile::~ServiceFile()
{
	save_changes(FILENAME, serviceList);
}

ServiceFile & ServiceFile::getInstance()
{
	static ServiceFile instance;
	return instance;
}

const std::vector<Service>& ServiceFile::get_service_list()
{
	return serviceList;
}

std::vector<Service> ServiceFile::get_services(short int fuel)
{
	std::vector<Service>  services;
	for (Service serv : serviceList) {
			bool isAdd = true;
		switch (fuel) {
		case PETROL:
			isAdd = serv.petrol;
			break;
		case GAS_PETROL:
			isAdd = serv.gasPetrol;
			break;
		case HYBRID:
			isAdd = serv.hybrid;
			break;
		case ELECTRICITY:
			isAdd = serv.electricity;
			break;
		}
		if (isAdd) services.push_back(serv);
	}

	return services;
}

void ServiceFile::add(const Service service)
{
	serviceList.push_back(service);
}

void ServiceFile::remove(const Service service)
{
	for (int i = 0; i < serviceList.size(); i++) {
		if (strcmp(serviceList[i].name, service.name) == 0 &&
			strcmp(serviceList[i].address, service.address) == 0) {
				serviceList.erase(serviceList.begin() + i);
				break;
		}
	}
}