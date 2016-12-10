package pl.scr.project.ui;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import pl.scr.project.model.ChartElement;
import pl.scr.project.utils.ColorUtil;

public class CustomChart extends AreaChart<Number, Number> {

	private NumberAxis xAxis;
	private NumberAxis yAxis;
	private int ratio = 40;
	private int hiperperiod;

	public CustomChart(int hiperperiod) {
		super(new NumberAxis(0, hiperperiod, 1), new NumberAxis(0, 1.5, 1));
		this.xAxis = (NumberAxis) getXAxis();
		this.yAxis = (NumberAxis) getYAxis();
		this.hiperperiod = hiperperiod;
		configure();
		configureXAxis();
		configureYAxis();
	}

	private void configure() {
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

	private void changeChartStyle(AreaChart.Series<Number, Number> series) {
		Node fill = series.getNode().lookup(".chart-series-area-fill");
		Node line = series.getNode().lookup(".chart-series-area-line");
		String randomColor = ColorUtil.getRandomColor();
		fill.setStyle("-fx-fill: rgba(" + randomColor + ", 0.40);");
		line.setStyle("-fx-stroke: rgba(" + randomColor + ", 1.0);");
	}
	
	private XYChart.Series<Number, Number> createPeriodChart() {
		AreaChart.Series<Number, Number> series = new AreaChart.Series<>();
		for (int i = 0; i < hiperperiod; i += hiperperiod * 0.1) {
			series.getData().add(new XYChart.Data<>(i, -0.2));
			series.getData().add(new XYChart.Data<>(i, 1.5));
			series.getData().add(new XYChart.Data<>(i, -0.2));
		}
		return series;
	}

	public void createData(Entry<Integer, Map<Integer, Boolean>> processEntry) {
		AreaChart.Series<Number, Number> series = new AreaChart.Series<>();
		for (Entry<Integer, Boolean> entry : processEntry.getValue().entrySet()) {
			double heightUnit = entry.getValue() ? 1.0 : 0.2;
			Integer timeUnit = entry.getKey();
			series.getData().add(new XYChart.Data<>(timeUnit, -0.2));
			series.getData().add(new XYChart.Data<>(timeUnit, heightUnit));
			series.getData().add(new XYChart.Data<>(timeUnit + 1, heightUnit));
			series.getData().add(new XYChart.Data<>(timeUnit + 1, -0.2));
		}
		// getData().add(createPeriodChart());
		getData().add(series);
		changeChartStyle(series);
	}
}
