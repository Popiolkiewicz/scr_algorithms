package pl.scr.project.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.scr.project.constants.Constants;
import pl.scr.project.model.Process;

public class ProcessRandomizer {

	public synchronized static List<Process> generateRandomData() {
		List<Process> result = new ArrayList<>();
		Random random = new Random();
		int processesCount = random.nextInt(Constants.MAX_PROCESS_COUNT);
		for (int i = 0; i <= processesCount; i++) {
			Process process = new Process();
			process.setArrivalTime(random.nextInt(5));
			process.setPeriod(random.nextInt(10));
			process.setProcessingTime(random.nextInt(process.getPeriod() + 1));
			process.setDeadline(random.nextInt(process.getPeriod() + 1));
			process.setPriority(random.nextInt(100));
			result.add(process);
		}
		return result;
	}

}
