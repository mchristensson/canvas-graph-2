package org.mac.canvasgraph;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mac.canvasgraph.ds.DsNetwork;
import org.mac.canvasgraph.ds.DsNetworkException;
import org.mac.canvasgraph.ds.DsNetworkValidator;
import org.mac.canvasgraph.ds.DsStartNode;
import org.mac.canvasgraph.ds.DsOutputHandler;
import org.mac.canvasgraph.svg.DsSvgOutputHandler;
import org.mac.canvasgraph.svg.DsSvgOutputHandlerExample;
import org.mac.canvasgraph.ds.DsNode;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

@RunWith(MockitoJUnitRunner.class)
public class DsNetworkWriterTest {

	@InjectMocks
	private HomeController homeController;

	@Test
	public void homePage__expectHomeString() throws DsNetworkException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Writer writer = new OutputStreamWriter(bos, StandardCharsets.UTF_8);
		
		DsNetwork network = getTestDsNetwork();
		DsOutputHandler outputHandler = new DsSvgOutputHandlerExample();
		try {
			network.render(writer, outputHandler);
			writer.close();
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
		byte[] arr = bos.toByteArray();
		System.out.println(new String(arr, StandardCharsets.UTF_8));
		assertThat(arr.length, equalTo(135));
	}

	
	

	/**
	 * TestHelper method
	 * @return
	 * @throws DsNetworkException
	 */
	private DsNetwork getTestDsNetwork() throws DsNetworkException {
		DsNetwork network =	DsNetwork.getInstance();
		DsStartNode swimmerA = network.createStartNode();
		DsNode swimmerAGoal = network.createNode();
		swimmerA.fork(swimmerAGoal, 0.8);

		DsStartNode swimmerB = network.createStartNode();
		DsNode swimmerBGoal = network.createNode();
		swimmerB.fork(swimmerBGoal, 1);
		swimmerA.fork(swimmerBGoal, 0.2);

		DsNetworkValidator validator = dsNetwork -> {
			assertThat("Simmare A saknas", dsNetwork.getSwimmer(swimmerA), notNullValue());
			assertThat("Simmare B-goal saknas", dsNetwork.getSwimmer(swimmerAGoal), notNullValue());
			assertThat("Simmare A saknas", dsNetwork.getSwimmer(swimmerA), notNullValue());
			assertThat("Simmare B-goal saknas", dsNetwork.getSwimmer(swimmerBGoal), notNullValue());
			
			assertThat("Simmare A har fel värde", dsNetwork.getSwimmer(swimmerA).getValue(), equalTo(10.0));
			assertThat("Simmare A-goal har fel värde", dsNetwork.getSwimmer(swimmerAGoal).getValue(), equalTo(8.0));
			
			assertThat("Simmare B har fel värde", dsNetwork.getSwimmer(swimmerB).getValue(), equalTo(20.0));
			assertThat("Simmare B-goal har fel värde", dsNetwork.getSwimmer(swimmerBGoal).getValue(), equalTo(22.0));
			
			assertThat("Input har fel värde", dsNetwork.getInputSum(), equalTo(30.0));
			assertThat("Output har inte samma värde som input. Data förloras någonstans", dsNetwork.getOutputSum(), equalTo(dsNetwork.getInputSum()));
			assertThat("Output har fel värde", dsNetwork.getOutputSum(), equalTo(30.0));
			
			return true;
		};

		int inputStregthA = 10;
		int inputStregthB = 20;

		// Run
		network
		.push(swimmerA, inputStregthA)
		.push(swimmerB, inputStregthB).validate(validator);
		
		return network;
	}

}
