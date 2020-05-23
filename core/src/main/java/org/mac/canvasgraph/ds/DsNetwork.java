package org.mac.canvasgraph.ds;

import java.util.ArrayList;
import java.util.List;

public class DsNetwork {

	private final List<DsSwimmer> dsSwimmers;
	private double inputSum = 0;

	private DsNetwork() {
		this.dsSwimmers = new ArrayList<>();
	}

	public DsNetwork push(DsStartSwimmer dsSwimmer, double inputValue) throws DsNetworkException {
		if (!contains(dsSwimmer)) {
			throw new DsNetworkException("Element is not part of the model");
		} else {
			dsSwimmer.push(inputValue);
			inputSum += inputValue;
		}
		return this;
	}

	public DsNetwork run() {
		return this;
	}

	public static DsNetwork getInstance() {
		return new DsNetwork();
	}

	public boolean validate(DsNetworkValidator validator) {
		return validator.validate(this);
	}

	public DsSwimmer getSwimmer(DsSwimmer swimmer) {
		int i = this.dsSwimmers.indexOf(swimmer);
		if (i < 0) {
			return null;
		} else {
			return this.dsSwimmers.get(i);
		}
	}

	private boolean contains(DsSwimmer dsSwimmer) {
		return !(this.dsSwimmers.indexOf(dsSwimmer) < 0);
	}

	public Double getOutputSum() {
		return this.dsSwimmers.stream().filter(element -> element.getForks().isEmpty())
				.map(element -> element.getValue()).reduce(0.0, Double::sum);
	}

	public double getInputSum() {
		return inputSum;
	}

	public DsStartSwimmer getStartNode() {
		DsStartSwimmer dsSwimmer = new DsStartSwimmer(this);
		this.dsSwimmers.add(dsSwimmer);
		return dsSwimmer;
	}

	public DsSwimmer getNode() {
		DsSwimmer dsSwimmer = new DsSwimmer(this);
		this.dsSwimmers.add(dsSwimmer);
		return dsSwimmer;
	}

}
