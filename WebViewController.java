package application;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class WebViewController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	String darkCSS = this.getClass().getResource("/application/resources/darkTheme.css").toExternalForm();
	String lightCSS = this.getClass().getResource("/application/resources/lightTheme.css").toExternalForm();
	
	@FXML
	private Text loggedInAs;
	@FXML
	private WebView webView;

	public void initialize() {
		loggedInAs.setText("Logged in as: " + PathUtil.getConfigName());
		webView.getEngine().load("https://haveibeenpwned.com/");
	}
	
	public void settingsClicked(ActionEvent e) throws Exception {
		root = FXMLLoader.load(getClass().getResource("/application/resources/settingsView.fxml"));
		stage =(Stage) ((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		if(PathUtil.getConfigTheme().equals("dark")) {
			scene.getStylesheets().add(darkCSS);
		}
		else {
			scene.getStylesheets().add(lightCSS);
		}
		stage.setScene(scene);
		stage.show();
	}
	
	public void backToMainMenu(ActionEvent e) throws Exception {
		root = FXMLLoader.load(getClass().getResource("/application/resources/mainMenuView.fxml"));
		stage =(Stage) ((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		if(PathUtil.getConfigTheme().equals("dark")) {
			scene.getStylesheets().add(darkCSS);
		}
		else {
			scene.getStylesheets().add(lightCSS);
		}
		stage.setScene(scene);
		stage.show();
	}
	
	public void goToEditCreate(ActionEvent e) throws Exception {
		root = FXMLLoader.load(getClass().getResource("/application/resources/mainMenuViewEdit.fxml"));
		stage =(Stage) ((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		if(PathUtil.getConfigTheme().equals("dark")) {
			scene.getStylesheets().add(darkCSS);
		}
		else {
			scene.getStylesheets().add(lightCSS);
		}
		stage.setScene(scene);
		stage.show();
	}
	
	public void goToPasswordGen(ActionEvent e) throws Exception
	{
		root = FXMLLoader.load(getClass().getResource("/application/resources/mainMenuPassGen.fxml"));
		stage =(Stage) ((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		if(PathUtil.getConfigTheme().equals("dark")) {
			scene.getStylesheets().add(darkCSS);
		}
		else {
			scene.getStylesheets().add(lightCSS);
		}
		stage.setScene(scene);
		stage.show();
	}
	
	public void goToDataBreach(ActionEvent e) throws Exception {
		root = FXMLLoader.load(getClass().getResource("/application/resources/mainMenuWebView.fxml"));
		stage =(Stage) ((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		if(PathUtil.getConfigTheme().equals("dark")) {
			scene.getStylesheets().add(darkCSS);
		}
		else {
			scene.getStylesheets().add(lightCSS);
		}
		stage.setScene(scene);
		stage.show();
	}
	
}

