package csci4311.nc;
/*
	Dustin Peabody
	CSCI 4311
	Homework 2
	Java Netcat Proxy
*/
import java.io.*;
import java.net.*;
import java.util.*;
public class NetcatMulticast{
	public static void main(String[] args) throws Exception{
		//arrays of sockets and output streams so any number of servers can be talked to
		Socket[] sockets = new Socket[(args.length)/2];
		OutputStream[] outs = new OutputStream[sockets.length];

		ServerSocket servrar = new ServerSocket(Integer.parseInt(args[0]));
		int j = 0;
		//make those sockets
		for(int i = 0; i < sockets.length; i++){

			sockets[i] = new Socket(args[j+1],Integer.parseInt(args[j+2]));
			j = j + 2;
		}
		//get da streams
		for(int i = 0; i<outs.length; i++){
			outs[i] = sockets[i].getOutputStream();
		}

		// open the socket for clients to connet to
		byte[] dataBuffer = new byte[1024];
		//buffer for the data read from and too the streams
		int bytesRead = 0;
		//used for read, read returns -1 if the stream is empty

		while(true){
	
			Socket connectionSocket = servrar.accept();

			InputStream fromClient = connectionSocket.getInputStream();
			//open the socket from the client 
			
			while(true){
					
					while((fromClient.available() > 0) && ((bytesRead = fromClient.read(dataBuffer)) > 0)) {
						//read bytes from the input stream as long as there are bytes to read
						for(int i =0; i<outs.length; i++){
							outs[i].write(dataBuffer, 0, bytesRead);
						}
						//write the data to the outstreams
						dataBuffer = new byte[1024];
						//flash buffer
					}
			}
		}
	}
}