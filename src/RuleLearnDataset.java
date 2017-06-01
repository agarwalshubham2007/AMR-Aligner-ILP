import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

public class RuleLearnDataset extends Preprocessing {

	/*
	 * generating a sub-dataset for learning modal concepts like "possible", "likely", "obligate", "permit", "recommend", "prefer" 
	 */
	public ArrayList<DataInstance> generateModalConceptDataset(String modalConcept,
			ArrayList<DataInstance> dataInstances) {
		ArrayList<DataInstance> modalConceptDataset = new ArrayList<>();
		for (DataInstance d : dataInstances) {
			ArrayList<String> concepts = new ArrayList<>();
			getConcepts(d.root, concepts);
			if (concepts.contains(modalConcept)) {
				modalConceptDataset.add(d);
			}
		}
		return modalConceptDataset;
	}

	public ArrayList<Entry<DataInstance, ArrayList<String>>> generateModalConceptDataset(
			ArrayList<DataInstance> dataInstances) {
		ArrayList<Map.Entry<DataInstance, ArrayList<String>>> modalConceptDataset = new ArrayList<>();
		for (DataInstance d : dataInstances) {
			boolean flag = false;
			ArrayList<String> concepts = new ArrayList<>();
			ArrayList<String> mconcepts = new ArrayList<>();
			getConcepts(d.root, concepts);
			for (String c : Constants.ModalConceptList) {
				if (concepts.contains(c)) {
					flag = true;
					mconcepts.add(c);
				}
			}
			if (flag) {
				modalConceptDataset.add(new ModalConceptDatasetEntry(d, mconcepts));
			}
		}

		return modalConceptDataset;
	}
}
