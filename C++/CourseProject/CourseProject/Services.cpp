#include "Services.h"
#include "AddNewService.h"
#include "ServiceFile.h"


Services::Services(short int fuelType) {
	fuel = fuelType;
	ui.setupUi(this);

	refresh();
}

Services::Services(QWidget *parent)
	: QWidget(parent)
{
	ui.setupUi(this);

	refresh();
}

void Services::refresh()
{
	ui.servicesLW->clear();
	ServiceFile& f = ServiceFile::getInstance();
	services = f.get_services(fuel);
	for (Service& service : services)
		ui.servicesLW->addItem(QString("") + service.name + " " + service.address);
}

void Services::on_addServiceBtn_clicked() {
	AddNewService* addDialog = new AddNewService();
	addDialog->show();
}

void Services::on_deleteBtn_clicked() {

	int index = ui.servicesLW->currentIndex().row();
	if (index < 0 || index >= services.size()) return;
	ServiceFile& f = ServiceFile::getInstance();
	f.remove(services[index]);
	refresh();
}

Services::~Services()
{
}

bool Services::event(QEvent *e)
{
	if (e->type() == QEvent::WindowActivate) {
		// window was activated
		refresh();
	}
	return QWidget::event(e);
}
