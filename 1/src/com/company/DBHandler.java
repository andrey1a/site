package com.company;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import static com.company.Const.*;

public class DBHandler {
    Scanner in = new Scanner(System.in);
    ScanReader sc = new ScanReader(System.in);
    Connection dbConection = null;
    Statement stmt = null;

//Подключение к БД
    public boolean open() {
        try {
            Class.forName("org.sqlite.JDBC");
            dbConection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\1\\Desktop\\Java\\TEST\\Pasha\\1\\kurs.db");
            System.out.println("База данных подключена");
            dbConection.setAutoCommit(false);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

// Метод добавления новой страны
    public void insertCountry() throws SQLException, NullPointerException, IOException {
        System.out.println("Введите название страны: ");
        String name = sc.scanString();
        System.out.println("Введите площадь страны: ");
        Float area = sc.scanFloat();
        String query = "INSERT INTO " + COUNTRY_TABLE + " ("+ NAME_COUNTRY +", "+ AREA +") " +
                "VALUES ('" + name + "', '" + area + "');";
        stmt = dbConection.createStatement();
        stmt.executeUpdate(query);
        System.out.println("Запись добавлена");
    }

    public void editCountry() throws SQLException, NullPointerException, IOException {
        System.out.println("Введите название страны дял исправления: ");
        String nameEdit = sc.scanString();
        System.out.println("Введите новое название страны:  ");
        String name = sc.scanString();
        System.out.println("Введите новую площадь страны: ");
        Float areaEdit = in.nextFloat();
        stmt = dbConection.createStatement();
        stmt.executeUpdate("UPDATE "+ COUNTRY_TABLE +" SET "+ AREA +" = '"+ areaEdit +"', "+ NAME_COUNTRY +" = '"+ name +"' WHERE "+ NAME_COUNTRY +" = '"+ nameEdit +"';");
        System.out.println("Запись добавлена");
    }

    public void editCity() throws SQLException, NullPointerException, IOException {
        System.out.println("Введите название города для исправления: ");
        String nameEdit = sc.scanString();
        System.out.println("Введите новое название города: ");
        String cityNameEdit = sc.scanString();
        System.out.println("Введите новое население города: ");
        int populationEdit = sc.scanInt();
        System.out.println("Введите новую среднюю зарплату в городе: ");
        int salaryEdit = sc.scanInt();
        System.out.println("Введите новую страну, в которой находится город");
        String idCountryEdit = sc.scanString();
        stmt = dbConection.createStatement();
        stmt.executeUpdate("UPDATE "+ CITY_TABLE+" SET "+ NAME_CITY +" = '"+ cityNameEdit +"', "+ POPULATION +" = '"+ populationEdit +"', "+ SALARY +" = '"+ salaryEdit +"', "+ FOREIGN_KEY +" = '"+ idCountryEdit +"' WHERE "+ NAME_CITY +" = '"+ nameEdit +"';");
        System.out.println("Запись добавлена");
    }



// Метод добавления нового города
    public void insertCity() throws SQLException, NullPointerException, IOException{
        System.out.println("Введите название города: ");
        String cityName = sc.scanString();
        System.out.println("Введите население города: ");
        int population = sc.scanInt();
        System.out.println("Введите среднюю зарплату в городе: ");
        int salary = sc.scanInt();
        System.out.println("Введите страну, в которой находится город");
        String idCountryString = sc.scanString();
        int idCountryKey = getIdCountry(idCountryString);
        String query = "INSERT INTO "+ CITY_TABLE +" ("+ NAME_CITY +", "+ POPULATION +", "+ SALARY +", "+ FOREIGN_KEY +") " +
                "VALUES ('"+ cityName +"', '"+ population +"', '"+ salary +"', '"+ idCountryKey +"');";
        stmt = dbConection.createStatement();
        stmt.executeUpdate(query);
        System.out.println("Запись добавлена");
    }

//Метод вывода полной таблицы (тревиальный способ)
    public void showAll() throws SQLException {
        String query = "SELECT "+ CITY_TABLE +"."+ NAME_CITY +", "+ CITY_TABLE +"."+ POPULATION +", "+ CITY_TABLE +"."+ SALARY +", "+ COUNTRY_TABLE +"."+ NAME_COUNTRY +", "+ COUNTRY_TABLE +"."+ AREA +
                " FROM "+ CITY_TABLE +" LEFT JOIN "+ COUNTRY_TABLE +
                " ON ("+ CITY_TABLE +"."+ FOREIGN_KEY +" = "+ COUNTRY_TABLE +"."+ ID_COUNTRY +");";
        stmt = dbConection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = stmt.executeQuery(query);
        System.out.printf("%-20s%-15s%-20s%-14s%-20s%n","Город", "Население", "Средняя зарплата", "Страна", "Площадь страны");
        System.out.println("---------------------------------------------------------------------------------------");
        while (rs.next()) {
            System.out.printf("%-20s%-15s%-20s%-14s%-20s%n", rs.getString(NAME_CITY), rs.getString(POPULATION), rs.getString(SALARY), rs.getString(NAME_COUNTRY), rs.getString(AREA));
        }

    }



//Метод получения значения id страны
    public int getIdCountry(String idCountryString) throws SQLException, NullPointerException {
        String query = "SELECT "+ ID_COUNTRY +" FROM "+ COUNTRY_TABLE +" WHERE "+ COUNTRY_TABLE +"."+ NAME_COUNTRY +" = '"+ idCountryString +"';";
        stmt = dbConection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        int idCountryKey = rs.getInt("id");
        return idCountryKey;
    }

//Метод получения значения id города
    public int getIdCity(String name) throws SQLException, NullPointerException {
        String query = "SELECT " + ID_CITY + " FROM " + CITY_TABLE + " WHERE " + CITY_TABLE + "." + NAME_CITY + " = '" + name + "';";
        stmt = dbConection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        int id = rs.getInt("id");
        return id;
    }

    public void deleteCity() throws IOException, SQLException {
        System.out.println("Введите город, который требуется удалить: ");
        String cityName= sc.scanString();
        stmt = dbConection.createStatement();
        stmt.executeUpdate("DELETE FROM "+ CITY_TABLE +" WHERE "+ NAME_CITY +" = '"+ cityName +"';");
        System.out.println("Запись удалена");
    }
    public void deleteCountry() throws IOException, SQLException {
        System.out.println("Введите страну, которую требуется удалить: ");
        String countryName= sc.scanString();
        stmt = dbConection.createStatement();
        stmt.executeUpdate("DELETE FROM "+ CITY_TABLE +" WHERE "+ ID_COUNTRY +" = (SELECT "+ ID_COUNTRY +" FROM "+ COUNTRY_TABLE +" WHERE "+ NAME_COUNTRY +" = '"+ countryName +"');");
        stmt.executeUpdate("DELETE FROM "+ COUNTRY_TABLE +" WHERE "+ NAME_COUNTRY +" = '"+ countryName +"';");
        System.out.println("Запись удалена");
    }
    public void rollback() throws IOException, SQLException {
        System.out.println("Введите страну, которую требуется удалить: ");
        String countryName= sc.scanString();
        stmt = dbConection.createStatement();
        stmt.executeUpdate("DELETE FROM "+ CITY_TABLE +" WHERE "+ ID_COUNTRY +" = (SELECT "+ ID_COUNTRY +" FROM "+ COUNTRY_TABLE +" WHERE "+ NAME_COUNTRY +" = '"+ countryName +"');");
        stmt.executeUpdate("DELETE FROM "+ COUNTRY_TABLE +" WHERE "+ NAME_COUNTRY +" = '"+ countryName +"';");
        System.out.println("Запись удалена");
    }
//Закрытие соеденения с БД
    public void close() {
        try {
            dbConection.close();
            System.out.println("База данных закрыта");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void commitBD() throws SQLException {
        dbConection.commit();
        System.out.println("База данных успешно подтверждена");
    }
    public void rollbackBD() throws SQLException {
        dbConection.rollback();
        System.out.println("База данных успешно откатилась до последнего подтверждения");
    }

    public void Serializable() throws SQLException, NullPointerException, IOException {
        ArrayList<Country> list = new ArrayList();
        String query = "SELECT * FROM "+ COUNTRY_TABLE +";";
        stmt = dbConection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while(rs.next())
        {
            Country obj = new Country(rs.getInt("id"), rs.getString("nameCountry"), rs.getFloat("area"));
            list.add(obj);
        }
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream("Demo.ser")));
            out.writeObject(list);
        } catch ( IOException ex ) {
            ex.printStackTrace();
        } finally {
            if ( out != null )
                try {
                    out.close();
                } catch ( IOException ex ) {
                    ex.printStackTrace();
                }
        }
    }
    public void Derializable() throws SQLException, NullPointerException, IOException {
        ArrayList<Country> list = new ArrayList();

        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new BufferedInputStream(
                    new FileInputStream("Demo.ser")));
            list = (ArrayList) in.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
        }
        Iterator iter = list.iterator();
        for ( int i = 0; iter.hasNext(); i++ ) {
            System.out.println("N "+i+"="+iter.next());
           // String query = "INSERT INTO " + COUNTRY_TABLE + " ("+ NAME_COUNTRY +", "+ AREA +") " +
           //         "VALUES ('" + list. + "', '" + area + "');";
            //stmt = dbConection.createStatement();
           // stmt.executeUpdate(query);
        }

    }
}


