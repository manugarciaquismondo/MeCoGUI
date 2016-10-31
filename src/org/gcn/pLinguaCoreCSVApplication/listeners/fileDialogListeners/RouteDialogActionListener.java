package org.gcn.pLinguaCoreCSVApplication.listeners.fileDialogListeners;

import org.gcn.pLinguaCoreCSVApplication.data.SimulationRoutes;

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
