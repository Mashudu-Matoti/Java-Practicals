package csc2b.server;
import java.io.BufferedReader;
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
import java.util.Scanner;
import java.util.StringTokenizer;

public class ZEDEMHandler implements Runnable {
	/**Attributes*/
	Socket socket ;
	BufferedReader br = null;
	PrintWriter pw = null;
	DataOutputStream dos = null;
	InputStream is = null;
	OutputStream os = null;
	
	public ZEDEMHandler(Socket connection) throws IOException {
		socket = connection;
		is = socket.getInputStream();
		os = socket.getOutputStream();
		br = new BufferedReader(new InputStreamReader(is));
		pw = new PrintWriter(os, true);
		dos = new DataOutputStream(os);
	}
	
	@Override
	public void run() {
	try {
		while(true) {
		String line = br.readLine();
		System.out.println(line);
		
		//String command = st.nextToken();
		if(line!= null) {
			StringTokenizer st = new StringTokenizer(line);
			switch(st.nextToken()) {
			case "BONJOUR":{
				if(matchUser(st.nextToken(),st.nextToken())) {
					pw.println("Ja, login successful");
				}
				else {
					pw.println("Nee, login unsuccessful");
				}
				break;
			}
			case "PLAYLIST":{
				String strLine = " ";
				for(String s: getFileList()) {
					strLine += s+"#";
				}
				
				pw.println(strLine);
				break;
			}
			case "ZEDEMGET":{
				String ID = null;
				String fileName = null;
				String fileSize = null;
				StringTokenizer st2 = new StringTokenizer(idToFileName(st.nextToken()));
				ID = st2.nextToken();
				fileName = st2.nextToken();
				File file = new  File("data/server/"+fileName);
				FileInputStream fis = new FileInputStream(file);
				byte[] buffer = new byte[10000];
				int n =0;
				pw.println(fileName.length());
				pw.println(fileName);
				while((n=fis.read(buffer))>0) {
					dos.write(buffer, 0,n);
					dos.flush();
				}
				break;
			}
			case "ZEDEMBYE":{
				br.close();
				pw.close();
				dos.close();
				os.close();
				is.close();
				break;
			}
			}
		}
		}
	} catch (IOException e) {
		System.out.println("Could not read in message from client");
	}
	}
	
	private boolean matchUser(String userN, String passW)
	{
		boolean found = false;
		
		//Code to search users.txt file for match with userN and passW.
		File userFile = new File("data/server/users.txt"/*OMITTED - Enter file location*/);
		try
		{
		    Scanner scan = new Scanner(userFile);
		    while(scan.hasNextLine()&&!found)
		    {
			String line = scan.nextLine();
			String lineSec[] = line.split("\\s");
			System.out.println(lineSec[0]);
			System.out.println(lineSec[1]);
			//***OMITTED - Enter code here to compare user*** 
			if(lineSec[0].equals(userN) && lineSec[1].equals(passW)) {
				found = true;
				
			}
			
		    }
		    scan.close();
		}
		catch(IOException ex)
		{
		    ex.printStackTrace();
		}
		
		return found;
	}
	
	//Returns array of file names and IDs
	private ArrayList<String> getFileList()
	{
		ArrayList<String> result = new ArrayList<String>();
		
		//Code to add list text file contents to the arraylist.
		File lstFile = new File("data/server/List.txt"/*OMITTED - Enter file location*/);
		if(lstFile.exists()) {
		
		try
		{
			Scanner scan = new Scanner(lstFile);

			//***OMITTED - Read each line of the file and add to the arraylist***
			String strLine = null;
			
			while(scan.hasNext()) {
				//read file name and add to array
				strLine = scan.nextLine();
				
				result.add(strLine);
				
			}
			scan.close();
		}	    
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		}
		else {
			System.out.println("File not Found");
		}
		
		return result;
	}
	
	private String idToFileName(String strID)
	{
		String result ="";
		
		//Code to find the file name that matches strID
		File lstFile = new File("data/server/List.txt" /*OMITTED - Enter file location*/);
    	try
    	{
    		Scanner scan = new Scanner(lstFile);

    		String line = "";
    		//***OMITTED - Read filename from file and search for filename based on ID***
    		while(scan.hasNext()) {
    			line = scan.nextLine();
    			if(line.contains(strID)) {
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
