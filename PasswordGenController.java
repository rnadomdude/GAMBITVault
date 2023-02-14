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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PasswordGenController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	String darkCSS = this.getClass().getResource("/application/resources/darkTheme.css").toExternalForm();
	String lightCSS = this.getClass().getResource("/application/resources/lightTheme.css").toExternalForm();
	
	@FXML
	private Text loggedInAs;
	@FXML
	private TextField passLength, passwordField;
	@FXML
	private CheckBox capBox, smallBox, numBox, symBox;
	@FXML
	private Button generateButton, copyButton, backButton;
	
	private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*";

	public void initialize() {
		loggedInAs.setText("Logged in as: " + PathUtil.getConfigName());
		passLength.setText("12");
		
		// This lambda forces users to input numbers only from the textfield
		passLength.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!newValue.matches("\\d*")) {
		    	passLength.setText(newValue.replaceAll("[^\\d]", ""));
		    }
		});
		
		capBox.setSelected(true);
		smallBox.setSelected(true);
		numBox.setSelected(true);
		symBox.setSelected(true);
	}
	
	public void generateClicked(ActionEvent event) {
		String passlength = passLength.getText();
		int length = 12;
		try {
		    length = Integer.parseInt(passlength);
		} catch (NumberFormatException e) {
		    System.out.println("Error: Cannot convert string to int");
		}
		
		StringBuilder charSet = new StringBuilder();
        if (capBox.isSelected()) {
            charSet.append(LOWERCASE_LETTERS);
        }
        if (smallBox.isSelected()) {
            charSet.append(UPPERCASE_LETTERS);
        }
        if (numBox.isSelected()) {
            charSet.append(NUMBERS);
        }
        if (symBox.isSelected()) {
            charSet.append(SPECIAL_CHARACTERS);
        }
		
        if (charSet.length() == 0) {
            passwordField.setText("Error: Please select at least one option");
            return;
        }
        
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i <= length ; i++) {
            int charIndex = random.nextInt(charSet.length());
            password.append(charSet.charAt(charIndex));
        }

		passwordField.setText(password.toString());
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
	
	public void copyPass(ActionEvent e) throws Exception {
		String text = passwordField.getText();
        StringSelection selection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
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
