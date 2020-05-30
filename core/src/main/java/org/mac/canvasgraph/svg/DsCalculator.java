package org.mac.canvasgraph.svg;

import java.util.stream.Stream;

interface DsCalculator<T extends Number> {

	T add(T a, T b);

	T multiply(T value, int factor);

	T multiply(T value, T factor);

	@SuppressWarnings("unchecked")
	T sum(T... value);

	int castToInt(T value);

	T sum(Stream<T> stream);

	T getInstance(double d);


}