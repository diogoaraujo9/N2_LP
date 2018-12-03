

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(Start.class, args);
	}
	
	static Stage stageLoader;
	@Override
	public void start(Stage stage) 
	{
		Start.stageLoader = stage;
		//AnchorPane root;
		try 
		{
			Parent root = FXMLLoader.load(getClass().getResource("views/TelaLogin.fxml"));
	        stage.setTitle("Menu");
	        
			Scene scene = new Scene(root);
			scene.getStylesheets().add("style/StyleSheet.css");
			
			stage.setScene(scene);
			stage.show();
		
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
