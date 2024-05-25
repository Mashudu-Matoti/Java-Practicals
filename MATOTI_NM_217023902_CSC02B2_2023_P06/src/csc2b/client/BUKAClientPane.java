package csc2b.client;

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
import java.net.UnknownHostException;
import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BUKAClientPane extends GridPane //You may change the JavaFX pane layout
{
	/**Attributes*/
	Socket socket = null;
	OutputStream os = null;
	InputStream is = null;
	PrintWriter pw = null;
	BufferedReader br = null;
	DataInputStream dis = null;
	Boolean check = false;
	Button btnDownload = null;
	Button btnSendList = null;
	Stage stage = null;
	
	/**Ctor*/
    public BUKAClientPane(Stage stage)
    {
    	//textfields  and controls gui
    	TextField tName = new TextField("name");
    	TextField tPassword = new TextField("password");
    	TextField id = new TextField("File to download");
    	Button btnConnect = new Button("Connect");
    	Button btnLog = new Button("LogOut");
    	 this.btnDownload = new Button("Download Pdf");
		 this.btnSendList = new Button("Send List");
    	this.add(tName, 1, 2);
    	this.add(tPassword, 2, 2);
    	this.add(btnConnect, 4, 2);
    	this.setHgap(10);
    	this.setVgap(10);
    	this.setAlignment(Pos.CENTER);
    	this.stage = stage;
    	//Authenticates user then connects
    	btnConnect.setOnAction(e->{
    		try {	
    				//instantiates connection
    				this.socket = new Socket("localhost",2018);

    				this.os= socket.getOutputStream();
    				is = socket.getInputStream();
    				pw = new PrintWriter(os,true);
    				br = new BufferedReader(new InputStreamReader(is));
    				dis = new DataInputStream(is);
    				
    				pw.println("AUTH "+tName.getText()+" "+ tPassword.getText());
    				String strLine = br.readLine();
    				System.out.print(strLine);
    				
    				//If authentication field fails prompt user to try again
    				if(strLine.contains("Successful")) {
    					this.getChildren().clear();
    			    	this.add(btnDownload, 2,2);
    			    	this.add(btnSendList, 3,2);
    			    	this.add(id, 2, 3);
    			    	this.add(btnLog, 4, 3);
    				}
    				else {
    					System.out.println("Not Connected");
    					tName.appendText("Invalid UserName");
    					tPassword.appendText("Invalid username or password");
    				}
				
				
			} catch (UnknownHostException e1) {
				System.out.println("Host not found/inavlid port");
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	});
	//Create buttons for each command
    	//event handler for sending list
		   
		this.btnSendList.setOnAction(e2->{
    		TextArea listView = new TextArea();
	  		
	  		pw.println("LIST");
			try {
				
				String str = br.readLine();
				String[] list = str.split("#");//reads inn list as one line then splits it into filenames
	
				for(String s: list) {
					System.out.println(s);
					listView.appendText(s+" \n");
				}
				this.add(listView, 3,3);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
	  		
	  	});
	  	//event handler for download button
	  	this.btnDownload.setOnAction(e2->{
	  		try {
	  			pw.println("PDFRET "+id.getText());
				String file= br.readLine();
				int fileSize= Integer.parseInt(br.readLine());

				File fileToRet = new File("data/client/"+file);
				FileOutputStream fos = new FileOutputStream(fileToRet);//reads in file as bytes from sserver
				byte[] buffer = new byte[1500];
				int n=0;
				int totalbytes = 0;
				while(totalbytes != fileSize) {
					n=dis.read(buffer,0, buffer.length);
					fos.write(buffer,0,n);
					fos.flush();
					totalbytes +=n;
				}
				System.out.println("File saved on client side");
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	  		
	  		
	  	});
    	/**
    	 * event handler fir logging out
    	 */
	  	btnLog.setOnAction(e3->{
	  		try {
	  			this.stage.close();
	  			pw.close();
	  			br.close();
	  			dis.close();
	  			this.socket.close();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	  		
	  	});
    	
    }
}
