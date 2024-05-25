package acsse;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author NM Matoti
 * @version P05
 *
 */

public class Leecher {
	
	/**Attributes*/
	private static DatagramSocket dgSocket = null;
	private static InetAddress address = null;
	private static int serverPort = 0;
	private static byte[] buffer = new byte[2000];
	private static String strMessage = null;
	private static String response = null;
	private static DatagramPacket dgPacket = null;
	private static  ArrayList<String> arrList = null;
	private static File file = null;
	private static boolean bool = false;
	/**Ctor*/
	public Leecher(int serverPort,String strAdress, String str) {
		this.serverPort = serverPort;
		this.strMessage = str;
		this.bool = bool;
		try {
			dgSocket = new DatagramSocket(); //initialise to any available port on leecher sider
		} catch (SocketException e1) {
			System.err.println("Could not instantiate client socket");
		}
		try {
			this.address = InetAddress.getByName(strAdress);
			
		} catch (UnknownHostException e) {
			System.err.println("Could not resolve host address");
		}
	}
	
	private static void sendReceive() {
	
		while(bool) {
			byte[] buffer = strMessage.getBytes();
			dgPacket = new DatagramPacket(buffer, buffer.length, address, serverPort);
				try {
					//Send packet via socket to Seeder
					dgSocket.send(dgPacket);
					//wait for response from Seeder
					if(strMessage.contains("List")) {
					
						ReceiveList();
					}
					else if(strMessage.contains("0")) {
						
						file = new File("data/"+ strMessage);
						FileOutputStream fos = new FileOutputStream(file); //to write the incoming data to disk
						dgSocket.receive(dgPacket);
						fos.write(dgPacket.getData(),0,buffer.length);
						fos.flush();
					}
					else {
						dgSocket.receive(dgPacket);
						response = new String(dgPacket.getData(),0,dgPacket.getLength());
						System.out.println("The Seeder said you said "+ response);
					}
					
			} catch (IOException e) {
				System.err.println("Could not send initial message to seeder");
			}
		}
	}

	
	public void setMessage(String strMessage) {
		this.strMessage = strMessage;
	}
	
	public String getMessage() throws IOException {
		response = new String(dgPacket.getData());
		return response;
	}

	public ArrayList<String> getList(){
		return arrList;
		
	}
	private static void ReceiveList() throws IOException {
		dgSocket.receive(dgPacket);
		String str = new String(dgPacket.getData());
		arrList.add(str);
	}
	
	
	private File getFile(String fileName) throws IOException {
		
		return file;
		}



/*public class Leecher {

	//**Attributes	private DatagramSocket dgSocket = null;
	private InetAddress seederAddress = null;
	private DatagramPacket dgPacket = null;
	private byte[] buffer = null;
	private int port = 0;
	private File file = null;
	private ArrayList<String> arrList= new ArrayList<>();
	
	public Leecher(DatagramSocket dgSocket , InetAddress seederAdress, int port) {
		this.dgSocket = dgSocket;
		this.seederAddress = seederAdress;
		this.port = port;
	}
	
	public void Connect(String str) {
		//Messgase to prompt seeder
		String msgToSend = str;
	
		//Loop for our client to be able to continuously send and receive packets
		while(true) {
			//Message to instantiate path
			//Convert string into byte array so that it can be sent as a packet
			this.buffer = msgToSend.getBytes();//getBytes is a String method that returns a string as an array of bytes
			dgPacket = new DatagramPacket(buffer, buffer.length, seederAddress, port);
			try {
				//Send packet via socket to Seeder
				dgSocket.send(dgPacket);
				//wait for response from Seeder
				
				if(str.contains("List")) {
					ReceiveList();
				}
				else if(str.contains("Request")) {
					file=ReceiveFile(str);
				}
				else {
					dgSocket.receive(dgPacket);
					String msgReceived = new String(dgPacket.getData(),0,dgPacket.getLength());
					System.out.println("The Seeder said you said "+msgReceived);
				}
				
			} catch (IOException e) {
				System.out.println("Leecher could not send packet to Seeder");
				break; // if error occurs, break out of while loop
			}
		}
	}
	
	private void ReceiveList() throws IOException {
		dgSocket.receive(dgPacket);
		String str = new String(dgPacket.getData());
		arrList.add(str);
	}
	
	private File ReceiveFile(String fileName) throws IOException {
		
		File file = new File("data/"+ fileName);
		FileOutputStream fos = new FileOutputStream(file); //to write the incoming data to disk
		fos.write(this.buffer,0,buffer.length);
		fos.flush();
		return file;
		}
	
		
	
	

*/
public static void main(String[] args) {
	Leecher.sendReceive();
	System.out.println("Send datagram packet to server");
	//leecher.Connect();
}
}
