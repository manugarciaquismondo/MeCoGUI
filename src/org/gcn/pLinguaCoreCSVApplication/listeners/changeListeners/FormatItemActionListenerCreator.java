package org.gcn.pLinguaCoreCSVApplication.listeners.changeListeners;

import java.awt.FileDialog;
import java.awt.event.ActionListener;

import org.gcn.pLinguaCoreCSVApplication.OleraceaGUIPanel;

public class FormatItemActionListenerCreator implements ActionListenerCreator {

	@Override
	public ActionListener createActionListener(String label,
			OleraceaGUIPanel panel) {
		// TODO Auto-generated method stub
		return new FormatItemActionListener(label, FileDialog.SAVE, panel);
	}

}
