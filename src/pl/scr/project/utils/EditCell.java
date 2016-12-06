package pl.scr.project.utils;

import java.util.function.Function;

import javafx.beans.property.StringProperty;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import pl.scr.project.model.Process;

public class EditCell extends TableCell<Process, String> {

	private final TextField textField = new TextField();

	private final Function<Process, StringProperty> property;

	public EditCell(Function<Process, StringProperty> property) {
		this.property = property;

		textProperty().bind(itemProperty());
		setGraphic(textField);
		setContentDisplay(ContentDisplay.TEXT_ONLY);

		textField.setOnAction(evt -> {
			commitEdit(textField.getText());
		});
		textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
			if (!isNowFocused)
				commitEdit(textField.getText());
		});
		textField.setOnKeyPressed((KeyEvent event) -> {
			if (event.getCode() == KeyCode.TAB) {
				commitEdit(textField.getText());
				getTableView().getSelectionModel().selectRightCell();
			}
		});

	}

	@Override
	public void startEdit() {
		super.startEdit();
		textField.setText(getItem() != null ? getItem().toString() : "");
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		textField.requestFocus();
	}

	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setContentDisplay(ContentDisplay.TEXT_ONLY);
	}

	@Override
	public void commitEdit(String text) {
		super.commitEdit(text);
		Process process = getTableView().getItems().get(getIndex());
		StringProperty cellProperty = property.apply(process);
		cellProperty.set(text);
		setContentDisplay(ContentDisplay.TEXT_ONLY);
	}

	@Override
	public void updateItem(String item, boolean empty) {
		if (item == null)
			return;
		try {
			Integer.parseInt(item);
		} catch (NumberFormatException e) {
			return;
		}
		super.updateItem(item, empty);
	}

}