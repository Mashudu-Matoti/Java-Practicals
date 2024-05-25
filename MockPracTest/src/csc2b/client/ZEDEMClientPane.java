package csc2b.client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ZEDEMClientPane extends GridPane //You may change the JavaFX pane layout
{
	/**Attributes*/
	Socket socket = null;
	InputStream is = null;
	OutputStream os = null;
	DataInputStream dis =null;
	BufferedReader br = null;
	PrintWriter pw = null;
	
	public ZEDEMClientPane() {
		
		Button btnLogIn = new Button("Login In");
		TextField tUser = new TextField("UserName");
		TextField tPass = new TextField("Password");
		Button btnList = new Button("PlayList");
		Button btnDownload = new Button("Download");
		TextField tID = new TextField("File ID");
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(10);
		this.add(btnLogIn, 1, 3);
		this.add(tUser, 1, 1);
		this.add(tPass, 1, 2);
		
		btnLogIn.setOnAction(e->{
			try {
				 socket = new Socket("localhost",2021);
				 is = socket.getInputStream();
				 os = socket.getOutputStream();
				 br = new BufferedReader(new InputStreamReader(is));
				 pw = new PrintWriter(os, true);
				 dis = new DataInputStream(is);
				 
				pw.println("BONJOUR "+tUser.getText()+" "+tPass.getText());
				String strLine = br.readLine();
				System.out.println(strLine);
				if(strLine.contains("Ja")) {
					
					this.getChildren().clear();
					this.add(btnDownload, 1, 1);
					this.add(btnList, 2, 1);
					this.add(tID, 1, 2);
				}
				else {
					tUser.setText("Invalid username/password");
					tPass.setText("Invalid username/password");
				}
				
			} catch (IOException e1) {
				System.out.println("Could not instantiate client socket");
				e1.printStackTrace();
			
			}
		});
		btnList.setOnAction(e1->{
			pw.println("PLAYLIST");
			TextArea listArea = new TextArea();
			this.add(listArea, 2, 1);
			try {
				
				
				String line = br.readLine();
				
				String[] arrList = line.split("#");
				for(String s: arrList) {
					System.out.println(s);
					listArea.appendText(s+"\n");
				}
			} catch (IOException e3) {
				System.out.println("Could not read list");
			}
			
			
		});
		
		btnDownload.setOnAction(e2->{
			pw.println("ZEDEMGET "+tID.getText());
			try {
				String fileSize = br.readLine();
				int size = Integer.parseInt(fileSize);
				String filename = br.readLine();
				File file =new File("data/client/"+filename);
				FileOutputStream fos = new FileOutputStream(file);
				byte[] buffer = new byte[10000];
				int n=0;
				int totalBytes = 0;
				
				while(totalBytes!= size) {
					n=dis.read(buffer,0,buffer.length);
					fos.write(buffer,0,n);
					fos.flush();
					totalBytes =+n;
				}
				System.out.print("File sent!");
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			
			
		});
		
		
	}
	
	
	

}
