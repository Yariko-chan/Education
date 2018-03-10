#include "AddNewService.h"
#include "ServiceFile.h"

AddNewService::AddNewService(QWidget *parent)
	: QWidget(parent)
{
	ui.setupUi(this);
}


void AddNewService::on_addBtn_clicked() 
{
	Service s;
	std::string name = ui.nameLE->text().toStdString();
	std::string address = ui.addressLE->text().toStdString();
	strncpy_s(s.name, sizeof(s.name) - 1,
		name.c_str(), sizeof(s.name) - 1);
	strncpy_s(s.address, sizeof(s.address) - 1,
		address.c_str(), sizeof(s.address) - 1);
	s.petrol = ui.petrolCB->isChecked();
	s.gasPetrol = ui.gasPetrolCB->isChecked();
	s.hybrid = ui.hybridCB->isChecked();
	s.electricity = ui.electricityCB->isChecked();
	ServiceFile& f = ServiceFile::getInstance();
	f.add(s);
	close();
}

AddNewService::~AddNewService()
{
}
