package org.gcn.MeCoGUI.writers.specificWriters;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.gcn.MeCoGUI.paneStreams.PaneTuple;
import org.gcn.MeCoGUI.writers.BasicWrittenObjectReader;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;

import com.opencsv.CSVWriter;

public class RegenerativeResultsWriter extends
		ProbabilisticGuardedResultsWriter {
	
	protected static RegenerativeResultsWriter instance;
	
	protected RegenerativeResultsWriter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static RegenerativeResultsWriter getInstance(){
		if(instance==null){
			instance=new RegenerativeResultsWriter();
		}
		return instance;
		
	}


	protected int maxNumMembranes;
	protected final int HEADER_LENGTH=3, OBJECT_STAGE=0, LINK_STAGE=1;
	protected int writeStage;
	protected String fileNameRoot;
	

	



	@Override
	public void writeResults(String destinationRoute) {
		// TODO Auto-generated method stub
		objectReader=new BasicWrittenObjectReader();
		writeStage=OBJECT_STAGE;
		super.writeResults(destinationRoute);
		String destinationDirectory=destinationRoute.substring(0, destinationRoute.lastIndexOf("\\"));
		fileNameRoot = destinationRoute.substring(destinationRoute.lastIndexOf("\\"), destinationRoute.length());
		fileNameRoot=fileNameRoot.substring(0, fileNameRoot.lastIndexOf("."));
		String destinationLinkRoute=destinationDirectory+fileNameRoot+"links.csv";
		writeStage=LINK_STAGE;
		super.writeResults(destinationLinkRoute);
	}

	

	protected IPsystemItemWriter createIPsystemItemWriter() {
		IPsystemItemWriter result=null;
		switch(writeStage){
			case(OBJECT_STAGE):
				result=new ObjectWithIDMembraneWriter(objectReader);
				break;
			case(LINK_STAGE):
				result=new LinkMembraneWriter();
				break;
		}
		return result;
	}
	

}
