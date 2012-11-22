package edu.clarkson.gdc.simulator.framework;

/**
 * 
 * @author Hao Jiang
 * @since Simulator 1.0
 * @version 1.0
 */
public abstract class Component implements Stepper {

	private Clock clock;

	private long latency = 1;

	private String id;

	public Component() {
		setClock(Clock.getInstance());
		Clock.getInstance().register(this);
	}

	public Component(String id) {
		this();
		setId(id);
	}

	public Clock getClock() {
		return clock;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}

	public long getLatency() {
		return latency;
	}

	public void setLatency(long latency) {
		this.latency = latency;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
