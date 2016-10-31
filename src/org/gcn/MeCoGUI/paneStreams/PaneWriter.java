package org.gcn.MeCoGUI.paneStreams;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class PaneWriter implements Runnable {

	JTextPane textPane;
	String text;
	
	public PaneWriter(JTextPane textPane, String text) {
		super();
		this.textPane = textPane;
		this.text = text;
	}

	@Override
		 public void run() {
		      Document doc = textPane.getDocument();
		      try {
		        doc.insertString(doc.getLength(), text, null);
		      } catch (BadLocationException e) {
		        throw new RuntimeException(e);
		      }
		      textPane.setCaretPosition(doc.getLength() - 1);
		    }
		  

	}


