public class Edge {
        public int u;
        public int v;
        public int flow;
        public int capacity;
        public Edge invers; // För flödesminskning
        public Edge parent; // För BFS
        public Edge next; // För Lista

        public Edge(int u, int v, int capacity, int flow) {
            this.u = u;
            this.v = v;
            this.capacity = capacity;
            this.flow = flow;
        }

        @Override
        public boolean equals(Object o) {
            Edge t = (Edge) o;
            if (this.v == t.v)
                return true;
            return false;
        }
    }
