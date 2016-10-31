package org.gcn.pLinguaCoreCSVApplication.paneStreams;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;

public class PaneOutputStream extends OutputStream {


	    private JTextPane pane;

		public PaneOutputStream(JTextPane pane) {
			super();
			this.pane = pane;
		}

		@Override
	    public void write(final int b) throws IOException {
	      updateTextPane(String.valueOf((char) b), pane);
	    }
	 
	    @Override
	    public void write(byte[] b, int off, int len) throws IOException {
	      updateTextPane(new String(b, off, len), pane);
	    }
	 
	    @Override
	    public void write(byte[] b) throws IOException {
	      write(b, 0, b.length);
	    }
	  
		private void updateTextPane(final String text, JTextPane pane) {
			  SwingUtilities.invokeLater(new PaneWriter(pane, text));
		}

}
