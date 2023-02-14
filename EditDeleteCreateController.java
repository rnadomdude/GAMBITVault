package application;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditDeleteCreateController implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	String darkCSS = this.getClass().getResource("/application/resources/darkTheme.css").toExternalForm();
	String lightCSS = this.getClass().getResource("/application/resources/lightTheme.css").toExternalForm();
	@FXML
	private TextField descriptionText, websiteText, usernameText, passwordText;
	@FXML
	private Text loggedInAs, passwordStrength;
	@FXML
	private ChoiceBox<String> typeChoiceBox;
	
	private int id;
	private String username;
	private String description;
	private String website;
	private String login;
	private String password;
	private String type;
	
	private String[] types = {"login", "cards", "secure notes"};
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		typeChoiceBox.getItems().addAll(types);
		loggedInAs.setText("Logged in as: " + PathUtil.getConfigName());
	}

	public void fillEditValues(int id, String username, String description, String website, String login, String password, String type) throws IOException {
		this.id = id;
		this.username = username;
		this.description = description;
		this.website = website;
		this.login = login;
		this.password = password;
		this.type = type;
		loggedInAs.setText("Logged in as: " + username);
		descriptionText.setText(description);
		websiteText.setText(website);
		usernameText.setText(login);
		passwordText.setText(password);
		typeChoiceBox.setValue(type);
	}
	
	public void checkPassword(KeyEvent e) throws Exception {
		String text;
		text = CheckerUtil.checkPassword(passwordText.getText());
		if (text.equals("Strong"))
			passwordStrength.setStyle("-fx-fill: #04EBAC");
		else if(text.equals("Moderate"))
			passwordStrength.setStyle("-fx-fill: #ff7d41");
		else
			passwordStrength.setStyle("-fx-fill: #ff4242");
		
		passwordStrength.setText("Password strength: " + text);
	}
	
	public void copyLogin(ActionEvent e) throws Exception {
		String text = usernameText.getText();
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
	}
	
	public void copyPassword(ActionEvent e) throws Exception {
		String text = passwordText.getText();
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
	}
	
	public void deleteDetails(ActionEvent e) throws Exception {
		if(AlertUtil.showDeleteDialog()) {
			DatabaseHandler.deletePassword(id, username, description, website, login, AESEncryption.encrypt(password), type);
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
	}
	
	public void saveDetails(ActionEvent e) throws Exception {
		DatabaseHandler.updatePassword(id, PathUtil.getConfigName(), descriptionText.getText(), websiteText.getText(), usernameText.getText(), AESEncryption.encrypt(passwordText.getText()), typeChoiceBox.getValue());
		
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
	
	public void backToMainMenu(ActionEvent e) throws Exception
	{
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
	
	public void goToDataBreach(ActionEvent e) throws Exception
	{
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
