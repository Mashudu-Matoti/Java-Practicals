import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import acsse.Leecher;
import acsse.Seeder;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 
 * @author NM Matoti
 * @version P05
 *
 */
public class Client extends Application{
	
	/**Attributes*/
	
	private TextArea txtArea;
	private TextField txtField;
	private GridPane grid;
	private Button sendBtn1;
	private Button sendBtn2;
	
	private ArrayList<byte[]> arrFile= null;
	String[] arrFiles = null;
	DatagramSocket dgSocket = null;
	Seeder seeder = null;
	DatagramSocket socket = null;
	InetAddress inetAdress = null;
	Leecher leecher = null;
	Stage s = null;
	
	
	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("Peer2Peer File Sharing");
		grid= new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		txtArea = new TextArea();
		txtField = new TextField();
		sendBtn1 = new Button("Seeder Mode");
		sendBtn2 = new Button("Leecher Mode");
		grid.add(sendBtn1, 2, 2);
		grid.add(sendBtn2, 2, 4);
		/*grid.add(txtField, 0, 0);
		grid.add(txtArea, 1, 1);*/
		Scene scene = new Scene(grid,800,600);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		sendBtn1.setOnAction(e->{
			SeederMode(primaryStage);
		});
		sendBtn2.setOnAction(e->{
			LeechererMode(primaryStage);
		});		
	}
	

	
	
	//Creating Scene for seeder mode
	private void SeederMode(Stage primaryStage) {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		Button bt1 = new Button("Add to list");
		grid.add(bt1, 2, 2);
		bt1.setOnAction(e->{
			
		});
		//Change scene to seeder mode
		Scene seederScene = new Scene(grid,800,600);
		primaryStage.setTitle("Seeder Mode");
		primaryStage.setScene(seederScene);
	}
	
	//Creating Scene for Leecher mode
		private void LeechererMode(Stage primaryStage) {
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			TextField txtHost = new TextField("enter host name");
			txtHost.setOpacity(2.00);
			TextField txtPort = new TextField("enter port number");
			txtPort.setOpacity(2.00);
			grid.add(txtHost, 2, 2);
			grid.add(txtPort, 4, 2);
			Button bt1 = new Button("Connect to Seeder");
			grid.add(bt1, 4, 4);
			bt1.setOnAction(e->{
				TextField t1 = new TextField();
				t1.cancelEdit();
				//take in user input and address and typecast port nr
				int port = Integer.parseInt(txtPort.getText());
				//Instantiate UDP classes
				Seeder seeder = new Seeder(port, txtHost.getText() );
				Leecher leecher = new Leecher(port, txtHost.getText(), "Hello");
		
				//leecher.setMessage("HELLO");
				try {
					t1.setText(leecher.getMessage());
				} catch (IOException e1) {
					System.err.println("Could not display text");
				}
				Button btList = new Button("List");
                Button btDownload = new Button("Request file");
				TextField fileId = new TextField("Please enter file number");
				fileId.setOpacity(2.00);
				//remove connect button from scene and other textfields upon established connections
				grid.getChildren().clear();
				grid.add(btList, 4, 2);
				grid.add(btDownload, 4, 4);
				btList.setOnAction(a->{
					leecher.setMessage("list");
				});
                btDownload.setOnAction(b->{
                	String str = fileId.getText();
                	if(!fileId.getText().contains(" ")) {
                		leecher.setMessage("str");
                		
                	}else {
                		fileId.setText("Enter valid Id");
                	}
				});
				
			});//Change scene to leecher mode
			Scene leecherScene = new Scene(grid,800,600);
			primaryStage.setTitle("Leecher Mode");
			primaryStage.setScene(leecherScene);
		}
	
	
	
}
