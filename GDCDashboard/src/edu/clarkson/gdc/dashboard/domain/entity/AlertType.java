package edu.clarkson.gdc.dashboard.domain.entity;

public enum AlertType {

	BTY_LOW_LEVEL(AlertLevel.SEVERE);

	private AlertLevel level;

	AlertType(AlertLevel level) {
		this.level = level;
	}

	public AlertLevel level() {
		return this.level;
	}
}