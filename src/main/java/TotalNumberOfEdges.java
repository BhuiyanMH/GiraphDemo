import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.LongWritable;

public class TotalNumberOfEdges extends
        BasicComputation<Text, DoubleWritable, DoubleWritable, NullWritable> {
    @Override
    public void compute(Vertex<Text, DoubleWritable, DoubleWritable> vertex,
                        Iterable<NullWritable> messages) {
        aggregate(TotalNumberOfEdgesMC.ID, new LongWritable(vertex.getNumEdges()));
        vertex.voteToHalt();
    }
}
