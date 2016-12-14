package pl.scr.project.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Process {

	private int id;
	private int unitsToProcess;
	private Map<Integer, Boolean> displayData = new LinkedHashMap<>();
	private List<Integer> deadlineExceededTimeUnits = new ArrayList<>();
	private List<Integer> deadlineOkTimeUnits = new ArrayList<>();

	public Process() {
		this.id = new Random().nextInt(Integer.MAX_VALUE);
	}

	public Integer getId() {
		return id;
	}

	public int getUnitsToProcess() {
		return unitsToProcess;
	}

	public void incUnitsToProcess() {
		this.unitsToProcess += getProcessingTime();
	}

	public void clearUnitsToProcess() {
		this.unitsToProcess = 0;
	}

	public void decUnitsToProcess() {
		this.unitsToProcess -= 1;
	}

	/**
	 * @return true if all unit has been processed
	 */
	public boolean isDone() {
		return unitsToProcess == 0;
	}

	/**
	 * @return true if current has been processed
	 */
	public boolean isOneProcessed() {
		return unitsToProcess % getProcessingTime() == 0;
	}

	public Map<Integer, Boolean> getDisplayData() {
		return displayData;
	}

	public List<Integer> getDeadlineExceededTimeUnits() {
		return deadlineExceededTimeUnits;
	}

	public void clearDeadlineExceededTimeUnits() {
		this.deadlineExceededTimeUnits.clear();
	}

	public List<Integer> getDeadlineOkTimeUnits() {
		return deadlineOkTimeUnits;
	}

	public void clearDeadlineOkTimeUnits() {
		this.deadlineOkTimeUnits.clear();
	}

	public void clearDisplatData() {
		displayData.clear();
	}

	public void setDisplayData(Map<Integer, Boolean> displayData) {
		this.displayData = displayData;
	}

	private StringProperty arrivalTimeSP = new SimpleStringProperty("0");
	private StringProperty processingTimeSP = new SimpleStringProperty("0");
	private StringProperty periodSP = new SimpleStringProperty("0");
	private StringProperty deadlineSP = new SimpleStringProperty("0");
	private StringProperty prioritySP = new SimpleStringProperty("0");

	public final StringProperty arrivalTimeProperty() {
		return this.arrivalTimeSP;
	}

	public final StringProperty processingTimeProperty() {
		return this.processingTimeSP;
	}

	public final StringProperty periodProperty() {
		return this.periodSP;
	}

	public final StringProperty deadlineProperty() {
		return this.deadlineSP;
	}

	public final StringProperty priorityProperty() {
		return this.prioritySP;
	}

	public Integer getArrivalTime() {
		return Integer.parseInt(arrivalTimeSP.get());
	}

	public void setArrivalTime(Integer arrivalTime) {
		this.arrivalTimeSP.set(String.valueOf(arrivalTime));
	}

	public Integer getProcessingTime() {
		return Integer.parseInt(processingTimeSP.get());
	}

	public void setProcessingTime(Integer processingTime) {
		this.processingTimeSP.set(String.valueOf(processingTime));
	}

	public Integer getPeriod() {
		return Integer.parseInt(periodSP.get());
	}

	public void setPeriod(Integer period) {
		this.periodSP.set(String.valueOf(period));
	}

	public Integer getDeadline() {
		return Integer.parseInt(deadlineSP.get());
	}

	public void setDeadline(Integer deadline) {
		this.deadlineSP.set(String.valueOf(deadline));
	}

	public Integer getPriority() {
		return Integer.parseInt(prioritySP.get());
	}

	public void setPriority(Integer priority) {
		this.prioritySP.set(String.valueOf(priority));
	}

	@Override
	public String toString() {
		return "Process [arrivalTimeSP=" + arrivalTimeSP + ", processingTimeSP=" + processingTimeSP + ", periodSP="
				+ periodSP + ", deadlineSP=" + deadlineSP + ", prioritySP=" + prioritySP + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Process other = (Process) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
