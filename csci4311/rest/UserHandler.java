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
import java.net.*;

import com.sun.net.httpserver.*;


public class UserHandler implements HttpHandler{
	private ConcurrentHashMap<String, String> usersTable;
	private ConcurrentHashMap<String, LinkedList<Message>> messagesTable;

	public UserHandler(ConcurrentHashMap<String, String> user, ConcurrentHashMap<String, LinkedList<Message>> messages){
		this.usersTable = user;
		this.messagesTable = messages;
	}
	public void handle( HttpExchange exchange) throws IOException{
		//again the starting stuff
		String requestMethod = exchange.getRequestMethod();
		Headers responseHeaders = exchange.getResponseHeaders();
		
		PrintStream response = new PrintStream( exchange.getResponseBody());

		if( requestMethod.equalsIgnoreCase( "GET")) {

			try {//relativize throws an exception that the handler can't handle
				//set up the base uri to relativize against
				URI base = new URI ("/user/");
				URI less = base.relativize(exchange.getRequestURI());
				String userName = less.toString();
				//this is the uri with all the extra starting stuff removed leaving only the user name

				if(usersTable.containsKey(userName)){
					//if the user is present set up the response and send it on its way
					responseHeaders.set( "Content-Type", "json");
					exchange.sendResponseHeaders( 200, 0);
					response.print("{ ");
					response.print("id:\"" + userName + ", name:\"" + usersTable.get(userName) +"\"");
					response.print("}");
				}

				else{
					//if he's not there respond with 404 user not found
					responseHeaders.set( "Content-Type", "json");
					exchange.sendResponseHeaders( 404, 0);
					response.print("User Not Found");
				}
			}
			catch (URISyntaxException ex){
				//something goofed up so just 404
				exchange.sendResponseHeaders(404,0);
				response.print("404");
			}
			
		}

		else if( requestMethod.equalsIgnoreCase( "PUT")){
			//makin a user
			try {
				//same as above
				URI base = new URI ("/user/");
				URI less = base.relativize(exchange.getRequestURI());
				//split on the ? to get the user name
				String userName = less.toString().split("\\?")[0];
				//this guy is for the users actual name
				String query = less.getQuery().split("=")[1];

				//if the user already exists don't allow modification
				if(usersTable.containsKey(userName)){
					responseHeaders.set( "Content-Type", "json");
					exchange.sendResponseHeaders( 405, 0);
					response.print("Not Allowed");
				}
				else{
					//otherwise add the user
					usersTable.put(userName, query);
					responseHeaders.set( "Content-Type", "json");
					exchange.sendResponseHeaders( 200, 0);
					response.print("{}");
				}
			}
			catch (URISyntaxException ex){
				//something don goofed
				exchange.sendResponseHeaders(404,0);
				response.print("404");
			}

		}
		else if( requestMethod.equalsIgnoreCase( "DELETE")){
			//deletin a user
			try {
				URI base = new URI ("/user/");
				URI less = base.relativize(exchange.getRequestURI());
				String userName = less.toString();

				if(usersTable.containsKey(userName)){
					//if the user exists we need to delete all the messages he posted as well
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
					//now we delete him
					usersTable.remove(userName);
					responseHeaders.set( "Content-Type", "json");
					exchange.sendResponseHeaders( 200, 0);
					response.print("{}");
				}

				else{
					//he didn't exist so no change user not found
					responseHeaders.set( "Content-Type", "json");
					exchange.sendResponseHeaders( 404, 0);
					response.print("User Not Found");
				}
			}
			catch (URISyntaxException ex){
				//goof
				exchange.sendResponseHeaders(404,0);
				response.print("404");
			}
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