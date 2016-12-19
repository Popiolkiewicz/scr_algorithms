package pl.scr.project.logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import pl.scr.project.model.Cache;
import pl.scr.project.model.Process;
import pl.scr.project.utils.CPUUsageUtil;

public class ProcessRandomizer {

	private Random random = new Random();
	private Cache cache = Cache.get();

	public synchronized List<Process> generateRandomData() {
		List<Process> result = new ArrayList<>();
		int processesCount = getProcessesCount();
		for (int i = 1; i <= processesCount; i++) {
			Process process = new Process();
			process.setArrivalTime(0);
			process.setProcessingTime(getProcessingTime());
			result.add(process);
		}

		double cpuUsageFrom = cache.getProcessorUsageFrom();
		double cpuUsageTo = cache.getProcessorUsageTo();
		double cpuUsageOverall = cpuUsageFrom + (cpuUsageTo - cpuUsageFrom) * random.nextDouble();
		CPUUsageUtil cpuUsageUtil = new CPUUsageUtil(processesCount, cpuUsageOverall);

		Iterator<Process> iterator = result.iterator();
		while (iterator.hasNext()) {
			Process process = iterator.next();
			double calculatedCpuUsage;
			if (iterator.hasNext())
				calculatedCpuUsage = cpuUsageUtil.getRange(cpuUsageOverall).get(0);
			else
				calculatedCpuUsage = cpuUsageOverall < 0 ? 0.01 : cpuUsageUtil.getSingle(cpuUsageOverall);
			int period = (int) Math.round((process.getProcessingTime() / calculatedCpuUsage));
			cpuUsageOverall -= calculatedCpuUsage;
			process.setPeriod(period);
			if (cache.getDeadlineEqualsPeriod())
				process.setDeadline(process.getPeriod());
			else {
				int deadline = process.getProcessingTime() + random.nextInt(process.getPeriod());
				if (deadline > process.getPeriod())
					deadline = process.getPeriod() - random.nextInt(2);
				process.setDeadline(deadline);
			}
			process.setPriority(random.nextInt(100));
		}
		return result;
	}

	private int getProcessesCount() {
		int processesRangeFrom = cache.getProcessesRangeFrom();
		int processesRangeTo = cache.getProcessesRangeTo();
		return random.nextInt(processesRangeTo - processesRangeFrom + 1) + processesRangeFrom;
	}

	private int getProcessingTime() {
		int upperBound = random.nextInt(200);
		if (upperBound <= 100)
			return 1;
		if ((upperBound -= 100) <= 99)
			return (upperBound / 11) + 1;
		return 10;
	}
}
