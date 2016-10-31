package org.gcn.pLinguaCoreCSVApplication.writers.specificWriters;

import org.gcn.pLinguaCoreCSVApplication.writers.BasicWrittenObjectReader;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;

import com.opencsv.CSVWriter;

public class ObjectWithIDMembraneWriter extends ObjectMembraneWriter {

	public ObjectWithIDMembraneWriter(BasicWrittenObjectReader objectReader) {
		super(objectReader);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected void writeObject(String object, long count, Membrane membrane,
			int configurationNumber, CSVWriter writer, int computationIndex) {
		// TODO Auto-generated method stub
		writer.writeNext(new String[]{computationIndex+"", configurationNumber+"", membrane.getLabel(), membrane.getId()+"", object, ""+count});

	}
	
	@Override
	public void writeHeader(CSVWriter writer) {
		writer.writeNext(new String[]{"Simulation", "Configuration", "Membrane", "MembraneID", "Object", "Multiplicity"});
		
	}
	


}
