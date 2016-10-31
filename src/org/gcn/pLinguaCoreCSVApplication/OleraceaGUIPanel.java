package org.gcn.pLinguaCoreCSVApplication;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.gcn.pLinguaCoreCSVApplication.data.SimulationParameters;
import org.gcn.pLinguaCoreCSVApplication.data.SimulationRoutes;
import org.gcn.pLinguaCoreCSVApplication.listeners.buttonListeners.ClearConsolesActionListener;
import org.gcn.pLinguaCoreCSVApplication.listeners.buttonListeners.InitializeActionListener;
import org.gcn.pLinguaCoreCSVApplication.listeners.buttonListeners.SaveResultsActionListener;
import org.gcn.pLinguaCoreCSVApplication.listeners.buttonListeners.SimulateActionListener;
import org.gcn.pLinguaCoreCSVApplication.listeners.buttonListeners.SimulateAllActionListener;
import org.gcn.pLinguaCoreCSVApplication.listeners.buttonListeners.TakeStepActionListener;
import org.gcn.pLinguaCoreCSVApplication.listeners.buttonListeners.TranslateActionListener;
import org.gcn.pLinguaCoreCSVApplication.listeners.changeListeners.TextFieldsUpdaterListener;
import org.gcn.pLinguaCoreCSVApplication.listeners.fileDialogListeners.LoadDataDialogActionListener;
import org.gcn.pLinguaCoreCSVApplication.listeners.fileDialogListeners.LoadModelDialogActionListener;
import org.gcn.pLinguaCoreCSVApplication.listeners.fileDialogListeners.ResultsFileDialogActionListener;
import org.gcn.pLinguaCoreCSVApplication.listeners.simulationParametersListeners.CyclesActionListener;
import org.gcn.pLinguaCoreCSVApplication.listeners.simulationParametersListeners.SimulationsActionListener;
import org.gcn.pLinguaCoreCSVApplication.listeners.simulationParametersListeners.StepsPerCycleActionListener;
import org.gcn.pLinguaCoreCSVApplication.paneStreams.PaneTuple;
import org.gcn.pLinguaCoreCSVApplication.textFields.ParametersTextFields;
import org.gcn.pLinguaCoreCSVApplication.textFields.RouteTextFields;


public class OleraceaGUIPanel extends JFrame{
	

	OleraceaGUIMenuBarContent menuBar;


	SimulationRoutes simulationRoutes;
	SimulationParameters simulationParameters;
	JPanel paramsComponent;
	GridBagConstraints restrictions=new GridBagConstraints();
	private RouteTextFields routeTextFields;
	private ParametersTextFields parametersTextFields;
	private PaneTuple paneTuple;
	private SimulatorHolder simulationHolder;
	private static final int WIDTH=1200;
	private static final int HEIGHT=800;
	public static final String INITIAL_STRING=
			"                                                                                             ";
	



