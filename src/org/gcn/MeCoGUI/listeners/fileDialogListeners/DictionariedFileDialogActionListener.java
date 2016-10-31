package org.gcn.MeCoGUI.listeners.fileDialogListeners;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import org.gcn.MeCoGUI.data.SimulationRoutes;
import org.gcn.MeCoGUI.listeners.fileFilters.DictionaryFileFilter;
import org.gcn.plinguacore.parser.output.OutputParser;
import org.gcn.plinguacore.parser.output.simplekernel.DictionariedOutputParser;

public abstract class DictionariedFileDialogActionListener extends
		RouteDialogActionListener {


	protected String dictionaryRoute;
	
	
	public DictionariedFileDialogActionListener(String label, int mode,
			SimulationRoutes guiData) {
		super(label, mode, guiData);
		dictionaryRoute=System.getProperty("user.dir");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected abstract void setDataElement(String string);

	@Override
	protected abstract FileFilter createFileFilter();
	
	protected void queryDictionaryRoute() {
		JFileChooser fileChooser = new JFileChooser(dictionaryRoute);
		fileChooser.setFileFilter(createDictionaryFileFilter());
		int rVal = fileChooser.showOpenDialog(new JFrame());
		  if (rVal == JFileChooser.APPROVE_OPTION) {
			  dictionaryRoute=fileChooser.getSelectedFile().toString();
		  }
			 
	}

	private FileFilter createDictionaryFileFilter() {
		// TODO Auto-generated method stub
		return new DictionaryFileFilter();

	}

}
