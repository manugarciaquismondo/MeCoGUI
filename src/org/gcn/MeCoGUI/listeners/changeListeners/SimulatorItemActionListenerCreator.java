package org.gcn.MeCoGUI.listeners.changeListeners;

import java.awt.event.ActionListener;

import org.gcn.MeCoGUI.OleraceaGUIPanel;

public class SimulatorItemActionListenerCreator implements
		ActionListenerCreator {

	@Override
	public ActionListener createActionListener(String label, OleraceaGUIPanel panel) {
		// TODO Auto-generated method stub
		return new SimulatorItemActionListener(label, panel);
	}

}
