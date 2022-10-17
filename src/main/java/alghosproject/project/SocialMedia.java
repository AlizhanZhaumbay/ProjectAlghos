package alghosproject.project;


import alghosproject.dao.PostDAO;
import alghosproject.dao.SubscriptionSubscriberDAO;
import alghosproject.dao.UserDAO;
import alghosproject.models.Post;
import alghosproject.models.User;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class SocialMedia {
    private static final Scanner input = new Scanner(System.in);
    private static SocialMedia session;
    private static SubscriptionSubscriberDAO subscriptionSubscriberDAO;
    private static UserDAO userDAO;
    private static PostDAO postDAO;

    public static void enter(User user) {

        if (Objects.isNull(session)) {
            session = new SocialMedia();
            subscriptionSubscriberDAO = new SubscriptionSubscriberDAO();
            userDAO = new UserDAO();
            postDAO = new PostDAO();
        }

        while (true) {
            System.out.println();
            System.out.println("1. Create new post");
            System.out.println("2. Your posts");
            System.out.println("3. Your subscribers");
            System.out.println("4. Your subscriptions");
            System.out.println("5. Find user");
            System.out.println("6. Your profile");
            System.out.println("7. Change password");
            System.out.println("-- ENTER ANY OTHER BUTTON TO EXIT. --");
            System.out.println();

            int choice = input.nextInt();
            try {
                switch (choice) {
                    case 1:
                        session.addPostToUser(user);
                        break;
                    case 2:
                        session.showPosts(user);
                        break;
                    case 3:
                        session.findAllSubscribers(user);
                        break;
                    case 4:
                        session.findAllSubscriptions(user);
                        break;
                    case 5:
                        session.findUser(user);
                        break;
                    case 6:
                        session.showInfo(user);
                        break;
                    case 7:
                        session.changePassword(user);
                        break;
                    default:
                        Authentication.main();
                        break;
                }
                System.out.println();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addPostToUser(User user) throws SQLException {
        System.out.print("Write something about yourself: ");
        input.nextLine();
        String text = input.nextLine();
        Post post = new Post(text, Date.valueOf(LocalDate.now()), user.getId());
        postDAO.addPost(post);
        System.out.println("You added new post!");
    }

    private List<Post> getPosts(User user) throws SQLException {
        return postDAO.getPostsOfUser(user.getId());
    }

    private void showPosts(User user) throws SQLException {
        List<Post> posts = session.getPosts(user);
        if (posts.isEmpty()) System.out.println("You don't have any posts.");
        else {
            for (Post post : posts)
                System.out.println(post);
        }
    }

    private void findUser(User user) throws SQLException {
        System.out.print("Write login: ");
        String login = input.next();
        int choice = -1;
        long respondent_id = UserDAO.getUserIdByLogin(login);
        actionsWithUser(user, respondent_id, login);
    }

    private void actionsWithUser(User user, long respondent_id, String login) throws SQLException {
        int choice = -1;
        while (true) {
            System.out.println("1. Show profile");
            boolean subscribed = subscriptionSubscriberDAO.checkForSubscribe(user.getId(), respondent_id);
            if (subscribed) {
                System.out.println("2. Unsubscribe");
            } else {
                System.out.println("2. Subscribe");
            }
            System.out.println("3. Show posts");
            System.out.println("4. Block this user");
            try {
                choice = input.nextInt();
            } catch (Exception e) {
                break;
            }
            if (choice == 1) session.showInfo(userDAO.getUser(login));
            else if (choice == 2 && subscribed) {
                subscriptionSubscriberDAO.unfollow(user.getId(), respondent_id);
                System.out.println("You unsubscribed from user " + login);
            } else if (choice == 2) {
                subscriptionSubscriberDAO.follow(user.getId(), respondent_id);
                System.out.println("You subscribed for user " + login + "!");
            } else if (choice == 3) {
                session.showPosts(userDAO.getUser(respondent_id));
            } else break;
        }
    }

    private void findAllSubscribers(User user) throws SQLException {
        if (!Objects.isNull(subscriptionSubscriberDAO.getSubscribers(user.getId())) &&
                !subscriptionSubscriberDAO.getSubscribers(user.getId()).isEmpty()) {

            System.out.println("Your Followers: ");
            List<User> subscribers = subscriptionSubscriberDAO.getSubscribers(user.getId());
            printAllUsers(subscribers);
            try {
                int choice = input.nextInt();
                if (choice > subscribers.size() || choice < subscribers.size()) return;

                User user_to_action = subscribers.get(choice);
                actionsWithUser(user,user_to_action.getId(), user_to_action.getLogin());
            } catch (Exception e) {
            }
        } else System.out.println("You don't have any subscribers");
    }

    private void findAllSubscriptions(User user) throws SQLException {
        if (!Objects.isNull(subscriptionSubscriberDAO.getSubscriptions(user.getId())) &&
                !subscriptionSubscriberDAO.getSubscriptions(user.getId()).isEmpty()) {
            try {
                System.out.println("Your subscriptions: ");
                List<User> subscriptions = subscriptionSubscriberDAO.getSubscriptions(user.getId());
                printAllUsers(subscriptions);
                try {
                    int choice = input.nextInt();
                    if (choice > subscriptions.size() || choice < subscriptions.size()) return;
                    User user_to_action = subscriptions.get(choice - 1);
                    actionsWithUser(user,user_to_action.getId(), user_to_action.getLogin());
                } catch (Exception e) {
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Your don't have any subscriptions");
        }
    }

    private void printAllUsers(List<User> users) {
        for (int i = 0; i < users.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, users.get(i).getLogin());
        }
        System.out.println();
    }


    private void showInfo(User user) throws SQLException {
        System.out.println("Id: " + user.getId());
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Age: " + user.getAge());
        System.out.println("Gender: " + user.getGender().toString());
        System.out.println();
    }

    private void changePassword(User user) throws SQLException {
        System.out.println("Enter your previous password: ");
        String password = input.next();
        if (!user.getPassword().equals(password)) {
            System.out.println("Incorrect password. Try again...");
            changePassword(user);
        }
        System.out.println("Enter your new password: ");
        String new_password = input.next();
        user.setPassword(new_password);
        userDAO.changePassword(user.getLogin(), new_password);
        System.out.println("Your password was changed successfully!");
        System.out.println();
    }
}
