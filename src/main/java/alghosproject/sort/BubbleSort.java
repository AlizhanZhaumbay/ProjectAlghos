package alghosproject.sort;

import alghosproject.models.Post;

public class BubbleSort extends AbstractSort {

    public BubbleSort(Post[] posts) {
        super(posts);
    }

    @Override
    public void sort(int begin, int end) {
        for (int i = begin; i <= end; i++) {
            for (int j = begin; j < end - i; j++) {
                if (posts[j].getLikes() < posts[j + 1].getLikes()) {
                    swap(j, j + 1);
                }
            }
        }
    }
}
