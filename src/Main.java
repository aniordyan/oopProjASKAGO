import Operations.AddNewCar;
import People.Admin;
import People.User;
import Vehicles.Car;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your role(customer/admin): ");
        String person = sc.next();
        if(person.equals("customer")) {
            User customer = new User();
        } else{
            Admin admin = new Admin();
            admin.EntryPoint();
        }



    }
}