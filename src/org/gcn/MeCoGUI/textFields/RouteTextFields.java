package org.gcn.MeCoGUI.textFields;

import javax.swing.JTextField;

import org.gcn.MeCoGUI.OleraceaGUIPanel;


public class RouteTextFields {
	JTextField modelTextField, dataTextField, resultsTextField, simulatorTextField, reportTextField;
	int fieldLength;

	
	public RouteTextFields(JTextField modelTextField, JTextField dataTextField,
			JTextField resultsTextField, JTextField simulatorTextField, JTextField reportTextField) {
		super();
		fieldLength=OleraceaGUIPanel.INITIAL_STRING.length();
		this.modelTextField = modelTextField;
		this.dataTextField = dataTextField;
		this.resultsTextField = resultsTextField;
		this.simulatorTextField= simulatorTextField;
		this.reportTextField=reportTextField;
	}
	
	public void setModel(String model){
		this.modelTextField.setText(getAppendix(model));
	}

	
	public void setData(String data){
		this.dataTextField.setText(getAppendix(data));
	}
	
	public void setResults(String results){
		this.resultsTextField.setText(getAppendix(results));
	}

	
	public void setSimulator(String simulator){
		this.simulatorTextField.setText(getAppendix(simulator));

	}
	

	protected String getAppendix(String string) {
		String appendix="";
		int appendixLength=fieldLength-string.length();
		if(appendixLength>0)
			appendix=OleraceaGUIPanel.INITIAL_STRING.substring(0, appendixLength);
		return string+appendix;
	}

	public void setReport(String reportRoute) {
		this.reportTextField.setText(getAppendix(reportRoute));
		
	}
	
}
