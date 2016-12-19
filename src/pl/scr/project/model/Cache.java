package pl.scr.project.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;

public class Cache {

	private static Cache cache;

	private Cache() {
	}

	public static final Cache get() {
		return cache != null ? cache : (cache = new Cache());
	}

	private DoubleProperty processorUsage = new SimpleDoubleProperty();
	private LongProperty hiperperiod = new SimpleLongProperty();

	public DoubleProperty processorUsage() {
		return processorUsage;
	}

	public final void setProcessorUsage(double processorUsage) {
		this.processorUsage.set(processorUsage);
	}

	public double getProcessorUsage() {
		return processorUsage.get();
	}

	public LongProperty hiperperiod() {
		return hiperperiod;
	}

	public void setHiperperiod(long hiperperiod) {
		this.hiperperiod.set(hiperperiod);
	}

	public long getHiperperiod() {
		return hiperperiod.get();
	}

	// Settings

	private BooleanProperty interruptAfterDeadline = new SimpleBooleanProperty(false);
	private BooleanProperty deadlineEqualsPeriod = new SimpleBooleanProperty(true);
	private IntegerProperty processesRangeFrom = new SimpleIntegerProperty(3);
	private IntegerProperty processesRangeTo = new SimpleIntegerProperty(10);
	private DoubleProperty processorUsageFrom = new SimpleDoubleProperty(0.8);
	private DoubleProperty processorUsageTo = new SimpleDoubleProperty(1.1);

	public final BooleanProperty interruptAfterDeadline() {
		return interruptAfterDeadline;
	}

	public boolean getInterruptAfterDeadline() {
		return interruptAfterDeadline.get();
	}

	public BooleanProperty deadlineEqualsPeriod() {
		return deadlineEqualsPeriod;
	}

	public boolean getDeadlineEqualsPeriod() {
		return deadlineEqualsPeriod.get();
	}

	public IntegerProperty processesRangeFrom() {
		return processesRangeFrom;
	}

	public int getProcessesRangeFrom() {
		return processesRangeFrom.get();
	}

	public IntegerProperty processesRangeTo() {
		return processesRangeTo;
	}

	public int getProcessesRangeTo() {
		return processesRangeTo.get();
	}

	public DoubleProperty processorUsageFrom() {
		return processorUsageFrom;
	}

	public double getProcessorUsageFrom() {
		return processorUsageFrom.get();
	}

	public DoubleProperty processorUsageTo() {
		return processorUsageTo;
	}

	public double getProcessorUsageTo() {
		return processorUsageTo.get();
	}

}
