package main;

import main.ui.Ui;

/**
 * С  использованием  графического  интерфейса  пользователя  требуется
 * разработать  приложение,  взаимодействующее    с    БД.    Приложение должно
 * позволять:
 * - добавлять, удалять, редактировать записи;
 * - осуществлять поиск информации;
 * - осуществлять сортировку информации;
 * - сохранять результаты в файл.
 * В качестве СУБД использовать MicrosoftAccess.
 * В БД должны присутствовать поля разных типов
 * (минимальные требования: числа, текст и дата).
 *
 * 6.   Разработать подсистему учета и регистрации поступлений цветов в цветочный магазин.
 * Обязательно: использовать классы List, Choice, диалог подтверждения обновления.
 *
 * Created by Ganeeva Diana in Май, 2018
 * for DB
 */
public class FlowersDB {
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.start();
    }
}
