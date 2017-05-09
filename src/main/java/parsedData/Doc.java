package main.java.parsedData;

import java.util.ArrayList;
import java.util.HashMap;


public class Doc {
	
	private HashMap<String, Entity> entityList;
	private HashMap<String, Activity> activityList;
	private ArrayList<Used> usedList;
	private ArrayList<WasAssociatedWith> wasAssociatedWithList;
	private ArrayList<WasAttributedTo> wasAttributedToList;
	private ArrayList<WasEndedBy> wasEndedByList;
	private ArrayList<WasGeneratedBy> wasGeneratedByList;
	private ArrayList<WasStartedBy> wasStartedByList;
	private HashMap<String, Agent> agentList;
	private ArrayList<ActedOnBehalfOf> actedOnBehalfOfList;
	private ArrayList<WasDerivedFrom> wasDerivedFromList;
	private ArrayList<SpecializationOf> specializationOfList;
	private ArrayList<AlternateOf> alternateOfList;
	
	
	public ArrayList<SpecializationOf> getSpecializationOfList() {
		return specializationOfList;
	}
	public void setSpecializationOfList(ArrayList<SpecializationOf> specializationOfList) {
		this.specializationOfList = specializationOfList;
	}
	public ArrayList<AlternateOf> getAlternateOfList() {
		return alternateOfList;
	}
	public void setAlternateOfList(ArrayList<AlternateOf> alternateOfList) {
		this.alternateOfList = alternateOfList;
	}
	public HashMap<String, Entity> getEntityList() {
		return entityList;
	}
	public void setEntityList(HashMap<String, Entity> entityList) {
		this.entityList = entityList;
	}
	public HashMap<String, Activity> getActivityList() {
		return activityList;
	}
	public void setActivityList(HashMap<String, Activity> activityList) {
		this.activityList = activityList;
	}
	public ArrayList<Used> getUsedList() {
		return usedList;
	}
	public void setUsedList(ArrayList<Used> usedList) {
		this.usedList = usedList;
	}
	public ArrayList<WasAssociatedWith> getWasAssociatedWithList() {
		return wasAssociatedWithList;
	}
	public void setWasAssociatedWithList(ArrayList<WasAssociatedWith> wasAssociatedWithList) {
		this.wasAssociatedWithList = wasAssociatedWithList;
	}
	public ArrayList<WasAttributedTo> getWasAttributedToList() {
		return wasAttributedToList;
	}
	public void setWasAttributedToList(ArrayList<WasAttributedTo> wasAttributedToList) {
		this.wasAttributedToList = wasAttributedToList;
	}
	public ArrayList<WasEndedBy> getWasEndedByList() {
		return wasEndedByList;
	}
	public void setWasEndedByList(ArrayList<WasEndedBy> wasEndedByList) {
		this.wasEndedByList = wasEndedByList;
	}
	public ArrayList<WasGeneratedBy> getWasGeneratedByList() {
		return wasGeneratedByList;
	}
	public void setWasGeneratedByList(ArrayList<WasGeneratedBy> wasGeneratedByList) {
		this.wasGeneratedByList = wasGeneratedByList;
	}
	public ArrayList<WasStartedBy> getWasStartedByList() {
		return wasStartedByList;
	}
	public void setWasStartedByList(ArrayList<WasStartedBy> wasStartedByList) {
		this.wasStartedByList = wasStartedByList;
	}
	public HashMap<String, Agent> getAgentList() {
		return agentList;
	}
	public void setAgentList(HashMap<String, Agent> agentList) {
		this.agentList = agentList;
	}
	public ArrayList<ActedOnBehalfOf> getActedOnBehalfOfList() {
		return actedOnBehalfOfList;
	}
	public void setActedOnBehalfOfList(ArrayList<ActedOnBehalfOf> actedOnBehalfOfList) {
		this.actedOnBehalfOfList = actedOnBehalfOfList;
	}
	public ArrayList<WasDerivedFrom> getWasDerivedFromList() {
		return wasDerivedFromList;
	}
	public void setWasDerivedFromList(ArrayList<WasDerivedFrom> wasDerivedFromList) {
		this.wasDerivedFromList = wasDerivedFromList;
	}
	
	
}
