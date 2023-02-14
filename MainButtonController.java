package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainButtonController {
	private Stage stage;
	private Scene scene;
	private Parent root;
	String darkCSS = this.getClass().getResource("/application/resources/darkTheme.css").toExternalForm();
	String lightCSS = this.getClass().getResource("/application/resources/lightTheme.css").toExternalForm();
	boolean login = false;
	@FXML
	private TextField usernameInput, masterKeyInput, repeatMasterKeyInput, loginUsername, loginMasterkey;
	@FXML
	private CheckBox rememberUsernameBox;
	@FXML
	private Text wrongPassword, wrongMasterKey, passwordStrength;
	
	public void createAccountClicked(ActionEvent e) throws Exception {
		 try {
			 String username = usernameInput.getText();
			 String masterkey = masterKeyInput.getText();
			 String repeatedMasterkey = repeatMasterKeyInput.getText();
			 String encryptedMasterkey;
			 System.out.println(masterkey);
			 if(!masterkey.equals("")) {
				 if (masterkey.equals(repeatedMasterkey)) {
					 encryptedMasterkey = AESEncryption.encrypt(repeatedMasterkey);
					 DatabaseHandler.createDatabase();
					 DatabaseHandler.createMainTable();
					 DatabaseHandler.insertPassword(username, encryptedMasterkey);
					 if(!PathUtil.checkIfConfigExists()) {
						 PathUtil.createConfFiles(username + ":" + "dark");
					 } 
					 root = FXMLLoader.load(getClass().getResource("/application/resources/loginView.fxml"));
					 stage =(Stage) ((Node)e.getSource()).getScene().getWindow();
					 scene = new Scene(root);
					 scene.getStylesheets().add(darkCSS);
					 stage.setScene(scene);
					 stage.show();
				 } else {
					 wrongMasterKey.setText("Master keys do not match.");
					 usernameInput.setStyle("-fx-border-color: #ff4242");
					 masterKeyInput.setStyle("-fx-border-color: #ff4242");
					 repeatMasterKeyInput.setStyle("-fx-border-color: #ff4242");
					 
				 }
			 } else {
				 wrongMasterKey.setText("Fill in the fields.");
				 usernameInput.setStyle("-fx-border-color: #ff4242");
				 masterKeyInput.setStyle("-fx-border-color: #ff4242");
				 repeatMasterKeyInput.setStyle("-fx-border-color: #ff4242");
			 }
         } catch (Exception e1){
             AlertUtil.showExceptionStackTraceDialog(e1);
         }
	}
	
	public void checkPassword(KeyEvent e) throws Exception {
		String text;
		text = CheckerUtil.checkPassword(masterKeyInput.getText());
		if (text.equals("Strong"))
			passwordStrength.setStyle("-fx-fill: #04EBAC");
		else if(text.equals("Moderate"))
			passwordStrength.setStyle("-fx-fill: #ff7d41");
		else
			passwordStrength.setStyle("-fx-fill: #ff4242");
		
		passwordStrength.setText("Password strength: " + text);
	}
	
	public void goToCreateAccount(ActionEvent e) throws Exception {
		root = FXMLLoader.load(getClass().getResource("/application/resources/signinView.fxml"));
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
	
	public void alreadyHaveAccountClicked(ActionEvent e) throws Exception {
		root = FXMLLoader.load(getClass().getResource("/application/resources/loginView.fxml"));
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
	
	public void sendToMainMenu(ActionEvent e) throws Exception {
		try {
			String username = loginUsername.getText();
			String masterkey = loginMasterkey.getText();
			String encryptedMasterkey;
			String dbEncryptedMasterkey;
			String theme;
			encryptedMasterkey = AESEncryption.encrypt(masterkey);
			dbEncryptedMasterkey = DatabaseHandler.getUser(username, encryptedMasterkey);
			if(!masterkey.equals("")){
				if (encryptedMasterkey.equals(dbEncryptedMasterkey)) {
					theme = PathUtil.getConfigTheme();
					PathUtil.deleteConfFiles();
					PathUtil.createConfFiles(username + ":" + theme);
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
				} else {
					wrongPassword.setText("Wrong password or username.");
					loginUsername.setStyle("-fx-border-color: #ff4242");
					loginMasterkey.setStyle("-fx-border-color: #ff4242");
				}
			} else {
				wrongPassword.setText("Empty username or masterkey");
				loginUsername.setStyle("-fx-border-color: #ff4242");
				loginMasterkey.setStyle("-fx-border-color: #ff4242");
			}
		} catch (Exception e1) {
			AlertUtil.showExceptionStackTraceDialog(e1);
		}
		
	}

}
