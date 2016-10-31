package org.gcn.pLinguaCoreCSVApplication.writers.specificWriters.sqlite;

public class LineOrgan extends Organ {
	private double vecEnd1X, vecEnd1Y, vecEnd2X, vecEnd2Y;

	public LineOrgan(String type, double vecX, double vecY, double vecEnd1X, double vecEnd1Y, double vecEnd2X, double vecEnd2Y) {
		super(type, vecX, vecY);
		this.vecEnd1X=vecEnd1X;
		this.vecEnd1Y=vecEnd1Y;
		this.vecEnd2X=vecEnd2X;
		this.vecEnd2Y=vecEnd2Y;
			
		// TODO Auto-generated constructor stub
	}
	
	public double getCurrentVecEnd1X(){
		return vecEnd1X;
	}
	public double getCurrentVecEnd1Y(){
		return vecEnd1Y;
	}
	public double getCurrentVecEnd2X(){
		return vecEnd2X;
	}
	public double getCurrentVecEnd2Y(){
		return vecEnd2Y;
	}
}
