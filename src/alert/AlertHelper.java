package alert;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertHelper {
	public static void showWarningMessage(String title, String headerContent, String contentText) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(headerContent);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
	
	public static void showErrorMessage(String title, String headerContent, String contentText) {
		System.out.println("OI" + title);
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerContent);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
}
