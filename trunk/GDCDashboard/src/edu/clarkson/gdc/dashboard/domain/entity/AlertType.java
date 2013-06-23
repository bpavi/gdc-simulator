package edu.clarkson.gdc.dashboard.domain.entity;

public enum AlertType {

	POWER_EXHAUST(AlertLevel.SEVERE);

	private AlertLevel level;

	AlertType(AlertLevel level) {
		this.level = level;
	}

	public AlertLevel level() {
		return this.level;
	}
}
