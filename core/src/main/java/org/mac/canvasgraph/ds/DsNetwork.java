package org.mac.canvasgraph.ds;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class DsNetwork {

	private final List<DsNode> dsSwimmers;
	private double inputSum = 0;
	private final List<DsNode> startNodes;

	private DsNetwork() {
		this.dsSwimmers = new ArrayList<>();
		this.startNodes = new ArrayList<>();
	}

	public DsNetwork push(DsStartNode dsSwimmer, double inputValue) throws DsNetworkException {
		if (!containsStartNode(dsSwimmer)) {
			throw new DsNetworkException("Element is not part of the model");
		} else {
			dsSwimmer.push(inputValue);
			inputSum += inputValue;
		}
		return this;
	}

	public static DsNetwork getInstance() {
		return new DsNetwork();
	}

	public boolean validate(DsNetworkValidator validator) {
		return validator.validate(this);
	}

	public DsNode getSwimmer(DsNode swimmer) {
		int i = this.dsSwimmers.indexOf(swimmer);
		if (i < 0) {
			return null;
		} else {
			return this.dsSwimmers.get(i);
		}
	}

	private boolean containsStartNode(DsStartNode dsSwimmer) {
		return !(this.startNodes.indexOf(dsSwimmer) < 0);
	}

	public Double getOutputSum() {
		return this.dsSwimmers.stream().filter(element -> element.getForks().isEmpty())
				.map(element -> element.getValue()).reduce(0.0, Double::sum);
	}

	public double getInputSum() {
		return inputSum;
	}

	public DsStartNode createStartNode() {
		DsStartNode dsSwimmer = new DsStartNode(this);
		this.dsSwimmers.add(dsSwimmer);
		this.startNodes.add(dsSwimmer);
		return dsSwimmer;
	}

	public DsNode createNode() {
		DsNode dsSwimmer = new DsNode(this);
		this.dsSwimmers.add(dsSwimmer);
		return dsSwimmer;
	}

	public void render(Writer writer, DsOutputHandler outputHandler) throws IOException {
		outputHandler.write(writer, this.startNodes, this.dsSwimmers, inputSum);
	}
}
