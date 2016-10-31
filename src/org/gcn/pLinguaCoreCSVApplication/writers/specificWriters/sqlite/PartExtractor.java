package org.gcn.pLinguaCoreCSVApplication.writers.specificWriters.sqlite;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.Triple;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;
import org.gcn.plinguacore.util.psystem.regenerative.membrane.RegenerativeMembrane;
import org.gcn.plinguacore.util.psystem.tissueLike.membrane.TissueLikeMembraneStructure;

public class PartExtractor {
	
	protected TissueLikeMembraneStructure currentMembraneStructure;
	protected List<Morphology> morphologies;
	List<Configuration> computation;
	protected Set<String> bodyRegionObjects, organObjects;
	protected final String HEAD_STRING="head", TAIL_STRING="tail", TRUNK_STRING="trunk";
	protected final String LEFTEYE_STRING="lefteye", RIGHTEYE_STRING="righteye", LEFTLOBE_STRING="leftlobe", RIGHTLOBE_STRING="rightlobe", LEFTNERVE_STRING="leftnerve", RIGHTNERVE_STRING="rightnerve", PHARYNX_STRING="pharynx";
	private NeighborChecker neighborChecker;
	
	public PartExtractor(List<Configuration> computation) {
		super();
		bodyRegionObjects=new HashSet<String>();
		organObjects=new HashSet<String>();
		if (computation == null) {
			throw new IllegalArgumentException("Argument of type "
					+ List.class
					+ " cannot be null when creating an object of type "
					+ getClass());
		}
		this.computation = computation;
		neighborChecker=new NeighborChecker();
		setUpObjectsOfInterest();
		// TODO Auto-generated constructor stub
	}

	public int getNumberOfMorphologies(){
		return morphologies.size();
	}
	public void getBodyParts(){
		morphologies = new LinkedList<Morphology>();
		Morphology previousMorphology=null;
		for(Configuration configuration: computation){
			previousMorphology=setStructureParts((TissueLikeMembraneStructure)configuration.getMembraneStructure(), previousMorphology, configuration.getNumber());
		}
	}

	protected Morphology setStructureParts(TissueLikeMembraneStructure structure, Morphology previousMorphology, int configurationNumber) {
		currentMembraneStructure=structure;
		BodyPart head, tail, trunk;
		head = getSide(HEAD_STRING);
		tail = getSide(TAIL_STRING);
		trunk = getTrunk();
		checkAndAddConnections(head, HEAD_STRING, tail, TAIL_STRING);
		checkAndAddConnections(head, HEAD_STRING, trunk, TRUNK_STRING);
		checkAndAddConnections(tail, TAIL_STRING, trunk, TRUNK_STRING);
		Morphology morphology = new Morphology("SampleMorphology"+(morphologies.size()+1)+"-"+configurationNumber);
		morphology.addBodyPart(head);
		morphology.addBodyPart(tail);
		morphology.addBodyPart(trunk);
		if(previousMorphology==null||!morphology.isEquivalent(previousMorphology)){
			morphologies.add(morphology);
			return morphology;
		}
		else{
			return previousMorphology;
		}
	}


	protected void checkAndAddConnections(BodyPart bodypart1, String partname1,
			BodyPart bodypart2, String partname2){
		if(haveConnections(bodypart1, partname1, bodypart2, partname2)){
			String direction=getDirection(bodypart1.getDelimitingPoints(), bodypart2.getDelimitingPoints());
			bodypart1.addConnection(partname2);
			bodypart2.addConnection(partname1);
			bodypart1.addDirection(direction);
			bodypart2.addDirection(getOppositeDirection(direction));
		}
	}
	
