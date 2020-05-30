package org.mac.canvasgraph.svg;

import java.util.Arrays;
import java.util.stream.Stream;

class DsIntCalculator implements DsCalculator<Integer> {

	@Override
	public Integer add(Integer a, Integer b) {
		return a + b;
	}

	@Override
	public Integer multiply(Integer a, int factor) {
		return a * factor;
	}

	@Override
	public Integer multiply(Integer a, Integer factor) {
		return a * factor;
	}

	@Override
	public Integer sum(Integer... value) {
		return Arrays.stream(value).mapToInt(Integer::intValue).sum();
	}

	@Override
	public int castToInt(Integer value) {
		return value;
	}

	@Override
	public Integer sum(Stream<Integer> stream) {
		return stream.mapToInt(Integer::intValue).sum();
	}

	@Override
	public Integer getInstance(double d) {
		return new Double(d).intValue();
	}

}