package pl.scr.project.logic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import pl.scr.project.model.Cache;
import pl.scr.project.model.Process;

public abstract class Calculator {

	protected List<Process> dataSource;
	protected long hiperperiod;
	protected double cpuUsage;
	protected int currentTimeUnit;

	public Calculator(List<Process> dataSource) {
		this.dataSource = dataSource;
		this.dataSource.forEach(process -> {
			process.clearUnitsToProcess();
			process.clearDisplatData();
			process.clearDeadlineExceededTimeUnits();
			process.clearDeadlineOkTimeUnits();
		});
	}

	private void calculateProcessorUsage() {
		for (Process process : dataSource)
			cpuUsage += (double) process.getProcessingTime() / (double) process.getPeriod();
		System.out.println("Processor usage: " + cpuUsage);
	}

	private void calculateHiperperiod() {
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
			if (i >= Integer.MAX_VALUE) {
				hiperperiod = Integer.MAX_VALUE;
				break;
			}
			if (found) {
				hiperperiod = i;
				break;
			}
		}
		System.out.println("Hiperperiod: " + hiperperiod);
	}

	public long getHiperperiod() {
		return hiperperiod;
	}

	public double getCpuUsage() {
		return cpuUsage;
	}

	public abstract Comparator<Process> getAlgorithmComparator();

	public void calculate() {
		calculateProcessorUsage();
		calculateHiperperiod();

		List<Process> awaiting = new ArrayList<>();
		Set<Process> paused = new HashSet<>();
		Set<Process> startedNewPeriod = new HashSet<>();
		Process currentProcess = null;

		for (currentTimeUnit = 0; currentTimeUnit <= 1000; currentTimeUnit++) {
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
			awaiting.sort(getAlgorithmComparator());

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
		return currentTimeUnit >= process.getArrivalTime() && ((currentTimeUnit
				% process.getPeriod() == process.getDeadline() + process.getArrivalTime() % process.getPeriod()
				|| (currentTimeUnit % process.getPeriod() == ((process.getArrivalTime() + process.getDeadline())
						% process.getPeriod()))));
	}

	private boolean checkIfAddNewUnits(Process process) {
		return currentTimeUnit >= process.getArrivalTime()
				&& (currentTimeUnit % process.getPeriod() == (process.getArrivalTime() % process.getPeriod()));
	}

}
