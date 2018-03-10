#pragma once

#include <QWidget>
#include "ui_AddNewService.h"

class AddNewService : public QWidget
{
	Q_OBJECT

public:
	AddNewService(QWidget *parent = Q_NULLPTR);
	~AddNewService();

private slots:
	void on_addBtn_clicked();

private:
	Ui::AddNewService ui;
};
