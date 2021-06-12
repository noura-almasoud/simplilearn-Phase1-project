package App;
/*
Version: 1.0
Created on: june 6, 2021, 12:28:01 AM
Author: Noura Al-Masoud
Email: nono-453@hotmail.com
*/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
	
	static String option;
	static User user;
	static Credentials userCredentials;
	static Scanner UserInput;	
	static List<String> locker_data;
	static List<String> user_data;
	static File users_file;
	static FileWriter users_writer;
	static FileWriter locker_writer;
	static PrintWriter users_db;
	static PrintWriter locker_db;
	static Boolean register;
	static Boolean found;
	
	public static void main(String[] args) {

		UserInput = new Scanner(System.in);
		
		try {
			
			//input -> write
			users_file = new File("users_db.txt");
			users_writer = new FileWriter(users_file,true);
			
		} catch (IOException e) {
			System.out.println("404 : File Not Found ");
		}
		
		welcome();
		AppOptions();

	}

	public static void welcome() {
		
		System.out.println("\nWelcome to LockMe Application");
		System.out.println("Your Personal Digital Locker");
		System.out.println("Developed by Noura Al-Masoud ");
		System.out.println("*****************************");
		
	}

	
	public static void AppOptions() {
		
		System.out.println("1-Registration");
		System.out.println("2-Login");
		System.out.println("3-Exit");
		
		option  = UserInput.next();
		
		switch (option) {
		case "1":
			registerUser();
			break;
			
		case "2":
			loginUser();
			break;
			
		case "3":
			System.out.println("Have a Nice Day");
			break;
			
		default:
			System.out.println("Please select 1, 2 or 3");
			AppOptions();
			break;
		}
		
		UserInput.close();
	}

	
	public static void loginUser() {

		System.out.println("_____________________________\n");
		System.out.println("Login Page");
		System.out.println("_____________________________\n");
		
		System.out.println("Enter Username :");
		String UserName = UserInput.next();
		
		System.out.println("Enter Password :");
		String Password = UserInput.next();
		
		register = false;

		//output -> read
		user_data = lines("users_db.txt");
		
		for (int line = 0; line <user_data.size() && !register; line++) {
			if(line % 2 == 0) {
				if(user_data.get(line).equals(UserName))
				{
					register = true;
					
					if(user_data.get(line+1).equals(Password)) {
						System.out.println("Login Successful.");
						
						iniLocker_db(UserName);
						lockerOptions(UserName);
						break;
					}
					
					System.out.println("Incorrect Password");
					loginUser();
					break;
				}
			}
		}
		
		if(!register) {
			System.out.println("User Not Found\n");
			AppOptions();
		} 
	}
	

	public static void registerUser() {
		
		System.out.println("_____________________________\n");
		System.out.println("Registration Page");
		System.out.println("_____________________________\n");
		
		System.out.println("Enter Username :");
		String UserName = UserInput.next();
		
		System.out.println("Enter Password :");
		String Password = UserInput.next();
		
		register = false;
		
		if(Pattern.matches("(?=.*[a-zA-Z])(?=.*[0-9])(?=\\S+$).{6,}", Password)) {

			//output -> read
			user_data = lines("users_db.txt");
			
			for (int line = 0; line <user_data.size() && !register; line++) {
				if(line % 2 == 0) {
					if(user_data.get(line).equals(UserName))
					{
						register = true;
						
						System.out.println("The UserName Already Exists\n");
						break;
					}
				}
			}
			
			if(register) {
				AppOptions();
			}
			else {
				user = new User(UserName, Password);
				users_db = new PrintWriter(users_writer);
				
				users_db.println(user.getUserName());
				users_db.println(user.getPassword());
				
				System.out.println("User Registration Suscessful");
				
				users_db.close();
				iniLocker_db(UserName);
				lockerOptions(UserName);
			}
		}
		else {
			System.out.println("Password must:"
					+ "\n Be at least 6 characters in length."
					+ "\n contain at least one number and one letter."
					+ "\n Does not contain space.");
			registerUser();
		}
	}
	

	public static void iniLocker_db(String UserName) {

		try {
			//input -> write
			File locker_file = new File(UserName+"_locker_db.txt");
			locker_writer = new FileWriter(locker_file,true);
			locker_db = new PrintWriter(locker_writer);
			
		} catch (IOException e) {
			System.out.println("404 : File Not Found ");
		}
		
	}

	
	public static void lockerOptions(String UserName) {
		System.out.println("_____________________________\n");
		System.out.println(UserName + " Digital Locaker");
		System.out.println("_____________________________\n");

		System.out.println("1-New Stored Credentials ");
		System.out.println("2-Fetch All Stored Credentials ");
		System.out.println("3-Search For A Specified Stored Credentials");
		System.out.println("4-Delete A Specified Stored Credentials");
		System.out.println("5-Logout");
		System.out.println("6-Logout And Exit");
		
		option = UserInput.next();
		
		switch (option) {
		case "1":
			StoredCredentials(UserName);
			break;
			
		case "2":
			FetchCredentials(UserName);
			break;
			
		case "3":
			SearchCredentials(UserName);
			break;
			
		case "4":
			DeleteCredentials(UserName);
			break;

		case "5":
			System.out.println("Logout Successful");
			AppOptions();
			break;
			
		case "6":
			System.out.println("Logout Successful");
			System.out.println("Have a Nice Day");
			break;
			
		default:
			System.out.println("Please select 1, 2, 3 or 4");
			lockerOptions(UserName);
			break;
		}
		
		UserInput.close();
	}

	
	public static void StoredCredentials(String UserName) {
		System.out.println("_____________________________\n");
		System.out.println("New Stored Credentials");
		System.out.println("_____________________________\n");
		
		System.out.println("Enter Site Name :");
		String SiteName = UserInput.next();
		
		System.out.println("Enter Username :");
		String SiteUserName = UserInput.next();
		
		System.out.println("Enter Password :");
		String SitePassword = UserInput.next();
		
		userCredentials = new Credentials(SiteName, SiteUserName, SitePassword);
		
		iniLocker_db(UserName);
		
		locker_db.println(userCredentials.getSiteName());
		locker_db.println(userCredentials.getSiteUserName());
		locker_db.println(userCredentials.getSitePassword());
		
		System.out.println("Your Card Stored Suscessfully");

		locker_db.close();
		lockerOptions(UserName);	
	}

	
	public static void FetchCredentials(String UserName) {
		System.out.println("_____________________________\n");
		System.out.println(UserName +" Stored Credentials");
		System.out.println("_____________________________\n");
		
		locker_data = lines(UserName+"_locker_db.txt");
		
		for (int line = 0; line <locker_data.size(); line++) {
			System.out.println(locker_data.get(line));
			System.out.println("Username: " + locker_data.get(++line) +
					"\tPassword: " + locker_data.get(++line) + "\n");
		}
		
		lockerOptions(UserName);
	}
	
	
	public static void SearchCredentials(String UserName) {
		System.out.println("_____________________________\n");
		System.out.println("Searching");
		System.out.println("_____________________________\n");
		
		System.out.println("Enter  Site Name :");
		String SiteName = UserInput.next();
		
		found = false;
		
		//output -> read
		locker_data = lines(UserName+"_locker_db.txt");
		
		for (int line = 0; line <locker_data.size(); line++) {
			if(line % 3 == 0) {
				if(locker_data.get(line).equals(SiteName))
				{
					found = true;
					System.out.println(locker_data.get(line));
					System.out.println("Username: " + locker_data.get(line+1) +
							"\tPassword: " + locker_data.get(line+2) + "\n");
					break;
				}
			}
		}
		
		if(found) {
			lockerOptions(UserName);
		}
		else {
			System.out.println("Incorrect Site Name / Site Dose Not Exist");
			lockerOptions(UserName);
		}
	}
	

	public static void DeleteCredentials(String UserName) {
		System.out.println("_____________________________\n");
		System.out.println("Delete A Stored Credentials");
		System.out.println("_____________________________\n");
		
		System.out.println("Enter  Site Name :");
		String SiteName = UserInput.next();
		
		found = false;
		
		//output -> read
		locker_data = lines(UserName+"_locker_db.txt");
		
		for (int line = 0; line <locker_data.size(); line++) {
			if(line % 3 == 0) {
				if(locker_data.get(line).equals(SiteName))
				{
					found = true;
					locker_data.remove(line+2);
					locker_data.remove(line+1);
					locker_data.remove(line);
					Update_locker_data(UserName, locker_data);
					System.out.println("Your Card Deleted Suscessfully");
					break;
				}
			}
		}
		
		if(found) {
			lockerOptions(UserName);
		}
		else {
			System.out.println("Incorrect Site Name / Site Dose Not Exist");
			lockerOptions(UserName);
		}
		
	}
	
	
	public static void Update_locker_data(String UserName, List<String> Data) {
		//delete all data from the file
		try {
			//input -> write
			File locker_file = new File(UserName+"_locker_db.txt");
			locker_writer = new FileWriter(locker_file,false);
			locker_db = new PrintWriter(locker_writer,false);
			locker_db.print("");
			locker_db.close();
			
		} catch (IOException e) {
			System.out.println("404 : File Not Found ");
		}
		
		//add the updated data to the file
		iniLocker_db(UserName);
		
		for (String line : Data) {
			locker_db.println(line);
		}
		
		locker_db.close();
	}

	static List<String> lines(String file) {

		List<String> lines = Collections.emptyList();	
		
		try {
			lines = Files.readAllLines(Paths.get(file),StandardCharsets.UTF_8);
		} catch (IOException e) {			
			System.out.println("File not found");
		}
		
		return lines;
	}
	
//Author: Noura Al-Masoud
	
/*
 static Scanner user_data;
 public static void loginUser() {

		System.out.println("_____________________________\n");
		System.out.println("Login Page");
		System.out.println("_____________________________\n");
		
		System.out.println("Enter Username :");
		String UserName = UserInput.next();
		
		System.out.println("Enter Password :");
		String Password = UserInput.next();
		
		Boolean register = false;
		
		try {
			//output -> read
			user_data = new Scanner(users_file);
			
			while (user_data.hasNext() && !register) {
				if(user_data.next().equals(UserName))
				{
					register = true;
					
					if(user_data.next().equals(Password)) {
						System.out.println("Login Successful.");
						
						user_data.close();
						iniLocker_db(UserName);
						lockerOptions(UserName);
						break;
					}
					
					System.out.println("Incorrect Password");
					user_data.close();
					loginUser();
					break;
				}
			}
			
			if(!register) {
				System.out.println("User Not Found");
				user_data.close();
				AppOptions();
			}
		} 
		catch (FileNotFoundException e) {
		}
	}
	
 */
}
