package pl.scr.project.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import pl.scr.project.constants.ProcessState;
import pl.scr.project.model.Process;

public class PTCalculator {

	private List<Process> dataSource;
	private int hiperperiod;

	private int hiperperiodUnit;
	private List<Process> awaiting = new ArrayList<>();
	private Process processing;
	private List<Integer> processIdByHiperperiodUnit;

	public PTCalculator(List<Process> dataSource) {
		this.dataSource = dataSource;
		this.dataSource.sort((o1, o2) -> Integer.compare(o2.getPriority(), o1.getPriority()));
		this.hiperperiod = calculateHiperperiod();
	}

	public void calculate() {
		for (this.hiperperiodUnit = 0; hiperperiodUnit < hiperperiod; hiperperiodUnit++) {
			checkStartState();
			markProcessing();
			updateDisplayData();
		}
	}

	private void checkStartState() {
		this.awaiting.clear();
		for (Process process : dataSource) {
			switch (process.getState()) {
			case NOT_ARRIVED:
				if (process.getArrivalTime() == hiperperiodUnit)
					process.setState(ProcessState.AWAITING);
			case AWAITING:
				awaiting.add(process);
				break;
			case PAUSED:
				break;
			case PROCESSED:
				break;
			case PROCESSING:
				break;
			default:
				throw new IllegalStateException();
			}
		}
	}

	private void markProcessing() {
		awaiting.sort(Process.STATE_COMPARATOR);
		if (processing == null)
			processing = awaiting.get(0);
		else {
			Process hpProcess = awaiting.get(0);
			if (hpProcess.getPriority() > processing.getPriority())
				processing = hpProcess;
		}
	}

	private void updateDisplayData() {
		processIdByHiperperiodUnit.add(hiperperiodUnit, processing.getID());
	}

	public int calculateHiperperiod() {
		List<Integer> collect = dataSource.stream().map(process -> process.getPeriod()).collect(Collectors.toList());
		Integer[] periodArray = collect.toArray(new Integer[collect.size()]);
		int hiperperiod = 0;
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
		return hiperperiod;
	}

	// ChartDisplayData cds = new ChartDisplayData(dataSource.size(),
	// hiperperiod);

	// for (int j = 0; j < dataSource.size(); j++) {
	// Process process = dataSource.get(j);
	// List<ChartElement> chartElements = new ArrayList<>();
	// for (int i = 0; i < hiperperiod; i += process.getPeriod()) {
	// ChartElement chartElement = new ChartElement();
	// chartElement.setStart(i);
	// chartElement.setEnd(i + process.getProcessingTime());
	// chartElements.add(chartElement);
	// }
	// cds.getSeries().put(j, chartElements);
	// }
}
