package org.gcn.MeCoGUI.listeners.fileDialogListeners;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

import org.gcn.MeCoGUI.data.SimulationRoutes;


public abstract class FileDialogActionListener implements ActionListener {

	protected String parameter;

	
	
	public FileDialogActionListener() {
		super();
		parameter= System.getProperty("user.dir");
		// TODO Auto-generated constructor stub
	}

	protected abstract void setDataElement(String string);
	public String getRoute(){ return parameter;}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser(parameter);
		fileChooser.setFileFilter(createFileFilter());
	    int rVal = fileChooser.showOpenDialog(new JFrame());
	      if (rVal == JFileChooser.APPROVE_OPTION) {
	    	  setDataElement(fileChooser.getSelectedFile().toString());
	          parameter=fileChooser.getCurrentDirectory().toString();
	      }
	}

	protected abstract FileFilter createFileFilter();
	
}
