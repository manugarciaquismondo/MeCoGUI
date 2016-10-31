package org.gcn.pLinguaCoreCSVApplication.textFields;

import javax.swing.JTextField;

public class ParametersTextFields {
		JTextField cyclesTextField, stepsPerCycleTextField, simulationsTextField;

	public ParametersTextFields(JTextField cyclesTextField,
			JTextField stepsPerCycleTextField, JTextField simulationsTextField) {
		super();
		this.cyclesTextField = cyclesTextField;
		this.stepsPerCycleTextField = stepsPerCycleTextField;
		this.simulationsTextField = simulationsTextField;
		this.stepsPerCycleTextField.setEditable(false);
	}
		
	public JTextField getCyclesTextField() {
		return cyclesTextField;
	}

	public JTextField getStepsPerCycleTextField() {
		return stepsPerCycleTextField;
	}

	public JTextField getSimulationsTextField() {
		return simulationsTextField;
	}

	public void setCycles(String cycles){
		this.cyclesTextField.setText(cycles);
	}
		
	public void setStepsPerCycle(String stepsPerCycle){
		this.stepsPerCycleTextField.setText(stepsPerCycle);
	}
		
	public void setSimulations(String simulations){
		this.simulationsTextField.setText(simulations);
	}

	
}
