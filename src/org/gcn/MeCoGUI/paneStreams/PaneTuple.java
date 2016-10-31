package org.gcn.MeCoGUI.paneStreams;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;


public class PaneTuple {
	JTextPane infoPane, warningPane, errorPane;
	PrintStream infoChannel, warningChannel, errorChannel;
	
	
	public PaneTuple(JTextPane infoPane, JTextPane warningPane, JTextPane errorPane) {
		super();
		this.infoPane = infoPane;
		this.warningPane = warningPane;
		this.errorPane = errorPane;
		infoChannel = new PrintStream(new PaneOutputStream(infoPane), true);
		warningChannel = new PrintStream(new PaneOutputStream(warningPane), true);
		errorChannel = new PrintStream(new PaneOutputStream(errorPane), true);
		setColors();
	}
	
	private void setColors() {
		infoPane.setForeground(Color.BLACK);
		warningPane.setForeground(Color.BLUE);
		errorPane.setForeground(Color.RED);
		
	}
	
	public void writeInfo(String info){
		infoChannel.append(info);
	}
	
	public void writeWarning(String warning){
		infoChannel.append(warning);
	}
	
	public void writeError(String error){
		infoChannel.append(error);
	}

	public PrintStream getInfoChannel(){
		return infoChannel;
	}
	
	public PrintStream getWarningChannel(){
		return warningChannel;
	}
	
	public PrintStream getErrorChannel(){
		return errorChannel;
	}

	public void clearPanes() {
		infoPane.setText("");
		warningPane.setText("");
		errorPane.setText("");
		
	}

}
