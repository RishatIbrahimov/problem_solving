package ru.kpfu;

import ru.kpfu.annealing.SimulatedAnnealing;
import ru.kpfu.generator.Generator;
import ru.kpfu.generator.Graph;
import ru.kpfu.mst.KruskalMST;

public class Main {

  public static void main(String[] args) {
    Graph graph = Generator.generate(100);
    Graph mst = KruskalMST.findMST(graph);
    Graph anneal = SimulatedAnnealing.anneal(graph, 0.00001, 10, 100000);

    graph.writeToFile("graph.txt");
    mst.writeToFile("mstResult.txt");
    anneal.writeToFile("annealResult.txt");

    System.out.println("MST Sum = " + 2 * getWeightSum(mst));
    System.out.println("SimulateAnneal Sum = " + getWeightSum(anneal));
  }

  private static double getWeightSum(Graph g) {
    return g.getEdges().stream().map(Graph.Edge::getC).reduce(0d, (a, b) ->  a + b);
  }
}
