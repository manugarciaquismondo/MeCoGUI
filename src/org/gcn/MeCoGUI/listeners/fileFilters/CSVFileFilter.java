package org.gcn.MeCoGUI.listeners.fileFilters;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class CSVFileFilter extends FileFilter {

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
}
