import Exceptions.SongNotFoundException;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) throws SongNotFoundException {

     Scanner sc = new Scanner(System.in);
        System.out.println("Enter your role(user/creator): ");
        String person = sc.next();
        if(person.equals("user")) {
            User customer = new User();
        } else{
            Creator admin = new Creator();
            System.out.printf("------------------------------------------%n");
            System.out.printf("      Welcome to ASKAGO Music Player      %n");
            System.out.printf("             (creator panel)              %n");
            System.out.printf("------------------------------------------%n");
            admin.EntryPoint();
        }



    }
}