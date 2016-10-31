package org.gcn.MeCoGUI.listeners.changeListeners;

import java.awt.event.ActionListener;

import org.gcn.MeCoGUI.OleraceaGUIPanel;

public interface ActionListenerCreator {
	
	public ActionListener createActionListener(String label, OleraceaGUIPanel panel);

}
