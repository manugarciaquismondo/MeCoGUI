package org.gcn.MeCoGUI.listeners.fileDialogListeners;

import org.gcn.MeCoGUI.data.SimulationRoutes;

public abstract class RouteDialogActionListener extends FileDialogActionListener {


	protected SimulationRoutes guiData;
	protected String label;
	int mode;
	
	public RouteDialogActionListener(String label, int mode, SimulationRoutes guiData) {
		this.label =label;  
		this.mode = mode;
		this.guiData = guiData;
		// TODO Auto-generated constructor stub
	}


}
