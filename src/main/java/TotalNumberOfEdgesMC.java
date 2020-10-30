import org.apache.giraph.aggregators.LongSumAggregator;
import org.apache.giraph.master.DefaultMasterCompute;

public class TotalNumberOfEdgesMC extends DefaultMasterCompute { // master compute
// implementations are usually extending the DefaultMasterCompute abstract class
// just the same way that our basic BSP compute implementations extended
// BasicComputation. Unlike BasicComputation you have to provide implementations
// for two methods: compute() and initialize()
    public static final String ID = "TotalNumberOfEdgesAggregator"; // all registered
// aggregators are referenced by unique (within a given application) but arbitrary
// strings
@Override
public void compute() {
// this is a global master compute method that will be executed once
// before every superstep. In our case we're simply outputting the
// running tally of total number of edges in a graph
    System.out.println("Total number of edges at superstep " +
            getSuperstep() + " is " +
            getAggregatedValue(ID));
}
    @Override
// this method gets called during overall initialization of the Giraph
// BSP machinery. It is an ideal place to register all required
// aggregators.
    public void initialize() throws InstantiationException,
            IllegalAccessException {
        registerAggregator(ID, LongSumAggregator.class); // this is how we associate
// an aggregator ID with a particular implementation of an aggregator:
// in our case we are using a built-in LongSumAggregator that simply
// sums all of the values sent to it
    }
}

/*
Command to run the application:
#empty the output folder
$ rm -rf aggregatorValues*

# Execute the program
# note: master compute (mc), aggregate writer (aw), Custom argument (ca) record frequency: each super-step
$ giraph target/*.jar TotalNumberOfEdges -mc TotalNumberOfEdgesMC \
-aw org.apache.giraph.aggregators.TextAggregatorWriter \
-ca giraph.textAggregatorWriter.frequency=1 \
-vip src/main/resources/2 \
-vif org.apache.giraph.io.formats.TextDoubleDoubleAdjacencyListVertexInputFormat \
-w 1 -ca giraph.SplitMasterWorker=false,giraph.logLevel=error

#print the output in console
$ cat aggregatorValues*
 */