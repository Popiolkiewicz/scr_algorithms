package pl.scr.project.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Process {

	private StringProperty arrivalTimeSP = new SimpleStringProperty("0");
	private StringProperty processingTimeSP = new SimpleStringProperty("0");
	private StringProperty periodSP = new SimpleStringProperty("0");
	private StringProperty deadlineSP = new SimpleStringProperty("0");
	private StringProperty prioritySP = new SimpleStringProperty("0");

	public Process() {
	}

	public Process(int arrivalTime, int processingTime, int period, int deadline, int priority) {
		setArrivalTime(arrivalTime);
		setProcessingTime(processingTime);
		setPeriod(period);
		setDeadline(deadline);
		setPriority(priority);
	}

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
				+ periodSP + ", deadlineSP=" + deadlineSP + ", prioritySP=" + prioritySP + "]";
	}

}
