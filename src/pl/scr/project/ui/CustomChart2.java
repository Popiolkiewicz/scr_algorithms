package pl.scr.project.ui;

import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;

public class CustomChart2 extends AreaChart<Number, Number> {

	private NumberAxis xAxis;
	private NumberAxis yAxis;

	public CustomChart2() {
		super(new NumberAxis(0, 2500, 5), new NumberAxis(0, 1.5, 1.5));
		this.xAxis = (NumberAxis) getXAxis();
		this.yAxis = (NumberAxis) getYAxis();
		configure();
		configureXAxis();
		configureYAxis();
	}

	private void configure() {
		setAnimated(false);
		setCreateSymbols(false);
		setMinWidth(2500 * 20);
		computeMaxHeight(60);
		setMaxHeight(60);
		maxHeight(60);
		setMinHeight(60);
		setHorizontalGridLinesVisible(false);
		setVerticalGridLinesVisible(false);
		setLegendVisible(false);
		setVerticalZeroLineVisible(false);
		setHorizontalZeroLineVisible(false);
	}

	private void configureXAxis() {
		xAxis.setAutoRanging(false);
	}

	private void configureYAxis() {
		yAxis.setPrefSize(0, 0);
		yAxis.setAutoRanging(false);
		yAxis.setTickMarkVisible(false);
		yAxis.setTickLabelsVisible(false);
		yAxis.setMinorTickVisible(false);
	}
}
