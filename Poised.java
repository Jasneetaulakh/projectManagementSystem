import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class Poised {
    private Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) throws ParseException, IOException {

        Scanner keyboard = new Scanner(System.in);
        Date date = new Date();
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        String todaysDate = sdFormat.format(date);
        Poised poised = new Poised();

        //variables
        String projNum = "";
        String projName = "";
        String buildingType = "";
        String projAddress = "";
        int erfNum = 0;
        double totalFee = 0;
        double totalPaid = 0;
        String deadline = "";
        String name = "";
        String phoneNum = "";
        String emailAdd = "";
        String address = "";
        String name2 = "";
        String phoneNum2 = "";
        String emailAdd2 = "";
        String address2 = "";
        String name3 = "";
        String phoneNum3 = "";
        String emailAdd3 = "";
        String address3 = "";
        String completed = "";

        //project menu
        System.out.println("Main menu");
        System.out.println("Select from the following options: \n1: Enter a new project" +
                "\n2: Make a change to your project \n3: View overdue projects" +
                "\n4: View incomplete projects \n5: View project by entering project name" +
                " \n6: View project by entering project number ");
        System.out.println("Enter 1 / 2 / 3 / 4 / 5 / 6 ");
        String userMenuInput = keyboard.nextLine();

        //project input, user enters details and it is added to the database

        if (userMenuInput.equals("1")) {
            // don't need to ask user for proj num as it is auto-incremented in MySQL table

            projName = poised.getStringDate("Enter project name: ");

            buildingType = poised.getStringDate("Enter building type: ");

            projAddress = poised.getStringDate("Enter project address: ");

            erfNum = poised.getIntData("Enter ERF number: ");

            totalFee = poised.getDoubleDate("Enter total fee: ");

            //if total paid > total fee assume incorrect and ask again
            totalPaid = poised.getDoubleDate("Enter amount paid to date: ");
            if (totalPaid > totalFee) {
                System.out.println("You have entered an incorrect amount. ");
                totalPaid = poised.getDoubleDate("Enter amount paid to date: ");
            }
            //while deadline is not 8 characters, ask user for deadline again
            deadline = poised.getStringDate("Enter project deadline YYYY-MM-DD: ");

            //architect input
            name = poised.getStringDate("Enter architect name: ");

            //phone num in UK is 11 digits, enter 11 characters to continue
            phoneNum = poised.getStringDate("Enter architect phone number: ");
            while (phoneNum.length() != 11) {
                System.out.println("You have not entered a correct phone number.");
                phoneNum = poised.getStringDate("Enter architect phone number: ");
            }

            emailAdd = poised.getStringDate("Enter architect email address: ");

            address = poised.getStringDate("Enter architect address: ");

            //contractor input
            name2 = poised.getStringDate("Enter contractor name: ");

            phoneNum2 = poised.getStringDate("Enter contractor phone number: ");
            while (phoneNum2.length() != 11) {
                System.out.println("You have not entered a correct phone number.");
                phoneNum2 = poised.getStringDate("Enter contractor phone number: ");
            }

            emailAdd2 = poised.getStringDate("Enter contractor email address: ");

            address2 = poised.getStringDate("Enter contractor address: ");

            //customer input
            name3 = poised.getStringDate("Enter customer name: ");

            phoneNum3 = poised.getStringDate("Enter customer phone number: ");
            while (phoneNum3.length() != 11) {
                System.out.println("You have not entered a correct phone number.");
                phoneNum3 = poised.getStringDate("Enter customer phone number: ");
            }

            emailAdd3 = poised.getStringDate("Enter customer email address: ");

            address3 = poised.getStringDate("Enter customer address: ");

            //architect object
            Person architect = new Person(name, phoneNum, emailAdd, address);
            //contractor object
            Person contractor = new Person(name2, phoneNum2, emailAdd2, address2);
            //customer object
            Person customer = new Person(name3, phoneNum3, emailAdd3, address3);

            Project project1 = new Project(projNum, projName, buildingType, projAddress,
                    erfNum, totalFee, totalPaid, deadline, architect,
                    contractor, customer);

//if project is complete, print invoice and add to database with "complete"
            double amountToPay = totalFee - totalPaid;
            System.out.println("Is the project completed? Enter " +
                    "'yes' or 'no': ");
            String isProjComp = keyboard.nextLine();
            if (isProjComp.equalsIgnoreCase("yes") && amountToPay > 0) {
                String invoice = "Invoice \nCustomers contact details: " +
                        project1.getCustomer() + "\n" +
                        "Amount to pay: £" + amountToPay;
                System.out.println(invoice);
            }

            if (isProjComp.equalsIgnoreCase("yes")) {
                completed = "complete";
                try {
                    Connection connection = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/poised_db?allowPublicKeyRetrieval=true&useSSL=false",
                            "userone",
                            "fishy"
                    );
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO poised_projects " +
                            "(ProjName, BuildingType, ProjAddress, " +
                            "ERFnum, TotalFee, TotalPaid, Deadline, ArchitectName, ArchitectPhone, " +
                            "ArchitectEmail, ArchitectAddress, ContractorName, ContractorPhone, ContractorEmail, " +
                            "ContractorAddress, CustomerName, CustomerPhone, CustomerEmail, " +
                            "CustomerAddress, Completed) VALUES (?, ?, ?, ?," +
                            " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    ps.setString(1, projName);
                    ps.setString(2, buildingType);
                    ps.setString(3, projAddress);
                    ps.setInt(4, erfNum);
                    ps.setDouble(5, totalFee);
                    ps.setDouble(6, totalPaid);
                    ps.setString(7, deadline);
                    ps.setString(8, name);
                    ps.setString(9, phoneNum);
                    ps.setString(10, emailAdd);
                    ps.setString(11, address);
                    ps.setString(12, name2);
                    ps.setString(13, phoneNum2);
                    ps.setString(14, emailAdd2);
                    ps.setString(15, address2);
                    ps.setString(16, name3);
                    ps.setString(17, phoneNum3);
                    ps.setString(18, emailAdd3);
                    ps.setString(19, address3);
                    ps.setString(20, completed);

                    ps.executeUpdate();

                    ps.close();
                    connection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            //if incomplete, don't print invoice and add to database with "incomplete"
            if (isProjComp.equalsIgnoreCase("no")) {
                completed = "incomplete";
                try {
                    Connection connection = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/poised_db?allowPublicKeyRetrieval=true&useSSL=false",
                            "userone",
                            "fishy"
                    );
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO poised_projects " +
                            "(ProjName, BuildingType, ProjAddress, " +
                            "ERFnum, TotalFee, TotalPaid, Deadline, ArchitectName, ArchitectPhone, " +
                            "ArchitectEmail, ArchitectAddress, ContractorName, ContractorPhone, ContractorEmail, " +
                            "ContractorAddress, CustomerName, CustomerPhone, CustomerEmail, " +
                            "CustomerAddress, Completed) VALUES (?, ?, ?, ?," +
                            " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                    ps.setString(1, projName);
                    ps.setString(2, buildingType);
                    ps.setString(3, projAddress);
                    ps.setInt(4, erfNum);
                    ps.setDouble(5, totalFee);
                    ps.setDouble(6, totalPaid);
                    ps.setString(7, deadline);
                    ps.setString(8, name);
                    ps.setString(9, phoneNum);
                    ps.setString(10, emailAdd);
                    ps.setString(11, address);
                    ps.setString(12, name2);
                    ps.setString(13, phoneNum2);
                    ps.setString(14, emailAdd2);
                    ps.setString(15, address2);
                    ps.setString(16, name3);
                    ps.setString(17, phoneNum3);
                    ps.setString(18, emailAdd3);
                    ps.setString(19, address3);
                    ps.setString(20, completed);

                    ps.executeUpdate();

                    ps.close();
                    connection.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }//incomplete if-statement
        }// 1 if-statement

        /*
        make changes to projects, user is showed another menu with options to change
        parts of the projects such as deadline or contractors details
         */
        else if(userMenuInput.equalsIgnoreCase("2")) {
            System.out.println("Enter project num you would like to make a change to: ");
            String changeProjNum = keyboard.nextLine();
            System.out.println("Enter a number to make a change: " +
                    "\n1 = deadline \n2 = amount paid \n3 = architects phone" +
                    "\n4 = architects email \n5 = architects address \n6 = contractors phone" +
                    "\n7 = contratcors email \n8 = contractors address \n9 = complete a project");
            String changeThis = keyboard.nextLine();

                if(changeThis.equals("1")) {
                    System.out.println("Enter new deadline: ");
                    deadline = keyboard.nextLine();

                    try {
                        Connection connection = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/poised_db?allowPublicKeyRetrieval=true&useSSL=false",
                                "userone",
                                "fishy"
                        );
                        Statement statement = connection.createStatement();
                        int rowsAffected;

                        rowsAffected = statement.executeUpdate(
                                "UPDATE poised_projects SET Deadline = " + deadline + " WHERE ProjNum = " +
                                        changeProjNum);

                        statement.close();
                        connection.close();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }// 1 if-statement

                else if (changeThis.equals("2")) {
                    System.out.println("Enter amount paid: ");
                    totalPaid = Double.parseDouble(keyboard.nextLine());

                    try {
                        Connection connection = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/poised_db?allowPublicKeyRetrieval=true&useSSL=false",
                                "userone",
                                "fishy"
                        );
                        Statement statement = connection.createStatement();
                        int rowsAffected;

                        rowsAffected = statement.executeUpdate(
                                "UPDATE poised_projects SET TotalPaid = " + totalPaid + " WHERE ProjNum = " +
                                        changeProjNum);

                        statement.close();
                        connection.close();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }// 2 if-statement

                else if (changeThis.equals("3")) {
                    System.out.println("Enter architects phone: ");
                    phoneNum = keyboard.nextLine();

                    try {
                        Connection connection = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/poised_db?allowPublicKeyRetrieval=true&useSSL=false",
                                "userone",
                                "fishy"
                        );
                        Statement statement = connection.createStatement();
                        int rowsAffected;

                        rowsAffected = statement.executeUpdate(
                                "UPDATE poised_projects SET ArchitectPhone = " + phoneNum + " WHERE ProjNum = " +
                                        changeProjNum);

                        statement.close();
                        connection.close();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }// 3 if-statement

                else if (changeThis.equals("4")) {
                    System.out.println("Enter architects email: ");
                    emailAdd = keyboard.nextLine();

                    try {
                        Connection connection = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/poised_db?allowPublicKeyRetrieval=true&useSSL=false",
                                "userone",
                                "fishy"
                        );
                        Statement statement = connection.createStatement();
                        int rowsAffected;

                        rowsAffected = statement.executeUpdate(
                                "UPDATE poised_projects SET ArchitectEmail = " + emailAdd + " WHERE ProjNum = " +
                                        changeProjNum);

                        statement.close();
                        connection.close();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }// 4 if-statement

                else if (changeThis.equals("5")) {
                    System.out.println("Enter architects address: ");
                    address = keyboard.nextLine();

                    try {
                        Connection connection = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/poised_db?allowPublicKeyRetrieval=true&useSSL=false",
                                "userone",
                                "fishy"
                        );
                        Statement statement = connection.createStatement();
                        int rowsAffected;

                        rowsAffected = statement.executeUpdate(
                                "UPDATE poised_projects SET ArchitectAddress = " + address + " WHERE ProjNum = " +
                                        changeProjNum);

                        statement.close();
                        connection.close();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }// 5 if-statement

                else if (changeThis.equals("6")) {
                    System.out.println("Enter contractors phone: ");
                    phoneNum2 = keyboard.nextLine();

                    try {
                        Connection connection = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/poised_db?allowPublicKeyRetrieval=true&useSSL=false",
                                "userone",
                                "fishy"
                        );
                        Statement statement = connection.createStatement();
                        int rowsAffected;

                        rowsAffected = statement.executeUpdate(
                                "UPDATE poised_projects SET ContractorPhone = " + phoneNum2 + " WHERE ProjNum = " +
                                        changeProjNum);

                        statement.close();
                        connection.close();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }// 6 if-statement

                else if (changeThis.equals("7")) {
                    System.out.println("Enter contractors email: ");
                    emailAdd2 = keyboard.nextLine();

                    try {
                        Connection connection = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/poised_db?allowPublicKeyRetrieval=true&useSSL=false",
                                "userone",
                                "fishy"
                        );
                        Statement statement = connection.createStatement();
                        int rowsAffected;

                        rowsAffected = statement.executeUpdate(
                                "UPDATE poised_projects SET ContractorEmail = " + emailAdd2 + " WHERE ProjNum = " +
                                        changeProjNum);

                        statement.close();
                        connection.close();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }// 7 if-statement

                else if (changeThis.equals("8")) {
                    System.out.println("Enter contractors address: ");
                    address2 = keyboard.nextLine();

                    try {
                        Connection connection = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/poised_db?allowPublicKeyRetrieval=true&useSSL=false",
                                "userone",
                                "fishy"
                        );
                        Statement statement = connection.createStatement();
                        int rowsAffected;

                        rowsAffected = statement.executeUpdate(
                                "UPDATE poised_projects SET ContractorAddress = " + address2 + " WHERE ProjNum = " +
                                        changeProjNum);

                        statement.close();
                        connection.close();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }// 8 if-statement

                else if (changeThis.equals("9")) {
                    completed = "complete";

                    try {
                        Connection connection = DriverManager.getConnection(
                                "jdbc:mysql://localhost:3306/poised_db?allowPublicKeyRetrieval=true&useSSL=false",
                                "userone",
                                "fishy"
                        );
                        Statement statement = connection.createStatement();
                        int rowsAffected;

                        rowsAffected = statement.executeUpdate(
                                "UPDATE poised_projects SET Completed = " + completed + " WHERE ProjNum = " +
                                        changeProjNum);

                        statement.close();
                        connection.close();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }// 9 if-statement
            System.out.println("Project updated");
        }// 2 if-statement

        //view out of date projects
        else if(userMenuInput.equals("3")) {

            try {
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/poised_db?allowPublicKeyRetrieval=true&useSSL=false",
                        "userone",
                        "fishy"
                );

                Statement statement = connection.createStatement();

                String SQL = "SELECT * FROM poised_projects WHERE Deadline < '" + todaysDate + "' AND " +
                        "Completed = 'incomplete'";

                ResultSet resultSearch = statement.executeQuery(SQL);

                while(resultSearch.next()) {
                    System.out.println("Proj Num: " + resultSearch.getInt("ProjNum") + ", Proj Name: " +
                            resultSearch.getString("ProjName") + ", Building Type: " +
                            resultSearch.getString("BuildingType") + ", Proj Address: " +
                            resultSearch.getString("ProjAddress") + ", ERF Num: " +
                            resultSearch.getInt("ERFnum") + ", Total Fee: " +
                            resultSearch.getDouble("TotalFee") + ", Total Paid: " +
                            resultSearch.getDouble("TotalPaid") + ", Deadline: " +
                            resultSearch.getDate("Deadline"));
                }
                resultSearch.close();
                statement.close();
                connection.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }// 3 if-statement

        //view incomplete projects
        else if(userMenuInput.equals("4")) {
            try {
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/poised_db?allowPublicKeyRetrieval=true&useSSL=false",
                        "userone",
                        "fishy"
                );
                Statement statement = connection.createStatement();

                String SQL = "SELECT * FROM poised_projects WHERE Completed = 'incomplete'";

                ResultSet resultSearch = statement.executeQuery(SQL);

                while(resultSearch.next()) {
                    System.out.println("Proj Num: " + resultSearch.getInt("ProjNum") + ", Proj Name: " +
                            resultSearch.getString("ProjName") + ", Building Type: " +
                            resultSearch.getString("BuildingType") + ", Proj Address: " +
                            resultSearch.getString("ProjAddress") + ", ERF Num: " +
                            resultSearch.getInt("ERFnum") + ", Total Fee: " +
                            resultSearch.getDouble("TotalFee") + ", Total Paid: " +
                            resultSearch.getDouble("TotalPaid") + ", Deadline: " +
                            resultSearch.getDate("Deadline"));
                }
                resultSearch.close();
                statement.close();
                connection.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }// 4 if-statement

        //search project by proj name
        else if(userMenuInput.equals("5")) {
            System.out.println("Enter project name: ");
            String findByName = keyboard.nextLine();
            try {
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/poised_db?allowPublicKeyRetrieval=true&useSSL=false",
                        "userone",
                        "fishy"
                );
                Statement statement = connection.createStatement();

                String SQL = "SELECT * FROM poised_projects WHERE ProjName LIKE '" + findByName+"'";

                ResultSet resultSearch = statement.executeQuery(SQL);

                while(resultSearch.next()) {
                    System.out.println("Proj Num: " + resultSearch.getInt("ProjNum") + ", Proj Name: " +
                            resultSearch.getString("ProjName") + ", Building Type: " +
                            resultSearch.getString("BuildingType") + ", Proj Address: " +
                            resultSearch.getString("ProjAddress") + ", ERF Num: " +
                            resultSearch.getInt("ERFnum") + ", Total Fee: " +
                            resultSearch.getDouble("TotalFee") + ", Total Paid: " +
                            resultSearch.getDouble("TotalPaid") + ", Deadline: " +
                            resultSearch.getDate("Deadline"));
                }
                resultSearch.close();
                statement.close();
                connection.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }// 5 if=statement

        //search by project number
        else if(userMenuInput.equals("6")) {
            System.out.println("Enter project number: ");
            String findByNum = keyboard.nextLine();

            try {
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/poised_db?allowPublicKeyRetrieval=true&useSSL=false",
                        "userone",
                        "fishy"
                );
                Statement statement = connection.createStatement();

                String SQL = "SELECT * FROM poised_projects WHERE ProjNum = " + findByNum;

                ResultSet resultSearch = statement.executeQuery(SQL);

                while(resultSearch.next()) {
                    System.out.println("Proj Num: " + resultSearch.getInt("ProjNum") + ", Proj Name: " +
                            resultSearch.getString("ProjName") + ", Building Type: " +
                            resultSearch.getString("BuildingType") + ", Proj Address: " +
                            resultSearch.getString("ProjAddress") + ", ERF Num: " +
                            resultSearch.getInt("ERFnum") + ", Total Fee: " +
                            resultSearch.getDouble("TotalFee") + ", Total Paid: " +
                            resultSearch.getDouble("TotalPaid") + ", Deadline: " +
                            resultSearch.getDate("Deadline"));
                }
                resultSearch.close();
                statement.close();
                connection.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }// 6 if=statement

    }//end main

    //methods to get data from user
    private String getStringDate(String message) {
        boolean isValidMessage = false;
        String output = "";
        System.out.println(message);
        while (!isValidMessage) {
            try {
                output = keyboard.nextLine();
                isValidMessage = true;
            } catch (Exception e) {
                System.out.println("Invalid text. Please re-enter. ");
            }
        }//end while

        return output;
    }

    private int getIntData(String message) {
        boolean isValidMessage = false;
        int output = 0;
        System.out.println(message);
        while(!isValidMessage) {
            try {
                output = Integer.parseInt((keyboard.nextLine()));
                isValidMessage = true;
            }
            catch (Exception e) {
                System.out.println("Invalid text. Please enter a number.");
            }
        }//end while
        return output;
    }

    private double getDoubleDate(String message) {
        boolean isValidMessage = false;
        double output = 0;
        System.out.println(message);
        while(!isValidMessage) {
            try {
                output = Double.parseDouble(keyboard.nextLine());
                isValidMessage = true;
            }
            catch(Exception e) {
                System.out.println("Invalid text. Please enter a number");
            }
        }//end while

        return output;
    }

}
