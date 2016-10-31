package org.gcn.pLinguaCoreCSVApplication.writers.specificWriters.sqlite;

public class NeighborChecker {
	public boolean areNeighbors(String label1, String label2) {
		String labelIndexes1[]=label1.split(",");
		String labelIndexes2[]=label2.split(",");
		return checkIndexAdjacency(labelIndexes1, labelIndexes2, 0)||checkIndexAdjacency(labelIndexes1, labelIndexes2, 1);
	}

	protected boolean checkIndexAdjacency(String[] labelIndexes1,
			String[] labelIndexes2, int index) {
		if((labelIndexes1.length>=3||labelIndexes2.length>=3)&&(!labelIndexes1[2].equals(labelIndexes2[2]))){
				return false;
		} else{
			int otherIndex=index-1;
			if(labelIndexes1[index].equals(labelIndexes2[index])){
				if(Math.abs(Integer.parseInt(labelIndexes1[otherIndex])-Integer.parseInt(labelIndexes2[otherIndex]))<=1)
					return true;
			}
		}
		return false;
	}
}
