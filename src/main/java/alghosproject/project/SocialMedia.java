package alghosproject.project;

import alghosproject.collections.MyArrayList;
import alghosproject.dao.PostDAO;
import alghosproject.dao.SubscriptionSubscriberDAO;
import alghosproject.dao.UserDAO;
import alghosproject.models.Comment;
import alghosproject.models.Post;
import alghosproject.models.User;
import alghosproject.util.UserDetails;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class SocialMedia {
    private static final Scanner input;
    private static final SubscriptionSubscriberDAO subscriptionSubscriberDAO;
    private static final UserDAO userDAO;
    private static final PostDAO postDAO;

    public void enter(User currentUser) throws SQLException {
        loop:
        while (true) {
            // Initializing this user
            UserDetails userDetails = new UserDetails(currentUser);

            System.out.println();
            System.out.println("1. News");
            System.out.println("2. Create new post");
            System.out.println("3. Your posts");
            System.out.println("4. Your subscribers");
            System.out.println("5. Your subscriptions");
            System.out.println("6. Find user");
            System.out.println("7. Your profile");
            System.out.println("8. Change password");
            System.out.println("-- ENTER ANY OTHER BUTTON TO EXIT. --");
            System.out.println();

            int choice = getChoiceOrLeave(8);

            switch (choice) {
                case 1 -> userDetails.showNews();
                case 2 -> addPostToUser(currentUser);
                case 3 -> {
                    long post_id = userDetails.showPostsOfUser();
                    actionsWithPost(currentUser, post_id);
                }
                case 4 -> findAllSubscribersAndSubscriptions(currentUser, SubscriptionTYPE.SUBSCRIBER);
                case 5 -> findAllSubscribersAndSubscriptions(currentUser, SubscriptionTYPE.RESIDENT);
                case 6 -> findUserByLogin(currentUser);
                case 7 -> userDetails.showInfo();
                case 8 -> changePassword(currentUser);
                default -> {
                    break loop;
                }
            }
            System.out.println();

        }
        Authentication.authenticate();
    }

    private void addPostToUser(User currentUser) throws SQLException {
        System.out.print("Write something about yourself: ");
        input.nextLine();
        String text = input.nextLine();
        System.out.println("Give a name to this post");
        String name = input.nextLine();
        Post post = new Post(text, Date.valueOf(LocalDate.now()), currentUser.getId(), name, 0);
        postDAO.addPost(post);
        System.out.println("You added new post!");
    }

    private MyArrayList<Post> getPosts(User currentUser) throws SQLException {
        return postDAO.getPostsOfUser(currentUser.getId());
    }

    private void actionsWithPost(User currentUser, long post_id) throws SQLException {

        System.out.println("\n" + "1. Like");
        System.out.printf("%nAdd comment? Yes : No%n");

        String answer = input.next();

        if (answer.equalsIgnoreCase("yes")) {
            System.out.print("Your comment: ");
            input.nextLine();
            String comment = input.nextLine();
            postDAO.addComment(new Comment
                    (comment, new Date(System.currentTimeMillis()), currentUser.getId(), post_id));
        } else if (answer.equalsIgnoreCase("1")) {
            postDAO.addLike(post_id);
        }
        System.out.println();
    }

    private void findUserByLogin(User currentUser) throws SQLException {
        System.out.print("Write login: ");
        String login = input.next();
        User user_to_visit = userDAO.getUser(login);
        actionsWithUser(currentUser, user_to_visit);
    }

    private void actionsWithUser(User currentUser, User user_to_visit) throws SQLException {
        if (subscriptionSubscriberDAO.checkForBlocking(user_to_visit.getId(), currentUser.getId())) {
            System.out.println("This user blocked you. Don't want to block it? YES/NO");
            String response = input.next();
            if (response.equalsIgnoreCase("yes")) {
                subscriptionSubscriberDAO.blockUser(currentUser.getId(), user_to_visit.getId());
            }
            return;
        }

        loop:
        while (true) {
            boolean blocked =
                    subscriptionSubscriberDAO.checkForBlocking(currentUser.getId(), user_to_visit.getId());
            boolean subscribed =
                    subscriptionSubscriberDAO.checkForSubscribe(currentUser.getId(), user_to_visit.getId());
            String subscribeCaption = (!subscribed) ? "Subscribe" : "Unsubscribe";
            String blockCaption = (blocked) ? "Unblock" : "Block";


            System.out.println("1. Show profile");
            System.out.printf("2. %s%n", subscribeCaption);
            System.out.println("3. Show posts");
            System.out.printf("4. %s this user%n", blockCaption);

            int choice = getChoiceOrLeave(4);
            UserDetails userDetails = new UserDetails(user_to_visit);

            switch (choice) {
                case 1 -> userDetails.showInfo();
                case 2 -> {
                    if (subscribed) {
                        subscriptionSubscriberDAO.unfollow(currentUser.getId(), user_to_visit.getId());
                        System.out.println("You unsubscribed from user " + user_to_visit.getLogin());
                    } else {
                        subscriptionSubscriberDAO.follow(currentUser.getId(), user_to_visit.getId());
                        System.out.println("You subscribed for user " + user_to_visit.getLogin());
                    }
                }
                case 3 -> {
                    long post_id = userDetails.showPostsOfUser();
                    if (post_id >= 0) {
                        actionsWithPost(currentUser, post_id);
                    }
                }
                case 4 -> {
                    if (blocked) {
                        subscriptionSubscriberDAO.unblockUser(currentUser.getId(), user_to_visit.getId());
                        System.out.println("You unblocked " + user_to_visit.getLogin() + ":)");
                    } else {
                        subscriptionSubscriberDAO.blockUser(currentUser.getId(), user_to_visit.getId());
                        System.out.println("You blocked " + user_to_visit.getLogin() + " :(");
                    }
                }
                default -> {
                    break loop;
                }
            }
        }
    }

    private void findAllSubscribersAndSubscriptions(User currentUser, SubscriptionTYPE subscriptionTYPE) throws SQLException {
        MyArrayList<User> users;

        String caption = (subscriptionTYPE == SubscriptionTYPE.SUBSCRIBER) ? "Subscribers" : "Subscriptions";

        if (subscriptionSubscriberDAO.getSubscriptions(currentUser.getId()).isEmpty()
                && subscriptionSubscriberDAO.getSubscribers(currentUser.getId()).isEmpty()) {
            System.out.printf("You don't have any %s%n", caption);
            return;
        }

        if (subscriptionTYPE == SubscriptionTYPE.SUBSCRIBER) {
            users = subscriptionSubscriberDAO.getSubscribers(currentUser.getId());
        } else {
            users = subscriptionSubscriberDAO.getSubscriptions(currentUser.getId());
        }

        //Print all
        System.out.printf("Your %s%n: ", caption);
        printAllUsers(users);

        try {
            int choice = input.nextInt();
            User user_to_visit = users.get(choice - 1);
            actionsWithUser(currentUser, user_to_visit);
        } catch (Exception e) {
            System.out.println("Invalid type of value...");
        }
    }

    public void printAllUsers(MyArrayList<User> users) {
        for (int i = 0; i < users.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, users.get(i).getLogin());
        }
        System.out.println();
    }

    public void changePassword(User currentUser) throws SQLException {
        System.out.println("Enter your previous password: ");
        String password = input.next();
        if (!currentUser.getPassword().equals(password)) {
            System.out.println("Incorrect password. Try again...");
            changePassword(currentUser);
        } else {
            System.out.println("Enter your new password: ");
            String new_password = input.next();
            currentUser.setPassword(new_password);
            userDAO.changePassword(currentUser.getLogin(), new_password);
            System.out.printf("Your password was changed successfully!%n%n");
        }
    }

    private int getChoiceOrLeave(int size) {
        int choice;
        try {
            choice = input.nextInt();
        } catch (Exception ignored) {
            return 0;
        }
        if (choice < 1 || choice > size) return 0;
        return choice;
    }

    static {
        subscriptionSubscriberDAO = new SubscriptionSubscriberDAO();
        userDAO = new UserDAO();
        postDAO = new PostDAO();
        input = new Scanner(System.in);
    }
}
