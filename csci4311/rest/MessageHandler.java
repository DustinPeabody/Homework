package csci4311.rest;

/*	Dustin Peabody
	csci4311
	PA3
	Restful Server
	Message Handler
*/

import java.io.*;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.regex.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ConcurrentHashMap;

import java.net.*;

import com.sun.net.httpserver.*;

public class MessageHandler implements HttpHandler{
	private ConcurrentHashMap<String, String> usersTable;
	private ConcurrentHashMap<String, LinkedList<Message>> messagesTable;

	public MessageHandler(ConcurrentHashMap<String, String> user, ConcurrentHashMap<String, LinkedList<Message>> topic){
		this.usersTable = user;
		this.messagesTable = topic;
	}
	public void handle( HttpExchange exchange) throws IOException{
		String requestMethod = exchange.getRequestMethod();
		Headers responseHeaders = exchange.getResponseHeaders();
		
		PrintStream response = new PrintStream( exchange.getResponseBody());
			

		if( requestMethod.equalsIgnoreCase( "POST")) {
			//if we're posting get the message
			BufferedReader messageLine = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
			// the message is always preceded by "message=" so get everything following the =
			String message = messageLine.readLine().split("\\=")[1];

			try{
				URI base = new URI ("/message/");
				URI less = base.relativize(exchange.getRequestURI());
				String userName = less.toString();
				//create a new message
				Message inputMessage = new Message(userName,message);
				//this regex if for parsing out the topics which are words starting with a # sign
				Matcher matcher = Pattern.compile("#(\\w+)").matcher(message);

				while (matcher.find()) {
					//takes the topic found with each pass
					if (messagesTable.containsKey(matcher.group(1))){
						//if the topic already exists then add the message to the list for that key
						messagesTable.get(matcher.group(1)).add(inputMessage);
					}
					else{
						//otherwise create that entry and then add it to the table
						LinkedList<Message> newList = new LinkedList<Message>();
						newList.add(inputMessage);
						messagesTable.put(matcher.group(1),newList);
					}
				}
			}
			catch (URISyntaxException ex){
				//oops
				response.print("404");
			}
			responseHeaders.set( "Content-Type", "json");
			exchange.sendResponseHeaders( 200, 0);
			response.print("{}");

		}
		else{
			responseHeaders.set( "Content-Type", "json");
			exchange.sendResponseHeaders( 405, 0);
			response.print("Not Allowed");
		}
		response.close();

	}
}

