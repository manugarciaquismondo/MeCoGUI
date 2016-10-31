package org.gcn.pLinguaCoreCSVApplication.csvAdaptor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.gcn.plinguacore.util.PlinguaCoreException;

import com.opencsv.CSVReader;

public class DataReader {
	

	private static final String STEPS_PER_CYCLE = "steps_per_cycle";
	private Map<String, String> parameters;
	private final int PARAMETER_NAME_POSITION=0;
	private final int PARAMETER_VALUE_POSITION=1;
	private final int PARAMETER_INDEXES_POSITION=2;
	private final int PARAMETER_INDEXES_START_POSITION=3;
	private final int STARTING_ROW=1;
	private int stepsPerCycle;
	
	public DataReader() {
		super();
		parameters = new HashMap<String, String>();
		stepsPerCycle=0;
	}

	
	public void readParametersAndReportExceptions(String route) throws PlinguaCoreException{
		try {
			readParameters(route);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new PlinguaCoreException("File ["+route+"] not found\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new PlinguaCoreException("Errors occurred while parsing parameters from file ["+route+"]\n");
		}
	}


	protected void readParameters(String route) throws FileNotFoundException,
			IOException {
		parameters = new HashMap<String, String>();
		CSVReader csvReader = new CSVReader(new FileReader(route), ',', '\"', STARTING_ROW);
		String[] row = null;
		while((row = csvReader.readNext()) != null) {
			if(row[PARAMETER_NAME_POSITION].equals(STEPS_PER_CYCLE))
			{
				setStepsPerCycle(row[PARAMETER_VALUE_POSITION]);
			}
			else{
				readParameter(row);
			}
			
		}
		csvReader.close();
	}
	

	private void setStepsPerCycle(String string) {
		this.stepsPerCycle=Integer.parseInt(string);
		
	}
	
	public int getStepsPerCycle(){
		return stepsPerCycle;
	}

	public double getParameter(String parameterName){
		if(!parameters.containsKey(parameterName)){
			throw new IllegalArgumentException("Parameter "+parameterName+" is not defined");
		}
		return Double.parseDouble(parameters.get(parameterName));
	}

	private void readParameter(String[] row) {
		String constantID=constructConstantID(row);
		String value = row[PARAMETER_VALUE_POSITION];
		parameters.put(constantID, value);
		
	}

	private String constructConstantID(String[] row) {
		// TODO Auto-generated method stub
		String constantID=row[PARAMETER_NAME_POSITION];
		int indexes =Integer.parseInt(row[PARAMETER_INDEXES_POSITION]);
		if(indexes>0)
			constantID+=constructIndexes(row, indexes);
		return constantID;
	}

	private String constructIndexes(String[] row, int numberOfIndexes) {
		// TODO Auto-generated method stub
		String indexes="{";
		for(int i=PARAMETER_INDEXES_START_POSITION; i<PARAMETER_INDEXES_START_POSITION+numberOfIndexes; i++)
			indexes+=row[i]+", ";
		indexes=indexes.substring(0,  indexes.length()-2);
		indexes+="}";
		return indexes;
	}

	public Set<String> getParameters(){
		return parameters.keySet();
	}
	
	public String getAsString(String parameter){
		if(!(parameters.containsKey(parameter)))
			return ";\n";
		else
			return parameter+"="+parameters.get(parameter)+";\n";
	}
}
