package org.gcn.pLinguaCoreCSVApplication.listeners.changeListeners;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import org.gcn.pLinguaCoreCSVApplication.OleraceaGUIPanel;
import org.gcn.pLinguaCoreCSVApplication.SimulatorHolder;
import org.gcn.pLinguaCoreCSVApplication.data.SimulationRoutes;
import org.gcn.pLinguaCoreCSVApplication.listeners.fileDialogListeners.DictionariedFileDialogActionListener;
import org.gcn.pLinguaCoreCSVApplication.listeners.fileDialogListeners.RouteDialogActionListener;
import org.gcn.pLinguaCoreCSVApplication.paneStreams.PaneTuple;
import org.gcn.plinguacore.parser.AbstractParserFactory;
import org.gcn.plinguacore.parser.input.InputParser;
import org.gcn.plinguacore.parser.input.InputParserFactory;
import org.gcn.plinguacore.parser.output.OutputParser;
import org.gcn.plinguacore.parser.output.OutputParserFactory;
import org.gcn.plinguacore.parser.output.simplekernel.DictionariedOutputParser;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Psystem;

public class FormatItemActionListener extends DictionariedFileDialogActionListener {




	private PaneTuple paneTuple;
	String fileExtension;
	String dictionaryRoute;
	OleraceaGUIPanel panel;

	public FormatItemActionListener(String label, int mode,
			OleraceaGUIPanel panel) {
		super(label, mode, panel.getSimulationRoutes());
		this.paneTuple=panel.getPaneTuple();
		this.panel=panel;
		fileExtension=AbstractParserFactory.getParserInfo().getFileExtension(label);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setDataElement(String string) {
		
		AbstractParserFactory outputParserFactory = new OutputParserFactory();
		SimulatorHolder simulationHolder=panel.getSimulatorHolder();
		Psystem psystem=simulationHolder.getPsystem();
		if(psystem==null) return;
		if(string!=null&&psystem!=null){
			try{
				OutputParser outputParser = (OutputParser) outputParserFactory
						.createParser(label);				
				outputParser.parse(psystem, new FileOutputStream(string));
			}
			catch(IOException ioe){
				paneTuple.writeError("An error ocurred while writing file "+string+" into format "+label+"\n");
			}
			catch(PlinguaCoreException pce){
				paneTuple.writeError("An error ocurred while generating P system in file "+string+" in format "+label+":\n"+pce.getMessage()+"\n");
			}
		}

	}

	

	@Override
	protected FileFilter createFileFilter() {
		// TODO Auto-generated method stub
		return new FileFilter(){

			@Override
			public boolean accept(File f) {
				// TODO Auto-generated method stub
				return f.isDirectory()||f.getName().endsWith("."+fileExtension);
			}

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return label+" Files (."+fileExtension+")";
			};
		};
	}

}
