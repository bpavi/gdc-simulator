package edu.clarkson.gdc.workflow.param;

public class InputParam implements Param {

	protected int index;

	public InputParam(int index) {
		this.index = index;
	}

	@Override
	public Object getValue(Object[] input) {
		return input[index];
	}

}