package org.gcn.MeCoGUI;

import java.awt.FileDialog;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.util.Iterator;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.gcn.MeCoGUI.listeners.changeListeners.ActionListenerCreator;
import org.gcn.MeCoGUI.listeners.changeListeners.FormatItemActionListenerCreator;
import org.gcn.MeCoGUI.listeners.changeListeners.SimulatorItemActionListener;
import org.gcn.MeCoGUI.listeners.changeListeners.SimulatorItemActionListenerCreator;
import org.gcn.MeCoGUI.listeners.fileDialogListeners.LoadDataDialogActionListener;
import org.gcn.MeCoGUI.listeners.fileDialogListeners.LoadModelDialogActionListener;
import org.gcn.MeCoGUI.listeners.fileDialogListeners.ResultsFileDialogActionListener;
import org.gcn.MeCoGUI.listeners.fileDialogListeners.SetReportDialogActionListener;
import org.gcn.plinguacore.parser.AbstractParserFactory;
import org.gcn.plinguacore.parser.output.OutputParserFactory;



public class OleraceaGUIMenuBarContent {

	JMenu setSimulatorMenu;
	JMenu setOutputFormatMenu;
	OleraceaGUIPanel panel;
	
	
	
	
	
	public OleraceaGUIMenuBarContent(OleraceaGUIPanel panel) {
		super();
		this.panel = panel;
	}

	JMenuBar createGUIMenuBar() {
		// TODO Auto-generated method stub
		JMenuBar menuBar = new JMenuBar();
		createMenus(menuBar);
		return menuBar;
	}

	private void createMenus(JMenuBar menuBar) {
		menuBar.add(createFileMenu());	
		menuBar.add(createSimulatorsMenu());
		menuBar.add(createFormatsMenu());
		
	}


	private JMenu createFormatsMenu(){
		setOutputFormatMenu = new JMenu("Output formats");
		setOutputFormatMenu.setEnabled(false);
		addAvailableOutputFormats();
		return setOutputFormatMenu;
	}



	private void addAvailableOutputFormats() {
		Iterator<String> availableFormatsIterator=AbstractParserFactory.getParserInfo().getFormatsIterator();
		setOutputFormats(availableFormatsIterator);
		
	}

	private JMenu createSimulatorsMenu() {
		// TODO Auto-generated method stub
		setSimulatorMenu = new JMenu("Simulators");
		setSimulatorMenu.setEnabled(false);
		return setSimulatorMenu;
	}

	private JMenu createFileMenu() {
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(createLoadModelMenuItem());
		fileMenu.add(createLoadDataMenuItem());
		fileMenu.add(createSetResultsFileMenuItem());
		fileMenu.add(createSetReportFileMenuItem());
		return fileMenu;
	}


	private JMenuItem createSetReportFileMenuItem() {
		String label = "Set Report";
		JMenuItem item = new JMenuItem(label);
		item.addActionListener(new SetReportDialogActionListener(label, FileDialog.SAVE, panel.getSimulationRoutes()));
		return item;
	}

	private JMenuItem createLoadModelMenuItem() {
		String label = "Load Model";
		JMenuItem item = new JMenuItem(label);
		item.addActionListener(new LoadModelDialogActionListener(label, FileDialog.LOAD, panel.getSimulationRoutes()));
		return item;
	}

	private JMenuItem createLoadDataMenuItem() {
		String label = "Load Data";
		JMenuItem item = new JMenuItem(label);
		item.addActionListener(new LoadDataDialogActionListener(label, FileDialog.LOAD, panel.getSimulationRoutes()));
		return item;
	}

	private JMenuItem createSetResultsFileMenuItem() {
		String label = "Save Results";
		JMenuItem item = new JMenuItem(label);
		item.addActionListener(new ResultsFileDialogActionListener(label, FileDialog.SAVE, panel));
		return item;
	}
	
	protected void createMenuItems(JMenu menu, Iterator<String> itemIDs, ActionListenerCreator creator){
		menu.removeAll();
		while(itemIDs.hasNext()){
			String itemID=itemIDs.next();
			JMenuItem menuItem = new JMenuItem(itemID);
			menuItem.addActionListener(creator.createActionListener(itemID, panel));
			menu.add(menuItem);
		}
	}
	
	public void setSimulators(Iterator<String> simulatorsIDs){	
		createMenuItems(setSimulatorMenu, simulatorsIDs, new SimulatorItemActionListenerCreator());
		setSimulatorMenu.setEnabled(true);
		setOutputFormatMenu.setEnabled(true);
	}
	
	public void setOutputFormats(Iterator<String> formatIDs){
		createMenuItems(setOutputFormatMenu, formatIDs, new FormatItemActionListenerCreator());	
	}

	public void disableMenus() {
		setSimulatorMenu.setEnabled(false);
		setOutputFormatMenu.setEnabled(false);
		// TODO Auto-generated method stub
		
	}
}
