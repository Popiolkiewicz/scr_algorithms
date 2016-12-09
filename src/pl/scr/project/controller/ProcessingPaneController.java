package pl.scr.project.controller;

import java.net.URL;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import pl.scr.project.constants.AlgorithmTypeEnum;
import pl.scr.project.constants.Constants;
import pl.scr.project.logic.PTCalculator;
import pl.scr.project.logic.ProcessRandomizer;
import pl.scr.project.model.ChartDisplayData;
import pl.scr.project.model.ChartElement;
import pl.scr.project.model.Process;
import pl.scr.project.ui.CustomChart;
import pl.scr.project.ui.EditCell;

public class ProcessingPaneController implements Initializable {

	@FXML
	private TableView<Process> processesTableView;

	@FXML
	private VBox chartBox;

	private ObservableList<Process> dataSource = FXCollections.observableArrayList();

	@FXML
	private void handleRandomButtonAction(ActionEvent event) {
		dataSource.clear();
		dataSource.addAll(ProcessRandomizer.generateRandomData());
		processesTableView.refresh();
	}

	@FXML
	private void handleAddProcessButtonAction(ActionEvent event) {
		if (dataSource.size() < Constants.MAX_PROCESS_COUNT)
			dataSource.add(new Process());
	}

	@FXML
	public void handleDeleteProcessButtonAction(ActionEvent event) {
		int selectedIndex = processesTableView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			processesTableView.getItems().remove(selectedIndex);
			processesTableView.refresh();
		}
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
		PTCalculator ptc = new PTCalculator(dataSource);
		System.out.println(dataSource);
		ChartDisplayData cdd = ptc.fakeCalculate();
		System.out.println(cdd);
		chartBox.getChildren().clear();
		for (Entry<Integer, List<ChartElement>> entry : cdd.getSeries().entrySet()) 
			createChart(entry.getValue(), cdd.getHiperperiod());
		
	}

	private void createChart(List<ChartElement> list, int hiperperiod) {
		CustomChart customChart = new CustomChart(hiperperiod);
		chartBox.getChildren().add(customChart);
		customChart.createData(list);
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
