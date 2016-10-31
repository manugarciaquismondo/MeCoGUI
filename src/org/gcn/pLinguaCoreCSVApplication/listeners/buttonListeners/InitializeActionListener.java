package org.gcn.pLinguaCoreCSVApplication.listeners.buttonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.gcn.pLinguaCoreCSVApplication.OleraceaGUIMenuBarContent;
import org.gcn.pLinguaCoreCSVApplication.OleraceaGUIPanel;
import org.gcn.pLinguaCoreCSVApplication.SimulatorHolder;
import org.gcn.pLinguaCoreCSVApplication.csvAdaptor.PsystemCompiler;
import org.gcn.pLinguaCoreCSVApplication.data.SimulationRoutes;
import org.gcn.pLinguaCoreCSVApplication.paneStreams.PaneTuple;
import org.gcn.plinguacore.util.psystem.Psystem;


public class InitializeActionListener implements ActionListener {

	
	PsystemCompiler psystemCompiler;
	OleraceaGUIPanel panel;
	
	public InitializeActionListener(OleraceaGUIPanel panel) {
		psystemCompiler = new PsystemCompiler(panel.getPaneTuple());
		this.panel = panel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		SimulationRoutes simulationRoutes = panel.getSimulationRoutes();
		Psystem psystem = psystemCompiler.parsePsystemAndReportErrors
				(simulationRoutes.getModelRoute(), 
						simulationRoutes.getDataRoute());
		if(psystem!=null){
			int stepsPerCycle=psystemCompiler.getStepsPerCycle();
			panel.getParametersTextFields().setStepsPerCycle(""+stepsPerCycle);		
			setPsystemInSimulatorHolder(psystem);
			
		}
		else
			panel.getParametersTextFields().setStepsPerCycle("000");		

	}

	protected void setPsystemInSimulatorHolder(Psystem psystem) {
		SimulatorHolder simulatorHolder = panel.getSimulatorHolder();
		simulatorHolder.clearPanes();		
		panel.getRouteTextFields().setSimulator("");
		OleraceaGUIMenuBarContent menuBarContent= panel.getMenuBarContent();
		if(psystem!=null){
			simulatorHolder.setDataReader(psystemCompiler.getDataReader());
			simulatorHolder.setPsystem(psystem);		
			menuBarContent.setSimulators(simulatorHolder.getSimulators());			
		}
		else{
			menuBarContent.disableMenus();
		}
	}

}
