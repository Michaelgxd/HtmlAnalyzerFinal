import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class analyzes the word occurences in 
 * the file.
 */
public class HtmlAnalyzerProcessor {

	private Map<String, Integer> wordMap;

	public HtmlAnalyzerProcessor() {
		wordMap = new HashMap<>();
	}
	
	/**
	 * This function reads the file line by line, parses each line to get word tokens and
	 * then puts tokens into a map.
	 * 
	 * @param fileName
	 * @throws IOException
	 */
	public void readFile(String fileName) throws IOException {
		try {

			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			String line;

			while ((line = reader.readLine()) != null) {
				line = line.toLowerCase();
				line = line.replaceAll("[^\\sa-zA-Z]", "");
				line = line.replaceAll("\\s+", " ").trim();

				if (!line.isEmpty()) {
					String[] s = line.split(" ");
					for (String token : s) {
						if (wordMap.containsKey(token)) {
							Integer count = wordMap.get(token);
							wordMap.put(token, count + 1);
						} else {
							wordMap.put(token, 1);
						}
					} 
				} 
			} 

			reader.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			System.out.println("File not found: " + ex.getMessage());
			throw ex;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error occurred: " + e.getMessage());
			throw e;
		}
	}

	public List<HtmlAnalyzer> getFrequency() {

		List<Map.Entry<String, Integer>> linkedMap = new LinkedList<>(wordMap.entrySet());

		Collections.sort(linkedMap, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return -1 * o1.getValue().compareTo(o2.getValue());
			}
		});

		int num = 1;

		List<HtmlAnalyzer> list = new ArrayList<HtmlAnalyzer>();
		for (Map.Entry<String, Integer> wordFreq : linkedMap) {
			list.add(new HtmlAnalyzer(num, wordFreq.getKey(), wordFreq.getValue()));
			num++;
			if (num > 20) {
				break;
			}
		}

		return list;
	}
}
