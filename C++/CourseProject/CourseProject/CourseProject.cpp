#include "CourseProject.h"
#include <sstream>
#include <QStringList>
#include <CarFile.h>
#include "Services.h"

#include <string>

static const std::vector<std::string> firms = { "Audi", "BMW", "Chevrolet", "Citroen",
"Ford", "Honda", "Hyundai", "KIA", "LADA", "Land Rover",
"Mazda", "Mersedes Benz", "Mitsubishi", "Nissan", "Opel",
"Peugeot", "Renault", "Skoda", "Subaru", "Suzuki", "Toyota",
"Volkswagen", "Volvo" };
static const std::vector<std::string> bodyTypes = { "Sedan", "Hatchback", "Estate", "Liftback",
"Coupe", "Convertible", "Roadster", "Stretch",
"Targa", "SUV", "Crossover", "Pickup",
"Van", "Minivan" };


CourseProject::CourseProject(QWidget *parent)
	: QMainWindow(parent)
{
	ui.setupUi(this);

	isEditing = false;

	QStringList firmList;
	for (int i = 0; i < firms.size(); i++) {
		firmList << QString::fromStdString(firms[i]);
	}
	
	ui.firmsCB->addItems(firmList);

	QStringList bodyTypesList;
	for (int i = 0; i < bodyTypes.size(); i++) {
		bodyTypesList << QString::fromStdString(bodyTypes[i]);
	}
	ui.bodyTypesCB->addItems(bodyTypesList);

	refresh();
}

void CourseProject::refresh()
{
	ui.carsLW->clear();
	CarFile& f = CarFile::getInstance();
	std::vector<Car> cars = f.get_car_list();
	for (Car& car : cars)
		ui.carsLW->addItem(QString("") + car.firm + " " + car.model);

	ui.textEdit->setText("");
	ui.firmsCB->setCurrentIndex(0);
	ui.modelLE->setText("");
	ui.bodyTypesCB->setCurrentIndex(0);
	ui.automaticRB->setChecked(true);
	ui.rwdRB->setChecked(true);
	ui.petrolRB->setChecked(true);
	ui.priceSP->setValue(20000);
}

void CourseProject::on_addBtn_clicked()
{
	Car c;
	std::string firm = ui.firmsCB->currentText().toStdString();
	std::string model = ui.modelLE->text().toStdString();
	std::string bodyType = ui.bodyTypesCB->currentText().toStdString();
	strncpy_s(c.firm, sizeof(c.firm) - 1, 
		firm.c_str(), sizeof(c.firm) - 1);
	strncpy_s(c.model, sizeof(c.model) - 1, 
		model.c_str(), sizeof(c.model) - 1);
	strncpy_s(c.bodyType, sizeof(c.bodyType) - 1, 
		bodyType.c_str(), sizeof(c.bodyType) - 1);

	c.transmissionType = (ui.automaticRB->isChecked()) ? AUTOMATIC_TT : MECHANIC_TT;

	if (ui.rwdRB->isChecked()) c.driveType = RWD;
	else if (ui.fwdRB->isChecked()) c.driveType = FWD;
	else c.driveType = AWD;

	if (ui.petrolRB->isChecked()) c.fuelType = PETROL;
	else if (ui.gasPetrolRB->isChecked()) c.fuelType = GAS_PETROL;
	else if (ui.hybridRB->isChecked()) c.fuelType = HYBRID;
	else c.fuelType = ELECTRICITY;

	c.price = ui.priceSP->value();
	CarFile& f = CarFile::getInstance();
	if (isEditing) {
		int index = ui.carsLW->currentIndex().row();
		f.update(index, c);
		isEditing = false;
		ui.groupBox_4->setTitle("Add new car");
		ui.addBtn->setText("Add");
	}
	else {
		f.add(c);
	}
	refresh();
}

void CourseProject::on_carsLW_itemClicked(QListWidgetItem *item)
{
	int index = item->listWidget()->row(item);
	Car car = CarFile::getInstance().get_car(index);
	std::stringstream s;
	s << "<p><b><h1\">" 
		<< car.firm << " " << car.model 
		<< "</h1></b></p>";
	s << "Body type: " << car.bodyType << "<br/>";
	s << "Transmission type: ";
	switch (car.transmissionType) {
	case AUTOMATIC_TT:
		s << "automatic";
		break;
	case MECHANIC_TT:
		s << "mechanic";
		break;
	}
	s << "<br/>";
	s << "Drivetrain setup: ";
	switch (car.driveType) {
	case RWD:
		s << "rear-wheel drive";
		break;
	case FWD:
		s << "front-wheel drive";
		break;
	case AWD:
		s << "all-wheel drive";
		break;
	}
	s << "<br/>";
	s << "Fuel: ";
	switch (car.fuelType) {
	case PETROL:
		s << "petrol";
		break;
	case GAS_PETROL:
		s << "gas&petrol";
		break;
	case HYBRID:
		s << "hybrid";
		break;
	case ELECTRICITY:
		s << "electricity";
		break;
	}
	s << "<br/>";
	s << "Price: " << "<u><b>" << car.price << "</u></b>" << '$';
	ui.textEdit->setText(QString::fromStdString(s.str()));

}

void CourseProject::on_deleteBtn_clicked()
{
	int index = ui.carsLW->currentIndex().row();
	CarFile& f = CarFile::getInstance();
	f.remove(index);
	refresh();
}

void CourseProject::on_editBtn_clicked()
{
	int index = ui.carsLW->currentIndex().row();
	CarFile& f = CarFile::getInstance();
	Car car = f.get_car(index);

	isEditing = true;
	ui.groupBox_4->setTitle("Edit car");
	ui.addBtn->setText("Save changes");

	int firmIndex = 0;
	for (int i = 0; i < firms.size(); i++) {
		if (firms[i] == car.firm) {
			firmIndex = i;
			break;
		}
	}
	ui.firmsCB->setCurrentIndex(firmIndex);

	ui.modelLE->setText(car.model);

	int bodyTypeIndex = 0;
	for (int i = 0; i < bodyTypes.size(); i++) {
		if (bodyTypes[i] == car.firm) {
			bodyTypeIndex = i;
			break;
		}
	}
	ui.bodyTypesCB->setCurrentIndex(bodyTypeIndex);

	switch (car.transmissionType) {
	case AUTOMATIC_TT:
		ui.automaticRB->setChecked(true);
		break;
	case MECHANIC_TT:
		ui.mechanicRB->setChecked(true);
		break;
	}

	switch (car.driveType) {
	case RWD:
		ui.rwdRB->setChecked(true);
		break;
	case FWD:
		ui.fwdRB->setChecked(true);
		break;
	case AWD:
		ui.awdRB->setChecked(true);
		break;
	}

	switch (car.fuelType) {
	case PETROL:
		ui.petrolRB->setChecked(true);
		break;
	case GAS_PETROL:
		ui.gasPetrolRB->setChecked(true);
		break;
	case HYBRID:
		ui.hybridRB->setChecked(true);
		break;
	case ELECTRICITY:
		ui.electricityRB->setChecked(true);
		break;
	}

	ui.priceSP->setValue(car.price);
}

void CourseProject::on_viewServicesBtn_clicked()
{
	int index = ui.carsLW->currentIndex().row();
	CarFile& f = CarFile::getInstance();
	short int fuelType = f.get_car(index).fuelType;
	Services* viewServices = new Services(fuelType);
	viewServices->setAttribute(Qt::WA_DeleteOnClose);
	viewServices->show();
}