package org.gcn.MeCoGUI.writers.specificWriters;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.gcn.MeCoGUI.OleraceaGUIPanel;
import org.gcn.MeCoGUI.SimulatorHolder;
import org.gcn.MeCoGUI.paneStreams.PaneTuple;
import org.gcn.MeCoGUI.writers.SimulatorResultsWriter;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;

import com.opencsv.CSVWriter;

public class ProbabilisticGuardedResultsWriter extends SimulatorResultsWriter {

	protected static ProbabilisticGuardedResultsWriter instance;
	protected IPsystemItemWriter itemWriter;
	protected ProbabilisticGuardedResultsWriter() {
		super();
	}
	
	public static ProbabilisticGuardedResultsWriter getInstance(){
		if(instance==null){
			instance=new ProbabilisticGuardedResultsWriter();
		}
		return instance;
		
	}
	


	public void writeResults(String destinationRoute) {
		try {
			objectReader.readWritableObjects();
			itemWriter=createIPsystemItemWriter();
			CSVWriter writer = new CSVWriter(new FileWriter(destinationRoute));
			writeHeader(writer);
			writeSimulations(writer);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			paneTuple.writeError("Errors ocurred while saving computation into file ["+destinationRoute+"]\n");
		} catch (PlinguaCoreException e) {
			// TODO Auto-generated catch block
			paneTuple.writeError(e.getMessage());
		}
	
	}

	protected IPsystemItemWriter createIPsystemItemWriter() {
		return new ObjectMembraneWriter(objectReader);
	}

	protected void writeHeader(CSVWriter writer) {
		itemWriter.writeHeader(writer);
		
	}

	protected void writeSimulations(CSVWriter writer) {
		int computationIndex=1;
		for (List<Configuration> computation : computationList) {
			writeComputation(writer, computation, computationIndex);
			computationIndex++;
		}
	}

	private void writeComputation(CSVWriter writer, List<Configuration> computation, int computationIndex) {
		int configurationIndex=0;
		for (Configuration configuration : computation) {
			writeConfiguration(configurationIndex, configuration, writer, computationIndex);
			configurationIndex++;
		}
	}

	private void writeConfiguration(int configurationIndex, Configuration configuration, CSVWriter writer,
			int computationIndex) {
					for (Membrane membrane : configuration.getMembraneStructure().getAllMembranes()) {
						writeMembrane(configurationIndex, membrane, writer, computationIndex);
					}
				
			
				
			}

	private void writeMembrane(int configurationNumber, Membrane membrane, CSVWriter writer,
			int computationIndex) {
				itemWriter.writeMembrane(configurationNumber, membrane, writer, computationIndex);
				
			}

	

}
