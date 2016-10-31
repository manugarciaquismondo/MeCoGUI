package org.gcn.MeCoGUI.listeners.fileDialogListeners;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.gcn.MeCoGUI.data.SimulationRoutes;
import org.gcn.MeCoGUI.listeners.fileFilters.DictionaryFileFilter;


public class SetTemporaryFileDialogActionListener extends
		RouteDialogActionListener {

	public SetTemporaryFileDialogActionListener(String label, int mode,
			SimulationRoutes guiData) {
		super(label, mode, guiData);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setDataElement(String string) {
		//uiData.setTemporaryRoute(string);

	}

	@Override
	protected FileFilter createFileFilter() {
		// TODO Auto-generated method stub
		return new DictionaryFileFilter();
	}

}
