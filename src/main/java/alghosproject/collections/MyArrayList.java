package alghosproject.collections;

import java.util.Arrays;

public class MyArrayList<T> {
    private Object[] data;
    private int capacity;
    private int size;

    public MyArrayList(int capacity) {
        this.capacity = capacity;
        data = new Object[capacity];
    }

    public MyArrayList() {
        data = new Object[5];
        this.capacity = 5;
    }

    public T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    public void add(T t) {
        if (size == capacity) {
            capacity *= 2;
            Object[] temp = data;
            data = new Object[capacity];
            System.arraycopy(temp, 0, data, 0, temp.length);
        }
        data[size++] = t;
    }

    public void add(int index, T t) {
        checkIndex(index);
        Object temp = null;
        for (int i = index; i + 1 < size; i++) {
            temp = data[i + 1];
            data[i + 1] = data[i];
        }
        data[index] = t;
        data[size++] = temp;
    }

    public T set(int index, T t) {
        checkIndex(index);
        T oldValue = (T) data[index];
        data[index] = t;
        return oldValue;
    }

    public void remove(int index) {
        checkIndex(index);
        for (int i = index; i + 1 < size; i++) {
            Object temp = data[i];
            data[i] = data[i + 1];
            data[i + 1] = temp;
        }
        data[size - 1] = null;
        size--;
    }

    public Object[] toArray() {
        return Arrays.copyOf(data, size);
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