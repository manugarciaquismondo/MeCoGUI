package org.gcn.pLinguaCoreCSVApplication.writers.specificWriters.sqlite;

import java.util.Set;

import org.gcn.plinguacore.util.Pair;

public class OrganParameters {
	public static final double 
	VNCVecEnd1X[]={8.0d, -8.0d}, 
	VNCVecEnd1Y[]={176.5d, 176.5d},  
	VNCVecEnd2X[]={36.0d, -36.0d}, 
	VNCVecEnd2Y[]={-250.0d, -250.0d},  
	VNCVecX[]={-43.0d, 43.0d},  
	VNCVecY[]={0.0d, 0.0d},
	EyeVecX[]={-21.2132034355964d, 21.2132034355964d},
	EyeVecY[]={21.2132034355964d, 21.2132034355964d},
	BrainVecX[]={-25.9807621135332d, 25.9807621135332d},
	BrainVecY[]={15.0d, 15.0d},
	PharynxVecX[]={0.0d},
	PharynxVecY[]={0.0d},
	EyeVecRot[]={0.0d, 3.14159265358979d},
	BrainVecRot[]={6.02138591938044d, 3.40339204138894d},
	PharynxVecRot[]={1.5707963267949d};
	protected static final String TOP="top", BOTTOM="bottom", LEFT="left", RIGHT="right";
	protected static String downOneLink[]={LEFT, TOP, RIGHT}, upOneLink[]={RIGHT, BOTTOM, LEFT}, twoLinks[]={LEFT, RIGHT}, noLinks[]={TOP, LEFT, BOTTOM, RIGHT}, threeLinks[]={LEFT,RIGHT,BOTTOM};
	
	public static String getDirection(Set<String> directions, int index){
		if(directions.isEmpty()){
			return noLinks[index];
		}
		if(directions.size()==1){
			if(directions.contains("top")){
				return downOneLink[index];
			}
			if(directions.contains("bottom")){
				return upOneLink[index];
			}
		}
		else{
			if(directions.size()==2)
				return twoLinks[index];
			else{
				return threeLinks[index];
			}
		}
		return "";
		
	}

	
}
