package ru.kpfu.mst;

/**
 * @author Rishat Ibrahimov, "Integrated Information Solutions Ltd"
 */
public class DisjointSetsUnion {
  private int[] parent;
  private int[] rank;

  public DisjointSetsUnion(int size) {
    parent = new int[size];
    rank = new int[size];
    for (int i = 0; i < size; i++) {
      parent[i] = i;
      rank[i] = 0;
    }
  }

  public int findSet(int v) {
    return parent[v] == v ? v : (parent[v] = findSet(parent[v]));
  }

  public void unite(int a, int b) {
    a = findSet(a);
    b = findSet(b);
    if (a != b) {
      if (rank[a] < rank[b]) {
        a = a ^ b;
        b = b ^ a;
        a = a ^ b;
      }
      parent[b] = a;
      if (rank[a] == rank[b])
        ++rank[a];
    }

  }
}
