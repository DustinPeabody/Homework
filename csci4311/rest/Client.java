package csci4311.rest;

/*	Dustin Peabody
	csci4311
	PA4
	Restful Client
*/

import java.io.*;
import java.net.*;
import java.util.Scanner;

	public class Client{
		public static void main(String[] args) throws IOException{
			//The user that is currently signed-in
			String currentUser = "";
			String baseURL = "http://" + args[0] + ":" + args[1] +"/";
			

			// URL url = new URL("http://" + args[0] + ":" + args[1] + "/users");
			// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// conn.setRequestMethod("GET");
			// conn.setRequestProperty("Accept", "application/json");
			// BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
 
			// String output;
			// System.out.println("Output from Server .... \n");
			// while ((output = br.readLine()) != null) {
			// 	System.out.println(output);
			// }
 
			// conn.disconnect();
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String consumed;
			String command = "";
			String part1;
			String part2;


			while(!command.equals("quit")){
					System.out.print(currentUser + ">");
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
							executeCommand(command,part1,part2,currentUser,baseURL);

						}
						//the command and a single option
						else if(partsSize == 2){
							command = parts[0];
							part1 = parts[1];
							part2 = "";
							executeCommand(command,part1,part2,currentUser,baseURL);

						}
						//the command and two options
						else if(partsSize == 3){
							command = parts[0];
							part1 = parts[1];
							part2 = parts[2];	
							executeCommand(command,part1,part2,currentUser,baseURL);
						}
						//anymore than a command and two options is a no go
						else{
							System.err.println("Ussage: !<command> <id_string>|<topic> \"<alpha_string>\"");
						}
						
					}
					else{
						//post line as a message from current user
					}

			}


		}

		//Determines the method being called by the user and executes it
		private static void executeCommand(String command, String option1, String option2, String currentUser, String baseURL){

			if(command.equals("mkuser")){
				makeUser(option1,option2,baseURL);
			}
			else if(command.equals("rmuser")){
				removeUser(option1,baseURL);
			}
			else if(command.equals("user")){
				user(baseURL);
			}
			else if(command.equals("login")){
				login(option1, currentUser,baseURL);
			}
			else if(command.equals("topics")){
				topics(baseURL);
			}
			else if(command.equals("rmtopic")){
				removeTopic(option1,baseURL);
			}
			else if(command.equals("post")){
				post(option1, currentUser,baseURL);
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
		private static void makeUser(String id, String name, String baseURL){
			HttpURLConnection conn = null;
			try{
				URL url = new URL(baseURL +"user/" + id + "?name=" + name);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("PUT");
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

		private static void removeUser(String id, String baseURL){
			
			
		}

		private static void user(String baseURL){
			
		}

		private static void login(String id, String currentUser, String baseURL){
			
		}

		private static void topics(String baseURL){
			
		}

		private static void removeTopic(String topic, String baseURL){
			
		}

		private static void post(String filePath, String currentUser, String baseURL){
			
		}
	}