package org.gcn.MeCoGUI.precompiler;

import java.io.BufferedWriter;
import java.io.IOException;

import org.gcn.plinguacore.util.PlinguaCoreException;

public class BufferedWriterWrapper {
	
	private BufferedWriter bufferedWriter;

	public BufferedWriterWrapper(BufferedWriter bufferedWriter) {
		super();
		this.bufferedWriter = bufferedWriter;
	}
	
	public void writeNewLine() throws PlinguaCoreException{
		if(bufferedWriter!=null)
			try {
				bufferedWriter.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new PlinguaCoreException("Error founds while writing a new line");
			}
	}
	
	public void writeLine(String line) throws PlinguaCoreException{
		if(bufferedWriter!=null)
			try {
				bufferedWriter.write(line);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new PlinguaCoreException("Error founds while writing line: "+line);
			}
	}
	
	public void closeFile() throws PlinguaCoreException{
		if(bufferedWriter!=null)
			try {
				bufferedWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new PlinguaCoreException("Error founds while closing file");
			}
	}

}
