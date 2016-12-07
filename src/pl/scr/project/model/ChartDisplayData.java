package pl.scr.project.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartDisplayData {

	private Map<Integer, List<ChartElement>> series = new HashMap<>();
	private int hiperperiod;

	public ChartDisplayData(int dataSourceSize, int hiperperiod) {
		this.hiperperiod = hiperperiod;
		this.series = new HashMap<>(dataSourceSize);
	}

	public Map<Integer, List<ChartElement>> getSeries() {
		return series;
	}

	public int getHiperperiod() {
		return hiperperiod;
	}

	@Override
	public String toString() {
		return "ChartDisplayData [hiperperiod=" + hiperperiod + ", series=" + series + "]";
	}

}