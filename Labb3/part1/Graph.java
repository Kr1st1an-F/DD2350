package part1;

public class Graph {
    private int x;
    private int y;

    private int e;
    private Edge edges[];

    public Graph(int x, int y, int e) {
        this.x = x;
        this.y = y;

        this.edges = new Edge[e];
        this.e = 0;
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int getE() {
        return this.e;
    }

    // kanske vill checka att kanten inte redan finns?
    public void addEdge(int u, int v) {
        addEdge(new Edge(u, v));
    }
    public void addEdge(Edge edge) {
        if(this.e < this.edges.length)
            this.edges[this.e++] = edge;
    }

    public Edge getEdge(int i) {
        return this.edges[i];
    }

    public void clearEdges(int e) {
        edges = new Edge[e];
        this.e = 0;
    }
}
