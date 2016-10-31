package org.gcn.pLinguaCoreCSVApplication.data;

import org.gcn.pLinguaCoreCSVApplication.OleraceaGUIPanel;

public class SimulationParameters {
	
	private int cycles;
	private int stepsPerCycle;
	private int simulations;
	private OleraceaGUIPanel panel;
	
	
	public SimulationParameters(OleraceaGUIPanel panel) {
		super();
		this.panel=panel;
		cycles=0;
		stepsPerCycle=0;
		simulations=0;
		
		// TODO Auto-generated constructor stub
	}
	public int getCycles() {
		return cycles;
	}
	public void setCycles(int cycles) {
		this.cycles = cycles;
	}
	public int getStepsPerCycle() {
		return stepsPerCycle;
	}
	public void setStepsPerCycle(int stepsPerCycle) {
		this.stepsPerCycle = stepsPerCycle;
	}
	public int getSimulations() {
		return simulations;
	}
	public void setSimulations(int simulations) {
		this.simulations = simulations;
	}
	
	public int getSteps(){
		return getStepsPerCycle()*getCycles();
	}

}
