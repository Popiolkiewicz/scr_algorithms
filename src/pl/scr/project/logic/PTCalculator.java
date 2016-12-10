package pl.scr.project.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import pl.scr.project.model.Cache;
import pl.scr.project.model.Process;

public class PTCalculator {

	private List<Process> dataSource;
	private Integer hiperperiod;
	private int currentTimeUnit;

	public PTCalculator(List<Process> dataSource) {
		this.dataSource = dataSource;
		this.dataSource.forEach(process -> {
			process.clearUnitsToProcess();
			process.clearDisplatData();
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

	public void calculate() {
		List<Process> awaiting = new ArrayList<>();
		Set<Process> paused = new HashSet<>();
		Set<Process> startedNewPeriod = new HashSet<>();
		Process currentProcess = null;

		for (currentTimeUnit = 0; currentTimeUnit < hiperperiod; currentTimeUnit++) {
			//Preparation
			startedNewPeriod.clear();
			awaiting.clear();
			dataSource.forEach(process -> {
				if (checkIfInterrupt(process)) {
					process.clearUnitsToProcess();
					paused.remove(process);
				}
				if (checkIfAddNewUnits(process)) {
					process.incUnitsToProcess();
					startedNewPeriod.add(process);
				}
				if (!process.isDone())
					awaiting.add(process);
			});

			if (awaiting.isEmpty()) {
				currentProcess = null;
				continue;
			}
			awaiting.sort(Process.PRIORITY_COMPARATOR);

			//Determining new process
			Process incomingProcess = awaiting.get(0);
			if (currentProcess != null && !Objects.equals(currentProcess.getId(), incomingProcess.getId())) {
				if (!currentProcess.isDone() && !startedNewPeriod.contains(currentProcess))
					paused.add(currentProcess);
				paused.remove(incomingProcess);
			}
			paused.forEach(process -> process.getDisplayData().put(currentTimeUnit, false));

			currentProcess = incomingProcess;
			currentProcess.decUnitsToProcess();
			currentProcess.getDisplayData().put(currentTimeUnit, true);
		}
	}

	private boolean checkIfInterrupt(Process process) {
		return Cache.get().getInterruptAfterDeadline()
				&& currentTimeUnit % process.getPeriod() == process.getDeadline();
	}

	private boolean checkIfAddNewUnits(Process process) {
		if (process.getArrivalTime().equals(currentTimeUnit))
			return true;
		return currentTimeUnit % process.getPeriod() == 0;
	}

}