	private String getDirection(List<Pair<Integer, Integer>> delimitingPoints1,
			List<Pair<Integer, Integer>> delimitingPoints2) {
		// TODO Auto-generated method stub
		Pair<Integer, Integer> initialPoint1, initialPoint2, finalPoint1, finalPoint2;
		initialPoint1=delimitingPoints1.get(0);
		initialPoint2=delimitingPoints2.get(0);
		finalPoint1=delimitingPoints1.get(1);
		finalPoint2=delimitingPoints2.get(1);
		if(initialPoint1.getFirst()==finalPoint2.getFirst()+1)
			return OrganParameters.BOTTOM;
		if(finalPoint1.getFirst()==initialPoint2.getFirst()-1)
			return OrganParameters.TOP;
		if(initialPoint1.getSecond()==finalPoint2.getSecond()+1)
			return OrganParameters.RIGHT;
		if(finalPoint1.getSecond()==initialPoint2.getSecond()-1)
			return OrganParameters.LEFT;
		throw new IllegalArgumentException("Parts delimited by {"+initialPoint1+", "+finalPoint1+"} and {"+initialPoint1+", "+finalPoint1+"} are not adjacent");
	}
	
	private String getOppositeDirection(String direction){
		switch(direction){
		case OrganParameters.TOP: return OrganParameters.BOTTOM;
		case OrganParameters.BOTTOM: return OrganParameters.TOP;
		case OrganParameters.LEFT: return OrganParameters.RIGHT;
		case OrganParameters.RIGHT: return OrganParameters.LEFT;
		default: return "";
		}
	}

	private boolean haveConnections(
			BodyPart bodypart1, String partname1, BodyPart bodypart2, String partname2) {

		for(String label1: bodypart1.getCells()){
			RegenerativeMembrane membrane = (RegenerativeMembrane)currentMembraneStructure.iterator(label1).next();
			List<Integer> linkedMembranes=membrane.getLinkedMembranes();
			for(int linkedMembraneId: linkedMembranes){
				String label2=currentMembraneStructure.getMembrane(linkedMembraneId).getLabel();
				if(bodypart2.containsCell(label2)&&areNeighbors(label1, label2)){
					return true;
				}
			}
			
		}
		return false;
		// TODO Auto-generated method stub
		
	}

	private boolean areNeighbors(String label1, String label2) {
		return neighborChecker.areNeighbors(label1, label2);
	}


	private BodyPart getTrunk() {
		BodyPart trunk = new BodyPart(TRUNK_STRING);
		trunk.extractCellsAndDimensions(currentMembraneStructure.getAllMembranes(), bodyRegionObjects, organObjects, currentMembraneStructure.getEnvironmentLabel(), false);
		return trunk;
	}

	protected BodyPart getSide(String idobject){
		Set<String> fetchedObjects=new HashSet<String>();
		fetchedObjects.add(idobject);
		BodyPart trunk = new BodyPart(idobject);
		trunk.extractCellsAndDimensions(currentMembraneStructure.getAllMembranes(), fetchedObjects, organObjects, currentMembraneStructure.getEnvironmentLabel(), true);
		return trunk;
	
	}

	
	private void setUpObjectsOfInterest() {
		bodyRegionObjects.add(HEAD_STRING);
		bodyRegionObjects.add(TAIL_STRING);
		organObjects.add(LEFTEYE_STRING);
		organObjects.add(RIGHTEYE_STRING);
		organObjects.add(LEFTLOBE_STRING);
		organObjects.add(RIGHTLOBE_STRING);
		organObjects.add(LEFTNERVE_STRING);
		organObjects.add(RIGHTNERVE_STRING);
		organObjects.add(PHARYNX_STRING);
		
	}
	
	protected Morphology getMorphology(int index){
		return morphologies.get(index);
	}
	
	protected BodyPart getGenericBodyPart(int index, String partName){
		return getMorphology(index).getBodyPart(partName);
	}
	
	public BodyPart getHead(int index){
		return getGenericBodyPart(index, HEAD_STRING);
	}
	
	public BodyPart getTail(int index){
		return getGenericBodyPart(index, TAIL_STRING);
	}
	
	public BodyPart getTrunk(int index){
		return getGenericBodyPart(index, TRUNK_STRING);
	}
	
}
