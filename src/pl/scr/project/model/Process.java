package pl.scr.project.model;

import java.util.Comparator;
import java.util.Random;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Process {

	public static final Comparator<Process> PRIORITY_COMPARATOR = (o1, o2) -> Integer.compare(o2.getPriority(),
			o1.getPriority());

	private int id;
	private int unitsToProcess;

	public Process() {
		this.id = new Random().nextInt(100000);
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

	public boolean isDone() {
		return unitsToProcess == 0;
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

}
