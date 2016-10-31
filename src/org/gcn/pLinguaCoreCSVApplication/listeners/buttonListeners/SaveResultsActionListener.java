package org.gcn.pLinguaCoreCSVApplication.listeners.buttonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.gcn.pLinguaCoreCSVApplication.SimulatorHolder;
import org.gcn.pLinguaCoreCSVApplication.data.SimulationRoutes;
import org.gcn.pLinguaCoreCSVApplication.paneStreams.PaneTuple;


public class SaveResultsActionListener implements ActionListener {
	
	PaneTuple paneTuple;
	SimulationRoutes simulationRoutes;
	SimulatorHolder simulationHolder;

	public SaveResultsActionListener(PaneTuple paneTuple, SimulationRoutes simulationRoutes, SimulatorHolder simulationHolder) {
		this.paneTuple = paneTuple;
		this.simulationRoutes = simulationRoutes;
		this.simulationHolder = simulationHolder;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String destinationRoute = simulationRoutes.getResultsRoute();
		if(destinationRoute==null||destinationRoute.isEmpty())
			paneTuple.writeError("Destination route is empty\n");
		simulationHolder.writeResults(destinationRoute);
	}

}
