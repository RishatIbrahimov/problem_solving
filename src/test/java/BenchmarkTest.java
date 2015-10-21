
import com.google.caliper.BeforeExperiment;
import com.google.caliper.Benchmark;
import com.google.caliper.Param;
import com.google.caliper.api.VmOptions;
import com.google.caliper.config.InvalidConfigurationException;
import com.google.caliper.runner.CaliperMain;
import com.google.caliper.runner.InvalidBenchmarkException;
import com.google.caliper.runner.Running;
import com.google.caliper.util.InvalidCommandException;
import ru.kpfu.annealing.SimulatedAnnealing;
import ru.kpfu.generator.Generator;
import ru.kpfu.generator.Graph;
import ru.kpfu.mst.KruskalMST;

import java.io.PrintWriter;

/**
 * @author Rishat Ibrahimov
 */
@VmOptions("-server")
public class BenchmarkTest {
  @Param({"50"/*, "100", "500"*/})
  private int vertexCount;

  private Graph graph;

  @BeforeExperiment
  public void setUp() {
    System.out.println("setUp: " + vertexCount);
    graph = Generator.generate(vertexCount);
  }

  @Benchmark
  public void annealing() {
    System.out.println("annealing: " + vertexCount);
    SimulatedAnnealing.anneal(graph, 0.00001, 10, 100000);
  }

  @Benchmark
  public void mst(long reps) {
    System.out.println("mst: " + vertexCount);
    KruskalMST.findMST(graph);
  }

  public static void main(String[] args) throws InvalidConfigurationException, InvalidCommandException, InvalidBenchmarkException {
    String[] fullArgs = new String[args.length + 1];
    fullArgs[0] = BenchmarkTest.class.getName();
    System.arraycopy(args, 0, fullArgs, 1, args.length);
    CaliperMain.exitlessMain(fullArgs, new PrintWriter(System.out, true), new PrintWriter(System.err, true));
  }

}
