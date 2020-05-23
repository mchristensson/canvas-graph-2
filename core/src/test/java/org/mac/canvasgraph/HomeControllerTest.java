package org.mac.canvasgraph;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mac.canvasgraph.ds.DsNetwork;
import org.mac.canvasgraph.ds.DsNetworkException;
import org.mac.canvasgraph.ds.DsNetworkValidator;
import org.mac.canvasgraph.ds.DsStartSwimmer;
import org.mac.canvasgraph.ds.DsSwimmer;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

	@InjectMocks
	private HomeController homeController;

	@Test
	public void homePage__expectHomeString() {
		Model model = mock(Model.class);
		String response = homeController.homePage(model);
		assertThat(response, equalTo("home"));
	}

	@Test(expected = Exception.class)
	public void errorTrigger__expectException() throws Exception {
		Model model = mock(Model.class);
		homeController.errorTrigger(model);
		fail("Exception förväntades");
	}

	@Test
	public void getImageWithMediaTypeCanada_expectData() throws Exception {
		byte[] response = homeController.getImageWithMediaTypeCanada();
		assertThat(response, notNullValue());
		assertThat(response.length, equalTo(10708));
	}


	@Test
	public void model_swimmersAdded_noException() throws DsNetworkException {
		DsNetwork network =	DsNetwork.getInstance();
		DsStartSwimmer swimmerA = network.getStartNode();
		DsStartSwimmer swimmerB = network.getStartNode();

		DsNetworkValidator validator = dsNetwork -> {
			assertThat("Simmare A saknas", dsNetwork.getSwimmer(swimmerA), notNullValue());
			assertThat("Simmare B saknas", dsNetwork.getSwimmer(swimmerB), notNullValue());
			return true;
		};

		int inputStregthA = 10;
		int inputStregthB = 20;

		// Run
		network.push(swimmerA, inputStregthA).push(swimmerB, inputStregthB)
				.run().validate(validator);
	}

	@Test
	public void model_assertValuesTransferredNoRatios_noException() throws DsNetworkException {
		DsNetwork network =	DsNetwork.getInstance();
		DsStartSwimmer swimmerA = network.getStartNode();
		DsSwimmer swimmerAGoal = network.getNode();
		swimmerA.fork(swimmerAGoal, 1);

		DsStartSwimmer swimmerB = network.getStartNode();
		DsSwimmer swimmerBGoal = network.getNode();
		swimmerB.fork(swimmerBGoal, 1);

		DsNetworkValidator validator = dsNetwork -> {
			assertThat("Simmare A saknas", dsNetwork.getSwimmer(swimmerA), notNullValue());
			assertThat("Simmare B-goal saknas", dsNetwork.getSwimmer(swimmerAGoal), notNullValue());
			assertThat("Simmare A saknas", dsNetwork.getSwimmer(swimmerA), notNullValue());
			assertThat("Simmare B-goal saknas", dsNetwork.getSwimmer(swimmerBGoal), notNullValue());
			
			assertThat("Simmare A har fel värde", dsNetwork.getSwimmer(swimmerA).getValue(), equalTo(10.0));
			assertThat("Simmare A-goal har fel värde", dsNetwork.getSwimmer(swimmerAGoal).getValue(), equalTo(10.0));
			
			assertThat("Simmare B har fel värde", dsNetwork.getSwimmer(swimmerB).getValue(), equalTo(20.0));
			assertThat("Simmare B-goal har fel värde", dsNetwork.getSwimmer(swimmerBGoal).getValue(), equalTo(20.0));
			return true;
		};

		int inputStregthA = 10;
		int inputStregthB = 20;

		// Run
		network
		.push(swimmerA, inputStregthA)
		.push(swimmerB, inputStregthB)
				.run().validate(validator);
	}
	
	@Test
	public void model_assertValuesTransferredWithRatios_noException() throws DsNetworkException {
		DsNetwork network =	DsNetwork.getInstance();
		DsStartSwimmer swimmerA = network.getStartNode();
		DsSwimmer swimmerAGoal = network.getNode();
		swimmerA.fork(swimmerAGoal, 0.8);

		DsStartSwimmer swimmerB = network.getStartNode();
		DsSwimmer swimmerBGoal = network.getNode();
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
		.push(swimmerB, inputStregthB)
				.run().validate(validator);
	}
	
	@Test
	public void model_assertValuesTransferredWithRatios2_() throws DsNetworkException {
		DsNetwork network =	DsNetwork.getInstance();
		DsStartSwimmer swimmerA = network.getStartNode();
		DsSwimmer swimmerAGoal = network.getNode();
		swimmerA.fork(swimmerAGoal, 0.8);

		DsStartSwimmer swimmerB = network.getStartNode();
		DsSwimmer swimmerBGoal = network.getNode();
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
		.push(swimmerB, inputStregthB)
				.run().validate(validator);
	}

}
