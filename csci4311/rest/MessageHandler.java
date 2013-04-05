package csci4311.rest;

/*	Dustin Peabody
	csci4311
	PA3
	Restful Server
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
	ConcurrentHashMap<String, String> usersTable;
	ConcurrentHashMap<String, LinkedList<Message>> messagesTable;

	public MessageHandler(ConcurrentHashMap<String, String> user, ConcurrentHashMap<String, LinkedList<Message>> topic){
		this.usersTable = user;
		this.messagesTable = topic;
	}
	public void handle( HttpExchange exchange) throws IOException{
		String requestMethod = exchange.getRequestMethod();
		Headers responseHeaders = exchange.getResponseHeaders();
		
		PrintStream response = new PrintStream( exchange.getResponseBody());
			

		if( requestMethod.equalsIgnoreCase( "POST")) {
			
			BufferedReader messageLine = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
			String message = messageLine.readLine().split("\\=")[1];

			try{
				URI base = new URI ("/message/");
				URI less = base.relativize(exchange.getRequestURI());
				String userName = less.toString();

				Message inputMessage = new Message(userName,message);
				Matcher matcher = Pattern.compile("#(\\w+)").matcher(message);

				while (matcher.find()) {
					if (messagesTable.containsKey(matcher.group(1))){
						messagesTable.get(matcher.group(1)).add(inputMessage);
					}
					else{
						LinkedList<Message> newList = new LinkedList<Message>();
						newList.add(inputMessage);
						messagesTable.put(matcher.group(1),newList);
					}
				}
			}
			catch (URISyntaxException ex){
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

