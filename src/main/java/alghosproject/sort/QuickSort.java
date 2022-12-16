package alghosproject.sort;

import alghosproject.models.Post;

public class QuickSort extends AbstractSort {
    public QuickSort(Post[] posts) {
        super(posts);
    }

    @Override
    public void sort(int begin, int end) {
        if (begin >= end) {
            return;
        }

        int pivot = partition(begin, end);

        sort(begin, pivot - 1);
        sort(pivot + 1, end);
    }

    private int partition(int l, int h) {
        int ptr = l - 1;
        long temp = posts[h].getLikes();
        for (int i = l; i < h; i++) {
            if (posts[i].getLikes() >= temp) {
                ptr++;
                swap(i, ptr);
            }
        }
        swap(ptr + 1, h);
        return ptr + 1;
    }
}
