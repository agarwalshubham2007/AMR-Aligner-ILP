import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class Evaluation {
	public HashMap<String, HashMap<String, Integer>> testData;

	public Evaluation() {
		testData = new HashMap<>();
	}

	public HashMap<String, HashMap<String, Integer>> readEvaluationDataset(String path, String fileName)
			throws IOException {
		BufferedReader br = null;
		FileReader fr = null;
		String amr_file = path + "/" + fileName;

		fr = new FileReader(amr_file);
		br = new BufferedReader(fr);

		String sCurrentLine;

		br = new BufferedReader(new FileReader(amr_file));

		String id = "";
		while ((sCurrentLine = br.readLine()) != null) {
			if (sCurrentLine.startsWith("# ::id")) {
				id = sCurrentLine.split(" ")[sCurrentLine.split(" ").length - 1].trim();
			}
			if (sCurrentLine.startsWith("# ::alignments")) {
				HashMap<String, Integer> alignments = new HashMap<>();
				String[] parts = sCurrentLine.split(" ");
				for (int i = 2; i < parts.length; i++) {
					alignments.put(parts[i].split("-")[1], Integer.parseInt(parts[i].split("-")[0]));
				}
				testData.put(id, alignments);
			}
		}
		br.close();
		fr.close();
		return testData;
	}

	public void evaluate(HashMap<String, HashMap<String, Integer>> alignedData) {
		int truePositive = 0, falsePositive = 0, total = 0;
		HashSet<String> present = new HashSet<>();
		for (String id : alignedData.keySet()) {
			if (testData.containsKey(id)) {
				HashMap<String, Integer> alignedDataInstance = alignedData.get(id);
				HashMap<String, Integer> testDataInstance = testData.get(id);
				for (String pos : testDataInstance.keySet()) {
					if (!pos.endsWith("r"))
						total++;
				}
				for (String pos : alignedDataInstance.keySet()) {
					if (testDataInstance.containsKey(pos)) {
						present.add(pos);
						if (alignedDataInstance.get(pos) == testDataInstance.get(pos))
							truePositive++;
						else {
							falsePositive++;
							//							System.out.println(id + " : " + pos + " | " + alignedDataInstance.get(pos) + " | "
							//									+ testDataInstance.get(pos));
						}
					}
				}

				for (String pos : testDataInstance.keySet()) {
					if (!present.contains(pos) && !pos.endsWith("r")) {
						System.out.println(id + " : " + pos + " | " + testDataInstance.get(pos));
					}
				}
				present.clear();
			}
		}
		System.out.println(truePositive);
		System.out.println(falsePositive);
		double precision = ((double) truePositive / (truePositive + falsePositive));
		double recall = ((double) truePositive / total);
		double fscore = 2 * precision * recall / (precision + recall);
		System.out.println("precision: " + precision);
		System.out.println("recall: " + recall);
		System.out.println("fscore: " + fscore);
	}
}
