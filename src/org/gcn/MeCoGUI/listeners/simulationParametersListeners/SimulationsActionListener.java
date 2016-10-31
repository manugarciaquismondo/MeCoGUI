package org.gcn.MeCoGUI.listeners.simulationParametersListeners;

import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import org.gcn.MeCoGUI.data.SimulationParameters;


public class SimulationsActionListener extends
		SimulationParametersActionListener {


	public SimulationsActionListener(JTextField field,
			SimulationParameters parameters) {
		super(field, parameters);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setDataElement(int value) {
		parameters.setSimulations(value);

	}



}
