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
					if(consumed.charAt(0) == '!'){
						String[] parts = consumed.substring(1).split(" ");
						int partsSize = parts.length;
						if(partsSize == 1){
							command = parts[0];

						}
						else if(partsSize == 2){
							command = parts[0];
							part1 = parts[1];

						}
						else if(partsSize == 3){
							command = parts[0];
							part1 = parts[1];
							part2 = parts[2];	
						}
						else{
							System.err.println("Ussage: !<command> <id_string>|<topic> \"<alpha_string>\"");
						}
						

					}

			}


		}
	}