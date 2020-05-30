package org.mac.canvasgraph;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mac.canvasgraph.ds.DsNetworkException;
import org.mac.canvasgraph.svg.DsLayoutHandler;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DsLayoutHandlerTest {

	@InjectMocks
	private DsLayoutHandler dsLayoutHandler;

	/**
	 * 
	 * -  5 --- margin ----
	 * - 10 --- channel ---
	 * -  5 --- margin ----
	 * Sum: 20
	 *  
	 * @throws DsNetworkException
	 */
	@Test
	public void getOutputHeight_oneChannel_correctHeight() throws DsNetworkException {
		int channel1Height = 10;
		int marginY = 5;
		dsLayoutHandler.addChannel(channel1Height).setMarginY(marginY).setChannelPadding(10);
		
		assertThat(dsLayoutHandler.getOutputHeight(), equalTo(20));
	}
	
	/**
	 * 
	 * -  5 --- margin ----
	 * - 10 --- channel ---
	 * - 12 --- padding ---
	 * - 45 --- channel ---
	 * -  5 --- margin ----
	 * Sum: 77
	 *  
	 * @throws DsNetworkException
	 */
	@Test
	public void getOutputHeight_twoChannels_correctHeight() throws DsNetworkException {
		int channel1Height = 10;
		int marginY = 5;
		int channelPadding = 12;
		int channel2Height = 45;
		dsLayoutHandler.addChannel(channel1Height).addChannel(channel2Height ).setMarginY(marginY).setChannelPadding(channelPadding);
		
		assertThat(dsLayoutHandler.getOutputHeight(), equalTo(77));
	}
	
	/**
	 * 
	 * -  5 --- margin ----
	 * - 10 --- channel ---
	 * - 12 --- padding ---
	 * - 45 --- channel ---
	 * - 12 --- padding ---
	 * - 23 --- channel ---
	 * -  5 --- margin ----
	 * Sum: 112
	 *  
	 * @throws DsNetworkException
	 */
	@Test
	public void getOutputHeight_threeChannels_correctHeight() throws DsNetworkException {
		int channel1Height = 10;
		int marginY = 5;
		int channelPadding = 12;
		int channel2Height = 45;
		int channel3Height = 23;
		dsLayoutHandler.addChannel(channel1Height)
		.addChannel(channel3Height)
		.addChannel(channel2Height )
		.setMarginY(marginY).setChannelPadding(channelPadding);
		
		assertThat(dsLayoutHandler.getOutputHeight(), equalTo(112));
	}

}
