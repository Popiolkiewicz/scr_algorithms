package pl.scr.project.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.NumberStringConverter;
import pl.scr.project.constants.Constants;
import pl.scr.project.logic.Calculator;
import pl.scr.project.logic.EDFCalculator;
import pl.scr.project.logic.PTCalculator;
import pl.scr.project.logic.ProcessRandomizer;
import pl.scr.project.logic.RMSCalculator;
import pl.scr.project.model.Cache;
import pl.scr.project.model.Process;
import pl.scr.project.ui.CustomAlert;
import pl.scr.project.ui.CustomChart;
import pl.scr.project.ui.EditCell;
import pl.scr.project.utils.LoaderUtil;

public class ProcessingPaneController implements Initializable {

	@FXML
	private TableView<Process> processesTableView;

	@FXML
	private VBox chartBox;

	private ObservableList<Process> dataSource = FXCollections.observableArrayList();

	/*
	 * Action handlers
	 */
	@FXML
	private void handleRandomButtonAction(ActionEvent event) {
		dataSource.clear();
		dataSource.addAll(new ProcessRandomizer().generateRandomData());
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
	public void handleSettingsButtonAction(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("..\\views\\SettingsWindowPane.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Ustawienia zaawansowane");
			stage.setScene(new Scene(root, 300, 170));
			stage.setResizable(false);
			stage.initStyle(StageStyle.UTILITY);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void handleCalculateButtonAction(ActionEvent event) {
		if (!validateData())
			return;
		chartBox.getChildren().clear();
		LoaderUtil.show();
		Calculator calc = null;
		switch (selectAlgorithmComboBox.getValue()) {
		case Constants.PT:
			calc = new PTCalculator(dataSource);
			break;
		case Constants.RMS:
			calc = new RMSCalculator(dataSource);
			break;
		case Constants.EDF:
			calc = new EDFCalculator(dataSource);
			break;
		default:
			break;
		}
		final Calculator finalCalc = calc;
		Task<Void> calculation = new Task<Void>() {
			@Override
			public Void call() {
				finalCalc.calculate();
				return null;
			}
		};
		calculation.setOnSucceeded(e -> {
			Cache.get().hiperperiod().set(finalCalc.getHiperperiod());
			Cache.get().processorUsage().set(finalCalc.getCpuUsage());
			// List<ImageView> ims = new ArrayList<>();
			for (Process process : dataSource) {
				CustomChart customChart = new CustomChart();
				chartBox.getChildren().add(customChart);
				customChart.createData(process);
				// SnapshotParameters sp = new SnapshotParameters();
				// WritableImage snapshot = customChart.snapshot(sp,
				// (WritableImage) null);
				// chartBox.getChildren().add(new ImageView(snapshot));
				// ims.add(new ImageView(snapshot));
				// chartBox.getChildren().add(new ImageView(snapshot));
			}
			// chartBox.getChildren().add(new ImageView(snapshot));
			// chartBox.getChildren().clear();
			// chartBox.getChildren().addAll(ims);
			LoaderUtil.hide();
		});
		new Thread(calculation).start();
	}

	/*
	 * Validation
	 */
	private boolean validateData() {
		String algorithmSelection = selectAlgorithmComboBox.getValue();
		if (algorithmSelection == null) {
			new CustomAlert("Nie wybrano algorytmu!").show();
			return false;
		}
		return validateProcesses();
	}

	private boolean validateProcesses() {
		for (Process process : dataSource) {
			if (process.getDeadline() > process.getPeriod() || process.getProcessingTime() > process.getDeadline()) {
				new CustomAlert("Wprowadzono niepoprawne dane procesów!").show();
				return false;
			}
		}
		return true;
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
	@FXML
	private CheckBox interruptCheckbox;
	@FXML
	private Label hiperperiodLabel;
	@FXML
	private Label processorUsageLabel;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeComboBox();
		Cache.get().interruptAfterDeadline().bind(interruptCheckbox.selectedProperty());
		processesTableView.setItems(dataSource);
		indexColumn.setCellValueFactory(column -> new ReadOnlyObjectWrapper<Number>(
				processesTableView.getItems().indexOf(column.getValue()) + 1));
		initializeColumn(arrivalTimeColumn, Process::arrivalTimeProperty);
		initializeColumn(processingTimeColumn, Process::processingTimeProperty);
		initializeColumn(periodColumn, Process::periodProperty);
		initializeColumn(deadlineColumn, Process::deadlineProperty);
		initializeColumn(priorityColumn, Process::priorityProperty);
		bindLabels();
	}

	private void initializeComboBox() {
		selectAlgorithmComboBox.getItems().add(Constants.PT);
		selectAlgorithmComboBox.getItems().add(Constants.RMS);
		selectAlgorithmComboBox.getItems().add(Constants.EDF);
	}

	private void initializeColumn(TableColumn<Process, String> column, Function<Process, StringProperty> property) {
		column.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
		column.setCellFactory(column1 -> new EditCell(property));
	}

	private void bindLabels() {
		Bindings.bindBidirectional(hiperperiodLabel.textProperty(), Cache.get().hiperperiod(),
				new NumberStringConverter() {

					@Override
					public String toString(Number value) {
						String result = super.toString(value);
						if (value.intValue() >= Integer.MAX_VALUE)
							return "Hiperokres: Ponad " + result;
						else
							return "Hiperokres: " + result;
					}

				});
		Bindings.bindBidirectional(processorUsageLabel.textProperty(), Cache.get().processorUsage(),
				new NumberStringConverter() {

					@Override
					public String toString(Number value) {
						String result = super.toString(value);
						return "Zu¿ycie procesora: " + result;
					}

				});

	}

}
