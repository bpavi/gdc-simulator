package edu.clarkson.gdc.dashboard.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class DataCenter extends Node {

	private List<Battery> batteries;

	public DataCenter() {
		batteries = new ArrayList<Battery>();
	}

	public List<Battery> getBatteries() {
		return batteries;
	}

	public String getType() {
		return "Data Center";
	}
}