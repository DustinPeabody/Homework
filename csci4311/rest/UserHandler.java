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
import java.net.*;

import com.sun.net.httpserver.*;


public class UserHandler implements HttpHandler{
	ConcurrentHashMap<String, String> usersTable;
	ConcurrentHashMap<String, LinkedList<Message>> messagesTable;

	public UserHandler(ConcurrentHashMap<String, String> user, ConcurrentHashMap<String, LinkedList<Message>> messages){
		this.usersTable = user;
		this.messagesTable = messages;
	}
	public void handle( HttpExchange exchange) throws IOException{
		String requestMethod = exchange.getRequestMethod();
		Headers responseHeaders = exchange.getResponseHeaders();
		
		PrintStream response = new PrintStream( exchange.getResponseBody());

		if( requestMethod.equalsIgnoreCase( "GET")) {

			try {
				URI base = new URI ("/user/");
				URI less = base.relativize(exchange.getRequestURI());
				String userName = less.toString();

				if(usersTable.containsKey(userName)){
					responseHeaders.set( "Content-Type", "json");
					exchange.sendResponseHeaders( 200, 0);
					response.print("{ ");
					response.print("id:\"" + userName + ", name:\"" + usersTable.get(userName) +"\"");
					response.print("}");
				}

				else{
					responseHeaders.set( "Content-Type", "json");
					exchange.sendResponseHeaders( 404, 0);
					response.print("User Not Found");
				}
			}
			catch (URISyntaxException ex){
				exchange.sendResponseHeaders(404,0);
				response.print("404");
			}
			
		}

		else if( requestMethod.equalsIgnoreCase( "PUT")){
			try {
				URI base = new URI ("/user/");
				URI less = base.relativize(exchange.getRequestURI());
				String userName = less.toString().split("\\?")[0];
				String query = less.getQuery().split("=")[1];

				if(usersTable.containsKey(userName)){
					responseHeaders.set( "Content-Type", "json");
					exchange.sendResponseHeaders( 405, 0);
					response.print("Not Allowed");
				}
				else{
					usersTable.put(userName, query);
					responseHeaders.set( "Content-Type", "json");
					exchange.sendResponseHeaders( 200, 0);
					response.print("{}");
				}
			}
			catch (URISyntaxException ex){
				exchange.sendResponseHeaders(404,0);
				response.print("404");
			}

		}
		else if( requestMethod.equalsIgnoreCase( "DELETE")){
			try {
				URI base = new URI ("/user/");
				URI less = base.relativize(exchange.getRequestURI());
				String userName = less.toString();

				if(usersTable.containsKey(userName)){
					Enumeration<String> topics = messagesTable.keys();
					while (topics.hasMoreElements()){
						LinkedList<Message> tempList = messagesTable.get(topics.nextElement());
						ListIterator<Message> iter = tempList.listIterator(0);
						while(iter.hasNext()){
							if(iter.next().getUser().equals(userName)){
								iter.remove();
							}
						}
					}
					usersTable.remove(userName);
					responseHeaders.set( "Content-Type", "json");
					exchange.sendResponseHeaders( 200, 0);
					response.print("{}");
				}

				else{
					responseHeaders.set( "Content-Type", "json");
					exchange.sendResponseHeaders( 404, 0);
					response.print("User Not Found");
				}
			}
			catch (URISyntaxException ex){
				exchange.sendResponseHeaders(404,0);
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