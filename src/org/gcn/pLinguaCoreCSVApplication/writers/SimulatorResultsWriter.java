package org.gcn.pLinguaCoreCSVApplication.writers;

import java.util.List;

import org.gcn.pLinguaCoreCSVApplication.csvAdaptor.DataReader;
import org.gcn.pLinguaCoreCSVApplication.paneStreams.PaneTuple;
import org.gcn.plinguacore.util.psystem.Configuration;

public abstract class SimulatorResultsWriter {

	protected static BasicWrittenObjectReader objectReader;
	protected static List<List<Configuration>> computationList;
	protected static PaneTuple paneTuple;
	protected String simulationName;
	protected DataReader dataReader;

	public abstract void writeResults(String string);

	public void setWritingParameters(BasicWrittenObjectReader objectReader, List<List<Configuration>> computationList, PaneTuple paneTuple) {
		if (objectReader == null) {
			throw new IllegalArgumentException("Argument of type " + WrittenObjectReader.class
					+ " cannot be null when creating an object of type "
					+ getClass());
		}
		SimulatorResultsWriter.objectReader=objectReader;
		if (computationList == null) {
			throw new IllegalArgumentException("Argument of type "
					+ List.class
					+ " cannot be null when creating an object of type "
					+ getClass());
		}
		SimulatorResultsWriter.computationList=computationList;
		if (paneTuple == null) {
			throw new IllegalArgumentException("Argument of type "
					+ PaneTuple.class
					+ " cannot be null when creating an object of type "
					+ getClass());
		}
		SimulatorResultsWriter.paneTuple = paneTuple;
	}
	
	public void setSimulationName(String name){
		if(name!=null&&!name.isEmpty()){
			simulationName=name;
		}
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

}
