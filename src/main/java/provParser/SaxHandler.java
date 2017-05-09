package main.java.provParser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import main.java.parsedData.ActedOnBehalfOf;
import main.java.parsedData.Activity;
import main.java.parsedData.Agent;
import main.java.parsedData.AlternateOf;
import main.java.parsedData.Doc;
import main.java.parsedData.Entity;
import main.java.parsedData.SpecializationOf;
import main.java.parsedData.Used;
import main.java.parsedData.WasAssociatedWith;
import main.java.parsedData.WasAttributedTo;
import main.java.parsedData.WasDerivedFrom;
import main.java.parsedData.WasEndedBy;
import main.java.parsedData.WasGeneratedBy;
import main.java.parsedData.WasStartedBy;


public class SaxHandler extends DefaultHandler{
	
	
	public ArrayList<Used> usedList;
	public HashMap<String, Entity> entityList;
	public HashMap<String, Activity> activityList;
	public ArrayList<WasAssociatedWith> wasAssociatedWithList;
	public ArrayList<WasAttributedTo> wasAttributedToList;
	public ArrayList<WasEndedBy> wasEndedByList;
	public ArrayList<WasGeneratedBy> wasGeneratedByList;
	public ArrayList<WasStartedBy> wasStartedByList;
	public HashMap<String, Agent> agentList;
	public ArrayList<ActedOnBehalfOf> actedOnBehalfOfList;
	public ArrayList<WasDerivedFrom> wasDerivedFromList;
	public ArrayList<SpecializationOf> specializationOfList;
	public ArrayList<AlternateOf> alternateOfList;
	
	Entity entity;
	Activity activity;
	Used used;
	WasAssociatedWith wasAssociatedWith;
	WasAttributedTo wasAttributedTo;
	WasEndedBy wasEndedBy;
	WasGeneratedBy wasGeneratedBy;
	WasStartedBy wasStartedBy;
	Agent agent;
	ActedOnBehalfOf actedOnBehalfOf;
	WasDerivedFrom wasDerivedFrom;
	SpecializationOf specializationOf;
	AlternateOf alternateOf;
	
	Doc doc = new Doc();
	int num;
	int parentNum;
	String id;
	
