package alghosproject.util;

import alghosproject.collections.MaxHeap;
import alghosproject.collections.MyArrayList;
import alghosproject.collections.MyHashtable;
import alghosproject.dao.PostDAO;
import alghosproject.dao.UserDAO;
import alghosproject.models.Comment;
import alghosproject.models.Post;
import alghosproject.models.User;
import alghosproject.sort.BubbleSort;
import alghosproject.sort.InsertionSort;
import alghosproject.sort.MergeSort;
import alghosproject.sort.QuickSort;

import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.Scanner;

public class UserDetails {
    private static final Scanner input;
    private static final UserDAO userDAO;
    private final User user;
    private static final PostDAO postDAO;

    public UserDetails(User user) {
        this.user = user;
    }

    public long showPostsOfUser() throws SQLException {
        MyArrayList<Post> posts = postDAO.getPostsOfUser(user.getId());
        if (posts.isEmpty()) {
            System.out.println("User doesn't have any posts.");
            return -1;
        }

        for (int i = 0; i < posts.size(); i++) {
            System.out.printf("%d. %s.%n%d - likes.%n", i + 1, posts.get(i).getName(),
                    postDAO.getLikesOfPost(posts.get(i).getId()));
        }
        System.out.println();
        int post_idx = getChoiceOrLeave(posts.size());
        if(post_idx < 0) return -1;
        return showCommentsOfPost(posts.get(post_idx - 1).getId());
    }

    public long showCommentsOfPost(long post_id) throws SQLException {
        if(post_id < 0) return -1;
        MyArrayList<Comment> comments = postDAO.getComments(post_id);

        if (comments != null && !comments.isEmpty()) {
            for (int i = 0; i < comments.size(); i++) {
                Comment comment = comments.get(i);

                User commentator = userDAO.getUser(comment.getUser_id());

                System.out.printf("%s: %s.%n", commentator.getLogin(), comment.getMessage());
            }
        } else {
            System.out.println("This post hasn't any comments.");
        }

        return post_id;
    }


    public void showInfo() throws SQLException {
        System.out.println("Id: " + user.getId());
        System.out.println("Name: " + user.getName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Age: " + user.getAge());
        System.out.println("Gender: " + user.getGender().toString());
        System.out.println();
    }

    private User findUserByLogin() throws SQLException {
        System.out.print("Write login: ");
        String login = input.next();
        User user = userDAO.getUser(login);
        return user;
    }


    public void showNews() throws SQLException {
        MyArrayList<Post> posts = postDAO.getAllPosts();


        loop:
        while (true) {
            System.out.println("1. Getting k posts with a maximum number of likes");
            System.out.println("2. Sorting posts by number of likes");
            System.out.println("3. Checking how many likes a post has");
            System.out.println("4. Checking which your subscriber has the highest number of common subscribers");
            System.out.println("Press any other digit to leave.");

            int choice = getChoiceOrLeave(4);

            switch (choice) {
                case 1 -> getKPostsWithMaxLikes(posts);
                case 2 -> sortingPostsByLikes(posts);
                case 3 -> checkLikesOfPost(posts);
                //case 4 -> commonSubscribers();
                default -> {
                    break loop;
                }
            }

        }
    }

    private int getChoiceOrLeave(int size) {
        int choice;
        try {
            choice = input.nextInt();
        } catch (Exception ignored) {
            return -1;
        }
        if (choice < 1 || choice > size) return -1;
        return choice;
    }

    private void checkLikesOfPost(MyArrayList<Post> posts) {
        MyHashtable<Post, Long> myHashtable = new MyHashtable<>();
        for (int i = 0; i < posts.size(); i++) {
            myHashtable.add(posts.get(i),posts.get(i).getLikes());
        }
        for(int i = 0; i < posts.size(); i++){
            Post post = posts.get(i);
            System.out.println(posts.get(i).getName() + " " + myHashtable.get(post));
        }
        System.out.println();
    }

    private void getKPostsWithMaxLikes(MyArrayList<Post> posts) {
        System.out.print("Enter k: ");
        int k = input.nextInt();
        System.out.println();

        MaxHeap maxHeap = new MaxHeap(posts.size());
        for (int i = 0; i < posts.size(); i++) {
            maxHeap.insert(posts.get(i));
        }

        maxHeap.print(k);
    }

    private void sortingPostsByLikes(MyArrayList<Post> posts) {
        Post[] postsArr = (Post[]) Array.newInstance(Post.class, posts.size());
        System.arraycopy(posts.toArray(), 0, postsArr, 0, posts.size());

        System.out.println("1. Bubble sort.");
        System.out.println("2. Insertion sort");
        System.out.println("3. Quick sort");
        System.out.println("4. Merge sort");
        System.out.println("Press any other digit to leave.");

        int choice = getChoiceOrLeave(4);
        switch (choice) {
            case 1 -> new BubbleSort(postsArr).sort(0, postsArr.length - 1);
            case 2 -> new InsertionSort(postsArr).sort(0, postsArr.length - 1);
            case 3 -> new QuickSort(postsArr).sort(0, postsArr.length - 1);
            case 4 -> new MergeSort(postsArr).sort(0, postsArr.length - 1);
        }
        printPosts(postsArr);
    }

    private void printPosts(Post[] posts) {
        for (Post post : posts) System.out.println(post);
        System.out.println();
    }

    public User getUser() {
        return user;
    }

    static {
        input = new Scanner(System.in);
        userDAO = new UserDAO();
        postDAO = new PostDAO();
    }

}
