package org.mac.canvasgraph.ds;

import java.util.ArrayList;
import java.util.List;

public class DsNode {

	private List<DsCurrent> forks;
	private double value;

	public DsNode(DsNetwork factory) {}
	
	public void fork(DsNode target, double rate) {
		getForks().add(new DsCurrent(target, rate));
	}
	
	protected List<DsCurrent> getForks() {
		if (this.forks == null) {
			this.forks = new ArrayList<>();
		}
		return this.forks;
	}

	public void push(double value) {
		this.value += value;
		getForks().forEach(fork -> fork.getTarget().push(value * fork.getRatio()));
	}

	public double getValue() {
		return this.value;
	}

}
