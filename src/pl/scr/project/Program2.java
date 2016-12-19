package pl.scr.project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import pl.scr.project.ui.CustomChart2;

public class Program2 extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			// ScrollPane scrollPane = new ScrollPane();
			ListView<CustomChart2> listView = new ListView<>();
			// VBox vBox = new VBox();
			// listView.getItems().add(vBox);
			Scene scene = new Scene(listView, 600, 600);
			primaryStage.setMaximized(true);
			primaryStage.setScene(scene);
			primaryStage.show();
			for (int i = 0; i < 15; i++) {
				CustomChart2 customChart2 = new CustomChart2();
				AreaChart.Series<Number, Number> series = new AreaChart.Series<>();
				for (int j = 0; j < 2500; j += i + 2) {
					series.getData().add(new XYChart.Data<>(j, -0.2));
					series.getData().add(new XYChart.Data<>(j, 1));
					series.getData().add(new XYChart.Data<>(j + 1, 1));
					series.getData().add(new XYChart.Data<>(j + 1, -0.2));
				}
				customChart2.getData().add(series);
				AreaChart.Series<Number, Number> series2 = new AreaChart.Series<>();
				for (int j = 0; j < 2500; j += i + 1) {
					series2.getData().add(new XYChart.Data<>(j, -0.2));
					series2.getData().add(new XYChart.Data<>(j, 1.5));
					series2.getData().add(new XYChart.Data<>(j + 0.01, 1.5));
					series2.getData().add(new XYChart.Data<>(j + 0.01, -0.2));
				}
				customChart2.getData().add(series2);
				AreaChart.Series<Number, Number> series3 = new AreaChart.Series<>();
				for (int j = 2; j < 2500; j += i + 1) {
					series3.getData().add(new XYChart.Data<>(j, -0.2));
					series3.getData().add(new XYChart.Data<>(j, 1.2));
					series3.getData().add(new XYChart.Data<>(j + 0.01, 1.2));
					series3.getData().add(new XYChart.Data<>(j + 0.01, -0.2));
				}
				customChart2.getData().add(series3);
				listView.getItems().add(customChart2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
