package csci4311.nc;
/*
	Dustin Peabody
	CSCI 4311
	Homework 2
	Java Netcat Chat
*/
import java.io.*;
import java.net.*;
public class NetcatServer{
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
			//open the socket from the client to download a file
			
			while(true){
					
					while((System.in.available() > 0) && ((bytesRead = System.in.read(dataBuffer)) > 0)) {
						//while there are bytes to be read from the inputstream
						//read them 
						toClient.write(dataBuffer, 0, bytesRead);
						//write them to the outputstream
						dataBuffer = new byte[1024];
						//flash the buffer to get rid of junk data
						
					}


					while((fromClient.available() > 0) && ((bytesRead = fromClient.read(dataBuffer)) > 0)) {
						//read bytes from the input stream as long as there are bytes to read
						System.out.write(dataBuffer, 0, bytesRead);
						//write the data to the outstream
						dataBuffer = new byte[1024];
						//flash buffer
					}
			}
		}
	}
}