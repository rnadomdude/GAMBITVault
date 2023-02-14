package application;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.awt.datatransfer.Clipboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoggedInMenuButtonController implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	String darkCSS = this.getClass().getResource("/application/resources/darkTheme.css").toExternalForm();
	String lightCSS = this.getClass().getResource("/application/resources/lightTheme.css").toExternalForm();

	@FXML
	private TableView<DescView> TV = new TableView<>();
	@FXML
	private TableColumn<DescView, String> descriptionColumn;
	@FXML
	private TableColumn<DescView, String> websiteColumn;
	@FXML
	private TableColumn<DescView, String> loginColumn;
	@FXML
	private TableColumn<DescView, String> passwordColumn;
	@FXML
	private TableColumn<DescView, String> typeColumn;
	@FXML
	private TextField searchField;
	@FXML
	private Text loggedInAs;
	@FXML
	private Button buttonAll, buttonLogin, buttonCard, buttonSecureNote;

	private ObservableList<DescView> data = FXCollections.observableArrayList();
	
	public void initialize(URL url, ResourceBundle rb) {
		ResultSet rs = DatabaseHandler.selectPasswordsSet(PathUtil.getConfigName());
		try {
			while (rs.next()) {
				data.add(new DescView(
						rs.getInt("id"),
						rs.getString("desc"),
						rs.getString("website"),
						rs.getString("login"),
						rs.getString("encryptedPassword"),
						rs.getString("type")
						));
			}
		} catch (SQLException e) {
			AlertUtil.showExceptionStackTraceDialog(e);
		}
		
		TableColumn<DescView, String> id1 = new TableColumn<>();
		
		id1.setCellValueFactory(new PropertyValueFactory<>("id"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        websiteColumn.setCellValueFactory(new PropertyValueFactory<>("website"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("encryptedPassword"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        
        TV.setItems(data);
        
        FilteredList<DescView> filteredData = new FilteredList<>(data, b -> true);
        
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
        	filteredData.setPredicate(DescView -> {
        		
        		if(newValue.isEmpty() || newValue.isBlank() || newValue==null) {
        			return true;
        		}
        		
        		String searchTerm = newValue.toLowerCase();
        		
        		if(DescView.getDescription().toLowerCase().indexOf(searchTerm)> -1) {
        			return true;
        		} else if (DescView.getWebsite().toLowerCase().indexOf(searchTerm)> -1) {
        			return true;
        		} else if (DescView.getLogin().toLowerCase().indexOf(searchTerm)> -1) {
        			return true;
        		} else if (DescView.getType().toLowerCase().indexOf(searchTerm)> -1) {
            		return true;
        		} else {
        			return false;
        		}
        	});
        });
        
        buttonAll.setOnAction(event -> {
            filteredData.setPredicate(DescView -> {
                String filter = "login";
                String filter1 = "card";
                String filter2 = "secure note";
                filter = filter.toLowerCase();
                filter1 = filter1.toLowerCase();
                filter2 = filter2.toLowerCase();
                return DescView.getType().toLowerCase().contains(filter) ||
                		DescView.getType().toLowerCase().contains(filter1) ||
                		 DescView.getType().toLowerCase().contains(filter2);
            });
        });
        
        buttonLogin.setOnAction(event -> {
            filteredData.setPredicate(DescView -> {
                String filter = "login";
                filter = filter.toLowerCase();
                return DescView.getType().toLowerCase().contains(filter);
            });
        });
        
        buttonCard.setOnAction(event -> {
            filteredData.setPredicate(DescView -> {
                String filter = "card";
                filter = filter.toLowerCase();
                return DescView.getType().toLowerCase().contains(filter);
            });
        });
        
        buttonSecureNote.setOnAction(event -> {
            filteredData.setPredicate(DescView -> {
                String filter = "secure note";
                filter = filter.toLowerCase();
                return DescView.getType().toLowerCase().contains(filter);
            });
        });
        SortedList<DescView> sortedData = new SortedList<>(filteredData);
        //Bind sorted filteredData to the TV 
        sortedData.comparatorProperty().bind(TV.comparatorProperty());
        // Apply changes
        TV.setItems(sortedData);
        loggedInAs.setText("Logged in as: " + PathUtil.getConfigName());
	}
	
	public void clickItem(MouseEvent event)
	{
		try {
		    if (event.getClickCount() == 2) //Checking double click
		    {
		        StringSelection selection = new StringSelection(TV.getSelectionModel().getSelectedItem().getDecryptedPassword());
		        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		        clipboard.setContents(selection, null);
		    } else {
			        FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/resources/mainMenuViewEdit.fxml"));
			        root = loader.load();
			        // Go to edit screen
			        EditDeleteCreateController controller = loader.getController();
				        controller.fillEditValues( 
				        	TV.getSelectionModel().getSelectedItem().getId(), // db row id
				        	PathUtil.getConfigName(), //db username
				        	TV.getSelectionModel().getSelectedItem().getDescription(), 
				        	TV.getSelectionModel().getSelectedItem().getWebsite(), 
				        	TV.getSelectionModel().getSelectedItem().getLogin(), 
				        	TV.getSelectionModel().getSelectedItem().getDecryptedPassword(),
				        	TV.getSelectionModel().getSelectedItem().getType());
				    stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
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
		} catch (Exception e) {
			//AlertUtil.showExceptionStackTraceDialog(e);
		}
	}
	
	public void addItem(ActionEvent e) throws Exception
	{
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
	
	public static class DescView {
		private SimpleStringProperty description;
	    private SimpleStringProperty website;
	    private SimpleStringProperty login;
	    private SimpleStringProperty encryptedPassword;
	    private SimpleStringProperty type;
	    private String decryptedPassword;
	    private SimpleIntegerProperty id;
	 
	    //private final SimpleStringProperty activePwd;
	    
	    public DescView(int id, String description, String website, String login, String encryptedPassword, String type){
	    	this.id = new SimpleIntegerProperty(id);
	        this.description = new SimpleStringProperty(description);
	        this.website = new SimpleStringProperty(website);
	        this.login = new SimpleStringProperty(login);
	        this.encryptedPassword = new SimpleStringProperty(encryptedPassword);
	        this.type = new SimpleStringProperty(type);
	    }
	    /**
		 * @return the id of the database row
		 */
	    public int getId() {
			return id.get();
		}
	    
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description.get();
		}

		/**
		 * @return the website
		 */
		public String getWebsite() {
			return website.get();
		}

		/**
		 * @return the login
		 */
		public String getLogin() {
			return login.get();
		}
	    
		/**
		 * @return the password
		 */
		public String getPassword() {
			return encryptedPassword.get();
		}
		
		public String getDecryptedPassword() {
			try {
				decryptedPassword = AESEncryption.decrypt(encryptedPassword.get());
			} catch (Exception e) {
				AlertUtil.showExceptionStackTraceDialog(e);
			}
			return decryptedPassword;
		}
		
		/**
		 * @return the type 
		 */
	    public String getType() {
			return type.get();
		}
		
		public SimpleStringProperty idProperty() {
            return encryptedPassword;
        }
		
		public SimpleStringProperty descriptionProperty() {
            return description;
        }
	    
		public SimpleStringProperty websiteProperty() {
            return website;
		}
		
		public SimpleStringProperty loginProperty() {
            return login;
        }
		
		public SimpleStringProperty encryptedPasswordProperty() {
            return encryptedPassword;
        }
		
		public SimpleStringProperty typeProperty() {
            return type;
        }
	}

}
