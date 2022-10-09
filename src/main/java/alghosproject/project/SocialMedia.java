package alghosproject.project;


import alghosproject.dao.SubscriptionSubscriberDAO;
import alghosproject.dao.UserDAO;
import alghosproject.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class SocialMedia {
    private static final Scanner input = new Scanner(System.in);
    private static SocialMedia session;
    private static SubscriptionSubscriberDAO subscriptionSubscriberDAO;
    private static UserDAO userDAO;

    public static void enter(User user) {

        if (Objects.isNull(session)) {
            session = new SocialMedia();
            subscriptionSubscriberDAO = new SubscriptionSubscriberDAO();
            userDAO = new UserDAO();
        }

        while (true) {
            System.out.println();
            System.out.println("1. Your posts");
            System.out.println("2. Your subscribers");
            System.out.println("3. Your subscriptions");
            System.out.println("4. Find user");
            System.out.println("5. Your profile");
            System.out.println("6. Change password");
            System.out.println("-- ENTER ANY OTHER BUTTON TO EXIT. --");
            System.out.println();

            int choice = input.nextInt();
            try {
                switch (choice) {
                    case 1:
                        for (Post post : session.getPosts(user))
                            System.out.println(post);
                        break;
                    case 2:
                        if (!Objects.isNull(subscriptionSubscriberDAO.getSubscribers(user.getId())) &&
                                !subscriptionSubscriberDAO.getSubscribers(user.getId()).isEmpty()) {
                            try {
                                System.out.println("Your Followers: ");
                                List<User> subscribers = subscriptionSubscriberDAO.getSubscribers(user.getId());
                                for (int i = 0; i < subscribers.size(); i++) {
                                    System.out.printf("%d. %s%n", i + 1, subscribers.get(i).getLogin());
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        } else System.out.println("You don't have any subscribers");
                        break;
                    case 3:
                        if (!Objects.isNull(subscriptionSubscriberDAO.getSubscriptions(user.getId())) &&
                                !subscriptionSubscriberDAO.getSubscriptions(user.getId()).isEmpty()) {
                            try {
                                System.out.println("Your subscriptions: ");
                                List<User> subscriptions = subscriptionSubscriberDAO.getSubscriptions(user.getId());
                                for (int i = 0; i < subscriptions.size(); i++) {
                                    System.out.printf("%d. %s%n", i + 1, subscriptions.get(i).getLogin());
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            System.out.println("Your don't have any subscriptions");
                        }
                        break;
                    case 4:
                        System.out.print("Write login: ");
                        String login = input.next();
                        long respondent_id = UserDAO.getUserIdByLogin(login);
                        System.out.println("1. Show profile");
                        boolean subscribed = subscriptionSubscriberDAO.checkForSubscribe(user.getId(), respondent_id);
                        if (subscribed) {
                            System.out.println("2. Unsubscribe");
                        } else {
                            System.out.println("2. Subscribe");
                        }
                        System.out.println("3. Block this user");
                        choice = input.nextInt();
                        if (choice == 1) session.showInfo(userDAO.getUser(login));
                        else if (choice == 2 && subscribed) {
                            subscriptionSubscriberDAO.unfollow(user.getId(), respondent_id);
                            System.out.println("You unsubscribed from user " + login);
                        } else if (choice == 2) {
                            subscriptionSubscriberDAO.follow(user.getId(), respondent_id);
                            System.out.println("You subscribed for user " + login + "!");
                        }
                        break;
                    case 5:
                        session.showInfo(user);
                        break;
                    case 6:
                        session.changePassword(user);
                        break;
                    default:
                        Authentication.main();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<Post> getPosts(User user) {
        return user.getPosts();
    }


    private void showInfo(User user) throws SQLException {
        System.out.println("Id: " + user.getId());
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Age: " + user.getAge());
        System.out.println("Gender: " + user.getGender().toString());
        System.out.println();
    }

    private void changePassword(User user) {
        System.out.println("Enter your previous password: ");
        String password = input.next();
        if (!user.getPassword().equals(password)) {
            System.out.println("Incorrect password. Try again...");
            changePassword(user);
        }
        System.out.println("Enter your new password: ");
        String new_password = input.next();
        user.setPassword(new_password);
        System.out.println("Your password was changed successfully!");
        System.out.println();
    }
}
