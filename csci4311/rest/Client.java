package csci4311.rest;

/*	Dustin Peabody
	csci4311
	PA4
	Restful Client
*/

import java.io.*;
import java.net.*;
import java.util.regex.*;

	public class Client{
		String currentUser = "";
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String baseURL = "";
		String consumed;
		String command = "";
		String part1;
		String part2;

	//Make a new client and build the base string
	 public Client(String url1, String port){
	 	baseURL = "http://" + url1 + ":" + port +"/";


	 }
	 	//make a new client and pass the args to build the url
		public static void main(String[] args) throws IOException{
			new Client(args[0], args[1]).run();
		}

		//main loop contains the tui logic
		private void run(){
			while(!command.equals("quit")){
					System.out.print(currentUser + ">");
					try{
						consumed = input.readLine();
						//If the input is a command
						if(consumed.charAt(0) == '!'){
							//parse out the command and the options
							String[] parts = consumed.substring(1).split(" ");
							int partsSize = parts.length;

							//only the command is entered
							if(partsSize == 1){
								command = parts[0];
								part1="";
								part2="";
								executeCommand(command,part1,part2);

							}
							//the command and a single option
							else if(partsSize == 2){
								command = parts[0];
								part1 = parts[1];
								part2 = "";
								executeCommand(command,part1,part2);

							}
							//the command and two options
							else if(partsSize == 3){
								command = parts[0];
								part1 = parts[1];
								part2 = parts[2];	
								executeCommand(command,part1,part2);
							}
							//anymore than a command and two options is a no go
							else{
								System.err.println("Ussage: !<command> <id_string>|<topic> \"<alpha_string>\"");
							}
							
						}
						else{
							post(consumed);
							//post line as a message from current user
						}
					}
					catch(IOException e){
						System.err.println("IOException");
					}

			}
		}

		//Determines the method being called by the user and executes it
		private void executeCommand(String command, String option1, String option2){

			if(command.equals("mkuser")){
				makeUser(option1,option2);
			}
			else if(command.equals("rmuser")){
				removeUser(option1);
			}
			else if(command.equals("user")){
				user(option1);
			}
			else if(command.equals("users")){
				users();
			}
			else if(command.equals("login")){
				login(option1);
			}
			else if(command.equals("topics")){
				topics();
			}
			else if(command.equals("rmtopic")){
				removeTopic(option1);
			}
			else if(command.equals("post")){
				postFile(option1);
			}
			else if(command.equals("quit")){
				System.exit(0);
			}
			else{
				System.err.println("Unknown command. Please try again");
			}
		}


		//Make a user with the given id and name
		//Throws an io exception if the user didn't have the right number of arguments, its caught though
		private void makeUser(String id, String name){
			HttpURLConnection conn = null;
			try{
				URL url = new URL(baseURL +"user/" + id + "?name=" + name);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("PUT");
				conn.setRequestProperty("Accept", "application/json");
				//this line is neccessary to actually send the request;
				conn.getInputStream();
	 
				conn.disconnect();
			}

			catch(MalformedURLException e){
				System.err.println("Bad URL. Please make sure the URL is correct.");
			}

			catch(IOException i){
				try{
					System.err.println(conn.getResponseCode() + " " + conn.getResponseMessage());
				}
				catch(IOException p){
					System.err.println("Wrong number of arguments for this method, try again.");
				}
			}

		}

		//removes the user with the given id
		private void removeUser(String id){
			HttpURLConnection conn = null;
			try{
				URL url = new URL(baseURL +"user/" + id);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("DELETE");
				conn.setRequestProperty("Accept", "application/json");
				conn.getInputStream();
	 
				conn.disconnect();
			}

			catch(MalformedURLException e){
				System.err.println("Bad URL. Please make sure the URL is correct.");
			}

			catch(IOException i){
				try{
					System.err.println(conn.getResponseCode() + " " + conn.getResponseMessage());
				}
				catch(IOException p){
					System.err.println("Wrong number of arguments for this method, try again.");
				}
			}
		}

		private void user(String id){
			HttpURLConnection conn = null;
			try{
				URL url = new URL(baseURL +"user/" + id);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				String output;
				while ((output = br.readLine()) != null) {
					System.out.println(output.split("\"name\":")[1].replaceAll("[^\\p{L}\\p{N}]", ""));
				}
	 
				conn.disconnect();
			}

			catch(MalformedURLException e){
				System.err.println("Bad URL. Please make sure the URL is correct.");
			}

			catch(IOException i){
				try{
					System.err.println(conn.getResponseCode() + " " + conn.getResponseMessage());
				}
				catch(IOException p){
					System.err.println("Wrong number of arguments for this method, try again.");
				}
			}
			
		}

		private void users(){
			HttpURLConnection conn = null;
			try{
				URL url = new URL(baseURL +"users");
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				String output;
				while ((output = br.readLine()) != null) {
					System.out.println(output);
				}
	 
				conn.disconnect();
			}

			catch(MalformedURLException e){
				System.err.println("Bad URL. Please make sure the URL is correct.");
			}

			catch(IOException i){
				try{
					System.err.println(conn.getResponseCode() + " " + conn.getResponseMessage());
				}
				catch(IOException p){
					System.err.println("Wrong number of arguments for this method, try again.");
				}
			}
			
		}

		private void login(String id){
			HttpURLConnection conn = null;
			try{
				URL url = new URL(baseURL +"user/" + id);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	 
				conn.disconnect();
				currentUser = id;
			}

			catch(MalformedURLException e){
				System.err.println("Bad URL. Please make sure the URL is correct.");
			}

			catch(IOException i){
				try{
					System.err.println(conn.getResponseCode() + " " + conn.getResponseMessage());
				}
				catch(IOException p){
					System.err.println("Wrong number of arguments for this method, try again.");
				}
			}
			
		}

		private void topics(){
			HttpURLConnection conn = null;
			try{
				URL url = new URL(baseURL +"topics");
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				String output;
				while ((output = br.readLine()) != null) {
					System.out.println(output);
				}
	 
				conn.disconnect();
			}

			catch(MalformedURLException e){
				System.err.println("Bad URL. Please make sure the URL is correct.");
			}

			catch(IOException i){
				try{
					System.err.println(conn.getResponseCode() + " " + conn.getResponseMessage());
				}
				catch(IOException p){
					System.err.println("Wrong number of arguments for this method, try again.");
				}
			}
			
		}

		private void removeTopic(String topic){
			HttpURLConnection conn = null;
			try{
				URL url = new URL(baseURL +"topic/" + topic);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("DELETE");
				conn.setRequestProperty("Accept", "application/json");
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	 
				conn.disconnect();
			}

			catch(MalformedURLException e){
				System.err.println("Bad URL. Please make sure the URL is correct.");
			}

			catch(IOException i){
				try{
					System.err.println(conn.getResponseCode() + " " + conn.getResponseMessage());
				}
				catch(IOException p){
					System.err.println("Wrong number of arguments for this method, try again.");
				}
			}
			
		}

		private void postFile(String filePath){
			try{
				BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
				String output;
				while ((output = fileReader.readLine()) != null) {
					post(output);
				}
			}
			catch(IOException e){
				System.err.println("File not found.");
			}
		}

		private void post(String message){
			HttpURLConnection conn = null;
			try{
				URL url = new URL(baseURL +"message/" + currentUser);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Accept", "application/json");
				
      			conn.setDoOutput(true);

      			//Send request
      			DataOutputStream wr = new DataOutputStream (conn.getOutputStream ());
			    wr.writeBytes ("message=" + message);
			    wr.flush ();
			    wr.close ();

			    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
	 
				conn.disconnect();
			}

			catch(MalformedURLException e){
				System.err.println("Bad URL. Please make sure the URL is correct.");
			}

			catch(IOException i){
				try{
					System.err.println(conn.getResponseCode() + " " + conn.getResponseMessage());
				}
				catch(IOException p){
					System.err.println("Wrong number of arguments for this method, try again.");
				}
			}

		}
	}