package csc2b.client;

import csc2b.server.ZEDEMHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		ZEDEMClientPane zpane = new ZEDEMClientPane();
		Scene scene = new Scene(zpane, 800,600);
		primaryStage.setTitle("ZEDEM Client");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

}
