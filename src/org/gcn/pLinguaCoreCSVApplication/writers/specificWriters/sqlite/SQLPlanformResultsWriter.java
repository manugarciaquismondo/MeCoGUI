package org.gcn.pLinguaCoreCSVApplication.writers.specificWriters.sqlite;

import org.gcn.pLinguaCoreCSVApplication.WorkspaceRouteProvider;
import org.gcn.pLinguaCoreCSVApplication.writers.specificWriters.RegenerativeResultsWriter;
import org.gcn.plinguacore.util.Pair;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Configuration;

import java.io.PrintStream;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SQLPlanformResultsWriter extends RegenerativeResultsWriter {

	protected static SQLPlanformResultsWriter instance;
	protected int morphologyIds[];
	protected Map<String, Integer> bodyPartIds, spotOrganIds, lineOrganIds;
	protected int headPartIds[], tailPartIds[], trunkPartIds[];
	protected String resourcesDir;
	protected Statement entryStatement;
	protected PartExtractor partExtractor;
	protected final String HEAD_STRING="Head", TAIL_STRING="Tail", TRUNK_STRING="Trunk";
	protected final String BRAIN_STRING="Brain", VNC_STRING="VNC", EYE_STRING="Eye", PHARYNX_STRING="Phar.";
	protected final String LEFT_EYE="lefteye", RIGHT_EYE="righteye", LEFT_LOBE="leftlobe", RIGHT_LOBE="rightlobe", LEFT_NERVE="leftnerve", RIGHT_NERVE="rightnerve", PHARYNX="pharynx";
	protected final String DB_FILE_NAME = "planformDB_2.0.1.edb";
	protected final double DB_DISTANCE=200.0d, DB_RATIO=0.5d, DB_ANGLE_UNIT=4.71238898038469d, DB_PARAM_DEFAULT_VALUE=60.0d, DB_EYE_ROTATION=0.0d;
	protected Map<String, Organ> organMap;
	protected Morphology currentMorphology;
	protected final double DIRECTION_DIVISOR_FACTOR[]={1.0d, 1.0d};
	protected WorkspaceRouteProvider workspaceRouteProvider;
	

	protected final int PARAMETERS_PER_REGION_WITH_ONE_LINK=3, PARAMETERS_PER_REGION_WITH_NO_LINK=4;
	
	
	protected double currentAngle;
	protected int numberOfMorphologies;
	protected Set<Pair<Integer, Integer>> linkedRegionDBIds;
	protected Connection c;
	
	

	protected SQLPlanformResultsWriter() {
		super();
		linkedRegionDBIds=new HashSet<Pair<Integer,Integer>>();
		bodyPartIds=new HashMap<String, Integer>();
		spotOrganIds=new HashMap<String, Integer>();
		lineOrganIds=new HashMap<String, Integer>();
		fillInOrganMap();
		workspaceRouteProvider = new WorkspaceRouteProvider();
		setResourcesDir(getDBDirectory());
	}
	
	private void fillInOrganMap() {
		organMap=new HashMap<String, Organ>();
		organMap.put(LEFT_NERVE, new LineOrgan(LEFT_NERVE, OrganParameters.VNCVecX[0], OrganParameters.VNCVecY[0], OrganParameters.VNCVecEnd1X[0], OrganParameters.VNCVecEnd1Y[0], OrganParameters.VNCVecEnd2X[0], OrganParameters.VNCVecEnd2Y[0]));
		organMap.put(RIGHT_NERVE, new LineOrgan(RIGHT_NERVE, OrganParameters.VNCVecX[1], OrganParameters.VNCVecY[1], OrganParameters.VNCVecEnd1X[1], OrganParameters.VNCVecEnd1Y[1], OrganParameters.VNCVecEnd2X[1], OrganParameters.VNCVecEnd2Y[1]));
		organMap.put(LEFT_EYE, new SpotOrgan(LEFT_EYE, OrganParameters.EyeVecX[0], OrganParameters.EyeVecY[0], OrganParameters.EyeVecRot[0]));
		organMap.put(RIGHT_EYE, new SpotOrgan(RIGHT_EYE, OrganParameters.EyeVecX[1], OrganParameters.EyeVecY[1], OrganParameters.EyeVecRot[1]));		
		organMap.put(LEFT_LOBE, new SpotOrgan(LEFT_LOBE, OrganParameters.BrainVecX[0], OrganParameters.BrainVecY[0], OrganParameters.BrainVecRot[0]));
		organMap.put(RIGHT_LOBE, new SpotOrgan(RIGHT_LOBE, OrganParameters.BrainVecX[1], OrganParameters.BrainVecY[1], OrganParameters.BrainVecRot[1]));
		organMap.put(PHARYNX, new SpotOrgan(PHARYNX, OrganParameters.PharynxVecX[0], OrganParameters.PharynxVecY[0], OrganParameters.PharynxVecRot[0]));

	}

	protected String getApplicationDirectory(){

		return workspaceRouteProvider.getWorkspaceRoute();
	}
	
	protected String getDBDirectory(){
		return getApplicationDirectory()+"pLinguaCoreOleraceaWrapper/src/org/gcn/pLinguaCoreCSVApplication/resources/";
	}
	

	public void printDatabaseResultsAndTreatExceptions(List<Configuration> computation){
		try{
			printDatabaseResults(computation);
		}
		catch(Exception e){
			printError("Errors occurred while storing computation into the database: "+getResourcesDir()+DB_FILE_NAME+".");
			
		}
	}

	private void printDatabaseResults(List<Configuration> computation) {
		currentAngle=DB_ANGLE_UNIT;
		linkedRegionDBIds.clear();
		partExtractor= new PartExtractor(computation);
		partExtractor.getBodyParts();
		numberOfMorphologies=partExtractor.getNumberOfMorphologies();
		if(!openConnection()||!getBodyPartIds()||!insertBodyPartRegisters()||!addBodyPartLinks()){
			closeDBSafely();
			printInfo("");
			return;
		}
		closeConnection();
		printInfo("");
	}


	protected boolean addBodyPartLinks() {
		Set<String> adjacentBodyParts=new HashSet<String>();
		adjacentBodyParts.add(HEAD_STRING.toLowerCase());
		adjacentBodyParts.add(TAIL_STRING.toLowerCase());
		adjacentBodyParts.add(TRUNK_STRING.toLowerCase());
		try{
			for(int index=0; index<numberOfMorphologies; index++){
				insertLink(partExtractor.getHead(index), headPartIds[index], index, HEAD_STRING.toLowerCase());
				insertLink(partExtractor.getTail(index), tailPartIds[index], index, TAIL_STRING.toLowerCase());
				insertLink(partExtractor.getTrunk(index), trunkPartIds[index], index, TRUNK_STRING.toLowerCase());
			}
		}
		catch(SQLException exception){
			return false;
		}
		return true;

	}

	protected boolean insertLink(BodyPart bodyPart, int partIndex, int index, String regionString) throws SQLException{
		if(bodyPart!=null){
			addLinks(partIndex, bodyPart.getConnections(), index, regionString.toLowerCase());
			return true;
		}
		return false;
	}

	protected void closeConnection() {
		try{
			c.close();
			printInfo("Database "+DB_FILE_NAME+" closed successfully");
		}
		catch(SQLException e){
			printError("Database "+DB_FILE_NAME+" could not be properly closed: "+e.getMessage());
			return;
		}
	}
	
	private boolean addLinks(int partId, Set<String> linkedParts, int morphologyIndex, String regionName) throws SQLException{
		if(partId>=0){
			for(String linkedPart: linkedParts){
				int linkedPartId=getIdFromString(linkedPart, morphologyIndex);
				try{
					insertLink(partId, linkedPartId);
				}
				catch(SQLException exception){
					printError("Error while inserting body part link between "+regionName+" with ID "+partId+" and "+linkedPart+" with ID "+linkedPartId+": " + exception.getMessage() );
					throw exception;
				}
				
			}
		}
		return true;
		
	}


	private void insertLink(int partId, int linkedPartId) throws SQLException {
		int queryCount=entryStatement.executeQuery("select count('Id') from 'RegionsLink' where ('RegionsLink.FromRegion' = "+partId+" and 'RegionsLink.ToRegion' = "+linkedPartId+") or ('RegionsLink.FromRegion' = "+linkedPartId+" and 'RegionsLink.ToRegion' = "+partId+");").getInt(1);
		Pair<Integer, Integer> linkedPair=new Pair<Integer, Integer>(partId, linkedPartId);
		Pair<Integer, Integer> inverseLinkedPair=new Pair<Integer, Integer>(linkedPartId, partId);
		if(queryCount<=0&&!linkedRegionDBIds.contains(linkedPair)){
			entryStatement.executeUpdate("insert into 'RegionsLink' (FromRegion, ToRegion, Dist, Ratio, Ang) values ("+partId+", "+linkedPartId+", "+DB_DISTANCE+", "+DB_RATIO+", "+currentAngle+");");
			currentAngle+=DB_ANGLE_UNIT*2;
		}
		linkedRegionDBIds.add(linkedPair);
		linkedRegionDBIds.add(inverseLinkedPair);
		
	}


	protected int getIdFromString(String bodyPartName, int morphologyIndex){
		switch(bodyPartName.toLowerCase()){
			case "head": return headPartIds[morphologyIndex];
			case "tail": return tailPartIds[morphologyIndex];
			case "trunk": return trunkPartIds[morphologyIndex];
		}
		return -1;
	}


	protected boolean insertBodyPartRegisters() {
		headPartIds=new int[numberOfMorphologies];
		tailPartIds=new int[numberOfMorphologies];
		trunkPartIds=new int[numberOfMorphologies];
		try{
			for(int index=0; index<numberOfMorphologies; index++){
				currentMorphology=partExtractor.getMorphology(index);
				headPartIds[index]=insertBodyPart(partExtractor.getHead(index), bodyPartIds.get(HEAD_STRING), morphologyIds[index]);
				tailPartIds[index]=insertBodyPart(partExtractor.getTail(index), bodyPartIds.get(TAIL_STRING), morphologyIds[index]);
				trunkPartIds[index]=insertBodyPart(partExtractor.getTrunk(index), bodyPartIds.get(TRUNK_STRING), morphologyIds[index]);
			}
		}
		catch(SQLException e){
			printError("Error while inserting body part registers: " + e.getMessage() );
			return false;
		}
		return true;
	}

	protected void printError(String errorMessage){
		PrintStream usedErrorChannel=null;
		if(paneTuple!=null){
			usedErrorChannel=paneTuple.getErrorChannel();
		}
		else{
			usedErrorChannel=System.err;
		}
		usedErrorChannel.println(errorMessage);
	}
	
	protected void printInfo(String infoMessage){
		PrintStream usedInfoChannel=null;
		if(paneTuple!=null){
			usedInfoChannel=paneTuple.getInfoChannel();
		}
		else{
			usedInfoChannel=System.out;
		}
		usedInfoChannel.println(infoMessage);
	}

	protected boolean getBodyPartIds() {
		try{
			bodyPartIds.put(HEAD_STRING, getBodyPartIndex(HEAD_STRING));
			bodyPartIds.put(TAIL_STRING, getBodyPartIndex(TAIL_STRING));
			bodyPartIds.put(TRUNK_STRING, getBodyPartIndex(TRUNK_STRING));
			spotOrganIds.put(EYE_STRING, getSpotOrganPartIndex(EYE_STRING));
			spotOrganIds.put(BRAIN_STRING, getSpotOrganPartIndex(BRAIN_STRING));
			spotOrganIds.put(PHARYNX_STRING, getSpotOrganPartIndex(PHARYNX_STRING));
			lineOrganIds.put(VNC_STRING, getLineOrganPartIndex(VNC_STRING));
		}
		catch(SQLException e){
			printError("Error while obtaining body part Ids: " + e.getMessage() );
			return false;
		}
		return true;
	}

	private int getLineOrganPartIndex(String organString) throws SQLException {
		// TODO Auto-generated method stub
		return getOrganPartIndex(organString, "LineOrganType");
	}

	private int getSpotOrganPartIndex(String organString) throws SQLException {
		// TODO Auto-generated method stub
		return getOrganPartIndex(organString, "SpotOrganType");
	}

	private int getOrganPartIndex(String organString, String table) throws SQLException {
		// TODO Auto-generated method stub
		ResultSet maxMorphologyIdSet=entryStatement.executeQuery("select Id from "+table+" where name like '"+organString+"';");
		return maxMorphologyIdSet.getInt("Id");		
	}

	private int insertBodyPart(
			BodyPart bodyPart,
			int partId, int index) throws SQLException {
		if(partId>=0&&bodyPart!=null&&!bodyPart.getCells().isEmpty()){
			entryStatement.executeUpdate("INSERT INTO 'Region' (Morphology,Type) VALUES ("+index+", "+partId+");");
			int bodyPartId= entryStatement.executeQuery("select Region.Id from 'Region' where Region.Morphology="+index+" and Region.Type="+partId+";").getInt("Id");
			if(!insertRegionParameters(bodyPart, bodyPartId, getNumberOfParameters(bodyPart.getConnections().size()))){
				return -1;
			}
			if(!insertOrgans(bodyPartId, bodyPart.getOrgans())){
				return -1;
			}
			return bodyPartId;
		}
		return -1;
	}


	private boolean insertOrgans(int bodyPartId, Set<String> organs) {
		// TODO Auto-generated method stub
		for(String organName: organs){
			String organDatabaseEquivalent=this.getDatabaseEquivalent(organName);
			Organ organ=organMap.get(organName);
			if(isLineOrgan(organDatabaseEquivalent)){
				if(!insertLineOrgan(bodyPartId, organDatabaseEquivalent, organ)){
					return false;
				}
			}
			else{
				if(!insertSpotOrgan(bodyPartId, organDatabaseEquivalent, organ)){
					return false;
				}
			}
		}
		return true;
	}

	private boolean insertLineOrgan(int bodyPartId, String organDatabaseEquivalent, Organ organ) {
		LineOrgan lineOrgan=(LineOrgan)organ;
		try{
			int lineOrganId=insertSpecificLineOrgan(lineOrganIds.get(organDatabaseEquivalent), lineOrgan);
			insertGeneralOrgan(bodyPartId, "LineOrgan", lineOrganId, lineOrgan);
		}
		catch(SQLException e){
			printError("Unable to register line Organ with Id: "+bodyPartId+" and type: "+organDatabaseEquivalent+"; Exception: "+e.getMessage());
			return false;
		}
		
		return true;
	}

	private int insertSpecificLineOrgan(int lineOrganTypeId, LineOrgan organ) throws SQLException {
		// TODO Auto-generated method stub
		entryStatement.executeUpdate("INSERT INTO 'LineOrgan' (Type,VecEnd1X,VecEnd1Y,VecEnd2X,VecEnd2Y) VALUES ("+lineOrganTypeId+", "+organ.getCurrentVecEnd1X()+", "+organ.getCurrentVecEnd1Y()+", "+organ.getCurrentVecEnd2X()+", "+organ.getCurrentVecEnd2Y()+");");
		ResultSet lineOrganIdSet =entryStatement.executeQuery("SELECT MAX(Id) FROM 'LineOrgan';");
		return lineOrganIdSet.getInt("MAX(Id)");

	}

	protected double calculateRegionDivisor() {
		int numberOfRegions=currentMorphology.numberOfRegions();
		double regionDivisor=1d;
		if(numberOfRegions<3){
			regionDivisor=2d;
		}
		return regionDivisor;
	}

	private void insertGeneralOrgan(int bodyPartId, String organType, int typeOrganId, Organ organ) throws SQLException {
		entryStatement.executeUpdate("INSERT INTO 'Organ' (Region,VecX,VecY,"+organType+") VALUES ("+bodyPartId+", "+organ.getCurrentVecX()+", "+organ.getCurrentVecY()+", "+typeOrganId+");");
		
	}

	private boolean insertSpotOrgan(int bodyPartId, String organDatabaseEquivalent, Organ organ) {
		SpotOrgan spotOrgan=(SpotOrgan)organ;
		try{
			int lineOrganId=insertSpecificSpotOrgan(spotOrganIds.get(organDatabaseEquivalent), spotOrgan);
			insertGeneralOrgan(bodyPartId, "SpotOrgan", lineOrganId, spotOrgan);
		}
		catch(SQLException e){
			printError("Unable to register spot Organ with Id: "+bodyPartId+" and type: "+organDatabaseEquivalent+"; Exception: "+e.getMessage());
			return false;
		}
		
		return true;
		
	}

	private int insertSpecificSpotOrgan(int spotOrganTypeId, SpotOrgan spotOrgan) throws SQLException {
		// TODO Auto-generated method stub
		entryStatement.executeUpdate("INSERT INTO 'SpotOrgan' (Type,Rot) VALUES ("+spotOrganTypeId+", "+spotOrgan.getCurrentRotation()+");");
		ResultSet lineOrganIdSet =entryStatement.executeQuery("SELECT MAX(Id) FROM 'SpotOrgan';");
		return lineOrganIdSet.getInt("MAX(Id)");

	}

	private int getNumberOfParameters(int numberOfLinks) {
		// TODO Auto-generated method stub
		switch(numberOfLinks){
			case 0:
				return PARAMETERS_PER_REGION_WITH_NO_LINK;
			case 1:
				return PARAMETERS_PER_REGION_WITH_ONE_LINK;
			default:
				return numberOfLinks;
		}
	}

	private boolean insertRegionParameters(BodyPart bodyPart, int bodyPartId, int numberOfParameters){
		try{
			for (int parameterIndex = 0; parameterIndex < numberOfParameters; parameterIndex++) {
				String direction=OrganParameters.getDirection(bodyPart.getDirections(), parameterIndex);
				double regionRegisterValue=calculateRegionRegisterValue(bodyPart, direction);
				entryStatement.executeUpdate("INSERT INTO 'RegionParam' (Region,ParamInd,Value) VALUES ("+bodyPartId+", "+parameterIndex+", "+regionRegisterValue+");");
			}
			return true;
		}
		catch(SQLException exception){
			printError("Error while inserting region parameter with values: "+bodyPartId+", "+DB_PARAM_DEFAULT_VALUE+": "+exception.getMessage());
			return false;
		} catch (PlinguaCoreException exception) {
			// TODO Auto-generated catch block
			printError("Error while inserting region parameter with values: "+bodyPartId+", "+DB_PARAM_DEFAULT_VALUE+": "+exception.getMessage());
			return false;
		}
		
	}

	private double calculateRegionRegisterValue(BodyPart bodyPart, String direction) throws PlinguaCoreException {
		// TODO Auto-generated method stub
		return getExpectedValue(direction, bodyPart);
		
	}

	private double getExpectedValue(String direction, BodyPart bodyPart) throws PlinguaCoreException {
		// TODO Auto-generated method stub
		double orientationValue= getOrientationValue(direction);
		double comparedValue=getValueOfInterest(direction, bodyPart,orientationValue);
		if(bodyPart.getType().equals(TRUNK_STRING.toLowerCase())){
			if((direction.equals(OrganParameters.TOP)&&comparedValue==orientationValue+1)||(direction.equals(OrganParameters.BOTTOM)&&comparedValue==orientationValue-1)){
				comparedValue=orientationValue;
			}
			
			
		}
		double expectedValue= DB_PARAM_DEFAULT_VALUE*(orientationValue-Math.abs(comparedValue-orientationValue))/orientationValue;
		if(expectedValue<DB_PARAM_DEFAULT_VALUE){
			expectedValue/=getDirectionScaleValue(direction);
		}
		return expectedValue;
	}

	protected double getDirectionScaleValue(String direction) throws PlinguaCoreException{
		switch(direction){
			case OrganParameters.TOP:
			case OrganParameters.BOTTOM:
				return DIRECTION_DIVISOR_FACTOR[0];
			case OrganParameters.LEFT:
			case OrganParameters.RIGHT:
				return DIRECTION_DIVISOR_FACTOR[1];
		}
		throw new PlinguaCoreException("Direction "+direction+" has no associated scale factor");
	}
	
	private double getValueOfInterest(String type, BodyPart bodyPart, double orientationValue) {
		// TODO Auto-generated method stub
		switch(type.toLowerCase()){
			case OrganParameters.TOP:
				if(bodyPart.getType().equals(HEAD_STRING.toLowerCase())||bodyPart.getType().equals(TAIL_STRING.toLowerCase()))
					return orientationValue;
				return bodyPart.getDelimitingPoints().get(0).getFirst();
			case OrganParameters.BOTTOM:
				if(bodyPart.getType().equals(HEAD_STRING.toLowerCase())||bodyPart.getType().equals(TAIL_STRING.toLowerCase()))
					return orientationValue;
				return bodyPart.getDelimitingPoints().get(1).getFirst();
			case OrganParameters.LEFT:
				return bodyPart.getDelimitingPoints().get(0).getSecond();
			case OrganParameters.RIGHT:
				return bodyPart.getDelimitingPoints().get(1).getSecond();
		}
		return 0.0f;
	}

	protected double getOrientationValue(String type) {
		switch(type){
			case OrganParameters.TOP:
				return 1.0d;
			case OrganParameters.BOTTOM:
				return dataReader.getParameter("length");
			case OrganParameters.LEFT:
				return 1.0d;
			case OrganParameters.RIGHT:
				return dataReader.getParameter("height");
		}
		return 0.0d;
	
	}



	private int getBodyPartIndex(String partString) throws SQLException {
		ResultSet maxMorphologyIdSet=entryStatement.executeQuery("select Id from 'RegionType' where RegionType.Name like '"+partString+"';");
		return maxMorphologyIdSet.getInt("Id");		
	}


	public static SQLPlanformResultsWriter getInstance(){
		if(instance==null){
			instance=new SQLPlanformResultsWriter();
		}
		return instance;
	}
	
	public boolean openConnection()
	  {
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:"+getResourcesDir()+DB_FILE_NAME);
	      printInfo("Opened database successfully");
	      entryStatement = c.createStatement();
	      morphologyIds=new int[numberOfMorphologies];
	      for(int index=0; index<numberOfMorphologies; index++){
	    	  morphologyIds[index]=queryAndGetMorphologyID(index);
	    	  printInfo("Morphology with name "+printMorphologyName(index)+" and ID "+morphologyIds[index]+" inserted into database");
	      }
	    } catch ( Exception e ) {
	    	printError("Unable to open connection to database "+DB_FILE_NAME+": " + e.getMessage() );
	    	return false;
	    }
	    return true;
	  }

	protected boolean closeDBSafely() {
		if(c!=null){
			try{
				c.rollback();
				c.close();
			}
			catch(SQLException exception){
				return false;
			}
		}
		return true;
	}

	protected int queryAndGetMorphologyID(int index) throws SQLException {
		entryStatement.executeUpdate("DELETE from 'Morphology' WHERE Name IS '"+printMorphologyName(index)+"';");
	      entryStatement.executeUpdate("INSERT INTO 'Morphology' (Name) VALUES ('"+printMorphologyName(index)+"');");
	      return extractMorphologyId(entryStatement, index);
	}

	protected int extractMorphologyId(Statement createEntryStatement, int index)
			throws SQLException {
		ResultSet maxMorphologyIdSet=createEntryStatement.executeQuery("SELECT Id FROM 'Morphology' WHERE Name IS '"+printMorphologyName(index)+"';");
	      return maxMorphologyIdSet.getInt("Id");

	}

	private String printMorphologyName(int index) {
		// TODO Auto-generated method stub
		return fileNameRoot.substring(1)+partExtractor.getMorphology(index).getName();
	}
	
	public void setFileNameRoot(String fileNameRoot){
		if(fileNameRoot!=null && !fileNameRoot.isEmpty()){
			this.fileNameRoot=fileNameRoot;
		}	
	}

	@Override
	public void writeResults(String destinationRoute) {
		// TODO Auto-generated method stub
		super.writeResults(destinationRoute);
		if(!computationList.isEmpty())
			printDatabaseResultsAndTreatExceptions(computationList.get(0));
	}

	public void setResourcesDir(String resourcesDir) {
		// TODO Auto-generated method stub
		if(resourcesDir!=null)
			this.resourcesDir=resourcesDir;

	}
	
	public String getResourcesDir() {
		// TODO Auto-generated method stub
		return resourcesDir;
	}
	
	protected String getDatabaseEquivalent(String object){
		switch(object){
			case LEFT_EYE:
			case RIGHT_EYE:
				return EYE_STRING;
			case LEFT_LOBE:
			case RIGHT_LOBE:
				return BRAIN_STRING;
			case LEFT_NERVE:
			case RIGHT_NERVE:
				return VNC_STRING;
			case PHARYNX:
				return PHARYNX_STRING;
			default:
				throw new IllegalArgumentException("There is no database equivalent for object {"+object+"}");
			
			
		}
	}
	
	protected boolean isLineOrgan(String object){
		return lineOrganIds.containsKey(object);
	}


	

}
