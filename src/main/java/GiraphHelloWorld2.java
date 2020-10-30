import org.apache.giraph.edge.Edge;
import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;

public class GiraphHelloWorld2 extends
    // note a change in type variables to match new input
    BasicComputation<Text, DoubleWritable, DoubleWritable, NullWritable> {
    @Override
    public void compute(Vertex<Text,DoubleWritable,DoubleWritable> vertex,
                        Iterable<NullWritable> messages) {
        System.out.print("Hello world from the: " +
                vertex.getId().toString() + " who is following:");
        for (Edge<Text, DoubleWritable> e : vertex.getEdges()) {
            System.out.print(" " + e.getTargetVertexId());
        }
        System.out.println("");
        vertex.voteToHalt();
    }
}

/*
Command to execute the program:
giraph target/*.jar GiraphHelloWorld2 -vip src/main/resources/2 \
–vif org.apache.giraph.io.formats.TextDoubleDoubleAdjacencyListVertexInputFormat \
-w 1 -ca giraph.SplitMasterWorker=false,giraph.logLevel=error
 */