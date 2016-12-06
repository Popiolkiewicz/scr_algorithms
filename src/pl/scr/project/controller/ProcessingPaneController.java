package pl.scr.project.controller;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.Function;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import pl.scr.project.constants.AlgorithmTypeEnum;
import pl.scr.project.constants.Constants;
import pl.scr.project.model.Process;
import pl.scr.project.utils.CustomChart;
import pl.scr.project.utils.EditCell;

public class ProcessingPaneController implements Initializable {

	@FXML
	private TableView<Process> processesTableView;
	@FXML
	private AnchorPane chartPane;

	private ObservableList<Process> dataSource = FXCollections.observableArrayList();

	@FXML
	private void handleAddProcessButtonAction(ActionEvent event) {
		if (dataSource.size() < Constants.MAX_PROCESS_COUNT)
			dataSource.add(new Process());
	}

	@FXML
	public void handleDeleteProcessButtonAction(ActionEvent event) {
		int selectedIndex = processesTableView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0)
			processesTableView.getItems().remove(selectedIndex);
	}

	@FXML
	private void handleRandomButtonAction(ActionEvent event) {
		System.out.println("handleRandomButtonAction");
	}

	@FXML
	private void handleTableViewKeyPressed(KeyEvent event) {
		@SuppressWarnings("unchecked")
		TablePosition<Process, String> pos = processesTableView.getFocusModel().getFocusedCell();
		if (pos != null)
			processesTableView.edit(pos.getRow(), pos.getTableColumn());
	}

	@FXML
	public void handleCalculateButtonAction(ActionEvent event) {
		dataSource.stream().sorted((e1, e2) -> Integer.compare(e1.getPriority(), e2.getPriority())).forEach(process -> {
			System.out.println(process.toString());
			createChart(process);
		});
	}

	public int counter = 0;

	private void createChart(Process process) {
		CustomChart customChart = CustomChart.create();
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
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
		series.getData().add(new XYChart.Data<>(new Random().nextInt(10), 0));
		customChart.getData().retainAll();
		customChart.getData().add(counter, series);
		chartPane.getChildren().add(customChart);
		counter++;
	}

	/*
	 * Extra GUI initialization
	 */
	@FXML
	private ComboBox<String> selectAlgorithmComboBox;
	@FXML
	private TableColumn<Process, Number> indexColumn;
	@FXML
	private TableColumn<Process, String> arrivalTimeColumn;
	@FXML
	private TableColumn<Process, String> processingTimeColumn;
	@FXML
	private TableColumn<Process, String> periodColumn;
	@FXML
	private TableColumn<Process, String> deadlineColumn;
	@FXML
	private TableColumn<Process, String> priorityColumn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		selectAlgorithmComboBox.getItems().addAll(AlgorithmTypeEnum.getDescsAsList());
		processesTableView.setItems(dataSource);
		indexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Number>(
				processesTableView.getItems().indexOf(column.getValue()) + 1));
		initializeColumn(arrivalTimeColumn, Process::arrivalTimeProperty);
		initializeColumn(processingTimeColumn, Process::processingTimeProperty);
		initializeColumn(periodColumn, Process::periodProperty);
		initializeColumn(deadlineColumn, Process::deadlineProperty);
		initializeColumn(priorityColumn, Process::priorityProperty);
	}

	private void initializeColumn(TableColumn<Process, String> column, Function<Process, StringProperty> property) {
		column.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
		column.setCellFactory(column1 -> new EditCell(property));
	}

}
