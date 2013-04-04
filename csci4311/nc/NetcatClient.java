package csci4311.nc;
/*
	Dustin Peabody
	CSCI 4311
	Homework 2
	Java Netcat Chat
*/
import java.io.*;
import java.net.*;
public class NetcatClient{
	public static void main(String[] args) throws Exception{
		byte[] dataBuffer = new byte[1024];//buffer for the data read from and too the streams
		int bytesRead = 0; //used for read, read returns -1 if the stream is empty
		Socket clientSocket = new Socket(args[0],Integer.parseInt(args[1]));
		//open a new socket with the spedified ip and socket number
		OutputStream toServer = clientSocket.getOutputStream();
		//open an output stream to write to 
		InputStream fromServer = clientSocket.getInputStream();
		//open the socket for download
		while(true){
				while((System.in.available() > 0) && ((bytesRead = System.in.read(dataBuffer)) > 0)) {
				//while there are bytes to read do it
				toServer.write(dataBuffer, 0, bytesRead);
				//write them
				dataBuffer = new byte[1024];
				//flash the buffer

				}
				while((fromServer.available() > 0) && ((bytesRead = fromServer.read(dataBuffer)) > 0)) {
					//read dem bytes till there are no more
					System.out.write(dataBuffer, 0, bytesRead);
					//write dem bytes
					dataBuffer = new byte[1024];
					//flash dat buffer
				}
		}
	}
}