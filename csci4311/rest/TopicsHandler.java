package csci4311.rest;

/*	Dustin Peabody
	csci4311
	PA3
	Restful Server
	Topics Handler
*/

import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ConcurrentHashMap;

import com.sun.net.httpserver.*;

public class TopicsHandler implements HttpHandler{
	private ConcurrentHashMap<String, String> usersTable;
	private ConcurrentHashMap<String, LinkedList<Message>> messagesTable;

	public TopicsHandler(ConcurrentHashMap<String, String> user, ConcurrentHashMap<String, LinkedList<Message>> topic){
		this.usersTable = user;
		this.messagesTable = topic;
	}
	public void handle( HttpExchange exchange) throws IOException{
		//standard method and request fare
		String requestMethod = exchange.getRequestMethod();
		Headers responseHeaders = exchange.getResponseHeaders();
		
		PrintStream response = new PrintStream( exchange.getResponseBody());

		if( requestMethod.equalsIgnoreCase( "GET")) {
			//get all the topics
			responseHeaders.set( "Content-Type", "json");
			exchange.sendResponseHeaders( 200, 0);
			//loop through and build the response
			Enumeration<String> topics = messagesTable.keys();
			response.print("{ [");

			StringBuilder temp = new StringBuilder();
			while (topics.hasMoreElements()){
				temp.append("\"" + topics.nextElement() + "\", ");
			}
			if(temp.length() >0){
				temp.deleteCharAt(temp.length()-1);
				temp.deleteCharAt(temp.length()-1);
			}
			//send the response
			response.print(temp);
			response.print("] }");
		}
		else{
			//no other methods allowed
			responseHeaders.set( "Content-Type", "json");
			exchange.sendResponseHeaders( 405, 0);
			response.print("Not Allowed");
		}
		response.close();

	}

}