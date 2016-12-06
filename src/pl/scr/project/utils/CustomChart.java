package pl.scr.project.utils;

import java.util.Random;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

public class CustomChart extends LineChart<Number, Number> {

	private NumberAxis xAxis;
	private NumberAxis yAxis;

	public synchronized static CustomChart create() {
		NumberAxis xAxis = new NumberAxis(0, 1000, 1);
		NumberAxis yAxis = new NumberAxis(0, 1.25, 1);
		return new CustomChart(xAxis, yAxis);
	}

	private CustomChart(NumberAxis xAxis, NumberAxis yAxis) {
		super(xAxis, xAxis);
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		configureXAxis();
		configureYAxis();
		configure();
	}

	private void configure() {
		setTitle("Wykres Gaant'a");
		setMaxHeight(50);
		setMinWidth(new Random().nextInt(7000));
	}

	private void configureXAxis() {
		xAxis.setMinorTickVisible(false);
	}

	private void configureYAxis() {
		yAxis.setTickLabelsVisible(false);
		yAxis.setTickLength(10);
		yAxis.setMinorTickVisible(false);
	}
}
