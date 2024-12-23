public class BipRed {
    int s, t, totalFlow, v, x, y, e;
    Kattio io;
    //public static int r = 1; // Eftersom vi analyserer bipartita grafer kommer
                                // flödet alltid vara 1, alltså maxflödet kommer
                                // också alltid vara 1
    LinkedList[] edgeList; // Kanterna från en viss nod.

    public BipRed() throws Exception {
        io = new Kattio(System.in, System.out);
        loadNetwork();
        calculateMaximumFlow();
        displayFlowResults();
        io.close();
    }

    private void loadNetwork() {
        // Läser in från System.in
        x = io.getInt();
        y = io.getInt();
        e = io.getInt();
        // Räknar ut s och t, antalet kanter v får vi genom att kolla på den
        // sista kanten (t).
        s = x + y + 2;
        t = s + 1;
        v = t;
        // Vi använder oss av grannmatriser för att få bättre tidskomplexitet
        // O(v³) ist för O(v⁴)
        edgeList = new LinkedList[v + 1];
        // Inga null, tack!!!
        for (int i = 0; i <= v; i++)
            edgeList[i] = new LinkedList();
        // Läs in kanterna
        for (int i = 0; i < e; ++i) {
            int a = io.getInt();
            int b = io.getInt();
            addkant(a, b);
        }
        // Lägger till kanter från källan till alla element i X
        for (int i = 0; i < x; i++) {
            addkant(s, (i + 1));
        }
        // Lägger till kanter från alla element i Y till sänkan.
        for (int i = 0; i < y; i++) {
            addkant(x + 1 + i, t);
        }

    }

    private void addkant(int a, int b) {
        Edge x = new Edge(a, b, 1, 0);
        Edge y = new Edge(b, a, 0, 0);
    
        edgeList[a].add(x);
        edgeList[b].add(y);
        x.invers = y;
        y.invers = x;
    }

    private void displayFlowResults() {
        io.println(x + " " + y);
        io.println(totalFlow);
        for (int i = 1; i < edgeList.length - 2; i++) {
            Edge le = edgeList[i].first;
            while (le != null) {
                if (le.v != t && le.flow > 0) {
                    io.println(i + " " + le.v);
                }
                le = le.next;
            }
        }
        io.flush(); //Glöm inte att spola kröken
    }

    /**
     * Som pseudo-koden i labblydelsen 
     */
    private void calculateMaximumFlow() throws Exception {
        Edge x;
        totalFlow = 0;
        //BFS reurnerar sista kanten i en stig, minska flöde med r för alla i stigen. (För alla stigar)
        while ((x = BFS()) != null) {
            while (x != null) {
                x.flow += 1;
                x.invers.flow = -x.flow;
                x = x.parent; //Vi använder inte restflow utan räknar cap - flow
            }
            totalFlow += 1;
        }
    }
    /**
     * trad. bfs
     * @return
     * @throws Exception
     */
    private Edge BFS() throws Exception {
        boolean[] visited = new boolean[v + 1];
        Queue q = new Queue();
        Edge le = edgeList[s].first;
        while (le != null) {
            if ((le.capacity - le.flow) > 0) {
                q.Put(le);
                le.parent = null;
            }
            le = le.next;
        }
        visited[s] = true;
        while (!q.IsEmpty()) {
            Edge at = (Edge) q.Get();
            if (!visited[at.v]) {
                visited[at.v] = true;
                if (at.v == t) {
                    return at;
                }
                le = edgeList[at.v].first;
                while (le != null) {
                    if (!visited[le.v] && (le.capacity - le.flow) > 0) {
                        le.parent = at;
                        q.Put(le);
                    }
                    le = le.next;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        new BipRed();
    }
}
