package alghosproject.sort;

import alghosproject.models.Post;

public class InsertionSort extends AbstractSort{
    public InsertionSort(Post[] posts) {
        super(posts);
    }

    @Override
    public void sort(int begin, int end) {
        for (int i = 1; i <= end; i++) {
            Post temp = posts[i];
            int j = i - 1;
            while (j >= begin && posts[j].getLikes() < temp.getLikes()) {
                posts[j + 1] = posts[j];
                j--;
            }
            posts[j + 1] = temp;
        }
    }
}
