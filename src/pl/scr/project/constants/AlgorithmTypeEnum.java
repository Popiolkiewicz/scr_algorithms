package pl.scr.project.constants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum AlgorithmTypeEnum {

	PT("Priorytetowe"), RMS("RMS"), EDF("EDS");

	private String name;

	private AlgorithmTypeEnum(final String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static List<String> getDescsAsList() {
		return Arrays.asList(AlgorithmTypeEnum.values())
				.stream()
				.map(value -> value.getName())
				.collect(Collectors.toList());
	}
}