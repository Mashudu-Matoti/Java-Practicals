package csc2b.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	/**Attributes*/
	ServerSocket sSocket = null;
	
	
	public Server(int port) {
		try {
			this.sSocket = new ServerSocket(port);
			startServer();
		} catch (IOException e) {
			System.out.println("Could not instantiate server socket");
		}
		
		
	}
	
	private void startServer() {
		
		while(true) {
			Socket socket;
			try {
				socket = sSocket.accept();
				ZEDEMHandler zhandler = new ZEDEMHandler(socket);
				Thread thread = new Thread(zhandler);
				thread.start();
			} catch (IOException e) {
				System.out.println("Could not connect client socket to sever");
			}
			
		}
	}
	
	public static void main(String[] argv) {
		Server server = new Server(2021);
		
	}
}
