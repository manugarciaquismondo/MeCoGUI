package org.gcn.MeCoGUI.csvAdaptor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import org.gcn.MeCoGUI.paneStreams.PaneTuple;
import org.gcn.plinguacore.parser.AbstractParserFactory;
import org.gcn.plinguacore.parser.input.InputParser;
import org.gcn.plinguacore.parser.input.InputParserFactory;
import org.gcn.plinguacore.parser.input.precompiler.PlinguaPrecompiler;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Psystem;

public class PsystemCompiler {
	protected PaneTuple paneTuple;
	protected ParameterJoiner parameterJoiner;
	protected final String PRECOMPILED_APPEND="precompiled.pli";
	
	public PsystemCompiler(PaneTuple paneTuple) {
		super();
		this.paneTuple = paneTuple;
		this.parameterJoiner = new ParameterJoiner();
	}

	protected Psystem parsePsystem(String modelRoute, String parametersRoute) throws PlinguaCoreException{
		InputStream parameterizedModelStream=parameterJoiner.initModel(modelRoute, parametersRoute);
		return parsePsystem(parameterizedModelStream, modelRoute, "");
	}
	

	private InputStream preprocessRoute(String temporaryRoute) throws PlinguaCoreException {
		// TODO Auto-generated method stub
		try {
			return new FileInputStream(temporaryRoute);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new PlinguaCoreException("Errors occurred while turning file "+temporaryRoute+" into an input stream");
		}
	}


	private String getTemporaryFile(String parametersRoute) {
		// TODO Auto-generated method stub
		String folder="";
		String separator="/";
		if(parametersRoute.contains("/")){
			folder = parametersRoute.substring(0,parametersRoute.indexOf("/"));
			separator="/";
		}
		else{
			folder = parametersRoute.substring(0,parametersRoute.lastIndexOf("\\"));
			separator="\\";
		}
		return folder+separator+"temporary.pli";
	}

	public Psystem parsePsystemAndReportErrors(String modelRoute, String parametersRoute){
		if(!checkRoutes(modelRoute, parametersRoute)) return null;	
		try {
			return parsePsystem(modelRoute, parametersRoute);
		} catch (PlinguaCoreException e) {
			// TODO Auto-generated catch block
			paneTuple.writeError(e.getMessage()+"\n");
		}
		return null;
	}

	protected boolean checkRoutes(String modelRoute, String parametersRoute) {
		if(modelRoute==null||modelRoute.isEmpty()){
			paneTuple.writeError("The model route field is empty\n");
			return false;
		}
		if(parametersRoute==null||parametersRoute.isEmpty()){
			paneTuple.writeError("The parameters route field is empty\n");	
			return false;
		}
		return true;
	}
	protected Psystem parseParameterizedPsystem(String plinguaFileRoute, String referenceRoute) throws PlinguaCoreException{
		return parsePsystem(createPsystemFile(plinguaFileRoute), plinguaFileRoute, referenceRoute);
	}
	
	
	protected Psystem parsePsystem(InputStream pLinguaStream, String plinguaFileRoute, String referenceRoute) throws PlinguaCoreException{
		AbstractParserFactory inputParserFactory = new InputParserFactory();
		InputParser inputParser = (InputParser) inputParserFactory
				.createParser("P-Lingua");
		inputParser.setVerbosityLevel(5);
		inputParser.setInfoChannel(paneTuple.getInfoChannel());
		inputParser.setWarningChannel(paneTuple.getWarningChannel());
		inputParser.setErrorChannel(paneTuple.getErrorChannel());
		/* Parse the file */
		Psystem psystem = null;
		if(referenceRoute==null||referenceRoute.isEmpty()){
			psystem = inputParser.parse(pLinguaStream,
				new String[] { plinguaFileRoute});
		}
		else{
			psystem = inputParser.parse(pLinguaStream,
					new String[] { plinguaFileRoute, referenceRoute });
		}
		writeResults(psystem);
		return psystem;
	}

	protected void writeResults(Psystem psystem) {
		if(psystem!=null)
			paneTuple.writeInfo("Parser finished without errors\n");
		else
			paneTuple.writeInfo("Errors ocurred during parsing\n");
	}

	protected FileInputStream createPsystemFile(String plinguaFileRoute)
			throws PlinguaCoreException {
		try{
			return new FileInputStream(plinguaFileRoute);
		}
		catch(FileNotFoundException fnfe){
			throw new PlinguaCoreException("File "+plinguaFileRoute+" not found");
		}
	}

	public int getStepsPerCycle() {
		// TODO Auto-generated method stub
		return parameterJoiner.getStepsPerCycle();
	}
	
	public DataReader getDataReader(){
		return parameterJoiner.getDataReader();
	}
}
