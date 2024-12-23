public class Main {
    public static void main(String[] args) {
        final int ROLE_BASECASE = 3;
        final int SCENE_BASECASE = 2;
        final int ACTOR_BASECASE = 3;
        
        Kattio io = new Kattio(System.in);
        
        // input: graph-colouring
        
        int v = io.getInt(); // 1 <= v <= 300, vertices
        int e = io.getInt(); // 0 <= e <= 25000, edges
        int m = io.getInt(); // 1 <= m <= 2^30, colours
        
        int[] vertices = new int[v + 1];
        int[][] edges = new int[e][2];
        for (int i = 0; i < e; i++) {
            int a = io.getInt(), b = io.getInt();
            
            vertices[a] = a;
            vertices[b] = b;
            edges[i][0] = a;
            edges[i][1] = b;
        }
        
        // remove isolated vertices. Each isolated vertex decrement all other further down
        int decr = 0;
        for (int i = 1; i <= v; i++) {
            if (vertices[i] == 0) decr--;
            else vertices[i] += decr + ROLE_BASECASE;
        }
        v += decr;
        
        // output: role-assignment
  
        int n = v + ROLE_BASECASE; // roles
        int s = e + SCENE_BASECASE; // scenes
        if (m > v) m = v;
        int k = m + ACTOR_BASECASE; // actors
        
        StringBuilder sb = new StringBuilder();
        
        // base cases roles
        sb.append(n).append("\n").append(s).append("\n").append(k).append("\n");
        sb.append("1 1\n1 2\n1 3\n");
        
        for (int i = 0; i < v; i++) {
            sb.append(m).append(" ");
            for (int j = ACTOR_BASECASE; j < k; j++) {
                sb.append(j).append(" ");
            }
            sb.append("\n");
        }
        
        // base cases scenes
        sb.append("2 1 3\n2 2 3\n");
        
        for (int i = 0; i < e; i++) {
            sb.append("2 ").append(vertices[edges[i][0]]).append(" ").append(vertices[edges[i][1]]).append("\n");
        }
        
        io.print(sb);
        io.close();
    }
}
