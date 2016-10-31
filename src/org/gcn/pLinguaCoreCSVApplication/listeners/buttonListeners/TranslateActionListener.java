package org.gcn.pLinguaCoreCSVApplication.listeners.buttonListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import org.gcn.pLinguaCoreCSVApplication.OleraceaGUIPanel;
import org.gcn.pLinguaCoreCSVApplication.SimulatorHolder;
import org.gcn.pLinguaCoreCSVApplication.data.SimulationRoutes;
import org.gcn.pLinguaCoreCSVApplication.listeners.fileDialogListeners.DictionariedFileDialogActionListener;
import org.gcn.pLinguaCoreCSVApplication.listeners.fileDialogListeners.FileDialogActionListener;
import org.gcn.pLinguaCoreCSVApplication.listeners.fileFilters.CSVFileFilter;
import org.gcn.pLinguaCoreCSVApplication.paneStreams.PaneTuple;
import org.gcn.plinguacore.parser.input.probabilisticGuarded.ProbabilisticGuardedDictionaryReader;
import org.gcn.plinguacore.parser.input.simplekernel.KernelDictionaryReader;
import org.gcn.plinguacore.util.PlinguaCoreException;

public class TranslateActionListener extends DictionariedFileDialogActionListener{






	private PaneTuple paneTuple;
	private String resultsRoute;


	public TranslateActionListener(String label, int mode,
			OleraceaGUIPanel panel) {
		super(label, mode, panel.getSimulationRoutes());
		paneTuple=panel.getPaneTuple();
		resultsRoute=System.getProperty("user.dir");

		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected void setDataElement(String string) {
		ProbabilisticGuardedDictionaryReader dictionaryReader=new ProbabilisticGuardedDictionaryReader();
		try{
			queryResultsRoute();
			dictionaryRoute="dictionary.txt";
			dictionaryReader.readSimulations(dictionaryRoute, string, resultsRoute);
		}
		catch(Exception e){
			paneTuple.writeError("Errors ocurred while translating computation from file "+string+":\n");
		}

	}

	private void queryResultsRoute() {
		JFileChooser fileChooser = new JFileChooser(resultsRoute);
		fileChooser.setFileFilter(new CSVFileFilter());
		
		int rVal = fileChooser.showOpenDialog(new JFrame());
		  if (rVal == JFileChooser.APPROVE_OPTION) {
			  resultsRoute=fileChooser.getSelectedFile().toString();
		  }
		
	}

	@Override
	protected FileFilter createFileFilter() {
		// TODO Auto-generated method stub
		return new FileFilter(){

			@Override
			public boolean accept(File f) {
				// TODO Auto-generated method stub
				return f.isDirectory()||f.getName().endsWith(".csv");
			}

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "CSV Files (*.csv)";
			};
		};
	}

}
