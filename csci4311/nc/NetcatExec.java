package csci4311.nc;
/*
	Dustin Peabody
	CSCI 4311
	Homework 2
	Java Netcat Exec
*/
import java.io.*;
import java.net.*;
import java.lang.*;

public class NetcatExec{
	public static void main(String[] args) throws Exception{
		ServerSocket servrar = new ServerSocket(Integer.parseInt(args[0]));
		// open the socket for clients to connet to
		byte[] dataBuffer = new byte[1024];
		//buffer for the data read from and too the streams
		int bytesRead = 0;
		//used for read, read returns -1 if the stream is empty

		while(true){
	
			Socket connectionSocket = servrar.accept();

			OutputStream toClient = connectionSocket.getOutputStream();
			//open an output stream 

			InputStream fromClient = connectionSocket.getInputStream();
			//open the socket from the client
			//Use a process Builder to build a new process from the command line
			Process clientProcess = new ProcessBuilder(args[1]).start();
			InputStream processInStream = clientProcess.getInputStream();
			OutputStream processOutStream = clientProcess.getOutputStream();
			//get the process's output streams
			
			while(true){
					
					while((processInStream.available() > 0) && ((bytesRead = processInStream.read(dataBuffer)) > 0)) {
						//while there are bytes to be read from the inputstream
						//read them 
						toClient.write(dataBuffer, 0, bytesRead);
						//write them to the outputstream
						dataBuffer = new byte[1024];
						//flash the buffer to get rid of junk data
						toClient.flush();
						
					}


					while((fromClient.available() > 0) && ((bytesRead = fromClient.read(dataBuffer)) > 0)) {
						//read bytes from the input stream as long as there are bytes to read
						processOutStream.write(dataBuffer, 0, bytesRead);
						//write the data to the outstream
						dataBuffer = new byte[1024];
						//flash buffer
						processOutStream.flush();
					}
			}
		}
	}
}