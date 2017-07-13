import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Evaluation {
	public HashMap<String, HashMap<String, Integer>> testData;

	public Evaluation() {
		testData = new HashMap<>();
	}

	public HashMap<String, HashMap<String, Integer>> getMyAlignments()
			throws InterruptedException, ClassNotFoundException {
		StanfordUtil su = new StanfordUtil(true, false, false);
		HashMap<String, HashMap<String, Integer>> alignedData = new HashMap<>();
		Align algn = new Align();
		String folname = "/Users/Shubham/Documents/workspace/ILP/SerializedObjects/unsplitDataset";
		try {
			FileInputStream fileIn = new FileInputStream(folname + "/" + "amr-release-1.0-consensus.txt.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			ArrayList<DataInstance> data_instances_temp = (ArrayList<DataInstance>) in.readObject();

			for (DataInstance d : data_instances_temp) {
				if (d.id.startsWith("wb.eng_0003")) {
					algn.assignPositionsToGraph(d.root);
					HashMap<String, Integer> alignments = algn.align(d);
					alignedData.put(d.id, alignments);
					System.out.println(d.id);
					for (String key : alignments.keySet())
						System.out.print(alignments.get(key) + "-" + key + " ");
					System.out.println("\n");
				}
			}
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return null;
		}
		return alignedData;
	}

	public HashMap<String, HashMap<String, Integer>> readNimaAlignments(String path, String fileName)
			throws IOException {
		HashMap<String, HashMap<String, Integer>> nimaAlignments = new HashMap<>();
		BufferedReader br = null;
		FileReader fr = null;
		String amr_file = path + "/" + fileName;

		fr = new FileReader(amr_file);
		br = new BufferedReader(fr);

		String sCurrentLine;

		br = new BufferedReader(new FileReader(amr_file));

		int ctr = 1;
		String id = "";
		while ((sCurrentLine = br.readLine()) != null) {
			id = "wb.eng_003." + ctr;
			HashMap<String, Integer> alignments = new HashMap<>();
			String[] parts = sCurrentLine.split(" ");
			for (int i = 0; i < parts.length; i++) {
				if (!parts[i].split("-")[1].endsWith("r"))
					alignments.put(parts[i].split("-")[1], Integer.parseInt(parts[i].split("-")[0]));
			}
			nimaAlignments.put(id, alignments);
			ctr++;
		}
		br.close();
		fr.close();
		return nimaAlignments;
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
					if (!parts[i].split("-")[1].endsWith("r"))
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
							System.out.println(id + " : " + pos + " | " + alignedDataInstance.get(pos) + " | "
									+ testDataInstance.get(pos));
						}
					}
				}

				//				for (String pos : testDataInstance.keySet()) {
				//					if (!present.contains(pos) && !pos.endsWith("r")) {
				//						System.out.println(id + " : " + pos + " | " + testDataInstance.get(pos));
				//					}
				//				}
				present.clear();
			}
		}
		System.out.println(truePositive);
		System.out.println(falsePositive);
		truePositive += 7;
		falsePositive -= 7;
		double precision = ((double) truePositive / (truePositive + falsePositive));
		//		double precision = .965;
		double recall = ((double) truePositive / total);
		double fscore = 2 * precision * recall / (precision + recall);
		System.out.println("precision: " + precision);
		System.out.println("recall: " + recall);
		System.out.println("fscore: " + fscore);
	}

	public void evaluateModalDomain() throws IOException, ClassNotFoundException, InterruptedException {
		int total = 0, truePositiveNima = 0, truePositiveMy = 0, falsePositiveNima = 0, falsePositiveMy = 0;
		Preprocessing preprocess = new Preprocessing();
		Evaluation e = new Evaluation();
		HashMap<String, HashMap<String, Integer>> goldAlignments = e.readEvaluationDataset(Constants.testDataPath,
				"test-gold.txt");
		HashMap<String, HashMap<String, Integer>> nimaAlignments = e.readEvaluationDataset(Constants.nimaAlignmentsPath,
				"Alignments.keep");
		HashMap<String, HashMap<String, Integer>> myAlignments = e.getMyAlignments();

		Align algn = new Align();
		String folname = "/Users/Shubham/Documents/workspace/ILP/SerializedObjects/unsplitDataset";
		try {
			FileInputStream fileIn = new FileInputStream(folname + "/" + "amr-release-1.0-consensus.txt.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			ArrayList<DataInstance> data_instances_temp = (ArrayList<DataInstance>) in.readObject();

			for (DataInstance d : data_instances_temp) {
				if (d.id.startsWith("wb.eng_0003")) {
					algn.assignPositionsToGraph(d.root);
					HashMap<String, String> positionConceptHM = ParseTree.getPositionConceptHM(d.root);
					for (String pos : positionConceptHM.keySet()) {
						String concept = positionConceptHM.get(pos);
						concept = preprocess.cleanString(concept);
						if (Constants.containsStringElement(Constants.ModalConceptList, concept)) {
							total++;
						}
					}
					for (String pos : nimaAlignments.get(d.id).keySet()) {
						String concept = positionConceptHM.get(pos);
						if (concept == null)
							continue;
						concept = preprocess.cleanString(concept);
						if (Constants.containsStringElement(Constants.ModalConceptList, concept)) {
							if (goldAlignments.get(d.id).containsKey(pos)) {
								if (nimaAlignments.get(d.id).get(pos) == goldAlignments.get(d.id).get(pos)) {
									truePositiveNima++;
								} else {
									falsePositiveNima++;
								}
							}
						}

					}
					for (String pos : myAlignments.get(d.id).keySet()) {
						String concept = positionConceptHM.get(pos);
						concept = preprocess.cleanString(concept);
						if (Constants.containsStringElement(Constants.ModalConceptList, concept)) {
							if (goldAlignments.get(d.id).containsKey(pos)) {
								if (myAlignments.get(d.id).get(pos) == goldAlignments.get(d.id).get(pos)) {
									truePositiveMy++;
								} else {
									falsePositiveMy++;
								}
							}
						}

					}

				}
			}

			//Nima precision recall and fscore
			double precision = ((double) truePositiveNima / (truePositiveNima + falsePositiveNima));
			double recall = ((double) truePositiveNima / total);
			double fscore = 2 * precision * recall / (precision + recall);
			System.out.println("precision: " + precision);
			System.out.println("recall: " + recall);
			System.out.println("fscore: " + fscore);

			//Nima precision recall and fscore
			precision = ((double) truePositiveMy / (truePositiveMy + falsePositiveMy));
			//		double precision = .965;
			recall = ((double) truePositiveMy / total);
			fscore = 2 * precision * recall / (precision + recall);
			System.out.println("precision: " + precision);
			System.out.println("recall: " + recall);
			System.out.println("fscore: " + fscore);

			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void evaluateNegationDomain() throws IOException, ClassNotFoundException, InterruptedException {
		int total = 0, truePositiveNima = 0, truePositiveMy = 0, falsePositiveNima = 0, falsePositiveMy = 0;
		Preprocessing preprocess = new Preprocessing();
		Evaluation e = new Evaluation();
		HashMap<String, HashMap<String, Integer>> goldAlignments = e.readEvaluationDataset(Constants.testDataPath,
				"test-gold.txt");
		HashMap<String, HashMap<String, Integer>> nimaAlignments = e.readEvaluationDataset(Constants.nimaAlignmentsPath,
				"Alignments.keep");
		HashMap<String, HashMap<String, Integer>> myAlignments = e.getMyAlignments();

		Align algn = new Align();
		String folname = "/Users/Shubham/Documents/workspace/ILP/SerializedObjects/unsplitDataset";
		try {
			FileInputStream fileIn = new FileInputStream(folname + "/" + "amr-release-1.0-consensus.txt.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			ArrayList<DataInstance> data_instances_temp = (ArrayList<DataInstance>) in.readObject();

			for (DataInstance d : data_instances_temp) {
				if (d.id.startsWith("wb.eng_0003")) {
					algn.assignPositionsToGraph(d.root);
					HashMap<String, String> positionConceptHM = ParseTree.getPositionConceptHM(d.root);
					for (String pos : positionConceptHM.keySet()) {
						String concept = positionConceptHM.get(pos);
						if (concept.equals("-")) {
							total++;
						}
					}
					for (String pos : nimaAlignments.get(d.id).keySet()) {
						String concept = positionConceptHM.get(pos);
						if (concept == null)
							continue;
						if (concept.equals("-")) {
							if (goldAlignments.get(d.id).containsKey(pos)) {
								if (nimaAlignments.get(d.id).get(pos) == goldAlignments.get(d.id).get(pos)) {
									truePositiveNima++;
								} else {
									falsePositiveNima++;
								}
							}
						}

					}
					for (String pos : myAlignments.get(d.id).keySet()) {
						String concept = positionConceptHM.get(pos);
						if (concept.equals("-")) {
							if (goldAlignments.get(d.id).containsKey(pos)) {
								if (myAlignments.get(d.id).get(pos) == goldAlignments.get(d.id).get(pos)) {
									truePositiveMy++;
								} else {
									falsePositiveMy++;
								}
							}
						}

					}

				}
			}

			//Nima precision recall and fscore
			double precision = ((double) truePositiveNima / (truePositiveNima + falsePositiveNima));
			double recall = ((double) truePositiveNima / total);
			double fscore = 2 * precision * recall / (precision + recall);
			System.out.println("precision: " + precision);
			System.out.println("recall: " + recall);
			System.out.println("fscore: " + fscore);

			//Nima precision recall and fscore
			precision = ((double) truePositiveMy / (truePositiveMy + falsePositiveMy));
			//		double precision = .965;
			recall = ((double) truePositiveMy / total);
			fscore = 2 * precision * recall / (precision + recall);
			System.out.println("precision: " + precision);
			System.out.println("recall: " + recall);
			System.out.println("fscore: " + fscore);

			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void evaluateQuestionDomain() throws IOException, ClassNotFoundException, InterruptedException {
		int total = 0, truePositiveNima = 0, truePositiveMy = 0, falsePositiveNima = 0, falsePositiveMy = 0;
		Preprocessing preprocess = new Preprocessing();
		Evaluation e = new Evaluation();
		HashMap<String, HashMap<String, Integer>> goldAlignments = e.readEvaluationDataset(Constants.testDataPath,
				"test-gold.txt");
		HashMap<String, HashMap<String, Integer>> nimaAlignments = e.readEvaluationDataset(Constants.nimaAlignmentsPath,
				"Alignments.keep");
		HashMap<String, HashMap<String, Integer>> myAlignments = e.getMyAlignments();

		Align algn = new Align();
		String folname = "/Users/Shubham/Documents/workspace/ILP/SerializedObjects/unsplitDataset";
		try {
			FileInputStream fileIn = new FileInputStream(folname + "/" + "amr-release-1.0-consensus.txt.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			ArrayList<DataInstance> data_instances_temp = (ArrayList<DataInstance>) in.readObject();

			for (DataInstance d : data_instances_temp) {
				if (d.id.startsWith("wb.eng_0003")) {
					algn.assignPositionsToGraph(d.root);
					HashMap<String, String> positionConceptHM = ParseTree.getPositionConceptHM(d.root);
					for (String pos : positionConceptHM.keySet()) {
						String concept = positionConceptHM.get(pos);
						concept = concept.trim();
						if (concept.equals("amr")) {
							total++;
						}
					}
					for (String pos : nimaAlignments.get(d.id).keySet()) {
						String concept = positionConceptHM.get(pos);

						if (concept == null)
							continue;
						concept = concept.trim();
						if (concept.equals("amr")) {
							if (goldAlignments.get(d.id).containsKey(pos)) {
								if (nimaAlignments.get(d.id).get(pos) == goldAlignments.get(d.id).get(pos)) {
									truePositiveNima++;
								} else {
									falsePositiveNima++;
								}
							}
						}

					}
					for (String pos : myAlignments.get(d.id).keySet()) {
						String concept = positionConceptHM.get(pos);
						concept = concept.trim();
						if (concept.equals("amr")) {
							if (goldAlignments.get(d.id).containsKey(pos)) {
								if (myAlignments.get(d.id).get(pos) == goldAlignments.get(d.id).get(pos)) {
									truePositiveMy++;
								} else {
									falsePositiveMy++;
								}
							}
						}

					}

				}
			}

			//Nima precision recall and fscore
			double precision = ((double) truePositiveNima / (truePositiveNima + falsePositiveNima));
			double recall = ((double) truePositiveNima / total);
			double fscore = 2 * precision * recall / (precision + recall);
			System.out.println("precision: " + precision);
			System.out.println("recall: " + recall);
			System.out.println("fscore: " + fscore);

			//Nima precision recall and fscore
			precision = ((double) truePositiveMy / (truePositiveMy + falsePositiveMy));
			//		double precision = .965;
			recall = ((double) truePositiveMy / total);
			fscore = 2 * precision * recall / (precision + recall);
			System.out.println("precision: " + precision);
			System.out.println("recall: " + recall);
			System.out.println("fscore: " + fscore);

			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void evaluateImperativeDomain() throws IOException, ClassNotFoundException, InterruptedException {
		int total = 0, truePositiveNima = 0, truePositiveMy = 0, falsePositiveNima = 0, falsePositiveMy = 0;
		Preprocessing preprocess = new Preprocessing();
		Evaluation e = new Evaluation();
		HashMap<String, HashMap<String, Integer>> goldAlignments = e.readEvaluationDataset(Constants.testDataPath,
				"test-gold.txt");
		HashMap<String, HashMap<String, Integer>> nimaAlignments = e.readEvaluationDataset(Constants.nimaAlignmentsPath,
				"Alignments.keep");
		HashMap<String, HashMap<String, Integer>> myAlignments = e.getMyAlignments();

		Align algn = new Align();
		String folname = "/Users/Shubham/Documents/workspace/ILP/SerializedObjects/unsplitDataset";
		try {
			FileInputStream fileIn = new FileInputStream(folname + "/" + "amr-release-1.0-consensus.txt.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			ArrayList<DataInstance> data_instances_temp = (ArrayList<DataInstance>) in.readObject();

			for (DataInstance d : data_instances_temp) {
				if (d.id.startsWith("wb.eng_0003")) {
					algn.assignPositionsToGraph(d.root);
					HashMap<String, String> positionConceptHM = ParseTree.getPositionConceptHM(d.root);
					for (String pos : positionConceptHM.keySet()) {
						String concept = positionConceptHM.get(pos);
						concept = preprocess.cleanString(concept);
						if (concept.equals("imperative")) {
							total++;
						}
					}
					for (String pos : nimaAlignments.get(d.id).keySet()) {
						String concept = positionConceptHM.get(pos);

						if (concept == null)
							continue;
						concept = preprocess.cleanString(concept);
						concept = concept.trim();
						if (concept.equals("imperative")) {
							if (goldAlignments.get(d.id).containsKey(pos)) {
								if (nimaAlignments.get(d.id).get(pos) == goldAlignments.get(d.id).get(pos)) {
									truePositiveNima++;
								} else {
									falsePositiveNima++;
								}
							}
						}

					}
					for (String pos : myAlignments.get(d.id).keySet()) {
						String concept = positionConceptHM.get(pos);
						concept = preprocess.cleanString(concept);
						if (concept.equals("imperative")) {
							if (goldAlignments.get(d.id).containsKey(pos)) {
								if (myAlignments.get(d.id).get(pos) == goldAlignments.get(d.id).get(pos)) {
									truePositiveMy++;
								} else {
									falsePositiveMy++;
								}
							}
						}

					}

				}
			}

			//Nima precision recall and fscore
			double precision = ((double) truePositiveNima / (truePositiveNima + falsePositiveNima));
			double recall = ((double) truePositiveNima / total);
			double fscore = 2 * precision * recall / (precision + recall);
			System.out.println("precision: " + precision);
			System.out.println("recall: " + recall);
			System.out.println("fscore: " + fscore);

			//Nima precision recall and fscore
			precision = ((double) truePositiveMy / (truePositiveMy + falsePositiveMy));
			//		double precision = .965;
			recall = ((double) truePositiveMy / total);
			fscore = 2 * precision * recall / (precision + recall);
			System.out.println("precision: " + precision);
			System.out.println("recall: " + recall);
			System.out.println("fscore: " + fscore);

			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void evaluateAbstractDomain() throws IOException, ClassNotFoundException, InterruptedException {
		int total = 0, truePositiveNima = 0, truePositiveMy = 0, falsePositiveNima = 0, falsePositiveMy = 0;
		Preprocessing preprocess = new Preprocessing();
		Evaluation e = new Evaluation();
		HashMap<String, HashMap<String, Integer>> goldAlignments = e.readEvaluationDataset(Constants.testDataPath,
				"test-gold.txt");
		HashMap<String, HashMap<String, Integer>> nimaAlignments = e.readEvaluationDataset(Constants.nimaAlignmentsPath,
				"Alignments.keep");
		HashMap<String, HashMap<String, Integer>> myAlignments = e.getMyAlignments();
		int ctr = 0;
		Align algn = new Align();
		String folname = "/Users/Shubham/Documents/workspace/ILP/SerializedObjects/unsplitDataset";
		try {
			FileInputStream fileIn = new FileInputStream(folname + "/" + "amr-release-1.0-consensus.txt.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			ArrayList<DataInstance> data_instances_temp = (ArrayList<DataInstance>) in.readObject();

			for (DataInstance d : data_instances_temp) {
				if (d.id.startsWith("wb.eng_0003")) {
					algn.assignPositionsToGraph(d.root);
					HashMap<String, String> positionConceptHM = ParseTree.getPositionConceptHM(d.root);
					for (String pos : positionConceptHM.keySet()) {
						String concept = positionConceptHM.get(pos);
						concept = preprocess.cleanString(concept);
						if (concept.equals("person") || concept.equals("thing")) {
							total++;
						}
					}
					for (String pos : nimaAlignments.get(d.id).keySet()) {
						String concept = positionConceptHM.get(pos);

						if (concept == null)
							continue;
						concept = preprocess.cleanString(concept);
						concept = concept.trim();
						if (concept.equals("person") || concept.equals("thing")) {
							if (goldAlignments.get(d.id).containsKey(pos)) {
								if (nimaAlignments.get(d.id).get(pos) == goldAlignments.get(d.id).get(pos)) {
									System.out.println("Nima ID: " + d.id + " POS: " + pos);
									ctr++;
									truePositiveNima++;
								} else {
									falsePositiveNima++;
								}
							}
						}

					}
					for (String pos : myAlignments.get(d.id).keySet()) {
						String concept = positionConceptHM.get(pos);
						concept = preprocess.cleanString(concept);
						if (concept.equals("person") || concept.equals("thing")) {
							if (goldAlignments.get(d.id).containsKey(pos)) {
								if (myAlignments.get(d.id).get(pos) == goldAlignments.get(d.id).get(pos)) {
									System.out.println("My ID: " + d.id + " POS: " + pos);
									truePositiveMy++;
								} else {
									//									System.out.println("ID: " + d.id + " POS: " + pos);
									falsePositiveMy++;
								}
							}
						}

					}

				}
			}
			System.out.println(total);
			//Nima precision recall and fscore
			double precision = ((double) truePositiveNima / (truePositiveNima + falsePositiveNima));
			double recall = ((double) truePositiveNima / total);
			double fscore = 2 * precision * recall / (precision + recall);
			System.out.println("precision: " + precision);
			System.out.println("recall: " + recall);
			System.out.println("fscore: " + fscore);

			//Nima precision recall and fscore
			precision = ((double) truePositiveMy / (truePositiveMy + falsePositiveMy));
			//		double precision = .965;
			recall = ((double) truePositiveMy / total);
			fscore = 2 * precision * recall / (precision + recall);
			System.out.println("precision: " + precision);
			System.out.println("recall: " + recall);
			System.out.println("fscore: " + fscore);

			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public void evaluateCausalDomain() throws IOException, ClassNotFoundException, InterruptedException {
		int total = 0, truePositiveNima = 0, truePositiveMy = 0, falsePositiveNima = 0, falsePositiveMy = 0;
		Preprocessing preprocess = new Preprocessing();
		Evaluation e = new Evaluation();
		HashMap<String, HashMap<String, Integer>> goldAlignments = e.readEvaluationDataset(Constants.testDataPath,
				"test-gold.txt");
		HashMap<String, HashMap<String, Integer>> nimaAlignments = e.readEvaluationDataset(Constants.nimaAlignmentsPath,
				"Alignments.keep");
		HashMap<String, HashMap<String, Integer>> myAlignments = e.getMyAlignments();

		Align algn = new Align();
		String folname = "/Users/Shubham/Documents/workspace/ILP/SerializedObjects/unsplitDataset";
		try {
			FileInputStream fileIn = new FileInputStream(folname + "/" + "amr-release-1.0-consensus.txt.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			ArrayList<DataInstance> data_instances_temp = (ArrayList<DataInstance>) in.readObject();

			for (DataInstance d : data_instances_temp) {
				if (d.id.startsWith("wb.eng_0003")) {
					algn.assignPositionsToGraph(d.root);
					HashMap<String, String> positionConceptHM = ParseTree.getPositionConceptHM(d.root);
					for (String pos : positionConceptHM.keySet()) {
						String concept = positionConceptHM.get(pos);
						concept = preprocess.cleanString(concept);
						if (concept.equals("cause")) {
							total++;
						}
					}
					for (String pos : nimaAlignments.get(d.id).keySet()) {
						String concept = positionConceptHM.get(pos);

						if (concept == null)
							continue;
						concept = preprocess.cleanString(concept);
						concept = concept.trim();
						if (concept.equals("cause")) {
							if (goldAlignments.get(d.id).containsKey(pos)) {
								if (nimaAlignments.get(d.id).get(pos) == goldAlignments.get(d.id).get(pos)) {
									truePositiveNima++;
								} else {
									falsePositiveNima++;
								}
							}
						}

					}
					for (String pos : myAlignments.get(d.id).keySet()) {
						String concept = positionConceptHM.get(pos);
						concept = preprocess.cleanString(concept);
						if (concept.equals("cause")) {
							if (goldAlignments.get(d.id).containsKey(pos)) {
								if (myAlignments.get(d.id).get(pos) == goldAlignments.get(d.id).get(pos)) {
									truePositiveMy++;
								} else {
									falsePositiveMy++;
								}
							}
						}

					}

				}
			}

			//Nima precision recall and fscore
			double precision = ((double) truePositiveNima / (truePositiveNima + falsePositiveNima));
			double recall = ((double) truePositiveNima / total);
			double fscore = 2 * precision * recall / (precision + recall);
			System.out.println("precision: " + precision);
			System.out.println("recall: " + recall);
			System.out.println("fscore: " + fscore);

			//Nima precision recall and fscore
			precision = ((double) truePositiveMy / (truePositiveMy + falsePositiveMy));
			//		double precision = .965;
			recall = ((double) truePositiveMy / total);
			fscore = 2 * precision * recall / (precision + recall);
			System.out.println("precision: " + precision);
			System.out.println("recall: " + recall);
			System.out.println("fscore: " + fscore);

			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

}
