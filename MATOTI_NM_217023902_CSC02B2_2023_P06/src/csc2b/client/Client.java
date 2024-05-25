package csc2b.client;
import csc2b.server.Server;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application
{
    public static void main(String[] args)
    {
    	launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		//create the ClientPane, set up the Scene and Stage
		BUKAClientPane bcp = new BUKAClientPane(primaryStage);
		Scene scene = new Scene(bcp, 800,600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("BUKA FILE SYSTEM");
		primaryStage.show();
	}
}
