package org.gcn.pLinguaCoreCSVApplication.writers.specificWriters.sqlite;

public abstract class Organ {
	protected String type;
	protected int parameterIndex;
	private double vecX, vecY;
	public Organ(String type, double vecX, double vecY) {
		super();
		this.vecX=vecX;
		this.vecY=vecY;
		this.type = type;
	}
	
	public double getCurrentVecX(){
		return vecX;
	}
	public double getCurrentVecY(){
		return vecY;
	}
	
	

}
