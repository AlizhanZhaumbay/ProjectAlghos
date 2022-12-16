package alghosproject.sort;

import alghosproject.models.Post;

public class MergeSort extends AbstractSort {
    public MergeSort(Post[] posts) {
        super(posts);
    }

    @Override
    public void sort(int begin, int end) {
        if (begin < end) {
            int m = begin + (end - begin) / 2;

            sort(begin, m);
            sort(m + 1, end);

            merge(begin, m, end);
        }
    }

    private void merge(int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        Post[] L = new Post[n1];
        Post[] R = new Post[n2];

        System.arraycopy(posts, l, L, 0, n1);
        System.arraycopy(posts, m + 1, R, 0, n2);

        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            if (L[i].getLikes() > R[j].getLikes()) {
                posts[k] = L[i];
                i++;
            } else {
                posts[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            posts[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            posts[k] = R[j];
            j++;
            k++;
        }
    }


}
