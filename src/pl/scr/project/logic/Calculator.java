package pl.scr.project.logic;

import java.util.List;
import java.util.stream.Collectors;

import pl.scr.project.model.Process;

public abstract class Calculator {

	protected List<Process> dataSource;
	protected Integer hiperperiod;
	protected int currentTimeUnit;

	public Calculator(List<Process> dataSource) {
		this.dataSource = dataSource;
		this.dataSource.forEach(process -> {
			process.clearUnitsToProcess();
			process.clearDisplatData();
			process.clearDeadlineExceededTimeUnits();
			process.clearDeadlineOkTimeUnits();
		});
		calculateHiperperiod();
	}

	public void calculateHiperperiod() {
		List<Integer> collect = dataSource.stream().map(process -> process.getPeriod()).collect(Collectors.toList());
		Integer[] periodArray = collect.toArray(new Integer[collect.size()]);
		hiperperiod = 0;
		boolean found;
		for (int i = 1;; i++) {
			found = true;
			for (int x = 0; x < periodArray.length; x++) {
				if (i % periodArray[x] != 0) {
					found = false;
					break;
				}
			}
			if (found) {
				hiperperiod = i;
				break;
			}
		}
	}

	public int getHiperperiod() {
		if (hiperperiod == null)
			throw new IllegalStateException("Hiperokres nie wyliczony");
		return hiperperiod;
	}

	public abstract void calculate();
}
