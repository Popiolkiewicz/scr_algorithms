package pl.scr.project.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import pl.scr.project.model.Cache;

public class SettingsWindowController implements Initializable {

	@FXML
	TextField processesRangeFrom;
	@FXML
	TextField processesRangeTo;
	@FXML
	TextField processorUsageFrom;
	@FXML
	TextField processorUsageTo;
	@FXML
	CheckBox deadlineEqualsPeriod;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		deadlineEqualsPeriod.selectedProperty().bindBidirectional(Cache.get().deadlineEqualsPeriod());
		Bindings.bindBidirectional(processesRangeFrom.textProperty(), Cache.get().processesRangeFrom(),
				new NumberStringConverter());
		Bindings.bindBidirectional(processesRangeTo.textProperty(), Cache.get().processesRangeTo(),
				new NumberStringConverter());
		Bindings.bindBidirectional(processorUsageFrom.textProperty(), Cache.get().processorUsageFrom(),
				new NumberStringConverter());
		Bindings.bindBidirectional(processorUsageTo.textProperty(), Cache.get().processorUsageTo(),
				new NumberStringConverter());
		processesRangeFrom.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (!newValue && !processesRangeFrom.getText().matches("[1-9]|10"))
				processesRangeFrom.setText("");
		});
		processesRangeTo.focusedProperty().addListener((arg0, oldValue, newValue) -> {
			if (!newValue && !processesRangeTo.getText().matches("[1-9]|10"))
				processesRangeTo.setText("");
		});
	}

}
