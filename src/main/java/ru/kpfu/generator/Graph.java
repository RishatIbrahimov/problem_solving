package ru.kpfu.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rishat Ibrahimov, "Integrated Information Solutions Ltd"
 */
public class Graph {
  private int vertexCount;
  private List<Edge> edges;
  private List<Vertex> vertices;

  public Graph(Graph graph) {
    this();
    for (Vertex vertex : graph.getVertices()) {
      addVertex(new Vertex(vertex.getX(), vertex.getY()));
    }
  }

  public Graph() {
    this.edges = new ArrayList<>();
    this.vertices = new ArrayList<>();
    vertexCount = 0;
  }

  public void addVertex(Vertex vertex) {
    vertices.add(vertex);
    vertexCount++;
  }

  public boolean addEdge(int i, int j, double c) {
    if (i >= vertexCount || j >= vertexCount) {
      return false;
    }
    return edges.add(new Edge(i, j, c));
  }

  public List<Edge> getEdges() {
    return edges;
  }

  public List<Vertex> getVertices() {
    return vertices;
  }

  public int getVertexCount() {
    return vertexCount;
  }

  public void writeToFile(String fileName) {
    try (PrintWriter printWriter = new PrintWriter(new FileOutputStream(new File(fileName)))) {
      //printWriter.println(vertexCount);
      StringBuilder xBuilder = new StringBuilder("[");
      StringBuilder yBuilder = new StringBuilder("[");
      boolean first = true;
      for (Vertex vertex : vertices) {
        if (!first) {
          xBuilder.append(", ");
          yBuilder.append(", ");
        }
        first = false;
        xBuilder.append(vertex.getX());
        yBuilder.append(vertex.getY());
      }
      xBuilder.append("]");
      yBuilder.append("]");

      printWriter.println("x = " + xBuilder.toString());
      printWriter.println("y = " + yBuilder.toString());
      printWriter.println("plot(x, y, 'o')");

      //printWriter.println(edges.size());
      printWriter.println();
      for (Edge edge : edges) {
        Vertex u = vertices.get(edge.getI());
        Vertex v = vertices.get(edge.getJ());
        printWriter.println(String.format("plot([%s, %s], [%s, %s])", u.getX(), v.getX(), u.getY(), v.getY()));
      }

      printWriter.close();
    } catch (FileNotFoundException e) {
      throw new IllegalStateException(e);
    }
  }

  public static class Edge implements Comparable<Edge> {
    private int i;
    private int j;
    private double c;

    public Edge(int i, int j, double c) {
      this.i = i;
      this.j = j;
      this.c = c;
    }

    public int getI() {
      return i;
    }

    public void setI(int i) {
      this.i = i;
    }

    public int getJ() {
      return j;
    }

    public void setJ(int j) {
      this.j = j;
    }

    public double getC() {
      return c;
    }

    public void setC(double c) {
      this.c = c;
    }

    @Override
    public int compareTo(Edge o) {
      return Double.compare(c, o.c);
    }
  }

  public static class Vertex {
    private double x;
    private double y;

    public Vertex(double x, double y) {
      this.x = x;
      this.y = y;
    }

    public double getX() {
      return x;
    }

    public void setX(double x) {
      this.x = x;
    }

    public double getY() {
      return y;
    }

    public void setY(double y) {
      this.y = y;
    }
  }
}
