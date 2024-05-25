package csc2b.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server
{
	/**Attributes*/
	private static ServerSocket sSocket;
	private static Boolean running = false;
	
	/**Ctor*/
	public Server(int port) {
		try {
			// instantiate server
			sSocket = new ServerSocket(port);
			//for server to run in a 'forever' loop
			running =true;
			
		} catch (IOException e) {
			System.out.println("Could not connect server to port");
		}
		running = true;
		
	}
	/**
	 * Function defining the instantiation of the server
	 */
	private void startServer() {
		while(running) {
    		try {
    			//Create thread for every new connection to server
    			System.out.println("server waiting for connection");
				Thread thread = new Thread(new BUKAHandler(sSocket.accept()));
				thread.start();
				
			} catch (IOException e) {
				System.out.println("Server could not accept a connection");
				
			}
    	}
	}
	/**
	 *  Calls server to run 
	 * @param argv
	 */
    public static void main(String[] argv)
    {
    	Server server = new Server(2018);
    	server.startServer();
    	
    	
    }
}

