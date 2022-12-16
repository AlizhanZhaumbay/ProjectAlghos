package alghosproject.collections;

public class MyStack<T> {
    private Object[] data;
    private int size;
    private int capacity;

    public MyStack() {
        capacity = 5;
        data = new Object[capacity];
    }

    public MyStack(int capacity) {
        this.capacity = capacity;
        data = new Object[capacity];
    }

    public void push(T element) {
        if (size == capacity) {
            capacity *= 2;
            Object[] temp = data;
            data = new Object[capacity];
            System.arraycopy(temp, 0, data, 0, size);
        }
        data[size++] = element;
    }

    public T peek(){
        return (T) data[size - 1];
    }

    public T pop() {
        return (T) data[--size];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
