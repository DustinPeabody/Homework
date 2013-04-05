package csci4311.rest;

/*	Dustin Peabody
	csci4311
	PA3
	Restful Server

*/

import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ConcurrentHashMap;
import java.util.LinkedList;

import java.net.*;

import com.sun.net.httpserver.*;

public class TopicHandler implements HttpHandler{
	ConcurrentHashMap<String, String> usersTable;
	ConcurrentHashMap<String, LinkedList<Message>> messagesTable;

	public TopicHandler(ConcurrentHashMap<String, String> user, ConcurrentHashMap<String, LinkedList<Message>> topic){
		this.usersTable = user;
		this.messagesTable = topic;
	}
	public void handle( HttpExchange exchange) throws IOException{

		String requestMethod = exchange.getRequestMethod();
		Headers responseHeaders = exchange.getResponseHeaders();
		
		PrintStream response = new PrintStream( exchange.getResponseBody());

		if( requestMethod.equalsIgnoreCase( "GET")) {

			try {
				URI base = new URI ("/topic/");
				URI less = base.relativize(exchange.getRequestURI());
				String topicName = less.toString();

				if(messagesTable.containsKey(topicName)){
					responseHeaders.set( "Content-Type", "json");
					exchange.sendResponseHeaders( 200, 0);
					response.print("{ [ ");
					LinkedList<Message> tempList = messagesTable.get(topicName);
					for(int i=0; i < tempList.size(); i++){
						response.print("{id:\"" + tempList.get(i).getUser() + ", message:\"" + tempList.get(i).getMessage() +"\"}");
						if(i < tempList.size()-1){
							response.print(", ");
						}
					}
					response.print(" ] }");
				}

				else{
					responseHeaders.set( "Content-Type", "json");
					exchange.sendResponseHeaders( 200, 0);
					response.print("{}");
				}
			}
			catch (URISyntaxException ex){
				response.print("404");
			}
			
		}
		else if( requestMethod.equalsIgnoreCase("DELETE")){
			try {
				URI base = new URI ("/topic/");
				URI less = base.relativize(exchange.getRequestURI());
				String topicName = less.toString();

				responseHeaders.set( "Content-Type", "json");
				exchange.sendResponseHeaders( 200, 0);
				messagesTable.remove(topicName);
				response.print("{}");
			}
			catch (URISyntaxException ex){
				response.print("404");
			}
		}
		else{
			responseHeaders.set( "Content-Type", "json");
			exchange.sendResponseHeaders( 405, 0);
			response.print("Not Allowed");
		}
		response.close();

	}

}