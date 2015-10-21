package ru.kpfu.mst;

import ru.kpfu.generator.Graph;

import java.util.Collections;
import java.util.List;

/**
 * @author Rishat Ibrahimov, "Integrated Information Solutions Ltd"
 */
public class KruskalMST {
  public static Graph findMST(Graph g) {
    int size = g.getVertexCount();
    DisjointSetsUnion dsu = new DisjointSetsUnion(size);
    Graph mst = new Graph(g);
    List<Graph.Edge> edges = g.getEdges();
    Collections.sort(edges);
    for (Graph.Edge edge : edges) {
      if (dsu.findSet(edge.getI()) != dsu.findSet(edge.getJ())) {
        dsu.unite(edge.getI(), edge.getJ());
        mst.addEdge(edge.getI(), edge.getJ(), edge.getC());
      }
      if (mst.getEdges().size() == size - 1) {
        return mst;
      }
    }
    return mst;
  }
}
