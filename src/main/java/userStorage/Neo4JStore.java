package main.java.userStorage;
import static org.neo4j.driver.v1.Values.parameters;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Config;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;

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
import main.java.provParser.SaxHandler;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;

public class Neo4JStore {
	
    public static void insert(String path) {
        
    	
    	SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    	Doc doc = new Doc();
	    try {
	        SAXParser saxParser = saxParserFactory.newSAXParser();
	        SaxHandler parser = new SaxHandler();
	        //saxParser.parse(new File("/Users/sanjeetphatak/Documents/primer-prov-xml-examples-tz.provx"), parser);
	        saxParser.parse(new File(path), parser);
	        
	        
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
	        doc.setSpecializationOfList(parser.specializationOfList);
	        doc.setAlternateOfList(parser.alternateOfList);
	    } catch(Exception e){
	    	
	    }
	    
        Config noSSL = Config.build().withEncryptionLevel(Config.EncryptionLevel.NONE).toConfig();
        Driver driver = GraphDatabase.driver("bolt://localhost",AuthTokens.basic("neo4j","sanjeet"),noSSL); // <password>
        
        try (Session session = driver.session()) {
        	
        	for(String a : doc.getAgentList().keySet()){
        		
        		Agent agent = doc.getAgentList().get(a);
        		session.run( "CREATE (agent:Agent {id: {id}, name: {name}, type: {type}, givenName: {givenName}, mbox: {mbox}}) "+ 
        					 "RETURN agent",
        		        parameters( "id", agent.getId(), "name", agent.getName(), "type", agent.getType(),
        		        		"givenName", agent.getGivenName(), "mbox", agent.getMbox() ) );
        		
        
        	}
        	
        	for(String a : doc.getActivityList().keySet()){
        		Activity activity = doc.getActivityList().get(a);
        		session.run( "CREATE (activity:Activity {id: {id}, startTime: {startTime}, endTime: {endTime}}) " + 
        					 "RETURN activity" ,
        		        parameters( "id", activity.getId(), "startTime", activity.getStartTime(), "endTime", 
        		        		activity.getEndTime()) );
        	}
        	
        	for(String a : doc.getEntityList().keySet()){
        		Entity entity = doc.getEntityList().get(a);
        		session.run( "CREATE (entity:Entity {id: {id}, title: {title}, type: {type}}) "+
        					 "RETURN entity",
        		        parameters( "id", entity.getId(), "title", entity.getTitle(), "type", entity.getType()) );
        	}
        	  
        	for(Used u : doc.getUsedList()){
        		String query = "MATCH (e:Entity), (a:Activity)"+
        						"WHERE e.id='" + u.getEntity() + "' AND a.id='" + u.getActivity() + "'" +
        						"CREATE (e)-[used:USED{role: \"" + u.getRole() + "\"}]->(a)"+
        						"RETURN e,used,a";
        		session.run(query);
        	}
        	
        	for(WasGeneratedBy w : doc.getWasGeneratedByList()){
        		String query = "MATCH (e:Entity), (a:Activity)"+
        						"WHERE e.id ='"+ w.getEntity() + "' AND a.id ='" + w.getActivity() + "'" +
								"CREATE (e)-[was:WAS_GENERATED_BY{time: \""+ w.getTime()+"\", role: \""+ w.getRole()+"\"}]->(a)"+
								"RETURN e,was,a";
        		session.run(query);
        	}
        	
        	for(WasDerivedFrom w : doc.getWasDerivedFromList()){
        		String query = "MATCH (e:Entity), (u:Entity)" +
								"WHERE e.id='" + w.getUsedEntity() + "' AND u.id='" + w.getGeneratedEntity() +"'" +
        						"CREATE (e)-[was:WAS_DERIVED_FROM{type: \""+ w.getType()+"\"}]->(u)"+
								"RETURN e,was,u";
        		session.run(query);
        	}
        	
        	for(WasAttributedTo w : doc.getWasAttributedToList()){
        		String query = "MATCH (e:Entity), (a:Agent)"+
								"WHERE e.id='" + w.getEntity() + "' AND a.id ='" + w.getAgent() + "'" +
        						"CREATE (e)-[was:WAS_ATTRIBUTED_TO]->(a)"+
								"RETURN e,was,a";
        		session.run(query);
        	}
        	
        	for(ActedOnBehalfOf a : doc.getActedOnBehalfOfList()){
        		String query = "MATCH (e:Agent), (a:Agent)"+
								"WHERE e.id='" + a.getResponsible() + "' AND a.id='" + a.getDelegate() + "'" +
        						"CREATE (e)-[was:ACTED_ON_BEHALF_OF]->(a)"+
								"RETURN e,was,a";
        		session.run(query);
        	}
        	
        	for(WasAssociatedWith w : doc.getWasAssociatedWithList()){
        		String query = "MATCH (e:Activity), (a:Agent)"+
								"WHERE e.id ='" + w.getActivity() + "' AND a.id='" + w.getAgent() +"'"+
        						"CREATE (e)-[was:WAS_ASSOCIATED_WITH{plan: \""+ w.getPlan()+"\", role: \""+ w.getRole()+"\"}]->(a)"+
								"RETURN e,was,a";
        		session.run(query);
        	}
        	
        	for(SpecializationOf a : doc.getSpecializationOfList()){
        		String query = "MATCH (e:Entity), (a:Entity)"+
								"WHERE e.id='" + a.getSpecificEntity() + "' AND a.id='" + a.getGeneralEntity() +"'" + 
        						"CREATE (e)-[was:SPECIALIZATION_OF]->(a)"+
								"RETURN e,was,a";
        		session.run(query);
        	}
        	
        	for(AlternateOf a : doc.getAlternateOfList()){
        		String query = "MATCH (e:Entity {id:\""+ a.getAlternate1() + "\"}), (a:Entity {id:\""+ a.getAlternate2()+"\"})"+
								"WHERE e.id='"+a.getAlternate1() + "' AND a.id='" + a.getAlternate2() +"'"+
								"CREATE (e)-[was:ALTERNATE_OF]->(a)"+
								"RETURN e,was,a";
        		session.run(query);
        	}
               //session.close();
               //driver.close();
      
               

        }
    }
    
