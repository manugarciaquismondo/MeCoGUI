package org.gcn.MeCoGUI.listeners.fileDialogListeners;

import javax.swing.filechooser.FileFilter;

import org.gcn.MeCoGUI.data.SimulationRoutes;
import org.gcn.MeCoGUI.listeners.fileFilters.CSVFileFilter;

public class SetReportDialogActionListener extends RouteDialogActionListener {

	public SetReportDialogActionListener(String label, int mode,
			SimulationRoutes guiData) {
		super(label, mode, guiData);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setDataElement(String string) {
		// TODO Auto-generated method stub
		guiData.setReportRoute(string);
	}

	@Override
	protected FileFilter createFileFilter() {
		// TODO Auto-generated method stub
		return new CSVFileFilter();
	}

}
