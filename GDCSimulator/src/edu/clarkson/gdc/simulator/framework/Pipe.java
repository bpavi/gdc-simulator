package edu.clarkson.gdc.simulator.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.lang.Validate;

import edu.clarkson.gdc.simulator.framework.DataMessage.PathNode;
import edu.clarkson.gdc.simulator.framework.ProcessTimeModel.ConstantTimeModel;

/**
 * 
 * @author Hao Jiang
 * @since Simulator 1.0
 * @version 1.0
 */
public class Pipe extends Component {

	protected Queue<DataMessage> requestBuffer;

	protected Queue<DataMessage> responseBuffer;

	protected Node source;

	protected Node destination;

	public Pipe() {
		super();
		this.requestBuffer = new ConcurrentLinkedQueue<DataMessage>();
		this.responseBuffer = new ConcurrentLinkedQueue<DataMessage>();

		setTimeModel(new ConstantTimeModel(1));
	}

	public Pipe(Node source, Node destination) {
		this();
		Validate.notNull(source);
		Validate.notNull(destination);
		// Default Pipe doesn't allow source to be the same as destination
		Validate.isTrue(source != destination);
		this.source = source;
		this.destination = destination;
		// Build Connections
		this.source.addPipe(this);
		this.destination.addPipe(this);

		setId(source.getId() + "-" + destination.getId());
	}

	public Pipe(Node source, Node destination, int latency) {
		this(source, destination);
		setTimeModel(new ConstantTimeModel(latency));
	}

	@Override
	public Clock getClock() {
		if (null != super.getClock())
			return super.getClock();
		if (source.getEnvironment() != null) {
			setEnvironment(source.getEnvironment());
			return getClock();
		}
		if (destination.getEnvironment() != null) {
			setEnvironment(destination.getEnvironment());
			return getClock();
		}
		return null;
	}

	public List<DataMessage> get(Node receiver) {
		List<DataMessage> output = new ArrayList<DataMessage>();
		Queue<DataMessage> buffer = (receiver == destination) ? requestBuffer
				: responseBuffer;
		while (true) {
			if (buffer.isEmpty())
				break;
			DataMessage event = buffer.peek();
			if (event.getTimestamp() <= getClock().getCounter())
				output.add(buffer.poll());
			else {
				break;
			}
		}
		return output;
	}

	// Asynchronous Event Sending
	public void put(Node sender, DataMessage event) {
		event.access(new PathNode(this, getClock().getCounter()
				+ getLatency(null)));
		Queue<DataMessage> buffer = (sender == source) ? requestBuffer
				: responseBuffer;
		buffer.offer(event);
	}

	public Node getSource() {
		return source;
	}

	public Node getDestination() {
		return destination;
	}

	@Override
	public void send() {
	}

	@Override
	public void work() {
	}

	public Node getOpponent(Node me) {
		Validate.notNull(me);
		return getSource().equals(me) ? getDestination() : getSource();
	}

	@Override
	protected long getLatency(Map<Pipe, List<DataMessage>> msgs) {
		if (null == getTimeModel())
			return 1;
		return getTimeModel().latency(this, msgs);
	}
}
