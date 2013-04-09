package csci4311.rest;

/*	Dustin Peabody
	csci4311
	PA3
	Restful Server
	User Handler
*/

import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ConcurrentHashMap;

import com.sun.net.httpserver.*;

public class UsersHandler implements HttpHandler{
	//only need the usersTable
	private ConcurrentHashMap<String, String> usersTable;

	public UsersHandler(ConcurrentHashMap<String, String> user){
		this.usersTable = user;
	}

	public void handle( HttpExchange exchange) throws IOException{
		//get the method name and set up the headers
		
		String requestMethod = exchange.getRequestMethod();
		Headers responseHeaders = exchange.getResponseHeaders();
		
		//get ready for returns!
		PrintStream response = new PrintStream( exchange.getResponseBody());

		if( requestMethod.equalsIgnoreCase( "GET")) {
			//if we're in get mode
			//set up the response stuff
			responseHeaders.set( "Content-Type", "json");
			exchange.sendResponseHeaders( 200, 0);
			Enumeration<String> users = usersTable.keys();
			response.print("{ [");
			//format the response stuff
			StringBuilder temp = new StringBuilder();
			while (users.hasMoreElements()){
				temp.append("\"" + users.nextElement() + "\", ");
			}
			if(temp.length() >0){
				//this is to get rid of the trailing commas
				temp.deleteCharAt(temp.length()-1);
				temp.deleteCharAt(temp.length()-1);
			}
			//send the response on out
			response.print(temp);
			response.print("] }");

			
		}
		else{

			//only gets are allowed
			responseHeaders.set( "Content-Type", "json");
			exchange.sendResponseHeaders( 405, 0);
			response.print("Not Allowed");
		}
		response.close();
	}

}