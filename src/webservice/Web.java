package webservice;

import static spark.Spark.setPort;

import main.java.userStorage.DBWrapper;
import main.java.userStorage.Neo4JStore;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class Web {

	static DBWrapper dbWrapper;
	public static void main(String[] args) {
		setPort(8080);
		Web web = new Web();
		web.call();
		dbWrapper = DBWrapper.getDataStore();
		dbWrapper.createNewDatabase("database");
		dbWrapper.createTable();
	}
	
	public void call(){
        Spark.get(new Route("/display") {
        	
			@Override
			public Object handle(Request request, Response response) {
				StringBuilder out = new StringBuilder();
				Neo4JStore n = new Neo4JStore();
				String resp = n.query();
				
	      		response.type("text/html");
	      		return resp;
	        	
			}
        	
        });
        
        Spark.get(new Route("/upload") {

			@Override
			public Object handle(Request request, Response response) {
				StringBuilder out = new StringBuilder();
	      		response.type("text/html");
				
				out.append("<HTML><HEAD><TITLE>Upload Page</TITLE></HEAD><BODY>\n");
    			out.append("<center><b>Upload Page<b></center><br><br>");
    			out.append("<form action=\"upload\" method=\"post\">\n");
    			out.append("<div class=\"container\" style=\"background-color:#f1f1f1\"\n");
    			out.append("<label><b>User name: </b></label>\n");
    			out.append("<input type=\"text\" placeholder=\"Enter user name:\" name=\"username\" required>\n");
    			out.append("<br><br><label><b>Password: </b></label>\n");
    			out.append("<input type=\"text\" placeholder=\"Enter password:\" name=\"pass\" required>\n");
    			out.append("<br><br><label><b>File Name: </b></label>\n");
    			out.append("<input type=\"text\" placeholder=\"Enter File Name\" name=\"file\" required>\n");
    			out.append("<br><br><label><b>Input Directory: </b></label>\n");
    			out.append("<input type=\"text\" placeholder=\"Enter Path\" name=\"path\" required>\n");
    			out.append("<br><br><button type=\"submit\">Upload</button>\n");
    			out.append("</form>\n");
    			
    			out.append("</BODY></HTML>");
    			return out.toString();
			}
        });
        
        Spark.post(new Route("/upload") {

			@Override
			public Object handle(Request request, Response response) {
				String user = request.queryParams("username");
				String fileName = request.queryParams("file");
				String inputDirectory = request.queryParams("path");
	      	  	String password = request.queryParams("pass");
	      	  	
	      	  	
	      	  	
	      	  	//dbWrapper.insertData(user, fileName, password);
	      	  	Neo4JStore main = new Neo4JStore();
	      	  	main.insert(inputDirectory);
	      	  	return "OK";
			}
        });
	}

}
