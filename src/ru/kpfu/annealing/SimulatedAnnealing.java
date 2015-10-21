package ru.kpfu.annealing;

import ru.kpfu.generator.Generator;
import ru.kpfu.generator.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Rishat Ibrahimov, "Integrated Information Solutions Ltd"
 */
public class SimulatedAnnealing {
  private static Random random = new Random();

  public static Graph anneal(Graph g, double minT, double maxT, int maxStep) {
    double t = maxT;
    List<Integer> state = prepareState(g.getVertexCount());
    double energy = getEnergy(state, g);
    int step = 1;
    createGraph(state, g).writeToFile("anneal_initial.txt");
    while (/*t > minT && */step < maxStep) {
      List<Integer> candidate = generateCandidate(state);
      double candidateEnergy = getEnergy(candidate, g);
      if (candidateEnergy < energy || needTransition(getTransitionProbability(candidateEnergy - energy, t))) {
        energy = candidateEnergy;
        state = candidate;
      }
      t = decreaseTemperature(t, step);
      step++;
    }
    return createGraph(state, g);
  }

  private static double decreaseTemperature(double temperature, int step) {
    return temperature * 0.1 / step;
  }

  private static boolean needTransition(double probability) {
    double v = random.nextDouble();
    return v < probability;
  }

  private static List<Integer> prepareState(int size) {
    List<Integer> state = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      state.add(i);
    }
    Collections.shuffle(state);
    return state;
  }

  private static List<Integer> generateCandidate(List<Integer> state) {
    int r1 = random.nextInt(state.size());
    int r2 = random.nextInt(state.size());
    int start = Math.min(r1, r2);
    int end = Math.max(r1, r2);

    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < start; i++) {
      result.add(state.get(i));
    }
    for (int i = start; i < end; i++) {
      result.add(state.get(end - i - 1 + start));
    }
    for (int i = end; i < state.size(); i++) {
      result.add(state.get(i));
    }
    return result;
  }

  private static double getEnergy(List<Integer> state, Graph g) {
    double energy = 0;
    List<Graph.Vertex> vertices = g.getVertices();
    for (int i = 1; i < state.size(); i++) {
      energy += Generator.distance(vertices.get(state.get(i-1)), vertices.get(state.get(i)));
    }
    energy += Generator.distance(vertices.get(state.get(0)), vertices.get(state.get(state.size() - 1)));
    return energy;
  }

  private static double getTransitionProbability(double diff, double t) {
    return Math.exp(-diff/t);
  }

  private static Graph createGraph(List<Integer> state, Graph g) {
    Graph result = new Graph(g);
    for (int i = 1; i < state.size(); i++) {
      int s = state.get(i-1);
      int e = state.get(i);
      result.addEdge(s, e, Generator.distance(g.getVertices().get(s), g.getVertices().get(e)));
    }
    int s = state.get(state.size() - 1);
    int e = state.get(0);
    result.addEdge(s, e, Generator.distance(g.getVertices().get(s), g.getVertices().get(e)));
    return result;
  }
}
