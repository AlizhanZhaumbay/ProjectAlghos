package alghosproject.collections;

public class MyArrayList<T> {
    private T[] arr;
    private int capacity;
    private int size;

    public MyArrayList(int capacity) {
        this.capacity = capacity;
        arr = (T[]) new Object[capacity];
    }

    public MyArrayList() {
        arr = (T[]) new Object[5];
        this.capacity = 5;
    }

    public T get(int index) {
        checkIndex(index);
        return arr[index];
    }

    public void add(T t) {
        if (size == capacity) {
            capacity *= 2;
            T[] temp = arr;
            arr = (T[]) new Object[capacity];
            System.arraycopy(temp, 0, arr, 0, temp.length);
        }
        arr[size++] = t;
    }

    public void add(int index, T t) {
        checkIndex(index);
        T temp = null;
        for(int i = index; i + 1 < size; i++){
            temp = arr[i + 1];
            arr[i + 1] = arr[i];
        }
        arr[index] = t;
        arr[size++] = temp;
    }

    public void remove(int index) {
        checkIndex(index);
        for (int i = index; i + 1 < size; i++) {
            T temp = arr[i];
            arr[i] = arr[i + 1];
            arr[i + 1] = temp;
        }
        arr[size - 1] = null;
        size--;
    }

    public void checkIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException(index);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }
}