package fr.xebia.cascading.learn.level3;

import cascading.flow.FlowDef;
import cascading.operation.Aggregator;
import cascading.operation.Function;
import cascading.operation.aggregator.Count;
import cascading.pipe.Each;
import cascading.pipe.Every;
import cascading.pipe.GroupBy;
import cascading.pipe.Pipe;
import cascading.pipe.assembly.CountBy;
import cascading.tap.Tap;
import cascading.tuple.Fields;

/**
 * Once each input has been individually curated, it can be needed to aggregate information.
 */
public class Reducing {

  /**
   * {@link GroupBy} "word" and then apply {@link Count}. It should be noted that once grouped, the
   * semantic is different. You will need to use a {@link Every} instead of a {@link Each}. And
   * {@link Count} is an {@link Aggregator} instead of a {@link Function}.
   * 
   * source field(s) : "word" sink field(s) : "word","count"
   * 
   * @see http://docs.cascading.org/cascading/2.1/userguide/html/ch03s03.html#N205C2
   */
  public static FlowDef aggregate(Tap<?, ?, ?> source, Tap<?, ?, ?> sink) {
    Pipe sourcePipe = new Pipe("Source data");
    GroupBy word = new GroupBy(sourcePipe, Fields.ALL);
    Count cWord = new Count();
    Pipe everyWord = new Every(word, cWord);
    return FlowDef.flowDef().addSource(sourcePipe, source).addTailSink(everyWord, sink);
  }

  /**
   * Aggregation should be done as soon as possible and Cascading does have a technique almost
   * similar to map/reduce 'combiner'. Use {@link CountBy} in order to do the same thing as above.
   * It is shorter to write and more efficient.
   * 
   * source field(s) : "word" sink field(s) : "word","count"
   * 
   * @see http://docs.cascading.org/cascading/2.1/userguide/html/ch07s08.html
   */
  public static FlowDef efficientlyAggregate(Tap<?, ?, ?> source, Tap<?, ?, ?> sink) {
    Pipe sourcePipe = new Pipe("SourcePipe");
    Fields word = new Fields("word");
    // todo
    return FlowDef.flowDef().addSource(sourcePipe, source).addTailSink(word, sink);
  }
}
