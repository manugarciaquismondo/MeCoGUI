package org.gcn.pLinguaCoreCSVApplication.listeners.fileDialogListeners;

import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.filechooser.FileFilter;

import org.gcn.pLinguaCoreCSVApplication.data.SimulationRoutes;


public class LoadModelDialogActionListener extends RouteDialogActionListener{

	public LoadModelDialogActionListener(String label, int mode,
			SimulationRoutes guiData) {
		super(label, mode, guiData);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected
	void setDataElement(String string) {
		guiData.setModelRoute(string);

	}


	@Override
	protected FileFilter createFileFilter() {
		// TODO Auto-generated method stub
		return new FileFilter(){

			@Override
			public boolean accept(File f) {
				// TODO Auto-generated method stub
				return f.isDirectory()||f.getName().endsWith(".pli");
			}

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "P-Lingua Files (*.pli)";
			};
		};
	}



}
