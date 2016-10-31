package org.gcn.pLinguaCoreCSVApplication.listeners.changeListeners;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.gcn.pLinguaCoreCSVApplication.OleraceaGUIPanel;
import org.gcn.pLinguaCoreCSVApplication.data.SimulationParameters;
import org.gcn.pLinguaCoreCSVApplication.data.SimulationRoutes;


public class TextFieldsUpdaterListener implements ChangeListener {

	public TextFieldsUpdaterListener(OleraceaGUIPanel panel) {
		super();
		this.panel = panel;
	}
	
	OleraceaGUIPanel panel;
	@Override
	public void stateChanged(ChangeEvent e) {
		SimulationRoutes routes = panel.getSimulationRoutes();
		SimulationParameters parameters = panel.getSimulationParametes();
		panel.createTextFields(routes.getModelRoute(), 
				routes.getDataRoute(), 
				routes.getResultsRoute(), 
				routes.getSimulator(),
				routes.getReportRoute(),
				parameters.getSteps(), 
				parameters.getCycles(),
				parameters.getSimulations()
				);

	}


}
