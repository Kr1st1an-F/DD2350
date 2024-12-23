package part1;

public class BipRed {
    Kattio io;
    Graph graph;
    
    void readBipartiteGraph() {
        // Läs antal hörn och kanter
        int x = io.getInt();
        int y = io.getInt();
        int e = io.getInt();
        graph = new Graph(x, y, e);
        
        // Läs in kanterna
        for (int i = 0; i < e; ++i) {
            int a = io.getInt();
            int b = io.getInt();
            graph.addEdge(a, b);
        }
    }
    
    
    void writeFlowGraph() {
        int v = graph.getX() + graph.getY() + 2;  // alla hörn + s + t
        int s = v-1, t = v; // numrerade som näst sist och sist
        
        // Skriv ut antal hörn och kanter samt källa och sänka
        io.println(v);
        io.println(s + " " + t);
        io.println(graph.getE()+graph.getX()+graph.getY()); // alla kanter + nya kanter till s och t

        for(int i = 0; i < graph.getE(); ++i) // alla kanter i g
            io.println(graph.getEdge(i).u + " " + graph.getEdge(i).v + " 1"); // kapacitet är 1 enligt F13 sida 10
        for(int i = 0; i < graph.getX(); ++i) // alla kanter från s till x
            io.println(s + " " + (i+1) + " 1");
        for(int i = 0; i < graph.getY(); ++i) // alla kanter från y till t
            io.println(graph.getX()+1+i + " " + t + " 1");

        // Var noggrann med att flusha utdata när flödesgrafen skrivits ut!
        io.flush();
        
        // Debugutskrift
        System.err.println("Skickade iväg flödesgrafen");
    }
    
    
    void readMaxFlowSolution() {
        // Läs in antal hörn, kanter, källa, sänka, och totalt flöde
        // (Antal hörn, källa och sänka borde vara samma som vi i grafen vi
        // skickade iväg)
        io.getInt(); // få ut v, samma x och y som förut, inget behov
        int s = io.getInt();
        int t = io.getInt();
        io.getInt(); // får totala flöde, har inget behov
        int e = io.getInt(); // antal kanter med positivt flöde

        graph.clearEdges(e);
        for (int i = 0; i < e; ++i) {
            // Flöde f från a till b
            int a = io.getInt();
            int b = io.getInt();
            io.getInt(); // får ut flödet från a till b, svarta lådan skriver bara ut kanter med positiv flöde, inget behov

            if(a != s && b != s && a != t && b != t) // vi tar bort alla kanter med s och t
                graph.addEdge(a, b);
        }
    }
    
    
    void writeBipMatchSolution() {
        // Skriv ut antal hörn och storleken på matchningen
        io.println(graph.getX() + " " + graph.getY());
        io.println(graph.getE());
        
        for (int i = 0; i < graph.getE(); ++i)// Kant mellan u och v ingår i vår matchningslösning
            io.println(graph.getEdge(i).u + " " + graph.getEdge(i).v);
    }
    
    BipRed() {
        io = new Kattio(System.in, System.out);
        
        readBipartiteGraph();
        
        writeFlowGraph();
        
        readMaxFlowSolution();
        
        writeBipMatchSolution();

        // debugutskrift
        System.err.println("Bipred avslutar\n");

        // Kom ihåg att stänga ner Kattio-klassen
        io.close();
    }
    
    public static void main(String args[]) {
        new BipRed();
    }
}
