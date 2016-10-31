package org.gcn.MeCoGUI.writers.specificWriters.sqlite;

public class SpotOrgan extends Organ{
	
	private double rotation;

	public SpotOrgan(String type, double vecX, double vecY, double rotation) {
		super(type, vecX, vecY);
		this.rotation=rotation;
		// TODO Auto-generated constructor stub
	}
	
	public double getCurrentRotation(){
		return rotation;
	}

	
	

}
