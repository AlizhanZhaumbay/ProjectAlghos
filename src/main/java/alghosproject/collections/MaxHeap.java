package alghosproject.collections;

import alghosproject.models.Post;

public class MaxHeap {
    private Post[] posts;
    private int size;
    private int capacity;

    public MaxHeap() {
        this.capacity = 5;
        posts = new Post[capacity];
    }

    public MaxHeap(int capacity) {
        this.capacity = capacity;
        posts = new Post[capacity];
    }

    public void insert(Post post) {
        if(size == capacity){
            capacity *= 2;
            Post[] temp = posts;
            posts = new Post[capacity];
            System.arraycopy(temp,0,posts,0,size);
        }
        posts[size] = post;

        int curr = size;
        while (posts[curr].getLikes() > posts[parent(curr)].getLikes()) {
            swap(curr, parent(curr));
            curr = parent(curr);
        }
        size++;
    }

    public Post getMax() {
        Post mostLikedPost = posts[0];

        posts[0] = posts[--size];
        maxHeapify(0);


        return mostLikedPost;
    }

    public void print(int k) {
        if(k > size) throw new IndexOutOfBoundsException();

        while(k-- > 0){
            System.out.println(getMax());
        }
    }

    private int parent(int position) {
        return (position - 1) / 2;
    }

    private int leftChild(int position) {
        return (position * 2) + 1;
    }

    private int rightChild(int position) {
        return (position * 2) + 2;
    }

    private boolean isLeaf(int position) {
        return position >= (size / 2) && position <= size;
    }

    private void maxHeapify(int position) {
        if (isLeaf(position)) return;

        if (posts[position].getLikes() < posts[leftChild(position)].getLikes()
                || posts[position].getLikes() < posts[rightChild(position)].getLikes()) {
            if (posts[leftChild(position)].getLikes() > posts[rightChild(position)].getLikes()) {
                swap(position, leftChild(position));
                maxHeapify(leftChild(position));
            } else {
                swap(position, rightChild(position));
                maxHeapify(rightChild(position));
            }
        }

    }

    private void swap(int a, int b) {
        Post temp = posts[a];
        posts[a] = posts[b];
        posts[b] = temp;
    }

    public int getSize() {
        return size;
    }

}
