package pl.scr.project.logic;

import java.util.Comparator;
import java.util.List;

import pl.scr.project.model.Process;

public class PTCalculator extends Calculator {

	public PTCalculator(List<Process> dataSource) {
		super(dataSource);
	}

	@Override
	public Comparator<Process> getAlgorithmComparator() {
		return (o1, o2) -> Integer.compare(o2.getPriority(), o1.getPriority());
	}

}