	public void setUpFrame() {		
		simulationRoutes = new SimulationRoutes(this);
		simulationParameters = new SimulationParameters(this);
		setUpLayout();
		menuBar = new OleraceaGUIMenuBarContent(this);
		setJMenuBar(menuBar.createGUIMenuBar());		
		createMainConsole();
		simulationHolder = new SimulatorHolder(this);
		createTextFields(INITIAL_STRING, INITIAL_STRING, INITIAL_STRING, INITIAL_STRING, INITIAL_STRING, 0, 0, 0);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	public PaneTuple getPaneTuple(){
		return paneTuple;
	}
	
	private void createMainConsole() {
		JTabbedPane mainConsole = new JTabbedPane(JTabbedPane.TOP);
		mainConsole.setPreferredSize(new Dimension(WIDTH, HEIGHT/2));
		mainConsole.setBorder(new TitledBorder("Simulation Console"));
		JTextPane infoPane = createPane("Info console", mainConsole);
		JTextPane warningPane = createPane("Warning console", mainConsole);
		JTextPane errorPane = createPane("Error console", mainConsole);
		paneTuple = new PaneTuple(infoPane, warningPane, errorPane);
		//mainConsole.addChangeListener(new TextFieldsUpdaterListener(this));			
		getContentPane().add(mainConsole, BorderLayout.SOUTH);
		
	}

	private JTextPane createPane(String label, JTabbedPane mainConsole){
		JTextPane pane = new JTextPane();
		JScrollPane scroll = new JScrollPane(pane);		
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pane.setEditable(false);
		mainConsole.addTab(label, scroll);
		return pane;
	}

	public void createTextFields(String model, String data, String results, String simulator, String report, int steps, int cycles, int simulations) {
		createRouteTextFields(model, data, results, simulator, report);
		createSimulationTextFields(steps, cycles, simulations);

	}

	private void createSimulationTextFields(int cycles, int steps, int simulations) {
		JTextField cyclesTextField = createTextField("Number of cycles", 4, 0, true, getInitialNumericalStringParameter(cycles), false, false);
		addGrid(new JLabel("        "), 6, 0);
		JTextField stepsTextField = createTextField("Steps per cycle", 4, 1, true, getInitialNumericalStringParameter(steps), false, false);
		addGrid(new JLabel("        "), 6, 1);
		JTextField simulationsTextField = createTextField("Number of simulations", 4, 2, true, getInitialNumericalStringParameter(simulations), false, false);	
		parametersTextFields = new ParametersTextFields(cyclesTextField, stepsTextField, simulationsTextField);
		createInitializeButton();	
		createSimulateAllButton();
		createSimulateButton();	
		createTakeStepButton();
		createClearButton();
		creatTranslateButton();
	}

	private void createTakeStepButton() {
		JButton setSimulationButton = new JButton("Take Step");
		addGrid(setSimulationButton, 7, 2);
		setSimulationButton.addActionListener(new TakeStepActionListener(parametersTextFields, paneTuple, simulationRoutes, simulationHolder, simulationParameters));
		
	}

	protected String getInitialNumericalStringParameter(int value) {
		return value==0?"000":value+"";
	}

	protected void createInitializeButton() {
		JButton setInitButton = new JButton("Initialize");
		addGrid(setInitButton, 7, 0);
		setInitButton.addActionListener(new InitializeActionListener(this));
	}
	
	protected void creatTranslateButton() {
		JButton translateButton = new JButton("Translate results");
		addGrid(translateButton, 8, 0);
		translateButton.addActionListener(new TranslateActionListener("Translate results", FileDialog.SAVE, this));
	}

	private void createSimulateButton() {
		JButton setSimulationButton = new JButton("Simulate");
		addGrid(setSimulationButton, 7, 1);
		setSimulationButton.addActionListener(new SimulateActionListener(parametersTextFields, paneTuple, simulationRoutes, simulationHolder, simulationParameters));
		
	}

	private void createSimulateAllButton() {
		JButton setSimulationButton = new JButton("Simulate all");
		addGrid(setSimulationButton, 7, 3);
		setSimulationButton.addActionListener(new SimulateAllActionListener(parametersTextFields, paneTuple, simulationRoutes, simulationHolder, simulationParameters));
		
	}

	private void createClearButton() {
		JButton setInitButton = new JButton("Clear consoles");
		addGrid(setInitButton, 7, 4);
		setInitButton.addActionListener(new ClearConsolesActionListener(this));		
	}

	protected void createRouteTextFields(String model, String data, String results, String simulator, String report) {
		JTextField modelTextField = createTextField("Model", 0, 0, false, model, true, true);
		addGrid(new JLabel("        "), 2, 0);
		JTextField dataTextField = createTextField("Data", 0, 1, false, data, true, true);
		addGrid(new JLabel("        "), 2, 1);
		JTextField resultsTextField = createTextField("Results", 0, 2, false, results, true, true);
		addGrid(new JLabel("        "), 2, 2);
		JTextField reportTextField = createTextField("Report", 0, 3, false, report, true, true);
		addGrid(new JLabel("        "), 2, 3);
		JTextField simulatorTextField = createTextField("Simulator", 0, 4, false, simulator, false, true);
		addGrid(new JLabel("        "), 2, 4);
		routeTextFields = new RouteTextFields(modelTextField, dataTextField, resultsTextField, simulatorTextField, reportTextField);
	}
	
	public ParametersTextFields getParametersTextFields(){
		return parametersTextFields;
	}
	
	public RouteTextFields getRouteTextFields() {
		return routeTextFields;
	}

	private JTextField createTextField(String label, int x, int y, boolean editable, String initValue, boolean isFile, boolean padding){
		JLabel textLabel = new JLabel(label+(isFile?" File Route: ":""));
		JTextField textField = new JTextField(initValue);
		textField.setText(buildInitValueString(initValue, padding));
		textField.setEditable(editable);
		addGrid(textLabel, x, y);
		addGrid(textField, x+1, y);
		return textField;
	}

	protected String buildInitValueString(String initValue, boolean padding) {
		if(padding)
			return "  "+initValue+"  ";
		else
			return initValue;
	}


	public SimulationRoutes getSimulationRoutes() {
		return simulationRoutes;
	}
	
	public SimulationParameters getSimulationParametes() {
		return simulationParameters;
	}


	protected void setUpLayout() {
		setLocation(50, 50);
		setSize(WIDTH, HEIGHT);
		paramsComponent = new JPanel(new GridBagLayout());
		setLayout(new BorderLayout());
		getContentPane().add(paramsComponent, BorderLayout.NORTH);
	}

	public OleraceaGUIMenuBarContent getMenuBarContent(){
		return menuBar;
	}
	
	private void addGrid(Component c, int x, int y){
		restrictions.gridx=x;
		restrictions.gridy=y;
		paramsComponent.add(c,restrictions);
	}


	public void setSimulators(Iterator<String> simulatorsIDs){
		menuBar.setSimulators(simulatorsIDs);
	}

	public SimulatorHolder getSimulatorHolder() {
		// TODO Auto-generated method stub
		return simulationHolder;
	}

	public void setSimulator(String simulatorID) {
		routeTextFields.setSimulator(simulatorID);
		simulationHolder.setSimulator(simulatorID);
		
	}


}
