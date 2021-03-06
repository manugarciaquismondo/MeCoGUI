package org.gcn.MeCoGUI.listeners.changeListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.gcn.MeCoGUI.OleraceaGUIPanel;
import org.gcn.MeCoGUI.SimulatorHolder;


public class SimulatorItemActionListener implements ActionListener {

	String simulatorID;
	private OleraceaGUIPanel panel;
	
	public SimulatorItemActionListener(String simulatorID,
			OleraceaGUIPanel panel) {
		this.simulatorID=simulatorID;
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		SimulatorHolder simulatorHolder = panel.getSimulatorHolder();
		simulatorHolder.setSimulator(simulatorID);
		panel.setSimulator(simulatorID);		
	}

}
