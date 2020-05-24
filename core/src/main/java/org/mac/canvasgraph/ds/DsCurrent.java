package org.mac.canvasgraph.ds;



public class DsCurrent {

	private final DsNode target;
	private final double ratio;

	public DsCurrent(DsNode target, double ratio) {
		this.target = target;
		this.ratio = ratio;
	}

	public DsNode getTarget() {
		return this.target;
	}

	public double getRatio() {
		return ratio;
	}


}
