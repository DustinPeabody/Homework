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

import com.sun.net.httpserver.*;

public class Server{
	public static void main(String[] args) throws IOException {
		//The two tables I'll be using
		ConcurrentHashMap<String, String> usersTable = new ConcurrentHashMap<String, String>();
		ConcurrentHashMap<String, LinkedList<Message>> messagesTable = new ConcurrentHashMap<String, LinkedList<Message>>();

		//Set up socket stuff, get this server going!
		InetSocketAddress addr = new InetSocketAddress(Integer.parseInt(args[0]));
		HttpServer server = HttpServer.create(addr, 0);
		//set up those handlers
		server.createContext( "/", new RootHandler(usersTable, messagesTable));	
		server.createContext( "/users", new UsersHandler(usersTable));
		server.createContext( "/user/", new UserHandler(usersTable, messagesTable));
		server.createContext( "/topics", new TopicsHandler(usersTable, messagesTable));
		server.createContext( "/topic/", new TopicHandler(usersTable, messagesTable));
		server.createContext( "/message/", new MessageHandler(usersTable, messagesTable));
			
		server.setExecutor( Executors.newCachedThreadPool());
		server.start();
		System.out.println("Server is listening on port" + args[0] );

	}
}