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

import com.sun.net.httpserver.*;

public class UsersHandler implements HttpHandler{
	ConcurrentHashMap<String, String> usersTable;

	public UsersHandler(ConcurrentHashMap<String, String> user){
		this.usersTable = user;
	}

	public void handle( HttpExchange exchange) throws IOException{
		
		String requestMethod = exchange.getRequestMethod();
		Headers responseHeaders = exchange.getResponseHeaders();
		
		PrintStream response = new PrintStream( exchange.getResponseBody());

		if( requestMethod.equalsIgnoreCase( "GET")) {
			responseHeaders.set( "Content-Type", "json");
			exchange.sendResponseHeaders( 200, 0);
			Enumeration<String> users = usersTable.keys();
			response.print("{ [");

			StringBuilder temp = new StringBuilder();
			while (users.hasMoreElements()){
				temp.append("\"" + users.nextElement() + "\", ");
			}
			if(temp.length() >0){
				temp.deleteCharAt(temp.length()-1);
				temp.deleteCharAt(temp.length()-1);
			}
			response.print(temp);
			response.print("] }");

			
		}
		else{
			responseHeaders.set( "Content-Type", "json");
			exchange.sendResponseHeaders( 405, 0);
			response.print("Not Allowed");
		}
		response.close();
	}

}