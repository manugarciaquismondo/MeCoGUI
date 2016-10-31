package org.gcn.MeCoGUI.writers.specificWriters;

import java.util.List;

import org.gcn.plinguacore.util.MultiSet;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.membrane.MembraneStructure;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembrane;

import com.opencsv.CSVWriter;

public class LinkMembraneWriter implements IPsystemItemWriter {

	protected MembraneStructure structure;
	@Override
	public void writeMembrane(int configurationNumber, Membrane membrane,
			CSVWriter writer, int computationIndex) {
		if(membrane instanceof RegenerativeMembrane){
			RegenerativeMembrane regenerativeMembrane=(RegenerativeMembrane)membrane;
			List<Integer> linkedMembraneIDs = regenerativeMembrane.getLinkedMembranes();
			for (Integer linkedMembrane : linkedMembraneIDs) {
				String linkedLabel=((TissueLikeMembrane)membrane).getStructure().getCell(linkedMembrane).getLabel();
				writeLink(writer, computationIndex, configurationNumber, membrane.getId(), linkedMembrane, membrane.getLabel(), linkedLabel);
	
			}
		}

	}
	
	void setStructure(MembraneStructure structure){
		this.structure=structure;
	}

	protected void writeLink(CSVWriter writer, int computationIndex, int configurationNumber,
			int id, int linkedMembrane, String linkerLabel, String linkedLabel) {
		writer.writeNext(new String[]{computationIndex+"", configurationNumber+"", id+"", linkedMembrane+"", linkerLabel+"", linkedLabel+""});
		
	}

	@Override
	public void writeHeader(CSVWriter writer) {
		writer.writeNext(new String[]{"Simulation", "Configuration", "Linker", "Linked", "LinkerLabel", "LinkedLabel"});

	}

}
