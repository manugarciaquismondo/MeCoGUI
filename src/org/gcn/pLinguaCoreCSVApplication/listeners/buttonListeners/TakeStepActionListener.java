package org.gcn.pLinguaCoreCSVApplication.listeners.buttonListeners;

import org.gcn.pLinguaCoreCSVApplication.SimulatorHolder;
import org.gcn.pLinguaCoreCSVApplication.data.SimulationParameters;
import org.gcn.pLinguaCoreCSVApplication.data.SimulationRoutes;
import org.gcn.pLinguaCoreCSVApplication.paneStreams.PaneTuple;
import org.gcn.pLinguaCoreCSVApplication.textFields.ParametersTextFields;

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
