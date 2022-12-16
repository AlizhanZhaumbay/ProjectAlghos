package alghosproject.collections.graph;

import alghosproject.collections.MyStack;

public class Graph {
    private int maxN = 10;
    private int[][] mas;
    Vertex[] vertices;
    private int currN;
    private MyStack<Integer> myStack;

    public Graph() {
        vertices = new Vertex[maxN];
        mas = new int[maxN][maxN];
    }

    public void addVertex(char name) {
        vertices[currN++] = new Vertex(name);
    }

    public void addEdge(int start, int end, int val) {
        mas[start][end] = 1;
        mas[end][start] = val;
    }

    public int check(int v) {
        for (int i = 0; i < currN; i++) {
            if (mas[v][i] == 1 && !vertices[i].isVisited) {
                return i;
            }
        }

        return -1;
    }

    public void passInDeep(int index) {
        System.out.println(vertices[index].name);
        vertices[index].isVisited = true;
        myStack.push(index);

        while (!myStack.isEmpty()) {
            int neighbour = check(myStack.peek());

            if (neighbour == -1) {
                neighbour = myStack.pop();
            } else {
                System.out.println(vertices[neighbour].name);
                vertices[neighbour].isVisited = true;
                myStack.push(neighbour);
            }
        }

        for (int i = 0; i < currN; i++) {
            vertices[i].isVisited = false;
        }
    }
}
