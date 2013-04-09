package csci4311.rest;

/*	Dustin Peabody
	csci4311
	PA3
	Restful Server
	Root Handler
*/

import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ConcurrentHashMap;

import com.sun.net.httpserver.*;

public class RootHandler implements HttpHandler{
	private ConcurrentHashMap<String, String> usersTable;
	private ConcurrentHashMap<String, LinkedList<Message>> messagesTable;

	public RootHandler(ConcurrentHashMap<String, String> user, ConcurrentHashMap<String, LinkedList<Message>> topic){
		this.usersTable = user;
		this.messagesTable = topic;
	}
	public void handle( HttpExchange exchange) throws IOException{
		//match everything else with only 404 
		String requestMethod = exchange.getRequestMethod();
		Headers responseHeaders = exchange.getResponseHeaders();
		
		PrintStream response = new PrintStream( exchange.getResponseBody());
		responseHeaders.set( "Content-Type", "json");
		exchange.sendResponseHeaders( 404, 0);
		response.print("Page Not Found");

	}

}