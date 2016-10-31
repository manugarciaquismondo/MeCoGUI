package org.gcn.MeCoGUI.writers.specificWriters.sqlite;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.Triple;
import org.gcn.plinguacore.util.psystem.membrane.Membrane;

public class BodyPart {

	protected String type;
	protected Set<String> cellLabels;
	protected Set<String> connections, directions;
	protected Pair<Integer, Integer> size, initialPoint, finalPoint; //lengthSize, heightSize;
	protected Set<String> organs;
	private int maxLengthCoordinate;
	private int maxHeightCoordinate;
	private int minLengthCoordinate;
	private int minHeightCoordinate;
	
	public BodyPart(String type) {
		super();
		if (type == null) {
			throw new IllegalArgumentException("Argument of type "
					+ String.class
					+ " cannot be null when creating an object of type "
					+ getClass());
		}
		this.type = type;
		cellLabels=new HashSet<String>();
		connections=new HashSet<String>();
		directions=new HashSet<String>();
		organs=new HashSet<String>();
		size=new Pair<Integer, Integer>(0, 0);
		initialPoint=new Pair<Integer, Integer>(0, 0);
		finalPoint=new Pair<Integer, Integer>(0, 0);
	}

	public String getType() {
		return type;
	}

	public int getLengthSize() {
		return size.getFirst();
	}

	public int getHeightSize() {
		return size.getSecond();
	} 
	
	public void addCell(String label){
		this.cellLabels.add(label);
	}
	
	public void addConnection(String label){
		this.connections.add(label);
	}
	
	public void addOrgan(String organ){
		this.organs.add(organ);
	}
	

	public void extractCellsAndDimensions(
			Collection<? extends Membrane> allMembranes, Set<String> bodyRegionObjects, Set<String> organObjects, String environmentLabel, boolean fetched){

		Set<Membrane> relevantMembranes = extractRelevantMembranes(
				allMembranes, bodyRegionObjects, environmentLabel, fetched);
		initializeCoordinates();
		calculateOrgansSizesAndCoordinates(organObjects, relevantMembranes);
		registerSizesAndCoordinates();
	}

	protected void registerSizesAndCoordinates() {
		size.setFirst(maxLengthCoordinate-minLengthCoordinate+1);
		size.setSecond(maxHeightCoordinate-minHeightCoordinate+1);
		initialPoint.setFirst(minLengthCoordinate);
		initialPoint.setSecond(minHeightCoordinate);
		finalPoint.setFirst(maxLengthCoordinate);
		finalPoint.setSecond(maxHeightCoordinate);
	}

	protected void calculateOrgansSizesAndCoordinates(Set<String> organObjects,
			Set<Membrane> relevantMembranes) {
		for(Membrane membrane: relevantMembranes){
			String membraneLabel=membrane.getLabel();
			cellLabels.add(membraneLabel);
			String labelIndexes[]=membraneLabel.split(",");
			int localeLength=Integer.parseInt(labelIndexes[0]);
			int localeHeight=Integer.parseInt(labelIndexes[1]);
			maxLengthCoordinate=Math.max(maxLengthCoordinate,localeLength);
			maxHeightCoordinate=Math.max(maxHeightCoordinate,localeHeight);
			minLengthCoordinate=Math.min(minLengthCoordinate,localeLength);
			minHeightCoordinate=Math.min(minHeightCoordinate,localeHeight);
			Set<String> membraneOrganObjects=new HashSet<String>(membrane.getMultiSet().entrySet());
			membraneOrganObjects.retainAll(organObjects);
			organs.addAll(membraneOrganObjects);
		}
	}

	protected void initializeCoordinates() {
		maxLengthCoordinate = Integer.MIN_VALUE;
		maxHeightCoordinate = Integer.MIN_VALUE;
		minLengthCoordinate = Integer.MAX_VALUE;
		minHeightCoordinate = Integer.MAX_VALUE;
	}

	protected Set<Membrane> extractRelevantMembranes(
			Collection<? extends Membrane> allMembranes,
			Set<String> bodyRegionObjects, String environmentLabel,
			boolean fetched) {
		Set<Membrane> relevantMembranes=new HashSet<Membrane>();
		for(Membrane membrane: allMembranes){
			Set<String> membraneBodyObjects=new HashSet<String>(membrane.getMultiSet().entrySet());
			membraneBodyObjects.retainAll(bodyRegionObjects);
			if(membraneBodyObjects.isEmpty()!=fetched){
				String membraneLabel=membrane.getLabel();
				if(!membraneLabel.equals(environmentLabel)){
					relevantMembranes.add(membrane);
				}
			}
		}
		return relevantMembranes;
	}

	public Set<String> getCells(){
		return new HashSet<String>(cellLabels);
	}
	
	public Set<String> getConnections(){
		return new HashSet<String>(connections);
	}
	public Set<String> getOrgans(){
		return new HashSet<String>(organs);
	}
	public boolean containsOrgan(String organ){
		return organs.contains(organ);
	}
	public boolean containsCell(String label){
		return cellLabels.contains(label);
	}
	
	public boolean isEquivalent(BodyPart bodyPart){
		return areConnectionsEquivalent(bodyPart)&&areTypesEquivalent(bodyPart)&&areOrgansEquivalent(bodyPart);
	}
	
	private boolean areOrgansEquivalent(BodyPart bodyPart) {
		// TODO Auto-generated method stub
		return bodyPart.getOrgans().containsAll(getOrgans())&&getOrgans().containsAll(bodyPart.getOrgans());
	}

	private boolean areTypesEquivalent(BodyPart bodyPart) {
		// TODO Auto-generated method stub
		return bodyPart.getType().equals(getType());
	}

	public boolean areConnectionsEquivalent(BodyPart bodyPart){
		return bodyPart.getConnections().containsAll(getConnections())&&getConnections().containsAll(bodyPart.getConnections());
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(!(obj instanceof BodyPart))
			return false;
		BodyPart objBodyPart=(BodyPart)obj;
		if(!objBodyPart.getType().equals(getType()))
			return false;
		if(!areEquivalent(getCells(), objBodyPart.getCells()))
			return false;
		if(!areEquivalent(getConnections(), objBodyPart.getConnections()))
			return false;
		if(!areEquivalent(getOrgans(), objBodyPart.getOrgans()))
			return false;
		if(!hasSameDimensions(objBodyPart))
			return false;
		return true;
	}

	private boolean areEquivalent(Set<String> objectSet1, Set<String> objectSet2) {
		return objectSet1.containsAll(objectSet2)&&objectSet2.containsAll(objectSet1);
	}

	private boolean hasSameDimensions(BodyPart objBodyPart) {
		// TODO Auto-generated method stub
		return objBodyPart.getLengthSize()==this.getLengthSize()&&objBodyPart.getHeightSize()==getHeightSize();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Type: "+getType()+", Organs: {"+getOrgans()+"}, Connections: {"+getConnections()+"}";
	}
	
	public List<Pair<Integer, Integer>> getDelimitingPoints(){
		 List<Pair<Integer, Integer>> delimitingPoints=new LinkedList<Pair<Integer, Integer>>();
		 delimitingPoints.add(initialPoint);
		 delimitingPoints.add(finalPoint);
		return delimitingPoints;
	}

	public void addDirection(String direction) {
		directions.add(direction);
		
	}
	
	public Set<String> getDirections() {
		return new HashSet<String>(directions);
		
	}
}
