package pl.scr.project.ui;

import javafx.scene.control.Alert;

public class CustomAlert extends Alert {

	public CustomAlert(String contentText) {
		super(AlertType.WARNING);
		setResizable(false);
		setContentText(contentText);
		show();
	}

}
