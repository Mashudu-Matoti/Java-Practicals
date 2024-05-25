package csc2b.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;

public class BUKAHandler implements Runnable
{
	/**Attributes*/
	Socket cSocket;
	BufferedReader br;
	PrintWriter pw ;
	DataOutputStream dos;
	DataInputStream dis;
	OutputStream os;
	InputStream is;
	
	String username;
	String password;
	String line; // to read in 
	
	/**
	 * 
	 * @param newConnectionToClient Socket for server side
	 * @throws IOException
	 */
    public BUKAHandler(Socket newConnectionToClient) throws IOException
    {	
	//Bind streams
    	this.cSocket = newConnectionToClient;
    	os = cSocket.getOutputStream();
    	is = cSocket.getInputStream();
    	pw = new PrintWriter(os, true);// auto-flush
    	br = new BufferedReader(new InputStreamReader(is));
    	dos = new DataOutputStream(os);
    	dis = new DataInputStream(is);
    	
    }
    /**
     * run function exectuted on multi-threading
     */
    public void run()
    {
    	
	//Process commands from client		
    	while(true) {
    		try {
				line = br.readLine();
				//displays client response to console
				
				if(line!= null) {
					System.out.print(line);
				
				StringTokenizer st = new StringTokenizer(line," ");

				//Check login details
				switch(st.nextToken()) {
				
					case "AUTH":{
						//Check user name and password
						
						if(matchUser(st.nextToken(),st.nextToken())) {
							pw.println("Login Successful");
						}
						else {
							pw.println("Invalid username/password");
						}
						break;
					}//List Command
					case "LIST":{
						String strLine= new String();
						for(String s: getFileList()) {
							strLine+= s+ "#";
						}
						pw.println(strLine); //stores each string in one line
						
						
						break;
					
					}//Returns file for download
					case "PDFRET":{
						String retFile =idToFile(st.nextToken());
						StringTokenizer st2= new StringTokenizer(retFile);//tokenize string to get file name
						String id = st2.nextToken();

						String fileName = st2.nextToken();
						pw.println(fileName);// send file size
						System.out.println("Sending: "+fileName);
						File file = new File("./data/server/file");
						pw.println(file.length());
						//reads file as bytes to send to client
						if(file.exists()) {
							pw.println((int) file.length());
							FileInputStream fis = new FileInputStream(file);
							byte[] buffer = new byte[1600];
							int n=0;
							while((n=fis.read(buffer))>0) {
								
								dos.write(buffer,0,n);
								dos.flush();
							}
							fis.close();
							System.out.println("File sent to client");
						}{
							pw.write("Requested File does not exist");
						}
						
						
						
						break;
					}
					default:{
						pw.write("Please add valid command");
					}
				}
				}
			} catch (IOException e) {
				System.out.println("Could not read in Client Commands");
				e.printStackTrace();
			}
    		
    	}
    	
    }
    /**
     * Checks username and password from client
     * @param username 
     * @param password
     * @return
     */
    private boolean matchUser(String username,String password)
    {
	boolean found = false;
	File userFile = new File("data/server/users.txt"/*OMITTED - Enter file location*/);
	try
	{
		//Code to search users.txt file for match with username and password
	    Scanner scan = new Scanner(userFile);
	    while(scan.hasNextLine()&&!found)
	    {
		String line = scan.nextLine();
		String lineSec[] = line.split("\\s");
    		
		//***OMITTED - Enter code here to compare user*** 
		this.username = lineSec[0];
		this.password = lineSec[1];
	    }
	    scan.close();
	    
	    if(username.equals(this.username) && password.equals(this.password)) {
	    	found= true;
	    }
	}
	catch(IOException ex)
	{
	    ex.printStackTrace();
	}
	
	return found;
    }
    
    /**
     * Retrieves list of available files from the system
     * @return result
     */
    private ArrayList<String> getFileList()
    {
		ArrayList<String> result = new ArrayList<String>();
		//Code to add list text file contents to the arraylist.
		File lstFile = new File("data/server/PdfList.txt"/*OMITTED - Enter file location*/);
		try
		{
			String line;
			Scanner scan = new Scanner(lstFile);

			//***OMITTED - Read each line of the file and add to the arraylist***
			while(scan.hasNext()) {
				line = scan.nextLine();
				result.add(line);
			}
			
			scan.close();
		}	    
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		
		return result;
    }
    /**
     * takes in file ID from user and returns appropriate file
     * @param ID
     * @return
     */
    private String idToFile(String ID)
    {
    	String result = "";
    	//Code to find the file name that matches strID
    	File lstFile = new File("data/server/PdfList.txt"/*OMITTED - Enter file location*/);
    	try
    	{
    		Scanner scan = new Scanner(lstFile);
    		String line = "";
    		//***OMITTED - Read filename from file and search for filename based on ID***
    		while(scan.hasNext()) {
    			line = scan.nextLine();
    			if(line.contains(ID)) {
    				result = line;
    			}
    		}
    		scan.close();
    	}
    	catch(IOException ex)
    	{
    		ex.printStackTrace();
    	}
    	return result;
    }
}
