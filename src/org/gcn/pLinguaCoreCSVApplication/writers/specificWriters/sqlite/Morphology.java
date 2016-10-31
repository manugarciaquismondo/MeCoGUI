package org.gcn.pLinguaCoreCSVApplication.writers.specificWriters.sqlite;

import java.util.LinkedList;
import java.util.List;

public class Morphology {
	List<BodyPart> bodyParts;
	protected String name;

	public Morphology(String name) {
		super();
		// TODO Auto-generated constructor stub
		bodyParts=new LinkedList<BodyPart>();
		if (name == null) {
			throw new IllegalArgumentException("Argument of type "
					+ String.class
					+ " cannot be null when creating an object of type "
					+ getClass());
		}
		this.name = name;
	}

	public void addBodyPart(BodyPart bodyPart){
		if (bodyPart == null) {
			throw new IllegalArgumentException("Argument of type "
					+ BodyPart.class
					+ " cannot be null when creating an object of type "
					+ getClass());
		}
		if(!bodyPart.getCells().isEmpty()){
			bodyParts.add(bodyPart);
		}
	}
	
	public List<BodyPart> getBodyParts(){
		return new LinkedList<BodyPart>(bodyParts);
	}
	
	public int numberOfBodyParts(){
		return bodyParts.size();
	}
	
	public boolean isEquivalent(Morphology morphology){
		for(BodyPart bodyPart:getBodyParts()){
			if(!morphology.containsBodyPart(bodyPart))
				return false;
		}
		for(BodyPart bodyPart:morphology.getBodyParts()){
			if(!containsBodyPart(bodyPart))
				return false;
		}
		return numberOfBodyParts()==morphology.numberOfBodyParts();
	}

	private boolean containsBodyPart(BodyPart bodyPart) {
		// TODO Auto-generated method stub
		for(BodyPart iteratedBodyPart:getBodyParts()){
			if(bodyPart.isEquivalent(iteratedBodyPart))
				return true;
		}
		return false;
	}
	
	public BodyPart getBodyPart(String name){
		for(BodyPart bodyPart: bodyParts){
			if(bodyPart.getType().equals(name))
				return bodyPart;
		}
		return null;
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Morphology: "+name+", Body parts: {"+bodyParts.toString()+"}";
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public int numberOfRegions() {
		// TODO Auto-generated method stub
		return bodyParts.size();
	}
	
	
	
}
