#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_CourseProject.h"

class QListWidgetItem;

class CourseProject : public QMainWindow
{
	Q_OBJECT

public:
	CourseProject(QWidget *parent = Q_NULLPTR);

private slots:
    void on_addBtn_clicked(); 
	void on_deleteBtn_clicked();
	void on_editBtn_clicked();
	void on_viewServicesBtn_clicked();
	void on_carsLW_itemClicked(QListWidgetItem *item);

private:
	Ui::CourseProjectClass ui;
	bool isEditing;

	void refresh();
};
