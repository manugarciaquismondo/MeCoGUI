package org.gcn.pLinguaCoreCSVApplication.listeners.simulationParametersListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import org.gcn.pLinguaCoreCSVApplication.data.SimulationParameters;
import org.gcn.pLinguaCoreCSVApplication.listeners.fileDialogListeners.FileDialogActionListener;


public abstract class SimulationParametersActionListener{

	SimulationParameters parameters;
	JTextField field;
	
	public SimulationParametersActionListener(JTextField field, SimulationParameters parameters) {
		super();
		this.parameters = parameters;
		this.field = field;
		// TODO Auto-generated constructor stub
	}
	
	public void performAction() {
		try{
			int value = Integer.parseInt(field.getText().trim());
			if(value>0)
				setDataElement(value);
			else
				field.setText("NaN");
		}
		catch(NumberFormatException exception){
			field.setText("NaN");
		}
		
	}


	protected abstract void setDataElement(int value);

	


}
