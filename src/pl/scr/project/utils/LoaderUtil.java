package pl.scr.project.utils;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoaderUtil {

	private static final Stage LOADING_STAGE = new Stage();

	static {
		Label loader = new Label();
		loader.setGraphic(new ImageView(new Image("/pl/scr/project/views/loading.gif")));
		LOADING_STAGE.setScene(new Scene(loader, 60, 60));
		LOADING_STAGE.setResizable(false);
		LOADING_STAGE.initStyle(StageStyle.UNDECORATED);
	}

	public static void show() {
		LOADING_STAGE.show();
	}

	public static void hide() {
		LOADING_STAGE.hide();
	}
}
