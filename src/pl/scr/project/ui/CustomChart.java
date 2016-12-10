package pl.scr.project.ui;

import java.util.Map.Entry;

import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import pl.scr.project.model.Process;
import pl.scr.project.utils.ColorUtil;

public class CustomChart extends AreaChart<Number, Number> {

	private NumberAxis xAxis;
	private NumberAxis yAxis;
	private int ratio = 20;
	private int hiperperiod;

	public CustomChart(int hiperperiod) {
		super(new NumberAxis(0, hiperperiod, 1), new NumberAxis(0, 1.5, 5));
		this.xAxis = (NumberAxis) getXAxis();
		this.yAxis = (NumberAxis) getYAxis();
		this.hiperperiod = hiperperiod;
		configure();
		configureXAxis();
		configureYAxis();
	}

	private void configure() {
		setAnimated(false);
		setCreateSymbols(false);
		setMinWidth(hiperperiod * ratio);
		computeMaxHeight(40);
		setMaxHeight(40);
		maxHeight(60);
		setMinHeight(60);
		setHorizontalGridLinesVisible(false);
		setVerticalGridLinesVisible(false);
		setLegendVisible(false);
		setVerticalZeroLineVisible(false);
		setHorizontalZeroLineVisible(false);
	}

	private void configureXAxis() {
		xAxis.setMinorTickVisible(false);
		xAxis.setAutoRanging(false);
		xAxis.setMinorTickVisible(false);
	}

	private void configureYAxis() {
		yAxis.setPrefSize(0, 0);
		yAxis.setAutoRanging(false);
		yAxis.setTickMarkVisible(false);
		yAxis.setTickLabelsVisible(false);
		yAxis.setMinorTickVisible(false);
	}

	public void createData(Process process) {
		AreaChart.Series<Number, Number> series = new AreaChart.Series<>();
		for (Entry<Integer, Boolean> entry : process.getDisplayData().entrySet()) {
			double heightUnit = entry.getValue() ? 1.0 : 0.2;
			Integer timeUnit = entry.getKey();
			series.getData().add(new XYChart.Data<>(timeUnit, -0.2));
			series.getData().add(new XYChart.Data<>(timeUnit, heightUnit));
			series.getData().add(new XYChart.Data<>(timeUnit + 1, heightUnit));
			series.getData().add(new XYChart.Data<>(timeUnit + 1, -0.2));
		}
		getData().add(series);
		changeChartStyle(series);

		AreaChart.Series<Number, Number> periodSeries = createPeriodChart(process.getPeriod());
		getData().add(periodSeries);
		changeChartStyle(periodSeries, "00,00,00");

		AreaChart.Series<Number, Number> deadlineSeries = createDeadlineChart(process);
		getData().add(deadlineSeries);
		changeChartStyle(deadlineSeries, "58,191,71");
	}

	private AreaChart.Series<Number, Number> createDeadlineChart(Process process) {
		AreaChart.Series<Number, Number> series = new AreaChart.Series<>();
		for (int i = process.getDeadline(); i < hiperperiod; i += process.getPeriod()) {
			series.getData().add(new XYChart.Data<>(i, -0.2));
			series.getData().add(new XYChart.Data<>(i, 1.2));
			series.getData().add(new XYChart.Data<>(i + 0.01, 1.2));
			series.getData().add(new XYChart.Data<>(i + 0.01, -0.2));
		}
		return series;
	}

	private void changeChartStyle(AreaChart.Series<Number, Number> series) {
		changeChartStyle(series, ColorUtil.getRandomColor());
	}

	private void changeChartStyle(AreaChart.Series<Number, Number> series, String colorRGB) {
		Node fill = series.getNode().lookup(".chart-series-area-fill");
		Node line = series.getNode().lookup(".chart-series-area-line");
		fill.setStyle("-fx-fill: rgba(" + colorRGB + ", 0.40);");
		line.setStyle("-fx-stroke: rgba(" + colorRGB + ", 1.0);");
	}

	private XYChart.Series<Number, Number> createPeriodChart(Integer period) {
		AreaChart.Series<Number, Number> series = new AreaChart.Series<>();
		for (int i = 0; i < hiperperiod; i += period) {
			series.getData().add(new XYChart.Data<>(i, -0.2));
			series.getData().add(new XYChart.Data<>(i, 1.5));
			series.getData().add(new XYChart.Data<>(i + 0.01, 1.5));
			series.getData().add(new XYChart.Data<>(i + 0.01, -0.2));
		}
		return series;
	}
}
