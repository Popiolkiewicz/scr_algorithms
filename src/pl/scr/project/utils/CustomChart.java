package pl.scr.project.utils;

import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class CustomChart extends AreaChart<Number, Number> {

	private NumberAxis xAxis;
	private NumberAxis yAxis;

	public synchronized static CustomChart create() {
		NumberAxis xAxis = new NumberAxis(0, 100, 1);
		NumberAxis yAxis = new NumberAxis(0, 1.5, 1);
		return new CustomChart(xAxis, yAxis);
	}

	private CustomChart(NumberAxis xAxis, NumberAxis yAxis) {
		super(xAxis, yAxis);
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		configure();
		configureXAxis();
		configureYAxis();
	}

	private void configure() {
		setCreateSymbols(false);
		setMinWidth(6000);
		setHeight(50);
		computeMaxHeight(40);
		setMaxHeight(40);
	}

	private void configureXAxis() {
		xAxis.setMinorTickVisible(false);
	}

	private void configureYAxis() {
		yAxis.setPrefHeight(50);
		yAxis.setTickLabelsVisible(false);
		yAxis.setMinorTickVisible(false);
	}

	public void createData() {
		AreaChart.Series<Number, Number> series = new AreaChart.Series<>();
		series.getData().add(new XYChart.Data<>(0, 0));
		series.getData().add(new XYChart.Data<>(0, 1));
		series.getData().add(new XYChart.Data<>(1, 1));
		series.getData().add(new XYChart.Data<>(1, 0));
		series.getData().add(new XYChart.Data<>(2, 0));
		series.getData().add(new XYChart.Data<>(3, 0));
		series.getData().add(new XYChart.Data<>(4, 0));
		series.getData().add(new XYChart.Data<>(4, 1));
		series.getData().add(new XYChart.Data<>(6, 1));
		series.getData().add(new XYChart.Data<>(6, 0));
		series.getData().add(new XYChart.Data<>(8, 0));

		getData().add(series);
		Node fill = series.getNode().lookup(".chart-series-area-fill"); 
		Node line = series.getNode().lookup(".chart-series-area-line");
		String randomColor = ColorUtil.getRandomColor();
		fill.setStyle("-fx-fill: rgba(" + randomColor + ", 0.40);");
		line.setStyle("-fx-stroke: rgba(" + randomColor + ", 1.0);");
	}
}
