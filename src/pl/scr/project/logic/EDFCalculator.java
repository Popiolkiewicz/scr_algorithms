package pl.scr.project.logic;

import java.util.Comparator;
import java.util.List;

import pl.scr.project.model.Process;

public class EDFCalculator extends Calculator {

	public EDFCalculator(List<Process> dataSource) {
		super(dataSource);
	}

	@Override
	public Comparator<Process> getAlgorithmComparator() {
		return (o1, o2) -> Integer.compare(getComparisionValue(o1), getComparisionValue(o2));
	}

	private Integer getComparisionValue(Process process) {
		return (process.getDeadline() + process.getArrivalTime() % process.getPeriod())
				- currentTimeUnit % process.getPeriod();
	}
}
