import org.apache.giraph.edge.Edge;
import org.apache.giraph.GiraphRunner;
import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.util.ToolRunner;

// Giraph applications are custom classes that typically use
// BasicComputation class for all their defaults... except for
// the compute method that has to be defined

public class GiraphHelloWorld extends
        BasicComputation<IntWritable, IntWritable,
                NullWritable, NullWritable> {
    @Override
    public void compute(Vertex<IntWritable,
            IntWritable, NullWritable> vertex,
                        Iterable<NullWritable> messages) {
        System.out.print("Hello world from the: " +
                vertex.getId().toString() + " who is following:");
        // iterating over vertex's neighbors
        for (Edge<IntWritable, NullWritable> e : vertex.getEdges()) {
            System.out.print(" " + e.getTargetVertexId());
        }
        System.out.println("");
        // signaling the end of the current BSP computation for the current vertex
        vertex.voteToHalt();
    }
    //Main method is not necessary, its just a demo to show in case of running to project without jar
    public static void main(String[] args) throws Exception {
        System.exit(ToolRunner.run(new GiraphRunner(), args));
    }
}

/*
Command to execute the program:
giraph target/*.jar GiraphHelloWorld -vip src/main/resources/1 \
-vif org.apache.giraph.io.formats.IntIntNullTextInputFormat \
-w 1 -ca giraph.SplitMasterWorker=false,giraph.logLevel=error
 */