	public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {

        if (qName.equalsIgnoreCase("prov:entity")) {
            
            
            if(parentNum == 30){
            	used.setEntity(attributes.getValue("prov:ref"));
            } else if(parentNum == 70){
            	wasGeneratedBy.setEntity(attributes.getValue("prov:ref"));
            } else if(parentNum == 50){
            	wasAttributedTo.setEntity(attributes.getValue("prov:ref"));
            }else{
            	parentNum = 1;
            	id = attributes.getValue("prov:id");
            	entity = new Entity();
                entity.setId(id);
                
                //initialize list
                if (entityList == null)
                    entityList = new HashMap<>();
            }
        } else if (qName.equalsIgnoreCase("prov:activity")) {
            
        	if(parentNum == 30){
            	used.setActivity(attributes.getValue("prov:ref"));
            } else if(parentNum == 70){
            	wasGeneratedBy.setActivity(attributes.getValue("prov:ref"));
            } else if(parentNum == 40){
            	wasAssociatedWith.setActivity(attributes.getValue("prov:ref"));
            } else if(parentNum == 60){
            	if(attributes.getValue("prov:ref") != null){
            		wasEndedBy.setActivity(attributes.getValue("prov:ref"));
            	} else if(attributes.getValue("prov:id") != null)
            		wasEndedBy.setActivity(attributes.getValue("prov:id"));
            } else if(parentNum == 80){
            	wasStartedBy.setActivity(attributes.getValue("prov:ref"));
            } else{
            	parentNum = 10;
            	id = attributes.getValue("prov:id");
                
                activity = new Activity();
                activity.setId(id);
                
                //initialize list
                if (activityList == null)
                	activityList = new HashMap<>();
            }
            
        } else if (qName.equalsIgnoreCase("prov:agent")) {
        	
        	if(parentNum == 50){
            	wasAttributedTo.setAgent(attributes.getValue("prov:ref"));
            } else if(parentNum == 40){
            	wasAssociatedWith.setAgent(attributes.getValue("prov:ref"));
            } else{
            	parentNum = 20;
            	id = attributes.getValue("prov:id");
                
                agent = new Agent();
                agent.setId(id);
                
                //initialize list
                if (agentList == null)
                	agentList = new HashMap<>();
            }
        } else if (qName.equalsIgnoreCase("prov:used")) {
        	
        	if(parentNum == 0) parentNum = 30;
            
            used = new Used();
            
            //initialize list
            if (usedList == null)
            	usedList = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("prov:wasAssociatedWith")) {
        	parentNum = 40;

            wasAssociatedWith = new WasAssociatedWith();
            
            //initialize list
            if (wasAssociatedWithList == null)
            	wasAssociatedWithList = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("prov:wasAttributedTo")) {
        	parentNum = 50;

            wasAttributedTo = new WasAttributedTo();
            
            //initialize list
            if (wasAttributedToList == null)
            	wasAttributedToList = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("prov:wasEndedBy")) {
        	parentNum = 60;

            wasEndedBy = new WasEndedBy();
            
            //initialize list
            if (wasEndedByList == null)
            	wasEndedByList = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("prov:wasGeneratedBy")) {
        	parentNum = 70;

            wasGeneratedBy = new WasGeneratedBy();
            
            //initialize list
            if (wasGeneratedByList == null)
            	wasGeneratedByList = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("prov:wasStartedBy")) {
        	parentNum = 80;

            wasStartedBy = new WasStartedBy();
            
            //initialize list
            if (wasStartedByList == null)
            	wasStartedByList = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("prov:actedOnBehalfOf")) {
        	parentNum = 90;

            actedOnBehalfOf = new ActedOnBehalfOf();
            
            //initialize list
            if (actedOnBehalfOfList == null)
            	actedOnBehalfOfList = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("prov:wasDerivedFrom")) {
        	parentNum = 100;

        	wasDerivedFrom = new WasDerivedFrom();
            
            //initialize list
            if (wasDerivedFromList == null)
            	wasDerivedFromList = new ArrayList<>();
        } else if (qName.equalsIgnoreCase("dct:title")) {
        	num = 4;
        } else if (qName.equalsIgnoreCase("prov:type")) {
        	if(parentNum == 1) num = 5;
        	else if(parentNum == 10) num = 14;
        	else if(parentNum == 20) num = 24;
        	else if(parentNum == 100) num = 104;
        } else if(qName.equalsIgnoreCase("foaf:givenName")){
        	num = 25;
        } else if(qName.equalsIgnoreCase("foaf:name")){
        	num = 26;
        } else if(qName.equalsIgnoreCase("foaf:mbox")){
        	num = 27;
        } else if(qName.equalsIgnoreCase("prov:delegate")){
        	actedOnBehalfOf.setDelegate(attributes.getValue("prov:ref"));
        } else if(qName.equalsIgnoreCase("prov:responsible")){
        	actedOnBehalfOf.setResponsible("prov:ref");
        } else if(qName.equalsIgnoreCase("prov:role")){
        	if(parentNum == 30) num = 35;
        	else if(parentNum == 40) num = 45;
        	else if(parentNum == 70) num = 75;	
        } else if(qName.equalsIgnoreCase("prov:generatedEntity")){
        	wasDerivedFrom.setGeneratedEntity(attributes.getValue("prov:ref"));	
        } else if(qName.equalsIgnoreCase("prov:usedEntity")){
        	wasDerivedFrom.setUsedEntity(attributes.getValue("prov:ref"));
        } else if(qName.equalsIgnoreCase("prov:plan")){
        	num = 46;	
        } else if(qName.equalsIgnoreCase("prov:time")){
        	if(parentNum == 70) num = 76;
        	else if(parentNum == 80) num = 86;
        	else if(parentNum == 60) num = 66;
        } else if(qName.equalsIgnoreCase("prov:startTime")){
        	num = 19;	
        } else if(qName.equalsIgnoreCase("prov:endTime")){
        	num = 18;	
        } else if(qName.equalsIgnoreCase("prov:specializationOf")){
        	parentNum = 1000;
        	specializationOf = new SpecializationOf();
        	specializationOfList = new ArrayList<>();
        } else if(qName.equalsIgnoreCase("prov:alternateOf")){
        	parentNum = 1100;
        	alternateOf = new AlternateOf();
        	alternateOfList =new ArrayList<>();
        } else if(qName.equalsIgnoreCase("prov:specificEntity")){
        	specializationOf.setSpecificEntity(attributes.getValue("prov:ref"));
        } else if(qName.equalsIgnoreCase("prov:generalEntity")){
        	specializationOf.setGeneralEntity(attributes.getValue("prov:ref"));
        } else if(qName.equalsIgnoreCase("prov:alternate1")){
        	alternateOf.setAlternate1(attributes.getValue("prov:ref"));
        } else if(qName.equalsIgnoreCase("prov:alternate2")){
        	alternateOf.setAlternate2(attributes.getValue("prov:ref"));
        }
        
        
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equalsIgnoreCase("prov:entity") && parentNum == 1) {
            entityList.put(id, entity);
            parentNum = 0;
        } else if (qName.equalsIgnoreCase("prov:activity") && parentNum == 10) {
            activityList.put(id, activity);
            parentNum = 0;
        } else if (qName.equalsIgnoreCase("prov:agent") && parentNum == 20) {
            agentList.put(id, agent);
            parentNum = 0;
        } else if (qName.equalsIgnoreCase("prov:used") && parentNum == 30) {
        	usedList.add(used);
            parentNum = 0;
        } else if (qName.equalsIgnoreCase("prov:wasAssociatedWith") && parentNum == 40) {
        	wasAssociatedWithList.add(wasAssociatedWith);
            parentNum = 0;
        } else if (qName.equalsIgnoreCase("prov:wasAttributedTo") && parentNum == 50) {
        	wasAttributedToList.add(wasAttributedTo);
            parentNum = 0;
        } else if (qName.equalsIgnoreCase("prov:wasEndedBy") && parentNum == 60) {
        	wasEndedByList.add(wasEndedBy);
            parentNum = 0;
        } else if (qName.equalsIgnoreCase("prov:wasGeneratedBy") && parentNum == 70) {
        	wasGeneratedByList.add(wasGeneratedBy);
            parentNum = 0;
        } else if (qName.equalsIgnoreCase("prov:wasStartedBy") && parentNum == 80) {
        	wasStartedByList.add(wasStartedBy);
            parentNum = 0;
        } else if (qName.equalsIgnoreCase("prov:actedOnBehalfOf") && parentNum == 90) {
        	actedOnBehalfOfList.add(actedOnBehalfOf);
            parentNum = 0;
        } else if (qName.equalsIgnoreCase("prov:wasDerivedFrom") && parentNum == 100) {
        	wasDerivedFromList.add(wasDerivedFrom);
            parentNum = 0;
        } else if (qName.equalsIgnoreCase("prov:specializationOf")) {
        	specializationOfList.add(specializationOf);
            parentNum = 0;
        } else if (qName.equalsIgnoreCase("prov:alternateOf")) {
        	alternateOfList.add(alternateOf);
            parentNum = 0;
        }
    }

 
    public void characters(char ch[], int start, int length) throws SAXException {
    	if (num == 4) {
            entity.setTitle(new String(ch, start, length));
            num = 0;
        } else if(num == 17){
        	wasGeneratedBy.setEntity(new String(ch, start, length));
        	num = 0;
        } else if(num == 15){
        	wasAttributedTo.setEntity(new String(ch, start, length));
        	num = 0;
        } else if(num == 5){
        	entity.setType(new String(ch, start, length));
        	num = 0;
        } else if(num == 104){
        	wasDerivedFrom.setType(new String(ch, start, length));
        	num = 0;
        } else if(num == 24){
        	agent.setType(new String(ch, start, length));
        	num = 0;
        } else if(num == 25){
        	agent.setGivenName(new String(ch, start, length));
        	num = 0;
        } else if(num == 26){
        	agent.setName(new String(ch, start, length));
        	num = 0;
        } else if(num == 27){
        	agent.setMbox(new String(ch, start, length));
        	num = 0;
        } else if(num == 42){
        	wasAssociatedWith.setActivity(new String(ch, start, length));
        	num = 0;
        } else if(num == 43){
        	wasAssociatedWith.setAgent(new String(ch, start, length));
        	num = 0;
        } else if(num == 51){
        	wasAttributedTo.setEntity(new String(ch, start, length));
        	num = 0;
        } else if(num == 53){
        	wasAttributedTo.setAgent(new String(ch, start, length));
        	num = 0;
        } else if(num == 35){
        	used.setRole(new String(ch, start, length));
        	num = 0;
        } else if(num == 45){
        	wasAssociatedWith.setRole(new String(ch, start, length));
        	num = 0;
        } else if(num == 75){
        	wasGeneratedBy.setRole(new String(ch, start, length));
        	num = 0;
        } else if(num == 32){
        	used.setActivity(new String(ch, start, length));
        	num = 0;
        } else if(num == 72){
        	wasGeneratedBy.setActivity(new String(ch, start, length));
        	num = 0;
        } else if(num == 46){
        	wasAssociatedWith.setPlan(new String(ch, start, length));
        	num = 0;
        } else if(num == 66){
        	wasEndedBy.setTime(new String(ch, start, length));
        	num = 0;
        } else if(num == 76){
        	wasGeneratedBy.setTime(new String(ch, start, length));
        	num = 0;
        } else if(num == 86){
        	wasStartedBy.setTime(new String(ch, start, length));
        	num = 0;
        } else if(num == 82){
        	wasStartedBy.setActivity(new String(ch, start, length));
        	num = 0;
        } else if(num == 62){
        	wasEndedBy.setActivity(new String(ch, start, length));
        	num = 0;
        } else if(num == 19){
        	activity.setStartTime(new String(ch, start, length));
        	num = 0;
        } else if(num == 18){
        	activity.setEndTime(new String(ch, start, length));
        	num = 0;
        } 
       
    }

}
