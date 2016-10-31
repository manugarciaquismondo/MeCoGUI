package org.gcn.pLinguaCoreCSVApplication.writers;

import java.lang.reflect.InvocationTargetException;

import org.gcn.pLinguaCoreCSVApplication.OleraceaGUIPanel;
import org.gcn.pLinguaCoreCSVApplication.SimulatorHolder;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.factory.AbstractPsystemFactory;
import org.gcn.plinguacore.util.psystem.factory.IModelsClassProvider;
import org.gcn.plinguacore.util.psystem.factory.IPsystemFactory;

public class SimulatorResultsWriterProvider {

	protected static XmlSimulatorResultsWritersResource singletonSimulatorHoldersClassProvider;
	public SimulatorResultsWriterProvider() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	protected static XmlSimulatorResultsWritersResource getSimulatorWritersClassProvider()
	{
		if (singletonSimulatorHoldersClassProvider==null)
			singletonSimulatorHoldersClassProvider= new XmlSimulatorResultsWritersResource();
		return singletonSimulatorHoldersClassProvider;
	}
	
	public static SimulatorResultsWriter createSimulatorResultsWriterFromPsystemClass(String modelClassName) throws PlinguaCoreException{
		String modelId=((IModelsClassProvider)AbstractPsystemFactory.getModelsInfo()).getModelId(modelClassName);
		return createSimulatorResultsWriter(getSimulatorWritersClassProvider().getModelClassName(
				modelId));
	}
	
	public static SimulatorResultsWriter createSimulatorResultsWriterFromPsystemID(String pSystemId) throws PlinguaCoreException{
		return createSimulatorResultsWriter(getSimulatorWritersClassProvider().getModelClassName(
				pSystemId));
	}
	
	public static SimulatorResultsWriter createSimulatorResultsWriter(String resultsWriterClassName)
			throws PlinguaCoreException {
		Class<?> c = null;
		try {
			/* First, the class is loaded */
			c = Class.forName(resultsWriterClassName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new PlinguaCoreException("The class for " + resultsWriterClassName
					+ " simulator writer doesn't exist: " + e.getMessage());
		}
		
		

		SimulatorResultsWriter result = null;
		try {
			/*
			 * Since the class is a singleton, we need to invoke the getInstance
			 * method
			 */
			result = (SimulatorResultsWriter) (c.getMethod("getInstance"))
					.invoke(null);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			throw new PlinguaCoreException("The class for " + resultsWriterClassName
					+ " simulator writer cannot be instantiated: " + e.getMessage());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			throw new PlinguaCoreException("The class for " + resultsWriterClassName
					+ " simulator writer cannot be accessed: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			throw new PlinguaCoreException(e.getMessage());
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			throw new PlinguaCoreException(e.getMessage());
		}
		return result;

	}


}
