package org.gcn.MeCoGUI.writers.specificWriters;

import org.gcn.plinguacore.util.psystem.membrane.Membrane;

import com.opencsv.CSVWriter;

public interface IPsystemItemWriter {
	
	public void writeMembrane(int configurationNumber, Membrane membrane, CSVWriter writer,
			int computationIndex);

	public void writeHeader(CSVWriter writer);
	

}
