package acsse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * @author NM Matoti
 * @version P05
 * 
 * class that acts as a server to the client
 *
 */

public class Seeder{
	
	/**Attributes*/
	
	private static byte[] buffer = new byte[1000]; //stores incoming message
	private static DatagramSocket dgSocket = null;
	static int port = 0;
	static InetAddress address = null;
	
	public Seeder(int port, String strAddress) {
		this.port = port;
		try {
			this.dgSocket = new DatagramSocket();
			this.address = InetAddress.getByName(strAddress);
		} catch (SocketException e) {
			System.out.println("Could not instantiate socket on seeder");
		} catch (UnknownHostException e) {
			System.out.println("Adress not found");
		}
	}
	
	private static  void receiveSend() {
		System.out.println("Server running on specified port");
		while(true) {
			DatagramPacket dgPacket = new DatagramPacket(buffer, buffer.length);
			try {
				dgSocket.receive(dgPacket);
				int leechPort = dgPacket.getPort();
				String str = new String(dgPacket.getData(),0,dgPacket.getLength());
				if(str.contains("list")) {
					//SendList();
				}
				else if(str.contains("file")) {
					//SendFile(stage);
				}
				else {
					str = "Connected";
					buffer = str.getBytes();
					try {
						dgPacket = new DatagramPacket(buffer, buffer.length,address,leechPort);
						dgSocket.send(dgPacket);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
				}
			} catch (IOException e) {
				System.out.println("Could not receive Packet from Leecher");
			}
		}
	}
	public static void main(String[] args) {
		Seeder.receiveSend();
	}
}
/*public class Seeder {

	/**Attributes
	private DatagramSocket dgSocket;
	InetAddress leecherAddress = null;
	int port = 0;
	DatagramPacket dgPacket = null;
	private byte[] buffer = null;
	String[] arrFiles = null;
	Stage stage = null;
	
	public Seeder(DatagramSocket dgSocket, String[] arrFiles, Stage stage) {
		this.dgSocket = dgSocket;
		this.arrFiles = arrFiles;
		this.stage = stage;
	}
	
	public void ReceiveSend() {
		//wait for prompt from leechers
		while(true) {
			buffer = new byte[2000];
			dgPacket = new DatagramPacket(buffer,buffer.length);
			try {
				dgSocket.receive(dgPacket);
				this.leecherAddress =dgPacket.getAddress(); //returns IP address from which packet was sent. Will be used to send response
				port = dgPacket.getPort(); //returns the port number in which the communication will take place
				String strMsg = new String(dgPacket.getData(),0,dgPacket.getLength()); // reads in data from leechers as string
				if(strMsg.contains("list")) {
					SendList();
				}
				else if(strMsg.contains("file")) {
					SendFile(stage);
				}
				else {
					String str = new String("Connected");
					buffer = str.getBytes();
					dgPacket = new DatagramPacket(buffer, buffer.length,leecherAddress,port);
					dgSocket.send(dgPacket);	
				}
				//dgSocket.receive(dgPacket);
			} catch (IOException e) {
				System.out.println("Sever socket could not read in datagram packet");
				break;
			}
		}
	}
	
	private void SendList() throws IOException {
		for(String str : this.arrFiles) {
			buffer = str.getBytes();
			dgPacket = new DatagramPacket(buffer, buffer.length,leecherAddress,port);
			dgSocket.send(dgPacket);
			
		}
		
	}
	
	private void SendFile(Stage stage) {
		FileChooser fc = new FileChooser();
		File file = fc.showOpenDialog(stage);
		if(file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				buffer = fis.readAllBytes();
				fis.close();
				dgPacket = new DatagramPacket(buffer, buffer.length,leecherAddress,port);
				dgSocket.send(dgPacket);
				System.out.println("File uploaded succesfully");
			
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


//Main method
	public static void main(String[] args) {
		try {
			Client c = new Client();
			DatagramSocket dgSocket = new DatagramSocket(1234);
			Seeder seeder = new Seeder(dgSocket,c.arrFiles, c.s);
			seeder.ReceiveSend();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}*/
