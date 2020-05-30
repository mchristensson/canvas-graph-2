package org.mac.canvasgraph.svg;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Stream;

public class DsDoubleCalculator implements DsCalculator<Double> {

	@Override
	public Double add(Double a, Double b) {
		return BigDecimal.valueOf(a).add(BigDecimal.valueOf(b)).doubleValue();
	}
	
	@Override
	public Double multiply(Double a, int factor) {
		return BigDecimal.valueOf(a).multiply(BigDecimal.valueOf(factor)).doubleValue();
	}

	@Override
	public Double multiply(Double a, Double factor) {
		return BigDecimal.valueOf(a).multiply(BigDecimal.valueOf(factor)).doubleValue();
	}

	@Override
	public Double sum(Double... value) {
		return Arrays.stream(value).map(v -> BigDecimal.valueOf(v)).reduce(BigDecimal.ZERO, BigDecimal::add)
				.doubleValue();
	}
	
	@Override
	public Double sum(Stream<Double> stream) {
		return stream.map(v -> BigDecimal.valueOf(v)).reduce(BigDecimal.ZERO, BigDecimal::add)
				.doubleValue();
	}

	@Override
	public int castToInt(Double value) {
		return value.intValue();
	}

	@Override
	public Double getInstance(double d) {
		return d;
	}

}
