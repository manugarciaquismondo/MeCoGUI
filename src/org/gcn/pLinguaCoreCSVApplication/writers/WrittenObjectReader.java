package org.gcn.pLinguaCoreCSVApplication.writers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.gcn.plinguacore.util.PlinguaCoreException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class WrittenObjectReader extends BasicWrittenObjectReader{
	
	private Set<String> writableObjects;
	private String XMLRoute="objects.xml";
	protected SAXBuilder builder;
	
	public WrittenObjectReader() {
		super();
		builder= new SAXBuilder();
		writableObjects = new HashSet<String>();
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean writableObject(String object){
		return writableObjects.contains(object);
	}
	@Override
	public boolean readWritableObjects() throws PlinguaCoreException{
		boolean parsed = false;
		try{
			InputStream stream = new FileInputStream(XMLRoute);
			parsed = parseParameters(stream);
			stream.close();
		}
		catch (IOException e) {
			throw new PlinguaCoreException("Errors found while reading writable objects from file ["+XMLRoute+"]");
		}
		catch(JDOMException e){
			throw new PlinguaCoreException("Errors found while building the XML file of writable objects from file ["+XMLRoute+"]");
		}
		return parsed;
	}

	
	private boolean parseParameters(InputStream stream) throws JDOMException, IOException {
		/* We can write P-systems on Write or OutputStream instances */
		Document document=builder.build(stream);
		readParameters(document);
		return true;
	
	}

	private void readParameters(Document document) {
		Element writtenElement = document.getRootElement().getChild("written");
		for (Object object : writtenElement.getChildren("object")) {
			Element element = (Element)object;
			writableObjects.add(element.getAttributeValue("name"));
			
		}
		
	}

}
