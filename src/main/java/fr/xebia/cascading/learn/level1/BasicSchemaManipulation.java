package fr.xebia.cascading.learn.level1;

import cascading.flow.FlowDef;
import cascading.pipe.Pipe;
import cascading.pipe.assembly.Discard;
import cascading.pipe.assembly.Rename;
import cascading.pipe.assembly.Retain;
import cascading.tap.Tap;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;

/**
 * {@link Tuple} have a soft schema defined by {@link Fields}.
 */
public class BasicSchemaManipulation {

	/**
	 * We want to replicate results of the plain copy (see level 0) but you are
	 * out of luck. The provided source has also a spam "discardme" field. Use
	 * {@link Discard} in order to get rid of it.
	 * 
	 * source field(s) : "discardme","line"
	 * sink field(s) : "line"
	 * 
	 * The result file has the names of the fields in the first line.
	 * 
	 * @see http://docs.cascading.org/cascading/2.1/userguide/html/ch09s03.html
	 */
	public static FlowDef discardField(Tap<?, ?, ?> source, Tap<?, ?, ?> sink) {
		Pipe start = new Pipe("Source Data");
		Pipe discard = new Discard(start, new Fields("discardme"));
		return FlowDef.flowDef().addSource(start, source).addTailSink(discard, sink);
	}
	
	/**
	 * Same logic but we want to {@link Retain} only the "line" field.
	 * 
	 * input field(s) : "donotretainme", "line"
	 * output field(s) : "line"
	 * 
	 * @see http://docs.cascading.org/cascading/2.1/userguide/html/ch09s05.html
	 */
	public static FlowDef retainField(Tap<?, ?, ?> source, Tap<?, ?, ?> sink) {
		Pipe start = new Pipe("Source Data");
		Pipe retainField = new Retain(start, new Fields("line"));
		return FlowDef.flowDef().addSource(start, source).addTailSink(retainField, sink);
	}
	
	/**
	 * Bad luck again, the field is badly named "renameme", {@link Rename} it into "line".
	 * 
	 * source field(s) : "renameme"
	 * sink field(s) : "line"
	 * 
	 * @see http://docs.cascading.org/cascading/2.1/userguide/html/ch09s04.html
	 */
	public static FlowDef renameField(Tap<?, ?, ?> source, Tap<?, ?, ?> sink) {
		Pipe start = new Pipe("Source Data");
		Pipe renameField = new Rename(start, new Fields("renameme"), new Fields("line"));
		return FlowDef.flowDef().addSource(start, source).addTailSink(renameField, sink);
	}

}
