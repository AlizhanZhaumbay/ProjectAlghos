package alghosproject.project;


import alghosproject.dao.UserDAO;
import alghosproject.models.User;


import java.sql.SQLException;
import java.util.Scanner;


public class Authentication {

    private static final Scanner input = new Scanner(System.in);
    private static final UserDAO userDAO = new UserDAO();


    public static void authenticate() {
        try {
            while (true) {
                System.out.println("1. Log In");
                System.out.println("2. Register now");
                System.out.println("-- ENTER ANY OTHER BUTTON TO EXIT. --");

                Authentication authentication = new Authentication();
                int choice = input.nextInt();
                if (choice == Auth_TYPE.LOGIN.getValue()) authentication.login();
                else if (choice == Auth_TYPE.REGISTER.getValue()) authentication.register();
                else System.exit(0);
                System.out.println();
            }
        } catch (Exception ignored) {}
    }

    private void register() throws Exception {
        String name = askName();
        String login = askLogin();
        String password = askPassword();
        int age = askAge();
        String phone_number = askPhoneNumber();
        Gender gender = askGender();
        String email = askEmail();

        User user = new User(login, password, name, age, email, gender, phone_number);
        userDAO.addUser(user);
        new SocialMedia().enter(user);
    }

    private String askName() {
        System.out.println("Enter your name: ");
        input.nextLine();
        String name = input.nextLine();
        if (name.isEmpty()) {
            System.out.println("Incorrect name");
            return askName();
        }
        return name;
    }

    private int askAge() {
        System.out.println("Enter your age: ");
        int age = 0;
        try {
            age = input.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid data. Try again...");
            askAge();
        }
        return age;
    }

    private String askLogin() {
        System.out.println("Enter your login: ");
        String name = input.nextLine();
        if (name.isEmpty()) {
            System.out.println("Incorrect login");
            askLogin();
        }
        return name;
    }

    private String askEmail() {
        System.out.println("Enter your email: ");
        String email = input.next();
        if (email.isEmpty()) {
            System.out.println("Incorrect email");
            askName();
        }
        return email;
    }

    private Gender askGender() {
        System.out.println("Enter your gender: ");
        String gender = input.nextLine();
        if (gender.equalsIgnoreCase("male")) {
            return Gender.MALE;
        } else if (gender.equalsIgnoreCase("female")) {
            return Gender.FEMALE;
        }
        System.out.println("Gender was written incorrectly...");
        return askGender();
    }

    private String askPhoneNumber() {
        System.out.println("Enter your phone_number: ");
        input.nextLine();
        String phone_number = input.nextLine();
        return phone_number;
    }

    private String askPassword() {
        System.out.println("Enter your password: ");
        String password = input.nextLine();
        return password;
    }


    private void login() throws SQLException {
        System.out.print("Enter your login: ");
        String login = input.next();
        System.out.print("Enter your password: ");
        String password = input.next();
        User user;
        try {
            user = userDAO.getUser(login, password);
            new SocialMedia().enter(user);
        } catch (Exception e) {
            System.out.println("Incorrect login or password...");
        }
    }

}
