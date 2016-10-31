package org.gcn.pLinguaCoreCSVApplication.listeners.changeListeners;

import java.awt.event.ActionListener;

import org.gcn.pLinguaCoreCSVApplication.OleraceaGUIPanel;

public interface ActionListenerCreator {
	
	public ActionListener createActionListener(String label, OleraceaGUIPanel panel);

}
