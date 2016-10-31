package org.gcn.MeCoGUI.listeners.buttonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.gcn.MeCoGUI.OleraceaGUIPanel;


public class ClearConsolesActionListener implements ActionListener {

	private OleraceaGUIPanel panel;
	
	public ClearConsolesActionListener(OleraceaGUIPanel panel) {
		super();
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		panel.getPaneTuple().clearPanes();

	}

}
