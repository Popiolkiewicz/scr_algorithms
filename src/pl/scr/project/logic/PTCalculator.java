package pl.scr.project.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import pl.scr.project.model.Cache;
import pl.scr.project.model.Process;

public class PTCalculator extends Calculator {

	public PTCalculator(List<Process> dataSource) {
		super(dataSource);
	}

	@Override
	public void calculate() {
		List<Process> awaiting = new ArrayList<>();
		Set<Process> paused = new HashSet<>();
		Set<Process> startedNewPeriod = new HashSet<>();
		Process currentProcess = null;

		for (currentTimeUnit = 0; currentTimeUnit <= hiperperiod; currentTimeUnit++) {
			// Preparation
			startedNewPeriod.clear();
			awaiting.clear();
			dataSource.forEach(process -> {
				if (checkIfDeadlineReached(process)) {
					if (process.getUnitsToProcess() > 0)
						process.getDeadlineExceededTimeUnits().add(currentTimeUnit);
					else
						process.getDeadlineOkTimeUnits().add(currentTimeUnit);
					if (checkIfInterrupt()) {
						process.clearUnitsToProcess();
						paused.remove(process);
					}
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

			// Determining new process
			Process incomingProcess = awaiting.get(0);
			if (currentProcess != null && !Objects.equals(currentProcess.getId(), incomingProcess.getId())) {
				if (!currentProcess.isDone() && !currentProcess.isOneProcessed()
						&& !startedNewPeriod.contains(currentProcess))
					paused.add(currentProcess);
				paused.remove(incomingProcess);
			}
			paused.forEach(process -> process.getDisplayData().put(currentTimeUnit, false));

			currentProcess = incomingProcess;
			currentProcess.decUnitsToProcess();
			currentProcess.getDisplayData().put(currentTimeUnit, true);
		}
	}

	private boolean checkIfInterrupt() {
		return Cache.get().getInterruptAfterDeadline();
	}

	private boolean checkIfDeadlineReached(Process process) {
		return (currentTimeUnit % process.getPeriod() == process.getDeadline()
				|| (currentTimeUnit % process.getPeriod() == 0 && process.getPeriod() == process.getDeadline()));
	}

	private boolean checkIfAddNewUnits(Process process) {
		if (process.getArrivalTime().equals(currentTimeUnit))
			return true;
		return currentTimeUnit % process.getPeriod() == 0;
	}

}
