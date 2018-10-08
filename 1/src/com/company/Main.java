package com.company;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        Scanner in = new Scanner(System.in);
        printHelp();
        String x = " ";
        DBHandler db = new DBHandler();
        while (x != "exit") {
            //in.nextLine();
            x = in.nextLine();
            switch (x) {
                case "add country":
                    db.insertCountry();
                    break;
                case "edit country":
                    db.editCountry();
                    break;
                case "delete country":
                    db.deleteCountry();
                    break;
                case "add city":
                    db.insertCity();
                    break;
                case "edit city":
                    db.editCity();
                    break;
                case "delete city":
                    db.deleteCity();
                    break;
                case "show":
                    db.showAll();
                    break;
                case "open":
                    db.open();
                    break;
                case "close":
                    db.close();
                    break;
                case "rollback":
                    db.rollbackBD();
                    break;
                case "commit":
                    db.commitBD();
                    break;
                case "seriz":
                    db.Serializable();
                    break;
                case "deriz":
                    db.Derializable();
                    break;
                case "help":
                    printHelp();
                    break;
                case "exit":
                    x = "exit";
                    break;
                default:
                        System.out.println("Неизвестная команда");
                        break;
            }
        }
    }

    public static void printHelp() {
        System.out.println("open - открыть базу данных \n" +
                "close - закрыть базу данных \n" +
                "add country - добавить новую страну \n" +
                "edit country - редактировать страну \n" +
                "delete country - удалить страну \n" +
                "add city - добавить новый город \n" +
                "edit city - редактировать город \n" +
                "delete city - удалить город \n" +
                "show - вывод всей таблицы \n" +
                "commit- выполнить подтверждение БД \n" +
                "rollback- откат до последнего подтверждения \n" +
                "help - вывод всех команд \n" +
                "exit - выход из программы \n");
    }
}
