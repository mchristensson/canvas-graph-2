package org.mac.canvasgraph.ds;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DsSwimmer {

	private List<DsForkProvider> forks;
	private double value;

	public DsSwimmer(DsNetwork factory) {}
	
	public void fork(DsSwimmer target, double rate) {
		getForks().add(new DsForkProvider(target, rate));
	}
	
	protected List<DsForkProvider> getForks() {
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
