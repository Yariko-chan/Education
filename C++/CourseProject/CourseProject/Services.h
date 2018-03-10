#pragma once

#include <QWidget>
#include <vector>
#include "ui_Services.h"
#include "ServiceFile.h"

class Services : public QWidget
{
	Q_OBJECT

public:
	Services(short int fuel);
	Services(QWidget *parent = Q_NULLPTR);
	~Services();

private slots:
	void on_addServiceBtn_clicked();
	void on_deleteBtn_clicked();

private:
	std::vector<Service> services;
	short int fuel;

	Ui::Services ui;
	void Services::refresh();
	bool event(QEvent * e) override;
};