    public String query(){
    	Config noSSL = Config.build().withEncryptionLevel(Config.EncryptionLevel.NONE).toConfig();
        Driver driver = GraphDatabase.driver("bolt://localhost",AuthTokens.basic("neo4j","sanjeet"),noSSL);
    	StatementResult result;
        StringBuilder out = new StringBuilder();
        
    	out.append("<HTML><HEAD><head>");
    	out.append("<style>");
    	out.append("table, th, td {");
    	out.append("border: 1px solid black;");
    	out.append("}");
    	out.append("</style>");
    	out.append("</head><TITLE>Info</TITLE></HEAD><BODY>\n");
		
        
        
        try (Session session = driver.session()) {
        	String foafQuery = 
          		   "MATCH (a:Agent) " +
                             "RETURN a.id AS id, a.name AS name, a.type AS type, a.givenName AS givenName, "
                             + "a.getMbox AS mbox";
        	result = session.run(foafQuery,parameters( "name", "Derek" ) );
        	
        	//Agent table
        	out.append("<center><b>Agent<b></center><br><br>");
    		out.append("<center><table style=\"width:100%\">\n");
    		out.append("<table><tr>");
    		out.append("<br><br>");
    		out.append("<th>ID</th>");
    		out.append("<th>Name</th>");
    		out.append("<th>Type</th>");
    		out.append("<th>Given Name</th>");
    		out.append("<th>Mbox</th>");
    		out.append("</tr>");
    		
    		
            while (result.hasNext()){
         	   Record record = result.next();
         	  out.append("<tr>");
         	  out.append("<td>"+ record.get("id").asString() +"</td>");
         	  out.append("<td>"+ record.get("name").asString() +"</td>");
         	  out.append("<td>"+ record.get("type").asString() +"</td>");
         	  out.append("<td>"+ record.get("givenName").asString() +"</td>");
         	  out.append("<td>"+ record.get("mbox").asString() +"</td>");
         	  out.append("</tr>");
            }
            
            out.append("</table></center>");
            
            foafQuery = "MATCH (e:Entity) " +
                    "RETURN e.id AS id, e.title AS title, e.type AS type";
            
            
            out.append("<br><br><center><b>Entity<b></center><br><br>");
    		out.append("<center><table style=\"width:100%\">\n");
    		out.append("<table><tr>");
    		out.append("<br><br>");
    		out.append("<th>ID</th>");
    		out.append("<th>Title</th>");
    		out.append("<th>Type</th>");
    		out.append("</tr>");
    		
    		result = session.run(foafQuery,parameters( "name", "Derek" ) );
            
    		while (result.hasNext()){
         	   Record record = result.next();
         	  out.append("<tr>");
         	  out.append("<td>"+ record.get("id").asString() +"</td>");
         	  out.append("<td>"+ record.get("title").asString() +"</td>");
         	  out.append("<td>"+ record.get("type").asString() +"</td>");
         	  out.append("</tr>");
            }
    		
    		out.append("</table></center>");
    		//Activity
    		foafQuery = "MATCH (a:Activity) " +
                    "RETURN a.id AS id, a.startTime AS startime, a.endTime AS endtime";
            
            
            out.append("<br><br><center><b>Activity<b></center><br><br>");
    		out.append("<center><table style=\"width:100%\">\n");
    		out.append("<table><tr>");
    		out.append("<br><br>");
    		out.append("<th>ID</th>");
    		out.append("<th>StartTime</th>");
    		out.append("<th>EndTime</th>");
    		out.append("</tr>");
    		
    		result = session.run(foafQuery,parameters( "name", "Derek" ) );
            
    		while (result.hasNext()){
         	   Record record = result.next();
         	  out.append("<tr>");
         	  out.append("<td>"+ record.get("id").asString() +"</td>");
         	  out.append("<td>"+ record.get("starttime").asString() +"</td>");
         	  out.append("<td>"+ record.get("endtime").asString() +"</td>");
         	  out.append("</tr>");
            }
    		out.append("</table></center>");
    		
    		
    		
    		foafQuery = "MATCH (a:WAS_ASSOCIATED_WITH) " +
                    "RETURN a.id AS id, a.role AS role, a.plan AS plan";
            
            
            out.append("<br><br><center><b>Activity<b></center><br><br>");
    		out.append("<center><table style=\"width:100%\">\n");
    		out.append("<table><tr>");
    		out.append("<br><br>");
    		out.append("<th>ID</th>");
    		out.append("<th>StartTime</th>");
    		out.append("<th>EndTime</th>");
    		out.append("</tr>");
    		
    		result = session.run(foafQuery);
            
    		while (result.hasNext()){
         	   Record record = result.next();
         	  out.append("<tr>");
         	  out.append("<td>"+ record.get("id").asString() +"</td>");
         	  out.append("<td>"+ record.get("starttime").asString() +"</td>");
         	  out.append("<td>"+ record.get("endtime").asString() +"</td>");
         	  out.append("</tr>");
            }
    		out.append("</table></center>");
        }     
        
        
        out.append("</body>");
        out.append("</html>");
        return out.toString();
    }
    
