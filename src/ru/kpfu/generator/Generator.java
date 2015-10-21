package ru.kpfu.generator;

import java.util.Random;

/**
 * @author Rishat Ibrahimov, "Integrated Information Solutions Ltd"
 */
public class Generator {
  private static Random random = new Random();

  public static Graph generate(int vertexCount) {
    Graph graph = new Graph();
    for (int i = 0; i < vertexCount; i++) {
      double x = random.nextDouble() * 1000;
      double y = random.nextDouble() * 1000;
      graph.addVertex(new Graph.Vertex(x, y));
    }

    for (int i = 0; i < vertexCount; i++) {
      for (int j = i + 1; j < vertexCount; j++) {
        graph.addEdge(i, j, distance(graph.getVertices().get(i), graph.getVertices().get(j)));
      }
    }
    return graph;
  }

  public static double distance(Graph.Vertex u, Graph.Vertex v) {
    return Math.sqrt((u.getX() - v.getX())*(u.getX() - v.getX()) +  (u.getY() - v.getY())*(u.getY() - v.getY()));
  }
}
