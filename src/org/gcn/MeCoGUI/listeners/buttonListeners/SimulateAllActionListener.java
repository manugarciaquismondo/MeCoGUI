package org.gcn.MeCoGUI.listeners.buttonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.gcn.MeCoGUI.SimulatorHolder;
import org.gcn.MeCoGUI.data.SimulationParameters;
import org.gcn.MeCoGUI.data.SimulationRoutes;
import org.gcn.MeCoGUI.listeners.simulationParametersListeners.SimulationParametersActionListener;
import org.gcn.MeCoGUI.listeners.simulationParametersListeners.SimulationsActionListener;
import org.gcn.MeCoGUI.paneStreams.PaneTuple;
import org.gcn.MeCoGUI.textFields.ParametersTextFields;


public class SimulateAllActionListener extends SimulateActionListener {
	
	protected SimulationParametersActionListener simulationsListener;

	public SimulateAllActionListener(ParametersTextFields parametersTextFields,
			PaneTuple paneTuple, SimulationRoutes simulationRoutes,
			SimulatorHolder simulationHolder,
			SimulationParameters simulationParameters) {
		super(parametersTextFields, paneTuple, simulationRoutes, simulationHolder,
				simulationParameters);
		this.simulationsListener = new SimulationsActionListener(parametersTextFields.getSimulationsTextField(), simulationParameters);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initializeSimulationParameters() {
		super.initializeSimulationParameters();
		simulationsListener.performAction();		
	}

	@Override
	protected void executeAction() {
		// TODO Auto-generated method stub
		simulationHolder.performSimulations(simulationParameters.getSteps(), simulationParameters.getSimulations());
	}
	


}
