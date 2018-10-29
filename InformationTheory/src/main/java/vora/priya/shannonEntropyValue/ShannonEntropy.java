package vora.priya.shannonEntropyValue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShannonEntropy {
	private static List<String> values = new ArrayList<String>();

	public static void main(String[] args) {
		values = readFile();
		calculateShannonEntropy(values);
		System.out.println("---------------");
	}

	public static Double calculateShannonEntropy(List<String> values) {
		Map<String, Integer> shannonMap = new HashMap<String, Integer>();
		// count the occurrences of each value
		for (String eachValue : values) {
			if (!shannonMap.containsKey(eachValue)) {
				shannonMap.put(eachValue, 0);
			}
			shannonMap.put(eachValue, shannonMap.get(eachValue) + 1);
		}
		shannonMap.forEach((key, value) -> System.out.println(key + ":" + value));

		Double entropyValue = 0.0;
		for (String eachWord : shannonMap.keySet()) {
			Double frequency = (double) shannonMap.get(eachWord) / values.size();
			entropyValue -= frequency * (Math.log(frequency) / Math.log(2));
		}
		System.out.println("\nResult: " + entropyValue);
		return entropyValue;
	}

	public static List<String> readFile() {
		List<String> wordsList = new ArrayList<>();
		String file = "/home/priya/Desktop/Files/Elephants";

		File returnFile = new File(file);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(returnFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String st;
		String readFileBody = "";
		try {
			while ((st = br.readLine()) != null) {
				readFileBody += st;
			}
			System.out.println("File Content: " + readFileBody + "\n");
			String[] eachWords = readFileBody.split("\\s+");
			for (String word : eachWords) {
				wordsList.add(word);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return wordsList;
	}
}
