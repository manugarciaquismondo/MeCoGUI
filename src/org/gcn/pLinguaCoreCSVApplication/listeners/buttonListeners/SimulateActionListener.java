package org.gcn.pLinguaCoreCSVApplication.listeners.buttonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.gcn.pLinguaCoreCSVApplication.SimulatorHolder;
import org.gcn.pLinguaCoreCSVApplication.data.SimulationParameters;
import org.gcn.pLinguaCoreCSVApplication.data.SimulationRoutes;
import org.gcn.pLinguaCoreCSVApplication.listeners.simulationParametersListeners.CyclesActionListener;
import org.gcn.pLinguaCoreCSVApplication.listeners.simulationParametersListeners.SimulationParametersActionListener;
import org.gcn.pLinguaCoreCSVApplication.listeners.simulationParametersListeners.SimulationsActionListener;
import org.gcn.pLinguaCoreCSVApplication.listeners.simulationParametersListeners.StepsPerCycleActionListener;
import org.gcn.pLinguaCoreCSVApplication.paneStreams.PaneTuple;
import org.gcn.pLinguaCoreCSVApplication.textFields.ParametersTextFields;


public class SimulateActionListener implements ActionListener {

	protected PaneTuple paneTuple;
	protected SimulationRoutes simulationRoutes;
	protected SimulatorHolder simulationHolder;
	protected SimulationParameters simulationParameters;
	protected SimulationParametersActionListener cyclesListener, stepsPerCyclesListener;

	public SimulateActionListener(ParametersTextFields parametersTextFields, PaneTuple paneTuple, SimulationRoutes simulationRoutes, SimulatorHolder simulationHolder, SimulationParameters simulationParameters) {
		this.paneTuple = paneTuple;
		this.simulationRoutes = simulationRoutes;
		this.simulationHolder = simulationHolder;
		this.simulationParameters=simulationParameters;
		this.cyclesListener = new CyclesActionListener(parametersTextFields.getCyclesTextField(), simulationParameters);
		this.stepsPerCyclesListener = new StepsPerCycleActionListener(parametersTextFields.getStepsPerCycleTextField(), simulationParameters);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		initializeSimulationParameters();
		executeAction();
	}

	protected void executeAction() {
		simulationHolder.takeSteps(simulationParameters.getSteps());
	}

	protected void initializeSimulationParameters() {
		cyclesListener.performAction();
		stepsPerCyclesListener.performAction();
	}

}
