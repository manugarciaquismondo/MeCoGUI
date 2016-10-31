package org.gcn.MeCoGUI.writers.specificWriters;

import org.gcn.MeCoGUI.writers.BasicWrittenObjectReader;
import org.gcn.MeCoGUI.writers.WrittenObjectReader;
import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;

import com.opencsv.CSVWriter;

public class ObjectMembraneWriter implements IPsystemItemWriter {

	protected BasicWrittenObjectReader objectReader;
	public ObjectMembraneWriter(BasicWrittenObjectReader objectReader) {
		super();
		if (objectReader == null) {
			throw new IllegalArgumentException("Argument of type " + WrittenObjectReader.class
					+ " cannot be null when creating an object of type " + getClass());
		}
		this.objectReader = objectReader;
	}

	@Override
	public void writeMembrane(int configurationNumber, Membrane membrane, CSVWriter writer,
			int computationIndex) {
		MultiSet<String> multiSet = membrane.getMultiSet();
		for (String object : multiSet.entrySet()) {
			if(objectReader.writableObject(object))
				writeObject(object, multiSet.count(object), membrane, configurationNumber, writer, computationIndex);

		}

	}

	protected void writeObject(String object, long count, Membrane membrane,
			int configurationNumber, CSVWriter writer, int computationIndex) {
		writer.writeNext(new String[]{computationIndex+"", configurationNumber+"", membrane.getLabel(), object, ""+count});
		// TODO Auto-generated method stub

	}

	@Override
	public void writeHeader(CSVWriter writer) {
		writer.writeNext(new String[]{"Simulation", "Configuration", "Membrane", "Object", "Multiplicity"});

	}

}
