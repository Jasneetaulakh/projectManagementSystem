import java.util.Scanner;

public class Poised {
    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);

        //project input
        System.out.println("Enter project name: ");
        String projName = keyboard.nextLine();
        System.out.println("Enter building type: ");
        String buildingType = keyboard.nextLine();
        System.out.println("Enter project address: ");
        String projAddress = keyboard.nextLine();
        System.out.println("Enter ERF number: ");
        int erfNum = keyboard.nextInt();
        System.out.println("Enter total fee: ");
        double totalFee = keyboard.nextDouble();
        System.out.println("Enter amount paid to date: ");
        double totalPaid = keyboard.nextDouble();
        System.out.println("Project Deadline: ");
        keyboard.nextLine();
        String deadline = keyboard.nextLine();

        //architect input
        System.out.println("Enter architect name: ");
        String name = keyboard.nextLine();
        System.out.println("Enter architect phone number: ");
        String phoneNum = keyboard.nextLine();
        System.out.println("Enter architect email address: ");
        String emailAdd = keyboard.nextLine();
        System.out.println("Enter architect address: ");
        String address = keyboard.nextLine();

        //architect object
        Person architect = new Person(name, phoneNum, emailAdd, address);

        //contractor input
        System.out.println("Enter contractor name: ");
        String name2 = keyboard.nextLine();
        System.out.println("Enter contractor phone number: ");
        String phoneNum2 = keyboard.nextLine();
        System.out.println("Enter contractor email address: ");
        String emailAdd2 = keyboard.nextLine();
        System.out.println("Enter contractor address: ");
        String address2 = keyboard.nextLine();

        //contractor object
        Person contractor = new Person(name2, phoneNum2, emailAdd2, address2);

        //customer input
        System.out.println("Enter customer name: ");
        String name3 = keyboard.nextLine();
        System.out.println("Enter customer phone number: ");
        String phoneNum3 = keyboard.nextLine();
        System.out.println("Enter customer email address: ");
        String emailAdd3 = keyboard.nextLine();
        System.out.println("Enter customer address: ");
        String address3 = keyboard.nextLine();

        //customer object
        Person customer = new Person(name3, phoneNum3, emailAdd3, address3);

        //project object
        Project project1 = new Project(projName, buildingType, projAddress,
                erfNum, totalFee, totalPaid, deadline,architect,
                contractor, customer);

        System.out.println(project1);
        //change deadline/total paid/architect contact details
        project1.setDeadline("31/11/2021");
        project1.setTotalPaid(1500);
        project1.getArchitect().setPhoneNum("01289765566");
        project1.getArchitect().setAddress("5 Happy St");
        project1.getArchitect().setEmailAdd("architect@hello.com");

    }//end main

}
