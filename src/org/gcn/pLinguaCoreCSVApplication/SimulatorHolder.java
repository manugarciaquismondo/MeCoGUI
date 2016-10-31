package org.gcn.pLinguaCoreCSVApplication;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;












import org.gcn.pLinguaCoreCSVApplication.writers.SimulatorResultsWriterProvider;
import org.gcn.pLinguaCoreCSVApplication.writers.SimulatorResultsWriter;
import org.gcn.pLinguaCoreCSVApplication.writers.WrittenObjectReader;
import org.gcn.pLinguaCoreCSVApplication.csvAdaptor.DataReader;
import org.gcn.pLinguaCoreCSVApplication.paneStreams.PaneTuple;
import org.gcn.plinguacore.simulator.ISimulator;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.factory.AbstractPsystemFactory;
import org.gcn.plinguacore.util.psystem.factory.IModelsClassProvider;

public class SimulatorHolder {
	
	Psystem psystem;
	protected PaneTuple paneTuple;
	ISimulator simulator;
	protected List<List<Configuration>> computationList;
	private OleraceaGUIPanel panel;
	protected WrittenObjectReader objectReader;
	protected SimulatorResultsWriter resultsWriter;
	protected DataReader dataReader;
	
	public SimulatorHolder(OleraceaGUIPanel panel){
		this.paneTuple = panel.getPaneTuple();
		this.panel = panel;
		computationList = new LinkedList<List<Configuration>>();
		objectReader = new WrittenObjectReader();
		
	}
	
	
	public void setPsystem(Psystem psystem){
		this.psystem = psystem;
		computationList.clear();
		setInitialSimulator(psystem);
	}


	protected void setInitialSimulator(Psystem psystem){
		String simulatorID=null;
		try{
			simulatorID=psystem.getSimulatorsIDs().next();
		}
		catch(PlinguaCoreException e){
			paneTuple.writeError(e.getMessage()+"\n");
		}
		if(simulatorID!=null){
			setSimulator(simulatorID);		
			panel.getSimulationRoutes().setSimulator(simulatorID);
		}

	}
	
	public Iterator<String> getSimulators(){
		try{
			return psystem.getSimulatorsIDs();
		}
		catch(PlinguaCoreException e){
			paneTuple.writeError(e.getMessage()+"\n");
		}
		return null;
	}
	
	public void setSimulator(String simulatorID){
		try {
			simulator = psystem.createSimulator(false, false, simulatorID);
			simulator.setInfoChannel(paneTuple.getInfoChannel());
			String modelClassName=psystem.getAbstractPsystemFactory().getClass().getName();
			createResultsWriter(modelClassName);
		} catch (PlinguaCoreException e) {
			// TODO Auto-generated catch block
			paneTuple.writeError(e.getMessage()+"\n");
		}
		
	}

	protected void createResultsWriter(String modelClassName) throws PlinguaCoreException {
		resultsWriter=SimulatorResultsWriterProvider.createSimulatorResultsWriterFromPsystemClass(modelClassName);
		resultsWriter.setWritingParameters(objectReader, computationList, paneTuple);
		resultsWriter.setDataReader(getDataReader());
		
	}


	public void takeSteps(int steps) {
		if(simulator==null) return;
		List<Configuration> currentComputation = initializeComputationList();
		try{
			performSteps(steps, currentComputation);
		}
		catch(PlinguaCoreException pce){
			this.paneTuple.writeError(pce.getMessage()+"\n");
		}
		
	}


	protected List<Configuration> initializeComputationList() {
		if(computationList.isEmpty()){
			computationList.add(new LinkedList<Configuration>());
			computationList.get(0).add(cloneCurrentConfiguration());
		}
		return computationList.get(0);
	}


	protected Configuration cloneCurrentConfiguration() {
		return (Configuration)simulator.getCurrentConfig().clone();
	}
	
	public void clearPanes(){
		this.paneTuple.clearPanes();
	}


	public void performSimulations(int steps, int simulations) {
		if(simulator==null) return;
		String reportRoute=panel.getSimulationRoutes().getReportRoute();
		if(reportRoute==null||reportRoute.length()==0)
			performUntimedSimulations(steps, simulations);
		else{
			SimulationTimer timer= new SimulationTimer(this, reportRoute, this.paneTuple, psystem);
			timer.performSimulations(steps, simulations);
		}
			
		
	}


	public void performUntimedSimulations(int steps, int simulations) {
		if(simulator==null) return;
		computationList.clear();
		simulator.reset();
		try{
			for(int i=0; i<simulations; i++){
				List<Configuration> currentComputation = new LinkedList<Configuration>();	
				currentComputation.add(cloneCurrentConfiguration());
				performSteps(steps, currentComputation);
				computationList.add(currentComputation);
				simulator.reset();
			}
			
			
		}
		catch(PlinguaCoreException pce){
			this.paneTuple.writeError(pce.getMessage()+"\n");
		}
	}


	protected void performSteps(int steps,
			List<Configuration> currentComputation) throws PlinguaCoreException {
		boolean stepTaken=true;
		for(int j=0; j<steps&&stepTaken; j++){
			stepTaken=stepTaken&&simulator.step();
			stepTaken=stepTaken&&!simulator.isFinished();
			if(stepTaken)
				currentComputation.add(cloneCurrentConfiguration());
		}
	}


	public Psystem getPsystem() {
		// TODO Auto-generated method stub
		return psystem;
	}


	public void writeResults(String string){
		if(resultsWriter!=null)
			resultsWriter.writeResults(string);
	}
	
	
	public void setDataReader(DataReader dataReader){
		if (dataReader == null) {
			throw new IllegalArgumentException("Argument of type "
					+ DataReader.class
					+ " cannot be null when creating an object of type "
					+ getClass());
		}
		this.dataReader = dataReader;
	}
	
	public DataReader getDataReader(){
		return dataReader;
	}

}
