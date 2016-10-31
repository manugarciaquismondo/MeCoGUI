package org.gcn.MeCoGUI.listeners.simulationParametersListeners;

import javax.swing.JTextField;

import org.gcn.MeCoGUI.data.SimulationParameters;


public class CyclesActionListener extends SimulationParametersActionListener {


	public CyclesActionListener(JTextField field,
			SimulationParameters parameters) {
		super(field, parameters);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected
	void setDataElement(int value) {
		parameters.setCycles(value);

	}

}
