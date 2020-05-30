package org.mac.canvasgraph.svg;

import java.util.stream.Stream;

public class DsLayoutHandler<T extends Number> {

	private T marginX;
	private T marginY;
	private T channelPadding;

	private T accumulatedX;
	private T accumulatedY;

	private int channelCount;
	private int unitCount;

	private final DsCalculator<T> calculator;
	private T defaultUnitLength;
	private final T unitPadding;

	public DsLayoutHandler(DsCalculator<T> calculator) {
		this.calculator = calculator;
		marginX = this.calculator.getInstance(0.0);
		marginY = this.calculator.getInstance(0.0);
		accumulatedX = this.calculator.getInstance(0.0);
		accumulatedY = this.calculator.getInstance(0.0);
		channelPadding = this.calculator.getInstance(0.0);
		defaultUnitLength = this.calculator.getInstance(50.0);
		unitPadding = this.calculator.getInstance(0.0);
	}

	public DsLayoutHandler<T> addChannel(T channel1Height) {
		this.accumulatedY = calculator.add(this.accumulatedY, channel1Height);
		this.channelCount++;
		return this;
	}

	public DsLayoutHandler<T> addUnit() {
		this.accumulatedX = calculator.add(this.accumulatedX, this.defaultUnitLength);
		this.unitCount++;
		return this;
	}

	public DsLayoutHandler<T> setMarginY(T marginY) {
		this.marginY = marginY;
		return this;
	}

	public DsLayoutHandler<T> setMarginX(T marginX) {
		this.marginX = marginX;
		return this;
	}

	public DsLayoutHandler<T> setChannelPadding(T channelPadding) {
		this.channelPadding = channelPadding;
		return this;
	}

	public DsLayoutHandler<T> setDefaultUnitLength(T defaultUnitLength) {
		this.defaultUnitLength = defaultUnitLength;
		return this;
	}

	public T getOutputHeight() {
		return calculator.sum(Stream.of(calculator.multiply(marginY, 2),
				calculator.multiply(channelPadding, (channelCount - 1)), accumulatedY));
	}

	public T getOutputWidth() {
		return calculator.sum(Stream.of(
					calculator.multiply(marginX, 2),
					calculator.multiply(unitPadding, unitCount-1), 
					accumulatedX));
	}

	public T getCurrentHeight() {
		return calculator
				.sum(Stream.of(marginY, calculator.multiply(channelPadding, (channelCount - 1)), accumulatedY));
	}

	public T getCurrentWidth() {
		return calculator.sum(Stream.of(marginX, calculator.multiply(unitPadding, (unitCount - 1)), calculator.multiply(defaultUnitLength, -1), accumulatedX));
	}

	public int getMarginXAsInt() {
		return calculator.castToInt(marginX);
	}

	public int getHeightAsInt() {
		return calculator.castToInt(getCurrentHeight());
	}

	public int getWidthAsInt() {
		return calculator.castToInt(getCurrentWidth());
	}

	public int getDefaultUnitLength() {
		return calculator.castToInt(this.defaultUnitLength);
	}

	@Override
	public String toString() {
		return String.format(
				"DsLayoutHandler [marginX=%s, marginY=%s, channelPadding=%s, accumulatedX=%s, accumulatedY=%s, channelCount=%s, unitCount=%s, calculator=%s, defaultUnitLength=%s, unitPadding=%s, getOutputHeight()=%s, getOutputWidth()=%s, getHeight()=%s, getWidth()=%s]",
				marginX, marginY, channelPadding, accumulatedX, accumulatedY, channelCount, unitCount, calculator,
				defaultUnitLength, unitPadding, getOutputHeight(), getOutputWidth(), getCurrentHeight(), getCurrentWidth());
	}

}
