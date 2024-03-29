package edu.clarkson.gdc.simulator.module.network.impl;

import java.util.Random;

public class LatencyCable implements Cable {

	private int minLatency;

	private int maxLatency;

	private transient Random random;

	public LatencyCable(int min, int max) {
		minLatency = min;
		maxLatency = max;
	}

	protected Random getRandom() {
		if (null == random)
			random = new Random(System.currentTimeMillis());
		return random;
	}

	@Override
	public int getLatency() {
		return getRandom().nextInt(maxLatency - minLatency) + minLatency;
	}

	public int getMinLatency() {
		return minLatency;
	}

	public void setMinLatency(int minLatency) {
		this.minLatency = minLatency;
	}

	public int getMaxLatency() {
		return maxLatency;
	}

	public void setMaxLatency(int maxLatency) {
		this.maxLatency = maxLatency;
	}

}
