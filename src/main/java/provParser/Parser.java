package main.java.provParser;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import main.java.parsedData.Doc;

public class Parser {

	public static void main(String[] args) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	    try {
	        SAXParser saxParser = saxParserFactory.newSAXParser();
	        SaxHandler parser = new SaxHandler();
	        saxParser.parse(new File("/Users/sanjeetphatak/Documents/primer-prov-xml-examples-tz.provx"), parser);
	        
	        Doc doc = new Doc();
	        doc.setActivityList(parser.activityList);
	        doc.setActedOnBehalfOfList(parser.actedOnBehalfOfList);
	        doc.setAgentList(parser.agentList);
	        doc.setEntityList(parser.entityList);
	        doc.setUsedList(parser.usedList);
	        doc.setWasAssociatedWithList(parser.wasAssociatedWithList);
	        doc.setWasAttributedToList(parser.wasAttributedToList);
	        doc.setWasDerivedFromList(parser.wasDerivedFromList);
	        doc.setWasEndedByList(parser.wasEndedByList);
	        doc.setWasGeneratedByList(parser.wasGeneratedByList);
	        doc.setWasStartedByList(parser.wasStartedByList);
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	}

}
