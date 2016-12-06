package pl.scr.project.utils;

import java.util.Random;

public class ColorUtil {

	public static String getRandomColor() {
		Random randomRGB = new Random();
		int red = randomRGB.nextInt(200) + 55;
		int green = randomRGB.nextInt(200) + 55;
		int blue = randomRGB.nextInt(200) + 55;
		return String.format("%d, %d, %d", red, green, blue);
	}
}
