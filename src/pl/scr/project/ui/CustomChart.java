package pl.scr.project.ui;

import java.util.Map.Entry;

import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import pl.scr.project.model.Cache;
import pl.scr.project.model.Process;
import pl.scr.project.utils.ColorUtil;

public class CustomChart extends AreaChart<Number, Number> {

	private NumberAxis xAxis;
	private NumberAxis yAxis;
	private int ratio = 20;
	private long chartEndLine;

	public CustomChart() {
		super(new NumberAxis(0, Cache.get().getHiperperiod() < 1000 ? Cache.get().getHiperperiod() : 1000, 5),
				new NumberAxis(0, 1.5, 5));
		this.xAxis = (NumberAxis) getXAxis();
		this.yAxis = (NumberAxis) getYAxis();
		this.chartEndLine = Cache.get().getHiperperiod() < 1000 ? Cache.get().getHiperperiod() : 1000;
		configure();
		configureXAxis();
		configureYAxis();
	}

	private void configure() {
		setAnimated(false);
		setCreateSymbols(false);
		setMinWidth(chartEndLine * ratio);
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
		xAxis.setAutoRanging(false);
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
			if (timeUnit > chartEndLine)
				break;
		}
		getData().add(series);
		changeChartStyle(series);

		AreaChart.Series<Number, Number> periodSeries = createPeriodSeries(process);
		getData().add(periodSeries);
		changeChartStyle(periodSeries, "00,00,00");

		AreaChart.Series<Number, Number> deadlineOkSeries = createDeadlineOkSeries(process);
		getData().add(deadlineOkSeries);
		changeChartStyle(deadlineOkSeries, "58,191,71");

		AreaChart.Series<Number, Number> deadlineExceededSeries = createDeadlineExceededSeries(process);
		getData().add(deadlineExceededSeries);
		changeChartStyle(deadlineExceededSeries, "255,0,0");
	}

	private XYChart.Series<Number, Number> createPeriodSeries(Process process) {
		AreaChart.Series<Number, Number> series = new AreaChart.Series<>();
		for (int i = process.getArrivalTime(); i <= chartEndLine; i += process.getPeriod()) {
			series.getData().add(new XYChart.Data<>(i, -0.2));
			series.getData().add(new XYChart.Data<>(i, 1.5));
			series.getData().add(new XYChart.Data<>(i + 0.01, 1.5));
			series.getData().add(new XYChart.Data<>(i + 0.01, -0.2));
		}
		return series;
	}

	private AreaChart.Series<Number, Number> createDeadlineOkSeries(Process process) {
		AreaChart.Series<Number, Number> series = new AreaChart.Series<>();
		for (Integer i : process.getDeadlineOkTimeUnits()) {
			series.getData().add(new XYChart.Data<>(i, -0.2));
			series.getData().add(new XYChart.Data<>(i, 1.2));
			series.getData().add(new XYChart.Data<>(i + 0.01, 1.2));
			series.getData().add(new XYChart.Data<>(i + 0.01, -0.2));
			if (i > chartEndLine)
				break;
		}
		return series;
	}

	private AreaChart.Series<Number, Number> createDeadlineExceededSeries(Process process) {
		AreaChart.Series<Number, Number> series = new AreaChart.Series<>();
		for (Integer i : process.getDeadlineExceededTimeUnits()) {
			series.getData().add(new XYChart.Data<>(i, -0.2));
			series.getData().add(new XYChart.Data<>(i, 1.2));
			series.getData().add(new XYChart.Data<>(i + 0.01, 1.2));
			series.getData().add(new XYChart.Data<>(i + 0.01, -0.2));
			if (i > chartEndLine)
				break;
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
}
