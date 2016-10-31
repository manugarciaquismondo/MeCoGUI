package org.gcn.MeCoGUI.listeners.buttonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.gcn.MeCoGUI.SimulatorHolder;
import org.gcn.MeCoGUI.data.SimulationParameters;
import org.gcn.MeCoGUI.data.SimulationRoutes;
import org.gcn.MeCoGUI.listeners.simulationParametersListeners.CyclesActionListener;
import org.gcn.MeCoGUI.listeners.simulationParametersListeners.SimulationParametersActionListener;
import org.gcn.MeCoGUI.listeners.simulationParametersListeners.SimulationsActionListener;
import org.gcn.MeCoGUI.listeners.simulationParametersListeners.StepsPerCycleActionListener;
import org.gcn.MeCoGUI.paneStreams.PaneTuple;
import org.gcn.MeCoGUI.textFields.ParametersTextFields;


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
