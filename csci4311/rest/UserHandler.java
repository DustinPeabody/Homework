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


public class UserHandler implements HttpHandler{
	ConcurrentHashMap<String, String> usersTable;
	ConcurrentHashMap<String, String[]> messagesTable;

	public UserHandler(ConcurrentHashMap<String, String> user, ConcurrentHashMap<String, String[]> topic){
		this.usersTable = user;
		this.messagesTable = topic;
	}
	public void handle( HttpExchange exchange) throws IOException{

	}

}