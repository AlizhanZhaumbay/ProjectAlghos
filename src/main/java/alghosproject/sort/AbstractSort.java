package alghosproject.sort;

import alghosproject.models.Post;

public abstract class AbstractSort {
    protected final Post[] posts;

    public AbstractSort(Post[] posts){
        this.posts = posts;
    }

    public abstract void sort(int begin, int end);

    protected void printPosts(Post[] posts) {
        for (Post post : posts) System.out.println(post);
        System.out.println();
    }

    protected void swap(int a, int b){
        Post temp = posts[a];
        posts[a] = posts[b];
        posts[b] = temp;
    }
}
