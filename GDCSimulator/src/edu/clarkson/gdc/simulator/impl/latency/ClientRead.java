package edu.clarkson.gdc.simulator.impl.latency;

import java.util.UUID;

import edu.clarkson.gdc.simulator.framework.DataMessage;

public class ClientRead extends DataMessage {
	public ClientRead() {
		super();
		setSessionId(UUID.randomUUID().toString());
	}
}
