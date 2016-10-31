package org.gcn.MeCoGUI.listeners.fileDialogListeners;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.gcn.MeCoGUI.OleraceaGUIPanel;
import org.gcn.MeCoGUI.SimulatorHolder;
import org.gcn.MeCoGUI.data.SimulationRoutes;
import org.gcn.MeCoGUI.listeners.fileFilters.CSVFileFilter;


public class ResultsFileDialogActionListener extends RouteDialogActionListener {

	private OleraceaGUIPanel panel;
	

	public ResultsFileDialogActionListener(String label, int mode,
			OleraceaGUIPanel panel) {		
		super(label, mode, panel.getSimulationRoutes());
		this.panel = panel;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected
	void setDataElement(String string) {
		guiData.setResultsRoute(string);
		panel.getSimulatorHolder().writeResults(string);

	}

	@Override
	protected FileFilter createFileFilter() {
		// TODO Auto-generated method stub
		return new CSVFileFilter();
	}

}
