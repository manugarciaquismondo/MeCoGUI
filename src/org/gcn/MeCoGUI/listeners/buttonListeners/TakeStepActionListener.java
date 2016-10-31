package org.gcn.MeCoGUI.listeners.buttonListeners;

import org.gcn.MeCoGUI.SimulatorHolder;
import org.gcn.MeCoGUI.data.SimulationParameters;
import org.gcn.MeCoGUI.data.SimulationRoutes;
import org.gcn.MeCoGUI.paneStreams.PaneTuple;
import org.gcn.MeCoGUI.textFields.ParametersTextFields;

public class TakeStepActionListener extends SimulateActionListener {

	public TakeStepActionListener(ParametersTextFields parametersTextFields,
			PaneTuple paneTuple, SimulationRoutes simulationRoutes,
			SimulatorHolder simulationHolder,
			SimulationParameters simulationParameters) {
		super(parametersTextFields, paneTuple, simulationRoutes, simulationHolder,
				simulationParameters);
		// TODO Auto-generated constructor stub
	}
	
	protected void executeAction() {
		simulationHolder.takeSteps(1);
	}

}
