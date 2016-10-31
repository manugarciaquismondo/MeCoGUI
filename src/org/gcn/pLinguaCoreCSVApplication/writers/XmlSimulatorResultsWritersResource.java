package org.gcn.pLinguaCoreCSVApplication.writers;

import org.gcn.pLinguaCoreCSVApplication.SimulatorHolder;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.XmlFileIDs;
import org.gcn.plinguacore.util.psystem.factory.IPsystemFactory;
import org.jdom.Document;
import org.jdom.Element;

public class XmlSimulatorResultsWritersResource extends XmlFileIDs{

	
	private static final String DIRECTORY_ROUTE = "/resources/";
	private static final String DIRECTORY_FILENAME = "writers.xml";
	
	public XmlSimulatorResultsWritersResource() {
		super(DIRECTORY_ROUTE+DIRECTORY_FILENAME);

	}
	@Override
	protected String getRootDirectory() {
		// TODO Auto-generated method stub
		return "org/gcn/pLinguaCoreCSVApplication";
	}

	protected String getFileString(){
		return "writers";
	}

	
	public String getModelClassName(String simulatorWriter) throws PlinguaCoreException {

		try {
			return idClassName(simulatorWriter, getFileString());
		} catch (PlinguaCoreException e) {

			throw new PlinguaCoreException(simulatorWriter + " simulator writer not supported");
		}

	}


	
	@Override
	protected void readDocument(Document doc) {

		Element root = doc.getRootElement();
		addSingletonID(root, getFileString(), "writer", SimulatorResultsWriter.class);

	}

}