    public void reconstruct(String fileName){
    	Config noSSL = Config.build().withEncryptionLevel(Config.EncryptionLevel.NONE).toConfig();
        Driver driver = GraphDatabase.driver("bolt://localhost",AuthTokens.basic("neo4j","sanjeet"),noSSL);
        StatementResult result;
    	try{
    	    PrintWriter writer = new PrintWriter(fileName, "UTF-8");
    	    writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
    	    writer.println("<prov:document xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"");
    	    writer.println("\t"+ "xmlns:prov=\"http://www.w3.org/ns/prov#\" xmlns:exc=\"http://www.example.org/\"");
    	    writer.println("\t" + "xmlns:ex=\"http://example.org/\" xmlns:dct=\"http://purl.org/dc/terms/");
    	    writer.println("\t" + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:foaf=\"http://xmlns.com/foaf/0.1/\">");
            
    	    try (Session session = driver.session()) {
            	String foafQuery = 
              		   "MATCH (a:Agent) " +
                                 "RETURN a.id AS id, a.name AS name, a.type AS type, a.givenName AS givenName, "
                                 + "a.getMbox AS mbox";
            	result = session.run(foafQuery);
        		
        		
                while (result.hasNext()){
             	   Record record = result.next();
             	  out.append("<tr>");
             	  out.append("<td>"+ record.get("id").asString() +"</td>");
             	  out.append("<td>"+ record.get("name").asString() +"</td>");
             	  out.append("<td>"+ record.get("type").asString() +"</td>");
             	  out.append("<td>"+ record.get("givenName").asString() +"</td>");
             	  out.append("<td>"+ record.get("mbox").asString() +"</td>");
             	  out.append("</tr>");
                }
                
                out.append("</table></center>");
                
                foafQuery = "MATCH (e:Entity) " +
                        "RETURN e.id AS id, e.title AS title, e.type AS type";
                
                
                out.append("<br><br><center><b>Entity<b></center><br><br>");
        		out.append("<center><table style=\"width:100%\">\n");
        		out.append("<table><tr>");
        		out.append("<br><br>");
        		out.append("<th>ID</th>");
        		out.append("<th>Title</th>");
        		out.append("<th>Type</th>");
        		out.append("</tr>");
        		
        		result = session.run(foafQuery,parameters( "name", "Derek" ) );
                
        		while (result.hasNext()){
             	   Record record = result.next();
             	  out.append("<tr>");
             	  out.append("<td>"+ record.get("id").asString() +"</td>");
             	  out.append("<td>"+ record.get("title").asString() +"</td>");
             	  out.append("<td>"+ record.get("type").asString() +"</td>");
             	  out.append("</tr>");
                }
        		
        		out.append("</table></center>");
        		//Activity
        		foafQuery = "MATCH (a:Activity) " +
                        "RETURN a.id AS id, a.startTime AS startime, a.endTime AS endtime";
                
                
                out.append("<br><br><center><b>Activity<b></center><br><br>");
        		out.append("<center><table style=\"width:100%\">\n");
        		out.append("<table><tr>");
        		out.append("<br><br>");
        		out.append("<th>ID</th>");
        		out.append("<th>StartTime</th>");
        		out.append("<th>EndTime</th>");
        		out.append("</tr>");
        		
        		result = session.run(foafQuery,parameters( "name", "Derek" ) );
                
        		while (result.hasNext()){
             	   Record record = result.next();
             	  out.append("<tr>");
             	  out.append("<td>"+ record.get("id").asString() +"</td>");
             	  out.append("<td>"+ record.get("starttime").asString() +"</td>");
             	  out.append("<td>"+ record.get("endtime").asString() +"</td>");
             	  out.append("</tr>");
                }
        		out.append("</table></center>");
        		
        		
        		
        		foafQuery = "MATCH (a:WAS_ASSOCIATED_WITH) " +
                        "RETURN a.id AS id, a.role AS role, a.plan AS plan";
                
                
                out.append("<br><br><center><b>Activity<b></center><br><br>");
        		out.append("<center><table style=\"width:100%\">\n");
        		out.append("<table><tr>");
        		out.append("<br><br>");
        		out.append("<th>ID</th>");
        		out.append("<th>StartTime</th>");
        		out.append("<th>EndTime</th>");
        		out.append("</tr>");
        		
        		result = session.run(foafQuery);
                
        		while (result.hasNext()){
             	   Record record = result.next();
             	  out.append("<tr>");
             	  out.append("<td>"+ record.get("id").asString() +"</td>");
             	  out.append("<td>"+ record.get("starttime").asString() +"</td>");
             	  out.append("<td>"+ record.get("endtime").asString() +"</td>");
             	  out.append("</tr>");
                }
        		out.append("</table></center>");
            }
    	    writer.println("");
    	    writer.println("");
    	    writer.println("");
    	    writer.println("");
    	    writer.println("");
    	    writer.println("");
    	    writer.println("");
    	    writer.println("");
    	    writer.println("");
    	    writer.println("");
    	    writer.println("");
    	    writer.println("");
    	    writer.println("");
    	    writer.println("");
    	    writer.println("");
    	    
    	    
    	    writer.close();
    	} catch (IOException e) {
    	   // do something
    	}
	}
}