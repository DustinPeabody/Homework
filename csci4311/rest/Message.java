package csci4311.rest;

/*	Dustin Peabody
	csci4311
	PA3
	Restful Server
*/

public class Message{
	//Messages have a poster who posted that message 
	private String poster;
	//and the message itself
	private String message;

	public Message(String poster, String message){
		this.poster = poster;
		this.message = message;
	}
	//getters for each user and message 
	public String getUser(){
		return this.poster;
	}

	public String getMessage(){
		return this.message;
	}
	//no setters not needed
}	