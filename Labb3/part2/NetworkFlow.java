import java.util.LinkedList;
import java.util.Iterator;

public class NetworkFlow {
    private Kattio inputOutput;
    private int numberOfVertices, sourceVertex, sinkVertex, numberOfEdges, totalFlow;
    private LinkedList<FlowEdge>[] edgeList;

    public NetworkFlow() throws Exception {
        inputOutput = new Kattio(System.in, System.out);
        loadNetwork();
        calculateMaximumFlow();
        displayFlowResults();
        inputOutput.close();
    }

    private void loadNetwork() {
        numberOfVertices = inputOutput.getInt();
        sourceVertex = inputOutput.getInt();
        sinkVertex = inputOutput.getInt();
        numberOfEdges = inputOutput.getInt();
        edgeList = new LinkedList[numberOfVertices + 1];

        for (int i = 0; i <= numberOfVertices; i++) {
            edgeList[i] = new LinkedList<>();
        }

        for (int i = 0; i < numberOfEdges; i++) {
            int from = inputOutput.getInt();
            int to = inputOutput.getInt();
            int capacity = inputOutput.getInt();
            FlowEdge forward = new FlowEdge(from, to, capacity, 0);
            FlowEdge backward = new FlowEdge(to, from, 0, 0);

            edgeList[from].add(forward);
            edgeList[to].add(backward);
            forward.reverseEdge = backward;
            backward.reverseEdge = forward;
        }
    }

    private void calculateMaximumFlow() throws Exception {
        totalFlow = 0;
        FlowEdge edge;
        while ((edge = findAugmentingPath()) != null) {
            int pathFlow = Integer.MAX_VALUE;

            for (FlowEdge e = edge; e != null; e = e.parent) {
                pathFlow = Math.min(pathFlow, e.capacity - e.flow);
            }

            for (FlowEdge e = edge; e != null; e = e.parent) {
                e.flow += pathFlow;
                e.reverseEdge.flow -= pathFlow;
            }

            totalFlow += pathFlow;
        }
    }

    private void displayFlowResults() throws Exception {
        inputOutput.println(numberOfVertices);
        inputOutput.println(sourceVertex + " " + sinkVertex + " " + totalFlow);
        LinkedList<String> flows = new LinkedList<>();

        for (int i = 1; i <= numberOfVertices; i++) {
            for (FlowEdge e : edgeList[i]) {
                if (e.flow > 0) {
                    flows.add(e.from + " " + e.to + " " + e.flow);
                }
            }
        }

        inputOutput.println(flows.size());
        for (String flow : flows) {
            inputOutput.println(flow);
        }
        inputOutput.flush();
    }

    private FlowEdge findAugmentingPath() throws Exception {
        boolean[] visited = new boolean[numberOfVertices + 1];
        LinkedList<FlowEdge> queue = new LinkedList<>();
        Iterator<FlowEdge> iter = edgeList[sourceVertex].iterator();

        while (iter.hasNext()) {
            FlowEdge e = iter.next();
            if (e.capacity - e.flow > 0) {
                queue.add(e);
                e.parent = null;
            }
        }

        visited[sourceVertex] = true;

        while (!queue.isEmpty()) {
            FlowEdge current = queue.poll();
            int currentNode = current.to;

            if (!visited[currentNode]) {
                visited[currentNode] = true;
                if (currentNode == sinkVertex) {
                    return current;
                }

                iter = edgeList[currentNode].iterator();
                while (iter.hasNext()) {
                    FlowEdge next = iter.next();
                    if (!visited[next.to] && next.capacity - next.flow > 0) {
                        next.parent = current;
                        queue.add(next);
                    }
                }
            }
        }

        return null;
    }

    public static void main(String[] args) throws Exception {
        new NetworkFlow();
    }

    private class FlowEdge {
        int from, to, flow, capacity;
        FlowEdge reverseEdge, parent;

        FlowEdge(int from, int to, int capacity, int flow) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = flow;
        }
    }
}
