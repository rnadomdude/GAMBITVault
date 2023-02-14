package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LoginController extends Application {
	
	@Override
	public void start(Stage stage){
		String darkCSS = this.getClass().getResource("/application/resources/darkTheme.css").toExternalForm();
		String lightCSS = this.getClass().getResource("/application/resources/lightTheme.css").toExternalForm();
		try {
			if(PathUtil.checkIfConfigExists()) {
				Parent root = FXMLLoader.load(getClass().getResource("/application/resources/loginView.fxml"));
				Scene scene = new Scene(root, 1920, 1080 );
				/*	 Set theme to white upon login if there is config file*/
				if(PathUtil.getConfigTheme().equals("dark")) {
					scene.getStylesheets().add(darkCSS);
				}
				else {
					scene.getStylesheets().add(lightCSS);
				}
				stage.getIcons().add(new Image(getClass().getResourceAsStream("/application/resources/gambitvaultwhite.png")));
				stage.setTitle("GAMBITVault");
		        stage.setScene(scene);
		        stage.show();
			} else {
				Parent root = FXMLLoader.load(getClass().getResource("/application/resources/mainView.fxml"));
				Scene scene = new Scene(root, 1920, 1080 );
				/*	 Set theme to white upon login if there is config file*/
				if(PathUtil.getConfigTheme().equals("dark")) {
					scene.getStylesheets().add(darkCSS);
				}
				else {
					scene.getStylesheets().add(lightCSS);
				}
				stage.getIcons().add(new Image(getClass().getResourceAsStream("/application/resources/gambitvaultwhite.png")));
				stage.setTitle("GAMBITVault");
		        stage.setScene(scene);
		        stage.show();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
