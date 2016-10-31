package org.gcn.MeCoGUI.data;

import org.gcn.MeCoGUI.OleraceaGUIPanel;

public class SimulationRoutes {
	

	String modelRoute;
	String dataRoute;
	String resultsRoute;
	String reportRoute;
	String simulator;
	private OleraceaGUIPanel panel;
	

	public SimulationRoutes(OleraceaGUIPanel panel) {
		this.panel = panel;
		
	}
	
	public String getModelRoute() {
		return modelRoute;
	}


	public void setModelRoute(String modelRoute) {
		this.modelRoute = modelRoute;
		panel.getRouteTextFields().setModel(modelRoute);
	}


	public String getDataRoute() {
		return dataRoute;
	}


	public void setDataRoute(String dataRoute) {
		this.dataRoute = dataRoute;
		panel.getRouteTextFields().setData(dataRoute);
	}


	public String getResultsRoute() {
		return resultsRoute;
	}


	public void setResultsRoute(String resultsRoute) {
		this.resultsRoute = resultsRoute;
		panel.getRouteTextFields().setResults(resultsRoute);
	}
	
	
	public void setSimulator(String simulator){
		this.simulator=simulator;
		panel.getRouteTextFields().setSimulator(simulator);
	}

	public String getSimulator() {
		// TODO Auto-generated method stub
		return simulator;
	}

	public void setReportRoute(String reportRoute) {
		this.reportRoute=reportRoute;
		panel.getRouteTextFields().setReport(reportRoute);
		
	}

	public String getReportRoute() {
		return reportRoute;
	}




}