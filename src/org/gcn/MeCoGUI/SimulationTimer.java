package org.gcn.MeCoGUI;

import java.io.FileWriter;
import java.io.IOException;

import org.gcn.MeCoGUI.paneStreams.PaneTuple;
import org.gcn.plinguacore.parser.output.probabilisticGuarded.ProbabilisticGuardedAuxiliaryWriter;
import org.gcn.plinguacore.util.psystem.Psystem;

import com.opencsv.CSVWriter;

public class SimulationTimer {
	
	private SimulatorHolder simulationHolder;
	private String reportRoute;
	private PaneTuple paneTuple;
	private Psystem psystem;

	public SimulationTimer(SimulatorHolder simulatorHolder, String reportRoute, PaneTuple paneTuple, Psystem psystem) {
		// TODO Auto-generated constructor stub
		this.simulationHolder=simulatorHolder;
		this.reportRoute=reportRoute;
		this.paneTuple=paneTuple;
		this.psystem=psystem;
	}

	public void performSimulations(int steps, int simulations) {
		long startingTime=System.currentTimeMillis();
		simulationHolder.performUntimedSimulations(steps, simulations);
		long endingTime=System.currentTimeMillis();
		long elapsedTime=endingTime-startingTime;
		writeSummary(steps, simulations, elapsedTime);
		
	}

	private void writeSummary(int steps, int simulations, long elapsedTime) {
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(reportRoute));
			writeHeader(writer);
			writeReport(writer, steps, simulations, elapsedTime);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			paneTuple.writeError("Errors ocurred while writing simulation report into file ["+reportRoute+"]\n");
		}
		
	}

	private void writeReport(CSVWriter writer, int steps, int simulations, long elapsedTime) {
		int numberOfMembranes=psystem.getMembraneStructure().getAllMembranes().size();
		int maxNumberOfRulesPerMembrane=calculateMaxNumberOfRulesPerMembrane();
		int alphabetSize=psystem.getAlphabet().size();
		String[] reportString={""+numberOfMembranes, ""+maxNumberOfRulesPerMembrane, ""+alphabetSize, ""+steps, ""+simulations, ""+elapsedTime};
		writer.writeNext(reportString);
		// TODO Auto-generated method stub
		
	}

	private int calculateMaxNumberOfRulesPerMembrane() {
		// TODO Auto-generated method stub
		return ProbabilisticGuardedAuxiliaryWriter.getMaximumNumberOfRulesPerMembrane(psystem);
	}

	private void writeHeader(CSVWriter writer) {
		String[] header={"Number of membranes", "Max number of rules per membrane", "Alphabet size", "Steps", "Simulations", "Elapsed time"};
		writer.writeNext(header);
		
	}

}
