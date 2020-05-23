package org.mac.canvasgraph.ds;



public class DsForkProvider {

	private final DsSwimmer target;
	private final double ratio;

	public DsForkProvider(DsSwimmer target, double ratio) {
		this.target = target;
		this.ratio = ratio;
	}

	public DsSwimmer getTarget() {
		return this.target;
	}

	public double getRatio() {
		return ratio;
	}


}
