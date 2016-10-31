package org.gcn.MeCoGUI.csvAdaptor;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.gcn.plinguacore.parser.AbstractParserFactory;
import org.gcn.plinguacore.parser.input.InputParser;
import org.gcn.plinguacore.parser.input.InputParserFactory;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Psystem;

public class ParameterJoiner {
	
	protected DataReader dataReader;
	
	public ParameterJoiner() {
		super(); 
		dataReader = new DataReader();
		
		// TODO Auto-generated constructor stub
	}
	
	public InputStream initModel(String modelRoute, String parametersRoute) throws PlinguaCoreException {
		dataReader.readParametersAndReportExceptions(parametersRoute);
		return appendParameters(dataReader, modelRoute);
	}
	
	protected String convertStreamToString(InputStream is) {
		Scanner inputScanner=new Scanner(is);
	    java.util.Scanner s = inputScanner.useDelimiter("\\A");
	    String result= s.hasNext() ? s.next() : "";
	    inputScanner.close();
	    return result;
	}

	public int getStepsPerCycle(){
		return dataReader.getStepsPerCycle();
	}

	private InputStream appendParameters(DataReader dataReader, String modelRoute) throws PlinguaCoreException {
		String fileContent="";
		try{
			fileContent=convertStreamToString(new FileInputStream(modelRoute));
		}
		catch(IOException e){
			throw new PlinguaCoreException("Errors occurred while appending parameters on file ["+modelRoute+"]");
		}
		fileContent+='\n';
		for (String parameter : dataReader.getParameters()) {
			fileContent+=dataReader.getAsString(parameter);
		}	
		return new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));

	}

	private void copyFiles(String modelRoute,
			String temporaryRoute) throws PlinguaCoreException {
		FileInputStream modelFile = null;
		FileOutputStream temporaryFile = null;
		try{
			modelFile = new FileInputStream(modelRoute);
			temporaryFile = new FileOutputStream(temporaryRoute);
			byte[] buf = new byte[1024];
			int len;
			while ((len = modelFile.read(buf)) > 0){
				temporaryFile.write(buf, 0, len);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new PlinguaCoreException("File ["+modelRoute+"] or ["+temporaryRoute+"] not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new PlinguaCoreException("Errors occurred while copying file ["+modelRoute+"] into ["+temporaryRoute+"]");
		}
		
		finally{
			
				try {
					if(modelFile!=null) modelFile.close();
					if(temporaryFile!=null) temporaryFile.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw new PlinguaCoreException("Errors occurred while closing files ["+modelRoute+"] and ["+temporaryRoute+"]");
				}
			
		}

	}
	
	public DataReader getDataReader(){
		return this.dataReader;
	}
	
	
	

}
