package application;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class AlertUtil {


    /**
     * Show an error dialog with a title, header, and body
     * 
     * @param errTitle The title of the error dialog.
     * @param errHeader The header text of the error dialog.
     * @param errBody The body of the error message.
     */
    public static void showErrorDialog(String errTitle, String errHeader, String errBody){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(errTitle);
        alert.setHeaderText(errHeader);
        alert.setContentText(errBody);
        alert.getButtonTypes().clear();
        alert.setGraphic(new ImageView(LoginController.class.getResource("/application/resources/icons/gambitvaultblack.png").toString()));
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(LoginController.class.getResourceAsStream("/application/resources/icons/90.png")));
        alert.getDialogPane().getStylesheets().add(LoginController.class.getResource("/application/resources/darkTheme.css").toExternalForm());
        ButtonType btnConfirm = new ButtonType("OK");
        alert.getDialogPane().getButtonTypes().add(btnConfirm);

        Node confirm = alert.getDialogPane().lookupButton(btnConfirm);
        confirm.getStyleClass().add("btn");//NON-NLS
        alert.showAndWait();
    }


    /**
     * It shows a dialog with the stacktrace of the exception.
     * 
     * @param e The exception that was thrown.
     */
    public static void showExceptionStackTraceDialog(Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Critical error");
        alert.setContentText("Delete the database file in the appdata folder.");
        alert.getButtonTypes().clear();
        alert.setGraphic(new ImageView(LoginController.class.getResource("/application/resources/icons/gambitvaultblack.png").toString()));
        alert.getDialogPane().getStylesheets().add(LoginController.class.getResource("/application/resources/darkTheme.css").toExternalForm());

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getDialogPane().getButtonTypes().add(okButtonType);
        Node okBtn = alert.getDialogPane().lookupButton(okButtonType);
        okBtn.getStyleClass().add("btn");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(LoginController.class.getResourceAsStream("/application/resources/icons/90.png")));

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("exception.stacktrace");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    public static boolean showDeleteDialog(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete?");
        alert.setHeaderText("Delete?");
        alert.setContentText("Are you sure you want to delete it?");
        alert.getButtonTypes().clear();
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(LoginController.class.getResourceAsStream("/application/resources/icons/90.png")));
        alert.getDialogPane().getStylesheets().add(LoginController.class.getResource("/application/resources/darkTheme.css").toExternalForm());
        ButtonType btnYes = new ButtonType("Yes");
        ButtonType btnNo = new ButtonType("No");
        
        alert.getDialogPane().getButtonTypes().add(btnYes);
        alert.getDialogPane().getButtonTypes().add(btnNo);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == btnYes){
            //user chose yes
        	return true;
        } else {
            //user chose no or closed dialog
        	return false;
        }
    }
    
}