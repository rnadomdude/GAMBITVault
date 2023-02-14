package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.RadioButton;

public class SettingsController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	String darkCSS = this.getClass().getResource("/application/resources/darkTheme.css").toExternalForm();
	String lightCSS = this.getClass().getResource("/application/resources/lightTheme.css").toExternalForm();
	
	@FXML
	private RadioButton lightButton, darkButton;
	
	public void logout(ActionEvent e) throws Exception {
		root = FXMLLoader.load(getClass().getResource("/application/resources/loginView.fxml"));
		stage =(Stage) ((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		if (PathUtil.getConfigTheme().equals("dark")) {
			scene.getStylesheets().add(darkCSS);
		} else {
			scene.getStylesheets().add(lightCSS);
		}
		stage.setScene(scene);
		stage.show();
	}
	
	public void deleteData(ActionEvent e) throws Exception {
		if(AlertUtil.showDeleteDialog())
			DatabaseHandler.truncateData(PathUtil.getConfigName());
	}
	
	public void deleteAccount(ActionEvent e) throws Exception {
		if(AlertUtil.showDeleteDialog()) {
			DatabaseHandler.truncateUsers(PathUtil.getConfigName());
			root = FXMLLoader.load(getClass().getResource("/application/resources/loginView.fxml"));
			stage =(Stage) ((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			if (PathUtil.getConfigTheme().equals("dark")) {
				scene.getStylesheets().add(darkCSS);
			} else {
				scene.getStylesheets().add(lightCSS);
			}
			stage.setScene(scene);
			stage.show();
		}
	}
	
	public void backClicked(ActionEvent e) throws Exception {
		root = FXMLLoader.load(getClass().getResource("/application/resources/mainMenuView.fxml"));
		stage =(Stage) ((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		if (PathUtil.getConfigTheme().equals("dark")) {
			scene.getStylesheets().add(darkCSS);
		} else {
			scene.getStylesheets().add(lightCSS);
		}
		stage.setScene(scene);
		stage.show();
	}
	
	public void changeWhiteTheme(ActionEvent e) throws Exception {
		root = FXMLLoader.load(getClass().getResource("/application/resources/settingsView.fxml"));
		stage =(Stage) ((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(lightCSS);
		stage.setScene(scene);
		stage.show();
		lightButton.setSelected(true);
		darkButton.setSelected(false);
		PathUtil.changeTheme("light");
	}
	
	public void changeDarkTheme(ActionEvent e) throws Exception {
		root = FXMLLoader.load(getClass().getResource("/application/resources/settingsView.fxml"));
		stage =(Stage) ((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(darkCSS);
		stage.setScene(scene);
		stage.show();
		lightButton.setSelected(false);
		darkButton.setSelected(true);
		PathUtil.changeTheme("dark");
	}
	
}
