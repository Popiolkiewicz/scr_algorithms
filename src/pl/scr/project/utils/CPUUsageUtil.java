package pl.scr.project.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class CPUUsageUtil {

	private TreeSet<Double> percentages = new TreeSet<>();

	public CPUUsageUtil(int processesCount, double usage) {
		for (double d = usage / processesCount * 0.7; d <= usage / processesCount * 1.4; d += 0.02)
			percentages.add(d);
	}

	public List<Double> getRange(double floor) {
		percentages.headSet(floor);
		ArrayList<Double> arrayList = new ArrayList<>(percentages);
		Collections.shuffle(arrayList);
		return arrayList;
	}

	public double getSingle(double ceiling) {
		try {
			return percentages.floor(ceiling);
		} catch (NullPointerException e) {
			return ceiling;
		}
	}
}
